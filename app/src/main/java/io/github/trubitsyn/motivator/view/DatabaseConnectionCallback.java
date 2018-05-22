package io.github.trubitsyn.motivator.view;

import io.github.trubitsyn.motivator.model.Task;

public interface DatabaseConnectionCallback {
    void removeItem(Task task);
}
