package mvc.dbcon;

import java.sql.Connection;
import java.sql.DriverManager;

// db 연결 하는 클래스
public class Dbconn {
	// myspl 접속정보
	 private Connection conn; // 맴버 변수는 선언만해도 자동 초기화 되므로 널을 없앰 /Connection import
	 private String url="jdbc:mysql://127.0.0.1/aws0822?serverTimezone=UTC";
	 private String user="root";
	 private String password="1234";
	 
	 public Connection getConnection() { // try catch로 이쪽에서 오류 해결 -> 함수화
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		 System.out.println("객체 생성 연결 확인==> "+conn);
		 return conn; 
		 // 연결 객체가 생성되었을 때의 객체정보를 담고있는 객체 참조 변수 , 
		 // null이면 연결객체가 생성되지 않았다 mysql과 연결할 수 없음
	 }
// driver 등록
}
// jsp를 클래스로 바꾸기보다 클래스 파일을 만들어서 연결해서 속도가 빨라지게 만듬