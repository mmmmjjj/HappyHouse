package com.ssafy.vue.model;


import java.util.List;

public class HospitalDto {
	private int no;
	private String date;
	private String sido;
	private String gungu;
	private String name;
	private String add;
	private String type;
	public HospitalDto(int no, String date, String sido, String gungu, String name, String add, String type) {
		super();
		this.no = no;
		this.date = date;
		this.sido = sido;
		this.gungu = gungu;
		this.name = name;
		this.add = add;
		this.type = type;
	}
	public HospitalDto(int no, String sido, String gungu, String name, String add, String type) {
		super();
		this.no = no;
		this.sido = sido;
		this.gungu = gungu;
		this.name = name;
		this.add = add;
		this.type = type;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getGungu() {
		return gungu;
	}
	public void setGungu(String gungu) {
		this.gungu = gungu;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "HospitalDto [no=" + no + ", date=" + date + ", sido=" + sido + ", gungu=" + gungu + ", name=" + name
				+ ", add=" + add + ", type=" + type + "]";
	}
	
}
