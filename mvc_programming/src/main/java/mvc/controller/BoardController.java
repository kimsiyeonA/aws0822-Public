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
import java.io.PrintWriter;
import java.util.ArrayList;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;


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
			//System.out.println("222asdfasd");
			BoardDao bd = new BoardDao();
			
			//--------- 페이징 처리
			String page = request.getParameter("page"); // 넘겨온 페이지
			if(page == null) page="1"; // 실행문이 하나면 하나만 써도 됨
			int pageInt = Integer.parseInt(page); // 문자를 숫자로 변경한다.
			
			Criteria cri = new Criteria();
			cri.setPage(pageInt);
			//System.out.println("222"+pageInt);
			//System.out.println("23e3322"+cri);
			
			PageMaker pm = new PageMaker();
			pm.setCri(cri); // PageMaker에 Criteria 담아서 가지고 다닌다.
			
			//페이징 처리하기 위한 전체 대이터 갯수 가져오기
			int boardCnt = bd.boardTotalCount(); // 게시판의 전체 개수
			//System.out.println("게시물 수는? "+boardCnt);
			
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
			//System.out.println("boardWriteAction.aws"); 
			
			// 저장될 위치
			/*
			 * String
			 * savePath="D:\\Git\\aws0822-Public\\mvc_programming\\src\\main\\webapp\\img";
			 * int sizeLimit = 15*1024*1024; // 15M // 저장파일 용량 크기 String dataType = "UTF-8";
			 * // 저장파일 인식 확인 DefaultFileRenamePolicy policy = new
			 * DefaultFileRenamePolicy();// 파일 이름이 중복될때 쓰는 매서드 MultipartRequest multi = new
			 * MultipartRequest(request,savePath,sizeLimit,dataType,policy);
			 * 
			 * Integer
			 */
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
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
			System.out.println("boardSelectOne bv " + bv);
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

		}else if(location.equals("boardContents.aws")){
			
			//1. 넘어온 값 받기
			String bidx = request.getParameter("bidx"); // ? 어떻게 bidx 값을 받을 수 있는 것일까...
			//System.out.println("bidx--->"+bidx);
			int bidxInt = Integer.parseInt(bidx); // 파라미터는 스트링 타입으로만 받기 때문에 int형으로 전환해준다.
			//System.out.println("boardSelectOnebidxInt" + bidxInt);
			
			//2. 처리하기
			BoardDao bd = new BoardDao(); // 보드 메서드 객체생성하고
			
			//---------여기에 조회수 업데이트 하게 만드는 쿼리 만들기
			bd.boardViewCntUpdate(bidxInt); // 트랜젝션은 업데이트와 인서트만 들어가고 셀렉트는 해당이 안됨
			// 그래서 값을 표시하는게 아니라 구문만 실행하면 됨
			// 업데이트와 인서트는 값을 왔다갔다 하면서 바꿀 수 있고 셀렉트는 조회용이라서 값을 주고 받을 수 없음
			// 그래서 업데이트를 한 후 업데이트한 값을 셀렉트 자체에서 끌어오면 됨
			//------------------------------------------------------
			
			BoardVo bv = bd.boardSelectOne(bidxInt); // 파라미터 보드번호를 넘겨 받아 BoardVo로 출력되는 객체를 담아주기
			
		
			request.setAttribute("bv" , bv);//포워드 방식이라 같은 영역 안에 있어서 공유헤서 jsp 페이지에서 꺼내쓸수 있다.
			//System.out.println("boardSelectOnebv" + bv);
			
			//3. 이동해서 화면 보여주기
			paramMethod="F"; // 화면을 보여주기 위해서 같은 영역 내부안에 jsp페이지를 보여준다.
			url= "/board/BoardBody.jsp";
			
			
		}else if(location.equals("BoardUpdate.aws")){
			
			String bidx = request.getParameter("bidx");
			int bidxInt = Integer.parseInt(bidx);
			
			BoardDao bd = new BoardDao(); // 보드 메서드 객체생성하고
			BoardVo bv = bd.boardSelectOne(bidxInt); // 파라미터 보드번호를 넘겨 받아 BoardVo로 출력되는 객체를 담아주기
			bv.setViewcnt(bd.boardViewCntUpdate(bidxInt));
			request.setAttribute("bv" , bv);//포워드 방식이라 같은 영역 안에 있어서 공유헤서 jsp 페이지에서 꺼내쓸수 있다.
			
			paramMethod="F"; 
			url= "/board/BoardUpdate.jsp";
			
		}else if(location.equals("boardModifyAction.aws")){
			System.out.println("boardModifyAction.aws"); 

			// 1. 파라미터 값을 넘겨받는다. 
			String subject = request.getParameter("subject");
			String contents = request.getParameter("contents");
			String writer = request.getParameter("writer");
			String password = request.getParameter("password");
			String bidx = request.getParameter("bidx"); // 통신으로 받아서 스트링으로 받음
			int bidxInt = Integer.parseInt(bidx); // 숫자형 변환
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(bidxInt);
			
			// 비밀번호 체크
			if(password.equals(bv.getPassword())) {
				// 같으면
				BoardDao bd2 = new BoardDao();
				BoardVo bv2 = new BoardVo();
				bv2.setSubject(subject);
				bv2.setContents(contents);
				bv2.setWriter(writer);
				bv2.setPassword(password);
				bv2.setBidx(bidxInt); //파라미터의 값을 객체에 담기
				int value = bd2.boardUpdate(bv2);// 그 객체를 BoardDao에 있는 메소드에 인자값으로 담아주기
				
				if(value==1) {
					  url = request.getContextPath() + "/board/boardContents.aws?bidx="+bidx;
					  System.out.println("boardModifyAction BoardBody ");
				}else {
					  url = request.getContextPath() + "/board/BoardUpdate.aws?bidx="+bidx;
					  System.out.println("boardModifyAction BoardUpdate ");
				}
			}else {
				// 다르면 

	             url = request.getContextPath() + "/board/BoardUpdate.aws?bidx="+bidx;
	        
	             
			}
			paramMethod = "S";
		}else if(location.equals("boardRecom.aws")){
			
			String bidx = request.getParameter("bidx");
			int bidxInt = Integer.parseInt(bidx);
			
			BoardDao bd = new BoardDao();
			int recom = bd.boardRecomUpdate(bidxInt);
			System.out.println("boardRecom recom"+recom);
			//paramMethod = "S";
			//url = request.getContextPath() + "/board/boardContents.aws?bidx="+bidx;
			

			// 조회수가 변화하지 않게 에이작스 사용
			PrintWriter out = response.getWriter();
			//out.print("{\"키값\":\"결과값\"}"); // 안에 있는 큰 따옴표는 밖에 있는 큰 따옴표와 구분하기 위해서 \로 구분함
			out.print("{\"recom\": \""+recom+"\"}"); // {"키값":"결과값"}
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
