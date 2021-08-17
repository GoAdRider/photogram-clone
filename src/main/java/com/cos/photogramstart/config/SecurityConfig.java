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
		http.csrf().disable();
		
		//super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨.
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**").authenticated()//해당 주소만 인증이 필요
			.anyRequest().permitAll()//허용함
			.and()
			.formLogin()
			.loginPage("/auth/signin")
			.defaultSuccessUrl("/");
	}
}
