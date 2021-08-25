package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;

@Controller
public class UserController {
	
	
	//사람마다 다를수 있으니 
	//  "/user/profile"로 매핑받지 말고
	//  "/user/{id}" 로 매핑받는다
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id) {
		return "user/profile";
		
	}
	
//   -----------------------------------------------------------------------------------------------------------------------
	
	//원래 세션은 HttpSession 이라는 객체를 사용하면 그 key 값과 value값을 확인하기 쉬웠다.
	//그러나 SpringSecurity 에서는 Session 을 열라 복잡하게 관리한다
	
	
	// [POST] 방식으로 /auth/signin 요청하면
	// SpringSecurity 가 낚아 챈다 -> 왜? Config 에 .loginProcessingUrl("/auth/signin")	설정 해놨으니까..
	// 그리고 내가 만들어 놓은 PrincipalDetailsService 객체에게 넘긴다 -> 
	// loadUserByUsername(String username) 메소드를 사용하여 UserDetails 타입으로 바꾼다
	// PrincipalDetailsService 내부에서 username 이 있는지 확인한다.
	// 없으면? ----> 쫓겨낸다 
	// 있으면?
	// return 한 PrincipalDetails 객체를 Session 에 저장한다.   --> PrincipalDetails 는 내가 만든 객체이지만 UserDetails 을 상속받은 객체이기에 같은타입이라 사용가능
	
	// 하지만 위에서도 말했지만 Security 의 Session 영역은 일반적인 세션 영역이 아니다
	// 아래와 같은 구조이다
	// Session 영역 -> SecurityContextholder -> Authentication -> PrincipalDetails -> User 
	// 와우 복잡하다...
	// 위와 같은 복잡한 방식으로 찾아가는 방법 
	//	-------------------------------------------------------------------------------------------
	//   :  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	//		PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
	//		mPrincipalDetails.getUser();
	//	-------------------------------------------------------------------------------------------
	// 하지만 Authentication 객체에 바로 접근할 수 있는 어노테이션이 있다 => 그것은 !
	// @@AuthenticationPrincipal  이다 !!
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
}
