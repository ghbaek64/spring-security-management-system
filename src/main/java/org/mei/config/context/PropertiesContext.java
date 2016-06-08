package org.mei.config.context;

import org.mei.core.io.PathMatchingResourceResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 7.
 */
@Configuration
@PropertySource("classpath:/org/mei/config/mei.properties")
public class PropertiesContext {

	@Value("${app.charset}")
	private String appCharset;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public Properties mei() throws IOException {

		PathMatchingResourceResolver pathMatchingResourceResolver = new PathMatchingResourceResolver();
		Resource[] resources = pathMatchingResourceResolver.getResources(new String[]{
				"classpath*:org/mei/config/mei.properties",
				"classpath*:org/mei/config/mei.dev.properties"
		});

		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocations(resources);
		propertiesFactoryBean.setIgnoreResourceNotFound(true); // 프로퍼티 파일이 없는 경우 무시한다.
		propertiesFactoryBean.setFileEncoding(appCharset);
		propertiesFactoryBean.setLocalOverride(true); // 중복의 프로퍼티인 경우 나중에 프로퍼티를 사용한다.
		propertiesFactoryBean.setSingleton(true); // 싱글톤 여부 기본값 true

		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
}
