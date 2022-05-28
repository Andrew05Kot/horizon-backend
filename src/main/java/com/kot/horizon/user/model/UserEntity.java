package com.kot.horizon.user.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.architecture.model.BaseEntity;

@Entity
@Table(name = "api_user")
public class UserEntity implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "social_id", unique = true)
	private String socialId;

	@Column(name = "last_name", nullable = false)
	@NotBlank
	private String lastName;

	@Column(name = "first_name")
	private String firstName = "";

	@Column(name = "email")
	@Email
	private String email;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "birth_date")
	private LocalDate birthDate;

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(name = "locale", nullable = false)
	@NotNull
	@Enumerated(EnumType.STRING)
	private Language language;

	@Column(name = "image_url")
	private String imageUrl;

	@NotBlank
	@Column(name = "social_type")
	private String socialType;

	@Column(name = "about_me")
	private String aboutMe;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSocialType() {
		return socialType;
	}

	public void setSocialType(String socialType) {
		this.socialType = socialType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		UserEntity that = (UserEntity) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(socialId, that.socialId)
				.append(lastName, that.lastName)
				.append(firstName, that.firstName)
				.append(email, that.email)
				.append(birthDate, that.birthDate)
				.append(role, that.role)
				.append(language, that.language)
				.append(socialType, that.socialType)
				.append(phoneNumber, that.phoneNumber)
				.append(aboutMe, that.aboutMe)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(socialId)
				.append(lastName)
				.append(firstName)
				.append(email)
				.append(birthDate)
				.append(role)
				.append(language)
				.append(socialType)
				.append(phoneNumber)
				.append(aboutMe)
				.toHashCode();
	}

	@Override
	public String toString() {
		return "UserEntity{" +
				"id=" + id +
				", socialId='" + socialId + '\'' +
				", lastName='" + lastName + '\'' +
				", firstName='" + firstName + '\'' +
				", email='" + email + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", birthDate=" + birthDate +
				", role=" + role +
				", language=" + language +
				", socialType='" + socialType + '\'' +
				", aboutMe='" + aboutMe + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				'}';
	}
}