package org.mei.app.modules.grant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 13.
 */
@Entity
@Table(name = "GRANT_RULE_PERMIT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrantRulePermit implements Serializable {

	private static final long serialVersionUID = -954312860713037351L;

	@Column(name = "rule_permit_srl")
	@Id
	@GeneratedValue(generator = "IdGenerator")
	@GenericGenerator(name = "IdGenerator",
			strategy = "org.mei.core.orm.IdGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "sequenceName", value = "GRANT_RULE_PERMIT_SEQ")
			})
	private Long rulePermitSrl;

	@Column(name = "role_name")
	private String roleName;
	private String patterns;
	private String methods;
	private String permission;
	private Integer seq;

	public Long getRulePermitSrl() {
		return rulePermitSrl;
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

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
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
