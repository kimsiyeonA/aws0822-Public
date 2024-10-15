<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE> 회원가입 페이지 </TITLE>
  <link href="../css/memberJoinStyle.css" type="text/css" rel="stylesheet">
  <!-- 상위폴더에서 css > style 폴더 / 루트로 부터 나타내는 폴더 : 절대경로/css/style.css-->
<script>
//alert("test");
// 버튼을 눌럿을 때 check 함수 사용

// Document 객체가 내장되어 있음 > document
function check3(){
	//alert("test1");
	var fm = document.frm2;
	//alert(document.frm2.a.value);
	//alert("test2");
	
	const email = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$/i;
	
	//alert(email.test("hello5@email.com"));
	
	if (fm.memberid.value == ""){
		alert("아이디를 입력해주세요");
		fm.memberid.focus(); // 팝업창이 키고 깜빡거리게 
		return;
	}else if(fm.memberpwd.value == ""){
		alert("비밀번호를 입력해주세요");
		fm.memberpwd.focus(); 
		return;
	}else if(fm.memberpwd2.value == ""){
		alert("비밀번호 확인을 입력해주세요");
		fm.memberpwd2.focus(); 
		return;
	}else if(fm.memberpwd.value != fm.memberpwd2.value){
		alert("비밀번호가 일치하지 않습니다.");
		fm.memberpwd2.value = "";
		fm.memberpwd2.focus(); 
		return;
	}else if(fm.membername.value == ""){
		alert("이름을 입력해주세요");
		fm.membername.focus(); 
		return;
	}else if(fm.memberemail.value == ""){
		alert("이메일을 입력해주세요");
		fm.memberemail.focus(); 
		return;
	}else if(email.test(fm.memberemail.value) == false){
		alert("이메일 형식이 올바르지 않습니다.");
		fm.memberemail.value = "";
		fm.memberemail.focus(); 
		return;
	}else if(fm.memberphone.value == ""){
		alert("연락처를 입력해주세요");
		fm.memberphone.focus(); 
		return;
	}else if(fm.memberbirth.value == ""){
		alert("생년월일을 입력해주세요");
		fm.memberbirth.focus(); 
		return;
	}else if(hobbyCheck() == false){
		alert("취미를 한개이상 선택해주세요.");
		return;
	}
	
	
	
	var ans = confirm("저장하시겠습니까?")
	
	if(ans == true){
		//alert("이동할 정보 등록할 차례입니다.")
		fm.action="<%=request.getContextPath()%>/member/memberJoinAction.jsp" ;
		fm.method="post";
		fm.submit();
	}

	return; // 리턴에 값을 안쓰면 그냥 멈춤
}

function hobbyCheck(){
	var arr = document.frm2.memberhobby; // 문서 객체 안에 폼객체 안에 input 객체 선언
	var flag = false;					 // 체크 유무 초기 값 false tjsdjs
	
	for(var i = 0 ; i < arr.length; i++){// 선택한 여러값을 반복해서 출력
		if(arr[i].checked == true){		 // 하나라도 선택했다면 true값 리턴
			flag = true;
			break;						 // 
		}	
	}
	
	/*
	if(!flag){
		alert("취미를 한개이상 선택해주세요.");
		return false;
	}
	*/
	return flag;
}


//check();
</script>
 </HEAD>

 <BODY>
 <header><a href ="./memberJoin.jsp" style ="text-decoration : none"> 회원가입 페이지 </a></header>
 
 <nav> <a href ="./memberLogin.jsp"> 회원 로그인 가기 </a></nav>
  <!--<a href ="./member.jsp"> 회원  가기 </a>-->
 
 <section>

	<form name="frm2">
	<!-- "frm2"는 객체이름 -->
	<table border = 1>
	<tr>
		<td class = "idcolor">아이디</td>
		<td>
		<input type = "text" name="memberid" maxlength = "50" style = "width:300px;"   placeholder ="아이디를 입력하세요" ></td>
		 <!--<input type = "text" name="memberid1" maxlength = "50" style = "width:300px;"   placeholder ="아이디를 입력하세요" ></td>-->
	</tr>
	<tr>
		<td class = "idcolor">비밀번호</td>
		<td><input type = "password" name = "memberpwd" maxlength = "50" style = "width:150px;"></td>
	</tr>
	<tr>
		<td>비밀번호 확인</td>
		<td><input type = "password" name = "memberpwd2" maxlength = "50" style = "width:150px;"></td>
	</tr>
	<tr>
		<td id = "name">이름</td>
		<td><input type = "text" name = "membername" maxlength = "50" style = "width:300px;"></td>
	</tr>
	<tr>
		<td>이메일</td>
		<td><input type = "email" name = "memberemail" maxlength = "50" style = "width:300px;"></td>
	</tr>
	<tr>
		<td>연락처</td>
		<td><input type = "tel" name = "memberphone" maxlength = "50" style = "width:300px;"></td>
	</tr>
	<tr>
		<td>주소</td>
		<td>
		<select name = "memberaddr" style = "width:100px">
		<option value = "서울" > 서울 </option>
		<option value = "대전" selected > 대전 </option>
		<option value = "부산" > 부산 </option>
		<option value = "인천" > 인천 </option>
		</td>
		
	</tr>
	<tr>
		<td>성별</td>
		<td>
		<input type = "radio" name = "membergender" value = "M"><label for = "select1">남성</label>
		<input type = "radio" name = "membergender" value = "F" checked><label for = "select1">여성</label>
		 <!--input type = "radio" name = "membergender" value = "여성" checked>여성-->
		</td>
	</tr>
	<tr>
		<td>생년월일</td>
		<td>
		<input type = "date" name = "memberbirth" maxlength= "8" style = "width:150px;">
		예) 20240101
		</td>
	</tr>
	<tr>
		<td>취미</td>
		<td>
		<input type = "checkbox" name = "memberhobby" id = "check" value = "야구"> <label for = "야구">야구</label>
		<input type = "checkbox" name = "memberhobby" id = "check" value = "축구"> <label for = "축구">축구</label>
		<input type = "checkbox" name = "memberhobby" id = "check" value = "농구"> <label for = "농구">농구</label>
		</td>
	</tr>
	<tr>
	<td colspan = "2" style = "text-align : center;">
	<button type = "button" name="btn2" onclick="check3();">
	저장하기
	</button>
	<input type = "reset" name = "btn" value = "초기화">
	</td>
	</tr>
	</table>
	</form>
 </section>

 <aside>

 </aside>

 <footer>

 <br>
 made by ***
</footer>
  
 </BODY>
</HTML>
