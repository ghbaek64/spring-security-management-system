package org.mei.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 4. 21.
 */

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(
	basePackages = "org.mei.config.context",
	includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
	}
)
public class BootstrapContext {

}