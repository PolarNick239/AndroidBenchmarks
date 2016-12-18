package com.polarnick.androidbenchmarks.life;

import com.polarnick.androidbenchmarks.life.updaters.MultithreadedUpdater;
import com.polarnick.androidbenchmarks.life.updaters.Updater;

/**
 * Polyarniy Nikolay, 18.12.16
 */
public class MultithreadedUpdaterTest extends UpdaterTest {

    @Override
    public Updater newUpdater() {
        return new MultithreadedUpdater();
    }

}