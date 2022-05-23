package com.kot.horizon.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.architecture.model.BaseEntity;
import com.kot.horizon.user.model.UserEntity;

@Entity
@Table(name = "notification")
public class NotificationEntity implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "recipient_id")
	private UserEntity recipient;

	@Column(name = "statue")
	private String status;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getRecipient() {
		return recipient;
	}

	public void setRecipient(UserEntity recipient) {
		this.recipient = recipient;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		NotificationEntity that = (NotificationEntity) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(recipient, that.recipient)
				.append(status, that.status)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(recipient)
				.append(status)
				.toHashCode();
	}
}
