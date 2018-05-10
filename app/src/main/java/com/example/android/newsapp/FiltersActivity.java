package com.example.android.newsapp;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.preference.ListPreference;

public class FiltersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters_activity);

    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements android.preference.Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.filter_main);

            android.preference.Preference toDate = findPreference("to-date");
            bindPreferenceSummaryToValue(toDate);

            android.preference.Preference fromDate = findPreference("from-date");
            bindPreferenceSummaryToValue(fromDate);

            android.preference.Preference orderBy = findPreference("order-by");
            bindPreferenceSummaryToValue(orderBy);

            android.preference.Preference pageSize = findPreference("page-size");
            bindPreferenceSummaryToValue(pageSize);
        }

        @Override
        public boolean onPreferenceChange(android.preference.Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof android.preference.ListPreference) {
                ListPreference listPreference = (android.preference.ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue+"");
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(android.preference.Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }

}