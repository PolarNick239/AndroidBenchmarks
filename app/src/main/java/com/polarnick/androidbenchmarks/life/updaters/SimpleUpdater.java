package com.polarnick.androidbenchmarks.life.updaters;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Polyarniy Nikolay, 03.12.16
 */

public class SimpleUpdater extends Updater {

    private int[] state = null;
    private int[] nextState = null;

    @Override
    public String getName() {
        return "Simple";
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
    public int[] next() {
        update();
        swapBuffers();
        return state;
    }

    private void swapBuffers() {
        int[] tmp = state;
        state = nextState;
        nextState = tmp;
    }

    private void update() {
        update(state, nextState, width, height, n);
    }

    private static void update(int[] cur, int[] next, int width, int height, int n) {
        int dx[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int dy[] = {-1, -1, -1, 0, 1, 1, 1, 0};
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                boolean succeeded = false;

                for (int i = 0; i < dx.length; ++i) {
                    int neighbourX = x + dx[i];
                    int neighbourY = y + dy[i];

                    if (...) { // 1.1 TODO проверить 'поглощает' ли сосед текущую клетку?
                        succeeded = true;
                    }
                }

                // 1.2 TODO посчитать состояние текущей клетки в следующий момент времени
                next[width * y + x] = ...;
            }
        }
    }

}
