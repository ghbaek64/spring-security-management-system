package org.mei.core.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * @date 2015. 12. 4.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 */
public class AntPathMatchers extends AntPathMatcher {

	public boolean matches(String pattern, String path) {
		return matches(new String[]{ pattern }, path);
	}
	public boolean matches(String[] patterns, String path) {
		return matches(Arrays.asList(patterns), path);
	}
	public boolean matches(List<String> patterns, String path) {
		if (patterns == null || path == null) return false;

		boolean result = false;

		for (String pattern : patterns) {
			if (StringUtils.isEmpty(pattern)) continue;
			if (match(StringUtils.trim(pattern), path)) {
				result = true;
				break;
			}
		}

		return result;
	}

}
