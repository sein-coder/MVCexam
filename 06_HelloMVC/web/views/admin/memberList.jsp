<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.List,com.kh.member.model.vo.Member" %>

<% 
	List<Member> list = (List<Member>)request.getAttribute("list"); 
	String searchType = (String)request.getAttribute("searchType");
	String searchKeyword = (String)request.getAttribute("searchKeyword");
	String numPerPage = (request.getAttribute("numPerPage").toString());
%>

<%@ include file = "/views/common/header.jsp" %>

 <style type="text/css">
	 section#memberList-container {text-align:center;}
	 
	 section#memberList-container table#tbl-member {width:100%; border:1px solid gray; border-collapse:collapse;}
	 section#memberList-container table#tbl-member th, table#tbl-member td {border:1px solid gray; padding:10px; }
	 div#search-container {margin:0 0 10px 0; padding:3px; background-color: rgba(0, 188, 212, 0.3);}
	 div#search-userName{display:none;}
	 div#search-gender{display:none;}
	 div#search-userId{display: inline-block;}
 
 	section#memberList-container div#neck-container{padding:0px; height: 50px; background-color:rgba(0, 188, 212, 0.3);}

	section#memberList-container div#search-container {margin:0 0 10px 0; padding:3px; float:left;}

	section#memberList-container div#numPerPage-container{float:right;}
	section#memberList-container form#numPerPageFrm{display:inline;}
 </style>

<script>
	$(function() {
		$("#searchType").change(function(){
			$("#search-userId").css("display","none");
			$("#search-userName").css("display","none");
			$("#search-gender").css("display","none");
			$("#search-"+$(this).val()).css("display","inline-block");
			
		});
		$("#searchType").trigger("change");
	});
</script>

	<section id="memberList-container">
		<h2>회원관리</h2>
		<!-- 검색기능 추가하기 -->
		<div id="neck-container">
			<div id="search-container">
				검색타입 :
				<select id="searchType">
					<option value="userId" <%="userId".equals(searchType)?"selected" : "" %>>아이디</option>
					<option value="userName" <%="userName".equals(searchType)?"selected":"" %>>이름</option>
					<option value="gender" <%="gender".equals(searchType)?"selected":"" %>>성별</option>
				</select>
				<div id="search-userId">
					<form action="<%=request.getContextPath()%>/admin/memberFinder">
						<input type="hidden" name="searchType" value="userId"/>
						<input type="text" name="searchKeyword" size="25" placeholder="아이디 입력"
						value='<%=searchType!=null&&searchType.equals("userId")?searchKeyword:""%>'>
						<button type="submit">검색</button>
					</form>
				</div>
				<div id="search-userName">
					<form action="<%=request.getContextPath()%>/admin/memberFinder">
						<input type="hidden" name="searchType" value="userName"/>
						<input type="text" name="searchKeyword" size="25" placeholder="회원이름 입력"
						value='<%=searchType!=null&&searchType.equals("userName")?searchKeyword:""%>'>
						<button type="submit">검색</button>
					</form>
				</div>
				<div id="search-gender">
					<form action="<%=request.getContextPath()%>/admin/memberFinder">
						<input type="hidden" name="searchType" value="gender"/>
						<label><input type="radio" name="searchKeyword" value="M"
						<%=searchKeyword!=null&&searchKeyword.equals("M")?"checked":""%>>남</label>
						<label><input type="radio" name="searchKeyword" value="F"
						<%=searchKeyword!=null&&searchKeyword.equals("F")?"checked":""%>>여</label>
										
						<button type="submit">검색</button>
					</form>
				</div>
			</div>
			<div id="numPerPage-container">
				페이지 회원수 :
				<% if(searchType!=null||searchKeyword!=null) { %>
				<form action="<%=request.getContextPath()%>/admin/memberFinder" id="numPerPageFrm">
					<select name="numPerPage" id="numPerPage" onchange="this.form.submit();">
						<option value="10" <%=numPerPage.equals("10")?"selected":"" %>>10</option>
						<option value="5" <%=numPerPage.equals("5")?"selected":"" %>>5</option>
						<option value="3" <%=numPerPage.equals("3")?"selected":"" %>>3</option>
						<%-- <%= (numPerPage!=null && numPerPage.equals("10"))?"selected":"" %> --%>
					</select>
					<input name="searchType" type="hidden" value="<%= searchType!=null?searchType:"" %>">
					<input name="searchKeyword" type="hidden" value="<%= searchKeyword!=null?searchKeyword:"" %>">
				</form>
				<% } else { %>
								<form action="<%=request.getContextPath()%>/admin/memberList" id="numPerPageFrm">
					<select name="numPerPage" id="numPerPage" onchange="this.form.submit();">
						<option value="10" <%=numPerPage.equals("10")?"selected":"" %>>10</option>
						<option value="5" <%=numPerPage.equals("5")?"selected":"" %>>5</option>
						<option value="3" <%=numPerPage.equals("3")?"selected":"" %>>3</option>
						<%-- <%= (numPerPage!=null && numPerPage.equals("10"))?"selected":"" %> --%>
					</select>
				</form>
				<% } %>
			</div>
		</div>
		
		<table id = "tbl-member">
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>성별</th>
				<th>나이</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>주소</th>
				<th>취미</th>
				<th>가입일자</th>
			</tr>
			
			<% for(Member m : list) { %>
			<tr>
				<td><%=m.getUserId() %></td>
				<td><%=m.getUserName() %></td>
				<td><%=m.getGender() %></td>
				<td><%=m.getAge() %></td>
				<td><%=m.getEmail() %></td>
				<td><%=m.getPhone() %></td>
				<td><%=m.getAddress() %></td>
				<td><%=m.getHobby() %></td>
				<td><%=m.getEnrollDate() %></td>
			</tr>
			<% } %>
			
		</table>
		
		<div id="pageBar">
			<%= request.getAttribute("pageBar") %>
		</div>
		
	</section>

<%@ include file = "/views/common/footer.jsp" %>
