package org.mei.core.security.access;

import org.mei.core.security.authorization.ConsumerPermit;

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
	List<AccessRule> getAccessRules();

	/**
	 * 접근 퍼미션을 모두 조회한다.
	 *
	 * @return
	 */
	List<AccessPermit> getAccessPermits();

	/**
	 * 기본 퍼미션을 모두 조회한다.
	 *
	 * @return
	 */
	Map<String, BasicPermit> getBasicPermit();

	/**
	 * 룰의 기본 퍼미션을 조회한다.
	 *
	 * @param roleName role name
	 * @return
	 */
	BasicPermit getBasicPermit(String roleName);

	/**
	 * 모든 사용자에 부여된 권한을 조회한다.
	 *
	 * @return
	 */
	Map<String, List<ConsumerPermit>> getConsumerPermit();

	/**
	 * 사용자에 부여된 권한을 조회한다.
	 *
	 * @param username 사용자 아이디
	 * @return
	 */
	List<ConsumerPermit> getConsumerPermits(String username);
}
