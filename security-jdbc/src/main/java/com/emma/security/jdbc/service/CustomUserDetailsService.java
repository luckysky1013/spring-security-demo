package com.emma.security.jdbc.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emma.security.jdbc.domian.Role;

/**
 * @author liujian
 * @date 2019/4/28
 */
@Service(value = "accountService")
public class CustomUserDetailsService implements UserDetailsService {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//		User user = userService.findBySso(username);
		//		System.out.println("User : "+user);
		//		if(user==null){
		//			System.out.println("User not found");
		//			throw new UsernameNotFoundException("Username not found");
		//		}
		//		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
		//				user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select * from users where username=:username");
		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("username", username);
		com.emma.security.jdbc.domian.User user = jdbcTemplate
				.queryForObject(sqlQuery.toString(), params1, new UserMapper());
		if (user == null) {
			System.out.println("不是本系统用户");
			throw new AuthenticationServiceException("非本系统用户，请重新登录");
		}
		String password = user.getPassword();
		boolean enabled = true;
		boolean accountNonLoked = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExipred = true;
		/**
		 * 权限信息
		 */
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		StringBuilder roleSql = new StringBuilder();
		roleSql.append("select * from user_roles where username=:username");

		List<Role> roles = jdbcTemplate.query(roleSql.toString(), params1, new RoleMapper());
		if (roles == null) {
			return null;
		}
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return new User(username, password, enabled, accountNonExpired, credentialsNonExipred, accountNonLoked,
				authorities);
	}

	/**
	 * 用户的实体映射处理
	 */
	private static final class UserMapper implements RowMapper<com.emma.security.jdbc.domian.User> {
		@Override
		public com.emma.security.jdbc.domian.User mapRow(ResultSet rs, int rowNum) throws SQLException {
			com.emma.security.jdbc.domian.User dto = new com.emma.security.jdbc.domian.User();
			dto.setUsername(rs.getString("username"));
			dto.setPassword(rs.getString("password"));
			dto.setEnabled(rs.getString("enabled"));
			return dto;
		}
	}

	/**
	 * 用户的实体映射处理
	 */
	private static final class RoleMapper implements RowMapper<com.emma.security.jdbc.domian.Role> {
		@Override
		public com.emma.security.jdbc.domian.Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			com.emma.security.jdbc.domian.Role dto = new com.emma.security.jdbc.domian.Role();
			dto.setUsername(rs.getString("username"));
			dto.setId(rs.getInt("id"));
			dto.setRole(rs.getString("role"));
			return dto;
		}
	}
}
