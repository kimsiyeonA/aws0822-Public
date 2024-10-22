<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="mvc.vo.BoardVo" %>
 <% 
 BoardVo bv = (BoardVo)request.getAttribute("bv");
 // request.getAttribute();는 object로 되므로 BoardVo으로 강제 형변환 시켜준다.
//System.out.println("boardSelectOne bvbv" + bv);
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
<style>
html{
	width:100%;
	height: 100%;
}
body{
	width:100%;
	height: 100%;
}

button.A{
	border:none;
	background: none;
}
button.B{
	background: black;
	color : white;
}
img{
	width:50px;
	height: 50px;
}
div.B{
	text-align: right;
	padding-right: 20px;

}
div.C{
    display:block;
	width:90%;
	margin: 0 auto;

}
button.C{
	width:20%; 
	height: 2rem; 
	border-style: none;
	background: black;
	color : white;
	margin-left: 9%;
	float: right;
}
div.C.a{
	width:20%; 
	height: 2rem; 
    display:inline-block;
	padding: 5px 0;
	background: gray;
	color : white;
	text-align: center;
}
div.C.b{
    width: 100%;
    display:inline-block;
	border: 1px solid black;

}
input[type="text"]{
      width:70%;
      height:100%;
      border:none;
      font-size:1em;
      padding-left: 5px;
      font-style: oblique;
      display:inline;
      outline:none;
      box-sizing: border-box;
      color:black;
    }

div.D{
    display:block;
	width:90%;
	margin: 0 auto;

}
table{
	 width:100%;
	 border : 1px solid #1d435c;
	 border-collapse : collapse; 
     text-align: center;
	}
	td, th {
	 border : 1px dotted #1f93e0;
	 padding : 5px;
	 
	}
a{ 
	text-decoration: none;
	color: none;
	
}
</style>

<script>

function check1(){

	alert("파일을 저장하시겠습니까?");
	return; 
}

function check2(){

	alert("글수정페이지로 이동합니다.");
	return; 
	
}
function check3(){

	alert("글삭제페이지로 이동합니다.");
	return; 
	
}
function check4(){

	alert("글답변페이지로 이동합니다.");
	return; 
	
}
function check5(){

	alert("글목록페이지로 이동합니다.");
	return; 
	
}
function check6(){

	var fm = document.frm;

	
	if (fm.comment.value == ""){
		alert("댓글을 입력해주세요");
		fm.comment.focus(); 
		return;
	}else if(fm.comment.value != ""){
		alert("댓글을 저장하시겠습니까?");
		return;
	}
	
}
</script>
</head>
<body>
	<form name="frm">

<hr style="border: 2px solid black;">
<div>
<div>
<h3><%=bv.getSubject() %>(조회수:<%=bv.getViewcnt() %>)</h3>
<%=bv.getWriter() %>(<%=bv.getWriterday() %>)
</div>
<hr>
<div>
<!--  link : <a href="#">http://www.naver.com/</a>>-->
<p>

<%=bv.getContents() %>
<!--블록 코딩을 이용해 센서피아노를 만드는 수업입니다. 
선생님 설명을 따라 코딩값을 입력하고, 빨간 버튼을 누르자 스피커에서 음악이 흘러나옵니다. (인터뷰) 현은진/학성여고 3학년 '실제 피아노는 건반도 많고 손을...-->
</p>
</div>
<hr>
<div>
<%if(bv.getFilename() != null){ %>
	<button type = "button" class="A" name="btn2" onclick="check1();">
	<img src = "<%=bv.getFilename()%>">
	</button>
<%} %>
</div>
</div>
<hr style="border: 2px solid black;">


<div class="B">
	<a href="<%=request.getContextPath() %>/board/BoardUpdate.aws?bidx=<%=bv.getBidx() %>">
	<button type = "button" class="B"  name="btn2" onclick="check2();">
	수정
	</button>
	</a>
	
	<button type = "button" class="B" name="btn2" onclick="check3();">
	삭제
	</button>
	<button type = "button" class="B" name="btn2" onclick="check4();">
	답변
	</button>
	<button type = "button" class="B" name="btn2" onclick="check5();">
	목록
	</button>
</div>
<br>
<div class="C">
	<div class="C a">
	admin
	</div>
	<div class="C b">
	<input type = "text"  name="comment" style = "width:70%; height: 2rem; margin: 0;">
	<button type = "button" class="C" name="btn2" onclick="check6();" >
	댓글쓰기
	</button>
	</div>
</div>
<br>
<div class="D">
<table>
	<thead>
	<tr>
		<th style = "width:10%;">번호</th>
		<th style = "width:20%;">작성자</th>
		<th style = "width:40%;">내용</th>
		<th style = "width:20%;">날짜</th>
		<th style = "width:10%;">DEL</th>
	</tr>
	</thead>
	<tbody>
	</tbody>
</table>

</div>
</form>
</body>
</html>