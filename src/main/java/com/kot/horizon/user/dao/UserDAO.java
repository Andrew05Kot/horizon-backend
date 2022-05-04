package com.kot.horizon.user.dao;

import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.repository.UserRepository;

@Service
public class UserDAO extends AbstractDAO<UserEntity, UserRepository> {

    public UserEntity findBySocialId(String facebookId){
        return repository.findBySocialId(facebookId).orElse(null);
    }

    // this method allowed for VerificationTokenService
    public UserEntity findOneForSystem(Long userId){
        return repository.findById(userId).orElse(null);
    }

}
