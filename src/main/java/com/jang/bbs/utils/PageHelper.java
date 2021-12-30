package com.jang.bbs.utils;

import com.jang.bbs.model.SearchVO;

public class PageHelper {

private int pageSize = 10; // �� �������� ���̴� �Խù� ��
private int blockSize = 10; //�� ���� ���̴� ������ ��

public int getPageSize() {
	return pageSize;
}

public int getBlockSize() {
	return blockSize;
}

// ���� �������� ��ü ���ڵ���� �Է� �޾� ����¡ ǥ�� ���ڿ��� ��ȯ�Ѵ�. 
public StringBuffer getPageUrl(SearchVO searchVO, int totalRow) {

StringBuffer pageHtml = new StringBuffer();
int currentPage = searchVO.getPage();
int startPage = 0;
int endPage = 0;

//ǥ���� ������������ �������� ��� 
startPage = ((currentPage-1) / blockSize) * blockSize + 1;
endPage = startPage + blockSize - 1;

//��ü ������ ���� ���� ���  
int totalPage = totalRow/pageSize + 1;
int totalBlock = totalPage/blockSize + 1;
int currentBlock = currentPage/blockSize + 1; 
//ǥ�õǴ� ���� ���� �� �������� ��ü ������ �������� ���- ��ü ���������� ū ��� �߻���  ����  : 223��/10 + 1 = 23������
if( endPage > (totalRow / pageSize ) ) {
	endPage = (totalRow /pageSize) + 1;
}
//���� ���� 2 �̻��� ��� [����] ǥ��
if(currentBlock > 1){
	pageHtml.append("<span><a href=\"list?page=" + (currentPage-1) + "\"><����></a>&nbsp;&nbsp;");
}else{
	pageHtml.append("<span>");
}
//���� ���ȿ� ���Ե�  ������ ǥ��
for(int i = startPage ; i <= endPage ; i++) {
if(i == currentPage){
pageHtml.append(".&nbsp;<strong>");
pageHtml.append( i );
pageHtml.append("&nbsp;</strong>");
} else {
pageHtml.append(".&nbsp;<a href=\"list?page=" +i + "\" class=\"page\">" + i + "</a>&nbsp;");
}
}
//���� ��� ���� ��ü �� ���� ������ [����] ǥ��
if(currentBlock < totalBlock){
	pageHtml.append(".&nbsp;&nbsp;<a href=\"list?page=" + (currentPage+1) + "\"><����></a></span>");
}else{
	pageHtml.append(".</span>");
}

return pageHtml;  
}
}
