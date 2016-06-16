package org.mei.core.security.authentication;

import org.mei.core.security.authorization.Role;
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

		if (authentication != null) {

			List<Role> authorities = (List<Role>) authentication.getAuthorities();

			for (Role role : authorities) {
				this.hasRoles.add(role.getRoleName());
			}
		}
	}

	public List<String> getHasRoles() {
		return hasRoles;
	}
}
