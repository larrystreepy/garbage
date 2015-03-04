package com.bluehub.util;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DocumentSearch extends AFileDownloader {

    private static final long serialVersionUID = -56221825833136122L;
    private static Logger logger = Logger.getLogger(DocumentSearch.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("DocumentSearch starts");

        download(Constants.getPlatformProperyValue(Constants.FILE_UPLOAD_PATH), null, request, response);

        logger.info("DocumentSearch ends");
    }
}
