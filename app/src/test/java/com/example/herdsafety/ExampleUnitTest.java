package com.example.herdsafety;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.herdsafety.AppObjects.AAlert;
import com.example.herdsafety.AppObjects.AlertFactory;
import com.google.android.gms.maps.model.LatLng;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void dataBaseAdditionCorrectAllOptions() {
//        AAlert testAlert = AlertFactory.singletonFactory
//                .createAlert("descriptionTest", null, "Caution");
//        DBHandler dbHandler = new DBHandler( TODO Find input for here);
//        dbHandler.addNewAlert(testAlert);
    }
}