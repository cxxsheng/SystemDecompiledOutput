package com.android.server.power.batterysaver;

/* loaded from: classes2.dex */
public class BatterySaverController implements com.android.server.power.batterysaver.BatterySaverPolicy.BatterySaverPolicyListener {
    static final boolean DEBUG = false;
    public static final int REASON_ADAPTIVE_DYNAMIC_POWER_SAVINGS_CHANGED = 11;
    public static final int REASON_DYNAMIC_POWER_SAVINGS_AUTOMATIC_OFF = 10;
    public static final int REASON_DYNAMIC_POWER_SAVINGS_AUTOMATIC_ON = 9;
    public static final int REASON_FULL_POWER_SAVINGS_CHANGED = 13;
    public static final int REASON_INTERACTIVE_CHANGED = 5;
    public static final int REASON_MANUAL_OFF = 3;
    public static final int REASON_MANUAL_ON = 2;
    public static final int REASON_PERCENTAGE_AUTOMATIC_OFF = 1;
    public static final int REASON_PERCENTAGE_AUTOMATIC_ON = 0;
    public static final int REASON_PLUGGED_IN = 7;
    public static final int REASON_POLICY_CHANGED = 6;
    public static final int REASON_SETTING_CHANGED = 8;
    public static final int REASON_STICKY_RESTORE = 4;
    public static final int REASON_TIMEOUT = 12;
    static final java.lang.String TAG = "BatterySaverController";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mAdaptiveEnabledRaw;
    private boolean mAdaptivePreviouslyEnabled;
    private final com.android.server.power.batterysaver.BatterySaverPolicy mBatterySaverPolicy;
    private final com.android.server.power.batterysaver.BatterySavingStats mBatterySavingStats;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mFullEnabledRaw;
    private boolean mFullPreviouslyEnabled;
    private final com.android.server.power.batterysaver.BatterySaverController.MyHandler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsInteractive;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsPluggedIn;
    private final java.lang.Object mLock;
    private android.os.PowerManager mPowerManager;

    @android.annotation.Nullable
    private java.util.Optional<java.lang.String> mPowerSaveModeChangedListenerPackage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<android.os.PowerManagerInternal.LowPowerModeListener> mListeners = new java.util.ArrayList<>();
    private final android.content.BroadcastReceiver mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.power.batterysaver.BatterySaverController.1
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            java.lang.String action = intent.getAction();
            boolean z = true;
            switch (action.hashCode()) {
                case -2128145023:
                    if (action.equals("android.intent.action.SCREEN_OFF")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1538406691:
                    if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1454123155:
                    if (action.equals("android.intent.action.SCREEN_ON")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 498807504:
                    if (action.equals("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 870701415:
                    if (action.equals("android.os.action.DEVICE_IDLE_MODE_CHANGED")) {
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
                case 1:
                    if (!com.android.server.power.batterysaver.BatterySaverController.this.isPolicyEnabled()) {
                        com.android.server.power.batterysaver.BatterySaverController.this.updateBatterySavingStats();
                        return;
                    } else {
                        com.android.server.power.batterysaver.BatterySaverController.this.mHandler.postStateChanged(false, 5);
                        return;
                    }
                case 2:
                    synchronized (com.android.server.power.batterysaver.BatterySaverController.this.mLock) {
                        com.android.server.power.batterysaver.BatterySaverController batterySaverController = com.android.server.power.batterysaver.BatterySaverController.this;
                        if (intent.getIntExtra("plugged", 0) == 0) {
                            z = false;
                        }
                        batterySaverController.mIsPluggedIn = z;
                        break;
                    }
                case 3:
                case 4:
                    break;
                default:
                    return;
            }
            com.android.server.power.batterysaver.BatterySaverController.this.updateBatterySavingStats();
        }
    };

    static java.lang.String reasonToString(int i) {
        switch (i) {
            case 0:
                return "Percentage Auto ON";
            case 1:
                return "Percentage Auto OFF";
            case 2:
                return "Manual ON";
            case 3:
                return "Manual OFF";
            case 4:
                return "Sticky restore";
            case 5:
                return "Interactivity changed";
            case 6:
                return "Policy changed";
            case 7:
                return "Plugged in";
            case 8:
                return "Setting changed";
            case 9:
                return "Dynamic Warning Auto ON";
            case 10:
                return "Dynamic Warning Auto OFF";
            case 11:
                return "Adaptive Power Savings changed";
            case 12:
                return "timeout";
            case 13:
                return "Full Power Savings changed";
            default:
                return "Unknown reason: " + i;
        }
    }

    public BatterySaverController(java.lang.Object obj, android.content.Context context, android.os.Looper looper, com.android.server.power.batterysaver.BatterySaverPolicy batterySaverPolicy, com.android.server.power.batterysaver.BatterySavingStats batterySavingStats) {
        this.mLock = obj;
        this.mContext = context;
        this.mHandler = new com.android.server.power.batterysaver.BatterySaverController.MyHandler(looper);
        this.mBatterySaverPolicy = batterySaverPolicy;
        this.mBatterySaverPolicy.addListener(this);
        this.mBatterySavingStats = batterySavingStats;
        android.os.PowerManager.invalidatePowerSaveModeCaches();
    }

    public void addListener(android.os.PowerManagerInternal.LowPowerModeListener lowPowerModeListener) {
        synchronized (this.mLock) {
            this.mListeners.add(lowPowerModeListener);
        }
    }

    public void systemReady() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        intentFilter.addAction("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        this.mHandler.postSystemReady();
    }

    private android.os.PowerManager getPowerManager() {
        if (this.mPowerManager == null) {
            android.os.PowerManager powerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
            java.util.Objects.requireNonNull(powerManager);
            this.mPowerManager = powerManager;
        }
        return this.mPowerManager;
    }

    @Override // com.android.server.power.batterysaver.BatterySaverPolicy.BatterySaverPolicyListener
    public void onBatterySaverPolicyChanged(com.android.server.power.batterysaver.BatterySaverPolicy batterySaverPolicy) {
        if (!isPolicyEnabled()) {
            return;
        }
        this.mHandler.postStateChanged(true, 6);
    }

    private class MyHandler extends android.os.Handler {
        private static final int ARG_DONT_SEND_BROADCAST = 0;
        private static final int ARG_SEND_BROADCAST = 1;
        private static final int MSG_STATE_CHANGED = 1;
        private static final int MSG_SYSTEM_READY = 2;

        public MyHandler(android.os.Looper looper) {
            super(looper);
        }

        void postStateChanged(boolean z, int i) {
            obtainMessage(1, z ? 1 : 0, i).sendToTarget();
        }

        public void postSystemReady() {
            obtainMessage(2, 0, 0).sendToTarget();
        }

        @Override // android.os.Handler
        public void dispatchMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.power.batterysaver.BatterySaverController.this.handleBatterySaverStateChanged(message.arg1 == 1, message.arg2);
                    break;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void enableBatterySaver(boolean z, int i) {
        synchronized (this.mLock) {
            try {
                if (getFullEnabledLocked() == z) {
                    return;
                }
                setFullEnabledLocked(z);
                if (updatePolicyLevelLocked()) {
                    this.mHandler.postStateChanged(true, i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean updatePolicyLevelLocked() {
        if (getFullEnabledLocked()) {
            return this.mBatterySaverPolicy.setPolicyLevel(2);
        }
        if (getAdaptiveEnabledLocked()) {
            return this.mBatterySaverPolicy.setPolicyLevel(1);
        }
        return this.mBatterySaverPolicy.setPolicyLevel(0);
    }

    android.os.BatterySaverPolicyConfig getPolicyLocked(int i) {
        return this.mBatterySaverPolicy.getPolicyLocked(i).toConfig();
    }

    public boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = getFullEnabledLocked() || (getAdaptiveEnabledLocked() && this.mBatterySaverPolicy.shouldAdvertiseIsEnabled());
            } finally {
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPolicyEnabled() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = getFullEnabledLocked() || getAdaptiveEnabledLocked();
            } finally {
            }
        }
        return z;
    }

    boolean isFullEnabled() {
        boolean fullEnabledLocked;
        synchronized (this.mLock) {
            fullEnabledLocked = getFullEnabledLocked();
        }
        return fullEnabledLocked;
    }

    boolean setFullPolicyLocked(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig, int i) {
        return setFullPolicyLocked(com.android.server.power.batterysaver.BatterySaverPolicy.Policy.fromConfig(batterySaverPolicyConfig), i);
    }

    boolean setFullPolicyLocked(com.android.server.power.batterysaver.BatterySaverPolicy.Policy policy, int i) {
        if (this.mBatterySaverPolicy.setFullPolicyLocked(policy)) {
            this.mHandler.postStateChanged(true, i);
            return true;
        }
        return false;
    }

    boolean isAdaptiveEnabled() {
        boolean adaptiveEnabledLocked;
        synchronized (this.mLock) {
            adaptiveEnabledLocked = getAdaptiveEnabledLocked();
        }
        return adaptiveEnabledLocked;
    }

    boolean setAdaptivePolicyLocked(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig, int i) {
        return setAdaptivePolicyLocked(com.android.server.power.batterysaver.BatterySaverPolicy.Policy.fromConfig(batterySaverPolicyConfig), i);
    }

    boolean setAdaptivePolicyLocked(com.android.server.power.batterysaver.BatterySaverPolicy.Policy policy, int i) {
        if (this.mBatterySaverPolicy.setAdaptivePolicyLocked(policy)) {
            this.mHandler.postStateChanged(true, i);
            return true;
        }
        return false;
    }

    boolean resetAdaptivePolicyLocked(int i) {
        if (this.mBatterySaverPolicy.resetAdaptivePolicyLocked()) {
            this.mHandler.postStateChanged(true, i);
            return true;
        }
        return false;
    }

    boolean setAdaptivePolicyEnabledLocked(boolean z, int i) {
        if (getAdaptiveEnabledLocked() == z) {
            return false;
        }
        setAdaptiveEnabledLocked(z);
        if (!updatePolicyLevelLocked()) {
            return false;
        }
        this.mHandler.postStateChanged(true, i);
        return true;
    }

    public boolean isInteractive() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsInteractive;
        }
        return z;
    }

    public com.android.server.power.batterysaver.BatterySaverPolicy getBatterySaverPolicy() {
        return this.mBatterySaverPolicy;
    }

    public boolean isLaunchBoostDisabled() {
        return isPolicyEnabled() && this.mBatterySaverPolicy.isLaunchBoostDisabled();
    }

    /* JADX WARN: Multi-variable type inference failed */
    void handleBatterySaverStateChanged(boolean z, int i) {
        int i2;
        android.os.PowerManagerInternal.LowPowerModeListener[] lowPowerModeListenerArr;
        boolean isInteractive = getPowerManager().isInteractive();
        synchronized (this.mLock) {
            try {
                byte b = getFullEnabledLocked() || getAdaptiveEnabledLocked();
                com.android.server.EventLogTags.writeBatterySaverMode(this.mFullPreviouslyEnabled ? 1 : 0, this.mAdaptivePreviouslyEnabled ? 1 : 0, getFullEnabledLocked() ? 1 : 0, getAdaptiveEnabledLocked() ? 1 : 0, isInteractive ? 1 : 0, b != false ? this.mBatterySaverPolicy.toEventLogString() : "", i);
                this.mFullPreviouslyEnabled = getFullEnabledLocked();
                this.mAdaptivePreviouslyEnabled = getAdaptiveEnabledLocked();
                lowPowerModeListenerArr = (android.os.PowerManagerInternal.LowPowerModeListener[]) this.mListeners.toArray(new android.os.PowerManagerInternal.LowPowerModeListener[0]);
                this.mIsInteractive = isInteractive;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.os.PowerManagerInternal powerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
        if (powerManagerInternal != null) {
            powerManagerInternal.setPowerMode(1, isEnabled());
        }
        updateBatterySavingStats();
        if (z) {
            android.content.Intent intent = new android.content.Intent("android.os.action.POWER_SAVE_MODE_CHANGED");
            intent.addFlags(1073741824);
            this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
            if (getPowerSaveModeChangedListenerPackage().isPresent()) {
                this.mContext.sendBroadcastAsUser(new android.content.Intent("android.os.action.POWER_SAVE_MODE_CHANGED").setPackage(getPowerSaveModeChangedListenerPackage().get()).addFlags(android.hardware.audio.common.V2_0.AudioFormat.EVRCB), android.os.UserHandle.ALL);
            }
            android.content.Intent intent2 = new android.content.Intent("android.os.action.POWER_SAVE_MODE_CHANGED_INTERNAL");
            intent2.addFlags(1073741824);
            this.mContext.sendBroadcastAsUser(intent2, android.os.UserHandle.ALL, "android.permission.DEVICE_POWER");
            for (android.os.PowerManagerInternal.LowPowerModeListener lowPowerModeListener : lowPowerModeListenerArr) {
                lowPowerModeListener.onLowPowerModeChanged(this.mBatterySaverPolicy.getBatterySaverPolicy(lowPowerModeListener.getServiceType()));
            }
        }
    }

    private java.util.Optional<java.lang.String> getPowerSaveModeChangedListenerPackage() {
        java.util.Optional<java.lang.String> empty;
        if (this.mPowerSaveModeChangedListenerPackage == null) {
            java.lang.String string = this.mContext.getString(android.R.string.config_oem_enabled_satellite_sos_handover_app);
            if (((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).isSystemPackage(string)) {
                empty = java.util.Optional.of(string);
            } else {
                empty = java.util.Optional.empty();
            }
            this.mPowerSaveModeChangedListenerPackage = empty;
        }
        return this.mPowerSaveModeChangedListenerPackage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBatterySavingStats() {
        int i;
        android.os.PowerManager powerManager = getPowerManager();
        if (powerManager == null) {
            android.util.Slog.wtf(TAG, "PowerManager not initialized");
            return;
        }
        boolean isInteractive = powerManager.isInteractive();
        int i2 = 2;
        if (powerManager.isDeviceIdleMode()) {
            i = 2;
        } else {
            i = powerManager.isLightDeviceIdleMode() ? 1 : 0;
        }
        synchronized (this.mLock) {
            com.android.server.power.batterysaver.BatterySavingStats batterySavingStats = this.mBatterySavingStats;
            if (getFullEnabledLocked()) {
                i2 = 1;
            } else if (!getAdaptiveEnabledLocked()) {
                i2 = 0;
            }
            batterySavingStats.transitionState(i2, isInteractive ? 1 : 0, i, this.mIsPluggedIn ? 1 : 0);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setFullEnabledLocked(boolean z) {
        if (this.mFullEnabledRaw == z) {
            return;
        }
        android.os.PowerManager.invalidatePowerSaveModeCaches();
        this.mFullEnabledRaw = z;
    }

    private boolean getFullEnabledLocked() {
        return this.mFullEnabledRaw;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setAdaptiveEnabledLocked(boolean z) {
        if (this.mAdaptiveEnabledRaw == z) {
            return;
        }
        android.os.PowerManager.invalidatePowerSaveModeCaches();
        this.mAdaptiveEnabledRaw = z;
    }

    private boolean getAdaptiveEnabledLocked() {
        return this.mAdaptiveEnabledRaw;
    }
}
