package org.mei.core.security.service;

import org.mei.core.security.authorization.Privilege;
import org.mei.core.security.authorization.Role;
import org.mei.core.security.enums.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Consumer consumer = consumerService.getConsumerDetails(username);

			if (consumer == null) return null;

			boolean accountNonLocked = !consumer.getRole().equals("ROLE_BLOCK");

			Set<Role> authorities = new HashSet<>();
			authorities.add(new Role(consumer.getRole(), null));

			Set<Privilege> privileges = new HashSet<>();
			privileges.add(new Privilege(Permission.LIST));
			privileges.add(new Privilege(Permission.WRITE));
			authorities.add(new Role("ROLE_PERM_0001", privileges));

			return new User(consumer.getUserId(), consumer.getPassword(), true, true, true, accountNonLocked, authorities);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}
}
