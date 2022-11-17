package com.example.herdsafety.AppObjects;

public class CautionAlert extends AAlert {

    public CautionAlert(int newAlertId, String description) {
        super(newAlertId, description, "Caution");
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
