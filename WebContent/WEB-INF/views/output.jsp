<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*, java.text.*" %>
<%
	String measureDate = request.getParameter("measureDate");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>전기요금 계산</title>
<style>
	body { font-size:11px;}
	table { border-width: thin; }
</style>
</head>
<body>
	<table width="600" align="center" border="1">
		<tr>
			<th colspan="4">전기요금 계산 결과</th>
		</tr>
		<tr>
			<td colspan="2"><b>사용일</b></td>
			<td colspan="2">${measureDate}</td>
		</tr>
		<tr>
			<td colspan="2"><b>사용량(KWh)</b></td>
			<td colspan="2">${eAmount} KWh</td>
		</tr>
		<tr>
			<td colspan="2"> <b>정액할인</b> </td>
			<td colspan="2">0 원</td>
		</tr>
		<tr>
			<td colspan="2"> <b>정액할인 내용</b> </td>
			<td colspan="2">0 원 할인 (월 1만6천원, 여름 2만원 한도)</td>
		</tr>
		<%--
		<tr>
			<td colspan="2" align="center"> <b>정률할인(30%)</b> </td>
			<td colspan="2"></td>
		</tr>
		 --%>
		<tr>
			<td colspan="4" align="center">
				<b> 요금 계산  </b>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				① 기본요금 : ${basic} <br/>
				② 전력량요금 : ${er} <br/>
				<ul>
				<%
				    double[] items = {120.0d, 214.6d, 307.3d};
				    int step = (Integer)request.getAttribute("step");
				    int eAmount = Integer.valueOf((String)request.getAttribute("eAmount"));
				
				    // 각 구간별로 전력량 요금을 계산하고 출력
				    for(int i = 0; i < step; i++) {
				        int 계산량 = Math.min(eAmount, 200); 
				        eAmount -= 계산량; // 사용량 갱신
				%>
			        <li><%= (i + 1) + "단계 : " + 계산량 + "kWh × " + items[i] + "원 = " + String.format("%,.0f", (계산량 * items[i])) + "원" %></li>
				<%
				    }
				%>
				</ul>
				③ 기후환경요금 : ${cef} <br/>
				④ 연료비조정요금 : ${fcaf} <br/>
				⑤ 전기요금계 : ${ebm} <br/>
				⑥ 부가가치세 : ${vat} <br/>
				⑦ 전력기반기금 : ${eif} <br/>
				⑧ 할인금액 : 0원 <br/>
				⑨ 청구금액 : ${ba}
			</td>
		</tr>
	</table>
</body>
</html>