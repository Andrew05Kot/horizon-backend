package com.kot.horizon.security;

public enum SocialTypes {

    FACEBOOK("facebook"),
    GOOGLE("google");

    public String value;

    SocialTypes(String socialType) {
        this.value = socialType;
    }
}
