package com.kot.horizon.api.v1.tour.controller;

import java.util.List;
import java.util.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kot.horizon.api.v1.ServiceAPIUrl;
import com.kot.horizon.api.v1.general.AbstractController;
import com.kot.horizon.api.v1.tour.dto.TourRequest;
import com.kot.horizon.api.v1.tour.dto.TourResponse;
import com.kot.horizon.api.v1.tour.service.TourAPIService;
import com.kot.horizon.common.config.SwaggerInfo;
import com.kot.horizon.image.exception.UnsupportedImageTypeException;
import com.kot.horizon.image.exception.WrongImageSizeException;
import com.kot.horizon.tour.model.TourEntity;
import com.kot.horizon.tour.service.TourService;

@Api(tags = SwaggerInfo.TOUR_API)
@RestController
@RequestMapping(value = TourController.BASE_URL)
public class TourController extends AbstractController<TourEntity, TourRequest, TourResponse, TourService, TourAPIService> {

	public static final String BASE_URL = ServiceAPIUrl.V1_PATH + "/tour";

	@PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create images")
	@ResponseBody
	public TourResponse uploadImages(@RequestParam("files") Optional<MultipartFile[]> files,
									 @ApiParam(name = "tourId", value = "Identify number of tour", example = "12")
									 @PathVariable(value = "id") Long tourId,
									 @RequestParam("imageIdsToRemove") Optional<List<Long>> imageIdsToRemove)
			throws UnsupportedImageTypeException, WrongImageSizeException {
		return apiService.uploadAndSaveImages(files, tourId, imageIdsToRemove);
	}

	@Override
	@ApiOperation(value = "Unsupported operation", hidden = true)
	public void patch(Long id, TourRequest request) {
		throw new UnsupportedOperationException();
	}

}
