package io.github.trubitsyn.motivator.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;

public class Preferences {
    private static final String INTRO_SHOWN = "INTRO_SHOWN";
    private static final String IGNORE_FROM_HOUR = "IGNORE_FROM_HOUR";
    private static final String IGNORE_TO_HOUR = "IGNORE_TO_HOUR";
    private SharedPreferences preferences;

    public Preferences(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setIntroShown(boolean shown) {
        SharedPreferences.Editor editor = preferences.edit();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        editor.putBoolean(INTRO_SHOWN, shown);
        editor.apply();
    }

    public boolean isIntroShown() {
        return preferences.getBoolean(INTRO_SHOWN, false);
    }

    public void setIgnoreFromHour(int hour) {
        SharedPreferences.Editor editor = preferences.edit();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        editor.putInt(IGNORE_FROM_HOUR, hour);
        editor.apply();
    }

    public int getIgnoreFromHour() {
        return preferences.getInt(IGNORE_FROM_HOUR, -1);
    }

    public void setIgnoreToHour(int hour) {
        SharedPreferences.Editor editor = preferences.edit();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        editor.putInt(IGNORE_TO_HOUR, hour);
        editor.apply();
    }

    public int getIgnoreToHour() {
        return preferences.getInt(IGNORE_TO_HOUR, -1);
    }
}
