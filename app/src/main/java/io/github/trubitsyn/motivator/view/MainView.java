package io.github.trubitsyn.motivator.view;

import java.util.List;

import io.github.trubitsyn.motivator.model.Task;

public interface MainView extends View {
    void showTasks(List<Task> tasks);
    void showEmptyState();
}
