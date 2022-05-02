package com.kot.horizon.model.photo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import com.kot.horizon.model.BaseEntity;
import com.kot.horizon.model.user.UserEntity;

@Entity
@Table(name = "photo")
public class PhotoEntity implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", columnDefinition = "bytea", nullable = false)
	private byte[] content;

	@NotNull
	@Column(name = "mime_type", nullable = false)
	private String mimeType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof PhotoEntity)){
			return false;
		}
		if (this == obj){
			return true;
		}
		final PhotoEntity otherObject = (PhotoEntity) obj;
		return new EqualsBuilder()
				.append(this.id, otherObject.id)
				.append(this.content, otherObject.content)
				.append(this.mimeType, otherObject.mimeType)
				.isEquals();
	}

	@Override
	public int hashCode(){
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(id);
		builder.append(content);
		builder.append(mimeType);
		return builder.hashCode();
	}
}
