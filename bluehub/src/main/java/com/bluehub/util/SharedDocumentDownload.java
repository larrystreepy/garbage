package com.bluehub.util;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SharedDocumentDownload extends AFileDownloader {

    private static final long serialVersionUID = -56221825833136122L;
    private static Logger logger = Logger.getLogger(SharedDocumentDownload.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("SharedDocumentDownload starts");

        String usrId = request.getParameter("userId");
        logger.info("userId : " + usrId);

        download(Constants.getPropertyValue(Constants.FAREFILE_UPLOAD_PATH), usrId, request, response);

        logger.info("SharedDocumentDownload ends");
    }
}
