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

		if (authentication == null)
			throw new AuthenticationCredentialsNotFoundException(messageSourceAccessor.getMessage("AccountStatusUserDetailsChecker.disabled"));

		AccessRole needRole = accessMatchingRole.needRole(path, method);




		consumerAuthenticationResolver = new ConsumerAuthenticationResolver(authentication);
		List<String> hasAuthorities = consumerAuthenticationResolver.getHasAuthorities();

		if (logger.isDebugEnabled()) {
			logger.debug("request path: " + path);
			logger.debug("request method: " + method);
			logger.debug("need configAttributes: " + needConfigAttributes);
			logger.debug("need role: " + needRole);
			logger.debug("has Authorities: " + hasAuthorities);
		}

		boolean error = true;

		if (needRole == null) {
			error = false;
		} else {

			boolean isAllow = needRole.isAllow();
			List<ConfigAttribute> roleNames = needRole.getRoleName();

			if (isAllow && roleNames.size() == 0) { // 모두 허용
				error = false;
			} else if (!isAllow && roleNames.size() == 0) { // 모두 차단
				error = true;
			} else {
				for (ConfigAttribute attribute : roleNames) {
					// 권한이 있는 경우
					if (hasAuthorities.indexOf(attribute.getAttribute()) > -1) {
						error = !isAllow;
						break;
					}
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Access is allow check: " + error);
		}

		if (error) {
			// ROLE_ANONYMOUS 인 경우
			if (authenticationTrustResolver.isAnonymous(authentication)) {
				throw new AuthenticationCredentialsNotFoundException(messageSourceAccessor.getMessage("AccountStatusUserDetailsChecker.disabled"));
			} else {
				throw new AccessDeniedException(messageSourceAccessor.getMessage("AbstractAccessDecisionManager.accessDenied"));
			}
		}

		if (hasAuthorities.indexOf(Role.ROLE_ADMIN) > -1 || hasAuthorities.indexOf(Role.ROLE_MANAGER) > -1) {
			return;
		}

		// 퍼미션 체크
		for (ConfigAttribute configAttribute : needRole.getRoleName()) {
			String roleName = configAttribute.getAttribute();
			Permission permission = accessMatchingRole.needPermission(roleName, path, Method.valueOf(method));

			if (permission == null) continue;
			if (permission == Permission.NONE) continue;

			List<Permission> hasPermissions = consumerAuthenticationResolver.getHasPermissions(roleName);

			if (logger.isDebugEnabled()) {
				logger.debug("need permission: " + permission);
				logger.debug("has permission: " + hasPermissions);
			}

			if (hasPermissions.indexOf(Permission.NONE) > -1 || hasPermissions.indexOf(permission) == -1) {
				throw new AccessDeniedException(messageSourceAccessor.getMessage("AbstractAccessDecisionManager.accessDenied"));
			}
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
