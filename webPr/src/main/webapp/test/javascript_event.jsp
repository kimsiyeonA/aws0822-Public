<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 연습하기</title>
</head>
<body onload="alert('모든 객체가 생성된 후에 로딩됩니다.')">
<script>
window.onload=function calculate(){
	alert('모든 객체가 생성된 후에 로딩됩니다.123');
}


function calculate(){

	let exp = document.getElementById("exp"); // exp input 객체 찾기
	let result = document.getElementById("result");// result input 객체 찾기
	
	result.value = eval(exp.value);
	//문자를 숫자로 바꿔셔 계산하는 함수eval(); //오작동이 많다 = 사용 지양 // 서버에 있는 걸로 계산해서 바로 됨
	return;
}
/*
function aaa(){
	alert("오른쪽 버튼은 사용금지입니다.")
	return false;
}

document.oncontextmenu = aaa;
*/
function changeImage(){
	//alert("함수에 들어왔나요?")
	let sel = document.getElementById("sel"); //select 객체 찾기
	//alert("객체가 생성되었나요? "+sel);
	let img = document.getElementById("myImg"); //이미지 객체 찾기
	//alert("객체가 생성되었나요? "+img);
	img.onload = function(){// 이미지가 로딩이 되면 익명함수 동작
		let mySpan = document.getElementById("mySpan");
		mySpan.innerHTML=img.width+"x"+img.height;
	}

	let index = sel.selectedIndex;
	img.src = sel.options[index].value;
}

function chk(){
	// alert("함수안에 들어옴")
	let nm = document.getElementById("nm");
	// alert("nm 확인 "+nm)
	
	if(nm.value==""){
		alert("입력안하면 포커스로 돌아옴");
	    setTimeout(() => {
            nm.focus();
        }, 0);
	}
	
	return;
}
</script>
<form>
<input type="text" id="exp" value="">
<br>
<input type="text" id="result">
<br>
<input type="button" value="계산하기" onclick="calculate();">
<br>
<hr>
<p>
마우스 오른쪽 버튼 클릭하기 해보는 테스트
</p>
<hr>
<form>
<select id="sel" onchange="changeImage();">
<option value="../img/apple.png">사과</option>
<option value="../img/banana.png">바나나</option>
<option value="../img/strawberry.png">딸기</option>
</select>

<span id="mySpan">이미지 크기</span>
<p> 
<img id="myImg" src="../img/apple.png">
</p>

<input type="text" name="nm" id="nm" onblur="chk();"><!-- 그 위치를 떠날때 발생하는 이벤트 -->
</form>
</form>
</body>
</html>


<!--  
코드를 보면 `alert()`을 사용하여 여러 메시지를 표시하고 있는데, 알림창에서 루프가 발생하는 이유는 **`nm.focus()`로 인해 포커스가 다시 입력 필드로 이동**하는 상황에서 `alert()`이 연속적으로 실행되기 때문입니다.

### 문제 원인:
- `nm.focus()`로 인해 입력 필드로 포커스가 이동하고, 만약 사용자가 `alert()` 창을 닫고도 입력 필드에 값을 입력하지 않으면 다시 `nm.focus()`가 실행되면서 포커스가 입력 필드로 돌아가게 됩니다.
- 그 상태에서 또 다시 `alert()`이 뜨게 되니, 사용자가 값을 입력하지 않고 계속 `alert()` 창을 닫기만 한다면 이 과정이 반복되어 루프처럼 보이게 됩니다.

### 해결 방법:
`alert()`을 남발하지 않도록 하거나, 특정 동작이 발생하는 시점에서 한 번만 실행되도록 할 수 있습니다. 예를 들어, 한 번의 `alert()`만 나타나도록 하거나, 조건을 명확히 하고 `alert()`을 제거하는 방법이 있습니다.

### 수정된 코드 (알림 최소화):
```javascript
function chk() {
    let nm = document.getElementById("nm");

    if (nm.value == "") {
        alert("이름을 입력하세요."); // 값이 비어 있을 때 한 번만 알림 표시
        nm.focus(); // 입력 필드에 포커스를 이동
    }

    return;
}
```

### 설명:
- `alert()`는 값이 비어 있을 때 한 번만 호출됩니다.
- `nm.focus()`가 실행되면 포커스가 입력 필드로 돌아가지만, 더 이상 추가적인 `alert()`이 발생하지 않으므로 반복적인 알림이 뜨는 현상이 사라집니다.

이렇게 수정하면 루프처럼 보이는 현상 없이 동작이 정상적으로 이루어질 것입니다.
-->