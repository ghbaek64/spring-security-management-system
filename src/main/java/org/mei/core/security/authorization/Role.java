package org.mei.core.security.authorization;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = -7125481982234132933L;

	private final String role;
	private final List<Privilege> privilege;

	public Role(String role, List<Privilege> privilege) {
		Assert.hasText(role, "A granted authority textual representation is required");
		this.role = role;
		this.privilege = privilege;
	}

	@Override
	public String getAuthority() {
		return role;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Role) {
			return role.equals(((Role) obj).role);
		}

		return false;
	}

	public int hashCode() {
		return this.role.hashCode();
	}

	public List<Privilege> getPrivilege() {
		return privilege;
	}

	@Override
	public String toString() {
		return "Role{" +
				"role='" + role + '\'' +
				", privilege=" + privilege +
				'}';
	}
}
