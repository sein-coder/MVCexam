package com.kh.board.model.dao;

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

import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.BoardComment;

import oracle.jdbc.proxy.annotation.Pre;

import static common.template.JDBCTemplate.close;

public class BoardDao {

	private Properties prop = new Properties();
	
	public BoardDao() {
		String path = BoardDao.class.getResource("/sql/board/board-query.properties").getPath();
		try {
			prop.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public int selectBoardCount(Connection conn) {
		PreparedStatement pstmt = null;
		int result = 0;
		ResultSet rs = null;
		String sql = prop.getProperty("selectBoardCount");
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}

	public List<Board> selectBoardList(Connection conn, int cPage, int numPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = prop.getProperty("selectBoardList");
		List<Board> list = new ArrayList();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (cPage-1)*numPerPage+1);
			pstmt.setInt(2, cPage*numPerPage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board b = new Board();
				b.setBoard_No(rs.getInt("board_no"));
				b.setBoard_Title(rs.getString("board_title"));
				b.setBoard_Writer(rs.getString("board_writer"));
				b.setBoard_Content(rs.getString("board_content"));
				b.setBoard_Original_FileName(rs.getString("board_original_filename"));
				b.setBoard_Renamed_FileName(rs.getString("board_renamed_filename"));
				b.setBoard_Date(rs.getDate("board_date"));
				b.setBoard_ReadCount(rs.getInt("board_readcount"));
				list.add(b);
			}
		}catch(SQLException e) { 
			e.printStackTrace();
		}
		finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}


	public Board selectBoardView(Connection conn, int board_No) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = prop.getProperty("selectBoardView");
		Board b = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_No);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				b = new Board();
				b.setBoard_No(rs.getInt("board_no"));
				b.setBoard_Title(rs.getString("board_title"));
				b.setBoard_Writer(rs.getString("board_writer"));
				b.setBoard_Content(rs.getString("board_content"));
				b.setBoard_Original_FileName(rs.getString("board_original_filename"));
				b.setBoard_Renamed_FileName(rs.getString("board_renamed_filename"));
				b.setBoard_Date(rs.getDate("board_date"));
				b.setBoard_ReadCount(rs.getInt("board_readcount"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return b;
	}


	public int insertBoard(Connection conn, Board b) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertBoard");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getBoard_Title());
			pstmt.setString(2, b.getBoard_Writer());
			pstmt.setString(3, b.getBoard_Content());
			pstmt.setString(4, b.getBoard_Original_FileName());
			pstmt.setString(5, b.getBoard_Renamed_FileName());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int updateReadCount(Connection conn, int board_no) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateReadCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_no);
			result = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
	}


	public int selectBoardNo(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select seq_board_no.currval from dual";
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


	public int insertComment(Connection conn, BoardComment bc) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertComment");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bc.getBoardCommentLevel());
			pstmt.setString(2, bc.getBoardCommentWriter());
			pstmt.setString(3, bc.getBoardCommentContent());
			pstmt.setInt(4, bc.getBoardRef());
			
			//int형 자료형에 자료가 없이 null값을 넣을 때는
			//아래와 같이 강제로 형변환하여 값을 넣으면 오라클은 알아서 다시 형변환하여 값을 넣어준다.
			pstmt.setString(5, bc.getBoardCommentRef()==0?null:String.valueOf(bc.getBoardCommentRef()));
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public List<BoardComment> selectBoardComment(Connection conn, int board_No) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardComment> list = new ArrayList();
		String sql = prop.getProperty("selectBoardComment");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_No);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardComment bc = new BoardComment();
				bc.setBoardCommentNo(rs.getInt("board_comment_no"));
				bc.setBoardCommentLevel(rs.getInt("board_comment_level"));
				bc.setBoardCommentWriter(rs.getString("board_comment_writer"));
				bc.setBoardCommentContent(rs.getString("board_comment_content"));
				bc.setBoardRef(rs.getInt("board_ref"));
				bc.setBoardCommentRef(rs.getInt("board_comment_ref"));
				bc.setBoardCommentDate(rs.getDate("board_comment_date"));
				list.add(bc);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return list;
	}


	public int deleteComment(Connection conn, int boardRef, int boardCommentNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("deleteComment");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardRef);
			pstmt.setInt(2, boardCommentNo);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
}
