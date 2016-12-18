package com.polarnick.androidbenchmarks.life;

import com.polarnick.androidbenchmarks.life.updaters.SimpleUpdater;
import com.polarnick.androidbenchmarks.life.updaters.Updater;

/**
 * Polyarniy Nikolay, 18.12.16
 */
public class SimpleUpdaterTest extends UpdaterTest {

    @Override
    public Updater newUpdater() {
        return new SimpleUpdater();
    }

}