package org.mei.core.security.authorization;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = -7125481982234132933L;

	private final String roleName;
	private final Collection<Privilege> privilege;

	public Role(String roleName, Collection<Privilege> privilege) {
		this.roleName = roleName;
		this.privilege = privilege;
	}

	@Override
	public String getAuthority() {
		return roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public Collection<Privilege> getPrivilege() {
		return privilege;
	}

	@Override
	public String toString() {
		return "Role{" +
				"roleName='" + roleName + '\'' +
				", privilege=" + privilege +
				'}';
	}
}
