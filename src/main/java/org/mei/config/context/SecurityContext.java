package org.mei.config.context;

import org.mei.core.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. 암호화
 * 2. 중복 로그인인 경우 경고 메세지 출력 후 로그인 여부 선택하기
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 30.
*/
@EnableWebSecurity
@Order(2)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled=true)
public class SecurityContext {

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("user").password("1234").roles("USER")
				.and().withUser("admin").password("1234").roles("ADMIN", "USER");
	}

	@Configuration
	public static class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired SessionRegistry sessionRegistry;

		private AuthenticationManager authenticationManager;

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			authenticationManager = super.authenticationManagerBean();
			return authenticationManager;
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			String loginUrl = "/member/login";

			String usernameParameter = "user_id";
			String passwordParameter = "password";

			String logoutUrl = "/member/logout";
			String cookieName = "JSESSIONID";

			String targetUrlParameter = "redirect_url";
			boolean alwaysUseDefaultTargetUrl = false;
			String defaultTargetUrl = "/";
			String expiredUrl = "/";

			// 오류가 발생했을 때
			String errorPageUrl = "/pages/error/401.jsp";
			String redirectUrlParameter = "redirect_url";

			/**
			 * @see SessionCreationPolicy.IF_REQUIRED : 시큐리티에서 필요한 경우 한번 생성함.
			 * @see SessionCreationPolicy.ALWAYS : 무조건 만듬
			 * @see SessionCreationPolicy.NEVER : 응용프로그램에서 필요할 경우 생성함.
			 * @see SessionCreationPolicy.STATELESS : 만들지 않음
			 */

			UnauthorizedEntryPoint unauthorizedEntryPoint = new UnauthorizedEntryPoint(loginUrl);

			SignInSuccessHandler signInSuccessHandler = new SignInSuccessHandler();
			signInSuccessHandler.setTargetUrlParameter(targetUrlParameter);
			signInSuccessHandler.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
			signInSuccessHandler.setDefaultTargetUrl(defaultTargetUrl);

			SignOutSuccessHandler signOutSuccessHandler = new SignOutSuccessHandler();
			signOutSuccessHandler.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
			signOutSuccessHandler.setDefaultTargetUrl(defaultTargetUrl);
			signOutSuccessHandler.setTargetUrlParameter(targetUrlParameter);

			SignInFailureHandler signInFailureHandler = new SignInFailureHandler();
			signInFailureHandler.setDefaultFailureUrl(defaultTargetUrl);

			AccessFailureHandler accessFailureHandler = new AccessFailureHandler(loginUrl, errorPageUrl);
			accessFailureHandler.setRedirectUrlParameter(redirectUrlParameter);

			SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new SessionFixationProtectionStrategy();
			RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry);

			// SessionAuthenticationStrategy : SAS

			// @see ConcurrentSessionControlAuthenticationHandler
			ConcurrentSessionControlAuthenticationStrategy controlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
			controlAuthenticationStrategy.setMaximumSessions(1);
			controlAuthenticationStrategy.setExceptionIfMaximumExceeded(false);

			List<SessionAuthenticationStrategy> delegateStrategies = new ArrayList<>();
			delegateStrategies.add(controlAuthenticationStrategy);
			delegateStrategies.add(sessionFixationProtectionStrategy);
			delegateStrategies.add(registerSessionAuthenticationStrategy);
			SessionAuthenticationStrategy sas =  new CompositeSessionAuthenticationStrategy(delegateStrategies);
			// SessionAuthenticationStrategy : SAS

			// Custom Filter Setting
			UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
			usernamePasswordAuthenticationFilter.setSessionAuthenticationStrategy(sas);
			usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);

			ConcurrentSessionFilter concurrentSessionFilter = new ConcurrentSessionFilter(sessionRegistry, expiredUrl);
			// Custom Filter Setting

			http
					.sessionManagement()
					.sessionAuthenticationStrategy(sas)
					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

					.and().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
					.and().exceptionHandling().accessDeniedHandler(accessFailureHandler)

					.and()
					.formLogin()
					.loginPage(loginUrl)
					.usernameParameter(usernameParameter)
					.passwordParameter(passwordParameter)
					.loginProcessingUrl(loginUrl).permitAll()
					.failureHandler(signInFailureHandler)
					.successHandler(signInSuccessHandler)

					.and()
					.logout()
					.invalidateHttpSession(true)
					.logoutUrl(logoutUrl)
					.logoutSuccessHandler(signOutSuccessHandler)
					.deleteCookies(cookieName)

					.and()
					.addFilter(concurrentSessionFilter)
					.addFilter(usernamePasswordAuthenticationFilter)
					.csrf().disable()
					.authorizeRequests(); // 없으면 오류 발생 (알수없음)
		}
	}

}
