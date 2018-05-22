package io.github.trubitsyn.motivator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.github.trubitsyn.motivator.model.Frequency;
import io.github.trubitsyn.motivator.model.SqliteTaskRepository;
import io.github.trubitsyn.motivator.model.Task;
import io.github.trubitsyn.motivator.model.TaskContract;
import io.github.trubitsyn.motivator.model.TaskDbHelper;
import io.github.trubitsyn.motivator.util.MockModelFabric;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SqliteTaskRepositoryTest {
    private TaskDbHelper dbHelper;
    private SqliteTaskRepository repository;

    @Before
    public void setUp() {
        dbHelper = new TaskDbHelper(RuntimeEnvironment.application);
        repository = new SqliteTaskRepository(dbHelper);
    }

    @After
    public void tearDown() {
        dbHelper.close();
    }

    @Test
    public void insert() {
        Task task = MockModelFabric.getTask();
        repository.insert(task).blockingGet();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE_NAME, new String[] {TaskContract.TaskEntry.COLUMN_NAME_TITLE, TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY, TaskContract.TaskEntry.COLUMN_NAME_ACTIVE}, null, null, null, null, null);
        cursor.moveToFirst();
        String title = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_TITLE));
        int frequency = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY));
        boolean active = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_ACTIVE)) == 1;
        cursor.close();

        assertEquals(task.getTitle(), title);
        assertEquals(task.getFrequency(), frequency);
        assertEquals(task.isActive(), active);
    }

    @Test
    public void update() {
        Task updatedElement = MockModelFabric.getTask();
        long id = repository.insert(updatedElement).blockingGet();
        updatedElement.setId(id);
        updatedElement.setTitle("New title");
        updatedElement.setFrequency(Frequency.RARELY.ordinal());
        updatedElement.setActive(false);
        repository.update(updatedElement).blockingSubscribe();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE_NAME, new String[] {TaskContract.TaskEntry.COLUMN_NAME_TITLE, TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY, TaskContract.TaskEntry.COLUMN_NAME_ACTIVE}, null, null, null, null, null);
        cursor.moveToFirst();
        String title = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_TITLE));
        int frequency = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY));
        boolean active = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_ACTIVE)) == 1;
        cursor.close();

        assertEquals(updatedElement.getTitle(), title);
        assertEquals(updatedElement.getFrequency(), frequency);
        assertEquals(updatedElement.isActive(), active);
    }

    @Test
    public void delete() {
        Task task = MockModelFabric.getTask();
        long id = repository.insert(task).blockingGet();
        task.setId(id);
        repository.delete(task).blockingSubscribe();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE_NAME, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        assertEquals(0, count);
    }

    @Test
    public void query() {
        Task task = MockModelFabric.getTask();
        repository.insert(task).blockingGet();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE_NAME, new String[] {TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COLUMN_NAME_TITLE, TaskContract.TaskEntry.COLUMN_NAME_START_TIME_MILLIS, TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY, TaskContract.TaskEntry.COLUMN_NAME_ACTIVE}, null, null, null, null, null);

        List<Task> tasks = new ArrayList<>();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_TITLE));
            long startTimeMillis = cursor.getLong(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_START_TIME_MILLIS));
            int frequency = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY));
            boolean active = cursor.getInt(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_ACTIVE)) == 1;
            tasks.add(new Task(id, title, startTimeMillis, frequency, active));
        }

        cursor.close();

        List<Task> queried = repository.query().blockingGet();
        assertEquals(tasks, queried);
    }
}
