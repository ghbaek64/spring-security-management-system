package org.mei.core.security.access;

import org.mei.core.security.enums.Permission;

import java.util.List;

/**
 * 접근에 대한 기본 퍼미션 설정
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 22.
 */
public class BasicPermit {
	private final String roleName;
	private final List<Permission> permission;

	public BasicPermit(String roleName, List<Permission> permission) {
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
