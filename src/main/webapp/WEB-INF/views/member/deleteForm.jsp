<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>회원탈퇴</title>

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

	
  <form:form modelAttribute="userVO" class="login-form" name="login-form" method="post" action="delete">

     <input name='userId' type="hidden"  value="${userId}" /> 
     
     <p align="left"><span class="error"><form:errors path="passwd" /></span></p>
     <input name='passwd' type="password" placeholder="password" required />
     
     <button type="submit">확인</button>
     
  </form:form>
</div></div>
</body>

</html>