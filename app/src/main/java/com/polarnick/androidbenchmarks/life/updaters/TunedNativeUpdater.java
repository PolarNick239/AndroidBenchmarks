package com.polarnick.androidbenchmarks.life.updaters;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Polyarniy Nikolay, 04.12.16
 */

public class TunedNativeUpdater extends Updater {

    private int[] state = null;
    private int[] nextState = null;

    @Override
    public String getName() {
        return "Tuned Native (" + nthreads + " threads)";
    }

    @Override
    public void setup(int width, int height, int n) {
        super.setup(width, height, n);
        this.state = new int[height * width];
        this.nextState = new int[height * width];

        Random r = new Random(239);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                state[y * width + x] = r.nextInt(n);
            }
        }
    }

    @Override
    public void cleanup() {
        this.width = 0;
        this.height = 0;
        this.n = 0;
        this.state = null;
        this.nextState = null;
    }

    @Override
    public int[] next() throws InterruptedException {
        update();
        swapBuffers();
        return state;
    }

    private void swapBuffers() {
        int[] tmp = state;
        state = nextState;
        nextState = tmp;
    }

    private void update() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(nthreads);
        for (int i = 0; i < nthreads; ++i) {
            final int row0 = (height * i) / nthreads;
            final int row1 = (height * (i + 1)) / nthreads;
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    updatePartTuned(state, nextState, width, height, n,
                            row0, row1, 0, width);
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    public native void updatePartTuned(int[] cur, int[] next, int width, int height, int n,
                                       int row0, int row1, int col0, int col1);

}
