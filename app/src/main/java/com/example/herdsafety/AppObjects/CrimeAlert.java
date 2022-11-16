package com.example.herdsafety.AppObjects;

public class CrimeAlert extends AAlert {

    public CrimeAlert(int newAlertId, String description) {
        super(newAlertId, description);
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
