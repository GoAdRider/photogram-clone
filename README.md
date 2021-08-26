# 인스타그램 클론 코딩 Ver.0.4.0

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트


## Subscribe

### 수정된 부분

> Subscribe 모델 만들기
1. Subscribe.java 추가
2. SubscribeRepository.java 추가

---

### 1 Subscribe.java 추가

[Subscribe.java 접근](./src/main/java/com/cos/photogramstart/domain/subscribe/Subscribe.java)

> #### 데이터가 중복해서 들어오지 못하게 설정
> 
> <img src="https://user-images.githubusercontent.com/57707484/130954996-d060e0ac-7cb2-4e85-a8b8-2853f63dfb1b.png" width="30%" height="30%"/>
> 
> 3번의 데이터값과 1번의 데이터값이 중복된다. <br/>
> 이렇게 들어오면 안되니 중복 데이터를 들어오지 못하게 설정해줘야한다


```java
@Table(uniqueConstraints = {
		@UniqueConstraint(
				name="subscribe_uk",
				columnNames= {"fromUserId","toUserId"}//실제 DB 의 컬럼명을 기재해줘야함
		)
})
public class Subscribe
...
```
<br/>

> 실제 DB 에 들어가는 컬럼명 지정 @JoinColumn
> 관계설정 @ManyToOne
> 
> @ManytoOne 으로 Subscribe 테이블 생성과 동시에 FK 지정 ( User 테이블과 관계설정 )
<img width="461" alt="1" src="https://user-images.githubusercontent.com/57707484/130954429-31901092-46dc-4e72-9dd5-ff176ffb1e45.PNG">

<br/>

```java
	@JoinColumn(name="fromUserId")
	@ManyToOne
	private User fromUser;

	@JoinColumn(name="toUserId")
	@ManyToOne
	private User toUser;
```

<br/><br/>


### 2 SubscribeRepository.java 추가

[SubscribeRepository.java 접근](./src/main/java/com/cos/photogramstart/domain/subscribe/SubscribeRepository.java)

<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
