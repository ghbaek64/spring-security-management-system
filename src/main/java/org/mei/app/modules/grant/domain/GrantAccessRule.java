package org.mei.app.modules.grant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 4.
 */
@Entity(name = "GRANT_ACCESS_RULE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrantAccessRule implements Serializable {

	private static final long serialVersionUID = 8935674872273631881L;

	@Column(name = "access_rule_srl")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accessRuleSrl;
	private String patterns;
	private String methods;
	@Column(name = "role_names")
	private String roleNames;
	private String allow;
	private int seq;

	public long getAccessRuleSrl() {
		return accessRuleSrl;
	}

	public void setAccessRuleSrl(long accessRuleSrl) {
		this.accessRuleSrl = accessRuleSrl;
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

	@Override
	public String toString() {
		return super.toString() + '{' +
				"accessRuleSrl=" + accessRuleSrl +
				", patterns='" + patterns + '\'' +
				", methods='" + methods + '\'' +
				", roleNames='" + roleNames + '\'' +
				", allow='" + allow + '\'' +
				", seq=" + seq +
				'}';
	}
}
