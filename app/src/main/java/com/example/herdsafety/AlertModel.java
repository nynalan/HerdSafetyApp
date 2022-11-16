package com.example.herdsafety;

public class AlertModel {
    private int id;
    private String description;

    // Constructors.
    public AlertModel(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public AlertModel () {}

    @Override
    public String toString() {
        return "AlertModel{" +
                "id=" + id +
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
