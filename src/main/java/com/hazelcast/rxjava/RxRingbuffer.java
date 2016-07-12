package com.hazelcast.rxjava;

import com.hazelcast.core.IFunction;
import com.hazelcast.ringbuffer.OverflowPolicy;
import com.hazelcast.ringbuffer.Ringbuffer;
import rx.Observable;

import java.util.Collection;

public interface RxRingbuffer<E> {

    /**
     * Asynchronously writes an item with a configurable {@link OverflowPolicy}.
     * <p>
     * If there is space in the ringbuffer, the call will return the sequence of the written item.
     * <p>
     * If there is no space, it depends on the overflow policy what happens:
     * <ol>
     * <li>{@link OverflowPolicy#OVERWRITE}: we just overwrite the oldest item in the ringbuffer and we violate
     * the ttl</li>
     * <li>{@link OverflowPolicy#FAIL}: we return -1 </li>
     * </ol>
     * <p>
     * The reason that FAIL exist is to give the opportunity to obey the ttl. If blocking behavior is required,
     * this can be implemented using retrying in combination with a exponential backoff. Example:
     * <code>
     * long sleepMs = 100;
     * for (; ; ) {
     * long result = ringbuffer.addAsync(item, FAIL).get();
     * if (result != -1) {
     * break;
     * }
     * TimeUnit.MILLISECONDS.sleep(sleepMs);
     * sleepMs = min(5000, sleepMs * 2);
     * }
     * </code>
     *
     * @param item           the item to add
     * @param overflowPolicy the OverflowPolicy to use.
     * @return the sequenceId of the added item, or -1 if the add failed.
     * @throws NullPointerException if item or overflowPolicy is null.
     */
    Observable<Long> add(E item, OverflowPolicy overflowPolicy);

    /**
     * Adds all the items of a collection to the tail of the Ringbuffer.
     * <p>
     * A addAll is likely to outperform multiple calls to add(Object) due to better io utilization and a reduced number
     * of executed operations.
     * <p>
     * If the batch is empty, the call is ignored.
     * <p>
     * When the collection is not empty, the content is copied into a different data-structure. This means that:
     * <ol>
     * <li>after this call completes, the collection can be re-used.</li>
     * <li>the collection doesn't need to be serializable</li>
     * </ol>
     * <p>
     * If the collection is larger than the capacity of the ringbuffer, then the items that were written first will
     * be overwritten. Therefor this call will not block.
     * <p>
     * The items are inserted in the order of the Iterator of the collection. If an addAll is executed concurrently with
     * an add or addAll, no guarantee is given that items are contiguous.
     * <p>
     * The result of the future contains the sequenceId of the last written item
     *
     * @param collection the batch of items to add.
     * @return the ICompletableFuture to synchronize on completion.
     * @throws java.lang.NullPointerException if batch is null,
     *                                        or if an item in this batch is null
     *                                        or if overflowPolicy is null
     * @throws IllegalArgumentException       if collection is empty
     */
    Observable<Long> addAll(Collection<? extends E> collection, OverflowPolicy overflowPolicy);

    /**
     * Reads a batch of items from the Ringbuffer. If the number of available items after the first read item is smaller than
     * the maxCount, these items are returned. So it could be the number of items read is smaller than the maxCount.
     * <p>
     * If there are less items available than minCount, then this call blocks.
     * <p>
     * Reading a batch of items is likely to perform better because less overhead is involved.
     * <p>
     * A filter can be provided to only select items that need to be read. If the filter is null, all items are read.
     * If the filter is not null, only items where the filter function returns true are returned. Using filters is a good
     * way to prevent getting items that are of no value to the receiver. This reduces the amount of IO and the number of
     * operations being executed, and can result in a significant performance improvement.
     *
     * @param startSequence the startSequence of the first item to read.
     * @param minCount      the minimum number of items to read.
     * @param maxCount      the maximum number of items to read.
     * @param filter        the filter. Filter is allowed to be null, indicating there is no filter.
     * @return a future containing the items read.
     * @throws java.lang.IllegalArgumentException if startSequence is smaller than 0
     *                                            or if startSequence larger than tailSequence()
     *                                            or if minCount smaller than 0
     *                                            or if minCount larger than maxCount,
     *                                            or if maxCount larger than the capacity of the ringbuffer
     *                                            or if maxCount larger than 1000 (to prevent overload)
     */
    Observable<E> readMany(long startSequence, int minCount, int maxCount, IFunction<E, Boolean> filter);

    Ringbuffer<E> getDelegate();

}
