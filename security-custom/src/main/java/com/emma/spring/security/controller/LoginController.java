package com.emma.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author liujian
 * @date 2019/4/28
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
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error, Model model){
		if(error!=null){
			model.addAttribute("loginfailure","登录失败，用户名或密码错误!");
			return "login-failure";
		}
		return "login";
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

}
