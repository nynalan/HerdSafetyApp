package com.example.herdsafety.AppObjects;

import com.example.herdsafety.CautionSimilarity;
import com.example.herdsafety.SimilarityStrategy;
import com.example.herdsafety.WarningSimilarity;

public class CautionAlert extends AAlert {

    public SimilarityStrategy sim_algorithm = new CautionSimilarity();

    public CautionAlert(int newAlertId, String description) {
        super(newAlertId, description, "Caution");
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
