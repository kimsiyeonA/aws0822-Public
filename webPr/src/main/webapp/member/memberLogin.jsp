<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE HTML>
<HTML>
 <HEAD>

  <TITLE> MemberLogin </TITLE>
  <style>
  header {
	width:100%;
	height: 15%; 
	text-align: center;
	background-color : #f7edd2;
}
nav{
	width: 15%; 
	height: 50%; 
	float: left; 
	background-color : #dce8fa;
}
section { 

	width: 70%; 
	height: 50%; 
	float: left; 
	background-color : #ddfadc; 
}
aside { 
	width: 15%; 
	height: 50%; 
	float: left; 
	background-color : #f7dcfa; 
}
footer { 
	width: 100%; 
	height: 15%; 
	clear: both; 
	text-align: center;
	background-color : #ffded4; 
}
  </style>
 </HEAD>

 <BODY>

 <header> 로그인 </header>
 <nav></nav>
 <section>
 <article>

	<table>
	<tr>
		<td>아이디</td>
		<td><input type = " text" name = "memberid" maxlength = "50" style = "width:150px;"></td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td><input type = "password" name = "memberPwd" maxlength = "50" style = "width:150px;"></td>
	</tr>
	<tr>
		<td colspan = "2" style = "text-align : center;">
		<input type = "submit" name = "btn" value = "확인">
		</td>
	</tr>
	</table>
	<a href = "" > 아이디 찾기</a> |
	<a href = "" > 비밀번호 찾기</a> |
	<a href = "" > 회원가입 </a>

 </article>
 </section>
 <aside>

 </aside>

 <footer>

 </footer>

 </BODY>
</HTML>
