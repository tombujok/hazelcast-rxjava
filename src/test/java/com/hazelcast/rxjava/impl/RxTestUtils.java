package com.hazelcast.rxjava.impl;

import com.hazelcast.test.HazelcastSerialClassRunner;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import rx.observers.TestSubscriber;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(HazelcastSerialClassRunner.class)
@Category({QuickTest.class})
public class RxTestUtils {

    public static <T> void assertSingleResult(T expected, TestSubscriber<T> subscriber) {
        subscriber.awaitTerminalEvent();
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        assertEquals(expected, subscriber.getOnNextEvents().iterator().next());
    }

    public static <T> void assertMultipleResult(List<T> expected, TestSubscriber<T> subscriber) {
        subscriber.awaitTerminalEvent();
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(expected.size());
        assertEquals(expected, subscriber.getOnNextEvents());
    }

    public static <T> void assertVoidResult(TestSubscriber<T> subscriber) {
        subscriber.awaitTerminalEvent();
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
    }

}
