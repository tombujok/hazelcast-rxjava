# Hazelcast RxJava Plugin [![Build Status](https://secure.travis-ci.org/tombujok/hazelcast-rxjava.png)](http://travis-ci.org/tombujok/hazelcast-rxjava) [![codecov](https://codecov.io/gh/tombujok/hazelcast-rxjava/branch/master/graph/badge.svg)](https://codecov.io/gh/tombujok/hazelcast-rxjava)

## Using Hazelcast through RxJava

Hazelcast plugin that enables using Hazelcast over RxJava in a reactive way.

## Supported Data Structures

- IMap
- RingBuffer
- IAtomicLong
- IAtomicReference

The RxJava plugin offers support only for data structures that already provide async methods.


## How to use it?

RxHazelcast object is the entry point for creating new instances or converting existing instances to rx-ified ones.

### HazelcastInstance

```java
# Converting an existing HZ instance to RxHazelcastInstance
RxHazelcastInstance rxInstance = RxHazelcast.from(instance);
```

### IMap

```java
# Creating new RxIMap instance
RxIMap rxIMap = RxHazelcastInstance.getMap("map-name");
```

```java
# Converting an existing IMap instance to RxIMap
RxImap rxIMap = RxHazelcast.from(imap);
```


### Ringbuffer

```java
# Creating new Ringbuffer instance
RxRingbuffer rxRingbuffer = RxHazelcastInstance.getRingbuffer("ringbuffer-name");
```

```java
# Converting an existing Ringbuffer instance to RxRingbuffer
RxRingbuffer rxRingbuffer = RxHazelcast.from(ringbuffer);
```

### IAtomicReference

```java
# Creating new IAtomicReference instance
IAtomicReference<Long> reference = RxHazelcastInstance.getAtomicReference("reference-name");
```

```java
# Converting an existing IAtomicReference instance to RxAtomicReference
RxAtomicReference rxReference = RxHazelcast.from(reference);
```


### IAtomicLong

```java
# Creating new IAtomicLong instance
IAtomicLong atomicLong = RxHazelcastInstance.getAtomicLong("long-name");
```

```java
# Converting an existing IAtomicReference instance to RxAtomicReference
RxAtomicLong rxAtomicLong = RxHazelcast.from(atomicLong);
```


## Example

### Observables flow in Java8

- Fetch task to process from a RingBuffer
- Fetch two required values simultaneously from two maps based on the task to process
- Store a result in a RingBuffer


```java
RxIMap<String, Float> rxCurrency = RxHazelcast.from(currency);
RxIMap<String, Float> rxCommission = RxHazelcast.from(commission);
RxRingbuffer<Exchange> rxToProcess = RxHazelcast.from(exchange);
RxRingbuffer<ProcessedExchange> rxProcessed = RxHazelcast.from(processed);

rxToProcess.readMany(0, 1, 10, null)
        .flatMap(exchange -> {
            log.info("Processing exchange" + exchange);
            String fromTo = exchange.from + exchange.to;
            return Observable.zip(
                    rxCurrency.get(fromTo),
                    rxCommission.get(fromTo),
                    (exchangeRate, commissionPercentage) -> {
                        Float commission = exchange.amount * commissionPercentage;
                        Float targetAmount = exchange.amount * exchangeRate;
                        return new ProcessedExchange(exchange.id, targetAmount, commission);
                    });
        })
        .flatMap(processedExchange -> {
            log.info("Storing exchange" + processedExchange);
            return rxProcessed.add(processedExchange, OverflowPolicy.FAIL);
        }).subscribe(subscriber);

subscriber.awaitTerminalEvent(10, TimeUnit.SECONDS);
```



## Requirements

- Hazelcast 3.7.x

## Maven

```xml
<dependency>
    <groupId>com.hazelcast</groupId>
    <artifactId>hazelcast-rxjava</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```

## Known limitations

The plugin provides support only for existing async methods. It does not convert sync methods into RxJava methods.
