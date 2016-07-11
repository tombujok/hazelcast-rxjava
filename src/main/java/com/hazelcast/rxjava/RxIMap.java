package com.hazelcast.rxjava;

import com.hazelcast.core.IMap;
import com.hazelcast.map.EntryProcessor;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public interface RxIMap<K, V> {

    Observable<V> get(K key);

    Observable<V> put(K key, V value);

    Observable<V> put(K key, V value, long ttl, TimeUnit timeout);

    Observable<Void> set(K key, V value);

    Observable<Void> set(K key, V value, long ttl, TimeUnit timeout);

    Observable<V> remove(K key);

    <T> Observable<T> submitToKey(K key, EntryProcessor<K, V> entryProcessor);

    IMap<K, V> getDelegate();

}
