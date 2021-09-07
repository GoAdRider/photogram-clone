package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException {
	//객체를 구분할 때 !! = 우리한테 중요한건 아님 JVM 에게 중요함
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap;
	
	public CustomValidationApiException(String message) {
		super(message);
		System.out.println("CustomValidationApiException 호출");
	}
	public CustomValidationApiException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
		System.out.println("CustomValidationApiException 호출");
	}
	
	public Map<String, String> getErrorMap() {
		return errorMap;
	}
}
