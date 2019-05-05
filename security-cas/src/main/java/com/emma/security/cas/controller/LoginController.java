package com.emma.security.cas.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liujian
 * @date 2019/5/5
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
		return "admin";
	}

	/**
	 * 用户退出登录
	 * @return 退出后跳转的视图
	 */
	@RequestMapping("/logout")
	public String logout() {
		//		SecurityUtils.getSubject().logout();
		return "redirect:login";
	}

	@RequestMapping("/showName")
	public String showName(){
		//		Map map = new HashMap();
		// 获得用户名信息:
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		//		map.put("username", username);

		return username;
	}

}