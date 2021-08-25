# 인스타그램 클론 코딩 Ver.0.3.3

## 구현완료

>
#### 회원가입, 로그인


## 사용자 업데이트

### 수정된 부분

>
1. 프론트 유효성 검사 추가
1-1. update.jsp 수정
1-2. update.js 수정
2. 백엔드단 CustomValidationApiException.java 추가 후 ControllerExceptionHandler 익셉션 메소드 추가
3. UserApiController.java 일부 수정
4. UserUpdateDTO 의 name, password 부분에 @NotBlank 어노테이션 추가
---


### 1-1 update.jsp 수정

[update.jsp 접근](./src/main/webapp/WEB-INF/views/user/update.jsp)

```html
	<form id="profileUpdate" onsubmit="update(${principal.user.id}, event)">
    
				<div class="content-item__02">
					<div class="item__title">이름</div>
					<div class="item__input">
						<input type="text" name="name" placeholder="이름"
							value="${principal.user.name}"  required="required"/>
					</div>
				</div>
    
    		<div class="content-item__04">
					<div class="item__title">패스워드</div>
					<div class="item__input">
						<input type="password" name="password" placeholder="패스워드" required = "required" />
					</div>
				</div>
    
    		<!--제출버튼-->
				<div class="content-item__11">
					<div class="item__title"></div>
					<div class="item__input">
						<button>제출</button>
					</div>
				</div>
				<!--제출버튼end-->

		</form>
```

### 1-2 update.js 수정

[update.js 접근](./src/main/resources/static/js/update.js)

```javascript
function update(userId, event) {
	event.preventDefault(); //form 태그 액션을 막기 !!(더이상 진행되지 않게)
  
	let data=$("#profileUpdate").serialize();	// profileUpdate 명의 <form> 태그안에 있는 모든 info 값을 담음
	
	console.log(data);
	
	$.ajax({
		type:"put",
		url:`/api/user/${userId}`,
		data:data,
		contentType:"application/x-www-form-urlencoded; charset=utf-8", // data가 무엇인지 설명하는 곳=> key:value 형식의 마임타입 =>application/x-www-form-urlencoded
		dataType:"json"
	}).done(res=>{//HttpStatus 상태코드 200 번대
		//res에 json을 javascript 로 parsing 해서 받음 : res 는 javascript 오브젝트임
		console.log("update 성공",res);
		location.href=`/user/${userId}`;
	}).fail(error=>{//HttpStatus 상태코드 200번대가 아닐 때
		alert(JSON.stringify(error.responseJSON.errorMap));
	});
}
```


### 1-2 백엔드단 CustomValidationApiException.java 추가 후 ControllerExceptionHandler 익셉션 메소드 추가

[CustomValidationApiException.java 접근](./src/main/java/com/cos/photogramstart/handler/ex/CustomValidationApiException.java)

[ControllerExceptionHandler.java 접근](./src/main/java/com/cos/photogramstart/handler/ControllerExceptionHandler.java)

#### CustomValidationApiException.java

> CustomValidationException과 똑같음

```java
public class CustomValidationApiException extends RuntimeException {
	//객체를 구분할 때 !! = 우리한테 중요한건 아님 JVM 에게 중요함
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap;
	
	public CustomValidationApiException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
		System.out.println("CustomValidationApiException 호출");
	}
	
	public Map<String, String> getErrorMap() {
		return errorMap;
	}
}
```

#### ControllerExceptionHandler.java
```java
	//ResponseEntity<> : Http 상태코드와 같이 응답으로 넘기는 방식
	//CMRespDto<> : Map 에 에러 메시지 담아서 Json 형식으로 뿌려준다
	// 데이터 리턴 방식 (Ajax 에서 사용)
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<CMRespDto<?>> validationApiException(CustomValidationApiException e) {//CMRespDto<?> <- 제네릭 사용 ? 는 와일드카드임=> 즉 자동으로 CMRespDto<Map<String,String>> 으로 변환됨
		System.out.println("ControllerExceptionHandler 호출");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);//BAD_REQUEST : 400 번(요청을 잘 못했을 때)
	}
```

### 3 UserApiController.java 일부 수정

[UserApiController.java 접근](./src/main/java/com/cos/photogramstart/web/api/UserApiController.java)

```java
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id, 
			@Valid UserUpdateDto userUpdateDto, 
			BindingResult bindingResult, // 꼭 @Valid 가 적혀있는 다음 파라미터에 적어야됨 !(안그러면 에러)
			@AuthenticationPrincipal PrincipalDetails principalDetails)
	{
		
		if(bindingResult.hasErrors()) {	// 정상이 아닐 때
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationApiException("유효성 검사 실패",errorMap);
		}else {	//정상일 때
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity);//세션정보변경
			return new CMRespDto<>(1, "회원수정완료", userEntity);
		}
	}
```


### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
