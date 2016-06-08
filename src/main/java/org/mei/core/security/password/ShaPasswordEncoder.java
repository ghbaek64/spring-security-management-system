package org.mei.core.security.password;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 7.
 */
public class ShaPasswordEncoder implements PasswordEncoder {
	private org.springframework.security.authentication.encoding.ShaPasswordEncoder shaPasswordEncoder;
	private Object salt = null;

	public ShaPasswordEncoder() {
		this(1);
	}

	/**
	 * Initialize the ShaPasswordEncoder with a given SHA stength as supported by the JVM
	 * EX: <code>ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);</code>
	 * initializes with SHA-256
	 *
	 * @param strength EX: 1, 256, 384, 512
	 */
	public ShaPasswordEncoder(int strength) {
		shaPasswordEncoder = new org.springframework.security.authentication.encoding.ShaPasswordEncoder(strength);
	}

	public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
		shaPasswordEncoder.setEncodeHashAsBase64(encodeHashAsBase64);
	}
	public void setSalt(Object salt) {
		this.salt = salt;
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return shaPasswordEncoder.encodePassword(rawPassword.toString(), salt);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return shaPasswordEncoder.isPasswordValid(encodedPassword, rawPassword.toString(), salt);
	}
}
