<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<h3>장애학생들을 위한 특별한 피아노(조회수:7)</h3>
관리자(2024-10-12)
</div>
<hr>
<div>
link : <a href="#">http://www.naver.com/</a>
<p>
블록 코딩을 이용해 센서피아노를 만드는 수업입니다. 
선생님 설명을 따라 코딩값을 입력하고, 빨간 버튼을 누르자 스피커에서 음악이 흘러나옵니다. (인터뷰) 현은진/학성여고 3학년 '실제 피아노는 건반도 많고 손을...
</p>
</div>
<hr>
<div>
	<button type = "button" class="A" name="btn2" onclick="check1();">
	<img src = "../img/save.png">
	</button>
</div>
</div>
<hr style="border: 2px solid black;">


<div class="B">
	<button type = "button" class="B"  name="btn2" onclick="check2();">
	수정
	</button>
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