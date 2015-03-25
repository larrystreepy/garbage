package com.bluehub.util;

import com.sfax.rest.SFax;
import com.sfax.rest.outbound.OCreate;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Utility methods to support fax operations.
 */
public class FaxSupport {

    /**
     * Send a file to the given fax number.
     *
     * @param toFaxNumber Fax number to send to
     * @param filePath    Path to file to send
     * @return Parsed JSON response from fax service
     * @throws ParseException
     */
    public static JSONObject sendFax(String toFaxNumber, String filePath)
            throws ParseException {

        String faxUsername = Constants.getEfaxPropertyValue("faxusername");
        String myFaxNumber = Constants.getEfaxPropertyValue("faxnumber");
        String webApiKey = Constants.getEfaxPropertyValue("webapikey");
        String vector = Constants.getEfaxPropertyValue("vector");
        String encryptionKey = Constants.getEfaxPropertyValue("encryptionkey");

        // Ensure the fax number is in a reasonable format (no hyphens or spaces)
        toFaxNumber = toFaxNumber.trim();
        toFaxNumber = toFaxNumber.replace("-", "");
        toFaxNumber = toFaxNumber.replace(" ", "");

        // We only allow faxing to the US for now, so ensure that the number starts with a 1
        if (toFaxNumber.charAt(0) != '1') toFaxNumber = "1" + toFaxNumber;

        SFax.init(faxUsername, webApiKey, vector, encryptionKey, myFaxNumber);
        String response = OCreate.outboundFaxCreate(toFaxNumber, "Requesting_Clinical_Information", filePath, "");

        return (JSONObject) new JSONParser().parse(response);
    }
}
