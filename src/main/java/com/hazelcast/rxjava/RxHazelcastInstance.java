package com.hazelcast.rxjava;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IAtomicReference;

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
     * Creates cluster-wide atomic long. Hazelcast {@link IAtomicLong} is distributed
     * implementation of <tt>java.util.concurrent.atomic.AtomicLong</tt>.
     *
     * @param name name of the {@link IAtomicLong} proxy
     * @return IAtomicLong proxy for the given name
     */
    RxAtomicLong getAtomicLong(String name);

    /**
     * Creates cluster-wide atomic reference. Hazelcast {@link IAtomicReference} is distributed
     * implementation of <tt>java.util.concurrent.atomic.AtomicReference</tt>.
     *
     * @param name name of the {@link IAtomicReference} proxy
     * @return {@link IAtomicReference} proxy for the given name
     */
    <E> RxAtomicReference<E> getAtomicReference(String name);

    /**
     * @return Returns the underlying non-RxJava object
     */
    HazelcastInstance getDelegate();

}
