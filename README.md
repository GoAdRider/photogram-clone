# 인스타그램 클론 코딩 Ver.0.1.1

## 회원가입

### 수정된 부분
1. ExceptionHandler 부분 Map<String,String> 타입에서 String 타입으로..


### 1. ExceptionHandler 부분 Map<String,String> 타입에서 String 타입으로..

#### Before	---> 개발자에게 좋은방식 ---(Json)--> 코드로 응답받을수 있으니까
```java
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class)
	public Map<String, String> validationException(CustomValidationException e) {
		return e.getErrorMap();
	}
}
```
#### After	---> 클라이언트에게 좋은방식 ---(javascript)--> alert로 정보를 바로바로 보여주니까
```java
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		return Script.back(e.getErrorMap().toString());
	}
```

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
