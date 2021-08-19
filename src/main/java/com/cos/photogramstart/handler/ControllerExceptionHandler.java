package com.cos.photogramstart.handler;


import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController		//낚아채고 응답해줘야하니까 -> 데이터로 응답(RestController)
@ControllerAdvice	// 모든 Exception 을 다 낚아챔(intercept)
public class ControllerExceptionHandler {
	
	
	//CMRespDto, Script 비교
	//1. 클라이언트에게 응답할 때는 Script 가 좋음 (alert로 바로바로 보여주니까)
	//2. Ajax 통신, Android 통신은 CMRespDto 방식으로 사용하는게 좋음 (개발자가 코드로 응답받을 수 있으니까)
	
	//첫번째 방법 => Map 에 에러 메시지 담아서 Json 형식으로 뿌려주는 방식
//	@ExceptionHandler(CustomValidationException.class)
//	public CMRespDto<?> validationException(CustomValidationException e) {//CMRespDto<?> <- 제네릭 사용 ? 는 와일드카드임=> 즉 자동으로 CMRespDto<Map<String,String>> 으로 변환됨
//		System.out.println("ControllerExceptionHandler 호출");
//		return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap());//파라미터에 map 이 있으니 <> 안에도 Map 이 있어야함
//	}
	
	//두번째 방법 => javascript 코드를 만들어서 뿌려주는 방식
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {//CMRespDto<?> <- 제네릭 사용 ? 는 와일드카드임=> 즉 자동으로 CMRespDto<Map<String,String>> 으로 변환됨
		System.out.println("ControllerExceptionHandler 호출");
		return Script.back(e.getErrorMap().toString());//파라미터에 map 이 있으니 <> 안에도 Map 이 있어야함
	}
}
