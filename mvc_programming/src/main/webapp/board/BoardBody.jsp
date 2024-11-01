<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="mvc.vo.BoardVo" %>
 <% 
 BoardVo bv = (BoardVo)request.getAttribute("bv");
 // request.getAttribute();는 object로 되므로 BoardVo으로 강제 형변환 시켜준다.
//System.out.println("boardSelectOne bvbv" + bv);
/*
 String memberName="";
 if(session.getAttribute("memberName")!=null){
		out.println("<script>alert('로그인 해주세요');location.href='"+request.getContextPath()+"/member/memberLogin.aws'</script>");
	}
 */
/*  String midx="";
 if(session.getAttribute("midx")!=null){
		session.
	} */
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
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
	width:5%;
	height: 5%;
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
input[type="text"] {
    width: 70%;
    height: 100%;
    border: none;
    font-size: 1em;
    padding-left: 5px;
    display: inline;
    outline: none;
    box-sizing: border-box;
    color: black;
    background: inherit;
    text-align: center;
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
.b a{ 
	text-decoration: none;
	color: none;
	
}
 a{ 
	text-decoration: none;
	color: black;
	
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

function commentDel(cidx){
	
	let ans = confirm("삭제하시겠습니까?");
	
	if(ans==true){
		$.ajax({
			type : "get", 
			url : "<%=request.getContextPath()%>/comment/commentDelectAction.aws?cidx="+cidx, 
					// 넘기는 주소
			dataType : "json", //json타입은 문서에서 {"키값":"value값","키값2":"value2값"}
			success : function(result){//결과가 넘어와서 성공했을 때 받는 영역
				alert("commentDelete 전송 성공 테스트")
				alert(result.value);
				$.boardCommentList();
			},
			error : function(result){// 결과가 실패했을 때 받는 영역
				alert("commentDelete 전송 실패 테스트")
			} 
		});
	}
	return;
}

$.boardCommentList=function(){
	//alert("ㅁㅎㅁㅎ")
	$.ajax({
		type : "get", 
		url : "<%=request.getContextPath()%>/comment/commentList.aws?bidx=<%=bv.getBidx()%>", 
				// 넘기는 주소
		dataType : "json", //json타입은 문서에서 {"키값":"value값","키값2":"value2값"}
		success : function(result){//결과가 넘어와서 성공했을 때 받는 영역
			//alert("boardCommentList 전송 성공 테스트")
			
			var length = result.length;
			var strTr="";
			$(result).each(function(){
				
				var btnn="";
				//현재로그인사람과 댓글은 사람
				if(this.delyn=="N"){
				if(this.delyn=="N"){
					btnn="<button type='button' onclick='commentDel("+this+")'>삭제</button>";
				}
				
				
				strTr=strTr+"<tr>"+
				"<td style = \'width:10%;\'>"+ length-- +"</td>"+
				"<td style = \'width:20%;\'>"+this.cwriter+"</td>"+
				"<td style = \'width:40%;\'>"+this.ccontents+"</td>"+
				"<td style = \'width:20%;\'>"+this.writeday+"</td>"+
				"<td style = \'width:10%;\'>"+btnn+"</td></tr>"
				
			});

			var str="<table><tr>"+
			"<th style = \'width:10%;\'>번호</th>"+
			"<th style = \'width:20%;\'>작성자</th>"+
			"<th style = \'width:40%;\'>내용</th>"+
			"<th style = \'width:20%;\'>날짜</th>"+
			"<th style = \'width:10%;\'>DEL</th>"+
			"</tr>"+strTr+"</table>"

			$("#commentListView").html(str)

		},
		error : function(result){// 결과가 실패했을 때 받는 영역
			alert("boardCommentList 전송 실패 테스트")
		} 
	});

}


$(document).ready(function(){
	$.boardCommentList();
	$("#btn").click(function(){
		alert("추천버튼 클릭")
		
		$.ajax({
			type : "get", // 전송 방식을 무엇으로 할지
			url : "<%=request.getContextPath()%>/board/boardRecom.aws?bidx=<%=bv.getBidx()%>", 
					// 넘기는 주소
			dataType : "json", //json타입은 문서에서 {"키값":"value값","키값2":"value2값"}
			//data : {"memberId" : memberId},get 방식아라서 물음포 달고 가니깐 안써도 됨
			success : function(result){//결과가 넘어와서 성공했을 때 받는 영역
				//alert("길이는"+result.length);
				//alert("cnt값은"+result.cnt)
				var str = "추천("+result.recom+")";
				$("#btn").val(str);
			},
			error : function(result){// 결과가 실패했을 때 받는 영역
				alert("전송 실패 테스트")
			} 
		});
		// 에이작스에서 결과가 안들어와도 아래 코드가 실행됨
		// 기다리다가 결과가 나타남(기다리는게 비동기성)
	});
	
	$("#cmtBtn").click(function(){
		//
		
	
	
		let loginCheck = "";
		if(loginCheck=="" || loginCheck=="null" || loginCheck==null){
			let ans = confirm("로그인을 입력해주세요")//함수의 값을 참과 거짓 true, false로 나눈다.
			
			if(ans == true){
				location.href="<%=request.getContextPath() %>/member/memberLogin.aws";
			}else{
				
			}
			
			//alert("로그인을 입력해주세요")
			//location.href="<=request.getContextPath() %>/member/memberLogin.aws";
			return;
		}
		
		let cwriter  = $("#cwriter").val();
		let ccontents = $("#ccontents").val();
		
		if(cwriter == "" ){
			alert("작성자를 입력해주세요")
			$("#cwriter").focus();
			return;
		}else if(ccontents==""){
			alert("내용을 입력해주세요")
			$("#ccontents").focus();
			return;
		}//유효성 검사
		
		$.ajax({
			type : "post", // 내용을 넘기므로 Post
			url : "<%=request.getContextPath()%>/comment/commentWriteAction.aws", 
					// 넘기는 주소
			dataType : "json", //json타입은 문서에서 {"키값":"value값","키값2":"value2값"}
			data : {"cwriter" : cwriter,
					"ccontents":ccontents,
					"bidx":<%=bv.getBidx()%>,
					"midx":},
			success : function(result){//결과가 넘어와서 성공했을 때 받는 영역
	
				var str = "("+result.value+")";
				alert("길이는"+result.value);
				$.boardCommentList();
				if(result.value==1){
					$("#ccontents").val("");
				}
			},
			error : function(result){// 결과가 실패했을 때 받는 영역
				alert("전송 실패 테스트")
			} 
		});
		
	});

});


</script>
</head>
<body>
	<form name="frm">

<hr style="border: 2px solid black;">
<div>
<div>
<h3><%=bv.getSubject() %>(조회수:<%=bv.getViewcnt() %>)</h3>
<!--  onclick="location.href='request.getContextPath()/board/boardRecom.aws?bidx=bv.getBidx()'-->
 <input type="button"  id="btn" value ="추천(<%=bv.getRecom() %>)">
<%=bv.getWriter() %>(<%=bv.getWriterday().substring(0,10) %>) 
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
<div >
<%if(bv.getFilename() == null || bv.getFilename().equals("")){}else{ %>
	<img src="<%=request.getContextPath()%>/images/<%=bv.getFilename()%>">
	<%=bv.getFilename()%>
	
	<p>
	<a href="<%=request.getContextPath() %>/board/BoardDowmload.aws?filename=<%=bv.getFilename() %>">
	<button type = "button" class="B"  name="btn2" >
	첨부파일 다운로드	
	</button>
	</a>
	</p>
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
	<a href="<%=request.getContextPath() %>/board/BoardDelete.aws?bidx=<%=bv.getBidx() %>">
		<button type = "button" class="B" name="btn2" onclick="check3();">
		삭제
		</button>
	</a>
	<a href="<%=request.getContextPath() %>/board/BoardReply.aws?bidx=<%=bv.getBidx() %>">
	<button type = "button" class="B" name="btn2" onclick="check4();">
	답변
	</button>
	</a>
	<!--bv.getOriginbidx() > bidx 만넘겨서 컨트롤러에서 값들을 받아오면서 간단하게 만들기로 함  -->
	
	<a href="<%=request.getContextPath() %>/board/boardList.aws">
	<button type = "button" class="B" name="btn2" onclick="check5();">
	목록
	</button>
	</a>
</div>
<br>
<div class="C">
	<div class="C a">
	<input type = "text" id="cwriter" name="cwriter" value=
	<%
	 if(session.getAttribute("memberName") == null){
		 out.print("비회원");
	 }else{
		 out.print(session.getAttribute("memberName"));  
	}
	%>
	readonly="readonly">
	</div>
	<div class="C b">
	<input type = "text" id="ccontents" name="ccontents" style = "width:70%; height: 2rem; margin: 0; text-align: left;">
	<button type = "button" id="cmtBtn" class="C" name="btn2" >
	댓글쓰기
	</button>
	</div>
</div>
<br>
<div class="D">
<div id="commentListView">

</div>

</div>
</form>
</body>
</html>