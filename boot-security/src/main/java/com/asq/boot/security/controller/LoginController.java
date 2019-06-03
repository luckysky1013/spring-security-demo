package com.asq.boot.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liujian
 * @date 2019/5/30
 */
@Controller
public class LoginController {

	@RequestMapping(value = "/test")
	@ResponseBody
	public String test(){
		return "success";
	}
}
