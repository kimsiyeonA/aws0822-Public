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
import mvc.dao.CommentDao;
import mvc.vo.CommentVo;
import mvc.vo.Criteria;
import mvc.vo.PageMaker;
import mvc.vo.SearchCriteria;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;




@WebServlet("/CommentController")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String location; // 맴버변수 (전역) 초기화 => 이동할 페이지
	// 여기에는 3번째의 주소가 들어가 있다
	// 아래 생성자에게서 받아서
	
	public CommentController(String location) {
		this.location = location;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//request.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html;charset=UTF-8");
		
		
		// 에이작스를 사용할 거라 필요없음
		//String paramMethod=""; 
		//String url="";
		
		if(location.equals("commentList.aws")) { // 댓글 목록 화면 뿌려주기 용
			
			String bidx = request.getParameter("bidx");
			
			CommentDao cd = new CommentDao();
			
			ArrayList<CommentVo> alist = cd.commentSelectAll(Integer.parseInt(bidx));
			
			int cidx=0;
			String cwriter="";
			String ccontents="";
			String writeday="";
			String delyn="";
			int midx=0;
			String str = ""; // 에이작스 담기
			/*
			for(int i=0;i<alist.size();i++) {
				cidx=alist.get(i).getCidx();
				cwriter=alist.get(i).getCwriter();
				ccontents=alist.get(i).getCcontents();
				writeday=alist.get(i).getWriteday();
				delyn=alist.get(i).getDelyn();
				str = str + "{ \"cidx\" : \""+cidx+" \", \"cwriter\" : \""+cwriter+"\", \"ccontents\":\""+ccontents+"\",\"writeday\":\""+writeday+"\",\"delyn\":\""+delyn+"\" }";
			    //str = str + "{ \"cidx\" : \""+cidx+" \", \"cwriter\" : \""+cwriter+"\", \"ccontents\":\""+ccontents+"\",\"writeday\":\""+writeday+"\", \"delyn\":\""+delyn+"\" }";
				// { "a": "1","b": "2", "c": "3"}
			}
			*/
			for(int i=0;i<alist.size();i++) {
				
				cidx = alist.get(i).getCidx();
				cwriter = alist.get(i).getCwriter();
			    ccontents = alist.get(i).getCcontents();
			    writeday = alist.get(i).getWriteday();
			    delyn = alist.get(i).getDelyn();
			    midx = alist.get(i).getMidx();
			    
			    String cma = "";
			    if (i == alist.size()-1 ) {
			    	cma="";
			    }else {
			    	cma=",";
			    }
			    
			    
			    str = str + "{ \"cidx\" : \""+cidx+" \", \"cwriter\" : \""
			    +cwriter+"\", \"ccontents\":\""+ccontents+"\",\"writeday\":\""+writeday+"\",\"delyn\":\""+delyn+"\",\"midx\":\\"+midx+"\" }"+cma;
			    
			}
			
			PrintWriter out = response.getWriter();
			out.println("["+str+"]");// 대괄호를 포함하여 가지고 온다,
					
		
		}else if(location.equals("commentWriteAction.aws")){
			
			String cwriter = request.getParameter("cwriter");
			//System.out.println("commentWriteAction cwriter==> "+cwriter);
			String ccontents = request.getParameter("ccontents");
			//System.out.println("commentWriteAction ccontents==> "+ccontents);
			String bidx = request.getParameter("bidx");
			//System.out.println("commentWriteAction bidx==> "+bidx);
			String midx = request.getParameter("midx");
			//System.out.println("commentWriteAction midx==> "+midx);
			
			CommentVo cv = new CommentVo();
			cv.setCwriter(cwriter);
			cv.setCcontents(ccontents);
			cv.setBidx(Integer.parseInt(bidx));
			cv.setMidx(Integer.parseInt(midx));
			//System.out.println("commentWriteAction cv==> "+cv);
			
			//Comment 객체 생성
			CommentDao cd = new CommentDao();
			//System.out.println("commentWriteAction cd==> "+cd);
			int value = cd.commentInsert(cv);
			//System.out.println("commentWriteAction value==> "+value);
			
			PrintWriter out =response.getWriter();
			
			String str = "{\"value\":\""+value+"\"}";
			out.print(str);
			
		
		}else if(location.equals("commentDelectAction.aws")){

			String cidx = request.getParameter("cidx");
			System.out.println("commentDelectAction cidx==>>"+cidx);
			//delyn y로 업데이트 하는 메소드를 만들어서 호출한다.
			CommentDao cd = new CommentDao();
			int value = cd.commentDelete(Integer.parseInt(cidx));
			
			
			//그리고 나서 화면에 실행성공여부를 json 파일로 보여준다.
			PrintWriter out =response.getWriter();
			
			String str = "{\"value\":\""+value+"\"}";
			out.print(str);
		}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	
	
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
