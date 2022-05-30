package com.kot.horizon.booking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.architecture.model.BaseEntity;
import com.kot.horizon.tour.model.TourEntity;
import com.kot.horizon.user.model.UserEntity;

@Entity
@Table(name = "booking")
public class BookingEntity implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "tour_id")
	private TourEntity tour;

	@ManyToOne
	@JoinColumn(name = "tourist_id")
	private UserEntity tourist;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private BookingStatus status;

	@Column(name = "liked")
	private Boolean liked;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TourEntity getTour() {
		return tour;
	}

	public void setTour(TourEntity tour) {
		this.tour = tour;
	}

	public UserEntity getTourist() {
		return tourist;
	}

	public void setTourist(UserEntity tourist) {
		this.tourist = tourist;
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

		BookingEntity that = (BookingEntity) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(tour, that.tour)
				.append(tourist, that.tourist)
				.append(status, that.status)
				.append(liked, that.liked)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(tour)
				.append(tourist)
				.append(status)
				.append(liked)
				.toHashCode();
	}
}
