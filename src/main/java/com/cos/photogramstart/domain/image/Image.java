package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder			// Builder 패턴
@AllArgsConstructor	// 전체 생성자
@NoArgsConstructor	// 빈 생성자
@Data
@Entity 
public class Image {	// User 와의 관계 : Image --(Many2One) --> User
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)	// 번호 증가 전략 : 데이터베이스를 따라간다.
	private int id;
	
	private String caption; 		// "오늘 나 너무 피곤해 !!"
	private String postImageUrl; 	// 사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장 - DB 에 그 저장된 경로를 Insert !
	
	@JoinColumn(name="userId")
	@ManyToOne			// 오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 User 부분을 출력되지 않게 해야 함(무한 참조 user <-> images)
	private User user; // 객체가 들어왔으니 연관관계 정해주기
	
	// 기능 업데이트 할 사항
		// - 이미지 좋아요
		// - 댓글
	
	private LocalDateTime createDate;
	
	@PrePersist	// DB에 Insert 되기 직전에 실행됨
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
