package com.kot.horizon.architecture.model;

import com.kot.horizon.user.model.UserEntity;

public interface UserFilterable {
 
    public UserEntity getUser();

    public void setUser( UserEntity user );
    
}
