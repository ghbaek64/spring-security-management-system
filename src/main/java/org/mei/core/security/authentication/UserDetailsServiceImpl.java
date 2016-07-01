package org.mei.core.security.authentication;

import org.mei.core.security.authorization.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 8.
 */
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	private final ConsumerDetailsService consumerDetailsService;

	public UserDetailsServiceImpl(ConsumerDetailsService consumerDetailsService) {
		this.consumerDetailsService = consumerDetailsService;
	}

	public ConsumerDetailsService getConsumerDetailsService() {
		return consumerDetailsService;
	}

	@Override
	public Consumer loadUserByUsername(String username) throws UsernameNotFoundException {
		return consumerDetailsService.loadUserByUsername(username);
	}
}
