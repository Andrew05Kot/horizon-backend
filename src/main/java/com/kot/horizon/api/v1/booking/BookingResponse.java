package com.kot.horizon.api.v1.booking;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.api.v1.general.AbstractResponse;
import com.kot.horizon.api.v1.tour.dto.TourResponse;
import com.kot.horizon.api.v1.user.User;
import com.kot.horizon.booking.BookingStatus;

public class BookingResponse implements AbstractResponse {

	@ApiModelProperty(notes = "The identification unique number of item", example = "1")
	private Long id;

	@ApiModelProperty(notes = "The tour of booking")
	private TourResponse tour;

	@ApiModelProperty(notes = "The tourist of booking")
	private User tourist;

	@ApiModelProperty(notes = "Geographical information of the tour")
	private BookingStatus status;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TourResponse getTour() {
		return tour;
	}

	public void setTour(TourResponse tour) {
		this.tour = tour;
	}

	public User getTourist() {
		return tourist;
	}

	public void setTourist(User tourist) {
		this.tourist = tourist;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		BookingResponse that = (BookingResponse) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(status, that.status)
				.append(tour, that.tour)
				.append(tourist, that.tourist)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(status)
				.append(tour)
				.append(tourist)
				.toHashCode();
	}
}
