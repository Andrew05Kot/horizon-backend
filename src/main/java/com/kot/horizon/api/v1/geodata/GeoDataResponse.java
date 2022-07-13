package com.kot.horizon.api.v1.geodata;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.api.v1.general.AbstractResponse;

public class GeoDataResponse implements AbstractResponse {

	@ApiModelProperty(notes = "The identification unique number of item", example = "1")
	private Long id;

	@ApiModelProperty("latitude")
	private Double latitude;

	@ApiModelProperty("longitude")
	private Double longitude;

	@ApiModelProperty("altitude")
	private Double altitude;

	@ApiModelProperty("addressName")
	private String addressName;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		GeoDataResponse that = (GeoDataResponse) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(latitude, that.latitude)
				.append(longitude, that.longitude)
				.append(altitude, that.altitude)
				.append(addressName, that.addressName)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(latitude)
				.append(longitude)
				.append(altitude)
				.append(addressName)
				.toHashCode();
	}
}
