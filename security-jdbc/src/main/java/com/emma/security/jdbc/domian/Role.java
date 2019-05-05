package com.emma.security.jdbc.domian;

/**
 * @author liujian
 * @date 2019/4/30
 */
public class Role {

	private int id;

	private String username;

	private String role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
