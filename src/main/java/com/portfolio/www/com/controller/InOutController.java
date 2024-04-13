package com.portfolio.www.com.controller;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.com.service.Calculation;

@Controller
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

		ModelAndView mv = new ModelAndView();
		
		if(params.get("measureDate").toString() == "") {
			System.out.println("되돌아갈래ㅐㅐ");
			mv.setViewName("input");
			mv.addObject("error","날짜형식이 올바르지 않습니다.");
			return mv;
		}
//		System.out.println("params.get(\"measureDate\") : " + params.get("measureDate"));
		String measureDate = params.get("measureDate").toString();
//		LocalDateTime measureDate = LocalDateTime.parse(params.get("measureDate").toString());
		String eAmount = params.get("eAmount").toString();
		
		
		Calculation cal = Calculation.getInstance();
		
		cal.set사용량(Integer.parseInt(eAmount));
		
		int 단계 = cal.get사용량() <=200 ? 1 : (201 <= cal.get사용량() && cal.get사용량() < 400 ? 2 : 3);
		String 기본요금 = String.format("%,d원 (%d단계 단가)",cal.get기본요금(),단계);
		String 전력량요금 = String.format("%,d원 (%s단계 요금의 합계, 원미만절사)",(int)cal.get전력량요금(),1<단계? "1~"+단계 : 단계);
		String 기후환경요금 = String.format("%,d kWh x 9원 = %,d원",(int)cal.get사용량(),(int)cal.get기후환경요금());
		String 연료비조정요금 = String.format("%,d kWh x 5원 = %,d원",(int)cal.get사용량(),(int)cal.get연료비조정요금());
		String 전기요금계 = String.format("%,d원 = %,d원(①) + %,d원(②) + %,d원(③)",(int)cal.get전기요금계(),cal.get기본요금(),(int)cal.get전력량요금(),(int)cal.get기후환경요금());
//		System.out.println("현재 부가가치세 : " + (int)Math.round(cal.get부가가치세()));
		String 부가가치세 = String.format("%,d원(⑤) x 10%% = %,d원 (원미만 반올림))",(int)cal.get전기요금계(),(int)Math.round(cal.get부가가치세()));
		String 전력기반기금 = String.format("%,d원(⑤) x 3.7%% = %,d원 (10원미만절사))",(int)cal.get전기요금계(),(int)cal.get전력기반기금());
//		System.out.println("청구 금액전 부가가치세 : " + (int)Math.round(cal.get부가가치세()));
		String 청구금액 = String.format("%,d원(⑤) + %,d원(⑥) + %,d원(⑦) - 0원(⑧) = %,d원 (10원미만절사)",(int)cal.get전기요금계(),(int)Math.round(cal.get부가가치세()),(int)cal.get전력기반기금(),(int)(cal.get전기요금계() + cal.get부가가치세() + cal.get전력기반기금())/10 * 10);
		
//		int eAmount = Integer.valueOf(params.get("eAmount").toString());
		
		/*여름철 구분 : 6월 1일 ~ 8월 31일 
		 *기초생활수급자 및 차상위 계층 정액할인은 정률할인(30%)과 중복 적용 가능 */
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
		
		mv.setViewName("output");
		mv.addObject("measureDate", measureDate);
		mv.addObject("eAmount", eAmount);
		mv.addObject("step", 단계);
		mv.addObject("basic", 기본요금);
		mv.addObject("er", 전력량요금);
		mv.addObject("cef", 기후환경요금);
		mv.addObject("fcaf", 연료비조정요금);
		mv.addObject("ebm", 전기요금계);
		mv.addObject("vat", 부가가치세);
		mv.addObject("eif", 전력기반기금);
		mv.addObject("ba", 청구금액);
		return mv;
	}
	
	
	
}
