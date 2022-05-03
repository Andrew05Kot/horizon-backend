package com.kot.horizon.api.v1.registration.controller;

import javax.validation.Valid;
import javax.validation.groups.Default;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.kot.horizon.api.ServiceAPIUrl;
import com.kot.horizon.api.v1.registration.converter.RegistrationMapper;
import com.kot.horizon.api.v1.registration.dto.RegistrationRequest;
import com.kot.horizon.api.v1.registration.dto.RegistrationResponse;
import com.kot.horizon.config.SwaggerInfo;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.service.user.UserService;

//@Slf4j
@Api(tags = {SwaggerInfo.REGISTRATION_API})
@Controller
@RequestMapping(value = RegistrationController.BASE_URL)
public class RegistrationController {

	public static final String BASE_URL = ServiceAPIUrl.V1_PATH + "/auth";

	@Autowired
	private UserService userService;

	@Autowired
	private RegistrationMapper mapper;

	@ApiOperation("Signup new user")
	@ResponseBody
	@PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegistrationResponse> registerUser(@Validated({Default.class}) @RequestBody RegistrationRequest registrationRequest) {
		UserEntity entity = userService.registerUser(mapper.mapFromDto(registrationRequest));
		RegistrationResponse response = mapper.mapToDto(entity);
		return ResponseEntity.ok().body(response);
	}
}
