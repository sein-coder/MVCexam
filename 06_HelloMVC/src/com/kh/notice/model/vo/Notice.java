package com.kh.notice.model.vo;

import java.sql.Date;

public class Notice {
	
	private int notice_No;
	private String notice_Title;
	private String notice_Writer;
	private String notice_Content;
	private Date notice_Date;
	private String filePath;
	private char status;
	
	public Notice() {
		// TODO Auto-generated constructor stub
	}

	public Notice(int notice_No, String notice_Title, String notice_Writer, String notice_Content, Date notice_Date,
			String filePath, char status) {
		super();
		this.notice_No = notice_No;
		this.notice_Title = notice_Title;
		this.notice_Writer = notice_Writer;
		this.notice_Content = notice_Content;
		this.notice_Date = notice_Date;
		this.filePath = filePath;
		this.status = status;
	}

	public int getNotice_No() {
		return notice_No;
	}

	public void setNotice_No(int notice_No) {
		this.notice_No = notice_No;
	}

	public String getNotice_Title() {
		return notice_Title;
	}

	public void setNotice_Title(String notice_Title) {
		this.notice_Title = notice_Title;
	}

	public String getNotice_Writer() {
		return notice_Writer;
	}

	public void setNotice_Writer(String notice_Writer) {
		this.notice_Writer = notice_Writer;
	}

	public String getNotice_Content() {
		return notice_Content;
	}

	public void setNotice_Content(String notice_Content) {
		this.notice_Content = notice_Content;
	}

	public Date getNotice_Date() {
		return notice_Date;
	}

	public void setNotice_Date(Date notice_Date) {
		this.notice_Date = notice_Date;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	
}
