# 인스타그램 클론 코딩 Ver.0.4.3

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트


## Subscribe

### 수정된 부분

> 구독관련 예외처리 추가 ( 이제 예외 처리관련 메소드, 클래스 생성할 일 없을 듯)<br/>
> 예외처리 컨트롤러 : ControllerExceptionHandler.java<br/>
> 유효성검사 익셉션 클래스 : CustomValidationException.java, CustomValidationApiException.java<br/>
> 구독,구독취소 익셉션 클래스 : CustomApiException.java<br/>
> 총 4가지로 익셉션 핸들링
1. CustomApiException.java 생성
2. ControllerExceptionHandler.java 에 익셉션 등록
3. SubscribeService.java 에 try..catch 추가

---

### 1 CustomApiException.java 생성

[CustomApiException.java 접근](./src/main/java/com/cos/photogramstart/handler/ex/CustomApiException.java)

> error 가 여러개 터질 일이 없을 것 같은 Exception 이라서 errormap 은 지움
> 
> [CustomValidationException.java 와 비교하기](./src/main/java/com/cos/photogramstart/handler/ex/CustomValidationException.java)

```java
public class CustomApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomApiException(String message) {
		super(message);
		System.out.println("CustomApiException 호출");
	}
}
```

<br/><br/>

### 2 ControllerExceptionHandler.java 에 익셉션 등록

[ControllerExceptionHandler.java 접근](./src/main/java/com/cos/photogramstart/handler/ControllerExceptionHandler.java)

```java
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<CMRespDto<?>> validationApiException(CustomApiException e) {
		System.out.println("ControllerExceptionHandler 호출");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
```

<br/><br/>

### 3 SubscribeService.java 에 try..catch 추가

[SubscribeService.java 접근](./src/main/java/com/cos/photogramstart/service/SubscribeService.java)

> 삭제는 오류날 일이 없어서 try..catch 굳이 작성 안함

```java
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) { 
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		}catch(Exception e){
			throw new CustomApiException("이미 구독을 하였습니다");
		}
	}

	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) { 
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
```

<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
