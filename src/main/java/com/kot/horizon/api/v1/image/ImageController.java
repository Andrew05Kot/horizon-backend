package com.kot.horizon.api.v1.image;

import java.io.FileNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.kot.horizon.api.v1.ServiceAPIUrl;
import com.kot.horizon.common.config.SwaggerInfo;
import com.kot.horizon.common.filtering.specifications.EqualSpecification;
import com.kot.horizon.image.model.ImageEntity;
import com.kot.horizon.image.service.ImageService;

@Controller
@RequestMapping(ImageController.BASE_URL)
@Api(tags = SwaggerInfo.IMAGE_API)
public class ImageController {

	@Autowired
	private ImageService imageService;

	public static final String BASE_URL = ServiceAPIUrl.V1_PATH + "/image";

	@ApiOperation("Get image by name")
	@GetMapping(value = "/{imageName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> get(@ApiParam(value = "The name of image", example = "avatar.png") @PathVariable String imageName) throws FileNotFoundException {
		ImageEntity image = imageService.findOne(new EqualSpecification<ImageEntity>("imageName", imageName));
		return new ResponseEntity<>( imageService.getImageByName(imageName), buildImageHeaders(image), HttpStatus.OK);
	}

	@ApiOperation("Delete image by id")
	@DeleteMapping(value = "/{imageId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@ApiParam(value = "The id of image", example = "2") @PathVariable Long imageId) {
		ImageEntity image = imageService.findById(imageId);
		imageService.deleteImage(image);
	}

	private HttpHeaders buildImageHeaders(ImageEntity image) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment");
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, image.getMimeType());
		httpHeaders.add(HttpHeaders.CACHE_CONTROL, CacheControl.noCache().getHeaderValue());
		httpHeaders.setContentDispositionFormData("attachment", image.getImageName());
		return httpHeaders;
	}

}
