package com.emma.security.jdbc.domian;

import javax.persistence.*;

/**
 * @author liujian
 * @date 2019/4/30
 */
public class User {

	private String username;

	private String password;

	private String enabled;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
}
