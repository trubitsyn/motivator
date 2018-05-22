package io.github.trubitsyn.motivator.model;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface Repository<E> {
    Single<Long> insert(E element);
    Observable<Void> update(E updatedElement);
    Observable<Void> delete(E element);

    Single<List<Task>> query();

    void destroy();
}
