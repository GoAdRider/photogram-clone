package com.cos.photogramstart.handler;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController		//낚아채고 응답해줘야하니까 -> 데이터로 응답(RestController)
@ControllerAdvice	// 모든 Exception 을 다 낚아챔(intercept)
public class ControllerExceptionHandler {
	
	
	//CMRespDto, Script 비교
	//1. 클라이언트에게 응답할 때는 Script 가 좋음 (alert로 바로바로 보여주니까)
	//2. Ajax 통신, Android 통신은 CMRespDto 방식으로 사용하는게 좋음 (개발자가 코드로 응답받을 수 있으니까)
	
//	//Map 에 에러 메시지 담아서 Json 형식으로 뿌려주는 방식
//	// 데이터 리턴 방식 (Ajax 에서 사용)
//	@ExceptionHandler(CustomValidationApiException.class)
//	public CMRespDto<?> validationApiException(CustomValidationApiException e) {//CMRespDto<?> <- 제네릭 사용 ? 는 와일드카드임=> 즉 자동으로 CMRespDto<Map<String,String>> 으로 변환됨
//		System.out.println("ControllerExceptionHandler 호출");
//		return new CMRespDto<>(-1,e.getMessage(),e.getErrorMap());
//	}
	
	//javascript 코드를 만들어서 뿌려주는 방식
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		System.out.println("ControllerExceptionHandler 호출");
		
		// 파일을 보내지 않을 때 서버측에서 Validation 이 필요하다
		// ImageUploadDto 에서 @NotBlank 를 걸려고 했으나 멀티파일 타입에는 이 어노테이션이 지원이 안된다.
		// ImageController 에서 file이 안 들어왔을 때 익셉션을 던졌으나
		// errorMap 에 대한 null 이 존재하였기에 이 곳에서 errorMap 에 대한 분기가 필요하였다.
		if(e.getErrorMap()==null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString());//파라미터에 map 이 있으니 <> 안에도 Map 이 있어야함
		}
	}
	@ExceptionHandler(CustomException.class)
	public String exception(CustomException e) {
		System.out.println("ControllerExceptionHandler 호출");
		return Script.back(e.getMessage());
	}
	
	//ResponseEntity<> : Http 상태코드와 같이 응답으로 넘기는 방식
	//CMRespDto<> : Map 에 에러 메시지 담아서 Json 형식으로 뿌려준다
	// 데이터 리턴 방식 (Ajax 에서 사용)
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<CMRespDto<?>> validationApiException(CustomValidationApiException e) {//CMRespDto<?> <- 제네릭 사용 ? 는 와일드카드임=> 즉 자동으로 CMRespDto<Map<String,String>> 으로 변환됨
		System.out.println("ControllerExceptionHandler 호출");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);//BAD_REQUEST : 400 번(요청을 잘 못했을 때)
	}
	
	
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<CMRespDto<?>> validationApiException(CustomApiException e) {
		System.out.println("ControllerExceptionHandler 호출");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
}
