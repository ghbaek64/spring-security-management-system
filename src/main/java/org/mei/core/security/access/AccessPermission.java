package org.mei.core.security.access;

import org.mei.core.security.enums.Permission;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class AccessPermission {
	/**
	 * The Role name.
	 */
	private final String roleName;
	private final String pattern;
	private List<MehtodAttribute> method;
	private Permission permission;

	public AccessPermission(String roleName, String pattern) {
		this(roleName, pattern, null, null);
	}

	public AccessPermission(String roleName, String pattern, Permission permission) {
		this(roleName, pattern, null, permission);
	}

	public AccessPermission(String roleName, String pattern, List<MehtodAttribute> method, Permission permission) {
		this.roleName = roleName;
		this.pattern = pattern;
		this.method = method;
		this.permission = permission;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getPattern() {
		return pattern;
	}

	public List<MehtodAttribute> getMethod() {
		return method;
	}

	public void setMethod(List<MehtodAttribute> method) {
		this.method = method;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "AccessPermission{" +
				"roleName='" + roleName + '\'' +
				", pattern='" + pattern + '\'' +
				", method=" + method +
				", permission=" + permission +
				'}';
	}
}
