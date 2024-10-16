<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제이쿼리 연습하기</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
<script>
$(document).ready(function(){
	
	//alert("hello world");
	// 태그 셀럭터로 부르는 것  
	//$("button").click(function(){//버튼이라는 태그를 제이쿼리로 찾아서 클릭 속성을 넣기
	//	$("p").hide();
	//});
	
	$("#btn2").dblclick(function(){//버튼이라는 태그를 제이쿼리로 찾아서 더블클릭 속성을 넣기
		$("p").show();
	});
	
	// id를 부를 때는 #
	$("#btn").click(function(){
		$("#divid").hide();
	});
	
	
	//$("셀렉터").메소드(function(){ < 익명함수를 통째로 넘김
	//	$("#divid").hide();
	//});
	
	//$("#btn3").click(function(){
	//	$("#div1").fadeIn();
	//	$("#div2").fadeIn("slow");
	//	$("#div3").fadeIn(3000);
	//});

	$("#btn3").click(function(){
		$("#div1").fadeIn();
		$("#div2").fadeIn("slow");
		$("#div3").fadeIn(3000);
	});
	
	$("#btn4").click(function(){
		$("#div4").fadeOut();
		$("#div5").fadeOut("slow");
		$("#div6").fadeOut(3000);
	});
	
	$("#filep").click(function(){
		$("#panel").slideDown("slow");
		
	});
	//제이쿼리는 이미 가지고 있는 함수를 가지고 와서 사용한다.


});
</script>

<p>저는 홍길동입니다.</p>
<button id="btn2">클릭하면 글이 사라집니다.</button>
<div id="divid">
안녕하세요
</div>
<button id="btn">클릭하면 글이 사라집니다.</button>
<button id="btn3">서서히 나타나다.</button><button id="btn4">서서히 사라지다.</button>
<table>
	<tr>
	<td>
		<div id="div1" style="width:80px; height:80px; background:red; display:none;"></div>
		<br>
		<div id="div2" style="width:80px; height:80px; background:blue; display:none;"></div>
		<br>
		<div id="div3" style="width:80px; height:80px; background:yellow; display:none;"></div>
	</td>
	<td>
		<div id="div4" style="width:80px; height:80px; background:red;"></div>
		<br>
		<div id="div5" style="width:80px; height:80px; background:blue;"></div>
		<br>
		<div id="div6" style="width:80px; height:80px; background:yellow;"></div>
	</td>
	</tr>
</table>

<div id="filep" style="padding:5px;text-align:center;background:#e5eecc;border:1px solid #c3c3c3;">클릭하면 아래로 펼쳐져요</div>
<div id="panel" style="padding:50px;display:none;background:#e5eecc;">클릭하면 아래로 펼쳐져요</div>
</body>
</html>



