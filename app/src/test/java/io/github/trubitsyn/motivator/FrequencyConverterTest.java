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

package io.github.trubitsyn.motivator;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.github.trubitsyn.motivator.model.Frequency;
import io.github.trubitsyn.motivator.notification.FrequencyConverter;

import static junit.framework.Assert.assertEquals;

public class FrequencyConverterTest {

    @Test
    public void veryOften() {
        assertEquals(30, FrequencyConverter.toMinutes(Frequency.VERY_OFTEN));
    }

    @Test
    public void often() {
        assertEquals(60, FrequencyConverter.toMinutes(Frequency.OFTEN));
    }

    @Test
    public void timeAfterTime() {
        assertEquals(90, FrequencyConverter.toMinutes(Frequency.TIME_AFTER_TIME));
    }

    @Test
    public void rarely() {
        assertEquals(120, FrequencyConverter.toMinutes(Frequency.RARELY));
    }

    @Test
    public void veryRarely() {
        assertEquals(150, FrequencyConverter.toMinutes(Frequency.VERY_RARELY));
    }

    @Test
    public void toMilliseconds() {
        assertEquals(TimeUnit.MINUTES.toMillis(FrequencyConverter.toMinutes(Frequency.VERY_OFTEN)), FrequencyConverter.toMilliseconds(Frequency.VERY_OFTEN));
    }
}
