# 인스타그램 클론 코딩 Ver.0.5.7

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트, Subscribe


## Profile

### 수정된 부분

> #### 강의 48 ~ 51강까지 완료한 부분입니다.<br/>
> #### 다시 보고 정리할 페이지 입니다.

<br/><br/>

> #### 아직 46강에서 발생한 error 를 잡지 못한 상황입니다. (브랜치 버전으로는 ver.0.5.5 부터 발생) <br/>
> ![image](https://user-images.githubusercontent.com/57707484/132374594-fc65ac8d-358d-4671-88e5-c084276b9eff.png) <br/>
> #### 위 에러까지 잡고 이 페이지를 정리할 예정입니다.

<br/><br/>

> #### 위 에러 메시지는 `User.java` 의 `images` 부분 `fetch=FetchType.LAZY` 를 적용하고부터 발생했습니다. (ver.0.5.5 부터)
> ```java
>@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
>private List<Image> images;
> ```

<br/><br/>

> #### 위 에러는 로그인 페이지에서 로그인 실행시 발생합니다.<br/>
> #### 위 에러는 영속성 컨텍스트의 세션에 관한 에러로 추정됩니다. <br/>
> #### Controller --(세션생성)-> Service -> Repository -> DB -> Repository -> Service --(세션종료)-> Controller <br/>
> #### 아마 세션이 종료된 상황에서 세션을 호출해서 에러가 발생한 것일겁니다.

<br/><br/>

> #### UserService, UserController 를 보면 될 것이라 생각했지만 아마 조금 더 근본적인 곳에서 발생하는 것 같습니다.<br/>
> #### 그래서 로그인 시도시 발동하는 PrincipalDetailsService 와 PrincipalDetails 를 확인 해보았으나 아직까진 잘 모르겠습니다.

<br/><br/>

## 실제 화면

![image](https://user-images.githubusercontent.com/57707484/132376055-8cdb32aa-ab95-4d47-bf5c-1ed150af36d3.png)

<br/>

> 위 로그인 화면은 잘 나오나 로그인 실행 시 아래와 같은 오류 화면으로 넘어가진다

<br/>

![image](https://user-images.githubusercontent.com/57707484/132376144-6939926c-9968-4a79-be41-d1c141148c86.png)


<br/><br/>

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
