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

import com.hazelcast.core.AsyncAtomicReference;
import com.hazelcast.core.IAtomicReference;
import com.hazelcast.core.IFunction;
import com.hazelcast.rxjava.RxAtomicReference;
import rx.Observable;

import java.util.concurrent.Executor;

public final class RxAtomicReferenceImpl<E> implements RxAtomicReference<E> {

    private final AsyncAtomicReference<E> atomicReference;
    private final Executor executor;

    private RxAtomicReferenceImpl(IAtomicReference<E> atomicReference) {
        this.atomicReference = (AsyncAtomicReference<E>) atomicReference;
        this.executor = null;
    }

    private RxAtomicReferenceImpl(IAtomicReference<E> atomicReference, Executor executor) {
        this.atomicReference = (AsyncAtomicReference<E>) atomicReference;
        this.executor = executor;
    }

    @Override
    public Observable<Boolean> compareAndSet(E expect, E update) {
        return RxIObservable.from(atomicReference.asyncCompareAndSet(expect, update));
    }

    @Override
    public Observable<E> get() {
        return RxIObservable.from(atomicReference.asyncGet());
    }

    @Override
    public Observable<Void> set(E newValue) {
        return RxIObservable.from(atomicReference.asyncSet(newValue));
    }

    @Override
    public Observable<E> getAndSet(E newValue) {
        return RxIObservable.from(atomicReference.asyncGetAndSet(newValue));
    }

    @Override
    public Observable<Boolean> isNull() {
        return RxIObservable.from(atomicReference.asyncIsNull());
    }

    @Override
    public Observable<Void> clear() {
        return RxIObservable.from(atomicReference.asyncClear());
    }

    @Override
    public Observable<Boolean> contains(E value) {
        return RxIObservable.from(atomicReference.asyncContains(value));
    }

    @Override
    public Observable<Void> alter(IFunction<E, E> function) {
        return RxIObservable.from(atomicReference.asyncAlter(function));
    }

    @Override
    public Observable<E> alterAndGet(IFunction<E, E> function) {
        return RxIObservable.from(atomicReference.asyncAlterAndGet(function));
    }

    @Override
    public Observable<E> getAndAlter(IFunction<E, E> function) {
        return RxIObservable.from(atomicReference.asyncGetAndAlter(function));
    }

    @Override
    public <R> Observable<R> apply(IFunction<E, R> function) {
        return RxIObservable.from(atomicReference.asyncApply(function));
    }

    @Override
    public IAtomicReference<E> getDelegate() {
        return atomicReference;
    }

    public static <E> RxAtomicReference<E> from(IAtomicReference<E> atomicReference) {
        return new RxAtomicReferenceImpl(atomicReference);
    }

    public static <E> RxAtomicReference<E> from(IAtomicReference<E> atomicReference, Executor executor) {
        return new RxAtomicReferenceImpl(atomicReference, executor);
    }

}
