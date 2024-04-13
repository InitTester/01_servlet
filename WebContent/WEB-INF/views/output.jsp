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
				① 기본요금 : 7,300 원 (3단계 단가)<br/>
				② 전력량요금 : 82,285원 (1~3단계 요금의 합계, 원미만절사) <br/>
				<ul>
					<li>1단계 : 200kWh × 120.0원 = 24,000원</li>
					<li>2단계 : 200kWh × 214.6원 = 42,920원</li>
					<li>3단계 : 50kWh × 307.3원 = 15,365원</li>
				</ul>
				<br/>
				③ 기후환경요금 : 450kWh × 9원 = 4,050원 <br/>
				④ 연료비조정요금 : 450kWh × 5원 = 2,250원 <br/>
				⑤ 전기요금계 : 93,635원 = 7,300원(①) + 82,285원(②) + 4,050원(③) <br/>
				⑥ 부가가치세 : 93,635원(④) × 10% = 9,364원 (원미만 반올림)<br/>
				⑦ 전력기반기금 93,635원(④) × 3.7% = 3,460원 (10원미만절사)<br/>
				⑧ 할인금액 : 0원 <br/>
				⑨ 청구금액 : 95,885원(⑤) + 9,589원(⑥) + 3,540원(⑦) - 0원(⑧) = 109,010원 (10원미만절사)
			</td>
		</tr>
	</table>
</body>
</html>