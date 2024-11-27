package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class LocationUsageLogger {
    private static final int API_USAGE_LOG_HOURLY_CAP = 60;
    private static final int ONE_HOUR_IN_MILLIS = 3600000;
    private static final int ONE_MINUTE_IN_MILLIS = 60000;
    private static final int ONE_SEC_IN_MILLIS = 1000;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mLastApiUsageLogHour = 0;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mApiUsageLogHourlyCount = 0;

    /* JADX WARN: Removed duplicated region for block: B:29:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0073 A[Catch: Exception -> 0x005e, TryCatch #0 {Exception -> 0x005e, blocks: (B:2:0x0000, B:27:0x0064, B:30:0x007d, B:33:0x0073, B:34:0x0054, B:36:0x0047, B:37:0x0037, B:38:0x002a, B:39:0x0021, B:40:0x0018), top: B:1:0x0000 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void logLocationApiUsage(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, android.location.LocationRequest locationRequest, boolean z, boolean z2, android.location.Geofence geofence, boolean z3) {
        int bucketizeProvider;
        int quality;
        int bucketizeInterval;
        int bucketizeDistance;
        int i3;
        int bucketizeRadius;
        try {
            if (hitApiUsageLogCap()) {
                return;
            }
            boolean z4 = locationRequest == null;
            boolean z5 = geofence == null;
            if (z4) {
                bucketizeProvider = 0;
            } else {
                bucketizeProvider = bucketizeProvider(str3);
            }
            if (z4) {
                quality = 0;
            } else {
                quality = locationRequest.getQuality();
            }
            if (z4) {
                bucketizeInterval = 0;
            } else {
                bucketizeInterval = bucketizeInterval(locationRequest.getIntervalMillis());
            }
            if (z4) {
                bucketizeDistance = 0;
            } else {
                bucketizeDistance = bucketizeDistance(locationRequest.getMinUpdateDistanceMeters());
            }
            long maxUpdates = z4 ? 0L : locationRequest.getMaxUpdates();
            if (!z4 && i != 1) {
                i3 = bucketizeExpireIn(locationRequest.getDurationMillis());
                int callbackType = getCallbackType(i2, z, z2);
                if (!z5) {
                    bucketizeRadius = 0;
                } else {
                    bucketizeRadius = bucketizeRadius(geofence.getRadius());
                }
                com.android.internal.util.FrameworkStatsLog.write(210, i, i2, str, bucketizeProvider, quality, bucketizeInterval, bucketizeDistance, maxUpdates, i3, callbackType, bucketizeRadius, categorizeActivityImportance(z3), str2);
            }
            i3 = 0;
            int callbackType2 = getCallbackType(i2, z, z2);
            if (!z5) {
            }
            com.android.internal.util.FrameworkStatsLog.write(210, i, i2, str, bucketizeProvider, quality, bucketizeInterval, bucketizeDistance, maxUpdates, i3, callbackType2, bucketizeRadius, categorizeActivityImportance(z3), str2);
        } catch (java.lang.Exception e) {
            android.util.Log.w(com.android.server.location.LocationManagerService.TAG, "Failed to log API usage to statsd.", e);
        }
    }

    public void logLocationApiUsage(int i, int i2, java.lang.String str) {
        try {
            if (hitApiUsageLogCap()) {
                return;
            }
            com.android.internal.util.FrameworkStatsLog.write(210, i, i2, (java.lang.String) null, bucketizeProvider(str), 0, 0, 0, 0L, 0, getCallbackType(i2, true, true), 0, 0, (java.lang.String) null);
        } catch (java.lang.Exception e) {
            android.util.Log.w(com.android.server.location.LocationManagerService.TAG, "Failed to log API usage to statsd.", e);
        }
    }

    public synchronized void logLocationEnabledStateChanged(boolean z) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.LOCATION_ENABLED_STATE_CHANGED, z);
    }

    public synchronized void logEmergencyStateChanged(boolean z) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.EMERGENCY_STATE_CHANGED, z);
    }

    private static int bucketizeProvider(java.lang.String str) {
        if ("network".equals(str)) {
            return 1;
        }
        if ("gps".equals(str)) {
            return 2;
        }
        if ("passive".equals(str)) {
            return 3;
        }
        if ("fused".equals(str)) {
            return 4;
        }
        return 0;
    }

    private static int bucketizeInterval(long j) {
        if (j < 1000) {
            return 1;
        }
        if (j < 5000) {
            return 2;
        }
        if (j < 60000) {
            return 3;
        }
        if (j < 600000) {
            return 4;
        }
        if (j < 3600000) {
            return 5;
        }
        return 6;
    }

    private static int bucketizeDistance(float f) {
        if (f <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return 1;
        }
        if (f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f <= 100.0f) {
            return 2;
        }
        return 3;
    }

    private static int bucketizeRadius(float f) {
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return 7;
        }
        if (f < 100.0f) {
            return 1;
        }
        if (f < 200.0f) {
            return 2;
        }
        if (f < 300.0f) {
            return 3;
        }
        if (f < 1000.0f) {
            return 4;
        }
        if (f < 10000.0f) {
            return 5;
        }
        return 6;
    }

    private static int bucketizeExpireIn(long j) {
        if (j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            return 6;
        }
        if (j < 20000) {
            return 1;
        }
        if (j < 60000) {
            return 2;
        }
        if (j < 600000) {
            return 3;
        }
        if (j < 3600000) {
            return 4;
        }
        return 5;
    }

    private static int categorizeActivityImportance(boolean z) {
        if (z) {
            return 1;
        }
        return 3;
    }

    private static int getCallbackType(int i, boolean z, boolean z2) {
        if (i == 5) {
            return 1;
        }
        if (z2) {
            return 3;
        }
        if (z) {
            return 2;
        }
        return 0;
    }

    private synchronized boolean hitApiUsageLogCap() {
        long epochMilli = java.time.Instant.now().toEpochMilli() / 3600000;
        if (epochMilli > this.mLastApiUsageLogHour) {
            this.mLastApiUsageLogHour = epochMilli;
            this.mApiUsageLogHourlyCount = 0;
            return false;
        }
        this.mApiUsageLogHourlyCount = java.lang.Math.min(this.mApiUsageLogHourlyCount + 1, 60);
        return this.mApiUsageLogHourlyCount >= 60;
    }
}
