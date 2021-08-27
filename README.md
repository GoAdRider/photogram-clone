# 인스타그램 클론 코딩 Ver.0.4.4

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트


## Subscribe

### 수정된 부분

> API 주소 시큐리티 config 설정
1. SecurityConfig.java 에 api 주소 추가

---

### 1 SecurityConfig.java 에 api 주소 추가

[SecurityConfig.java 접근](./src/main/java/com/cos/photogramstart/config/SecurityConfig.java)

> `.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**","/api/**").authenticated()` 에
> `/api/**` 경로 추가

```java
http.authorizeRequests()
	.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**","/api/**").authenticated()//해당 주소만 인증이 필요
	.anyRequest().permitAll()//허용함
	.and()
	.formLogin()
	.loginPage("/auth/signin")		// GET 방식
	.loginProcessingUrl("/auth/signin")	//POST 방식
	.defaultSuccessUrl("/");
```

<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
