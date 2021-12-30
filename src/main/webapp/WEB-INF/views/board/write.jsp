<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>새 글 쓰기</title>

<link href="${pageContext.request.contextPath}/resources/css/board.css" rel="stylesheet" type="text/css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
function formCheck() {
		if (writeForm.title.value.length < 1){
			alert("제목을 입력하세요.");
			writeForm.title.focus();
		return false;
		}
		if (writeForm.content.value.length < 1){
				alert("내용을 입력하세요.");
				writeForm.content.focus();
			return false;
		}
		return true;
		}
</script>
</head>

<body>
<div class="wrapper">
<h3>새 글 쓰기</h3>
<form name="writeForm" action="write" method="post" onsubmit="return formCheck()" enctype="multipart/form-data">
<table class="boardWrite">
<tr>
<th><label for="subject">제목</label></th>
<td>
<input type="text" id="title" name="title" class="boardSubject"/>
<input type="hidden" id="writer" name="writer" value="${username}" />  

<input type="hidden" id="writerId" name="writerId" value="${userId}" />
</td>
</tr>
<tr>
<th><label for="content">내용</label></th>
<td><textarea id="content" name="content" class="boardContent"></textarea></td>
</tr>
<tr>
<th><label for="file">파일</label></th>
<td><input type="file" id="file" name="file" multiple />  
<span class="date">&nbsp;&nbsp;*&nbsp;임의로 파일명이 변경될 수 있습니다.</span></td>
</tr>
</table>
<br />
<input type="reset" value="재작성" class="writeBt"/>
<input type="submit" value="확인" class="writeBt"/>
</form>
</div>
</body>
</html>
