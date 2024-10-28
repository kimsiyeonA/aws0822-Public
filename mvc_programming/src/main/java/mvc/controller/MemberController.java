package mvc.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mvc.dao.MemberDao;
import mvc.vo.MemberVo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

// 서버로 만든 프로그래밍 (클래스 파일)
@WebServlet("/MemverController")
public class MemberController extends HttpServlet {  // MVC방식으로 가기전에 첫번째 model2 방식
	//HttpServlet 상속받고 있음 > http 통신을 한다는 뜻
	// 인터넷 기능을 가지고 있는 클래스 // 휍페이지 기능을 한다는 뜻
	// 서블릿 : 자바로 만든 웹페이지(접속 주소 : /MemverController)
	private static final long serialVersionUID = 1L;
    
	String location; // 맴버변수 (전역) 초기화 => 이동할 페이지
	public MemberController(String location) {
		this.location = location;
	}
	
	
	
	
	// MemberController 상위에 있는 HttpServlet 생성자를 불러옴 // 매개변수가 없는 기본 생성자 > 생략가능
	/*
    public MemberController() { 
        super();
    }
	 */
	
	// ? 뒤에 넘어오는 객체
	// get 기본으로 실행
	// 코드는 doGet 안에 작성하게 됨
	// HttpServletRequest, HttpServletResponse > $ 통신객체
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		// 넘어온 모든 값은 여기에서 처리해서 분기한다 - controller 역할
		
		//System.out.println("값이 넘어오나요?");
		
		// 넘어온 주소 찾아도는 메서드
		// 전체 주소를 추출
		//String uri = request.getRequestURI();
		//System.out.println(uri); //	/mvc_programming/member/memberJoinAction.aws> mvc_programming 서버에서 수정해서 3>2fh 바뀜
		
		String paramMethod=""; // 전달방식에 대한 paramMethod 전송방식이 sendRedirect면 S forward방식이면 F
		String url="";
		
		// 넘어온 주소들을 배열로 넣음
		//String[] location = uri.split("/");
		if(location.equals("memberJoinAction.aws")) { // 배열이 아니라 프론트컨트롤러에서 넘어온 뒤의 값을 넣어 일치하는지 확인

			
			String memberId = request.getParameter("memberid");
			String memberPwd = request.getParameter("memberpwd");
			//String memberPwd2 = request.getParameter("memberpwd2");
			String memberName = request.getParameter("membername");
			String memberEmail = request.getParameter("memberemail");
			String memberPhone = request.getParameter("memberphone");
			String memberAddr = request.getParameter("memberaddr");
			String memberGender = request.getParameter("membergender");
			String memberBirth = request.getParameter("memberbirth");
			String[] memberHobby = request.getParameterValues("memberhobby");
			String memberInHobby = "";
			for(int i = 0; i < memberHobby.length; i++) {
			 memberInHobby = memberInHobby + memberHobby[i] + ",";
			 // out.println("memberHobby값은? : " + memberHobby[i]);
			} 

			MemberDao md = new MemberDao();
			int value = md.memberInsert(memberId, 		// 객체 안에 생성해놓은 맴버 메소드를 호출해서 값을 꺼낸다.
					memberPwd, 
					memberName, 
					memberGender, 
					memberBirth,
					memberAddr,  
					memberPhone,  
					memberEmail,  
					memberInHobby);

			// value값이 1이면 입력성공 0이면 입력실패다.
			// 1이면 성공했기 때문에 다른 페이지로 이동시키고 0이면 다시 회원가입 페이지로 
			// String pageUrl= "/index.jsp";
			String msg = "";
			
			//세션객체 = 연결성 / 로그인 정보를 가지고 있음
			HttpSession session = request.getSession();
			
			if(value == 1){							// index.jsp파일은 web.xml웹설정파일에 기본등록 되어있어
				msg= "회원가입 되었습니다" ;
				session.setAttribute("msg", msg);
				
				url=request.getContextPath()+"/";	
				
			}else{
				msg= "회원가입 오류발생되었습니다" ;
				session.setAttribute("msg", msg);
				
				url=request.getContextPath()+"/member/memberJoin.jsp";	
				
			}
			paramMethod="S"; // 밑에서 sendRedirct 방식으로 처리한다.
			
			
			//System.out.println("msg "+ msg );
		}else if (location.equals("memberJoin.aws")) {
			//System.out.println("들어왔나");
			url = "/member/memberJoin.jsp";
			paramMethod="F"; // 하단에서 포워드로 처리합니다
		
		}else if (location.equals("memberLogin.aws")) {
			//System.out.println("들어왔나");
			url = "/member/memberLogin.jsp";
			paramMethod="F"; // 하단에서 포워드로 처리합니다
			
		}else if (location.equals("memberLoginAction.aws")) {
			//System.out.println("memberLoginAction들어왔나");
			
			String memberId = request.getParameter("memberid");
			String memberPwd = request.getParameter("memberpwd");
			
			//memberDao에 있는 메소드 불러와서 체크하기
			MemberDao md = new MemberDao();
			MemberVo mv = md.memberLogincheck(memberId, memberPwd);
			//ㄴystem.out.println("mv객체확인 "+mv);
			
			
			if(mv == null) {
				url = request.getContextPath()+"/member/memberLogin.aws";
				paramMethod="S";
				
			}else {//해당되는 로그인 사용자가 잇으면 세션에 회원정보 담아서 메인으로 가라
				String mid = mv.getMemberid(); //  아이디 꺼내기
				int midx = mv.getMidx();
				String memberName = mv.getMembername();
				
				HttpSession session = request.getSession();
				session.setAttribute("mid", mid);
				session.setAttribute("midx", midx);
				session.setAttribute("memberName", memberName);
						
				url=request.getContextPath()+"/";
				paramMethod="S";
			}
		}else if (location.equals("memberLogout.aws")) {
			//System.out.println("memberLogout들어왔나");
			
			// 세션 삭제
			HttpSession session = request.getSession();
			session.removeAttribute("mid");
			session.removeAttribute("midx");
			session.removeAttribute("memberName");
			session.invalidate();

			url=request.getContextPath()+"/";
			paramMethod="S";					
			
		}else if(location.equals("memberList.aws")) {
			//System.out.println("memberList");
			
			//1. 메소드 불러서 처리하는 코드를 만들어야한다.
			MemberDao md = new MemberDao();
			ArrayList<MemberVo> alist = md.memberSelectAll();
			
			request.setAttribute("alist", alist);// 통신객체에 "alist"에  alist를 담아서 가지고갈 예정
			//2. 보여줄 페이지를 forward방식으로 보여준다.
			//forward는 공유의 특성을 가지고 있다. alist를 화면까지 가지고 갈것
			url = "/member/memberList.jsp";
			paramMethod="F";
		}else if(location.equals("memberIdCheck.aws")) {
			System.out.println("memberIdCheck");
			String memberId = request.getParameter("memberid"); // ?
			
			MemberDao mv = new MemberDao();
			int cnt = mv.memberIdCheck(memberId); // 이동하지 않고 그대로 뜨게 해야함 json 파일이여서 
			
			System.out.println("cnt : "+cnt);
			
			PrintWriter out = response.getWriter();
			//out.print("{\"키값\":\"결과값\"}"); // 안에 있는 큰 따옴표는 밖에 있는 큰 따옴표와 구분하기 위해서 \로 구분함
			out.print("{\"cnt\": \""+cnt+"\"}"); // {"키값":"결과값"}
		}
		

		
		if(paramMethod.equals("F")) {
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request,response);
		}else if(paramMethod.equals("S")) {
			response.sendRedirect(url);
		}
	}

	// 통신을 post로 받을 수 있음
	// post에서 넘어오는 객체는 get으로 가서 실행이 됨
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
// 모델 1 : 컨트롤러를 제외한 Dao(:데이터를 담음), Vo(: 서비스를 연결) 분리
// 모델 2 : 액션처리 > 서블릿 > 컨트롤러 = 가상경로 인식시키는게 중요(aws) 
// 물리적인 경로를 노출하지 않기 위해서 (.do 확장자는 본인 선택) >> 서버에게 가상경로에 알려줌 : 웹xml에 설정

// 클래스 사용 이유
// 1. 서버 부하를 줄이기 위해
// 2. 유지관리 용이
// >>m(:Dao,Vo)v(:jsp)c:
// 조인 > 액션 > 이동하는 부분역할 (화면을 나타내주지 않는 역할은 서블릿으로 사용) 굳이 jsp 쓸 필요 없음
// 이동하는 분기 역할
// 무조건 거쳤다가 지나가는 역할을 함 >> 가상경로를 통해 컨트롤로로 함
// 웹 xml로 추가적으로 내용 기입
// *aws로 오면 멤버 컨트롤러로 넘겨줌

// 아이디 넘어왔는지 체크
// 비밀번호 체크
// 디비 확인(select)(count)
// 0 > 없는 페이지, 1 > 홈페이지 메인
// post >> 보안유지로 인해 post로 넘김
// 

