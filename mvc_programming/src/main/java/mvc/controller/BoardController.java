package mvc.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mvc.dao.BoardDao;
import mvc.vo.BoardVo;
import mvc.vo.Criteria;
import mvc.vo.PageMaker;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String location; // 맴버변수 (전역) 초기화 => 이동할 페이지
	public BoardController(String location) {
		this.location = location;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String paramMethod=""; // 전달방식에 대한 paramMethod 전송방식이 sendRedirect면 S forward방식이면 F
		String url="";
		
		if(location.equals("boardList.aws")) { // 포워드 방식
			System.out.println("222asdfasd");
			BoardDao bd = new BoardDao();
			
			//--------- 페이징 처리
			String page = request.getParameter("page"); // 넘겨온 페이지
			if(page == null) page="1"; // 실행문이 하나면 하나만 써도 됨
			int pageInt = Integer.parseInt(page); // 문자를 숫자로 변경한다.
			
			Criteria cri = new Criteria();
			cri.setPage(pageInt);
			System.out.println("222"+pageInt);
			System.out.println("23e3322"+cri);
			
			PageMaker pm = new PageMaker();
			pm.setCri(cri); // PageMaker에 Criteria 담아서 가지고 다닌다.
			
			//페이징 처리하기 위한 전체 대이터 갯수 가져오기
			int boardCnt = bd.boardTotalCount(); // 게시판의 전체 개수
			System.out.println("게시물 수는? "+boardCnt);
			
			pm.setTotalCount(boardCnt); // PageMaker에 전체게시물수를 담아서 가지고 다닌다.
			// --------
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(cri); //cri 페이지 번호 매개변수로 넣기 
			//System.out.println("alist : "+alist);//객체 주소가 나오면 객체가 생성된 것ㅇ을 짐작할 수 있다.
			request.setAttribute("alist", alist); // 
			
			// -------- 페이징 처리
			request.setAttribute("pm", pm); //
			// --------
			
			paramMethod="F";
			url= "/board/BoardList.jsp";  // 실제 내부경로
		}else if(location.equals("boardWrite.aws")){
			url = "/board/BoardWrite.jsp";
			paramMethod="F"; // 하단에서 포워드로 처리합니다 // 내부안에서 토스함 // send >경로를 통해 주소가 넘어감
			// 포워드 방식은 내부에서 공유하는 것이기 때문에 내부에서 활동하고 이용한다.
		}else if(location.equals("boardWriteAction.aws")){
			System.out.println("boardWriteAction.aws"); 
			
			// 1. 파라미터 값을 넘겨받는다. ? 
			String subject = request.getParameter("boardtitle");
			String contents = request.getParameter("boardbody");
			String writer = request.getParameter("writer");
			String password = request.getParameter("boardpwd");
			
			HttpSession session = request.getSession();// 세션 객체를 불러와서
			int midx = Integer.parseInt(session.getAttribute("midx").toString());
			
			BoardVo bv = new BoardVo();
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setPassword(password);
			bv.setMidx(midx);
			
			// 2. db 처리한다
			BoardDao bd = new BoardDao();
			int value = bd.boardInsert(bv);
			
			// 3.처리후 이동한다. sendRedirect
		      if (value == 2) {  // 입력성공
		            
		            paramMethod = "S";
		            url = request.getContextPath() + "/board/boardList.aws";
		         } else {  // 실패했으면

		            paramMethod = "S";
		            url = request.getContextPath() + "/board/boardWrite.aws";
		            
		         }

		}
		
		if(paramMethod.equals("F")) {
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request,response);
		}else if(paramMethod.equals("S")) {
			response.sendRedirect(url);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
