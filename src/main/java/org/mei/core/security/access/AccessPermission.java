package org.mei.core.security.access;

import org.mei.core.security.enums.Method;
import org.mei.core.security.enums.Permission;

import java.util.List;

/**
 * AccessPermissionRole에 대한 퍼미션 정보
 *
 * @see AccessPermissionRole
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class AccessPermission {
	private final List<String> pattern;
	private List<Method> method;
	private final Permission permission;

	public AccessPermission(List<String> pattern, List<Method> method, Permission permission) {
		this.pattern = pattern;
		this.method = method;
		this.permission = permission;
	}

	public List<String> getPattern() {
		return pattern;
	}

	public List<Method> getMethod() {
		return method;
	}

	public Permission getPermission() {
		return permission;
	}

	@Override
	public String toString() {
		return super.toString() + '{' +
				"pattern=" + pattern +
				", method=" + method +
				", permission=" + permission +
				'}';
	}
}
