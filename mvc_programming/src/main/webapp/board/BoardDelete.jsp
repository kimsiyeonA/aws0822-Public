<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
String bidx = (String)request.getAttribute("bidx");
 
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글삭제</title>
<style>
form, table{
	margin-left:auto;
	margin-right:auto;
	text-align: center;
}
td.A{
text-align: center;
}
table{
	width:80%;
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
	
	if (fm.boarddelete.value == ""){
		alert("비밀번호를 입력해주세요");
		fm.boarddelete.focus(); 
		return;
	}
	
	let ans = confirm("삭제하시겠습니까");
	
	if(ans == true){
		fm.action="<%=request.getContextPath()%>/board/BoardDelectAction.aws";
		fm.method="post";
		fm.submit();
	}
	return;
}

function check2(){

	alert("삭제를 취소하시겠습니까?");

	return; 
}
</script>

</head>
<body>
<hr>
<section>
	<form name="frm">
	<input type="hidden" name="bidx" value="<%=bidx %>" >
	<table>
	<tr>
		<td >비밀번호</td>
		<td class = "A">
		<input type = "password"  name="password" maxlength = "50" style = "width:200px; height: 1rem;" >
		</td>
	</tr>
	
	</table>
	<div>
	<button type = "button" name="btn1" onclick="check1();">
	저장
	</button>
	<a href="#" onclick="history.back();" >
	<button type = "button" name="btn2" >
	취소
	</button>
	</a>
	</div>
	</form>
 </section>
</body>
</html>