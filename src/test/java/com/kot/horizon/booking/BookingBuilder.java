package com.kot.horizon.booking;

import java.util.concurrent.ThreadLocalRandom;
import com.kot.horizon.UserBuilder;
import com.kot.horizon.tour.TourBuilder;
import com.kot.horizon.tour.TourEntity;
import com.kot.horizon.user.model.UserEntity;

public class BookingBuilder {

	private Long id;
	private TourEntity tour;
	private UserEntity tourist;
	private BookingStatus status;
	private Boolean liked;

	private final UserBuilder userBuilder = new UserBuilder();
	private final TourBuilder tourBuilder = new TourBuilder();

	public BookingBuilder() {
		this.initDefaultData();
	}

	private void initDefaultData() {
		Long randomValue = ThreadLocalRandom.current().nextLong(1, 999999);
		this.id = randomValue;
		this.tour = tourBuilder.build();
		this.tourist = userBuilder.build();
		this.status = BookingStatus.PENDING;
		this.liked = randomValue % 2 == 0;
	}

	public BookingEntity build() {
		BookingEntity entity = new BookingEntity();
		entity.setId(this.id);
		entity.setTour(this.tour);
		entity.setTourist(this.tourist);
		entity.setStatus(this.status);
		entity.setLiked(this.liked);
		this.initDefaultData();
		return entity;
	}

	public BookingEntity buildNew() {
		return this.setId(null).build();
	}

	public BookingBuilder setId(Long id) {
		this.id = id;
		return this;
	}

	public BookingBuilder setTour(TourEntity tour) {
		this.tour = tour;
		return this;
	}

	public BookingBuilder setTourist(UserEntity tourist) {
		this.tourist = tourist;
		return this;
	}

	public BookingBuilder setStatus(BookingStatus status) {
		this.status = status;
		return this;
	}

	public BookingBuilder setLiked(Boolean liked) {
		this.liked = liked;
		return this;
	}
}
