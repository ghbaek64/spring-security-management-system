package org.mei.core.security.access;

import java.util.List;
import java.util.Map;

/**
 * 접근제어에 필요한 권한 정보를 조회한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http ://syaku.tistory.com
 * @since 2016.06.13
 */
public interface AccessControlService {
	/**
	 * 접근 룰 전부를 조회한다.
	 *
	 * @return
	 */
	List<AccessRole> getAccessRoleList();

	/**
	 * 접근 퍼미션을 모두 조회한다.
	 *
	 * @return
	 */
	List<AccessPermissionRole> getAccessPermissionRoleList();

	/**
	 * 기본 퍼미션을 모두 조회한다.
	 *
	 * @return
	 */
	Map<String, BasicPermission> getBasicPermissionObject();

	/**
	 * 룰의 기본 퍼미션을 조회한다.
	 *
	 * @param roleName role name
	 * @return
	 */
	BasicPermission getBasicPermissionObject(String roleName);
}
