package mvc.vo;

// 페이징을 하기 위해서 기준이 되는 데이터를 담는 클래스
public class Criteria {
	
	private int page=1; // 페이지 번호를 담는 변수, 기본 화면에 지정할 페이지 수 기본값 지정
	private int perPageNum=15; // 화면에 리스트 게시물 수 num/page, 기본 화면에 페이지 목록 기본값 지정
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
	
	
}
