package com.kot.horizon.api.v1.tour.dto;

import java.util.List;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.api.v1.general.AbstractResponse;
import com.kot.horizon.api.v1.image.ImageResponse;

public class TourResponse implements AbstractResponse {

	@ApiModelProperty(notes = "The identification unique number of item", example = "1")
	private Long id;

	@ApiModelProperty(notes = "The name that describes tour", example = "Odessa")
	private String name;

	@ApiModelProperty(notes = "The information about the tour", example = "Best trip to sea..")
	private String description;

	@ApiModelProperty(notes = "The rate of tour", example = "Best trip to sea..")
	private int rate;

	@ApiModelProperty(notes= "Images of tour")
	private List<String> images;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		TourResponse that = (TourResponse) o;

		return new EqualsBuilder()
				.append(rate, that.rate)
				.append(id, that.id)
				.append(name, that.name)
				.append(description, that.description)
				.append(images, that.images)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(name)
				.append(description)
				.append(rate)
				.append(images)
				.toHashCode();
	}
}
