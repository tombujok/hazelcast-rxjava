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

import com.hazelcast.core.IMap;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.rxjava.RxIMap;
import rx.Observable;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Reactive version of the {@link IMap} - implementation
 *
 * @param <K>
 * @param <V>
 */
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
