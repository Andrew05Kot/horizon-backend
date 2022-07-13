package com.kot.horizon.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.model.UserPrincipal;
import com.kot.horizon.user.model.UserRole;

@Service
public class CurrentUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserService.class);

    public boolean isAdministrator() {
        return isAdministrator( getCurrentUser() );
    }

    public boolean isAdministrator( UserEntity userEntity ) {
        if (userEntity == null) {
            return false;
        }
        return userEntity.getRole().equals(UserRole.ROLE_ADMIN);
    }

    public boolean isUserHasRole(UserEntity userEntity, UserRole userRole) {
        if (userEntity == null) {
            return false;
        }
        return userEntity.getRole().equals(userRole);
    }

    public UserEntity getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if ( authentication != null ) {
            Object principal = authentication.getPrincipal();
            if ( principal instanceof UserPrincipal) {
                return ( ( UserPrincipal ) principal ).getUserEntity();
            }
        }
        return null;
    }
}
