package com.cos.photogramstart.domain.subscribe;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder			// Builder 패턴
@AllArgsConstructor	// 전체 생성자
@NoArgsConstructor	// 빈 생성자
@Data
@Entity 			// 디비에 테이블을 생성
@Table(uniqueConstraints = {
		//데이터가 중복해서 들어오지 못하게 설정
		//ex)
		// id/ fromUserId / toUserId
		// 1         1         2
		// 2         1         3
		// 3         1         2          <-- 데이터가 중복됨. 이렇게 되지 말라고 설정해줘야 함
		@UniqueConstraint(
				name="subscribe_uk",
				columnNames= {"fromUserId","toUserId"}//실제 DB 의 컬럼명을 기재해줘야함
		)
})
//중간테이블
//User 와의 관계 :  User --(One2Many)--> Subscribe
public class Subscribe {
	@Id	// primary 키 지정. 없으면 에러남
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 번호 증가 전략 : 데이터베이스를 따라간다.
	private int id;
	
	
	//기본적으로 fromUser_id 라는 칼럼명으로 DB에 만들어짐 하지만 JoinColumn(name= 으로 칼럼명 커스텀가능
	//바꿀때 application.yml 에 jpa: ddl-auto: 부분이 update 로 되어있으면 칼럼이 추가가 됨. 
	// ex) 칼럼=> id / fromUser_id/ fromUserId
	//그래서 스키마를 만들때 항상 ddl-auto 부분은 create 나 none 으로 바꿔주고 진행해야 함
	@JoinColumn(name="fromUserId")
	@ManyToOne	// 자동으로 테이블 생성해주면서 칼럼 관계설정해줌 (FK 지정)
	private User fromUser;
	
	@JoinColumn(name="toUserId")
	@ManyToOne
	private User toUser;
	
	private LocalDateTime createDate;
	
	@PrePersist	// DB에 Insert 되기 직전에 실행됨
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
