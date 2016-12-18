package com.polarnick.androidbenchmarks.life.updaters;

/**
 * Polyarniy Nikolay, 18.12.16
 */
public class SimpleUpdaterTest extends UpdaterTest {

    @Override
    public Updater newUpdater() {
        return new SimpleUpdater();
    }

}