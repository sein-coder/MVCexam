<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/views/common/header.jsp" %>
<%@ page import ="com.kh.notice.model.vo.Notice, java.util.List"  %>

<%
	List<Notice> list = (List<Notice>)request.getAttribute("list");
	
%>

<style>
    section#notice-container{width:600px; margin:0 auto; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse;}
    table#tbl-notice th, table#tbl-notice td {border:1px solid; padding: 5px 0; text-align:center;} 
    input#btn-add{float:right; margin: 0 0 15px;}
    table#tbl-notice{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
</style>
 
<section id="notice-container">
        <h2>공지사항</h2>
        <% if(loginMember!=null && 
        		(loginMember.getUserId()).equals("admin") ) {%>
        <input type="button" value="글쓰기" id="btn-add"/>
        <% } %>
        <script>
        	$(function(){
        		$("#btn-add").click(function(){
        			location.href="<%=request.getContextPath()%>/notice/noticeWrite";
        		});
        	});
        </script>
        <table id="tbl-notice">
            <tr>
            
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>첨부파일</th>
                <th>작성일</th>
            </tr>

	<!--직접구현-->
	<!--
	첨부파일은 webshare에 있는 file.png를
	web/images폴더를 만들고 거기에 넣고 불러옴
	filepath가 있으면 이미지(img)출력 없으면 공백출력 
        할것
	-->
			<% for(Notice n : list) { %>
			<tr>
				<td><%= n.getNotice_No() %></td>
				<td>
					<a href="<%=request.getContextPath() %>/notice/noticeView?notice_No=<%=n.getNotice_No()%>"><%= n.getNotice_Title() %></a>
				</td>
				<td><%= n.getNotice_Writer() %></td>
				<td><% if(n.getFilePath()!=null){ %>
					<img src="<%= request.getContextPath() %>/images/file.png" width="16px"/>
					<% } %>
				</td>
				<td><%= n.getNotice_Date() %></td>
			</tr>
			<% } %>
        </table>
        <div id="pageBar">
        	<%=request.getAttribute("pageBar") %>
        </div>
    </section>

<%@ include file = "/views/common/footer.jsp" %>