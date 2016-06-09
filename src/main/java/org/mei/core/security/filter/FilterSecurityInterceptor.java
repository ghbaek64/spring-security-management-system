package org.mei.core.security.filter;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 9.
 */
public class FilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
	private final FilterInvocationSecurityMetadataSource securityMetadataSource;

	public FilterSecurityInterceptor(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation( request, response, chain );
		InterceptorStatusToken interceptorStatusToken = this.beforeInvocation(fi);
		fi.getChain().doFilter(request, response);
		this.afterInvocation(interceptorStatusToken, null);
	}

	@Override
	public Class<? extends Object> getSecureObjectClass(){
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init( FilterConfig filterconfig ) throws ServletException {
	}
}