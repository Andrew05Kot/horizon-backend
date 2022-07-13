package com.kot.horizon.api.v1.image;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.api.v1.general.AbstractResponse;

public class ImageResponse implements AbstractResponse {


	private Long id;
	@ApiModelProperty(notes = "Name of the image", example = "dog")
	private String imageName;

	@ApiModelProperty(notes = "The media type of file", example = "image/jpj")
	private String mimeType;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ImageResponse response = (ImageResponse) o;

		return new EqualsBuilder()
				.append(id, response.id)
				.append(imageName, response.imageName)
				.append(mimeType, response.mimeType)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(imageName)
				.append(mimeType)
				.toHashCode();
	}
}
