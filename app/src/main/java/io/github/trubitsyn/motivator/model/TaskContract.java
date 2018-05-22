package io.github.trubitsyn.motivator.model;

import android.provider.BaseColumns;

public final class TaskContract {

    private TaskContract() {}

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_START_TIME_MILLIS = "startTimeMillis";
        public static final String COLUMN_NAME_FREQUENCY = "frequency";
        public static final String COLUMN_NAME_ACTIVE = "active";
    }
}
