package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;

@RestController		//낚아채고 응답해줘야하니까 -> 데이터로 응답(RestController)
@ControllerAdvice	// 모든 Exception 을 다 낚아챔(intercept)
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class)
	public Map<String, String> validationException(CustomValidationException e) {
		System.out.println("CustomValidationException 호출");
		return e.getErrorMap();
	}
}
