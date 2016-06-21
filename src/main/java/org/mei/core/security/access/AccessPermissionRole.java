package org.mei.core.security.access;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 21.
 */
public class AccessPermissionRole {
	private final String roleName;
	private final List<AccessPermission> accessPermission;

	public AccessPermissionRole(String roleName, List<AccessPermission> accessPermission) {
		this.roleName = roleName;
		this.accessPermission = accessPermission;
	}

	public String getRoleName() {
		return roleName;
	}

	public List<AccessPermission> getAccessPermission() {
		return accessPermission;
	}
}
