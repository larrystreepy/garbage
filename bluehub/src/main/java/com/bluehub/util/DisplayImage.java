package com.bluehub.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DisplayImage extends HttpServlet {

	private static final long serialVersionUID = -56221825833136122L;
	private static Logger logger = Logger.getLogger(DisplayImage.class);

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("DisplayImage starts");
		String fileName = null;
		String emailId = null;
		String saveDirectory = null;
		String syntax = null;
		String mimeType = null;
		String extension = null;

		fileName = request.getParameter("fileName");
		emailId = request.getParameter("emailId");
		logger.debug("DisplayImage fileName==>" + fileName);
		logger.debug("DisplayImage emailId==>" + emailId);
		saveDirectory = Constants.getPropertyValue(Constants.FILE_UPLOAD_PATH);
		syntax = Constants.getPropertyValue(Constants.FILE_UPLOAD_SYNTAX);
		;

		OutputStream outPutStream = response.getOutputStream();
		try {
			if (StringUtils.isNotBlank(emailId)) {
				emailId = emailId + syntax;
			}
			saveDirectory = saveDirectory + emailId + fileName;
			logger.debug("DisplayImage saveDirectory==>" + saveDirectory);
			extension = fileName.substring(fileName.lastIndexOf("."));
			if (extension != null && !extension.isEmpty()
					&& extension.contains("pdf")) {
				mimeType = "application/pdf";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("doc")) {
				mimeType = "application/msword";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("mp4")) {
				mimeType = "video/mp4";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("MP4")) {
				mimeType = "video/mp4";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("mp3")) {
				mimeType = "audio/mpeg";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("MP3")) {
				mimeType = "audio/mpeg";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("jpg")) {
				mimeType = "image/jpg";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("JPG")) {
				mimeType = "image/jpg";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("jpeg")) {
				mimeType = "image/jpeg";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("JPEG")) {
				mimeType = "image/jpeg";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("gif")) {
				mimeType = "image/gif";
			} else if (extension != null && !extension.isEmpty()
					&& extension.contains("GIF")) {
				mimeType = "image/gif";
			} else {
				mimeType = "image/png";
			}
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			response.setContentType(mimeType);
			response.setCharacterEncoding("UTF-8");

			File file = new File(saveDirectory);
			if (file.exists()) {
				FileInputStream fin = new FileInputStream(saveDirectory);

				BufferedInputStream bin = new BufferedInputStream(fin);
				BufferedOutputStream bout = new BufferedOutputStream(
						outPutStream);
				int ch = 0;
				while ((ch = bin.read()) != -1) {
					bout.write(ch);
				}
				bin.close();
				fin.close();
				bout.close();
				outPutStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.LOG_ERROR + e.getMessage());
		}

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}
}
