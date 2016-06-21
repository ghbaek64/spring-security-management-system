package org.mei.core.security.service;

import org.mei.core.security.access.AccessPermission;
import org.mei.core.security.access.AccessPermissionRole;
import org.mei.core.security.access.AccessRole;
import org.mei.core.security.access.AccessBasicPermission;

import java.util.List;
import java.util.Map;

/**
 * 권한관리에 필요한 데이터를 조회한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http ://syaku.tistory.com
 * @since 2016.06.13
 */
public interface AccessControlService {

	/**
	 * 룰에 대한 기본 퍼미션을 리턴한다.
	 *
	 * @return
	 */
	AccessBasicPermission getAccessBasicPermission(String roleName);

	/**
	 * 모든 룰을 리턴한다
	 * @return
	 */
	List<AccessRole> getAccessRole();

	AccessPermissionRole getAccessPermissionRole(String roleName);
}
