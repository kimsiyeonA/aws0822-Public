package mvc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;//멀티파일을 생성한다. 클래스
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import mvc.dao.MemberDao;

import java.io.IOException;


@WebServlet("/FrontController") // 주소와 클래스를 매칭 시켜서 객체를 생성시킴(웹 서블릿)
@MultipartConfig( //멀티파일을 생성한다.
	fileSizeThreshold = 1024*1024*1, // 1MB
	maxFileSize = 1024*1024*50, // 10MB > 50
	maxRequestSize = 1024*1024*100, // 15MB > 100
	location		= "D:\\Delve\\temp" // 임시로 보관하는 위치(물리적으로 만들어 놔야 한다.)
)
// 넘어오는 것을 환경설정 해줌, 속성을 적어줌 (여러 방식으로 넘겨주기 때문에 프론트 컨트롤러에서는 클래스안에 설정을 잡아준다)

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String uri = request.getRequestURI(); // 전체주소 가져오기
		//		/member/memberJoinAction.aws
		String[] entity = uri.split("/"); //split으로 잘라주기 (/로 나누어주기)
		
		// 생성자를 이용한 생성자 호출 // 주소값에서 뒤에 1본째 값을 넣어주기
		if(entity[1].equals("member")) {
			MemberController mc = new MemberController(entity[2]);
			mc.doGet(request, response);

			
		}else if (entity[1].equals("board")) {
			BoardController bc = new BoardController(entity[2]);
			bc.doGet(request, response);
			//System.out.println(entity[2]);
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
