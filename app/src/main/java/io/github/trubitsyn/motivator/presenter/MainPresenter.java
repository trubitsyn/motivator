package io.github.trubitsyn.motivator.presenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.github.trubitsyn.motivator.Motivator;
import io.github.trubitsyn.motivator.model.Repository;
import io.github.trubitsyn.motivator.model.Task;
import io.github.trubitsyn.motivator.notification.Notifications;
import io.github.trubitsyn.motivator.view.MainView;

public class MainPresenter implements Presenter<MainView> {
    private MainView mainView;
    private Repository<Task> repository;
    private Motivator app;
    private CompositeDisposable compositeDisposable;

    @Override
    public void attachView(MainView view) {
        mainView = view;
        app = Motivator.get(mainView.getContext());
        repository = app.getTaskRepository();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        mainView = null;
        compositeDisposable.dispose();
    }

    public void loadTasks() {
        Disposable d = repository.query()
                .subscribeOn(app.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(new Consumer<List<Task>>() {
                    @Override
                    public void accept(List<Task> tasks) throws Exception {
                        if (tasks.size() > 0) {
                            mainView.showTasks(tasks);
                        } else {
                            mainView.showEmptyState();
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mainView.showEmptyState();
                    }
                })
                .subscribe();
        compositeDisposable.add(d);
    }

    public void onNewTaskReceived(final Task task) {
        Disposable d = repository.insert(task)
                .subscribeOn(app.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(new Consumer<Long>() {
                    @Override
                    public void accept(Long id) throws Exception {
                        task.setId(id);
                        Notifications.scheduleRepeating(mainView.getContext(), task);
                    }
                })
                .subscribe();
        compositeDisposable.add(d);
    }

    public void onTaskEdited(final Task task) {
        Disposable d = repository.update(task)
                .subscribeOn(app.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (task.isActive()) {
                            Notifications.cancelRepeating(mainView.getContext(), task);
                            Notifications.scheduleRepeating(mainView.getContext(), task);
                        }
                    }
                })
                .subscribe();
        compositeDisposable.add(d);
    }

    public void onDeleteButtonClicked(final Task task) {
        Disposable d = repository.delete(task)
                .subscribeOn(app.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Notifications.cancelRepeating(mainView.getContext(), task);
                    }
                })
                .subscribe();
        compositeDisposable.add(d);
    }

    public void onResumeButtonClicked(final Task task) {
        Notifications.scheduleRepeating(mainView.getContext(), task);
        Disposable d = repository.update(task)
                .subscribeOn(app.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        compositeDisposable.add(d);
    }

    public void onPauseButtonClicked(final Task task) {
        Notifications.cancelRepeating(mainView.getContext(), task);
        Disposable d = repository.update(task)
                .subscribeOn(app.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        compositeDisposable.add(d);
    }
}
