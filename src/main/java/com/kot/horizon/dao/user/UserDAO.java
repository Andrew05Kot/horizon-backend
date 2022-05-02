package com.kot.horizon.dao.user;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import com.kot.horizon.dao.AbstractDAO;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.repository.user.UserRepository;

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
