package com.android.server.vibrator;

/* loaded from: classes2.dex */
public class VibratorManagerService extends android.os.IVibratorManagerService.Stub {
    private static final int ATTRIBUTES_ALL_BYPASS_FLAGS = 19;
    private static final long BATTERY_STATS_REPEATING_VIBRATION_DURATION = 5000;
    private static final boolean DEBUG = false;
    private static final android.os.VibrationAttributes DEFAULT_ATTRIBUTES = new android.os.VibrationAttributes.Builder().build();
    private static final java.lang.String EXTERNAL_VIBRATOR_SERVICE = "external_vibrator_service";
    private static final java.lang.String TAG = "VibratorManagerService";
    private static final long VIBRATION_CANCEL_WAIT_MILLIS = 5000;
    private static final java.lang.String VIBRATOR_CONTROL_SERVICE = "android.frameworks.vibrator.IVibratorControlService/default";
    private final android.app.AppOpsManager mAppOps;
    private final com.android.internal.app.IBatteryStats mBatteryStatsService;
    private final long mCapabilities;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.os.VibratorInfo mCombinedVibratorInfo;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.vibrator.VibratorManagerService.ExternalVibrationHolder mCurrentExternalVibration;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.vibrator.VibrationStepConductor mCurrentVibration;
    private final com.android.server.vibrator.DeviceAdapter mDeviceAdapter;
    private final com.android.server.vibrator.VibratorFrameworkStatsLogger mFrameworkStatsLogger;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.vibrator.HapticFeedbackVibrationProvider mHapticFeedbackVibrationProvider;
    private final com.android.server.vibrator.VibratorManagerService.Injector mInjector;
    private final com.android.server.vibrator.InputDeviceDelegate mInputDeviceDelegate;
    private final com.android.server.vibrator.VibratorManagerService.NativeWrapper mNativeWrapper;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.vibrator.VibrationStepConductor mNextVibration;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mServiceReady;
    private final com.android.server.vibrator.VibrationScaler mVibrationScaler;
    private final com.android.server.vibrator.VibrationSettings mVibrationSettings;
    private final com.android.server.vibrator.VibrationThread mVibrationThread;
    private final com.android.server.vibrator.VibratorControlService mVibratorControlService;
    private final int[] mVibratorIds;
    private final com.android.server.vibrator.VibratorManagerService.VibratorManagerRecords mVibratorManagerRecords;
    private final android.util.SparseArray<com.android.server.vibrator.VibratorController> mVibrators;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.server.vibrator.VibratorManagerService.VibrationThreadCallbacks mVibrationThreadCallbacks = new com.android.server.vibrator.VibratorManagerService.VibrationThreadCallbacks();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.vibrator.VibratorManagerService.AlwaysOnVibration> mAlwaysOnEffects = new android.util.SparseArray<>();
    private android.content.BroadcastReceiver mIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.vibrator.VibratorManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                synchronized (com.android.server.vibrator.VibratorManagerService.this.mLock) {
                    try {
                        if (com.android.server.vibrator.VibratorManagerService.this.shouldCancelOnScreenOffLocked(com.android.server.vibrator.VibratorManagerService.this.mNextVibration)) {
                            com.android.server.vibrator.VibratorManagerService.this.clearNextVibrationLocked(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_BY_SCREEN_OFF));
                        }
                        if (com.android.server.vibrator.VibratorManagerService.this.shouldCancelOnScreenOffLocked(com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration)) {
                            com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration.notifyCancelled(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_BY_SCREEN_OFF), false);
                        }
                    } finally {
                    }
                }
            }
        }
    };

    @com.android.internal.annotations.VisibleForTesting
    interface OnSyncedVibrationCompleteListener {
        void onComplete(long j);
    }

    static native void nativeCancelSynced(long j);

    static native long nativeGetCapabilities(long j);

    static native long nativeGetFinalizer();

    static native int[] nativeGetVibratorIds(long j);

    static native long nativeInit(com.android.server.vibrator.VibratorManagerService.OnSyncedVibrationCompleteListener onSyncedVibrationCompleteListener);

    static native boolean nativePrepareSynced(long j, int[] iArr);

    static native boolean nativeTriggerSynced(long j, long j2);

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.vibrator.VibratorManagerService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mService = new com.android.server.vibrator.VibratorManagerService(getContext(), new com.android.server.vibrator.VibratorManagerService.Injector());
            publishBinderService("vibrator_manager", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 500) {
                this.mService.systemReady();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    VibratorManagerService(android.content.Context context, com.android.server.vibrator.VibratorManagerService.Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.mHandler = injector.createHandler(android.os.Looper.myLooper());
        this.mVibrationSettings = new com.android.server.vibrator.VibrationSettings(this.mContext, this.mHandler);
        this.mVibrationScaler = new com.android.server.vibrator.VibrationScaler(this.mContext, this.mVibrationSettings);
        this.mVibratorControlService = new com.android.server.vibrator.VibratorControlService(this.mContext, injector.createVibratorControllerHolder(), this.mVibrationScaler, this.mVibrationSettings, this.mLock);
        this.mInputDeviceDelegate = new com.android.server.vibrator.InputDeviceDelegate(this.mContext, this.mHandler);
        com.android.server.vibrator.VibratorManagerService.VibrationCompleteListener vibrationCompleteListener = new com.android.server.vibrator.VibratorManagerService.VibrationCompleteListener(this);
        this.mNativeWrapper = injector.getNativeWrapper();
        this.mNativeWrapper.init(vibrationCompleteListener);
        this.mVibratorManagerRecords = new com.android.server.vibrator.VibratorManagerService.VibratorManagerRecords(this.mContext.getResources().getInteger(android.R.integer.config_powerStatsAggregationPeriod), this.mContext.getResources().getInteger(android.R.integer.config_pictureInPictureMaxNumberOfActions), this.mContext.getResources().getInteger(android.R.integer.config_phonenumber_compare_min_match));
        this.mBatteryStatsService = injector.getBatteryStatsService();
        this.mFrameworkStatsLogger = injector.getFrameworkStatsLogger(this.mHandler);
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService(android.os.PowerManager.class)).newWakeLock(1, "*vibrator*");
        this.mWakeLock.setReferenceCounted(true);
        this.mVibrationThread = new com.android.server.vibrator.VibrationThread(this.mWakeLock, this.mVibrationThreadCallbacks);
        this.mVibrationThread.start();
        this.mCapabilities = this.mNativeWrapper.getCapabilities();
        int[] vibratorIds = this.mNativeWrapper.getVibratorIds();
        if (vibratorIds == null) {
            this.mVibratorIds = new int[0];
            this.mVibrators = new android.util.SparseArray<>(0);
        } else {
            this.mVibratorIds = vibratorIds;
            this.mVibrators = new android.util.SparseArray<>(this.mVibratorIds.length);
            for (int i : vibratorIds) {
                this.mVibrators.put(i, injector.createVibratorController(i, vibrationCompleteListener));
            }
        }
        this.mDeviceAdapter = new com.android.server.vibrator.DeviceAdapter(this.mVibrationSettings, this.mVibrators);
        this.mNativeWrapper.cancelSynced();
        for (int i2 = 0; i2 < this.mVibrators.size(); i2++) {
            this.mVibrators.valueAt(i2).reset();
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(this.mIntentReceiver, intentFilter, 4);
        injector.addService(EXTERNAL_VIBRATOR_SERVICE, new com.android.server.vibrator.VibratorManagerService.ExternalVibratorService());
        if (injector.isServiceDeclared(VIBRATOR_CONTROL_SERVICE)) {
            injector.addService(VIBRATOR_CONTROL_SERVICE, this.mVibratorControlService);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void systemReady() {
        android.util.Slog.v(TAG, "Initializing VibratorManager service...");
        android.os.Trace.traceBegin(8388608L, "systemReady");
        for (int i = 0; i < this.mVibrators.size(); i++) {
            try {
                this.mVibrators.valueAt(i).reloadVibratorInfoIfNeeded();
            } catch (java.lang.Throwable th) {
                synchronized (this.mLock) {
                    this.mServiceReady = true;
                    android.util.Slog.v(TAG, "VibratorManager service initialized");
                    android.os.Trace.traceEnd(8388608L);
                    throw th;
                }
            }
        }
        this.mVibrationSettings.onSystemReady();
        this.mInputDeviceDelegate.onSystemReady();
        this.mVibrationSettings.addListener(new com.android.server.vibrator.VibrationSettings.OnVibratorSettingsChanged() { // from class: com.android.server.vibrator.VibratorManagerService$$ExternalSyntheticLambda2
            @Override // com.android.server.vibrator.VibrationSettings.OnVibratorSettingsChanged
            public final void onChange() {
                com.android.server.vibrator.VibratorManagerService.this.updateServiceState();
            }
        });
        updateServiceState();
        synchronized (this.mLock) {
            this.mServiceReady = true;
        }
        android.util.Slog.v(TAG, "VibratorManager service initialized");
        android.os.Trace.traceEnd(8388608L);
    }

    public int[] getVibratorIds() {
        return java.util.Arrays.copyOf(this.mVibratorIds, this.mVibratorIds.length);
    }

    @android.annotation.Nullable
    public android.os.VibratorInfo getVibratorInfo(int i) {
        com.android.server.vibrator.VibratorController vibratorController = this.mVibrators.get(i);
        if (vibratorController == null) {
            return null;
        }
        android.os.VibratorInfo vibratorInfo = vibratorController.getVibratorInfo();
        synchronized (this.mLock) {
            try {
                if (this.mServiceReady) {
                    return vibratorInfo;
                }
                if (vibratorController.isVibratorInfoLoadSuccessful()) {
                    return vibratorInfo;
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_VIBRATOR_STATE")
    public boolean isVibrating(int i) {
        isVibrating_enforcePermission();
        com.android.server.vibrator.VibratorController vibratorController = this.mVibrators.get(i);
        return vibratorController != null && vibratorController.isVibrating();
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_VIBRATOR_STATE")
    public boolean registerVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) {
        registerVibratorStateListener_enforcePermission();
        com.android.server.vibrator.VibratorController vibratorController = this.mVibrators.get(i);
        if (vibratorController == null) {
            return false;
        }
        return vibratorController.registerVibratorStateListener(iVibratorStateListener);
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_VIBRATOR_STATE")
    public boolean unregisterVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) {
        unregisterVibratorStateListener_enforcePermission();
        com.android.server.vibrator.VibratorController vibratorController = this.mVibrators.get(i);
        if (vibratorController == null) {
            return false;
        }
        return vibratorController.unregisterVibratorStateListener(iVibratorStateListener);
    }

    public boolean setAlwaysOnEffect(int i, java.lang.String str, final int i2, @android.annotation.Nullable android.os.CombinedVibration combinedVibration, @android.annotation.Nullable android.os.VibrationAttributes vibrationAttributes) {
        android.os.Trace.traceBegin(8388608L, "setAlwaysOnEffect");
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.VIBRATE_ALWAYS_ON", "setAlwaysOnEffect");
            if (combinedVibration == null) {
                synchronized (this.mLock) {
                    this.mAlwaysOnEffects.delete(i2);
                    onAllVibratorsLocked(new java.util.function.Consumer() { // from class: com.android.server.vibrator.VibratorManagerService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.vibrator.VibratorManagerService.lambda$setAlwaysOnEffect$0(i2, (com.android.server.vibrator.VibratorController) obj);
                        }
                    });
                }
                android.os.Trace.traceEnd(8388608L);
                return true;
            }
            if (!isEffectValid(combinedVibration)) {
                android.os.Trace.traceEnd(8388608L);
                return false;
            }
            android.os.VibrationAttributes fixupVibrationAttributes = fixupVibrationAttributes(vibrationAttributes, combinedVibration);
            synchronized (this.mLock) {
                android.util.SparseArray<android.os.vibrator.PrebakedSegment> fixupAlwaysOnEffectsLocked = fixupAlwaysOnEffectsLocked(combinedVibration);
                if (fixupAlwaysOnEffectsLocked == null) {
                    android.os.Trace.traceEnd(8388608L);
                    return false;
                }
                com.android.server.vibrator.VibratorManagerService.AlwaysOnVibration alwaysOnVibration = new com.android.server.vibrator.VibratorManagerService.AlwaysOnVibration(i2, new com.android.server.vibrator.Vibration.CallerInfo(fixupVibrationAttributes, i, 0, str, null), fixupAlwaysOnEffectsLocked);
                this.mAlwaysOnEffects.put(i2, alwaysOnVibration);
                updateAlwaysOnLocked(alwaysOnVibration);
                android.os.Trace.traceEnd(8388608L);
                return true;
            }
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(8388608L);
            throw th;
        }
        android.os.Trace.traceEnd(8388608L);
        throw th;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setAlwaysOnEffect$0(int i, com.android.server.vibrator.VibratorController vibratorController) {
        if (vibratorController.hasCapability(64L)) {
            vibratorController.updateAlwaysOn(i, null);
        }
    }

    public void vibrate(int i, int i2, java.lang.String str, @android.annotation.NonNull android.os.CombinedVibration combinedVibration, @android.annotation.Nullable android.os.VibrationAttributes vibrationAttributes, java.lang.String str2, android.os.IBinder iBinder) {
        vibrateWithPermissionCheck(i, i2, str, combinedVibration, vibrationAttributes, str2, iBinder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void performHapticFeedback(int i, int i2, java.lang.String str, int i3, boolean z, java.lang.String str2, boolean z2) {
        performHapticFeedbackInternal(i, i2, str, i3, z, str2, this, z2);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.vibrator.HalVibration performHapticFeedbackInternal(int i, int i2, java.lang.String str, int i3, boolean z, java.lang.String str2, android.os.IBinder iBinder, boolean z2) {
        com.android.server.vibrator.HapticFeedbackVibrationProvider hapticVibrationProvider = getHapticVibrationProvider();
        if (hapticVibrationProvider == null) {
            android.util.Slog.w(TAG, "performHapticFeedback; haptic vibration provider not ready.");
            return null;
        }
        android.os.VibrationEffect vibrationForHapticFeedback = hapticVibrationProvider.getVibrationForHapticFeedback(i3);
        if (vibrationForHapticFeedback == null) {
            android.util.Slog.w(TAG, "performHapticFeedback; vibration absent for effect " + i3);
            return null;
        }
        android.os.CombinedVibration createParallel = android.os.CombinedVibration.createParallel(vibrationForHapticFeedback);
        android.os.VibrationAttributes vibrationAttributesForHapticFeedback = hapticVibrationProvider.getVibrationAttributesForHapticFeedback(i3, z, z2);
        com.android.server.vibrator.VibratorFrameworkStatsLogger.logPerformHapticsFeedbackIfKeyboard(i, i3);
        return vibrateWithoutPermissionCheck(i, i2, str, createParallel, vibrationAttributesForHapticFeedback, "performHapticFeedback: " + str2, iBinder);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.vibrator.HalVibration vibrateWithPermissionCheck(int i, int i2, java.lang.String str, @android.annotation.NonNull android.os.CombinedVibration combinedVibration, @android.annotation.Nullable android.os.VibrationAttributes vibrationAttributes, java.lang.String str2, android.os.IBinder iBinder) {
        android.os.Trace.traceBegin(8388608L, "vibrate, reason = " + str2);
        try {
            android.os.VibrationAttributes fixupVibrationAttributes = fixupVibrationAttributes(vibrationAttributes, combinedVibration);
            this.mContext.enforceCallingOrSelfPermission("android.permission.VIBRATE", "vibrate");
            return vibrateInternal(i, i2, str, combinedVibration, fixupVibrationAttributes, str2, iBinder);
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }

    com.android.server.vibrator.HalVibration vibrateWithoutPermissionCheck(int i, int i2, java.lang.String str, @android.annotation.NonNull android.os.CombinedVibration combinedVibration, @android.annotation.NonNull android.os.VibrationAttributes vibrationAttributes, java.lang.String str2, android.os.IBinder iBinder) {
        android.os.Trace.traceBegin(8388608L, "vibrate no perm check, reason = " + str2);
        try {
            return vibrateInternal(i, i2, str, combinedVibration, vibrationAttributes, str2, iBinder);
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }

    private com.android.server.vibrator.HalVibration vibrateInternal(int i, int i2, java.lang.String str, @android.annotation.NonNull android.os.CombinedVibration combinedVibration, @android.annotation.NonNull android.os.VibrationAttributes vibrationAttributes, java.lang.String str2, android.os.IBinder iBinder) {
        if (iBinder == null) {
            android.util.Slog.e(TAG, "token must not be null");
            return null;
        }
        enforceUpdateAppOpsStatsPermission(i);
        if (!isEffectValid(combinedVibration)) {
            return null;
        }
        com.android.server.vibrator.HalVibration halVibration = new com.android.server.vibrator.HalVibration(iBinder, combinedVibration, new com.android.server.vibrator.Vibration.CallerInfo(vibrationAttributes, i, i2, str, str2));
        fillVibrationFallbacks(halVibration, combinedVibration);
        if (vibrationAttributes.isFlagSet(4)) {
            this.mVibrationSettings.update();
        }
        synchronized (this.mLock) {
            try {
                com.android.server.vibrator.Vibration.EndInfo shouldIgnoreVibrationLocked = shouldIgnoreVibrationLocked(halVibration.callerInfo);
                if (shouldIgnoreVibrationLocked == null) {
                    shouldIgnoreVibrationLocked = shouldIgnoreVibrationForOngoingLocked(halVibration);
                }
                if (shouldIgnoreVibrationLocked == null) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (this.mCurrentExternalVibration != null) {
                            this.mCurrentExternalVibration.mute();
                            halVibration.stats.reportInterruptedAnotherVibration(this.mCurrentExternalVibration.callerInfo);
                            endExternalVibrateLocked(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_SUPERSEDED, halVibration.callerInfo), false);
                        } else if (this.mCurrentVibration != null && !this.mCurrentVibration.getVibration().canPipelineWith(halVibration)) {
                            halVibration.stats.reportInterruptedAnotherVibration(this.mCurrentVibration.getVibration().callerInfo);
                            this.mCurrentVibration.notifyCancelled(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_SUPERSEDED, halVibration.callerInfo), false);
                        }
                        com.android.server.vibrator.Vibration.EndInfo startVibrationLocked = startVibrationLocked(halVibration);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        shouldIgnoreVibrationLocked = startVibrationLocked;
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                if (shouldIgnoreVibrationLocked != null) {
                    endVibrationLocked(halVibration, shouldIgnoreVibrationLocked, true);
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
        return halVibration;
    }

    public void cancelVibrate(int i, android.os.IBinder iBinder) {
        android.os.Trace.traceBegin(8388608L, "cancelVibrate");
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.VIBRATE", "cancelVibrate");
            synchronized (this.mLock) {
                com.android.server.vibrator.Vibration.EndInfo endInfo = new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_BY_USER);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (this.mNextVibration != null && shouldCancelVibration(this.mNextVibration.getVibration(), i, iBinder)) {
                        clearNextVibrationLocked(endInfo);
                    }
                    if (this.mCurrentVibration != null && shouldCancelVibration(this.mCurrentVibration.getVibration(), i, iBinder)) {
                        this.mCurrentVibration.notifyCancelled(endInfo, false);
                    }
                    if (this.mCurrentExternalVibration != null && shouldCancelVibration(this.mCurrentExternalVibration.externalVibration.getVibrationAttributes(), i)) {
                        this.mCurrentExternalVibration.mute();
                        endExternalVibrateLocked(endInfo, false);
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            boolean z = false;
            for (java.lang.String str : strArr) {
                if (str.equals("--proto")) {
                    z = true;
                }
            }
            try {
                if (z) {
                    dumpProto(fileDescriptor);
                } else {
                    dumpText(printWriter);
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    private void dumpText(java.io.PrintWriter printWriter) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("VibratorManagerService:");
                indentingPrintWriter.increaseIndent();
                this.mVibrationSettings.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                this.mVibrationScaler.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Vibrators:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mVibrators.size(); i++) {
                    this.mVibrators.valueAt(i).dump(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("CurrentVibration:");
                indentingPrintWriter.increaseIndent();
                if (this.mCurrentVibration != null) {
                    this.mCurrentVibration.getVibration().getDebugInfo().dump(indentingPrintWriter);
                } else {
                    indentingPrintWriter.println("null");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("NextVibration:");
                indentingPrintWriter.increaseIndent();
                if (this.mNextVibration != null) {
                    this.mNextVibration.getVibration().getDebugInfo().dump(indentingPrintWriter);
                } else {
                    indentingPrintWriter.println("null");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("CurrentExternalVibration:");
                indentingPrintWriter.increaseIndent();
                if (this.mCurrentExternalVibration != null) {
                    this.mCurrentExternalVibration.getDebugInfo().dump(indentingPrintWriter);
                } else {
                    indentingPrintWriter.println("null");
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.println();
        indentingPrintWriter.println();
        this.mVibratorManagerRecords.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.println();
        this.mVibratorControlService.dump(indentingPrintWriter);
    }

    private void dumpProto(java.io.FileDescriptor fileDescriptor) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        synchronized (this.mLock) {
            try {
                this.mVibrationSettings.dump(protoOutputStream);
                this.mVibrationScaler.dump(protoOutputStream);
                if (this.mCurrentVibration != null) {
                    this.mCurrentVibration.getVibration().getDebugInfo().dump(protoOutputStream, 1146756268034L);
                }
                if (this.mCurrentExternalVibration != null) {
                    this.mCurrentExternalVibration.getDebugInfo().dump(protoOutputStream, 1146756268036L);
                }
                boolean z = false;
                boolean z2 = false;
                for (int i = 0; i < this.mVibrators.size(); i++) {
                    protoOutputStream.write(2220498092033L, this.mVibrators.keyAt(i));
                    z |= this.mVibrators.valueAt(i).isVibrating();
                    z2 |= this.mVibrators.valueAt(i).isUnderExternalControl();
                }
                protoOutputStream.write(1133871366147L, z);
                protoOutputStream.write(1133871366149L, z2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mVibratorManagerRecords.dump(protoOutputStream);
        this.mVibratorControlService.dump(protoOutputStream);
        protoOutputStream.flush();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand(shellCallback.getShellCallbackBinder()).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateServiceState() {
        synchronized (this.mLock) {
            try {
                boolean updateInputDeviceVibrators = this.mInputDeviceDelegate.updateInputDeviceVibrators(this.mVibrationSettings.shouldVibrateInputDevices());
                for (int i = 0; i < this.mAlwaysOnEffects.size(); i++) {
                    updateAlwaysOnLocked(this.mAlwaysOnEffects.valueAt(i));
                }
                if (this.mCurrentVibration == null) {
                    return;
                }
                com.android.server.vibrator.Vibration.EndInfo shouldIgnoreVibrationLocked = shouldIgnoreVibrationLocked(this.mCurrentVibration.getVibration().callerInfo);
                if (updateInputDeviceVibrators || shouldIgnoreVibrationLocked != null) {
                    this.mCurrentVibration.notifyCancelled(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_BY_SETTINGS_UPDATE), false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setExternalControl(boolean z, com.android.server.vibrator.VibrationStats vibrationStats) {
        for (int i = 0; i < this.mVibrators.size(); i++) {
            this.mVibrators.valueAt(i).setExternalControl(z);
            vibrationStats.reportSetExternalControl();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateAlwaysOnLocked(com.android.server.vibrator.VibratorManagerService.AlwaysOnVibration alwaysOnVibration) {
        android.os.vibrator.PrebakedSegment prebakedSegment;
        for (int i = 0; i < alwaysOnVibration.effects.size(); i++) {
            com.android.server.vibrator.VibratorController vibratorController = this.mVibrators.get(alwaysOnVibration.effects.keyAt(i));
            android.os.vibrator.PrebakedSegment valueAt = alwaysOnVibration.effects.valueAt(i);
            if (vibratorController != null) {
                if (shouldIgnoreVibrationLocked(alwaysOnVibration.callerInfo) == null) {
                    prebakedSegment = this.mVibrationScaler.scale(valueAt, alwaysOnVibration.callerInfo.attrs.getUsage());
                } else {
                    prebakedSegment = null;
                }
                vibratorController.updateAlwaysOn(alwaysOnVibration.alwaysOnId, prebakedSegment);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.vibrator.Vibration.EndInfo startVibrationLocked(com.android.server.vibrator.HalVibration halVibration) {
        android.os.Trace.traceBegin(8388608L, "startVibrationLocked");
        try {
            if (this.mInputDeviceDelegate.isAvailable()) {
                return startVibrationOnInputDevicesLocked(halVibration);
            }
            com.android.server.vibrator.VibrationStepConductor createVibrationStepConductor = createVibrationStepConductor(halVibration);
            if (this.mCurrentVibration == null) {
                return startVibrationOnThreadLocked(createVibrationStepConductor);
            }
            clearNextVibrationLocked(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_SUPERSEDED, halVibration.callerInfo));
            this.mNextVibration = createVibrationStepConductor;
            android.os.Trace.traceEnd(8388608L);
            return null;
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public com.android.server.vibrator.Vibration.EndInfo startVibrationOnThreadLocked(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor) {
        android.os.Trace.traceBegin(8388608L, "startVibrationThreadLocked");
        try {
            com.android.server.vibrator.HalVibration vibration = vibrationStepConductor.getVibration();
            switch (startAppOpModeLocked(vibration.callerInfo)) {
                case 0:
                    android.os.Trace.asyncTraceBegin(8388608L, "vibration", 0);
                    this.mCurrentVibration = vibrationStepConductor;
                    if (this.mVibrationThread.runVibrationOnVibrationThread(this.mCurrentVibration)) {
                        return null;
                    }
                    this.mCurrentVibration = null;
                    return new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_ERROR_SCHEDULING);
                case 1:
                default:
                    return new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_APP_OPS);
                case 2:
                    android.util.Slog.w(TAG, "Start AppOpsManager operation errored for uid " + vibration.callerInfo.uid);
                    return new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_ERROR_APP_OPS);
            }
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void endVibrationLocked(com.android.server.vibrator.HalVibration halVibration, com.android.server.vibrator.Vibration.EndInfo endInfo, boolean z) {
        halVibration.end(endInfo);
        logVibrationStatus(halVibration.callerInfo.uid, halVibration.callerInfo.attrs, endInfo.status);
        this.mVibratorManagerRecords.record(halVibration);
        if (z) {
            this.mFrameworkStatsLogger.writeVibrationReportedAsync(halVibration.getStatsInfo(android.os.SystemClock.uptimeMillis()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void endVibrationAndWriteStatsLocked(com.android.server.vibrator.VibratorManagerService.ExternalVibrationHolder externalVibrationHolder, com.android.server.vibrator.Vibration.EndInfo endInfo) {
        externalVibrationHolder.end(endInfo);
        logVibrationStatus(externalVibrationHolder.externalVibration.getUid(), externalVibrationHolder.externalVibration.getVibrationAttributes(), endInfo.status);
        this.mVibratorManagerRecords.record(externalVibrationHolder);
        this.mFrameworkStatsLogger.writeVibrationReportedAsync(externalVibrationHolder.getStatsInfo(android.os.SystemClock.uptimeMillis()));
    }

    private com.android.server.vibrator.VibrationStepConductor createVibrationStepConductor(com.android.server.vibrator.HalVibration halVibration) {
        java.util.concurrent.CompletableFuture<java.lang.Void> completableFuture;
        if (android.os.vibrator.Flags.adaptiveHapticsEnabled() && !halVibration.callerInfo.attrs.isFlagSet(16) && this.mVibratorControlService.shouldRequestVibrationParams(halVibration.callerInfo.attrs.getUsage())) {
            completableFuture = this.mVibratorControlService.triggerVibrationParamsRequest(halVibration.callerInfo.attrs.getUsage(), this.mVibrationSettings.getRequestVibrationParamsTimeoutMs());
        } else {
            completableFuture = null;
        }
        return new com.android.server.vibrator.VibrationStepConductor(halVibration, this.mVibrationSettings, this.mDeviceAdapter, this.mVibrationScaler, completableFuture, this.mVibrationThreadCallbacks);
    }

    private com.android.server.vibrator.Vibration.EndInfo startVibrationOnInputDevicesLocked(com.android.server.vibrator.HalVibration halVibration) {
        if (!halVibration.callerInfo.attrs.isFlagSet(16)) {
            halVibration.scaleEffects(this.mVibrationScaler);
        } else {
            halVibration.resolveEffects(this.mVibrationScaler.getDefaultVibrationAmplitude());
        }
        this.mInputDeviceDelegate.vibrateIfAvailable(halVibration.callerInfo, halVibration.getEffectToPlay());
        return new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.FORWARDED_TO_INPUT_DEVICES);
    }

    /* renamed from: com.android.server.vibrator.VibratorManagerService$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$android$server$vibrator$Vibration$Status = new int[com.android.server.vibrator.Vibration.Status.values().length];

        static {
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[com.android.server.vibrator.Vibration.Status.IGNORED_BACKGROUND.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[com.android.server.vibrator.Vibration.Status.IGNORED_ERROR_APP_OPS.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[com.android.server.vibrator.Vibration.Status.IGNORED_FOR_EXTERNAL.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[com.android.server.vibrator.Vibration.Status.IGNORED_FOR_HIGHER_IMPORTANCE.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[com.android.server.vibrator.Vibration.Status.IGNORED_FOR_ONGOING.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[com.android.server.vibrator.Vibration.Status.IGNORED_FOR_RINGER_MODE.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$server$vibrator$Vibration$Status[com.android.server.vibrator.Vibration.Status.IGNORED_FROM_VIRTUAL_DEVICE.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
        }
    }

    private void logVibrationStatus(int i, android.os.VibrationAttributes vibrationAttributes, com.android.server.vibrator.Vibration.Status status) {
        switch (com.android.server.vibrator.VibratorManagerService.AnonymousClass2.$SwitchMap$com$android$server$vibrator$Vibration$Status[status.ordinal()]) {
            case 1:
                android.util.Slog.e(TAG, "Ignoring incoming vibration as process with uid= " + i + " is background, attrs= " + vibrationAttributes);
                break;
            case 2:
                android.util.Slog.w(TAG, "Would be an error: vibrate from uid " + i);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void reportFinishedVibrationLocked(com.android.server.vibrator.Vibration.EndInfo endInfo) {
        android.os.Trace.traceBegin(8388608L, "reportFinishVibrationLocked");
        android.os.Trace.asyncTraceEnd(8388608L, "vibration", 0);
        try {
            com.android.server.vibrator.HalVibration vibration = this.mCurrentVibration.getVibration();
            endVibrationLocked(vibration, endInfo, false);
            finishAppOpModeLocked(vibration.callerInfo);
        } finally {
            android.os.Trace.traceEnd(8388608L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSyncedVibrationComplete(long j) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentVibration != null && this.mCurrentVibration.getVibration().id == j) {
                    this.mCurrentVibration.notifySyncedVibrationComplete();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVibrationComplete(int i, long j) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentVibration != null && this.mCurrentVibration.getVibration().id == j) {
                    this.mCurrentVibration.notifyVibratorComplete(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public com.android.server.vibrator.Vibration.EndInfo shouldIgnoreVibrationForOngoingLocked(com.android.server.vibrator.Vibration vibration) {
        com.android.server.vibrator.Vibration.EndInfo shouldIgnoreVibrationForOngoing;
        if (this.mCurrentExternalVibration != null) {
            return shouldIgnoreVibrationForOngoing(vibration, this.mCurrentExternalVibration);
        }
        if (this.mNextVibration != null && (shouldIgnoreVibrationForOngoing = shouldIgnoreVibrationForOngoing(vibration, this.mNextVibration.getVibration())) != null) {
            return shouldIgnoreVibrationForOngoing;
        }
        if (this.mCurrentVibration == null) {
            return null;
        }
        com.android.server.vibrator.HalVibration vibration2 = this.mCurrentVibration.getVibration();
        if (vibration2.hasEnded() || this.mCurrentVibration.wasNotifiedToCancel()) {
            return null;
        }
        return shouldIgnoreVibrationForOngoing(vibration, vibration2);
    }

    @android.annotation.Nullable
    private static com.android.server.vibrator.Vibration.EndInfo shouldIgnoreVibrationForOngoing(@android.annotation.NonNull com.android.server.vibrator.Vibration vibration, @android.annotation.NonNull com.android.server.vibrator.Vibration vibration2) {
        int vibrationImportance = getVibrationImportance(vibration);
        int vibrationImportance2 = getVibrationImportance(vibration2);
        if (vibrationImportance > vibrationImportance2) {
            return null;
        }
        if (vibrationImportance2 > vibrationImportance) {
            return new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_FOR_HIGHER_IMPORTANCE, vibration2.callerInfo);
        }
        if (!vibration2.isRepeating() || vibration.isRepeating()) {
            return null;
        }
        return new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_FOR_ONGOING, vibration2.callerInfo);
    }

    private static int getVibrationImportance(com.android.server.vibrator.Vibration vibration) {
        int usage = vibration.callerInfo.attrs.getUsage();
        if (usage == 0) {
            if (vibration.isRepeating()) {
                usage = 33;
            } else {
                usage = 18;
            }
        }
        switch (usage) {
            case 17:
                return 4;
            case 33:
                return 5;
            case 34:
            case 50:
                return 1;
            case 49:
                return 3;
            case 65:
            case 66:
                return 2;
            default:
                return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public com.android.server.vibrator.Vibration.EndInfo shouldIgnoreVibrationLocked(com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
        com.android.server.vibrator.Vibration.Status shouldIgnoreVibration = this.mVibrationSettings.shouldIgnoreVibration(callerInfo);
        if (shouldIgnoreVibration != null) {
            return new com.android.server.vibrator.Vibration.EndInfo(shouldIgnoreVibration);
        }
        int checkAppOpModeLocked = checkAppOpModeLocked(callerInfo);
        if (checkAppOpModeLocked != 0) {
            if (checkAppOpModeLocked == 2) {
                return new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_ERROR_APP_OPS);
            }
            return new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_APP_OPS);
        }
        return null;
    }

    private boolean shouldCancelVibration(com.android.server.vibrator.HalVibration halVibration, int i, android.os.IBinder iBinder) {
        return halVibration.callerToken == iBinder && shouldCancelVibration(halVibration.callerInfo.attrs, i);
    }

    private boolean shouldCancelVibration(android.os.VibrationAttributes vibrationAttributes, int i) {
        return vibrationAttributes.getUsage() == 0 ? i == 0 || i == -1 : (i & vibrationAttributes.getUsage()) == vibrationAttributes.getUsage();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int checkAppOpModeLocked(com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
        int checkAudioOpNoThrow = this.mAppOps.checkAudioOpNoThrow(3, callerInfo.attrs.getAudioUsage(), callerInfo.uid, callerInfo.opPkg);
        int fixupAppOpModeLocked = fixupAppOpModeLocked(checkAudioOpNoThrow, callerInfo.attrs);
        if (checkAudioOpNoThrow != fixupAppOpModeLocked && fixupAppOpModeLocked == 0) {
            android.util.Slog.d(TAG, "Bypassing DND for vibrate from uid " + callerInfo.uid);
        }
        return fixupAppOpModeLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int startAppOpModeLocked(com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
        return fixupAppOpModeLocked(this.mAppOps.startOpNoThrow(3, callerInfo.uid, callerInfo.opPkg), callerInfo.attrs);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void finishAppOpModeLocked(com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
        this.mAppOps.finishOp(3, callerInfo.uid, callerInfo.opPkg);
    }

    private void enforceUpdateAppOpsStatsPermission(int i) {
        if (i == android.os.Binder.getCallingUid() || android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
            return;
        }
        this.mContext.enforcePermission("android.permission.UPDATE_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), null);
    }

    private static boolean isEffectValid(@android.annotation.Nullable android.os.CombinedVibration combinedVibration) {
        if (combinedVibration == null) {
            android.util.Slog.wtf(TAG, "effect must not be null");
            return false;
        }
        try {
            combinedVibration.validate();
            return true;
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, "Encountered issue when verifying CombinedVibrationEffect.", e);
            return false;
        }
    }

    private void fillVibrationFallbacks(com.android.server.vibrator.HalVibration halVibration, android.os.CombinedVibration combinedVibration) {
        if (combinedVibration instanceof android.os.CombinedVibration.Mono) {
            fillVibrationFallbacks(halVibration, ((android.os.CombinedVibration.Mono) combinedVibration).getEffect());
            return;
        }
        int i = 0;
        if (combinedVibration instanceof android.os.CombinedVibration.Stereo) {
            android.util.SparseArray effects = ((android.os.CombinedVibration.Stereo) combinedVibration).getEffects();
            while (i < effects.size()) {
                fillVibrationFallbacks(halVibration, (android.os.VibrationEffect) effects.valueAt(i));
                i++;
            }
            return;
        }
        if (combinedVibration instanceof android.os.CombinedVibration.Sequential) {
            java.util.List effects2 = ((android.os.CombinedVibration.Sequential) combinedVibration).getEffects();
            while (i < effects2.size()) {
                fillVibrationFallbacks(halVibration, (android.os.CombinedVibration) effects2.get(i));
                i++;
            }
        }
    }

    private void fillVibrationFallbacks(com.android.server.vibrator.HalVibration halVibration, android.os.VibrationEffect vibrationEffect) {
        android.os.VibrationEffect.Composed composed = (android.os.VibrationEffect.Composed) vibrationEffect;
        int size = composed.getSegments().size();
        for (int i = 0; i < size; i++) {
            android.os.vibrator.PrebakedSegment prebakedSegment = (android.os.vibrator.VibrationEffectSegment) composed.getSegments().get(i);
            if (prebakedSegment instanceof android.os.vibrator.PrebakedSegment) {
                android.os.vibrator.PrebakedSegment prebakedSegment2 = prebakedSegment;
                android.os.VibrationEffect fallbackEffect = this.mVibrationSettings.getFallbackEffect(prebakedSegment2.getEffectId());
                if (prebakedSegment2.shouldFallback() && fallbackEffect != null) {
                    halVibration.addFallback(prebakedSegment2.getEffectId(), fallbackEffect);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public android.os.VibrationAttributes fixupVibrationAttributes(@android.annotation.Nullable android.os.VibrationAttributes vibrationAttributes, @android.annotation.Nullable android.os.CombinedVibration combinedVibration) {
        if (vibrationAttributes == null) {
            vibrationAttributes = DEFAULT_ATTRIBUTES;
        }
        int usage = vibrationAttributes.getUsage();
        if (usage == 0 && combinedVibration != null && combinedVibration.isHapticFeedbackCandidate()) {
            usage = 18;
        }
        int flags = vibrationAttributes.getFlags();
        if ((flags & 19) != 0 && !hasPermission("android.permission.WRITE_SECURE_SETTINGS") && !hasPermission("android.permission.MODIFY_PHONE_STATE") && !hasPermission("android.permission.MODIFY_AUDIO_ROUTING")) {
            flags &= -20;
        }
        if (usage == vibrationAttributes.getUsage() && flags == vibrationAttributes.getFlags()) {
            return vibrationAttributes;
        }
        return new android.os.VibrationAttributes.Builder(vibrationAttributes).setUsage(usage).setFlags(flags, vibrationAttributes.getFlags()).build();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.util.SparseArray<android.os.vibrator.PrebakedSegment> fixupAlwaysOnEffectsLocked(android.os.CombinedVibration combinedVibration) {
        android.util.SparseArray effects;
        android.os.Trace.traceBegin(8388608L, "fixupAlwaysOnEffectsLocked");
        try {
            if (combinedVibration instanceof android.os.CombinedVibration.Mono) {
                final android.os.VibrationEffect effect = ((android.os.CombinedVibration.Mono) combinedVibration).getEffect();
                effects = transformAllVibratorsLocked(new java.util.function.Function() { // from class: com.android.server.vibrator.VibratorManagerService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        android.os.VibrationEffect lambda$fixupAlwaysOnEffectsLocked$1;
                        lambda$fixupAlwaysOnEffectsLocked$1 = com.android.server.vibrator.VibratorManagerService.lambda$fixupAlwaysOnEffectsLocked$1(effect, (com.android.server.vibrator.VibratorController) obj);
                        return lambda$fixupAlwaysOnEffectsLocked$1;
                    }
                });
            } else {
                if (!(combinedVibration instanceof android.os.CombinedVibration.Stereo)) {
                    android.os.Trace.traceEnd(8388608L);
                    return null;
                }
                effects = ((android.os.CombinedVibration.Stereo) combinedVibration).getEffects();
            }
            android.util.SparseArray<android.os.vibrator.PrebakedSegment> sparseArray = new android.util.SparseArray<>();
            for (int i = 0; i < effects.size(); i++) {
                android.os.vibrator.PrebakedSegment extractPrebakedSegment = extractPrebakedSegment((android.os.VibrationEffect) effects.valueAt(i));
                if (extractPrebakedSegment == null) {
                    android.util.Slog.e(TAG, "Only prebaked effects supported for always-on.");
                    android.os.Trace.traceEnd(8388608L);
                    return null;
                }
                int keyAt = effects.keyAt(i);
                com.android.server.vibrator.VibratorController vibratorController = this.mVibrators.get(keyAt);
                if (vibratorController != null && vibratorController.hasCapability(64L)) {
                    sparseArray.put(keyAt, extractPrebakedSegment);
                }
            }
            if (sparseArray.size() == 0) {
                android.os.Trace.traceEnd(8388608L);
                return null;
            }
            android.os.Trace.traceEnd(8388608L);
            return sparseArray;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(8388608L);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.os.VibrationEffect lambda$fixupAlwaysOnEffectsLocked$1(android.os.VibrationEffect vibrationEffect, com.android.server.vibrator.VibratorController vibratorController) {
        return vibrationEffect;
    }

    @android.annotation.Nullable
    private static android.os.vibrator.PrebakedSegment extractPrebakedSegment(android.os.VibrationEffect vibrationEffect) {
        if (vibrationEffect instanceof android.os.VibrationEffect.Composed) {
            android.os.VibrationEffect.Composed composed = (android.os.VibrationEffect.Composed) vibrationEffect;
            if (composed.getSegments().size() == 1) {
                android.os.vibrator.PrebakedSegment prebakedSegment = (android.os.vibrator.VibrationEffectSegment) composed.getSegments().get(0);
                if (prebakedSegment instanceof android.os.vibrator.PrebakedSegment) {
                    return prebakedSegment;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int fixupAppOpModeLocked(int i, android.os.VibrationAttributes vibrationAttributes) {
        if (i == 1 && vibrationAttributes.isFlagSet(1)) {
            return 0;
        }
        return i;
    }

    private boolean hasPermission(java.lang.String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean shouldCancelOnScreenOffLocked(@android.annotation.Nullable com.android.server.vibrator.VibrationStepConductor vibrationStepConductor) {
        if (vibrationStepConductor == null) {
            return false;
        }
        com.android.server.vibrator.HalVibration vibration = vibrationStepConductor.getVibration();
        return this.mVibrationSettings.shouldCancelVibrationOnScreenOff(vibration.callerInfo, vibration.stats.getCreateUptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onAllVibratorsLocked(java.util.function.Consumer<com.android.server.vibrator.VibratorController> consumer) {
        for (int i = 0; i < this.mVibrators.size(); i++) {
            consumer.accept(this.mVibrators.valueAt(i));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private <T> android.util.SparseArray<T> transformAllVibratorsLocked(java.util.function.Function<com.android.server.vibrator.VibratorController, T> function) {
        android.util.SparseArray<T> sparseArray = new android.util.SparseArray<>(this.mVibrators.size());
        for (int i = 0; i < this.mVibrators.size(); i++) {
            sparseArray.put(this.mVibrators.keyAt(i), function.apply(this.mVibrators.valueAt(i)));
        }
        return sparseArray;
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.vibrator.VibratorManagerService.NativeWrapper getNativeWrapper() {
            return new com.android.server.vibrator.VibratorManagerService.NativeWrapper();
        }

        android.os.Handler createHandler(android.os.Looper looper) {
            return new android.os.Handler(looper);
        }

        com.android.internal.app.IBatteryStats getBatteryStatsService() {
            return com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getService("batterystats"));
        }

        com.android.server.vibrator.VibratorFrameworkStatsLogger getFrameworkStatsLogger(android.os.Handler handler) {
            return new com.android.server.vibrator.VibratorFrameworkStatsLogger(handler);
        }

        com.android.server.vibrator.VibratorController createVibratorController(int i, com.android.server.vibrator.VibratorController.OnVibrationCompleteListener onVibrationCompleteListener) {
            return new com.android.server.vibrator.VibratorController(i, onVibrationCompleteListener);
        }

        com.android.server.vibrator.HapticFeedbackVibrationProvider createHapticFeedbackVibrationProvider(android.content.res.Resources resources, android.os.VibratorInfo vibratorInfo) {
            return new com.android.server.vibrator.HapticFeedbackVibrationProvider(resources, vibratorInfo);
        }

        void addService(java.lang.String str, android.os.IBinder iBinder) {
            android.os.ServiceManager.addService(str, iBinder);
        }

        com.android.server.vibrator.VibratorControllerHolder createVibratorControllerHolder() {
            return new com.android.server.vibrator.VibratorControllerHolder();
        }

        boolean isServiceDeclared(java.lang.String str) {
            return android.os.ServiceManager.isDeclared(str);
        }
    }

    private final class VibrationThreadCallbacks implements com.android.server.vibrator.VibrationThread.VibratorManagerHooks {
        private VibrationThreadCallbacks() {
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public boolean prepareSyncedVibration(long j, int[] iArr) {
            if ((com.android.server.vibrator.VibratorManagerService.this.mCapabilities & j) != j) {
                return false;
            }
            return com.android.server.vibrator.VibratorManagerService.this.mNativeWrapper.prepareSynced(iArr);
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public boolean triggerSyncedVibration(long j) {
            return com.android.server.vibrator.VibratorManagerService.this.mNativeWrapper.triggerSynced(j);
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public void cancelSyncedVibration() {
            com.android.server.vibrator.VibratorManagerService.this.mNativeWrapper.cancelSynced();
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public void noteVibratorOn(int i, long j) {
            if (j <= 0) {
                return;
            }
            if (j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                j = 5000;
            }
            try {
                com.android.server.vibrator.VibratorManagerService.this.mBatteryStatsService.noteVibratorOn(i, j);
                com.android.server.vibrator.VibratorManagerService.this.mFrameworkStatsLogger.writeVibratorStateOnAsync(i, j);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.vibrator.VibratorManagerService.TAG, "Error logging VibratorStateChanged to ON", e);
            }
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public void noteVibratorOff(int i) {
            try {
                com.android.server.vibrator.VibratorManagerService.this.mBatteryStatsService.noteVibratorOff(i);
                com.android.server.vibrator.VibratorManagerService.this.mFrameworkStatsLogger.writeVibratorStateOffAsync(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.vibrator.VibratorManagerService.TAG, "Error logging VibratorStateChanged to OFF", e);
            }
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public void onVibrationCompleted(long j, com.android.server.vibrator.Vibration.EndInfo endInfo) {
            synchronized (com.android.server.vibrator.VibratorManagerService.this.mLock) {
                try {
                    if (com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration != null && com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration.getVibration().id == j) {
                        com.android.server.vibrator.VibratorManagerService.this.reportFinishedVibrationLocked(endInfo);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.vibrator.VibrationThread.VibratorManagerHooks
        public void onVibrationThreadReleased(long j) {
            synchronized (com.android.server.vibrator.VibratorManagerService.this.mLock) {
                try {
                    if (android.os.Build.IS_DEBUGGABLE && com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration != null && com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration.getVibration().id != j) {
                        android.util.Slog.wtf(com.android.server.vibrator.VibratorManagerService.TAG, android.text.TextUtils.formatSimple("VibrationId mismatch on release. expected=%d, released=%d", new java.lang.Object[]{java.lang.Long.valueOf(com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration.getVibration().id), java.lang.Long.valueOf(j)}));
                    }
                    if (com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration != null) {
                        com.android.server.vibrator.VibratorManagerService.this.mFrameworkStatsLogger.writeVibrationReportedAsync(com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration.getVibration().getStatsInfo(android.os.SystemClock.uptimeMillis()));
                        com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration = null;
                    }
                    if (com.android.server.vibrator.VibratorManagerService.this.mNextVibration != null) {
                        com.android.server.vibrator.VibrationStepConductor vibrationStepConductor = com.android.server.vibrator.VibratorManagerService.this.mNextVibration;
                        com.android.server.vibrator.VibratorManagerService.this.mNextVibration = null;
                        com.android.server.vibrator.Vibration.EndInfo startVibrationOnThreadLocked = com.android.server.vibrator.VibratorManagerService.this.startVibrationOnThreadLocked(vibrationStepConductor);
                        if (startVibrationOnThreadLocked != null) {
                            com.android.server.vibrator.VibratorManagerService.this.endVibrationLocked(vibrationStepConductor.getVibration(), startVibrationOnThreadLocked, true);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static final class VibrationCompleteListener implements com.android.server.vibrator.VibratorController.OnVibrationCompleteListener, com.android.server.vibrator.VibratorManagerService.OnSyncedVibrationCompleteListener {
        private java.lang.ref.WeakReference<com.android.server.vibrator.VibratorManagerService> mServiceRef;

        VibrationCompleteListener(com.android.server.vibrator.VibratorManagerService vibratorManagerService) {
            this.mServiceRef = new java.lang.ref.WeakReference<>(vibratorManagerService);
        }

        @Override // com.android.server.vibrator.VibratorManagerService.OnSyncedVibrationCompleteListener
        public void onComplete(long j) {
            com.android.server.vibrator.VibratorManagerService vibratorManagerService = this.mServiceRef.get();
            if (vibratorManagerService != null) {
                vibratorManagerService.onSyncedVibrationComplete(j);
            }
        }

        @Override // com.android.server.vibrator.VibratorController.OnVibrationCompleteListener
        public void onComplete(int i, long j) {
            com.android.server.vibrator.VibratorManagerService vibratorManagerService = this.mServiceRef.get();
            if (vibratorManagerService != null) {
                vibratorManagerService.onVibrationComplete(i, j);
            }
        }
    }

    private static final class AlwaysOnVibration {
        public final int alwaysOnId;
        public final com.android.server.vibrator.Vibration.CallerInfo callerInfo;
        public final android.util.SparseArray<android.os.vibrator.PrebakedSegment> effects;

        AlwaysOnVibration(int i, com.android.server.vibrator.Vibration.CallerInfo callerInfo, android.util.SparseArray<android.os.vibrator.PrebakedSegment> sparseArray) {
            this.alwaysOnId = i;
            this.callerInfo = callerInfo;
            this.effects = sparseArray;
        }
    }

    private final class ExternalVibrationHolder extends com.android.server.vibrator.Vibration implements android.os.IBinder.DeathRecipient {
        public final android.os.ExternalVibration externalVibration;
        private com.android.server.vibrator.Vibration.Status mStatus;
        public android.os.ExternalVibrationScale scale;

        private ExternalVibrationHolder(android.os.ExternalVibration externalVibration) {
            super(externalVibration.getToken(), new com.android.server.vibrator.Vibration.CallerInfo(externalVibration.getVibrationAttributes(), externalVibration.getUid(), -1, externalVibration.getPackage(), null));
            this.scale = new android.os.ExternalVibrationScale();
            this.externalVibration = externalVibration;
            this.mStatus = com.android.server.vibrator.Vibration.Status.RUNNING;
        }

        public void mute() {
            this.externalVibration.mute();
        }

        public void linkToDeath() {
            this.externalVibration.linkToDeath(this);
        }

        public void unlinkToDeath() {
            this.externalVibration.unlinkToDeath(this);
        }

        public boolean isHoldingSameVibration(android.os.ExternalVibration externalVibration) {
            return this.externalVibration.equals(externalVibration);
        }

        public void end(com.android.server.vibrator.Vibration.EndInfo endInfo) {
            if (this.mStatus != com.android.server.vibrator.Vibration.Status.RUNNING) {
                return;
            }
            this.mStatus = endInfo.status;
            this.stats.reportEnded(endInfo.endedBy);
            if (this.stats.hasStarted()) {
                this.stats.reportVibratorOn(this.stats.getEndUptimeMillis() - this.stats.getStartUptimeMillis());
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.vibrator.VibratorManagerService.this.mLock) {
                try {
                    if (com.android.server.vibrator.VibratorManagerService.this.mCurrentExternalVibration != null) {
                        com.android.server.vibrator.VibratorManagerService.this.endExternalVibrateLocked(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_BINDER_DIED), false);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public com.android.server.vibrator.Vibration.DebugInfo getDebugInfo() {
            return new com.android.server.vibrator.Vibration.DebugInfo(this.mStatus, this.stats, null, null, this.scale.scaleLevel, this.scale.adaptiveHapticsScale, this.callerInfo);
        }

        public com.android.server.vibrator.VibrationStats.StatsInfo getStatsInfo(long j) {
            return new com.android.server.vibrator.VibrationStats.StatsInfo(this.externalVibration.getUid(), 3, this.externalVibration.getVibrationAttributes().getUsage(), this.mStatus, this.stats, j);
        }

        @Override // com.android.server.vibrator.Vibration
        boolean isRepeating() {
            int usage = this.externalVibration.getVibrationAttributes().getUsage();
            return usage == 33 || usage == 17;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class NativeWrapper {
        private long mNativeServicePtr = 0;

        public void init(com.android.server.vibrator.VibratorManagerService.OnSyncedVibrationCompleteListener onSyncedVibrationCompleteListener) {
            this.mNativeServicePtr = com.android.server.vibrator.VibratorManagerService.nativeInit(onSyncedVibrationCompleteListener);
            long nativeGetFinalizer = com.android.server.vibrator.VibratorManagerService.nativeGetFinalizer();
            if (nativeGetFinalizer != 0) {
                libcore.util.NativeAllocationRegistry.createMalloced(com.android.server.vibrator.VibratorManagerService.class.getClassLoader(), nativeGetFinalizer).registerNativeAllocation(this, this.mNativeServicePtr);
            }
        }

        public long getCapabilities() {
            return com.android.server.vibrator.VibratorManagerService.nativeGetCapabilities(this.mNativeServicePtr);
        }

        public int[] getVibratorIds() {
            return com.android.server.vibrator.VibratorManagerService.nativeGetVibratorIds(this.mNativeServicePtr);
        }

        public boolean prepareSynced(@android.annotation.NonNull int[] iArr) {
            return com.android.server.vibrator.VibratorManagerService.nativePrepareSynced(this.mNativeServicePtr, iArr);
        }

        public boolean triggerSynced(long j) {
            return com.android.server.vibrator.VibratorManagerService.nativeTriggerSynced(this.mNativeServicePtr, j);
        }

        public void cancelSynced() {
            com.android.server.vibrator.VibratorManagerService.nativeCancelSynced(this.mNativeServicePtr);
        }
    }

    private static final class VibratorManagerRecords {
        private final com.android.server.vibrator.VibratorManagerService.VibrationRecords mAggregatedVibrationHistory;
        private final com.android.server.vibrator.VibratorManagerService.VibrationRecords mRecentVibrations;

        VibratorManagerRecords(int i, int i2, int i3) {
            this.mAggregatedVibrationHistory = new com.android.server.vibrator.VibratorManagerService.VibrationRecords(i2, i3);
            this.mRecentVibrations = new com.android.server.vibrator.VibratorManagerService.VibrationRecords(i, 0);
        }

        synchronized void record(com.android.server.vibrator.HalVibration halVibration) {
            record(halVibration.getDebugInfo());
        }

        synchronized void record(com.android.server.vibrator.VibratorManagerService.ExternalVibrationHolder externalVibrationHolder) {
            record(externalVibrationHolder.getDebugInfo());
        }

        private synchronized void record(com.android.server.vibrator.Vibration.DebugInfo debugInfo) {
            com.android.server.vibrator.GroupedAggregatedLogRecords.AggregatedLogRecord<com.android.server.vibrator.VibratorManagerService.VibrationRecord> add = this.mRecentVibrations.add(new com.android.server.vibrator.VibratorManagerService.VibrationRecord(debugInfo));
            if (add != null) {
                this.mAggregatedVibrationHistory.add(add.getLatest());
            }
        }

        synchronized void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Recent vibrations:");
            indentingPrintWriter.increaseIndent();
            this.mRecentVibrations.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println();
            indentingPrintWriter.println("Aggregated vibration history:");
            indentingPrintWriter.increaseIndent();
            this.mAggregatedVibrationHistory.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }

        synchronized void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
            this.mRecentVibrations.dump(protoOutputStream);
        }
    }

    private static final class VibrationRecords extends com.android.server.vibrator.GroupedAggregatedLogRecords<com.android.server.vibrator.VibratorManagerService.VibrationRecord> {
        VibrationRecords(int i, int i2) {
            super(i, i2);
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords
        void dumpGroupHeader(android.util.IndentingPrintWriter indentingPrintWriter, int i) {
            indentingPrintWriter.println(android.os.VibrationAttributes.usageToString(i) + ":");
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords
        long findGroupKeyProtoFieldId(int i) {
            switch (i) {
                case 17:
                    return 2246267895823L;
                case 33:
                    return 2246267895821L;
                case 49:
                    return 2246267895822L;
                default:
                    return 2246267895824L;
            }
        }
    }

    private static final class VibrationRecord implements com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord {
        private final com.android.server.vibrator.Vibration.DebugInfo mInfo;

        VibrationRecord(com.android.server.vibrator.Vibration.DebugInfo debugInfo) {
            this.mInfo = debugInfo;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public int getGroupKey() {
            return this.mInfo.mCallerInfo.attrs.getUsage();
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public long getCreateUptimeMs() {
            return this.mInfo.mCreateTime;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public boolean mayAggregate(com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord singleLogRecord) {
            if (!(singleLogRecord instanceof com.android.server.vibrator.VibratorManagerService.VibrationRecord)) {
                return false;
            }
            com.android.server.vibrator.Vibration.DebugInfo debugInfo = ((com.android.server.vibrator.VibratorManagerService.VibrationRecord) singleLogRecord).mInfo;
            return this.mInfo.mCallerInfo.uid == debugInfo.mCallerInfo.uid && java.util.Objects.equals(this.mInfo.mCallerInfo.attrs, debugInfo.mCallerInfo.attrs) && java.util.Objects.equals(this.mInfo.mPlayedEffect, debugInfo.mPlayedEffect);
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            this.mInfo.dumpCompact(indentingPrintWriter);
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            this.mInfo.dump(protoOutputStream, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void clearNextVibrationLocked(com.android.server.vibrator.Vibration.EndInfo endInfo) {
        if (this.mNextVibration != null) {
            endVibrationLocked(this.mNextVibration.getVibration(), endInfo, true);
            this.mNextVibration = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void endExternalVibrateLocked(com.android.server.vibrator.Vibration.EndInfo endInfo, boolean z) {
        android.os.Trace.traceBegin(8388608L, "endExternalVibrateLocked");
        try {
            if (this.mCurrentExternalVibration == null) {
                android.os.Trace.traceEnd(8388608L);
                return;
            }
            this.mCurrentExternalVibration.unlinkToDeath();
            if (!z) {
                setExternalControl(false, this.mCurrentExternalVibration.stats);
            }
            endVibrationAndWriteStatsLocked(this.mCurrentExternalVibration, endInfo);
            this.mCurrentExternalVibration = null;
            android.os.Trace.traceEnd(8388608L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(8388608L);
            throw th;
        }
    }

    private com.android.server.vibrator.HapticFeedbackVibrationProvider getHapticVibrationProvider() {
        synchronized (this.mLock) {
            try {
                if (this.mHapticFeedbackVibrationProvider != null) {
                    return this.mHapticFeedbackVibrationProvider;
                }
                android.os.VibratorInfo combinedVibratorInfo = getCombinedVibratorInfo();
                if (combinedVibratorInfo == null) {
                    return null;
                }
                com.android.server.vibrator.HapticFeedbackVibrationProvider createHapticFeedbackVibrationProvider = this.mInjector.createHapticFeedbackVibrationProvider(this.mContext.getResources(), combinedVibratorInfo);
                this.mHapticFeedbackVibrationProvider = createHapticFeedbackVibrationProvider;
                return createHapticFeedbackVibrationProvider;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.VibratorInfo getCombinedVibratorInfo() {
        synchronized (this.mLock) {
            try {
                if (this.mCombinedVibratorInfo != null) {
                    return this.mCombinedVibratorInfo;
                }
                if (this.mVibratorIds.length == 0) {
                    android.os.VibratorInfo vibratorInfo = android.os.VibratorInfo.EMPTY_VIBRATOR_INFO;
                    this.mCombinedVibratorInfo = vibratorInfo;
                    return vibratorInfo;
                }
                android.os.VibratorInfo[] vibratorInfoArr = new android.os.VibratorInfo[this.mVibratorIds.length];
                for (int i = 0; i < this.mVibratorIds.length; i++) {
                    android.os.VibratorInfo vibratorInfo2 = getVibratorInfo(this.mVibratorIds[i]);
                    if (vibratorInfo2 == null) {
                        return null;
                    }
                    vibratorInfoArr[i] = vibratorInfo2;
                }
                android.os.VibratorInfo create = android.os.vibrator.VibratorInfoFactory.create(-1, vibratorInfoArr);
                this.mCombinedVibratorInfo = create;
                return create;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class ExternalVibratorService extends android.os.IExternalVibratorService.Stub {
        private static final android.os.ExternalVibrationScale SCALE_MUTE = new android.os.ExternalVibrationScale();

        ExternalVibratorService() {
        }

        static {
            SCALE_MUTE.scaleLevel = -100;
        }

        public android.os.ExternalVibrationScale onExternalVibrationStart(android.os.ExternalVibration externalVibration) {
            boolean z;
            boolean z2;
            if (!hasExternalControlCapability()) {
                return SCALE_MUTE;
            }
            if (android.app.ActivityManager.checkComponentPermission("android.permission.VIBRATE", externalVibration.getUid(), -1, true) != 0) {
                android.util.Slog.w(com.android.server.vibrator.VibratorManagerService.TAG, "pkg=" + externalVibration.getPackage() + ", uid=" + externalVibration.getUid() + " tried to play externally controlled vibration without VIBRATE permission, ignoring.");
                return SCALE_MUTE;
            }
            com.android.server.vibrator.VibratorManagerService.ExternalVibrationHolder externalVibrationHolder = new com.android.server.vibrator.VibratorManagerService.ExternalVibrationHolder(externalVibration);
            android.os.VibrationAttributes fixupVibrationAttributes = com.android.server.vibrator.VibratorManagerService.this.fixupVibrationAttributes(externalVibration.getVibrationAttributes(), null);
            if (fixupVibrationAttributes.isFlagSet(4)) {
                com.android.server.vibrator.VibratorManagerService.this.mVibrationSettings.update();
            }
            synchronized (com.android.server.vibrator.VibratorManagerService.this.mLock) {
                try {
                    com.android.server.vibrator.Vibration.EndInfo shouldIgnoreVibrationLocked = com.android.server.vibrator.VibratorManagerService.this.shouldIgnoreVibrationLocked(externalVibrationHolder.callerInfo);
                    if (shouldIgnoreVibrationLocked == null && com.android.server.vibrator.VibratorManagerService.this.mCurrentExternalVibration != null && com.android.server.vibrator.VibratorManagerService.this.mCurrentExternalVibration.isHoldingSameVibration(externalVibration)) {
                        return com.android.server.vibrator.VibratorManagerService.this.mCurrentExternalVibration.scale;
                    }
                    if (shouldIgnoreVibrationLocked == null) {
                        shouldIgnoreVibrationLocked = com.android.server.vibrator.VibratorManagerService.this.shouldIgnoreVibrationForOngoingLocked(externalVibrationHolder);
                    }
                    if (shouldIgnoreVibrationLocked != null) {
                        externalVibrationHolder.scale = SCALE_MUTE;
                        com.android.server.vibrator.VibratorManagerService.this.endVibrationAndWriteStatsLocked(externalVibrationHolder, shouldIgnoreVibrationLocked);
                        return externalVibrationHolder.scale;
                    }
                    if (com.android.server.vibrator.VibratorManagerService.this.mCurrentExternalVibration == null) {
                        if (com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration == null) {
                            z2 = false;
                            z = false;
                        } else {
                            externalVibrationHolder.stats.reportInterruptedAnotherVibration(com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration.getVibration().callerInfo);
                            com.android.server.vibrator.VibratorManagerService.this.clearNextVibrationLocked(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_FOR_EXTERNAL, externalVibrationHolder.callerInfo));
                            com.android.server.vibrator.VibratorManagerService.this.mCurrentVibration.notifyCancelled(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_SUPERSEDED, externalVibrationHolder.callerInfo), true);
                            z2 = true;
                            z = false;
                        }
                    } else {
                        com.android.server.vibrator.VibratorManagerService.this.mCurrentExternalVibration.mute();
                        externalVibrationHolder.stats.reportInterruptedAnotherVibration(com.android.server.vibrator.VibratorManagerService.this.mCurrentExternalVibration.callerInfo);
                        com.android.server.vibrator.VibratorManagerService.this.endExternalVibrateLocked(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.CANCELLED_SUPERSEDED, externalVibrationHolder.callerInfo), true);
                        z = true;
                        z2 = false;
                    }
                    com.android.server.vibrator.VibratorManagerService.this.mCurrentExternalVibration = externalVibrationHolder;
                    externalVibrationHolder.linkToDeath();
                    externalVibrationHolder.scale.scaleLevel = com.android.server.vibrator.VibratorManagerService.this.mVibrationScaler.getScaleLevel(fixupVibrationAttributes.getUsage());
                    externalVibrationHolder.scale.adaptiveHapticsScale = com.android.server.vibrator.VibratorManagerService.this.mVibrationScaler.getAdaptiveHapticsScale(fixupVibrationAttributes.getUsage());
                    if (z2 && !com.android.server.vibrator.VibratorManagerService.this.mVibrationThread.waitForThreadIdle(5000L)) {
                        android.util.Slog.e(com.android.server.vibrator.VibratorManagerService.TAG, "Timed out waiting for vibration to cancel");
                        synchronized (com.android.server.vibrator.VibratorManagerService.this.mLock) {
                            com.android.server.vibrator.VibratorManagerService.this.endExternalVibrateLocked(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.IGNORED_ERROR_CANCELLING), false);
                        }
                        return SCALE_MUTE;
                    }
                    if (!z) {
                        com.android.server.vibrator.VibratorManagerService.this.setExternalControl(true, externalVibrationHolder.stats);
                    }
                    externalVibrationHolder.stats.reportStarted();
                    return externalVibrationHolder.scale;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onExternalVibrationStop(android.os.ExternalVibration externalVibration) {
            synchronized (com.android.server.vibrator.VibratorManagerService.this.mLock) {
                try {
                    if (com.android.server.vibrator.VibratorManagerService.this.mCurrentExternalVibration != null && com.android.server.vibrator.VibratorManagerService.this.mCurrentExternalVibration.isHoldingSameVibration(externalVibration)) {
                        com.android.server.vibrator.VibratorManagerService.this.endExternalVibrateLocked(new com.android.server.vibrator.Vibration.EndInfo(com.android.server.vibrator.Vibration.Status.FINISHED), false);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private boolean hasExternalControlCapability() {
            for (int i = 0; i < com.android.server.vibrator.VibratorManagerService.this.mVibrators.size(); i++) {
                if (((com.android.server.vibrator.VibratorController) com.android.server.vibrator.VibratorManagerService.this.mVibrators.valueAt(i)).hasCapability(8L)) {
                    return true;
                }
            }
            return false;
        }
    }

    private final class VibratorManagerShellCommand extends android.os.ShellCommand {
        public static final java.lang.String SHELL_PACKAGE_NAME = "com.android.shell";
        private final android.os.IBinder mShellCallbacksToken;

        private final class CommonOptions {
            public boolean background;
            public java.lang.String description;
            public boolean force;

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            CommonOptions() {
                char c;
                this.force = false;
                this.description = com.android.server.notification.NotificationShellCmd.CHANNEL_NAME;
                this.background = false;
                while (true) {
                    java.lang.String peekNextArg = com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.this.peekNextArg();
                    if (peekNextArg != null) {
                        switch (peekNextArg.hashCode()) {
                            case 1461:
                                if (peekNextArg.equals("-B")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1495:
                                if (peekNextArg.equals("-d")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1497:
                                if (peekNextArg.equals("-f")) {
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
                                com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.this.getNextArgRequired();
                                this.force = true;
                                break;
                            case 1:
                                com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.this.getNextArgRequired();
                                this.background = true;
                                break;
                            case 2:
                                com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.this.getNextArgRequired();
                                this.description = com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.this.getNextArgRequired();
                                break;
                            default:
                                return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        private VibratorManagerShellCommand(android.os.IBinder iBinder) {
            this.mShellCallbacksToken = iBinder;
        }

        public int onCommand(java.lang.String str) {
            android.os.Trace.traceBegin(8388608L, "onCommand " + str);
            try {
                return "list".equals(str) ? runListVibrators() : "synced".equals(str) ? runMono() : "combined".equals(str) ? runStereo() : "sequential".equals(str) ? runSequential() : "xml".equals(str) ? runXml() : "cancel".equals(str) ? runCancel() : "feedback".equals(str) ? runHapticFeedback() : handleDefaultCommands(str);
            } finally {
                android.os.Trace.traceEnd(8388608L);
            }
        }

        private int runListVibrators() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                if (com.android.server.vibrator.VibratorManagerService.this.mVibratorIds.length == 0) {
                    outPrintWriter.println("No vibrator found");
                } else {
                    for (int i : com.android.server.vibrator.VibratorManagerService.this.mVibratorIds) {
                        outPrintWriter.println(i);
                    }
                }
                outPrintWriter.println("");
                outPrintWriter.close();
                return 0;
            } catch (java.lang.Throwable th) {
                if (outPrintWriter != null) {
                    try {
                        outPrintWriter.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        private void runVibrate(com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions commonOptions, android.os.CombinedVibration combinedVibration) {
            android.os.VibrationAttributes createVibrationAttributes = createVibrationAttributes(commonOptions);
            android.os.IVibratorManagerService.Stub stub = commonOptions.background ? com.android.server.vibrator.VibratorManagerService.this : this.mShellCallbacksToken;
            int callingUid = android.os.Binder.getCallingUid();
            maybeWaitOnVibration(com.android.server.vibrator.VibratorManagerService.this.vibrateWithPermissionCheck(callingUid, 0, android.app.AppOpsManager.resolvePackageName(callingUid, SHELL_PACKAGE_NAME), combinedVibration, createVibrationAttributes, commonOptions.description, stub), commonOptions);
        }

        private int runMono() {
            runVibrate(new com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions(), android.os.CombinedVibration.createParallel(nextEffect()));
            return 0;
        }

        private int runStereo() {
            com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions commonOptions = new com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions();
            android.os.CombinedVibration.ParallelCombination startParallel = android.os.CombinedVibration.startParallel();
            while ("-v".equals(getNextOption())) {
                startParallel.addVibrator(java.lang.Integer.parseInt(getNextArgRequired()), nextEffect());
            }
            runVibrate(commonOptions, startParallel.combine());
            return 0;
        }

        private int runSequential() {
            com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions commonOptions = new com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions();
            android.os.CombinedVibration.SequentialCombination startSequential = android.os.CombinedVibration.startSequential();
            while ("-v".equals(getNextOption())) {
                startSequential.addNext(java.lang.Integer.parseInt(getNextArgRequired()), nextEffect());
            }
            runVibrate(commonOptions, startSequential.combine());
            return 0;
        }

        private int runXml() {
            runVibrate(new com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions(), parseXml(getNextArgRequired()));
            return 0;
        }

        private int runCancel() {
            com.android.server.vibrator.VibratorManagerService.this.cancelVibrate(-1, com.android.server.vibrator.VibratorManagerService.this);
            return 0;
        }

        private int runHapticFeedback() {
            com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions commonOptions = new com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions();
            maybeWaitOnVibration(com.android.server.vibrator.VibratorManagerService.this.performHapticFeedbackInternal(android.os.Binder.getCallingUid(), 0, SHELL_PACKAGE_NAME, java.lang.Integer.parseInt(getNextArgRequired()), commonOptions.force, commonOptions.description, commonOptions.background ? com.android.server.vibrator.VibratorManagerService.this : this.mShellCallbacksToken, false), commonOptions);
            return 0;
        }

        private android.os.VibrationEffect nextEffect() {
            android.os.VibrationEffect.Composition startComposition = android.os.VibrationEffect.startComposition();
            while (true) {
                java.lang.String peekNextArg = peekNextArg();
                if (peekNextArg != null) {
                    if ("oneshot".equals(peekNextArg)) {
                        addOneShotToComposition(startComposition);
                    } else if ("waveform".equals(peekNextArg)) {
                        addWaveformToComposition(startComposition);
                    } else if ("prebaked".equals(peekNextArg)) {
                        addPrebakedToComposition(startComposition);
                    } else {
                        if (!"primitives".equals(peekNextArg)) {
                            break;
                        }
                        addPrimitivesToComposition(startComposition);
                    }
                } else {
                    break;
                }
            }
            return startComposition.compose();
        }

        private void addOneShotToComposition(android.os.VibrationEffect.Composition composition) {
            getNextArgRequired();
            boolean z = false;
            int i = 0;
            while (true) {
                java.lang.String nextOption = getNextOption();
                if (nextOption == null) {
                    break;
                }
                if ("-a".equals(nextOption)) {
                    z = true;
                } else if ("-w".equals(nextOption)) {
                    i = java.lang.Integer.parseInt(getNextArgRequired());
                }
            }
            long parseLong = java.lang.Long.parseLong(getNextArgRequired());
            int parseInt = z ? java.lang.Integer.parseInt(getNextArgRequired()) : -1;
            composition.addOffDuration(java.time.Duration.ofMillis(i));
            composition.addEffect(android.os.VibrationEffect.createOneShot(parseLong, parseInt));
        }

        private void addWaveformToComposition(android.os.VibrationEffect.Composition composition) {
            java.time.Duration duration;
            java.time.Duration ofMillis;
            getNextArgRequired();
            int i = -1;
            boolean z = false;
            boolean z2 = false;
            int i2 = 0;
            boolean z3 = false;
            while (true) {
                java.lang.String nextOption = getNextOption();
                if (nextOption == null) {
                    break;
                }
                if ("-a".equals(nextOption)) {
                    z = true;
                } else if ("-r".equals(nextOption)) {
                    i = java.lang.Integer.parseInt(getNextArgRequired());
                } else if ("-w".equals(nextOption)) {
                    i2 = java.lang.Integer.parseInt(getNextArgRequired());
                } else if ("-f".equals(nextOption)) {
                    z2 = true;
                } else if ("-c".equals(nextOption)) {
                    z3 = true;
                }
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            java.util.ArrayList arrayList3 = new java.util.ArrayList();
            float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            while (true) {
                java.lang.String peekNextArg = peekNextArg();
                if (peekNextArg == null) {
                    break;
                }
                try {
                    arrayList.add(java.lang.Integer.valueOf(java.lang.Integer.parseInt(peekNextArg)));
                    getNextArgRequired();
                    if (z) {
                        arrayList2.add(java.lang.Float.valueOf(java.lang.Float.parseFloat(getNextArgRequired()) / 255.0f));
                    } else {
                        arrayList2.add(java.lang.Float.valueOf(f));
                        f = 1.0f - f;
                    }
                    if (z2) {
                        arrayList3.add(java.lang.Float.valueOf(java.lang.Float.parseFloat(getNextArgRequired())));
                    }
                } catch (java.lang.NumberFormatException e) {
                }
            }
            composition.addOffDuration(java.time.Duration.ofMillis(i2));
            android.os.VibrationEffect.WaveformBuilder startWaveform = android.os.VibrationEffect.startWaveform();
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                if (z3) {
                    duration = java.time.Duration.ofMillis(((java.lang.Integer) arrayList.get(i3)).intValue());
                } else {
                    duration = java.time.Duration.ZERO;
                }
                if (z3) {
                    ofMillis = java.time.Duration.ZERO;
                } else {
                    ofMillis = java.time.Duration.ofMillis(((java.lang.Integer) arrayList.get(i3)).intValue());
                }
                if (z2) {
                    startWaveform.addTransition(duration, android.os.VibrationEffect.VibrationParameter.targetAmplitude(((java.lang.Float) arrayList2.get(i3)).floatValue()), android.os.VibrationEffect.VibrationParameter.targetFrequency(((java.lang.Float) arrayList3.get(i3)).floatValue()));
                } else {
                    startWaveform.addTransition(duration, android.os.VibrationEffect.VibrationParameter.targetAmplitude(((java.lang.Float) arrayList2.get(i3)).floatValue()));
                }
                if (!ofMillis.isZero()) {
                    startWaveform.addSustain(ofMillis);
                }
                if (i3 > 0 && i3 == i) {
                    composition.addEffect(startWaveform.build());
                    if (z2) {
                        startWaveform = android.os.VibrationEffect.startWaveform(android.os.VibrationEffect.VibrationParameter.targetAmplitude(((java.lang.Float) arrayList2.get(i3)).floatValue()), android.os.VibrationEffect.VibrationParameter.targetFrequency(((java.lang.Float) arrayList3.get(i3)).floatValue()));
                    } else {
                        startWaveform = android.os.VibrationEffect.startWaveform(android.os.VibrationEffect.VibrationParameter.targetAmplitude(((java.lang.Float) arrayList2.get(i3)).floatValue()));
                    }
                }
            }
            if (i < 0) {
                composition.addEffect(startWaveform.build());
            } else {
                composition.repeatEffectIndefinitely(startWaveform.build());
            }
        }

        private void addPrebakedToComposition(android.os.VibrationEffect.Composition composition) {
            getNextArgRequired();
            int i = 0;
            boolean z = false;
            while (true) {
                java.lang.String nextOption = getNextOption();
                if (nextOption != null) {
                    if ("-b".equals(nextOption)) {
                        z = true;
                    } else if ("-w".equals(nextOption)) {
                        i = java.lang.Integer.parseInt(getNextArgRequired());
                    }
                } else {
                    int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                    composition.addOffDuration(java.time.Duration.ofMillis(i));
                    composition.addEffect(android.os.VibrationEffect.get(parseInt, z));
                    return;
                }
            }
        }

        private void addPrimitivesToComposition(android.os.VibrationEffect.Composition composition) {
            int i;
            getNextArgRequired();
            while (true) {
                java.lang.String peekNextArg = peekNextArg();
                if (peekNextArg != null) {
                    if (!"-w".equals(peekNextArg)) {
                        i = 0;
                    } else {
                        getNextArgRequired();
                        i = java.lang.Integer.parseInt(getNextArgRequired());
                        peekNextArg = peekNextArg();
                    }
                    try {
                        composition.addPrimitive(java.lang.Integer.parseInt(peekNextArg), 1.0f, i);
                        getNextArgRequired();
                    } catch (java.lang.NullPointerException | java.lang.NumberFormatException e) {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        private android.os.VibrationAttributes createVibrationAttributes(com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions commonOptions) {
            return new android.os.VibrationAttributes.Builder().setFlags(commonOptions.force ? 19 : 0).setUsage(65).build();
        }

        private android.os.CombinedVibration parseXml(java.lang.String str) {
            try {
                android.os.vibrator.persistence.ParsedVibration parseDocument = android.os.vibrator.persistence.VibrationXmlParser.parseDocument(new java.io.StringReader(str));
                if (parseDocument == null) {
                    throw new java.lang.IllegalArgumentException("Error parsing vibration XML " + str);
                }
                android.os.VibratorInfo combinedVibratorInfo = com.android.server.vibrator.VibratorManagerService.this.getCombinedVibratorInfo();
                if (combinedVibratorInfo == null) {
                    throw new java.lang.IllegalStateException("No combined vibrator info to parse vibration XML " + str);
                }
                android.os.VibrationEffect resolve = parseDocument.resolve(combinedVibratorInfo);
                if (resolve == null) {
                    throw new java.lang.IllegalArgumentException("Parsed vibration cannot be resolved for vibration XML " + str);
                }
                return android.os.CombinedVibration.createParallel(resolve);
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException("Error parsing vibration XML " + str, e);
            }
        }

        private void maybeWaitOnVibration(com.android.server.vibrator.HalVibration halVibration, com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.CommonOptions commonOptions) {
            if (halVibration != null && !commonOptions.background) {
                try {
                    halVibration.waitForEnd();
                } catch (java.lang.InterruptedException e) {
                }
            }
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                outPrintWriter.println("Vibrator Manager commands:");
                outPrintWriter.println("  help");
                outPrintWriter.println("    Prints this help text.");
                outPrintWriter.println("");
                outPrintWriter.println("  list");
                outPrintWriter.println("    Prints the id of device vibrators. This does not include any ");
                outPrintWriter.println("    connected input device.");
                outPrintWriter.println("  synced [options] <effect>...");
                outPrintWriter.println("    Vibrates effect on all vibrators in sync.");
                outPrintWriter.println("  combined [options] (-v <vibrator-id> <effect>...)...");
                outPrintWriter.println("    Vibrates different effects on each vibrator in sync.");
                outPrintWriter.println("  sequential [options] (-v <vibrator-id> <effect>...)...");
                outPrintWriter.println("    Vibrates different effects on each vibrator in sequence.");
                outPrintWriter.println("  xml [options] <xml>");
                outPrintWriter.println("    Vibrates using combined vibration described in given XML string");
                outPrintWriter.println("    on all vibrators in sync. The XML could be:");
                outPrintWriter.println("        XML containing a single effect, or");
                outPrintWriter.println("        A vibration select XML containing multiple effects.");
                outPrintWriter.println("    Vibrates using combined vibration described in given XML string.");
                outPrintWriter.println("    XML containing a single effect it runs on all vibrators in sync.");
                outPrintWriter.println("  cancel");
                outPrintWriter.println("    Cancels any active vibration");
                outPrintWriter.println("  feedback [-f] [-d <description>] <constant>");
                outPrintWriter.println("    Performs a haptic feedback with the given constant.");
                outPrintWriter.println("    The force (-f) option enables the `always` configuration, which");
                outPrintWriter.println("    plays the haptic irrespective of the vibration intensity settings");
                outPrintWriter.println("");
                outPrintWriter.println("Effect commands:");
                outPrintWriter.println("  oneshot [-w delay] [-a] <duration> [<amplitude>]");
                outPrintWriter.println("    Vibrates for duration milliseconds; ignored when device is on ");
                outPrintWriter.println("    DND (Do Not Disturb) mode; touch feedback strength user setting ");
                outPrintWriter.println("    will be used to scale amplitude.");
                outPrintWriter.println("    If -w is provided, the effect will be played after the specified");
                outPrintWriter.println("    wait time in milliseconds.");
                outPrintWriter.println("    If -a is provided, the command accepts a second argument for ");
                outPrintWriter.println("    amplitude, in a scale of 1-255.");
                outPrintWriter.print("  waveform [-w delay] [-r index] [-a] [-f] [-c] ");
                outPrintWriter.println("(<duration> [<amplitude>] [<frequency>])...");
                outPrintWriter.println("    Vibrates for durations and amplitudes in list; ignored when ");
                outPrintWriter.println("    device is on DND (Do Not Disturb) mode; touch feedback strength ");
                outPrintWriter.println("    user setting will be used to scale amplitude.");
                outPrintWriter.println("    If -w is provided, the effect will be played after the specified");
                outPrintWriter.println("    wait time in milliseconds.");
                outPrintWriter.println("    If -r is provided, the waveform loops back to the specified");
                outPrintWriter.println("    index (e.g. 0 loops from the beginning)");
                outPrintWriter.println("    If -a is provided, the command expects amplitude to follow each");
                outPrintWriter.println("    duration; otherwise, it accepts durations only and alternates");
                outPrintWriter.println("    off/on");
                outPrintWriter.println("    If -f is provided, the command expects frequency to follow each");
                outPrintWriter.println("    amplitude or duration; otherwise, it uses resonant frequency");
                outPrintWriter.println("    If -c is provided, the waveform is continuous and will ramp");
                outPrintWriter.println("    between values; otherwise each entry is a fixed step.");
                outPrintWriter.println("    Duration is in milliseconds; amplitude is a scale of 1-255;");
                outPrintWriter.println("    frequency is an absolute value in hertz;");
                outPrintWriter.println("  prebaked [-w delay] [-b] <effect-id>");
                outPrintWriter.println("    Vibrates with prebaked effect; ignored when device is on DND ");
                outPrintWriter.println("    (Do Not Disturb) mode; touch feedback strength user setting ");
                outPrintWriter.println("    will be used to scale amplitude.");
                outPrintWriter.println("    If -w is provided, the effect will be played after the specified");
                outPrintWriter.println("    wait time in milliseconds.");
                outPrintWriter.println("    If -b is provided, the prebaked fallback effect will be played if");
                outPrintWriter.println("    the device doesn't support the given effect-id.");
                outPrintWriter.println("  primitives ([-w delay] <primitive-id>)...");
                outPrintWriter.println("    Vibrates with a composed effect; ignored when device is on DND ");
                outPrintWriter.println("    (Do Not Disturb) mode; touch feedback strength user setting ");
                outPrintWriter.println("    will be used to scale primitive intensities.");
                outPrintWriter.println("    If -w is provided, the next primitive will be played after the ");
                outPrintWriter.println("    specified wait time in milliseconds.");
                outPrintWriter.println("");
                outPrintWriter.println("Common Options:");
                outPrintWriter.println("  -f");
                outPrintWriter.println("    Force. Ignore Do Not Disturb setting.");
                outPrintWriter.println("  -B");
                outPrintWriter.println("    Run in the background; without this option the shell cmd will");
                outPrintWriter.println("    block until the vibration has completed.");
                outPrintWriter.println("  -d <description>");
                outPrintWriter.println("    Add description to the vibration.");
                outPrintWriter.println("");
                outPrintWriter.close();
            } catch (java.lang.Throwable th) {
                if (outPrintWriter != null) {
                    try {
                        outPrintWriter.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }
}
