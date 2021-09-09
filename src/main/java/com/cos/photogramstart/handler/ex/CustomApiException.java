package com.cos.photogramstart.handler.ex;


public class CustomApiException extends RuntimeException {
	//객체를 구분할 때 !! = 우리한테 중요한건 아님 JVM 에게 중요함
	private static final long serialVersionUID = 1L;
	
	//error 가 여러개 터질 일이 없을 것 같은 Exception 이라서 errormap 은 지움
	
	public CustomApiException(String message) {
		super(message);
//		System.out.println("CustomApiException 호출");
	}
}
