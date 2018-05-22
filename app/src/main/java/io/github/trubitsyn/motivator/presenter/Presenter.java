package io.github.trubitsyn.motivator.presenter;

public interface Presenter<V> {
    void attachView(V view);
    void detachView();
}
