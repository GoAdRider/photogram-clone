package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{

	//객체를 구분할 때 !! = 우리한테 중요한건 아님 JVM 에게 중요함
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap;
	
	public CustomValidationException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
		System.out.println("CustomValidationException 호출");
	}
	
	public Map<String, String> getErrorMap() {
		return errorMap;
	}
}
