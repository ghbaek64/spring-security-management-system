package org.mei.config.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
}
