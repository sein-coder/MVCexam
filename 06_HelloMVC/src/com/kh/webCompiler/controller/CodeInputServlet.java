package com.kh.webCompiler.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.webCompiler.model.service.webCompilerService;

/**
 * Servlet implementation class CodeInputServlet
 */
@WebServlet("/webCompiler/codeInput")
public class CodeInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeInputServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lang = request.getParameter("language-choice");
		String inputCode = request.getParameter("inputcode");
		
		String saveDir = getServletContext().getRealPath("/upload/inputCode");
		File f = new File(saveDir+"/"+"Code.java");
		if(!(f.exists())) {
			f.createNewFile();
		}
		BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(f));
		
		bo.write(inputCode.getBytes());
		
		bo.close();
		
		new webCompilerService().Compile(lang,f);
		
//		Runtime.getRuntime().exec(f.getPath());
		
//		bo.write(inputCode.getBytes());
		
		/*
		 * File file = new File(request.getContextPath()+"/upload/inputCode/test.java");
		 */
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
