<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file = "/views/common/header.jsp" %>
	
	<style>
		section#webCompiler-container{ width: 100%; text-align: center; }
		section#webCompiler-container textarea#inputcode{ resize: none; font-size: 25px; }
		div#language-container { text-align:left; padding-left: 610px }
		select#language-choice { position: relative; margin-right: 130px; font-size: 18px; }
		select#language-choice option { font-size: 18px; }
	</style>
	
	<section id="webCompiler-container">
		<h2>Web Compiler</h2>
		<div id="language-container">
		언어 선택 :
		<select id="language-choice">
			<option value="Java">Java</option>
			<option value="HTML">HTML</option>
			<option value="javaScript">javaScript</option>
		</select>
		<h3 style="display: inline; position: relative;">코드 입력 부분</h3>
		</div>
		<textarea name="inputcode" id="inputcode" rows="15" cols="60">public static void main(String args[]){	  }
		</textarea>
<!-- 		<h3>코드 출력부분</h3>
		<div id="code-output">
			<textarea rows="10" cols="100"></textarea>
		</div> -->
	</section>
	<script>
		$(function(){
			$("#language-choice").change(function(){
				if($(this).val()=="Java"){
					var code = "public static void main(String args[]){		}";
					$("#inputcode").val(code);
				}
			})
		})
		
	</script>

<%@ include file = "/views/common/footer.jsp" %>