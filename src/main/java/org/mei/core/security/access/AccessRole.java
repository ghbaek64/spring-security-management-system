package org.mei.core.security.access;

import org.springframework.security.access.ConfigAttribute;

import java.util.List;

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
	private final String pattern;
	/**
	 * Request Mehtod
	 */
	private List<MehtodAttribute> method;
	/**
	 * path에 대한 접근 허용여부.
	 */
	private boolean allow;
	/**
	 * 대상 룰
	 */
	private final List<ConfigAttribute> roleName;

	public AccessRole(String pattern) {
		this(pattern, null, true, null);
	}

	public AccessRole(String pattern, List<ConfigAttribute> roleName) {
		this(pattern, null, true, roleName);
	}

	public AccessRole(String pattern, List<MehtodAttribute> method, List<ConfigAttribute> roleName) {
		this(pattern, method, true, roleName);
	}

	public AccessRole(String pattern, List<MehtodAttribute> method, boolean allow, List<ConfigAttribute> roleName) {
		this.pattern = pattern;
		this.method = method;
		this.allow = allow;
		this.roleName = roleName;
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

	public boolean isAllow() {
		return allow;
	}

	public void setAllow(boolean allow) {
		this.allow = allow;
	}

	public List<ConfigAttribute> getRoleName() {
		return roleName;
	}

	@Override
	public String toString() {
		return "AccessRole{" +
				"pattern='" + pattern + '\'' +
				", method=" + method +
				", allow=" + allow +
				", roleName=" + roleName +
				'}';
	}
}
