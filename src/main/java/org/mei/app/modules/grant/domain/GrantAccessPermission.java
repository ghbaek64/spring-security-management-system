package org.mei.app.modules.grant.domain;

import org.mei.core.security.enums.Method;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 10.
 */
public class GrantAccessPermission {
	private String roleName;
	private String path;
	private String method;
	private String permission;
	private int order;

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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "GrantAccessPermission{" +
				"roleName='" + roleName + '\'' +
				", path='" + path + '\'' +
				", method='" + method + '\'' +
				", permission='" + permission + '\'' +
				", order=" + order +
				'}';
	}
}
