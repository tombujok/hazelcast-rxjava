package com.hazelcast.rxjava;

import com.hazelcast.core.IFunction;
import com.hazelcast.ringbuffer.OverflowPolicy;
import com.hazelcast.ringbuffer.Ringbuffer;
import rx.Observable;

import java.util.Collection;

public interface RxRingbuffer<E> {

    Observable<Long> add(E item, OverflowPolicy overflowPolicy);

    Observable<Long> addAll(Collection<? extends E> collection, OverflowPolicy overflowPolicy);

    Observable<E> readMany(long startSequence, int minCount, int maxCount, IFunction<E, Boolean> filter);

    Ringbuffer<E> getDelegate();

}
