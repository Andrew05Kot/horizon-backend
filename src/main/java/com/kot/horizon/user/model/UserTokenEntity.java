package com.kot.horizon.user.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.kot.horizon.architecture.model.BaseEntity;

@Entity
@Table(name = "user_token")
public class UserTokenEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    @NotBlank
    @NotNull
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,  updatable = false)
    @NotNull
    private UserEntity userEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserToken() {
        return token;
    }

    public void setUserToken(String userToken) {
        this.token = userToken;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTokenEntity userTokenEntity = (UserTokenEntity) o;
        return Objects.equals(id, userTokenEntity.id) &&
                Objects.equals(token, userTokenEntity.token) &&
                Objects.equals(userEntity, userTokenEntity.userEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, userEntity);
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", userToken='" + token + '\'' +
                ", userEntity=" + userEntity +
                '}';
    }
}
