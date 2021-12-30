package com.jang.bbs.model;

import javax.validation.constraints.NotEmpty;

public class UserVO {
	  private int mno;
	  @NotEmpty(message="이름을 입력하세요!")
	  private String name;
	  @NotEmpty(message="패스워드를 입력하세요!")
	  private String passwd;
	  @NotEmpty(message="ID를 입력하세요!")
	  private String userId;
	  @NotEmpty(message="생년월일을 입력하세요!")
	  private String birthday;
	  @NotEmpty(message="이메일을 입력하세요!")
	  private String email;
	  private String regDate;
	  @NotEmpty(message="핸드폰번호를 입력하세요!")
	  private String phone;
	  @NotEmpty(message="우편번호를 입력하세요!")
	  private  String zip;
	  @NotEmpty(message="주소를 입력하세요!")
	  private  String addr1;
	  @NotEmpty(message="주소를 입력하세요!")
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
