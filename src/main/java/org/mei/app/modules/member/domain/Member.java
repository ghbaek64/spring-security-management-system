package org.mei.app.modules.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.mei.core.orm.support.BooleanToIntegerConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 8.
 */
@Entity(name = "MEMBER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member implements Serializable {

	private static final long serialVersionUID = 7266752152183703556L;

	@Column(name = "member_idx")
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
	String memberIdx;

	@Column(name = "user_id")
	String userId;

	@Column(name = "user_name")
	String userName;

	@Column(name = "email")
	String email;

	@Column(name = "password")
	String password;

	@Column(name = "reg_date")
	@Temporal(TemporalType.TIMESTAMP)
	Date regDate;

	@Column(name = "last_access_date")
	@Temporal(TemporalType.TIMESTAMP)
	Date lastAccessDate;

	@Column(name = "last_access_ip")
	String lastAccessIp;

	@Column(name = "session_id")
	String sessionId;

	@Column(name = "role_name")
	String roleName;

	@Column(name = "account_block")
	@Convert(converter=BooleanToIntegerConverter.class)
	Boolean accountBlock;

	public String getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(String memberIdx) {
		this.memberIdx = memberIdx;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public String getLastAccessIp() {
		return lastAccessIp;
	}

	public void setLastAccessIp(String lastAccessIp) {
		this.lastAccessIp = lastAccessIp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isAccountBlock() {
		return accountBlock;
	}

	public void setAccountBlock(boolean accountBlock) {
		this.accountBlock = accountBlock;
	}
}