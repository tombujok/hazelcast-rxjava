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

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.rxjava.RxAtomicLong;
import com.hazelcast.rxjava.RxAtomicReference;
import com.hazelcast.rxjava.RxHazelcastInstance;
import com.hazelcast.rxjava.RxIMap;
import com.hazelcast.rxjava.RxRingbuffer;

import java.util.concurrent.Executor;

/**
 * Reactive version of the {@link HazelcastInstance} - implementation
 */
public final class RxHazelcastInstanceImpl implements RxHazelcastInstance {

    private final HazelcastInstance instance;
    private final Executor executor;

    public RxHazelcastInstanceImpl(HazelcastInstance instance) {
        this.instance = instance;
        this.executor = null;
    }

    public RxHazelcastInstanceImpl(HazelcastInstance instance, Executor executor) {
        this.instance = instance;
        this.executor = executor;
    }

    @Override
    public <K, V> RxIMap<K, V> getMap(String name) {
        return RxIMapImpl.from(instance.<K, V>getMap(name), executor);
    }

    @Override
    public <E> RxRingbuffer<E> getRingbuffer(String name) {
        return RxRingbufferImpl.from(instance.<E>getRingbuffer(name), executor);
    }

    @Override
    public RxAtomicLong getAtomicLong(String name) {
        return RxAtomicLongImpl.from(instance.getAtomicLong(name), executor);
    }

    @Override
    public <E> RxAtomicReference<E> getAtomicReference(String name) {
        return RxAtomicReferenceImpl.from(instance.<E>getAtomicReference(name), executor);
    }

    @Override
    public HazelcastInstance getDelegate() {
        return instance;
    }

    public static RxHazelcastInstanceImpl from(HazelcastInstance instance) {
        return new RxHazelcastInstanceImpl(instance);
    }

    public static RxHazelcastInstanceImpl from(HazelcastInstance instance, Executor executor) {
        return new RxHazelcastInstanceImpl(instance, executor);
    }

}
