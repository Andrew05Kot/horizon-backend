package com.kot.horizon.model.user;

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