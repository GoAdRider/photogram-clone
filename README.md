# 인스타그램 클론 코딩 Ver.0.5.2

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트, Subscribe


## Profile

### 수정된 부분

> #### Image 서버에 업로드

> 백엔드
> 
>1. ImageController.java 에 사진 업로드 메소드 추가
>2. ImageUploadDto.java 추가
>3. ImageService.java 에 UUID 생성 후 서버에 파일 생성

> 프론트엔드
> 
> 4. upload.jsp 에 action, method, enctype 설정

---

### 1 ImageController.java 에 사진 업로드 메소드 추가

[ImageController.java 접근](./src/main/java/com/cos/photogramstart/web/ImageController.java)

> 사진 업로드 완료시 본인의 userprofile 페이지로 넘어가게 설정

```java
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		imageService.사진업로드(imageUploadDto, principalDetails);
		return "redirect:/user/"+principalDetails.getUser().getId();
	}
```

<br/><br/>

### 2 ImageUploadDto.java 추가

[ImageUploadDto.java 접근](./src/main/java/com/cos/photogramstart/web/dto/image/ImageUploadDto.java)

```java
@Data
public class ImageUploadDto {
	private MultipartFile file;
	private String caption;
}
```

<br/><br/>

### 3 ImageService.java 에 UUID 생성 후 서버에 파일 생성

[ImageService.java 접근](./src/main/java/com/cos/photogramstart/service/ImageService.java)

> `UUID` : 파일 명 중복을 막기 위해 사용한다
> `UUID 정의` : 네트워크 상에서 고유성이 보장되는 id 를 만들기 위한 표준 규약
> 
> ![image](https://user-images.githubusercontent.com/57707484/131171818-93339b54-851c-4641-840c-fd22960c0898.png)
> 
> (실제 UUID + 기존파일명을 입력했을 때 나온 파일 명)

<br/>

> 변수 `imageFileName` = "/파일경로/"+"파일명.확장자";
> 
> 통신이나 I/O 는 컴파일해석기로 익셉션을 잡지 못한다. 런타임시 익셉션이 뜨기때문에 예외처리가 필수다

```java
@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Value("${file.path}")
	private String uploadFolder;
	
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();
		System.out.println("이미지 파일 이름 : "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		// 통신, I/O -> 예외가 발생할 수 있음
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
```

<br/>

> 위 변수 `uploadFolder` 위의 **@Value("${file.path}")** 는 `application.yml` 의 **file.path** 의 **value** 를 가져온다.
> 
> `application.yml` 에서 **servlet, jpa, spring** 같은 예약어 뿐만 아니라 **file** 같이 내가 지정해서 사용할 수도 있다. 

```yml
file:
  path: C:/workspace_springboot/upload/
```

<br/><br/>

### 4 upload.jsp 에 action, method, enctype 설정

[upload.jsp 접근](./src/main/webapp/WEB-INF/views/image/upload.jsp)

> **두번째 input** 인 **사진설명** 쪽만 보면 `enctype` 이 `application/x-www-form-urlencoded` **(key:value)** 가 맞다
>
> 하지만 **첫번째 input** 인 file 을 받는 타입 때문에 `enctype` 을 `application/x-www-form-urlencoded` 로 설정 할 수 없다.
>
> 그래서 **여러 종류의 타입을 묶어서 전송할 때 사용하는 인코딩 타입** 인 `multipart/form-data` 을 사용해야 한다.

```html
<!--사진업로드 Form-->
<form class="upload-form" action="/image" method="post" enctype="multipart/form-data">
	<input  type="file" name="file"  onchange="imageChoose(this)"/>
	<div class="upload-img">
		<img src="/images/person.jpeg" alt="" id="imageUploadPreview" />
	</div>
                    
	<!--사진설명 + 업로드버튼-->
	<div class="upload-form-detail">
		<input type="text" placeholder="사진설명" name="caption"/> 
		<button class="cta blue">업로드</button>
	</div>
	<!--사진설명end-->
</form>
```

<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
