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

	// login.do 이나 회원가입페이지를 실행하려는 경우(/bbs/member/join.do) 이미 로그인 되어 있는 자는 로그인이나 가입이 필요없음==>list.do  
	if(request.getRequestURI().equals("/bbs/member/login")||
	request.getRequestURI().equals("/bbs/member/join")) {
	if(userId != null){
	   response.sendRedirect(request.getContextPath() + "/board/list");
	   return false ; //list.do 실행
	} 
	else {
	   return true; //login.do 실행
	}
	      }
	if(userId == null){      //url이  /login or join.do 가 아닌 모든 경우 로그인 되어 있지않으면 
	    response.sendRedirect(request.getContextPath() + "/member/login");
	    return false; //현재 URL 페이지 실행 않고 login.do 실행
	} 
	else {
	    return true; //현재 URL 페이지 실행
	}
	   }
	   @Override
	   public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
	                   ModelAndView modelAndView) throws Exception {
	   }
	}
