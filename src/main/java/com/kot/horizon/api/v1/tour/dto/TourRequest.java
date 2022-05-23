package com.kot.horizon.api.v1.tour.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.api.v1.general.AbstractRequest;
import com.kot.horizon.api.v1.geodata.GeoDataRequest;

public class TourRequest implements AbstractRequest {

	@ApiModelProperty(notes = "The identification unique number of item", example = "1")
	private Long id;

	@ApiModelProperty(notes = "The name that describes tour", example = "Odessa")
	@NotBlank
	private String name;

	@ApiModelProperty(notes = "The information about the tour", example = "Best trip to sea..")
	@NotBlank
	private String description;

	@ApiModelProperty(notes = "The rate of tour", example = "50")
	@Min(0)
	@Max(100)
	private int rate;

	@ApiModelProperty(notes = "Geographical information of the tour")
	@NotNull
	private GeoDataRequest geoData;

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

	public GeoDataRequest getGeoData() {
		return geoData;
	}

	public void setGeoData(GeoDataRequest geoData) {
		this.geoData = geoData;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		TourRequest that = (TourRequest) o;

		return new EqualsBuilder()
				.append(rate, that.rate)
				.append(id, that.id)
				.append(name, that.name)
				.append(description, that.description)
				.append(geoData, that.geoData)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(name)
				.append(description)
				.append(rate)
				.append(geoData)
				.toHashCode();
	}
}
