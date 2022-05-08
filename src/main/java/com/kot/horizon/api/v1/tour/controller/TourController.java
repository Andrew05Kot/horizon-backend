package com.kot.horizon.api.v1.tour.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kot.horizon.api.v1.ServiceAPIUrl;
import com.kot.horizon.api.v1.general.AbstractController;
import com.kot.horizon.api.v1.tour.dto.TourRequest;
import com.kot.horizon.api.v1.tour.dto.TourResponse;
import com.kot.horizon.api.v1.tour.service.TourAPIService;
import com.kot.horizon.common.config.SwaggerInfo;
import com.kot.horizon.tour.model.TourEntity;
import com.kot.horizon.tour.service.TourService;

@Api(tags = SwaggerInfo.TOUR_API)
@RestController
@RequestMapping(value = TourController.BASE_URL)
public class TourController extends AbstractController<TourEntity, TourRequest, TourResponse, TourService, TourAPIService> {

	public static final String BASE_URL = ServiceAPIUrl.V1_PATH + "/tour";

	@Override
	@ApiOperation(value = "Unsupported operation", hidden = true)
	public void patch(Long id, TourRequest request) {
		throw new UnsupportedOperationException();
	}

}
