package io.github.trubitsyn.motivator;

import org.junit.Test;

import io.github.trubitsyn.motivator.model.TaskContract;

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
