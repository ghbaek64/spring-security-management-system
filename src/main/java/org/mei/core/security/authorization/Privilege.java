package org.mei.core.security.authorization;

import org.mei.core.security.enums.Permission;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
public class Privilege implements Serializable {
	private final Permission privilege;

	public Privilege(Permission privilege) {
		this.privilege = privilege;
	}

	public Permission getPrivilege() {
		return privilege;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Privilege) {
			Privilege privilege = (Privilege) obj;

			return this.privilege.equals(privilege.getPrivilege());
		}

		return false;
	}

	public int hashCode() {
		return this.privilege.hashCode();
	}

	public String toString() {
		return this.privilege.toString();
	}

	public static List<Privilege> createList(Permission... privileges) {
		Assert.notNull(privileges, "You must supply an array of attribute names");
		List<Privilege> privilegeList = new ArrayList<>(privileges.length);

		for (Permission privilege : privileges) {
			privilegeList.add(new Privilege(privilege));
		}

		return privilegeList;
	}
}
