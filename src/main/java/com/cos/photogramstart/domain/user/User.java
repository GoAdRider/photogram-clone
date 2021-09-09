package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	// 양방향 매핑을 위해 필요하다 (한쪽은 OneToMany 다른쪽은 ManyToOne)
	// String은 Varchar2 타입 int 는 Number 타입으로 DB 에 생성되지만 List 는 DB 의 타입에 속해있지 않다.
	// 그러므로 mappedBy 를 사용해서 그 의미를 정확히 적어주어야 한다.
	// mappedBy 의미 !
	// 의미 1 : 나는 연관관계의 주인이 아니다. 그러므로 테이블에 칼럼을 만들지 마 !
	// 의미 2 : User 를 Select 할 때 해당 User id 로 등록된 image들을 다 가져와 !
	// fetch의 의미 !
	// Lazy (지연로딩) = User 를 Select 할 때 해당 User id 로 등록 된 image 들을 가져오지 마 ! 
	//						- 대신!! getImages() 함수의 image 들이 호출 될 때 가져 와 !
	// Eager (즉시로딩) = User 를 Select 할 때 해당 User id 로 등록 된 Image 들을 전부 Join 해서 가져 와 !
	@OneToMany(mappedBy="user", fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"user"})
	private List<Image> images;
	
	private LocalDateTime createDate;
	
	@PrePersist	// DB에 Insert 되기 직전에 실행됨
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
