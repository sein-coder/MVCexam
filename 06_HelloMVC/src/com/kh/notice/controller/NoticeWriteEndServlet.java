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
 * Servlet implementation class NoticeWriteEndServlet
 */
@WebServlet("/notice/noticeWriteEnd")
public class NoticeWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeWriteEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//multipart로 전송되었는지 request를 확인한다!
		//ServletFileUpload객체를 이용한다.
		//isMultipartContent(request) -> multipart인지아닌지 확인가능
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "공지사항작성오류![form:enctype 관리자자에게 문의하세요]");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return;
		}
		//파일업로드 작성 진행
		//1.파일을 저장할 서버의 실제경로(파일시스템상 경로)를 불러온다.
		//2.파일저장 최대크기를 결정. (여러사람이 이용하기때문에 적정크기를 정해야한다.)
		//3.cos.jar에서 지원하는 MultipartRequest객체를 생성한다.
		//끝!
		
		String root = getServletContext().getRealPath("/"); //가장 최상이 되는 root경로에서 context경로까지를 불러온다.
		
		String saveDir = root+"/upload/notice"; //실제로 파일을 저장할 위치경로를 지정 
		String saveDir2 = root+File.separator+"upload"+File.separator+"notice";
		
		//업로드 파일 크기 설정
		int maxSize = 1024*1024*10; //바이트 * 킬로바이트 * 10 = 10MB
		//MultipartRequest객체생성시 매개변수가 있어야한다.
		//매개변수
		//첫번째매개변수 : request값
		//두번째매개변수 : file저장경로
		//세번째매개변수 : 파일저장 최대크기
		//네번째매개변수 : 인코딩값
		//다섯번째매개변수 : rename정책(파일이름),중복이 안되도록, 중복파일이 있을시 (1)과 같이 숫자가 붙도록
		MultipartRequest mr = new MultipartRequest(request, saveDir,maxSize,"UTF-8",new DefaultFileRenamePolicy());
		
		String title = mr.getParameter("title");
		String writer = mr.getParameter("writer");
		String content = mr.getParameter("content");
		
		String fileName = mr.getFilesystemName("up_file");
		
		Notice n = new Notice();
		n.setNotice_Title(title);
		n.setNotice_Writer(writer);
		n.setNotice_Content(content);
		n.setFilePath(fileName);
		
		int result = new NoticeService().insertNotice(n);
		
		if(result>0) {
			request.getRequestDispatcher("/notice/noticeList").forward(request, response);;
		}else {
			request.setAttribute("msg", "글쓰기 실패");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
