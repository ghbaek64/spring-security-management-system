package org.mei.core.security.access;

import org.apache.commons.lang3.ArrayUtils;
import org.mei.core.security.enums.Method;
import org.mei.core.security.service.RoleService;
import org.mei.core.util.AntPathMatchers;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import java.util.Collection;
import java.util.List;

/**
 * url에 관련된 룰을 조회한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 13.
 */
public class AccessMatchingRole {
	private AntPathMatchers antPathMatchers = new AntPathMatchers();

	private final RoleService roleService;
	public AccessMatchingRole(RoleService roleService) {
		this.roleService = roleService;

	}

	public RoleService getRoleService() {
		return roleService;
	}

	/**
	 * url과 method에 접속하기 위해 필요한 룰을 조회한다.
	 * 일치하는 룰을 찾으면 리턴하고 작업을 종료한다.
	 *
	 * @param url    request url
	 * @param method request method
	 * @return
	 */
	public Collection<ConfigAttribute> needRole(String url, Method method) {
		List<AccessRole> roleList = roleService.getRoleList();

		if (roleList == null) return null;

		for(AccessRole role : roleList) {
			String pattern = role.getPattern();
			Method[] methods = role.getMethod();
			String[] roleName = role.getRoleName();

			if (methods != null) {
				if (ArrayUtils.indexOf(methods, method) > -1) continue;
			}

			if (antPathMatchers.matches(pattern, url)) return SecurityConfig.createList(roleName);
		}

		return null;
	}

}
