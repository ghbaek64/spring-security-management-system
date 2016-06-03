package org.mei.core.io.support;

import java.util.*;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http ://syaku.tistory.com
 * @since 16. 5. 25.
 */
public class PropertyUtils {
	private final Properties properties;

	public PropertyUtils(Properties properties) {
		this.properties = properties;
	}

	/**
	 * target 파라메터와 일치하는 프로퍼티를 찾는 다.
	 *
	 * @param target properties name
	 * @return
	 */
	public List<String> getNames(String target) {
		List<String> result;

		Set<String> names = properties.stringPropertyNames();

		if (names == null) return null;

		result = new ArrayList<>();

		Iterator<String> itr = names.iterator();
		while(itr.hasNext()) {
			String name = itr.next();

			if (name == null) continue;

			if (!name.isEmpty() && name.startsWith(target)) {
				result.add(name);
			}
		}

		return result;
	}
}
