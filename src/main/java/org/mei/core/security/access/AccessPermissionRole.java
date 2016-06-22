package org.mei.core.security.access;

import org.mei.core.security.enums.Method;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 21.
 */
public class AccessPermissionRole {
	private final String roleName;
	private final List<String> pattern;
	private List<Method> method;
	private List<AccessPermission> accessPermission;

	public AccessPermissionRole(String roleName, List<String> pattern, List<Method> method, List<AccessPermission> accessPermission) {
		this.roleName = roleName;
		this.pattern = pattern;
		this.method = method;
		this.accessPermission = accessPermission;
	}

	public String getRoleName() {
		return roleName;
	}

	public List<String> getPattern() {
		return pattern;
	}

	public List<Method> getMethod() {
		return method;
	}

	public List<AccessPermission> getAccessPermission() {
		return accessPermission;
	}

	@Override
	public String toString() {
		return "AccessPermissionRole{" +
				"roleName='" + roleName + '\'' +
				", pattern=" + pattern +
				", method=" + method +
				", accessPermission=" + accessPermission +
				'}';
	}
}
