# 인스타그램 클론 코딩 Ver.0.3.1

## 구현완료

>
#### 회원가입, 로그인


## 사용자 업데이트

### 수정된 부분

>
1. update.jsp 일부수정, header.jsp 에 principal 추가 ,update.js 추가
2. UserUpdateDto.java 추가
3. UserApiController.java 추가
---


### 1. update.jsp 일부수정, header.jsp 에 principal 추가 ,update.js 추가


[update.jsp 접근](./src/main/webapp/WEB-INF/views/user/update.jsp)

[header.jsp 접근](./src/main/webapp/WEB-INF/views/layout/header.jsp)

[update.js 접근](./src/main/resources/static/js/update.js )

#### header.jsp
> #### 인증된 정보에 접근하는 방법(세션정보 접근)
> 백엔드단에서 Model 로 데이터를 넣지 않아도 principal 로 jsp 전역에서 사용가능
```html
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var = "principal"/>
</sec:authorize>
```

####  pom.xml

> pom.xml 에 시큐리티 태그 라이브러리 추가해야 사용가능

```xml
	<!-- 시큐리티 태그 라이브러리 -->
	<dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-taglibs</artifactId>
	</dependency>
```

#### update.jsp 일부 중

```html
	<div class="content-item__02">
		<div class="item__title">이름</div>
		<div class="item__input">
			<input type="text" name="name" placeholder="이름"
				value="${principal.user.name}" />
		</div>
	</div>
```

#### 2. UserUpdateDto.java 추가

[UserUpdateDto.java 접근](./src/main/java/com/cos/photogramstart/web/dto/user/UserUpdateDto.java )

```java
@Data
public class UserUpdateDto {

	private String name;		//필수
	private String password;	//필수
	// 밑에서부터 옵션
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	//조금 위험함. 코드 수정이 필요할 예정
	public User toEntity() {
		return User.builder()
				.username(name)
				.password(password)
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
```

#### 3. UserApiController.java 추가

[UserApiController.java 접근](./src/main/java/com/cos/photogramstart/web/api/UserApiController.java)

```java
@RestController
public class UserApiController {
	// 테스트(변경필요)
	@PutMapping("/api/user/{id}")
	public String update(UserUpdateDto userUpdateDto) {
		System.out.println(userUpdateDto);
		return "ok";
	}
}
```

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
