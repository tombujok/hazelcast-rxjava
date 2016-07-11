package com.hazelcast.rxjava;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Category({QuickTest.class})
public class RxHazelcastTest extends HazelcastTestSupport {

    @Test
    public void newHazelcastInstance() throws Exception {
        // WHEN
        RxHazelcastInstance hazelcast = RxHazelcast.newHazelcastInstance();
        // THEN
        assertNotNull(hazelcast.getDelegate());
    }

    @Test
    public void newHazelcastInstanceWithConfig() throws Exception {
        // WHEN
        Config config = new Config();
        RxHazelcastInstance hazelcast = RxHazelcast.newHazelcastInstance(config);
        // THEN
        assertNotNull(hazelcast.getDelegate());
    }

    @Test
    public void newHazelcastWithConfigAndExecutor() throws Exception {
        // WHEN
        Config config = new Config();
        Executor executor = Executors.newSingleThreadExecutor();
        RxHazelcastInstance hazelcast = RxHazelcast.newHazelcastInstance(config, executor);
        // THEN
        assertNotNull(hazelcast.getDelegate());
    }

    @Test
    public void newHazelcastWithExecutor() throws Exception {
        // WHEN
        Executor executor = Executors.newSingleThreadExecutor();
        RxHazelcastInstance hazelcast = RxHazelcast.newHazelcastInstance(executor);
        // THEN
        assertNotNull(hazelcast.getDelegate());
    }

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

}
