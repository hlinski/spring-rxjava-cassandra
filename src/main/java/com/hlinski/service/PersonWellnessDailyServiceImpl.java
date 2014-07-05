package com.hlinski.service;

import com.hlinski.dao.PersonWellnessDailyDao;
import com.hlinski.func.CustomMathObservable;
import com.hlinski.model.TimeSeriesData;
import com.hlinski.model.Tuple;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

/**
 * Created by hlinski on 6/28/14.
 */
@Service
public class PersonWellnessDailyServiceImpl implements PersonWellnessDailyService {

    @Autowired
    private PersonWellnessDailyDao personWellnessDailyDao;

    @Override
    public void createTimeSeriesData(TimeSeriesData personData) {
        personWellnessDailyDao.save(personData);
    }

    @Override
    public Observable<Tuple> averageTimeSeriesByDay(LocalDate startDate, LocalDate endDate) {
        Observable<TimeSeriesData> timeSeriesDataObservable = personWellnessDailyDao.findTimeSeries(startDate, endDate);
        return timeSeriesDataObservable.map(new Func1<TimeSeriesData, Tuple>() {
            @Override
            public Tuple call(TimeSeriesData timeSeriesData) {

                return new Tuple(timeSeriesData.getLogDate().toString(), (double)timeSeriesData.getData());
            }
        }).groupBy(new Func1<Tuple, String>() {
            @Override
            public String call(Tuple tuple) {
                return tuple.getKey();
            }
        }).flatMap(new Func1<GroupedObservable<String, Tuple>, GroupedObservable<String, Tuple>>() {
            @Override
            public GroupedObservable<String, Tuple> call(GroupedObservable<String, Tuple> tupleGroupedObservable) {

                return GroupedObservable.from(tupleGroupedObservable.getKey(), CustomMathObservable.averageTuple(tupleGroupedObservable));
            }
        });

    }
}
