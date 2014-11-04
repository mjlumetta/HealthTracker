package edu.swarthmore.cs.lab4.healthtracker;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;

/**
 * Created by mlumett1 on 10/21/14.
 */
public class HealthEventListFragment extends ListFragment {
    private ArrayList<HealthEvent> mHealthEvents;
    private static final String TAG = "HealthEventListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.health_events_header);
        mHealthEvents = HealthEventStore.get(getActivity()).getHealthEvents();

        HealthEventAdapter adapter = new HealthEventAdapter(mHealthEvents);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HealthEventAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        HealthEventAdapter la = (HealthEventAdapter) getListAdapter();
        HealthEvent he = (HealthEvent)la.getItem(position);
        Log.d(TAG, he.getTitle() + " was clicked");

        Intent i = new Intent(getActivity(), HealthActivity.class);
        i.putExtra(HealthFragment.EXTRA_HEALTHEVENT_ID, he.getId());
        startActivity(i);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_new_event) {
            HealthEvent he = new HealthEvent();
            HealthEventStore.get(getActivity()).addHealthEvent(he);
            Intent i = new Intent(getActivity(), HealthActivity.class);
            i.putExtra(HealthFragment.EXTRA_HEALTHEVENT_ID, he.getId());
            startActivity(i);
            return true;
        }
        else {
            Log.d(TAG, "Item id is " + String.valueOf(item.getItemId()));
            Log.d(TAG, "Correct id is " + String.valueOf(R.id.menu_item_new_event));
            return super.onOptionsItemSelected(item);
        }
    }

    private class HealthEventAdapter extends ArrayAdapter<HealthEvent> {
        public HealthEventAdapter(ArrayList<HealthEvent> healthEvents) {
            super(getActivity(), 0, healthEvents);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_health_event, null);
            }

            // configure the view for this HealthEvent
            HealthEvent he = getItem(position);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.health_event_list_titleTextView);
            titleTextView.setText(he.getTitle());
            TextView dateTextView =
                    (TextView)convertView.findViewById(R.id.health_event_list_dateTextView);
            dateTextView.setText(he.getDate().toString());
            CheckBox solvedCheckBox =
                    (CheckBox)convertView.findViewById(R.id.health_event_list_item_treatedCheckBox);
            solvedCheckBox.setChecked(he.isTreated());

            return convertView;
        }
    }
}
