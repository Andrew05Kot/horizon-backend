package com.kot.horizon.repository.user;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.repository.BaseCRUDRepository;

@Repository
public interface UserRepository extends BaseCRUDRepository<UserEntity> {
	
	Optional<UserEntity> findBySocialId(String socialId);
	Optional<UserEntity> findByEmail(String email);
}