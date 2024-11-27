package android.os;

/* loaded from: classes3.dex */
public final class SystemClock {
    private static final java.lang.String TAG = "SystemClock";
    private static final long sAnchorNanoTime$ravenwood = java.lang.System.nanoTime();
    private static volatile android.app.IAlarmManager sIAlarmManager;

    @dalvik.annotation.optimization.CriticalNative
    public static native long currentThreadTimeMicro();

    @dalvik.annotation.optimization.CriticalNative
    public static native long currentThreadTimeMillis();

    @dalvik.annotation.optimization.CriticalNative
    public static native long currentTimeMicro();

    @dalvik.annotation.optimization.CriticalNative
    public static native long elapsedRealtime();

    @dalvik.annotation.optimization.CriticalNative
    public static native long elapsedRealtimeNanos();

    @dalvik.annotation.optimization.CriticalNative
    public static native long uptimeMillis();

    @dalvik.annotation.optimization.CriticalNative
    public static native long uptimeNanos();

    private SystemClock() {
    }

    public static void sleep(long j) {
        long uptimeMillis = uptimeMillis();
        boolean z = false;
        long j2 = j;
        do {
            try {
                java.lang.Thread.sleep(j2);
            } catch (java.lang.InterruptedException e) {
                z = true;
            }
            j2 = (uptimeMillis + j) - uptimeMillis();
        } while (j2 > 0);
        if (z) {
            java.lang.Thread.currentThread().interrupt();
        }
    }

    public static boolean setCurrentTimeMillis(long j) {
        android.app.IAlarmManager iAlarmManager = getIAlarmManager();
        if (iAlarmManager == null) {
            android.util.Slog.e(TAG, "Unable to set RTC: mgr == null");
            return false;
        }
        try {
            return iAlarmManager.setTime(j);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to set RTC", e);
            return false;
        } catch (java.lang.SecurityException e2) {
            android.util.Slog.e(TAG, "Unable to set RTC", e2);
            return false;
        }
    }

    private static android.app.IAlarmManager getIAlarmManager() {
        if (sIAlarmManager == null) {
            sIAlarmManager = android.app.IAlarmManager.Stub.asInterface(android.os.ServiceManager.getService("alarm"));
        }
        return sIAlarmManager;
    }

    public static long uptimeMillis$ravenwood() {
        return uptimeNanos() / 1000000;
    }

    public static long uptimeNanos$ravenwood() {
        return java.lang.System.nanoTime() - sAnchorNanoTime$ravenwood;
    }

    public static java.time.Clock uptimeClock() {
        return new android.os.SimpleClock(java.time.ZoneOffset.UTC) { // from class: android.os.SystemClock.1
            @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                return android.os.SystemClock.uptimeMillis();
            }
        };
    }

    public static long elapsedRealtime$ravenwood() {
        return elapsedRealtimeNanos() / 1000000;
    }

    public static java.time.Clock elapsedRealtimeClock() {
        return new android.os.SimpleClock(java.time.ZoneOffset.UTC) { // from class: android.os.SystemClock.2
            @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                return android.os.SystemClock.elapsedRealtime();
            }
        };
    }

    public static long elapsedRealtimeNanos$ravenwood() {
        return uptimeNanos() + 3600000000000L;
    }

    public static long currentTimeMicro$ravenwood() {
        return java.lang.System.nanoTime() / 1000;
    }

    public static long currentNetworkTimeMillis() {
        android.app.timedetector.ITimeDetectorService asInterface = android.app.timedetector.ITimeDetectorService.Stub.asInterface(android.os.ServiceManager.getService("time_detector"));
        if (asInterface != null) {
            try {
                android.app.time.UnixEpochTime latestNetworkTime = asInterface.latestNetworkTime();
                if (latestNetworkTime == null) {
                    throw new java.time.DateTimeException("Network based time is not available.");
                }
                return latestNetworkTime.getUnixEpochTimeMillis() + (elapsedRealtime() - latestNetworkTime.getElapsedRealtimeMillis());
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.time.DateTimeException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
        throw new java.lang.RuntimeException(new android.os.DeadSystemException());
    }

    public static java.time.Clock currentNetworkTimeClock() {
        return new android.os.SimpleClock(java.time.ZoneOffset.UTC) { // from class: android.os.SystemClock.3
            @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                return android.os.SystemClock.currentNetworkTimeMillis();
            }
        };
    }

    public static java.time.Clock currentGnssTimeClock() {
        return new android.os.SimpleClock(java.time.ZoneOffset.UTC) { // from class: android.os.SystemClock.4
            private final android.location.ILocationManager mMgr = android.location.ILocationManager.Stub.asInterface(android.os.ServiceManager.getService("location"));

            @Override // android.os.SimpleClock, java.time.Clock, java.time.InstantSource
            public long millis() {
                try {
                    android.location.LocationTime gnssTimeMillis = this.mMgr.getGnssTimeMillis();
                    if (gnssTimeMillis == null) {
                        throw new java.time.DateTimeException("Gnss based time is not available.");
                    }
                    return gnssTimeMillis.getUnixEpochTimeMillis() + ((android.os.SystemClock.elapsedRealtimeNanos() - gnssTimeMillis.getElapsedRealtimeNanos()) / 1000000);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
    }
}
