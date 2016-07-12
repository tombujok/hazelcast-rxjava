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
