package com.testapp.testtask.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Addition {
    @Expose
    @SerializedName("icon")
    private String icon;

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("description")
    private String description;

    public Addition(String icon, String code, String description) {
        this.icon = icon;
        this.code = code;
        this.description = description;
    }

    public Addition() {
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
