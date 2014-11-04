package edu.swarthmore.cs.lab4.healthtracker;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by mlumett1 on 10/21/14.
 */
public class HealthEventStore {
    private static HealthEventStore sEventStore;
    private Context mAppContext;
    private ArrayList<HealthEvent> mHealthEventList;

    private HealthEventStore(Context appContext) {
        mAppContext = appContext;
        mHealthEventList = new ArrayList<HealthEvent>();
    }

    public static HealthEventStore get(Context c) {
        if (sEventStore == null) {
            sEventStore = new HealthEventStore(c.getApplicationContext());
        }
        return sEventStore;
    }

    public ArrayList<HealthEvent> getHealthEvents() {
        return mHealthEventList;
    }

    public HealthEvent getHealthEvent(UUID id) {
        for (HealthEvent event : mHealthEventList) {
            if (event.getId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    public void addHealthEvent(HealthEvent he) {
        mHealthEventList.add(he);
    }
}
