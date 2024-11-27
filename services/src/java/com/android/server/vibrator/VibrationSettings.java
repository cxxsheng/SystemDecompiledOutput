package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class VibrationSettings {
    private static final java.lang.String TAG = "VibrationSettings";
    private static final int VIBRATE_ON_DISABLED_USAGE_ALLOWED = 66;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.media.AudioManager mAudioManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mBatterySaverMode;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseIntArray mCurrentVibrationIntensities;
    private final android.util.SparseArray<android.os.VibrationEffect> mFallbackEffects;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mKeyboardVibrationOn;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<com.android.server.vibrator.VibrationSettings.OnVibratorSettingsChanged> mListeners;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mOnWirelessCharger;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.os.PowerManagerInternal mPowerManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mRingerMode;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.vibrator.VibrationSettings.SettingsBroadcastReceiver mSettingChangeReceiver;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.vibrator.VibrationSettings.SettingsContentObserver mSettingObserver;
    private final java.lang.String mSystemUiPackage;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.vibrator.VibrationSettings.VibrationUidObserver mUidObserver;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.vibrator.VibrationSettings.VibrationUserSwitchObserver mUserSwitchObserver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mVibrateInputDevices;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mVibrateOn;
    private final android.os.vibrator.VibrationConfig mVibrationConfig;

    @android.annotation.Nullable
    private com.android.server.companion.virtual.VirtualDeviceManagerInternal mVirtualDeviceManagerInternal;
    private static final java.util.Set<java.lang.Integer> BACKGROUND_PROCESS_USAGE_ALLOWLIST = new java.util.HashSet(java.util.Arrays.asList(33, 17, 49, 65, 50, 34));
    private static final java.util.Set<java.lang.Integer> BATTERY_SAVER_USAGE_ALLOWLIST = new java.util.HashSet(java.util.Arrays.asList(33, 17, 65, 34, 50));
    private static final java.util.Set<java.lang.Integer> SYSTEM_VIBRATION_SCREEN_OFF_USAGE_ALLOWLIST = new java.util.HashSet(java.util.Arrays.asList(18, 66, 34, 50));
    private static final java.util.Set<java.lang.Integer> POWER_MANAGER_SLEEP_REASON_ALLOWLIST = new java.util.HashSet(java.util.Arrays.asList(9, 2));
    private static final android.content.IntentFilter INTERNAL_RINGER_MODE_CHANGED_INTENT_FILTER = new android.content.IntentFilter("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");

    interface OnVibratorSettingsChanged {
        void onChange();
    }

    VibrationSettings(android.content.Context context, android.os.Handler handler) {
        this(context, handler, new android.os.vibrator.VibrationConfig(context.getResources()));
    }

    @com.android.internal.annotations.VisibleForTesting
    VibrationSettings(android.content.Context context, android.os.Handler handler, android.os.vibrator.VibrationConfig vibrationConfig) {
        this.mLock = new java.lang.Object();
        this.mListeners = new java.util.ArrayList();
        this.mCurrentVibrationIntensities = new android.util.SparseIntArray();
        this.mContext = context;
        this.mVibrationConfig = vibrationConfig;
        this.mSettingObserver = new com.android.server.vibrator.VibrationSettings.SettingsContentObserver(handler);
        this.mSettingChangeReceiver = new com.android.server.vibrator.VibrationSettings.SettingsBroadcastReceiver();
        this.mUidObserver = new com.android.server.vibrator.VibrationSettings.VibrationUidObserver();
        this.mUserSwitchObserver = new com.android.server.vibrator.VibrationSettings.VibrationUserSwitchObserver();
        this.mSystemUiPackage = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getSystemUiServiceComponent().getPackageName();
        android.os.VibrationEffect createEffectFromResource = createEffectFromResource(android.R.array.config_udfps_sensor_props);
        android.os.VibrationEffect createEffectFromResource2 = createEffectFromResource(android.R.array.config_display_no_service_when_sim_unready);
        android.os.VibrationEffect createEffectFromResource3 = createEffectFromResource(android.R.array.config_locationProviderPackageNames);
        android.os.VibrationEffect createEffectFromResource4 = createEffectFromResource(android.R.array.config_clockTickVibePattern);
        this.mFallbackEffects = new android.util.SparseArray<>();
        this.mFallbackEffects.put(0, createEffectFromResource);
        this.mFallbackEffects.put(1, createEffectFromResource2);
        this.mFallbackEffects.put(2, createEffectFromResource4);
        this.mFallbackEffects.put(5, createEffectFromResource3);
        this.mFallbackEffects.put(21, android.os.VibrationEffect.get(2, false));
        update();
    }

    public void onSystemReady() {
        android.content.Intent registerReceiver;
        android.os.PowerManagerInternal powerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
        android.media.AudioManager audioManager = (android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class);
        int ringerModeInternal = audioManager.getRingerModeInternal();
        synchronized (this.mLock) {
            this.mPowerManagerInternal = powerManagerInternal;
            this.mAudioManager = audioManager;
            this.mRingerMode = ringerModeInternal;
        }
        try {
            android.app.ActivityManager.getService().registerUidObserver(this.mUidObserver, 3, -1, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
        }
        try {
            android.app.ActivityManager.getService().registerUserSwitchObserver(this.mUserSwitchObserver, TAG);
        } catch (android.os.RemoteException e2) {
        }
        powerManagerInternal.registerLowPowerModeObserver(new android.os.PowerManagerInternal.LowPowerModeListener() { // from class: com.android.server.vibrator.VibrationSettings.1
            public int getServiceType() {
                return 2;
            }

            public void onLowPowerModeChanged(android.os.PowerSaveState powerSaveState) {
                boolean z;
                synchronized (com.android.server.vibrator.VibrationSettings.this.mLock) {
                    z = powerSaveState.batterySaverEnabled != com.android.server.vibrator.VibrationSettings.this.mBatterySaverMode;
                    com.android.server.vibrator.VibrationSettings.this.mBatterySaverMode = powerSaveState.batterySaverEnabled;
                }
                if (z) {
                    com.android.server.vibrator.VibrationSettings.this.notifyListeners();
                }
            }
        });
        registerSettingsChangeReceiver(INTERNAL_RINGER_MODE_CHANGED_INTENT_FILTER);
        registerSettingsObserver(android.provider.Settings.System.getUriFor("vibrate_input_devices"));
        registerSettingsObserver(android.provider.Settings.System.getUriFor("vibrate_on"));
        registerSettingsObserver(android.provider.Settings.System.getUriFor("haptic_feedback_enabled"));
        registerSettingsObserver(android.provider.Settings.System.getUriFor("alarm_vibration_intensity"));
        registerSettingsObserver(android.provider.Settings.System.getUriFor("haptic_feedback_intensity"));
        registerSettingsObserver(android.provider.Settings.System.getUriFor("hardware_haptic_feedback_intensity"));
        registerSettingsObserver(android.provider.Settings.System.getUriFor("media_vibration_intensity"));
        registerSettingsObserver(android.provider.Settings.System.getUriFor("notification_vibration_intensity"));
        registerSettingsObserver(android.provider.Settings.System.getUriFor("ring_vibration_intensity"));
        registerSettingsObserver(android.provider.Settings.System.getUriFor("keyboard_vibration_enabled"));
        if (this.mVibrationConfig.ignoreVibrationsOnWirelessCharger() && (registerReceiver = this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.vibrator.VibrationSettings.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.vibrator.VibrationSettings.this.updateBatteryInfo(intent);
            }
        }, new android.content.IntentFilter("android.intent.action.BATTERY_CHANGED"), 4)) != null) {
            updateBatteryInfo(registerReceiver);
        }
        update();
    }

    public void addListener(com.android.server.vibrator.VibrationSettings.OnVibratorSettingsChanged onVibratorSettingsChanged) {
        synchronized (this.mLock) {
            try {
                if (!this.mListeners.contains(onVibratorSettingsChanged)) {
                    this.mListeners.add(onVibratorSettingsChanged);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeListener(com.android.server.vibrator.VibrationSettings.OnVibratorSettingsChanged onVibratorSettingsChanged) {
        synchronized (this.mLock) {
            this.mListeners.remove(onVibratorSettingsChanged);
        }
    }

    public int getRampStepDuration() {
        return this.mVibrationConfig.getRampStepDurationMs();
    }

    public int getRampDownDuration() {
        return this.mVibrationConfig.getRampDownDurationMs();
    }

    public int getDefaultIntensity(int i) {
        return this.mVibrationConfig.getDefaultVibrationIntensity(i);
    }

    public int getCurrentIntensity(int i) {
        int i2;
        int defaultIntensity = getDefaultIntensity(i);
        synchronized (this.mLock) {
            i2 = this.mCurrentVibrationIntensities.get(i, defaultIntensity);
        }
        return i2;
    }

    public int getRequestVibrationParamsTimeoutMs() {
        return this.mVibrationConfig.getRequestVibrationParamsTimeoutMs();
    }

    public int[] getRequestVibrationParamsForUsages() {
        return this.mVibrationConfig.getRequestVibrationParamsForUsages();
    }

    public android.os.VibrationEffect getFallbackEffect(int i) {
        return this.mFallbackEffects.get(i);
    }

    public boolean shouldVibrateInputDevices() {
        return this.mVibrateInputDevices;
    }

    @android.annotation.Nullable
    public com.android.server.vibrator.Vibration.Status shouldIgnoreVibration(@android.annotation.NonNull com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
        int usage = callerInfo.attrs.getUsage();
        synchronized (this.mLock) {
            try {
                if (!this.mUidObserver.isUidForeground(callerInfo.uid) && !BACKGROUND_PROCESS_USAGE_ALLOWLIST.contains(java.lang.Integer.valueOf(usage))) {
                    return com.android.server.vibrator.Vibration.Status.IGNORED_BACKGROUND;
                }
                if (callerInfo.deviceId != 0 && callerInfo.deviceId != -1) {
                    return com.android.server.vibrator.Vibration.Status.IGNORED_FROM_VIRTUAL_DEVICE;
                }
                if (callerInfo.deviceId == -1 && isAppRunningOnAnyVirtualDevice(callerInfo.uid)) {
                    return com.android.server.vibrator.Vibration.Status.IGNORED_FROM_VIRTUAL_DEVICE;
                }
                if (this.mBatterySaverMode && !BATTERY_SAVER_USAGE_ALLOWLIST.contains(java.lang.Integer.valueOf(usage))) {
                    return com.android.server.vibrator.Vibration.Status.IGNORED_FOR_POWER;
                }
                if (!callerInfo.attrs.isFlagSet(2) && !shouldVibrateForUserSetting(callerInfo)) {
                    return com.android.server.vibrator.Vibration.Status.IGNORED_FOR_SETTINGS;
                }
                if (!callerInfo.attrs.isFlagSet(1) && !shouldVibrateForRingerModeLocked(usage)) {
                    return com.android.server.vibrator.Vibration.Status.IGNORED_FOR_RINGER_MODE;
                }
                if (this.mVibrationConfig.ignoreVibrationsOnWirelessCharger() && this.mOnWirelessCharger) {
                    return com.android.server.vibrator.Vibration.Status.IGNORED_ON_WIRELESS_CHARGER;
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean shouldCancelVibrationOnScreenOff(@android.annotation.NonNull com.android.server.vibrator.Vibration.CallerInfo callerInfo, long j) {
        android.os.PowerManagerInternal powerManagerInternal;
        synchronized (this.mLock) {
            powerManagerInternal = this.mPowerManagerInternal;
        }
        if (powerManagerInternal != null) {
            android.os.PowerManager.SleepData lastGoToSleep = powerManagerInternal.getLastGoToSleep();
            if (lastGoToSleep.goToSleepUptimeMillis < j || POWER_MANAGER_SLEEP_REASON_ALLOWLIST.contains(java.lang.Integer.valueOf(lastGoToSleep.goToSleepReason))) {
                android.util.Slog.d(TAG, "Ignoring screen off event triggered at uptime " + lastGoToSleep.goToSleepUptimeMillis + " for reason " + android.os.PowerManager.sleepReasonToString(lastGoToSleep.goToSleepReason));
                return false;
            }
        }
        if (SYSTEM_VIBRATION_SCREEN_OFF_USAGE_ALLOWLIST.contains(java.lang.Integer.valueOf(callerInfo.attrs.getUsage()))) {
            return (callerInfo.uid == 1000 || callerInfo.uid == 0 || this.mSystemUiPackage.equals(callerInfo.opPkg)) ? false : true;
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean shouldVibrateForRingerModeLocked(int i) {
        return ((i == 33 || i == 49) && this.mRingerMode == 0) ? false : true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean shouldVibrateForUserSetting(com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
        int usage = callerInfo.attrs.getUsage();
        if (!this.mVibrateOn && 66 != usage) {
            return false;
        }
        if (android.os.vibrator.Flags.keyboardCategoryEnabled() && this.mVibrationConfig.hasFixedKeyboardAmplitude()) {
            int category = callerInfo.attrs.getCategory();
            if (usage == 18 && category == 1) {
                return this.mKeyboardVibrationOn;
            }
        }
        return getCurrentIntensity(usage) != 0;
    }

    void update() {
        updateSettings(-2);
        updateRingerMode();
        notifyListeners();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSettings(int i) {
        synchronized (this.mLock) {
            try {
                boolean z = true;
                this.mVibrateInputDevices = loadSystemSetting("vibrate_input_devices", 0, i) > 0;
                this.mVibrateOn = loadSystemSetting("vibrate_on", 1, i) > 0;
                if (loadSystemSetting("keyboard_vibration_enabled", this.mVibrationConfig.isDefaultKeyboardVibrationEnabled() ? 1 : 0, i) <= 0) {
                    z = false;
                }
                this.mKeyboardVibrationOn = z;
                int intensity = toIntensity(loadSystemSetting("alarm_vibration_intensity", -1, i), getDefaultIntensity(17));
                int defaultIntensity = getDefaultIntensity(18);
                int intensity2 = toIntensity(loadSystemSetting("haptic_feedback_intensity", -1, i), defaultIntensity);
                int positiveIntensity = toPositiveIntensity(intensity2, defaultIntensity);
                int intensity3 = toIntensity(loadSystemSetting("hardware_haptic_feedback_intensity", -1, i), positiveIntensity);
                int intensity4 = toIntensity(loadSystemSetting("media_vibration_intensity", -1, i), getDefaultIntensity(19));
                int defaultIntensity2 = getDefaultIntensity(49);
                int intensity5 = toIntensity(loadSystemSetting("notification_vibration_intensity", -1, i), defaultIntensity2);
                int positiveIntensity2 = toPositiveIntensity(intensity5, defaultIntensity2);
                int intensity6 = toIntensity(loadSystemSetting("ring_vibration_intensity", -1, i), getDefaultIntensity(33));
                this.mCurrentVibrationIntensities.clear();
                this.mCurrentVibrationIntensities.put(17, intensity);
                this.mCurrentVibrationIntensities.put(49, intensity5);
                this.mCurrentVibrationIntensities.put(19, intensity4);
                this.mCurrentVibrationIntensities.put(0, intensity4);
                this.mCurrentVibrationIntensities.put(33, intensity6);
                this.mCurrentVibrationIntensities.put(65, positiveIntensity2);
                this.mCurrentVibrationIntensities.put(50, intensity3);
                this.mCurrentVibrationIntensities.put(34, intensity3);
                if (!loadBooleanSetting("haptic_feedback_enabled", i)) {
                    this.mCurrentVibrationIntensities.put(18, 0);
                } else {
                    this.mCurrentVibrationIntensities.put(18, intensity2);
                }
                this.mCurrentVibrationIntensities.put(66, positiveIntensity);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRingerMode() {
        int ringerModeInternal;
        synchronized (this.mLock) {
            if (this.mAudioManager == null) {
                ringerModeInternal = 0;
            } else {
                ringerModeInternal = this.mAudioManager.getRingerModeInternal();
            }
            this.mRingerMode = ringerModeInternal;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBatteryInfo(android.content.Intent intent) {
        int intExtra = intent.getIntExtra("plugged", 0);
        synchronized (this.mLock) {
            this.mOnWirelessCharger = intExtra == 4;
        }
    }

    public java.lang.String toString() {
        java.lang.String str;
        synchronized (this.mLock) {
            try {
                java.lang.StringBuilder sb = new java.lang.StringBuilder("{");
                for (int i = 0; i < this.mCurrentVibrationIntensities.size(); i++) {
                    int keyAt = this.mCurrentVibrationIntensities.keyAt(i);
                    int valueAt = this.mCurrentVibrationIntensities.valueAt(i);
                    sb.append(android.os.VibrationAttributes.usageToString(keyAt));
                    sb.append("=(");
                    sb.append(intensityToString(valueAt));
                    sb.append(",default:");
                    sb.append(intensityToString(getDefaultIntensity(keyAt)));
                    sb.append("), ");
                }
                sb.append('}');
                str = "VibrationSettings{mVibratorConfig=" + this.mVibrationConfig + ", mVibrateOn=" + this.mVibrateOn + ", mKeyboardVibrationOn=" + (this.mKeyboardVibrationOn + " (default: " + this.mVibrationConfig.isDefaultKeyboardVibrationEnabled() + ")") + ", mVibrateInputDevices=" + this.mVibrateInputDevices + ", mBatterySaverMode=" + this.mBatterySaverMode + ", mRingerMode=" + ringerModeToString(this.mRingerMode) + ", mOnWirelessCharger=" + this.mOnWirelessCharger + ", mVibrationIntensities=" + ((java.lang.Object) sb) + ", mProcStatesCache=" + this.mUidObserver.mProcStatesCache + '}';
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return str;
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("VibrationSettings:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("vibrateOn = " + this.mVibrateOn);
                indentingPrintWriter.println("keyboardVibrationOn = " + this.mKeyboardVibrationOn + ", default: " + this.mVibrationConfig.isDefaultKeyboardVibrationEnabled());
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("vibrateInputDevices = ");
                sb.append(this.mVibrateInputDevices);
                indentingPrintWriter.println(sb.toString());
                indentingPrintWriter.println("batterySaverMode = " + this.mBatterySaverMode);
                indentingPrintWriter.println("ringerMode = " + ringerModeToString(this.mRingerMode));
                indentingPrintWriter.println("onWirelessCharger = " + this.mOnWirelessCharger);
                indentingPrintWriter.println("processStateCache size = " + this.mUidObserver.mProcStatesCache.size());
                indentingPrintWriter.println("VibrationIntensities:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mCurrentVibrationIntensities.size(); i++) {
                    int keyAt = this.mCurrentVibrationIntensities.keyAt(i);
                    indentingPrintWriter.println(android.os.VibrationAttributes.usageToString(keyAt) + " = " + intensityToString(this.mCurrentVibrationIntensities.valueAt(i)) + ", default: " + intensityToString(getDefaultIntensity(keyAt)));
                }
                indentingPrintWriter.decreaseIndent();
                this.mVibrationConfig.dumpWithoutDefaultSettings(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
        synchronized (this.mLock) {
            protoOutputStream.write(1133871366168L, this.mVibrateOn);
            protoOutputStream.write(1133871366169L, this.mKeyboardVibrationOn);
            protoOutputStream.write(1133871366150L, this.mBatterySaverMode);
            protoOutputStream.write(1120986464274L, getCurrentIntensity(17));
            protoOutputStream.write(1120986464275L, getDefaultIntensity(17));
            protoOutputStream.write(1120986464278L, getCurrentIntensity(50));
            protoOutputStream.write(1120986464279L, getDefaultIntensity(50));
            protoOutputStream.write(1120986464263L, getCurrentIntensity(18));
            protoOutputStream.write(1120986464264L, getDefaultIntensity(18));
            protoOutputStream.write(1120986464276L, getCurrentIntensity(19));
            protoOutputStream.write(1120986464277L, getDefaultIntensity(19));
            protoOutputStream.write(1120986464265L, getCurrentIntensity(49));
            protoOutputStream.write(1120986464266L, getDefaultIntensity(49));
            protoOutputStream.write(1120986464267L, getCurrentIntensity(33));
            protoOutputStream.write(1120986464268L, getDefaultIntensity(33));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyListeners() {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new java.util.ArrayList(this.mListeners);
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((com.android.server.vibrator.VibrationSettings.OnVibratorSettingsChanged) it.next()).onChange();
        }
    }

    private static java.lang.String intensityToString(int i) {
        switch (i) {
            case 0:
                return "OFF";
            case 1:
                return "LOW";
            case 2:
                return "MEDIUM";
            case 3:
                return com.android.server.utils.PriorityDump.PRIORITY_ARG_HIGH;
            default:
                return "UNKNOWN INTENSITY " + i;
        }
    }

    private static java.lang.String ringerModeToString(int i) {
        switch (i) {
            case 0:
                return "silent";
            case 1:
                return "vibrate";
            case 2:
                return "normal";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    private int toPositiveIntensity(int i, int i2) {
        if (i == 0) {
            return i2;
        }
        return toIntensity(i, i2);
    }

    private int toIntensity(int i, int i2) {
        if (i < 0 || i > 3) {
            return i2;
        }
        return i;
    }

    private boolean loadBooleanSetting(java.lang.String str, int i) {
        return loadSystemSetting(str, 0, i) != 0;
    }

    private int loadSystemSetting(java.lang.String str, int i, int i2) {
        return android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), str, i, i2);
    }

    private void registerSettingsObserver(android.net.Uri uri) {
        this.mContext.getContentResolver().registerContentObserver(uri, true, this.mSettingObserver, -1);
    }

    private void registerSettingsChangeReceiver(android.content.IntentFilter intentFilter) {
        this.mContext.registerReceiver(this.mSettingChangeReceiver, intentFilter, 2);
    }

    @android.annotation.Nullable
    private android.os.VibrationEffect createEffectFromResource(int i) {
        return createEffectFromResource(this.mContext.getResources(), i);
    }

    @android.annotation.Nullable
    static android.os.VibrationEffect createEffectFromResource(android.content.res.Resources resources, int i) {
        return createEffectFromTimings(getLongIntArray(resources, i));
    }

    @android.annotation.Nullable
    private static android.os.VibrationEffect createEffectFromTimings(@android.annotation.Nullable long[] jArr) {
        if (jArr == null || jArr.length == 0) {
            return null;
        }
        if (jArr.length == 1) {
            return android.os.VibrationEffect.createOneShot(jArr[0], -1);
        }
        return android.os.VibrationEffect.createWaveform(jArr, -1);
    }

    private static long[] getLongIntArray(android.content.res.Resources resources, int i) {
        int[] intArray = resources.getIntArray(i);
        if (intArray == null) {
            return null;
        }
        long[] jArr = new long[intArray.length];
        for (int i2 = 0; i2 < intArray.length; i2++) {
            jArr[i2] = intArray[i2];
        }
        return jArr;
    }

    private boolean isAppRunningOnAnyVirtualDevice(int i) {
        if (this.mVirtualDeviceManagerInternal == null) {
            this.mVirtualDeviceManagerInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
        }
        return this.mVirtualDeviceManagerInternal != null && this.mVirtualDeviceManagerInternal.isAppRunningOnAnyVirtualDevice(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    final class SettingsContentObserver extends android.database.ContentObserver {
        SettingsContentObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            com.android.server.vibrator.VibrationSettings.this.updateSettings(-2);
            com.android.server.vibrator.VibrationSettings.this.notifyListeners();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class SettingsBroadcastReceiver extends android.content.BroadcastReceiver {
        SettingsBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(intent.getAction())) {
                com.android.server.vibrator.VibrationSettings.this.updateRingerMode();
                com.android.server.vibrator.VibrationSettings.this.notifyListeners();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class VibrationUidObserver extends android.app.UidObserver {
        private final android.util.SparseArray<java.lang.Integer> mProcStatesCache = new android.util.SparseArray<>();

        VibrationUidObserver() {
        }

        public boolean isUidForeground(int i) {
            boolean z;
            synchronized (this) {
                z = this.mProcStatesCache.get(i, 6).intValue() <= 6;
            }
            return z;
        }

        public void onUidGone(int i, boolean z) {
            synchronized (this) {
                this.mProcStatesCache.delete(i);
            }
        }

        public void onUidStateChanged(int i, int i2, long j, int i3) {
            synchronized (this) {
                this.mProcStatesCache.put(i, java.lang.Integer.valueOf(i2));
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class VibrationUserSwitchObserver extends android.app.SynchronousUserSwitchObserver {
        VibrationUserSwitchObserver() {
        }

        public void onUserSwitching(int i) {
            com.android.server.vibrator.VibrationSettings.this.updateSettings(i);
            com.android.server.vibrator.VibrationSettings.this.notifyListeners();
        }

        public void onUserSwitchComplete(int i) {
            com.android.server.vibrator.VibrationSettings.this.update();
        }
    }
}
