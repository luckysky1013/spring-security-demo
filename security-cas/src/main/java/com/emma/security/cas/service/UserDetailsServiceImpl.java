package com.emma.security.cas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author liujian
 * @date 2019/5/5
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("经过了认证类的请求");
		List<GrantedAuthority> grantAuths = new ArrayList();
		grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		grantAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		/**
		 *  认证信息（角色）
		 *  参数：用户名 ，密码，权限集合
		 */
		return new User(username, "", grantAuths);
	}

}
