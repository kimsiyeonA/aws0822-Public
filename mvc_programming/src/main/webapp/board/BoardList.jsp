<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="mvc.vo.*"  %>    
<%
ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");
//System.out.println("alist : "+alist);
PageMaker pm = (PageMaker)request.getAttribute("pm");

//------ 리스트 순위 주기
int totalCount = pm.getTotalCount();
//System.out.println("게시물 수는? list "+totalCount);
//int num = totalCount - (pm.getScri().getPage()-1) * pm.getScri().getPerPageNum();
// 1 num => 전체개수 번호 30 - 0*15 = 30
// 2 30 - (2-1)*15 =15 
//------


String keyword =  pm.getScri().getKeyword();
String searchType = pm.getScri().getSearchType();

String param="keyword="+keyword+"&searchType="+searchType+"";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글목록 페이지</title>
<style>
html{
	width:100%;c
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
.C span{ 
padding-top:5px;
text-align: center;
display:inline-block;
    width:30px;
	height: 30px;
	text-decoration: none;
	
}
.C span:active {
   padding-top:5px;
   display:inline-block;
    width:30px;
	height: 30px;
	background: black;
	color : white;
	text-decoration: none;
}

a{ 
	text-decoration: none;
	color: black;
	
}
.C span a{ 
padding-top:5px;
text-align: center;
display:inline-block;
    width:30px;
	height: 30px;
	text-decoration: none;
	color: inherit;
	
}
.C span a:active {
    padding-top:5px;
    display:inline-block;
    width:30px;
	height: 30px;
	background: black;
	color : white;
	text-decoration: none;
}

.title{
text-align: left;
}

</style>
</head>
<body>
 <section>
<hr>
<form name="frm" method="get" action="<%=request.getContextPath() %>/board/boardList.aws" >
<div class="A">
	<select style = "width:60px;" name="searchType">
	<option value="subject" selected> 제목 </option>
	<option value="writer" > 작성자 </option>
	<!--  <option value = "작성자" > 작성자 </option>-->
	</select> 
	<input type = "text" name = "keyword" maxlength = "30" style = "width:160px;">
	<button type="submit" class="btn">검색</button>
</div>
</form>
<table>
	<thead>
	<tr>
		<th>NO</th>
		<th>제목</th>
		<th>작성자</th>
		<th>조회</th>
		<th>추천</th>
		<th>날짜</th>
	</tr>
	</thead>
	<tbody>
	<%
	int num = totalCount - (pm.getScri().getPage()-1) * pm.getScri().getPerPageNum();
	for(BoardVo bv : alist){ 
		
		String lvlStr = "" ;
		for(int i=1; i<=bv.getLevel_();i++){
			lvlStr = lvlStr+ "&nbsp;&nbsp;&nbsp;";
			if(i==bv.getLevel_()){
				lvlStr=lvlStr+"ㄴ";
			}
		}
		
		//System.out.println("bv.getLevel_() "+bv.getLevel_());
		//System.out.println("lvlStr"+lvlStr); 
/* 		String lvlStr = "";
		for(int i=1;i<=bv.getLevel_(); i++){
			
			lvlStr = lvlStr +"&nbsp;&nbsp;";
			
			if (i == bv.getLevel_()){
				lvlStr  = lvlStr + "ㄴ";
			}
		}
		 */
		
	%>
		<tr>
			<td><%=num %></td> <!-- bv.getBidx() -->
			<td class="title">
			<%=lvlStr %>
			<a href="<%=request.getContextPath()%>/board/boardContents.aws?bidx=<%=bv.getBidx()%>"><%=bv.getSubject() %></a></td>
			<td><%=bv.getWriter() %></td>
			<td><%=bv.getViewcnt() %></td>
			<td><%=bv.getRecom() %></td>
			<td><%=bv.getWriterday().substring(0,10) %></td>
		</tr>
	<%
	num = num-1;
	}
	%>

	</tbody>
</table>
<div class="B">
<a href="<%=request.getContextPath() %>/board/boardWrite.aws">
<button type="button" class="btn">글쓰기</button>
</a>
</div>
	<div class="C">
	<% if(pm.isPrev()==true){ %>	
	<span><a href="<%=request.getContextPath()%>/board/boardList.aws?page=<%=pm.getStartPage()-1%>&<%=param %>">◀</a></span>
	<%}%>	
	<% for(int i = pm.getStartPage(); i<=pm.getEndPage();i++){ %>	
	<span <%if(i==pm.getScri().getPage()){%>class="on"<%}%>>
	<a href="<%=request.getContextPath() %>/board/boardList.aws?page=<%=i%>&<%=param %>"> <%=i %></a></span>
	<%}%>	
	<span>
	<% if(pm.isNext()==true && pm.getEndPage()>0){ %>	
	<a href="<%=request.getContextPath()%>/board/boardList.aws?page=<%=pm.getEndPage()+1%>&<%=param %>">▶</a>
	<%}%>	
	</span>
	</div>
</section>
</body>
</html>