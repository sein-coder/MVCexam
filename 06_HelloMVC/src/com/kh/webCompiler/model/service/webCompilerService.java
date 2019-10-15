package com.kh.webCompiler.model.service;

import java.io.File;
import java.io.IOException;

public class webCompilerService {

	public void Compile(String lang, File f) {
		switch (lang) {
		case "Java":
			try {
				Process process = Runtime.getRuntime().exec("javac "+f.getPath());
				try {
					System.out.println(f.getPath().substring(0, f.getPath().lastIndexOf(".")));
					process = Runtime.getRuntime().exec("java "+f.getPath().substring(0, f.getPath().lastIndexOf(".")));
				}catch(IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			break;

		case "HTML":
			break;
		case "JavaScript":
			break;
		}
		
	}

}
