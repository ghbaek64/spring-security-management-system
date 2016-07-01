package org.mei.core.security.authorization;

import org.mei.core.security.enums.Permission;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 1.
 */
public class ConsumerPermission {
	private String username;
	private String roleName;
	private List<Permission> permissions;

	public ConsumerPermission(String username, String roleName, List<Permission> permissions) {
		this.username = username;
		this.roleName = roleName;
		this.permissions = permissions;
	}

	public String getUsername() {
		return username;
	}

	public String getRoleName() {
		return roleName;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	@Override
	public String toString() {
		return super.toString() + '{' +
				"username='" + username + '\'' +
				", roleName='" + roleName + '\'' +
				", permissions=" + permissions +
				'}';
	}
}
