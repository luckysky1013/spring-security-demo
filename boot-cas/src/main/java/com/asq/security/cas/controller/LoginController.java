package com.asq.security.cas.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author liujian
 * @date 2019/5/28
 */
@Controller
public class LoginController {

	/**
	 * 返回 admin 视图
	 * @return
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String adminPage(Model model) {
		model.addAttribute("title", "Spring Security Hello World");
		model.addAttribute("message", "This is protected page!");
		User user= (User) SecurityContextHolder.getContext().getAuthentication();
		return "admin";
	}
}
