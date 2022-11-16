package com.example.herdsafety.AppObjects;

public class WarningAlert extends AAlert{

    public WarningAlert(int newAlertId, String description) {
        super(newAlertId, description);
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
