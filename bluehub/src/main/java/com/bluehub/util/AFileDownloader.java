package com.bluehub.util;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Abstract base class for handling a file download request.
 */
public class AFileDownloader extends HttpServlet {
    private static final long serialVersionUID = -56221825833136122L;
    private static Logger logger = Logger.getLogger(AFileDownloader.class);

    /**
     * Download a requested file.
     *
     * @param dir      The top-level source directory
     * @param subDir   Subdirectory containing file, pass null/empty if no subdirectory is used
     * @param request  The HTTP request, must contain the "fileName" parameter
     * @param response The HTTP response, the file content will be written to this response
     * @throws ServletException
     * @throws IOException
     */
    protected void download(String dir, String subDir, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fileName = request.getParameter("fileName");
        logger.debug("fileName==>" + fileName);

        OutputStream outPutStream = response.getOutputStream();
        try {
            final int index = fileName.lastIndexOf(".");
            String extension = index > 0 ? fileName.substring(index).toLowerCase() : "";
            final String mimeType;

            if (extension.contains("pdf")) {
                mimeType = "application/pdf";
            } else if (extension.contains("doc")) {
                mimeType = "application/msword";
            } else if (extension.contains("mp4")) {
                mimeType = "video/mp4";
            } else if (extension.contains("mp3")) {
                mimeType = "audio/mpeg";
            } else if (extension.contains("jpg")) {
                mimeType = "image/jpg";
            } else if (extension.contains("jpeg")) {
                mimeType = "image/jpeg";
            } else if (extension.contains("gif")) {
                mimeType = "image/gif";
            } else if (extension.contains("xls") || extension.contains("xlt") || extension.contains("xla")) {
                mimeType = "application/vnd.ms-excel";
            } else if (extension.contains("xlsx")) {
                mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            } else {
                mimeType = "image/png";
            }

            final boolean haveSubdir = subDir != null && !subDir.isEmpty();
            final File saveDir = haveSubdir ? new File(dir, subDir) : new File(dir);
            File inputFile = new File(saveDir, fileName);

            logger.debug("download input==>" + inputFile + "type=" + mimeType);

            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentType(mimeType);
            response.setCharacterEncoding("UTF-8");

            if (inputFile.exists()) {
                FileInputStream fin = new FileInputStream(inputFile);

                BufferedInputStream bin = new BufferedInputStream(fin);
                BufferedOutputStream bout = new BufferedOutputStream(outPutStream);
                int ch;
                while ((ch = bin.read()) != -1) {
                    bout.write(ch);
                }
                bin.close();
                fin.close();
                bout.close();
                outPutStream.close();
            }
        } catch (Exception e) {
            logger.error(Constants.LOG_ERROR + e.getMessage(), e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
