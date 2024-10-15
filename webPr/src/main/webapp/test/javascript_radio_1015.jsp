<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function check(){
	let alramCity = "";
	let korCity = document.getElementsByName("city"); //name으로 객체 찾기
	for(let i=0;i<korCity.length;i++){
		if(korCity[i].checked==true){ // 각 값이 체크되었는지 물어봄
			alramCity=korCity[i] // 체크되어 있으면 체크되어있는 객체를 옮겨 담는다.
		}
	}
	if(alramCity == ""){
		alert("선택한 값이 없어요");
	}else{
		alert("선택한 도시는? "+alramCity.value); // 
	}
	return	
}

</script>
</head>
<body>
<form>
<input type="radio" name="city" value="seoul" checked>서울
<input type="radio" name="city" value="busan">부산
<input type="radio" name="city" value="chunchen">춘천
<input type="button" value="클릭" onclick="check();">
</form>
</body>
</html>