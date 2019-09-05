package com.kh.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberEnrollEndServlet
 */
@WebServlet(name="memberEnrollEnd", urlPatterns = "/memberEnrollEnd")
public class MemberEnrollEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberEnrollEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//필터적용방식
		//1. 어노테이션 방식
		//2. web.xml 직접등록
		
		//Client가 보낸 값 받기!
		String id = request.getParameter("userId");
		String pw = request.getParameter("password");
		String name = request.getParameter("userName");
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		char gender = request.getParameter("gender").charAt(0);
		String hobby[] = request.getParameterValues("hobby");
//		Date enrollDate = new java.sql.Date(new java.util.Date().getTime());
		
		//VO객체 이용해서 DB에 전송
		String hobbys = String.join(",", hobby);
		System.out.println(pw);
		Member m = new Member(id,pw,name,gender,age,email,phone,address,hobbys,null);
		
		//비즈니스로직
		//사용자가 보내준 데이터를 DB에 저장하고 결과를 반환
		
		int result = new MemberService().insertMember(m);
		
		String msg = result>0?"가입완료!":"가입실패!";
		String loc = "/";
		request.setAttribute("msg",msg);
		request.setAttribute("loc",loc);
		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
