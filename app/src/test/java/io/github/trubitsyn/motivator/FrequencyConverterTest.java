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
