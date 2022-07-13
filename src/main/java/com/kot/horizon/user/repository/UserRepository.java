package com.kot.horizon.user.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;

@Repository
public interface UserRepository extends BaseCRUDRepository<UserEntity> {
	
	Optional<UserEntity> findBySocialId(String socialId);
	Optional<UserEntity> findByEmail(String email);
}