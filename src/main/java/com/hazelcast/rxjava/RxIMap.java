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

package com.hazelcast.rxjava;

import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.map.EntryProcessor;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Reactive version of the {@link IMap}
 *
 * @param <K>
 * @param <V>
 */
public interface RxIMap<K, V> {

    /**
     * Asynchronously gets the given key.
     * <p>
     * <p><b>Warning:</b></p>
     * This method uses <tt>hashCode</tt> and <tt>equals</tt> of the binary form of
     * the <tt>key</tt>, not the actual implementations of <tt>hashCode</tt> and <tt>equals</tt>
     * defined in the <tt>key</tt>'s class.
     *
     * @param key the key of the map entry.
     * @return Observable from which the value of the key can be retrieved.
     * @throws NullPointerException if the specified key is null.
     * @see ICompletableFuture
     */
    Observable<V> get(K key);

    /**
     * Asynchronously puts the given key and value.
     * <p>
     * <p><b>Warning:</b></p>
     * This method uses <tt>hashCode</tt> and <tt>equals</tt> of the binary form of
     * the <tt>key</tt>, not the actual implementations of <tt>hashCode</tt> and <tt>equals</tt>
     * defined in the <tt>key</tt>'s class.
     *
     * @param key   the key of the map entry.
     * @param value the new value of the map entry.
     * @return Observable from which the old value of the key can be retrieved.
     * @throws NullPointerException if the specified key or value is null.
     * @see ICompletableFuture
     */
    Observable<V> put(K key, V value);

    /**
     * Asynchronously puts the given key and value into this map with a given ttl (time to live) value.
     * Entry will expire and get evicted after the ttl. If ttl is 0, then
     * the entry lives forever.
     * <p>
     * <p><b>Warning 1:</b></p>
     * This method uses <tt>hashCode</tt> and <tt>equals</tt> of the binary form of
     * the <tt>key</tt>, not the actual implementations of <tt>hashCode</tt> and <tt>equals</tt>
     * defined in the <tt>key</tt>'s class.
     * <p/>
     * <p><b>Warning 2:</b></p>
     * Time resolution for TTL is seconds. The given TTL value is rounded to the next closest second value.
     *
     * @param key      the key of the map entry.
     * @param value    the new value of the map entry.
     * @param ttl      maximum time for this entry to stay in the map.
     *                 0 means infinite.
     * @param timeunit time unit for the ttl.
     * @return Observable from which the old value of the key can be retrieved.
     * @throws NullPointerException if the specified key or value is null.
     * @see ICompletableFuture
     */
    Observable<V> put(K key, V value, long ttl, TimeUnit timeunit);

    /**
     * Asynchronously puts the given key and value.
     * the entry lives forever.
     * Similar to the put operation except that set
     * doesn't return the old value, which is more efficient.
     * <p>
     * <p><b>Warning 1:</b></p>
     * This method uses <tt>hashCode</tt> and <tt>equals</tt> of the binary form of
     * the <tt>key</tt>, not the actual implementations of <tt>hashCode</tt> and <tt>equals</tt>
     * defined in the <tt>key</tt>'s class.
     * <p/>
     *
     * @param key   the key of the map entry.
     * @param value the new value of the map entry.
     * @return Observable on which to block waiting for the operation to complete or
     * register an {@link ExecutionCallback} to be invoked upon completion.
     * @throws NullPointerException if the specified key or value is null.
     * @see ICompletableFuture
     */
    Observable<Void> set(K key, V value);

    /**
     * Asynchronously puts the given key and value into this map with a given ttl (time to live) value.
     * Entry will expire and get evicted after the ttl. If ttl is 0, then
     * the entry lives forever.
     * Similar to the put operation except that set
     * doesn't return the old value, which is more efficient.
     * <p>
     * <p><b>Warning 1:</b></p>
     * This method uses <tt>hashCode</tt> and <tt>equals</tt> of the binary form of
     * the <tt>key</tt>, not the actual implementations of <tt>hashCode</tt> and <tt>equals</tt>
     * defined in the <tt>key</tt>'s class.
     * <p/>
     * <p><b>Warning 2:</b></p>
     * Time resolution for TTL is seconds. The given TTL value is rounded to the next closest second value.
     *
     * @param key      the key of the map entry.
     * @param value    the new value of the map entry.
     * @param ttl      maximum time for this entry to stay in the map.
     *                 0 means infinite.
     * @param timeunit time unit for the ttl.
     * @return Observable on which client code can block waiting for the operation to
     * complete or provide an {@link ExecutionCallback} to be invoked
     * upon set operation completion.
     * @throws NullPointerException if the specified key or value is null.
     * @see ICompletableFuture
     */
    Observable<Void> set(K key, V value, long ttl, TimeUnit timeunit);

    /**
     * Asynchronously removes the given key, returning an {@link Observable}
     * <p><b>Warning:</b></p>
     * This method uses <tt>hashCode</tt> and <tt>equals</tt> of the binary form of
     * the <tt>key</tt>, not the actual implementations of <tt>hashCode</tt> and <tt>equals</tt>
     * defined in the <tt>key</tt>'s class.
     *
     * @param key The key of the map entry to remove.
     * @return Observable from which the value removed from the map can be
     * retrieved.
     * @throws NullPointerException if the specified key is null.
     * @see ICompletableFuture
     */
    Observable<V> remove(K key);

    /**
     * Applies the user defined EntryProcessor to the entry mapped by the key.
     * Returns immediately with a ICompletableFuture representing that task.
     * <p/>
     * EntryProcessor is not cancellable, so calling ICompletableFuture.cancel() method
     * won't cancel the operation of EntryProcessor.
     *
     * @param key            key to be processed
     * @param entryProcessor processor to process the key
     * @return Observable from which the result of the operation can be retrieved.
     * @see ICompletableFuture
     */
    <T> Observable<T> submitToKey(K key, EntryProcessor<K, V> entryProcessor);

    /**
     * @return Returns the underlying non-reactive object
     */
    IMap<K, V> getDelegate();

}
