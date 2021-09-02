package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Value("${file.path}")//@Value 로 application.yml 에 있는 file.path 의 값을 가져올 수 있다.
	private String uploadFolder;
	
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		//파일 명 중복을 막음.. DB 에 똑같은 파일명이 저장 될 수도 있다. 그러면 덮어쓰기가 되므로 중복되지 않을 수 있게 지정해줘야한다.
		UUID uuid = UUID.randomUUID();//네트워크 상에서 고유성이 보장되는 id 를 만들기 위한 표준 규약 Universally Unique Identifier
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();	// 1.jpg
		System.out.println("이미지 파일 이름 : "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		// 통신, I/O -> 예외가 발생할 수 있음
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());
		Image imageEntity = imageRepository.save(image);
		
		System.out.println("imageEntity : "+imageEntity);
		
	}
}
