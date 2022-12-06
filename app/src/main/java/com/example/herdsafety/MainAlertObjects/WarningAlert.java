package com.example.herdsafety.MainAlertObjects;

import com.example.herdsafety.Similarity.WarningSimilarity;

public class WarningAlert extends AAlert{

    public WarningAlert(int newAlertId, String description) {
        super(newAlertId, description, "Warning");
        sim_algorithm = new WarningSimilarity();
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
