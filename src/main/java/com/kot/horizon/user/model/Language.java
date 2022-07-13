package com.kot.horizon.user.model;

import java.util.Locale;

public enum Language {

    UK ("uk"),
    EN("en");

    public String langKey;

    Language(String langKey) {
        this.langKey = langKey;
    }

    public Locale getLocale(){
        return new Locale(this.langKey);
    }
}