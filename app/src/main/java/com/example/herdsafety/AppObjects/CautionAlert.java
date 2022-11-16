package com.example.herdsafety.AppObjects;

public class CautionAlert extends AAlert {

    public CautionAlert(int newAlertId, String description) {
        super(newAlertId, description);
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
