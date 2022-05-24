package com.kot.horizon.api.v1.booking;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kot.horizon.api.v1.ServiceAPIUrl;
import com.kot.horizon.api.v1.general.AbstractController;
import com.kot.horizon.booking.BookingEntity;
import com.kot.horizon.booking.BookingService;
import com.kot.horizon.common.config.SwaggerInfo;

@Api(tags = SwaggerInfo.BOOKING_API)
@RestController
@RequestMapping(value = BookingController.BASE_URL)
public class BookingController extends AbstractController<BookingEntity, BookingRequest, BookingResponse, BookingService, BookingAPIService> {

	public static final String BASE_URL = ServiceAPIUrl.V1_PATH + "/booking";

	@Override
	@ApiOperation(value = "Unsupported operation", hidden = true)
	public void patch(Long id, BookingRequest request) {
		throw new UnsupportedOperationException();
	}
}
