package org.mei.core.security.authentication;

import org.mei.core.security.authorization.Authority;
import org.mei.core.security.authorization.Consumer;
import org.mei.core.security.authorization.Privilege;
import org.mei.core.security.enums.Permission;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class ConsumerAuthenticationResolver {
	private final Authentication authentication;
	private Consumer consumer;
	private List<Authority> hasAuthorities;
	private String authority;

	public ConsumerAuthenticationResolver(Authentication authentication) {
		this.authentication = authentication;
		this.hasAuthorities = new ArrayList<>();
		this.consumer = null;
		this.authority = null;

		// 로그인하지 않아도 세션이 생성되면 ROLE_ANONYMOUS 가질수 있으면 Role로 캐스팅이 되지 않는 다.
		if (authentication != null) {
			if (authentication instanceof AnonymousAuthenticationToken) {
				this.hasAuthorities.add(new Authority("ROLE_ANONYMOUS", null));
			} else {
				this.consumer = (Consumer) authentication.getPrincipal();
				if (consumer != null) {
					authority = consumer.getAuthority();
				}
				this.hasAuthorities = (List<Authority>) authentication.getAuthorities();
			}
		}
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public String getAuthority() {
		return authority;
	}

	public List<String> getHasAuthorities() {
		List<String> authorities = new ArrayList<>();

		if (authority != null) {
			authorities.add(authority);
		}

		for (Authority authority : hasAuthorities) {
			authorities.add(authority.getAuthority());
		}

		return authorities;
	}

	public List<Permission> getHasPermissions(String roleName) {
		for (Authority authority : hasAuthorities) {
			if (authority.getAuthority().equals(roleName)) return authority.getPrivilege();
		}

		return Collections.emptyList();
	}
}
