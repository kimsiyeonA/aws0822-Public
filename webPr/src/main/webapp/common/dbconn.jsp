<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import = "java.sql.*" %>
 <%
 Connection conn = null;
 String url="jdbc:mysql://127.0.0.1/aws0822?serverTimezone=UTC";
 String user="root";
 String password="1234";

 Class.forName("com.mysql.cj.jdbc.Driver");  // driver 등록
 conn = DriverManager.getConnection(url, user, password);
 System.out.println("conn:" + conn);
// test
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
