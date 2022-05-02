package com.kot.horizon.api.v1.user;

import javax.validation.Valid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kot.horizon.api.ServiceAPIUrl;
import com.kot.horizon.api.v1.general.AbstractController;
import com.kot.horizon.config.SwaggerInfo;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.service.user.UserService;

@Api(tags = SwaggerInfo.USER_API)
@RestController
@RequestMapping(value = UserController.BASE_URL)
public class UserController extends AbstractController<UserEntity, User, User, UserService, UserAPIService> {

	public static final String BASE_URL = ServiceAPIUrl.V1_PATH + "/user";
	
	@ApiOperation( "Get current authorized user" )
	@GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getCurrentUser() {
		return apiService.getCurrentUser();
	}

	@Override
	@ApiOperation( value = "Unsupported operation", hidden = true )
	public User create( @RequestBody @Valid User request ) {
		throw new UnsupportedOperationException();
	}

	@Override
	@ApiOperation( value = "Unsupported operation", hidden = true )
	public void delete( @PathVariable Long id ) {
		throw new UnsupportedOperationException();
	}

}