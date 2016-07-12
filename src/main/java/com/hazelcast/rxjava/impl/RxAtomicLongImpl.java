/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.rxjava.impl;

import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IFunction;
import com.hazelcast.rxjava.RxAtomicLong;
import rx.Observable;

import java.util.concurrent.Executor;

public final class RxAtomicLongImpl implements RxAtomicLong {

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
