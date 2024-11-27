package com.android.server.job.controllers.idle;

/* loaded from: classes2.dex */
public final class CarIdlenessTracker extends android.content.BroadcastReceiver implements com.android.server.job.controllers.idle.IdlenessTracker {
    public static final java.lang.String ACTION_FORCE_IDLE = "com.android.server.jobscheduler.FORCE_IDLE";
    public static final java.lang.String ACTION_GARAGE_MODE_OFF = "com.android.server.jobscheduler.GARAGE_MODE_OFF";
    public static final java.lang.String ACTION_GARAGE_MODE_ON = "com.android.server.jobscheduler.GARAGE_MODE_ON";
    public static final java.lang.String ACTION_UNFORCE_IDLE = "com.android.server.jobscheduler.UNFORCE_IDLE";
    private static final boolean DEBUG;
    private static final java.lang.String TAG = "JobScheduler.CarIdlenessTracker";
    private com.android.server.job.controllers.idle.IdlenessListener mIdleListener;
    private boolean mIdle = false;
    private boolean mGarageModeOn = false;
    private boolean mForced = false;
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
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction(ACTION_GARAGE_MODE_ON);
        intentFilter.addAction(ACTION_GARAGE_MODE_OFF);
        intentFilter.addAction(ACTION_FORCE_IDLE);
        intentFilter.addAction(ACTION_UNFORCE_IDLE);
        intentFilter.addAction(com.android.server.am.ActivityManagerService.ACTION_TRIGGER_IDLE);
        context.registerReceiver(this, intentFilter, null, com.android.server.AppSchedulingModuleThread.getHandler());
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void processConstant(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str) {
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void onBatteryStateChanged(boolean z, boolean z2) {
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.print("  mIdle: ");
        printWriter.println(this.mIdle);
        printWriter.print("  mGarageModeOn: ");
        printWriter.println(this.mGarageModeOn);
        printWriter.print("  mForced: ");
        printWriter.println(this.mForced);
        printWriter.print("  mScreenOn: ");
        printWriter.println(this.mScreenOn);
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1133871366145L, this.mIdle);
        protoOutputStream.write(1133871366146L, this.mGarageModeOn);
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.idle.IdlenessTracker
    public void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter) {
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        java.lang.String action = intent.getAction();
        logIfDebug("Received action: " + action);
        if (action.equals(ACTION_FORCE_IDLE)) {
            logIfDebug("Forcing idle...");
            setForceIdleState(true);
            return;
        }
        if (action.equals(ACTION_UNFORCE_IDLE)) {
            logIfDebug("Unforcing idle...");
            setForceIdleState(false);
            return;
        }
        if (action.equals("android.intent.action.SCREEN_ON")) {
            logIfDebug("Screen is on...");
            handleScreenOn();
            return;
        }
        if (action.equals("android.intent.action.SCREEN_OFF")) {
            logIfDebug("Screen is off...");
            this.mScreenOn = false;
            return;
        }
        if (action.equals(ACTION_GARAGE_MODE_ON)) {
            logIfDebug("GarageMode is on...");
            this.mGarageModeOn = true;
            updateIdlenessState();
            return;
        }
        if (action.equals(ACTION_GARAGE_MODE_OFF)) {
            logIfDebug("GarageMode is off...");
            this.mGarageModeOn = false;
            updateIdlenessState();
        } else if (action.equals(com.android.server.am.ActivityManagerService.ACTION_TRIGGER_IDLE)) {
            if (!this.mGarageModeOn) {
                logIfDebug("Idle trigger fired...");
                triggerIdleness();
                return;
            }
            logIfDebug("TRIGGER_IDLE received but not changing state; mIdle=" + this.mIdle + " mGarageModeOn=" + this.mGarageModeOn);
        }
    }

    private void setForceIdleState(boolean z) {
        this.mForced = z;
        updateIdlenessState();
    }

    private void updateIdlenessState() {
        boolean z = this.mForced || this.mGarageModeOn;
        if (this.mIdle != z) {
            logIfDebug("Device idleness changed. New idle=" + z);
            this.mIdle = z;
            this.mIdleListener.reportNewIdleState(this.mIdle);
            return;
        }
        logIfDebug("Device idleness is the same. Current idle=" + z);
    }

    private void triggerIdleness() {
        if (this.mIdle) {
            logIfDebug("Device is already idle");
            return;
        }
        if (!this.mScreenOn) {
            logIfDebug("Device is going idle");
            this.mIdle = true;
            this.mIdleListener.reportNewIdleState(this.mIdle);
        } else {
            logIfDebug("TRIGGER_IDLE received but not changing state: mIdle = " + this.mIdle + ", mScreenOn = " + this.mScreenOn);
        }
    }

    private void handleScreenOn() {
        this.mScreenOn = true;
        if (this.mForced || this.mGarageModeOn) {
            logIfDebug("Screen is on, but device cannot exit idle");
        } else {
            if (this.mIdle) {
                logIfDebug("Device is exiting idle");
                this.mIdle = false;
                this.mIdleListener.reportNewIdleState(this.mIdle);
                return;
            }
            logIfDebug("Device is already non-idle");
        }
    }

    private static void logIfDebug(java.lang.String str) {
        if (DEBUG) {
            android.util.Slog.v(TAG, str);
        }
    }
}
