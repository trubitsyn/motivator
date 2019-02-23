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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.trubitsyn.motivator.Motivator;

import java.util.List;

import org.trubitsyn.motivator.model.Repository;
import org.trubitsyn.motivator.model.Task;

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