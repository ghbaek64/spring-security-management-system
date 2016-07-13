package org.mei.core.security.access;

import org.mei.core.security.authorization.ConsumerManager;
import org.mei.core.security.enums.Method;
import org.mei.core.security.enums.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * 사용자가 접근한 경로에 대한 권한(Role, Permission)을 확인하고 접근 허가여부를 판단한다.
 * @see AccessDecisionManager
 *
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class AccessDecisionService implements AccessDecisionManager {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final AccessMatchingService accessMatchingService;

	public AccessDecisionService(AccessMatchingService accessMatchingService) {
		this.accessMatchingService = accessMatchingService;
	}

	private MessageSourceAccessor messageSourceAccessor = SpringSecurityMessageSource.getAccessor();
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	private ConsumerManager consumerAuthentication;

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		FilterInvocation fi = (FilterInvocation) object;
		HttpServletRequest request = fi.getRequest();
		String path = request.getServletPath();
		Method method = Method.valueOf(request.getMethod());

		if (authentication == null) throw new AuthenticationCredentialsNotFoundException(messageSourceAccessor.getMessage("AccountStatusUserDetailsChecker.disabled"));

		if (logger.isDebugEnabled()) {
			logger.debug("authentication: " + authentication);
		}
		consumerAuthentication = new ConsumerManager(authentication);
		List<String> hasAuthorities = consumerAuthentication.getAuthoritiesToString();

		AccessRule needRule = accessMatchingService.needRule(path, method);

		if (logger.isDebugEnabled()) {
			logger.debug("request path: " + path);
			logger.debug("request method: " + method);
			logger.debug("need configAttributes: " + configAttributes);
			logger.debug("need AccessRule: " + needRule);
			logger.debug("has Authorities: " + consumerAuthentication.getAuthorities());
		}

		boolean error = true;

		if (needRule == null) {
			error = false;
		} else {

			boolean isAllow = needRule.isAllow();
			List<String> roleNames = needRule.getRoleName();

			if (isAllow && roleNames.size() == 0) { // 모두 허용
				error = false;
			} else if (!isAllow && roleNames.size() == 0) { // 모두 차단
				error = true;
			} else {
				for (String roleName : roleNames) {
					if (hasAuthorities.indexOf(roleName) > -1) {
						error = !isAllow;
						break;
					}
				}
			}
		}

		if (error) {
			if (logger.isDebugEnabled()) {
				logger.debug("권한이 없어 접근할 수 없습니다.");
			}
			if (authenticationTrustResolver.isAnonymous(authentication)) {
				throw new AuthenticationCredentialsNotFoundException(messageSourceAccessor.getMessage("AbstractAccessDecisionManager.accessDenied"));
			} else {
				throw new AccessDeniedException(messageSourceAccessor.getMessage("AbstractAccessDecisionManager.accessDenied"));
			}
		}
/*
		if (hasAuthorities.indexOf(Role.ROLE_ADMIN.name()) > -1 || hasAuthorities.indexOf(Role.ROLE_MANAGER.name()) > -1) {
			if (logger.isDebugEnabled()) {
				logger.debug("관리자 권한을 가지고 있어 접근을 허가합니다.");
			}
			return;
		}
*/
		AccessPermit needPermit = accessMatchingService.needPermit(path, method);
		Permission needPermission = accessMatchingService.needPermission(path, method, needPermit);

		if (logger.isDebugEnabled()) {
			logger.debug("need AccessPermit: " + needPermit);
			logger.debug("need Permission: " + needPermission);
		}

		if (needPermission == null || needPermission == Permission.NONE) {

			if (logger.isDebugEnabled()) {
				logger.debug("permission none");
			}

			return;
		}

		List<Permission> hasPermissions = accessMatchingService.hasPermission(consumerAuthentication, needPermit.getRoleName());

		if (logger.isDebugEnabled()) {
			logger.debug("has permission: " + hasPermissions);
		}

		if (hasPermissions.indexOf(Permission.ADMIN) > -1 || hasPermissions.indexOf(Permission.MANAGER) > -1) {
			return;
		}

		if (hasPermissions.indexOf(Permission.NONE) > -1 || hasPermissions.indexOf(needPermission) == -1) {
			throw new AccessDeniedException(messageSourceAccessor.getMessage("AbstractAccessDecisionManager.accessDenied"));
		}
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
}
