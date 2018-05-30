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
