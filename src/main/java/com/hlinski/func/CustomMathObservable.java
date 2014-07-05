package com.hlinski.func;

import com.hlinski.func.OperatorAverageTuple;
import com.hlinski.model.Tuple;
import rx.Observable;
import rx.functions.Functions;

/**
 * Created by hlinski on 7/4/14.
 */
public class CustomMathObservable {

    public final static Observable<Tuple> averageTuple(Observable<Tuple> source) {
        return source.lift(new OperatorAverageTuple<Tuple>(Functions.<Tuple>identity()));
    }
}
