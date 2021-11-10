package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration  // IoC 에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//csrf 토큰 때문에 페이지가 안열리니 비활성화 해둠
		http.csrf().disable();
		
		//super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨.
		
		
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**","/api/**").authenticated()//해당 주소만 인증이 필요
			.anyRequest().permitAll()//위 주소 제외한 부분은 다 허용함
			.and()
			.formLogin()						// <form> 태그있는 form 로그인 
			.loginPage("/auth/signin")			// GET 방식 : (파라미터)form 로그인이 있는 페이지
			.loginProcessingUrl("/auth/signin")	//POST 방식
			.defaultSuccessUrl("/");			// 로그인이 성공했으면 / url 로 가게 함
	}
}
