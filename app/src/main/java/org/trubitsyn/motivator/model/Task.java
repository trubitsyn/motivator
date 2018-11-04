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

package org.trubitsyn.motivator.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Task implements Parcelable {
    private long id;
    private String title;
    private long startTimeMillis;
    private int frequency;
    private boolean active;

    public Task() {}

    public Task(String title, long startTimeMillis, int frequency, boolean active) {
        this.title = title;
        this.startTimeMillis = startTimeMillis;
        this.frequency = frequency;
        this.active = active;
    }

    public Task(long id, String title, long startTimeMillis, int frequency, boolean active) {
        this.id = id;
        this.title = title;
        this.startTimeMillis = startTimeMillis;
        this.frequency = frequency;
        this.active = active;
    }

    public Task(long id, String title, long startTimeMillis, Frequency frequency, boolean active) {
        this.id = id;
        this.title = title;
        this.startTimeMillis = startTimeMillis;
        this.frequency = frequency.ordinal();
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int daysActive() {
        return (int) TimeUnit.MILLISECONDS.toDays(new Date().getTime() - startTimeMillis);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeLong(this.startTimeMillis);
        dest.writeInt(this.frequency);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
    }

    protected Task(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.startTimeMillis = in.readLong();
        this.frequency = in.readInt();
        this.active = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                startTimeMillis == task.startTimeMillis &&
                frequency == task.frequency &&
                active == task.active &&
                Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, startTimeMillis, frequency, active);
    }
}