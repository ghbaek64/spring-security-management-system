package org.mei.app.modules.grant.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 13.
 */
@Entity(name = "GRANT_BASIC_PERMIT")
public class GrantBasicPermit implements Serializable {

	private static final long serialVersionUID = -8645675549624832171L;
	@Id
	@Column(name = "role_name")
	private String roleName;
	private String permissions;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
}
