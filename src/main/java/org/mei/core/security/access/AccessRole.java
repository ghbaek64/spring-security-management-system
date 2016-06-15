package org.mei.core.security.access;

import org.mei.core.security.enums.Method;

import java.util.Arrays;

/**
 * url (AntiStyle pattern)에 대해 룰을 설정한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 13.
 */
public class AccessRole {
	/**
	 * url or AntiStyle patterns
	 */
	private String pattern;
	/**
	 * Request Mehtod
	 */
	private Method[] method;
	/**
	 * path에 대한 접근 허용여부.
	 */
	private boolean allow = true;
	/**
	 * 대상 룰
	 */
	private String[] roleName;

	public AccessRole() {
	}

	public AccessRole(String pattern, String[] roleName) {
		this.pattern = pattern;
		this.roleName = roleName;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Method[] getMethod() {
		return method;
	}

	public void setMethod(Method[] method) {
		this.method = method;
	}

	public boolean isAllow() {
		return allow;
	}

	public void setAllow(boolean allow) {
		this.allow = allow;
	}

	public String[] getRoleName() {
		return roleName;
	}

	public void setRoleName(String[] roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role{" +
				"pattern='" + pattern + '\'' +
				", method=" + Arrays.toString(method) +
				", allow=" + allow +
				", roleName=" + Arrays.toString(roleName) +
				'}';
	}
}
