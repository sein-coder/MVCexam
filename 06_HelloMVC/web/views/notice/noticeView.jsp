<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/views/common/header.jsp" %>
<%@ page import="com.kh.notice.model.vo.Notice" %>

<% Notice n = (Notice)request.getAttribute("notice"); %>


	<style>
	section#notice-container{width:600px; margin:0 auto; text-align:center;}
	section#notice-container h2{margin:10px 0;}
	table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
	table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
	table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
	</style> 
	
	<section id="notice-container">
		<h2>공지사항</h2>
		<table id="tbl-notice">
		
			<tr>
				<th>제 목</th>
				<td><%= n.getNotice_Title() %></td>
			</tr>
			
			<tr>
				<th>작성자</th>
				<td><%= n.getNotice_Writer() %></td>
			</tr>
			
			<tr>
				<th>첨부파일</th>
				<td>
				<% if(n.getFilePath()!=null) { %>
					<a href="<%=request.getContextPath()%>/notice/fileDown?fileName=<%=n.getFilePath()%>">
						<img src="<%=request.getContextPath()%>/images/file.png" width="16px"/>
					</a>
				<% } %>
				</td>
			</tr>
			
			<tr>
				<th>내 용</th>
				<td><%= n.getNotice_Content() %></td>
			</tr>
			<%if(loginMember!=null&&loginMember.getUserId().equals("admin")) { %>
			<tr>
				<th colspan="2" style="text-align: center;">
				<input type="button" value="수정하기" onclick="location.href='<%= request.getContextPath() %>/notice/noticeUpdate?notice_No=<%= n.getNotice_No() %>'" ><!-- 새로운 페이지로 수정 -->
				<input type="button" value="삭제하기" onclick="location.href='<%= request.getContextPath() %>/notice/noticeDelete?notice_No=<%= n.getNotice_No() %>'" ><!-- status = 'N' 으로 업데이트 -->
				</th>
			</tr>
			<% } %>
		</table>
	</section>

<%@ include file = "/views/common/footer.jsp" %>
