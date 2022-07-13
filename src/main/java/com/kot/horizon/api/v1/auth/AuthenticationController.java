package com.kot.horizon.api.v1.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kot.horizon.api.v1.ServiceAPIUrl;
import com.kot.horizon.common.config.SwaggerInfo;

@Api(tags = SwaggerInfo.USER_API)
@RestController
@RequestMapping(value = AuthenticationController.BASE_URL)
public class AuthenticationController {

	public static final String BASE_URL = ServiceAPIUrl.V1_PATH + "/auth";

	@Autowired
	private AuthenticationAPIService authenticationAPIService;
	
	@ApiOperation(value = "Get long lived authentication token")
	@GetMapping
	public ResponseEntity<String> authenticate(
			@ApiParam(name = "token", value = "Short lived token used to get a new long lived token")
			@RequestParam String token) {
		String longLivedToken = authenticationAPIService.authenticate(token);
		return new ResponseEntity<>(String.format("{\"token\" : \"%s\"}", longLivedToken), HttpStatus.OK);
	}
}