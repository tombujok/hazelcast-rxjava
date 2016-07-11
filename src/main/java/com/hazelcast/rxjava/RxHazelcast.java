package com.hazelcast.rxjava;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.hazelcast.rxjava.impl.RxHazelcastInstanceImpl;
import com.hazelcast.rxjava.impl.RxIMapImpl;
import com.hazelcast.rxjava.impl.RxRingbufferImpl;

import java.util.concurrent.Executor;

public final class RxHazelcast {

    public static RxHazelcastInstance newHazelcastInstance() {
        return new RxHazelcastInstanceImpl(Hazelcast.newHazelcastInstance());
    }

    public static RxHazelcastInstance newHazelcastInstance(Config config) {
        return new RxHazelcastInstanceImpl(Hazelcast.newHazelcastInstance(config));
    }

    public static RxHazelcastInstance newHazelcastInstance(Config config, Executor executor) {
        return new RxHazelcastInstanceImpl(Hazelcast.newHazelcastInstance(config), executor);
    }

    public static RxHazelcastInstance newHazelcastInstance(Executor executor) {
        return new RxHazelcastInstanceImpl(Hazelcast.newHazelcastInstance(), executor);
    }

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

}
