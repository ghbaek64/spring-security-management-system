package org.mei.core.security.access;

import org.mei.core.security.service.AccessControlService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 22.
 */
public class InMemoryAuthorization implements AccessControlService {
	private static final Map<String, BasicPermission> basicPermission;
	private static final List<AccessRole> accessRole;
	private static final List<AccessPermissionRole> accessPermissionRole;

	static {
		basicPermission = new HashMap<>();
		accessRole = new ArrayList<>();
		accessPermissionRole = new ArrayList<>();
	}

	public InMemoryAuthorization add(BasicPermission permission) {
		basicPermission.put(permission.getRoleName(), permission);
		return this;
	}

	public InMemoryAuthorization add(AccessRole role) {
		accessRole.add(role);
		return this;
	}

	public InMemoryAuthorization add(AccessPermissionRole role) {
		accessPermissionRole.add(role);
		return this;
	}

	@Override
	public List<AccessRole> getAccessRoleList() {
		return accessRole;
	}

	@Override
	public List<AccessPermissionRole> getAccessPermissionRoleList() {
		return accessPermissionRole;
	}

	@Override
	public Map<String, BasicPermission> getBasicPermissionObject() {
		return basicPermission;
	}

	@Override
	public BasicPermission getBasicPermissionObject(String roleName) {
		if (basicPermission == null) return null;
		return basicPermission.get(roleName);
	}
}
