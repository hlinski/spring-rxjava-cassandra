package com.hlinski.func;

import com.hlinski.model.Tuple;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hlinski on 7/3/14.
 */
public class OperatorAverageTuple <T> implements Observable.Operator<Tuple, T> {
    final Func1<T, Tuple> valueExtractor;

    public OperatorAverageTuple(Func1<T, Tuple> valueExtractor) {
        this.valueExtractor = valueExtractor;
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super Tuple> child) {
        return new AverageObserver(child);
    }

    /** Computes the average. */
    private final class AverageObserver extends Subscriber<T> {
        final Subscriber<? super Tuple> child;
        Tuple sum;
        int count;

        public AverageObserver(Subscriber<? super Tuple> subscriber) {
            super(subscriber);
            this.child = subscriber;
        }

        @Override
        public void onNext(T args) {
            if(sum == null){
                sum = new Tuple(valueExtractor.call(args).getKey(), valueExtractor.call(args).getValue());
            }else {
                sum.setValue(sum.getValue() + valueExtractor.call(args).getValue());
            }
            count++;
        }

        @Override
        public void onError(Throwable e) {
            child.onError(e);
        }

        @Override
        public void onCompleted() {
            if (count > 0) {
                try {
                    sum.setValue(sum.getValue() / count);
                    child.onNext(sum);
                } catch (Throwable t) {
                    child.onError(t);
                    return;
                }
                child.onCompleted();
            } else {
                child.onError(new IllegalArgumentException("Sequence contains no elements"));
            }
        }

    }

}

