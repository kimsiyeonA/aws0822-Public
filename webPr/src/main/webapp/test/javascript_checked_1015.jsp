<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>체크박스 연습하기</title>
<script >
let sum = 0;// 누적합을 담는 변수(전역변수 선언)
function calc(obj){ // 인자값을 받을 매개변수를 받는다.
	
	if(obj.checked){ // 조건이 참이면 실행되니 true를 생략해도 됨 / 결과값이 true(참)이면
		sum = sum + parseInt(obj.value); //숫자형으로 바꾼다. = sum += parseInt(obj.value);
	}else{
		sum = sum - parseInt(obj.value);
	}
	document.getElementById("sumtext").value = sum;
	
}


</script>
</head>
<body>
<form>
<input type="checkbox" name="hap" value="10000" onclick="calc(this)"> 모자 1만원
<input type="checkbox" name="shose" value="30000" onclick="calc(this)"> 구두 3만원
<input type="checkbox" name="bag" value="80000" onclick="calc(this)"> 가방 8만원
</form>
지불하실 금액 <input type="text" id="sumtext" value="0" >
</body>
</html>