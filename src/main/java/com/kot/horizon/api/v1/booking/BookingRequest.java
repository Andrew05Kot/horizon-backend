package com.kot.horizon.api.v1.booking;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.api.v1.general.AbstractRequest;
import com.kot.horizon.booking.BookingStatus;

public class BookingRequest implements AbstractRequest {

	@ApiModelProperty(notes = "The identification unique number of tour", example = "1")
	@NotNull
	private Long tourId;

	@ApiModelProperty(notes = "The identification unique number of tourist that wants a booking", example = "1")
	@NotNull
	private Long touristId;

	@ApiModelProperty(notes = "Geographical information of the tour")
	private BookingStatus status;

	@ApiModelProperty(notes = "Is well or bed tour for this user")
	private Boolean liked;

	public Long getTourId() {
		return tourId;
	}

	public void setTourId(Long tourId) {
		this.tourId = tourId;
	}

	public Long getTouristId() {
		return touristId;
	}

	public void setTouristId(Long touristId) {
		this.touristId = touristId;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public Boolean getLiked() {
		return liked;
	}

	public void setLiked(Boolean liked) {
		this.liked = liked;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		BookingRequest that = (BookingRequest) o;

		return new EqualsBuilder()
				.append(tourId, that.tourId)
				.append(touristId, that.touristId)
				.append(status, that.status)
				.append(liked, that.liked)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(tourId)
				.append(touristId)
				.append(status)
				.append(liked)
				.toHashCode();
	}
}
