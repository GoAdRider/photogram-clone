# 인스타그램 클론 코딩 Ver.0.5.7

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트, Subscribe


## Profile

### 수정된 부분

> #### 강의 48 ~ 51강까지 완료한 부분입니다.<br/>
> User.class 에 @Data -> @Getter, @Setter 변경. @ToString 제외(무한참조나서 잠시 제거)
> User.class 에 List<Image> images 부분 @JsonIgnoreProperties({"user"}) 추가
> application.yml 에 open-in-view : true 세팅
> UserProfileDto.class 추가
> UserService.class 일부 
> profile.jsp 일부 수정

<br/><br/>

> #### 46강에서 발생한 error 해결 (브랜치 버전으로는 ver.0.5.5 부터 발생했던 error) <br/>
> ![image](https://user-images.githubusercontent.com/57707484/132374594-fc65ac8d-358d-4671-88e5-c084276b9eff.png) <br/>

<br/><br/>

> #### 위 에러 메시지는 `User.java` 의 `images` 부분 `fetch=FetchType.LAZY` 를 적용하고부터 발생했습니다. (ver.0.5.5 부터)
> ```java
>@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
>private List<Image> images;
> ```

<br/><br/>

> #### 위 에러는 로그인 페이지에서 로그인 실행시 발생했습니다.<br/>
> #### 위 에러는 영속성 컨텍스트의 세션에서 무한참조로 인한 에러였습니다. <br/>
> #### Controller --(세션생성)-> Service -> Repository -> DB -> Repository -> Service --(세션종료)-> Controller <br/>


<br/><br/>

## 에러 화면

![image](https://user-images.githubusercontent.com/57707484/132376055-8cdb32aa-ab95-4d47-bf5c-1ed150af36d3.png)

<br/>

> 위 로그인 화면은 잘 나오나 로그인 실행 시 아래와 같은 오류 화면으로 넘어가졌다.

<br/>

![image](https://user-images.githubusercontent.com/57707484/132376144-6939926c-9968-4a79-be41-d1c141148c86.png)
  
<br/><br/>
  
## 해결
  
> @Data 로 처리한 부분을 @Getter, @Setter 만 등록해놓았습니다. @ToString 부분에 대한 문제였습니다. (User <-> Image 무한참조)
```java
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User
```
  
<br/><br/>
  
## open-in-view 와 FetchType

![image](https://user-images.githubusercontent.com/57707484/141121622-9eaa0930-3c1b-4a12-b2e6-b07f531b0810.png)
  
  
<br/><br/>  

## JsonIgnoreProperties

> 회원정보 변경시 뜨는 Exception

![image](https://user-images.githubusercontent.com/57707484/141123010-ff69c017-5d4a-4186-8e9e-5a5fa7ae6412.png)

  
문제는 UserApiController.class 에서 json 응답 할때 user <-> images 무한참조 때문이다.
  
  
User.class
```java
...
@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
@JsonIgnoreProperties({"user"})
private List<Image> images;
...
```
  
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
	@ManyToOne(fetch=FetchType.EAGER)
	private User user;                        // <- 이부분

	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
```
  
>@JsonIgnoreProperties 란?
> @JsonIgnoreProperties({"user"})라면, user json 응답 할 때 Image.class 에서 user 부분만 제외해서 응답해준다.
> 물론 image json 응답할 때는 user 가 그대로 쓰여진다. user json 응답할 때만 제외
  
<br/><br/>
  
## UserProfileDto.class
  
```java
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	//페이지의 주인인지?
	private boolean pageOwnerState;
	private int imageCount;
	private User user;
}
```
  
<br/> 
  
## UserService.class

```java
	@Transactional(readOnly=true)
	public UserProfileDto 회원프로필(int pageUserId, int principalId) {
		UserProfileDto dto = new UserProfileDto();
		
		// SELECT * FROM image WHERE userId = :userId; 
		User userEntity = userRepository.findById(pageUserId).orElseThrow(()-> {return new CustomException("해당 프로필 페이지는 없는 페이지입니다."); });
		
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId);	// true 는 페이지 주인, false 는 주인이 아님
		dto.setImageCount(userEntity.getImages().size());
		return dto;
	}
```

<br/>

## profile.jsp

> el 표현식 dto 

```html
...

		<!--유저정보 및 사진등록 구독하기-->
		<div class="profile-right">
			<div class="name-group">
				<h2>${dto.user.name}</h2>

				<c:choose>
					<c:when test="${dto.pageOwnerState}">
						<button class="cta" onclick="location.href='/image/upload'">사진등록</button>
					</c:when>
				</c:choose>
				<c:otherwise>
					<button class="cta" onclick="toggleSubscribe(this)">구독하기</button>
				</c:otherwise>
				<button class="modi" onclick="popup('.modal-info')">
					<i class="fas fa-cog"></i>
				</button>
			</div>

			<div class="subscribe">
				<ul>
					<li><a href=""> 게시물<span>${dto.imageCount}</span>
					</a></li>
					<li><a href="javascript:subscribeInfoModalOpen();"> 구독정보<span>2</span>
					</a></li>
				</ul>
			</div>
			<div class="state">
				<h4>${dto.user.bio}</h4>
				<h4>${dto.user.website}</h4>
			</div>
		</div>
		<!--유저정보 및 사진등록 구독하기-->

...
```


<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
