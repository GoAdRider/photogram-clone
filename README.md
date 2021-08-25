# 인스타그램 클론 코딩 Ver.0.3.4

## 구현완료

>
#### 회원가입, 로그인


## 사용자 업데이트

### 수정된 부분

>
1. CustomValidationException.java 메시지만 받는 생성자 생성
2. UserService.java id값(index) 못 찾을시 익셉션 추가
3. update.js 의 error 부분 400번 에러와 500 번 에러 if-else분기
---

<img width="524" alt="11" src="https://user-images.githubusercontent.com/57707484/130834351-dadc4a04-4689-4616-8057-c2925a809d5d.PNG">
ver.0.3.3 프론트단과 ver.0.3.4 백엔드단 추가정보를 그림으로 표현
  
  ---


### 1 CustomValidationException.java 메시지만 받는 생성자 생성

[CustomValidationException.java 접근](./src/main/java/com/cos/photogramstart/handler/ex/CustomValidationException.java)

```java
	public CustomValidationException(String message) {
		super(message);
	}
```

<br/>
<br/>
  
### 2 UserService.java id값(index) 못 찾을시 익셉션 추가

[UserService.java 접근](./src/main/java/com/cos/photogramstart/service/UserService.java)

> id 값(index) 을 못찾을 시 익셉션 띄우기 추가 : 기존에 .get() 에서 .orElseThrow() 로 변경
> 람다식으로 작성
> IllegalArgumentException() 으로 익셉션 띄워도 되지만 ControllerExceptionHandler 로 낚아채게끔 CustomValidationException 으로 익셉션 !

```java
  	// findById의 id 를 못찾았다면 Supplier 의 Exception 이 실행 됨
		// ControllerExceptionHandlerd 에서 낚아 챌 때 CMRespDTO 에서 ErrorMap 이 없고 Message만 있으니 map은 null로 넘어감 하지만 괜찮음 
		//2. 람다 식
    User userEntity = userRepository.findById(id).orElseThrow(()->{ return new CustomValidationException("찾을 수 없는 id 입니다"); });
```
  
<br/>
<br/>

### 3 update.js 의 error 부분 400번 에러와 500 번 에러 if-else분기

[update.js 접근](./src/main/resources/static/js/update.js)

```javascript
		if(error.data==null){//500번대 에러(데이터가 넘어오지 않았을 때)
			alert(error.responseJSON.message);
		}else{//400번대 에러(요청을 잘 못했을 때)
			alert(JSON.stringify(error.responseJSON.errorMap));
		}
```

<br/>
<br/>


### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
