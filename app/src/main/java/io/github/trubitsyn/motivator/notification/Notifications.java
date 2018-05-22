package io.github.trubitsyn.motivator.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import io.github.trubitsyn.motivator.R;
import io.github.trubitsyn.motivator.model.Frequency;
import io.github.trubitsyn.motivator.model.Task;

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
