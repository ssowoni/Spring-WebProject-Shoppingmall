package com.myspring.kh.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FileDownloadController {
	private static final String ARTICLE_IMAGE_REPO = "C:\\board\\board_image";
	private static final String PRODUCT_IMAGE_REPO = "C:\\product\\product_image";
	
	//url에 download가 호출되면 아래 메서드가 실행된다.
	@RequestMapping("/download.do")
	protected void download(@RequestParam("imagename") String imagename, 
							@RequestParam("num") String num,HttpServletResponse response)throws Exception{
		OutputStream out = response.getOutputStream();
		String downFile = ARTICLE_IMAGE_REPO+"\\"+num+"\\"+imagename;
		File file = new File(downFile);
		
		response.setHeader("Cache-Control","no-cache");
		response.addHeader("Content-disposition", "attachment; fileName="+imagename);
		FileInputStream in = new FileInputStream(file);
		
		byte[] buffer = new byte[1024 * 8];
		while(true) {
			int count = in.read(buffer); //버퍼에 읽어들인 문자 개수
			if(count==-1)//버퍼의 마지막에 도달했는지 체크
				break;
			out.write(buffer,0,count);
		}
		in.close();
		out.close();
	}
	
	//url에 download가 호출되면 아래 메서드가 실행된다.
		@RequestMapping("/downloadProduct.do")
		protected void download(@RequestParam("imagename") String imagename, HttpServletResponse response)throws Exception{
			OutputStream out = response.getOutputStream();
			String downFile = PRODUCT_IMAGE_REPO+"\\"+imagename;
			File file = new File(downFile);
			
			response.setHeader("Cache-Control","no-cache");
			response.addHeader("Content-disposition", "attachment; fileName="+imagename);
			FileInputStream in = new FileInputStream(file);
			
			byte[] buffer = new byte[1024 * 8];
			while(true) {
				int count = in.read(buffer); //버퍼에 읽어들인 문자 개수
				if(count==-1)//버퍼의 마지막에 도달했는지 체크
					break;
				out.write(buffer,0,count);
			}
			in.close();
			out.close();
		}

}
