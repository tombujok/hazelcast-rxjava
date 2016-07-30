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

import com.hazelcast.cache.ICache;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IAtomicReference;
import com.hazelcast.core.IMap;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.hazelcast.rxjava.impl.*;

import java.util.concurrent.Executor;

/**
 * Reactive version of the {@link com.hazelcast.core.Hazelcast}
 */
public final class RxHazelcast {

    private RxHazelcast() {
    }

    /**
     * @param instance non-rx java object to wrap-around
     * @return a rx-java equivalent of the given input object
     */
    public static RxHazelcastInstance from(HazelcastInstance instance) {
        return RxHazelcastInstanceImpl.from(instance);
    }

    /**
     * @param instance non-rx java object to wrap-around
     * @param executor executor to pass to rx-java for callback execution
     * @return a rx-java equivalent of the given input object
     */
    public static RxHazelcastInstance from(HazelcastInstance instance, Executor executor) {
        return RxHazelcastInstanceImpl.from(instance, executor);
    }

    /**
     * @param map non-rx java object to wrap-around
     * @return a rx-java equivalent of the given input object
     */
    public static <K, V> RxIMap<K, V> from(IMap<K, V> map) {
        return RxIMapImpl.from(map);
    }

    /**
     * @param map      non-rx java object to wrap-around
     * @param executor executor to pass to rx-java for callback execution
     * @return a rx-java equivalent of the given input object
     */
    public static <K, V> RxIMap<K, V> from(IMap<K, V> map, Executor executor) {
        return RxIMapImpl.from(map, executor);
    }

    /**
     * @param ringbuffer non-rx java object to wrap-around
     * @return a rx-java equivalent of the given input object
     */
    public static <E> RxRingbuffer<E> from(Ringbuffer<E> ringbuffer) {
        return RxRingbufferImpl.from(ringbuffer);
    }

    /**
     * @param ringbuffer non-rx java object to wrap-around
     * @param executor   executor to pass to rx-java for callback execution
     * @return a rx-java equivalent of the given input object
     */
    public static <E> RxRingbuffer<E> from(Ringbuffer<E> ringbuffer, Executor executor) {
        return RxRingbufferImpl.from(ringbuffer, executor);
    }

    /**
     * @param atomicLong non-rx java object to wrap-around
     * @return a rx-java equivalent of the given input object
     */
    public static RxAtomicLong from(IAtomicLong atomicLong) {
        return RxAtomicLongImpl.from(atomicLong);
    }

    /**
     * @param atomicLong non-rx java object to wrap-around
     * @param executor   executor to pass to rx-java for callback execution
     * @return a rx-java equivalent of the given input object
     */
    public static RxAtomicLong from(IAtomicLong atomicLong, Executor executor) {
        return RxAtomicLongImpl.from(atomicLong, executor);
    }

    /**
     * @param atomicReference non-rx java object to wrap-around
     * @return a rx-java equivalent of the given input object
     */
    public static <E> RxAtomicReference<E> from(IAtomicReference<E> atomicReference) {
        return RxAtomicReferenceImpl.from(atomicReference);
    }

    /**
     * @param atomicReference non-rx java object to wrap-around
     * @param executor        executor to pass to rx-java for callback execution
     * @return a rx-java equivalent of the given input object
     */
    public static <E> RxAtomicReference<E> from(IAtomicReference<E> atomicReference, Executor executor) {
        return RxAtomicReferenceImpl.from(atomicReference, executor);
    }

    public static RxICache<String, String> from(ICache<String, String> cache) {
        return RxICacheImpl.from(cache);
    }

    public static RxICache<String, String> from(ICache<String, String> cache, Executor executor) {
        return RxICacheImpl.from(cache, executor);
    }
}
