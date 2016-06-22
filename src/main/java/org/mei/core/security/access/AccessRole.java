package org.mei.core.security.access;

import org.mei.core.security.enums.Method;
import org.springframework.security.access.ConfigAttribute;

import java.util.List;

/**
 * url (Ant-Style pattern)에 대해 룰 정보
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 13.
 */
public class AccessRole {

	private final List<String> pattern;
	private List<Method> method;
	private List<String> roleName;
	private boolean allow;

	public AccessRole(List<String> pattern, List<Method> method, boolean allow, List<String> roleName) {
		this.pattern = pattern;
		this.method = method;
		this.allow = allow;
		this.roleName = roleName;
	}

	public List<String> getPattern() {
		return pattern;
	}

	public List<Method> getMethod() {
		return method;
	}

	public List<String> getRoleName() {
		return roleName;
	}

	public boolean isAllow() {
		return allow;
	}

	@Override
	public String toString() {
		return "AccessRole{" +
				"pattern=" + pattern +
				", method=" + method +
				", roleName=" + roleName +
				", allow=" + allow +
				'}';
	}
}
