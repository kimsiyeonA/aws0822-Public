<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*"%>
<!--  <%//@ page import = "java.sql.Connection"%>-->
<!--<%//@ page import = "java.sql.DriverManager"%>  -->
<%@ include file = "/common/dbconn.jsp" %>
<%@ include file = "/common/function.jsp" %>
<jsp:useBean id="mv" class="Vo.MemberVo" scope="page" />
<!-- scope범위는 4가지가 있다. page(페이지 내애서만), request(전송하는 범위까지), 
session(서버에서 끝날때까지(로그아웃)), applmemberication(프로그램이 살아있을 때까지) -->
<jsp:setProperty name="mv" property="*"/> <!--한번에 모든것을 넘겨 받아서 씀  --> 

<%
/*
String memberId = request.getParameter("memberId");
out.println("memberId값은?"+memberId);
out.println("<br>");
String memberPwd = request.getParameter("memberPwd");
out.println("\nmemberPwd값은?"+memberPwd);
out.println("<br>");
String memberPwd2 = request.getParameter("memberPwd2");
out.println("\nmemberPwd2값은?"+memberPwd2);
out.println("<br>");
String memberName = request.getParameter("memberName");
out.println("memberName값은?"+memberName);
out.println("<br>");
String memberEmail = request.getParameter("memberEmail");
out.println("memberEmail값은?"+memberEmail);
out.println("<br>");
String memberPhone = request.getParameter("memberPhone");
out.println("memberPhone값은?"+memberPhone);
out.println("<br>");
String memberAddr = request.getParameter("memberAddr");
out.println("memberAddr값은?"+memberAddr);
out.println("<br>");
String memberGender = request.getParameter("memberGender");
out.println("memberGender값은?"+memberGender);
out.println("<br>");
String memberBirth = request.getParameter("memberBirth");
out.println("memberBirth값은?"+memberBirth);
out.println("<br>");
*/



// 1. jsp프로그래밍 (날코딩 날코딩방법부터 -> 함수화 -> 객체화)
// 2. java/jsp프로그래밍 (model1,model2,MVC방식으로 진화되는 방법)
// 3. spring 프레임 워크로 프로그래밍 하는 방법
/*
Connection conn = null;
String url = "jdbc:mysql://127.0.0.1/aws0822?serverTimezone=UTC"; // 주소 ? 프로미터 > get방식 /input > 호스트 방식
String user = "root";
String password = "1234";


Class.forName("com.mysql.cj.jdbc.Driver"); // 매소드 영역에서 지정된 영역을 드라이버로 해줌
conn = DriverManager.getConnection(url, user, password);

System.out.println("conn" + conn);
*/
/*
Connection conn = null;
String url = "jdbc:mysql://127.0.0.1/aws0822?serverTimezone=UTC";
String user = "root";
String password = "1234";

Class.forName("com.mysql.cj.jdbc.Driver");  // driver 등록
conn = DriverManager.getConnection(url, user, password);
System.out.println("conn:" + conn);
*/
//conn객체 안에는 많은 메소드가 있는데, 일단 createStatement 메소드 사용해서 쿼리 작성
/*
String sql = "insert into member(memberid,memberpwd,membername,membergender,"
+"memberbirth,memberaddr,memberphone,memberemail,memberhoby) "
+"values('"+memberId+"', '"+memberPwd+"', '"+memberName+"', '"+memberGender+"', '"
+memberBirth+"', '"+memberAddr+"', '"+memberPhone+"', '"+memberEmail+"', '"+memberInHobby+"')";

Statement stmt = conn.createStatement(); // 쿼리구문을 동작시키는 클래스 매소드 Statement
int value = stmt.executeUpdate(sql); // 되면 1 ,안되면 0
*/

/*
String sql = "insert into member(memberid,memberpwd,membername,membergender,"
+"memberbirth,memberaddr,memberphone,memberemail,memberhoby) "
+"values(?,?,?,?,?,?,?,?,?)";
PreparedStatement pstmt =conn.prepareStatement(sql);
pstmt.setString(1,memberId);
pstmt.setString(2,memberPwd);
pstmt.setString(3,memberName);
pstmt.setString(4,memberGender);
pstmt.setString(5,memberBirth);
pstmt.setString(6,memberAddr);
pstmt.setString(7,memberPhone);
pstmt.setString(8,memberEmail);
pstmt.setString(9,memberInHobby);
int value = pstmt.executeUpdate();
*/
// 자바나 스트링부트에서 씀
// PreparedStatement 클래스는 메소드화 시켜서 사용함


String[] memberHobby = request.getParameterValues("memberhobby");
String memberInHobby = "";
for(int i = 0; i < memberHobby.length; i++) {
   memberInHobby = memberInHobby + memberHobby[i] + ",";
   // out.println("memberHobby값은? : " + memberHobby[i]);
} 
//매게 변수에 인자값 대입해서 함수호출하자
int value = memberInsert(
		conn, 
		mv.getMemberid(), 		// 객체 안에 생성해놓은 맴버 메소드를 호출해서 값을 꺼낸다.
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

<script>
alert('<%=msg%>');
//자바스크립트로 페이지 이동시킨다, document갹체 안에 location객체안에 주소속성에 담아
document.location.href = "<%=pageUrl%>";

</script>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	

</body>
</html>