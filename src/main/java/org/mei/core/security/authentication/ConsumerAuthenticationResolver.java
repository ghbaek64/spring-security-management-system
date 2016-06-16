package org.mei.core.security.authentication;

import org.mei.core.security.authorization.Role;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class ConsumerAuthenticationResolver {
	private final Authentication authentication;
	private List<String> hasRoles;

	public ConsumerAuthenticationResolver(Authentication authentication) {
		this.authentication = authentication;
		this.hasRoles = new ArrayList<String>();

		// 로그인하지 않아도 세션이 생성되면 ROLE_ANONYMOUS 가질수 있으면 Role로 캐스팅이 되지 않는 다.
		if (authentication != null) {
			if (authentication instanceof AnonymousAuthenticationToken) {
				this.hasRoles.add("ROLE_ANONYMOUS");
			} else {
				List<Role> authorities = (List<Role>) authentication.getAuthorities();
				for (Role role : authorities) {
					this.hasRoles.add(role.getAuthority());
				}
			}
		}
	}

	public List<String> getHasRoles() {
		return hasRoles;
	}
}
