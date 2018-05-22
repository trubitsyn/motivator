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
