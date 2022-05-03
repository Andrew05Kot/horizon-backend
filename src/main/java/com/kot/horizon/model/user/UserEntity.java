package com.kot.horizon.model.user;

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
import com.kot.horizon.model.BaseEntity;

@Entity
@Table(name = "horizon_user")
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

	@Column(name = "birthDate")
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

//	@OneToOne
//	@JoinColumn(  name = "photo_id" )
//	private ShortPhotoEntity photo;

	@NotBlank
	@Column(name = "social_type")
	private String socialType;

	private String password;

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
				", birthDate=" + birthDate +
				", role=" + role +
				", language=" + language +
				", socialType='" + socialType + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				'}';
	}
}