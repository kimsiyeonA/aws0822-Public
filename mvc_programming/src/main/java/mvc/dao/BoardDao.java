package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.dbcon.Dbconn;
import mvc.vo.BoardVo;
import mvc.vo.Criteria;


public class BoardDao {
	
	private Connection conn; // 전역적으로 쓴다 연결객체를....
	private PreparedStatement pstmt; // 쿼리를 실행하기 위한 구문클래스
	
	public BoardDao() {//생성자를 만든다 왜? DB연결하는 DBcomm 객체를 생성하려고... 생성해야 mysql 접속하니깐
		Dbconn db = new Dbconn();
		this. conn = db.getConnection(); // 연결해서 구문클래스를 꺼내 연결시킨다.
		
	}
	
	
	public ArrayList<BoardVo> boardSelectAll(Criteria cri) {//생성자를 만든다 왜? DB연결하는 DBcomm 객체를 생성하려고... 생성해야 mysql 접속하니깐
		
		int page = cri.getPage(); // 페이지 번호
		int perPageNum = cri.getPerPageNum();//화면 노출 리스트 개수
		
		
		//System.out.println("9999");
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>(); // ArrayList 컬랙션 객체에 BoardVo를 담겠다.BoardVo는 컬럼값을 담겠다
		String sql="SELECT * FROM board ORDER BY originbidx DESC, depth ASC limit ?, ?";
		ResultSet rs = null; 

		//System.out.println("1234");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (page-1)*perPageNum);
			pstmt.setInt(2, perPageNum);
			
			rs = pstmt.executeQuery();
			//System.out.println("2222");
			while(rs.next()){ 
				int bidx = rs.getInt("bidx");
				String subject = rs.getString("subject");
				String writer = rs.getString("writer");
				int viewcnt = rs.getInt("viewcnt");
				String writerday = rs.getString("writerday");
				int recom = rs.getInt("recom");
				
				BoardVo bv = new BoardVo();
				bv.setBidx(bidx);
				bv.setSubject(subject);
				bv.setWriter(writer);
				bv.setViewcnt(viewcnt);
				bv.setWriterday(writerday);
				bv.setRecom(recom);
				
				alist.add(bv); 
				//System.out.println(bv);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			 try{
				 rs.close();
				 pstmt.close();
			 	 conn.close();
			 }catch(Exception e){
				 e.printStackTrace();
			 } 

		}
		return alist;
	}
	
	// 게시판 전체 개수 구하기
	public int boardTotalCount() {
		int value = 0;
		//1. 쿼리 만들기
		String sql ="select count(*) as cnt from board where delyn='N'";
		//2. conn 객체 안에 있는 구문클래스 호출하기
		//3. DB 컬럼값을 받는 전용 클래스 ResultSet 호출(ResultSet 특징은 데이터를 그래도 복사하기 때문에 전달이 빠름)
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);// 오류가 생길 수 있으니  try/catch 만들기
			rs = pstmt.executeQuery();
			
			if(rs.next()) {//커서를 이동시켜서 첫줄로 옮긴다. ?
				value = rs.getInt("cnt"); // 지역변수 value 값에 담아서 리턴해서 가져간다.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			 try{ // 각 객체도 소멸시키고 db연결을 끝는다.
				 rs.close();
				 pstmt.close();
			 	 // conn.close(); 여기서 끊어버리면 다른 리스트를 실행할 수 없음
			 }catch(Exception e){
				 e.printStackTrace();
			 } 

		}
		return value;
	}
	
	public int boardInsert(BoardVo bv) {
		int value = 0;
		
		String subject = bv.getSubject();
		String contents = bv.getContents();
		String writer = bv.getWriter();
		String password = bv.getSubject();
		int midx = bv.getMidx();
		
		String sql = "insert into board(originbidx,depth,level_,subject,contents,writer,password,midx) \r\n"
				+ "value(null,0,0,?,?,?,?,?)";
		String sql2="update board \r\n"
				+ "set originbidx = (select * from (select max(bidx) from board) as temp) \r\n"
				+ "where bidx = (select * from (select max(bidx) from board) as temp)";
		
		try {
			conn.setAutoCommit(false); // false : 수동커밋으로 하겠다.
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, contents);
			pstmt.setString(3, writer);
			pstmt.setString(4, password);
			pstmt.setInt(5, midx);
			int exec = pstmt.executeUpdate(); // 실행되면 1 안되면 0
			
			pstmt = conn.prepareStatement(sql2);
			int exec2 = pstmt.executeUpdate(); // 실행되면 1 안되면 0
			
			conn.commit();
			conn.setAutoCommit(true);
			
			value = exec+exec2;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			 try{ // 각 객체도 소멸시키고 db연결을 끝는다.
				 pstmt.close();
			 	 // conn.close(); 여기서 끊어버리면 다른 리스트를 실행할 수 없음
			 }catch(Exception e){
				 e.printStackTrace();
			 } 

		}
		return value;

	}
	
	public BoardVo  boardSelectOne(int bidx) {
		// 1. 형식부터 만든다.
		BoardVo bv = null;
		// 2. 사용할 쿼리를 준비한다
		String sql = "select * from board where delyn='N' and bidx=?";
		
		ResultSet rs = null;
		
		//System.out.println("boardSelectOners" + rs);
		try {
			// 3. conn 연결객체에서 쿼리실행 구문 클래스를 불러온다.
			pstmt = conn.prepareStatement(sql);//맴버변수(전역변수)로 선언한 prepareStatement 객체로 담음
			pstmt.setInt(1, bidx); // 첫번째 물음표에 bidx값을 담아서 구문을 실행한다.
			rs = pstmt.executeQuery();// 쿼리를 실행해서 결과값을 컬럼 전용클래스의 ResultSet객체에 담는다(복사기능)
			
			if(rs.next()==true) {//rs.next()는 커서를 다음줄로 이동시킨다 맨처음 커서는 상단위에 위치되어있다.
				// 값이 존재한다면 boardVo에 담는다.
				String subject = rs.getString("subject");
				String contents = rs.getString("contents");
				String writer = rs.getString("writer");
				String writerday = rs.getString("writerday");
				int viewcnt = rs.getInt("viewcnt");
				int recom = rs.getInt("recom");
				String filename = rs.getString("filename");
				int rtnbidx = rs.getInt("bidx");
				int originbidx = rs.getInt("originbidx");
				int depth = rs.getInt("depth");
				int level_ = rs.getInt("level_");
				String password = rs.getString("password");
				
				bv = new BoardVo(); 
				// 안에서 객체를 생성하면 블럭이 끝날때 객체가 사라지므로 밖에 있는 지역변수를 사용한다.
				// 객체 생성해서 지역변수 bv로 담아서 리턴해서 가져간다.
				bv.setSubject(subject);
				bv.setContents(contents);
				bv.setWriter(writer);
				bv.setWriterday(writerday);
				bv.setViewcnt(viewcnt);
				bv.setRecom(recom);
				bv.setFilename(filename);
				// 수정, 삭제, 답변에 쓸 데이터도 꺼내기
				bv.setBidx(rtnbidx);
				bv.setOriginbidx(originbidx);
				bv.setDepth(depth);
				bv.setLevel_(level_);
				bv.setPassword(password);
				//System.out.println("boardSelectOne bv " + bv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 try{ // 각 객체도 소멸시키고 db연결을 끝는다.
				 rs.close();
				 pstmt.close();
			 	conn.close(); // 자원 관리를 위해 연결을 끊음
			 }catch(Exception e){
				 e.printStackTrace();
			 } 

		}
		
	
		return bv;
	}
	
	public int boardUpdate(BoardVo bv) {
		
		int value = 0;
		String sql = "update board set subject=?,contents=?,writer=?,modifyday=now() where bidx=? and password=? ";
		try {
			pstmt = conn.prepareStatement(sql);//맴버변수(전역변수)로 선언한 prepareStatement 객체로 담음
			pstmt.setString(1, bv.getSubject()); 
			pstmt.setString(2, bv.getContents()); 
			pstmt.setString(3, bv.getWriter()); 
			pstmt.setInt(4, bv.getBidx()); 
			pstmt.setString(5, bv.getPassword()); 
			value = pstmt.executeUpdate(); // 바로 업데이트 시키는 구문 객체
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			 try{ // 각 객체도 소멸시키고 db연결을 끝는다.
				 pstmt.close();
			 	 conn.close(); // 자원 관리를 위해 연결을 끊음
			 }catch(Exception e){
				 e.printStackTrace();
			 } 
		}
		return value;
	}

	public int boardViewCntUpdate(int bidx) {
		int value = 0;
		String sql = "update board set viewcnt = viewcnt + 1 where bidx=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			value = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			 try{ // 각 객체도 소멸시키고 db연결을 끝는다.
				 pstmt.close();
			 	 //conn.close();  // 게시글 조회하기 메서드 실행해야되서 conn 연결은 끊지 않는다

			 }catch(Exception e){
				 e.printStackTrace();
			 } 
		}

		return value;
	}
	
	public int boardRecomUpdate(int bidx) {
		int value = 0;
		int recom = 0;
		ResultSet rs = null;
		String sql = "update board set recom = recom + 1 where bidx=?";
		String sql2 = "select recom from board where bidx=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			value = pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, bidx);
			rs = pstmt.executeQuery();
			//rs에 결과를 담아서 보냄
				if(rs.next()) {//rs에 데이터가 있으면 꺼내기 
					recom = rs.getInt("recom");
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			 try{ // 각 객체도 소멸시키고 db연결을 끝는다.
				 pstmt.close();
			 	 //conn.close();  // 게시글 조회하기 메서드 실행해야되서 conn 연결은 끊지 않는다

			 }catch(Exception e){
				 e.printStackTrace();
			 } 
		}
		System.out.println("dao recom"+recom);
		
		return recom; // recom 리턴
		
	}
}
