package com.android.server.dreams;

/* loaded from: classes.dex */
public final class DreamManagerService extends com.android.server.SystemService {
    private static final boolean DEBUG = false;
    private static final java.lang.String DOZE_WAKE_LOCK_TAG = "dream:doze";
    private static final int DREAM_DISABLED = 0;
    private static final int DREAM_ON_CHARGE = 2;
    private static final int DREAM_ON_DOCK = 1;
    private static final int DREAM_ON_DOCK_OR_CHARGE = 3;
    private static final java.lang.String DREAM_WAKE_LOCK_TAG = "dream:dream";
    private static final java.lang.String TAG = "DreamManagerService";
    private final com.android.server.wm.ActivityInterceptorCallback mActivityInterceptorCallback;
    private final android.content.ComponentName mAmbientDisplayComponent;
    private final com.android.server.wm.ActivityTaskManagerInternal mAtmInternal;
    private final android.content.BroadcastReceiver mChargingReceiver;
    private final android.content.Context mContext;
    private final com.android.server.dreams.DreamController mController;
    private final com.android.server.dreams.DreamController.Listener mControllerListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.dreams.DreamManagerService.DreamRecord mCurrentDream;
    private final boolean mDismissDreamOnActivityStart;
    private final android.content.BroadcastReceiver mDockStateReceiver;
    private final android.hardware.display.AmbientDisplayConfiguration mDozeConfig;
    private final android.database.ContentObserver mDozeEnabledObserver;
    private final android.os.PowerManager.WakeLock mDozeWakeLock;
    private final java.util.concurrent.CopyOnWriteArrayList<android.service.dreams.DreamManagerInternal.DreamManagerStateListener> mDreamManagerStateListeners;
    private android.content.ComponentName mDreamOverlayServiceName;
    private final com.android.server.dreams.DreamUiEventLogger mDreamUiEventLogger;
    private final boolean mDreamsActivatedOnChargeByDefault;
    private final boolean mDreamsActivatedOnDockByDefault;
    private final boolean mDreamsDisabledByAmbientModeSuppressionConfig;
    private final boolean mDreamsEnabledByDefaultConfig;
    private boolean mDreamsEnabledSetting;
    private final boolean mDreamsOnlyEnabledForDockUser;
    private boolean mForceAmbientDisplayEnabled;
    private final android.os.Handler mHandler;
    private boolean mIsCharging;
    private boolean mIsDocked;
    private final boolean mKeepDreamingWhenUnpluggingDefault;
    private final java.lang.Object mLock;
    private final android.content.pm.PackageManagerInternal mPmInternal;
    private final android.os.PowerManager mPowerManager;
    private final android.os.PowerManagerInternal mPowerManagerInternal;
    private com.android.server.dreams.DreamManagerService.SettingsObserver mSettingsObserver;
    private android.content.ComponentName mSystemDreamComponent;
    private final java.lang.Runnable mSystemPropertiesChanged;
    private final com.android.internal.logging.UiEventLogger mUiEventLogger;
    private final android.os.UserManager mUserManager;
    private int mWhenToDream;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WhenToDream {
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            com.android.server.dreams.DreamManagerService.this.updateWhenToDreamSettings();
        }
    }

    public DreamManagerService(android.content.Context context) {
        this(context, new com.android.server.dreams.DreamManagerService.DreamHandler(com.android.server.FgThread.get().getLooper()));
    }

    @com.android.internal.annotations.VisibleForTesting
    DreamManagerService(android.content.Context context, android.os.Handler handler) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mDreamManagerStateListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mActivityInterceptorCallback = new com.android.server.wm.ActivityInterceptorCallback() { // from class: com.android.server.dreams.DreamManagerService.1
            @Override // com.android.server.wm.ActivityInterceptorCallback
            @android.annotation.Nullable
            public com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptResult onInterceptActivityLaunch(@android.annotation.NonNull com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
                return null;
            }

            @Override // com.android.server.wm.ActivityInterceptorCallback
            public void onActivityLaunched(android.app.TaskInfo taskInfo, android.content.pm.ActivityInfo activityInfo, com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
                int activityType = taskInfo.getActivityType();
                boolean z = true;
                boolean z2 = activityType == 2 || activityType == 5 || activityType == 4;
                synchronized (com.android.server.dreams.DreamManagerService.this.mLock) {
                    try {
                        if (com.android.server.dreams.DreamManagerService.this.mCurrentDream == null || com.android.server.dreams.DreamManagerService.this.mCurrentDream.isWaking || com.android.server.dreams.DreamManagerService.this.mCurrentDream.isDozing || z2) {
                            z = false;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (z) {
                    com.android.server.dreams.DreamManagerService.this.requestAwakenInternal("stopping dream due to activity start: " + activityInfo.name);
                }
            }
        };
        this.mChargingReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.dreams.DreamManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.dreams.DreamManagerService.this.mIsCharging = "android.os.action.CHARGING".equals(intent.getAction());
            }
        };
        this.mDockStateReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.dreams.DreamManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                    com.android.server.dreams.DreamManagerService.this.mIsDocked = intExtra != 0;
                }
            }
        };
        this.mControllerListener = new com.android.server.dreams.DreamController.Listener() { // from class: com.android.server.dreams.DreamManagerService.4
            @Override // com.android.server.dreams.DreamController.Listener
            public void onDreamStarted(android.os.Binder binder) {
                com.android.server.dreams.DreamManagerService.this.reportDreamingStarted();
            }

            @Override // com.android.server.dreams.DreamController.Listener
            public void onDreamStopped(android.os.Binder binder) {
                synchronized (com.android.server.dreams.DreamManagerService.this.mLock) {
                    try {
                        if (com.android.server.dreams.DreamManagerService.this.mCurrentDream != null && com.android.server.dreams.DreamManagerService.this.mCurrentDream.token == binder) {
                            com.android.server.dreams.DreamManagerService.this.cleanupDreamLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                com.android.server.dreams.DreamManagerService.this.reportDreamingStopped();
            }
        };
        this.mDozeEnabledObserver = new android.database.ContentObserver(null) { // from class: com.android.server.dreams.DreamManagerService.5
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.dreams.DreamManagerService.this.writePulseGestureEnabled();
            }
        };
        this.mSystemPropertiesChanged = new java.lang.Runnable() { // from class: com.android.server.dreams.DreamManagerService.6
            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.server.dreams.DreamManagerService.this.mLock) {
                    try {
                        if (com.android.server.dreams.DreamManagerService.this.mCurrentDream != null && com.android.server.dreams.DreamManagerService.this.mCurrentDream.name != null && com.android.server.dreams.DreamManagerService.this.mCurrentDream.canDoze && !com.android.server.dreams.DreamManagerService.this.mCurrentDream.name.equals(com.android.server.dreams.DreamManagerService.this.getDozeComponent())) {
                            com.android.server.dreams.DreamManagerService.this.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), "android.server.dreams:SYSPROP");
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mContext = context;
        this.mHandler = handler;
        this.mController = new com.android.server.dreams.DreamController(context, this.mHandler, this.mControllerListener);
        this.mPowerManager = (android.os.PowerManager) context.getSystemService("power");
        this.mPowerManagerInternal = (android.os.PowerManagerInternal) getLocalService(android.os.PowerManagerInternal.class);
        this.mAtmInternal = (com.android.server.wm.ActivityTaskManagerInternal) getLocalService(com.android.server.wm.ActivityTaskManagerInternal.class);
        this.mPmInternal = (android.content.pm.PackageManagerInternal) getLocalService(android.content.pm.PackageManagerInternal.class);
        this.mUserManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        this.mDozeWakeLock = this.mPowerManager.newWakeLock(64, DOZE_WAKE_LOCK_TAG);
        this.mDozeConfig = new android.hardware.display.AmbientDisplayConfiguration(this.mContext);
        this.mUiEventLogger = new com.android.internal.logging.UiEventLoggerImpl();
        this.mDreamUiEventLogger = new com.android.server.dreams.DreamUiEventLoggerImpl(this.mContext.getResources().getStringArray(android.R.array.config_locationDriverAssistancePackageNames));
        this.mAmbientDisplayComponent = android.content.ComponentName.unflattenFromString(new android.hardware.display.AmbientDisplayConfiguration(this.mContext).ambientDisplayComponent());
        this.mDreamsOnlyEnabledForDockUser = this.mContext.getResources().getBoolean(android.R.bool.config_dreamsEnabledOnBattery);
        this.mDismissDreamOnActivityStart = this.mContext.getResources().getBoolean(android.R.bool.config_disable_all_cb_messages);
        this.mDreamsEnabledByDefaultConfig = this.mContext.getResources().getBoolean(android.R.bool.config_dreamsDisabledByAmbientModeSuppressionConfig);
        this.mDreamsActivatedOnChargeByDefault = this.mContext.getResources().getBoolean(android.R.bool.config_dreamsActivatedOnDockByDefault);
        this.mDreamsActivatedOnDockByDefault = this.mContext.getResources().getBoolean(android.R.bool.config_dozeWakeLockScreenSensorAvailable);
        this.mSettingsObserver = new com.android.server.dreams.DreamManagerService.SettingsObserver(this.mHandler);
        this.mKeepDreamingWhenUnpluggingDefault = this.mContext.getResources().getBoolean(android.R.bool.config_isPreApprovalRequestAvailable);
        this.mDreamsDisabledByAmbientModeSuppressionConfig = this.mContext.getResources().getBoolean(android.R.bool.config_dreamsActivatedOnSleepByDefault);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("dreams", new com.android.server.dreams.DreamManagerService.BinderService());
        publishLocalService(android.service.dreams.DreamManagerInternal.class, new com.android.server.dreams.DreamManagerService.LocalService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 600) {
            if (android.os.Build.IS_DEBUGGABLE) {
                android.os.SystemProperties.addChangeCallback(this.mSystemPropertiesChanged);
            }
            this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("doze_pulse_on_double_tap"), false, this.mDozeEnabledObserver, -1);
            writePulseGestureEnabled();
            if (this.mDismissDreamOnActivityStart) {
                this.mAtmInternal.registerActivityStartInterceptor(4, this.mActivityInterceptorCallback);
            }
            this.mContext.registerReceiver(this.mDockStateReceiver, new android.content.IntentFilter("android.intent.action.DOCK_EVENT"));
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.os.action.CHARGING");
            intentFilter.addAction("android.os.action.DISCHARGING");
            this.mContext.registerReceiver(this.mChargingReceiver, intentFilter);
            this.mSettingsObserver = new com.android.server.dreams.DreamManagerService.SettingsObserver(this.mHandler);
            this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("screensaver_activate_on_sleep"), false, this.mSettingsObserver, -1);
            this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("screensaver_activate_on_dock"), false, this.mSettingsObserver, -1);
            this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("screensaver_enabled"), false, this.mSettingsObserver, -1);
            this.mIsCharging = ((android.os.BatteryManager) this.mContext.getSystemService(android.os.BatteryManager.class)).isCharging();
            updateWhenToDreamSettings();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        updateWhenToDreamSettings();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.dreams.DreamManagerService.this.lambda$onUserSwitching$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserSwitching$0() {
        writePulseGestureEnabled();
        synchronized (this.mLock) {
            stopDreamLocked(false, "user switched");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpInternal(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.println("DREAM MANAGER (dumpsys dreams)");
            printWriter.println();
            printWriter.println("mCurrentDream=" + this.mCurrentDream);
            printWriter.println("mForceAmbientDisplayEnabled=" + this.mForceAmbientDisplayEnabled);
            printWriter.println("mDreamsOnlyEnabledForDockUser=" + this.mDreamsOnlyEnabledForDockUser);
            printWriter.println("mDreamsEnabledSetting=" + this.mDreamsEnabledSetting);
            printWriter.println("mDreamsActivatedOnDockByDefault=" + this.mDreamsActivatedOnDockByDefault);
            printWriter.println("mDreamsActivatedOnChargeByDefault=" + this.mDreamsActivatedOnChargeByDefault);
            printWriter.println("mIsDocked=" + this.mIsDocked);
            printWriter.println("mIsCharging=" + this.mIsCharging);
            printWriter.println("mWhenToDream=" + this.mWhenToDream);
            printWriter.println("mKeepDreamingWhenUnpluggingDefault=" + this.mKeepDreamingWhenUnpluggingDefault);
            printWriter.println("getDozeComponent()=" + getDozeComponent());
            printWriter.println("mDreamOverlayServiceName=" + android.content.ComponentName.flattenToShortString(this.mDreamOverlayServiceName));
            printWriter.println();
            com.android.internal.util.DumpUtils.dumpAsync(this.mHandler, new com.android.internal.util.DumpUtils.Dump() { // from class: com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda6
                public final void dump(java.io.PrintWriter printWriter2, java.lang.String str) {
                    com.android.server.dreams.DreamManagerService.this.lambda$dumpInternal$1(printWriter2, str);
                }
            }, printWriter, "", 200L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpInternal$1(java.io.PrintWriter printWriter, java.lang.String str) {
        this.mController.dump(printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWhenToDreamSettings() {
        synchronized (this.mLock) {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            boolean z = true;
            this.mWhenToDream = (android.provider.Settings.Secure.getIntForUser(contentResolver, "screensaver_activate_on_sleep", this.mDreamsActivatedOnChargeByDefault ? 1 : 0, -2) != 0 ? 2 : 0) + (android.provider.Settings.Secure.getIntForUser(contentResolver, "screensaver_activate_on_dock", this.mDreamsActivatedOnDockByDefault ? 1 : 0, -2) != 0 ? 1 : 0);
            if (android.provider.Settings.Secure.getIntForUser(contentResolver, "screensaver_enabled", this.mDreamsEnabledByDefaultConfig ? 1 : 0, -2) == 0) {
                z = false;
            }
            this.mDreamsEnabledSetting = z;
        }
    }

    private void reportKeepDreamingWhenUnpluggingChanged(final boolean z) {
        notifyDreamStateListeners(new java.util.function.Consumer() { // from class: com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.service.dreams.DreamManagerInternal.DreamManagerStateListener) obj).onKeepDreamingWhenUnpluggingChanged(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportDreamingStarted() {
        notifyDreamStateListeners(new java.util.function.Consumer() { // from class: com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.service.dreams.DreamManagerInternal.DreamManagerStateListener) obj).onDreamingStarted();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportDreamingStopped() {
        notifyDreamStateListeners(new java.util.function.Consumer() { // from class: com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.service.dreams.DreamManagerInternal.DreamManagerStateListener) obj).onDreamingStopped();
            }
        });
    }

    private void notifyDreamStateListeners(final java.util.function.Consumer<android.service.dreams.DreamManagerInternal.DreamManagerStateListener> consumer) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.dreams.DreamManagerService.this.lambda$notifyDreamStateListeners$5(consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyDreamStateListeners$5(java.util.function.Consumer consumer) {
        java.util.Iterator<android.service.dreams.DreamManagerInternal.DreamManagerStateListener> it = this.mDreamManagerStateListeners.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDreamingInternal() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = (this.mCurrentDream == null || this.mCurrentDream.isPreview || this.mCurrentDream.isWaking) ? false : true;
            } finally {
            }
        }
        return z;
    }

    private boolean isDozingInternal() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mCurrentDream != null && this.mCurrentDream.isDozing;
            } finally {
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDreamingOrInPreviewInternal() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = (this.mCurrentDream == null || this.mCurrentDream.isWaking) ? false : true;
            } finally {
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canStartDreamingInternal(boolean z) {
        synchronized (this.mLock) {
            if (z) {
                try {
                    if (isDreamingInternal()) {
                        return false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (!this.mDreamsEnabledSetting) {
                return false;
            }
            if (!dreamsEnabledForUser(android.app.ActivityManager.getCurrentUser())) {
                return false;
            }
            if (!this.mUserManager.isUserUnlocked()) {
                return false;
            }
            if (this.mDreamsDisabledByAmbientModeSuppressionConfig && this.mPowerManagerInternal.isAmbientDisplaySuppressed()) {
                android.util.Slog.i(TAG, "Can't start dreaming because ambient is suppressed.");
                return false;
            }
            if ((this.mWhenToDream & 2) == 2) {
                return this.mIsCharging;
            }
            if ((this.mWhenToDream & 1) != 1) {
                return false;
            }
            return this.mIsDocked;
        }
    }

    protected void requestStartDreamFromShell() {
        requestDreamInternal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestDreamInternal() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        this.mPowerManager.userActivity(uptimeMillis, true);
        this.mPowerManagerInternal.nap(uptimeMillis, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestAwakenInternal(java.lang.String str) {
        this.mPowerManager.userActivity(android.os.SystemClock.uptimeMillis(), false);
        stopDreamInternal(false, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishSelfInternal(android.os.IBinder iBinder, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentDream != null && this.mCurrentDream.token == iBinder) {
                    stopDreamLocked(z, "finished self");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void testDreamInternal(android.content.ComponentName componentName, int i) {
        synchronized (this.mLock) {
            startDreamLocked(componentName, true, false, i, "test dream");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDreamInternal(boolean z, java.lang.String str) {
        int currentUser = android.app.ActivityManager.getCurrentUser();
        android.content.ComponentName chooseDreamForUser = chooseDreamForUser(z, currentUser);
        if (chooseDreamForUser != null) {
            synchronized (this.mLock) {
                startDreamLocked(chooseDreamForUser, false, z, currentUser, str);
            }
        }
    }

    protected void requestStopDreamFromShell() {
        stopDreamInternal(false, "stopping dream from shell");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopDreamInternal(boolean z, java.lang.String str) {
        synchronized (this.mLock) {
            stopDreamLocked(z, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDozingInternal(android.os.IBinder iBinder, int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentDream != null && this.mCurrentDream.token == iBinder && this.mCurrentDream.canDoze) {
                    this.mCurrentDream.dozeScreenState = i;
                    this.mCurrentDream.dozeScreenBrightness = i2;
                    this.mPowerManagerInternal.setDozeOverrideFromDreamManager(i, i2);
                    if (!this.mCurrentDream.isDozing) {
                        this.mCurrentDream.isDozing = true;
                        this.mDozeWakeLock.acquire();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopDozingInternal(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentDream != null && this.mCurrentDream.token == iBinder && this.mCurrentDream.isDozing) {
                    this.mCurrentDream.isDozing = false;
                    this.mDozeWakeLock.release();
                    this.mPowerManagerInternal.setDozeOverrideFromDreamManager(0, -1);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceAmbientDisplayEnabledInternal(boolean z) {
        synchronized (this.mLock) {
            this.mForceAmbientDisplayEnabled = z;
        }
    }

    private android.content.ComponentName chooseDreamForUser(boolean z, int i) {
        if (z) {
            android.content.ComponentName dozeComponent = getDozeComponent(i);
            if (validateDream(dozeComponent)) {
                return dozeComponent;
            }
            return null;
        }
        if (this.mSystemDreamComponent != null) {
            return this.mSystemDreamComponent;
        }
        android.content.ComponentName[] dreamComponentsForUser = getDreamComponentsForUser(i);
        if (dreamComponentsForUser == null || dreamComponentsForUser.length == 0) {
            return null;
        }
        return dreamComponentsForUser[0];
    }

    private boolean validateDream(android.content.ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        android.content.pm.ServiceInfo serviceInfo = getServiceInfo(componentName);
        if (serviceInfo == null) {
            android.util.Slog.w(TAG, "Dream " + componentName + " does not exist");
            return false;
        }
        if (serviceInfo.applicationInfo.targetSdkVersion >= 21 && !"android.permission.BIND_DREAM_SERVICE".equals(serviceInfo.permission)) {
            android.util.Slog.w(TAG, "Dream " + componentName + " is not available because its manifest is missing the android.permission.BIND_DREAM_SERVICE permission on the dream service declaration.");
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.ComponentName[] getDreamComponentsForUser(int i) {
        android.content.ComponentName defaultDreamComponentForUser;
        if (!dreamsEnabledForUser(i)) {
            return null;
        }
        android.content.ComponentName[] componentsFromString = componentsFromString(android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "screensaver_components", i));
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (componentsFromString != null) {
            for (android.content.ComponentName componentName : componentsFromString) {
                if (validateDream(componentName)) {
                    arrayList.add(componentName);
                }
            }
        }
        if (arrayList.isEmpty() && (defaultDreamComponentForUser = getDefaultDreamComponentForUser(i)) != null) {
            android.util.Slog.w(TAG, "Falling back to default dream " + defaultDreamComponentForUser);
            arrayList.add(defaultDreamComponentForUser);
        }
        return (android.content.ComponentName[]) arrayList.toArray(new android.content.ComponentName[arrayList.size()]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDreamComponentsForUser(int i, android.content.ComponentName[] componentNameArr) {
        android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "screensaver_components", componentsToString(componentNameArr), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSystemDreamComponentInternal(android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                if (java.util.Objects.equals(this.mSystemDreamComponent, componentName)) {
                    return;
                }
                this.mSystemDreamComponent = componentName;
                reportKeepDreamingWhenUnpluggingChanged(shouldKeepDreamingWhenUnplugging());
                if (isDreamingInternal() && !isDozingInternal()) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(this.mSystemDreamComponent == null ? "clear" : "set");
                    sb.append(" system dream component");
                    startDreamInternal(false, sb.toString());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldKeepDreamingWhenUnplugging() {
        return this.mKeepDreamingWhenUnpluggingDefault && this.mSystemDreamComponent == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.ComponentName getDefaultDreamComponentForUser(int i) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "screensaver_default_component", i);
        if (stringForUser == null) {
            return null;
        }
        return android.content.ComponentName.unflattenFromString(stringForUser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.ComponentName getDozeComponent() {
        return getDozeComponent(android.app.ActivityManager.getCurrentUser());
    }

    private android.content.ComponentName getDozeComponent(int i) {
        if (this.mForceAmbientDisplayEnabled || this.mDozeConfig.enabled(i)) {
            return android.content.ComponentName.unflattenFromString(this.mDozeConfig.ambientDisplayComponent());
        }
        return null;
    }

    private boolean dreamsEnabledForUser(int i) {
        if (this.mDreamsOnlyEnabledForDockUser) {
            return i >= 0 && i == ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getMainUserId();
        }
        return true;
    }

    private android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName) {
        if (componentName != null) {
            try {
                return this.mContext.getPackageManager().getServiceInfo(componentName, 268435456);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return null;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startDreamLocked(final android.content.ComponentName componentName, final boolean z, final boolean z2, final int i, final java.lang.String str) {
        if (this.mCurrentDream != null && !this.mCurrentDream.isWaking && java.util.Objects.equals(this.mCurrentDream.name, componentName) && this.mCurrentDream.isPreview == z && this.mCurrentDream.canDoze == z2 && this.mCurrentDream.userId == i) {
            android.util.Slog.i(TAG, "Already in target dream.");
            return;
        }
        android.util.Slog.i(TAG, "Entering dreamland.");
        if (this.mCurrentDream != null && this.mCurrentDream.isDozing) {
            stopDozingInternal(this.mCurrentDream.token);
        }
        this.mCurrentDream = new com.android.server.dreams.DreamManagerService.DreamRecord(componentName, i, z, z2);
        if (!this.mCurrentDream.name.equals(this.mAmbientDisplayComponent)) {
            this.mUiEventLogger.log(com.android.server.dreams.DreamUiEventLogger.DreamUiEventEnum.DREAM_START);
            this.mDreamUiEventLogger.log(com.android.server.dreams.DreamUiEventLogger.DreamUiEventEnum.DREAM_START, this.mCurrentDream.name.flattenToString());
        }
        final android.os.PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, DREAM_WAKE_LOCK_TAG);
        final android.os.Binder binder = this.mCurrentDream.token;
        this.mHandler.post(newWakeLock.wrap(new java.lang.Runnable() { // from class: com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.dreams.DreamManagerService.this.lambda$startDreamLocked$6(componentName, binder, z, z2, i, newWakeLock, str);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDreamLocked$6(android.content.ComponentName componentName, android.os.Binder binder, boolean z, boolean z2, int i, android.os.PowerManager.WakeLock wakeLock, java.lang.String str) {
        this.mAtmInternal.notifyActiveDreamChanged(componentName);
        this.mController.startDream(binder, componentName, z, z2, i, wakeLock, this.mDreamOverlayServiceName, str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void stopDreamLocked(final boolean z, final java.lang.String str) {
        if (this.mCurrentDream != null) {
            if (z) {
                android.util.Slog.i(TAG, "Leaving dreamland.");
                cleanupDreamLocked();
            } else if (!this.mCurrentDream.isWaking) {
                android.util.Slog.i(TAG, "Gently waking up from dream.");
                this.mCurrentDream.isWaking = true;
            } else {
                return;
            }
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.dreams.DreamManagerService.this.lambda$stopDreamLocked$7(z, str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopDreamLocked$7(boolean z, java.lang.String str) {
        this.mController.stopDream(z, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void cleanupDreamLocked() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.dreams.DreamManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.dreams.DreamManagerService.this.lambda$cleanupDreamLocked$8();
            }
        });
        if (this.mCurrentDream == null) {
            return;
        }
        if (!this.mCurrentDream.name.equals(this.mAmbientDisplayComponent)) {
            this.mUiEventLogger.log(com.android.server.dreams.DreamUiEventLogger.DreamUiEventEnum.DREAM_STOP);
            this.mDreamUiEventLogger.log(com.android.server.dreams.DreamUiEventLogger.DreamUiEventEnum.DREAM_STOP, this.mCurrentDream.name.flattenToString());
        }
        if (this.mCurrentDream.isDozing) {
            this.mDozeWakeLock.release();
        }
        this.mCurrentDream = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanupDreamLocked$8() {
        this.mAtmInternal.notifyActiveDreamChanged(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPermission(java.lang.String str) {
        if (this.mContext.checkCallingOrSelfPermission(str) != 0) {
            throw new java.lang.SecurityException("Access denied to process: " + android.os.Binder.getCallingPid() + ", must have permission " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writePulseGestureEnabled() {
        ((com.android.server.input.InputManagerInternal) com.android.server.LocalServices.getService(com.android.server.input.InputManagerInternal.class)).setPulseGestureEnabled(validateDream(getDozeComponent()));
    }

    private static java.lang.String componentsToString(android.content.ComponentName[] componentNameArr) {
        if (componentNameArr == null) {
            return null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (android.content.ComponentName componentName : componentNameArr) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(componentName.flattenToString());
        }
        return sb.toString();
    }

    private static android.content.ComponentName[] componentsFromString(java.lang.String str) {
        if (str == null) {
            return null;
        }
        java.lang.String[] split = str.split(",");
        android.content.ComponentName[] componentNameArr = new android.content.ComponentName[split.length];
        for (int i = 0; i < split.length; i++) {
            componentNameArr[i] = android.content.ComponentName.unflattenFromString(split[i]);
        }
        return componentNameArr;
    }

    private static final class DreamHandler extends android.os.Handler {
        public DreamHandler(android.os.Looper looper) {
            super(looper, null, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class BinderService extends android.service.dreams.IDreamManager.Stub {
        private BinderService() {
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.dreams.DreamManagerService.this.mContext, com.android.server.dreams.DreamManagerService.TAG, printWriter)) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.dreams.DreamManagerService.this.dumpInternal(printWriter);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            new com.android.server.dreams.DreamShellCommand(com.android.server.dreams.DreamManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public android.content.ComponentName[] getDreamComponents() {
            return getDreamComponentsForUser(android.os.UserHandle.getCallingUserId());
        }

        public android.content.ComponentName[] getDreamComponentsForUser(int i) {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.READ_DREAM_STATE");
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, true, "getDreamComponents", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.dreams.DreamManagerService.this.getDreamComponentsForUser(handleIncomingUser);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setDreamComponents(android.content.ComponentName[] componentNameArr) {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.WRITE_DREAM_STATE");
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                setDreamComponentsForUser(callingUserId, componentNameArr);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setDreamComponentsForUser(int i, android.content.ComponentName[] componentNameArr) {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.WRITE_DREAM_STATE");
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, true, "setDreamComponents", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.dreams.DreamManagerService.this.setDreamComponentsForUser(handleIncomingUser, componentNameArr);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setSystemDreamComponent(android.content.ComponentName componentName) {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.WRITE_DREAM_STATE");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.dreams.DreamManagerService.this.setSystemDreamComponentInternal(componentName);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerDreamOverlayService(android.content.ComponentName componentName) {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.WRITE_DREAM_STATE");
            com.android.server.dreams.DreamManagerService.this.mDreamOverlayServiceName = componentName;
        }

        public android.content.ComponentName getDefaultDreamComponentForUser(int i) {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.READ_DREAM_STATE");
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, true, "getDefaultDreamComponent", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.dreams.DreamManagerService.this.getDefaultDreamComponentForUser(handleIncomingUser);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isDreaming() {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.READ_DREAM_STATE");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.dreams.DreamManagerService.this.isDreamingInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isDreamingOrInPreview() {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.READ_DREAM_STATE");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.dreams.DreamManagerService.this.isDreamingOrInPreviewInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dream() {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.WRITE_DREAM_STATE");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.dreams.DreamManagerService.this.requestDreamInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void testDream(int i, android.content.ComponentName componentName) {
            if (componentName == null) {
                throw new java.lang.IllegalArgumentException("dream must not be null");
            }
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.WRITE_DREAM_STATE");
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, true, "testDream", null);
            int currentUser = android.app.ActivityManager.getCurrentUser();
            if (handleIncomingUser != currentUser) {
                android.util.Slog.w(com.android.server.dreams.DreamManagerService.TAG, "Aborted attempt to start a test dream while a different  user is active: userId=" + handleIncomingUser + ", currentUserId=" + currentUser);
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.dreams.DreamManagerService.this.testDreamInternal(componentName, handleIncomingUser);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void awaken() {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.WRITE_DREAM_STATE");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.dreams.DreamManagerService.this.requestAwakenInternal("request awaken");
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void finishSelf(android.os.IBinder iBinder, boolean z) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("token must not be null");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.dreams.DreamManagerService.this.finishSelfInternal(iBinder, z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void startDozing(android.os.IBinder iBinder, int i, int i2) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("token must not be null");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.dreams.DreamManagerService.this.startDozingInternal(iBinder, i, i2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void stopDozing(android.os.IBinder iBinder) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("token must not be null");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.dreams.DreamManagerService.this.stopDozingInternal(iBinder);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void forceAmbientDisplayEnabled(boolean z) {
            com.android.server.dreams.DreamManagerService.this.checkPermission("android.permission.DEVICE_POWER");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.dreams.DreamManagerService.this.forceAmbientDisplayEnabledInternal(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void startDreamActivity(@android.annotation.NonNull final android.content.Intent intent) {
            final int callingUid = android.os.Binder.getCallingUid();
            final int callingPid = android.os.Binder.getCallingPid();
            com.android.server.dreams.DreamManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.dreams.DreamManagerService$BinderService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.dreams.DreamManagerService.BinderService.this.lambda$startDreamActivity$0(intent, callingUid, callingPid);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startDreamActivity$0(android.content.Intent intent, int i, int i2) {
            synchronized (com.android.server.dreams.DreamManagerService.this.mLock) {
                try {
                    if (com.android.server.dreams.DreamManagerService.this.mCurrentDream == null) {
                        android.util.Slog.e(com.android.server.dreams.DreamManagerService.TAG, "Attempt to start DreamActivity, but the device is not dreaming. Aborting without starting the DreamActivity.");
                        return;
                    }
                    android.os.Binder binder = com.android.server.dreams.DreamManagerService.this.mCurrentDream.token;
                    java.lang.String packageName = com.android.server.dreams.DreamManagerService.this.mCurrentDream.name.getPackageName();
                    if (!canLaunchDreamActivity(packageName, intent.getPackage(), i)) {
                        android.util.Slog.e(com.android.server.dreams.DreamManagerService.TAG, "The dream activity can be started only when the device is dreaming and only by the active dream package.");
                        return;
                    }
                    android.app.IAppTask startDreamActivity = com.android.server.dreams.DreamManagerService.this.mAtmInternal.startDreamActivity(intent, i, i2);
                    if (startDreamActivity == null) {
                        android.util.Slog.e(com.android.server.dreams.DreamManagerService.TAG, "Could not start dream activity.");
                        com.android.server.dreams.DreamManagerService.this.stopDreamInternal(true, "DreamActivity not started");
                    } else {
                        com.android.server.dreams.DreamManagerService.this.mController.setDreamAppTask(binder, startDreamActivity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        boolean canLaunchDreamActivity(java.lang.String str, java.lang.String str2, int i) {
            if (str == null || str2 == null) {
                android.util.Slog.e(com.android.server.dreams.DreamManagerService.TAG, "Cannot launch dream activity due to invalid state. dream component= " + str + ", packageName=" + str2);
                return false;
            }
            if (!com.android.server.dreams.DreamManagerService.this.mPmInternal.isSameApp(str2, i, android.os.UserHandle.getUserId(i))) {
                android.util.Slog.e(com.android.server.dreams.DreamManagerService.TAG, "Cannot launch dream activity because package=" + str2 + " does not match callingUid=" + i);
                return false;
            }
            if (str2.equals(str)) {
                return true;
            }
            android.util.Slog.e(com.android.server.dreams.DreamManagerService.TAG, "Dream packageName does not match active dream. Package " + str2 + " does not match " + str);
            return false;
        }
    }

    private final class LocalService extends android.service.dreams.DreamManagerInternal {
        private LocalService() {
        }

        public void startDream(boolean z, java.lang.String str) {
            com.android.server.dreams.DreamManagerService.this.startDreamInternal(z, str);
        }

        public void stopDream(boolean z, java.lang.String str) {
            com.android.server.dreams.DreamManagerService.this.stopDreamInternal(z, str);
        }

        public boolean isDreaming() {
            return com.android.server.dreams.DreamManagerService.this.isDreamingInternal();
        }

        public boolean canStartDreaming(boolean z) {
            return com.android.server.dreams.DreamManagerService.this.canStartDreamingInternal(z);
        }

        public void requestDream() {
            com.android.server.dreams.DreamManagerService.this.requestDreamInternal();
        }

        public void registerDreamManagerStateListener(android.service.dreams.DreamManagerInternal.DreamManagerStateListener dreamManagerStateListener) {
            com.android.server.dreams.DreamManagerService.this.mDreamManagerStateListeners.add(dreamManagerStateListener);
            dreamManagerStateListener.onKeepDreamingWhenUnpluggingChanged(com.android.server.dreams.DreamManagerService.this.shouldKeepDreamingWhenUnplugging());
        }

        public void unregisterDreamManagerStateListener(android.service.dreams.DreamManagerInternal.DreamManagerStateListener dreamManagerStateListener) {
            com.android.server.dreams.DreamManagerService.this.mDreamManagerStateListeners.remove(dreamManagerStateListener);
        }
    }

    private static final class DreamRecord {
        public final boolean canDoze;
        public final boolean isPreview;
        public final android.content.ComponentName name;
        public final int userId;
        public final android.os.Binder token = new android.os.Binder();
        public boolean isDozing = false;
        public boolean isWaking = false;
        public int dozeScreenState = 0;
        public int dozeScreenBrightness = -1;

        DreamRecord(android.content.ComponentName componentName, int i, boolean z, boolean z2) {
            this.name = componentName;
            this.userId = i;
            this.isPreview = z;
            this.canDoze = z2;
        }

        public java.lang.String toString() {
            return "DreamRecord{token=" + this.token + ", name=" + this.name + ", userId=" + this.userId + ", isPreview=" + this.isPreview + ", canDoze=" + this.canDoze + ", isDozing=" + this.isDozing + ", isWaking=" + this.isWaking + ", dozeScreenState=" + this.dozeScreenState + ", dozeScreenBrightness=" + this.dozeScreenBrightness + '}';
        }
    }
}
