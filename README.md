# 인스타그램 클론 코딩 Ver.0.2.2

## 구현완료

>
#### 회원가입, 로그인


## 사용자 업데이트

### 수정된 부분

>
1. 사용자 업데이트를 위한 세션 접근자 생성(직접 찾아가는방식, 어노테이션으로 찾아가는 방식)

---


### 1. 세션 접근자 생성

![image](https://user-images.githubusercontent.com/57707484/130081214-fee5e7e0-32d2-46ae-a5dc-a2e903bb5e52.png)


[찾아가기](./src/main/java/com/cos/photogramstart/web/UserController.java )

```java
@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) { 
		// 1. 어노테이션을 활용해서 얻은 세션 -> User 
		System.out.println("세션정보 : "+ principalDetails.getUser());

		// 2. 직접 객체로 찾아가서 얻은 세션 -> User
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();

		System.out.println("직접찾은 세션 정보" +mPrincipalDetails.getUser());

		return "user/update";
	}
```

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
