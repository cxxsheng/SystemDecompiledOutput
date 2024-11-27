package com.android.server;

/* loaded from: classes.dex */
final class UiModeManagerService extends com.android.server.SystemService {
    private static final boolean ENABLE_LAUNCH_DESK_DOCK_APP = true;
    private static final boolean LOG = false;
    private static final java.lang.String SYSTEM_PROPERTY_DEVICE_THEME = "persist.sys.theme";
    private final java.time.LocalTime DEFAULT_CUSTOM_NIGHT_END_TIME;
    private final java.time.LocalTime DEFAULT_CUSTOM_NIGHT_START_TIME;
    private com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManager;
    private android.app.AlarmManager mAlarmManager;
    private int mAttentionModeThemeOverlay;
    private final android.content.BroadcastReceiver mBatteryReceiver;
    private boolean mCar;
    private int mCarModeEnableFlags;
    private boolean mCarModeEnabled;
    private boolean mCarModeKeepsScreenOn;
    private java.util.Map<java.lang.Integer, java.lang.String> mCarModePackagePriority;
    private boolean mCharging;
    private boolean mComputedNightMode;
    private android.content.res.Configuration mConfiguration;
    private final android.database.ContentObserver mContrastObserver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.lang.Float> mContrasts;
    int mCurUiMode;
    private int mCurrentUser;
    private java.time.LocalTime mCustomAutoNightModeEndMilliseconds;
    private java.time.LocalTime mCustomAutoNightModeStartMilliseconds;
    private final android.app.AlarmManager.OnAlarmListener mCustomTimeListener;
    private final android.database.ContentObserver mDarkThemeObserver;
    private int mDefaultUiModeType;
    private boolean mDeskModeKeepsScreenOn;
    private final android.content.BroadcastReceiver mDeviceInactiveListener;
    private final android.content.BroadcastReceiver mDockModeReceiver;
    private int mDockState;
    private android.service.dreams.DreamManagerInternal mDreamManagerInternal;
    private boolean mDreamsDisabledByAmbientModeSuppression;
    private boolean mEnableCarDockLaunch;
    private final android.os.Handler mHandler;
    private boolean mHoldingConfiguration;
    private final com.android.server.UiModeManagerService.Injector mInjector;
    private android.app.KeyguardManager mKeyguardManager;
    private boolean mLastBedtimeRequestedNightMode;
    private int mLastBroadcastState;
    private android.os.PowerManagerInternal mLocalPowerManager;
    private final com.android.server.UiModeManagerService.LocalService mLocalService;
    private final java.lang.Object mLock;
    private final com.android.server.UiModeManagerService.NightMode mNightMode;
    private int mNightModeCustomType;
    private boolean mNightModeLocked;
    private android.app.NotificationManager mNotificationManager;
    private final android.content.BroadcastReceiver mOnShutdown;
    private final android.content.BroadcastReceiver mOnTimeChangedHandler;
    private boolean mOverrideNightModeOff;
    private boolean mOverrideNightModeOn;
    private int mOverrideNightModeUser;
    private android.os.PowerManager mPowerManager;
    private boolean mPowerSave;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.util.SparseArray<java.util.List<com.android.server.UiModeManagerService.ProjectionHolder>> mProjectionHolders;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.util.SparseArray<android.os.RemoteCallbackList<android.app.IOnProjectionStateChangedListener>> mProjectionListeners;
    private final android.content.BroadcastReceiver mResultReceiver;
    private final android.app.IUiModeManager.Stub mService;
    private int mSetUiMode;
    private final android.content.BroadcastReceiver mSettingsRestored;
    private boolean mSetupWizardComplete;
    private final android.database.ContentObserver mSetupWizardObserver;
    private boolean mStartDreamImmediatelyOnDock;
    private android.app.StatusBarManager mStatusBarManager;
    boolean mSystemReady;
    private boolean mTelevision;
    private final com.android.server.twilight.TwilightListener mTwilightListener;
    private com.android.server.twilight.TwilightManager mTwilightManager;
    private boolean mUiModeLocked;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.os.RemoteCallbackList<android.app.IUiModeManagerCallback>> mUiModeManagerCallbacks;
    private boolean mVrHeadset;
    private final android.service.vr.IVrStateCallbacks mVrStateCallbacks;
    private boolean mWaitForDeviceInactive;
    private android.os.PowerManager.WakeLock mWakeLock;
    private boolean mWatch;
    private com.android.server.wm.WindowManagerInternal mWindowManager;
    private static final java.lang.String TAG = android.app.UiModeManager.class.getSimpleName();

    @com.android.internal.annotations.VisibleForTesting
    public static final java.util.Set<java.lang.Integer> SUPPORTED_NIGHT_MODE_CUSTOM_TYPES = new android.util.ArraySet(new java.lang.Integer[]{0, 1});

    private interface NightMode {
        int get();

        void set(int i);
    }

    public UiModeManagerService(android.content.Context context) {
        this(context, false, null, new com.android.server.UiModeManagerService.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    protected UiModeManagerService(android.content.Context context, boolean z, com.android.server.twilight.TwilightManager twilightManager, com.android.server.UiModeManagerService.Injector injector) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mDockState = 0;
        this.mLastBroadcastState = 0;
        this.mNightMode = new com.android.server.UiModeManagerService.NightMode() { // from class: com.android.server.UiModeManagerService.1
            private int mNightModeValue = 1;

            @Override // com.android.server.UiModeManagerService.NightMode
            public int get() {
                return this.mNightModeValue;
            }

            @Override // com.android.server.UiModeManagerService.NightMode
            public void set(int i) {
                this.mNightModeValue = i;
                if (android.app.Flags.enableNightModeBinderCache()) {
                    android.app.UiModeManager.invalidateNightModeCache();
                }
            }
        };
        this.mNightModeCustomType = -1;
        this.mAttentionModeThemeOverlay = 1000;
        this.DEFAULT_CUSTOM_NIGHT_START_TIME = java.time.LocalTime.of(22, 0);
        this.DEFAULT_CUSTOM_NIGHT_END_TIME = java.time.LocalTime.of(6, 0);
        this.mCustomAutoNightModeStartMilliseconds = this.DEFAULT_CUSTOM_NIGHT_START_TIME;
        this.mCustomAutoNightModeEndMilliseconds = this.DEFAULT_CUSTOM_NIGHT_END_TIME;
        this.mCarModePackagePriority = new java.util.HashMap();
        this.mCarModeEnabled = false;
        this.mCharging = false;
        this.mPowerSave = false;
        this.mWaitForDeviceInactive = false;
        this.mLastBedtimeRequestedNightMode = false;
        this.mStartDreamImmediatelyOnDock = true;
        this.mDreamsDisabledByAmbientModeSuppression = false;
        this.mEnableCarDockLaunch = true;
        this.mUiModeLocked = false;
        this.mNightModeLocked = false;
        this.mCurUiMode = 0;
        this.mSetUiMode = 0;
        this.mHoldingConfiguration = false;
        this.mConfiguration = new android.content.res.Configuration();
        this.mHandler = new android.os.Handler();
        this.mOverrideNightModeUser = 0;
        this.mLocalService = new com.android.server.UiModeManagerService.LocalService();
        this.mUiModeManagerCallbacks = new android.util.SparseArray<>();
        this.mContrasts = new android.util.SparseArray<>();
        this.mResultReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (getResultCode() != -1) {
                    return;
                }
                int intExtra = intent.getIntExtra("enableFlags", 0);
                int intExtra2 = intent.getIntExtra("disableFlags", 0);
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    com.android.server.UiModeManagerService.this.updateAfterBroadcastLocked(intent.getAction(), intExtra, intExtra2);
                }
            }
        };
        this.mDockModeReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.UiModeManagerService.this.updateDockState(intent.getIntExtra("android.intent.extra.DOCK_STATE", 0));
            }
        };
        this.mBatteryReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                char c;
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case -1538406691:
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        com.android.server.UiModeManagerService.this.mCharging = intent.getIntExtra("plugged", 0) != 0;
                        break;
                }
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    try {
                        if (com.android.server.UiModeManagerService.this.mSystemReady) {
                            com.android.server.UiModeManagerService.this.updateLocked(0, 0);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mTwilightListener = new com.android.server.twilight.TwilightListener() { // from class: com.android.server.UiModeManagerService.5
            @Override // com.android.server.twilight.TwilightListener
            public void onTwilightStateChanged(@android.annotation.Nullable com.android.server.twilight.TwilightState twilightState) {
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    try {
                        if (com.android.server.UiModeManagerService.this.mNightMode.get() == 0 && com.android.server.UiModeManagerService.this.mSystemReady) {
                            if (com.android.server.UiModeManagerService.this.shouldApplyAutomaticChangesImmediately()) {
                                com.android.server.UiModeManagerService.this.updateLocked(0, 0);
                            } else {
                                com.android.server.UiModeManagerService.this.registerDeviceInactiveListenerLocked();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mDeviceInactiveListener = new android.content.BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    com.android.server.UiModeManagerService.this.unregisterDeviceInactiveListenerLocked();
                    com.android.server.UiModeManagerService.this.updateLocked(0, 0);
                }
            }
        };
        this.mOnTimeChangedHandler = new android.content.BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    com.android.server.UiModeManagerService.this.updateCustomTimeLocked();
                }
            }
        };
        this.mCustomTimeListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.UiModeManagerService$$ExternalSyntheticLambda2
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.UiModeManagerService.this.lambda$new$0();
            }
        };
        this.mVrStateCallbacks = new android.service.vr.IVrStateCallbacks.Stub() { // from class: com.android.server.UiModeManagerService.8
            public void onVrStateChanged(boolean z2) {
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    try {
                        com.android.server.UiModeManagerService.this.mVrHeadset = z2;
                        if (com.android.server.UiModeManagerService.this.mSystemReady) {
                            com.android.server.UiModeManagerService.this.updateLocked(0, 0);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mSetupWizardObserver = new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.UiModeManagerService.9
            @Override // android.database.ContentObserver
            public void onChange(boolean z2, android.net.Uri uri) {
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    try {
                        if (com.android.server.UiModeManagerService.this.setupWizardCompleteForCurrentUser() && !z2) {
                            com.android.server.UiModeManagerService.this.mSetupWizardComplete = true;
                            com.android.server.UiModeManagerService.this.getContext().getContentResolver().unregisterContentObserver(com.android.server.UiModeManagerService.this.mSetupWizardObserver);
                            android.content.Context context2 = com.android.server.UiModeManagerService.this.getContext();
                            com.android.server.UiModeManagerService.this.updateNightModeFromSettingsLocked(context2, context2.getResources(), android.os.UserHandle.getCallingUserId());
                            com.android.server.UiModeManagerService.this.updateLocked(0, 0);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mDarkThemeObserver = new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.UiModeManagerService.10
            @Override // android.database.ContentObserver
            public void onChange(boolean z2, android.net.Uri uri) {
                com.android.server.UiModeManagerService.this.updateSystemProperties();
            }
        };
        this.mContrastObserver = new com.android.server.UiModeManagerService.AnonymousClass11(this.mHandler);
        this.mOnShutdown = new android.content.BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.12
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (com.android.server.UiModeManagerService.this.mNightMode.get() == 0) {
                    com.android.server.UiModeManagerService.this.persistComputedNightMode(com.android.server.UiModeManagerService.this.mCurrentUser);
                }
            }
        };
        this.mSettingsRestored = new android.content.BroadcastReceiver() { // from class: com.android.server.UiModeManagerService.13
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (java.util.Arrays.asList("ui_night_mode", "dark_theme_custom_start_time", "dark_theme_custom_end_time").contains(intent.getExtras().getCharSequence("setting_name"))) {
                    synchronized (com.android.server.UiModeManagerService.this.mLock) {
                        com.android.server.UiModeManagerService.this.updateNightModeFromSettingsLocked(context2, context2.getResources(), android.os.UserHandle.getCallingUserId());
                        com.android.server.UiModeManagerService.this.updateConfigurationLocked();
                    }
                }
            }
        };
        this.mService = new com.android.server.UiModeManagerService.Stub(context);
        this.mConfiguration.setToDefaults();
        this.mSetupWizardComplete = z;
        this.mTwilightManager = twilightManager;
        this.mInjector = injector;
    }

    private static android.content.Intent buildHomeIntent(java.lang.String str) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory(str);
        intent.setFlags(270532608);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        synchronized (this.mLock) {
            updateCustomTimeLocked();
        }
    }

    /* renamed from: com.android.server.UiModeManagerService$11, reason: invalid class name */
    class AnonymousClass11 extends android.database.ContentObserver {
        AnonymousClass11(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                try {
                    if (com.android.server.UiModeManagerService.this.updateContrastLocked()) {
                        final float contrastLocked = com.android.server.UiModeManagerService.this.getContrastLocked();
                        ((android.os.RemoteCallbackList) com.android.server.UiModeManagerService.this.mUiModeManagerCallbacks.get(com.android.server.UiModeManagerService.this.mCurrentUser, new android.os.RemoteCallbackList())).broadcast(com.android.internal.util.FunctionalUtils.ignoreRemoteException(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.UiModeManagerService$11$$ExternalSyntheticLambda0
                            public final void acceptOrThrow(java.lang.Object obj) {
                                ((android.app.IUiModeManagerCallback) obj).notifyContrastChanged(contrastLocked);
                            }
                        }));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSystemProperties() {
        int intForUser = android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "ui_night_mode", this.mNightMode.get(), 0);
        if (intForUser == 0 || intForUser == 3) {
            intForUser = 2;
        }
        android.os.SystemProperties.set(SYSTEM_PROPERTY_DEVICE_THEME, java.lang.Integer.toString(intForUser));
    }

    @com.android.internal.annotations.VisibleForTesting
    void setStartDreamImmediatelyOnDock(boolean z) {
        this.mStartDreamImmediatelyOnDock = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setDreamsDisabledByAmbientModeSuppression(boolean z) {
        this.mDreamsDisabledByAmbientModeSuppression = z;
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        this.mCurrentUser = targetUser2.getUserIdentifier();
        if (this.mNightMode.get() == 0) {
            persistComputedNightMode(targetUser.getUserIdentifier());
        }
        getContext().getContentResolver().unregisterContentObserver(this.mSetupWizardObserver);
        verifySetupWizardCompleted();
        synchronized (this.mLock) {
            updateNightModeFromSettingsLocked(getContext(), getContext().getResources(), targetUser2.getUserIdentifier());
            updateLocked(0, 0);
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            synchronized (this.mLock) {
                try {
                    android.content.Context context = getContext();
                    boolean z = true;
                    this.mSystemReady = true;
                    this.mKeyguardManager = (android.app.KeyguardManager) context.getSystemService(android.app.KeyguardManager.class);
                    this.mPowerManager = (android.os.PowerManager) context.getSystemService("power");
                    this.mWakeLock = this.mPowerManager.newWakeLock(26, TAG);
                    this.mWindowManager = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
                    this.mActivityTaskManager = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
                    this.mAlarmManager = (android.app.AlarmManager) getContext().getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
                    com.android.server.twilight.TwilightManager twilightManager = (com.android.server.twilight.TwilightManager) getLocalService(com.android.server.twilight.TwilightManager.class);
                    if (twilightManager != null) {
                        this.mTwilightManager = twilightManager;
                    }
                    this.mLocalPowerManager = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
                    this.mDreamManagerInternal = (android.service.dreams.DreamManagerInternal) com.android.server.LocalServices.getService(android.service.dreams.DreamManagerInternal.class);
                    initPowerSave();
                    if (this.mDockState != 2) {
                        z = false;
                    }
                    this.mCarModeEnabled = z;
                    registerVrStateListener();
                    context.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("ui_night_mode"), false, this.mDarkThemeObserver, 0);
                    context.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("contrast_level"), false, this.mContrastObserver, -1);
                    context.registerReceiver(this.mDockModeReceiver, new android.content.IntentFilter("android.intent.action.DOCK_EVENT"));
                    context.registerReceiver(this.mBatteryReceiver, new android.content.IntentFilter("android.intent.action.BATTERY_CHANGED"));
                    context.registerReceiver(this.mSettingsRestored, new android.content.IntentFilter("android.os.action.SETTING_RESTORED"));
                    context.registerReceiver(this.mOnShutdown, new android.content.IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
                    updateConfigurationLocked();
                    applyConfigurationExternallyLocked();
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        final android.content.Context context = getContext();
        verifySetupWizardCompleted();
        final android.content.res.Resources resources = context.getResources();
        this.mNightMode.set(resources.getInteger(android.R.integer.config_defaultNightDisplayCustomEndTime));
        this.mStartDreamImmediatelyOnDock = resources.getBoolean(android.R.bool.config_sms_utf8_support);
        this.mDreamsDisabledByAmbientModeSuppression = resources.getBoolean(android.R.bool.config_dreamsActivatedOnSleepByDefault);
        this.mDefaultUiModeType = resources.getInteger(android.R.integer.config_defaultRefreshRateInHbmHdr);
        this.mCarModeKeepsScreenOn = resources.getInteger(android.R.integer.config_cameraPrivacyLightAlsAveragingIntervalMillis) == 1;
        this.mDeskModeKeepsScreenOn = resources.getInteger(android.R.integer.config_defaultUiModeType) == 1;
        this.mEnableCarDockLaunch = resources.getBoolean(android.R.bool.config_enableBurnInProtection);
        this.mUiModeLocked = resources.getBoolean(android.R.bool.config_letterboxIsVerticalReachabilityEnabled);
        this.mNightModeLocked = resources.getBoolean(android.R.bool.config_letterboxIsSplitScreenAspectRatioForUnresizableAppsEnabled);
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        this.mTelevision = packageManager.hasSystemFeature("android.hardware.type.television") || packageManager.hasSystemFeature("android.software.leanback");
        this.mCar = packageManager.hasSystemFeature("android.hardware.type.automotive");
        this.mWatch = packageManager.hasSystemFeature("android.hardware.type.watch");
        com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.UiModeManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.UiModeManagerService.this.lambda$onStart$1(context, resources);
            }
        }, TAG + ".onStart");
        publishBinderService("uimode", this.mService);
        publishLocalService(com.android.server.UiModeManagerInternal.class, this.mLocalService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$1(android.content.Context context, android.content.res.Resources resources) {
        synchronized (this.mLock) {
            try {
                com.android.server.twilight.TwilightManager twilightManager = (com.android.server.twilight.TwilightManager) getLocalService(com.android.server.twilight.TwilightManager.class);
                if (twilightManager != null) {
                    this.mTwilightManager = twilightManager;
                }
                updateNightModeFromSettingsLocked(context, resources, android.os.UserHandle.getCallingUserId());
                updateSystemProperties();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void persistComputedNightMode(int i) {
        android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "ui_night_mode_last_computed", this.mComputedNightMode ? 1 : 0, i);
    }

    private void initPowerSave() {
        this.mPowerSave = this.mLocalPowerManager.getLowPowerState(16).batterySaverEnabled;
        this.mLocalPowerManager.registerLowPowerModeObserver(16, new java.util.function.Consumer() { // from class: com.android.server.UiModeManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.UiModeManagerService.this.lambda$initPowerSave$2((android.os.PowerSaveState) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPowerSave$2(android.os.PowerSaveState powerSaveState) {
        synchronized (this.mLock) {
            try {
                if (this.mPowerSave == powerSaveState.batterySaverEnabled) {
                    return;
                }
                this.mPowerSave = powerSaveState.batterySaverEnabled;
                if (this.mSystemReady) {
                    updateLocked(0, 0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.app.IUiModeManager getService() {
        return this.mService;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.content.res.Configuration getConfiguration() {
        return this.mConfiguration;
    }

    private void verifySetupWizardCompleted() {
        android.content.Context context = getContext();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!setupWizardCompleteForCurrentUser()) {
            this.mSetupWizardComplete = false;
            context.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("user_setup_complete"), false, this.mSetupWizardObserver, callingUserId);
        } else {
            this.mSetupWizardComplete = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setupWizardCompleteForCurrentUser() {
        return android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "user_setup_complete", 0, android.os.UserHandle.getCallingUserId()) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCustomTimeLocked() {
        if (this.mNightMode.get() != 3) {
            return;
        }
        if (shouldApplyAutomaticChangesImmediately()) {
            updateLocked(0, 0);
        } else {
            registerDeviceInactiveListenerLocked();
        }
        scheduleNextCustomTimeListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNightModeFromSettingsLocked(android.content.Context context, android.content.res.Resources resources, int i) {
        if (!this.mCarModeEnabled && !this.mCar && this.mSetupWizardComplete) {
            this.mNightMode.set(android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "ui_night_mode", resources.getInteger(android.R.integer.config_defaultNightDisplayCustomEndTime), i));
            this.mNightModeCustomType = android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "ui_night_mode_custom_type", -1, i);
            this.mOverrideNightModeOn = android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "ui_night_mode_override_on", 0, i) != 0;
            this.mOverrideNightModeOff = android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "ui_night_mode_override_off", 0, i) != 0;
            this.mCustomAutoNightModeStartMilliseconds = java.time.LocalTime.ofNanoOfDay(android.provider.Settings.Secure.getLongForUser(context.getContentResolver(), "dark_theme_custom_start_time", this.DEFAULT_CUSTOM_NIGHT_START_TIME.toNanoOfDay() / 1000, i) * 1000);
            this.mCustomAutoNightModeEndMilliseconds = java.time.LocalTime.ofNanoOfDay(android.provider.Settings.Secure.getLongForUser(context.getContentResolver(), "dark_theme_custom_end_time", this.DEFAULT_CUSTOM_NIGHT_END_TIME.toNanoOfDay() / 1000, i) * 1000);
            if (this.mNightMode.get() == 0) {
                this.mComputedNightMode = android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "ui_night_mode_last_computed", 0, i) != 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long toMilliSeconds(java.time.LocalTime localTime) {
        return localTime.toNanoOfDay() / 1000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.time.LocalTime fromMilliseconds(long j) {
        return java.time.LocalTime.ofNanoOfDay(j * 1000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerDeviceInactiveListenerLocked() {
        if (this.mPowerSave) {
            return;
        }
        this.mWaitForDeviceInactive = true;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.DREAMING_STARTED");
        getContext().registerReceiver(this.mDeviceInactiveListener, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelCustomAlarm() {
        this.mAlarmManager.cancel(this.mCustomTimeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterDeviceInactiveListenerLocked() {
        this.mWaitForDeviceInactive = false;
        try {
            getContext().unregisterReceiver(this.mDeviceInactiveListener);
        } catch (java.lang.IllegalArgumentException e) {
        }
    }

    private void registerTimeChangeEvent() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        getContext().registerReceiver(this.mOnTimeChangedHandler, intentFilter);
    }

    private void unregisterTimeChangeEvent() {
        try {
            getContext().unregisterReceiver(this.mOnTimeChangedHandler);
        } catch (java.lang.IllegalArgumentException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class Stub extends android.app.IUiModeManager.Stub {
        Stub(android.content.Context context) {
            super(android.os.PermissionEnforcer.fromContext(context));
        }

        public void addCallback(android.app.IUiModeManagerCallback iUiModeManagerCallback) {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                try {
                    if (!com.android.server.UiModeManagerService.this.mUiModeManagerCallbacks.contains(callingUserId)) {
                        com.android.server.UiModeManagerService.this.mUiModeManagerCallbacks.put(callingUserId, new android.os.RemoteCallbackList());
                    }
                    ((android.os.RemoteCallbackList) com.android.server.UiModeManagerService.this.mUiModeManagerCallbacks.get(callingUserId)).register(iUiModeManagerCallback);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void enableCarMode(int i, int i2, java.lang.String str) {
            if (isUiModeLocked()) {
                android.util.Slog.e(com.android.server.UiModeManagerService.TAG, "enableCarMode while UI mode is locked");
                return;
            }
            if (i2 != 0 && com.android.server.UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.ENTER_CAR_MODE_PRIORITIZED") != 0) {
                throw new java.lang.SecurityException("Enabling car mode with a priority requires permission ENTER_CAR_MODE_PRIORITIZED");
            }
            if (!(com.android.server.UiModeManagerService.this.mInjector.getCallingUid() == 2000)) {
                com.android.server.UiModeManagerService.this.assertLegit(str);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    try {
                        com.android.server.UiModeManagerService.this.setCarModeLocked(true, i, i2, str);
                        if (com.android.server.UiModeManagerService.this.mSystemReady) {
                            com.android.server.UiModeManagerService.this.updateLocked(i, 0);
                        }
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void disableCarMode(int i) {
            disableCarModeByCallingPackage(i, null);
        }

        public void disableCarModeByCallingPackage(int i, final java.lang.String str) {
            if (isUiModeLocked()) {
                android.util.Slog.e(com.android.server.UiModeManagerService.TAG, "disableCarMode while UI mode is locked");
                return;
            }
            int callingUid = com.android.server.UiModeManagerService.this.mInjector.getCallingUid();
            boolean z = callingUid == 1000;
            boolean z2 = callingUid == 2000;
            if (!z && !z2) {
                com.android.server.UiModeManagerService.this.assertLegit(str);
            }
            int i2 = z ? i : i & (-3);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    try {
                        com.android.server.UiModeManagerService.this.setCarModeLocked(false, i2, ((java.lang.Integer) com.android.server.UiModeManagerService.this.mCarModePackagePriority.entrySet().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.UiModeManagerService$Stub$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(java.lang.Object obj) {
                                boolean lambda$disableCarModeByCallingPackage$0;
                                lambda$disableCarModeByCallingPackage$0 = com.android.server.UiModeManagerService.Stub.lambda$disableCarModeByCallingPackage$0(str, (java.util.Map.Entry) obj);
                                return lambda$disableCarModeByCallingPackage$0;
                            }
                        }).findFirst().map(new com.android.server.UiModeManagerService$Stub$$ExternalSyntheticLambda3()).orElse(0)).intValue(), str);
                        if (com.android.server.UiModeManagerService.this.mSystemReady) {
                            com.android.server.UiModeManagerService.this.updateLocked(0, i);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$disableCarModeByCallingPackage$0(java.lang.String str, java.util.Map.Entry entry) {
            return ((java.lang.String) entry.getValue()).equals(str);
        }

        public int getCurrentModeType() {
            int i;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    i = com.android.server.UiModeManagerService.this.mCurUiMode & 15;
                }
                return i;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setNightMode(int i) {
            int i2;
            if (i == 3) {
                i2 = 0;
            } else {
                i2 = -1;
            }
            setNightModeInternal(i, i2);
        }

        private void setNightModeInternal(int i, int i2) {
            if (isNightModeLocked() && com.android.server.UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.MODIFY_DAY_NIGHT_MODE") != 0) {
                android.util.Slog.e(com.android.server.UiModeManagerService.TAG, "Night mode locked, requires MODIFY_DAY_NIGHT_MODE permission");
                return;
            }
            switch (i) {
                case 0:
                case 1:
                case 2:
                    break;
                case 3:
                    if (!com.android.server.UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES.contains(java.lang.Integer.valueOf(i2))) {
                        throw new java.lang.IllegalArgumentException("Can't set the custom type to " + i2);
                    }
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown mode: " + i);
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.UiModeManagerService.this.mLock) {
                    try {
                        if (com.android.server.UiModeManagerService.this.mNightMode.get() != i || com.android.server.UiModeManagerService.this.mNightModeCustomType != i2) {
                            if (com.android.server.UiModeManagerService.this.mNightMode.get() == 0 || com.android.server.UiModeManagerService.this.mNightMode.get() == 3) {
                                com.android.server.UiModeManagerService.this.unregisterDeviceInactiveListenerLocked();
                                com.android.server.UiModeManagerService.this.cancelCustomAlarm();
                            }
                            com.android.server.UiModeManagerService uiModeManagerService = com.android.server.UiModeManagerService.this;
                            if (i != 3) {
                                i2 = -1;
                            }
                            uiModeManagerService.mNightModeCustomType = i2;
                            com.android.server.UiModeManagerService.this.mNightMode.set(i);
                            com.android.server.UiModeManagerService.this.mAttentionModeThemeOverlay = 1000;
                            com.android.server.UiModeManagerService.this.resetNightModeOverrideLocked();
                            com.android.server.UiModeManagerService.this.persistNightMode(callingUserId);
                            if ((com.android.server.UiModeManagerService.this.mNightMode.get() != 0 && com.android.server.UiModeManagerService.this.mNightMode.get() != 3) || com.android.server.UiModeManagerService.this.shouldApplyAutomaticChangesImmediately()) {
                                com.android.server.UiModeManagerService.this.unregisterDeviceInactiveListenerLocked();
                                com.android.server.UiModeManagerService.this.updateLocked(0, 0);
                            } else {
                                com.android.server.UiModeManagerService.this.registerDeviceInactiveListenerLocked();
                            }
                        }
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getNightMode() {
            int i;
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                i = com.android.server.UiModeManagerService.this.mNightMode.get();
            }
            return i;
        }

        @android.annotation.EnforcePermission("android.permission.MODIFY_DAY_NIGHT_MODE")
        public void setNightModeCustomType(int i) {
            setNightModeCustomType_enforcePermission();
            setNightModeInternal(3, i);
        }

        @android.annotation.EnforcePermission("android.permission.MODIFY_DAY_NIGHT_MODE")
        public int getNightModeCustomType() {
            int i;
            getNightModeCustomType_enforcePermission();
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                i = com.android.server.UiModeManagerService.this.mNightModeCustomType;
            }
            return i;
        }

        @android.annotation.EnforcePermission("android.permission.MODIFY_DAY_NIGHT_MODE")
        public void setAttentionModeThemeOverlay(int i) {
            setAttentionModeThemeOverlay_enforcePermission();
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                try {
                    if (com.android.server.UiModeManagerService.this.mAttentionModeThemeOverlay != i) {
                        com.android.server.UiModeManagerService.this.mAttentionModeThemeOverlay = i;
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.UiModeManagerService$Stub$$ExternalSyntheticLambda0
                            public final void runOrThrow() {
                                com.android.server.UiModeManagerService.Stub.this.lambda$setAttentionModeThemeOverlay$1();
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setAttentionModeThemeOverlay$1() throws java.lang.Exception {
            com.android.server.UiModeManagerService.this.updateLocked(0, 0);
        }

        @android.annotation.EnforcePermission("android.permission.MODIFY_DAY_NIGHT_MODE")
        public int getAttentionModeThemeOverlay() {
            int i;
            getAttentionModeThemeOverlay_enforcePermission();
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                i = com.android.server.UiModeManagerService.this.mAttentionModeThemeOverlay;
            }
            return i;
        }

        public void setApplicationNightMode(int i) {
            int i2;
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                    switch (i) {
                        case 1:
                            i2 = 16;
                            break;
                        case 2:
                            i2 = 32;
                            break;
                        default:
                            i2 = 0;
                            break;
                    }
                    com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater createPackageConfigurationUpdater = com.android.server.UiModeManagerService.this.mActivityTaskManager.createPackageConfigurationUpdater();
                    createPackageConfigurationUpdater.setNightMode(i2);
                    createPackageConfigurationUpdater.commit();
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown mode: " + i);
            }
        }

        public boolean isUiModeLocked() {
            boolean z;
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                z = com.android.server.UiModeManagerService.this.mUiModeLocked;
            }
            return z;
        }

        public boolean isNightModeLocked() {
            boolean z;
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                z = com.android.server.UiModeManagerService.this.mNightModeLocked;
            }
            return z;
        }

        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.UiModeManagerService.Shell(com.android.server.UiModeManagerService.this.mService).exec(com.android.server.UiModeManagerService.this.mService, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.UiModeManagerService.this.getContext(), com.android.server.UiModeManagerService.TAG, printWriter)) {
                com.android.server.UiModeManagerService.this.dumpImpl(printWriter);
            }
        }

        public boolean setNightModeActivatedForCustomMode(int i, boolean z) {
            return setNightModeActivatedForModeInternal(i, z);
        }

        public boolean setNightModeActivated(boolean z) {
            return setNightModeActivatedForModeInternal(com.android.server.UiModeManagerService.this.mNightModeCustomType, z);
        }

        private boolean setNightModeActivatedForModeInternal(int i, boolean z) {
            if (com.android.server.UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.MODIFY_DAY_NIGHT_MODE") != 0) {
                android.util.Slog.e(com.android.server.UiModeManagerService.TAG, "Night mode locked, requires MODIFY_DAY_NIGHT_MODE permission");
                return false;
            }
            if (android.os.Binder.getCallingUserHandle().getIdentifier() != com.android.server.UiModeManagerService.this.mCurrentUser && com.android.server.UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") != 0) {
                android.util.Slog.e(com.android.server.UiModeManagerService.TAG, "Target user is not current user, INTERACT_ACROSS_USERS permission is required");
                return false;
            }
            if (i == 1) {
                com.android.server.UiModeManagerService.this.mLastBedtimeRequestedNightMode = z;
            }
            if (i != com.android.server.UiModeManagerService.this.mNightModeCustomType) {
                return false;
            }
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                try {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (com.android.server.UiModeManagerService.this.mNightMode.get() != 0 && com.android.server.UiModeManagerService.this.mNightMode.get() != 3) {
                            if (com.android.server.UiModeManagerService.this.mNightMode.get() == 1 && z) {
                                com.android.server.UiModeManagerService.this.mNightMode.set(2);
                            } else if (com.android.server.UiModeManagerService.this.mNightMode.get() == 2 && !z) {
                                com.android.server.UiModeManagerService.this.mNightMode.set(1);
                            }
                            com.android.server.UiModeManagerService.this.updateConfigurationLocked();
                            com.android.server.UiModeManagerService.this.applyConfigurationExternallyLocked();
                            com.android.server.UiModeManagerService.this.persistNightMode(com.android.server.UiModeManagerService.this.mCurrentUser);
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                        com.android.server.UiModeManagerService.this.unregisterDeviceInactiveListenerLocked();
                        com.android.server.UiModeManagerService.this.mOverrideNightModeOff = z ? false : true;
                        com.android.server.UiModeManagerService.this.mOverrideNightModeOn = z;
                        com.android.server.UiModeManagerService.this.mOverrideNightModeUser = com.android.server.UiModeManagerService.this.mCurrentUser;
                        com.android.server.UiModeManagerService.this.persistNightModeOverrides(com.android.server.UiModeManagerService.this.mCurrentUser);
                        com.android.server.UiModeManagerService.this.updateConfigurationLocked();
                        com.android.server.UiModeManagerService.this.applyConfigurationExternallyLocked();
                        com.android.server.UiModeManagerService.this.persistNightMode(com.android.server.UiModeManagerService.this.mCurrentUser);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
            return true;
        }

        public long getCustomNightModeStart() {
            return com.android.server.UiModeManagerService.this.mCustomAutoNightModeStartMilliseconds.toNanoOfDay() / 1000;
        }

        public void setCustomNightModeStart(long j) {
            java.time.LocalTime ofNanoOfDay;
            if (isNightModeLocked() && com.android.server.UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.MODIFY_DAY_NIGHT_MODE") != 0) {
                android.util.Slog.e(com.android.server.UiModeManagerService.TAG, "Set custom time start, requires MODIFY_DAY_NIGHT_MODE permission");
                return;
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    ofNanoOfDay = java.time.LocalTime.ofNanoOfDay(j * 1000);
                } catch (java.time.DateTimeException e) {
                    com.android.server.UiModeManagerService.this.unregisterDeviceInactiveListenerLocked();
                }
                if (ofNanoOfDay == null) {
                    return;
                }
                com.android.server.UiModeManagerService.this.mCustomAutoNightModeStartMilliseconds = ofNanoOfDay;
                com.android.server.UiModeManagerService.this.persistNightMode(callingUserId);
                com.android.server.UiModeManagerService.this.onCustomTimeUpdated(callingUserId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public long getCustomNightModeEnd() {
            return com.android.server.UiModeManagerService.this.mCustomAutoNightModeEndMilliseconds.toNanoOfDay() / 1000;
        }

        public void setCustomNightModeEnd(long j) {
            java.time.LocalTime ofNanoOfDay;
            if (isNightModeLocked() && com.android.server.UiModeManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.MODIFY_DAY_NIGHT_MODE") != 0) {
                android.util.Slog.e(com.android.server.UiModeManagerService.TAG, "Set custom time end, requires MODIFY_DAY_NIGHT_MODE permission");
                return;
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    ofNanoOfDay = java.time.LocalTime.ofNanoOfDay(j * 1000);
                } catch (java.time.DateTimeException e) {
                    com.android.server.UiModeManagerService.this.unregisterDeviceInactiveListenerLocked();
                }
                if (ofNanoOfDay == null) {
                    return;
                }
                com.android.server.UiModeManagerService.this.mCustomAutoNightModeEndMilliseconds = ofNanoOfDay;
                com.android.server.UiModeManagerService.this.onCustomTimeUpdated(callingUserId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean requestProjection(android.os.IBinder iBinder, int i, @android.annotation.NonNull java.lang.String str) {
            com.android.server.UiModeManagerService.this.assertLegit(str);
            com.android.server.UiModeManagerService.assertSingleProjectionType(i);
            com.android.server.UiModeManagerService.this.enforceProjectionTypePermissions(i);
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                try {
                    if (com.android.server.UiModeManagerService.this.mProjectionHolders == null) {
                        com.android.server.UiModeManagerService.this.mProjectionHolders = new android.util.SparseArray(1);
                    }
                    if (!com.android.server.UiModeManagerService.this.mProjectionHolders.contains(i)) {
                        com.android.server.UiModeManagerService.this.mProjectionHolders.put(i, new java.util.ArrayList(1));
                    }
                    java.util.List list = (java.util.List) com.android.server.UiModeManagerService.this.mProjectionHolders.get(i);
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        if (str.equals(((com.android.server.UiModeManagerService.ProjectionHolder) list.get(i2)).mPackageName)) {
                            return true;
                        }
                    }
                    if (i == 1 && !list.isEmpty()) {
                        return false;
                    }
                    final com.android.server.UiModeManagerService uiModeManagerService = com.android.server.UiModeManagerService.this;
                    com.android.server.UiModeManagerService.ProjectionHolder projectionHolder = new com.android.server.UiModeManagerService.ProjectionHolder(str, i, iBinder, new com.android.server.UiModeManagerService.ProjectionHolder.ProjectionReleaser() { // from class: com.android.server.UiModeManagerService$Stub$$ExternalSyntheticLambda1
                        @Override // com.android.server.UiModeManagerService.ProjectionHolder.ProjectionReleaser
                        public final boolean release(int i3, java.lang.String str2) {
                            boolean releaseProjectionUnchecked;
                            releaseProjectionUnchecked = com.android.server.UiModeManagerService.this.releaseProjectionUnchecked(i3, str2);
                            return releaseProjectionUnchecked;
                        }
                    });
                    if (!projectionHolder.linkToDeath()) {
                        return false;
                    }
                    list.add(projectionHolder);
                    android.util.Slog.d(com.android.server.UiModeManagerService.TAG, "Package " + str + " set projection type " + i + ".");
                    com.android.server.UiModeManagerService.this.onProjectionStateChangedLocked(i);
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean releaseProjection(int i, @android.annotation.NonNull java.lang.String str) {
            com.android.server.UiModeManagerService.this.assertLegit(str);
            com.android.server.UiModeManagerService.assertSingleProjectionType(i);
            com.android.server.UiModeManagerService.this.enforceProjectionTypePermissions(i);
            return com.android.server.UiModeManagerService.this.releaseProjectionUnchecked(i, str);
        }

        @android.annotation.EnforcePermission("android.permission.READ_PROJECTION_STATE")
        public int getActiveProjectionTypes() {
            int i;
            getActiveProjectionTypes_enforcePermission();
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                try {
                    i = 0;
                    if (com.android.server.UiModeManagerService.this.mProjectionHolders != null) {
                        int i2 = 0;
                        while (i < com.android.server.UiModeManagerService.this.mProjectionHolders.size()) {
                            if (!((java.util.List) com.android.server.UiModeManagerService.this.mProjectionHolders.valueAt(i)).isEmpty()) {
                                i2 |= com.android.server.UiModeManagerService.this.mProjectionHolders.keyAt(i);
                            }
                            i++;
                        }
                        i = i2;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return i;
        }

        @android.annotation.EnforcePermission("android.permission.READ_PROJECTION_STATE")
        public java.util.List<java.lang.String> getProjectingPackages(int i) {
            java.util.ArrayList arrayList;
            getProjectingPackages_enforcePermission();
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                arrayList = new java.util.ArrayList();
                com.android.server.UiModeManagerService.this.populateWithRelevantActivePackageNames(i, arrayList);
            }
            return arrayList;
        }

        @android.annotation.EnforcePermission("android.permission.READ_PROJECTION_STATE")
        public void addOnProjectionStateChangedListener(android.app.IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i) {
            addOnProjectionStateChangedListener_enforcePermission();
            if (i == 0) {
                return;
            }
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                try {
                    if (com.android.server.UiModeManagerService.this.mProjectionListeners == null) {
                        com.android.server.UiModeManagerService.this.mProjectionListeners = new android.util.SparseArray(1);
                    }
                    if (!com.android.server.UiModeManagerService.this.mProjectionListeners.contains(i)) {
                        com.android.server.UiModeManagerService.this.mProjectionListeners.put(i, new android.os.RemoteCallbackList());
                    }
                    if (((android.os.RemoteCallbackList) com.android.server.UiModeManagerService.this.mProjectionListeners.get(i)).register(iOnProjectionStateChangedListener)) {
                        java.util.ArrayList arrayList = new java.util.ArrayList();
                        int populateWithRelevantActivePackageNames = com.android.server.UiModeManagerService.this.populateWithRelevantActivePackageNames(i, arrayList);
                        if (!arrayList.isEmpty()) {
                            try {
                                iOnProjectionStateChangedListener.onProjectionStateChanged(populateWithRelevantActivePackageNames, arrayList);
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.w(com.android.server.UiModeManagerService.TAG, "Failed a call to onProjectionStateChanged() during listener registration.");
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.READ_PROJECTION_STATE")
        public void removeOnProjectionStateChangedListener(android.app.IOnProjectionStateChangedListener iOnProjectionStateChangedListener) {
            removeOnProjectionStateChangedListener_enforcePermission();
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                try {
                    if (com.android.server.UiModeManagerService.this.mProjectionListeners != null) {
                        for (int i = 0; i < com.android.server.UiModeManagerService.this.mProjectionListeners.size(); i++) {
                            ((android.os.RemoteCallbackList) com.android.server.UiModeManagerService.this.mProjectionListeners.valueAt(i)).unregister(iOnProjectionStateChangedListener);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public float getContrast() {
            float contrastLocked;
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                contrastLocked = com.android.server.UiModeManagerService.this.getContrastLocked();
            }
            return contrastLocked;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceProjectionTypePermissions(int i) {
        if ((i & 1) != 0) {
            getContext().enforceCallingPermission("android.permission.TOGGLE_AUTOMOTIVE_PROJECTION", "toggleProjection");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void assertSingleProjectionType(int i) {
        boolean z = ((i + (-1)) & i) == 0;
        if (i == 0 || !z) {
            throw new java.lang.IllegalArgumentException("Must specify exactly one projection type.");
        }
    }

    private static java.util.List<java.lang.String> toPackageNameList(java.util.Collection<com.android.server.UiModeManagerService.ProjectionHolder> collection) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.android.server.UiModeManagerService.ProjectionHolder> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().mPackageName);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public int populateWithRelevantActivePackageNames(int i, java.util.List<java.lang.String> list) {
        list.clear();
        if (this.mProjectionHolders == null) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.mProjectionHolders.size(); i3++) {
            int keyAt = this.mProjectionHolders.keyAt(i3);
            java.util.List<com.android.server.UiModeManagerService.ProjectionHolder> valueAt = this.mProjectionHolders.valueAt(i3);
            if ((i & keyAt) != 0 && list.addAll(toPackageNameList(valueAt))) {
                i2 |= keyAt;
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean releaseProjectionUnchecked(int i, @android.annotation.NonNull java.lang.String str) {
        boolean z;
        java.util.List<com.android.server.UiModeManagerService.ProjectionHolder> list;
        synchronized (this.mLock) {
            try {
                z = false;
                if (this.mProjectionHolders != null && (list = this.mProjectionHolders.get(i)) != null) {
                    for (int size = list.size() - 1; size >= 0; size--) {
                        com.android.server.UiModeManagerService.ProjectionHolder projectionHolder = list.get(size);
                        if (str.equals(projectionHolder.mPackageName)) {
                            projectionHolder.unlinkToDeath();
                            android.util.Slog.d(TAG, "Projection type " + i + " released by " + str + ".");
                            list.remove(size);
                            z = true;
                        }
                    }
                }
                if (z) {
                    onProjectionStateChangedLocked(i);
                } else {
                    android.util.Slog.w(TAG, str + " tried to release projection type " + i + " but was not set by that package.");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public float getContrastLocked() {
        if (!this.mContrasts.contains(this.mCurrentUser)) {
            updateContrastLocked();
        }
        return this.mContrasts.get(this.mCurrentUser).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean updateContrastLocked() {
        float floatForUser = android.provider.Settings.Secure.getFloatForUser(getContext().getContentResolver(), "contrast_level", com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, this.mCurrentUser);
        if (java.lang.Math.abs(this.mContrasts.get(this.mCurrentUser, java.lang.Float.valueOf(Float.MAX_VALUE)).floatValue() - floatForUser) >= 1.0E-10d) {
            this.mContrasts.put(this.mCurrentUser, java.lang.Float.valueOf(floatForUser));
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ProjectionHolder implements android.os.IBinder.DeathRecipient {
        private final android.os.IBinder mBinder;
        private final java.lang.String mPackageName;
        private final com.android.server.UiModeManagerService.ProjectionHolder.ProjectionReleaser mProjectionReleaser;
        private final int mProjectionType;

        /* JADX INFO: Access modifiers changed from: private */
        interface ProjectionReleaser {
            boolean release(int i, @android.annotation.NonNull java.lang.String str);
        }

        private ProjectionHolder(java.lang.String str, int i, android.os.IBinder iBinder, com.android.server.UiModeManagerService.ProjectionHolder.ProjectionReleaser projectionReleaser) {
            this.mPackageName = str;
            this.mProjectionType = i;
            this.mBinder = iBinder;
            this.mProjectionReleaser = projectionReleaser;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean linkToDeath() {
            try {
                this.mBinder.linkToDeath(this, 0);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.UiModeManagerService.TAG, "linkToDeath failed for projection requester: " + this.mPackageName + ".", e);
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unlinkToDeath() {
            this.mBinder.unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Slog.w(com.android.server.UiModeManagerService.TAG, "Projection holder " + this.mPackageName + " died. Releasing projection type " + this.mProjectionType + ".");
            this.mProjectionReleaser.release(this.mProjectionType, this.mPackageName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assertLegit(@android.annotation.NonNull java.lang.String str) {
        if (!doesPackageHaveCallingUid(str)) {
            throw new java.lang.SecurityException("Caller claimed bogus packageName: " + str + ".");
        }
    }

    private boolean doesPackageHaveCallingUid(@android.annotation.NonNull java.lang.String str) {
        int callingUid = this.mInjector.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getContext().getPackageManager().getPackageUidAsUser(str, userId) == callingUid;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onProjectionStateChangedLocked(int i) {
        if (this.mProjectionListeners == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mProjectionListeners.size(); i2++) {
            int keyAt = this.mProjectionListeners.keyAt(i2);
            if ((i & keyAt) != 0) {
                android.os.RemoteCallbackList<android.app.IOnProjectionStateChangedListener> valueAt = this.mProjectionListeners.valueAt(i2);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int populateWithRelevantActivePackageNames = populateWithRelevantActivePackageNames(keyAt, arrayList);
                int beginBroadcast = valueAt.beginBroadcast();
                for (int i3 = 0; i3 < beginBroadcast; i3++) {
                    try {
                        valueAt.getBroadcastItem(i3).onProjectionStateChanged(populateWithRelevantActivePackageNames, arrayList);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed a call to onProjectionStateChanged().");
                    }
                }
                valueAt.finishBroadcast();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCustomTimeUpdated(int i) {
        persistNightMode(i);
        if (this.mNightMode.get() != 3) {
            return;
        }
        if (shouldApplyAutomaticChangesImmediately()) {
            unregisterDeviceInactiveListenerLocked();
            updateLocked(0, 0);
        } else {
            registerDeviceInactiveListenerLocked();
        }
    }

    void dumpImpl(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("Current UI Mode Service state:");
                printWriter.print("  mDockState=");
                printWriter.print(this.mDockState);
                printWriter.print(" mLastBroadcastState=");
                printWriter.println(this.mLastBroadcastState);
                printWriter.print(" mStartDreamImmediatelyOnDock=");
                printWriter.print(this.mStartDreamImmediatelyOnDock);
                printWriter.print("  mNightMode=");
                printWriter.print(this.mNightMode.get());
                printWriter.print(" (");
                printWriter.print(com.android.server.UiModeManagerService.Shell.nightModeToStr(this.mNightMode.get(), this.mNightModeCustomType));
                printWriter.print(") ");
                printWriter.print(" mOverrideOn/Off=");
                printWriter.print(this.mOverrideNightModeOn);
                printWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                printWriter.print(this.mOverrideNightModeOff);
                printWriter.print("  mAttentionModeThemeOverlay=");
                printWriter.print(this.mAttentionModeThemeOverlay);
                printWriter.print(" mNightModeLocked=");
                printWriter.println(this.mNightModeLocked);
                printWriter.print("  mCarModeEnabled=");
                printWriter.print(this.mCarModeEnabled);
                printWriter.print(" (carModeApps=");
                for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry : this.mCarModePackagePriority.entrySet()) {
                    printWriter.print(entry.getKey());
                    printWriter.print(":");
                    printWriter.print(entry.getValue());
                    printWriter.print(" ");
                }
                printWriter.println("");
                printWriter.print(" mWaitForDeviceInactive=");
                printWriter.print(this.mWaitForDeviceInactive);
                printWriter.print(" mComputedNightMode=");
                printWriter.print(this.mComputedNightMode);
                printWriter.print(" customStart=");
                printWriter.print(this.mCustomAutoNightModeStartMilliseconds);
                printWriter.print(" customEnd");
                printWriter.print(this.mCustomAutoNightModeEndMilliseconds);
                printWriter.print(" mCarModeEnableFlags=");
                printWriter.print(this.mCarModeEnableFlags);
                printWriter.print(" mEnableCarDockLaunch=");
                printWriter.println(this.mEnableCarDockLaunch);
                printWriter.print("  mCurUiMode=0x");
                printWriter.print(java.lang.Integer.toHexString(this.mCurUiMode));
                printWriter.print(" mUiModeLocked=");
                printWriter.print(this.mUiModeLocked);
                printWriter.print(" mSetUiMode=0x");
                printWriter.println(java.lang.Integer.toHexString(this.mSetUiMode));
                printWriter.print("  mHoldingConfiguration=");
                printWriter.print(this.mHoldingConfiguration);
                printWriter.print(" mSystemReady=");
                printWriter.println(this.mSystemReady);
                if (this.mTwilightManager != null) {
                    printWriter.print("  mTwilightService.getLastTwilightState()=");
                    printWriter.println(this.mTwilightManager.getLastTwilightState());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setCarModeLocked(boolean z, int i, int i2, java.lang.String str) {
        if (z) {
            enableCarMode(i2, str);
        } else {
            disableCarMode(i, i2, str);
        }
        boolean isCarModeEnabled = isCarModeEnabled();
        if (this.mCarModeEnabled != isCarModeEnabled) {
            this.mCarModeEnabled = isCarModeEnabled;
            if (!isCarModeEnabled) {
                android.content.Context context = getContext();
                updateNightModeFromSettingsLocked(context, context.getResources(), android.os.UserHandle.getCallingUserId());
            }
        }
        this.mCarModeEnableFlags = i;
    }

    private void disableCarMode(int i, int i2, java.lang.String str) {
        boolean z = (i & 2) != 0;
        if ((i2 == 0) || (this.mCarModePackagePriority.keySet().contains(java.lang.Integer.valueOf(i2)) && this.mCarModePackagePriority.get(java.lang.Integer.valueOf(i2)).equals(str)) || z) {
            android.util.Slog.d(TAG, "disableCarMode: disabling, priority=" + i2 + ", packageName=" + str);
            if (z) {
                android.util.ArraySet<java.util.Map.Entry> arraySet = new android.util.ArraySet(this.mCarModePackagePriority.entrySet());
                this.mCarModePackagePriority.clear();
                for (java.util.Map.Entry entry : arraySet) {
                    notifyCarModeDisabled(((java.lang.Integer) entry.getKey()).intValue(), (java.lang.String) entry.getValue());
                }
                return;
            }
            this.mCarModePackagePriority.remove(java.lang.Integer.valueOf(i2));
            notifyCarModeDisabled(i2, str);
        }
    }

    private void enableCarMode(int i, java.lang.String str) {
        boolean containsKey = this.mCarModePackagePriority.containsKey(java.lang.Integer.valueOf(i));
        boolean containsValue = this.mCarModePackagePriority.containsValue(str);
        if (!containsKey && !containsValue) {
            android.util.Slog.d(TAG, "enableCarMode: enabled at priority=" + i + ", packageName=" + str);
            this.mCarModePackagePriority.put(java.lang.Integer.valueOf(i), str);
            notifyCarModeEnabled(i, str);
            return;
        }
        android.util.Slog.d(TAG, "enableCarMode: car mode at priority " + i + " already enabled.");
    }

    private void notifyCarModeEnabled(int i, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent("android.app.action.ENTER_CAR_MODE_PRIORITIZED");
        intent.putExtra("android.app.extra.CALLING_PACKAGE", str);
        intent.putExtra("android.app.extra.PRIORITY", i);
        getContext().sendBroadcastAsUser(intent, android.os.UserHandle.ALL, "android.permission.HANDLE_CAR_MODE_CHANGES");
    }

    private void notifyCarModeDisabled(int i, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent("android.app.action.EXIT_CAR_MODE_PRIORITIZED");
        intent.putExtra("android.app.extra.CALLING_PACKAGE", str);
        intent.putExtra("android.app.extra.PRIORITY", i);
        getContext().sendBroadcastAsUser(intent, android.os.UserHandle.ALL, "android.permission.HANDLE_CAR_MODE_CHANGES");
    }

    private boolean isCarModeEnabled() {
        return this.mCarModePackagePriority.size() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDockState(int i) {
        synchronized (this.mLock) {
            try {
                if (i != this.mDockState) {
                    this.mDockState = i;
                    setCarModeLocked(this.mDockState == 2, 0, 0, "");
                    if (this.mSystemReady) {
                        updateLocked(1, 0);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static boolean isDeskDockState(int i) {
        switch (i) {
            case 1:
            case 3:
            case 4:
                return true;
            case 2:
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void persistNightMode(int i) {
        if (this.mCarModeEnabled || this.mCar) {
            return;
        }
        android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "ui_night_mode", this.mNightMode.get(), i);
        android.provider.Settings.Secure.putLongForUser(getContext().getContentResolver(), "ui_night_mode_custom_type", this.mNightModeCustomType, i);
        android.provider.Settings.Secure.putLongForUser(getContext().getContentResolver(), "dark_theme_custom_start_time", this.mCustomAutoNightModeStartMilliseconds.toNanoOfDay() / 1000, i);
        android.provider.Settings.Secure.putLongForUser(getContext().getContentResolver(), "dark_theme_custom_end_time", this.mCustomAutoNightModeEndMilliseconds.toNanoOfDay() / 1000, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void persistNightModeOverrides(int i) {
        if (this.mCarModeEnabled || this.mCar) {
            return;
        }
        android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "ui_night_mode_override_on", this.mOverrideNightModeOn ? 1 : 0, i);
        android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "ui_night_mode_override_off", this.mOverrideNightModeOff ? 1 : 0, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConfigurationLocked() {
        int computedUiModeConfiguration;
        int i = this.mDefaultUiModeType;
        if (!this.mUiModeLocked) {
            if (this.mTelevision) {
                i = 4;
            } else if (this.mWatch) {
                i = 6;
            } else if (this.mCarModeEnabled) {
                i = 3;
            } else if (isDeskDockState(this.mDockState)) {
                i = 2;
            } else if (this.mVrHeadset) {
                i = 7;
            }
        }
        if (this.mNightMode.get() == 2 || this.mNightMode.get() == 1) {
            updateComputedNightModeLocked(this.mNightMode.get() == 2);
        }
        if (this.mNightMode.get() == 0) {
            boolean z = this.mComputedNightMode;
            if (this.mTwilightManager != null) {
                this.mTwilightManager.registerListener(this.mTwilightListener, this.mHandler);
                com.android.server.twilight.TwilightState lastTwilightState = this.mTwilightManager.getLastTwilightState();
                z = lastTwilightState == null ? this.mComputedNightMode : lastTwilightState.isNight();
            }
            updateComputedNightModeLocked(z);
        } else if (this.mTwilightManager != null) {
            this.mTwilightManager.unregisterListener(this.mTwilightListener);
        }
        if (this.mNightMode.get() == 3) {
            if (this.mNightModeCustomType == 1) {
                updateComputedNightModeLocked(this.mLastBedtimeRequestedNightMode);
            } else {
                registerTimeChangeEvent();
                updateComputedNightModeLocked(computeCustomNightMode());
                scheduleNextCustomTimeListener();
            }
        } else {
            unregisterTimeChangeEvent();
        }
        if (this.mPowerSave && !this.mCarModeEnabled && !this.mCar) {
            computedUiModeConfiguration = (i & (-17)) | 32;
        } else {
            computedUiModeConfiguration = getComputedUiModeConfiguration(i);
        }
        this.mCurUiMode = computedUiModeConfiguration;
        if (this.mHoldingConfiguration) {
            return;
        }
        if (!this.mWaitForDeviceInactive || this.mPowerSave) {
            this.mConfiguration.uiMode = computedUiModeConfiguration;
        }
    }

    private int getComputedUiModeConfiguration(int i) {
        return (i | (this.mComputedNightMode ? 32 : 16)) & (this.mComputedNightMode ? -17 : -33);
    }

    private boolean computeCustomNightMode() {
        return android.util.TimeUtils.isTimeBetween(java.time.LocalTime.now(), this.mCustomAutoNightModeStartMilliseconds, this.mCustomAutoNightModeEndMilliseconds);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyConfigurationExternallyLocked() {
        if (this.mSetUiMode != this.mConfiguration.uiMode) {
            this.mSetUiMode = this.mConfiguration.uiMode;
            this.mWindowManager.clearSnapshotCache();
            try {
                android.app.ActivityTaskManager.getService().updateConfiguration(this.mConfiguration);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failure communicating with activity manager", e);
            } catch (java.lang.SecurityException e2) {
                android.util.Slog.e(TAG, "Activity does not have the ", e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldApplyAutomaticChangesImmediately() {
        return this.mCar || !this.mPowerManager.isInteractive() || this.mNightModeCustomType == 1 || this.mDreamManagerInternal.isDreaming();
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [java.time.ZonedDateTime] */
    private void scheduleNextCustomTimeListener() {
        java.time.LocalDateTime dateTimeAfter;
        cancelCustomAlarm();
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        if (computeCustomNightMode()) {
            dateTimeAfter = getDateTimeAfter(this.mCustomAutoNightModeEndMilliseconds, now);
        } else {
            dateTimeAfter = getDateTimeAfter(this.mCustomAutoNightModeStartMilliseconds, now);
        }
        this.mAlarmManager.setExact(1, dateTimeAfter.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli(), TAG, this.mCustomTimeListener, null);
    }

    private java.time.LocalDateTime getDateTimeAfter(java.time.LocalTime localTime, java.time.LocalDateTime localDateTime) {
        java.time.LocalDateTime of = java.time.LocalDateTime.of(localDateTime.toLocalDate(), localTime);
        return of.isBefore(localDateTime) ? of.plusDays(1L) : of;
    }

    void updateLocked(int i, int i2) {
        java.lang.String str;
        java.lang.String str2 = null;
        if (this.mLastBroadcastState == 2) {
            adjustStatusBarCarModeLocked();
            str = android.app.UiModeManager.ACTION_EXIT_CAR_MODE;
        } else if (!isDeskDockState(this.mLastBroadcastState)) {
            str = null;
        } else {
            str = android.app.UiModeManager.ACTION_EXIT_DESK_MODE;
        }
        boolean z = false;
        if (this.mCarModeEnabled) {
            if (this.mLastBroadcastState != 2) {
                adjustStatusBarCarModeLocked();
                if (str != null) {
                    sendForegroundBroadcastToAllUsers(str);
                }
                this.mLastBroadcastState = 2;
                str = android.app.UiModeManager.ACTION_ENTER_CAR_MODE;
            }
            str = null;
        } else if (isDeskDockState(this.mDockState)) {
            if (!isDeskDockState(this.mLastBroadcastState)) {
                if (str != null) {
                    sendForegroundBroadcastToAllUsers(str);
                }
                this.mLastBroadcastState = this.mDockState;
                str = android.app.UiModeManager.ACTION_ENTER_DESK_MODE;
            }
            str = null;
        } else {
            this.mLastBroadcastState = 0;
        }
        if (str != null) {
            android.content.Intent intent = new android.content.Intent(str);
            intent.putExtra("enableFlags", i);
            intent.putExtra("disableFlags", i2);
            intent.addFlags(268435456);
            getContext().sendOrderedBroadcastAsUser(intent, android.os.UserHandle.CURRENT, null, this.mResultReceiver, null, -1, null, null);
            this.mHoldingConfiguration = true;
            updateConfigurationLocked();
        } else {
            if (this.mCarModeEnabled) {
                if (this.mEnableCarDockLaunch && (i & 1) != 0) {
                    str2 = "android.intent.category.CAR_DOCK";
                }
            } else if (isDeskDockState(this.mDockState)) {
                if ((i & 1) != 0) {
                    str2 = "android.intent.category.DESK_DOCK";
                }
            } else if ((i2 & 1) != 0) {
                str2 = "android.intent.category.HOME";
            }
            sendConfigurationAndStartDreamOrDockAppLocked(str2);
        }
        if (this.mCharging && ((this.mCarModeEnabled && this.mCarModeKeepsScreenOn && (this.mCarModeEnableFlags & 2) == 0) || (this.mCurUiMode == 2 && this.mDeskModeKeepsScreenOn))) {
            z = true;
        }
        if (z != this.mWakeLock.isHeld()) {
            if (z) {
                this.mWakeLock.acquire();
            } else {
                this.mWakeLock.release();
            }
        }
    }

    private void sendForegroundBroadcastToAllUsers(java.lang.String str) {
        getContext().sendBroadcastAsUser(new android.content.Intent(str).addFlags(268435456), android.os.UserHandle.ALL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAfterBroadcastLocked(java.lang.String str, int i, int i2) {
        java.lang.String str2;
        if (android.app.UiModeManager.ACTION_ENTER_CAR_MODE.equals(str)) {
            if (this.mEnableCarDockLaunch && (i & 1) != 0) {
                str2 = "android.intent.category.CAR_DOCK";
            }
            str2 = null;
        } else if (android.app.UiModeManager.ACTION_ENTER_DESK_MODE.equals(str)) {
            if ((i & 1) != 0) {
                str2 = "android.intent.category.DESK_DOCK";
            }
            str2 = null;
        } else {
            if ((i2 & 1) != 0) {
                str2 = "android.intent.category.HOME";
            }
            str2 = null;
        }
        sendConfigurationAndStartDreamOrDockAppLocked(str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x009d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void sendConfigurationAndStartDreamOrDockAppLocked(java.lang.String str) {
        boolean z;
        android.content.Intent intent;
        this.mHoldingConfiguration = false;
        updateConfigurationLocked();
        if (str != null) {
            android.content.Intent buildHomeIntent = buildHomeIntent(str);
            if (android.service.dreams.Sandman.shouldStartDockApp(getContext(), buildHomeIntent)) {
                try {
                    intent = buildHomeIntent;
                    try {
                        int startActivityWithConfig = android.app.ActivityTaskManager.getService().startActivityWithConfig((android.app.IApplicationThread) null, getContext().getBasePackageName(), getContext().getAttributionTag(), buildHomeIntent, (java.lang.String) null, (android.os.IBinder) null, (java.lang.String) null, 0, 0, this.mConfiguration, (android.os.Bundle) null, -2);
                        if (android.app.ActivityManager.isStartResultSuccessful(startActivityWithConfig)) {
                            z = true;
                        } else {
                            if (startActivityWithConfig != -91) {
                                android.util.Slog.e(TAG, "Could not start dock app: " + intent + ", startActivityWithConfig result " + startActivityWithConfig);
                            }
                            z = false;
                        }
                    } catch (android.os.RemoteException e) {
                        e = e;
                        android.util.Slog.e(TAG, "Could not start dock app: " + intent, e);
                        z = false;
                        applyConfigurationExternallyLocked();
                        if (this.mDreamsDisabledByAmbientModeSuppression) {
                        }
                        if (str != null) {
                        }
                    }
                } catch (android.os.RemoteException e2) {
                    e = e2;
                    intent = buildHomeIntent;
                }
                applyConfigurationExternallyLocked();
                boolean z2 = !this.mDreamsDisabledByAmbientModeSuppression && this.mLocalPowerManager.isAmbientDisplaySuppressed();
                if (str != null && !z && !z2) {
                    if (this.mStartDreamImmediatelyOnDock || this.mWindowManager.isKeyguardShowingAndNotOccluded() || !this.mPowerManager.isInteractive()) {
                        this.mInjector.startDreamWhenDockedIfAppropriate(getContext());
                        return;
                    }
                    return;
                }
                return;
            }
        }
        z = false;
        applyConfigurationExternallyLocked();
        if (this.mDreamsDisabledByAmbientModeSuppression) {
        }
        if (str != null) {
        }
    }

    private void adjustStatusBarCarModeLocked() {
        int i;
        android.content.Context context = getContext();
        if (this.mStatusBarManager == null) {
            this.mStatusBarManager = (android.app.StatusBarManager) context.getSystemService("statusbar");
        }
        if (this.mStatusBarManager != null) {
            android.app.StatusBarManager statusBarManager = this.mStatusBarManager;
            if (this.mCarModeEnabled) {
                i = 524288;
            } else {
                i = 0;
            }
            statusBarManager.disable(i);
        }
        if (this.mNotificationManager == null) {
            this.mNotificationManager = (android.app.NotificationManager) context.getSystemService("notification");
        }
        if (this.mNotificationManager != null) {
            if (this.mCarModeEnabled) {
                this.mNotificationManager.notifyAsUser(null, 10, new android.app.Notification.Builder(context, com.android.internal.notification.SystemNotificationChannels.CAR_MODE).setSmallIcon(android.R.drawable.spinner_white_48).setDefaults(4).setOngoing(true).setWhen(0L).setColor(context.getColor(android.R.color.system_notification_accent_color)).setContentTitle(context.getString(android.R.string.capability_title_canRequestFilterKeyEvents)).setContentText(context.getString(android.R.string.capability_title_canPerformGestures)).setContentIntent(android.app.PendingIntent.getActivityAsUser(context, 0, new android.content.Intent(context, (java.lang.Class<?>) com.android.internal.app.DisableCarModeActivity.class), 33554432, null, android.os.UserHandle.CURRENT)).build(), android.os.UserHandle.ALL);
                return;
            }
            this.mNotificationManager.cancelAsUser(null, 10, android.os.UserHandle.ALL);
        }
    }

    private void updateComputedNightModeLocked(boolean z) {
        boolean z2 = false;
        if (this.mNightMode.get() != 2 && this.mNightMode.get() != 1) {
            if (this.mOverrideNightModeOn && !z) {
                z = true;
            } else if (this.mOverrideNightModeOff && z) {
                z = false;
            }
        }
        if (android.app.Flags.modesApi()) {
            switch (this.mAttentionModeThemeOverlay) {
                case 1001:
                    z2 = true;
                    break;
                case 1002:
                    break;
                default:
                    z2 = z;
                    break;
            }
            this.mComputedNightMode = z2;
        } else {
            this.mComputedNightMode = z;
        }
        if (this.mNightMode.get() != 0 || (this.mTwilightManager != null && this.mTwilightManager.getLastTwilightState() != null)) {
            resetNightModeOverrideLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean resetNightModeOverrideLocked() {
        if (!this.mOverrideNightModeOff && !this.mOverrideNightModeOn) {
            return false;
        }
        this.mOverrideNightModeOff = false;
        this.mOverrideNightModeOn = false;
        persistNightModeOverrides(this.mOverrideNightModeUser);
        this.mOverrideNightModeUser = 0;
        return true;
    }

    private void registerVrStateListener() {
        android.service.vr.IVrManager asInterface = android.service.vr.IVrManager.Stub.asInterface(android.os.ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                asInterface.registerListener(this.mVrStateCallbacks);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to register VR mode state listener: " + e);
            }
        }
    }

    private static class Shell extends android.os.ShellCommand {
        public static final java.lang.String NIGHT_MODE_STR_AUTO = "auto";
        public static final java.lang.String NIGHT_MODE_STR_CUSTOM_BEDTIME = "custom_bedtime";
        public static final java.lang.String NIGHT_MODE_STR_CUSTOM_SCHEDULE = "custom_schedule";
        public static final java.lang.String NIGHT_MODE_STR_NO = "no";
        public static final java.lang.String NIGHT_MODE_STR_UNKNOWN = "unknown";
        public static final java.lang.String NIGHT_MODE_STR_YES = "yes";
        private final android.app.IUiModeManager mInterface;

        Shell(android.app.IUiModeManager iUiModeManager) {
            this.mInterface = iUiModeManager;
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("UiModeManager service (uimode) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  night [yes|no|auto|custom_schedule|custom_bedtime]");
            outPrintWriter.println("    Set or read night mode.");
            outPrintWriter.println("  car [yes|no]");
            outPrintWriter.println("    Set or read car mode.");
            outPrintWriter.println("  time [start|end] <ISO time>");
            outPrintWriter.println("    Set custom start/end schedule time (night mode must be set to custom to apply).");
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int onCommand(java.lang.String str) {
            char c;
            if (str == null) {
                return handleDefaultCommands(str);
            }
            try {
                switch (str.hashCode()) {
                    case 98260:
                        if (str.equals("car")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3560141:
                        if (str.equals("time")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 104817688:
                        if (str.equals("night")) {
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
                }
            } catch (android.os.RemoteException e) {
                getErrPrintWriter().println("Remote exception: " + e);
                return -1;
            }
            return handleDefaultCommands(str);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private int handleCustomTime() throws android.os.RemoteException {
            boolean z;
            java.lang.String nextArg = getNextArg();
            if (nextArg == null) {
                printCustomTime();
                return 0;
            }
            switch (nextArg.hashCode()) {
                case 100571:
                    if (nextArg.equals("end")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 109757538:
                    if (nextArg.equals("start")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    this.mInterface.setCustomNightModeStart(com.android.server.UiModeManagerService.toMilliSeconds(java.time.LocalTime.parse(getNextArg())));
                    return 0;
                case true:
                    this.mInterface.setCustomNightModeEnd(com.android.server.UiModeManagerService.toMilliSeconds(java.time.LocalTime.parse(getNextArg())));
                    return 0;
                default:
                    getErrPrintWriter().println("command must be in [start|end]");
                    return -1;
            }
        }

        private void printCustomTime() throws android.os.RemoteException {
            getOutPrintWriter().println("start " + com.android.server.UiModeManagerService.fromMilliseconds(this.mInterface.getCustomNightModeStart()).toString());
            getOutPrintWriter().println("end " + com.android.server.UiModeManagerService.fromMilliseconds(this.mInterface.getCustomNightModeEnd()).toString());
        }

        private int handleNightMode() throws android.os.RemoteException {
            java.io.PrintWriter errPrintWriter = getErrPrintWriter();
            java.lang.String nextArg = getNextArg();
            if (nextArg == null) {
                printCurrentNightMode();
                return 0;
            }
            int strToNightMode = strToNightMode(nextArg);
            int strToNightModeCustomType = strToNightModeCustomType(nextArg);
            if (strToNightMode >= 0) {
                this.mInterface.setNightMode(strToNightMode);
                if (strToNightMode == 3) {
                    this.mInterface.setNightModeCustomType(strToNightModeCustomType);
                }
                printCurrentNightMode();
                return 0;
            }
            errPrintWriter.println("Error: mode must be 'yes', 'no', or 'auto', or 'custom_schedule', or 'custom_bedtime'");
            return -1;
        }

        private void printCurrentNightMode() throws android.os.RemoteException {
            getOutPrintWriter().println("Night mode: " + nightModeToStr(this.mInterface.getNightMode(), this.mInterface.getNightModeCustomType()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static java.lang.String nightModeToStr(int i, int i2) {
            switch (i) {
                case 0:
                    return NIGHT_MODE_STR_AUTO;
                case 1:
                    return NIGHT_MODE_STR_NO;
                case 2:
                    return NIGHT_MODE_STR_YES;
                case 3:
                    if (i2 == 0) {
                        return NIGHT_MODE_STR_CUSTOM_SCHEDULE;
                    }
                    if (i2 == 1) {
                        return NIGHT_MODE_STR_CUSTOM_BEDTIME;
                    }
                    return "unknown";
                default:
                    return "unknown";
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private static int strToNightMode(java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -757868544:
                    if (str.equals(NIGHT_MODE_STR_CUSTOM_BEDTIME)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 3521:
                    if (str.equals(NIGHT_MODE_STR_NO)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 119527:
                    if (str.equals(NIGHT_MODE_STR_YES)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3005871:
                    if (str.equals(NIGHT_MODE_STR_AUTO)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 164399013:
                    if (str.equals(NIGHT_MODE_STR_CUSTOM_SCHEDULE)) {
                        c = 3;
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
                    return 2;
                case 1:
                    return 1;
                case 2:
                    return 0;
                case 3:
                case 4:
                    return 3;
                default:
                    return -1;
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private static int strToNightModeCustomType(java.lang.String str) {
            boolean z;
            switch (str.hashCode()) {
                case -757868544:
                    if (str.equals(NIGHT_MODE_STR_CUSTOM_BEDTIME)) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 164399013:
                    if (str.equals(NIGHT_MODE_STR_CUSTOM_SCHEDULE)) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    return 1;
                case true:
                    return 0;
                default:
                    return -1;
            }
        }

        private int handleCarMode() throws android.os.RemoteException {
            java.io.PrintWriter errPrintWriter = getErrPrintWriter();
            java.lang.String nextArg = getNextArg();
            if (nextArg == null) {
                printCurrentCarMode();
                return 0;
            }
            if (nextArg.equals(NIGHT_MODE_STR_YES)) {
                this.mInterface.enableCarMode(0, 0, "");
                printCurrentCarMode();
                return 0;
            }
            if (nextArg.equals(NIGHT_MODE_STR_NO)) {
                this.mInterface.disableCarMode(0);
                printCurrentCarMode();
                return 0;
            }
            errPrintWriter.println("Error: mode must be 'yes', or 'no'");
            return -1;
        }

        private void printCurrentCarMode() throws android.os.RemoteException {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            int currentModeType = this.mInterface.getCurrentModeType();
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Car mode: ");
            sb.append(currentModeType == 3 ? NIGHT_MODE_STR_YES : NIGHT_MODE_STR_NO);
            outPrintWriter.println(sb.toString());
        }
    }

    public final class LocalService extends com.android.server.UiModeManagerInternal {
        public LocalService() {
        }

        @Override // com.android.server.UiModeManagerInternal
        public boolean isNightMode() {
            boolean z;
            synchronized (com.android.server.UiModeManagerService.this.mLock) {
                z = (com.android.server.UiModeManagerService.this.mConfiguration.uiMode & 32) != 0;
            }
            return z;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class Injector {
        public int getCallingUid() {
            return android.os.Binder.getCallingUid();
        }

        public void startDreamWhenDockedIfAppropriate(android.content.Context context) {
            android.service.dreams.Sandman.startDreamWhenDockedIfAppropriate(context);
        }
    }
}
