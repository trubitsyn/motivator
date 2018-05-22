package io.github.trubitsyn.motivator;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static trubitsyn.motivator.notification.TimeManager.shouldIgnoreNow;

public class TimeManagerTest {

    @Test
    public void shouldNotIgnore() {
        assertFalse(shouldIgnoreNow(-1, -1, 10));
        assertFalse(shouldIgnoreNow(0, 0, 10));
    }

    @Test
    public void from_10_to_15() {
        int from = 10;
        int to = 15;

        assertTrue(shouldIgnoreNow(from, to, 10));
        assertTrue(shouldIgnoreNow(from, to, 11));
        assertTrue(shouldIgnoreNow(from, to, 12));
        assertTrue(shouldIgnoreNow(from, to, 13));
        assertTrue(shouldIgnoreNow(from, to, 14));

        assertFalse(shouldIgnoreNow(from, to, 15));
        assertFalse(shouldIgnoreNow(from, to, 9));
        assertFalse(shouldIgnoreNow(from, to, 16));
    }

    @Test
    public void from_22_to_1() {
        int from = 22;
        int to = 1;

        assertTrue(shouldIgnoreNow(from, to, 22));
        assertTrue(shouldIgnoreNow(from, to, 23));
        assertTrue(shouldIgnoreNow(from, to, 0));

        assertFalse(shouldIgnoreNow(from, to, 1));
        assertFalse(shouldIgnoreNow(from, to, 15));
        assertFalse(shouldIgnoreNow(from, to, 21));
    }
}
