<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@page import="mvc.vo.BoardVo" %>
     <%@ include file="/common/loginCheck.jsp"%>
 <% 

 BoardVo bv = (BoardVo)request.getAttribute("bv");
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<style>
form, table{
	margin-left:auto;
	margin-right:auto;
	text-align: center;
}
td.A{
text-align: left;
}
table{
	width:800px;
	 border : 1px solid #1d435c;
	 border-collapse : collapse; 
     text-align: center;
	 
	 /*table과 td, th 선 합치기*/
	}
	td, th {
	 border : 1px dotted #1f93e0;
	 padding : 5px;
	 
	}
div{
	margin: 20px 0 ;

}
button{
	background: black;
	color : white;
}
</style>
<script>

function check1(){

	var fm = document.frm;
	
	if (fm.subject.value == ""){
		alert("제목을 입력해주세요");
		fm.subject.focus(); 
		return;
	}else if(fm.contents.value == ""){
		alert("내용을 입력해주세요");
		fm.contents.focus(); 
		return;
	}else if(fm.writer.value == ""){
		alert("작성자를 입력해주세요");
		fm.writer.focus(); 
		return;
	}else if(fm.password.value  == ""){
		alert("비밀번호를 입력해주세요");
		fm.password.focus(); 
		return;
	}
	
	let ans = confirm("저장하시겠습니까?")
	
	if(ans==true){
		fm.action="<%=request.getContextPath()%>/board/boardModifyAction.aws";
		fm.method="post"
		fm.submit();
	}

	return;
}

function check2(){

	alert("글수정을 취소하시겠습니까?");

	return; 
}
</script>

</head>
<body>
<hr>
<section>
	<form name="frm">
	<input type="hidden" name="bidx" value="<%=bv.getBidx() %>">
	<table>
	<tr>
		<td >제목</td>
		<td> <input type = "text" name="subject" style = "width:700px; height: 2rem;" value="<%=bv.getSubject() %>"></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea  name="contents" style = "width:700px; height: 15rem;" ><%=bv.getContents() %></textarea></td>
	</tr>
	<tr>
		<td >작성자</td>
		<td class = "A">
		<input type = "text" name="writer" maxlength = "10" style = "width:100px; height: 1rem;" value="<%=bv.getWriter()%>">
		</td>
	</tr>
	<tr>
		<td class = "idcolor">비밀번호</td>
		<td class = "A">
		<input type = "password"  name="password" maxlength = "50" style = "width:100px; height: 1rem;" >
		</td>
	</tr>
	<tr>
		<td>첨부파일</td>
		<td class = "A">
		<input type = "file" name="filename" >
		</td>
	</tr>
	</table>
	<div>
	<button type = "button" name="btn1" onclick="check1();">
	저장
	</button>
	<button type = "button" name="btn2" onclick="check2();">
	취소
	</button>
	</div>
	</form>
 </section>
</body>
</html>