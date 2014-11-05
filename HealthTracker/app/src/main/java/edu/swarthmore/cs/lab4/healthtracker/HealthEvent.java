package edu.swarthmore.cs.lab4.healthtracker;

import org.json.JSONObject;

import java.util.UUID;
import java.util.Date;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jwas on 10/1/14.
 */
public class HealthEvent {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mTreated;

    private final String JSON_ID = "id";
    private final String JSON_TITLE = "title";
    private final String JSON_DATE = "date";
    private final String JSON_TREATED = "treated";

    private static final String TAG = "HealthEvent";

    public HealthEvent() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public HealthEvent(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.getString(JSON_TITLE);
        mTreated = json.getBoolean(JSON_TREATED);
        mDate = new Date(json.getLong(JSON_DATE));
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isTreated() {
        return mTreated;
    }

    public void setTreated(boolean treated) {
        mTreated = treated;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_TREATED, mTreated);
        json.put(JSON_DATE, mDate.getTime());
        return json;
    }
}
