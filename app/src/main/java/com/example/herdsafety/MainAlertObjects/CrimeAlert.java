package com.example.herdsafety.MainAlertObjects;

import com.example.herdsafety.Similarity.CrimeSimilarity;

public class CrimeAlert extends AAlert {

    public CrimeAlert(int newAlertId, String description) {
        super(newAlertId, description, "Crime");
        sim_algorithm = new CrimeSimilarity();
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
