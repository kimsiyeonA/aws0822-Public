package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.dbcon.Dbconn;
import mvc.vo.MemberVo;

//데이터에 접근하는 객체 (메소드가 들어가져 잇는 클래스)
public class MemberDao { // MVC방식으로 가기전에 첫번째 model1 방식
	
	private Connection conn; // 전연변수로 사용, 페이지 어느곳에서도 사용할 수 있다.
	private PreparedStatement pstmt;
	//생성자를 통해서 db연결해서 메서드 사용

	
	public MemberDao(){
		Dbconn dbconn = new Dbconn();//DB객체 생성
		conn = dbconn.getConnection(); // 메소드 호출해서 연결객체를 가져온다.
	}
	
	
	public int memberInsert(String memberId, String memberPwd, 
			 String memberName, String memberGender, String memberBirth, 
			 String memberAddr, String memberPhone, String memberEmail, String memberInHobby){
		 
		 int value = 0; // 메소드 지역변수 결과값을 담는다.
		 String sql = "";
		 //PreparedStatement pstmt = null;// 쿼리 구문 클래스 선언
		 
		 try{
				sql = "insert into member(memberid,memberpwd,membername,membergender,"
					 +"memberbirth,memberaddr,memberphone,memberemail,memberhobby) "
					 +"values(?,?,?,?,?,?,?,?,?)";
					 pstmt =conn.prepareStatement(sql);
					 pstmt.setString(1,memberId);		// 문자형 메소드 사용
					 pstmt.setString(2,memberPwd);		// 문자형 메소드 사용 숫자형 setInt(번호, 값);
					 pstmt.setString(3,memberName);
					 pstmt.setString(4,memberGender);
					 pstmt.setString(5,memberBirth);
					 pstmt.setString(6,memberAddr);
					 pstmt.setString(7,memberPhone);
					 pstmt.setString(8,memberEmail);
					 pstmt.setString(9,memberInHobby);
					 value = pstmt.executeUpdate(); // 구문객체 실행하면 성공시 1. 실패시 0
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{// try했던 catch했던 꼭 실행해야하는 영역
			 // 객체 사라지게 하고 
			 // db연결 끊기
			 try{
				 pstmt.close();
			 	 conn.close();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
		 
		 return value; // 
	 }
	
	// ?ResultSet / prepareStatement
	// 로그인을 통해서 회원정보를 담아오는 메소드이다.
	public MemberVo memberLogincheck(String memberId, String memberPwd) {
		MemberVo mv = null;
		String sql = "select * from member where memberid=? and memberpwd=?";
		//디비에 가서 맞는 쿼리인지 확인하고 붙여 넣기
		ResultSet rs = null; // db에서 결과 데이터를 받아오는 전용클래스
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId); // 배열이 아니기 때문에 첫번째 물음표에 집어넣겠다.
			pstmt.setString(2, memberPwd);// 두번째 물음표에 집어넣겠다.
			rs = pstmt.executeQuery(); // 데이터베이스 안에 있는 커서가 어디행으로 갈지 정해야한다.
			
			if(rs.next()==true) { //커서가 이동해서 데이터 값이 있으면if(rs.next())와 같은 표현
				String memberid = rs.getString("memberid");	// 결과값에서 아이디 값을 뽑는다
				String membername = rs.getString("membername");	
				int midx = rs.getInt("midx");					// 결과값에서 회원번호를 뽑는다.
				
				mv = new MemberVo();		// 화면에 가지고갈 데이터를 담을 vo객체 생성
				mv.setMemberid(memberid); 	// 옮겨 담는다.
				mv.setMidx(midx);
				mv.setMembername(membername);
			}
			
		}catch(Exception e){
			 e.printStackTrace();
		 }finally{// try했던 catch했던 꼭 실행해야하는 영역
			 // 객체 사라지게 하고 
			 // db연결 끊기
			 try{
				 rs.close();
				 pstmt.close();
			 	 conn.close();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
		 
		 return mv; // 
	}
	
	public ArrayList<MemberVo> memberSelectAll() {
		
		ArrayList<MemberVo> alist = new ArrayList<MemberVo>();
		String sql="select * from member where delyn='n'order by midx desc";
		// 실행할 쿼리 붙여 넣기 > 구문 클래스 필요
		ResultSet rs = null; 
		// db에서 결과 데이터를 받아오는 전용클래스 ?
		try {
			pstmt = conn.prepareStatement(sql);//>conn에 있는 구문 클래스 불러오기 ? > 트라이 캐치로 넣어주기
			rs = pstmt.executeQuery();
			while(rs.next()){ 
				// 커서가 다음으로 이동해서 첫 글이 있느냐 물어보고 true이먼 진행 -> if(rs.next()==true) 
				// while문으로 커서가 있으면 옮겨 담을 수 있게 만듬
				// 더 담을 값이 없으면 falus를 리턴함
				int midx = rs.getInt("midx");
				String memberId = rs.getString("memberid");
				String memberName = rs.getString("membername");
				String memberGender = rs.getString("membergender");
				String writeday = rs.getString("writeday");
				
				MemberVo mv = new MemberVo(); // 첫행부터 mv에 옮겨담기
				mv.setMidx(midx);
				mv.setMemberid(memberId);
				mv.setMembername(memberName);
				mv.setMembergender(memberGender);
				mv.setWriteday(writeday);
				
				alist.add(mv); // ArrayList 객체에 하나씩 추가한다.
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 try{
				 rs.close();
				 pstmt.close();
			 	 conn.close();
			 }catch(Exception e){
				 e.printStackTrace();
			 } 
			 // 이걸 트라이 케치에 넣는 이유 알아보기
			 // 끝나면 가지고 있던 객체와 다른 정보 요소들을 소멸시키기
		}
		return alist;
	}
	//여러개 나올땐 ArrayList, 아닐때는 객체로
	


	public int memberIdCheck(String memberId) {
		String sql = "select count(*)as cnt from member where memberid=?";
		//디비에 가서 맞는 쿼리인지 확인하고 붙여 넣기
		ResultSet rs = null; // db에서 결과 데이터를 받아오는 전용클래스
		int cnt = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId); // 배열이 아니기 때문에 첫번째 물음표에 집어넣겠다.
			rs = pstmt.executeQuery(); // 데이터베이스 안에 있는 커서가 어디행으로 갈지 정해야한다.
			
			if(rs.next()) { //커서가 이동해서 데이터 값이 있으면if(rs.next())와 같은 표현
				cnt = rs.getInt("cnt");					
			}
			
		}catch(Exception e){
			 e.printStackTrace();
		 }finally{// try했던 catch했던 꼭 실행해야하는 영역
			 // 객체 사라지게 하고 
			 // db연결 끊기
			 try{
				 rs.close();
				 pstmt.close();
			 	 conn.close();
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
		 
		 return cnt; // 
	}
}
