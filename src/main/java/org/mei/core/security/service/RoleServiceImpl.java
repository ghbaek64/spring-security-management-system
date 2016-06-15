package org.mei.core.security.service;

import org.mei.core.security.access.AccessPermission;
import org.mei.core.security.access.AccessRole;
import org.mei.core.security.enums.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 13.
 */
public class RoleServiceImpl implements RoleService {
	List<AccessRole> accessRoleList;
	List<AccessPermission> accessPermissionList;

	public RoleServiceImpl() {
		accessRoleList = new ArrayList<>();
		accessRoleList.add(new AccessRole("/member/login", null));
		accessRoleList.add(new AccessRole("/member/mypage",new String[]{"ROLE_USER", "ROLE_ADMIN" }));
		accessRoleList.add(new AccessRole("/**",new String[]{"ROLE_USER", "ROLE_ADMIN" }));


		accessPermissionList = new ArrayList<>();
		accessPermissionList.add(new AccessPermission("ROLE_USER", "/board", Permission.LIST));
		accessPermissionList.add(new AccessPermission("ROLE_USER", "/board/view", Permission.VIEW));
		accessPermissionList.add(new AccessPermission("ROLE_ADMIN", "/board/write", Permission.WRITE));
		accessPermissionList.add(new AccessPermission("ROLE_ADMIN", "/board/delete", Permission.MANAGER));
	}

	@Override
	public List<AccessRole> getRoleList() {
		return accessRoleList;
	}
}
