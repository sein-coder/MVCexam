package com.kh.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.admin.model.service.AdminService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberFinderServlet
 */
@WebServlet("/admin/memberFinder")
public class AdminMemberFinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminMemberFinderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String searchKeyword = request.getParameter("searchKeyword");
		String searchType = request.getParameter("searchType");
		
		int cPage;
		
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
			
		}catch(NumberFormatException e) {
			cPage = 1;
		}
		int numPerPage = request.getParameter("numPerPage")!=null?Integer.parseInt(request.getParameter("numPerPage")):5;
		
		AdminService service = new AdminService();
		
		int totalData = service.selectSearchCount(searchType, searchKeyword);
		List<Member> list = service.selectSearch(cPage, numPerPage, searchType, searchKeyword);
		
		String pageBar = "";
		int totalPage = (int)Math.ceil((double)totalData/numPerPage);
		int pageBarSize = 10;
		int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd = pageNo + pageBarSize - 1;
		
		if(pageNo==1) {
			pageBar+="<span>[이전]</span>";
		}
		else {
			pageBar+="<a href='"+request.getContextPath()+"/admin/memberFinder?cPage="+(pageNo-1)
					+"&searchType="+searchType
					+"&searchKeyword="+searchKeyword
					+"&numPerPage="+numPerPage
					+"'> [이전] </a>"; 
		}
		
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(pageNo==cPage) {
				pageBar+="<span class='cPage'> "+pageNo+" </span>";
			}
			else {
				pageBar += "<a href='"+request.getContextPath()+"/admin/memberFinder?cPage="+pageNo
						+"&searchType="+searchType
						+"&searchKeyword="+searchKeyword
						+"&numPerPage="+numPerPage
						+"'> "+pageNo+" </a>";
			}
			pageNo++;
		}

		if(pageNo>totalPage) {
			pageBar+="<span> [다음] </span>";
		}
		else {
			pageBar += "<a href='"+request.getContextPath()+"/admin/memberFinder?cPage="+pageNo
					+"&searchType="+searchType
					+"&searchKeyword="+searchKeyword
					+"&numPerPage="+numPerPage
					+"'> [다음] </a>";
		}
		
		request.setAttribute("cPage", cPage);
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.setAttribute("numPerPage", numPerPage);
		
		request.setAttribute("searchType", searchType);
		request.setAttribute("searchKeyword", searchKeyword);
		
		
		request.getRequestDispatcher("/views/admin/memberList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
