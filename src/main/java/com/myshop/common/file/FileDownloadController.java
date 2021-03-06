package com.myshop.common.file;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@Controller
public class FileDownloadController {
	@RequestMapping("/download")
	protected void download(@RequestParam("fileName") String fileName,
	                        @RequestParam("goods_id") String goods_id,
	                        HttpServletResponse response, HttpServletRequest request) throws Exception {
		OutputStream out = response.getOutputStream();
		String CURR_IMAGE_REPO_PATH = request.getSession().getServletContext().getRealPath("/shopImage/shopping/file_repo");
		String filePath=CURR_IMAGE_REPO_PATH+"/"+goods_id+"/"+fileName;
		File image=new File(filePath);

		response.setHeader("Cache-Control","no-cache");
		response.addHeader("Content-disposition", "attachment; fileName="+fileName);
		FileInputStream in=new FileInputStream(image);
		byte[] buffer=new byte[1024*8];
		while(true){
			int count=in.read(buffer); //���ۿ� �о���� ���ڰ���
			if(count==-1)  //������ �������� �����ߴ��� üũ
				break;
			out.write(buffer,0,count);
		}
		in.close();
		out.close();
	}


	@RequestMapping("/thumbnails.do")
	protected void thumbnails(@RequestParam("fileName") String fileName,
	                          @RequestParam("goods_id") String goods_id,
	                          HttpServletResponse response, HttpServletRequest request) throws Exception {
		OutputStream out = response.getOutputStream();
		String CURR_IMAGE_REPO_PATH = request.getSession().getServletContext().getRealPath("/shopImage/shopping/file_repo");
		String filePath=CURR_IMAGE_REPO_PATH+"/"+goods_id+"/"+fileName;
		File image=new File(filePath);

		int lastIndex = fileName.lastIndexOf(".");
		String imageFileName = fileName.substring(0,lastIndex);
		if (image.exists()) {
			Thumbnails.of(image).size(121,154).outputFormat("png").toOutputStream(out);
		}
		byte[] buffer = new byte[1024 * 8];
		out.write(buffer);
		out.close();
	}
}
