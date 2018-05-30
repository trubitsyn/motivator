/*
 * Copyright (C) 2016 Nikola Trubitsyn
 *
 * This file is part of Motivator.
 *
 * Motivator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Motivator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Motivator.  If not, see <https://www.gnu.org/licenses/>.
 */

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
