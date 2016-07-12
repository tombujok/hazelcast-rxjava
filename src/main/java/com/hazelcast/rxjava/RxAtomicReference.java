package com.hazelcast.rxjava;

import com.hazelcast.core.IAtomicReference;
import com.hazelcast.core.IFunction;
import rx.Observable;

public interface RxAtomicReference<E> {

    /**
     * Atomically sets the value to the given updated value
     * only if the current value {@code ==} the expected value.
     *
     * @param expect the expected value
     * @param update the new value
     * @return true if successful; or false if the actual value
     * was not equal to the expected value.
     */
    Observable<Boolean> compareAndSet(E expect, E update);

    /**
     * Gets the current value.
     *
     * @return the current value
     */
    Observable<E> get();

    /**
     * Atomically sets the given value.
     *
     * @param newValue the new value
     */
    Observable<Void> set(E newValue);

    /**
     * Gets the value and sets the new value.
     *
     * @param newValue the new value.
     * @return the old value.
     */
    Observable<E> getAndSet(E newValue);

    /**
     * Checks if the stored reference is null.
     *
     * @return true if null, false otherwise.
     */
    Observable<Boolean> isNull();

    /**
     * Clears the current stored reference.
     */
    Observable<Void> clear();

    /**
     * Checks if the reference contains the value.
     *
     * @param value the value to check (is allowed to be null).
     * @return true if the value is found, false otherwise.
     */
    Observable<Boolean> contains(E value);

    /**
     * Alters the currently stored reference by applying a function on it.
     *
     * @param function the function
     * @throws IllegalArgumentException if function is null.
     */
    Observable<Void> alter(IFunction<E, E> function);

    /**
     * Alters the currently stored reference by applying a function on it and gets the result.
     *
     * @param function the function
     * @return the new value.
     * @throws IllegalArgumentException if function is null.
     */
    Observable<E> alterAndGet(IFunction<E, E> function);

    /**
     * Alters the currently stored reference by applying a function on it on and gets the old value.
     *
     * @param function the function
     * @return the old value
     * @throws IllegalArgumentException if function is null.
     */
    Observable<E> getAndAlter(IFunction<E, E> function);

    /**
     * Applies a function on the value, the actual stored value will not change.
     *
     * @param function the function
     * @return the result of the function application
     * @throws IllegalArgumentException if function is null.
     */
    <R> Observable<R> apply(IFunction<E, R> function);

    IAtomicReference<E> getDelegate();
}
