package common.policy;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File oldFile) {
		
		File newFile = null;
		do {
			//날짜(시간)+임의의값으로 rename 설정
			long currentTime = System.currentTimeMillis(); //밀리세컨초까지 받아서 옴
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
			int rndNum = (int)(Math.random()*1000);
			
			//파일명처리
			String oldName = oldFile.getName();
			String ext = "";
			
			int point = oldName.lastIndexOf(".");
			if(point>-1) {
				ext = oldName.substring(point); //ext는 확장자를 의미~				
//			String[] t = oldName.split(".");
//			ext = [t.length-1];
			}
			
			//새파일 생성
			String newName = sf.format(new Date(currentTime))+"_"+rndNum + ext;
			
			newFile = new File(oldFile.getParent(),newName);
			//oldFile.getParent() : 파일의 부모는 즉, 폴더이다. 결과적으로 directory(경로)값이 나옴
					
		}while(!createNewFile(newFile));
		
		return newFile;
	}
	//덮어쓰기 방지를 위한 메소드
	private boolean createNewFile(File newFile) {
		try {
			return newFile.createNewFile();
		}catch(IOException e) {
			return false;
		}
	}

}
