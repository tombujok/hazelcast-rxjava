package com.hazelcast.rxjava;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IAtomicReference;
import com.hazelcast.core.IMap;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.hazelcast.rxjava.impl.RxAtomicLongImpl;
import com.hazelcast.rxjava.impl.RxAtomicReferenceImpl;
import com.hazelcast.rxjava.impl.RxHazelcastInstanceImpl;
import com.hazelcast.rxjava.impl.RxIMapImpl;
import com.hazelcast.rxjava.impl.RxRingbufferImpl;

import java.util.concurrent.Executor;

public final class RxHazelcast {

    public static RxHazelcastInstance from(HazelcastInstance instance) {
        return RxHazelcastInstanceImpl.from(instance);
    }

    public static RxHazelcastInstance from(HazelcastInstance instance, Executor executor) {
        return RxHazelcastInstanceImpl.from(instance, executor);
    }

    public static <K, V> RxIMap<K, V> from(IMap<K, V> map) {
        return RxIMapImpl.from(map);
    }

    public static <K, V> RxIMap<K, V> from(IMap<K, V> map, Executor executor) {
        return RxIMapImpl.from(map, executor);
    }

    public static <E> RxRingbuffer<E> from(Ringbuffer<E> ringbuffer) {
        return RxRingbufferImpl.from(ringbuffer);
    }

    public static <E> RxRingbuffer<E> from(Ringbuffer<E> ringbuffer, Executor executor) {
        return RxRingbufferImpl.from(ringbuffer, executor);
    }

    public static RxAtomicLong from(IAtomicLong atomicLong) {
        return RxAtomicLongImpl.from(atomicLong);
    }

    public static RxAtomicLong from(IAtomicLong atomicLong, Executor executor) {
        return RxAtomicLongImpl.from(atomicLong, executor);
    }

    public static <E> RxAtomicReference<E> from(IAtomicReference<E> atomicReference) {
        return RxAtomicReferenceImpl.from(atomicReference);
    }

    public static <E> RxAtomicReference<E> from(IAtomicReference<E> atomicReference, Executor executor) {
        return RxAtomicReferenceImpl.from(atomicReference, executor);
    }

}
