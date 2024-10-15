<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BOM객체로 타이머 사용하기</title>
</head>
<body>
<img id="img" src="../img/strawberry.png" onmouseover="startTimer(2000);"
onmouseout="cancelTimer();">

<button onclick="window.open('./javascript_size_1015.jsp','test','width=300,height=300')">크기 조절</button>

<script type="text/javascript">
let timerID = null;
function startTimer(time){
	timerID = setTimeout("load('http://www.naver.com')",time);
}
function load(url){
	window.document.location.href = url;//이동한다.
}
function cancelTimer(){
	clearTimeout(timerID);
}

</script>

</body>
</html>