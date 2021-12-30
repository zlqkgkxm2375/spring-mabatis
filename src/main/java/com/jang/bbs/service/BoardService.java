package com.jang.bbs.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jang.bbs.model.AttFileVO;
import com.jang.bbs.model.BoardLikeVO;
import com.jang.bbs.model.BoardVO;
import com.jang.bbs.model.BoardViewVO;
import com.jang.bbs.model.ReplyLikeVO;
import com.jang.bbs.model.ReplyVO;
import com.jang.bbs.model.SearchVO;

@Mapper
public interface BoardService {
   
   List<BoardVO> getBoardList(SearchVO searchVO); //�� �����ȸ
   StringBuffer getPageUrl(SearchVO searchVO);
   
   BoardVO getArticle(int bno); //�� ��ȸ
   int writeArticle(BoardVO board); //������
   int updateArticle(BoardVO board); //�ۼ���
   int deleteArticle(int bno); //�ۻ���
   
   
   int getTotalRow(SearchVO searchVO); //��ü�� �� ��ȸ
   
   int incrementViewCnt(int bno); //��ȸ�� ����
   int incrementGoodCnt(int bno); //���ƿ� ����
   int incrementReplyCnt(int bno); //��� �� ����
   
   List<ReplyVO> getReplyList(int bno); //��� �����ȸ
   ReplyVO getReply(int rno); //��� ��ȸ
   
   int writeReply(ReplyVO reply); //��� ����
   int updateReply(ReplyVO reply); //��� ����
   int deleteReply(int rno); //��� ����
   int deleteReplyBybno(int bno); //���ۿ� �Ҽӵ� ��� ��ü ����
   
   int incReplyGoodCnt(int bno); //��� ���ƿ� ����
   
   List<AttFileVO> getFileList(int bno); //÷������ ��� ��ȸ
   String getFileName(int fno); //÷������ �̸� ��ȸ
   int insertFile(AttFileVO file); //÷������ ����
   
   int deleteFile(int fno); //÷������ ����
   int deleteFileBybno(int bno); //���ۿ� �Ҽӵ� ÷������ ��ü ����
   
   int addBoardLike(BoardLikeVO boardLike); //id-Ư���� ���ƿ� ��� ����
   int getBoardLike(BoardLikeVO boardLike); //id-Ư���� ���ƿ� ��� ��ȸ
   
   int addReplyLike(ReplyLikeVO replyLike); //id-Ư����� ���ƿ� ��� ����
   int getReplyLike(ReplyLikeVO replyLike); //id-Ư����� ���ƿ� ��� ��ȸ
   
   int addBoardView(BoardViewVO boardView); //id-Ư���� ��ȸ�̷� ���
   int getBoardView(BoardViewVO boardView); //id-Ư���� ��ȸ�̷� �б�
}