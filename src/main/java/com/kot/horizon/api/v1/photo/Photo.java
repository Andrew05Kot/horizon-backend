package com.kot.horizon.api.v1.photo;

import io.swagger.annotations.ApiModelProperty;

public class Photo {

    @ApiModelProperty(notes = "The identification unique number of photo", example = "1")
    private Long id;

    @ApiModelProperty(notes = "The mime type of photo", example = "image/jpeg")
    private String mimeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
