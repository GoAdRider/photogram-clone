# 인스타그램 클론 코딩 Ver.0.2.1

## 구현완료

>
#### 회원가입, 로그인


## 로그인

### 수정된 부분
1. 로그인 관련 90% 완료 -> 로그인 가능, But.. Security Config 관련 90%
2. View 페이지 - Controller 연결
3. Config 일부 추가
4. User 에 대해 권한을 주는 클래스 `PrincipalDetails implements UserDetails`
5. Login 관련 세션관리해주는 클래스 `PrincipalDetailsService implements UserDetailsService` (로그인이면 내부적으로 `UserDetailsService` 가 intercept
6. 그 밖에 Front 단 일부 수정


### 3. Config 일부 추가
#### Before
```java
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin")
			.defaultSuccessUrl("/");
	}
```

#### After ---> `.loginProcessingUrl("/auth/signin")` : POST 방식으로 받을때 사용
```java
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//csrf 토큰 때문에 페이지가 안열리니 비활성화 해둠
		http.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin")	
			.loginProcessingUrl("/auth/signin")
			.defaultSuccessUrl("/");
	}
```

### 4. User 에 대한 권한 클래스

#### [PrincipalDetails implements UserDetails](./src/main/java/com/cos/photogramstart/config/auth/PrincipalDetails.java)

### 5. Login 관련 세션관리해주는 클래스

#### [PrincipalDetailsService implements UserDetailsService](./src/main/java/com/cos/photogramstart/config/auth/PrincipalDetailsService.java)

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
