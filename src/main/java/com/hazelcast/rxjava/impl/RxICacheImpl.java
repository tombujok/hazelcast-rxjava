package com.hazelcast.rxjava.impl;

import com.hazelcast.cache.ICache;
import com.hazelcast.rxjava.RxICache;
import rx.Observable;

import javax.cache.expiry.ExpiryPolicy;
import java.util.concurrent.Executor;

/**
 * TODO
 *
 * @author Viktor Gamov on 7/29/16.
 *         Twitter: @gamussa
 * @since 0.0.1
 */
public class RxICacheImpl<K, V> implements RxICache<K, V> {

    private final ICache<K, V> cache;
    private final Executor executor;

    public RxICacheImpl(ICache<K, V> cache) {
        this.cache = cache;
        this.executor = null;
    }

    public RxICacheImpl(ICache<K, V> cache, Executor executor) {
        this.cache = cache;
        this.executor = executor;
    }

    @Override public Observable<V> get(K key) {
        return RxIObservable.from(cache.getAsync(key), executor);
    }

    @Override public Observable<V> getAndPut(K key, V newValue) {
        return RxIObservable.from(cache.getAndPutAsync(key, newValue), executor);
    }

    @Override public Observable<V> getAndPut(K key, V newValue, ExpiryPolicy expiryPolicy) {
        return RxIObservable.from(cache.getAndPutAsync(key, newValue, expiryPolicy), executor);
    }

    @Override public ICache<K, V> getDelegate() {
        return this.cache;
    }

    @Override public Observable<Void> put(K key, V value) {
        return RxIObservable.from(cache.putAsync(key, value), executor);
    }

    @Override public Observable<Void> put(K key, V value, ExpiryPolicy expiryPolicy) {
        return RxIObservable.from(cache.putAsync(key, value, expiryPolicy), executor);
    }

    public static RxICache<String, String> from(ICache<String, String> cache) {
        return new RxICacheImpl<String, String>(cache);
    }

    public static RxICache<String, String> from(ICache<String, String> cache, Executor executor) {
        return new RxICacheImpl<String, String>(cache, executor);
    }
}
