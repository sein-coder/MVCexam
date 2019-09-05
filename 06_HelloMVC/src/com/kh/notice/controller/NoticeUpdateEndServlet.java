package com.kh.notice.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class NoticeUpdateEndServlet
 */
@WebServlet("/notice/noticeUpdateEnd")
public class NoticeUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "공지사항작성오류![form:enctype 관리자자에게 문의하세요]");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return;
		}
		
		String saveDir = getServletContext().getRealPath("/upload/notice");
		
		int maxSize = 1024*1024*10;

		MultipartRequest mr = new MultipartRequest(request, saveDir,maxSize,"UTF-8",new DefaultFileRenamePolicy());
		
		String title = mr.getParameter("title");
		String writer = mr.getParameter("writer");
		String content = mr.getParameter("content");
		String fileName = mr.getFilesystemName("up_file");
		String oriFileName = mr.getParameter("ori_file");
		int notice_No = Integer.parseInt(mr.getParameter("notice_No"));
		
		File f = mr.getFile("up_file");
		
		if(f!=null && f.length()>0) {
			File deleteFile = new File(saveDir+"/"+oriFileName);
			boolean result = deleteFile.delete();
		}else {
			fileName = oriFileName;
		}

//		//기존의 파일에서 파일이 바뀌었을 경우 서버에서 기존 파일의 삭제
//		if(fileName!=oriFileName) {
//			File file = new File(saveDir+"/"+oriFileName);
//			if( file.exists() ){
//	            if(file.delete()){
//	                System.out.println("파일삭제 성공");
//	            }else{
//	                System.out.println("파일삭제 실패");
//	            }
//	        }else{
//	            System.out.println("파일이 존재하지 않습니다.");
//	        }
//		}
		
		
		Notice n = new Notice();
		n.setNotice_Title(title);
		n.setNotice_Writer(writer);
		n.setNotice_Content(content);
		n.setFilePath(fileName);
		n.setNotice_No(notice_No);
		
		
		int result = new NoticeService().updateNotice(n);
		String msg = "";
		String loc = "";
		String views = "/views/common/msg.jsp";
		if(result>0) {
			msg = "공지사항 수정이 완료되었습니다.";
			
//			request.getRequestDispatcher("/notice/noticeView?notice_No="+notice_No).forward(request, response);;
		}else {
			msg = "공지사항 수정 실패";
//			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
		loc = "/notice/noticeUpdate?notice_No="+notice_No;
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(views).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
