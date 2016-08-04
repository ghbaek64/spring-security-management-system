package org.mei.config.context;

import freemarker.template.utility.XmlEscape;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 8.
 */
@Configuration
@ComponentScan(
		basePackages = "org.mei.app",
		includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Component.class, Service.class})
)
public class ModuleContext {

	@Bean
	public FreeMarkerConfigurer freemarkerConfig() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/WEB-INF/ftl");
		Map<String, Object> map = new HashMap<>();
		map.put("xml_escape", new XmlEscape());
		configurer.setFreemarkerVariables(map);

		Properties properties = new Properties();
		properties.setProperty("cache_storage", "freemarker.cache.NullCacheStorage");
		properties.setProperty("auto_import", "spring.ftl as spring");
		properties.setProperty("number_format", "0.####");
		configurer.setFreemarkerSettings(properties);
		return configurer;
	}
}
