package com.hazelcast.rxjava;



import com.hazelcast.cache.ICache;
import rx.Observable;

import javax.cache.expiry.ExpiryPolicy;

/**
 * TODO
 *
 * @author Viktor Gamov on 7/29/16.
 *         Twitter: @gamussa
 * @since 0.0.1
 */
public interface RxICache<K, V> {

    Observable<V> get(K key);

    Observable<V> getAndPut(K key, V newValue);

    Observable<V> getAndPut(K key, V newValue, ExpiryPolicy expiryPolicy);

    Observable<Void> put(K key, V value);

    Observable<Void> put(K key, V value, ExpiryPolicy expiryPolicy);

    ICache<K, V> getDelegate();
}
