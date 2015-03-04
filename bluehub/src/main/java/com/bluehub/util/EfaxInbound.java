package com.bluehub.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sfax.rest.SFax;
import com.sfax.rest.inbound.IDownloadPdf;
import com.sfax.rest.inbound.IRetrieve;
import com.sfax.rest.xml.ServiceResponse;

public class EfaxInbound {

    private static final Logger logger = Logger.getLogger(EfaxInbound.class);

    public static void callEFaxInbound() throws ParseException {

        String faxUsername = Constants.getEfaxPropertyValue("faxusername");
        String faxNumber = Constants.getEfaxPropertyValue("faxnumber");
        String webApiKey = Constants.getEfaxPropertyValue("webapikey");
        String vector = Constants.getEfaxPropertyValue("vector");
        String encryptionKey = Constants.getEfaxPropertyValue("encryptionkey");

        String downLoadPath = Constants.getPlatformProperyValue(Constants.FAX_DOWNLOAD_PATH);

        if (logger.isDebugEnabled()) logger.debug("Poll inbound EFax queue");

        SFax.init(faxUsername, webApiKey, vector, encryptionKey, faxNumber);
        String jsonData = IRetrieve.inboundFaxRetrieveSet();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonData);

        //noinspection unchecked
        for (JSONObject js : (List<JSONObject>) json.get("InboundFaxItems")) {
            String faxId = (String) js.get("FaxId");
            ServiceResponse sr = IDownloadPdf.inboundFaxDownloadPdf(faxId, downLoadPath + faxId + ".pdf");

//			 here the code for sending mail

        }

    }

}
