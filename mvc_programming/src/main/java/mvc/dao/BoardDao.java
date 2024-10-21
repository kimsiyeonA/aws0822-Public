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
				
				BoardVo bv = new BoardVo();
				bv.setBidx(bidx);
				bv.setSubject(subject);
				bv.setWriter(writer);
				bv.setViewcnt(viewcnt);
				bv.setWriterday(writerday);
				
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

}
