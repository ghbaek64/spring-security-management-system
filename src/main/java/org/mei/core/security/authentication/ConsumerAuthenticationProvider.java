package org.mei.core.security.authentication;

import org.mei.core.security.access.AccessControlService;
import org.mei.core.security.authorization.Authority;
import org.mei.core.security.authorization.Consumer;
import org.mei.core.security.authorization.ConsumerPermission;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 7.
 */
public class ConsumerAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(ConsumerAuthenticationProvider.class);

	private final UserDetailsServiceImpl userDetailsService;
	private final AccessControlService accessControlService;
	private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
	private MessageSourceAccessor messageSourceAccessor = SpringSecurityMessageSource.getAccessor();

	public ConsumerAuthenticationProvider(UserDetailsServiceImpl userDetailsService, AccessControlService accessControlService) {
		this.userDetailsService = userDetailsService;
		this.accessControlService = accessControlService;
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

		Consumer consumer = userDetailsService.loadUserByUsername(username);

		if (consumer == null) {
			throw new UsernameNotFoundException(messageSourceAccessor.getMessage("DigestAuthenticationFilter.usernameNotFound", new String[]{ username }));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("username: " + username);
			logger.debug("password: " + password);
			logger.debug("password encoding: " + passwordEncoder.encode(password));

			logger.debug("userDetails username: " + consumer.getUsername());
			logger.debug("userDetails password: " + consumer.getPassword());
		}

		if ( !passwordEncoder.matches(password, consumer.getPassword()) ) {
			throw new BadCredentialsException(messageSourceAccessor.getMessage("AbstractLdapAuthenticationProvider.emptyPassword"));
		}

		if ( !consumer.isAccountNonLocked() ) {
			throw new LockedException(messageSourceAccessor.getMessage("AbstractUserDetailsAuthenticationProvider.locked"));
		}


		List<ConsumerPermission> consumerPermissionList = accessControlService.getConsumerPermissionObject(username);

		List<Authority> authorities = new ArrayList<>();

		if (consumerPermissionList != null) {
			for(ConsumerPermission consumerPermission : consumerPermissionList) {
				authorities.add(new Authority(consumerPermission.getRoleName(), consumerPermission.getPermissions()));
			}
		}

		Authentication auth = new UsernamePasswordAuthenticationToken(consumer, passwordEncoder.encode(password), authorities);
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
