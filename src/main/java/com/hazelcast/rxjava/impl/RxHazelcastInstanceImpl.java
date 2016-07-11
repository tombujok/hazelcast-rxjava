package com.hazelcast.rxjava.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.rxjava.RxHazelcastInstance;
import com.hazelcast.rxjava.RxIMap;
import com.hazelcast.rxjava.RxRingbuffer;

import java.util.concurrent.Executor;

public final class RxHazelcastInstanceImpl implements RxHazelcastInstance {

    private final HazelcastInstance instance;
    private final Executor executor;

    public RxHazelcastInstanceImpl(HazelcastInstance instance) {
        this.instance = instance;
        this.executor = null;
    }

    public RxHazelcastInstanceImpl(HazelcastInstance instance, Executor executor) {
        this.instance = instance;
        this.executor = executor;
    }

    @Override
    public <K, V> RxIMap<K, V> getMap(String name) {
        return RxIMapImpl.from(instance.<K, V>getMap(name), executor);
    }

    @Override
    public <E> RxRingbuffer<E> getRingbuffer(String name) {
        return RxRingbufferImpl.from(instance.<E>getRingbuffer(name), executor);
    }

    @Override
    public void shutdown() {
        instance.shutdown();
    }

    @Override
    public HazelcastInstance getDelegate() {
        return instance;
    }

    public static RxHazelcastInstanceImpl from(HazelcastInstance instance) {
        return new RxHazelcastInstanceImpl(instance);
    }

    public static RxHazelcastInstanceImpl from(HazelcastInstance instance, Executor executor) {
        return new RxHazelcastInstanceImpl(instance, executor);
    }

}
