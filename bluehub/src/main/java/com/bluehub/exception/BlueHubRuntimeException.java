package com.bluehub.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class BlueHubRuntimeException extends NestableRuntimeException {
    public String message;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BlueHubRuntimeException(String msg){		
		this.message=msg;		
	}
	
	public  String getMessage(){
		return message;
	}

}
