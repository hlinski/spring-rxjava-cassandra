package com.hlinski.model;

/**
 * Created by hlinski on 7/3/14.
 */
public class Tuple {
    private String key;
    private Double value = 0d;

    public Tuple() {
    }

    public Tuple(String key, Double value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
