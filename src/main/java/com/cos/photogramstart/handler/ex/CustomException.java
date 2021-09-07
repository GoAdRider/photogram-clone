package com.cos.photogramstart.handler.ex;


public class CustomException extends RuntimeException{

	//객체를 구분할 때 !! = 우리한테 중요한건 아님 JVM 에게 중요함
	private static final long serialVersionUID = 1L;
	
	//찾는 프로필 페이지가 없을때 보내는 익셉션 ( UserService.java 의 회원프로필() )
	public CustomException(String message) {
		super(message);
		System.out.println("CustomException 호출");
	}
	
}
