package com.kh.admin.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.member.model.vo.Member;

import static common.template.JDBCTemplate.close;

public class AdminDao {

	private Properties prop = new Properties();
	
	public AdminDao() {
		String path=AdminDao.class.getResource("/sql/admin/admin-query.properties").getPath();
		try {
			prop.load(new FileReader(path));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Member> selectMemberList(Connection conn, int cPage, int numPerPage){
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> list = new ArrayList();
		String sql = prop.getProperty("selectMemberList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (cPage-1)*numPerPage+1);//데이터의 시작값 정의식
			pstmt.setInt(2, cPage*numPerPage);//데이터의 마지막값 정의식
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member m = new Member();
				m.setUserId(rs.getString("userId"));
				m.setUserName(rs.getString("userName"));
				m.setAge(rs.getInt("age"));
				m.setGender( rs.getString("gender").charAt(0) );
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enrolldate"));
				list.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}
	
	public int selectCountMember(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = prop.getProperty("selectCountMember");
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1); // 칼럼인덱스번호로도 불러올수 있다.
				// result = rs.getInt("cnt");				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

		public List<Member> selectSearch(Connection conn, int cPage, int numPerPage, String searchType, String searchKeyword) {
//		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Member> list = new ArrayList();
//		String sql = "select * from member where "+searchType+" like '%"+searchKeyword+"%'";
		String sql = "select * from "
				+ "(select rownum as rnum, a.* from "
				+ "( select * from member where "+searchType+" like '%"+searchKeyword+"%' order by enrolldate desc ) a ) "
				+ "where rnum between "+((cPage-1)*numPerPage+1)+" and "+(cPage*numPerPage);
//		switch (searchType) {
//		case "userId": sql = prop.getProperty("selectSearchUserId");
//			break;
//		case "userName": sql = prop.getProperty("selectSearchUserName");
//			break;
//		case "gender": sql = prop.getProperty("selectSearchGender");
//			break;
//		}
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Member m = new Member();
				m.setUserId(rs.getString("userId"));
				m.setUserName(rs.getString("userName"));
				m.setAge(rs.getInt("age"));
				m.setGender( rs.getString("gender").charAt(0) );
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enrolldate"));
				list.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(stmt);
		}
		
		return list;
	}

		public int selectSearchCount(Connection conn, String searchType, String searchKeyword) {
			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;
			int result = 0;
			String sql = "select count(*) from member where "+searchType+" like '%"+searchKeyword+"%'";
//			String sql = prop.getProperty("selectSearchCount");
			
//			switch (searchType) {
//			case "userId": sql = prop.getProperty("selectSearchCountUserId");
//				break;
//			case "userName": sql = prop.getProperty("selectSearchCountUserName");
//				break;
//			case "gender": sql = prop.getProperty("selectSearchCountGender");
//				break;
			
			
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					result = rs.getInt(1);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(stmt);
			}
			return result;
		}

}
