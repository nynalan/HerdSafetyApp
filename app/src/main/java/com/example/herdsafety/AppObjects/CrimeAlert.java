package com.example.herdsafety.AppObjects;

import com.example.herdsafety.CrimeSimilarity;
import com.example.herdsafety.SimilarityStrategy;

public class CrimeAlert extends AAlert {

    public SimilarityStrategy sim_algorithm = new CrimeSimilarity();

    public CrimeAlert(int newAlertId, String description) {
        super(newAlertId, description, "Crime");
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
