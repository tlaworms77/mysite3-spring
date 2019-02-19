package com.douzone.mysite.vo;

public class PageVo {
	private int pageSize;	// 페이지갯수
	private int firstPageNo; // 첫번째 페이지 번호
	private int prevPageNo; // 이전 페이지 번호
	private int startPageNo; // 시작 페이지 번호
	private int pageNo; // 페이지번호
	private int endPageNo; // 끝 페이지
	private int nextPageNo; // 다음 페이지 번호
	private int finalPageNo; // 마지막 페이지 번호
	private int totalCount; // 게시 글 전체 수
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getFirstPageNo() {
		return firstPageNo;
	}
	public void setFirstPageNo(int firstPageNo) {
		this.firstPageNo = firstPageNo;
	}
	public int getPrevPageNo() {
		return prevPageNo;
	}
	public void setPrevPageNo(int prevPageNo) {
		if(prevPageNo < 2) {
			prevPageNo = 1;
		} else {
			prevPageNo--;
		}
		this.prevPageNo = prevPageNo;
	}
	public int getStartPageNo() {
		return startPageNo;
	}
	public void setStartPageNo(int pageNo) {
		if(pageNo<3)
			pageNo = 1;
		else 
			pageNo = pageNo-2;
		this.startPageNo = pageNo;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getEndPageNo() {
		return endPageNo;
	}
	public void setEndPageNo(int pageNo) {
		if(pageNo+2 == this.pageSize)
			pageNo = this.pageSize;
		else if(pageNo < 3)
			pageNo = 5;
		else 
			pageNo = pageNo+2;
		this.endPageNo = pageNo;
	}
	public int getNextPageNo() {
		return nextPageNo;
	}
	public void setNextPageNo(int pageNo) {
		
		this.nextPageNo = pageNo + 1;
		
	}
	public int getFinalPageNo() {
		return finalPageNo;
	}
	public void setFinalPageNo(int finalPageNo) {
		this.finalPageNo = finalPageNo;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public void pageSetting(int totalCount){
		this.pageSize = 10;
		this.totalCount = totalCount;
		
		
	}
	
	
}
