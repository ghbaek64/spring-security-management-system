package org.mei.core.security;

import org.mei.core.security.service.ConsumerDetailsService;
import org.mei.core.security.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 7.
 */
public class ConsumerAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(ConsumerAuthenticationProvider.class);

	private final ConsumerDetailsService consumerDetailsService;
	private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
	private MessageSourceAccessor messageSourceAccessor = SpringSecurityMessageSource.getAccessor();

	public ConsumerAuthenticationProvider(ConsumerDetailsService consumerDetailsService) {
		this.consumerDetailsService = consumerDetailsService;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		UserDetails userDetails = consumerDetailsService.loadUserByUsername(username);

		if (userDetails == null) {
			throw new UsernameNotFoundException(messageSourceAccessor.getMessage("DigestAuthenticationFilter.usernameNotFound", new String[]{ username }));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("username: " + username);
			logger.debug("password: " + password);
			logger.debug("password encoding: " + passwordEncoder.encode(password));

			logger.debug("userDetails username: " + userDetails.getUsername());
			logger.debug("userDetails password: " + userDetails.getPassword());
		}

		if ( !passwordEncoder.matches(password, userDetails.getPassword()) ) {
			throw new BadCredentialsException(messageSourceAccessor.getMessage("AbstractLdapAuthenticationProvider.emptyPassword"));
		}

		if ( !userDetails.isAccountNonLocked() ) {
			throw new LockedException(messageSourceAccessor.getMessage("AbstractUserDetailsAuthenticationProvider.locked"));
		}

		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, passwordEncoder.encode(password), userDetails.getAuthorities());
		ConsumerService consumerService = consumerDetailsService.getConsumerService();

		// 로그인 후 회원정보를 업데이트한다
		if (consumerService != null) consumerService.saveMemberLoginSuccess(authentication, userDetails);

		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
