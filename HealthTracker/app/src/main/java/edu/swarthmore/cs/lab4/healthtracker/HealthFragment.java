package edu.swarthmore.cs.lab4.healthtracker;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by jwas on 10/1/14.
 */
public class HealthFragment extends Fragment {
    private HealthEvent mHealthEvent;
    private EditText mTitleField;
    private static final String TAG = "HealthFragment";
    private Button mDateButton;
    private CheckBox mCheckBox;

    public static final String EXTRA_HEALTHEVENT_ID =
            "edu.swarthmore.cs.lab4.healthtracker.healthevent_id";

    public static HealthFragment newInstance(UUID healthID) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_HEALTHEVENT_ID, healthID);

        HealthFragment fragment = new HealthFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID healthEventId = (UUID) getArguments().getSerializable(EXTRA_HEALTHEVENT_ID);
        HealthEventStore store = HealthEventStore.get(getActivity());
        mHealthEvent = store.getHealthEvent(healthEventId);
        Log.d(TAG, "Got health event " + mHealthEvent.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_health, parent, false);
        mTitleField = (EditText) v.findViewById(R.id.health_title);
        mTitleField.setText(mHealthEvent.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space is intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String msg = s.toString();
                Log.d(TAG, msg);
                mHealthEvent.setTitle(msg);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This space is intentionally left blank
            }
        });

        mDateButton = (Button) v.findViewById(R.id.date_button);
        mDateButton.setText(mHealthEvent.getDate().toString());
        mDateButton.setEnabled(false);

        mCheckBox = (CheckBox) v.findViewById(R.id.treated_checkbox);
        mCheckBox.setChecked(mHealthEvent.isTreated());
        mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mHealthEvent.setTreated(isChecked);
                if (isChecked) {
                    Log.d(TAG, "Checkbox checked");
                }
                else {
                    Log.d(TAG, "Checkbox unchecked");
                }
            }
        });


        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        HealthEventStore.get(getActivity()).saveHealthEvents();
    }
}
