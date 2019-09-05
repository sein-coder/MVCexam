package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name="LoginServlet", urlPatterns =  "/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//클라이언트가 보낸 값을 받아온다.
		String id = request.getParameter("userId");
		String pw = request.getParameter("password");
		
		//로그인 저장 관리하기
		//session은 서버가 관리, cookie는 클라이언트가 관리
		//단 값을 저장하는것은 둘다 서버가 한다.
		String saveId = request.getParameter("saveId");
		System.out.println(saveId);
		
//		try {
//			throw new NullPointerException();
//			
//		}catch(NullPointerException e) {
//			request.setAttribute("e", e);
//			request.getRequestDispatcher("/views/common/error.jsp").forward(request, response);
//		}
		
		//체크했을시 on값이 넘어오고 미체크시 null값으로 넘어온다.
		
		
		//비지니스 로직수행
		//id와pw를 가지고 가서 DB에 일치하는 값이 있는지 확인하고 결과를 가져옴
		MemberService service = new MemberService();
		Member m = service.selectId(id,pw);
		//view에서 보여줄 메세지
		String msg = "";
		//view에서 다른 뷰로 이동하는 페이지 주소
		String loc = "/"; //성공하나 실패하나 index.jsp 페이지로 간다.
		String view = "";
		
		//기준!
		if(m!=null) {
			//로그인이 됐을 때 session에 로그인 결과에 대한 값을 유지시킨다!
			//http통신 web stateless(상태유지를 하지 않기 때문에)
			//특정값을 유지되는 곳에 저장을 하고 확인
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", m);
			view = "/";
			
			//session 속성값 설정!
			//setMaxInactiveInterval() : session 생존주기를 관리
			// * 매개변수의 second만큼만 session을 유지
			// getCreationTime() : 생성된 시간
			// getLastAccessedTime() : 마지막 요점 시간
			// invalidate() : 세션종료
			
			//session.setMaxInactiveInterval(60); //매개변수 1당 1초 
			//해당 초가 끝나면 session이 끝난다. 즉, 저장되어있던 데이터가 사라짐
			
			
			//아이디 Cookie를 이용해서 저장하기!
			//Cookie는 클라이언트측에서 저장하는 데이터를 말하고
			//서블릿에서 Cookie객체를 가지고 있음.
			
			if(saveId!=null) { //on이라는 뜻
				Cookie c = new Cookie("saveId", id);
				c.setMaxAge(3*24*60*60);//3일간 유지하기, 매개변수값을 초단위로 계산한다.
				response.addCookie(c); //response에 쿠키데이터를 추가
				//서버에서 저장될때 Cookie는 Cookie배열형태로 저장된다.
			}else { //미체크상태시
				Cookie c = new Cookie("saveId", id);
				c.setMaxAge(0); //0초만 유지, 즉 바로 지운다는뜻
				response.addCookie(c);
			}
			
			
		}
		else {
			
			//로그인이 안될 때
			msg = "아이디나 패스워드가 일치하지 않습니다.";
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			view = "/views/common/msg.jsp";
			
		
		}
		
		//넘겨줄 정보를 request안에 속성값으로 넣어준다.
		
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
