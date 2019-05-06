package com.zlab.noizer.app;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_global);

        final Preference themes = findPreference("theme_switch");
        themes.setOnPreferenceChangeListener((preference, o) -> {
            Toast.makeText(getApplicationContext(), R.string.restart_needed, Toast.LENGTH_LONG).show();
            MainActivity.needRestart = true;
            return true;
        });
    }
}
