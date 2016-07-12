package com.hazelcast.rxjava.impl;

import com.hazelcast.core.IMap;
import com.hazelcast.map.AbstractEntryProcessor;
import com.hazelcast.rxjava.RxHazelcast;
import com.hazelcast.rxjava.RxIMap;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import rx.observers.TestSubscriber;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class RxImapImplTest extends HazelcastTestSupport {

    private IMap<String, String> imap;
    private RxIMap<String, String> rxmap;

    @Before
    public void before() {
        imap = createHazelcastInstance().getMap("RxJava");
        imap.put("RxJava", "cool");
        rxmap = RxHazelcast.from(imap);
    }

    @Test
    public void fromImap() {
        // WHEN
        RxIMap<String, String> rxmap = RxHazelcast.from(imap);

        // THEN
        assertEquals(imap, rxmap.getDelegate());
    }

    @Test
    public void get() {
        // WHEN
        TestSubscriber<String> subscriber = new TestSubscriber<String>();
        rxmap.get("RxJava").subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult("cool", subscriber);
    }

    @Test
    public void put() {
        // WHEN
        TestSubscriber<String> subscriber = new TestSubscriber<String>();
        rxmap.put("Reactive", "rocks").subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(null, subscriber);
    }

    @Test
    public void putWithTtl() {
        // WHEN
        TestSubscriber<String> subscriber = new TestSubscriber<String>();
        rxmap.put("Reactive", "rocks", 120, TimeUnit.SECONDS).subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(null, subscriber);
    }

    @Test
    public void putWithTtl_correctTtlSet() {
        // WHEN
        IMap map = mock(IMap.class);
        RxIMap<String, String> rxmap = RxHazelcast.from(map);
        rxmap.put("Reactive", "rocks", 120, TimeUnit.SECONDS).subscribe(new TestSubscriber<String>());

        // THEN
        verify(map, times(1)).putAsync("Reactive", "rocks", 120, TimeUnit.SECONDS);
    }

    @Test
    public void set() {
        // WHEN
        TestSubscriber<Void> subscriber = new TestSubscriber<Void>();
        rxmap.set("Reactive", "rocks").subscribe(subscriber);

        // THEN
        RxTestUtils.assertVoidResult(subscriber);
    }

    @Test
    public void setWithTtl() {
        // WHEN
        TestSubscriber<Void> subscriber = new TestSubscriber<Void>();
        rxmap.set("Reactive", "rocks", 120, TimeUnit.SECONDS).subscribe(subscriber);

        // THEN
        RxTestUtils.assertVoidResult(subscriber);
    }

    @Test
    public void setWithTtl_correctTtlSet() {
        // WHEN
        IMap map = mock(IMap.class);
        RxIMap<String, String> rxmap = RxHazelcast.from(map);
        rxmap.set("Reactive", "rocks", 120, TimeUnit.SECONDS).subscribe(new TestSubscriber<Void>());

        // THEN
        verify(map, times(1)).setAsync("Reactive", "rocks", 120, TimeUnit.SECONDS);
    }


    @Test
    public void remove() {
        // WHEN
        TestSubscriber<String> subscriber = new TestSubscriber<String>();
        rxmap.remove("RxJava").subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult("cool", subscriber);
    }

    @Test
    public void submitToKey() {
        // WHEN
        TestSubscriber<String> subscriber = new TestSubscriber<String>();
        rxmap.<String>submitToKey("RxJava", new AbstractEntryProcessor<String, String>(false) {
            @Override
            public Object process(Map.Entry entry) {
                return entry.getValue();
            }
        }).subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult("cool", subscriber);
    }

}
