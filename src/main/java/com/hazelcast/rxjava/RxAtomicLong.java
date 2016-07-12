package com.hazelcast.rxjava;

import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IFunction;
import rx.Observable;

public interface RxAtomicLong {

    /**
     * Atomically adds the given value to the current value.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     * The operations result can be obtained in a blocking way, or a
     * callback can be provided for execution upon completion, as demonstrated in the following examples:
     *
     * @param delta the value to add
     * @return an {@link ICompletableFuture} bearing the response
     * @since 3.7
     */
    Observable<Long> addAndGet(long delta);

    /**
     * Atomically sets the value to the given updated value
     * only if the current value {@code ==} the expected value.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @param expect the expected value
     * @param update the new value
     * @return an {@link ICompletableFuture} with value true if successful; or false if the actual value
     * was not equal to the expected value.
     * @since 3.7
     */
    Observable<Boolean> compareAndSet(long expect, long update);

    /**
     * Atomically decrements the current value by one.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @return an {@link ICompletableFuture} with the updated value.
     * @since 3.7
     */
    Observable<Long> decrementAndGet();

    /**
     * Gets the current value. This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @return an {@link ICompletableFuture} with the current value
     * @since 3.7
     */
    Observable<Long> get();

    /**
     * Atomically adds the given value to the current value.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @param delta the value to add
     * @return an {@link ICompletableFuture} with the old value before the addition
     * @since 3.7
     */
    Observable<Long> getAndAdd(long delta);

    /**
     * Atomically sets the given value and returns the old value.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @param newValue the new value
     * @return an {@link ICompletableFuture} with the old value
     * @since 3.7
     */
    Observable<Long> getAndSet(long newValue);

    /**
     * Atomically increments the current value by one.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @return an {@link ICompletableFuture} with the updated value
     * @since 3.7
     */
    Observable<Long> incrementAndGet();

    /**
     * Atomically increments the current value by one.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @return an {@link ICompletableFuture} with the old value
     * @since 3.7
     */
    Observable<Long> getAndIncrement();

    /**
     * Atomically sets the given value.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @param newValue the new value
     * @return an {@link ICompletableFuture} API consumers can use to track execution of this request
     * @since 3.7
     */
    Observable<Void> set(long newValue);

    /**
     * Alters the currently stored value by applying a function on it.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @param function the function
     * @return an {@link ICompletableFuture} API consumers can use to track execution of this request
     * @throws IllegalArgumentException if function is null.
     * @since 3.7
     */
    Observable<Void> alter(IFunction<Long, Long> function);

    /**
     * Alters the currently stored value by applying a function on it and gets the result.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @param function the function
     * @return an {@link ICompletableFuture} with the new value.
     * @throws IllegalArgumentException if function is null.
     * @since 3.7
     */
    Observable<Long> alterAndGet(IFunction<Long, Long> function);

    /**
     * Alters the currently stored value by applying a function on it on and gets the old value.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @param function the function
     * @return an {@link ICompletableFuture} with the old value
     * @throws IllegalArgumentException if function is null.
     * @since 3.7
     */
    Observable<Long> getAndAlter(IFunction<Long, Long> function);

    /**
     * Applies a function on the value, the actual stored value will not change.
     * This method will dispatch a request and return immediately an {@link ICompletableFuture}.
     *
     * @param function the function
     * @return an {@link ICompletableFuture} with the result of the function application
     * @throws IllegalArgumentException if function is null.
     * @since 3.7
     */
    <R> Observable<R> apply(IFunction<Long, R> function);

    IAtomicLong getDelegate();

}
