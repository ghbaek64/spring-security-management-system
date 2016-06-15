package org.mei.core.security.service;

import org.mei.core.security.access.AccessPermission;
import org.mei.core.security.access.AccessRole;

import java.util.List;

/**
 * 권한관리에 필요한 데이터를 조회한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http ://syaku.tistory.com
 * @since 2016.06.13
 */
public interface RoleService {

	/**
	 * 모든 룰을 리턴한다
	 * @return
	 */
	List<AccessRole> getRoleList();

	List<AccessPermission> getPermissionList(String roleName);
}
