package com.jang.bbs.model;

import javax.validation.constraints.NotEmpty;

public class UserVO {
	  private int mno;
	  @NotEmpty(message="�̸��� �Է��ϼ���!")
	  private String name;
	  @NotEmpty(message="�н����带 �Է��ϼ���!")
	  private String passwd;
	  @NotEmpty(message="ID�� �Է��ϼ���!")
	  private String userId;
	  @NotEmpty(message="��������� �Է��ϼ���!")
	  private String birthday;
	  @NotEmpty(message="�̸����� �Է��ϼ���!")
	  private String email;
	  private String regDate;
	  @NotEmpty(message="�ڵ�����ȣ�� �Է��ϼ���!")
	  private String phone;
	  @NotEmpty(message="�����ȣ�� �Է��ϼ���!")
	  private  String zip;
	  @NotEmpty(message="�ּҸ� �Է��ϼ���!")
	  private  String addr1;
	  @NotEmpty(message="�ּҸ� �Է��ϼ���!")
	  private String addr2;
	  private char del_yn='n';
	  
	  
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public char getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(char del_yn) {
		this.del_yn = del_yn;
	}
	  
	  
}
