package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.policy.MyFileRenamePolicy;

/**
 * Servlet implementation class BoardWriteEndServlet
 */
@WebServlet("/board/boardWriteEnd")
public class BoardWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardWriteEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "게시판 글작성 파일업로드 오류");
			request.setAttribute("loc", "/board/boardWrite");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return;
		}

		String saveDir = getServletContext().getRealPath("/upload/board");
		int maxSize = 1024*1024*1024;
		
		MultipartRequest mr = new MultipartRequest(request, saveDir, maxSize, "UTF-8", new MyFileRenamePolicy());
		
		String title = mr.getParameter("title");
		String writer = mr.getParameter("writer");
		String content = mr.getParameter("content");
		String fileName = mr.getFilesystemName("up_file");
		String oriFileName = mr.getOriginalFileName("up_file");
		
		Board b = new Board();
		
		b.setBoard_Title(title);
		b.setBoard_Writer(writer);
		b.setBoard_Content(content);
		b.setBoard_Renamed_FileName(fileName);
		b.setBoard_Original_FileName(oriFileName);
		
		int result = new BoardService().insertBoard(b);
		//같은 session에서 nextval한 직후에 currval로 바로 가져올 수 있다.
		
		
		String msg = "";
		String loc = "";
		String view = "/views/common/msg.jsp";
		
		if(result>0) {
			loc = "/board/boardView?board_No="+result;
			msg = "게시글 등록 성공";
		}else {
			msg = "게시글등록실패";
			loc = "/board/boardForm";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(view).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
