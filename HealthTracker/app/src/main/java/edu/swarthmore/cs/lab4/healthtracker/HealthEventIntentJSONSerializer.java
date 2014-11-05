/**
 * Created by mike on 11/4/14.
 */
package edu.swarthmore.cs.lab4.healthtracker;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import org.json.JSONTokener;

public class HealthEventIntentJSONSerializer {
    private Context mContext;
    private String mFilename;

    public HealthEventIntentJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public void saveHealthEvents(ArrayList<HealthEvent> healthEvents)
            throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (HealthEvent he : healthEvents) {
            array.put(he.toJSON());
        }

        // Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public ArrayList<HealthEvent> loadHealthEvents() throws IOException, JSONException {
        ArrayList<HealthEvent> healthEvents = new ArrayList<HealthEvent>();
        BufferedReader reader = null;
        try {
            // Open and read the file into StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            // Parse the JSON
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // Build the array from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                healthEvents.add(new HealthEvent(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // We can ignore this one as it can happen when starting with no list
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return healthEvents;
    }
}
