package org.mei.core.security.access;

import org.mei.core.security.enums.Method;

import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public interface MehtodAttribute extends Serializable {
	Method getAttribute();
}
