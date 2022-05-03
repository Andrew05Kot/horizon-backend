package com.kot.horizon.api.v1.registration.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.kot.horizon.api.v1.registration.dto.RegistrationRequest;
import com.kot.horizon.api.v1.registration.dto.RegistrationResponse;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.model.user.UserRole;

@Component
public class RegistrationMapper {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserEntity mapFromDto(RegistrationRequest request) {
		UserEntity entity = new UserEntity();
		entity.setFirstName(request.getFirstName());
		entity.setLastName(request.getLastName());
		entity.setEmail(request.getEmail());
		entity.setPassword(request.getPassword());
		entity.setRole(UserRole.ROLE_USER);
		entity.setSocialType("local");
		return entity;
	}

	public RegistrationResponse mapToDto(UserEntity entity) {
		RegistrationResponse response = new RegistrationResponse();
		response.setId(entity.getId());
		response.setEmail(entity.getEmail());
		response.setFirstName(entity.getFirstName());
		response.setLastName(entity.getLastName());
		return response;
	}

}
