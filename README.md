# 인스타그램 클론 코딩 Ver.0.0.1

## 회원가입

### 수정된 부분
1. 인증되지 않은 모든 사용자에 관한 security 구현 **(url 강제 redirection)**
2. CSRF 토큰 해제
3. 유효성 검사 (@Valid)
4. Exception AOP (글로벌 예외처리)


### 1. 인증되지 않은 모든 사용자에 관한 security 구현
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
```java
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration  // IoC 에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//기존에 있던 super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨.
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**").authenticated()//해당 주소만 인증이 필요
			.anyRequest().permitAll()//허용함
			.and()
			.formLogin()
			.loginPage("/auth/signin")
			.defaultSuccessUrl("/");
	}
}
```

### 2. CSRF 토큰 해제
> CSRF 토큰이란?
> 
> 서버에서는 뷰 페이지를 발행할 때 랜덤으로 생성된 **Token을** 같이 준 뒤 **사용자 세션에 저장해둔다.** 그리고 사용자가 서버에 작업을 요청할 때 페이지에 Hidden으로 숨어있는 Token 값이 같이 서버로 전송되는데, 서버에서는 이 Token 값이 세션에 저장된 값과 일치하는지 확인하여 해당 요청이 위조된게 아니라는 것을 확인한다. 일치 여부를 확인한 Token은 바로 폐기하고 새로운 뷰 페이지를 발행할 때마다 새로 생성한다.
> 
> ![image](https://user-images.githubusercontent.com/57707484/129766143-03cf41f6-4ebc-4659-bf42-ad0107ff0737.png)

#### CSRF 토큰 때문에 회원가입 페이지에서 폼 작성 후 가입 버튼을 눌렀다
#### 그런데 ! 다음 동작으로 넘어가지 않고 403 이 뜬다
#### 그래서 CSRF 토큰을 비활성화 시켰다

```java
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration  // IoC 에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // csrf 토큰 비 활성화
		
		//기존에 있던 super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨.
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**").authenticated()//해당 주소만 인증이 필요
			.anyRequest().permitAll()//허용함
			.and()
			.formLogin()
			.loginPage("/auth/signin")
			.defaultSuccessUrl("/");
	}
}
```

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
>
[[Spring Boot] @Valid 어노테이션으로 Parameter 검증하기](https://bamdule.tistory.com/35)
