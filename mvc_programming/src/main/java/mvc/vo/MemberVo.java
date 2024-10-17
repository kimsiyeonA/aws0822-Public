package mvc.vo;

// 컬럼의 값을 가지고 오는 클래스 , 데이터 전송하는 용도 트랜스퍼
public class MemberVo { // Vo : value Object 값을 담는 객체다 또는 DTO라고도 한다.

	private int midx;				//DB테이블에 있는 컬럼이름과 동일하게 작성한다.
	private String memberid;		// 바인딩 기술을 사용하기 위해 
	private String memberpwd;		// Html input name 명과 동일하게 맞춘다.
	private String membername;		
	private String membergender;
	private String memberbirth;
	private String memberaddr;
	private String memberphone;
	private String memberemail;
	private String memberhobby;
	private String delyn;
	private String writeday;
	private String memberip;
	
	
	public int getMidx() {
		return midx;
	}
	public void setMidx(int midx) {
		this.midx = midx;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getMemberpwd() {
		return memberpwd;
	}
	public void setMemberpwd(String memberpwd) {
		this.memberpwd = memberpwd;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getMembergender() {
		return membergender;
	}
	public void setMembergender(String membergender) {
		this.membergender = membergender;
	}
	public String getMemberbirth() {
		return memberbirth;
	}
	public void setMemberbirth(String memberbirth) {
		this.memberbirth = memberbirth;
	}
	public String getMemberaddr() {
		return memberaddr;
	}
	public void setMemberaddr(String memberaddr) {
		this.memberaddr = memberaddr;
	}
	public String getMemberphone() {
		return memberphone;
	}
	public void setMemberphone(String memberphone) {
		this.memberphone = memberphone;
	}
	public String getMemberemail() {
		return memberemail;
	}
	public void setMemberemail(String memberemail) {
		this.memberemail = memberemail;
	}
	public String getMemberhobby() {
		return memberhobby;
	}
	public void setMemberhobby(String memberhobby) {
		this.memberhobby = memberhobby;
	}
	public String getDelyn() {
		return delyn;
	}
	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}
	public String getWriteday() {
		return writeday;
	}
	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}
	public String getMemberip() {
		return memberip;
	}
	public void setMemberip(String memberip) {
		this.memberip = memberip;
	}

	
}