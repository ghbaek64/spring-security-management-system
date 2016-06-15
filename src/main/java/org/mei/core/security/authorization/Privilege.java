package org.mei.core.security.authorization;

import org.mei.core.security.enums.Permission;

import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class Privilege implements Serializable {
	private final Permission permission;

	public Privilege(Permission permission) {
		this.permission = permission;
	}

	public Permission getPermission() {
		return permission;
	}

	@Override
	public String toString() {
		return "Privilege{" +
				"permission=" + permission +
				'}';
	}
}
