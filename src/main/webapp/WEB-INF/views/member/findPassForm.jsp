<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>비밀번호 찾기</title>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/joinStyle.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.9.2/i18n/jquery.ui.datepicker-ko.min.js"></script>

<script type="text/javascript">

function Check_Form() 
{
	if (findIdform.userId.value.length < 1){
		joinForm.userId.focus();
		return false;
	}
	if (findIdform.email.value.length < 1){
		joinForm.email.focus();
		return false;
	}

	return true;
}

</script>

</head>

<body onload="checkErrCode()">
<form modelAttribute="userVO" id="findpassform" name="findpassform" method=post  Action='findPass' onsubmit='return Check_Form()' >

<table width="600px" cellpadding="0" cellspacing="0" align=center>
   <tr><td><span class="member">비밀번호 찾기</span><span class="join">minseok</span>
   
   <%if( request.getAttribute("errMsg") != null) { %>
		  <span class="ecomment">|* ${errMsg} </span></td></tr>
  <% } else{ %>
	    <span class="comment">| * 회원정보를 입력해주세요.</span></td></tr>
  <% } %>
  
</table>

<table width="600px" cellpadding="0" cellspacing="0" align=center class="table01">
<colgroup>
  <col width="150">
  <col width="*">
</colgroup>
  <tr>
    <td class="tle">아이디</td>
	<td class="cont">
	<div class="col-md-4">
	<input  maxlength=20 size=20 id='reg_mb_Id' name="userId" class="ed" required/>
	</div>
	</td>
  </tr>
  <tr>
    <td class="tle">E-mail</td>
	<td class="cont">
	<input class=ed type=text id='reg_mb_email' name='email' size=38 maxlength=100  required  value=''>
	<span id='msg_mb_email'></span>
	</td>
  </tr>
</table>

<div align=center class="mg_t20"><input type="submit" class="myButton" value="찾기"  ></div>
</form>

</body>