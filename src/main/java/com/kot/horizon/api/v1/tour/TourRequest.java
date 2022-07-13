package com.kot.horizon.api.v1.tour;

import java.time.LocalDateTime;
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

	@ApiModelProperty(notes = "The price of the trip")
	private double price;

	@ApiModelProperty(notes = "The count of free places")
	private int freePlacesCount;

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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getFreePlacesCount() {
		return freePlacesCount;
	}

	public void setFreePlacesCount(int freePlacesCount) {
		this.freePlacesCount = freePlacesCount;
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
				.append(price, that.price)
				.append(freePlacesCount, that.freePlacesCount)
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
				.append(price)
				.append(freePlacesCount)
				.toHashCode();
	}
}
