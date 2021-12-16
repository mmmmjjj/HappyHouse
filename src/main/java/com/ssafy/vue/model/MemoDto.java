package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MemoDto : 답변정보", description = "답변 정보를 나타낸다.")
public class MemoDto {
	private int memono;
	private String userid;
	private String comment;
	private String memotime;
	private int articleno;
	public MemoDto() {
		super();
	}
	public MemoDto(String userid, String comment, int articleno) {
		super();
		this.userid = userid;
		this.comment = comment;
		this.articleno = articleno;
	}
	public MemoDto(int memono, String userid, String comment, String memotime, int articleno) {
		super();
		this.memono = memono;
		this.userid = userid;
		this.comment = comment;
		this.memotime = memotime;
		this.articleno = articleno;
	}
	public MemoDto(String userid, String comment, String memotime, int articleno) {
		super();
		this.userid = userid;
		this.comment = comment;
		this.memotime = memotime;
		this.articleno = articleno;
	}
	public int getMemono() {
		return memono;
	}
	public void setMemono(int memono) {
		this.memono = memono;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getMemotime() {
		return memotime;
	}
	public void setMemotime(String memotime) {
		this.memotime = memotime;
	}
	public int getArticleno() {
		return articleno;
	}
	public void setArticleno(int articleno) {
		this.articleno = articleno;
	}
	@Override
	public String toString() {
		return "MemoDto [memono=" + memono + ", userid=" + userid + ", comment=" + comment + ", memotime=" + memotime
				+ ", articleno=" + articleno + "]";
	}
	
	
}