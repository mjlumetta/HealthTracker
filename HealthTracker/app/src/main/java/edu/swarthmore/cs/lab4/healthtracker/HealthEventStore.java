package edu.swarthmore.cs.lab4.healthtracker;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;
import android.util.Log;

/**
 * Created by mlumett1 on 10/21/14.
 */
public class HealthEventStore {
    private static HealthEventStore sEventStore;
    private Context mAppContext;
    private ArrayList<HealthEvent> mHealthEventList;
    private HealthEventIntentJSONSerializer mSerializer;

    private static final String JSON_FILENAME = "healthEvents.json";
    private static final String TAG = "HealthEventStore";

    private HealthEventStore(Context appContext) {
        mAppContext = appContext;
        mSerializer = new HealthEventIntentJSONSerializer(mAppContext, JSON_FILENAME);
        try {
            mHealthEventList = mSerializer.loadHealthEvents();
        } catch (Exception e) {
            mHealthEventList = new ArrayList<HealthEvent>();
            Log.e(TAG, "Error loading Health Events: ", e);
        }
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

    public boolean saveHealthEvents() {
        try {
            mSerializer.saveHealthEvents(mHealthEventList);
            Log.d(TAG, "Health events saved to file.");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error saving Health Events: ", e);
            return false;
        }
    }
}
