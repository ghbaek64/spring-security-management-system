package org.mei.app.modules.grant.domain;

import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 14.
 */
public class GrantUserPermitPK implements Serializable {

	private static final long serialVersionUID = 4190371237210606268L;
	private String userId;
	private String roleName;

	public GrantUserPermitPK() {
	}

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
}
