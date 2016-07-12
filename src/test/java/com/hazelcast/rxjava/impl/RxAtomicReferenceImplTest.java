package com.hazelcast.rxjava.impl;

import com.hazelcast.core.IAtomicReference;
import com.hazelcast.core.IFunction;
import com.hazelcast.rxjava.RxAtomicReference;
import com.hazelcast.rxjava.RxHazelcast;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import rx.observers.TestSubscriber;

import java.io.Serializable;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class RxAtomicReferenceImplTest extends HazelcastTestSupport implements Serializable {

    private transient IAtomicReference<Long> atomicReference;
    private transient RxAtomicReference<Long> rxReference;

    @Before
    public void before() {
        atomicReference = createHazelcastInstance().getAtomicReference("RxJava");
        atomicReference.set(1L);
        rxReference = RxHazelcast.from(atomicReference);
    }

    @Test
    public void compareAndSet() throws Exception {
        // WHEN
        TestSubscriber<Boolean> subscriber = new TestSubscriber<Boolean>();
        rxReference.compareAndSet(1L, 2L).subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(true, subscriber);
    }

    @Test
    public void get() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxReference.get().subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(1L, subscriber);
    }

    @Test
    public void set() throws Exception {
        // WHEN
        TestSubscriber<Void> subscriber = new TestSubscriber<Void>();
        rxReference.set(2L).subscribe(subscriber);
        RxTestUtils.assertVoidResult(subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxReference.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(2L, resultSubscriber);
    }

    @Test
    public void getAndSet() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxReference.getAndSet(2L).subscribe(subscriber);
        RxTestUtils.assertSingleResult(1L, subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxReference.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(2L, resultSubscriber);
    }

    @Test
    public void isNull() throws Exception {
        // WHEN
        TestSubscriber<Boolean> subscriber = new TestSubscriber<Boolean>();
        rxReference.isNull().subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(false, subscriber);
    }

    @Test
    public void clear() throws Exception {
        // WHEN
        TestSubscriber<Void> subscriber = new TestSubscriber<Void>();
        rxReference.clear().subscribe(subscriber);
        RxTestUtils.assertVoidResult(subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxReference.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(null, resultSubscriber);
    }

    @Test
    public void contains() throws Exception {
        // WHEN
        TestSubscriber<Boolean> subscriber = new TestSubscriber<Boolean>();
        rxReference.contains(120L).subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(false, subscriber);
    }

    @Test
    public void alter() throws Exception {
        // WHEN
        TestSubscriber<Void> subscriber = new TestSubscriber<Void>();
        rxReference.alter(new IFunction<Long, Long>() {
            @Override
            public Long apply(Long input) {
                return 123L;
            }
        }).subscribe(subscriber);
        RxTestUtils.assertVoidResult(subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxReference.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(123L, resultSubscriber);

    }

    @Test
    public void alterAndGet() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxReference.alterAndGet(new IFunction<Long, Long>() {
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
        rxReference.getAndAlter(new IFunction<Long, Long>() {
            @Override
            public Long apply(Long input) {
                return 123L;
            }
        }).subscribe(subscriber);
        RxTestUtils.assertSingleResult(1L, subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxReference.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(123L, resultSubscriber);
    }

    @Test
    public void apply() throws Exception {
        // WHEN
        TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
        rxReference.apply(new IFunction<Long, Long>() {
            @Override
            public Long apply(Long input) {
                return 123L;
            }
        }).subscribe(subscriber);
        RxTestUtils.assertSingleResult(123L, subscriber);

        // THEN
        TestSubscriber<Long> resultSubscriber = new TestSubscriber<Long>();
        rxReference.get().subscribe(resultSubscriber);
        RxTestUtils.assertSingleResult(1L, resultSubscriber);
    }

}