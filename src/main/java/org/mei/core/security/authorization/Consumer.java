package org.mei.core.security.authorization;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 17.
 */
public class Consumer implements UserDetails, CredentialsContainer {
	private User user;

	public Consumer(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this(username, password, true, true, true, true, authorities);
	}

	public Consumer(String username, String password, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		this(username, password, true, true, true, accountNonLocked, authorities);
	}

	public Consumer(String username, String password, boolean enabled,
						  boolean accountNonExpired, boolean credentialsNonExpired,
						  boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {

		if (((username == null) || "".equals(username)) || (password == null)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.user = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	@Override
	public void eraseCredentials() {
		user.eraseCredentials();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}
}
