package io.github.trubitsyn.motivator.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class SqliteTaskRepository implements Repository<Task> {
    private final SQLiteDatabase readDb;
    private final SQLiteDatabase writeDb;

    public SqliteTaskRepository(TaskDbHelper dbHelper) {
        this.readDb = dbHelper.getReadableDatabase();
        this.writeDb = dbHelper.getWritableDatabase();
    }

    @Override
    public Single<Long> insert(final Task element) {
        return Single.create(new SingleOnSubscribe<Long>() {
            @Override
            public void subscribe(SingleEmitter<Long> e) throws Exception {
                ContentValues values = new ContentValues();
                values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE, element.getTitle());
                values.put(TaskContract.TaskEntry.COLUMN_NAME_START_TIME_MILLIS, element.getStartTimeMillis());
                values.put(TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY, element.getFrequency());
                values.put(TaskContract.TaskEntry.COLUMN_NAME_ACTIVE, element.isActive());
                long id = writeDb.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
                e.onSuccess(id);
            }
        });
    }

    @Override
    public Observable<Void> update(final Task updatedElement) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                ContentValues values = new ContentValues();
                values.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE, updatedElement.getTitle());
                values.put(TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY, updatedElement.getFrequency());
                values.put(TaskContract.TaskEntry.COLUMN_NAME_ACTIVE, updatedElement.isActive());

                String selection = TaskContract.TaskEntry._ID + "=?";
                String[] selectionArgs = new String[] {String.valueOf(updatedElement.getId())};

                writeDb.update(TaskContract.TaskEntry.TABLE_NAME, values, selection, selectionArgs);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Void> delete(final Task element) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                String whereClause = TaskContract.TaskEntry._ID + "=?";
                String[] whereArgs = new String[] { String.valueOf(element.getId())};
                writeDb.delete(TaskContract.TaskEntry.TABLE_NAME, whereClause, whereArgs);
                e.onComplete();
            }
        });
    }

    @Override
    public Single<List<Task>> query() {
        return Single.create(new SingleOnSubscribe<List<Task>>() {
            @Override
            public void subscribe(SingleEmitter<List<Task>> e) throws Exception {

                String[] projection = {
                        TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.COLUMN_NAME_TITLE,
                        TaskContract.TaskEntry.COLUMN_NAME_START_TIME_MILLIS,
                        TaskContract.TaskEntry.COLUMN_NAME_FREQUENCY,
                        TaskContract.TaskEntry.COLUMN_NAME_ACTIVE
                };

                Cursor cursor = readDb.query(TaskContract.TaskEntry.TABLE_NAME, projection, null, null, null, null, null);

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
                e.onSuccess(tasks);
            }
        });
    }

    @Override
    public void destroy() {
        readDb.close();
        writeDb.close();
    }
}
