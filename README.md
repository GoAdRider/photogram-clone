# 인스타그램 클론 코딩 Ver.0.3.2

## 구현완료

>
#### 회원가입, 로그인


## 사용자 업데이트

### 수정된 부분

>
1. UserService.java 에 영속화
2. UserApiController 에 세션 정보 변경 작업
3. update.js 에 업데이트 성공시 url 변경작업
---


### 1. UserService.java 에 영속화

[UserService.java 접근](./src/main/java/com/cos/photogramstart/service/UserService.java )

<img width="257" alt="123" src="https://user-images.githubusercontent.com/57707484/130614627-7ef1b52f-a7b7-44e4-9714-a84cbf1bc593.PNG">

> Spring 서버와 DB 사이에 영속성 컨텍스트라는 것이 존재함
> 
> 1. findById 로 ① 번 정보로 DB 의 User 정보를 찾음 그리고 영속성 컨텍스트에 ① 번 정보로 저장해놓음(**영속화 됨**)
> 
> 2. 영속화 된 데이터에 id, username, password 등 user 정보가 저장됨
> 
> 3. 영속성 컨텍스트 안에 있는 정보들을 변경만 하면 알아서 DB 에 저장됨(**더티체킹**)


> #### 더티체킹(Dirty Checking) 이란?
> 
> Dirty란 상태의 변화가 생긴 상태
> 
> 즉, Dirty Checking 이란 **상태변경검사** 정도로 이해하면 된다.


```java
@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public User 회원수정(int id, User user) {
		// 첫번째 - 영속화
		// findById : Optional<T> 객체
		User userEntity = userRepository.findById(id).get(); // 나중에 orElseThrow() 로 바꿀 예정
		
		// 두번째 - 영속화된 오브젝트를 수정 - 더티체킹이 일어남(업데이트가 완료 됨)
		userEntity.setName(user.getUsername());
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		userEntity.setPassword(encPassword);
		
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		return userEntity;
	}//더티체킹이 일어나서 업데이트가 완료 됨
}
```

### 2. UserApiController 에 세션 정보 변경 작업

[UserApiController.java 접근](./src/main/java/com/cos/photogramstart/web/api/UserApiController.java )

```java
@PutMapping("/api/user/{id}")
public CMRespDto<?> update(@PathVariable int id, UserUpdateDto userUpdateDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
	User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
	principalDetails.setUser(userEntity);//세션정보변경
	return new CMRespDto<>(1, "회원수정완료", userEntity);
}
```

### 3. update.js 에 업데이트 성공시 url 변경작업

[update.js 접근](./src/main/resources/static/js/update.js )

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
