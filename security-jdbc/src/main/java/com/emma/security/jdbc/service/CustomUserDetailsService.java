package com.emma.security.jdbc.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author liujian
 * @date 2019/4/28
 */
@Service(value = "accountService")
public class CustomUserDetailsService implements UserDetailsService {



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
		return null;
	}


}
