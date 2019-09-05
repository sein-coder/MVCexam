<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/views/common/header.jsp" %>
<%@ page import="com.kh.board.model.vo.Board" %>

<% Board b = (Board)request.getAttribute("board"); %>

<style>
section#board-container{width:600px; margin:0 auto; text-align:center;}
section#board-container h2{margin:10px 0;}
table#tbl-board{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
table#tbl-board th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
table#tbl-board td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
</style>
   
		<div id="board-container">
		<h2>게시판</h2>
		<table id="tbl-board">
			<tr>
				<th>글번호</th>
				<td><%=b.getBoard_No() %></td>
			</tr>
			<tr>
				<th>제 목</th>
				<td><%=b.getBoard_Title() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%= b.getBoard_Writer() %></td>
			</tr>
			<tr>
				<th>조회수</th>
				<td><%= b.getBoard_ReadCount() %></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<% if(b.getBoard_Original_FileName()!=null) { %>
					<img src="<%= request.getContextPath()%>/images/file.png"><%= b.getBoard_Original_FileName() %>
					<% } %>					
<!-- 				 file사진으로 출력되고 누르면 다운로드 될수있게 설정
					이미지 옆에는 올린이름이 출력될 수 있도록 설정(originalfilename) -->
				</td>
			</tr>
			<tr>
				<th>내 용</th>
				<td><%= b.getBoard_Content() %></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="button" value="목록으로" onclick="fn_listBoard();">
			<%--글작성자/관리자인경우 수정삭제 가능 --%>
			<% if(loginMember!=null && ( loginMember.getUserId().equals("admin") || loginMember.getUserId().equals(b.getBoard_Writer()) ) ) { %>
					<input type="button" value="수정하기" onclick="fn_updateBoard()">
					<input type="button" value="삭제하기" onclick="fn_deleteBoard()">
			<% } %>
				</th>
			</tr>

		</table>

    <script>
	function fn_listBoard() {
		location.href='<%=request.getContextPath()%>/board/boardList?cPage=<%=request.getAttribute("cPage")!=null?request.getAttribute("cPage"):"1"%>';
	}
    function fn_updateBoard(){
      
    }
    function fn_deleteBoard(){
       
    }
    </script>

    </div>
<%@ include file = "/views/common/footer.jsp" %>
