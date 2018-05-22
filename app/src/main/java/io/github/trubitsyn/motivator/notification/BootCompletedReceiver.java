package io.github.trubitsyn.motivator.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import io.github.trubitsyn.motivator.Motivator;
import io.github.trubitsyn.motivator.model.Repository;
import io.github.trubitsyn.motivator.model.Task;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Repository<Task> repository = Motivator.get(context).getTaskRepository();

            List<Task> tasks = repository.query().blockingGet();

            for (Task task : tasks) {
                if (task.isActive()) {
                    Notifications.scheduleRepeating(context, task);
                }
            }
        }
    }
}
