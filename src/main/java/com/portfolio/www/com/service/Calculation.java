package com.portfolio.www.com.service;

import java.time.*;

public class Calculation {
    // 6개의 요금을 구하는 메서드
    // 싱글톤 패턴
    private static Calculation instance;
    private double[] items = {120.0d, 214.6d, 307.3d};

    private double 사용량;
	private int 정액할인;
	private int 정액할인값;
	private int 정률할인; 
    private int 기본요금;
    private double 전력량요금;
    private double 기후환경요금;
    private double 연료비조정요금;
    private double 전기요금계;
    private double 부가가치세;
    private double 전력기반기금;
    private double 정률할인금액;

	private Calculation(){}

    public static Calculation getInstance() {
        if(instance == null){
            synchronized (Calculation.class){
                if(instance == null){
                    instance = new Calculation();
                }
            }
        }
        return instance;
    }

    public double get사용량() {
        return 사용량;
    }

    public void set사용량(double 사용량) {
        this.사용량 = 사용량;

        set기본요금();
        set전력량요금();
        set기후환경요금();
        set연료비조정요금();
        set전기요금계();
        set부가가치세();
        set전력기반기금();
    }

	
	/*여름철 구분 : 6월 1일 ~ 8월 31일 
	 *기초생활수급자 및 차상위 계층 정액할인은 정률할인(30%)과 중복 적용 가능 */
    
	/*정액 할인
	 * 1. 월 1만 6천원, 여름철 2만원 한도
	 * 2. 월 1만 6천원, 여름철 2만원 한도
	 * 3. 월 1만원, 여름철 1만 2천원 한도
	 * 4. 월 8천원, 여름철 1만원 한도 
	 * */
    
    public int get정액할인(int 정액할인) {
		return 정액할인;
	}

	public void set정액할인(int 정액할인, LocalDateTime ldt) {
		
		MonthDay md1 = MonthDay.of(6, 1); 
		MonthDay md2 = MonthDay.of(8, 31); 		
		MonthDay value = MonthDay.of(ldt.getMonth(), ldt.getDayOfMonth());
		boolean tf = md1.isAfter(value) && md2.isBefore(value);
		
		
		System.out.println("값은 :" +정액할인);
		
		if(정액할인 == 1 || 정액할인 == 2) {
			set정액할인값(tf ? 20000 : 16000);		
		} else if(정액할인 == 3) {
			set정액할인값(tf ? 12000 : 10000);		
		} else {
			set정액할인값(tf ? 10000 : 8000);
		}
	}
	
    public int get정액할인값() {
		return 정액할인값;
	}

	public void set정액할인값(int 정액할인값) {
		this.정액할인값 = 정액할인값;
	}
	
	/*정률할인(30%)
	 * 1. 30% 할인 (월 1만 6천원 한도)
	 * 2. 30% 할인 (월 1만 6천원 한도)
	 * 3. 30% 할인 (월 1만 6천원 한도)
	 * 4. 30% 할인
	 * 5. 30% 할인
	 * */
	
	public int get정률할인() {
		return 정률할인;
	}

	public void set정률할인(int 정률할인) {
		
		int 청구금액 = (int)(전기요금계 + 부가가치세 + 전력기반기금)/10 * 10;
		
		if(정률할인 ==1 ||정률할인 ==2 || 정률할인 ==3) {
			set정률할인금액((청구금액/100)*0.3 < 16000 ? (청구금액/100)*0.3 : 16000); 
		}else {
			set정률할인금액((청구금액/100)*0.3);
		}
	}


    public int get기본요금() {
        return 기본요금;
    }

    public void set기본요금() {
        if(사용량 <=200) {기본요금 = 910;}
        else if(201<= 사용량 && 사용량 <= 400 ) { 기본요금=1600;}
        else if(사용량 > 400) { 기본요금 = 7300;}
    }

    public double get전력량요금() {
        return 전력량요금;
    }

    public void set전력량요금() {
    	int step = 사용량 <=200 ? 1 : (201 <= 사용량 && 사용량 < 400 ? 2 : 3);
    	int 계산값 = (int)get사용량();
    	
    	for(int i=0; i <step; i++) {
    		int 계산량 = Math.min(계산값, 200);
    		계산값 -= 계산량;
    		전력량요금 += (items[i] * 계산량);
    	}
    }

    public double get기후환경요금() {
        return 기후환경요금;
    }

    public void set기후환경요금() {
        기후환경요금 = 사용량 *9;
    }
    
    public double get연료비조정요금() {
        return 연료비조정요금;
    }

    public void set연료비조정요금() {
    	연료비조정요금 = 사용량 *5;
    }

    public double get전기요금계() {
        return 전기요금계;
    }

    public void set전기요금계() {
        전기요금계 = (int)(get기본요금() + get전력량요금() +  get기후환경요금());
    }

    public double get부가가치세() {
        return 부가가치세;
    }

    public void set부가가치세() {
        부가가치세 = get전기요금계() * 0.1;
    }

    public double get전력기반기금() {
        return 전력기반기금;
    }

    public void set전력기반기금() {
        전력기반기금 = (int)(get전기요금계()*0.037/10) * 10;
    }
    
    public double get정률할인금액() {
		return 정률할인금액;
	}

	public void set정률할인금액(double 할인금액) {
		this.정률할인금액 = 할인금액;
	}

    @Override
    public String toString() {
        return String.format("기본요금 : %,d원 \n" +
                "전력량요금 : %,d원 \n" +
                "기후환경요금 : %,d원 \n" +
                "연료비조정요금 : %,d원 \n" +
                "전기요금계 : %,d원 \n" +
                "부가가치세 : %,d원 (원미만 반올림) \n" +
                "전력기반기금 : %,d원 (10원미만절사) \n" +
                "청구금액 : %,d원 (10원미만절사) \n",
                기본요금,
                (int)전력량요금,
                (int)기후환경요금,
                (int)연료비조정요금,
                (int)전기요금계,
                (int)Math.round(부가가치세),
                (int)전력기반기금,
                (int)(전기요금계 + 부가가치세 + 전력기반기금)/10 * 10) ;
    }
}