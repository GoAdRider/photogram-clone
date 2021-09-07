# 인스타그램 클론 코딩 Ver.0.5.6

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트, Subscribe


## Profile

### 수정된 부분

> #### Image view 렌더링

> 1. [백엔드] WebMvcConfig.java 생성 (주소패턴 변경하는 config)
> 2. [프론트] profile.jsp 에 EL 표현식으로 유저 정보 입력


---

<br/>

### 1. [백엔드] WebMvcConfig.java 생성 (주소패턴 변경하는 config)

[WebMvcConfig.java 접근](./src/main/java/com/cos/photogramstart/config/WebMvcConfig.java)

> `.addResourceHandler("/upload/**")` : jsp 페이지에서 /upload/** 이런 주소 패턴이 나오면 발동 !<br/>
> `.addResourceLocations("file:///"+uploadFolder)` : 위 `addResourceHandler` 의 파라미터와 같은 내용(주소)을 왼쪽의 파라미터 내용(주소)으로 바꿔줌<br/>
> `.setCachePeriod(60*10*6)` : 캐시 기간, 초 단위임, 즉 왼쪽의 숫자는 1시간을 나타냄

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	//application.yml 에 있는 file의 path
	@Value("${file.path}")
	private String uploadFolder;

	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);

			registry
				.addResourceHandler("/upload/**")
				.addResourceLocations("file:///"+uploadFolder)
				.setCachePeriod(60*10*6)
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
		}
}
```

<br/><br/>

### 2. [프론트] profile.jsp 에 EL 표현식으로 유저 정보 입력

[profile.jsp 접근](./src/main/webapp/WEB-INF/views/user/profile.jsp)

> 작성한 내용은 더 있지만 image 불러오는 부분만 적었다.

```html

...

<!--아이템들-->
<c:forEach var="image" items="${user.images}"><!-- EL 표현식에서 변수명을 적으면 get함수가 자동 호출 된다. -->
	<div class="img-box">
		<a href=""> <img src="${image.postImageUrl}" /></a>
		<div class="comment">
			<a href="#" class=""> <i class="fas fa-heart"></i><span>0</span></a>
		</div>
	</div>
</c:forEach>
```

<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
