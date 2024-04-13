package com.portfolio.www.com.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public class InOutController {

	 public InOutController() {		
		// TODO Auto-generated constructor stub
		System.out.println("\n\n---------------------------InOutController 생성됨---------------------------\n\n");
	 }
	
	@RequestMapping("/input.do")
	public String input() {
		return "input";
	}
	
	@RequestMapping("/output.do")
	public ModelAndView output(@RequestParam HashMap<String, String> params) {
		
		String measureDate = params.get("measureDate").toString();
		String eAmount = params.get("eAmount").toString();
		/*여름철 구분 : 6월 1일 ~ 8월 31일 */
		/*정액 할인
		 * 1. 월 1만 6천원, 여름철 2만원 한도
		 * 2. 월 1만 6천원, 여름철 2만원 한도
		 * 3. 월 1만원, 여름철 1만 2천원 한도
		 * 4. 월 8천원, 여름철 1만원 한도 
		 * */
		String discountType1 = params.get("discountType1").toString();
		
		/*정률할인(30%)
		 * 1. 30% 할인 (월 1만 6천원 한도)
		 * 2. 30% 할인 (월 1만 6천원 한도)
		 * 3. 30% 할인 (월 1만 6천원 한도)
		 * 4. 30% 할인
		 * 5. 30% 할인
		 * */
		// 정률할인(30%)
		String discountType2 = params.get("discountType2").toString();
		
		
		
		
		

		System.out.println(params);
		System.out.println("measure : " + measureDate);
		
		
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("output");
		mv.addObject("measureDate", "2024-01-01");
//		mv.addObject("measureDate", "2024-01-01");
//		System.out.println(mv.getModelMap());
		return mv;
	}
}
