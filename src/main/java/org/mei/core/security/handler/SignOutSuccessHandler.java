package org.mei.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mei.core.common.http.Requesting;
import org.mei.core.module.handler.SuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 로그아웃을 성공한 경우
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 30.
 */
public class SignOutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		if(Requesting.isAjax(request)) {
			String targetUrl = determineTargetUrl(request, response);

			if (logger.isDebugEnabled()) {
				logger.debug("targetUrl: " + targetUrl);
				logger.debug("targetUrlParameter name: " + getTargetUrlParameter());
				logger.debug("targetUrlParameter value: " + request.getParameter(getTargetUrlParameter()));
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");

			SuccessHandler successHandler = new SuccessHandler();

			response.setStatus(HttpServletResponse.SC_OK);

			successHandler.setError(false);
			successHandler.setMessage(null);
			successHandler.setData(targetUrl);

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(successHandler);

			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} else {
			super.onLogoutSuccess(request, response, authentication);
		}

	}
}
