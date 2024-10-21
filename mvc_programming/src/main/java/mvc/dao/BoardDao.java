package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.dbcon.Dbconn;
import mvc.vo.BoardVo;


public class BoardDao {
	
	private Connection conn; // 전역적으로 쓴다 연결객체를....
	private PreparedStatement pstmt;
	
	public BoardDao() {//생성자를 만든다 왜? DB연결하는 DBcomm 객체를 생성하려고... 생성해야 mysql 접속하니깐
		Dbconn db = new Dbconn();
		this. conn = db.getConnection(); // 연결해서 구문클래스를 꺼내 연결시킨다.
		
	}
	
	
	public ArrayList<BoardVo> boardSelectAll() {//생성자를 만든다 왜? DB연결하는 DBcomm 객체를 생성하려고... 생성해야 mysql 접속하니깐
		//System.out.println("9999");
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>(); // ArrayList 컬랙션 객체에 BoardVo를 담겠다.BoardVo는 컬럼값을 담겠다
		String sql="SELECT * FROM board ORDER BY originbidx DESC, depth ASC";
		ResultSet rs = null; 

		//System.out.println("1234");
		try {
			pstmt = conn.prepareStatement(sql);
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

}
