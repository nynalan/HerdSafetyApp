package com.example.herdsafety;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.herdsafety.Database.DBHandler;
import com.example.herdsafety.MainAlertObjects.AAlert;
import com.example.herdsafety.MainAlertObjects.CautionAlert;
import com.example.herdsafety.MainAlertObjects.CrimeAlert;
import com.example.herdsafety.MainAlertObjects.WarningAlert;

import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DBHandlerTest {

    private DBHandler dbHandler;

    @Before
    //Sets up the database handler
    public void createDBHandler(){
        dbHandler = new DBHandler(ApplicationProvider.getApplicationContext());
    }

    @Test
    //Test to see if an alert can be added to the database
    public void TestDBHandlerInsert(){
        CautionAlert cautionAlert = new CautionAlert(0, "Testing Alert",(float)40.00895,(float)-105.26798);
        dbHandler.addNewAlert(cautionAlert);
        List<AAlert> testingAlertList = dbHandler.retrieveNearbyAlerts();
        Boolean testSuccess = false;
        for (AAlert alert:testingAlertList) {
            if(alert.getDescription().equals("Testing Alert")){
                testSuccess = true;
            }
        }
        assertTrue(testSuccess);
    }

    @Test
    @After
    //Test to see if an alert can be deleted from the database
    public void TestDBHandlerDelete(){
        dbHandler.deleteOneAlert("Testing Alert");
        List<AAlert> testingAlertList = dbHandler.retrieveNearbyAlerts();
        Boolean testSuccess = true;
        for (AAlert alert:testingAlertList) {
            if(alert.getDescription().equals("Testing Alert")){
                testSuccess = false;
            }
        }
        assertTrue(testSuccess);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.herdsafety", appContext.getPackageName());
    }
}