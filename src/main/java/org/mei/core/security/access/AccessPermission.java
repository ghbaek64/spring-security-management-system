package org.mei.core.security.access;

import org.mei.core.security.enums.Permission;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 룰과 url에 대한 퍼미션 정보
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class AccessPermission {
	private final String pattern;
	private List<MehtodAttribute> method;
	private Permission permission;

	public AccessPermission(String pattern) {
		this(pattern, null, null);
	}

	public AccessPermission(String pattern, Permission permission) {
		this(pattern, null, permission);
	}

	public AccessPermission(String pattern, List<MehtodAttribute> method, Permission permission) {
		this.pattern = pattern;
		this.method = method;
		this.permission = permission;
	}

	public String getPattern() {
		return pattern;
	}

	public List<MehtodAttribute> getMethod() {
		return method;
	}

	public Permission getPermission() {
		return permission;
	}

	public static List<AccessPermission> createList(AccessPermission... accessPermissions) {
		Assert.notNull(accessPermissions, "null을 입력할 수 없습니다.");
		List<AccessPermission> accessPermissionsList = new ArrayList<>(accessPermissions.length);

		for (AccessPermission accessPermission : accessPermissions) {
			accessPermissionsList.add(accessPermission);
		}

		return accessPermissionsList;
	}

	@Override
	public String toString() {
		return "AccessPermission{" +
				" pattern='" + pattern + '\'' +
				", method=" + method +
				", permission=" + permission +
				'}';
	}
}
