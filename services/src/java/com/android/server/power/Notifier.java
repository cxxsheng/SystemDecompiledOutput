package com.android.server.power;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PROTECTED)
/* loaded from: classes2.dex */
public class Notifier {
    private static final boolean DEBUG = false;
    private static final int INTERACTIVE_STATE_ASLEEP = 2;
    private static final int INTERACTIVE_STATE_AWAKE = 1;
    private static final int INTERACTIVE_STATE_UNKNOWN = 0;
    private static final int MSG_BROADCAST = 2;
    private static final int MSG_BROADCAST_ENHANCED_PREDICTION = 4;
    private static final int MSG_PROFILE_TIMED_OUT = 5;
    private static final int MSG_SCREEN_POLICY = 7;
    private static final int MSG_USER_ACTIVITY = 1;
    private static final int MSG_WIRED_CHARGING_DISCONNECTED = 8;
    private static final int MSG_WIRED_CHARGING_STARTED = 6;
    private static final int MSG_WIRELESS_CHARGING_INTERRUPTED = 9;
    private static final int MSG_WIRELESS_CHARGING_STARTED = 3;
    private static final java.lang.String TAG = "PowerManagerNotifier";
    private final android.app.AppOpsManager mAppOps;
    private final android.media.AudioManager mAudioManager;
    private final java.util.concurrent.Executor mBackgroundExecutor;
    private final com.android.internal.app.IBatteryStats mBatteryStats;
    private boolean mBroadcastInProgress;
    private long mBroadcastStartTime;
    private int mBroadcastedInteractiveState;
    private final android.content.Context mContext;
    private final com.android.server.power.FaceDownDetector mFaceDownDetector;
    private final com.android.server.power.Notifier.NotifierHandler mHandler;
    private boolean mPendingGoToSleepBroadcast;
    private int mPendingInteractiveState;
    private boolean mPendingWakeUpBroadcast;
    private final com.android.server.policy.WindowManagerPolicy mPolicy;
    private final android.content.Intent mScreenOffIntent;
    private final android.os.Bundle mScreenOnOffOptions;
    private final com.android.server.power.ScreenUndimDetector mScreenUndimDetector;
    private final boolean mShowWirelessChargingAnimationConfig;
    private final com.android.server.power.SuspendBlocker mSuspendBlocker;
    private final boolean mSuspendWhenScreenOffDueToProximityConfig;
    private final android.app.trust.TrustManager mTrustManager;
    private boolean mUserActivityPending;
    private final android.os.Vibrator mVibrator;
    private final com.android.server.power.WakeLockLog mWakeLockLog;
    private static final long[] CHARGING_VIBRATION_TIME = {40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40};
    private static final int[] CHARGING_VIBRATION_AMPLITUDE = {1, 4, 11, 25, 44, 67, 91, 114, 123, 103, 79, 55, 34, 17, 7, 2};
    private static final android.os.VibrationEffect CHARGING_VIBRATION_EFFECT = android.os.VibrationEffect.createWaveform(CHARGING_VIBRATION_TIME, CHARGING_VIBRATION_AMPLITUDE, -1);
    private static final android.os.VibrationAttributes HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES = android.os.VibrationAttributes.createForUsage(50);
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.SparseArray<com.android.server.power.Notifier.Interactivity> mInteractivityByGroupId = new android.util.SparseArray<>();
    private com.android.server.power.Notifier.Interactivity mGlobalInteractivity = new com.android.server.power.Notifier.Interactivity();
    private final java.util.concurrent.atomic.AtomicBoolean mIsPlayingChargingStartedFeedback = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final android.content.IIntentReceiver mWakeUpBroadcastDone = new android.content.IIntentReceiver.Stub() { // from class: com.android.server.power.Notifier.2
        public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.POWER_SCREEN_BROADCAST_DONE, 1, java.lang.Long.valueOf(android.os.SystemClock.uptimeMillis() - com.android.server.power.Notifier.this.mBroadcastStartTime), 1);
            com.android.server.power.Notifier.this.sendNextBroadcast();
        }
    };
    private final android.content.IIntentReceiver mGoToSleepBroadcastDone = new android.content.IIntentReceiver.Stub() { // from class: com.android.server.power.Notifier.3
        public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.POWER_SCREEN_BROADCAST_DONE, 0, java.lang.Long.valueOf(android.os.SystemClock.uptimeMillis() - com.android.server.power.Notifier.this.mBroadcastStartTime), 1);
            com.android.server.power.Notifier.this.sendNextBroadcast();
        }
    };
    private final android.app.ActivityManagerInternal mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
    private final com.android.server.input.InputManagerInternal mInputManagerInternal = (com.android.server.input.InputManagerInternal) com.android.server.LocalServices.getService(com.android.server.input.InputManagerInternal.class);
    private final com.android.server.inputmethod.InputMethodManagerInternal mInputMethodManagerInternal = (com.android.server.inputmethod.InputMethodManagerInternal) com.android.server.LocalServices.getService(com.android.server.inputmethod.InputMethodManagerInternal.class);

    @android.annotation.Nullable
    private final com.android.server.statusbar.StatusBarManagerInternal mStatusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
    private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
    private final android.content.Intent mScreenOnIntent = new android.content.Intent("android.intent.action.SCREEN_ON");

    private static class Interactivity {
        public int changeReason;
        public long changeStartTime;
        public boolean isChanging;
        public boolean isInteractive;

        private Interactivity() {
            this.isInteractive = true;
        }
    }

    public Notifier(android.os.Looper looper, android.content.Context context, com.android.internal.app.IBatteryStats iBatteryStats, com.android.server.power.SuspendBlocker suspendBlocker, com.android.server.policy.WindowManagerPolicy windowManagerPolicy, com.android.server.power.FaceDownDetector faceDownDetector, com.android.server.power.ScreenUndimDetector screenUndimDetector, java.util.concurrent.Executor executor) {
        this.mContext = context;
        this.mBatteryStats = iBatteryStats;
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        this.mSuspendBlocker = suspendBlocker;
        this.mPolicy = windowManagerPolicy;
        this.mFaceDownDetector = faceDownDetector;
        this.mScreenUndimDetector = screenUndimDetector;
        this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class);
        this.mTrustManager = (android.app.trust.TrustManager) this.mContext.getSystemService(android.app.trust.TrustManager.class);
        this.mVibrator = (android.os.Vibrator) this.mContext.getSystemService(android.os.Vibrator.class);
        this.mHandler = new com.android.server.power.Notifier.NotifierHandler(looper);
        this.mBackgroundExecutor = executor;
        this.mScreenOnIntent.addFlags(1344274432);
        this.mScreenOffIntent = new android.content.Intent("android.intent.action.SCREEN_OFF");
        this.mScreenOffIntent.addFlags(1344274432);
        this.mScreenOnOffOptions = createScreenOnOffBroadcastOptions();
        this.mSuspendWhenScreenOffDueToProximityConfig = context.getResources().getBoolean(android.R.bool.config_supportsSplitScreenMultiWindow);
        this.mShowWirelessChargingAnimationConfig = context.getResources().getBoolean(android.R.bool.config_shortPressEarlyOnPower);
        this.mWakeLockLog = new com.android.server.power.WakeLockLog(context);
        try {
            this.mBatteryStats.noteInteractive(true);
        } catch (android.os.RemoteException e) {
        }
        com.android.internal.util.FrameworkStatsLog.write(33, 1);
    }

    private android.os.Bundle createScreenOnOffBroadcastOptions() {
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setDeliveryGroupPolicy(1);
        makeBasic.setDeliveryGroupMatchingKey(java.util.UUID.randomUUID().toString(), "android.intent.action.SCREEN_ON");
        makeBasic.setDeferralPolicy(2);
        return makeBasic.toBundle();
    }

    public void onWakeLockAcquired(int i, java.lang.String str, java.lang.String str2, int i2, int i3, android.os.WorkSource workSource, java.lang.String str3, android.os.IWakeLockCallback iWakeLockCallback) {
        notifyWakeLockListener(iWakeLockCallback, str, true);
        int batteryStatsWakeLockMonitorType = getBatteryStatsWakeLockMonitorType(i);
        if (batteryStatsWakeLockMonitorType >= 0) {
            boolean z = i2 == 1000 && (1073741824 & i) != 0;
            try {
                if (workSource != null) {
                    this.mBatteryStats.noteStartWakelockFromSource(workSource, i3, str, str3, batteryStatsWakeLockMonitorType, z);
                } else {
                    this.mBatteryStats.noteStartWakelock(i2, i3, str, str3, batteryStatsWakeLockMonitorType, z);
                    this.mAppOps.startOpNoThrow(40, i2, str2);
                }
            } catch (android.os.RemoteException e) {
            }
        }
        this.mWakeLockLog.onWakeLockAcquired(str, i2, i);
    }

    public void onLongPartialWakeLockStart(java.lang.String str, int i, android.os.WorkSource workSource, java.lang.String str2) {
        try {
            if (workSource != null) {
                this.mBatteryStats.noteLongPartialWakelockStartFromSource(str, str2, workSource);
                com.android.internal.util.FrameworkStatsLog.write(11, workSource, str, str2, 1);
            } else {
                this.mBatteryStats.noteLongPartialWakelockStart(str, str2, i);
                com.android.internal.util.FrameworkStatsLog.write_non_chained(11, i, (java.lang.String) null, str, str2, 1);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public void onLongPartialWakeLockFinish(java.lang.String str, int i, android.os.WorkSource workSource, java.lang.String str2) {
        try {
            if (workSource != null) {
                this.mBatteryStats.noteLongPartialWakelockFinishFromSource(str, str2, workSource);
                com.android.internal.util.FrameworkStatsLog.write(11, workSource, str, str2, 0);
            } else {
                this.mBatteryStats.noteLongPartialWakelockFinish(str, str2, i);
                com.android.internal.util.FrameworkStatsLog.write_non_chained(11, i, (java.lang.String) null, str, str2, 0);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public void onWakeLockChanging(int i, java.lang.String str, java.lang.String str2, int i2, int i3, android.os.WorkSource workSource, java.lang.String str3, android.os.IWakeLockCallback iWakeLockCallback, int i4, java.lang.String str4, java.lang.String str5, int i5, int i6, android.os.WorkSource workSource2, java.lang.String str6, android.os.IWakeLockCallback iWakeLockCallback2) {
        int batteryStatsWakeLockMonitorType = getBatteryStatsWakeLockMonitorType(i);
        int batteryStatsWakeLockMonitorType2 = getBatteryStatsWakeLockMonitorType(i4);
        if (workSource != null && workSource2 != null && batteryStatsWakeLockMonitorType >= 0 && batteryStatsWakeLockMonitorType2 >= 0) {
            try {
                this.mBatteryStats.noteChangeWakelockFromSource(workSource, i3, str, str3, batteryStatsWakeLockMonitorType, workSource2, i6, str4, str6, batteryStatsWakeLockMonitorType2, i5 == 1000 && (1073741824 & i4) != 0);
            } catch (android.os.RemoteException e) {
            }
        } else if (!com.android.server.power.PowerManagerService.isSameCallback(iWakeLockCallback, iWakeLockCallback2)) {
            onWakeLockReleased(i, str, str2, i2, i3, workSource, str3, null);
            onWakeLockAcquired(i4, str4, str5, i5, i6, workSource2, str6, iWakeLockCallback2);
        } else {
            onWakeLockReleased(i, str, str2, i2, i3, workSource, str3, iWakeLockCallback);
            onWakeLockAcquired(i4, str4, str5, i5, i6, workSource2, str6, iWakeLockCallback2);
        }
    }

    public void onWakeLockReleased(int i, java.lang.String str, java.lang.String str2, int i2, int i3, android.os.WorkSource workSource, java.lang.String str3, android.os.IWakeLockCallback iWakeLockCallback) {
        notifyWakeLockListener(iWakeLockCallback, str, false);
        int batteryStatsWakeLockMonitorType = getBatteryStatsWakeLockMonitorType(i);
        if (batteryStatsWakeLockMonitorType >= 0) {
            try {
                if (workSource != null) {
                    this.mBatteryStats.noteStopWakelockFromSource(workSource, i3, str, str3, batteryStatsWakeLockMonitorType);
                } else {
                    this.mBatteryStats.noteStopWakelock(i2, i3, str, str3, batteryStatsWakeLockMonitorType);
                    this.mAppOps.finishOp(40, i2, str2);
                }
            } catch (android.os.RemoteException e) {
            }
        }
        this.mWakeLockLog.onWakeLockReleased(str, i2);
    }

    public void showDismissibleKeyguard() {
        this.mPolicy.showDismissibleKeyguard();
    }

    private int getBatteryStatsWakeLockMonitorType(int i) {
        switch (i & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) {
            case 1:
                break;
            case 6:
            case 10:
                break;
            case 32:
                if (this.mSuspendWhenScreenOffDueToProximityConfig) {
                }
                break;
            case 64:
                break;
            case 128:
                break;
        }
        return -1;
    }

    public void onGlobalWakefulnessChangeStarted(final int i, int i2, long j) {
        int i3;
        boolean isInteractive = android.os.PowerManagerInternal.isInteractive(i);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.Notifier.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.power.Notifier.this.mActivityManagerInternal.onWakefulnessChanged(i);
            }
        });
        if (this.mGlobalInteractivity.isInteractive != isInteractive) {
            if (this.mGlobalInteractivity.isChanging) {
                handleLateGlobalInteractiveChange();
            }
            this.mInputManagerInternal.setInteractive(isInteractive);
            this.mInputMethodManagerInternal.setInteractive(isInteractive);
            try {
                this.mBatteryStats.noteInteractive(isInteractive);
            } catch (android.os.RemoteException e) {
            }
            if (isInteractive) {
                i3 = 1;
            } else {
                i3 = 0;
            }
            com.android.internal.util.FrameworkStatsLog.write(33, i3);
            this.mGlobalInteractivity.isInteractive = isInteractive;
            this.mGlobalInteractivity.isChanging = true;
            this.mGlobalInteractivity.changeReason = i2;
            this.mGlobalInteractivity.changeStartTime = j;
            handleEarlyGlobalInteractiveChange();
        }
    }

    public void onWakefulnessChangeFinished() {
        for (int i = 0; i < this.mInteractivityByGroupId.size(); i++) {
            int keyAt = this.mInteractivityByGroupId.keyAt(i);
            com.android.server.power.Notifier.Interactivity valueAt = this.mInteractivityByGroupId.valueAt(i);
            if (valueAt.isChanging) {
                valueAt.isChanging = false;
                handleLateInteractiveChange(keyAt);
            }
        }
        if (this.mGlobalInteractivity.isChanging) {
            this.mGlobalInteractivity.isChanging = false;
            handleLateGlobalInteractiveChange();
        }
    }

    private void handleEarlyInteractiveChange(final int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.power.Notifier.Interactivity interactivity = this.mInteractivityByGroupId.get(i);
                if (interactivity == null) {
                    android.util.Slog.e(TAG, "no Interactivity entry for groupId:" + i);
                    return;
                }
                final int i2 = interactivity.changeReason;
                if (interactivity.isInteractive) {
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.Notifier$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.power.Notifier.this.lambda$handleEarlyInteractiveChange$0(i, i2);
                        }
                    });
                } else {
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.Notifier$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.power.Notifier.this.lambda$handleEarlyInteractiveChange$1(i, i2);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleEarlyInteractiveChange$0(int i, int i2) {
        this.mPolicy.startedWakingUp(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleEarlyInteractiveChange$1(int i, int i2) {
        this.mPolicy.startedGoingToSleep(i, i2);
    }

    private void handleEarlyGlobalInteractiveChange() {
        synchronized (this.mLock) {
            try {
                if (this.mGlobalInteractivity.isInteractive) {
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.Notifier$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.power.Notifier.this.lambda$handleEarlyGlobalInteractiveChange$2();
                        }
                    });
                    this.mPendingInteractiveState = 1;
                    this.mPendingWakeUpBroadcast = true;
                    updatePendingBroadcastLocked();
                } else {
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.Notifier$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.power.Notifier.this.lambda$handleEarlyGlobalInteractiveChange$3();
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleEarlyGlobalInteractiveChange$2() {
        this.mDisplayManagerInternal.onEarlyInteractivityChange(true);
        this.mPolicy.startedWakingUpGlobal(this.mGlobalInteractivity.changeReason);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleEarlyGlobalInteractiveChange$3() {
        this.mDisplayManagerInternal.onEarlyInteractivityChange(false);
        this.mPolicy.startedGoingToSleepGlobal(this.mGlobalInteractivity.changeReason);
    }

    private void handleLateGlobalInteractiveChange() {
        synchronized (this.mLock) {
            try {
                final int uptimeMillis = (int) (android.os.SystemClock.uptimeMillis() - this.mGlobalInteractivity.changeStartTime);
                if (this.mGlobalInteractivity.isInteractive) {
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.Notifier$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.power.Notifier.this.lambda$handleLateGlobalInteractiveChange$4(uptimeMillis);
                        }
                    });
                } else {
                    if (this.mUserActivityPending) {
                        this.mUserActivityPending = false;
                        this.mHandler.removeMessages(1);
                    }
                    final int translateSleepReasonToOffReason = android.view.WindowManagerPolicyConstants.translateSleepReasonToOffReason(this.mGlobalInteractivity.changeReason);
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.Notifier$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.power.Notifier.this.lambda$handleLateGlobalInteractiveChange$5(translateSleepReasonToOffReason, uptimeMillis);
                        }
                    });
                    this.mPendingInteractiveState = 2;
                    this.mPendingGoToSleepBroadcast = true;
                    updatePendingBroadcastLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleLateGlobalInteractiveChange$4(int i) {
        android.metrics.LogMaker logMaker = new android.metrics.LogMaker(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_USB_DATA_SIGNALING);
        logMaker.setType(1);
        logMaker.setSubtype(android.view.WindowManagerPolicyConstants.translateWakeReasonToOnReason(this.mGlobalInteractivity.changeReason));
        logMaker.setLatency(i);
        logMaker.addTaggedData(1694, java.lang.Integer.valueOf(this.mGlobalInteractivity.changeReason));
        com.android.internal.logging.MetricsLogger.action(logMaker);
        com.android.server.EventLogTags.writePowerScreenState(1, 0, 0L, 0, i);
        this.mPolicy.finishedWakingUpGlobal(this.mGlobalInteractivity.changeReason);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleLateGlobalInteractiveChange$5(int i, int i2) {
        android.metrics.LogMaker logMaker = new android.metrics.LogMaker(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_USB_DATA_SIGNALING);
        logMaker.setType(2);
        logMaker.setSubtype(i);
        logMaker.setLatency(i2);
        logMaker.addTaggedData(1695, java.lang.Integer.valueOf(this.mGlobalInteractivity.changeReason));
        com.android.internal.logging.MetricsLogger.action(logMaker);
        com.android.server.EventLogTags.writePowerScreenState(0, i, 0L, 0, i2);
        this.mPolicy.finishedGoingToSleepGlobal(this.mGlobalInteractivity.changeReason);
    }

    private void handleLateInteractiveChange(final int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.power.Notifier.Interactivity interactivity = this.mInteractivityByGroupId.get(i);
                if (interactivity == null) {
                    android.util.Slog.e(TAG, "no Interactivity entry for groupId:" + i);
                    return;
                }
                final int i2 = interactivity.changeReason;
                if (interactivity.isInteractive) {
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.Notifier$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.power.Notifier.this.lambda$handleLateInteractiveChange$6(i, i2);
                        }
                    });
                } else {
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.Notifier$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.power.Notifier.this.lambda$handleLateInteractiveChange$7(i, i2);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleLateInteractiveChange$6(int i, int i2) {
        this.mPolicy.finishedWakingUp(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleLateInteractiveChange$7(int i, int i2) {
        this.mPolicy.finishedGoingToSleep(i, i2);
    }

    public void onGroupWakefulnessChangeStarted(int i, int i2, int i3, long j) {
        boolean z;
        boolean isInteractive = android.os.PowerManagerInternal.isInteractive(i2);
        com.android.server.power.Notifier.Interactivity interactivity = this.mInteractivityByGroupId.get(i);
        if (interactivity != null) {
            z = false;
        } else {
            interactivity = new com.android.server.power.Notifier.Interactivity();
            this.mInteractivityByGroupId.put(i, interactivity);
            z = true;
        }
        if (z || interactivity.isInteractive != isInteractive) {
            if (interactivity.isChanging) {
                handleLateInteractiveChange(i);
            }
            interactivity.isInteractive = isInteractive;
            interactivity.changeReason = i3;
            interactivity.changeStartTime = j;
            interactivity.isChanging = true;
            handleEarlyInteractiveChange(i);
        }
    }

    public void onGroupRemoved(int i) {
        this.mInteractivityByGroupId.remove(i);
    }

    public void onUserActivity(int i, int i2, int i3) {
        try {
            this.mBatteryStats.noteUserActivity(i3, i2);
        } catch (android.os.RemoteException e) {
        }
        synchronized (this.mLock) {
            try {
                if (!this.mUserActivityPending) {
                    this.mUserActivityPending = true;
                    android.os.Message obtainMessage = this.mHandler.obtainMessage(1);
                    obtainMessage.arg1 = i;
                    obtainMessage.arg2 = i2;
                    obtainMessage.setAsynchronous(true);
                    this.mHandler.sendMessage(obtainMessage);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onWakeUp(int i, java.lang.String str, int i2, java.lang.String str2, int i3) {
        try {
            this.mBatteryStats.noteWakeUp(str, i2);
            if (str2 != null) {
                this.mAppOps.noteOpNoThrow(61, i3, str2);
            }
        } catch (android.os.RemoteException e) {
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DISPLAY_WAKE_REPORTED, i, i2);
    }

    public void onProfileTimeout(int i) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(5);
        obtainMessage.setAsynchronous(true);
        obtainMessage.arg1 = i;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void onWirelessChargingStarted(int i, int i2) {
        this.mSuspendBlocker.acquire();
        android.os.Message obtainMessage = this.mHandler.obtainMessage(3);
        obtainMessage.setAsynchronous(true);
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = i2;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void onWirelessChargingInterrupted(int i) {
        this.mSuspendBlocker.acquire();
        android.os.Message obtainMessage = this.mHandler.obtainMessage(9);
        obtainMessage.setAsynchronous(true);
        obtainMessage.arg1 = i;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void onWiredChargingStarted(int i) {
        this.mSuspendBlocker.acquire();
        android.os.Message obtainMessage = this.mHandler.obtainMessage(6);
        obtainMessage.setAsynchronous(true);
        obtainMessage.arg1 = i;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void onWiredChargingDisconnected(int i) {
        this.mSuspendBlocker.acquire();
        android.os.Message obtainMessage = this.mHandler.obtainMessage(8);
        obtainMessage.setAsynchronous(true);
        obtainMessage.arg1 = i;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void onScreenPolicyUpdate(int i, int i2) {
        synchronized (this.mLock) {
            android.os.Message obtainMessage = this.mHandler.obtainMessage(7);
            obtainMessage.arg1 = i;
            obtainMessage.arg2 = i2;
            obtainMessage.setAsynchronous(true);
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        if (this.mWakeLockLog != null) {
            this.mWakeLockLog.dump(printWriter);
        }
    }

    private void updatePendingBroadcastLocked() {
        if (this.mBroadcastInProgress || this.mPendingInteractiveState == 0) {
            return;
        }
        if (this.mPendingWakeUpBroadcast || this.mPendingGoToSleepBroadcast || this.mPendingInteractiveState != this.mBroadcastedInteractiveState) {
            this.mBroadcastInProgress = true;
            this.mSuspendBlocker.acquire();
            android.os.Message obtainMessage = this.mHandler.obtainMessage(2);
            obtainMessage.setAsynchronous(true);
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    private void finishPendingBroadcastLocked() {
        this.mBroadcastInProgress = false;
        this.mSuspendBlocker.release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUserActivity(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mUserActivityPending) {
                    this.mUserActivityPending = false;
                    ((android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class)).notifyUserActivity();
                    this.mInputManagerInternal.notifyUserActivity();
                    this.mPolicy.userActivity(i, i2);
                    this.mFaceDownDetector.userActivity(i2);
                    this.mScreenUndimDetector.userActivity(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void postEnhancedDischargePredictionBroadcast(long j) {
        this.mHandler.sendEmptyMessageDelayed(4, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEnhancedDischargePredictionBroadcast() {
        this.mContext.sendBroadcastAsUser(new android.content.Intent("android.os.action.ENHANCED_DISCHARGE_PREDICTION_CHANGED").addFlags(1073741824), android.os.UserHandle.ALL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendNextBroadcast() {
        synchronized (this.mLock) {
            try {
                if (this.mBroadcastedInteractiveState == 0) {
                    switch (this.mPendingInteractiveState) {
                        case 2:
                            this.mPendingGoToSleepBroadcast = false;
                            this.mBroadcastedInteractiveState = 2;
                            break;
                        default:
                            this.mPendingWakeUpBroadcast = false;
                            this.mBroadcastedInteractiveState = 1;
                            break;
                    }
                } else if (this.mBroadcastedInteractiveState == 1) {
                    if (this.mPendingWakeUpBroadcast || this.mPendingGoToSleepBroadcast || this.mPendingInteractiveState == 2) {
                        this.mPendingGoToSleepBroadcast = false;
                        this.mBroadcastedInteractiveState = 2;
                    } else {
                        finishPendingBroadcastLocked();
                        return;
                    }
                } else if (this.mPendingWakeUpBroadcast || this.mPendingGoToSleepBroadcast || this.mPendingInteractiveState == 1) {
                    this.mPendingWakeUpBroadcast = false;
                    this.mBroadcastedInteractiveState = 1;
                } else {
                    finishPendingBroadcastLocked();
                    return;
                }
                this.mBroadcastStartTime = android.os.SystemClock.uptimeMillis();
                int i = this.mBroadcastedInteractiveState;
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.POWER_SCREEN_BROADCAST_SEND, 1);
                if (i == 1) {
                    sendWakeUpBroadcast();
                } else {
                    sendGoToSleepBroadcast();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void sendWakeUpBroadcast() {
        if (this.mActivityManagerInternal.isSystemReady()) {
            this.mActivityManagerInternal.broadcastIntent(this.mScreenOnIntent, this.mWakeUpBroadcastDone, (java.lang.String[]) null, !this.mActivityManagerInternal.isModernQueueEnabled(), -1, (int[]) null, (java.util.function.BiFunction) null, this.mScreenOnOffOptions);
        } else {
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.POWER_SCREEN_BROADCAST_STOP, 2, 1);
            sendNextBroadcast();
        }
    }

    private void sendGoToSleepBroadcast() {
        if (this.mActivityManagerInternal.isSystemReady()) {
            this.mActivityManagerInternal.broadcastIntent(this.mScreenOffIntent, this.mGoToSleepBroadcastDone, (java.lang.String[]) null, !this.mActivityManagerInternal.isModernQueueEnabled(), -1, (int[]) null, (java.util.function.BiFunction) null, this.mScreenOnOffOptions);
        } else {
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.POWER_SCREEN_BROADCAST_STOP, 3, 1);
            sendNextBroadcast();
        }
    }

    private void playChargingStartedFeedback(final int i, final boolean z) {
        if (!isChargingFeedbackEnabled(i) || !this.mIsPlayingChargingStartedFeedback.compareAndSet(false, true)) {
            return;
        }
        this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.power.Notifier$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.Notifier.this.lambda$playChargingStartedFeedback$8(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playChargingStartedFeedback$8(int i, boolean z) {
        if (android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "charging_vibration_enabled", 1, i) != 0) {
            this.mVibrator.vibrate(1000, this.mContext.getOpPackageName(), CHARGING_VIBRATION_EFFECT, "Charging started", HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES);
        }
        java.lang.String string = android.provider.Settings.Global.getString(this.mContext.getContentResolver(), z ? "wireless_charging_started_sound" : "charging_started_sound");
        if ("silent".equals(string)) {
            return;
        }
        android.net.Uri parse = android.net.Uri.parse(string);
        if (parse != null) {
            if (!parse.isAbsolute()) {
                parse = android.net.Uri.parse("file://" + string);
            }
            android.media.Ringtone ringtone = android.media.RingtoneManager.getRingtone(this.mContext, parse);
            if (ringtone != null) {
                ringtone.setStreamType(1);
                ringtone.play();
            }
        }
        this.mIsPlayingChargingStartedFeedback.set(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWirelessChargingStarted(int i, int i2) {
        playChargingStartedFeedback(i2, true);
        if (this.mShowWirelessChargingAnimationConfig && this.mStatusBarManagerInternal != null) {
            this.mStatusBarManagerInternal.showChargingAnimation(i);
        }
        this.mSuspendBlocker.release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWiredChargingStarted(int i) {
        playChargingStartedFeedback(i, false);
        this.mSuspendBlocker.release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showChargingStopped(int i, boolean z) {
        playChargingStartedFeedback(i, z);
        this.mSuspendBlocker.release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void screenPolicyChanging(int i, int i2) {
        this.mScreenUndimDetector.recordScreenPolicy(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lockProfile(int i) {
        this.mTrustManager.setDeviceLockedForUser(i, true);
    }

    private boolean isChargingFeedbackEnabled(int i) {
        return (android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "charging_sounds_enabled", 1, i) != 0) && (android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", 1) == 0) && !(this.mAudioManager.getRingerModeInternal() == 0);
    }

    private void notifyWakeLockListener(final android.os.IWakeLockCallback iWakeLockCallback, final java.lang.String str, final boolean z) {
        if (iWakeLockCallback != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.Notifier$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.power.Notifier.lambda$notifyWakeLockListener$9(iWakeLockCallback, z, str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyWakeLockListener$9(android.os.IWakeLockCallback iWakeLockCallback, boolean z, java.lang.String str) {
        try {
            iWakeLockCallback.onStateChanged(z);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Wakelock.mCallback [" + str + "] is already dead.", e);
        }
    }

    private final class NotifierHandler extends android.os.Handler {
        public NotifierHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.power.Notifier.this.sendUserActivity(message.arg1, message.arg2);
                    break;
                case 2:
                    com.android.server.power.Notifier.this.sendNextBroadcast();
                    break;
                case 3:
                    com.android.server.power.Notifier.this.showWirelessChargingStarted(message.arg1, message.arg2);
                    break;
                case 4:
                    removeMessages(4);
                    com.android.server.power.Notifier.this.sendEnhancedDischargePredictionBroadcast();
                    break;
                case 5:
                    com.android.server.power.Notifier.this.lockProfile(message.arg1);
                    break;
                case 6:
                    com.android.server.power.Notifier.this.showWiredChargingStarted(message.arg1);
                    break;
                case 7:
                    com.android.server.power.Notifier.this.screenPolicyChanging(message.arg1, message.arg2);
                    break;
                case 8:
                    com.android.server.power.Notifier.this.showChargingStopped(message.arg1, false);
                    break;
                case 9:
                    com.android.server.power.Notifier.this.showChargingStopped(message.arg1, true);
                    break;
            }
        }
    }
}
