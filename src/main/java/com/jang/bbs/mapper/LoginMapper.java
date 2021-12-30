package com.jang.bbs.mapper;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

import com.jang.bbs.model.UserVO;

@Mapper
public interface LoginMapper {
	
	UserVO findUser(UserVO userVO);
	
	UserVO getUser(String userId); // ID�ߺ�Ȯ��,�������� login
	
	int updateUser(UserVO userVO); // ��������
	
	int insertUser(UserVO userVO); //ȸ������
	
	String deleteUser(String userId);

	
	//�⸻������ �ؿ� �ּ�ó���س����� 
	//�����ð��� ���ϰ� �츮���˾Ƽ��϶�����
	
	 UserVO findId(UserVO userVO);
	 UserVO findPass(UserVO userVO);
	 int updatePass(UserVO userVO);
	 /*void deleteUser(UserVO userVO);
	 */

	
}
