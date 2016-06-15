package org.mei.core.security.authentication;

import org.springframework.security.core.Authentication;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class AuthenticationUser {
	private final Authentication authentication;

	public AuthenticationUser(Authentication authentication) {
		this.authentication = authentication;
	}
}
