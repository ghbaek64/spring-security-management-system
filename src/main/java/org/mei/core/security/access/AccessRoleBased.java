package org.mei.core.security.access;

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

import java.util.Collection;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class AccessRoleBased implements AccessDecisionManager {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private MessageSourceAccessor messageSourceAccessor = SpringSecurityMessageSource.getAccessor();
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

		if (authentication == null) throw new AuthenticationCredentialsNotFoundException(messageSourceAccessor.getMessage("AccountStatusUserDetailsChecker.disabled"));

		// ROLE_ANONYMOUS 인 경우
		if (authenticationTrustResolver.isAnonymous(authentication)) throw new AuthenticationCredentialsNotFoundException(messageSourceAccessor.getMessage("AccountStatusUserDetailsChecker.disabled"));
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
