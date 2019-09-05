package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class EncryptFilter
 */
@WebFilter(servletNames = {
		"LoginServlet",
		"memberEnrollEnd",
		"updatePasswordEnd",
		"changepassword"
})
public class EncryptFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EncryptFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//단방향은 암호화만 가능, 패스워드 같은 느낌.....원본복원이 불가능 md5, sha512,256 현재 둘다 권장하지 않는다.
		//양방향은 암호화,복호화 둘 다 가능, 모든것에 적용이 가능 
		
		//filter를 통해서 요청이 들어왔을때 암호화를 진행하도록한다.
		//wrapper클래스를 이용해서 encryption(암호화)을 한다.
		//암호화된 값을 servlet으로 넘긴다.
		EncryptPasswordWrapper encPw = new EncryptPasswordWrapper((HttpServletRequest)request);
		
		
		chain.doFilter(encPw, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
