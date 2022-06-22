package com.kot.horizon.tour;

import java.time.ZonedDateTime;
import java.util.concurrent.ThreadLocalRandom;
import com.kot.horizon.user.model.UserEntity;

public class TourBuilder {

	private Long id;
	private String name;
	private String description;
	private int rate;
	private ZonedDateTime eventDate;
	private UserEntity owner;
	private double price;
	private int freePlacesCount;

	public TourBuilder() {
		this.initDefaultData();
	}

	public TourEntity build() {
		TourEntity entity = new TourEntity();
		entity.setId(this.id);
		entity.setName(this.name);
		entity.setDescription(this.description);
		entity.setRate(this.rate);
		entity.setEventDate(eventDate);
		entity.setOwner(this.owner);
		entity.setPrice(this.price);
		entity.setFreePlacesCount(this.freePlacesCount);
		this.initDefaultData();
		return entity;
	}

	public TourEntity buildNew() {
		return this.setId(null).build();
	}

	private void initDefaultData() {
		Long randomValue = ThreadLocalRandom.current().nextLong(1, 999999);
		this.id = randomValue;
		this.name = "name " + randomValue;
		this.description = "ddeedsccrr " + randomValue;
		this.rate = randomValue % 2 == 0 ? 55 : 85;
		this.freePlacesCount = randomValue % 2 == 0 ? 5 : 8;
		this.price = 100;
		this.eventDate = ZonedDateTime.now();
	}

	public TourBuilder setId(Long id) {
		this.id = id;
		return this;
	}

	public TourBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public TourBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public TourBuilder setRate(int rate) {
		this.rate = rate;
		return this;
	}

	public TourBuilder setEventDate(ZonedDateTime eventDate) {
		this.eventDate = eventDate;
		return this;
	}

	public TourBuilder setOwner(UserEntity owner) {
		this.owner = owner;
		return this;
	}

	public TourBuilder setPrice(double price) {
		this.price = price;
		return this;
	}

	public TourBuilder setFreePlacesCount(int freePlacesCount) {
		this.freePlacesCount = freePlacesCount;
		return this;
	}
}
