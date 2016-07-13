package org.mei.app.modules.grant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 4.
 */

public class GrantAccess implements Serializable {

	private static final long serialVersionUID = 8935674872273631881L;

	@Column(name = "grant_access_srl")
	@Id
	@GeneratedValue(generator = "IdGenerator")
	@GenericGenerator(
			name = "IdGenerator",
			strategy = "org.mei.core.orm.IdGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "sequenceName", value = "MEMBER_IDX_SEQ"),
					@org.hibernate.annotations.Parameter(name = "prefix", value = "MEM"),
					@org.hibernate.annotations.Parameter(name = "pad", value = "17")
			})
	private long grantAccessSrl;
	private String patterns;
	private String methods;
	private String roleNames;
	private String allow;
	private int seq;

	public long getGrantAccessSrl() {
		return grantAccessSrl;
	}

	public void setGrantAccessSrl(long grantAccessSrl) {
		this.grantAccessSrl = grantAccessSrl;
	}

	public String getPatterns() {
		return patterns;
	}

	public void setPatterns(String patterns) {
		this.patterns = patterns;
	}

	public String getMethods() {
		return methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getAllow() {
		return allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}
