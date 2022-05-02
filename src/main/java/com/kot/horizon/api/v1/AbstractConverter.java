package com.kot.horizon.api.v1;

import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import com.kot.horizon.api.v1.general.AbstractResponse;
import com.kot.horizon.model.BaseEntity;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.service.user.CurrentUserService;

public abstract class AbstractConverter<Entity extends BaseEntity,
                                    Response extends AbstractResponse> {

    @Autowired
    protected CurrentUserService currentUserService;

    protected abstract Response getPublicResponse(Entity entity);

    protected abstract Response getOwnerResponse(Entity entity);

    protected abstract Response getAdminResponse(Entity entity);

    protected boolean publicCheck(Entity entity, UserEntity currentUser) {
        return currentUser == null;
    }

    protected abstract boolean ownerCheck(Entity entity, UserEntity currentUser);

    protected boolean adminCheck(UserEntity currentUser) {
        return currentUser != null && currentUserService.isAdministrator(currentUser);
    }

    public Response getResponseBean(Entity entity, List< String > entitiesToExpand) {

        UserEntity currentUser = currentUserService.getCurrentUser();

        if ( adminCheck(currentUser) ){
            return getResponseBean(entity, this::getAdminResponse, entitiesToExpand);
        }

        if ( publicCheck(entity, currentUser) ){
            return getResponseBean(entity, this::getPublicResponse, entitiesToExpand);
        }

        if ( ownerCheck(entity, currentUser) ){
            return getResponseBean(entity, this::getOwnerResponse, entitiesToExpand);
        }

        return null;
    }

    protected Response getResponseBean(Entity entity, Function<Entity,
            Response> responseFunction, List<String> entitiesToExpand){
        Response response = responseFunction.apply(entity);
        expandResponse(response, entity, entitiesToExpand);
        return response;
    }

    protected abstract void expandResponse(Response response, Entity entity, List<String> entitiesToExpand);

}
