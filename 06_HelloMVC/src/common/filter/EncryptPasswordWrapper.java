package common.filter;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.util.Base64;

public class EncryptPasswordWrapper extends HttpServletRequestWrapper {

	public EncryptPasswordWrapper(HttpServletRequest request) {
		super(request);
	}
	
	//암호화를 하기 위해 client가 보낸 데이터를 불러오는 getParameter메소드를 오버라이딩 처리
	@Override
	public String getParameter(String key) {
		String value = "";
		if(key!=null && (key.equals("password") || key.equals("cPw") || key.equals("nPw"))) {
			value = getEncryptPw(super.getParameter(key));
			System.out.println(value);
		}else {
			value = super.getParameter(key);
		}
		return value;
	}
	
	private static String getEncryptPw(String pw) {
		//헤시알고리즘을 이용한 sha512방식으로 암호화 처리
		//java api에서 기본적으로 제공하는 암호화처리 객체가 있음 -> MessageDigest
		//암호화는 비트단위로 이루어진다.
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		}catch(NoSuchAlgorithmException e	) {
			e.printStackTrace();
		}
		//비트단위로 처리를 위해 바이트단위로 값을 쪼개야한다.
		byte[] bytes;
		try {
			bytes = pw.getBytes("UTF-8");
			md.update(bytes); //바이트로 쪼개진 값을 알고리즘에 따라 암호화
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String encPw = Base64.getEncoder().encodeToString(md.digest());
		
		return encPw;
	}
	
}
