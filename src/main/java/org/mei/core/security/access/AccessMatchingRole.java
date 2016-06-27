package org.mei.core.security.access;

import org.mei.core.security.authorization.Authority;
import org.mei.core.security.authorization.Consumer;
import org.mei.core.security.enums.Method;
import org.mei.core.security.enums.Permission;
import org.mei.core.security.service.AccessControlService;
import org.mei.core.util.AntPathMatchers;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import java.util.*;

/**
 * Request에 대한 일치하는 권한(Role, Permission)을 조회한다.
 *
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http ://syaku.tistory.com
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
		Collection<ConfigAttribute> attributes = new ArrayList<>();

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

	public List<Permission> basicPermission(String roleName) {

		if (roleName == null) return Collections.EMPTY_LIST;

		Map<String, BasicPermission> basicPermissionMap = accessControlService.getBasicPermissionObject();
		BasicPermission basicPermission = basicPermissionMap.get(roleName);

		if (basicPermission == null) return Collections.EMPTY_LIST;

		List<Permission> permissionList = basicPermission.getPermission();

		if (permissionList == null) return Collections.EMPTY_LIST;

		return permissionList;
	}

	/**
	 * 사용자가 가진 퍼미션을 조회한다.
	 *
	 * 1) 사용가(Consumer) 가진 룰(Authority)에 대한 기본 퍼미션(BasicPermission) 조회
	 * 2) 사용자(Consumer)가 접근한 룰(roleName)에 대한 기본 퍼미션(BasicPermission) 조회
	 * 3) 사용자(Consumer)가 접근한 룰(roleName)에 대한 사용자가 가진 퍼미션(Authority -> Privilege) 조회
	 *
	 * 1번의 퍼미션에 ADMIN or MANAGER 을 가진 경우 반환 후 종료
	 * 2번의 퍼미션에 NONE를 가진 경우 1번에서 얻은 퍼미션 모두 초기화 하고 3번으로 넘어간다.
	 * 3번의 결과를 반환한다.
	 *
	 * @param consumer the consumer
	 * @param roleName the role name
	 * @return the has permission
	 */
	public List<Permission> hasPermission(Consumer consumer, String roleName) {
		List<Permission> hasPrivilege = basicPermission(consumer.getAuthority());
		if (hasPrivilege.indexOf(Permission.ADMIN) > -1 || hasPrivilege.indexOf(Permission.MANAGER) > -1 || roleName == null) return hasPrivilege;

		List<Permission> basicPermission = basicPermission(roleName);

		if (basicPermission.indexOf(Permission.NONE) > -1) {
			hasPrivilege.clear();
		} else {
			if (basicPermission.indexOf(Permission.ADMIN) > -1 || basicPermission.indexOf(Permission.MANAGER) > -1) return basicPermission;
			for(Permission permission : basicPermission) {
				if (hasPrivilege.indexOf(permission) == -1) hasPrivilege.add(permission);
			}
		}

		List<Authority> authorities = (List<Authority>) consumer.getAuthorities();
		List<Permission> hasPermission = null;

		for(Authority hasAuthority : authorities) {
			String authority = hasAuthority.getAuthority();
			if (!authority.equals(roleName)) continue;
			hasPermission = hasAuthority.getPrivilege();
			break;
		}

		if (hasPermission == null) {
			return hasPrivilege;
		} else {
			if (hasPermission.indexOf(Permission.ADMIN) > -1 || hasPermission.indexOf(Permission.MANAGER) > -1) return hasPermission;
			for(Permission permission : hasPermission) {
				if (hasPrivilege.indexOf(permission) == -1) hasPrivilege.add(permission);
			}
		}

		return hasPrivilege;
	}
}
