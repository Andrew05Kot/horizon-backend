package com.kot.horizon.api.v1.image;

import org.springframework.stereotype.Component;
import com.kot.horizon.image.model.ImageEntity;

@Component
public class ImageMapper {

	public ImageResponse toDto(ImageEntity entity) {
		ImageResponse response = new ImageResponse();
		response.setId(entity.getId());
		response.setImageName(entity.getImageName());
		response.setMimeType(entity.getMimeType());
		return response;
	}
}
