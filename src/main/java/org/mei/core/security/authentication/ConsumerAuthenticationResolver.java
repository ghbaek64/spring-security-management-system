package org.mei.core.security.authentication;

import org.mei.core.security.authorization.Authority;
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
	private List<Authority> hasAuthorities;

	public ConsumerAuthenticationResolver(Authentication authentication) {
		this.authentication = authentication;
		this.hasAuthorities = new ArrayList<>();

		// 로그인하지 않아도 세션이 생성되면 ROLE_ANONYMOUS 가질수 있으면 Role로 캐스팅이 되지 않는 다.
		if (authentication != null) {
			if (authentication instanceof AnonymousAuthenticationToken) {
				this.hasAuthorities.add(new Authority("ROLE_ANONYMOUS", null));
			} else {
				this.hasAuthorities = (List<Authority>) authentication.getAuthorities();
			}
		}
	}

	public List<String> getHasAuthorities() {
		List<String> list = new ArrayList<>();

		for (Authority authority : hasAuthorities) {
			list.add(authority.getAuthority());
		}

		return list;
	}

	public List<Permission> getHasPermissions(String roleName) {
		for (Authority authority : hasAuthorities) {
			if (authority.getAuthority().equals(roleName)) {
				List<Privilege> privileges = authority.getPrivilege();
				List<Permission> permissions = new ArrayList<>(privileges.size());
				for (Privilege privilege : privileges) {
					permissions.add(privilege.getPrivilege());
				}

				return permissions;
			}
		}

		return Collections.emptyList();
	}
}
