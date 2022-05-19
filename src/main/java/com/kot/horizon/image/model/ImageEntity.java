package com.kot.horizon.image.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.architecture.model.BaseEntity;

@Entity
@Table(name = "image")
public class ImageEntity implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "image_name")
	private String imageName;

	@Column(name = "mime_type")
	private String mimeType;

	@Column(name = "original_name")
	private String originalName;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ImageEntity that = (ImageEntity) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(imageName, that.imageName)
				.append(mimeType, that.mimeType)
				.append(originalName, that.originalName)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(imageName)
				.append(mimeType)
				.append(originalName)
				.toHashCode();
	}

	@Override
	public String toString() {
		return "ImageEntity{" +
				"id=" + id +
				", imageName='" + imageName + '\'' +
				", mimeType='" + mimeType + '\'' +
				", originalName='" + originalName + '\'' +
				'}';
	}
}
