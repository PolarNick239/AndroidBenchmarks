package com.polarnick.androidbenchmarks.life.updaters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Polyarniy Nikolay, 03.12.16
 */

public abstract class Updater {

    protected int n = 0;
    protected int width = 0;
    protected int height = 0;

    static final int nthreads = 5 * Runtime.getRuntime().availableProcessors();
    static final ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    public abstract String getName();

    public abstract int[] next() throws InterruptedException;

    public abstract void cleanup();

    public void setup(int width, int height, int n) {
        this.width = width;
        this.height = height;
        this.n = n;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}