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

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.trubitsyn.motivator.notification.TimeManager.shouldIgnoreNow;

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
