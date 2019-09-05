package com.kh.member.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;
import static common.template.JDBCTemplate.getConnection;
import static common.template.JDBCTemplate.close;
import static common.template.JDBCTemplate.commit;
import static common.template.JDBCTemplate.rollback;;

public class MemberService {
	
	private MemberDao dao = new MemberDao();
	
	public Member selectId(String id, String pw) {
		Connection conn = getConnection();
		Member m = dao.selectId(conn,id,pw);
		close(conn);
		
		return m;
	}

	public int insertMember(Member m) {
		Connection conn = getConnection();
		int result = dao.insertMember(conn,m);
		
		if(result==1) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
	}

//	public boolean checkId(String userId) {
//		Connection conn = getConnection();
//		Member m = dao.checkId(conn,userId);
//		close(conn);
//		if(m!=null) {
//			return true;
//		}
//		else {
//			return false;			
//		}
//	}

	public Member selectOne(String userId) {
		Connection conn = getConnection();
		Member m = dao.selectOne(conn,userId);
		close(conn);
		return m;
	}

	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = dao.updateMember(conn,m);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteMember(String userId) {
		// TODO Auto-generated method stub
		Connection conn=getConnection();
		int result=dao.deleteMember(conn,userId);
		
		if(result>0) {commit(conn);}
		else {rollback(conn);}
		close(conn);
		return result;
	}

	public int updatePassword(String userId, String cPw, String nPw) {
		Connection conn = getConnection();
		Member m = dao.selectId(conn, userId, cPw);
		int result = 0;
		if(m==null) {
			result = -1;
		}else {
			result = dao.updatePassword(conn, userId, nPw);
			
			if(result >0) {commit(conn);}
			else {rollback(conn);}
			
		}
		close(conn);
		return result;
	}
	
}
