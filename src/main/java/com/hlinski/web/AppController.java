package com.hlinski.web;

import com.datastax.driver.core.Session;
import com.google.common.collect.Lists;
import com.hlinski.model.TimeSeriesData;
import com.hlinski.model.Tuple;
import com.hlinski.service.PersonWellnessDailyService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.functions.Action1;

import java.util.List;

/**
 * Created by hlinski on 6/28/14.
 */
@RestController
public class AppController {

    @Autowired
    private Session session;

    @Autowired
    private PersonWellnessDailyService personWellnessDailyService;

    @RequestMapping("/average/day")
    @ResponseBody
    public DeferredResult<List<Tuple>> getAverageByDay() {
        final DeferredResult<List<Tuple>> result = new DeferredResult<List<Tuple>>();
        final List<Tuple> resultList = Lists.newArrayList();
        Observable<Tuple> groups = personWellnessDailyService.averageTimeSeriesByDay(LocalDate.now().minusDays(360), LocalDate.now());
        groups.subscribe(new Action1<Tuple>() {
            @Override
            public void call(Tuple tuple) {
                resultList.add(tuple);
                result.setResult(resultList);
            }
        });
        return result;
    }

    @RequestMapping(value = "/create/{personId}/{groupId}/{value}/{date}", method = RequestMethod.POST)
    public Integer saveDailyData(@PathVariable Long personId, @PathVariable Integer groupId,
                     @PathVariable Integer value, @PathVariable String date) {
        personWellnessDailyService.createTimeSeriesData(new TimeSeriesData(personId, groupId, value, LocalDate.parse(date)));
        return 0;
    }

}
