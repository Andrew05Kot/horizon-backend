package com.kot.horizon.model;

import com.kot.horizon.model.user.UserEntity;

public interface UserFilterable {
 
    public UserEntity getUser();

    public void setUser( UserEntity user );
    
}
