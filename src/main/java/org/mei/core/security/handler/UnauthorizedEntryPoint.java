package org.mei.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mei.core.common.http.Requesting;
import org.mei.core.module.handler.SuccessHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 인증(Consumer)되지 않은 사용자가 허가되지 않은 페이지에 접근할때 요청되는 헨들러.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 30.
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

	private final String loginUrl;
	private boolean chain = true;

	public UnauthorizedEntryPoint(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setChain(boolean chain) {
		this.chain = chain;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		if(Requesting.isAjax(request)) {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			SuccessHandler successHandler = new SuccessHandler();

			successHandler.setMessage(exception.getMessage());
			successHandler.setError(true);

			ObjectMapper objectMapper = new ObjectMapper();
			String data = objectMapper.writeValueAsString(successHandler);
			PrintWriter out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
		} else {

			if (chain) {
				request.setAttribute("chain", String.valueOf(chain));
				request.getRequestDispatcher(request.getContextPath() + loginUrl).forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + loginUrl);
			}
		}
	}
}
