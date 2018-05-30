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

package io.github.trubitsyn.motivator.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.threeten.bp.Clock;
import org.threeten.bp.LocalDateTime;

import io.github.trubitsyn.motivator.Motivator;
import io.github.trubitsyn.motivator.model.Preferences;

public class NotificationAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String INTENT_MESSAGE = "message";

    @Override
    public void onReceive(Context context, Intent intent) {
        Preferences preferences = Motivator.get(context).getPreferences();

        int ignoreFromHour = preferences.getIgnoreFromHour();
        int ignoreToHour = preferences.getIgnoreToHour();

        Clock clock = Clock.systemDefaultZone();
        LocalDateTime dt = LocalDateTime.now(clock);

        int currentHour = dt.getHour();

        if (TimeManager.shouldIgnoreNow(ignoreFromHour, ignoreToHour, currentHour)) {
            return;
        }

        Intent i = new Intent(context, NotificationService.class);
        String message = intent.getStringExtra(INTENT_MESSAGE);

        if (message != null) {
            i.putExtra(NotificationService.INTENT_MESSAGE, message);
            context.startService(i);
        }
    }
}
