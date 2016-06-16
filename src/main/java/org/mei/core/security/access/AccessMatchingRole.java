package org.mei.core.security.access;

import org.mei.core.security.enums.Method;
import org.mei.core.security.enums.Permission;
import org.mei.core.security.service.RoleService;
import org.mei.core.util.AntPathMatchers;
import org.springframework.security.access.ConfigAttribute;

import java.util.ArrayList;
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
	public Collection<ConfigAttribute> needConfigAttributes(String url, Method method) {
		List<AccessRole> roleList = roleService.getRoleList();

		if (roleList == null) return null;

		for(AccessRole role : roleList) {
			List<MehtodAttribute> methods = role.getMethod();

			if (methods != null) {
				if (methods.indexOf(method) == -1) continue;
			}

			String pattern = role.getPattern();
			if (antPathMatchers.matches(pattern, url)) {
				Collection<ConfigAttribute> roleName = role.getRoleName();
				return (roleName == null) ? null : roleName;
			}
		}

		return null;
	}

	public AccessRole needRole(String url, Method method) {
		List<AccessRole> roleList = roleService.getRoleList();

		if (roleList == null) return null;

		for(AccessRole role : roleList) {
			List<MehtodAttribute> methods = role.getMethod();

			if (methods != null) {
				if (methods.indexOf(method) == -1) continue;
			}

			String pattern = role.getPattern();
			if (antPathMatchers.matches(pattern, url)) {
				return role;
			}
		}

		return null;
	}

	/**
	 * role의 퍼미션을 조회하고 request 정보와 일치하는 퍼미션을 순차적으로 한개만 찾아 리턴하고 종료한다.
	 *
	 * @param roleName Collection<ConfigAttribute> configAttributes
	 * @param url      AntiStyle pattern
	 * @param method   Method
	 * @return Permission
	 */
	public Permission needPermission(String roleName, String url, Method method) {
		List<AccessPermission> accessPermissionList = roleService.getPermissionList(roleName);

		if (accessPermissionList == null) return null;

		for(AccessPermission accessPermission : accessPermissionList) {
			List<MehtodAttribute> methods = accessPermission.getMethod();

			if (methods != null) {
				if (methods.indexOf(method) == -1) continue;
			}

			String pattern = accessPermission.getPattern();
			if (antPathMatchers.matches(pattern, url)) {
				return accessPermission.getPermission();
			}
		}

		return null;
	}

	/**
	 * 다수의 role의 퍼미션을 모두 조회한다.
	 *
	 * @param configAttributes roleName
	 * @param url              AntiStyle pattern
	 * @param method           Method
	 * @return List<Permission>
	 */
	public List<Permission> needPermissions(Collection<ConfigAttribute> configAttributes, String url, Method method) {

		if (configAttributes == null) return null;

		List<Permission> permissionList = new ArrayList<>();

		for(ConfigAttribute configAttribute : configAttributes) {
			Permission permission = needPermission(configAttribute.getAttribute(), url, method);
			if (permission != null) permissionList.add(permission);
		}
		if (permissionList.size() == 0) return null;
		return permissionList;
	}

}
