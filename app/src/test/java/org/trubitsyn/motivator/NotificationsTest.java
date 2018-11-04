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

package org.trubitsyn.motivator;

import android.app.AlarmManager;
import android.content.Context;

import org.trubitsyn.motivator.util.MockModelFabric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlarmManager;
import org.robolectric.shadows.ShadowPendingIntent;

import org.trubitsyn.motivator.model.Frequency;
import org.trubitsyn.motivator.model.Task;
import org.trubitsyn.motivator.notification.FrequencyConverter;
import org.trubitsyn.motivator.notification.NotificationAlarmReceiver;
import org.trubitsyn.motivator.notification.Notifications;

import org.trubitsyn.motivator.BuildConfig;

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
