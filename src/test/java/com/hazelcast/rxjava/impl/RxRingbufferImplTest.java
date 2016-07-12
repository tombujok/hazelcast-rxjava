package com.hazelcast.rxjava.impl;

import com.hazelcast.core.IFunction;
import com.hazelcast.ringbuffer.OverflowPolicy;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.hazelcast.rxjava.RxHazelcast;
import com.hazelcast.rxjava.RxRingbuffer;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import rx.observers.TestSubscriber;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class RxRingbufferImplTest extends HazelcastTestSupport {

    private Ringbuffer<String> ring;
    private RxRingbuffer<String> rxring;

    @Before
    public void before() {
        ring = createHazelcastInstance().getRingbuffer("RxJava");
        ring.add("RxJava");
        rxring = RxHazelcast.from(ring);
    }

    @Test
    public void fromRingbuffer() {
        // WHEN
        RxRingbuffer<String> rxringAdHoc = RxHazelcast.from(ring);

        // THEN
        assertEquals(ring, rxringAdHoc.getDelegate());
    }

    @Test
    public void add() {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxring.add("Reactive", OverflowPolicy.FAIL).subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(1L, subscriber);
    }

    @Test
    public void addAll() {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxring.addAll(asList("Reactive", "Stuff"), OverflowPolicy.FAIL).subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(2L, subscriber);
    }

    @Test
    public void readMany() {
        // WHEN
        TestSubscriber<String> subscriber = new TestSubscriber<String>();
        rxring.addAll(asList("Reactive", "Stuff"), OverflowPolicy.FAIL).toBlocking();
        rxring.readMany(0, 1, 10, new IFunction<String, Boolean>() {
            @Override
            public Boolean apply(String input) {
                return true;
            }
        }).subscribe(subscriber);

        // THEN
        RxTestUtils.assertMultipleResult(asList("RxJava", "Reactive", "Stuff"), subscriber);
    }

}
