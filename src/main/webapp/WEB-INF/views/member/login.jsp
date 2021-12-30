<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>회원인증</title>

<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function checkErrCode(){
	var msgCode = "${msgCode}";
	if( msgCode != ""){
		alert(msgCode);
	}
}
</script>

</head>

<body onload="checkErrCode()">
<div class="login-page">
<div class="form">
  <form:form modelAttribute="userVO" class="login-form" name="login-form" method="post" action="login">

     <p align="left"><span class="error"><form:errors path="userId" /></span><br /></p>
     <input name='userId' type="text" placeholder="username" required="true" /> 
     
     <p align="left"><span class="error"><form:errors path="passwd" /></span></p>
     <input name='passwd' type="password" placeholder="password" required="true"/>
     
     <button type="submit">login</button>
     <p class="message"><ul id="ullog">
  	<li id="lilogb"><a href="<%=request.getContextPath()%>/member/join">회원가입/</a></li>
  	<li id="lilog"> <a href="<%=request.getContextPath()%>/member/findId">아이디/</a> 
    				<a href="<%=request.getContextPath()%>/member/findPass">비밀번호 찾기</a></li>
     </ul></p>
     
  </form:form>
</div></div>
</body>
</html>
