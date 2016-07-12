package com.hazelcast.rxjava.impl;

import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.logging.NoLogFactory;
import com.hazelcast.spi.impl.AbstractCompletableFuture;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import rx.observers.TestSubscriber;

import java.util.concurrent.Executors;

import static com.hazelcast.rxjava.impl.RxTestUtils.assertFailed;
import static com.hazelcast.rxjava.impl.RxTestUtils.assertSingleResult;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class RxIObservableTest {

    @Test
    public void fromFuture() {
        // GIVEN
        TestSubscriber<String> subscriber = new TestSubscriber<String>();
        MyFuture<String> future = new MyFuture<String>();
        RxIObservable.from(future).subscribe(subscriber);

        // WHEN
        future.setResult("RESULT");

        // THEN
        assertSingleResult("RESULT", subscriber);
    }

    @Test
    public void fromFutureWithException() {
        // GIVEN
        TestSubscriber<String> subscriber = new TestSubscriber<String>();
        MyFuture<String> future = new MyFuture<String>();
        RxIObservable.from(future).subscribe(subscriber);
        Exception ex = new RuntimeException();

        // WHEN
        future.setResult(ex);

        // THEN
        assertFailed(ex, subscriber);
    }

    @Test
    public void fromFutureWithExecutor() {
        // GIVEN
        TestSubscriber<String> subscriber = new TestSubscriber<String>();
        MyFuture<String> future = new MyFuture<String>();
        RxIObservable.from(future, Executors.newSingleThreadExecutor()).subscribe(subscriber);

        // WHEN
        future.setResult("RESULT");

        // THEN
        assertSingleResult("RESULT", subscriber);
    }

    private static class MyFuture<V> extends AbstractCompletableFuture<V> {
        protected MyFuture() {
            super(Executors.newSingleThreadExecutor(), new NoLogFactory().getLogger("no"));
        }

        public void setResult(Object result) {
            super.setResult(result);
        }
    }

}
