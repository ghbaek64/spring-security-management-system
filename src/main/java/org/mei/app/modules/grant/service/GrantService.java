package org.mei.app.modules.grant.service;

import org.mei.app.modules.grant.domain.GrantAccessPermission;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 10.
 */
public interface GrantService {

	List<GrantAccessPermission> getGrantAccessPermissionList();
}
