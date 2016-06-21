package org.mei.core.security.access;

import org.mei.core.security.enums.Permission;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 룰에 대한 기본 퍼미션 권한 정보
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 17.
 */
public class AccessBasicPermission {
	private final String roleName;
	private final List<Permission> permission;

	public AccessBasicPermission(String roleName, List<Permission> permission) {
		Assert.notNull(roleName, "null을 입력할 수 없습니다.");
		this.roleName = roleName;
		this.permission = permission;
	}

	public String getRoleName() {
		return roleName;
	}

	public List<Permission> getPermission() {
		return permission;
	}

	public static List<Permission> createList(Permission... permissions) {
		Assert.notNull(permissions, "null을 입력할 수 없습니다.");
		List<Permission> permissionList = new ArrayList<>(permissions.length);

		for (Permission permission : permissions) {
			permissionList.add(permission);
		}

		return permissionList;
	}
}
