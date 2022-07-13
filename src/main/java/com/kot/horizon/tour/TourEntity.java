package com.kot.horizon.tour;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.architecture.model.BaseEntity;
import com.kot.horizon.geodata.GeoDataEntity;
import com.kot.horizon.image.model.ImageEntity;
import com.kot.horizon.user.model.UserEntity;

@Entity
@Table(name = "tour")
public class TourEntity implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	@NotBlank
	private String name;

	@Column(name = "description", nullable = false)
	@NotBlank
	private String description;

	@Column(name = "rate", nullable = false)
	private int rate;

	@Column(name = "event_date", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private ZonedDateTime eventDate;

	@OneToMany
	@JoinTable( name = "tour_image",
			joinColumns = @JoinColumn(name = "tour_id", foreignKey = @ForeignKey(name = "fk_tour_image_to_tour")),
			inverseJoinColumns = @JoinColumn(name = "image_id", foreignKey = @ForeignKey(name = "fk_tour_image_to_image")))
	private List<ImageEntity> images;

	@OneToOne(cascade = CascadeType.ALL)
	private GeoDataEntity geoData;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private UserEntity owner;

	@Column(name = "price", nullable = false)
	private double price = 0;

	@Column(name = "free_places_count", nullable = false)
	private int freePlacesCount = 1;

	@ElementCollection
	@CollectionTable(name="hashtag")
	@MapKeyColumn(name = "hashtag_key")
	@Column(name = "hashtag")
	public Map<String, String> hashtags = new HashMap<>();

	@OneToMany
	@JoinTable( name = "tour_tourist",
			joinColumns = @JoinColumn(name = "tour_id", foreignKey = @ForeignKey(name = "fk_tour_tourist_to_tour")),
			inverseJoinColumns = @JoinColumn(name = "tourist_id", foreignKey = @ForeignKey(name = "fk_tour_tourist_to_tourist")))
	private List<UserEntity> tourists;

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

	public List<ImageEntity> getImages() {
		return images;
	}

	public void setImages(List<ImageEntity> images) {
		this.images = images;
	}

	public GeoDataEntity getGeoData() {
		return geoData;
	}

	public void setGeoData(GeoDataEntity geoData) {
		this.geoData = geoData;
	}

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

	public ZonedDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(ZonedDateTime eventDate) {
		this.eventDate = eventDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<UserEntity> getTourists() {
		return tourists;
	}

	public void setTourists(List<UserEntity> tourists) {
		this.tourists = tourists;
	}

	public void addTourist(UserEntity tourist) {
		this.tourists.add(tourist);
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

		TourEntity that = (TourEntity) o;

		return new EqualsBuilder()
				.append(rate, that.rate)
				.append(id, that.id)
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
				.append(id)
				.append(name)
				.append(description)
				.append(rate)
				.append(geoData)
				.append(eventDate)
				.append(price)
				.append(freePlacesCount)
				.toHashCode();
	}

	@Override
	public String toString() {
		return "TourEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", rate=" + rate +
				", price=" + price +
				", eventDate=" + eventDate +
				", geoData=" + geoData +
				", owner=" + owner +
				", freePlacesCount=" + freePlacesCount +
				'}';
	}
}
