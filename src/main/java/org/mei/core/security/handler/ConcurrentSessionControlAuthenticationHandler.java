package org.mei.core.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 로그인 중복이 발생한 경우 원하는 시나리오를 구성할 수 있는 헨들러
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 3.
 */
public abstract class ConcurrentSessionControlAuthenticationHandler extends ConcurrentSessionControlAuthenticationStrategy {
	protected static final Logger logger = LoggerFactory.getLogger(ConcurrentSessionControlAuthenticationHandler.class);

	private SessionRegistry sessionRegistry;

	public ConcurrentSessionControlAuthenticationHandler(SessionRegistry sessionRegistry) {
		super(sessionRegistry);
		super.setMaximumSessions(1);
		super.setExceptionIfMaximumExceeded(true);
		this.sessionRegistry = sessionRegistry;
	}

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {

		try {
			super.onAuthentication(authentication, request, response);
		} catch (SessionAuthenticationException e) {
			onConcurrentSessionControlAuthentication(sessionRegistry, authentication, request, response);
		}
	}

	abstract void onConcurrentSessionControlAuthentication(SessionRegistry sessionRegistry, Authentication authentication, HttpServletRequest request, HttpServletResponse response);
}
