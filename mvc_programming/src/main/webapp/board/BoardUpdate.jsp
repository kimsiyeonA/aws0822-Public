<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	if (fm.boardtitle.value == ""){
		alert("제목을 입력해주세요");
		fm.boardtitle.focus(); 
		return;
	}else if(fm.boardbody.value == ""){
		alert("내용을 입력해주세요");
		fm.boardbody.focus(); 
		return;
	}else if(fm.writer.value == ""){
		alert("작성자를 입력해주세요");
		fm.writer.focus(); 
		return;
	}else if(fm.writer.value != "" && fm.boardbody.value != ""&& fm.writer.value != ""){
		alert("저장하시겠습니까");
		return;
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
	<table>
	<tr>
		<td >제목</td>
		<td> <input type = "text" name="boardtitle" style = "width:700px; height: 2rem;"></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><input type = "text"  name="boardbody" style = "width:700px; height: 15rem;" ></td>
	</tr>
	<tr>
		<td >작성자</td>
		<td class = "A">
		<input type = "text" name="writer" maxlength = "10" style = "width:100px; height: 1rem;" >
		</td>
	</tr>
	<tr>
		<td class = "idcolor">비밀번호</td>
		<td class = "A">
		<input type = "text"  name="boardpwd" maxlength = "50" style = "width:100px; height: 1rem;" >
		</td>
	</tr>
	<tr>
		<td>첨부파일</td>
		<td class = "A">
		<input type = "file">
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