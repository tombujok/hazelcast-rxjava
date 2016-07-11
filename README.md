# Hazelcast RxJava Plugin [![Build Status](https://secure.travis-ci.org/tombujok/hazelcast-rxjava.png)](http://travis-ci.org/tombujok/hazelcast-rxjava)

## Using Hazelcast through RxJava

Hazelcast plugin that enables using Hazelcast over RxJava.

## Supported Data Structures

- IMap
- RingBuffer

## Unsupported Data Structures (support on the way)

- IAtomicLong
- IAtomicReference
- ExecutionService

RxJava plugin offers support only for data structures that provide async methods.


## How to use it?

RxHazelcast object is the entry point for creating new instances or converting existing instances to rx-ified ones.

### HazelcastInstance

```java
# Creating new RxHazelcastInstance - use the newHazelcastInstance* method family
RxHazelcastInstance rxInstance = RxHazelcast.newHazelcastInstance();
```

```java
# Converting an existing HZ instance to RxHazelcastInstance
RxHazelcastInstance rxInstance = RxHazelcast.from(instance);
```

### IMap

```java
# Creating new RxIMap instance
RxIMap rxIMap = RxHazelcast.getMap("map-name");
```

```java
# Converting an existing IMap instance to RxIMap
RxImap rxIMap = RxHazelcast.from(imap);
```


### Ringbuffer

```java
# Creating new Ringbuffer instance
RxRingbuffer rxRingbuffer = RxHazelcast.getRingbuffer("ringbuffer-name");
```

```java
# Converting an existing Ringbuffer instance to RxRingbuffer
RxRingbuffer rxRingbuffer = RxHazelcast.from(ringbuffer);
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

The plugin provides suppor only for existing async methods. It does not convert sync methods into RxJava methods.
