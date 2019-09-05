package com.kh.admin.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.admin.model.dao.AdminDao;
import com.kh.member.model.vo.Member;
import static common.template.JDBCTemplate.getConnection;
import static common.template.JDBCTemplate.close;


public class AdminService {
	
	private AdminDao dao = new AdminDao();
	
	public List<Member> selectMemberList(int cPage, int numPerPage){
		Connection conn = getConnection();
		List<Member> list = dao.selectMemberList(conn,cPage,numPerPage);
		close(conn);
		return list;
	}
	
	public int selectCountMember() {
		Connection conn = getConnection();
		int result = dao.selectCountMember(conn);
		close(conn);
		return result;
	}
	//검색결과에 대한 전체 자료수 count값을 가져오기 위한 메소드
	public int selectSearchCount(String searchType, String searchKeyword) {
		Connection conn = getConnection();
		int result = dao.selectSearchCount(conn,searchType,searchKeyword);
		close(conn);
		return result;
	}
	//검색된 결과중 numPerPage만큼의 갯수만 가져오는 메소드
	public List<Member> selectSearch(int cPage, int numPerPage, String searchType, String searchKeyword) {
		Connection conn = getConnection();
		
		List<Member> list = dao.selectSearch(conn,cPage,numPerPage,searchType,searchKeyword);
		close(conn);
		
		
		return list;
	}
	
}
