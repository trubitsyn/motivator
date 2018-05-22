package io.github.trubitsyn.motivator.util;

import java.util.ArrayList;
import java.util.List;

import io.github.trubitsyn.motivator.model.Frequency;
import io.github.trubitsyn.motivator.model.Task;

public class MockModelFabric {

    public static Task getTask() {
        return new Task(0, "Task", System.currentTimeMillis(), Frequency.VERY_OFTEN, true);
    }

    public static List<Task> getListOfTasks() {
        List<Task> tasks = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            tasks.add(new Task(i, "Task " + i, System.currentTimeMillis(), Frequency.VERY_OFTEN, true));
        }
        return tasks;
    }
}
