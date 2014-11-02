package edu.swarthmore.cs.lab4.healthtracker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import java.util.UUID;


public class HealthActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            UUID healthEventId = (UUID) getIntent().getSerializableExtra(HealthFragment.EXTRA_HEALTHEVENT_ID);
            fragment = HealthFragment.newInstance(healthEventId);
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }

}
