package com.android.server.job.controllers.idle;

/* loaded from: classes2.dex */
public final class DeviceIdlenessTracker extends android.content.BroadcastReceiver implements com.android.server.job.controllers.idle.IdlenessTracker {
    private static final boolean DEBUG;
    private static final java.lang.String IC_DIT_CONSTANT_PREFIX = "ic_dit_";
    private static final java.lang.String KEY_IDLE_WINDOW_SLOP_MS = "ic_dit_idle_window_slop_ms";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_INACTIVITY_IDLE_THRESHOLD_MS = "ic_dit_inactivity_idle_threshold_ms";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_INACTIVITY_STABLE_POWER_IDLE_THRESHOLD_MS = "ic_dit_inactivity_idle_stable_power_threshold_ms";
    private static final java.lang.String TAG = "JobScheduler.DeviceIdlenessTracker";
    private android.app.AlarmManager mAlarm;
    private boolean mDockIdle;
    private boolean mIdle;
    private com.android.server.job.controllers.idle.IdlenessListener mIdleListener;
    private long mIdleWindowSlop;
    private long mInactivityIdleThreshold;
    private long mInactivityStablePowerIdleThreshold;
    private boolean mIsStablePower;
    private android.os.PowerManager mPowerManager;
    private boolean mProjectionActive;
    private long mIdlenessCheckScheduledElapsed = -1;
    private long mIdleStartElapsed = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
    private final android.app.UiModeManager.OnProjectionStateChangedListener mOnProjectionStateChangedListener = new android.app.UiModeManager.OnProjectionStateChangedListener() { // from class: com.android.server.job.controllers.idle.DeviceIdlenessTracker$$ExternalSyntheticLambda0
        public final void onProjectionStateChanged(int i, java.util.Set set) {
            com.android.server.job.controllers.idle.DeviceIdlenessTracker.this.onProjectionStateChanged(i, set);
        }
    };
    private android.app.AlarmManager.OnAlarmListener mIdleAlarmListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.job.controllers.idle.DeviceIdlenessTracker$$ExternalSyntheticLambda1
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            com.android.server.job.controllers.idle.DeviceIdlenessTracker.this.lambda$new$0();
        }
    };
    private boolean mScreenOn = true;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public boolean isIdle() {
        return this.mIdle;
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void startTracking(android.content.Context context, com.android.server.job.JobSchedulerService jobSchedulerService, com.android.server.job.controllers.idle.IdlenessListener idlenessListener) {
        this.mIdleListener = idlenessListener;
        this.mInactivityIdleThreshold = context.getResources().getInteger(android.R.integer.config_globalActionsKeyTimeout);
        this.mInactivityStablePowerIdleThreshold = context.getResources().getInteger(android.R.integer.config_hotwordDetectedResultMaxBundleSize);
        this.mIdleWindowSlop = context.getResources().getInteger(android.R.integer.config_flipToScreenOffMaxLatencyMicros);
        this.mAlarm = (android.app.AlarmManager) context.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        this.mPowerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.DREAMING_STARTED");
        intentFilter.addAction("android.intent.action.DREAMING_STOPPED");
        intentFilter.addAction(com.android.server.am.ActivityManagerService.ACTION_TRIGGER_IDLE);
        intentFilter.addAction("android.intent.action.DOCK_IDLE");
        intentFilter.addAction("android.intent.action.DOCK_ACTIVE");
        context.registerReceiver(this, intentFilter, null, com.android.server.AppSchedulingModuleThread.getHandler());
        ((android.app.UiModeManager) context.getSystemService(android.app.UiModeManager.class)).addOnProjectionStateChangedListener(-1, com.android.server.AppSchedulingModuleThread.getExecutor(), this.mOnProjectionStateChangedListener);
        this.mIsStablePower = jobSchedulerService.isBatteryCharging() && jobSchedulerService.isBatteryNotLow();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void processConstant(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -365017934:
                if (str.equals(KEY_IDLE_WINDOW_SLOP_MS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -165204881:
                if (str.equals(KEY_INACTIVITY_IDLE_THRESHOLD_MS)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 500388387:
                if (str.equals(KEY_INACTIVITY_STABLE_POWER_IDLE_THRESHOLD_MS)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mInactivityIdleThreshold = java.lang.Math.max(60000L, java.lang.Math.min(14400000L, properties.getLong(str, this.mInactivityIdleThreshold)));
                break;
            case 1:
                this.mInactivityStablePowerIdleThreshold = java.lang.Math.max(60000L, java.lang.Math.min(14400000L, properties.getLong(str, this.mInactivityStablePowerIdleThreshold)));
                break;
            case 2:
                this.mIdleWindowSlop = java.lang.Math.max(60000L, java.lang.Math.min(900000L, properties.getLong(str, this.mIdleWindowSlop)));
                break;
        }
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void onBatteryStateChanged(boolean z, boolean z2) {
        boolean z3 = z && z2;
        if (this.mIsStablePower != z3) {
            this.mIsStablePower = z3;
            maybeScheduleIdlenessCheck("stable power changed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProjectionStateChanged(int i, java.util.Set<java.lang.String> set) {
        boolean z = i != 0;
        if (this.mProjectionActive == z) {
            return;
        }
        if (DEBUG) {
            android.util.Slog.v(TAG, "Projection state changed: " + z);
        }
        this.mProjectionActive = z;
        if (this.mProjectionActive) {
            exitIdle();
        } else {
            maybeScheduleIdlenessCheck("Projection ended");
        }
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.print("  mIdle: ");
        printWriter.println(this.mIdle);
        printWriter.print("  mScreenOn: ");
        printWriter.println(this.mScreenOn);
        printWriter.print("  mIsStablePower: ");
        printWriter.println(this.mIsStablePower);
        printWriter.print("  mDockIdle: ");
        printWriter.println(this.mDockIdle);
        printWriter.print("  mProjectionActive: ");
        printWriter.println(this.mProjectionActive);
        printWriter.print("  mIdlenessCheckScheduledElapsed: ");
        printWriter.println(this.mIdlenessCheckScheduledElapsed);
        printWriter.print("  mIdleStartElapsed: ");
        printWriter.println(this.mIdleStartElapsed);
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1133871366145L, this.mIdle);
        protoOutputStream.write(1133871366146L, this.mScreenOn);
        protoOutputStream.write(1133871366147L, this.mDockIdle);
        protoOutputStream.write(1133871366149L, this.mProjectionActive);
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("DeviceIdlenessTracker:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print(KEY_INACTIVITY_IDLE_THRESHOLD_MS, java.lang.Long.valueOf(this.mInactivityIdleThreshold)).println();
        indentingPrintWriter.print(KEY_INACTIVITY_STABLE_POWER_IDLE_THRESHOLD_MS, java.lang.Long.valueOf(this.mInactivityStablePowerIdleThreshold)).println();
        indentingPrintWriter.print(KEY_IDLE_WINDOW_SLOP_MS, java.lang.Long.valueOf(this.mIdleWindowSlop)).println();
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        char c;
        java.lang.String action = intent.getAction();
        if (DEBUG) {
            android.util.Slog.v(TAG, "Received action: " + action);
        }
        switch (action.hashCode()) {
            case -2128145023:
                if (action.equals("android.intent.action.SCREEN_OFF")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1454123155:
                if (action.equals("android.intent.action.SCREEN_ON")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -905264325:
                if (action.equals("android.intent.action.DOCK_IDLE")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 244891622:
                if (action.equals("android.intent.action.DREAMING_STARTED")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 257757490:
                if (action.equals("android.intent.action.DREAMING_STOPPED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1456569541:
                if (action.equals(com.android.server.am.ActivityManagerService.ACTION_TRIGGER_IDLE)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1689632941:
                if (action.equals("android.intent.action.DOCK_ACTIVE")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (!this.mScreenOn) {
                }
                break;
            case 1:
                if (!this.mPowerManager.isInteractive()) {
                }
                break;
            case 2:
                this.mScreenOn = true;
                this.mDockIdle = false;
                if (DEBUG) {
                    android.util.Slog.v(TAG, "exiting idle");
                }
                exitIdle();
                break;
            case 3:
            case 4:
            case 5:
                if (action.equals("android.intent.action.DOCK_IDLE")) {
                    if (this.mScreenOn) {
                        this.mDockIdle = true;
                    }
                } else {
                    this.mScreenOn = false;
                    this.mDockIdle = false;
                }
                maybeScheduleIdlenessCheck(action);
                break;
            case 6:
                lambda$new$0();
                break;
        }
    }

    private void maybeScheduleIdlenessCheck(java.lang.String str) {
        if (this.mIdle) {
            if (DEBUG) {
                android.util.Slog.w(TAG, "Already idle. Redundant reason=" + str);
                return;
            }
            return;
        }
        if ((!this.mScreenOn || this.mDockIdle) && !this.mProjectionActive) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            long j = this.mIsStablePower ? this.mInactivityStablePowerIdleThreshold : this.mInactivityIdleThreshold;
            if (this.mIdlenessCheckScheduledElapsed < 0) {
                this.mIdlenessCheckScheduledElapsed = millis;
            } else if (this.mIdlenessCheckScheduledElapsed + j <= millis) {
                if (DEBUG) {
                    android.util.Slog.v(TAG, "Previous idle check @ " + this.mIdlenessCheckScheduledElapsed + " allows device to be idle now");
                }
                lambda$new$0();
                return;
            }
            long j2 = this.mIdlenessCheckScheduledElapsed + j;
            if (j2 == this.mIdleStartElapsed) {
                if (DEBUG) {
                    android.util.Slog.i(TAG, "No change to idle start time");
                    return;
                }
                return;
            }
            this.mIdleStartElapsed = j2;
            if (DEBUG) {
                android.util.Slog.v(TAG, "Scheduling idle : " + str + " now:" + millis + " checkElapsed=" + this.mIdlenessCheckScheduledElapsed + " when=" + this.mIdleStartElapsed);
            }
            this.mAlarm.setWindow(2, this.mIdleStartElapsed, this.mIdleWindowSlop, "JS idleness", com.android.server.AppSchedulingModuleThread.getExecutor(), this.mIdleAlarmListener);
        }
    }

    private void exitIdle() {
        this.mAlarm.cancel(this.mIdleAlarmListener);
        this.mIdlenessCheckScheduledElapsed = -1L;
        this.mIdleStartElapsed = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        if (this.mIdle) {
            this.mIdle = false;
            this.mIdleListener.reportNewIdleState(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleIdleTrigger, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        if (!this.mIdle && ((!this.mScreenOn || this.mDockIdle) && !this.mProjectionActive)) {
            if (DEBUG) {
                android.util.Slog.v(TAG, "Idle trigger fired @ " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
            }
            this.mIdle = true;
            this.mIdleListener.reportNewIdleState(this.mIdle);
            return;
        }
        if (DEBUG) {
            android.util.Slog.v(TAG, "TRIGGER_IDLE received but not changing state; idle=" + this.mIdle + " screen=" + this.mScreenOn + " projection=" + this.mProjectionActive);
        }
    }
}
