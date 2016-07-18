package org.mei.app.modules.grant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 13.
 */
@Entity
@Table(name = "GRANT_BASIC_PERMIT")
@JsonIgnoreProperties(ignoreUnknown = true)
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

	@Override
	public String toString() {
		return "GrantBasicPermit{" +
				"roleName='" + roleName + '\'' +
				", permissions='" + permissions + '\'' +
				'}';
	}
}
