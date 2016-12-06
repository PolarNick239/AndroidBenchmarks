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

        // 1.0 TODO заполнить this.state случайными числами - класс Random (ВНЕЗАПНО)
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
        // 1.1 TODO посчитать следующее состояние клеточного автомата next по текущему состоянию cur
        ...
    }

}
