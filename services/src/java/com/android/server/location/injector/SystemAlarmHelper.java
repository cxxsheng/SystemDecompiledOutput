package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemAlarmHelper extends com.android.server.location.injector.AlarmHelper {
    private final android.content.Context mContext;

    public SystemAlarmHelper(android.content.Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.location.injector.AlarmHelper
    public void setDelayedAlarmInternal(long j, android.app.AlarmManager.OnAlarmListener onAlarmListener, android.os.WorkSource workSource) {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        java.util.Objects.requireNonNull(alarmManager);
        alarmManager.set(2, android.os.SystemClock.elapsedRealtime() + j, 0L, 0L, onAlarmListener, com.android.server.FgThread.getHandler(), workSource);
    }

    @Override // com.android.server.location.injector.AlarmHelper
    public void cancel(android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        java.util.Objects.requireNonNull(alarmManager);
        alarmManager.cancel(onAlarmListener);
    }
}
