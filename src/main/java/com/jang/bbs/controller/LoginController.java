package com.jang.bbs.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jang.bbs.model.UserVO;
import com.jang.bbs.service.BoardService;
import com.jang.bbs.service.LoginService;
import com.jang.bbs.utils.AES256Util;

@Controller @RequestMapping("/member")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/member/login";
	}
	@RequestMapping(value="/join", method = RequestMethod.GET)
	public String toUserEntryView( Model model) {
		model.addAttribute("userVO", new UserVO());
	    return "/member/joinForm";
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)//�Է��� ���� ����
	public String onSubmit(@Valid UserVO userVO, BindingResult result, Model model) throws Exception {
		
		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "/member/joinForm";
		}
		//passwd ��ȣȭ(BCypt)
		//String hashPass = BCrypt.hashpw(userVO.getPasswd(), BCrypt.gensalt(12));
	
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	String hashPass = passwordEncoder.encode( userVO.getPasswd());
	userVO.setPasswd(hashPass); //��ȣȭ�� passwd �� ����
	
	//������� ��ȣȭ (aes256)
	Path filePath = Paths.get("C:/jj/key3.txt");
	String key = Files.readString(filePath);
	
	AES256Util aes256 = new AES256Util(key);
	
	String hashBirthday = aes256.aesEncode(userVO.getBirthday());
	userVO.setBirthday(hashBirthday);
	
	if(this.loginService.insertUser(userVO) != 0) {
		model.addAttribute("user", userVO);
		model.addAttribute("msgCode", "�����մϴ�! ȸ�������� �Ϸ�Ǿ����ϴ�. �α��� �Ͻʽÿ�!"); //��ϼ���
		return "/member/login";
	}
	else {
		model.addAttribute("msgCode", "�˼��մϴ�! ȸ�������� �����Ͽ����ϴ�. �ٽ� �����Ͽ� �ֽʽÿ�."); //��Ͻ���
		return "/member/joinForm";
	}
	}	
	
	@RequestMapping(value = "/idCheck", method = RequestMethod.GET) //ID�ߺ�üũ
	@ResponseBody 
	public String idCheck( HttpServletRequest request){

	String userId = request.getParameter("userId");
	  
	JSONObject  obj = new JSONObject();

	UserVO loginUser = this.loginService.getUser(userId);

	if(loginUser != null){
	obj.put("msg", "false");
	return obj.toString();
	}
	else {
	obj.put("msg", "true");
	return obj.toString();
	}
	}
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String loginProc(@Valid UserVO userVO, BindingResult result, Model model, HttpSession session) {
	if (result.hasFieldErrors("userId")||result.hasFieldErrors("passwd"))  {
	model.addAllAttributes(result.getModel());
	return "/member/login";
	}

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	UserVO loginUser = this.loginService.getUser(userVO.getUserId());

	if(loginUser == null){
	model.addAttribute("userId", "");
	model.addAttribute("msgCode", "��ϵ������� ���̵� �Դϴ�.");// ��ϵ������� ���̵�
	return "/member/login";
	}
	else if(passwordEncoder.matches(userVO.getPasswd(), loginUser.getPasswd())) { // Loginmapper�� getUser�κп� where �� del_yn='n'���ص� ������� LoginController ���� elseif ����
		//loginUser.getdel_yn='n'���� �ص� Ż���� ������ �α����� �ȵ�. controller�� �����ϸ� ���̵�� �޾ƿ��ϱ� Ȱ��ȭ�� �ȵ��ִ°ɷ� �����̵�
	model.addAttribute("loginUser", loginUser);
	session.setAttribute("userId", loginUser.getUserId()); //���ǿ� �������
	session.setAttribute("userName", loginUser.getName()); //���ǿ� �������
	return "redirect:/board/list";
	}
	else {
	model.addAttribute("userId", "");
	model.addAttribute("msgCode", "�н����尡 ��ġ���� �ʽ��ϴ�.");// �н����� ����
	return "/member/login";
	}
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/member/login";
	}
	@RequestMapping(value="/editUser", method = RequestMethod.GET)
	public String toUserEditView( HttpSession session, Model model) throws Exception {
		
		String userId = session.getAttribute("userId").toString(); //�������� ���̵� �����Ŵ.
		UserVO loginUser = this.loginService.getUser(userId); // ID���� �����ͼ� loginUser�� ����
		
		if(loginUser == null) {
			model.addAttribute("userId", "");
			model.addAttribute("msgCode", "��ϵ������� ���̵� �Դϴ�."); //��ϵ��� ���� ���̵��Դϴ�.
			return "/member/login";
		}
		else {
			//������� ��ȣȭ aes256
			Path filePath = Paths.get("C://jj/Key3.txt");
			String key = Files.readString(filePath);
			AES256Util aes256 = new AES256Util(key);
			
			String hashBirthday=loginUser.getBirthday();
			String decBirthday = aes256.aesDecode(hashBirthday);
			loginUser.setBirthday(decBirthday);
			
			model.addAttribute("userVO", loginUser);
			return "/member/editForm";
		}
	}
	@RequestMapping(value="/editUser", method =RequestMethod.POST)
	public String onEditSave(@Valid UserVO userVO, BindingResult result, Model model)throws Exception {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "/member/editForm";
		}
		
		//passwd ��ȣȭ(BCrypt)
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashPass = passwordEncoder.encode( userVO.getPasswd());
		userVO.setPasswd(hashPass); //��ȣȭ�� passwd �� ����
		
		//������� ��ȣȭ(aes256)
		Path filePath = Paths.get("C://jj/Key3.txt");
		String key = Files.readString(filePath);
		
		AES256Util aes256 = new AES256Util(key);
		
		String hashBirthday = aes256.aesEncode(userVO.getBirthday());
		userVO.setBirthday(hashBirthday);
		
		if(this.loginService.updateUser(userVO) != 0) {
			model.addAttribute("user", userVO);
			model.addAttribute("msgCode", "ȸ�������� �����Ǿ����ϴ�. �α��� �Ͻʽÿ�!"); //��ϼ���
			return "/member/login";
		}
		else {
			model.addAttribute("msgCode" , "�˼��մϴ�! ȸ������ ������ �����Ͽ����ϴ�. �ٽ� �����Ͽ� �ֽʽÿ�.");//��Ͻ���
			return "/member/editForm";
	}
	}
	
	@RequestMapping(value="/findId", method = RequestMethod.GET)
	public String tofindId() throws Exception{
		return "/member/findIdForm";
	}
	
	@RequestMapping(value="/findId", method = RequestMethod.POST)
	public String findIdSubmit(@Valid UserVO userVO, BindingResult result, Model model)throws Exception {
		
		if (result.hasFieldErrors("name")||result.hasFieldErrors("email")) {
			model.addAllAttributes(result.getModel());
			return "/member/findIdForm";
		}
		
		UserVO findId = this.loginService.findId(userVO);
		
		if (findId == null) {
			model.addAttribute("errmsg","��ϵ��� ���� ���̵��Դϴ�.");
			return "/member/findIdForm";
		}
		else {
			model.addAttribute("userVO", findId);
			return "/member/findIdSuccess";
		}
	}
	
	@RequestMapping(value="/findPass", method = RequestMethod.GET)
	public String tofindPass() throws Exception{
		return "/member/findPassForm";
	}
	
	@RequestMapping(value="/findPass", method = RequestMethod.POST)
	public String findPassSubmit(@Valid UserVO userVO, BindingResult result, Model model)throws Exception {
		
		if (result.hasFieldErrors("userId")||result.hasFieldErrors("email")) {
			model.addAllAttributes(result.getModel());
			return "/member/findPassForm";
		}
		
		UserVO findPass = this.loginService.findPass(userVO);
		
		if (findPass == null) {
			model.addAttribute("errmsg","��ϵ��� ���� ���̵��Դϴ�.");
			return "/member/findPassForm";
		}
		else {
			model.addAttribute("userVO", findPass);
			return "/member/findPassEdit";
		}
	}
	
	@RequestMapping(value="/editPass", method=RequestMethod.POST)
	public String findEditPass(@Valid UserVO userVO, BindingResult result, Model model) throws Exception{
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashPass = passwordEncoder.encode(userVO.getPasswd());
		userVO.setPasswd(hashPass);
		
		int editPass = this.loginService.updatePass(userVO);
		
		if(editPass == 0) {
			model.addAttribute("msgCode", "안바뀜."); //��ϼ���
			
			return "/member/login";
			
		}else {
			model.addAttribute("msgCode", "비밀번호 바뀜");
			
			return "/member/login";
		}
	}
	
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete() {
		return "/member/deleteForm";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String ondelete(@Valid UserVO userVO, BindingResult result, Model model, HttpSession session) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		UserVO loginUser = this.loginService.getUser(userVO.getUserId()); //LoginMapper.xml���� getUser�� where
	// ��й�ȣ�� �ؿ� if������ �̹� ���ϰ������ϱ� Id�� ���������. �׷��� getUser�� userVO.getUserId�� ��������� 
		
		if(encoder.matches(userVO.getPasswd(), loginUser.getPasswd())) {
			return "/member/deleteAgree";
			
		}else {
			model.addAttribute("msgCode", "��й�ȣ�� Ʋ�Ƚ��ϴ�.");
			return "/member/deleteForm";
		}
		
	}
	@RequestMapping(value="/deleteAgree", method=RequestMethod.GET)
	public String deleteAgree(Model model, HttpSession session) {
		
		String userId = session.getAttribute("userId").toString();
		
		try {
			this.loginService.deleteUser(userId);
			model.addAttribute("msgCode", "������ ���������� �����Ǿ����ϴ�.");
			session.invalidate();
			return "/member/login";
		} catch (EmptyResultDataAccessException e){
			model.addAttribute("msgCode", "�������� �� ������ �߻��߽��ϴ�. �ٽýõ��Ͽ� �ֽʽÿ�.");
			return "/member/deleteForm";		
		}
		
	}
	
	
	
}
