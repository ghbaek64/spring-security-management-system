package org.mei.core.security.service;

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
public class ConsumerDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(ConsumerDetailsService.class);

	private final ConsumerService consumerService;

	public ConsumerDetailsService(ConsumerService consumerService) {
		this.consumerService = consumerService;
	}

	public ConsumerService getConsumerService() {
		return consumerService;
	}

	@Override
	public Consumer loadUserByUsername(String username) throws UsernameNotFoundException {
		return consumerService.loadUserByUsername(username);
	}
}
