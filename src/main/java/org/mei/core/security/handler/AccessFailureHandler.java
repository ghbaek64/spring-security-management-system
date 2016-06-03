package org.mei.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mei.core.common.http.Requesting;
import org.mei.core.module.handler.SuccessHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 로그인 후 페이지 접근 시도시 권한이 없는 경우
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 30.
 */
public class AccessFailureHandler implements AccessDeniedHandler {

	private final String loginUrl;
	private final String errorPage;
	private String redirectUrlParameter = "redirect_url";
	private boolean chain = true;

	public AccessFailureHandler(String loginUrl, String errorPage) {
		this.loginUrl = loginUrl;
		this.errorPage = errorPage;
	}

	public void setChain(boolean chain) {
		this.chain = chain;
	}

	public void setRedirectUrlParameter(String redirectUrlParameter) {
		this.redirectUrlParameter = redirectUrlParameter;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
		String url = Requesting.getPathQueryString(request);

		if(Requesting.isAjax(request)) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			SuccessHandler successHandler = new SuccessHandler(exception.getMessage(), true);

			ObjectMapper objectMapper = new ObjectMapper();
			String data = objectMapper.writeValueAsString(successHandler);

			PrintWriter out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
		} else {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			if (chain) {
				request.setAttribute("message", exception.getMessage());
				request.setAttribute(redirectUrlParameter, url);
				request.setAttribute("loginUrl", loginUrl);
				request.getRequestDispatcher(request.getContextPath() + errorPage).forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + errorPage);
			}
		}
	}
}
