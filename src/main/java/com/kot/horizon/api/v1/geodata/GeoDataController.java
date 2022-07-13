package com.kot.horizon.api.v1.geodata;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kot.horizon.api.v1.ServiceAPIUrl;
import com.kot.horizon.api.v1.general.AbstractController;
import com.kot.horizon.common.config.SwaggerInfo;
import com.kot.horizon.geodata.GeoDataEntity;
import com.kot.horizon.geodata.GeoDataService;

@Api(tags = SwaggerInfo.GEO_DATA_API)
@RestController
@RequestMapping(value = GeoDataController.BASE_URL)
public class GeoDataController extends AbstractController<GeoDataEntity, GeoDataRequest, GeoDataResponse, GeoDataService, GeoDataAPIService> {

	public static final String BASE_URL = ServiceAPIUrl.V1_PATH + "/geo-data";

}
