package com.kot.horizon.api.v1.geodata;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.api.v1.general.AbstractRequest;

public class GeoDataRequest implements AbstractRequest {

	@ApiModelProperty("latitude")
	private Double latitude;

	@ApiModelProperty("longitude")
	private Double longitude;

	@ApiModelProperty("altitude")
	private Double altitude;

	@ApiModelProperty("Full address name on map")
	private String addressName;

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

		GeoDataRequest that = (GeoDataRequest) o;

		return new EqualsBuilder()
				.append(latitude, that.latitude)
				.append(longitude, that.longitude)
				.append(altitude, that.altitude)
				.append(addressName, that.addressName)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(latitude)
				.append(longitude)
				.append(altitude)
				.append(addressName)
				.toHashCode();
	}
}
