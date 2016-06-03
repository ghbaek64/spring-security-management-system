package org.mei.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mei.core.common.http.Requesting;
import org.mei.core.module.handler.SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 시도를 실패할 경우
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 30.
 */
public class SignInFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	//@Autowired private MessageSourceAccessor messageSourceAccessor;

	public SignInFailureHandler() {
	}

	public SignInFailureHandler(String defaultFailureUrl) {
		super(defaultFailureUrl);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		Map<String, Object> data = new HashMap<>();

		if (exception.getClass().isAssignableFrom(CredentialsExpiredException.class)) {
			data.put("passwordExpired", true);
			System.out.println("aaaa");
		}

		if(Requesting.isAjax(request)) {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			SuccessHandler successHandler = new SuccessHandler();
			successHandler.setError(true);
			successHandler.setMessage(exception.getMessage());
			successHandler.setData(data);

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(successHandler);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}
}
