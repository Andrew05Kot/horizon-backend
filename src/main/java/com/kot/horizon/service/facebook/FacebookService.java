package com.kot.horizon.service.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.service.imageconverter.ImagesConverterService;

@Service
public class FacebookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookService.class);

    @Autowired
    private ImagesConverterService imagesConverterService;

    public byte[] getUserPicture(String accessToken) {
        FacebookClient client = createClient(accessToken);
        JsonObject userJson = getJsonObject(client, FacebookField.CUSTOM_BIG_PICTURE);
        try {
            return imagesConverterService.recoverImageFromUrl(userJson
                    .get(FacebookField.PICTURE.getField()).asObject()
                    .get(FacebookField.DATA.getField()).asObject()
                    .get(FacebookField.URL.getField()).asString());
        } catch (Exception e) {
            LOGGER.error("Failed to get photo from facebook ", e);
        }
        return new byte[0];
    }

    FacebookClient createClient(String accessToken) {
        return new DefaultFacebookClient(accessToken, Version.LATEST);
    }

    JsonObject getJsonObject(FacebookClient client, FacebookField field) {
        return client.fetchObject("me", JsonObject.class, Parameter.with("fields", field.getField()));
    }


}
