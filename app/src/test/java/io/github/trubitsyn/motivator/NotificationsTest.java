package io.github.trubitsyn.motivator;

import android.app.AlarmManager;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlarmManager;
import org.robolectric.shadows.ShadowPendingIntent;

import io.github.trubitsyn.motivator.model.Frequency;
import io.github.trubitsyn.motivator.model.Task;
import io.github.trubitsyn.motivator.notification.FrequencyConverter;
import io.github.trubitsyn.motivator.notification.NotificationAlarmReceiver;
import io.github.trubitsyn.motivator.notification.Notifications;
import io.github.trubitsyn.motivator.util.MockModelFabric;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NotificationsTest {
    private Context context;
    private ShadowAlarmManager shadowAlarmManager;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        shadowAlarmManager = shadowOf(alarmManager);
    }

    @Test
    public void scheduleRepeatingTest() {
        assertNull(shadowAlarmManager.getNextScheduledAlarm());
        Task task = MockModelFabric.getTask();
        Notifications.scheduleRepeating(context, task);
        ShadowAlarmManager.ScheduledAlarm alarm = shadowAlarmManager.getNextScheduledAlarm();
        assertEquals(AlarmManager.RTC_WAKEUP, alarm.type);
        assertEquals(FrequencyConverter.toMilliseconds(Frequency.values()[task.getFrequency()]), alarm.interval);

        ShadowPendingIntent pendingIntent = shadowOf(alarm.operation);
        int expectedRequestCode = (int) (NotificationAlarmReceiver.REQUEST_CODE / (task.getId() + 1));
        assertEquals(expectedRequestCode, pendingIntent.getRequestCode());
    }
}
