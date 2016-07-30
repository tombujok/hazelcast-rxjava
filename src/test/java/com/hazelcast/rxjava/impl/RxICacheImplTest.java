package com.hazelcast.rxjava.impl;

import com.hazelcast.cache.ICache;
import com.hazelcast.cache.impl.HazelcastServerCachingProvider;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.rxjava.RxHazelcast;
import com.hazelcast.rxjava.RxICache;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import rx.observers.TestSubscriber;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

/**
 * TODO
 *
 * @author Viktor Gamov on 7/29/16.
 *         Twitter: @gamussa
 * @since 0.0.1
 */
@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class RxICacheImplTest extends HazelcastTestSupport {

    private ICache<String, String> cache;
    private RxICache<String, String> rxCache;

    @Before
    public void before() {

        final HazelcastInstance hazelcastInstance = createHazelcastInstance();
        final MutableConfiguration<String, String> configuration = new MutableConfiguration<String, String>();
        HazelcastServerCachingProvider.createCachingProvider(hazelcastInstance)
            .getCacheManager().createCache("RxJava", configuration);

        cache = hazelcastInstance.getCacheManager().getCache("RxJava");
        cache.put("RxJava", "cool");
        rxCache = RxHazelcast.from(cache);
    }

    @Test
    public void fromICache() {
        // WHEN
        RxICache<String, String> rxICache = RxHazelcast.from(cache);

        // THEN
        assertEquals(cache, rxICache.getDelegate());
    }

    @Test
    public void get() {
        // WHEN
        TestSubscriber<String> subscriber = new TestSubscriber<String>();
        rxCache.get("RxJava").subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult("cool", subscriber);
    }

    @Test
    public void put() {
        // WHEN
        TestSubscriber<Void> subscriber = new TestSubscriber<Void>();
        rxCache.put("Reactive", "rocks").subscribe(subscriber);

        // THEN
        RxTestUtils.assertVoidResult(subscriber);
    }

    @Test
    public void putWithTtl() {
        // WHEN
        TestSubscriber<Void> subscriber = new TestSubscriber<Void>();
        rxCache.put("Reactive", "rocks", new CreatedExpiryPolicy(new Duration(SECONDS, 120)))
            .subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult(null, subscriber);
    }

    @Test
    public void getAndPut() {
        //WHEN
        final TestSubscriber<String> subscriber = new TestSubscriber<String>();
        rxCache.put("Reactive", "rocks").subscribe();
        rxCache.getAndPut("Reactive", "rocks!!!").subscribe(subscriber);

        // THEN
        RxTestUtils.assertSingleResult("rocks", subscriber);
    }

    @Test
    public void getAndPut_wthTtl() {
        //WHEN
        final TestSubscriber<String> subscriber = new TestSubscriber<String>();
        rxCache.put("Reactive", "rocks").subscribe();
        rxCache.getAndPut("Reactive", "rocks!!!", new CreatedExpiryPolicy(new Duration(SECONDS, 120))).subscribe
            (subscriber);

        // THEN
        RxTestUtils.assertSingleResult("rocks", subscriber);
    }

}
