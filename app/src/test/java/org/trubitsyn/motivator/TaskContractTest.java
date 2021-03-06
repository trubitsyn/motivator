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
import org.trubitsyn.motivator.model.TaskContract;

import static junit.framework.Assert.assertEquals;

public class TaskContractTest {

    @Test
    public void tableName() {
        assertEquals("task", TaskContract.TaskEntry.TABLE_NAME);
    }

    @Test
    public void titleColumnName() {
        assertEquals("title", TaskContract.TaskEntry.COLUMN_NAME_TITLE);
    }

    @Test
    public void startTimeColumnName() {
        assertEquals("startTimeMillis", TaskContract.TaskEntry.COLUMN_NAME_START_TIME_MILLIS);
    }

    @Test
    public void frequencyColumnName() {
        assertEquals("frequency", TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY);
    }
}
