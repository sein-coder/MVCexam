package com.kh.webCompiler.model.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class webCompilerService {

	public void compile(String lang, File f) {
		switch (lang) {
		case "Java":
			javaCompile(f);
			break;

		case "Python":
			break;
		}
		
	}
	
	public void javaCompile(File f) {
		try {
			Runtime.getRuntime().exec("javac "+f.getPath());
			
			String fileName = f.getName().substring(0, f.getName().lastIndexOf('.'));
			
			Thread.sleep(2000);
			
			Process pro = Runtime.getRuntime().exec("java "+fileName, null, new File(f.getParent()));

			InputStream is = pro.getInputStream();
			InputStreamReader isr = new InputStreamReader(is,"MS949");
			BufferedReader bs = new BufferedReader(isr);
			String line;
			while((line = bs.readLine())!=null) {
				System.out.println(line);
			}
			
			InputStream eis = pro.getErrorStream();
			InputStreamReader eisr = new InputStreamReader(eis,"MS949");
			BufferedReader ebs = new BufferedReader(eisr);
			String eline;
			while((eline = ebs.readLine())!=null) {
				System.out.println(eline);
			}
		}
		catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
		}
	}

}
