package com.kot.horizon.api.v1.photo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kot.horizon.api.ServiceAPIUrl;
import com.kot.horizon.config.SwaggerInfo;

@Api(tags = {SwaggerInfo.PHOTO_API})
@RestController
@RequestMapping(value = PhotoController.BASE_URL)
public class PhotoController {

	public static final String BASE_URL = ServiceAPIUrl.V1_PATH + "/photos";

	@Autowired
	private PhotoAPIService photoAPIService;

	@ApiOperation( "Save photos" )
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public Photo create(@ApiParam(value = "Save photo only jpeg or png", example = "image/jpeg") @RequestParam MultipartFile image) {
		return photoAPIService.create(image);
	}

	@ApiOperation( "get photo by id" )
	@GetMapping("/{id}")
	public ResponseEntity<ByteArrayResource> get(@ApiParam(value = "The identification of photo", example = "1") @PathVariable Long id){
		return photoAPIService.getById(id);
	}

	@ApiOperation( "Delete photo by id" )
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		photoAPIService.delete(id);
	}

}
