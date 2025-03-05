package com.food.basic.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;



//@Component //스프링에서 클래스 자동관리
public class FileManagerUtils {
	
	
	//현재폴더를 운영체제에 맞게 문자열로 반환
		public static String getDateFolder() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 포맷 방식
			Date date = new Date(); //오늘 날짜 정보
			
			String str = sdf.format(date); //"2024-05-16" 폴더명 문자열
			
			//File.separator : 이 코드를 실행하는 운영체제별로 파일의 경로구분자를 리턴
			/*
			유닉스, 맥, 리눅스 구분자 : / 예)"2024-05-16" -> "2024/05/16"
			윈도우 구분자 : \ 예)"2024-05-16" -> "2024\05\16"
			*/
			return str.replace("-", File.separator);
		}
		//파일업로드
		/*
		String uploadFolder : 업로드 폴더명 C:\\Dev\\upload\\pds
		String dateFolder : 날짜폴더명 "2024\\05\\16"
		MultipartFile uploadFile : 클라이언트에서 전송한 파일
		*/
		public static String uploadFile(String uploadFolder, String dateFolder, MultipartFile uploadFile) { 
			
			String realUploadFileName = ""; //클라이언트에서 업로드한 파일
			
			//File클래스 : JDK제공, 파일과 폴더 관련 기능을 제공
			/*
			 File file = new File(파일 또는 폴더정보구성); file.명령어(속성과 메소드)
			 -파일 또는 폴더 존재여부확인
			 -파일 또는 폴더 생성
			 -파일 또는 폴더 속성확인 
			 */
			File file = new File(uploadFolder, dateFolder); // 예)"C:/Dev/upload/pds" "2024/05/16"
			
			//"2024/05/16" 폴더가 존재하지 않으면 폴더생성
			if(file.exists() == false) {
				file.mkdirs();
			}
			//클라이언트에서 보낸 파일명
			String clientFileName = uploadFile.getOriginalFilename(); //예)abc.png
			
			//uuid : 범용 고유 식별자, 고유한 값 생성
			UUID uuid = UUID.randomUUID(); //예)2f48f241-9d64-4d16-bf56-70b9d4e0e79a
			
			//예)2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png
			realUploadFileName = uuid.toString() + "_" + clientFileName;
			
			//예외처리
			try {
				File saveFile = new File(file, realUploadFileName);
				
				uploadFile.transferTo(saveFile); //파일복사(원본)
				
				//Thumnail 작업(원본파일 해상도 크기를 줄여 섬네일이미지 생성하기)
				
				if(checkImageType(saveFile)) {
					File thumnailFile = new File(file, "s_" + realUploadFileName);
					
					BufferedImage bo_img = ImageIO.read(saveFile);
					double ratio = 3;
					int width = (int) (bo_img.getWidth() / ratio);
					int height = (int) (bo_img.getHeight() / ratio);
					
					Thumbnails.of(saveFile)
					         .size(width, height)
					         .toFile(thumnailFile);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return realUploadFileName; //실제 업로드 파일명 예)2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png
		}
		//업로드파일의 MIME타입 확인, 즉 이미지파일 또는 일반파일 여부를 체크
		public static boolean checkImageType(File saveFile) {
			
			boolean isImageType = false;
			
			try {
				//MIME : text/html, text/plain, image/jpde
				//클라이언트에서 전송한 파일의 MIME정보 추출
				String contentType = Files.probeContentType(saveFile.toPath());
				
				//contentType 변수의 내용이 "image"문자열 시작여부 boolean값으로 반환
				isImageType = contentType.startsWith("image");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return isImageType;
		}
		//이미지파일을 웹브라우저 화면에 보이는 작업
		//<img src="abc.gif"> <img src="매핑주소">
		/*
		  String uploadPath : 서버 업로드 폴더 예)"C:/Dev/upload/pds"
		  String fileName : 이미지 파일명(날짜 폴더명 포함)
		 */
		//파일 업로드 되는 폴더가 프로젝트 외부에 존재하여 보안적 이슈가 있으므로 업로드 파일들을 바이트 배열로 읽어서 클라이언트로 보낸다 
		public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName) throws Exception {
			ResponseEntity<byte[]> entity = null;
			
			File file = new File(uploadPath, fileName);
			
			if(!file.exists()) {
				return entity;
			}
			HttpHeaders headers = new HttpHeaders();
			//Files.probeContentType(file.toPath()) : MIME TYPE 정보 예)image/jpeg
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			
			entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
			
			return entity;
		}
		//이미지파일 삭제
		/*
		 String uploadPath : 서버 업로드 폴더
		 String folderName : 날짜 폴더명
		 String fileName : 파일명 (날짜 폴더명 포함)
		*/
		public static void delete(String uploadPath, String dateFolderName, String fileName, String type) {
			
			//2)원본 파일 fileName.substring(2) = s_ 를 빼고 원본파일명으로 만듦 69624b20-c0fc-4ce7-9b6c-edf5c9fada43_duke.png
			File file2 = new File((uploadPath + "\\" + dateFolderName + "\\" + fileName.substring(2)).replace('\\', File.separatorChar));
			if(file2.exists()) file2.delete();
			
			if(type.equals("image")) {
			
			//1)thumbnail파일 s_69624b20-c0fc-4ce7-9b6c-edf5c9fada43_duke.png
			File file1 = new File((uploadPath + "\\" + dateFolderName + "\\" + fileName).replace('\\', File.separatorChar));
			if(file1.exists()) file1.delete();
			

			}
		}
}
