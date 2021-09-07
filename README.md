# 인스타그램 클론 코딩 Ver.0.5.5

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트, Subscribe


## Profile

### 수정된 부분

> #### User (양방향 매핑 추가) Image

> 1. 존재하지 않는 프로필 페이지에 대한 익셉션 추가 <br/>
> 1-1. CustomException.java 추가<br/>
> 1-2. ControllerExceptionHandler.java 에 CustomException 을 위한 메소드 추가<br/>
> 1-3. UserService.java 에 회원프로필() method 추가<br/>
> 1-4. UserController.java 에 userEntity 를 add<br/>
> 
> 2. User, Image 사이의 **양방향 매핑** 을 위해 User 의 images 에 `@OneToMany` 어노테이션 등록<br/>
> 2-1. 필요 객체 확인<br/>
> 2-2. 영속성 컨텍스트 기본 작동 원리와 객체간 관계<br/>
> 2-3. User.java 코드에 `image` 를 `List<>` 타입으로 `@OnetoMany` 관계 설정<br/>
> 2-4. List<>() 를 그대로 영속성 컨텍스트에 등록하려 한다면 ??


---

<br/>

### 1. 존재하지 않는 프로필 페이지에 대한 익셉션 추가

> 존재하지 않는 userId 로 접근시 익셉션 가동<br/>
> **/user/1** 은 존재하기에 해당 유저의 프로필 페이지를 보여주겠지만<br/>
> **/user/2** 는 존재하지 않기에 아래와 같은 alert 창을 보게된다.

![image](https://user-images.githubusercontent.com/57707484/132310316-1edc7d6a-69e8-4066-ac15-5cb6a22c108a.png)

#### 1-1. CustomException.java 추가

[CustomException.java 접근](./src/main/java/com/cos/photogramstart/handler/ex/CustomException.java)

```java
public class CustomException extends RuntimeException{

	//객체를 구분할 때 !! = 우리한테 중요한건 아님 JVM 에게 중요함
	private static final long serialVersionUID = 1L;

	//찾는 프로필 페이지가 없을때 보내는 익셉션 ( UserService.java 의 회원프로필() )
	public CustomException(String message) {
		super(message);
		System.out.println("CustomException 호출");
	}
}
```
<br/>

#### 1-2. ControllerExceptionHandler.java 에 CustomException 을 위한 메소드 추가

[ControllerExceptionHandler.java 접근](./src/main/java/com/cos/photogramstart/handler/ControllerExceptionHandler.java)

```java
	@ExceptionHandler(CustomException.class)
	public String exception(CustomException e) {
		System.out.println("ControllerExceptionHandler 호출");
		return Script.back(e.getMessage());
	}
```
<br/>

#### 1-3. UserService.java 에 회원프로필() method 추가

[UserService.java 접근](./src/main/java/com/cos/photogramstart/service/UserService.java)

> 해당 id에 대한 익셉션을 던진다.
> 존재 O : userEntity 로 반환
> 존재 X : CustomException 으로 오류 메시지 던짐

```java
	public User 회원프로필(int userId) { // "/user/{id}" 의 id를 가져옴 ex. 1, 2, 3, ...  (유저 인덱스)
		// SELECT * FROM image WHERE userId = :userId; 
		User userEntity = userRepository.findById(userId).orElseThrow(()-> {throw new CustomException("해당 프로필 페이지는 없는 페이지입니다."); });
		return userEntity;
	}
```
<br/>

#### 1-4. UserController.java 에 userEntity 를 add

[UserController.java 접근](./src/main/java/com/cos/photogramstart/web/UserController.java)

> 사용자가 등록할 Image 정보도 운반해주어야 하기에 Model 이 필요하다
> Model 에는 Image 뿐만이 아닌 유저의 모든 정보를 넘겨주어야 하기에 userEntity 를 등록한다

```java
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id, Model model) {
		User userEntity = userService.회원프로필(id);
		model.addAttribute("user",userEntity);
		return "user/profile";
	}
```
<br/><br/>

### 2. User, Image 사이의 양방향 매핑을 위해 User 의 images 에 @OneToMany 어노테이션 등록

<br/>

#### 2-1. 필요 객체 확인

![image](https://user-images.githubusercontent.com/57707484/132334039-cda2da05-70ad-4bc4-a80e-b7f3a61c3b9f.png)

<br/>

> 위와 같이 한 페이지에 여러가지 데이터가 필요하다.<br/>
> 객체로 봤을 때 `User`, `Subscribe`, `Image`<br/>

<br/><br/>

#### 2-2. 영속성 컨텍스트 기본 작동 원리와 객체간 관계

![image](https://user-images.githubusercontent.com/57707484/132332567-582530e6-6184-4ff7-9f6b-36394d6aec50.png)

<br/>

|객체간 관계|User|Subscribe|Image|
|:-:|:-:|:-:|:-:|
|User|OneToOne|OneToMany|**OneToMany**|
|Subscribe|ManyToOne|ManyToMany|X|
|Image|ManyToOne|X|ManyToMany|

<br/>

### <div align="center"> 이번 작업에서 필요한 것은 User --> Image 즉 OnetoMany 이다. </div>




<br/>

#### 2-3. User.java 코드에 `image` 를 `List<>` 타입으로 `@OnetoMany` 관계 설정

[User.java 접근](./src/main/java/com/cos/photogramstart/domain/user/User.java)

> `mappedBy` 란? <br/>
> 양방향 매핑을 할 때 사용하는 attribute<br/>
> **의미 1** : 나는 연관관계의 주인이 아니다. 그러므로 테이블에 칼럼을 만들지 마 !<br/>
> **의미 2** : User 를 Select 할 때 해당 User id 로 등록된 image들을 다 가져와 !<br/>
>
> `fetch` 란?<br/>
> `Lazy (지연로딩)` = User 를 Select 할 때 해당 User id 로 등록 된 image 들을 가져오지 마 ! <br/>
> 　　　　　　　　　　　　　　　- 대신!! `getImages()` 함수의 image 들이 호출 될 때 가져 와 !<br/>
> `Eager (즉시로딩)` = User 를 Select 할 때 해당 User id 로 등록 된 Image 들을 전부 Join 해서 가져 와 !

```java
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<Image> images;
```

<br/>

#### 2-4. List<>() 를 그대로 영속성 컨텍스트에 등록하려 한다면 ??

> 만약 `mappedBy` 를 사용하지 않았다면 다음과 같은 일이 일어난다
>
> DB 에서 String 은 Varchar2 , int 는 Number 타입이다.<br/>
> 그러나 java 의 List 와 같이 DB 에서는 Collection 을 관리해주는 타입은 없다<br/>
>  `mappedBy` 를 사용하면 위에서 말한 **`mappedBy` 의미 1** 의 내용처럼 테이블에 칼럼을 만들지 않기에 error 가 나지 않는다.<br/>
>  그러나 `mappedBy` 를 사용하지 않았을 시 이런 error 메시지가 뜬다.

<br/>

![image](https://user-images.githubusercontent.com/57707484/132331788-0f56e697-9010-4929-995c-4ab82b4d96fe.png)


<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
