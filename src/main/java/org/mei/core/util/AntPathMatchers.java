package org.mei.core.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;

/**
 * @date 2015. 12. 4.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 */
public class AntPathMatchers extends AntPathMatcher {

	public boolean matches(String pattern, String path) {
		return matches(new String[]{ pattern}, path);
	}
	public boolean matches(String[] pattern, String path) {
		if (pattern == null || path == null) return false;

		boolean result = false;

		for (String pat : pattern) {
			if (StringUtils.isEmpty(pat)) continue;
			if (match(StringUtils.trim(pat), path)) {
				result = true;
				break;
			}
		}

		return result;
	}

}
