package com.kot.horizon.api.v1.tour;

import java.time.LocalDateTime;
import java.util.List;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.api.v1.general.AbstractResponse;
import com.kot.horizon.api.v1.geodata.GeoDataResponse;
import com.kot.horizon.api.v1.image.ImageResponse;
import com.kot.horizon.api.v1.user.User;

public class TourResponse implements AbstractResponse {

	@ApiModelProperty(notes = "The identification unique number of item", example = "1")
	private Long id;

	@ApiModelProperty(notes = "The name that describes tour", example = "Odessa")
	private String name;

	@ApiModelProperty(notes = "The information about the tour", example = "Best trip to sea..")
	private String description;

	@ApiModelProperty(notes = "The rate of tour", example = "99")
	private int rate;

	@ApiModelProperty(notes= "Images of tour")
	private List<ImageResponse> images;

	@ApiModelProperty(notes = "Geographical information of the tour")
	private GeoDataResponse geoData;

	@ApiModelProperty(notes = "The user owner of the tour.")
	private User owner;

	@ApiModelProperty(notes = "The start date of tour event")
	private LocalDateTime eventDate;

	@ApiModelProperty(notes= "Joined users to the tour")
	private List<User> joinedUsers;

	@ApiModelProperty(notes = "The price of the trip")
	private double price;

	@ApiModelProperty(notes = "The count of free places")
	private int freePlacesCount;

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

	public List<ImageResponse> getImages() {
		return images;
	}

	public void setImages(List<ImageResponse> images) {
		this.images = images;
	}

	public GeoDataResponse getGeoData() {
		return geoData;
	}

	public void setGeoData(GeoDataResponse geoData) {
		this.geoData = geoData;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
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

	public List<User> getJoinedUsers() {
		return joinedUsers;
	}

	public void setJoinedUsers(List<User> joinedUsers) {
		this.joinedUsers = joinedUsers;
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

		TourResponse that = (TourResponse) o;

		return new EqualsBuilder()
				.append(rate, that.rate)
				.append(id, that.id)
				.append(name, that.name)
				.append(description, that.description)
				.append(images, that.images)
				.append(geoData, that.geoData)
				.append(owner, that.owner)
				.append(eventDate, that.eventDate)
				.append(price, that.price)
				.append(joinedUsers, that.joinedUsers)
				.append(freePlacesCount, that.freePlacesCount)
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
				.append(geoData)
				.append(owner)
				.append(eventDate)
				.append(price)
				.append(joinedUsers)
				.append(freePlacesCount)
				.toHashCode();
	}
}
