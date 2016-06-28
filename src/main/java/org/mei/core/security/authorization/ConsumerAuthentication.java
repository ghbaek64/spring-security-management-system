package org.mei.core.security.authorization;

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
public class ConsumerAuthentication {
	private Consumer consumer;
	private List<Authority> authorities;
	private String groupRole;

	public ConsumerAuthentication(Authentication authentication) {
		this.authorities = new ArrayList<>();
		this.consumer = null;
		this.groupRole = null;

		// 로그인하지 않아도 세션이 생성되면 ROLE_ANONYMOUS 가질수 있으면 Role로 캐스팅이 되지 않는 다.
		if (authentication != null) {
			if (authentication instanceof AnonymousAuthenticationToken) {
				this.authorities.add(new Authority("ROLE_ANONYMOUS", null));
			} else {
				this.consumer = (Consumer) authentication.getPrincipal();
				if (consumer != null) {
					groupRole = consumer.getGroupRole();
				}
				this.authorities = (List<Authority>) authentication.getAuthorities();
			}
		}
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public String getGroupRole() {
		return groupRole;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public List<String> getAuthoritiesToString() {
		List<String> authoritieList = new ArrayList<>();

		if (groupRole != null) {
			authoritieList.add(groupRole);
		}

		for (Authority authority : authorities) {
			authoritieList.add(authority.getAuthority());
		}

		return authoritieList;
	}

	public List<Permission> getPermissions(String roleName) {
		for (Authority authority : authorities) {
			if (authority.getAuthority().equals(roleName)) return authority.getPrivilege();
		}

		return Collections.emptyList();
	}
}
