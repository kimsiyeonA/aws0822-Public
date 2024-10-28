
package mvc.vo;

// 페이지 하단에 페이징 네비게이션에 필요한 변수들을 담아놓은 클래스
public class PageMaker {
	
	private int diplayPageNum = 10; //페이지 목록번호 리스트 1,2,3,4,5,6,7,8,9,10 /기본값으로 10page까지 보여주기 위해서 10을 기본값으로 넣음
	private int startPage; //목록의 시작번호를 담는 변수
	private int endPage; // 목록의 끝번호를 담는 변수
	private int totalCount; // 총 게시물 수를 담는 변수
	
 	private boolean prev; // 이전 버튼 ◀
 	private boolean next; // 다음 버튼 ▶
 	
 	private SearchCriteria scri;

	public int getDiplayPageNum() {
		return diplayPageNum;
	}

	public void setDiplayPageNum(int diplayPageNum) {
		this.diplayPageNum = diplayPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) { // 총 게시물이 몇개인지 묻는 메소드
		this.totalCount = totalCount;
		calcData(); // 페이지 목록 리스트 번호를 나타내주기 위한 계산식

		
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public SearchCriteria getScri() {
		return scri;
	}

	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}

	
	private void calcData() { // 1 -10 /11- 20으로 설정하기 위한 메소드
		
		// 1. 기본적에서 1에서부터 10까지 나타나게 설정한다.(페이지 네비게이션에서) 페이지의 숫자가 변해도 10까지 유지할 수 있음
		endPage = (int)(Math.ceil(scri.getPage()/(double)diplayPageNum)*diplayPageNum); 
		// 모두 올림처리하는 메소드 Math.ceil() Math.ceil(scri.getPage()/(double)diplayPageNum)-> 0.1,0.2, 올림 ->1
		// diplayPageNum => 10 곱해서 정수형 만들기
		
		// 2. endPage가 설정되었으면 시작페이지도 설정
		startPage = (endPage-diplayPageNum)+1;
		
		// 3. 실제 게시물 수에 따라서 endPage를 구한다.
		// 15개로 나누어서 보여준다 
		int tempEndPage = (int)(Math.ceil(totalCount/(double)scri.getPerPageNum()));
		
		// 4. 설정한 endPage와 실제 endPage를 비교해서 최종 endPage를 구한다.
		if(endPage > tempEndPage) {
			endPage = tempEndPage; // 설정값을 실제값으로 맟춘다.
		}
		
		// 5. 이전 다음버튼 만들기
		prev=(startPage == 1? false : true); // 삼항연산자 사용
		next=(endPage*scri.getPerPageNum() >= totalCount? false : true);
	}
}
