# 인스타그램 클론 코딩 Ver.0.5.4

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트, Subscribe


## Profile

### 수정된 부분

> #### Image 유효성 검사

> 1. ImageController.java 에서 file 에 대한 Validation 작업 추가 (파일 유효성 검사)
> 2. ControllerExceptionHandler.java 에서 errorMap 의 null 값 분기

---

<br/>

### 1. ImageController.java 에서 file 에 대한 Validation 작업 추가 (파일 유효성 검사)

[ImageController.java 접근](./src/main/java/com/cos/photogramstart/web/ImageController.java)

> 이미지가 첨부되지 않았을 때 Server 에서 Validation 체크가 필요하다.
> 
> ImageUploadDto.java 에서 MultipartFile file; 에 @NotBlank 를 넣으면 될 것 같지만
> 
> 멀티파일 타입에는 @NotBlank 어노테이션 지원이 안 된다고 한다.
> 
> 그렇기에 Presentaion Layer 인 Controller 에서 작업을 해야한다.

```java
@PostMapping("/image")
public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {

	if(imageUploadDto.getFile().isEmpty()) {
		throw new CustomValidationException("이미지가 첨부되지 않았습니다");
	}
```

<br/>

#### 하지만 위 와같이 작성만 하고 끝내면...

<br/>

![image](https://user-images.githubusercontent.com/57707484/131846612-718b90db-4d81-4653-a390-f656ab9a3f01.png)

> 이 처럼 에러창이 뜬다.

<br/><br/>

### 2. ControllerExceptionHandler.java 에서 errorMap 의 null 값 분기

[ControllerExceptionHandler.java 접근](src/main/java/com/cos/photogramstart/handler/ControllerExceptionHandler.java)

> 1번 에서 난 에러는 `ControllerExceptionHandler.java` 에서 `CustomValidationException.java` 의  
> 
> `errorMap` 에 대한 null 값이 존재하였기에 발생하였다. 
> 
> `ControllerExceptionHandler.java` 에서 errorMap 에 대한 null 값 분기를 하였다.

```java
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		if(e.getErrorMap()==null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString());
		}
	}
```

<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
