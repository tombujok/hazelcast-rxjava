package com.hazelcast.rxjava;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.ringbuffer.OverflowPolicy;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.hazelcast.test.HazelcastSerialClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observers.TestSubscriber;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RunWith(HazelcastSerialClassRunner.class)
@Category({QuickTest.class})
public class RxHazelcastExamples extends HazelcastTestSupport {

    private Logger log = Logger.getLogger(RxHazelcastExamples.class.getName());

    private HazelcastInstance instance = Hazelcast.newHazelcastInstance();

    private RxIMap<String, Float> rxCurrency;
    private RxIMap<String, Float> rxCommission;
    private RxRingbuffer<Exchange> rxToProcess;
    private RxRingbuffer<ProcessedExchange> rxProcessed;

    @Before
    public void before() {
        IMap<String, Float> currency = instance.getMap("currency");
        currency.put("USDEUR", 1.23f);
        currency.put("USDCHF", 1.01f);

        IMap<String, Float> commission = instance.getMap("commission");
        commission.put("USDEUR", 0.020f);
        commission.put("USDCHF", 0.015f);

        Ringbuffer<Exchange> exchange = instance.getRingbuffer("exchange");
        exchange.add(new Exchange(1, "USD", "EUR", 1000.0f));
        exchange.add(new Exchange(2, "USD", "EUR", 1000.0f));

        Ringbuffer<ProcessedExchange> processed = instance.getRingbuffer("processed");

        rxCurrency = RxHazelcast.from(currency);
        rxCommission = RxHazelcast.from(commission);
        rxToProcess = RxHazelcast.from(exchange);
        rxProcessed = RxHazelcast.from(processed);
    }

    @After
    public void after() {
        instance.shutdown();
    }

    // Commented out since JDK set to 1.6
//    @Test
//    public void asyncFlowJava8() throws Exception {
//        TestSubscriber subscriber = new TestSubscriber();
//
//        rxToProcess.readMany(0, 1, 100, null)
//                .flatMap(exchange -> {
//                    log.info("Processing exchange" + exchange);
//                    String fromTo = exchange.from + exchange.to;
//                    return Observable.zip(
//                            rxCurrency.get(fromTo),
//                            rxCommission.get(fromTo),
//                            (exchangeRate, commissionPercentage) -> {
//                                Float commission = exchange.amount * commissionPercentage;
//                                Float targetAmount = exchange.amount * exchangeRate;
//                                return new ProcessedExchange(exchange.id, targetAmount, commission);
//                            });
//                })
//                .flatMap(processedExchange -> {
//                    log.info("Storing exchange" + processedExchange);
//                    return rxProcessed.add(processedExchange, OverflowPolicy.FAIL);
//                }).subscribe(subscriber);
//
//        subscriber.awaitTerminalEvent(10, TimeUnit.SECONDS);
//    }

    @Test
    public void asyncFlowJava7() throws Exception {
        TestSubscriber subscriber = new TestSubscriber();

        rxToProcess.readMany(0, 1, 100, null)
                .flatMap(new Func1<Exchange, Observable<ProcessedExchange>>() {
                    @Override
                    public Observable<ProcessedExchange> call(final Exchange exchange) {
                        log.info("Processing exchange" + exchange);
                        String fromTo = exchange.from + exchange.to;
                        return Observable.zip(
                                rxCurrency.get(fromTo),
                                rxCommission.get(fromTo),
                                new Func2<Float, Float, ProcessedExchange>() {
                                    @Override
                                    public ProcessedExchange call(Float exchangeRate, Float commissionPercentage) {
                                        Float commission = exchange.amount * commissionPercentage;
                                        Float targetAmount = exchange.amount * exchangeRate;
                                        return new ProcessedExchange(exchange.id, targetAmount, commission);
                                    }
                                }
                        );
                    }
                }).flatMap(new Func1<ProcessedExchange, Observable<Long>>() {
            @Override
            public Observable<Long> call(ProcessedExchange processedExchange) {
                log.info("Storing exchange" + processedExchange);
                return rxProcessed.add(processedExchange, OverflowPolicy.FAIL);
            }
        }).subscribe(subscriber);

        subscriber.awaitTerminalEvent(10, TimeUnit.SECONDS);
    }

    private static class Exchange implements Serializable {
        Integer id;
        String from;
        String to;
        Float amount;

        public Exchange(Integer id, String from, String to, Float amount) {
            this.id = id;
            this.from = from;
            this.to = to;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Exchange{" +
                    "id=" + id +
                    '}';
        }
    }

    private static class ProcessedExchange implements Serializable {
        Integer id;
        Float amount;
        Float commission;

        public ProcessedExchange(Integer id, Float amount, Float commission) {
            this.id = id;
            this.amount = amount;
            this.commission = commission;
        }

        @Override
        public String toString() {
            return "ProcessedExchange{" +
                    "id=" + id +
                    '}';
        }
    }


}


