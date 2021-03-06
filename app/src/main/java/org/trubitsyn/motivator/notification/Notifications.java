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

package org.trubitsyn.motivator.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import org.trubitsyn.motivator.model.Frequency;

import org.trubitsyn.motivator.R;

import org.trubitsyn.motivator.model.Task;

public class Notifications {

    public static void createNotification(Context context, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setPriority(1)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = (int) System.currentTimeMillis();
        manager.notify(id, builder.build());
    }

    public static void scheduleRepeating(Context context, Task task) {
        long interval = FrequencyConverter.toMilliseconds(Frequency.values()[task.getFrequency()]);
        long startTime = System.currentTimeMillis() + interval;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, getPendingIntent(context, task));
    }

    public static void cancelRepeating(Context context, Task task) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context, task));
    }

    private static PendingIntent getPendingIntent(Context context, Task task) {
        Intent intent = new Intent(context, NotificationAlarmReceiver.class);
        intent.putExtra(NotificationAlarmReceiver.INTENT_MESSAGE, task.getTitle());
        return PendingIntent.getBroadcast(context, getRequestCode(task.getId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static int getRequestCode(long id) {
        return (int) (NotificationAlarmReceiver.REQUEST_CODE / (id + 1));
    }
}
