package com.bluehub.exception;

import org.springframework.core.NestedCheckedException;

public class BlueHubBusinessException extends NestedCheckedException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    String message;
	public BlueHubBusinessException(String msg, Throwable cause) {
		
		super(msg, cause);
		this.message=msg;
		
		// TODO Auto-generated constructor stub
	}
	
	public  String getMessage(){
		return message;
	}

	
}
