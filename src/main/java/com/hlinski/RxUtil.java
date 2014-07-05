package com.hlinski;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;

import java.util.Collection;

/**
 * Created by hlinski on 7/2/14.
 */
public class RxUtil {
    protected final static Logger log = LoggerFactory.getLogger(RxUtil.class);

    public static <T> Observable<T> listToObservable(final ListenableFuture<? extends Collection<T>> futures) {

        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                Futures.addCallback(futures, new FutureCallback<Collection<T>>() {
                    public void onSuccess(Collection<T> elements) {
                        for (T item : elements) {
                            if (subscriber.isUnsubscribed()) {
                                return;
                            }
                            subscriber.onNext(item);
                        }
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    }

                    public void onFailure(Throwable thrown) {
                        log.error("Error during transformation Future with array values to Observable", thrown);
                    }
                });
            }
        });
    }

    public static <T> Observable<T> toObservable(final ListenableFuture<T> future) {

        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                Futures.addCallback(future, new FutureCallback<T>() {
                    public void onSuccess(T explosion) {
                        subscriber.onNext(explosion);
                        if (subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    }

                    public void onFailure(Throwable thrown) {
                        log.error("Error during transformation Future to Observable", thrown);
                    }
                });
            }
        });
    }
}
