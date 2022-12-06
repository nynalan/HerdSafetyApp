package com.example.herdsafety.MainAlertObjects;

import com.example.herdsafety.Similarity.CautionSimilarity;

public class CautionAlert extends AAlert {


    public CautionAlert(int newAlertId, String description) {
        super(newAlertId, description, "Caution");
        sim_algorithm = new CautionSimilarity();
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
