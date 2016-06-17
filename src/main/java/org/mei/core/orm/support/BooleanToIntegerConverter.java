package org.mei.core.orm.support;

import javax.persistence.AttributeConverter;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 17.
 */
public class BooleanToIntegerConverter implements AttributeConverter<Boolean, Integer>{
	@Override
	public Integer convertToDatabaseColumn(Boolean attribute) {
		return (attribute != null && attribute) ? 1 : 0;
	}

	@Override
	public Boolean convertToEntityAttribute(Integer dbData) {
		return dbData == 1 ? true :false;
	}
}
