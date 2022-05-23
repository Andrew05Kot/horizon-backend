package com.kot.horizon.common.config;

import java.util.Set;
import com.google.common.collect.Sets;
import org.springframework.http.MediaType;

public class SwaggerInfo {

    private SwaggerInfo() {}

	public static final String USER_API = "User API";
	public static final String TOUR_API = "Tour API";

	public static final String IMAGE_API = "Image API";

	public static final String GEO_DATA_API = "Geo Data API";

	public static final String REGISTRATION_API = "Registration API";

	public static final String AUTHENTICATION_API = "Authentication API";

	public static final Set<String> CONSUMES_AND_PRODUCES = Sets.newHashSet(MediaType.APPLICATION_JSON_VALUE);

	public static final String API_VERSION_V1 = "1.0";

	public static final String PATH_V1 = "/v1/.*";

	public static final String DESCRIPTION = "API for fetching user and wish related information";

	public static final String TITLE = "Horizon API";

	public static final String GROUP_NAME = "horizon-api-";

	public static final String USER_CONTROLLER_INFO = "The controller for user operations";

	public static final String TOUR_CONTROLLER_INFO = "The controller for tour operations";

	public static final String PHOTO_API = "Photo API";

}
