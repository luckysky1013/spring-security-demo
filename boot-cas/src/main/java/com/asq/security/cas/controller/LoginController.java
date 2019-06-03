package com.asq.security.cas.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liujian
 * @date 2019/5/28
 */
@Controller
public class LoginController {

	/**
	 * 返回chen
	 * @return
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	@ResponseBody
	public String adminPage(){
		return "success";
	}
}
