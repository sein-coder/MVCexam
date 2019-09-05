package com.kh.board.model.vo;

import java.sql.Date;

public class Board {
	
	private int board_No;
	private String board_Title;
	private String board_Writer;
	private String board_Content;
	private String board_Original_FileName;
	private String board_Renamed_FileName;
	private Date board_Date;
	private int board_ReadCount;
	
	public Board() {
		// TODO Auto-generated constructor stub
	}

	public Board(int board_No, String board_Title, String board_Writer, String board_Content,
			String board_Original_FileName, String board_Renamed_FileName, Date board_Date, int board_ReadCount) {
		super();
		this.board_No = board_No;
		this.board_Title = board_Title;
		this.board_Writer = board_Writer;
		this.board_Content = board_Content;
		this.board_Original_FileName = board_Original_FileName;
		this.board_Renamed_FileName = board_Renamed_FileName;
		this.board_Date = board_Date;
		this.board_ReadCount = board_ReadCount;
	}

	public int getBoard_No() {
		return board_No;
	}

	public void setBoard_No(int board_No) {
		this.board_No = board_No;
	}

	public String getBoard_Title() {
		return board_Title;
	}

	public void setBoard_Title(String board_Title) {
		this.board_Title = board_Title;
	}

	public String getBoard_Writer() {
		return board_Writer;
	}

	public void setBoard_Writer(String board_Writer) {
		this.board_Writer = board_Writer;
	}

	public String getBoard_Content() {
		return board_Content;
	}

	public void setBoard_Content(String board_Content) {
		this.board_Content = board_Content;
	}

	public String getBoard_Original_FileName() {
		return board_Original_FileName;
	}

	public void setBoard_Original_FileName(String board_Original_FileName) {
		this.board_Original_FileName = board_Original_FileName;
	}

	public String getBoard_Renamed_FileName() {
		return board_Renamed_FileName;
	}

	public void setBoard_Renamed_FileName(String board_Renamed_FileName) {
		this.board_Renamed_FileName = board_Renamed_FileName;
	}

	public Date getBoard_Date() {
		return board_Date;
	}

	public void setBoard_Date(Date board_Date) {
		this.board_Date = board_Date;
	}

	public int getBoard_ReadCount() {
		return board_ReadCount;
	}

	public void setBoard_ReadCount(int board_ReadCount) {
		this.board_ReadCount = board_ReadCount;
	}
	
}
