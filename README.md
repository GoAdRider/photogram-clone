# 인스타그램 클론 코딩 Ver.0.5.1

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트, Subscribe


## Profile

### 수정된 부분

> Image 모델 만들기
1. Image.java
2. ImageRepository.java 등록

---

### 1 Image.java

[Image.java 접근](./src/main/java/com/cos/photogramstart/domain/image/Image.java)

> User 와의 관계 : Image --(Many2One) --> User
> 
> 한명의 User 는 여러개의 Image 를 가질 수 있다.
> 
> 하지만 두명 이상의 유저는 한 Image 를 가질 수 없다.

<br/>

> 변수 `caption` 설명 : 사진과 함께 사용자가 올리는 사진 설명 ex) "오늘 나 너무 피곤해 !!"
> 
> 변서 `postImageUrl` 설명 : 사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장 - DB 에 그 저장된 경로를 Insert !

```java
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity 
public class Image {
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String caption; 
	private String postImageUrl; 

	@JoinColumn(name="userId")
	@ManyToOne
	private User user;

	private LocalDateTime createDate;

	@PrePersist	// DB에 Insert 되기 직전에 실행됨
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
```

<br/><br/>

### 2 ImageRepository.java 등록

[ImageRepository.java 접근](./src/main/java/com/cos/photogramstart/domain/image/ImageRepository.java)

```java
public interface ImageRepository extends JpaRepository<Image, Integer> {

}
```

<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
