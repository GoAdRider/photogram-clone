# 인스타그램 클론 코딩 Ver.0.4.0

## 구현완료

>
#### 회원가입, 로그인, 사용자 업데이트


## Subscribe

<img width="596" alt="11" src="https://user-images.githubusercontent.com/57707484/130948186-c48bbe29-1a04-4ab3-bd71-d1802b4b3eea.PNG">

### USERtoUSER 의 연관관계

USER <-(Many2Many)-> USER

ManytoMany 는 업무가 정의가 덜 된 상태이다 그래서 OneToMany 로 풀어줘야한다

Subscribe 라는 중간테이블을 넣어주어 풀어준다면 가능하다

User --(OnetoMany)--> Subscribe  <--(ManytoOne)-- User

### REFERENCE

>
[getinthere github](https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start)
