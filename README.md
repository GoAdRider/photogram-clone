# 인스타그램 클론 코딩 Ver.0.5.2

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트, Subscribe


## Profile

### 수정된 부분

> #### Image DB에 업로드

>1. ImageService.java 에서 받은 데이터를 Image 테이블에 저장
>2. ImageUploadDto.java 에 toEntity 에 필요 파라미터, DB에 저장할 부분(캡션,이미지가 저장되는 주소, 업로드하는 사람) 추가

---

<br/>

### 1 ImageService.java 에서 받은 데이터를 Image 테이블에 저장

[ImageService.java 접근](./src/main/java/com/cos/photogramstart/service/ImageService.java)

```java
// image 테이블에 저장
Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());
Image imageEntity = imageRepository.save(image);
```

<br/>

#### `.save` 를 호출 시?


* entity 가 새로 생성할 예정이라면 `persist()` 를 호출하고, 그렇지 않다면 `merge()` 를 호출합니다.

```java
SimpleJpaRepository.java

/*
 * (non-Javadoc)
 * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
 */
@Transactional
@Override
public <S extends T> S save(S entity) {

	if (entityInformation.isNew(entity)) {
		em.persist(entity);
		return entity;
	} else {
		return em.merge(entity);
	}
}
```

<details>
  <summary> Detail 하게 알아보려면? </summary>
	
	[spring-data-jpa save 동작 원리-Simple is best. 백엔드개발자](https://velog.io/@rainmaker007/spring-data-jpa-save-%EB%8F%99%EC%9E%91-%EC%9B%90%EB%A6%AC)
  
</details>

<br/><br/>

### 2 ImageUploadDto.java 에 toEntity 에 필요 파라미터, DB에 저장할 부분(캡션,이미지가 저장되는 주소, 업로드하는 사람) 추가

[ImageUploadDto.java 접근](./src/main/java/com/cos/photogramstart/web/dto/image/ImageUploadDto.java)


```java
@Data
public class ImageUploadDto {
	private MultipartFile file;
	private String caption;
	
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl)
				.user(user)
				.build();
	}
}
```

<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
