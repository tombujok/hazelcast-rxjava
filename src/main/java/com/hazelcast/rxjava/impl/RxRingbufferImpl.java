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

import com.hazelcast.core.IFunction;
import com.hazelcast.ringbuffer.OverflowPolicy;
import com.hazelcast.ringbuffer.ReadResultSet;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.hazelcast.rxjava.RxRingbuffer;
import rx.Observable;
import rx.functions.Func1;

import java.util.Collection;
import java.util.concurrent.Executor;

/**
 * Reactive version of the {@link Ringbuffer} - implementation
 *
 * @param <E>
 */
public final class RxRingbufferImpl<E> implements RxRingbuffer<E> {

    private final Ringbuffer<E> ringbuffer;
    private final Executor executor;

    private RxRingbufferImpl(Ringbuffer<E> ringbuffer) {
        this.ringbuffer = ringbuffer;
        this.executor = null;
    }

    private RxRingbufferImpl(Ringbuffer<E> ringbuffer, Executor executor) {
        this.ringbuffer = ringbuffer;
        this.executor = executor;
    }

    @Override
    public Observable<Long> add(E item, OverflowPolicy overflowPolicy) {
        return RxIObservable.from(ringbuffer.addAsync(item, overflowPolicy), executor);
    }

    @Override
    public Observable<Long> addAll(Collection<? extends E> collection, OverflowPolicy overflowPolicy) {
        return RxIObservable.from(ringbuffer.addAllAsync(collection, overflowPolicy), executor);
    }

    @Override
    public Observable<E> readMany(long startSequence, int minCount, int maxCount, IFunction<E, Boolean> filter) {
        return RxIObservable.from(ringbuffer.readManyAsync(startSequence, minCount, maxCount, filter), executor)
                .flatMap(new Func1<ReadResultSet<E>, Observable<E>>() {
                    @Override
                    public Observable<E> call(ReadResultSet<E> set) {
                        return Observable.from(set);
                    }
                });
    }

    public Ringbuffer<E> getDelegate() {
        return ringbuffer;
    }

    public static <E> RxRingbuffer<E> from(Ringbuffer<E> ringbuffer) {
        return new RxRingbufferImpl<E>(ringbuffer);
    }

    public static <E> RxRingbuffer<E> from(Ringbuffer<E> ringbuffer, Executor executor) {
        return new RxRingbufferImpl<E>(ringbuffer, executor);
    }

}
