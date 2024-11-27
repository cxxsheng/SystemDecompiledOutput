package com.android.server.people.data;

/* loaded from: classes2.dex */
public class AppUsageStatsData {
    private int mChosenCount;
    private int mLaunchCount;

    @com.android.internal.annotations.VisibleForTesting
    public AppUsageStatsData(int i, int i2) {
        this.mChosenCount = i;
        this.mLaunchCount = i2;
    }

    public AppUsageStatsData() {
    }

    public int getLaunchCount() {
        return this.mLaunchCount;
    }

    void incrementLaunchCountBy(int i) {
        this.mLaunchCount += i;
    }

    public int getChosenCount() {
        return this.mChosenCount;
    }

    void incrementChosenCountBy(int i) {
        this.mChosenCount += i;
    }
}
