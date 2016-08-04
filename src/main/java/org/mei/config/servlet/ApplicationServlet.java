package org.mei.config.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.Properties;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 4. 26.
 */
@Configuration
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled=true)
@EnableAspectJAutoProxy
@ComponentScan(
		basePackages = "org.mei.app",
		includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = { ControllerAdvice.class, Controller.class })
)
public class ApplicationServlet extends WebMvcConfigurerAdapter {

	@Autowired private Properties mei;

	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setContentType("text/html;charset=" + mei.getProperty("app.charset"));
		resolver.setCache(false);
		resolver.setCacheLimit(0);
		resolver.setPrefix("");
		resolver.setSuffix("");
		resolver.setExposeSpringMacroHelpers(true);

		return resolver;
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views");
		resolver.setContentType("text/html;charset=" + mei.getProperty("app.charset"));
		resolver.setSuffix(".jsp");
		resolver.setCache(false);
		resolver.setCacheLimit(0);
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
}
