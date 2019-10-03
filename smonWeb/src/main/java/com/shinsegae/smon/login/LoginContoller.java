package com.shinsegae.smon.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginContoller {
	
	@RequestMapping("/")
	public ModelAndView hello() {
		ModelAndView view = new ModelAndView("main");
		view.addObject("text", "world");
		return view;
	}
}
