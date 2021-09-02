package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {
	// 캡션은 들어오던 말던 상관 없고 파일은 무조건 들어와야 한다. ( Validation Check(유효성검사) )
	// 그래서 @NotBlank 를 걸으려 했더니, 멀티파일 타입에는 이 어노테이션 지원이 안 된다고 한다.
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
