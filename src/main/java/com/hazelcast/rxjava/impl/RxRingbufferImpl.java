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

public class RxRingbufferImpl<E> implements RxRingbuffer<E> {

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
