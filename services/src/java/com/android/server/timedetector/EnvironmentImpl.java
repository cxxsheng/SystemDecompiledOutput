package com.android.server.timedetector;

/* loaded from: classes2.dex */
final class EnvironmentImpl implements com.android.server.timedetector.TimeDetectorStrategyImpl.Environment {
    private static final java.lang.String LOG_TAG = "time_detector";

    @android.annotation.NonNull
    private final com.android.server.AlarmManagerInternal mAlarmManagerInternal;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final android.os.PowerManager.WakeLock mWakeLock;

    EnvironmentImpl(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler) {
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
        android.os.PowerManager.WakeLock newWakeLock = ((android.os.PowerManager) context.getSystemService(android.os.PowerManager.class)).newWakeLock(1, LOG_TAG);
        java.util.Objects.requireNonNull(newWakeLock);
        this.mWakeLock = newWakeLock;
        com.android.server.AlarmManagerInternal alarmManagerInternal = (com.android.server.AlarmManagerInternal) com.android.server.LocalServices.getService(com.android.server.AlarmManagerInternal.class);
        java.util.Objects.requireNonNull(alarmManagerInternal);
        this.mAlarmManagerInternal = alarmManagerInternal;
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void acquireWakeLock() {
        if (this.mWakeLock.isHeld()) {
            android.util.Slog.wtf(LOG_TAG, "WakeLock " + this.mWakeLock + " already held");
        }
        this.mWakeLock.acquire();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public long elapsedRealtimeMillis() {
        return android.os.SystemClock.elapsedRealtime();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public long systemClockMillis() {
        return java.lang.System.currentTimeMillis();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public int systemClockConfidence() {
        return com.android.server.SystemClockTime.getTimeConfidence();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void setSystemClock(long j, int i, @android.annotation.NonNull java.lang.String str) {
        checkWakeLockHeld();
        this.mAlarmManagerInternal.setTime(j, i, str);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void setSystemClockConfidence(int i, @android.annotation.NonNull java.lang.String str) {
        checkWakeLockHeld();
        com.android.server.SystemClockTime.setConfidence(i, str);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void releaseWakeLock() {
        checkWakeLockHeld();
        this.mWakeLock.release();
    }

    private void checkWakeLockHeld() {
        if (!this.mWakeLock.isHeld()) {
            android.util.Slog.wtf(LOG_TAG, "WakeLock " + this.mWakeLock + " not held");
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void addDebugLogEntry(@android.annotation.NonNull java.lang.String str) {
        com.android.server.SystemClockTime.addDebugLogEntry(str);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void dumpDebugLog(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter) {
        long elapsedRealtimeMillis = elapsedRealtimeMillis();
        indentingPrintWriter.printf("elapsedRealtimeMillis()=%s (%s)\n", new java.lang.Object[]{java.time.Duration.ofMillis(elapsedRealtimeMillis), java.lang.Long.valueOf(elapsedRealtimeMillis)});
        long systemClockMillis = systemClockMillis();
        indentingPrintWriter.printf("systemClockMillis()=%s (%s)\n", new java.lang.Object[]{java.time.Instant.ofEpochMilli(systemClockMillis), java.lang.Long.valueOf(systemClockMillis)});
        indentingPrintWriter.println("systemClockConfidence()=" + systemClockConfidence());
        indentingPrintWriter.println("SystemClockTime debug log:");
        indentingPrintWriter.increaseIndent();
        com.android.server.SystemClockTime.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategyImpl.Environment
    public void runAsync(@android.annotation.NonNull java.lang.Runnable runnable) {
        this.mHandler.post(runnable);
    }
}
