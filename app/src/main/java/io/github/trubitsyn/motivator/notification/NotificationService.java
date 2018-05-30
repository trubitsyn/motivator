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

import android.app.IntentService;
import android.content.Intent;

public class NotificationService extends IntentService {
    public static final String INTENT_MESSAGE = "message";

    public NotificationService() {
        super("NotificationService");
    }

    public NotificationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String message = intent.getStringExtra(INTENT_MESSAGE);

        if (message != null) {
            Notifications.createNotification(this, message);
        }
    }
}
