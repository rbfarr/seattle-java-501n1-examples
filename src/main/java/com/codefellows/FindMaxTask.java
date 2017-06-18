package com.codefellows;

import java.util.Random;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Brad on 6/17/2017.
 */
public class FindMaxTask extends RecursiveTask<Integer> {
    private static int[] data = new int[1000]; //1G

    static {
        for (int i = 0; i < data.length; i++) {
            data[i] = (new Random()).nextInt(1000);
        }
    }

    public static int[] getData() {
        return data;
    }

    private int threshold;
    private int start;
    private int end;

    public FindMaxTask(int start, int end, int threshold) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    @Override
    protected Integer compute() {
        if (end - start < threshold) { // around 64MB
            int max = Integer.MIN_VALUE;

            for (int i = start; i <= end; i++) {
                int n = data[i];
                if (n > max) {
                    max = n;
                }
            }

            return max;
        } else {
            int midway = (end - start) / 2 + start;

            FindMaxTask a1 = new FindMaxTask(start, midway, threshold);
            a1.fork();
            FindMaxTask a2 = new FindMaxTask(midway + 1, end, threshold);

            return Math.max(a2.compute(), a1.join());
        }
    }
}
