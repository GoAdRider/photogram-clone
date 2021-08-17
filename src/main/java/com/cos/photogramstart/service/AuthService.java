package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service	// 1.IoC 에 등록 2.트랜잭션관리
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//이 함수가 실행되고 함수가 종료될때 까지 트랜잭션 관리를 해줌
	//Write( Insert, Update, Delete) 를 위한 함수
	@Transactional	
	public User 회원가입(User user) {
		//회원가입 진행
		String rawPassword = user.getPassword();
		//rawPassword 를 -> 암호화된 패스워드
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");// 관리자 ROLE_ADMIN
		User userEntity =userRepository.save(user);
		return userEntity;
	}
}
