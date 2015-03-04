package com.bluehub.util;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DisplayImage extends AFileDownloader {

	private static final long serialVersionUID = -56221825833136122L;
	private static Logger logger = Logger.getLogger(DisplayImage.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		logger.info("DisplayImage starts");

		String fileName = request.getParameter("fileName");
        String emailId = request.getParameter("emailId");
		logger.debug("DisplayImage fileName==>" + fileName);
		logger.debug("DisplayImage emailId==>" + emailId);

        download(Constants.getPropertyValue(Constants.FILE_UPLOAD_PATH), emailId, request, response);

        logger.info("DisplayImage ends");
	}
}
