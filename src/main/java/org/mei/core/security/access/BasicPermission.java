package org.mei.core.security.access;

import org.mei.core.security.enums.Permission;

import java.util.List;

/**
 * 룰은 기본 퍼미션을 가질 수 있다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 22.
 */
public class BasicPermission {
	private final String roleName;
	private final List<Permission> permission;

	public BasicPermission(String roleName, List<Permission> permission) {
		this.roleName = roleName;
		this.permission = permission;
	}

	public String getRoleName() {
		return roleName;
	}

	public List<Permission> getPermission() {
		return permission;
	}

	@Override
	public String toString() {
		return super.toString() + '{' +
				"roleName='" + roleName + '\'' +
				", permission=" + permission +
				'}';
	}
}
