package com.hazelcast.rxjava.impl;

import com.hazelcast.rxjava.RxAtomicLong;
import com.hazelcast.rxjava.RxAtomicReference;
import com.hazelcast.rxjava.RxHazelcast;
import com.hazelcast.rxjava.RxHazelcastInstance;
import com.hazelcast.rxjava.RxIMap;
import com.hazelcast.rxjava.RxRingbuffer;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class RxHazelcastInstanceImplTest extends HazelcastTestSupport {

    private RxHazelcastInstance instance;

    @Before
    public void before() {
        instance = RxHazelcast.from(createHazelcastInstance());
    }

    @Test
    public void fromIMap() throws Exception {
        RxIMap<String, String> map = instance.getMap("map");
        assertNotNull(map);
        assertNotNull(map.getDelegate());
    }

    @Test
    public void fromRingbuffer() throws Exception {
        RxRingbuffer<String> ring = instance.getRingbuffer("buffer");
        assertNotNull(ring);
        assertNotNull(ring.getDelegate());
    }

    @Test
    public void fromAtomicReference() throws Exception {
        RxAtomicReference<String> reference = instance.getAtomicReference("reference");
        assertNotNull(reference);
        assertNotNull(reference.getDelegate());
    }

    @Test
    public void fromAtomicLong() throws Exception {
        RxAtomicLong reference = instance.getAtomicLong("long");
        assertNotNull(reference);
        assertNotNull(reference.getDelegate());
    }

}
