package io.github.trubitsyn.motivator;

import org.junit.Test;

import io.github.trubitsyn.motivator.model.Frequency;
import io.github.trubitsyn.motivator.model.Task;

import static org.junit.Assert.assertEquals;

public class TaskTest {
    private static final long ID = 1024;
    private static final String TITLE = "Example task";
    private static final long START_TIME_MILLIS = 5000;
    private static final int FREQUENCY = Frequency.VERY_OFTEN.ordinal();

    @Test
    public void getId() {
        Task task = new Task(ID, TITLE, START_TIME_MILLIS, FREQUENCY, true);
        assertEquals(ID, task.getId());
    }

    @Test
    public void setId() {
        Task task = new Task(ID, TITLE, START_TIME_MILLIS, FREQUENCY, true);
        final long NEW_ID = 2048;
        task.setId(NEW_ID);
        assertEquals(NEW_ID, task.getId());
    }

    @Test
    public void getTitle() {
        Task task = new Task(ID, TITLE, START_TIME_MILLIS, FREQUENCY, true);
        assertEquals(TITLE, task.getTitle());
    }

    @Test
    public void setTitle() {
        Task task = new Task(ID, TITLE, START_TIME_MILLIS, FREQUENCY, true);
        final String NEW_TITLE = "Another example task";
        task.setTitle(NEW_TITLE);
        assertEquals(NEW_TITLE, task.getTitle());
    }

    @Test
    public void getStartTimeMillis() {
        Task task = new Task(ID, TITLE, START_TIME_MILLIS, FREQUENCY, true);
        assertEquals(START_TIME_MILLIS, task.getStartTimeMillis());
    }

    @Test
    public void setStartTimeMillis() {
        Task task = new Task(ID, TITLE, START_TIME_MILLIS, FREQUENCY, true);
        final long NEW_START_TIME_MILLIS = 16384;
        task.setStartTimeMillis(NEW_START_TIME_MILLIS);
        assertEquals(NEW_START_TIME_MILLIS, task.getStartTimeMillis());
    }

    @Test
    public void getFrequency() {
        Task task = new Task(ID, TITLE, START_TIME_MILLIS, FREQUENCY, true);
        assertEquals(FREQUENCY, task.getFrequency());
    }

    @Test
    public void setFrequency() {
        Task task = new Task(ID, TITLE, START_TIME_MILLIS, FREQUENCY, true);
        final int NEW_FREQUENCY = Frequency.OFTEN.ordinal();
        task.setFrequency(NEW_FREQUENCY);
        assertEquals(NEW_FREQUENCY, task.getFrequency());
    }

    @Test
    public void isActive() {
        Task task = new Task(ID, TITLE, START_TIME_MILLIS, FREQUENCY, false);
        assertEquals(false, task.isActive());
    }

    @Test
    public void setActive() {
        Task task = new Task(ID, TITLE, START_TIME_MILLIS, FREQUENCY, false);
        final boolean newActive = true;
        task.setActive(newActive);
        assertEquals(newActive, task.isActive());
    }
}