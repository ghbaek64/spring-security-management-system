package org.mei.core.security.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 권한 정보 저장소.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class InMemoryAccessRole {
	private static final Map<String, AccessBasicPermission> accessBasicPermission;
	private static final List<AccessRole> accessRole;
	private static final Map<String, AccessPermissionRole> accessPermissionRole;

	static {
		accessBasicPermission = new HashMap<>();
		accessRole = new ArrayList<>();
		accessPermissionRole = new HashMap<>();
	}

	public void add(AccessBasicPermission permission) {
		accessBasicPermission.put(permission.getRoleName(), permission);
	}

	public void add(AccessRole role) {
		accessRole.add(role);
	}

	public void add(AccessPermissionRole role) {
		accessPermissionRole.put(role.getRoleName(), role);
	}

	public static Map<String, AccessBasicPermission> getAccessBasicPermission() {
		return accessBasicPermission;
	}

	public static List<AccessRole> getAccessRole() {
		return accessRole;
	}

	public static Map<String, AccessPermissionRole> getAccessPermissionRole() {
		return accessPermissionRole;
	}
}
