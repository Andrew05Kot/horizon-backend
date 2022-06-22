package com.kot.horizon.geodata;

import java.util.concurrent.ThreadLocalRandom;

public class GeoDataBuilder {

	private Long id;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	private String addressName;

	public GeoDataBuilder() {
		this.initDefaultData();
	}

	private void initDefaultData() {
		Long randomValue = ThreadLocalRandom.current().nextLong(1, 999999);
		this.id = randomValue;
		this.latitude = randomValue.doubleValue();
		this.longitude = randomValue.doubleValue();
		this.altitude = randomValue.doubleValue();
		this.addressName = ("address " + randomValue).toUpperCase();
	}

	public GeoDataEntity build() {
		GeoDataEntity entity = new GeoDataEntity();
		entity.setId(this.id);
		entity.setLongitude(this.longitude);
		entity.setAltitude(this.altitude);
		entity.setLatitude(this.latitude);
		entity.setAddressName(this.addressName);
		this.initDefaultData();
		return entity;
	}

	public GeoDataEntity buildNew() {
		return this.setId(null).build();
	}

	public GeoDataBuilder setId(Long id) {
		this.id = id;
		return this;
	}

	public GeoDataBuilder setLatitude(Double latitude) {
		this.latitude = latitude;
		return this;
	}

	public GeoDataBuilder setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}

	public GeoDataBuilder setAltitude(Double altitude) {
		this.altitude = altitude;
		return this;
	}

	public GeoDataBuilder setAddressName(String addressName) {
		this.addressName = addressName;
		return this;
	}
}
