package com.example.herdsafety.MainAlertObjects;

import com.example.herdsafety.Similarity.SimilarityStrategy;

import androidx.annotation.NonNull;

import java.util.List;

public abstract class AAlert {
    public static List<AAlert> alertList;

    //Strategy pattern
    public SimilarityStrategy sim_algorithm;
    
    private int id;
    private String title;
    private String description;
    private float latitude;
    private float longitude;
    private int reported_by;
    private int verifications;
    private float radius;
    private String type;
    private float notification_radius;
    private String last_updated;


    // Constructors.
    public AAlert(int id, String description, Float latitude, Float longitude, String type) {
        this.id = id;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return "AlertModel{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description
                + "'}";
    }

    // Getters and setters.
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getReportedBy() {
        return reported_by;
    }

    public void setReportedBy(int reported_by) {
        this.reported_by = reported_by;
    }

    public int getVerifications() {
        return verifications;
    }

    public void setVerifications(int verifications) {
        this.verifications = verifications;
    }

    public float getRadius() { return radius; }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) { this.type = type; }

    public float getNotificationRadius() { return notification_radius; }

    public void setNotificationRadius(float notification_radius) {
        this.notification_radius = notification_radius;
    }

    public String getLastUpdated() {
        return this.last_updated;
    }
    
    public void setLastUpdated(String last_updated) { this.last_updated = last_updated; }

}
