package com.android.internal.jank;

/* loaded from: classes4.dex */
public class DisplayRefreshRate {
    public static final int REFRESH_RATE_120_HZ = 5;
    public static final int REFRESH_RATE_240_HZ = 6;
    public static final int REFRESH_RATE_30_HZ = 2;
    public static final int REFRESH_RATE_60_HZ = 3;
    public static final int REFRESH_RATE_90_HZ = 4;
    public static final int UNKNOWN_REFRESH_RATE = 0;
    public static final int VARIABLE_REFRESH_RATE = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RefreshRate {
    }

    private DisplayRefreshRate() {
    }

    public static int getRefreshRate(long j) {
        long round = java.lang.Math.round(1.0E9d / j);
        if (round < 50) {
            return 2;
        }
        if (round < 80) {
            return 3;
        }
        if (round < 110) {
            return 4;
        }
        if (round < 180) {
            return 5;
        }
        return 6;
    }
}
