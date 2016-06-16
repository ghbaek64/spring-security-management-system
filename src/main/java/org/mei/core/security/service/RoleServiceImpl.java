package org.mei.core.security.service;

import org.mei.core.security.access.AccessPermission;
import org.mei.core.security.access.AccessRole;
import org.mei.core.security.access.InMemoryAccessRole;
import org.mei.core.security.enums.Permission;
import org.springframework.security.access.SecurityConfig;

import java.util.List;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 13.
 */
public class RoleServiceImpl implements RoleService {
	List<AccessRole> accessRoleList;
	Map<String, List<AccessPermission>> accessPermissionMap;

	public RoleServiceImpl() {
		InMemoryAccessRole inMemoryAccessRole = new InMemoryAccessRole();
		//inMemoryAccessRole.add(new AccessRole("/board/delete", null, false, SecurityConfig.createList("ROLE_ADMIN")));
		inMemoryAccessRole.add(new AccessRole("/board/**", SecurityConfig.createList("ROLE_PERM_0001")));
		inMemoryAccessRole.add(new AccessRole("/member/login", null));
		inMemoryAccessRole.add(new AccessRole("/member/mypage", SecurityConfig.createList("ROLE_USER", "ROLE_ADMIN")));
		inMemoryAccessRole.add(new AccessRole("/**", SecurityConfig.createList("ROLE_USER", "ROLE_ADMIN")));

		inMemoryAccessRole.add("ROLE_PERM_0001", new AccessPermission("ROLE_PERM_0001", "/board", Permission.LIST));
		inMemoryAccessRole.add("ROLE_PERM_0001", new AccessPermission("ROLE_PERM_0001", "/board/view", Permission.VIEW));
		inMemoryAccessRole.add("ROLE_PERM_0001", new AccessPermission("ROLE_PERM_0001", "/board/write", Permission.WRITE));
		inMemoryAccessRole.add("ROLE_PERM_0001", new AccessPermission("ROLE_PERM_0001", "/board/delete", Permission.MANAGER));

		accessRoleList = inMemoryAccessRole.getAccessRole();
		accessPermissionMap = inMemoryAccessRole.getAccessPermission();
	}

	@Override
	public List<AccessRole> getRoleList() {
		return accessRoleList;
	}

	@Override
	public List<AccessPermission> getPermissionList(String roleName) {
		return accessPermissionMap.get(roleName);
	}
}
