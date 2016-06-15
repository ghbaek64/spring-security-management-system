package org.mei.core.security.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class InMemoryAccessRole {
	private static final List<AccessRole> accessRole;
	private static final Map<String, List<AccessPermission>> accessPermission;

	static {
		accessRole = new ArrayList<>();
		accessPermission = new HashMap<>();
	}

	public void add(AccessRole role) {
		accessRole.add(role);
	}

	public void add(String roleName, AccessPermission permission) {
		if (accessPermission.containsKey(roleName)) {
			accessPermission.get(roleName).add(permission);
		} else {
			List<AccessPermission> accessPermissions = new ArrayList<>(1);
			accessPermissions.add(permission);
			accessPermission.put(roleName, accessPermissions);
		}

	}

	public List<AccessRole> getAccessRole() {
		return accessRole;
	}

	public Map<String, List<AccessPermission>> getAccessPermission() {
		return accessPermission;
	}
}
