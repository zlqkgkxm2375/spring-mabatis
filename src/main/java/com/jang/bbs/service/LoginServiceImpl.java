package com.jang.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jang.bbs.mapper.LoginMapper;
import com.jang.bbs.model.UserVO;

@Service (value = "loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired //@Resource(name ="loginMapper")
	private LoginMapper loginMapper;
	
	@Override
	public String deleteUser(String userId) {
		return loginMapper.deleteUser(userId);
	}
	
	@Override
	public UserVO findUser(UserVO userVO) {
		return loginMapper.findUser(userVO);
	}

	@Override
	public UserVO findId(UserVO userVO) {
		return loginMapper.findId(userVO);
	}

	@Override
	public UserVO findPass(UserVO userVO) {
		return loginMapper.findPass(userVO);
	}
	@Override
	public int updatePass(UserVO userVO) {
		return loginMapper.updatePass(userVO);
	}

	@Override
	public UserVO getUser(String userId) {
		return loginMapper.getUser(userId);
	}

	@Override
	public int insertUser(UserVO userVO) {
		return loginMapper.insertUser(userVO);
	}

	@Override
	public int updateUser(UserVO userVO) {
		return loginMapper.updateUser(userVO);
	}

}
