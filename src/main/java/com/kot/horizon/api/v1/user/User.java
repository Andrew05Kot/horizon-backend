package com.kot.horizon.api.v1.user;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import com.kot.horizon.api.v1.general.AbstractRequest;
import com.kot.horizon.api.v1.general.AbstractResponse;
import com.kot.horizon.api.v1.photo.Photo;
import com.kot.horizon.model.user.Language;
import com.kot.horizon.model.user.UserRole;

public class User implements AbstractRequest, AbstractResponse {
	
	@ApiModelProperty(notes = "The identification unique number of user", example = "1")
	private Long id;

	@NotBlank
	@ApiModelProperty(notes = "The last name of user <br> filterable equals (=) and contains(:), this field has been sorted", required = true, example = "Ivanenko")
	private String lastName;

	@ApiModelProperty(notes = "The first name of a user", required = true, example = "Ivan")
	private String firstName;

	@ApiModelProperty(notes = "The date of birth of a user", example = "2000-01-01")
	private LocalDate birthDate;

	@ApiModelProperty(notes = "The role of a user", required = true, example = "ROLE_USER")
	private UserRole role;

	@Email
	@ApiModelProperty(notes = "The email a user <br> filterable equals (=)  and contains(:), this field has been sorted")
	private String email;

	@ApiModelProperty(notes = "The language of user" , dataType = "string", allowableValues = "UK, EN")
	private Language language;

	@ApiModelProperty(notes = "The short version of table photo", required = false, example = "null")
	private Photo photo;

	@ApiModelProperty(notes = "Check is photo deleted")
	private boolean isPhotoToDelete;

	@ApiModelProperty(notes = "The name of user social login")
	private String socialType;

	public boolean getIsPhotoToDelete() {
		return isPhotoToDelete;
	}

	public void setIsPhotoToDelete(boolean photoToDelete) {
		isPhotoToDelete = photoToDelete;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getSocialType() {
		return socialType;
	}

	public void setSocialType(String socialType) {
		this.socialType = socialType;
	}
}