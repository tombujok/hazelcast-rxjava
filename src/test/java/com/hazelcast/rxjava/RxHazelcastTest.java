package com.hazelcast.rxjava;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IAtomicReference;
import com.hazelcast.core.IMap;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class RxHazelcastTest extends HazelcastTestSupport {

    @Test
    public void fromInstance() throws Exception {
        // WHEN
        HazelcastInstance instance = createHazelcastInstance();
        RxHazelcastInstance hazelcast = RxHazelcast.from(instance);
        // THEN
        assertNotNull(hazelcast.getDelegate());
    }

    @Test
    public void fromInstanceWithExecutor() throws Exception {
        // WHEN
        Executor executor = Executors.newSingleThreadExecutor();
        HazelcastInstance instance = createHazelcastInstance();
        RxHazelcastInstance hazelcast = RxHazelcast.from(instance, executor);
        // THEN
        assertNotNull(hazelcast.getDelegate());
    }

    @Test
    public void fromIMap() throws Exception {
        // WHEN
        HazelcastInstance instance = createHazelcastInstance();
        IMap<String, String> imap = instance.getMap("map");
        RxIMap<String, String> rxIMap = RxHazelcast.from(imap);
        // THEN
        assertEquals(imap, rxIMap.getDelegate());
    }

    @Test
    public void fromIMapWithExecutor() throws Exception {
        // WHEN
        Executor executor = Executors.newSingleThreadExecutor();
        HazelcastInstance instance = createHazelcastInstance();
        IMap<String, String> imap = instance.getMap("map");
        RxIMap<String, String> rxIMap = RxHazelcast.from(imap, executor);
        // THEN
        assertEquals(imap, rxIMap.getDelegate());
    }

    @Test
    public void fromRingbuffer() throws Exception {
        // WHEN
        HazelcastInstance instance = createHazelcastInstance();
        Ringbuffer<String> ringbuffer = instance.getRingbuffer("buffer");
        RxRingbuffer<String> rxRingbuffer = RxHazelcast.from(ringbuffer);
        // THEN
        assertEquals(ringbuffer, rxRingbuffer.getDelegate());
    }

    @Test
    public void fromRingbufferWithExecutor() throws Exception {
        // WHEN
        Executor executor = Executors.newSingleThreadExecutor();
        HazelcastInstance instance = createHazelcastInstance();
        Ringbuffer<String> ringbuffer = instance.getRingbuffer("buffer");
        RxRingbuffer<String> rxRingbuffer = RxHazelcast.from(ringbuffer, executor);
        // THEN
        assertEquals(ringbuffer, rxRingbuffer.getDelegate());
    }

    @Test
    public void fromAtomicReference() throws Exception {
        // WHEN
        HazelcastInstance instance = createHazelcastInstance();
        IAtomicReference<String> reference = instance.getAtomicReference("reference");
        RxAtomicReference<String> rxReference = RxHazelcast.from(reference);
        // THEN
        assertEquals(reference, rxReference.getDelegate());
    }

    @Test
    public void fromAtomicReferenceWithExecutor() throws Exception {
        // WHEN
        Executor executor = Executors.newSingleThreadExecutor();
        HazelcastInstance instance = createHazelcastInstance();
        IAtomicReference<String> reference = instance.getAtomicReference("reference");
        RxAtomicReference<String> rxReference = RxHazelcast.from(reference, executor);
        // THEN
        assertEquals(reference, rxReference.getDelegate());
    }

    @Test
    public void fromAtomicLong() throws Exception {
        // WHEN
        HazelcastInstance instance = createHazelcastInstance();
        IAtomicLong atomicLong = instance.getAtomicLong("atomicLong");
        RxAtomicLong rxReference = RxHazelcast.from(atomicLong);
        // THEN
        assertEquals(atomicLong, rxReference.getDelegate());
    }

    @Test
    public void fromAtomicLongWithExecutor() throws Exception {
        // WHEN
        Executor executor = Executors.newSingleThreadExecutor();
        HazelcastInstance instance = createHazelcastInstance();
        IAtomicLong atomicLong = instance.getAtomicLong("atomicLong");
        RxAtomicLong rxReference = RxHazelcast.from(atomicLong, executor);
        // THEN
        assertEquals(atomicLong, rxReference.getDelegate());
    }

}
