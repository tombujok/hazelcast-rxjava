package com.hazelcast.rxjava;

import com.hazelcast.core.HazelcastInstance;

public interface RxHazelcastInstance {

    /**
     * Returns the distributed map instance with the specified name.
     *
     * @param name name of the distributed map
     * @return distributed map instance with the specified name
     */
    <K, V> RxIMap<K, V> getMap(String name);

    /**
     * Returns the distributed Ringbuffer instance with the specified name.
     *
     * @param name name of the distributed Ringbuffer
     * @return distributed RingBuffer instance with the specified name
     */
    <E> RxRingbuffer<E> getRingbuffer(String name);

    /**
     * @return Returns the underlying non-RxJava object
     */
    HazelcastInstance getDelegate();

}
