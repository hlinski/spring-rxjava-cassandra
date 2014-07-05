package com.hlinski.dao;

import com.hlinski.model.TimeSeriesData;
import org.joda.time.LocalDate;
import rx.Observable;

/**
 * Created by hlinski on 6/28/14.
 */
public interface PersonWellnessDailyDao {

    void save(TimeSeriesData personData);

    Observable<TimeSeriesData> findTimeSeries(LocalDate startDate, LocalDate endDate);


}
