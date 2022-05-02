package com.kot.horizon.model.photo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.model.BaseEntity;
import com.kot.horizon.model.user.UserEntity;

@Entity
@Table(name = "photo")
public class ShortPhotoEntity implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "mime_type", nullable = false)
	private String mimeType;

	@NotNull
	@ManyToOne
	@JoinColumn( unique = true, nullable = false)
	private UserEntity user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity userId) {
		this.user = userId;
	}

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ShortPhotoEntity)){
			return false;
		}
		if (this == obj){
			return true;
		}
		final ShortPhotoEntity otherObject = (ShortPhotoEntity) obj;
		return new EqualsBuilder()
				.append(this.id, otherObject.id)
				.append(this.mimeType, otherObject.mimeType)
				.append(this.user, otherObject.user)
				.isEquals();
	}

	@Override
	public int hashCode(){
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(id);
		builder.append(mimeType);
		builder.append(user);
		return builder.hashCode();
	}

}
