package com.zlab.noizer.app;

import android.app.Application;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.widget.PopupMenu;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_global);

        final Preference themes = findPreference("theme_switch");
        themes.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                Toast.makeText(getApplicationContext(), R.string.restart_needed, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }
}
