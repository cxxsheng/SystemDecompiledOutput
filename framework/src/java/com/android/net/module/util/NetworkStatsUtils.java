package com.android.net.module.util;

/* loaded from: classes5.dex */
public class NetworkStatsUtils {
    private static final int DEFAULT_NETWORK_ALL = -1;
    public static final java.lang.String LIMIT_GLOBAL_ALERT = "globalAlert";
    private static final int METERED_ALL = -1;
    private static final int ROAMING_ALL = -1;
    private static final int SET_ALL = -1;
    public static final int SUBSCRIBER_ID_MATCH_RULE_ALL = 1;
    public static final int SUBSCRIBER_ID_MATCH_RULE_EXACT = 0;

    public static long multiplySafeByRational(long j, long j2, long j3) {
        if (j3 == 0) {
            throw new java.lang.ArithmeticException("Invalid Denominator");
        }
        long j4 = j * j2;
        if (((java.lang.Math.abs(j) | java.lang.Math.abs(j2)) >>> 31) != 0 && ((j2 != 0 && j4 / j2 != j) || (j == Long.MIN_VALUE && j2 == -1))) {
            return (long) ((j2 / j3) * j);
        }
        return j4 / j3;
    }

    public static int constrain(int i, int i2, int i3) {
        if (i2 <= i3) {
            return i < i2 ? i2 : i > i3 ? i3 : i;
        }
        throw new java.lang.IllegalArgumentException("low(" + i2 + ") > high(" + i3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    public static long constrain(long j, long j2, long j3) {
        if (j2 <= j3) {
            return j < j2 ? j2 : j > j3 ? j3 : j;
        }
        throw new java.lang.IllegalArgumentException("low(" + j2 + ") > high(" + j3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    public static android.net.NetworkStats fromPublicNetworkStats(android.app.usage.NetworkStats networkStats) {
        android.net.NetworkStats networkStats2 = new android.net.NetworkStats(0L, 0);
        while (networkStats.hasNextBucket()) {
            android.app.usage.NetworkStats.Bucket bucket = new android.app.usage.NetworkStats.Bucket();
            networkStats.getNextBucket(bucket);
            networkStats2 = networkStats2.addEntry(fromBucket(bucket));
        }
        return networkStats2;
    }

    public static android.net.NetworkStats.Entry fromBucket(android.app.usage.NetworkStats.Bucket bucket) {
        return new android.net.NetworkStats.Entry((java.lang.String) null, bucket.getUid(), convertBucketState(bucket.getState()), convertBucketTag(bucket.getTag()), convertBucketMetered(bucket.getMetered()), convertBucketRoaming(bucket.getRoaming()), convertBucketDefaultNetworkStatus(bucket.getDefaultNetworkStatus()), bucket.getRxBytes(), bucket.getRxPackets(), bucket.getTxBytes(), bucket.getTxPackets(), 0L);
    }

    private static int convertBucketState(int i) {
        switch (i) {
        }
        return 0;
    }

    private static int convertBucketTag(int i) {
        switch (i) {
            case 0:
                return 0;
            default:
                return i;
        }
    }

    private static int convertBucketMetered(int i) {
        switch (i) {
        }
        return 0;
    }

    private static int convertBucketRoaming(int i) {
        switch (i) {
        }
        return 0;
    }

    private static int convertBucketDefaultNetworkStatus(int i) {
        switch (i) {
        }
        return 0;
    }
}
