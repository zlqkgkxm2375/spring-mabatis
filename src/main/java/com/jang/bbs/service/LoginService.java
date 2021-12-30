package com.jang.bbs.service;

import org.apache.catalina.User;

import com.jang.bbs.model.UserVO;

public interface LoginService {
	
	UserVO findUser (UserVO userVO);
	
	UserVO getUser (String userId);
	
	int insertUser(UserVO userVO);
	
	int updateUser(UserVO userVO);
	
	String deleteUser(String userId);
	
	UserVO findId(UserVO userVO);
    UserVO findPass(UserVO userVO);
    int updatePass(UserVO userVO);

}
