package com.kot.horizon.api.v1.image;

import com.kot.horizon.image.model.ImageEntity;

public class ImageUtils {

	public static String buildImageUrl(ImageEntity image) {
		if (image != null) {
			return ImageController.BASE_URL + "/" + image.getImageName();
		}
		return "";
	}
}
