package com.hlinski.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.hlinski.RxUtil;
import com.hlinski.model.TimeSeriesData;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.List;

/**
 * Created by hlinski on 6/28/14.
 */
@Component
public class PersonWellnessDailyDaoImpl implements PersonWellnessDailyDao {
    @Autowired
    private Session session;
    @Override
    public void save(TimeSeriesData personData) {
        Insert insert = QueryBuilder.insertInto("user_data")
                .value("personId", personData.getId())
                .value("groupId", personData.getGroupId())
                .value("data", personData.getData())
                .value("logDate", personData.getLogDate().toDate());
        session.execute(insert);
    }

    @Override
    public Observable<TimeSeriesData> findTimeSeries(LocalDate startDate, LocalDate endDate) {
        String query = "select * from user_data where logDate >= '%s' " +
                "and logDate < '%s' ALLOW FILTERING;";

        ResultSetFuture future = session.executeAsync(String.format(query, startDate, endDate));

        return RxUtil.listToObservable(Futures.transform(future, new Function<ResultSet, List<TimeSeriesData>>() {
            @Override
            public List<TimeSeriesData> apply(ResultSet resultSet) {
                List<TimeSeriesData> result = Lists.newArrayList();
                for (Row row : resultSet.all()) {
                    TimeSeriesData item = new TimeSeriesData();
                    item.setId(row.getLong("personId"));
                    item.setData(row.getInt("data"));
                    item.setGroupId(row.getInt("groupId"));
                    item.setLogDate(LocalDate.fromDateFields(row.getDate("logDate")));
                    result.add(item);
                }
                return result;
            }
        }));
    }
}
