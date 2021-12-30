<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>비밀번호 변경</title>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/joinStyle.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.9.2/i18n/jquery.ui.datepicker-ko.min.js"></script>

<script type="text/javascript">


function Check_Form() 
{
	if (editpassform.passwd.value != editpassform.passwd_re.value){
		alert("패스워드가 일치하지 않습니다.");
		joinForm.passwd.focus();
		return false;
	}
	
	return true;
}

</script>

</head>

<body onload="checkErrCode()">
<form modelAttribute="userVO" id="editpassform" name="editpassform" method=post  Action='editPass' onsubmit='return Check_Form()' >

<table width="600px" cellpadding="0" cellspacing="0" align=center>
  <tr><td><span class="member">비밀번호 변경</span><span class="join">* ${userVO.name}님</span>

  <%if( request.getAttribute("errMsg") != null) { %>
		  <span class="ecomment">|* ${errMsg} </span></td></tr>
  <% } else{ %>
	    <span class="comment">| * 변경하실 비밀번호를 입력해주세요.</span></td></tr>
  <% } %>

</table>

<table width="600px" cellpadding="0" cellspacing="0" align=center class="table01">

<colgroup>
  <col width="150">
  <col width="*">
</colgroup>

 <input type="hidden" name="userId" id="userId" value="${userVO.userId}">
  
  <tr>
    <td class="tle">새 비밀번호</td>
	<td class="cont"><input class=ed type=password name="passwd" size=20 maxlength="20" required  placeholder="password-1"></td>
  </tr>
  <tr>
    <td class="tle">비밀번호 확인</td>
	<td class="cont"><input class=ed type=password name="passwd_re" size=20 maxlength=20 required placeholder="password-2"></td>
  </tr>
  <tr>
  
</table>

<div align=center class="mg_t20"><input type="submit" class="myButton" value="확인"  ></div>
</form>

</body>
</html>
