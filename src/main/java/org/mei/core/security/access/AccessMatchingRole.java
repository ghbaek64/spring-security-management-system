package org.mei.core.security.access;

import org.mei.core.security.enums.Method;
import org.mei.core.security.enums.Permission;
import org.mei.core.security.service.AccessControlService;
import org.mei.core.util.AntPathMatchers;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Request에 대한 일치하는 권한(Role, Permission)을 조회한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 13.
 */
public class AccessMatchingRole {
	private AntPathMatchers antPathMatchers = new AntPathMatchers();

	private final AccessControlService accessControlService;
	public AccessMatchingRole(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	public AccessControlService getAccessControlService() {
		return accessControlService;
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
		Collection<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();

		AccessRole accessRole = this.needRole(url, method);
		AccessPermissionRole accessPermissionRole = this.needPermissionRole(url, method);

		List<String> roleNames = new ArrayList<>();
		if (accessRole != null) roleNames.addAll(accessRole.getRoleName());
		if (accessPermissionRole != null) roleNames.add(accessPermissionRole.getRoleName());

		for(String roleName : roleNames) {
			attributes.add(new SecurityConfig(roleName));
		}

		return attributes;
	}

	public AccessRole needRole(String url, Method method) {
		List<AccessRole> accessRoleList = accessControlService.getAccessRoleList();

		if (accessRoleList == null) return null;

		for(AccessRole accessRole : accessRoleList) {
			List<Method> methods = accessRole.getMethod();

			if (methods != null) {
				if (methods.indexOf(method) == -1) continue;
			}

			List<String> patterns = accessRole.getPattern();
			if (antPathMatchers.matches(patterns, url)) {
				return accessRole;
			}
		}

		return null;
	}

	public AccessPermissionRole needPermissionRole(String url, Method method) {
		List<AccessPermissionRole> accessPermissionRoleList = accessControlService.getAccessPermissionRoleList();

		if (accessPermissionRoleList == null) return null;

		for(AccessPermissionRole accessPermissionRole : accessPermissionRoleList) {
			List<Method> methods = accessPermissionRole.getMethod();

			if (methods != null) {
				if (methods.indexOf(method) == -1) continue;
			}

			List<String> patterns = accessPermissionRole.getPattern();
			if (antPathMatchers.matches(patterns, url)) {
				return accessPermissionRole;
			}
		}

		return null;
	}

	public Permission needPermission(String url, Method method) {
		return needPermission(url, method, null);
	}

	public Permission needPermission(String url, Method method, AccessPermissionRole accessPermissionRole) {
		if (accessPermissionRole == null) accessPermissionRole = needPermissionRole(url, method);

		if (accessPermissionRole == null) return null;

		List<AccessPermission> accessPermissionList = accessPermissionRole.getAccessPermission();
		if (accessPermissionList == null) return null;

		for(AccessPermission accessPermission : accessPermissionList) {
			List<Method> methods = accessPermission.getMethod();

			if (methods != null) {
				if (methods.indexOf(method) == -1) continue;
			}

			List<String> patterns = accessPermission.getPattern();
			if (antPathMatchers.matches(patterns, url)) {
				return accessPermission.getPermission();
			}
		}

		return null;
	}
}
