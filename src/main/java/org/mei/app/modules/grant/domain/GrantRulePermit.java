package org.mei.app.modules.grant.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 13.
 */
public class GrantRulePermit implements Serializable {

	private static final long serialVersionUID = -954312860713037351L;

	@Column(name = "rule_permit_srl")
	@Id
	private long rulePermitSrl;
	@Column(name = "role_name")
	private String roleName;
	private String patterns;
	private String methods;
	private String permission;
	private int seq;

	public long getRulePermitSrl() {
		return rulePermitSrl;
	}

	public void setRulePermitSrl(long rulePermitSrl) {
		this.rulePermitSrl = rulePermitSrl;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "GrantRulePermit{" +
				"rulePermitSrl=" + rulePermitSrl +
				", roleName='" + roleName + '\'' +
				", patterns='" + patterns + '\'' +
				", methods='" + methods + '\'' +
				", permission='" + permission + '\'' +
				", seq=" + seq +
				'}';
	}
}
