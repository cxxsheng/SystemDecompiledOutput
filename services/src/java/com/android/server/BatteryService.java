package com.android.server;

/* loaded from: classes.dex */
public final class BatteryService extends com.android.server.SystemService {
    private static final long BATTERY_LEVEL_CHANGE_THROTTLE_MS = 60000;
    private static final int BATTERY_PLUGGED_NONE = 0;
    private static final int BATTERY_SCALE = 100;
    private static final boolean DEBUG = false;
    private static final java.lang.String DUMPSYS_DATA_PATH = "/data/system/";
    private static final long HEALTH_HAL_WAIT_MS = 1000;
    private static final int MAX_BATTERY_LEVELS_QUEUE_SIZE = 100;
    private static final int MOD_TYPE_EMERGENCY = 3;
    private static final int MOD_TYPE_SUPPLEMENTAL = 2;
    static final int OPTION_FORCE_UPDATE = 1;
    private static java.lang.String sSystemUiPackage;
    private android.app.ActivityManagerInternal mActivityManagerInternal;
    private android.os.Bundle mBatteryChangedOptions;
    private boolean mBatteryInputSuspended;
    private boolean mBatteryLevelCritical;
    private boolean mBatteryLevelLow;
    private java.util.ArrayDeque<android.os.Bundle> mBatteryLevelsEventQueue;
    private motorola.hardware.health.V1_0.BatteryProperties mBatteryModProps;
    private int mBatteryNearlyFullLevel;
    private android.os.Bundle mBatteryOptions;
    private com.android.server.BatteryService.BatteryPropertiesRegistrar mBatteryPropertiesRegistrar;
    private final com.android.internal.app.IBatteryStats mBatteryStats;
    com.android.server.BatteryService.BinderService mBinderService;
    private int mChargeStartLevel;
    private long mChargeStartTime;
    private final java.util.concurrent.CopyOnWriteArraySet<android.os.BatteryManagerInternal.ChargingPolicyChangeListener> mChargingPolicyChangeListeners;
    private final android.content.Context mContext;
    private int mCriticalBatteryLevel;
    private int mDischargeStartLevel;
    private long mDischargeStartTime;
    private final android.os.Handler mHandler;
    private android.hardware.health.HealthInfo mHealthInfo;
    private com.android.server.health.HealthServiceWrapper mHealthServiceWrapper;
    private int mInvalidCharger;
    private int mLastBatteryCycleCount;
    private int mLastBatteryFullCharge;
    private int mLastBatteryFullChargeDesign;
    private int mLastBatteryHealth;
    private int mLastBatteryLevel;
    private long mLastBatteryLevelChangedSentMs;
    private boolean mLastBatteryLevelCritical;
    private boolean mLastBatteryPresent;
    private int mLastBatteryStatus;
    private int mLastBatteryTemperature;
    private int mLastBatteryVoltage;
    private int mLastChargeCounter;
    private int mLastChargingPolicy;
    private int mLastCharingState;
    private final android.hardware.health.HealthInfo mLastHealthInfo;
    private int mLastInvalidCharger;
    private int mLastLowBatteryWarningLevel;
    private int mLastMaxChargingCurrent;
    private int mLastMaxChargingVoltage;
    private int mLastModFlag;
    private int mLastModLevel;
    private int mLastModPowerSource;
    private int mLastModStatus;
    private int mLastModType;
    private int mLastPlugType;
    private com.android.server.BatteryService.Led mLed;
    private org.lineageos.internal.notification.LineageBatteryLights mLineageBatteryLights;
    private final java.lang.Object mLock;
    private int mLowBatteryCloseWarningLevel;
    private int mLowBatteryWarningLevel;
    private com.android.internal.logging.MetricsLogger mMetricsLogger;
    private motorola.hardware.health.V1_0.IMotHealth mMotHealthService;
    private int mPlugType;
    private android.os.Bundle mPowerOptions;
    private boolean mSentLowBatteryBroadcast;
    private int mSequence;
    private int mShutdownBatteryTemperature;
    private boolean mShutdownIfNoPower;
    private boolean mUpdatesStopped;
    private static final java.lang.String TAG = com.android.server.BatteryService.class.getSimpleName();
    private static final java.lang.String[] DUMPSYS_ARGS = {"--checkin", "--unplugged"};

    public BatteryService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mLastHealthInfo = new android.hardware.health.HealthInfo();
        this.mSequence = 1;
        this.mLastPlugType = -1;
        this.mSentLowBatteryBroadcast = false;
        this.mChargingPolicyChangeListeners = new java.util.concurrent.CopyOnWriteArraySet<>();
        this.mBatteryChangedOptions = android.app.BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle();
        this.mPowerOptions = android.app.BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeliveryGroupMatchingKey(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, "android.intent.action.ACTION_POWER_CONNECTED").setDeferralPolicy(2).toBundle();
        this.mBatteryOptions = android.app.BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeliveryGroupMatchingKey(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, "android.intent.action.BATTERY_OKAY").setDeferralPolicy(2).toBundle();
        this.mMotHealthService = null;
        this.mContext = context;
        this.mHandler = new android.os.Handler(true);
        this.mLed = new com.android.server.BatteryService.Led(context, (com.android.server.lights.LightsManager) getLocalService(com.android.server.lights.LightsManager.class));
        this.mBatteryStats = com.android.server.am.BatteryStatsService.getService();
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mCriticalBatteryLevel = this.mContext.getResources().getInteger(android.R.integer.config_chooser_max_targets_per_row);
        this.mLowBatteryWarningLevel = this.mContext.getResources().getInteger(android.R.integer.config_longPressOnPowerDurationMs);
        this.mLowBatteryCloseWarningLevel = this.mLowBatteryWarningLevel + this.mContext.getResources().getInteger(android.R.integer.config_longPressOnPowerBehavior);
        this.mShutdownBatteryTemperature = this.mContext.getResources().getInteger(android.R.integer.config_screen_rotation_total_90);
        this.mShutdownIfNoPower = this.mContext.getResources().getBoolean(android.R.bool.config_showPercentageTextDuringRebootToUpdate);
        sSystemUiPackage = this.mContext.getResources().getString(android.R.string.config_systemUi);
        this.mBatteryLevelsEventQueue = new java.util.ArrayDeque<>();
        this.mMetricsLogger = new com.android.internal.logging.MetricsLogger();
        if (new java.io.File("/sys/devices/virtual/switch/invalid_charger/state").exists()) {
            new android.os.UEventObserver() { // from class: com.android.server.BatteryService.1
                public void onUEvent(android.os.UEventObserver.UEvent uEvent) {
                    boolean equals = "1".equals(uEvent.get("SWITCH_STATE"));
                    synchronized (com.android.server.BatteryService.this.mLock) {
                        try {
                            if (com.android.server.BatteryService.this.mInvalidCharger != equals) {
                                com.android.server.BatteryService.this.mInvalidCharger = equals ? 1 : 0;
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            }.startObserving("DEVPATH=/devices/virtual/switch/invalid_charger");
        }
        this.mBatteryInputSuspended = ((java.lang.Boolean) android.sysprop.PowerProperties.battery_input_suspended().orElse(false)).booleanValue();
        this.mBatteryModProps = new motorola.hardware.health.V1_0.BatteryProperties();
        this.mBatteryModProps.modLevel = -1;
        this.mBatteryModProps.modStatus = 1;
        this.mBatteryModProps.modFlag = 0;
        this.mBatteryModProps.modType = 0;
        this.mBatteryModProps.modPowerSource = 0;
        try {
            this.mMotHealthService = motorola.hardware.health.V1_0.IMotHealth.getService();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "health: cannot get service. (RemoteException)");
        } catch (java.util.NoSuchElementException e2) {
            android.util.Slog.e(TAG, "mothealth: cannot get service. (no supported health HAL service)");
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        registerHealthCallback();
        this.mBinderService = new com.android.server.BatteryService.BinderService();
        publishBinderService("battery", this.mBinderService);
        this.mBatteryPropertiesRegistrar = new com.android.server.BatteryService.BatteryPropertiesRegistrar();
        publishBinderService("batteryproperties", this.mBatteryPropertiesRegistrar);
        publishLocalService(android.os.BatteryManagerInternal.class, new com.android.server.BatteryService.LocalService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 550) {
            synchronized (this.mLock) {
                this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("low_power_trigger_level"), false, new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.BatteryService.2
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        synchronized (com.android.server.BatteryService.this.mLock) {
                            com.android.server.BatteryService.this.updateBatteryWarningLevelLocked();
                        }
                    }
                }, -1);
                updateBatteryWarningLevelLocked();
            }
            return;
        }
        if (i == 1000) {
            this.mLineageBatteryLights = new org.lineageos.internal.notification.LineageBatteryLights(this.mContext, new org.lineageos.internal.notification.LineageBatteryLights.LedUpdater() { // from class: com.android.server.BatteryService.3
                public void update() {
                    com.android.server.BatteryService.this.updateLedPulse();
                }
            });
            updateLedPulse();
        }
    }

    private void registerHealthCallback() {
        traceBegin("HealthInitWrapper");
        try {
            try {
                this.mHealthServiceWrapper = com.android.server.health.HealthServiceWrapper.create(new com.android.server.health.HealthInfoCallback() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda6
                    @Override // com.android.server.health.HealthInfoCallback
                    public final void update(android.hardware.health.HealthInfo healthInfo) {
                        com.android.server.BatteryService.this.update(healthInfo);
                    }
                });
                traceEnd();
                traceBegin("HealthInitWaitUpdate");
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                synchronized (this.mLock) {
                    while (this.mHealthInfo == null) {
                        android.util.Slog.i(TAG, "health: Waited " + (android.os.SystemClock.uptimeMillis() - uptimeMillis) + "ms for callbacks. Waiting another 1000 ms...");
                        try {
                            this.mLock.wait(1000L);
                        } catch (java.lang.InterruptedException e) {
                            android.util.Slog.i(TAG, "health: InterruptedException when waiting for update.  Continuing...");
                        }
                    }
                }
                android.util.Slog.i(TAG, "health: Waited " + (android.os.SystemClock.uptimeMillis() - uptimeMillis) + "ms and received the update.");
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "health: cannot register callback. (RemoteException)");
                throw e2.rethrowFromSystemServer();
            } catch (java.util.NoSuchElementException e3) {
                android.util.Slog.e(TAG, "health: cannot register callback. (no supported health HAL service)");
                throw e3;
            }
        } finally {
            traceEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBatteryWarningLevelLocked() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        int integer = this.mContext.getResources().getInteger(android.R.integer.config_longPressOnPowerDurationMs);
        this.mLastLowBatteryWarningLevel = this.mLowBatteryWarningLevel;
        this.mLowBatteryWarningLevel = android.provider.Settings.Global.getInt(contentResolver, "low_power_trigger_level", integer);
        if (this.mLowBatteryWarningLevel == 0) {
            this.mLowBatteryWarningLevel = integer;
        }
        if (this.mLowBatteryWarningLevel < this.mCriticalBatteryLevel) {
            this.mLowBatteryWarningLevel = this.mCriticalBatteryLevel;
        }
        this.mLowBatteryCloseWarningLevel = this.mLowBatteryWarningLevel + this.mContext.getResources().getInteger(android.R.integer.config_longPressOnPowerBehavior);
        lambda$setChargerAcOnline$1(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPoweredLocked(int i) {
        if (this.mHealthInfo.batteryStatus == 1) {
            return true;
        }
        if ((i & 1) != 0 && this.mHealthInfo.chargerAcOnline) {
            return true;
        }
        if ((i & 2) != 0 && this.mHealthInfo.chargerUsbOnline) {
            return true;
        }
        if ((i & 4) != 0 && this.mHealthInfo.chargerWirelessOnline) {
            return true;
        }
        int i2 = i & 8;
        if (i2 == 0 || !this.mHealthInfo.chargerDockOnline) {
            return i2 != 0 && supplementalOrEmergencyModOnline() && isModBatteryActive();
        }
        return true;
    }

    private boolean shouldSendBatteryLowLocked() {
        boolean z = this.mPlugType != 0;
        boolean z2 = this.mLastPlugType != 0;
        if (z || this.mHealthInfo.batteryStatus == 1 || this.mHealthInfo.batteryLevel > this.mLowBatteryWarningLevel) {
            return false;
        }
        return z2 || this.mLastBatteryLevel > this.mLowBatteryWarningLevel || this.mHealthInfo.batteryLevel > this.mLastLowBatteryWarningLevel;
    }

    private boolean shouldShutdownLocked() {
        return this.mHealthInfo.batteryCapacityLevel != -1 ? this.mHealthInfo.batteryCapacityLevel == 1 : this.mShutdownIfNoPower && this.mHealthInfo.batteryLevel <= 0 && this.mHealthInfo.batteryPresent && this.mHealthInfo.batteryStatus != 2;
    }

    private void shutdownIfNoPowerLocked() {
        if (shouldShutdownLocked()) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.BatteryService.4
                @Override // java.lang.Runnable
                public void run() {
                    if (com.android.server.BatteryService.this.mActivityManagerInternal.isSystemReady()) {
                        android.content.Intent intent = new android.content.Intent("com.android.internal.intent.action.REQUEST_SHUTDOWN");
                        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
                        intent.putExtra("android.intent.extra.REASON", "battery");
                        intent.setFlags(268435456);
                        com.android.server.BatteryService.this.mContext.startActivityAsUser(intent, android.os.UserHandle.CURRENT);
                    }
                }
            });
        }
    }

    private void shutdownIfOverTempLocked() {
        if (this.mHealthInfo.batteryTemperatureTenthsCelsius > this.mShutdownBatteryTemperature) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.BatteryService.5
                @Override // java.lang.Runnable
                public void run() {
                    if (com.android.server.BatteryService.this.mActivityManagerInternal.isSystemReady()) {
                        android.content.Intent intent = new android.content.Intent("com.android.internal.intent.action.REQUEST_SHUTDOWN");
                        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
                        intent.putExtra("android.intent.extra.REASON", "thermal,battery");
                        intent.setFlags(268435456);
                        com.android.server.BatteryService.this.mContext.startActivityAsUser(intent, android.os.UserHandle.CURRENT);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update(android.hardware.health.HealthInfo healthInfo) {
        traceBegin("HealthInfoUpdate");
        android.os.Trace.traceCounter(131072L, "BatteryChargeCounter", healthInfo.batteryChargeCounterUah);
        android.os.Trace.traceCounter(131072L, "BatteryCurrent", healthInfo.batteryCurrentMicroamps);
        android.os.Trace.traceCounter(131072L, "PlugType", plugType(healthInfo));
        android.os.Trace.traceCounter(131072L, "BatteryStatus", healthInfo.batteryStatus);
        synchronized (this.mLock) {
            if (!this.mUpdatesStopped) {
                this.mHealthInfo = healthInfo;
                if (this.mMotHealthService != null) {
                    try {
                        this.mBatteryModProps = this.mMotHealthService.getModBatteryProperties();
                        if (this.mBatteryModProps.modFlag > 0) {
                            this.mHealthInfo.batteryLevel = this.mBatteryModProps.batteryLevel;
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "getModBatteryProperties fail!");
                    }
                }
                lambda$setChargerAcOnline$1(false);
                this.mLock.notifyAll();
            } else {
                com.android.server.health.Utils.copyV1Battery(this.mLastHealthInfo, healthInfo);
            }
        }
        traceEnd();
    }

    private int plugType(android.hardware.health.HealthInfo healthInfo) {
        if (healthInfo.chargerAcOnline) {
            return 1;
        }
        if (healthInfo.chargerUsbOnline) {
            return 2;
        }
        if (healthInfo.chargerWirelessOnline) {
            return 4;
        }
        return (healthInfo.chargerDockOnline || supplementalOrEmergencyModOnline()) ? 8 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0306  */
    /* renamed from: processValuesLocked, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void lambda$setChargerAcOnline$1(boolean z) {
        boolean z2;
        long j;
        boolean z3;
        this.mBatteryLevelCritical = this.mHealthInfo.batteryStatus != 1 && this.mHealthInfo.batteryLevel <= this.mCriticalBatteryLevel;
        this.mPlugType = plugType(this.mHealthInfo);
        try {
            this.mBatteryStats.setBatteryState(this.mHealthInfo.batteryStatus, this.mHealthInfo.batteryHealth, maybeTranslatePlugType(this.mPlugType), this.mHealthInfo.batteryLevel, this.mHealthInfo.batteryTemperatureTenthsCelsius, this.mHealthInfo.batteryVoltageMillivolts, this.mHealthInfo.batteryChargeCounterUah, this.mHealthInfo.batteryFullChargeUah, this.mHealthInfo.batteryChargeTimeToFullNowSeconds);
        } catch (android.os.RemoteException e) {
        }
        shutdownIfNoPowerLocked();
        shutdownIfOverTempLocked();
        if (z || this.mHealthInfo.chargingPolicy != this.mLastChargingPolicy) {
            this.mLastChargingPolicy = this.mHealthInfo.chargingPolicy;
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.BatteryService.this.notifyChargingPolicyChanged();
                }
            });
        }
        if (z || this.mHealthInfo.batteryStatus != this.mLastBatteryStatus || this.mHealthInfo.batteryHealth != this.mLastBatteryHealth || this.mHealthInfo.batteryPresent != this.mLastBatteryPresent || this.mHealthInfo.batteryLevel != this.mLastBatteryLevel || this.mPlugType != this.mLastPlugType || this.mHealthInfo.batteryVoltageMillivolts != this.mLastBatteryVoltage || this.mHealthInfo.batteryTemperatureTenthsCelsius != this.mLastBatteryTemperature || this.mHealthInfo.maxChargingCurrentMicroamps != this.mLastMaxChargingCurrent || this.mHealthInfo.maxChargingVoltageMicrovolts != this.mLastMaxChargingVoltage || this.mHealthInfo.batteryChargeCounterUah != this.mLastChargeCounter || this.mInvalidCharger != this.mLastInvalidCharger || this.mHealthInfo.batteryFullChargeUah != this.mLastBatteryFullCharge || this.mHealthInfo.batteryFullChargeDesignCapacityUah != this.mLastBatteryFullChargeDesign || this.mHealthInfo.batteryCycleCount != this.mLastBatteryCycleCount || this.mBatteryModProps.modLevel != this.mLastModLevel || this.mBatteryModProps.modStatus != this.mLastModStatus || this.mBatteryModProps.modFlag != this.mLastModFlag || this.mBatteryModProps.modType != this.mLastModType || this.mBatteryModProps.modPowerSource != this.mLastModPowerSource || this.mHealthInfo.chargingState != this.mLastCharingState) {
            if (this.mPlugType != this.mLastPlugType) {
                if (this.mLastPlugType == 0) {
                    this.mChargeStartLevel = this.mHealthInfo.batteryLevel;
                    this.mChargeStartTime = android.os.SystemClock.elapsedRealtime();
                    android.metrics.LogMaker logMaker = new android.metrics.LogMaker(1417);
                    logMaker.setType(4);
                    logMaker.addTaggedData(1421, java.lang.Integer.valueOf(this.mPlugType));
                    logMaker.addTaggedData(1418, java.lang.Integer.valueOf(this.mHealthInfo.batteryLevel));
                    this.mMetricsLogger.write(logMaker);
                    if (this.mDischargeStartTime != 0 && this.mDischargeStartLevel != this.mHealthInfo.batteryLevel) {
                        j = android.os.SystemClock.elapsedRealtime() - this.mDischargeStartTime;
                        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BATTERY_DISCHARGE, java.lang.Long.valueOf(j), java.lang.Integer.valueOf(this.mDischargeStartLevel), java.lang.Integer.valueOf(this.mHealthInfo.batteryLevel));
                        this.mDischargeStartTime = 0L;
                        z2 = true;
                    } else {
                        z2 = false;
                        j = 0;
                    }
                    if (this.mHealthInfo.batteryStatus == this.mLastBatteryStatus || this.mHealthInfo.batteryHealth != this.mLastBatteryHealth || this.mHealthInfo.batteryPresent != this.mLastBatteryPresent || this.mPlugType != this.mLastPlugType) {
                        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BATTERY_STATUS, java.lang.Integer.valueOf(this.mHealthInfo.batteryStatus), java.lang.Integer.valueOf(this.mHealthInfo.batteryHealth), java.lang.Integer.valueOf(this.mHealthInfo.batteryPresent ? 1 : 0), java.lang.Integer.valueOf(this.mPlugType), this.mHealthInfo.batteryTechnology);
                        android.os.SystemProperties.set("debug.tracing.battery_status", java.lang.Integer.toString(this.mHealthInfo.batteryStatus));
                        android.os.SystemProperties.set("debug.tracing.plug_type", java.lang.Integer.toString(this.mPlugType));
                    }
                    if (this.mHealthInfo.batteryLevel != this.mLastBatteryLevel) {
                        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BATTERY_LEVEL, java.lang.Integer.valueOf(this.mHealthInfo.batteryLevel), java.lang.Integer.valueOf(this.mHealthInfo.batteryVoltageMillivolts), java.lang.Integer.valueOf(this.mHealthInfo.batteryTemperatureTenthsCelsius));
                    }
                    z3 = z2;
                    if (this.mBatteryLevelCritical) {
                        z3 = z2;
                        if (!this.mLastBatteryLevelCritical) {
                            z3 = z2;
                            if (this.mPlugType == 0) {
                                j = android.os.SystemClock.elapsedRealtime() - this.mDischargeStartTime;
                                z3 = true;
                            }
                        }
                    }
                    if (this.mBatteryLevelLow) {
                        if (this.mPlugType == 0 && this.mHealthInfo.batteryStatus != 1 && this.mHealthInfo.batteryLevel <= this.mLowBatteryWarningLevel) {
                            this.mBatteryLevelLow = true;
                        }
                    } else if (this.mPlugType != 0) {
                        this.mBatteryLevelLow = false;
                    } else if (this.mHealthInfo.batteryLevel >= this.mLowBatteryCloseWarningLevel) {
                        this.mBatteryLevelLow = false;
                    } else if (z && this.mHealthInfo.batteryLevel >= this.mLowBatteryWarningLevel) {
                        this.mBatteryLevelLow = false;
                    }
                    this.mSequence++;
                    if (this.mPlugType == 0 && this.mLastPlugType == 0) {
                        final android.content.Intent intent = new android.content.Intent("android.intent.action.ACTION_POWER_CONNECTED");
                        intent.setFlags(67108864);
                        intent.putExtra(com.android.server.storage.DeviceStorageMonitorService.EXTRA_SEQUENCE, this.mSequence);
                        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.BatteryService.6
                            @Override // java.lang.Runnable
                            public void run() {
                                com.android.server.BatteryService.this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL, null, com.android.server.BatteryService.this.mPowerOptions);
                            }
                        });
                    } else if (this.mPlugType == 0 && this.mLastPlugType != 0) {
                        final android.content.Intent intent2 = new android.content.Intent("android.intent.action.ACTION_POWER_DISCONNECTED");
                        intent2.setFlags(67108864);
                        intent2.putExtra(com.android.server.storage.DeviceStorageMonitorService.EXTRA_SEQUENCE, this.mSequence);
                        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.BatteryService.7
                            @Override // java.lang.Runnable
                            public void run() {
                                com.android.server.BatteryService.this.mContext.sendBroadcastAsUser(intent2, android.os.UserHandle.ALL, null, com.android.server.BatteryService.this.mPowerOptions);
                            }
                        });
                    }
                    if (!shouldSendBatteryLowLocked()) {
                        this.mSentLowBatteryBroadcast = true;
                        final android.content.Intent intent3 = new android.content.Intent("android.intent.action.BATTERY_LOW");
                        intent3.setFlags(67108864);
                        intent3.putExtra(com.android.server.storage.DeviceStorageMonitorService.EXTRA_SEQUENCE, this.mSequence);
                        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.BatteryService.8
                            @Override // java.lang.Runnable
                            public void run() {
                                com.android.server.BatteryService.this.mContext.sendBroadcastAsUser(intent3, android.os.UserHandle.ALL, null, com.android.server.BatteryService.this.mBatteryOptions);
                            }
                        });
                    } else if (this.mSentLowBatteryBroadcast && this.mHealthInfo.batteryLevel >= this.mLowBatteryCloseWarningLevel) {
                        this.mSentLowBatteryBroadcast = false;
                        final android.content.Intent intent4 = new android.content.Intent("android.intent.action.BATTERY_OKAY");
                        intent4.setFlags(67108864);
                        intent4.putExtra(com.android.server.storage.DeviceStorageMonitorService.EXTRA_SEQUENCE, this.mSequence);
                        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.BatteryService.9
                            @Override // java.lang.Runnable
                            public void run() {
                                com.android.server.BatteryService.this.mContext.sendBroadcastAsUser(intent4, android.os.UserHandle.ALL, null, com.android.server.BatteryService.this.mBatteryOptions);
                            }
                        });
                    }
                    sendBatteryChangedIntentLocked();
                    if (this.mLastBatteryLevel == this.mHealthInfo.batteryLevel || this.mLastPlugType != this.mPlugType) {
                        sendBatteryLevelChangedIntentLocked();
                    }
                    this.mLed.updateLightsLocked();
                    if (z3 && j != 0) {
                        logOutlierLocked(j);
                    }
                    this.mLastBatteryStatus = this.mHealthInfo.batteryStatus;
                    this.mLastBatteryHealth = this.mHealthInfo.batteryHealth;
                    this.mLastBatteryPresent = this.mHealthInfo.batteryPresent;
                    this.mLastBatteryLevel = this.mHealthInfo.batteryLevel;
                    this.mLastPlugType = this.mPlugType;
                    this.mLastBatteryVoltage = this.mHealthInfo.batteryVoltageMillivolts;
                    this.mLastBatteryTemperature = this.mHealthInfo.batteryTemperatureTenthsCelsius;
                    this.mLastMaxChargingCurrent = this.mHealthInfo.maxChargingCurrentMicroamps;
                    this.mLastMaxChargingVoltage = this.mHealthInfo.maxChargingVoltageMicrovolts;
                    this.mLastChargeCounter = this.mHealthInfo.batteryChargeCounterUah;
                    this.mLastBatteryLevelCritical = this.mBatteryLevelCritical;
                    this.mLastInvalidCharger = this.mInvalidCharger;
                    this.mLastBatteryCycleCount = this.mHealthInfo.batteryCycleCount;
                    this.mLastCharingState = this.mHealthInfo.chargingState;
                    this.mLastBatteryFullCharge = this.mHealthInfo.batteryFullChargeUah;
                    this.mLastBatteryFullChargeDesign = this.mHealthInfo.batteryFullChargeDesignCapacityUah;
                    this.mLastModLevel = this.mBatteryModProps.modLevel;
                    this.mLastModStatus = this.mBatteryModProps.modStatus;
                    this.mLastModFlag = this.mBatteryModProps.modFlag;
                    this.mLastModType = this.mBatteryModProps.modType;
                    this.mLastModPowerSource = this.mBatteryModProps.modPowerSource;
                }
                if (this.mPlugType == 0) {
                    this.mDischargeStartTime = android.os.SystemClock.elapsedRealtime();
                    this.mDischargeStartLevel = this.mHealthInfo.batteryLevel;
                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mChargeStartTime;
                    if (this.mChargeStartTime != 0 && elapsedRealtime != 0) {
                        android.metrics.LogMaker logMaker2 = new android.metrics.LogMaker(1417);
                        logMaker2.setType(5);
                        logMaker2.addTaggedData(1421, java.lang.Integer.valueOf(this.mLastPlugType));
                        logMaker2.addTaggedData(1420, java.lang.Long.valueOf(elapsedRealtime));
                        logMaker2.addTaggedData(1418, java.lang.Integer.valueOf(this.mChargeStartLevel));
                        logMaker2.addTaggedData(1419, java.lang.Integer.valueOf(this.mHealthInfo.batteryLevel));
                        this.mMetricsLogger.write(logMaker2);
                    }
                    this.mChargeStartTime = 0L;
                }
            }
            z2 = false;
            j = 0;
            if (this.mHealthInfo.batteryStatus == this.mLastBatteryStatus) {
            }
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.BATTERY_STATUS, java.lang.Integer.valueOf(this.mHealthInfo.batteryStatus), java.lang.Integer.valueOf(this.mHealthInfo.batteryHealth), java.lang.Integer.valueOf(this.mHealthInfo.batteryPresent ? 1 : 0), java.lang.Integer.valueOf(this.mPlugType), this.mHealthInfo.batteryTechnology);
            android.os.SystemProperties.set("debug.tracing.battery_status", java.lang.Integer.toString(this.mHealthInfo.batteryStatus));
            android.os.SystemProperties.set("debug.tracing.plug_type", java.lang.Integer.toString(this.mPlugType));
            if (this.mHealthInfo.batteryLevel != this.mLastBatteryLevel) {
            }
            z3 = z2;
            if (this.mBatteryLevelCritical) {
            }
            if (this.mBatteryLevelLow) {
            }
            this.mSequence++;
            if (this.mPlugType == 0) {
            }
            if (this.mPlugType == 0) {
                final android.content.Intent intent22 = new android.content.Intent("android.intent.action.ACTION_POWER_DISCONNECTED");
                intent22.setFlags(67108864);
                intent22.putExtra(com.android.server.storage.DeviceStorageMonitorService.EXTRA_SEQUENCE, this.mSequence);
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.BatteryService.7
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.BatteryService.this.mContext.sendBroadcastAsUser(intent22, android.os.UserHandle.ALL, null, com.android.server.BatteryService.this.mPowerOptions);
                    }
                });
            }
            if (!shouldSendBatteryLowLocked()) {
            }
            sendBatteryChangedIntentLocked();
            if (this.mLastBatteryLevel == this.mHealthInfo.batteryLevel) {
            }
            sendBatteryLevelChangedIntentLocked();
            this.mLed.updateLightsLocked();
            if (z3) {
                logOutlierLocked(j);
            }
            this.mLastBatteryStatus = this.mHealthInfo.batteryStatus;
            this.mLastBatteryHealth = this.mHealthInfo.batteryHealth;
            this.mLastBatteryPresent = this.mHealthInfo.batteryPresent;
            this.mLastBatteryLevel = this.mHealthInfo.batteryLevel;
            this.mLastPlugType = this.mPlugType;
            this.mLastBatteryVoltage = this.mHealthInfo.batteryVoltageMillivolts;
            this.mLastBatteryTemperature = this.mHealthInfo.batteryTemperatureTenthsCelsius;
            this.mLastMaxChargingCurrent = this.mHealthInfo.maxChargingCurrentMicroamps;
            this.mLastMaxChargingVoltage = this.mHealthInfo.maxChargingVoltageMicrovolts;
            this.mLastChargeCounter = this.mHealthInfo.batteryChargeCounterUah;
            this.mLastBatteryLevelCritical = this.mBatteryLevelCritical;
            this.mLastInvalidCharger = this.mInvalidCharger;
            this.mLastBatteryCycleCount = this.mHealthInfo.batteryCycleCount;
            this.mLastCharingState = this.mHealthInfo.chargingState;
            this.mLastBatteryFullCharge = this.mHealthInfo.batteryFullChargeUah;
            this.mLastBatteryFullChargeDesign = this.mHealthInfo.batteryFullChargeDesignCapacityUah;
            this.mLastModLevel = this.mBatteryModProps.modLevel;
            this.mLastModStatus = this.mBatteryModProps.modStatus;
            this.mLastModFlag = this.mBatteryModProps.modFlag;
            this.mLastModType = this.mBatteryModProps.modType;
            this.mLastModPowerSource = this.mBatteryModProps.modPowerSource;
        }
    }

    private void sendBatteryChangedIntentLocked() {
        final android.content.Intent intent = new android.content.Intent("android.intent.action.BATTERY_CHANGED");
        intent.addFlags(com.android.server.tare.JobSchedulerEconomicPolicy.ACTION_JOB_MAX_START);
        int iconLocked = getIconLocked(this.mHealthInfo.batteryLevel);
        intent.putExtra(com.android.server.storage.DeviceStorageMonitorService.EXTRA_SEQUENCE, this.mSequence);
        intent.putExtra("status", this.mHealthInfo.batteryStatus);
        intent.putExtra("health", this.mHealthInfo.batteryHealth);
        intent.putExtra("present", this.mHealthInfo.batteryPresent);
        intent.putExtra("level", this.mHealthInfo.batteryLevel);
        intent.putExtra("battery_low", this.mSentLowBatteryBroadcast);
        intent.putExtra("scale", 100);
        intent.putExtra("icon-small", iconLocked);
        intent.putExtra("plugged", maybeTranslatePlugType(this.mPlugType));
        intent.putExtra("voltage", this.mHealthInfo.batteryVoltageMillivolts);
        intent.putExtra("temperature", this.mHealthInfo.batteryTemperatureTenthsCelsius);
        intent.putExtra("technology", this.mHealthInfo.batteryTechnology);
        intent.putExtra("invalid_charger", this.mInvalidCharger);
        intent.putExtra("max_charging_current", this.mHealthInfo.maxChargingCurrentMicroamps);
        intent.putExtra("max_charging_voltage", this.mHealthInfo.maxChargingVoltageMicrovolts);
        intent.putExtra("charge_counter", this.mHealthInfo.batteryChargeCounterUah);
        intent.putExtra("android.os.extra.CYCLE_COUNT", this.mHealthInfo.batteryCycleCount);
        intent.putExtra("android.os.extra.CHARGING_STATUS", this.mHealthInfo.chargingState);
        intent.putExtra("android.os.extra.MAXIMUM_CAPACITY", this.mHealthInfo.batteryFullChargeUah);
        intent.putExtra("android.os.extra.DESIGN_CAPACITY", this.mHealthInfo.batteryFullChargeDesignCapacityUah);
        intent.putExtra("mod_level", this.mBatteryModProps.modLevel);
        intent.putExtra("mod_status", this.mBatteryModProps.modStatus);
        intent.putExtra("mod_flag", this.mBatteryModProps.modFlag);
        intent.putExtra("plugged_raw", this.mPlugType);
        intent.putExtra("mod_type", this.mBatteryModProps.modType);
        intent.putExtra("mod_psrc", this.mBatteryModProps.modPowerSource);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.BatteryService.this.lambda$sendBatteryChangedIntentLocked$0(intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendBatteryChangedIntentLocked$0(android.content.Intent intent) {
        broadcastBatteryChangedIntent(intent, this.mBatteryChangedOptions);
    }

    private static void broadcastBatteryChangedIntent(android.content.Intent intent, android.os.Bundle bundle) {
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.addFlags(268435456);
        intent2.setPackage(sSystemUiPackage);
        android.app.ActivityManager.broadcastStickyIntent(intent2, -1, bundle, -1);
        android.app.ActivityManager.broadcastStickyIntent(intent, new java.lang.String[]{sSystemUiPackage}, -1, bundle, -1);
    }

    private void sendBatteryLevelChangedIntentLocked() {
        android.os.Bundle bundle = new android.os.Bundle();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        bundle.putInt(com.android.server.storage.DeviceStorageMonitorService.EXTRA_SEQUENCE, this.mSequence);
        bundle.putInt("status", this.mHealthInfo.batteryStatus);
        bundle.putInt("health", this.mHealthInfo.batteryHealth);
        bundle.putBoolean("present", this.mHealthInfo.batteryPresent);
        bundle.putInt("level", this.mHealthInfo.batteryLevel);
        bundle.putBoolean("battery_low", this.mSentLowBatteryBroadcast);
        bundle.putInt("scale", 100);
        bundle.putInt("plugged", this.mPlugType);
        bundle.putInt("voltage", this.mHealthInfo.batteryVoltageMillivolts);
        bundle.putInt("temperature", this.mHealthInfo.batteryTemperatureTenthsCelsius);
        bundle.putInt("charge_counter", this.mHealthInfo.batteryChargeCounterUah);
        bundle.putLong("android.os.extra.EVENT_TIMESTAMP", elapsedRealtime);
        bundle.putInt("android.os.extra.CYCLE_COUNT", this.mHealthInfo.batteryCycleCount);
        bundle.putInt("android.os.extra.CHARGING_STATUS", this.mHealthInfo.chargingState);
        bundle.putInt("android.os.extra.MAXIMUM_CAPACITY", this.mHealthInfo.batteryFullChargeUah);
        bundle.putInt("android.os.extra.DESIGN_CAPACITY", this.mHealthInfo.batteryFullChargeDesignCapacityUah);
        boolean isEmpty = this.mBatteryLevelsEventQueue.isEmpty();
        this.mBatteryLevelsEventQueue.add(bundle);
        if (this.mBatteryLevelsEventQueue.size() > 100) {
            this.mBatteryLevelsEventQueue.removeFirst();
        }
        if (isEmpty) {
            this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.BatteryService.this.sendEnqueuedBatteryLevelChangedEvents();
                }
            }, elapsedRealtime - this.mLastBatteryLevelChangedSentMs > 60000 ? 0L : (this.mLastBatteryLevelChangedSentMs + 60000) - elapsedRealtime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEnqueuedBatteryLevelChangedEvents() {
        java.util.ArrayList<? extends android.os.Parcelable> arrayList;
        synchronized (this.mLock) {
            arrayList = new java.util.ArrayList<>(this.mBatteryLevelsEventQueue);
            this.mBatteryLevelsEventQueue.clear();
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.BATTERY_LEVEL_CHANGED");
        intent.addFlags(16777216);
        intent.putParcelableArrayListExtra("android.os.extra.EVENTS", arrayList);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL, "android.permission.BATTERY_STATS");
        this.mLastBatteryLevelChangedSentMs = android.os.SystemClock.elapsedRealtime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyChargingPolicyChanged() {
        int i;
        synchronized (this.mLock) {
            i = this.mLastChargingPolicy;
        }
        java.util.Iterator<android.os.BatteryManagerInternal.ChargingPolicyChangeListener> it = this.mChargingPolicyChangeListeners.iterator();
        while (it.hasNext()) {
            it.next().onChargingPolicyChanged(i);
        }
    }

    private void logBatteryStatsLocked() {
        android.os.DropBoxManager dropBoxManager;
        java.io.File file;
        java.lang.String str;
        java.lang.StringBuilder sb;
        android.os.IBinder service = android.os.ServiceManager.getService("batterystats");
        if (service == null || (dropBoxManager = (android.os.DropBoxManager) this.mContext.getSystemService("dropbox")) == null || !dropBoxManager.isTagEnabled("BATTERY_DISCHARGE_INFO")) {
            return;
        }
        java.io.FileOutputStream fileOutputStream = null;
        try {
            try {
                file = new java.io.File("/data/system/batterystats.dump");
                try {
                    java.io.FileOutputStream fileOutputStream2 = new java.io.FileOutputStream(file);
                    try {
                        service.dump(fileOutputStream2.getFD(), DUMPSYS_ARGS);
                        android.os.FileUtils.sync(fileOutputStream2);
                        dropBoxManager.addFile("BATTERY_DISCHARGE_INFO", file, 2);
                        try {
                            fileOutputStream2.close();
                        } catch (java.io.IOException e) {
                            android.util.Slog.e(TAG, "failed to close dumpsys output stream");
                        }
                    } catch (android.os.RemoteException e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        android.util.Slog.e(TAG, "failed to dump battery service", e);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (java.io.IOException e3) {
                                android.util.Slog.e(TAG, "failed to close dumpsys output stream");
                            }
                        }
                        if (file == null || file.delete()) {
                            return;
                        }
                        str = TAG;
                        sb = new java.lang.StringBuilder();
                        sb.append("failed to delete temporary dumpsys file: ");
                        sb.append(file.getAbsolutePath());
                        android.util.Slog.e(str, sb.toString());
                    } catch (java.io.IOException e4) {
                        e = e4;
                        fileOutputStream = fileOutputStream2;
                        android.util.Slog.e(TAG, "failed to write dumpsys file", e);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (java.io.IOException e5) {
                                android.util.Slog.e(TAG, "failed to close dumpsys output stream");
                            }
                        }
                        if (file == null || file.delete()) {
                            return;
                        }
                        str = TAG;
                        sb = new java.lang.StringBuilder();
                        sb.append("failed to delete temporary dumpsys file: ");
                        sb.append(file.getAbsolutePath());
                        android.util.Slog.e(str, sb.toString());
                    } catch (java.lang.Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (java.io.IOException e6) {
                                android.util.Slog.e(TAG, "failed to close dumpsys output stream");
                            }
                        }
                        if (file == null) {
                            throw th;
                        }
                        if (file.delete()) {
                            throw th;
                        }
                        android.util.Slog.e(TAG, "failed to delete temporary dumpsys file: " + file.getAbsolutePath());
                        throw th;
                    }
                } catch (android.os.RemoteException e7) {
                    e = e7;
                } catch (java.io.IOException e8) {
                    e = e8;
                }
            } catch (android.os.RemoteException e9) {
                e = e9;
                file = null;
            } catch (java.io.IOException e10) {
                e = e10;
                file = null;
            } catch (java.lang.Throwable th2) {
                th = th2;
                file = null;
            }
            if (file.delete()) {
                return;
            }
            str = TAG;
            sb = new java.lang.StringBuilder();
            sb.append("failed to delete temporary dumpsys file: ");
            sb.append(file.getAbsolutePath());
            android.util.Slog.e(str, sb.toString());
        } catch (java.lang.Throwable th3) {
            th = th3;
        }
    }

    private void logOutlierLocked(long j) {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        java.lang.String string = android.provider.Settings.Global.getString(contentResolver, "battery_discharge_threshold");
        java.lang.String string2 = android.provider.Settings.Global.getString(contentResolver, "battery_discharge_duration_threshold");
        if (string != null && string2 != null) {
            try {
                long parseLong = java.lang.Long.parseLong(string2);
                int parseInt = java.lang.Integer.parseInt(string);
                if (j <= parseLong && this.mDischargeStartLevel - this.mHealthInfo.batteryLevel >= parseInt) {
                    logBatteryStatsLocked();
                }
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.e(TAG, "Invalid DischargeThresholds GService string: " + string2 + " or " + string);
            }
        }
    }

    private int getIconLocked(int i) {
        if (this.mHealthInfo.batteryStatus == 2) {
            return android.R.drawable.stat_sys_battery_71;
        }
        if (this.mHealthInfo.batteryStatus == 3) {
            return android.R.drawable.stat_notify_sync_error;
        }
        if (this.mHealthInfo.batteryStatus == 4 || this.mHealthInfo.batteryStatus == 5) {
            return (!isPoweredLocked(15) || this.mHealthInfo.batteryLevel < 100) ? android.R.drawable.stat_notify_sync_error : android.R.drawable.stat_sys_battery_71;
        }
        return android.R.drawable.stat_sys_battery_charge_anim57;
    }

    private int maybeTranslatePlugType(int i) {
        if (i != 8) {
            return i;
        }
        if (this.mHealthInfo.batteryStatus == 2) {
            return 1;
        }
        return 0;
    }

    private boolean supplementalOrEmergencyModOnline() {
        return this.mBatteryModProps.modLevel > 0 && (this.mBatteryModProps.modType == 2 || this.mBatteryModProps.modType == 3);
    }

    private boolean isModBatteryActive() {
        if (this.mBatteryModProps.modLevel <= 0 || this.mBatteryModProps.modType != 2) {
            return false;
        }
        java.lang.String str = android.os.SystemProperties.get("sys.mod.batterymode");
        if ("0".equals(str)) {
            return true;
        }
        return !"2".equals(str) && this.mHealthInfo.batteryLevel <= 80;
    }

    class Shell extends android.os.ShellCommand {
        Shell() {
        }

        public int onCommand(java.lang.String str) {
            return com.android.server.BatteryService.this.onShellCommand(this, str);
        }

        public void onHelp() {
            com.android.server.BatteryService.dumpHelp(getOutPrintWriter());
        }
    }

    static void dumpHelp(java.io.PrintWriter printWriter) {
        printWriter.println("Battery service (battery) commands:");
        printWriter.println("  help");
        printWriter.println("    Print this help text.");
        java.lang.String str = "ac|usb|wireless|dock|status|level|temp|present|counter|invalid";
        if (android.os.Flags.batteryServiceSupportCurrentAdbCommand()) {
            str = "ac|usb|wireless|dock|status|level|temp|present|counter|invalid|current_now|current_average";
        }
        printWriter.println("  get [-f] [" + str + "]");
        printWriter.println("  set [-f] [" + str + "] <value>");
        printWriter.println("    Force a battery property value, freezing battery state.");
        printWriter.println("    -f: force a battery change broadcast be sent, prints new sequence.");
        printWriter.println("  unplug [-f]");
        printWriter.println("    Force battery unplugged, freezing battery state.");
        printWriter.println("    -f: force a battery change broadcast be sent, prints new sequence.");
        printWriter.println("  reset [-f]");
        printWriter.println("    Unfreeze battery state, returning to current hardware values.");
        printWriter.println("    -f: force a battery change broadcast be sent, prints new sequence.");
        if (android.os.Build.IS_DEBUGGABLE) {
            printWriter.println("  suspend_input");
            printWriter.println("    Suspend charging even if plugged in. ");
        }
    }

    int parseOptions(com.android.server.BatteryService.Shell shell) {
        int i = 0;
        while (true) {
            java.lang.String nextOption = shell.getNextOption();
            if (nextOption != null) {
                if ("-f".equals(nextOption)) {
                    i = 1;
                }
            } else {
                return i;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    int onShellCommand(com.android.server.BatteryService.Shell shell, java.lang.String str) {
        char c;
        char c2;
        char c3;
        boolean z;
        if (str == null) {
            return shell.handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = shell.getOutPrintWriter();
        switch (str.hashCode()) {
            case -840325209:
                if (str.equals("unplug")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -541966841:
                if (str.equals("suspend_input")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 102230:
                if (str.equals("get")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 113762:
                if (str.equals("set")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 108404047:
                if (str.equals("reset")) {
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
                int parseOptions = parseOptions(shell);
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                unplugBattery((parseOptions & 1) != 0, outPrintWriter);
                return 0;
            case 1:
                int parseOptions2 = parseOptions(shell);
                java.lang.String nextArg = shell.getNextArg();
                if (nextArg != null) {
                    switch (nextArg.hashCode()) {
                        case -1000044642:
                            if (nextArg.equals("wireless")) {
                                c2 = 3;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case -892481550:
                            if (nextArg.equals("status")) {
                                c2 = 5;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case -379340457:
                            if (nextArg.equals("current_average")) {
                                c2 = '\t';
                                break;
                            }
                            c2 = 65535;
                            break;
                        case -318277445:
                            if (nextArg.equals("present")) {
                                c2 = 0;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 3106:
                            if (nextArg.equals("ac")) {
                                c2 = 1;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 116100:
                            if (nextArg.equals("usb")) {
                                c2 = 2;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 3088947:
                            if (nextArg.equals("dock")) {
                                c2 = 4;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 3556308:
                            if (nextArg.equals("temp")) {
                                c2 = '\n';
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 102865796:
                            if (nextArg.equals("level")) {
                                c2 = 6;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 601568016:
                            if (nextArg.equals("current_now")) {
                                c2 = '\b';
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 957830652:
                            if (nextArg.equals("counter")) {
                                c2 = 7;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 1959784951:
                            if (nextArg.equals("invalid")) {
                                c2 = 11;
                                break;
                            }
                            c2 = 65535;
                            break;
                        default:
                            c2 = 65535;
                            break;
                    }
                    switch (c2) {
                        case 0:
                            outPrintWriter.println(this.mHealthInfo.batteryPresent);
                            return 0;
                        case 1:
                            outPrintWriter.println(this.mHealthInfo.chargerAcOnline);
                            return 0;
                        case 2:
                            outPrintWriter.println(this.mHealthInfo.chargerUsbOnline);
                            return 0;
                        case 3:
                            outPrintWriter.println(this.mHealthInfo.chargerWirelessOnline);
                            return 0;
                        case 4:
                            outPrintWriter.println(this.mHealthInfo.chargerDockOnline);
                            return 0;
                        case 5:
                            outPrintWriter.println(this.mHealthInfo.batteryStatus);
                            return 0;
                        case 6:
                            outPrintWriter.println(this.mHealthInfo.batteryLevel);
                            return 0;
                        case 7:
                            outPrintWriter.println(this.mHealthInfo.batteryChargeCounterUah);
                            return 0;
                        case '\b':
                            if (android.os.Flags.batteryServiceSupportCurrentAdbCommand()) {
                                if ((parseOptions2 & 1) != 0) {
                                    updateHealthInfo();
                                }
                                outPrintWriter.println(this.mHealthInfo.batteryCurrentMicroamps);
                                return 0;
                            }
                            return 0;
                        case '\t':
                            if (android.os.Flags.batteryServiceSupportCurrentAdbCommand()) {
                                if ((parseOptions2 & 1) != 0) {
                                    updateHealthInfo();
                                }
                                outPrintWriter.println(this.mHealthInfo.batteryCurrentAverageMicroamps);
                                return 0;
                            }
                            return 0;
                        case '\n':
                            outPrintWriter.println(this.mHealthInfo.batteryTemperatureTenthsCelsius);
                            return 0;
                        case 11:
                            outPrintWriter.println(this.mInvalidCharger);
                            return 0;
                        default:
                            outPrintWriter.println("Unknown get option: " + nextArg);
                            return 0;
                    }
                }
                outPrintWriter.println("No property specified");
                return -1;
            case 2:
                int parseOptions3 = parseOptions(shell);
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                java.lang.String nextArg2 = shell.getNextArg();
                if (nextArg2 == null) {
                    outPrintWriter.println("No property specified");
                    return -1;
                }
                java.lang.String nextArg3 = shell.getNextArg();
                if (nextArg3 == null) {
                    outPrintWriter.println("No value specified");
                    return -1;
                }
                try {
                    if (!this.mUpdatesStopped) {
                        com.android.server.health.Utils.copyV1Battery(this.mLastHealthInfo, this.mHealthInfo);
                    }
                    switch (nextArg2.hashCode()) {
                        case -1000044642:
                            if (nextArg2.equals("wireless")) {
                                c3 = 3;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case -892481550:
                            if (nextArg2.equals("status")) {
                                c3 = 5;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case -379340457:
                            if (nextArg2.equals("current_average")) {
                                c3 = '\t';
                                break;
                            }
                            c3 = 65535;
                            break;
                        case -318277445:
                            if (nextArg2.equals("present")) {
                                c3 = 0;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 3106:
                            if (nextArg2.equals("ac")) {
                                c3 = 1;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 116100:
                            if (nextArg2.equals("usb")) {
                                c3 = 2;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 3088947:
                            if (nextArg2.equals("dock")) {
                                c3 = 4;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 3556308:
                            if (nextArg2.equals("temp")) {
                                c3 = '\n';
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 102865796:
                            if (nextArg2.equals("level")) {
                                c3 = 6;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 601568016:
                            if (nextArg2.equals("current_now")) {
                                c3 = '\b';
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 957830652:
                            if (nextArg2.equals("counter")) {
                                c3 = 7;
                                break;
                            }
                            c3 = 65535;
                            break;
                        case 1959784951:
                            if (nextArg2.equals("invalid")) {
                                c3 = 11;
                                break;
                            }
                            c3 = 65535;
                            break;
                        default:
                            c3 = 65535;
                            break;
                    }
                    switch (c3) {
                        case 0:
                            this.mHealthInfo.batteryPresent = java.lang.Integer.parseInt(nextArg3) != 0;
                            z = true;
                            break;
                        case 1:
                            this.mHealthInfo.chargerAcOnline = java.lang.Integer.parseInt(nextArg3) != 0;
                            z = true;
                            break;
                        case 2:
                            this.mHealthInfo.chargerUsbOnline = java.lang.Integer.parseInt(nextArg3) != 0;
                            z = true;
                            break;
                        case 3:
                            this.mHealthInfo.chargerWirelessOnline = java.lang.Integer.parseInt(nextArg3) != 0;
                            z = true;
                            break;
                        case 4:
                            this.mHealthInfo.chargerDockOnline = java.lang.Integer.parseInt(nextArg3) != 0;
                            z = true;
                            break;
                        case 5:
                            this.mHealthInfo.batteryStatus = java.lang.Integer.parseInt(nextArg3);
                            z = true;
                            break;
                        case 6:
                            this.mHealthInfo.batteryLevel = java.lang.Integer.parseInt(nextArg3);
                            z = true;
                            break;
                        case 7:
                            this.mHealthInfo.batteryChargeCounterUah = java.lang.Integer.parseInt(nextArg3);
                            z = true;
                            break;
                        case '\b':
                            if (android.os.Flags.batteryServiceSupportCurrentAdbCommand()) {
                                this.mHealthInfo.batteryCurrentMicroamps = java.lang.Integer.parseInt(nextArg3);
                            }
                            z = true;
                            break;
                        case '\t':
                            if (android.os.Flags.batteryServiceSupportCurrentAdbCommand()) {
                                this.mHealthInfo.batteryCurrentAverageMicroamps = java.lang.Integer.parseInt(nextArg3);
                            }
                        case '\n':
                            this.mHealthInfo.batteryTemperatureTenthsCelsius = java.lang.Integer.parseInt(nextArg3);
                            z = true;
                            break;
                        case 11:
                            this.mInvalidCharger = java.lang.Integer.parseInt(nextArg3);
                            z = true;
                            break;
                        default:
                            outPrintWriter.println("Unknown set option: " + nextArg2);
                            z = false;
                            break;
                    }
                    if (z) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            this.mUpdatesStopped = true;
                            if ((parseOptions3 & 1) == 0) {
                                r9 = false;
                            }
                            lambda$unplugBattery$3(r9, outPrintWriter);
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return 0;
                        } catch (java.lang.Throwable th) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    return 0;
                } catch (java.lang.NumberFormatException e) {
                    outPrintWriter.println("Bad value: " + nextArg3);
                    return -1;
                }
            case 3:
                int parseOptions4 = parseOptions(shell);
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                resetBattery((parseOptions4 & 1) != 0, outPrintWriter);
                return 0;
            case 4:
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                suspendBatteryInput();
                return 0;
            default:
                return shell.handleDefaultCommands(str);
        }
    }

    private void updateHealthInfo() {
        try {
            this.mHealthServiceWrapper.scheduleUpdate();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to update health service data.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setChargerAcOnline(boolean z, final boolean z2) {
        if (!this.mUpdatesStopped) {
            com.android.server.health.Utils.copyV1Battery(this.mLastHealthInfo, this.mHealthInfo);
        }
        this.mHealthInfo.chargerAcOnline = z;
        this.mUpdatesStopped = true;
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                com.android.server.BatteryService.this.lambda$setChargerAcOnline$1(z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBatteryLevel(int i, final boolean z) {
        if (!this.mUpdatesStopped) {
            com.android.server.health.Utils.copyV1Battery(this.mLastHealthInfo, this.mHealthInfo);
        }
        this.mHealthInfo.batteryLevel = i;
        this.mUpdatesStopped = true;
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda4
            public final void runOrThrow() {
                com.android.server.BatteryService.this.lambda$setBatteryLevel$2(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unplugBattery(final boolean z, final java.io.PrintWriter printWriter) {
        if (!this.mUpdatesStopped) {
            com.android.server.health.Utils.copyV1Battery(this.mLastHealthInfo, this.mHealthInfo);
        }
        this.mHealthInfo.chargerAcOnline = false;
        this.mHealthInfo.chargerUsbOnline = false;
        this.mHealthInfo.chargerWirelessOnline = false;
        this.mHealthInfo.chargerDockOnline = false;
        this.mUpdatesStopped = true;
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda2
            public final void runOrThrow() {
                com.android.server.BatteryService.this.lambda$unplugBattery$3(z, printWriter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetBattery(final boolean z, @android.annotation.Nullable final java.io.PrintWriter printWriter) {
        if (this.mUpdatesStopped) {
            this.mUpdatesStopped = false;
            com.android.server.health.Utils.copyV1Battery(this.mHealthInfo, this.mLastHealthInfo);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.BatteryService$$ExternalSyntheticLambda5
                public final void runOrThrow() {
                    com.android.server.BatteryService.this.lambda$resetBattery$4(z, printWriter);
                }
            });
        }
        if (this.mBatteryInputSuspended) {
            android.sysprop.PowerProperties.battery_input_suspended(false);
            this.mBatteryInputSuspended = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void suspendBatteryInput() {
        if (!android.os.Build.IS_DEBUGGABLE) {
            throw new java.lang.SecurityException("battery suspend_input is only supported on debuggable builds");
        }
        android.sysprop.PowerProperties.battery_input_suspended(true);
        this.mBatteryInputSuspended = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: processValuesLocked, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$unplugBattery$3(boolean z, @android.annotation.Nullable java.io.PrintWriter printWriter) {
        lambda$setChargerAcOnline$1(z);
        if (printWriter != null && z) {
            printWriter.println(this.mSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpInternal(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        synchronized (this.mLock) {
            if (strArr != null) {
                try {
                    if (strArr.length != 0 && !"-a".equals(strArr[0])) {
                        new com.android.server.BatteryService.Shell().exec(this.mBinderService, (java.io.FileDescriptor) null, fileDescriptor, (java.io.FileDescriptor) null, strArr, (android.os.ShellCallback) null, new android.os.ResultReceiver(null));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            printWriter.println("Current Battery Service state:");
            if (this.mUpdatesStopped) {
                printWriter.println("  (UPDATES STOPPED -- use 'reset' to restart)");
            }
            printWriter.println("  AC powered: " + this.mHealthInfo.chargerAcOnline);
            printWriter.println("  USB powered: " + this.mHealthInfo.chargerUsbOnline);
            printWriter.println("  Wireless powered: " + this.mHealthInfo.chargerWirelessOnline);
            printWriter.println("  Dock powered: " + this.mHealthInfo.chargerDockOnline);
            printWriter.println("  Max charging current: " + this.mHealthInfo.maxChargingCurrentMicroamps);
            printWriter.println("  Max charging voltage: " + this.mHealthInfo.maxChargingVoltageMicrovolts);
            printWriter.println("  Charge counter: " + this.mHealthInfo.batteryChargeCounterUah);
            printWriter.println("  status: " + this.mHealthInfo.batteryStatus);
            printWriter.println("  health: " + this.mHealthInfo.batteryHealth);
            printWriter.println("  present: " + this.mHealthInfo.batteryPresent);
            printWriter.println("  level: " + this.mHealthInfo.batteryLevel);
            printWriter.println("  scale: 100");
            printWriter.println("  voltage: " + this.mHealthInfo.batteryVoltageMillivolts);
            printWriter.println("  temperature: " + this.mHealthInfo.batteryTemperatureTenthsCelsius);
            printWriter.println("  technology: " + this.mHealthInfo.batteryTechnology);
            printWriter.println("  Charging state: " + this.mHealthInfo.chargingState);
            printWriter.println("  Charging policy: " + this.mHealthInfo.chargingPolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpProto(java.io.FileDescriptor fileDescriptor) {
        int i;
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        synchronized (this.mLock) {
            protoOutputStream.write(1133871366145L, this.mUpdatesStopped);
            if (this.mHealthInfo.chargerAcOnline) {
                i = 1;
            } else if (this.mHealthInfo.chargerUsbOnline) {
                i = 2;
            } else if (this.mHealthInfo.chargerWirelessOnline) {
                i = 4;
            } else if (!this.mHealthInfo.chargerDockOnline) {
                i = 0;
            } else {
                i = 8;
            }
            protoOutputStream.write(1159641169922L, i);
            protoOutputStream.write(1120986464259L, this.mHealthInfo.maxChargingCurrentMicroamps);
            protoOutputStream.write(1120986464260L, this.mHealthInfo.maxChargingVoltageMicrovolts);
            protoOutputStream.write(1120986464261L, this.mHealthInfo.batteryChargeCounterUah);
            protoOutputStream.write(1159641169926L, this.mHealthInfo.batteryStatus);
            protoOutputStream.write(1159641169927L, this.mHealthInfo.batteryHealth);
            protoOutputStream.write(1133871366152L, this.mHealthInfo.batteryPresent);
            protoOutputStream.write(1120986464265L, this.mHealthInfo.batteryLevel);
            protoOutputStream.write(1120986464266L, 100);
            protoOutputStream.write(1120986464267L, this.mHealthInfo.batteryVoltageMillivolts);
            protoOutputStream.write(1120986464268L, this.mHealthInfo.batteryTemperatureTenthsCelsius);
            protoOutputStream.write(1138166333453L, this.mHealthInfo.batteryTechnology);
        }
        protoOutputStream.flush();
    }

    private static void traceBegin(java.lang.String str) {
        android.os.Trace.traceBegin(524288L, str);
    }

    private static void traceEnd() {
        android.os.Trace.traceEnd(524288L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void updateLedPulse() {
        this.mLed.updateLightsLocked();
    }

    private final class Led {
        static final int LOW_BATTERY_BEHAVIOR_DEFAULT = 0;
        static final int LOW_BATTERY_BEHAVIOR_FLASHING = 2;
        static final int LOW_BATTERY_BEHAVIOR_SOLID = 1;
        private final int mBatteryFullARGB;
        private final int mBatteryLedOff;
        private final int mBatteryLedOn;
        private final com.android.server.lights.LogicalLight mBatteryLight;
        private final int mBatteryLowARGB;
        private final int mBatteryLowBehavior;
        private final int mBatteryMediumARGB;

        public Led(android.content.Context context, com.android.server.lights.LightsManager lightsManager) {
            this.mBatteryLight = lightsManager.getLight(3);
            this.mBatteryLowARGB = context.getResources().getInteger(android.R.integer.config_notificationWarnRemoteViewSizeBytes);
            this.mBatteryMediumARGB = context.getResources().getInteger(android.R.integer.config_notificationsBatteryLedOff);
            this.mBatteryFullARGB = context.getResources().getInteger(android.R.integer.config_notificationLongTextMaxLineCount);
            this.mBatteryLedOn = context.getResources().getInteger(android.R.integer.config_notificationStripRemoteViewSizeBytes);
            this.mBatteryLedOff = context.getResources().getInteger(android.R.integer.config_notificationServiceArchiveSize);
            com.android.server.BatteryService.this.mBatteryNearlyFullLevel = context.getResources().getInteger(android.R.integer.config_notificationsBatteryLedOn);
            this.mBatteryLowBehavior = context.getResources().getInteger(android.R.integer.config_notificationsBatteryFullARGB);
        }

        public void updateLightsLocked() {
            if (this.mBatteryLight == null) {
                return;
            }
            if (com.android.server.BatteryService.this.mHealthInfo == null) {
                android.util.Slog.w(com.android.server.BatteryService.TAG, "updateLightsLocked: mHealthInfo is null; skipping");
                return;
            }
            if (com.android.server.BatteryService.this.mLineageBatteryLights == null || !com.android.server.BatteryService.this.mLineageBatteryLights.isSupported()) {
                return;
            }
            org.lineageos.internal.notification.LedValues ledValues = new org.lineageos.internal.notification.LedValues(0, this.mBatteryLedOn, this.mBatteryLedOff);
            com.android.server.BatteryService.this.mLineageBatteryLights.calcLights(ledValues, com.android.server.BatteryService.this.mHealthInfo.batteryLevel, com.android.server.BatteryService.this.mHealthInfo.batteryStatus, com.android.server.BatteryService.this.mHealthInfo.batteryLevel <= com.android.server.BatteryService.this.mLowBatteryWarningLevel);
            if (!ledValues.isEnabled()) {
                this.mBatteryLight.turnOff();
            } else if (ledValues.isPulsed()) {
                this.mBatteryLight.setModes(ledValues.getBrightness());
                this.mBatteryLight.setFlashing(ledValues.getColor(), 1, ledValues.getOnMs(), ledValues.getOffMs());
            } else {
                this.mBatteryLight.setModes(ledValues.getBrightness());
                this.mBatteryLight.setColor(ledValues.getColor());
            }
        }
    }

    private final class BinderService extends android.os.Binder {
        private BinderService() {
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.BatteryService.this.mContext, com.android.server.BatteryService.TAG, printWriter)) {
                if (strArr.length > 0 && "--proto".equals(strArr[0])) {
                    com.android.server.BatteryService.this.dumpProto(fileDescriptor);
                } else {
                    com.android.server.BatteryService.this.dumpInternal(fileDescriptor, printWriter, strArr);
                }
            }
        }

        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            com.android.server.BatteryService.this.new Shell().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    private final class BatteryPropertiesRegistrar extends android.os.IBatteryPropertiesRegistrar.Stub {
        private BatteryPropertiesRegistrar() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:3:0x0008, code lost:
        
            if (android.os.Flags.stateOfHealthPublic() != false) goto L8;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int getProperty(int i, android.os.BatteryProperty batteryProperty) throws android.os.RemoteException {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 11:
                case 12:
                    com.android.server.BatteryService.this.mContext.enforceCallingPermission("android.permission.BATTERY_STATS", null);
                    break;
            }
            return com.android.server.BatteryService.this.mHealthServiceWrapper.getProperty(i, batteryProperty);
        }

        public void scheduleUpdate() throws android.os.RemoteException {
            com.android.server.BatteryService.this.mHealthServiceWrapper.scheduleUpdate();
        }
    }

    private final class LocalService extends android.os.BatteryManagerInternal {
        private LocalService() {
        }

        public boolean isPowered(int i) {
            boolean isPoweredLocked;
            synchronized (com.android.server.BatteryService.this.mLock) {
                isPoweredLocked = com.android.server.BatteryService.this.isPoweredLocked(i);
            }
            return isPoweredLocked;
        }

        public int getPlugType() {
            int i;
            synchronized (com.android.server.BatteryService.this.mLock) {
                i = com.android.server.BatteryService.this.mPlugType;
            }
            return i;
        }

        public int getBatteryLevel() {
            int i;
            synchronized (com.android.server.BatteryService.this.mLock) {
                i = com.android.server.BatteryService.this.mHealthInfo.batteryLevel;
            }
            return i;
        }

        public int getBatteryChargeCounter() {
            int i;
            synchronized (com.android.server.BatteryService.this.mLock) {
                i = com.android.server.BatteryService.this.mHealthInfo.batteryChargeCounterUah;
            }
            return i;
        }

        public int getBatteryFullCharge() {
            int i;
            synchronized (com.android.server.BatteryService.this.mLock) {
                i = com.android.server.BatteryService.this.mHealthInfo.batteryFullChargeUah;
            }
            return i;
        }

        public int getBatteryHealth() {
            int i;
            synchronized (com.android.server.BatteryService.this.mLock) {
                i = com.android.server.BatteryService.this.mHealthInfo.batteryHealth;
            }
            return i;
        }

        public boolean getBatteryLevelLow() {
            boolean z;
            synchronized (com.android.server.BatteryService.this.mLock) {
                z = com.android.server.BatteryService.this.mBatteryLevelLow;
            }
            return z;
        }

        public void registerChargingPolicyChangeListener(android.os.BatteryManagerInternal.ChargingPolicyChangeListener chargingPolicyChangeListener) {
            com.android.server.BatteryService.this.mChargingPolicyChangeListeners.add(chargingPolicyChangeListener);
        }

        public int getChargingPolicy() {
            int i;
            synchronized (com.android.server.BatteryService.this.mLock) {
                i = com.android.server.BatteryService.this.mLastChargingPolicy;
            }
            return i;
        }

        public int getInvalidCharger() {
            int i;
            synchronized (com.android.server.BatteryService.this.mLock) {
                i = com.android.server.BatteryService.this.mInvalidCharger;
            }
            return i;
        }

        public void setChargerAcOnline(boolean z, boolean z2) {
            com.android.server.BatteryService.this.setChargerAcOnline(z, z2);
        }

        public void setBatteryLevel(int i, boolean z) {
            com.android.server.BatteryService.this.setBatteryLevel(i, z);
        }

        public void unplugBattery(boolean z) {
            com.android.server.BatteryService.this.unplugBattery(z, null);
        }

        public void resetBattery(boolean z) {
            com.android.server.BatteryService.this.resetBattery(z, null);
        }

        public void suspendBatteryInput() {
            com.android.server.BatteryService.this.suspendBatteryInput();
        }
    }
}
