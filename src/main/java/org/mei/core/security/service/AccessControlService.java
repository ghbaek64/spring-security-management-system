package org.mei.core.security.service;

import org.mei.core.security.access.*;

import java.util.List;
import java.util.Map;

/**
 * 권한관리에 필요한 데이터를 조회한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http ://syaku.tistory.com
 * @since 2016.06.13
 */
public interface AccessControlService {
	List<AccessRole> getAccessRoleList();
	List<AccessPermissionRole> getAccessPermissionRoleList();
	Map<String, BasicPermission> getBasicPermissionObject();
	BasicPermission getBasicPermissionObject(String roleName);
}
