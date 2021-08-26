package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;



//JpaRepository를 상속하면
//어노테이션이 없어도 IoC 등록이 자동으로 된다.
public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
	//JPA query method
}
