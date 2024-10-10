<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록 보기</title>
</head>
<body>
<h3>회원목록</h3>
<style>
table{
 border : 1px solid #1d435c;
 border-collapse : collapse; 
 /*table과 td, th 선 합치기*/
}
td, th {
 border : 1px dotted #1f93e0;
 padding : 10px;
 
}

th {
width : 100px;
height : 40px;

}
td {
width : 100px;
height : 20px;
text-align: right;
}
thead{
background: #70a7cc;
color : white;
}
tbody tr:nth-child(even) { /* 짝수 <tr>에 적용*/
	background : aliceblue;
}
tbody tr:hover {
	background : pink;
}

tfoot{
border-bottom : 1px solid #1d435c;
}
</style>
<hr>
<table>
<thead>
<tr>
	<th>회원번호</th>
	<th>회원아이디</th>
	<th>회원이름</th>
	<th>회원성별</th>
	<th>가입일</th>
</tr>
</thead>
<tbody>
<tr>
	<td>3</td>
	<td>test</td>
	<td>홍갑동</td>
	<td>남자</td>
	<td>2024-09-26</td>
</tr>
<tr>
	<td>4</td>
	<td>test2</td>
	<td>김영미</td>
	<td>여자</td>
	<td>2024-09-26</td>
</tr>
<tr>
	<td>5</td>
	<td>test3</td>
	<td>김갑동</td>
	<td>남자</td>
	<td>2024-09-26</td>
</tr>
</tbody>
<tfoot>
<tr>
	<td colspan = "5">총 3명 입니다.</td>
</tr>
</tfoot>
</table>

</body>
</html>