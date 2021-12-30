<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>아이디 찾기</title>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/joinStyle.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.9.2/i18n/jquery.ui.datepicker-ko.min.js"></script>

<script type="text/javascript">

function login() {
	location.href="/bbs/member/login";
}
</script>
</head>

<body>
<table width="600px" cellpadding="0" cellspacing="0" align=center>
  <tr><td><span class="member">아이디 찾기</span><span class="join">somcoco</span>
</table>

<table width="600px" cellpadding="0" cellspacing="0" align=center class="table01">

<colgroup>
  <col width="150">
  <col width="*">
</colgroup>
  
  <tr>
    <td class="tle">${userVO.name} 님의 아이디는  ${userVO.userId}  입니다.</td>
  </tr>
  
</table>

<div align=center class="mg_t20"><input type="button" class="myButton" value="로그인" onclick="login()"></div>

</body>
</html>
