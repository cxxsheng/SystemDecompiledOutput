package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
final class EnvironmentImpl implements com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment {
    private static final java.lang.String TIMEZONE_PROPERTY = "persist.sys.timezone";

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    EnvironmentImpl(@android.annotation.NonNull android.os.Handler handler) {
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    @android.annotation.NonNull
    public java.lang.String getDeviceTimeZone() {
        return android.os.SystemProperties.get(TIMEZONE_PROPERTY);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public int getDeviceTimeZoneConfidence() {
        return com.android.server.SystemTimeZone.getTimeZoneConfidence();
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public void setDeviceTimeZoneAndConfidence(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        ((com.android.server.AlarmManagerInternal) com.android.server.LocalServices.getService(com.android.server.AlarmManagerInternal.class)).setTimeZone(str, i, str2);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public long elapsedRealtimeMillis() {
        return android.os.SystemClock.elapsedRealtime();
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public void addDebugLogEntry(@android.annotation.NonNull java.lang.String str) {
        com.android.server.SystemTimeZone.addDebugLogEntry(str);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public void dumpDebugLog(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        com.android.server.SystemTimeZone.dump(printWriter);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment
    public void runAsync(@android.annotation.NonNull java.lang.Runnable runnable) {
        this.mHandler.post(runnable);
    }
}
