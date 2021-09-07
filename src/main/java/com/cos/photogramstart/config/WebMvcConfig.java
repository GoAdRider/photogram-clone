package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {	// web 설정 파일
	
	//application.yml 에 있는 file의 path
	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			registry
				.addResourceHandler("/upload/**") //jsp 페이지에서 /upload/**  이런 주소 패턴이 나오면 발동 !
				.addResourceLocations("file:///"+uploadFolder) //위 addResourceHandler 파라미터의 내용과 같은 패턴이 나오면 발동되서 왼쪽의 파라미터 내용으로 바꿔줌
				.setCachePeriod(60*10*6) //캐시 기간, 초 단위임, 즉 왼쪽의 숫자는 1시간을 나타냄
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
		}
}
