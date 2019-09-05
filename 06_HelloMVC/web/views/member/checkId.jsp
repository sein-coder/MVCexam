<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
	boolean unable = (boolean)request.getAttribute("unable"); 
	String userId = request.getParameter("userId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중복 아이디 체크</title>
	<style>
		div#checkId-container {
			text-align: center;
			padding-top:50px;
		}
		span#duplicated{
			color : red;
			font-weight: bold;
		}		
	</style>
</head>
	
<body>
	<div id="checkId-container">
		<%if(unable) { %>
			[<span id="duplicated"><%=userId %></span>]는 이미 사용중입니다.	
			<form action="<%=request.getContextPath()%>/checkIdDuplicate" 
			name="checkIdDuplicate" method="post">
				<input type="text" name="userId" placeholder="아이디 입력">
			</form>
			<button onclick="checkId();">중복검사</button>
		<% } else {%>
			[<span id="duplicated"><%=userId %></span>]는 사용가능합니다.
			<br><br>
			<button onclick="fn_close();">닫기</button>
		<% } %>
	</div>	
	
	<script>
		function checkId() {
			var id = checkIdDuplicate.userId.value;
			if(!id || id.trim().length<4){
				alert("아이디는 4글자 이상 입력");
				return;
			}
			checkIdDuplicate.submit();
		}
		function fn_close(){
			//자식창에서 부모창 컨트롤하기 위한 방법
			opener.document.getElementById("userId_").value = '<%=userId%>';
			opener.document.getElementById("password_").focus();
			self.close();
		}
		
	</script>
	
</body>
</html>