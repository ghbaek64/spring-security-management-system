package org.mei.app.modules.grant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 13.
 */
@Entity
@Table(name = "GRANT_USER_PERMIT")
@IdClass(GrantUserPermitPK.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrantUserPermit implements Serializable {

	private static final long serialVersionUID = -2278437267865863036L;

	@Column(name = "user_id")
	@Id
	private String userId;

	@Column(name = "role_name")
	@Id
	private String roleName;
	private String permissions;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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
		return "GrantUserPermit{" +
				"userId='" + userId + '\'' +
				", roleName='" + roleName + '\'' +
				", permissions='" + permissions + '\'' +
				'}';
	}
}
