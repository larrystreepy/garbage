package com.bluehub.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Provides static access to metadata regarding the current compute/server instance.  This is only available
 * when running within GCS.  If used outside of GCS, provided default values will be used for all metadata queries.
 */
public class ServerMetaData {
    private static final Logger logger = Logger.getLogger(ServerMetaData.class);

    private static final String MD_SERVER_URL_BASE = "http://169.254.169.254/computeMetadata/v1/instance/";

    /**
     * Read the value of the named attribute from the metadata server.
     *
     * @param attrName The name of the attribute to read
     * @param defaultValue Default value to use if data can't be read
     * @return attribute value or default value if unavailable
     */
    public static String getAttribute(String attrName, String defaultValue) {
        return readMetaData("attributes/" + attrName, defaultValue);
    }

    /**
     * Read a given metadata entry from the server.  The given path is assumed to be relative to the instance root.
     * The value is simply read as 1 line of output from the query, so the metadata must be readable as such.
     *
     * @param mdPath       Path to metadata
     * @param defaultValue Value to use if metadata value is not found, or we 're not running within GCS
     * @return metadata value or default value if unavailable
     */
    private static String readMetaData(String mdPath, String defaultValue) {
        try {
            final URLConnection urlConnection = new URL(MD_SERVER_URL_BASE + mdPath).openConnection();
            urlConnection.setConnectTimeout(2000); // Wait 2 seconds instead of default 20
            final BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line = r.readLine();

            if (line != null) {
                if (logger.isDebugEnabled()) logger.debug("MD value " + mdPath + "=" + line);
                return line.trim();
            } else {
                logger.warn("MD missing: " + mdPath + " -- using default: " + defaultValue);
                return defaultValue;
            }
        } catch (IOException e) {
            logger.info("Not running in GCS, return " + defaultValue + " for " + mdPath);
        }

        return defaultValue;
    }

    private ServerMetaData() {
        // no instances
    }
}
