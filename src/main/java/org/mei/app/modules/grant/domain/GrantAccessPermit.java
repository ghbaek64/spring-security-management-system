package org.mei.app.modules.grant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 10.
 */
@Entity(name = "GRANT_ACCESS_PERMIT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrantAccessPermit implements Serializable {

	private static final long serialVersionUID = -6906991921562410284L;

	@Column(name = "role_names")
	@Id
	private String roleName;
	private String patterns;
	private String methods;
	private int seq;

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

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
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
