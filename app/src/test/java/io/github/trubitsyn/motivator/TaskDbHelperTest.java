package io.github.trubitsyn.motivator;

import org.junit.Test;

import io.github.trubitsyn.motivator.model.TaskContract;
import io.github.trubitsyn.motivator.model.TaskDbHelper;

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
