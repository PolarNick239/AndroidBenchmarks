package com.polarnick.androidbenchmarks.life.updaters;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Polyarniy Nikolay, 03.12.16
 */

public class TunedUpdater extends Updater {

    private int[] state = null;
    private int[] nextState = null;

    @Override
    public String getName() {
        return "Tuned (" + nthreads + " threads)";
    }

    @Override
    public void setup(int width, int height, int n) {
        super.setup(width, height, n);
        this.state = new int[height * width];
        this.nextState = new int[height * width];

        Random r = new Random(239);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                state[width * y + x] = r.nextInt(n);
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
                    update(state, nextState, width, height, n,
                            row0, row1, 0, width);
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    private static void update(int[] cur, int[] next, int width, int height, int n,
                               int row0, int row1, int col0, int col1) {
        int dx[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int dy[] = {-1, -1, -1, 0, 1, 1, 1, 0};

        // Tunes:
        // 1. Remove branching (corner cases to separate loops)
        // 2. Calculate next state once (not for every neighbour)
        // 3. Break on first succeeding
        for (int y = Math.max(1, row0); y < Math.min(height - 1, row1); ++y) {
            for (int x = Math.max(1, col0); x < Math.min(width - 1, col1); ++x) {
                boolean succeeded = false;

                int toSucceed = (cur[width * y + x] + 1) % n;
                for (int i = 0; i < dx.length; ++i) {
                    if (cur[width * (y + dy[i]) + x + dx[i]] == toSucceed) {
                        succeeded = true;
                        break;
                    }
                }

                if (succeeded) {
                    next[width * y + x] = toSucceed;
                } else {
                    next[width * y + x] = cur[width * y + x];
                }
            }
        }

        // Corner cases
        for (int y : new int[]{0, height - 1}) {
            for (int x : new int[]{0, width - 1}) {
                boolean succeeded = false;

                for (int i = 0; i < dx.length; ++i) {
                    if (x + dx[i] < 0 || x + dx[i] >= width || y + dy[i] < 0 || y + dy[i] >= height) {
                        continue;
                    }
                    if (cur[width * (y + dy[i]) + x + dx[i]] == (cur[width * y + x] + 1) % n) {
                        succeeded = true;
                    }
                }

                if (succeeded) {
                    next[width * y + x] = (cur[width * y + x] + 1) % n;
                } else {
                    next[width * y + x] = cur[width * y + x];
                }
            }
        }
    }

}
