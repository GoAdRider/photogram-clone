package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistence API(자바로 데이터를 영구적으로 저장할 수 있는 API를 제공)


@Builder	// Builder 패턴
@AllArgsConstructor	// 전체 생성자
@NoArgsConstructor	// 빈 생성자
@Data
@Entity // 디비에 테이블을 생성
public class User {
	
	@Id	// primary 키 지정. 없으면 에러남
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 번호 증가 전략 : 데이터베이스를 따라간다.
	private int id;
	
	@Column(length = 20,unique = true)//유일해야함
	private String username;
	@Column(nullable=false)	// Dto 에서는 @Validation 을 사용했음. DB 랑 연결되는 이곳에는 @Column() 으로 하기!
	private String password;
	@Column(nullable=false)
	private String name;
	private String website;	// 웹사이트
	private String bio;		//자기소개
	@Column(nullable=false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; //사진
	private String role;	// 권한
	
	private LocalDateTime createDate;
	
	@PrePersist	// DB에 Insert 되기 직전에 실행됨
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
