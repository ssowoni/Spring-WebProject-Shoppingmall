package com.myspring.kh.board.page;

public class BoardPage {
	public static String pagingStr(int totalCount, int pageSize, int blockPage,
			int pageNum, String reqUrl) {
		String pagingStr="";
		
		//단계 3 : 전체 페이지 수 계산
		//ceil 무조건 올린다.
		int totalPages = (int) (Math.ceil(((double) totalCount / pageSize)));
		
		//단계 4 : '이전 페이지 블록 바로가기' 출력
		int pageTemp = (((pageNum -1)/blockPage) * blockPage) +1;
		if(pageTemp !=1) {
			pagingStr += "<a href='" + reqUrl + "?pageNum=1'> [첫 페이지] </a>";
			pagingStr += "&nbsp";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + (pageTemp -1) +"'> [이전 블록] </a>";
		
		}
		
		//단계 5 : 각 페이지 번호 출력
		int blockCount=1;
		while(blockCount <= blockPage && pageTemp <= totalPages) {
			if(pageTemp == pageNum) {
				//현재 페이지는 링크를 걸지 않음
				pagingStr += "&nbsp;" + pageTemp + "&nbsp;";
			}else {
				pagingStr += "&nbsp;<a href='" + reqUrl + "?pageNum=" +pageTemp + "'>" +pageTemp+"</a>&nbsp;";
			}
			pageTemp++;
			blockCount++;
		}
		
		//단계 6 : '다음 페이지 블록 바로가기' 출력
		if(pageTemp <= totalPages) {
			pagingStr += "<a href='" + reqUrl + "?pageNum=" +pageTemp + "'> [다음블록] </a>";
			pagingStr +="&nbsp;";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" +totalPages + "'> [마지막 페이지] </a>";
		}
		return pagingStr;
	}
}
/*
PAGES_PER_BLOCK : 한 화면에 출력할 페이지 번호 수 (1 2 3 4 5 6 7 8 9 10) 이런식으로
1단계 : board 테이블에 저장된 전체 레코드 수를 카운트 ex) 게시물이 105개라 가정해보자

2단계 : 각 페이지에서 출력할 게시물의 범위를 계산
범위의 시작값 : (현재 페이지 -1) * POSTS_PER_PAGE +1 
POSTS_PER_PAGE : 한 페이지에 출력할 게시물 수
범위의 종료값 : (현재페이지 * POSTS_PER_PAGE)
(1 - 1 ) * 10 + 1 = 1     1*10 = 10  1페이지 경우
(2 - 1) * 10 + 1 =11    2*10 = 20 2페이지 경우

3단계 : 전체 페이지 수를 계산 ceil땜에 무조건 올림 처리를 한다.
계단식 : Math.ceil(전체 게시물 수 / POSTS_PER_PAGE ) 
105 / 10 = 10.5 -> 11 
게시글이 105개가 있을 때 전체 페이지는 11페이지가 되는 것.!

4단계 : 이전 페이지 블록 바로가기 출력
계단식 : ((현재페이지 -1) / PAGES-PER_BLOCK ) * PAGES_PER_BLOCK +1 
만약 1페이지 일 때 : ((1 - 1) / 5) * 0 + 1 = 1
만약 5페이지 일 때 : ((5 - 1) / 5) * 0 + 1 = 1
만약 6페이지 일 때 : ((6 - 1) / 5) * 5 + 1 = 6
만약 10 페이지라면 : ((10 - 1) / 5) * 5 + 1 = 6

5단계 : 각 페이지 번호를 출력
4단계에서 계산한 pageTemp를 BLOCK_PAGE만큼 반복하면서 +1 연산 후 출력
pageTemp 1일 때 : 1 2 3 4 5 를 출력
pageTemp 6일 때 : 6 7 8 9 10 를 출력

6단계 : 다음페이지 블롤 바로가기 출력
각 페에지 번호를 출력한 후 pageTemp + 1 하여 다음 페이지 블록 바로가기 설정 
즉 마지막페이지 번호가 5라면 다음페이지 블록은 6이 된다.

*/
