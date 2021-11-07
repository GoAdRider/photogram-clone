package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private User user;

	public PrincipalDetails(User user) {
		this.user = user;
	}

	//서비스 할때는 아래 항목들 정적으로 true or false 입력하는게 아니라
	//user.getExpired(); 같이 만들어서 리턴을 관리해줘야 한다.
	//하지만 지금은 서비스하는 프로젝트가 아니니 그냥 고정값 밖아 넣음
	
	
	//권한 : 한개가 아닐수 있음 그래서 Collection 타입 (어떤 사람은 3개 이상의 권한을 들고 있을 수 있으니까)
	//하지만 지금 여기 프로젝트에서는 권한을 한개만 들고있는다.
	//user 면 user 권한, admin이면 admin 권한
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {//권한을 가져오는 함수 => User 객체에 role 이 들고있음
		Collection<GrantedAuthority> collector = new ArrayList<>();
		
		
		/*
		//		collector에 권한 넣기
		//		첫번째 방법 -> 지저분한 방법 왜? 보기가 불편해
		collector.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		
		*/
		
		//		두번째 방법 => 람다식 사용
		//	목적은 add() 안에 함수를 넣는 것이다
		// 그런데 자바에서는 매개변수에 함수를 못 넣는다.
		// 자바에서는 함수가 일급객체가 아니기 때문에..
		// 그래서 add() 안에 인터페이스를 넣어야 한다.
		// 인터페이스가 함수를 들고있기 때문에..
		// 그래서 함수를 파라미터로 전달하고 싶으면
		// 자바에서는 무조건 인터페이스를 넘기게 되어있다. 혹은 클래스 오브젝트를 넘긴다
		// 그래서 람다식으로 함수를 넘긴다
		collector.add(()->{return user.getRole();});
		
		//		세번째 방법 =>  이중 콜론 연산자 인스턴스::메소드 표현식
		//collector.add(user::getRole);
		
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {	//계정이 만료되지 않았니?? => 만료되었으면 false, 만료되지 않았으면 true
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {	//계정이 안잠겼니? => 계정이 잠겼으면 false, 계정이 안잠겼다면 true => false 라면 로그인 안됨
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {	//자격이 만료되지 않았니?(비밀번호가 1년이 지났는데도 한번도 안바뀐건 아니니?)	=> 상관없이 진행 => true , 만약 false 로 되어있다면 로그인이 안됨
		return true;
	}

	@Override
	public boolean isEnabled() {	//계정이 활성화 되어있니?(계정이 10년동안 로그인 안하면 비활성화 됨 => false), true 로 바꿔줌
		return true;
	}

}
