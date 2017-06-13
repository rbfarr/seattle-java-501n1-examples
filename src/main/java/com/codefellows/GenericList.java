package com.codefellows;

import java.util.List;

/**
 * Created by Brad on 6/12/2017.
 */
public class GenericList<T> {
    private List<T> list;

    public GenericList(List<T> list) {
        this.list = list;
    }

    public void add(T item) {
        list.add(item);
    }
}
