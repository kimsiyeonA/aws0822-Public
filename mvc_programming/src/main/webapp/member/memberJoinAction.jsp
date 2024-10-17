<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="mvc.dao.MemberDao" %>
<jsp:useBean id="mv" class="mvc.vo.MemberVo" scope="page" />
<jsp:setProperty name="mv" property="*"/> 

<%

	//매게 변수에 인자값 대입해서 함수호출하자
	String[] memberHobby = request.getParameterValues("memberhobby");
	String memberInHobby = "";
	for(int i = 0; i < memberHobby.length; i++) {
	 memberInHobby = memberInHobby + memberHobby[i] + ",";
	 // out.println("memberHobby값은? : " + memberHobby[i]);
	} 

	MemberDao md = new MemberDao();
	int value = md.memberInsert(mv.getMemberid(), 		// 객체 안에 생성해놓은 맴버 메소드를 호출해서 값을 꺼낸다.
				mv.getMemberpwd(), 
				mv.getMembername(), 
				mv.getMembergender(), 
				mv.getMemberbirth(),
				mv.getMemberaddr(),  
				mv.getMemberphone(),  
				mv.getMemberemail(),  
				memberInHobby);

	// value값이 1이면 입력성공 0이면 입력실패다.
	// 1이면 성공했기 때문에 다른 페이지로 이동시키고 0이면 다시 회원가입 페이지로 
	String pageUrl= "/index.jsp";
	String msg = "";
	if(value == 1){							// index.jsp파일은 web.xml웹설정파일에 기본등록 되어있어
		msg= "회원가입 되었습니다" ;
		pageUrl= request.getContextPath() + "/index.jsp"; //request.getContextPath() : 프로젝트
		//response.sendRedirect(pageUrl); //전송방식 : sendRedirect 요청 받으면 다시 그족으로 가라고 지시하는 방법 한곳을 거쳐서 돌아가서 그쪽으로 가세요. forward : 다이렉트로 전달
		
	}else{
		msg= "회원가입 오류발생되었습니다" ;
		pageUrl= request.getContextPath() + "/member/memberJoin.jsp"; //request.getContextPath() : 프로젝트
		//response.sendRedirect(pageUrl);
	}

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
alert('<%=msg%>');
//자바스크립트로 페이지 이동시킨다, document갹체 안에 location객체안에 주소속성에 담아
document.location.href = "<%=pageUrl%>";

</script>

</body>
</html>