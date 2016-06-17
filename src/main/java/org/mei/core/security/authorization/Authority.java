package org.mei.core.security.authorization;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class Authority implements GrantedAuthority {
	private static final long serialVersionUID = -7125481982234132933L;

	private final String role;
	private final List<Privilege> privilege;

	public Authority(String role, List<Privilege> privilege) {
		this.role = role;
		this.privilege = privilege;
	}

	@Override
	public String getAuthority() {
		return role;
	}

	public List<Privilege> getPrivilege() {
		return privilege;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Authority) {
			return role.equals(((Authority) obj).role);
		}

		return false;
	}

	public int hashCode() {
		return this.role.hashCode();
	}

	@Override
	public String toString() {
		return "Role{" +
				"role='" + role + '\'' +
				", privilege=" + privilege +
				'}';
	}
}
