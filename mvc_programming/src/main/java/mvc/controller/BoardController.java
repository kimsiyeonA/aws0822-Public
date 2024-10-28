package mvc.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import mvc.dao.BoardDao;
import mvc.vo.BoardVo;
import mvc.vo.Criteria;
import mvc.vo.PageMaker;
import mvc.vo.SearchCriteria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;


@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String location; // 맴버변수 (전역) 초기화 => 이동할 페이지
	// 여기에는 3번째의 주소가 들어가 있다
	// 아래 생성자에게서 받아서
	
	public BoardController(String location) {
		this.location = location;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		
		String paramMethod=""; // 전달방식에 대한 paramMethod 전송방식이 sendRedirect면 S forward방식이면 F
		String url="";
		
		if(location.equals("boardList.aws")) { // 포워드 방식
			//System.out.println("222asdfasd");
			
		
			//--------- 페이징 처리
			String page = request.getParameter("page"); // 넘겨온 페이지
			if(page == null) page="1"; // 실행문이 하나면 하나만 써도 됨
			int pageInt = Integer.parseInt(page); // 문자를 숫자로 변경한다.
			
			//---------검색기능
			String searchType=request.getParameter("searchType");
			String keyword=request.getParameter("keyword");
			if(keyword==null) {
				keyword="";
			}
			
			// 파라미터를 들고 다녀야 하지만 힘드므로 Criteria를 상속받아 가지고 다닐거임
			//--------
			
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pageInt);
			
			//---------검색기능 >> Criteria에서 상속 받은 SearchCriteria 사용 /PageMaker 도 수정
			scri.setSearchType(searchType);
			scri.setKeyword(keyword);
			//---------
						
			//System.out.println("222"+pageInt);
			//System.out.println("23e3322"+cri);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri); // PageMaker에 Criteria 담아서 가지고 다닌다.
			//System.out.println("게시물 수는pm? "+pm);
			
			BoardDao bd = new BoardDao();
			//페이징 처리하기 위한 전체 대이터 갯수 가져오기
			int boardCnt = bd.boardTotalCount(scri); // 게시판의 전체 개수
			//System.out.println("게시물 수는? "+boardCnt);
			
			pm.setTotalCount(boardCnt); // PageMaker에 전체게시물수를 담아서 가지고 다닌다.
			
			// --------
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(scri); //cri 페이지 번호 매개변수로 넣기 
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
			
			// 저장되는 위치
			String savePath = "D:\\Git\\aws0822-Public\\mvc_programming\\src\\main\\webapp\\images";
			//System.out.println(savePath);
			
			// 업로드 되는 파일 사이즈
			int fsize = (int)request.getPart("filename").getSize();
			//System.out.println(fsize);
			
			// 원본파일 이름
			String originFileName = "";
			if(fsize != 0) { // 넘어온 파일을 멀티파트 형식의  파일을 Part클래스로 담는다.
				Part filePart = (Part)request.getPart("filename"); 
				System.out.println("filePart===>"+filePart);
				
				originFileName = getFileName(filePart);// 파일이름 추출
				System.out.println("originFileName===>"+originFileName);
				
				System.out.println("저장되는 위치==>"+savePath+originFileName);
				
				File file = new File(savePath+originFileName);// 파일 객체 생성
				InputStream is = filePart.getInputStream(); // 파일 읽어드리는 스트링 생성
				FileOutputStream fos = null;
				
	            fos = new FileOutputStream(file); // 파일 작성 및 완성하는 스트림 생성
	            
	            int temp = -1;
	            
	            while ((temp = is.read()) != -1) { // 반복문을 돌려서 읽어드린 데이터를  작성한다.
	               fos.write(temp);
	            }
	            
	            is.close();
	            fos.close();

			}else {
				
				originFileName="";
			}
			
			
			
			// 1. 파라미터 값을 넘겨받는다. ? 
			String subject = request.getParameter("boardtitle");
			String contents = request.getParameter("boardbody");
			String writer = request.getParameter("writer");
			String password = request.getParameter("boardpwd");
			
			
			HttpSession session = request.getSession();// 세션 객체를 불러와서
			int midx = Integer.parseInt(session.getAttribute("midx").toString());
			
			//-----ip뽑기
			// 리퀘스트를 사용해서 헤더 정보를 사용해 브라우저마다 ip를 뽑아내는 방식이 다르다.
			// 실제 ip를 운용할 때 사용
			//String ip=request.getRemoteAddr(); //0:0:0:0:0:0:0:1 자기주소 아이피
			String ip="";
			try {
				ip = getUserIp(request);
				//String serverIp = InetAddress.getLocalHost().getHostAddress();// 서버 ip값 뽑기
				System.out.println("ip"+ip);
				//System.out.println("serverIp"+serverIp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			BoardVo bv = new BoardVo();
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setPassword(password);
			bv.setMidx(midx);
			bv.setFilename(originFileName);
			//System.out.println("boardSelectOne bv " + bv);
			bv.setIp(ip); // ip추가
			
			
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
		}else if(location.equals("BoardDelete.aws")){
			String bidx = request.getParameter("bidx");
			
			request.setAttribute("bidx", bidx);
			
			paramMethod="F"; 
			url= "/board/BoardDelete.jsp";
		}else if(location.equals("BoardDelectAction.aws")){
			
			String bidx = request.getParameter("bidx");
			String password = request.getParameter("password");
			
			// 처리하기
			BoardDao bd = new BoardDao();
			int value = bd.boardDelete(Integer.parseInt(bidx), password); // 0,1
			
			System.out.println("BoardDelectAction value"+value);
			
			// 삭제 후 가야할 경로 지정
			paramMethod="S";
			if(value==1) {
				  url = request.getContextPath() + "/board/boardList.aws?bidx="+bidx;

			}else {
				  url = request.getContextPath() + "/board/BoardDelete.aws?bidx="+bidx;
			}
		}else if(location.equals("BoardReply.aws")){
			String bidx = request.getParameter("bidx");
			
			BoardDao bd = new BoardDao();
			BoardVo bv = bd.boardSelectOne(Integer.parseInt(bidx));
			
			int originbidx = bv.getOriginbidx();
			int depth = bv.getDepth();
			int level_ = bv.getLevel_();
			
			request.setAttribute("bidx", Integer.parseInt(bidx));
			request.setAttribute("originbidx", originbidx);
			request.setAttribute("depth", depth);
			request.setAttribute("level_", level_);

			
			paramMethod="F"; 
			url= "/board/BoardReply.jsp";
			
		}else if(location.equals("BoardReplyAction.aws")){
			// 저장되는 위치
			String savePath = "D:\\Git\\aws0822-Public\\mvc_programming\\src\\main\\webapp\\images";
			System.out.println(savePath);
			
			// 업로드 되는 파일 사이즈
			int fsize = (int)request.getPart("filename").getSize();
			System.out.println(fsize);
			
			// 원본파일 이름
			String originFileName = "";
			if(fsize != 0) { // 넘어온 파일을 멀티파트 형식의  파일을 Part클래스로 담는다.
				Part filePart = (Part)request.getPart("filename"); 
				System.out.println("filePart===>"+filePart);
				
				originFileName = getFileName(filePart);// 파일이름 추출
				System.out.println("originFileName===>"+originFileName);
				
				System.out.println("저장되는 위치==>"+savePath+originFileName);
				
				File file = new File(savePath+originFileName);// 파일 객체 생성
				InputStream is = filePart.getInputStream(); // 파일 읽어드리는 스트링 생성
				FileOutputStream fos = null;
				
	            fos = new FileOutputStream(file); // 파일 작성 및 완성하는 스트림 생성
	            
	            int temp = -1;
	            
	            while ((temp = is.read()) != -1) { // 반복문을 돌려서 읽어드린 데이터를  작성한다.
	               fos.write(temp);
	            }
	            
	            is.close();
	            fos.close();

			}else {
				
				originFileName="";
			}
			
			//-----ip뽑기
			// 리퀘스트를 사용해서 헤더 정보를 사용해 브라우저마다 ip를 뽑아내는 방식이 다르다.
			// 실제 ip를 운용할 때 사용
			//String ip=request.getRemoteAddr(); //0:0:0:0:0:0:0:1 자기주소 아이피
			String ip="";
			try {
				ip = getUserIp(request);
				//String serverIp = InetAddress.getLocalHost().getHostAddress();// 서버 ip값 뽑기
				System.out.println("ip"+ip);
				//System.out.println("serverIp"+serverIp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			// 1. 파라미터 값을 넘겨받는다. 
			String subject = request.getParameter("subject");
			String contents = request.getParameter("contents");
			String writer = request.getParameter("writer");
			String password = request.getParameter("password");
			String bidx = request.getParameter("bidx");
			String originbidx = request.getParameter("originbidx");
			String depth = request.getParameter("depth");
			String level_ = request.getParameter("level_");
			
			
			HttpSession session = request.getSession();// 세션 객체를 불러와서
			int midx = Integer.parseInt(session.getAttribute("midx").toString());
		
			
			
			BoardVo bv = new BoardVo();
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setPassword(password);
			bv.setMidx(midx);
			bv.setFilename(originFileName);
			bv.setBidx(Integer.parseInt(bidx));
			bv.setOriginbidx(Integer.parseInt(originbidx));
			bv.setDepth (Integer.parseInt(depth));
			bv.setLevel_(Integer.parseInt(level_));
			bv.setIp(ip);
			System.out.println("boardSelectOne bv " + bv);
			
			// 2. db 처리한다
			BoardDao bd = new BoardDao(); // WriteAction과 동일 (번호들만 추가)
			// 메소드 만들기 
			int maxbidx = bd.boardReply(bv); // 2가 나와야 성공 >  maxbidx 실행
			
			paramMethod="S";
			if(maxbidx != 0) {
				 url = request.getContextPath() + "/board/boardContents.aws?bidx="+maxbidx;
			}else {
				 url = request.getContextPath() + "/board/BoardReply.aws?bidx="+bidx;
			}		   
		}else if(location.equals("BoardDowmload.aws")){
			
			
			
			String filename = request.getParameter("filename");
			String savePath = "D:\\Git\\aws0822-Public\\mvc_programming\\src\\main\\webapp\\images\\";
			
			ServletOutputStream sos = response.getOutputStream();
			
			String downfile = savePath+filename;
			System.out.println("downfile===>"+downfile);
			
			File f = new File(downfile); // 객체 만들기
			
			// 해더 정보 변경하기 >> 저장되어 있는 파일 이름으로 만듬
			//response.setHeader("Cache-Control", "no-cache");
			//response.setContentType("application/octet-stream");
			//response.setHeader("Content-disposition", "attachment;fileName="+filename);
			
			String header = request.getHeader("User-Agent");
			
			String fileName="";
			response.setHeader("Cache-Control", "no-cache");
			if(header.contains("Chrome") || header.contains("Opera")) {
				fileName = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
				response.setHeader("Content-disposition", "attachment;fileName="+fileName);
				
			}else if(header.contains("MSIE") || header.contains("Trident") || header.contains("Edge")){
				URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
				response.setHeader("Content-disposition", "attachment;fileName="+fileName);
			
			}else {
				response.setHeader("Content-disposition", "attachment;fileName="+fileName);
			}
			
			
			FileInputStream in = new FileInputStream(f); // 파일을 버퍼로 읽어봐서 출력한다.
			
			byte[] buffer = new byte[1024*8];
			
			while(true) {
				int count = in.read(buffer); // 바이트 단위로 넣어서 돌리겠다.
				if(count==-1) {
					break;
				}
				sos.write(buffer,0,count);
			}
			
			in.close();
			sos.close();
			
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
	
	public String  getFileName(Part filePart) {
		for(String filePartData :filePart.getHeader("content-Disposition").split(";")) {
			System.out.println(filePartData);
			
			if(filePartData.trim().startsWith("filename")) {
				return filePartData.substring(filePartData.indexOf("=")+1).trim().replace("\"","");
			}
		}
		
		return null;
	}
	
	// request 던지면 헤더 정보에서 ip 주소값을 뽑아낸다.
	
	
	
	
	
public String getUserIp(HttpServletRequest request) throws Exception {
		
        String ip = null;
        //HttpServletRequest request = 
        //((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

        ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-Real-IP"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-RealIP"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        }
        
        if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
        	InetAddress address = InetAddress.getLocalHost();
        	ip=address.getHostAddress();
        }
		
		return ip;
	}

}
