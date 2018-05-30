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

package io.github.trubitsyn.motivator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.github.trubitsyn.motivator.model.Repository;
import io.github.trubitsyn.motivator.model.SqliteTaskRepository;
import io.github.trubitsyn.motivator.model.Task;
import io.github.trubitsyn.motivator.presenter.MainPresenter;
import io.github.trubitsyn.motivator.util.MockModelFabric;
import io.github.trubitsyn.motivator.view.MainView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricTestRunner.class)
public class MainPresenterTest {
    private MainPresenter presenter;
    private MainView view;
    private Repository<Task> repository;

    @Before
    public void setUp() throws Exception {
        Motivator app = (Motivator) RuntimeEnvironment.application;
        repository = mock(SqliteTaskRepository.class);
        app.setTaskRepository(repository);
        app.setDefaultScheduler(Schedulers.trampoline());

        presenter = new MainPresenter();
        view = mock(MainView.class);
        when(view.getContext()).thenReturn(app);
        presenter.attachView(view);
    }

    @After
    public void tearDown() {
        presenter.detachView();
    }

    @Test
    public void loadTasksShowsTasks() {
        List<Task> tasks = MockModelFabric.getListOfTasks();
        when(repository.query()).thenReturn(Single.just(tasks));
        presenter.loadTasks();
        verify(view).showTasks(tasks);
    }

    @Test
    public void loadTasksShowsEmptyState() {
        List<Task> tasks = new ArrayList<>();
        when(repository.query()).thenReturn(Single.just(tasks));
        presenter.loadTasks();
        verify(view).showEmptyState();
    }

    @Test
    public void onNewTaskReceivedInsertsTaskToRepository() {
        Task task = MockModelFabric.getTask();
        when(repository.insert(task)).thenReturn(Single.just(1000L));
        presenter.onNewTaskReceived(task);
        verify(repository).insert(task);
    }

    @Test
    public void onNewTaskSetsTaskId() {
        Task task = mock(Task.class);
        when(repository.insert(task)).thenReturn(Single.just(1000L));
        presenter.onNewTaskReceived(task);
        verify(task).setId(1000L);
    }

    @Test
    public void onTaskEditedUpdatesTaskRepositoryEntry() {
        Task task = MockModelFabric.getTask();
        when(repository.update(task)).thenReturn(Observable.<Void>never());
        presenter.onTaskEdited(task);
        verify(repository).update(task);
    }

    @Test
    public void onDeleteButtonClickedDeletesTaskRepositoryEntry() {
        Task task = MockModelFabric.getTask();
        when(repository.delete(task)).thenReturn(Observable.<Void>never());
        presenter.onDeleteButtonClicked(task);
        verify(repository).delete(task);
    }
}
