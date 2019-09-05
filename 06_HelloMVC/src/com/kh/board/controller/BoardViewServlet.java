package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board/boardView")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int board_No = Integer.parseInt(request.getParameter("board_No"));
		int cPage;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}
		catch(NumberFormatException e) {
			cPage = 1;
		}
		Cookie[] cookies = request.getCookies();
		String boardCookieVal="";
		boolean hasRead = false;//읽었는지 안읽었는지 구분하는 기준
		
		if(cookies!=null) {
			for(Cookie c : cookies) {
				String name = c.getName(); //key값
				String value = c.getValue(); //value값
				if("boardCookie".equals(name)) {
					boardCookieVal = value;//이전값 보관
					if(value.contains("|"+board_No+"|")) {
						hasRead = true;
						break;
					}
				}
			}
		}
		//안읽었을때 조회수를 추가하고 cookie에 현재 board_No를 기록
		if(!hasRead) {
			Cookie c = new Cookie("boardCookie", boardCookieVal+"|"+ board_No +"|");
			c.setMaxAge(-1); //-1 은 브라우저가 닫히거나, 로그아웃했을때까지만 쿠키를 보관하다가 삭제한다는 뜻
			response.addCookie(c);
		}
		
		Board b = new BoardService().selectBoardView(board_No,hasRead);
		
		request.setAttribute("board", b);
		request.setAttribute("cPage", cPage);
		
		request.getRequestDispatcher("/views/board/boardView.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
