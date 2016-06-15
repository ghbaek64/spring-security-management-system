package org.mei.core.security.service;

import org.mei.core.security.access.AccessRole;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 13.
 */
public class RoleServiceImpl implements RoleService {
	List<AccessRole> accessRoleList;

	public RoleServiceImpl() {
		accessRoleList = new ArrayList<>();
		accessRoleList.add(new AccessRole("/**",new String[]{"ROLE_USER", "ROLE_ADMIN" }));
		accessRoleList.add(new AccessRole("/member/mypage",new String[]{"ROLE_USER", "ROLE_ADMIN" }));
		accessRoleList.add(new AccessRole("/member/login", null));
	}

	@Override
	public List<AccessRole> getRoleList() {
		return accessRoleList;
	}
}
