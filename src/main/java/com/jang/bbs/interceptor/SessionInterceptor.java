package com.jang.bbs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter {                                                            
	   @Override
	   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
	                            throws Exception {

	Object userId = request.getSession().getAttribute("userId");

	// login.do �̳� ȸ�������������� �����Ϸ��� ���(/bbs/member/join.do) �̹� �α��� �Ǿ� �ִ� �ڴ� �α����̳� ������ �ʿ����==>list.do  
	if(request.getRequestURI().equals("/bbs/member/login")||
	request.getRequestURI().equals("/bbs/member/join")) {
	if(userId != null){
	   response.sendRedirect(request.getContextPath() + "/board/list");
	   return false ; //list.do ����
	} 
	else {
	   return true; //login.do ����
	}
	      }
	if(userId == null){      //url��  /login or join.do �� �ƴ� ��� ��� �α��� �Ǿ� ���������� 
	    response.sendRedirect(request.getContextPath() + "/member/login");
	    return false; //���� URL ������ ���� �ʰ� login.do ����
	} 
	else {
	    return true; //���� URL ������ ����
	}
	   }
	   @Override
	   public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
	                   ModelAndView modelAndView) throws Exception {
	   }
	}
