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
import org.trubitsyn.motivator.model.TaskDbHelper;

import static junit.framework.Assert.assertEquals;

public class TaskDbHelperTest {

    @Test
    public void name() {
        assertEquals("Task.db", TaskDbHelper.DATABASE_NAME);
    }

    @Test
    public void version() {
        assertEquals(1, TaskDbHelper.DATABASE_VERSION);
    }

    @Test
    public void intialSql() {
        String expected = "CREATE TABLE IF NOT EXISTS " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY," +
                TaskContract.TaskEntry.COLUMN_NAME_START_TIME_MILLIS + " INTEGER," +
                TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY + " INTEGER," +
                TaskContract.TaskEntry.COLUMN_NAME_ACTIVE + " INTEGER," +
                TaskContract.TaskEntry.COLUMN_NAME_TITLE + " TEXT)";

        assertEquals(expected, TaskDbHelper.SQL_CREATE_ENTRIES);
    }
}
