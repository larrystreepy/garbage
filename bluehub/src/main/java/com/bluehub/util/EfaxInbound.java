package com.bluehub.util;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sfax.rest.SFax;
import com.sfax.rest.inbound.IDownloadPdf;
import com.sfax.rest.inbound.IRetrieve;
import com.sfax.rest.xml.ServiceResponse;

public class EfaxInbound {
	
	public static void callEFaxInbound() throws ParseException {
		
		String faxUsername = null;
		String faxNumber=null;
		String webApiKey=null;
		String vector=null;
		String encryptionKey=null;
		String downLoadPath = null;
		faxUsername = Constants.getEfaxPropertyValue("faxusername");
		faxNumber = Constants.getEfaxPropertyValue("faxnumber");
		webApiKey = Constants.getEfaxPropertyValue("webapikey");
		vector = Constants.getEfaxPropertyValue("vector");
		encryptionKey = Constants.getEfaxPropertyValue("encryptionkey");
		
		downLoadPath = Constants.getPropertyValue(Constants.FAX_DOWNLOAD_PATH);
		System.out.println("Quartzzz");
		
		
		SFax.init(faxUsername,webApiKey,vector,encryptionKey,faxNumber);//Enter a Valid Username, WebApiKey, Vector, EncryptionKey and Fax Number
		 ServiceResponse sr = null;
		String jsonData  = IRetrieve.inboundFaxRetrieveSet(); 
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(jsonData);
			Long length = (Long) json.get("FaxCount");
			List<JSONObject> list = new ArrayList<JSONObject>();
			list =  (List<JSONObject>) json.get("InboundFaxItems");
			for(int i = 0;i<list.size();i++){
				JSONObject js =   list.get(i);
				String faxnum = (String) js.get("FaxId");
				sr = IDownloadPdf.inboundFaxDownloadPdf(faxnum,downLoadPath+faxnum+".pdf"); //Enter a valid FaxId and Path with name of a file to save**/
		
//			 here the code for sending mail
		
			}
	
	}

}
