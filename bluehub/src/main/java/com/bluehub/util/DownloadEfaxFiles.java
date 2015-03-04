package com.bluehub.util;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DownloadEfaxFiles extends AFileDownloader {

    private static final long serialVersionUID = -56221825833136122L;
    private static Logger logger = Logger.getLogger(DownloadEfaxFiles.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("DownloadEfaxFiles starts");

        download(Constants.getPlatformProperyValue(Constants.FAX_DOWNLOAD_PATH), null, request, response);

        logger.info("DownloadEfaxFiles ends");
    }
}
