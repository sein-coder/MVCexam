<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.member.model.vo.Member" %>

<%-- <% String a = (String)request.getAttribute("abc"); 
	a.charAt(0);
%> --%>

<%
	Member loginMember = (Member)session.getAttribute("loginMember");
	Cookie[] cookies = request.getCookies();
	String saveId = null;
	if(cookies!=null){
		for(Cookie c : cookies){
			//Cookie객체에는 getName() / getValue() 내장함수가 각각 존재한다.
			System.out.println("name : "+ c.getName());
			System.out.println("value : "+c.getValue());
			
			if(c.getName().equals("saveId")){
				saveId = c.getValue();	
			}
		}
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HelloMVC</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" type="text/css"> <!-- CSS파일 호출 -->
<script src="<%= request.getContextPath() %>/js/jquery-3.4.1.min.js"></script>
 <link href="https://fonts.googleapis.com/css?family=Dokdo|Gamja+Flower|Yeon+Sung&display=swap" rel="stylesheet">
</head>
<body>

<!-- header - content/section(aside,nav,article) - footer 의 시멘트구조-->

<!-- header -->
<header>
	<h1>Hellow MVC</h1>
	    
    <% if(loginMember==null) {%>
	<div class="login-container">
        <form action="<%= request.getContextPath() %>/login" method="POST" onsubmit="return validate();">
        <!-- validate();는 유효성검사용 메소드 -->
            <table>
                <tr>
                    <td><input type="text" name="userId" 
                    	id="userId" placeholder="아이디입력" value = <%=saveId!=null?saveId:"" %>></td>
                    									<!-- 삼항연산자를 이용해서 간단하게 작성 -->
                    <td></td>
                </tr>
                <tr>
                    <td><input type="password" name="password" id="password" placeholder="비밀번호입력"></td>
                    <td><input type="submit" value="로그인"></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="checkbox" name="saveId" id="saveId"
                        <%= saveId!=null?"checked":"" %>>
                        
                        <label for="saveId">아이디저장</label>&nbsp;&nbsp;
                        
                        <input type="button" value="회원가입" onclick="location.href='<%=request.getContextPath()%>/memberEnroll';">
                        
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <%} else {%>
    <div class="login-container">
    	<table id="Logged-in">
    		<tr>
    			<td><%= loginMember.getUserName() %>님, 어서오세요.</td>
    		</tr>
    		<tr>
    			<td>
    				<input type="button" value="내정보보기" onclick="location.href='<%= request.getContextPath() %>/myPage?userId=<%=loginMember.getUserId()%>';">
    				<input type="button" value="로그아웃" onclick="location.href='<%=request.getContextPath()%>/logout';">
    				<!-- 절대경로를 써줘야한다. 절대경로란 프로젝트 루트컨텍스트부터 시작하는 것 -->
    			</td>
    		</tr>
    	</table>
    </div>
    <% } %>
    <!-- 메인메뉴 -->
    <nav>
        <ul class="main-nav">
            <li  class="home">
                <a href="<%= request.getContextPath() %>/">Home</a>
            </li>
            <li id="notice">
                <a href="<%= request.getContextPath()%>/notice/noticeList">공지사항</a>
            </li>
            <li id="board">
                <a href="<%= request.getContextPath()%>/board/boardList">게시판</a>
            </li>
            <li id="gallary">
                <a href="#">사진게시판</a>
            </li>
            <li id="webCompiler">
            	<a href="<%= request.getContextPath() %>/webCopiler/webCopilerView">웹 컴파일러</a>
            </li>
            <% if(loginMember!=null && loginMember.getUserId().equals("admin")) { %>
            <li id="admin-member">
            	<a href="<%=request.getContextPath()%>/admin/memberList">회원관리
            	<span class="animate_line"></span>
            	</a>
            </li>
            <% } %>
        </ul>
    </nav>
</header>

<script>
	function validate(){
		if($("#userId").val().trim().length<4){
			alert("아이디가 4글자 이상 입력하세요");
			$("#userId").focus();
			return false;
		}
		if($("#password").val().trim().length==0){
			alert("비밀번호를 입력하세요!");
			$("#password").focus();
			return false;
		}
		return true;
	}
</script>





