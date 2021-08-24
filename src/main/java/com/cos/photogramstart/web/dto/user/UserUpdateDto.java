package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {

	private String name;		//필수
	private String password;	//필수
	// 밑에서부터 옵션
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	//조금 위험함. 코드 수정이 필요할 예정
	public User toEntity() {
		return User.builder()
				.username(name)// 이름을 기재 안했으면 문제 !!! Validation 체크를 해줘야 함
				.password(password)// 패스워드를 기재 안했으면 문제 !!! Validation 체크를 해줘야 함
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}