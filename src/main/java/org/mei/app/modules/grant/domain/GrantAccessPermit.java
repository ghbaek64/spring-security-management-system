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
 * @since 16. 6. 10.
 *
 * @see org.mei.core.security.access.AccessPermit
 */
@Entity
@Table(name = "GRANT_ACCESS_PERMIT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrantAccessPermit implements Serializable {

	private static final long serialVersionUID = -6906991921562410284L;

	@Column(name = "role_name")
	@Id
	private String roleName;
	private String patterns;
	private String methods;
	private Integer seq;

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

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "GrantAccessPermit{" +
				"roleName='" + roleName + '\'' +
				", patterns='" + patterns + '\'' +
				", methods='" + methods + '\'' +
				", seq=" + seq +
				'}';
	}
}
