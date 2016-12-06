package com.polarnick.androidbenchmarks.life.updaters;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Polyarniy Nikolay, 03.12.16
 */

public class MultithreadedUpdater extends Updater {

    private int[] state = null;
    private int[] nextState = null;

    static final int nthreads = Runtime.getRuntime().availableProcessors() + 1; // узнаем число доступных ядер процессоров
    static final ExecutorService executors = Executors.newFixedThreadPool(nthreads); // создаем потоки для вычислений

    @Override
    public String getName() {
        return "x" + nthreads + " Multithreaded";
    }

    @Override
    public void setup(int width, int height, int n) {
        super.setup(width, height, n);

        this.state = new int[height * width];
        this.nextState = new int[height * width];

        // 2.0 TODO заполнить this.state случайными числами (как и в SimpleUpdater)
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
        // 2.2 TODO заменить однопоточные вычисления многопоточными - считая в каждом потоке ровно его часть работы
        // полезные классы: ExecutorService (см this.executors) и CountDownLatch

        // ...

        // теперь нужно как-то дождаться выполнения всех частей работы во всех использованных потоках, возможно поможет CountDownLatch?
    }

    private static void update(int[] cur, int[] next, int width, int height, int n) {

        throw new UnsupportedOperationException();

        // 2.1 TODO адаптировать эту функцию так, чтобы вычислительную работу можно было распараллелить (передавать через аргументы область работы)

        // скопировать сюда код из SimpleUpdater.update(...)
    }

}
