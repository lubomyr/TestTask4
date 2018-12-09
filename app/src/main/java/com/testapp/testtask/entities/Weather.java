package com.testapp.testtask.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Weather {
    @Id
    private String id;

    @Expose
    @SerializedName("city_name")
    private String cityName;

    @Expose
    @SerializedName("temp")
    private Double temp;

    @Expose
    @SerializedName("ob_time")
    private String obTime;

    @Expose
    @SerializedName("weather")
    @Transient
    private Addition addition;

    private String description;

    private Double latitude;

    private Double longitude;

    @Generated(hash = 499010581)
    public Weather(String id, String cityName, Double temp, String obTime,
            String description, Double latitude, Double longitude) {
        this.id = id;
        this.cityName = cityName;
        this.temp = temp;
        this.obTime = obTime;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Generated(hash = 556711069)
    public Weather() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getTemp() {
        return this.temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getObTime() {
        return this.obTime;
    }

    public void setObTime(String obTime) {
        this.obTime = obTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Addition getAddition() {
        return this.addition;
    }
}
