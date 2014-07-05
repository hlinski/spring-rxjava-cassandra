package com.hlinski.service;

import com.hlinski.model.TimeSeriesData;
import com.hlinski.model.Tuple;
import org.joda.time.LocalDate;
import rx.Observable;

/**
 * Created by hlinski on 6/28/14.
 */
public interface PersonWellnessDailyService {

    void createTimeSeriesData(TimeSeriesData personData);

    Observable<Tuple> averageTimeSeriesByDay(LocalDate startDate, LocalDate endDate);
}
