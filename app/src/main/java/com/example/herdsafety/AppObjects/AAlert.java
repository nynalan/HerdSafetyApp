package com.example.herdsafety.AppObjects;

import java.util.List;

public abstract class AAlert {
    public static List<AAlert> alertList;

    private int id;
    private String description;
    private String type;

    // Constructors.
    public AAlert(int id, String description, String type) {
        this.id = id;
        this.description = description;
        this.type = type;
    }

    public AAlert() {}

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

    public String getType() {
        return this.type;
    }

    /**
     * Gets the formatted displace, deploys strategy pattern
     * TODO: should this be a different type than string, like a listViewObject?
     * @return String
     */
    public abstract String getFormattedDisplay();
}
