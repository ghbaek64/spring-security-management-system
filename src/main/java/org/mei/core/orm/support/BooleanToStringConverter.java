package org.mei.core.orm.support;

import javax.persistence.AttributeConverter;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 17.
 */
public class BooleanToStringConverter implements AttributeConverter<Boolean, String>{
	@Override
	public String convertToDatabaseColumn(Boolean value) {
		return (value != null && value) ? "Y" : "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String data) {
		if (data == null) return false;
		return data.equals("Y");
	}
}
