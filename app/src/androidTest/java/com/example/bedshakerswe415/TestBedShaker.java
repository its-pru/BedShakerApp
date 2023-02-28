package com.example.bedshakerswe415;


import static org.junit.Assert.assertEquals;
import androidx.test.*;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestBedShaker {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);
    public ActivityScenario mainActivity = null;
   @Before
   public void setUp() throws Exception {
       mainActivity = mainActivityActivityTestRule.getScenario();
   }
    @Test
    public void testButton() {
        MainActivity instance = new MainActivity();
        TextView label = instance.findViewById(R.id.lblToggle) ;
        assertEquals("Bed Shaker OFF", label.getText());
        instance.toggle(instance.findViewById(R.id.btnToggle));
        assertEquals("Bed Shaker ON", label.getText());
    }
}

