package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	
	private final SubscribeRepository subscribeRepository;
	
	// insert 나 delete 로 db 에 영향을 주는거니까 @Transactional 어노테이션 걸어주기
	// Subscribe.java 에서 fromUserId 와 toUserID 는 User 타입이지 int 타입이 아니다
	// 그래서 인터페이스인 SubscribeRepository.java 에 Native Query 로 직접 int 형으로 받는다
	// User 객체 받아와서 findByid 로 바꿔서 하는 것보다 더 쉬우니까 이런 간단한 것은 NativeQuery 로 하자
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) { 
		subscribeRepository.mSubscribe(fromUserId, toUserId);	//mSubscribe 에서 m은 "내가 만들었다"의 약어
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
