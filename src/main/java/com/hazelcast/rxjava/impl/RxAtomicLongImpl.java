package com.hazelcast.rxjava.impl;

import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IFunction;
import com.hazelcast.core.IMap;
import com.hazelcast.rxjava.RxAtomicLong;
import rx.Observable;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

public class RxAtomicLongImpl implements RxAtomicLong {

    private final IAtomicLong atomicLong;
    private final Executor executor;

    private RxAtomicLongImpl(IAtomicLong atomicLong) {
        this.atomicLong = atomicLong;
        this.executor = null;
    }

    private RxAtomicLongImpl(IAtomicLong atomicLong, Executor executor) {
        this.atomicLong = atomicLong;
        this.executor = executor;
    }

    @Override
    public Observable<Long> addAndGet(long delta) {
        return RxIObservable.from(atomicLong.addAndGetAsync(delta));
    }

    @Override
    public Observable<Boolean> compareAndSet(long expect, long update) {
        return RxIObservable.from(atomicLong.compareAndSetAsync(expect, update));
    }

    @Override
    public Observable<Long> decrementAndGet() {
        return RxIObservable.from(atomicLong.decrementAndGetAsync());
    }

    @Override
    public Observable<Long> get() {
        return RxIObservable.from(atomicLong.getAsync());
    }

    @Override
    public Observable<Long> getAndAdd(long delta) {
        return RxIObservable.from(atomicLong.getAndAddAsync(delta));
    }

    @Override
    public Observable<Long> getAndSet(long newValue) {
        return RxIObservable.from(atomicLong.getAndSetAsync(newValue));
    }

    @Override
    public Observable<Long> incrementAndGet() {
        return RxIObservable.from(atomicLong.incrementAndGetAsync());
    }

    @Override
    public Observable<Long> getAndIncrement() {
        return RxIObservable.from(atomicLong.getAndIncrementAsync());
    }

    @Override
    public Observable<Void> set(long newValue) {
        return RxIObservable.from(atomicLong.setAsync(newValue));
    }

    @Override
    public Observable<Void> alter(IFunction<Long, Long> function) {
        return RxIObservable.from(atomicLong.alterAsync(function));
    }

    @Override
    public Observable<Long> alterAndGet(IFunction<Long, Long> function) {
        return RxIObservable.from(atomicLong.alterAndGetAsync(function));
    }

    @Override
    public Observable<Long> getAndAlter(IFunction<Long, Long> function) {
        return RxIObservable.from(atomicLong.getAndAlterAsync(function));
    }

    @Override
    public <R> Observable<R> apply(IFunction<Long, R> function) {
        return RxIObservable.from(atomicLong.applyAsync(function));
    }

    @Override
    public IAtomicLong getDelegate() {
        return atomicLong;
    }

    public static RxAtomicLong from(IAtomicLong atomicLong) {
        return new RxAtomicLongImpl(atomicLong);
    }

    public static RxAtomicLong from(IAtomicLong atomicLong, Executor executor) {
        return new RxAtomicLongImpl(atomicLong, executor);
    }

}
