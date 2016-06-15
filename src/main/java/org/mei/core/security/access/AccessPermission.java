package org.mei.core.security.access;

import org.mei.core.security.enums.Method;
import org.mei.core.security.enums.Permission;

import java.util.Arrays;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class AccessPermission {
	private String roleName;
	private String path;
	private Method[] method;
	private Permission permission;

	public AccessPermission() {
	}

	public AccessPermission(String roleName, String path, Permission permission) {
		this.roleName = roleName;
		this.path = path;
		this.permission = permission;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Method[] getMethod() {
		return method;
	}

	public void setMethod(Method[] method) {
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
				", path='" + path + '\'' +
				", method=" + Arrays.toString(method) +
				", permission=" + permission +
				'}';
	}
}
