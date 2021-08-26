# 인스타그램 클론 코딩 Ver.0.4.2

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트


## Subscribe

### 수정된 부분

> 구독, 구독취소 API 만들기
1. 인터페이스 SubscribeRepository.java 에 NativeQuery 문 추가
2. SubscribeService.java 생성 후 구독, 구독취소 메소드
3. SubscribeApiController.java 생성

---

### 1 인터페이스 SubscribeRepository.java 에 NativeQuery 문 추가

[SubscribeRepository.java 접근](./src/main/java/com/cos/photogramstart/domain/subscribe/SubscribeRepository.java)

> DB 가 변경되는 값을 넣어줄때는 항상 @Modifying 어노테이션을 넣어줘야 한다
> 즉 , INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성하려면 @Modifying 어노테이션이 필요!

```java
	@Modifying
	@Query(value="INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())",nativeQuery=true)
	void mSubscribe(int fromUserId, int toUserId);

	@Modifying
	@Query(value="DELETE FROM subscribe WHERE fromUserId=:fromUserId AND toUserId = :toUserId",nativeQuery=true)
	void mUnSubscribe(int fromUserId, int toUserId);
```

<br/><br/>

### 2 SubscribeService.java 생성 후 구독, 구독취소 메소드

[SubscribeService.java 접근](./src/main/java/com/cos/photogramstart/service/SubscribeService.java)

```java
@RequiredArgsConstructor
@Service
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) { 
		subscribeRepository.mSubscribe(fromUserId, toUserId);	//mSubscribe 에서 m은 "내가 만들었다"의 약어
	}

	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
```

<br/><br/>

### 3 SubscribeApiController.java 생성

[SubscribeApiController.java 접근](./src/main/java/com/cos/photogramstart/web/api/SubscribeApiController.java)

> 나중에 내부적으로 문제 생길 시 try..catch throw 를 통해 익셉션 던질 예정

```java
@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

	private final SubscribeService subscribeSerice;

	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){
		subscribeSerice.구독하기(principalDetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1,"구독하기 성공",null),HttpStatus.OK);// 무조건 성공(1)
	}

	@DeleteMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> unsubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){
		subscribeSerice.구독취소하기(principalDetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1,"구독취소하기 성공",null),HttpStatus.OK);
	}
}
```

<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
