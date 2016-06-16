package org.mei.core.security.authentication;

import org.mei.core.security.authorization.Privilege;
import org.mei.core.security.authorization.Role;
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
	private List<String> hasAuthorities;
	private List<Role> hasRoles;

	public ConsumerAuthenticationResolver(Authentication authentication) {
		this.authentication = authentication;
		this.hasAuthorities = new ArrayList<>();
		this.hasRoles = new ArrayList<>();

		// 로그인하지 않아도 세션이 생성되면 ROLE_ANONYMOUS 가질수 있으면 Role로 캐스팅이 되지 않는 다.
		if (authentication != null) {
			if (authentication instanceof AnonymousAuthenticationToken) {
				this.hasAuthorities.add("ROLE_ANONYMOUS");
			} else {
				hasRoles = (List<Role>) authentication.getAuthorities();
				if (hasRoles == null) return;
				for (Role role : hasRoles) {
					this.hasAuthorities.add(role.getAuthority());
				}
			}
		}
	}

	public List<String> getHasAuthorities() {
		return hasAuthorities;
	}

	public List<Permission> getHasPermissions(String roleName) {
		for (Role role : hasRoles) {
			if (role.getAuthority().equals(roleName)) {
				List<Privilege> privileges = role.getPrivilege();
				List<Permission> permissions = new ArrayList<>(privileges.size());
				for (Privilege privilege : privileges) {
					permissions.add(privilege.getPermission());
				}

				return permissions;
			}
		}

		return Collections.emptyList();
	}
}
