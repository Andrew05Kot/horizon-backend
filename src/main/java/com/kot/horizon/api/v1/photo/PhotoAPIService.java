package com.kot.horizon.api.v1.photo;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.kot.horizon.model.photo.PhotoEntity;
import com.kot.horizon.service.photo.PhotoService;

@Service
public class PhotoAPIService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhotoAPIService.class);

	private static final double MAX_SIZE = 5 * 1024 * 1024;

	@Autowired
	private PhotoService photoService;

	public Photo create(MultipartFile multipartFile){
		if (validateMultipartFile(multipartFile)){
			PhotoEntity photoEntity = new PhotoEntity();
			photoEntity.setMimeType(multipartFile.getContentType());
			try {
				photoEntity.setContent(multipartFile.getBytes());
			}
			catch (IOException e) {
				LOGGER.error("Failed reading to multipart file", e);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed reading to multipart file");
			}
			return getPhoto(photoService.create(photoEntity));
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed reading to multipart file");
	}

	public ResponseEntity<ByteArrayResource> getById(Long photoId){
		PhotoEntity photoEntity = photoService.getById(photoId);
		if(photoEntity == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo was not found");
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment");
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, photoEntity.getMimeType());
		// FIXME when we will have high load on the server, investigate and then use cache for photo
		//httpHeaders.add(HttpHeaders.CACHE_CONTROL, CacheControl.maxAge(cacheControlTime, TimeUnit.SECONDS).getHeaderValue());
		httpHeaders.add(HttpHeaders.CACHE_CONTROL, CacheControl.noCache().getHeaderValue());
		ByteArrayResource body = new ByteArrayResource(photoEntity.getContent());
		return new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);
	}

	public void delete(Long id) {
		photoService.delete(getPhotoIfExists(id).getId());
	}

	private boolean validateMultipartFile(MultipartFile multipartFile) {
		if ( !("image/jpeg".equals((multipartFile.getContentType())) || "image/png".equals(multipartFile.getContentType())) ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "type is not supported");
		}
		if ( multipartFile.getSize() >= MAX_SIZE ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file too large");
		}
		return true;
	}

	private Photo getPhoto(PhotoEntity photoEntity) {
		Photo photo = new Photo();
		photo.setId(photoEntity.getId());
		photo.setMimeType(photoEntity.getMimeType());
		return photo;
	}

	private PhotoEntity getPhotoIfExists(Long photoId) {
		PhotoEntity photoEntity = photoService.getById(photoId);

		if (photoEntity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo was not found.");
		}
		return photoEntity;
	}
}
