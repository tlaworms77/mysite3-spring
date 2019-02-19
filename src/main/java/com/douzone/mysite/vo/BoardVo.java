package com.douzone.mysite.vo;

import java.util.Date;

public class BoardVo {
	private long no;
	private String title;
	private String contents;
	private Date writeDate;
	private int hit;
	private int groupNo;
	private int orderNo;
	private int depth;
	
	private UserVo userNo;
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public UserVo getUserNo() {
		return userNo;
	}
	public void setUserNo(UserVo userVo) {
		this.userNo = userVo;
	}
	
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents + ", writeDate=" + writeDate
				+ ", hit=" + hit + ", groupNo=" + groupNo + ", orderNo=" + orderNo + ", depth=" + depth + ", userNo="
				+ userNo + "]";
	}
	
}
