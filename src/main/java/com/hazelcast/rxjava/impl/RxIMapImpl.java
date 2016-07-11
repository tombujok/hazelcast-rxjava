package com.hazelcast.rxjava.impl;

import com.hazelcast.core.IMap;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.rxjava.RxIMap;
import rx.Observable;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public final class RxIMapImpl<K, V> implements RxIMap<K, V> {

    private final IMap<K, V> map;
    private final Executor executor;

    private RxIMapImpl(IMap<K, V> map) {
        this.map = map;
        this.executor = null;
    }

    private RxIMapImpl(IMap<K, V> map, Executor executor) {
        this.map = map;
        this.executor = executor;
    }

    @Override
    public Observable<V> get(K key) {
        return RxIObservable.from(map.getAsync(key), executor);
    }

    @Override
    public Observable<V> put(K key, V value) {
        return RxIObservable.from(map.putAsync(key, value), executor);
    }

    @Override
    public Observable<V> put(K key, V value, long ttl, TimeUnit timeunit) {
        return RxIObservable.from(map.putAsync(key, value, ttl, timeunit), executor);
    }

    @Override
    public Observable<Void> set(K key, V value) {
        return RxIObservable.from(map.setAsync(key, value), executor);
    }

    @Override
    public Observable<Void> set(K key, V value, long ttl, TimeUnit timeunit) {
        return RxIObservable.from(map.setAsync(key, value, ttl, timeunit), executor);
    }

    @Override
    public Observable<V> remove(K key) {
        return RxIObservable.from(map.removeAsync(key), executor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Observable<T> submitToKey(K key, EntryProcessor<K, V> entryProcessor) {
        return RxIObservable.<T>from(map.submitToKey(key, entryProcessor), executor);
    }

    @Override
    public IMap<K, V> getDelegate() {
        return map;
    }

    public static <K, V> RxIMapImpl<K, V> from(IMap<K, V> map) {
        return new RxIMapImpl<K, V>(map);
    }

    public static <K, V> RxIMapImpl<K, V> from(IMap<K, V> map, Executor executor) {
        return new RxIMapImpl<K, V>(map, executor);
    }

}
