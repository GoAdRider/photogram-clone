package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	//패스워드를 위해서 bcrypt 해줘야한다. 안그러면 시크릿 코드(암호화) 없이 그냥 패스워드로 들어가게된다
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 회원 수정이 일어나는거니까 Transactional 붙여줌
	@Transactional
	public User 회원수정(int id, User user) {
		// 서비스에서 해야하는 것들 !
		// 첫번째 - 영속화
		// findById : Optional<T> 객체임 => Optinal 이 지원해주는 것
		// 1. 무조건 찾았다. 걱정마 get()
		// 2. 못찾았어 Exception 발동시킬게 orElseThrow()
		User userEntity = userRepository.findById(id).get(); // 나중에 orElseThrow() 로 바꿀 예정

		
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
