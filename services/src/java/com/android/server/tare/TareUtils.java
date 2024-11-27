package com.android.server.tare;

/* loaded from: classes2.dex */
class TareUtils {

    @com.android.internal.annotations.VisibleForTesting
    static java.time.Clock sSystemClock = java.time.Clock.systemUTC();

    TareUtils() {
    }

    static long getCurrentTimeMillis() {
        return sSystemClock.millis();
    }

    static int cakeToArc(long j) {
        return (int) (j / 1000000000);
    }

    @android.annotation.NonNull
    static java.lang.String cakeToString(long j) {
        if (j == 0) {
            return "0 ARCs";
        }
        long j2 = j % 1000000000;
        long cakeToArc = cakeToArc(j);
        if (cakeToArc == 0) {
            if (j2 == 1) {
                return j2 + " cake";
            }
            return j2 + " cakes";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(cakeToArc);
        if (j2 != 0) {
            sb.append(".");
            sb.append(java.lang.String.format("%03d", java.lang.Long.valueOf(java.lang.Math.abs(j2) / 1000000)));
        }
        sb.append(" ARC");
        if (cakeToArc != 1 || j2 != 0) {
            sb.append("s");
        }
        return sb.toString();
    }

    @android.annotation.NonNull
    static java.lang.String appToString(int i, java.lang.String str) {
        return "<" + i + ">" + str;
    }
}
