package com.hlinski.model;

import org.joda.time.LocalDate;

/**
 * Created by hlinski on 6/28/14.
 */
public class TimeSeriesData {

    private Long id;
    private Integer groupId;
    private Integer data;
    private LocalDate logDate;

    public TimeSeriesData() {
    }

    public TimeSeriesData(Long id, Integer groupId, Integer data, LocalDate logDate) {
        this.id = id;
        this.groupId = groupId;
        this.data = data;
        this.logDate = logDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }
}
