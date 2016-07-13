package org.mei.core.security.access;

import org.mei.core.security.enums.Method;

import java.util.List;

/**
 * 접근 퍼미션을 설정하여 룰을 생성한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 21.
 */
public class AccessPermit {
	private final String roleName;
	private final List<String> pattern;
	private List<Method> method;
	private List<RulePermit> accessPermission;

	public AccessPermit(String roleName, List<String> pattern, List<Method> method, List<RulePermit> accessPermission) {
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

	public List<RulePermit> getAccessPermission() {
		return accessPermission;
	}

	@Override
	public String toString() {
		return super.toString() + '{' +
				"roleName='" + roleName + '\'' +
				", pattern=" + pattern +
				", method=" + method +
				", accessPermission=" + accessPermission +
				'}';
	}
}
