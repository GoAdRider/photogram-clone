package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	//패스워드를 위해서 bcrypt 해줘야한다. 안그러면 시크릿 코드(암호화) 없이 그냥 패스워드로 들어가게된다
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User 회원프로필(int userId) { // "/user/{id}" 의 id를 가져옴 ex. 1, 2, 3, ...  (유저 인덱스)
		// SELECT * FROM image WHERE userId = :userId; 
		User userEntity = userRepository.findById(userId).orElseThrow(()-> {return new CustomException("해당 프로필 페이지는 없는 페이지입니다."); });
		
		return userEntity;
	}
	
	// 회원 수정이 일어나는거니까 Transactional 붙여줌
	@Transactional
	public User 회원수정(int id, User user) {
		// 서비스에서 해야하는 것들 !
		// 첫번째 - 영속화
		// findById : Optional<T> 객체임 => Optinal 이 지원해주는 것
		// 1. 무조건 찾았다. 걱정마 get()
		// 2. 못찾았어 Exception 발동시킬게 orElseThrow()
		
		/*
		1. 일반 식
		User userEntity = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("찾을 수 없는 id 입니다");
			}
		});
		*/
		
		
		// findById의 id 를 못찾았다면 Supplier 의 Exception 이 실행 됨
		// 반환 값 : 찾을 수 없는 id입니다
		// ControllExceptionHandler 로 낚아채서 하는 것이 직접 하는 것 보다 좋음. 
		// 그래서 IllegalArgumentException 로 해도 될 것을 CustomValidationException 로 return 함
		// ControllerExceptionHandlerd 에서 낚아 챌 때 CMRespDTO 에서 ErrorMap 이 없고 Message만 있으니 map은 null로 넘어감 하지만 괜찮음 
		//2. 람다 식
		User userEntity = userRepository.findById(id).orElseThrow(()->{ return new CustomValidationApiException("찾을 수 없는 id 입니다"); });
		
		// 두번째 - 영속화된 오브젝트를 수정 - 더티체킹이 일어남(업데이트가 완료 됨)
		// 영속화된 것 변경 시작
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
