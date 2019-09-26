package com.shinsegae.smon.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeContoller {
	
	@RequestMapping("/")
	public String hello() {
		return "main";
	}
}
