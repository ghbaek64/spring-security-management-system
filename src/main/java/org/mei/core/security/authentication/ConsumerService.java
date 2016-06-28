package org.mei.core.security.authentication;

import org.mei.core.security.authorization.Consumer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
	Consumer loadUserByUsername(String username) throws UsernameNotFoundException;
}
