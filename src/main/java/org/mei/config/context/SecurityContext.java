package org.mei.config.context;

import org.mei.app.modules.member.service.JsonConsumerService;
import org.mei.core.security.ConsumerAuthenticationProvider;
import org.mei.core.security.handler.*;
import org.mei.core.security.password.ShaPasswordEncoder;
import org.mei.core.security.service.ConsumerDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 30.
*/
@EnableWebSecurity
@Order(2)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled=true)
public class SecurityContext {
	private static final Logger logger = LoggerFactory.getLogger(SecurityContext.class);

	@Autowired Properties mei;

	private PasswordEncoder passwordEncoder;

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return passwordEncoder;
	}

	/**
	 * 암호화를 생성한다.
	 *
	 * @return
	 */
	private PasswordEncoder getPasswordEncoder() {
		String passwordEncoderType = mei.getProperty("security.passwordEncoder");
		String salt = mei.getProperty("security.sha.salt", null);

		if (passwordEncoderType.equals("noOpPasswordEncoder")) {
			return NoOpPasswordEncoder.getInstance();
		} else if (passwordEncoderType.equals("standardPasswordEncoder")) {
			return new StandardPasswordEncoder(salt);
		} else if (passwordEncoderType.equals("shaPasswordEncoder")) {
			int strength = Integer.parseInt(mei.getProperty("security.sha.strength", "256"));
			return new ShaPasswordEncoder(strength);
		} else {
			return new BCryptPasswordEncoder();
		}
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*
		auth.inMemoryAuthentication()
				.withUser("user").password("1234").roles("USER")
				.and().withUser("admin").password("1234").roles("ADMIN", "USER");
		 */
		passwordEncoder = getPasswordEncoder();
		UserDetailsService userDetailsService = new ConsumerDetailsService(new JsonConsumerService());
		ConsumerAuthenticationProvider consumerAuthenticationProvider = new ConsumerAuthenticationProvider(userDetailsService);
		consumerAuthenticationProvider.setPasswordEncoder(passwordEncoder);

		auth.authenticationProvider(consumerAuthenticationProvider);
	}

	@Configuration
	public static class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		private static final Logger logger = LoggerFactory.getLogger(SecurityConfigurationAdapter.class);

		@Autowired Properties mei;
		@Autowired SessionRegistry sessionRegistry;
		@Autowired PasswordEncoder passwordEncoder;

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
			String loginUrl = mei.getProperty("security.loginUrl");

			String usernameParameter = mei.getProperty("security.usernameParameter");
			String passwordParameter = mei.getProperty("security.passwordParameter");

			String logoutUrl = mei.getProperty("security.logoutUrl");
			String cookieName = mei.getProperty("security.cookieName");

			String targetUrlParameter = mei.getProperty("security.targetUrlParameter");
			boolean alwaysUseDefaultTargetUrl = Boolean.parseBoolean( mei.getProperty("security.alwaysUseDefaultTargetUrl") );
			String defaultTargetUrl = mei.getProperty("security.defaultTargetUrl");
			String expiredUrl = mei.getProperty("security.expiredUrl");

			// 오류가 발생했을 때
			String errorPageUrl = mei.getProperty("security.errorPageUrl");
			String redirectUrlParameter = mei.getProperty("security.redirectUrlParameter");

			int maximumSessions = Integer.parseInt(mei.getProperty("security.maximumSessions", "-1"));
			boolean exceptionIfMaximumExceeded = Boolean.parseBoolean(mei.getProperty("security.exceptionIfMaximumExceeded"));

			if (logger.isDebugEnabled()) {
				logger.debug("loginUrl: " + loginUrl);
				logger.debug("usernameParameter: " + usernameParameter);
				logger.debug("passwordParameter: " + passwordParameter);
				logger.debug("logoutUrl: " + logoutUrl);
				logger.debug("targetUrlParameter: " + targetUrlParameter);
				logger.debug("alwaysUseDefaultTargetUrl: " + alwaysUseDefaultTargetUrl);
				logger.debug("defaultTargetUrl: " + defaultTargetUrl);
				logger.debug("expiredUrl: " + expiredUrl);
				logger.debug("errorPageUrl: " + errorPageUrl);
				logger.debug("redirectUrlParameter: " + redirectUrlParameter);

				logger.debug("maximumSessions: " + maximumSessions);
				logger.debug("exceptionIfMaximumExceeded: " + exceptionIfMaximumExceeded);
			}

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
			controlAuthenticationStrategy.setMaximumSessions(maximumSessions);
			controlAuthenticationStrategy.setExceptionIfMaximumExceeded(exceptionIfMaximumExceeded);

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
