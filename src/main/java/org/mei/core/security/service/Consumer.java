package org.mei.core.security.service;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 7.
 */
public class Consumer {
	private final String userId;
	private String password;
	private String userName;
	private String role;

	public Consumer(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Consumer{" +
				"userId='" + userId + '\'' +
				", password='" + password + '\'' +
				", userName='" + userName + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}
