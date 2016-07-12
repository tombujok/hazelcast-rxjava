package com.hazelcast.rxjava.impl;

import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.ICompletableFuture;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Executor;

final class RxIObservable<T> extends Observable<T> {

    private RxIObservable(ICompletableFuture<T> completableFuture) {
        super(onSubscribe(completableFuture));
    }

    RxIObservable(ICompletableFuture<T> completableFuture, Executor executor) {
        super(executor != null ? onSubscribe(completableFuture, executor) : onSubscribe(completableFuture));
    }

    private static <T> OnSubscribe<T> onSubscribe(ICompletableFuture<T> completableFuture, Executor executor) {
        return new RxOnSubscribe<T>(completableFuture, executor);
    }

    private static <T> OnSubscribe<T> onSubscribe(ICompletableFuture<T> completableFuture) {
        return new RxOnSubscribe<T>(completableFuture);
    }

    static <T> RxIObservable<T> from(ICompletableFuture<T> completableFuture) {
        return new RxIObservable<T>(completableFuture);
    }

    static <T> RxIObservable<T> from(ICompletableFuture<T> completableFuture, Executor executor) {
        return new RxIObservable<T>(completableFuture, executor);
    }

    private static class RxOnSubscribe<T> implements OnSubscribe<T> {

        private final ICompletableFuture<T> completableFuture;
        private final Executor executor;

        private RxOnSubscribe(ICompletableFuture<T> completableFuture) {
            this.completableFuture = completableFuture;
            this.executor = null;
        }

        private RxOnSubscribe(ICompletableFuture<T> completableFuture, Executor executor) {
            this.completableFuture = completableFuture;
            this.executor = executor;
        }

        @Override
        public void call(Subscriber<? super T> subscriber) {
            if (executor != null) {
                completableFuture.andThen(new RxExecutionCallback<T>(subscriber), executor);
            } else {
                completableFuture.andThen(new RxExecutionCallback<T>(subscriber));
            }
        }
    }

    private static class RxExecutionCallback<T> implements ExecutionCallback<T> {

        private final Subscriber<? super T> subscriber;

        private RxExecutionCallback(final Subscriber<? super T> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void onResponse(T response) {
            if (isSubscribed(subscriber)) {
                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        }

        @Override
        public void onFailure(Throwable t) {
            if (isSubscribed(subscriber)) {
                subscriber.onError(t);
            }
        }

        private static boolean isSubscribed(Subscriber<?> subscriber) {
            return !subscriber.isUnsubscribed();
        }

    }

}