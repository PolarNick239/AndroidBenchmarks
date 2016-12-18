package com.polarnick.androidbenchmarks.life.updaters;

/**
 * Polyarniy Nikolay, 18.12.16
 */
public class TunedUpdaterTest extends UpdaterTest {

    @Override
    public Updater newUpdater() {
        return new TunedUpdater();
    }

}