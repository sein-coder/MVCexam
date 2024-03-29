package com.kh.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cPage;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}catch (NumberFormatException e) {
			cPage = 1;
		}
		int numPerPage = request.getParameter("numPerPage")!=null?Integer.parseInt(request.getParameter("numPerPage")):5;
		
		BoardService service = new BoardService();
		
		int totalData = service.selectBoardCount();
		List<Board> list = service.selectBoardList(cPage,numPerPage);
		
		String pageBar = "";
		int totalPage = (int)Math.ceil((double)totalData/numPerPage);
		int pageBarSize = 10;
		int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd = pageNo + pageBarSize - 1;
		
		if(pageNo==1) {
			pageBar += "<span> [이전] </span>";
		}else {
			pageBar += "<a href='"+request.getContextPath()+"/board/boardList?cPage="+(pageNo-1)+"'> [이전] </a>";
		}
		
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(pageNo == cPage) {
				pageBar += "<span> "+pageNo+" </span>";
			}else {
				pageBar += "<a href='"+request.getContextPath()+"/board/boardList?cPage="+pageNo+"'> "+pageNo+" </a>";
			}
			pageNo++;
		}
		
		if(pageNo>totalPage) {
			pageBar += "<span> [다음] </span>";
		}else {
			pageBar += "<a href='"+request.getContextPath()+"/board/boardList?cPage="+pageNo+"'> [다음] </a>";
		}
		
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.setAttribute("cPage", cPage);
		
		request.getRequestDispatcher("/views/board/boardList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
