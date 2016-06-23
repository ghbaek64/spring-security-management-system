package org.mei.core.security.access;

import org.mei.core.security.authentication.ConsumerAuthenticationResolver;
import org.mei.core.security.enums.Method;
import org.mei.core.security.enums.Permission;
import org.mei.core.security.enums.Role;
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
 * 권한 처리 로직
 * @see AccessDecisionManager
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class AccessRoleBased implements AccessDecisionManager {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final AccessMatchingRole accessMatchingRole;

	public AccessRoleBased(AccessMatchingRole accessMatchingRole) {
		this.accessMatchingRole = accessMatchingRole;
	}

	private MessageSourceAccessor messageSourceAccessor = SpringSecurityMessageSource.getAccessor();
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	private ConsumerAuthenticationResolver consumerAuthenticationResolver;

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		FilterInvocation fi = (FilterInvocation) object;
		HttpServletRequest request = fi.getRequest();
		String path = request.getServletPath();
		Method method = Method.valueOf(request.getMethod());

		if (authentication == null) throw new AuthenticationCredentialsNotFoundException(messageSourceAccessor.getMessage("AccountStatusUserDetailsChecker.disabled"));

		consumerAuthenticationResolver = new ConsumerAuthenticationResolver(authentication);
		List<String> hasAuthorities = consumerAuthenticationResolver.getHasAuthorities();

		AccessRole needRole = accessMatchingRole.needRole(path, method);

		if (logger.isDebugEnabled()) {
			logger.debug("request path: " + path);
			logger.debug("request method: " + method);
			logger.debug("need configAttributes: " + configAttributes);
			logger.debug("need AccessRole: " + needRole);
			logger.debug("has Authorities: " + hasAuthorities);
		}

		boolean error = true;

		if (needRole == null) {
			error = false;
		} else {

			boolean isAllow = needRole.isAllow();
			List<String> roleNames = needRole.getRoleName();

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
				throw new AuthenticationCredentialsNotFoundException(messageSourceAccessor.getMessage("AccountStatusUserDetailsChecker.disabled"));
			} else {
				throw new AccessDeniedException(messageSourceAccessor.getMessage("AbstractAccessDecisionManager.accessDenied"));
			}
		}

		if (hasAuthorities.indexOf(Role.ROLE_ADMIN.name()) > -1 || hasAuthorities.indexOf(Role.ROLE_MANAGER.name()) > -1) {
			if (logger.isDebugEnabled()) {
				logger.debug("관리자 권한을 가지고 있어 접근을 허가합니다.");
			}
			return;
		}

		AccessPermissionRole needPermissionRole = accessMatchingRole.needPermissionRole(path, method);
		Permission needPermission = accessMatchingRole.needPermission(path, method, needPermissionRole);

		if (needPermission == null || needPermission == Permission.NONE) {

			if (logger.isDebugEnabled()) {
				logger.debug("need permission none");
			}

			return;
		}

		List<Permission> hasPermissions = consumerAuthenticationResolver.getHasPermissions(needPermissionRole.getRoleName());

		if (logger.isDebugEnabled()) {
			logger.debug("need permission: " + needPermission);
			logger.debug("has permission: " + hasPermissions);
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
