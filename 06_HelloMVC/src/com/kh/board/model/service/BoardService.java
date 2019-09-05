package com.kh.board.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Board;
import com.sun.org.apache.regexp.internal.recompile;

import static common.template.JDBCTemplate.getConnection;
import static common.template.JDBCTemplate.close;
import static common.template.JDBCTemplate.commit;
import static common.template.JDBCTemplate.rollback;;

public class BoardService {

	private BoardDao dao = new BoardDao();
	
	public int selectBoardCount() {
		Connection conn = getConnection();
		int result = dao.selectBoardCount(conn);
		close(conn);
		return result;
	}

	public List<Board> selectBoardList(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Board> list = dao.selectBoardList(conn,cPage,numPerPage);
		close(conn);
		return list;
	}

	public Board selectBoardView(int board_No, boolean hasRead) {
		Connection conn = getConnection();
		Board b = dao.selectBoardView(conn,board_No);
		if(!hasRead && b != null) {
			int result = dao.updateReadCount(conn, board_No);
			if(result>0) {
				commit(conn);
			}else {
				rollback(conn);
			}
		}
		close(conn);
		return b;
	}

	public int insertBoard(Board b) {
		Connection conn = getConnection();
		int result = dao.insertBoard(conn,b);
		if(result>0) {
			result = dao.selectBoardNo(conn);
			commit(conn);
			}
		else {rollback(conn);}
		close(conn);
		return result;
	}
	
}
