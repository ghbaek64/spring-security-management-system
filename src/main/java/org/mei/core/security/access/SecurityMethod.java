package org.mei.core.security.access;

import org.mei.core.security.enums.Method;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class SecurityMethod implements MehtodAttribute {

	private final Method method;

	public SecurityMethod(Method method) {
		this.method = method;
	}

	public boolean equals(Object obj) {
		if (obj instanceof MehtodAttribute) {
			MehtodAttribute attr = (MehtodAttribute) obj;

			return this.method.equals(attr.getAttribute());
		}

		return false;
	}

	public Method getAttribute() {
		return this.method;
	}

	public int hashCode() {
		return this.method.hashCode();
	}

	public String toString() {
		return this.method.toString();
	}

	public static List<MehtodAttribute> createList(MehtodAttribute... methodAttributes) {
		Assert.notNull(methodAttributes, "You must supply an array of attribute names");
		List<MehtodAttribute> attributes = new ArrayList<>(methodAttributes.length);

		for (MehtodAttribute attribute : methodAttributes) {
			attributes.add(new SecurityMethod(attribute.getAttribute()));
		}

		return attributes;
	}
}
