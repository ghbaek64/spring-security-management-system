package org.mei.core.security.access;

import org.mei.core.security.authorization.Role;
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

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		FilterInvocation fi = (FilterInvocation) object;
		HttpServletRequest request = fi.getRequest();
		String path = request.getServletPath();
		String method = request.getMethod();

		if (configAttributes == null) return;

		List<ConfigAttribute> needRoles = (List<ConfigAttribute>) configAttributes;
		String needRole = null;
		for(ConfigAttribute configAttribute : needRoles) {
			String role = configAttribute.getAttribute();
			if (role == null) continue;
			if (role.startsWith("ROLE_PERM_")) needRole = role;
		}

		Permission needPermission = accessMatchingRole.needPermission(needRole, path, Method.valueOf(method));

		if (logger.isDebugEnabled()) {
			logger.debug("request path: " + path);
			logger.debug("request method: " + method);
			logger.debug("need role: " + needRole);
			logger.debug("need permission: " + needPermission);
		}

		if (configAttributes == null && needPermission == null) return;

		if (authentication == null) throw new AuthenticationCredentialsNotFoundException(messageSourceAccessor.getMessage("AccountStatusUserDetailsChecker.disabled"));

		// ROLE_ANONYMOUS 인 경우
		if (authenticationTrustResolver.isAnonymous(authentication)) throw new AuthenticationCredentialsNotFoundException(messageSourceAccessor.getMessage("AccountStatusUserDetailsChecker.disabled"));

		Collection<Role> authorities = (Collection<Role>) authentication.getAuthorities();

		if (logger.isDebugEnabled()) {
			logger.debug("has roles: " + authorities);
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
