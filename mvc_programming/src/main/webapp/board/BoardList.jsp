<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="mvc.vo.*"  %>    
<%
ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");
System.out.println("alist : "+alist);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글목록 페이지</title>
<style>
html{
	width:100%;
	height: 100%;
}
body{
	width:100%;
	height: 100%
}
div.A{
	margin-right: 10px 20px 10px 10px;
	text-align: right;
	margin: 10px;
}
div.A:select,input{
	height: 25px;
}
table{
	width:800px;
	 border : 1px solid #1d435c;
	 border-collapse : collapse; 
	 margin-left : auto;
     margin-right : auto;
     text-align: center;
	 
	 /*table과 td, th 선 합치기*/
	}
	td, th {
	 border : 1px dotted #1f93e0;
	 padding : 20px;
	 
	}

div.B{
	text-align: right;
	margin-right: 20px;
}
button{
	background: black;
	color : white;
}

div.C{
	text-align: center;
}
span{
padding-top:5px;
text-align: center;
display:inline-block;
    width:30px;
	height: 30px;
}
span:hover {
   padding-top:5px;
   display:inline-block;
    width:30px;
	height: 30px;
	background: black;
	color : white;
}
</style>
</head>
<body>
 <section>
<hr>
<div class="A">
	<select name = "boardlistchoice" style = "width:60px;" >
	<option value = "제목" selected> 제목 </option>
	<option value = "내용"  > 내용 </option>
	<option value = "작성자" > 작성자 </option>
	</select> 
	<input type = "text" name = "boardlistsearch" maxlength = "30" style = "width:160px;">
</div>
<table>
	<thead>
	<tr>
		<th>NO</th>
		<th>제목</th>
		<th>작성자</th>
		<th>조회</th>
		<th>날짜</th>
	</tr>
	</thead>
	<tbody>
	<%for(BoardVo bv : alist){ %>
		<tr>
			<td><%=bv.getBidx() %></td>
			<td><%=bv.getSubject() %></td>
			<td><%=bv.getWriter() %></td>
			<td><%=bv.getViewcnt() %></td>
			<td><%=bv.getWriterday() %></td>
		</tr>
	<%}%>
	</tbody>
</table>
<div class="B">
<a href="<%=request.getContextPath() %>/board/boardWrite.aws">
<button type="button" class="btn">글쓰기</button>
</a>
</div>
<div class="C">
<span>1</span>
<span>2</span>
<span>3</span>
<span>4</span>
<span>5</span>
<span>6</span>
<span>7</span>
<span>8</span>
<span>9</span>
<span>10</span>
</div>
</section>
</body>
</html>