package io.github.trubitsyn.motivator.notification;

import java.util.concurrent.TimeUnit;

import io.github.trubitsyn.motivator.model.Frequency;

public class FrequencyConverter {

    public static long toMinutes(Frequency frequency) {
        switch (frequency) {
            case VERY_OFTEN:
                return 30;
            case OFTEN:
                return 60;
            case TIME_AFTER_TIME:
                return 90;
            case RARELY:
                return 120;
            case VERY_RARELY:
                return 150;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static long toMilliseconds(Frequency frequency) {
        long minutes = toMinutes(frequency);
        return TimeUnit.MINUTES.toMillis(minutes);
    }
}
