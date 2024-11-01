package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.dbcon.Dbconn;
import mvc.vo.BoardVo;
import mvc.vo.CommentVo;
import mvc.vo.Criteria;
import mvc.vo.SearchCriteria;


public class CommentDao {
	
	private Connection conn;  //전역적으로 쓴다 연결객체를....
	private PreparedStatement pstmt;  //쿼리를 실행하기 위한 구문클래스
	
	public CommentDao() {//생성자를 만든다 왜? DB연결하는 DBcomm 객체를 생성하려고... 생성해야 mysql 접속하니깐
		Dbconn db = new Dbconn();
		this. conn = db.getConnection();  //연결해서 구문클래스를 꺼내 연결시킨다.
		
	}
	
	
	public ArrayList<CommentVo> commentSelectAll(int bidx) {
		
		ArrayList<CommentVo> alist = new ArrayList<CommentVo>();
		String sql="SELECT * FROM comment where delyn='N' and bidx= ? ORDER BY cidx DESC";
		ResultSet rs = null; 
		//System.out.println("CommentSelectAll sql ==> "+sql);

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			rs = pstmt.executeQuery();
			//System.out.println("CommentSelectAll rs ==> "+rs);
			
			while(rs.next()){ 
				int cidx = rs.getInt("cidx");
				String ccontents = rs.getString("ccontents");
				String cwriter = rs.getString("cwriter");
				String writeday = rs.getString("writeday");
				String delyn = rs.getString("delyn");
				int midx = rs.getInt("midx");

				
				CommentVo cv = new CommentVo();
				cv.setCidx(cidx);
				cv.setCcontents(ccontents);
				cv.setCwriter(cwriter);
				cv.setWriteday(writeday);
				cv.setDelyn(delyn);
				cv.setMidx(midx);
				
				alist.add(cv); 
				//System.out.println("CommentSelectAll cv ==> "+cv);
				
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
	
	 //게시판 전체 개수 구하기
	/*
	public int boardTotalCount(SearchCriteria scri) {
		
		
		String str = "";
		String keyword = scri.getKeyword();
		String searchType = scri.getSearchType();
		
		 키워드가 존재한다면 like구문을 활용한다
		if(!scri.getKeyword().equals("")) {
			
			str = "and "+searchType+" like concat('%','"+keyword+"','%')";
			
		}  없으면 전체실행되므로 else는 없어도 됨 
		
		
		int value = 0;
		1. 쿼리 만들기
		String sql ="select count(*) as cnt from board where delyn='N'"+str+"";
		2. conn 객체 안에 있는 구문클래스 호출하기
		3. DB 컬럼값을 받는 전용 클래스 ResultSet 호출(ResultSet 특징은 데이터를 그래도 복사하기 때문에 전달이 빠름)
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql); 오류가 생길 수 있으니  try/catch 만들기
			rs = pstmt.executeQuery();
			
			if(rs.next()) {커서를 이동시켜서 첫줄로 옮긴다. ?
				value = rs.getInt("cnt");  지역변수 value 값에 담아서 리턴해서 가져간다.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			 try{  각 객체도 소멸시키고 db연결을 끝는다.
				 rs.close();
				 pstmt.close();
			 	  conn.close(); 여기서 끊어버리면 다른 리스트를 실행할 수 없음
			 }catch(Exception e){
				 e.printStackTrace();
			 } 

		}
		return value;
		
	}
	*/
	
	public int commentInsert(CommentVo cv) {
		int value = 0;
		
		String cwriter  = cv.getCwriter();
		String ccontents  = cv.getCcontents();
		int bidx = cv.getBidx();
		int midx = cv.getMidx();
		String cip = cv.getCip();
	
		String sql = "insert into comment(csubject,ccontents,cwriter,bidx,midx,cip) \r\n"
				+ "value(null,?,?,?,?,?)";
	
		//System.out.println("CommentInsert value==> "+value);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ccontents);
			pstmt.setString(2, cwriter);
			pstmt.setInt(3, bidx);
			pstmt.setInt(4, midx);
			pstmt.setString(5, cip);
			value = pstmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			 try{  //각 객체도 소멸시키고 db연결을 끝는다.
				 pstmt.close();
			 	  conn.close(); 
			 }catch(Exception e){
				 e.printStackTrace();
			 } 
		}
		return value;

	}
	

	public int commentDelete(int cidx) {
		int value = 0;
		String sql = "update comment SET delyn = \"Y\" where cidx = ? ";
		
		
		try {
			pstmt =  conn.prepareStatement(sql);
			pstmt.setInt(1, cidx);
			value = pstmt.executeUpdate();  //성공하면 1, 실패하면 0 
			
			
		} catch (SQLException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			 try{  //각 객체도 소멸시키고 db연결을 끝는다.
				 pstmt.close();
			 	 conn.close();   //게시글 조회하기 메서드 실행해야되서 conn 연결은 끊지 않는다

			 }catch(Exception e){
				 e.printStackTrace();
			 } 
		}

		return value;
	}

}
	
	
