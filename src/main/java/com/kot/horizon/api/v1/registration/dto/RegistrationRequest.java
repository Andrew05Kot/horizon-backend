package com.kot.horizon.api.v1.registration.dto;

import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RegistrationRequest {

	@NotBlank
	@ApiModelProperty
	private String firstName;

	@NotBlank
	@ApiModelProperty
	private String lastName;

	@NotBlank
	@ApiModelProperty
	private String email;

	@NotBlank
	@ApiModelProperty
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		RegistrationRequest that = (RegistrationRequest) o;

		return new EqualsBuilder()
				.append(firstName, that.firstName)
				.append(lastName, that.lastName)
				.append(email, that.email)
				.append(password, that.password)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(firstName)
				.append(lastName)
				.append(email)
				.append(password)
				.toHashCode();
	}
}
