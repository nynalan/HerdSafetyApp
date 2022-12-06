package com.example.herdsafety.AppObjects;

import com.example.herdsafety.SimilarityStrategy;
import com.example.herdsafety.WarningSimilarity;

public class WarningAlert extends AAlert{

    public SimilarityStrategy sim_algorithm = new WarningSimilarity();

    public WarningAlert(int newAlertId, String description) {
        super(newAlertId, description, "Warning");
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
