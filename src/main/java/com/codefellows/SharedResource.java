package com.codefellows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Brad on 6/17/2017.
 */
public class SharedResource {
    private static int i = 0;

    private Integer x = 0;
    private Integer y = 0;

    private AtomicInteger z = new AtomicInteger(5);
    //private Integer z = 5;

    private List<Integer> syncInts = Collections.synchronizedList(new ArrayList<>());


    public int getAndIncrementZ() {
        return z.getAndIncrement();
    }

    public int getAndIncrementX() {
        System.out.println("thread entered async method X");


            synchronized (this) {
                return x++;
            }

    }

    public int getAndIncrementY() {
        System.out.println("thread entered async method Y");


            synchronized (this) {
                return y++;
            }

    }

    public synchronized int getAndIncrementI() {
        return i++;
    }
}
