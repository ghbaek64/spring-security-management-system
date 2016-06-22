package org.mei.core.common.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 22.
 */
public final class Collecting {
	private Collecting() {}

	public static <T> Iterable<T> forEmpty(Iterable<T> iterable) {
		return iterable == null ? Collections.<T>emptyList() : iterable;
	}

	public static boolean isEmpty(Collection<?> lol) {
		return (lol == null || lol.isEmpty());
	}

	public static <T> List<T> createList(T... s) {
		List<T> list = new ArrayList<>();

		for(T t : s) {
			list.add(t);
		}

		return list;
	}
}
