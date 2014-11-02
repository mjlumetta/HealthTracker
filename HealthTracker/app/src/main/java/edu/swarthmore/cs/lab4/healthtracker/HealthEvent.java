package edu.swarthmore.cs.lab4.healthtracker;

import java.util.UUID;
import java.util.Date;

/**
 * Created by jwas on 10/1/14.
 */
public class HealthEvent {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mTreated;

    public HealthEvent() {
        mId = UUID.randomUUID();
        mDate = new Date();
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
}
