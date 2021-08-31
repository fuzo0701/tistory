package jspServletProgress;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
// web.xml servlet매핑하지 않는경우에는 주석제거후 사
//@WebServlet("/upload")
public class FileUploadServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public FileUploadServlet() {}

	//파일업로드는 post형식으로 바운더리로 요청이 오기 때문에 doGet은 필요가 없습니다.
	public void doPost(HttpServletRequest reqeust, HttpServletResponse response) throws IOException, ServletException {
		//헤더의 바운더리 영역을 취득한다.
		Collection<Part> parts = reqeust.getParts();
		// 헤더 영역을 전부 for문으로 탐색한다.
		for (Part part : parts) {
			//파일 관계된 바운더리 데이터가 아니면 패스
			if(!part.getHeader("Content-Disposition").contains("filename=")) {
				continue;
			}
			//헤더 바운더리 영역에서 파일 이름을 추출한다.
			String fileName = extractFileName(part.getHeader("Content-Disposition"));
			// 파일 이름이 null이 아니거나ㅏ 사이즈가 0보다 크면..
			if(fileName != null && part.getSize() > 0 ) {
				// 저장하고싶은 곳에 저장 
				part.write("/Users/ijinmyeong/upload/" + File.separator + fileName );
				part.delete();
			}
		}
		
	}

	// 바운더리 헤더에서 파일 이름을 추출하는 함수  
	private String extractFileName(String header) {
		// 바운더리의 헤더는 ;의 구분으로 데이터가 있다.
		for (String cd : header.split(";")) {
			if(cd.trim().startsWith("filename")){
				// '=' 부터 까지 따옴표는 제거
				String fileName = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
				// 그리고 디렉토리 표시 된것도 제거
				int index = fileName.lastIndexOf(File.separator);
				// 파일 이름 추출
				return fileName.substring(index + 1);
			}
		}
		// 위츼 조건에 맞지 않으면 null 리턴 
		return null;
	}
	
	
	
}
