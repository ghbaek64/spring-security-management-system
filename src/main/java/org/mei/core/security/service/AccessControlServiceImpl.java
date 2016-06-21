package org.mei.core.security.service;

import org.mei.core.security.access.*;
import org.mei.core.security.enums.Permission;
import org.springframework.security.access.SecurityConfig;

import java.util.List;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 13.
 */
public class AccessControlServiceImpl implements AccessControlService {
	Map<String, AccessBasicPermission> accessBasicPermission;
	List<AccessRole> accessRole;
	Map<String, AccessPermissionRole> accessPermissionRole;

	public AccessControlServiceImpl() {
		InMemoryAccessRole inMemoryAccessRole = new InMemoryAccessRole();

		// 기본 권한 설정
		inMemoryAccessRole.add(new AccessBasicPermission("ROLE_ADMIN", AccessBasicPermission.createList(Permission.ADMIN)));

		// 룰 권한 설정
		//inMemoryAccessRole.add(new AccessRole("/board/delete", null, false, SecurityConfig.createList("ROLE_ADMIN")));
		inMemoryAccessRole.add(new AccessRole("/board/**", null, true, SecurityConfig.createList("ROLE_PERM_0001"), true));
		inMemoryAccessRole.add(new AccessRole("/member/login", null));
		inMemoryAccessRole.add(new AccessRole("/member/mypage", SecurityConfig.createList("ROLE_USER", "ROLE_ADMIN")));
		inMemoryAccessRole.add(new AccessRole("/**", SecurityConfig.createList("ROLE_USER", "ROLE_ADMIN")));

		// 퍼미션 권한 설정
		inMemoryAccessRole.add(new AccessPermissionRole("ROLE_PERM_0001", AccessPermission.createList(
				new AccessPermission("/board", Permission.LIST),
				new AccessPermission("/board/view", Permission.VIEW),
				new AccessPermission("/board/write", Permission.WRITE),
				new AccessPermission("/board/delete", Permission.MANAGER)) ));

		accessBasicPermission = inMemoryAccessRole.getAccessBasicPermission();
		accessRole = inMemoryAccessRole.getAccessRole();
		accessPermissionRole = inMemoryAccessRole.getAccessPermissionRole();
	}

	@Override
	public AccessBasicPermission getAccessBasicPermission(String roleName) {
		return accessBasicPermission.get(roleName);
	}

	@Override
	public List<AccessRole> getAccessRole() {
		return accessRole;
	}

	@Override
	public AccessPermissionRole getAccessPermissionRole(String roleName) {
		return accessPermissionRole.get(roleName);
	}
}
