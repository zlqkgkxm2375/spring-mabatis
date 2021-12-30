package com.jang.bbs.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jang.bbs.model.AttFileVO;
import com.jang.bbs.model.BoardLikeVO;
import com.jang.bbs.model.BoardVO;
import com.jang.bbs.model.BoardViewVO;
import com.jang.bbs.model.ReplyLikeVO;
import com.jang.bbs.model.ReplyVO;
import com.jang.bbs.model.SearchVO;
import com.jang.bbs.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listPage(@ModelAttribute("SearchVO") SearchVO searchVO, Model model, HttpSession session) throws Exception {
			
		List<BoardVO> blist = boardService.getBoardList(searchVO);
		model.addAttribute("boardList", blist);
		
		StringBuffer pageUrl = boardService.getPageUrl(searchVO);
		model.addAttribute("pageHttp", pageUrl);
		
		return "board/list";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String boardWrite(){
	return "/board/write";
	}
	 
	String uploadPath = "C:\\upload\\"; // file upload path 전역변수로 파일 저장소 지정

	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String boardWriteProc(@ModelAttribute("BoardVO") BoardVO boardVO, 
	        MultipartHttpServletRequest mrequest){

	//새 글저장
	String content =  boardVO.getContent().replaceAll("\r\n", "<br />");//java새줄 코드 HTML줄바꾸기로
	content = content.replaceAll("<","&lt;");
	content = content.replaceAll(">","&gt;");
	content = content.replaceAll("&","&amp;"); 
	content = content.replaceAll("\"","&quot;"); 

	boardVO.setContent(content);

	boardService.writeArticle(boardVO);

	int bno = boardVO.getBno(); 
	//저장시 생성한 새 글번호가 반환됨 : 어떻게? xml 파일 insert keyProperty="bno"에 의해서 설정됨

	
	
	//첨부 파일 저장
	   AttFileVO file = new AttFileVO();
	   List<MultipartFile> fileList = mrequest.getFiles("file"); 

	        for (MultipartFile mf : fileList) {
	        if (!mf.isEmpty()) {
	            String originFileName = mf.getOriginalFilename(); // 원본 파일 명
	            long fileSize = mf.getSize(); // 파일 사이즈
	            
	            file.setBno(bno);
	            file.setOfilename(originFileName);
	            file.setSfilename(originFileName); // 디스크에 파일 저장시 동일 파일명 발생대비:현재 처리 안함  
	            file.setFilesize(fileSize); 
	                 
	            boardService.insertFile(file);//테이블에 화일 정보 저장
	   
		      String safeFile = uploadPath +  originFileName; // 디스크에 파일 저장     

	            try {
	                mf.transferTo(new File(safeFile)); // 디스크에 파일 저장 

	            } catch (IllegalStateException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	       }        
	        return "redirect:list";
	}
	@RequestMapping("/view")
public String boardView(@RequestParam(value = "bno", required = false, defaultValue="0") int bno, HttpSession session, Model model) throws Exception {
		
	    String userId = (String) session.getAttribute("userId"); 

	    BoardViewVO boardViewVO = new BoardViewVO() ; //글조회 이력 용
	    boardViewVO.setBno(bno);
	    //조회수 중복증가 방지
	    if(userId != null ) { //회원인 경우
	    boardViewVO.setUserId(userId); 
	    boardViewVO.setMem_yn('y'); //회원   
	    if( boardService.getBoardView(boardViewVO) == 0) {//해당 번호 글을 읽은 기록이 없으면 
	 	    boardService. incrementViewCnt(bno);     //조회수 갱신
	    boardService.addBoardView(boardViewVO); //회원 조회 등록
	         }
	    }
	    else { //비회원인 경우 
	boardViewVO.setUserId(session.getId()); //세션id를 회원 id로 등록
	boardViewVO.setMem_yn('n'); //비회원

	if(boardService.getBoardView(boardViewVO) == 0) {//해당 번호 글을 읽은 기록이 없으면 
	boardService. incrementViewCnt(bno);    //조회수 갱신
	boardService.addBoardView(boardViewVO);//비회원 조회 등록
	     }
	   }

			
	BoardVO boardVO = boardService.getArticle(bno); // get selected article model

	List<ReplyVO> reply = boardService.getReplyList(bno); // 댓글 목록 읽어 오기 – list
	List<AttFileVO> fileList = boardService.getFileList(bno); // 첨부파일 목록 읽어 오기-list

	model.addAttribute("board", boardVO);
	model.addAttribute("replyList", reply);
	model.addAttribute("fileList", fileList);

	return "board/view";
	}
	
	@RequestMapping(value = "/modify", method=RequestMethod.GET )
	public String boardModify(HttpServletRequest request, HttpSession session, Model model){

	String userId = (String) session.getAttribute("userId");
	int bno = Integer.parseInt(request.getParameter("bno"));

	BoardVO board = boardService.getArticle(bno);

	// <br /> tag change to new line code
	String content = board.getContent().replaceAll("<br />", "\r\n");
	board.setContent(content); 

	if(!userId.equals(board.getWriterId())){ //글작성자 id가 로그인 한 아이디와 일치하면 실행
		model.addAttribute("errCode", "5"); //작성자만  글수정 가능
		model.addAttribute("bno", bno);
		return "redirect:view";
	} else {//회원 글수정
		List<AttFileVO> fileList = boardService.getFileList(bno); // 첨부파일 읽어 오기 - list
	
		model.addAttribute("board", board);
		model.addAttribute("fileList", fileList);
		return "/board/modify";
	}
	}
	
	@RequestMapping(value = "/modify", method=RequestMethod.POST)  //게시판 글 수정
	public String  boardModifyProc(@ModelAttribute("Board") BoardVO board, MultipartHttpServletRequest request, RedirectAttributes rea ){

	String content =  board.getContent().replaceAll("\r\n", "<br />"); //java 새줄 코드 HTML줄바꾸기로
	board.setContent(content);

	boardService.updateArticle(board);

	int bno = board.getBno(); 


	//체크된 파일을 테이블과 디스크에서 삭제한다.
	String[] fileno = request.getParameterValues("fileno");

	if (fileno != null) {

	for (String fn : fileno) {

	    int fno = Integer.parseInt(fn);
	    
	    String oFileName = boardService.getFileName(fno);
	   
	    String safeFile = uploadPath +  oFileName;
	    
	    File removeFile = new File(safeFile);// remove disk uploaded file 
	    removeFile.delete();
	        
	    boardService.deleteFile(fno); //remove table uploaded file 
	}
	}
	
	AttFileVO file = new AttFileVO ();

    //새첨부 파일  목록 일어오기
    List<MultipartFile> fileList = request.getFiles("file");
   
    for (MultipartFile mf : fileList) {
   if (!mf.isEmpty()) {
        String originFileName = mf.getOriginalFilename(); // 새첨부파일 원본 파일 명
    	 long fileSize = mf.getSize(); // 파일 사이즈
        
        file.setBno(bno);
        file.setFilesize(fileSize); 
        file.setOfilename(originFileName);
        file.setSfilename(originFileName);
             
        boardService.insertFile(file); // 테이블에 파일 저장 

        String safeFile = uploadPath +  originFileName; 

        try {
            mf.transferTo(new File(safeFile)); // 디스크에 파일 저장 
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
  }
  rea.addAttribute("bno", board.getBno());
  return "redirect:/board/view";
  
	}
	@RequestMapping("/delete")
	public String boardDelete(HttpServletRequest request, HttpSession session, RedirectAttributes rea){
	String userId = (String) session.getAttribute("userId"); // login 개발뒤 삭제
	int bno = Integer.parseInt(request.getParameter("bno"));

	BoardVO board = boardService.getArticle(bno);

	String setView="";
	if(userId.equals(board.getWriterId())){
	//답글 삭제 
	List<ReplyVO> reply = boardService.getReplyList(bno); 
	if(reply.size() > 0){
		boardService.deleteReplyBybno(bno);
	}
	//첨부 파일명 삭제, 실제 파일 삭제 
	List<AttFileVO> files  = boardService.getFileList(bno); 
	if(files.size() > 0){
	     //저장된  실제 파일 삭제
	for (AttFileVO filedel : files) {
	String f_stor_all = filedel.getOfilename();
	File f = new File(session.getServletContext().getRealPath("/") + f_stor_all); 
	f.delete();
	}
	boardService.deleteFileBybno(bno); //테이블에서 해당 번호 글 첨부 file 전체 삭제
	}
	//board 삭제
	boardService.deleteArticle(bno);
	setView="redirect:list";
	} else {
	rea.addAttribute("errCode", "1");// it's forbidden connection
	rea.addAttribute("bno", bno);
	setView="redirect:view";
	}
	return setView ;
	}
	
	
	@RequestMapping("/recommand")
	public String updateRecommendCnt(HttpServletRequest request, HttpSession session, RedirectAttributes rea){

	int bno = Integer.parseInt(request.getParameter("bno")); 
	String userId = (String) session.getAttribute("userId"); 

	System.out.println("추천이되었습니다.");
	if( userId==null){//userId가 로그인이안되있다는 뜻임
	rea.addAttribute("bno", bno);
	rea.addAttribute("errCode", "4");
	return "redirect:/board/view";
	}

	BoardLikeVO boardLike = new BoardLikeVO();
	boardLike.setBno(bno);
	boardLike.setUserId(userId);

	BoardVO board = boardService.getArticle(bno);

	if( board.getWriterId().equals(userId)){
		rea.addAttribute("errCode", "3");                //본인 글은 추천 불가
	} else {
	if (boardService.getBoardLike(boardLike) == 0 ) {  //이미 추천한 기록이 없으면
	boardService.incrementGoodCnt(bno);
	boardService.addBoardLike(boardLike);         //추천 기록 등록
	}else {
	     rea.addAttribute("errCode", "2");           //이미 추천했던 글이면 추천 불가 
	     }
	}
	rea.addAttribute("bno", bno);
	return "redirect:/board/view";
	}
	
	
	@RequestMapping("/writeReply")
	public String replyWriteProc(@ModelAttribute("reply") ReplyVO reply, RedirectAttributes rea){

	if (reply.getContent().isEmpty()) {
		rea.addAttribute("errCode", "1");
	}
	else {
	String  content = reply.getContent().replaceAll("<","&lt;");
	content = reply.getContent().replaceAll(">","&gt;");
	content = reply.getContent().replaceAll("&","&amp;"); 
	content = reply.getContent().replaceAll("\"","&quot;"); 
	content = reply.getContent().replaceAll("\r\n", "<br />");

	reply.setContent(content);

	boardService.writeReply(reply);
	}

	rea.addAttribute("bno", reply.getBno());

	return "redirect:view";
	}
	
	
	@RequestMapping("/deleteReply")
	public String commentDelete(HttpServletRequest request, HttpSession session, RedirectAttributes rea){

	int rno = Integer.parseInt(request.getParameter("rno"));
	int bno = Integer.parseInt(request.getParameter("bno"));
	
	String userId = (String) session.getAttribute("userId");
	ReplyVO reply = boardService.getReply(rno);

	
	
	if(!userId.equals(reply.getWriterId())){
		rea.addAttribute("errCode", "1");
	} else {
		boardService.deleteReply(rno);
	}

	rea.addAttribute("bno", bno); // move back to the article

	return "redirect:view";
	}
	
	
	@RequestMapping("/recommandReply")
	public String RecommendRely(HttpServletRequest request, HttpSession session, RedirectAttributes rea){

	int bno = Integer.parseInt(request.getParameter("bno")); 
	int rno = Integer.parseInt(request.getParameter("rno")); 
	String userId = (String) session.getAttribute("userId"); 
	
	if(userId==null){
	rea.addAttribute("bno", bno);
	rea.addAttribute("errCode", "4");
	return "redirect:/board/view";
	}

	ReplyLikeVO replyLike = new ReplyLikeVO();
	replyLike.setRno(rno);
	replyLike.setUserId(userId);

	BoardVO board = boardService.getArticle(bno);

	if(board.getWriterId().equals(userId)){
		rea.addAttribute("errCode", "3");
	} else {
	if (boardService.getReplyLike(replyLike) == 0 ) {
	boardService.incReplyGoodCnt(rno);
	boardService.addReplyLike(replyLike);
		} else {
	    	     rea.addAttribute("errCode", "2");
	    	}
	}
	rea.addAttribute("bno", bno);
	return "redirect:/board/view";
	}

	
	@RequestMapping(value = "filedown")
	@ResponseBody
	public byte[] downProcess(HttpServletResponse response,@RequestParam  String fileName ) throws IOException{

	File file = new File(uploadPath + fileName);
	byte[] bytes = FileCopyUtils.copyToByteArray(file); //SPRING 5.0 이상 

	String fn = new String(file.getName().getBytes(), "iso_8859_1");

	response.setHeader("Content-Disposition", "attachment;filename=\"" + fn + "\"");
	response.setContentLength(bytes.length);

	return bytes;
	}
	}