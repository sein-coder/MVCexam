package com.kh.notice.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.crypto.spec.PSource;

import com.kh.notice.model.vo.Notice;
import static common.template.JDBCTemplate.close;

public class NoticeDao {

	private Properties prop = new Properties();
	
	public NoticeDao() {
		String path=NoticeDao.class.getResource("/sql/notice/notice-query.properties").getPath();
		try {
			prop.load(new FileReader(path));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public int selectNoticeCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = prop.getProperty("selectCountList");
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}
	
	
	public List<Notice> selectNoticeList(Connection conn, int cPage, int numPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Notice> list = new ArrayList();
		String sql = prop.getProperty("selectNoticeList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (cPage-1)*numPerPage+1);
			pstmt.setInt(2, cPage*numPerPage);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Notice n = new Notice();
				n.setNotice_No(rs.getInt("notice_No"));
				n.setNotice_Title(rs.getString("notice_Title"));
				n.setNotice_Writer(rs.getString("notice_Writer"));
				n.setNotice_Content(rs.getString("notice_content"));
				n.setNotice_Date(rs.getDate("notice_date"));
				n.setFilePath(rs.getString("filepath"));
				n.setStatus(rs.getString("status").charAt(0));
				list.add(n);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return list;
	}

	public Notice selectNoticeView(Connection conn, int notice_No) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice n = null;
		String sql = prop.getProperty("selectNoticeView");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, notice_No);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				n = new Notice();
				n.setNotice_No(rs.getInt("notice_No"));
				n.setNotice_Title(rs.getString("notice_Title"));
				n.setNotice_Writer(rs.getString("notice_Writer"));
				n.setNotice_Content(rs.getString("notice_content"));
				n.setNotice_Date(rs.getDate("notice_date"));
				n.setFilePath(rs.getString("filepath"));
				n.setStatus(rs.getString("status").charAt(0));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		return n;
	}

	public int insertNotice(Connection conn, Notice n) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertNotice");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, n.getNotice_Title());
			pstmt.setString(2, n.getNotice_Writer());
			pstmt.setString(3, n.getNotice_Content());
			pstmt.setString(4, n.getFilePath());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateNotice(Connection conn, Notice n) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateNotice");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, n.getNotice_Title());
			pstmt.setString(2, n.getNotice_Writer());
			pstmt.setString(3, n.getFilePath());
			pstmt.setString(4, n.getNotice_Content());
			pstmt.setInt(5, n.getNotice_No());
			result = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteNotice(Connection conn, int notice_No) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("deleteNotice");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, notice_No);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

}
