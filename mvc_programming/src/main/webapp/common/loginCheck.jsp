<!-- 공통으로 들어갈 기능 :common -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
if(session.getAttribute("midx")==null){
	out.println("<script>alert('로그인 해주세요');location.href='"+request.getContextPath()+"/member/memberLogin.aws'</script>");
}
%>