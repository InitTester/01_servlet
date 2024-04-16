package com.portfolio.www.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.www.service.Calculation;

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
		
		// 추후 처리하기
		if(params.get("measureDate").toString() == "") {
			mv.setViewName("input");
			mv.addObject("error","날짜형식이 올바르지 않습니다.");
			return mv;
		}
		
		String measureDate = params.get("measureDate").toString();
		String eAmount = params.get("eAmount").toString();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(measureDate,format);

		int dist1 = Integer.parseInt(params.get("discountType1"));		
		int dist2 = Integer.parseInt(params.get("discountType2"));
		
		Calculation cal = Calculation.getInstance();		
		cal.set사용량(Integer.parseInt(eAmount));
		cal.set정액할인(dist1,date.atStartOfDay());
		cal.set정률할인(dist2);
		
		int 단계 = cal.get사용량() <=200 ? 1 : (201 <= cal.get사용량() && cal.get사용량() < 400 ? 2 : 3);
		
		String 기본요금 = String.format("%,d원 (%d단계 단가)",cal.get기본요금(),단계);
		String 전력량요금 = String.format("%,d원 (%s단계 요금의 합계, 원미만절사)",(int)cal.get전력량요금(),1<단계? "1~"+단계 : 단계);
		String 기후환경요금 = String.format("%,d kWh x 9원 = %,d원",(int)cal.get사용량(),(int)cal.get기후환경요금());
		String 연료비조정요금 = String.format("%,d kWh x 5원 = %,d원",(int)cal.get사용량(),(int)cal.get연료비조정요금());
		String 전기요금계 = String.format("%,d원 = %,d원(①) + %,d원(②) + %,d원(③)",(int)cal.get전기요금계(),cal.get기본요금(),(int)cal.get전력량요금(),(int)cal.get기후환경요금());
		String 부가가치세 = String.format("%,d원(⑤) x 10%% = %,d원 (원미만 반올림))",(int)cal.get전기요금계(),(int)Math.round(cal.get부가가치세()));
		String 전력기반기금 = String.format("%,d원(⑤) x 3.7%% = %,d원 (10원미만절사))",(int)cal.get전기요금계(),(int)cal.get전력기반기금());
		String 정액할인 =  String.format("%,d원",(int)cal.get정액할인값());		
		String 정액할인내용 = "";
	
		if(dist1 == 1 || dist1 ==2) {
			정액할인내용 = "월 1만 6천원, 여름철 2만원 한도";
		}else if(dist1 == 3) {
			정액할인내용 = "월 1만원, 여름철 1만 2천원 한도";
		}else {
			정액할인내용 = "월 8천원, 여름철 1만원 한도";
		}
		
		String 할인금액 = String.format("%,d원", dist1 !=1? (int)cal.get정액할인값() + (int)cal.get정률할인금액() : (int)cal.get정액할인값());
		double 총할인금액 = dist1 !=1? cal.get정액할인값() + cal.get정률할인금액() : cal.get정액할인값();
		
		String 청구금액 = String.format("%,d원(⑤) + %,d원(⑥) + %,d원(⑦) - %,d원(⑧) = %,d원 (10원미만절사)"
									  ,(int)cal.get전기요금계()
									  ,(int)Math.round(cal.get부가가치세())
									  ,(int)cal.get전력기반기금()
									  ,(int)총할인금액
									  ,(int)((cal.get전기요금계() + Math.round(cal.get부가가치세()) + cal.get전력기반기금() - (int)총할인금액)/10) * 10);
		mv.setViewName("output");
		mv.addObject("measureDate", measureDate);
		mv.addObject("eAmount", eAmount);
		mv.addObject("fd", 정액할인);
		mv.addObject("fdd", 정액할인내용);
		mv.addObject("step", 단계);
		mv.addObject("basic", 기본요금);
		mv.addObject("er", 전력량요금);
		mv.addObject("cef", 기후환경요금);
		mv.addObject("fcaf", 연료비조정요금);
		mv.addObject("ebm", 전기요금계);
		mv.addObject("vat", 부가가치세);
		mv.addObject("eif", 전력기반기금);
		mv.addObject("disc", 할인금액);
		mv.addObject("ba", 청구금액);
		return mv;
	}	
}
