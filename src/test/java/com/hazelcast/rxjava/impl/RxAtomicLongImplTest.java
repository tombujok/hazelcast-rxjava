package com.hazelcast.rxjava.impl;

import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IFunction;
import com.hazelcast.rxjava.RxAtomicLong;
import com.hazelcast.rxjava.RxHazelcast;
import com.hazelcast.test.HazelcastTestSupport;
import org.junit.Before;
import org.junit.Test;
import rx.observers.TestSubscriber;

public class RxAtomicLongImplTest extends HazelcastTestSupport {

    private IAtomicLong atomicLong;
    private RxAtomicLong rxLong;

    @Before
    public void before() {
        atomicLong = createHazelcastInstance().getAtomicLong("RxJava");
        atomicLong.set(1);
        rxLong = RxHazelcast.from(atomicLong);
    }

    @Test
    public void addAndGet() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxLong.addAndGet(1L).subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(2L, subscriber);
    }

    @Test
    public void compareAndSet() throws Exception {
        // WHEN
        TestSubscriber<Boolean> subscriber = new TestSubscriber<Boolean>();
        rxLong.compareAndSet(1L, 2L).subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(true, subscriber);
    }

    @Test
    public void decrementAndGet() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxLong.decrementAndGet().subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(0L, subscriber);
    }

    @Test
    public void get() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxLong.get().subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(1L, subscriber);
    }

    @Test
    public void getAndAdd() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxLong.getAndAdd(1).subscribe(subscriber);
        RxTestUtils.assertSingleResult(1L, subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxLong.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(2L, resultSubscriber);
    }

    @Test
    public void getAndSet() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxLong.getAndSet(10).subscribe(subscriber);
        RxTestUtils.assertSingleResult(1L, subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxLong.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(10L, resultSubscriber);
    }

    @Test
    public void incrementAndGet() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxLong.incrementAndGet().subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(2L, subscriber);
    }

    @Test
    public void getAndIncrement() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxLong.getAndIncrement().subscribe(subscriber);
        RxTestUtils.assertSingleResult(1L, subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxLong.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(2L, resultSubscriber);
    }

    @Test
    public void set() throws Exception {
        // WHEN
        TestSubscriber<Void> subscriber = new TestSubscriber<Void>();
        rxLong.set(10L).subscribe(subscriber);

        // THEN
        RxTestUtils.assertVoidResult(subscriber);
    }

    @Test
    public void alter() throws Exception {
        // WHEN
        TestSubscriber<Void> subscriber = new TestSubscriber<Void>();
        rxLong.alter(new IFunction<Long, Long>() {
            @Override
            public Long apply(Long input) {
                return 123L;
            }
        }).subscribe(subscriber);
        RxTestUtils.assertVoidResult(subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxLong.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(123L, resultSubscriber);

    }

    @Test
    public void alterAndGet() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxLong.alterAndGet(new IFunction<Long, Long>() {
            @Override
            public Long apply(Long input) {
                return 244L;
            }
        }).subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(244L, subscriber);
    }

    @Test
    public void getAndAlter() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxLong.getAndAlter(new IFunction<Long, Long>() {
            @Override
            public Long apply(Long input) {
                return 123L;
            }
        }).subscribe(subscriber);
        RxTestUtils.assertSingleResult(1L, subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxLong.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(123L, resultSubscriber);
    }

    @Test
    public void apply() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxLong.apply(new IFunction<Long, Long>() {
            @Override
            public Long apply(Long input) {
                return 123L;
            }
        }).subscribe(subscriber);
        RxTestUtils.assertSingleResult(123L, subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxLong.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(1L, resultSubscriber);
    }

}