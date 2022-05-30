package com.kot.horizon.api.v1.tour.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

	@ApiModelProperty(notes = "The start date of tour event")
	private LocalDateTime eventDate;

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

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		TourRequest that = (TourRequest) o;

		return new EqualsBuilder()
				.append(rate, that.rate)
				.append(name, that.name)
				.append(description, that.description)
				.append(geoData, that.geoData)
				.append(eventDate, that.eventDate)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(name)
				.append(description)
				.append(rate)
				.append(geoData)
				.append(eventDate)
				.toHashCode();
	}
}
