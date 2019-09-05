package com.kh.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.admin.model.service.AdminService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberListServlet
 */
@WebServlet("/admin/memberList")
public class AdminMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminMemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
	
		if(session.getAttribute("loginMember")==null ||
				!((Member)session.getAttribute("loginMember")).getUserId().equals("admin")) {
			request.setAttribute("msg", "잘못된 경로로 접근하셨습니다.");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
		else {
			
			int cPage; //현재페이지, request에서 cPage값을 넘겨받으면 해당 페이지, 넘겨받지 못했으면 시작값인 1페이지로 초기화한다.
			try {
				cPage = Integer.parseInt(request.getParameter("cPage"));
				
			}catch(NumberFormatException e) {
				cPage = 1; 
			}
			int numPerPage = request.getParameter("numPerPage")!=null?Integer.parseInt(request.getParameter("numPerPage")):5;
			
			AdminService service = new AdminService();
			
			int totalData = service.selectCountMember();
			List<Member> list = service.selectMemberList(cPage,numPerPage);
			
			//페이징처리 --> 페이지 바 만들기
			
			String pageBar = "";
			int totalPage = (int)Math.ceil((double)totalData/numPerPage);
			int pageBarSize = 10;
			int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
			int pageEnd = pageNo + pageBarSize - 1;
			
			//pageBar 구성 소스코드 작성!
			//[이전] 만들기
			
			if(pageNo==1) {
				pageBar+="<span>[이전]</span>";
			}
			else {
				pageBar+="<a href='"+request.getContextPath()+"/admin/memberList?cPage="+(pageNo-1)+"&numPerPage="+numPerPage+"'> [이전] </a>"; 
			}
			
			//중간 클릭할 페이지 만들기
			
			while(!(pageNo>pageEnd||pageNo>totalPage)) {
				if(pageNo==cPage) {
					pageBar+="<span class='cPage'> "+pageNo+" </span>";
				}
				else {
					pageBar += "<a href='"+request.getContextPath()+"/admin/memberList?cPage="+pageNo+"&numPerPage="+numPerPage+"'> "+pageNo+" </a>";
				}
				pageNo++;
			}
			
			//[다음]만들기
			if(pageNo>totalPage) {
				pageBar+="<span> [다음] </span>";
			}
			else {
				pageBar += "<a href='"+request.getContextPath()+"/admin/memberList?cPage="+pageNo+"&numPerPage="+numPerPage+"'> [다음] </a>";
			}
			
			request.setAttribute("cPage", cPage);
			request.setAttribute("pageBar", pageBar);
			request.setAttribute("list", list);
			request.setAttribute("numPerPage", numPerPage);
			
			request.getRequestDispatcher("/views/admin/memberList.jsp").forward(request, response);
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
