package com.kh.notice.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.notice.model.dao.NoticeDao;
import com.kh.notice.model.vo.Notice;

import static common.template.JDBCTemplate.getConnection;
import static common.template.JDBCTemplate.close;
import static common.template.JDBCTemplate.commit;
import static common.template.JDBCTemplate.rollback;;


public class NoticeService {

	private NoticeDao dao = new NoticeDao();
	
	public int selectNoticeCount() {
		Connection conn = getConnection();
		int result = dao.selectNoticeCount(conn);
		close(conn);
		return result;
	}
	
	
	public List<Notice> selectNoticeList(int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Notice> list = dao.selectNoticeList(conn,cPage,numPerPage);
		close(conn);
		return list;
	}


	public Notice selectNoticeView(int notice_No) {
		Connection conn = getConnection();
		Notice n = dao.selectNoticeView(conn, notice_No);
		close(conn);
		return n;
	}


	public int insertNotice(Notice n) {
		Connection conn = getConnection();
		int result = dao.insertNotice(conn, n);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}


	public int updateNotice(Notice n) {
		Connection conn = getConnection();
		int result = dao.updateNotice(conn, n);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}


	public int deleteNotice(int notice_No) {
		Connection conn = getConnection();
		int result = dao.deleteNotice(conn,notice_No);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

}
