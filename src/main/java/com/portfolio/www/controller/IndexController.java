package com.portfolio.www.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	public IndexController() {		
		// TODO Auto-generated constructor stub
		System.out.println("\n\n---------------------------생성됨---------------------------\n\n");
	}
	
	@RequestMapping("/index.do")
	public String indexPage() {
		return "index";
	}
}
