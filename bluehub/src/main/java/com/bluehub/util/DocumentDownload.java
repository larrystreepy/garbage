package com.bluehub.util;

import com.bluehub.bean.user.UserProfile;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DocumentDownload extends AFileDownloader {

    private static final long serialVersionUID = -56221825833136122L;
    private static Logger logger = Logger.getLogger(DocumentDownload.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("DocumentDownload starts");

        String userId = request.getParameter("userId");
        if (userId == null) {
            UserProfile user = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userId = user.getId().toString();
        }

        logger.info("userId : " + userId);

        download(Constants.getPlatformProperyValue(Constants.FAREFILE_UPLOAD_PATH), userId, request, response);

        logger.info("DocumentDownload ends");
    }
}
