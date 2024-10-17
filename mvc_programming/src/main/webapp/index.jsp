<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!-- 자바 언어를 쓰겟다 -->
    
 <%
 
 // System.out.println("안녕하세요");
 // 콘솔창에서 나타남
 // out.println("웹페이지에서 안녕하세요");
 // 웹페이지에서 작성
 
   String msg = "";
   if(session.getAttribute("msg") != null) {
      msg = (String)session.getAttribute("msg");   
   }

 session.setAttribute("msg","");
 
 int midx = 0;
 String memberid="";
 String memberName="";
 String alt="";
 String logMag="";
 if(session.getAttribute("midx") != null) {// 로그인이 되었으면
	 midx = (int)session.getAttribute("midx");
	 memberid = (String)session.getAttribute("memberid");
	 memberName = (String)session.getAttribute("memberName");

	 alt=memberName+"님이 로그인되었습니다."; 
	 logMag="<a href='"+request.getContextPath()+"/member/memberLogout.aws'>로그아웃</a>";
 }else{
	 alt="로그인하세요";
	 logMag="로그인"; 
 }
 
 %>  
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="./css/style.css" type="text/css" rel="stylesheet"> 
  <!--   <style type="text/css">
  div.main{
	border : 1px solid black;
	width : 300px;
	height : 300px;
	text-align : center;</style>
} -->

</head>
<body>

<script>
<% if(!msg.equals("")) { %>
alert('<%=msg%>');	
<%}%>
</script>
<%=alt%>
<%=logMag%>
<hr>
<div class = "main"> 환영합니다. 메인페이지 입니다. </div>
<div>
<a href="<%=request.getContextPath() %>/member/memberJoin.aws">회원가입 페이지 가기</a> <!-- 프로젝트를 나타나게 하는 경로 -->
</div>
<div>
<a href="<%=request.getContextPath() %>/member/memberLogin.aws">회원 로그인 하기</a>
</div>
<div>
<a href="<%=request.getContextPath() %>/member/memberList.aws">회원 목록 보기</a>
</div>
<div>
<a href="<%=request.getContextPath() %>/board/boardList.aws">게시판 목록 가기</a><!--  -->
</div>
<!-- 컨트롤러 > 서버 > 웹 xml > 주소추출 > 주소끝 일치 확인 -->
</body>
</html>