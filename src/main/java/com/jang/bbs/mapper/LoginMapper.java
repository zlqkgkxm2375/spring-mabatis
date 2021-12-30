package com.jang.bbs.mapper;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

import com.jang.bbs.model.UserVO;

@Mapper
public interface LoginMapper {
	
	UserVO findUser(UserVO userVO);
	
	UserVO getUser(String userId); // ID중복확인,정보수정 login
	
	int updateUser(UserVO userVO); // 정보수정
	
	int insertUser(UserVO userVO); //회원가입
	
	String deleteUser(String userId);

	
	//기말고사시험 밑에 주석처리해놓은것 
	//수업시간에 안하고 우리가알아서하라했음
	
	 UserVO findId(UserVO userVO);
	 UserVO findPass(UserVO userVO);
	 int updatePass(UserVO userVO);
	 /*void deleteUser(UserVO userVO);
	 */

	
}
