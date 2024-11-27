package com.android.server.power.batterysaver;

/* loaded from: classes2.dex */
public class BatterySaverPolicy extends android.database.ContentObserver implements android.provider.DeviceConfig.OnPropertiesChangedListener {
    static final boolean DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_ADJUST_BRIGHTNESS_FACTOR = "adjust_brightness_factor";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_ADVERTISE_IS_ENABLED = "advertise_is_enabled";

    @java.lang.Deprecated
    private static final java.lang.String KEY_CPU_FREQ_INTERACTIVE = "cpufreq-i";

    @java.lang.Deprecated
    private static final java.lang.String KEY_CPU_FREQ_NONINTERACTIVE = "cpufreq-n";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_DEFER_FULL_BACKUP = "defer_full_backup";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_DEFER_KEYVALUE_BACKUP = "defer_keyvalue_backup";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_DISABLE_ANIMATION = "disable_animation";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_DISABLE_AOD = "disable_aod";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_DISABLE_LAUNCH_BOOST = "disable_launch_boost";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_DISABLE_OPTIONAL_SENSORS = "disable_optional_sensors";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_DISABLE_VIBRATION = "disable_vibration";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_ENABLE_BRIGHTNESS_ADJUSTMENT = "enable_brightness_adjustment";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_ENABLE_DATASAVER = "enable_datasaver";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_ENABLE_FIREWALL = "enable_firewall";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_ENABLE_NIGHT_MODE = "enable_night_mode";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_ENABLE_QUICK_DOZE = "enable_quick_doze";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FORCE_ALL_APPS_STANDBY = "force_all_apps_standby";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_FORCE_BACKGROUND_CHECK = "force_background_check";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_LOCATION_MODE = "location_mode";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_SOUNDTRIGGER_MODE = "soundtrigger_mode";
    private static final java.lang.String KEY_SUFFIX_ADAPTIVE = "_adaptive";
    static final int POLICY_LEVEL_ADAPTIVE = 1;
    static final int POLICY_LEVEL_FULL = 2;
    static final int POLICY_LEVEL_OFF = 0;
    private static final java.lang.String TAG = "BatterySaverPolicy";
    private final com.android.server.power.batterysaver.BatterySaverPolicy.Policy DEFAULT_FULL_POLICY;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.power.batterysaver.BatterySaverPolicy.PolicyBoolean mAccessibilityEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.power.batterysaver.BatterySaverPolicy.Policy mAdaptivePolicy;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.power.batterysaver.BatterySaverPolicy.PolicyBoolean mAutomotiveProjectionActive;
    private final com.android.server.power.batterysaver.BatterySavingStats mBatterySavingStats;
    private final android.content.ContentResolver mContentResolver;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.power.batterysaver.BatterySaverPolicy.Policy mDefaultAdaptivePolicy;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.power.batterysaver.BatterySaverPolicy.Policy mDefaultFullPolicy;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mDeviceSpecificSettings;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mDeviceSpecificSettingsSource;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.power.batterysaver.BatterySaverPolicy.Policy mEffectivePolicyRaw;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mEventLogKeys;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.power.batterysaver.BatterySaverPolicy.Policy mFullPolicy;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.provider.DeviceConfig.Properties mLastDeviceConfigProperties;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<com.android.server.power.batterysaver.BatterySaverPolicy.BatterySaverPolicyListener> mListeners;
    private final java.lang.Object mLock;
    private final android.app.UiModeManager.OnProjectionStateChangedListener mOnProjectionStateChangedListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mPolicyLevel;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mSettings;

    @com.android.internal.annotations.VisibleForTesting
    static final com.android.server.power.batterysaver.BatterySaverPolicy.Policy OFF_POLICY = new com.android.server.power.batterysaver.BatterySaverPolicy.Policy(1.0f, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 0, 0);
    private static final com.android.server.power.batterysaver.BatterySaverPolicy.Policy DEFAULT_ADAPTIVE_POLICY = OFF_POLICY;

    public interface BatterySaverPolicyListener {
        void onBatterySaverPolicyChanged(com.android.server.power.batterysaver.BatterySaverPolicy batterySaverPolicy);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface PolicyLevel {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, java.util.Set set) {
        this.mAutomotiveProjectionActive.update(!set.isEmpty());
    }

    public BatterySaverPolicy(java.lang.Object obj, android.content.Context context, com.android.server.power.batterysaver.BatterySavingStats batterySavingStats) {
        super(com.android.internal.os.BackgroundThread.getHandler());
        this.mAccessibilityEnabled = new com.android.server.power.batterysaver.BatterySaverPolicy.PolicyBoolean("accessibility");
        this.mAutomotiveProjectionActive = new com.android.server.power.batterysaver.BatterySaverPolicy.PolicyBoolean("automotiveProjection");
        this.mDefaultAdaptivePolicy = DEFAULT_ADAPTIVE_POLICY;
        this.mAdaptivePolicy = DEFAULT_ADAPTIVE_POLICY;
        this.mEffectivePolicyRaw = OFF_POLICY;
        this.mPolicyLevel = 0;
        this.mOnProjectionStateChangedListener = new android.app.UiModeManager.OnProjectionStateChangedListener() { // from class: com.android.server.power.batterysaver.BatterySaverPolicy$$ExternalSyntheticLambda1
            public final void onProjectionStateChanged(int i, java.util.Set set) {
                com.android.server.power.batterysaver.BatterySaverPolicy.this.lambda$new$0(i, set);
            }
        };
        this.mListeners = new java.util.ArrayList();
        this.mLock = obj;
        this.mHandler = com.android.internal.os.BackgroundThread.getHandler();
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mBatterySavingStats = batterySavingStats;
        android.content.res.Resources resources = context.getResources();
        this.DEFAULT_FULL_POLICY = new com.android.server.power.batterysaver.BatterySaverPolicy.Policy(resources.getFloat(android.R.dimen.chooser_view_spacing), true, resources.getBoolean(android.R.bool.config_batterySaver_full_deferFullBackup), resources.getBoolean(android.R.bool.config_batterySaver_full_deferKeyValueBackup), resources.getBoolean(android.R.bool.config_batterySaver_full_disableAnimation), resources.getBoolean(android.R.bool.config_batterySaver_full_disableAod), resources.getBoolean(android.R.bool.config_batterySaver_full_disableLaunchBoost), resources.getBoolean(android.R.bool.config_batterySaver_full_disableOptionalSensors), resources.getBoolean(android.R.bool.config_batterySaver_full_disableVibration), resources.getBoolean(android.R.bool.config_batterySaver_full_enableAdjustBrightness), resources.getBoolean(android.R.bool.config_batterySaver_full_enableDataSaver), resources.getBoolean(android.R.bool.config_batterySaver_full_enableFirewall), resources.getBoolean(android.R.bool.config_batterySaver_full_enableNightMode), resources.getBoolean(android.R.bool.config_batterySaver_full_enableQuickDoze), resources.getBoolean(android.R.bool.config_batterySaver_full_forceAllAppsStandby), resources.getBoolean(android.R.bool.config_batterySaver_full_forceBackgroundCheck), resources.getInteger(android.R.integer.config_backgroundUserScheduledStopTimeSecs), resources.getInteger(android.R.integer.config_batterySaver_full_locationMode));
        this.mDefaultFullPolicy = this.DEFAULT_FULL_POLICY;
        this.mFullPolicy = this.DEFAULT_FULL_POLICY;
    }

    public void systemReady() {
        com.android.internal.util.ConcurrentUtils.wtfIfLockHeld(TAG, this.mLock);
        this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("battery_saver_constants"), false, this);
        this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("battery_saver_device_specific_constants"), false, this);
        android.view.accessibility.AccessibilityManager accessibilityManager = (android.view.accessibility.AccessibilityManager) this.mContext.getSystemService(android.view.accessibility.AccessibilityManager.class);
        accessibilityManager.addAccessibilityStateChangeListener(new android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener() { // from class: com.android.server.power.batterysaver.BatterySaverPolicy$$ExternalSyntheticLambda2
            @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
            public final void onAccessibilityStateChanged(boolean z) {
                com.android.server.power.batterysaver.BatterySaverPolicy.this.lambda$systemReady$1(z);
            }
        });
        this.mAccessibilityEnabled.initialize(accessibilityManager.isEnabled());
        android.app.UiModeManager uiModeManager = (android.app.UiModeManager) this.mContext.getSystemService(android.app.UiModeManager.class);
        uiModeManager.addOnProjectionStateChangedListener(1, this.mContext.getMainExecutor(), this.mOnProjectionStateChangedListener);
        this.mAutomotiveProjectionActive.initialize(uiModeManager.getActiveProjectionTypes() != 0);
        android.provider.DeviceConfig.addOnPropertiesChangedListener("battery_saver", this.mContext.getMainExecutor(), this);
        this.mLastDeviceConfigProperties = android.provider.DeviceConfig.getProperties("battery_saver", new java.lang.String[0]);
        onChange(true, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$1(boolean z) {
        this.mAccessibilityEnabled.update(z);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void addListener(com.android.server.power.batterysaver.BatterySaverPolicy.BatterySaverPolicyListener batterySaverPolicyListener) {
        synchronized (this.mLock) {
            this.mListeners.add(batterySaverPolicyListener);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String getGlobalSetting(java.lang.String str) {
        return android.provider.Settings.Global.getString(this.mContentResolver, str);
    }

    @com.android.internal.annotations.VisibleForTesting
    int getDeviceSpecificConfigResId() {
        return android.R.string.config_UsbDeviceConnectionHandling_component;
    }

    @com.android.internal.annotations.VisibleForTesting
    void invalidatePowerSaveModeCaches() {
        android.os.PowerManager.invalidatePowerSaveModeCaches();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeNotifyListenersOfPolicyChange() {
        synchronized (this.mLock) {
            try {
                if (this.mPolicyLevel == 0) {
                    return;
                }
                final com.android.server.power.batterysaver.BatterySaverPolicy.BatterySaverPolicyListener[] batterySaverPolicyListenerArr = (com.android.server.power.batterysaver.BatterySaverPolicy.BatterySaverPolicyListener[]) this.mListeners.toArray(new com.android.server.power.batterysaver.BatterySaverPolicy.BatterySaverPolicyListener[this.mListeners.size()]);
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverPolicy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.power.batterysaver.BatterySaverPolicy.this.lambda$maybeNotifyListenersOfPolicyChange$2(batterySaverPolicyListenerArr);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeNotifyListenersOfPolicyChange$2(com.android.server.power.batterysaver.BatterySaverPolicy.BatterySaverPolicyListener[] batterySaverPolicyListenerArr) {
        for (com.android.server.power.batterysaver.BatterySaverPolicy.BatterySaverPolicyListener batterySaverPolicyListener : batterySaverPolicyListenerArr) {
            batterySaverPolicyListener.onBatterySaverPolicyChanged(this);
        }
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, android.net.Uri uri) {
        refreshSettings();
    }

    public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
        boolean z;
        this.mLastDeviceConfigProperties = android.provider.DeviceConfig.getProperties("battery_saver", new java.lang.String[0]);
        synchronized (this.mLock) {
            try {
                com.android.server.power.batterysaver.BatterySaverPolicy.Policy policy = null;
                com.android.server.power.batterysaver.BatterySaverPolicy.Policy policy2 = null;
                for (java.lang.String str : properties.getKeyset()) {
                    if (str != null) {
                        if (str.endsWith(KEY_SUFFIX_ADAPTIVE)) {
                            if (policy2 == null) {
                                policy2 = com.android.server.power.batterysaver.BatterySaverPolicy.Policy.fromSettings("", "", this.mLastDeviceConfigProperties, KEY_SUFFIX_ADAPTIVE, DEFAULT_ADAPTIVE_POLICY);
                            }
                        } else if (policy == null) {
                            policy = com.android.server.power.batterysaver.BatterySaverPolicy.Policy.fromSettings(this.mSettings, this.mDeviceSpecificSettings, this.mLastDeviceConfigProperties, null, this.DEFAULT_FULL_POLICY);
                        }
                    }
                }
                if (policy == null) {
                    z = false;
                } else {
                    z = maybeUpdateDefaultFullPolicy(policy) | false;
                }
                if (policy2 != null && !this.mAdaptivePolicy.equals(policy2)) {
                    this.mDefaultAdaptivePolicy = policy2;
                    this.mAdaptivePolicy = this.mDefaultAdaptivePolicy;
                    z |= this.mPolicyLevel == 1;
                }
                updatePolicyDependenciesLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            maybeNotifyListenersOfPolicyChange();
        }
    }

    private void refreshSettings() {
        synchronized (this.mLock) {
            try {
                java.lang.String globalSetting = getGlobalSetting("battery_saver_constants");
                java.lang.String globalSetting2 = getGlobalSetting("battery_saver_device_specific_constants");
                this.mDeviceSpecificSettingsSource = "battery_saver_device_specific_constants";
                if (android.text.TextUtils.isEmpty(globalSetting2) || "null".equals(globalSetting2)) {
                    globalSetting2 = this.mContext.getString(getDeviceSpecificConfigResId());
                    this.mDeviceSpecificSettingsSource = "(overlay)";
                }
                if (updateConstantsLocked(globalSetting, globalSetting2)) {
                    maybeNotifyListenersOfPolicyChange();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean updateConstantsLocked(java.lang.String str, java.lang.String str2) {
        java.lang.String emptyIfNull = android.text.TextUtils.emptyIfNull(str);
        java.lang.String emptyIfNull2 = android.text.TextUtils.emptyIfNull(str2);
        if (emptyIfNull.equals(this.mSettings) && emptyIfNull2.equals(this.mDeviceSpecificSettings)) {
            return false;
        }
        this.mSettings = emptyIfNull;
        this.mDeviceSpecificSettings = emptyIfNull2;
        boolean maybeUpdateDefaultFullPolicy = maybeUpdateDefaultFullPolicy(com.android.server.power.batterysaver.BatterySaverPolicy.Policy.fromSettings(emptyIfNull, emptyIfNull2, this.mLastDeviceConfigProperties, null, this.DEFAULT_FULL_POLICY));
        this.mDefaultAdaptivePolicy = com.android.server.power.batterysaver.BatterySaverPolicy.Policy.fromSettings("", "", this.mLastDeviceConfigProperties, KEY_SUFFIX_ADAPTIVE, DEFAULT_ADAPTIVE_POLICY);
        if (this.mPolicyLevel == 1 && !this.mAdaptivePolicy.equals(this.mDefaultAdaptivePolicy)) {
            maybeUpdateDefaultFullPolicy = true;
        }
        this.mAdaptivePolicy = this.mDefaultAdaptivePolicy;
        updatePolicyDependenciesLocked();
        return maybeUpdateDefaultFullPolicy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updatePolicyDependenciesLocked() {
        int i;
        com.android.server.power.batterysaver.BatterySaverPolicy.Policy currentRawPolicyLocked = getCurrentRawPolicyLocked();
        invalidatePowerSaveModeCaches();
        if (this.mAutomotiveProjectionActive.get() && currentRawPolicyLocked.locationMode != 0 && currentRawPolicyLocked.locationMode != 3) {
            i = 3;
        } else {
            i = currentRawPolicyLocked.locationMode;
        }
        this.mEffectivePolicyRaw = new com.android.server.power.batterysaver.BatterySaverPolicy.Policy(currentRawPolicyLocked.adjustBrightnessFactor, currentRawPolicyLocked.advertiseIsEnabled, currentRawPolicyLocked.deferFullBackup, currentRawPolicyLocked.deferKeyValueBackup, currentRawPolicyLocked.disableAnimation, currentRawPolicyLocked.disableAod, currentRawPolicyLocked.disableLaunchBoost, currentRawPolicyLocked.disableOptionalSensors, currentRawPolicyLocked.disableVibration && !this.mAccessibilityEnabled.get(), currentRawPolicyLocked.enableAdjustBrightness, currentRawPolicyLocked.enableDataSaver, currentRawPolicyLocked.enableFirewall, currentRawPolicyLocked.enableNightMode && !this.mAutomotiveProjectionActive.get(), currentRawPolicyLocked.enableQuickDoze, currentRawPolicyLocked.forceAllAppsStandby, currentRawPolicyLocked.forceBackgroundCheck, i, currentRawPolicyLocked.soundTriggerMode);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (this.mEffectivePolicyRaw.forceAllAppsStandby) {
            sb.append("A");
        }
        if (this.mEffectivePolicyRaw.forceBackgroundCheck) {
            sb.append("B");
        }
        if (this.mEffectivePolicyRaw.disableVibration) {
            sb.append("v");
        }
        if (this.mEffectivePolicyRaw.disableAnimation) {
            sb.append(com.android.server.wm.ActivityTaskManagerService.DUMP_ACTIVITIES_SHORT_CMD);
        }
        sb.append(this.mEffectivePolicyRaw.soundTriggerMode);
        if (this.mEffectivePolicyRaw.deferFullBackup) {
            sb.append("F");
        }
        if (this.mEffectivePolicyRaw.deferKeyValueBackup) {
            sb.append("K");
        }
        if (this.mEffectivePolicyRaw.enableFirewall) {
            sb.append("f");
        }
        if (this.mEffectivePolicyRaw.enableDataSaver) {
            sb.append("d");
        }
        if (this.mEffectivePolicyRaw.enableAdjustBrightness) {
            sb.append("b");
        }
        if (this.mEffectivePolicyRaw.disableLaunchBoost) {
            sb.append("l");
        }
        if (this.mEffectivePolicyRaw.disableOptionalSensors) {
            sb.append("S");
        }
        if (this.mEffectivePolicyRaw.disableAod) {
            sb.append("o");
        }
        if (this.mEffectivePolicyRaw.enableQuickDoze) {
            sb.append("q");
        }
        sb.append(this.mEffectivePolicyRaw.locationMode);
        this.mEventLogKeys = sb.toString();
    }

    static class Policy {
        public final float adjustBrightnessFactor;
        public final boolean advertiseIsEnabled;
        public final boolean deferFullBackup;
        public final boolean deferKeyValueBackup;
        public final boolean disableAnimation;
        public final boolean disableAod;
        public final boolean disableLaunchBoost;
        public final boolean disableOptionalSensors;
        public final boolean disableVibration;
        public final boolean enableAdjustBrightness;
        public final boolean enableDataSaver;
        public final boolean enableFirewall;
        public final boolean enableNightMode;
        public final boolean enableQuickDoze;
        public final boolean forceAllAppsStandby;
        public final boolean forceBackgroundCheck;
        public final int locationMode;
        private final int mHashCode;
        public final int soundTriggerMode;

        Policy(float f, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, int i, int i2) {
            this.adjustBrightnessFactor = java.lang.Math.min(1.0f, java.lang.Math.max(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, f));
            this.advertiseIsEnabled = z;
            this.deferFullBackup = z2;
            this.deferKeyValueBackup = z3;
            this.disableAnimation = z4;
            this.disableAod = z5;
            this.disableLaunchBoost = z6;
            this.disableOptionalSensors = z7;
            this.disableVibration = z8;
            this.enableAdjustBrightness = z9;
            this.enableDataSaver = z10;
            this.enableFirewall = z11;
            this.enableNightMode = z12;
            this.enableQuickDoze = z13;
            this.forceAllAppsStandby = z14;
            this.forceBackgroundCheck = z15;
            if (i < 0 || 4 < i) {
                android.util.Slog.e(com.android.server.power.batterysaver.BatterySaverPolicy.TAG, "Invalid location mode: " + i);
                this.locationMode = 0;
            } else {
                this.locationMode = i;
            }
            if (i2 < 0 || i2 > 2) {
                android.util.Slog.e(com.android.server.power.batterysaver.BatterySaverPolicy.TAG, "Invalid SoundTrigger mode: " + i2);
                this.soundTriggerMode = 0;
            } else {
                this.soundTriggerMode = i2;
            }
            this.mHashCode = java.util.Objects.hash(java.lang.Float.valueOf(f), java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), java.lang.Boolean.valueOf(z3), java.lang.Boolean.valueOf(z4), java.lang.Boolean.valueOf(z5), java.lang.Boolean.valueOf(z6), java.lang.Boolean.valueOf(z7), java.lang.Boolean.valueOf(z8), java.lang.Boolean.valueOf(z9), java.lang.Boolean.valueOf(z10), java.lang.Boolean.valueOf(z11), java.lang.Boolean.valueOf(z12), java.lang.Boolean.valueOf(z13), java.lang.Boolean.valueOf(z14), java.lang.Boolean.valueOf(z15), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }

        static com.android.server.power.batterysaver.BatterySaverPolicy.Policy fromConfig(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) {
            if (batterySaverPolicyConfig == null) {
                android.util.Slog.e(com.android.server.power.batterysaver.BatterySaverPolicy.TAG, "Null config passed down to BatterySaverPolicy");
                return com.android.server.power.batterysaver.BatterySaverPolicy.OFF_POLICY;
            }
            batterySaverPolicyConfig.getDeviceSpecificSettings();
            return new com.android.server.power.batterysaver.BatterySaverPolicy.Policy(batterySaverPolicyConfig.getAdjustBrightnessFactor(), batterySaverPolicyConfig.getAdvertiseIsEnabled(), batterySaverPolicyConfig.getDeferFullBackup(), batterySaverPolicyConfig.getDeferKeyValueBackup(), batterySaverPolicyConfig.getDisableAnimation(), batterySaverPolicyConfig.getDisableAod(), batterySaverPolicyConfig.getDisableLaunchBoost(), batterySaverPolicyConfig.getDisableOptionalSensors(), batterySaverPolicyConfig.getDisableVibration(), batterySaverPolicyConfig.getEnableAdjustBrightness(), batterySaverPolicyConfig.getEnableDataSaver(), batterySaverPolicyConfig.getEnableFirewall(), batterySaverPolicyConfig.getEnableNightMode(), batterySaverPolicyConfig.getEnableQuickDoze(), batterySaverPolicyConfig.getForceAllAppsStandby(), batterySaverPolicyConfig.getForceBackgroundCheck(), batterySaverPolicyConfig.getLocationMode(), batterySaverPolicyConfig.getSoundTriggerMode());
        }

        android.os.BatterySaverPolicyConfig toConfig() {
            return new android.os.BatterySaverPolicyConfig.Builder().setAdjustBrightnessFactor(this.adjustBrightnessFactor).setAdvertiseIsEnabled(this.advertiseIsEnabled).setDeferFullBackup(this.deferFullBackup).setDeferKeyValueBackup(this.deferKeyValueBackup).setDisableAnimation(this.disableAnimation).setDisableAod(this.disableAod).setDisableLaunchBoost(this.disableLaunchBoost).setDisableOptionalSensors(this.disableOptionalSensors).setDisableVibration(this.disableVibration).setEnableAdjustBrightness(this.enableAdjustBrightness).setEnableDataSaver(this.enableDataSaver).setEnableFirewall(this.enableFirewall).setEnableNightMode(this.enableNightMode).setEnableQuickDoze(this.enableQuickDoze).setForceAllAppsStandby(this.forceAllAppsStandby).setForceBackgroundCheck(this.forceBackgroundCheck).setLocationMode(this.locationMode).setSoundTriggerMode(this.soundTriggerMode).build();
        }

        @com.android.internal.annotations.VisibleForTesting
        static com.android.server.power.batterysaver.BatterySaverPolicy.Policy fromSettings(java.lang.String str, java.lang.String str2, android.provider.DeviceConfig.Properties properties, java.lang.String str3) {
            return fromSettings(str, str2, properties, str3, com.android.server.power.batterysaver.BatterySaverPolicy.OFF_POLICY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static com.android.server.power.batterysaver.BatterySaverPolicy.Policy fromSettings(java.lang.String str, java.lang.String str2, android.provider.DeviceConfig.Properties properties, java.lang.String str3, com.android.server.power.batterysaver.BatterySaverPolicy.Policy policy) {
            com.android.server.utils.UserSettingDeviceConfigMediator.SettingsOverridesIndividualMediator settingsOverridesIndividualMediator = new com.android.server.utils.UserSettingDeviceConfigMediator.SettingsOverridesIndividualMediator(',');
            java.lang.String emptyIfNull = android.text.TextUtils.emptyIfNull(str3);
            try {
                settingsOverridesIndividualMediator.setSettingsString(str2);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.wtf(com.android.server.power.batterysaver.BatterySaverPolicy.TAG, "Bad device specific battery saver constants: " + str2);
            }
            try {
                settingsOverridesIndividualMediator.setSettingsString(str);
                settingsOverridesIndividualMediator.setDeviceConfigProperties(properties);
            } catch (java.lang.IllegalArgumentException e2) {
                android.util.Slog.wtf(com.android.server.power.batterysaver.BatterySaverPolicy.TAG, "Bad battery saver constants: " + str);
            }
            return new com.android.server.power.batterysaver.BatterySaverPolicy.Policy(settingsOverridesIndividualMediator.getFloat(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_ADJUST_BRIGHTNESS_FACTOR + emptyIfNull, policy.adjustBrightnessFactor), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_ADVERTISE_IS_ENABLED + emptyIfNull, policy.advertiseIsEnabled), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_DEFER_FULL_BACKUP + emptyIfNull, policy.deferFullBackup), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_DEFER_KEYVALUE_BACKUP + emptyIfNull, policy.deferKeyValueBackup), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_DISABLE_ANIMATION + emptyIfNull, policy.disableAnimation), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_DISABLE_AOD + emptyIfNull, policy.disableAod), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_DISABLE_LAUNCH_BOOST + emptyIfNull, policy.disableLaunchBoost), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_DISABLE_OPTIONAL_SENSORS + emptyIfNull, policy.disableOptionalSensors), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_DISABLE_VIBRATION + emptyIfNull, policy.disableVibration), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_ENABLE_BRIGHTNESS_ADJUSTMENT + emptyIfNull, policy.enableAdjustBrightness), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_ENABLE_DATASAVER + emptyIfNull, policy.enableDataSaver), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_ENABLE_FIREWALL + emptyIfNull, policy.enableFirewall), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_ENABLE_NIGHT_MODE + emptyIfNull, policy.enableNightMode), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_ENABLE_QUICK_DOZE + emptyIfNull, policy.enableQuickDoze), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_FORCE_ALL_APPS_STANDBY + emptyIfNull, policy.forceAllAppsStandby), settingsOverridesIndividualMediator.getBoolean(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_FORCE_BACKGROUND_CHECK + emptyIfNull, policy.forceBackgroundCheck), settingsOverridesIndividualMediator.getInt(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_LOCATION_MODE + emptyIfNull, policy.locationMode), settingsOverridesIndividualMediator.getInt(com.android.server.power.batterysaver.BatterySaverPolicy.KEY_SOUNDTRIGGER_MODE + emptyIfNull, policy.soundTriggerMode));
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.power.batterysaver.BatterySaverPolicy.Policy)) {
                return false;
            }
            com.android.server.power.batterysaver.BatterySaverPolicy.Policy policy = (com.android.server.power.batterysaver.BatterySaverPolicy.Policy) obj;
            return java.lang.Float.compare(policy.adjustBrightnessFactor, this.adjustBrightnessFactor) == 0 && this.advertiseIsEnabled == policy.advertiseIsEnabled && this.deferFullBackup == policy.deferFullBackup && this.deferKeyValueBackup == policy.deferKeyValueBackup && this.disableAnimation == policy.disableAnimation && this.disableAod == policy.disableAod && this.disableLaunchBoost == policy.disableLaunchBoost && this.disableOptionalSensors == policy.disableOptionalSensors && this.disableVibration == policy.disableVibration && this.enableAdjustBrightness == policy.enableAdjustBrightness && this.enableDataSaver == policy.enableDataSaver && this.enableFirewall == policy.enableFirewall && this.enableNightMode == policy.enableNightMode && this.enableQuickDoze == policy.enableQuickDoze && this.forceAllAppsStandby == policy.forceAllAppsStandby && this.forceBackgroundCheck == policy.forceBackgroundCheck && this.locationMode == policy.locationMode && this.soundTriggerMode == policy.soundTriggerMode;
        }

        public int hashCode() {
            return this.mHashCode;
        }
    }

    public android.os.PowerSaveState getBatterySaverPolicy(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.power.batterysaver.BatterySaverPolicy.Policy currentPolicyLocked = getCurrentPolicyLocked();
                android.os.PowerSaveState.Builder globalBatterySaverEnabled = new android.os.PowerSaveState.Builder().setGlobalBatterySaverEnabled(currentPolicyLocked.advertiseIsEnabled);
                boolean z = true;
                switch (i) {
                    case 1:
                        if (!currentPolicyLocked.advertiseIsEnabled && currentPolicyLocked.locationMode == 0) {
                            z = false;
                        }
                        return globalBatterySaverEnabled.setBatterySaverEnabled(z).setLocationMode(currentPolicyLocked.locationMode).build();
                    case 2:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.disableVibration).build();
                    case 3:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.disableAnimation).build();
                    case 4:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.deferFullBackup).build();
                    case 5:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.deferKeyValueBackup).build();
                    case 6:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.enableFirewall).build();
                    case 7:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.enableAdjustBrightness).setBrightnessFactor(currentPolicyLocked.adjustBrightnessFactor).build();
                    case 8:
                        if (!currentPolicyLocked.advertiseIsEnabled && currentPolicyLocked.soundTriggerMode == 0) {
                            z = false;
                        }
                        return globalBatterySaverEnabled.setBatterySaverEnabled(z).setSoundTriggerMode(currentPolicyLocked.soundTriggerMode).build();
                    case 9:
                    default:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.advertiseIsEnabled).build();
                    case 10:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.enableDataSaver).build();
                    case 11:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.forceAllAppsStandby).build();
                    case 12:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.forceBackgroundCheck).build();
                    case 13:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.disableOptionalSensors).build();
                    case 14:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.disableAod).build();
                    case 15:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.enableQuickDoze).build();
                    case 16:
                        return globalBatterySaverEnabled.setBatterySaverEnabled(currentPolicyLocked.enableNightMode).build();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean setPolicyLevel(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mPolicyLevel == i) {
                    return false;
                }
                if (this.mPolicyLevel == 2) {
                    this.mFullPolicy = this.mDefaultFullPolicy;
                }
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        this.mPolicyLevel = i;
                        updatePolicyDependenciesLocked();
                        return true;
                    default:
                        android.util.Slog.wtf(TAG, "setPolicyLevel invalid level given: " + i);
                        return false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    com.android.server.power.batterysaver.BatterySaverPolicy.Policy getPolicyLocked(int i) {
        switch (i) {
            case 0:
                return OFF_POLICY;
            case 1:
                return this.mAdaptivePolicy;
            case 2:
                return this.mFullPolicy;
            default:
                throw new java.lang.IllegalArgumentException("getPolicyLocked: incorrect policy level provided - " + i);
        }
    }

    private boolean maybeUpdateDefaultFullPolicy(com.android.server.power.batterysaver.BatterySaverPolicy.Policy policy) {
        boolean z = false;
        if (!this.mDefaultFullPolicy.equals(policy)) {
            if (!(!this.mDefaultFullPolicy.equals(this.mFullPolicy))) {
                this.mFullPolicy = policy;
                if (this.mPolicyLevel == 2) {
                    z = true;
                }
            }
            this.mDefaultFullPolicy = policy;
        }
        return z;
    }

    boolean setFullPolicyLocked(com.android.server.power.batterysaver.BatterySaverPolicy.Policy policy) {
        if (policy == null) {
            android.util.Slog.wtf(TAG, "setFullPolicy given null policy");
            return false;
        }
        if (this.mFullPolicy.equals(policy)) {
            return false;
        }
        this.mFullPolicy = policy;
        if (this.mPolicyLevel != 2) {
            return false;
        }
        updatePolicyDependenciesLocked();
        return true;
    }

    boolean setAdaptivePolicyLocked(com.android.server.power.batterysaver.BatterySaverPolicy.Policy policy) {
        if (policy == null) {
            android.util.Slog.wtf(TAG, "setAdaptivePolicy given null policy");
            return false;
        }
        if (this.mAdaptivePolicy.equals(policy)) {
            return false;
        }
        this.mAdaptivePolicy = policy;
        if (this.mPolicyLevel != 1) {
            return false;
        }
        updatePolicyDependenciesLocked();
        return true;
    }

    boolean resetAdaptivePolicyLocked() {
        return setAdaptivePolicyLocked(this.mDefaultAdaptivePolicy);
    }

    private com.android.server.power.batterysaver.BatterySaverPolicy.Policy getCurrentPolicyLocked() {
        return this.mEffectivePolicyRaw;
    }

    private com.android.server.power.batterysaver.BatterySaverPolicy.Policy getCurrentRawPolicyLocked() {
        switch (this.mPolicyLevel) {
            case 1:
                return this.mAdaptivePolicy;
            case 2:
                return this.mFullPolicy;
            default:
                return OFF_POLICY;
        }
    }

    public int getGpsMode() {
        int i;
        synchronized (this.mLock) {
            i = getCurrentPolicyLocked().locationMode;
        }
        return i;
    }

    public boolean isLaunchBoostDisabled() {
        boolean z;
        synchronized (this.mLock) {
            z = getCurrentPolicyLocked().disableLaunchBoost;
        }
        return z;
    }

    boolean shouldAdvertiseIsEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = getCurrentPolicyLocked().advertiseIsEnabled;
        }
        return z;
    }

    public java.lang.String toEventLogString() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = this.mEventLogKeys;
        }
        return str;
    }

    public void dump(java.io.PrintWriter printWriter) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println();
                this.mBatterySavingStats.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Battery saver policy (*NOTE* they only apply when battery saver is ON):");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("Settings: battery_saver_constants");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("value: " + this.mSettings);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Settings: " + this.mDeviceSpecificSettingsSource);
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("value: " + this.mDeviceSpecificSettings);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("DeviceConfig: battery_saver");
                indentingPrintWriter.increaseIndent();
                java.util.Set<java.lang.String> keyset = this.mLastDeviceConfigProperties.getKeyset();
                if (keyset.size() == 0) {
                    indentingPrintWriter.println("N/A");
                } else {
                    for (java.lang.String str : keyset) {
                        indentingPrintWriter.print(str);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mLastDeviceConfigProperties.getString(str, (java.lang.String) null));
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("mAccessibilityEnabled=" + this.mAccessibilityEnabled.get());
                indentingPrintWriter.println("mAutomotiveProjectionActive=" + this.mAutomotiveProjectionActive.get());
                indentingPrintWriter.println("mPolicyLevel=" + this.mPolicyLevel);
                dumpPolicyLocked(indentingPrintWriter, "default full", this.mDefaultFullPolicy);
                dumpPolicyLocked(indentingPrintWriter, "current full", this.mFullPolicy);
                dumpPolicyLocked(indentingPrintWriter, "default adaptive", this.mDefaultAdaptivePolicy);
                dumpPolicyLocked(indentingPrintWriter, "current adaptive", this.mAdaptivePolicy);
                dumpPolicyLocked(indentingPrintWriter, "effective", this.mEffectivePolicyRaw);
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dumpPolicyLocked(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, com.android.server.power.batterysaver.BatterySaverPolicy.Policy policy) {
        indentingPrintWriter.println();
        indentingPrintWriter.println("Policy '" + str + "'");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("advertise_is_enabled=" + policy.advertiseIsEnabled);
        indentingPrintWriter.println("disable_vibration=" + policy.disableVibration);
        indentingPrintWriter.println("disable_animation=" + policy.disableAnimation);
        indentingPrintWriter.println("defer_full_backup=" + policy.deferFullBackup);
        indentingPrintWriter.println("defer_keyvalue_backup=" + policy.deferKeyValueBackup);
        indentingPrintWriter.println("enable_firewall=" + policy.enableFirewall);
        indentingPrintWriter.println("enable_datasaver=" + policy.enableDataSaver);
        indentingPrintWriter.println("disable_launch_boost=" + policy.disableLaunchBoost);
        indentingPrintWriter.println("enable_brightness_adjustment=" + policy.enableAdjustBrightness);
        indentingPrintWriter.println("adjust_brightness_factor=" + policy.adjustBrightnessFactor);
        indentingPrintWriter.println("location_mode=" + policy.locationMode);
        indentingPrintWriter.println("force_all_apps_standby=" + policy.forceAllAppsStandby);
        indentingPrintWriter.println("force_background_check=" + policy.forceBackgroundCheck);
        indentingPrintWriter.println("disable_optional_sensors=" + policy.disableOptionalSensors);
        indentingPrintWriter.println("disable_aod=" + policy.disableAod);
        indentingPrintWriter.println("soundtrigger_mode=" + policy.soundTriggerMode);
        indentingPrintWriter.println("enable_quick_doze=" + policy.enableQuickDoze);
        indentingPrintWriter.println("enable_night_mode=" + policy.enableNightMode);
        indentingPrintWriter.decreaseIndent();
    }

    private void dumpMap(java.io.PrintWriter printWriter, android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap) {
        if (arrayMap == null || arrayMap.size() == 0) {
            printWriter.println("N/A");
            return;
        }
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            printWriter.print(arrayMap.keyAt(i));
            printWriter.print(": '");
            printWriter.print(arrayMap.valueAt(i));
            printWriter.println("'");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class PolicyBoolean {
        private final java.lang.String mDebugName;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean mValue;

        private PolicyBoolean(java.lang.String str) {
            this.mDebugName = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(boolean z) {
            synchronized (com.android.server.power.batterysaver.BatterySaverPolicy.this.mLock) {
                this.mValue = z;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean get() {
            boolean z;
            synchronized (com.android.server.power.batterysaver.BatterySaverPolicy.this.mLock) {
                z = this.mValue;
            }
            return z;
        }

        @com.android.internal.annotations.VisibleForTesting
        void update(boolean z) {
            synchronized (com.android.server.power.batterysaver.BatterySaverPolicy.this.mLock) {
                try {
                    if (this.mValue != z) {
                        android.util.Slog.d(com.android.server.power.batterysaver.BatterySaverPolicy.TAG, this.mDebugName + " changed to " + z + ", updating policy.");
                        this.mValue = z;
                        com.android.server.power.batterysaver.BatterySaverPolicy.this.updatePolicyDependenciesLocked();
                        com.android.server.power.batterysaver.BatterySaverPolicy.this.maybeNotifyListenersOfPolicyChange();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
