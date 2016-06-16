package org.mei.core.security.service;

import org.mei.app.modules.member.domain.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 8.
 */
public interface ConsumerService {

	/**
	 * 회원정보를 리턴한다
	 *
	 * @param username 로그인 계정
	 * @return
	 * @throws
	 */
	Consumer getConsumerDetails(String username) throws Exception;

	/**
	 * 로그인 완료 후 회원정보를 업데이트한다
	 *
	 * @return
	 */
	Consumer saveMemberLoginSuccess(Authentication authentication, UserDetails userDetails);
}
