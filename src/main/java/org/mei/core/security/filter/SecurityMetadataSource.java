package org.mei.core.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 9.
 */
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private static final Logger logger = LoggerFactory.getLogger(SecurityMetadataSource.class);

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
}
