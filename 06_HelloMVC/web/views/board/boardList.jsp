<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/views/common/header.jsp" %>

<%@ page import = "java.util.List , com.kh.board.model.vo.Board" %>

<% 
	List<Board> list = (List<Board>)request.getAttribute("list");
	int cPage = (int)request.getAttribute("cPage");
%>


<style>
section#board-container{width:600px; margin:0 auto; text-align:center;}
section#board-container h2{margin:10px 0;}
table#tbl-board{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
table#tbl-board th, table#tbl-board td {border:1px solid; padding: 5px 0; text-align:center;} 
/*글쓰기버튼*/
input#btn-add{float:right; margin: 0 0 15px;}
/*페이지바*/
div#pageBar{margin-top:10px; text-align:center; background-color:rgba(0, 188, 212, 0.3);}
div#pageBar span.cPage{color: #0066ff;}
</style>

	<section id="board-container">
		<h2>게시판 </h2>
		<% if(loginMember!=null) { %>
			<input type="button" value="글쓰기" id="btn-add" onclick="fn_boardForm()">
		<% } %>
		<script>
			<%-- $(function(){
				$("#btn-add").click(function(){
					location.href="<%= request.getContextPath() %>/board/boardWrite";
				});
			}); --%>
			
			function fn_boardForm(){
				location.href='<%=request.getContextPath()%>/board/boardWrite';
			}
			
			
		</script>
		<table id="tbl-board">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>첨부파일</th>
				<th>조회수</th>
			</tr>
		<!--내용작성-->
			<% if(list.isEmpty()) { %>
				<tr>
					<td colspan="6">
						등록된 게시글이 없습니다.
					</td>
				</tr>
			<% } else { %>
			<% for(Board b : list) { %>
			<tr>
				<td><%= b.getBoard_No() %></td>
				<td>
					<a href="<%=request.getContextPath()%>/board/boardView?board_No=<%=b.getBoard_No()%>&cPage=<%=cPage%>"><%= b.getBoard_Title() %></a>
				</td>
				<td><%= b.getBoard_Writer() %></td>
				<td><%= b.getBoard_Date() %></td>
				<td>
					<% if(b.getBoard_Original_FileName()!=null) { %>
						<img src="<%=request.getContextPath()%>/images/file.png">
					<% } %>
				</td>
				<td><%= b.getBoard_ReadCount() %></td>
			</tr>
			<% }  %>
			<% }  %>
		</table>
		<!--pageBar도 있어야함-->
		<div id="pageBar">
			<%= request.getAttribute("pageBar") %>
		</div>
	</section>

<%@ include file = "/views/common/footer.jsp" %>
