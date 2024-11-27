package com.android.server.power.batterysaver;

/* loaded from: classes2.dex */
public class BatterySaverStateMachine {
    private static final int ADAPTIVE_AUTO_DISABLE_BATTERY_LEVEL = 80;
    private static final long ADAPTIVE_CHANGE_TIMEOUT_MS = 86400000;
    private static final java.lang.String BATTERY_SAVER_NOTIF_CHANNEL_ID = "battery_saver_channel";
    private static final boolean DEBUG = false;
    private static final int DYNAMIC_MODE_NOTIFICATION_ID = 1992;
    private static final java.lang.String DYNAMIC_MODE_NOTIF_CHANNEL_ID = "dynamic_mode_notification";
    private static final int STATE_AUTOMATIC_ON = 3;
    private static final int STATE_MANUAL_ON = 2;
    private static final int STATE_OFF = 1;
    private static final int STATE_OFF_AUTOMATIC_SNOOZED = 4;
    private static final int STATE_PENDING_STICKY_ON = 5;
    private static final int STICKY_AUTO_DISABLED_NOTIFICATION_ID = 1993;
    private static final long STICKY_DISABLED_NOTIFY_TIMEOUT_MS = java.time.Duration.ofHours(12).toMillis();
    private static final java.lang.String TAG = "BatterySaverStateMachine";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mBatteryLevel;
    private final com.android.server.power.batterysaver.BatterySaverController mBatterySaverController;
    private final boolean mBatterySaverStickyBehaviourDisabled;
    private final boolean mBatterySaverTurnedOffNotificationEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mBatteryStatusSet;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mBootCompleted;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final int mDynamicPowerSavingsDefaultDisableThreshold;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mDynamicPowerSavingsDisableThreshold;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDynamicPowerSavingsEnableBatterySaver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsBatteryLevelLow;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsPowered;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastAdaptiveBatterySaverChangedExternallyElapsed;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mLastChangedIntReason;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mLastChangedStrReason;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mSettingAutomaticBatterySaver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSettingBatterySaverEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSettingBatterySaverEnabledSticky;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSettingBatterySaverStickyAutoDisableEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mSettingBatterySaverStickyAutoDisableThreshold;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mSettingBatterySaverTriggerThreshold;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSettingsLoaded;
    private final android.database.ContentObserver mSettingsObserver = new android.database.ContentObserver(null) { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            synchronized (com.android.server.power.batterysaver.BatterySaverStateMachine.this.mLock) {
                com.android.server.power.batterysaver.BatterySaverStateMachine.this.refreshSettingsLocked();
            }
        }
    };
    private final java.lang.Runnable mThresholdChangeLogger = new java.lang.Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.power.batterysaver.BatterySaverStateMachine.this.lambda$new$1();
        }
    };

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mState = 1;

    public BatterySaverStateMachine(java.lang.Object obj, android.content.Context context, com.android.server.power.batterysaver.BatterySaverController batterySaverController) {
        this.mLock = obj;
        this.mContext = context;
        this.mBatterySaverController = batterySaverController;
        this.mBatterySaverStickyBehaviourDisabled = this.mContext.getResources().getBoolean(android.R.bool.config_batterySaverStickyBehaviourDisabled);
        this.mBatterySaverTurnedOffNotificationEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_batterySaverTurnedOffNotificationEnabled);
        this.mDynamicPowerSavingsDefaultDisableThreshold = this.mContext.getResources().getInteger(android.R.integer.config_dreamsBatteryLevelDrainCutoff);
    }

    public void systemReady() {
        this.mBatterySaverController.systemReady();
        getBatterySaverPolicy().systemReady();
    }

    public com.android.server.power.batterysaver.BatterySaverController getBatterySaverController() {
        return this.mBatterySaverController;
    }

    public com.android.server.power.batterysaver.BatterySaverPolicy getBatterySaverPolicy() {
        return this.mBatterySaverController.getBatterySaverPolicy();
    }

    private boolean isAutomaticModeActiveLocked() {
        return this.mSettingAutomaticBatterySaver == 0 && this.mSettingBatterySaverTriggerThreshold > 0;
    }

    private boolean isInAutomaticLowZoneLocked() {
        return this.mIsBatteryLevelLow;
    }

    private boolean isDynamicModeActiveLocked() {
        return this.mSettingAutomaticBatterySaver == 1 && this.mDynamicPowerSavingsEnableBatterySaver;
    }

    private boolean isInDynamicLowZoneLocked() {
        return this.mBatteryLevel <= this.mDynamicPowerSavingsDisableThreshold;
    }

    public void onBootCompleted() {
        putGlobalSetting("low_power", 0);
        runOnBgThread(new java.lang.Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.batterysaver.BatterySaverStateMachine.this.lambda$onBootCompleted$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootCompleted$0() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("low_power"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("low_power_sticky"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("low_power_trigger_level"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("automatic_power_save_mode"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("dynamic_power_savings_enabled"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("dynamic_power_savings_disable_threshold"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("low_power_sticky_auto_disable_enabled"), false, this.mSettingsObserver, 0);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("low_power_sticky_auto_disable_level"), false, this.mSettingsObserver, 0);
        synchronized (this.mLock) {
            try {
                if (getGlobalSetting("low_power_sticky", 0) != 0) {
                    this.mState = 5;
                }
                this.mBootCompleted = true;
                refreshSettingsLocked();
                doAutoBatterySaverLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void runOnBgThread(java.lang.Runnable runnable) {
        com.android.internal.os.BackgroundThread.getHandler().post(runnable);
    }

    @com.android.internal.annotations.VisibleForTesting
    void runOnBgThreadLazy(java.lang.Runnable runnable, int i) {
        android.os.Handler handler = com.android.internal.os.BackgroundThread.getHandler();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void refreshSettingsLocked() {
        boolean z = getGlobalSetting("low_power", 0) != 0;
        boolean z2 = getGlobalSetting("low_power_sticky", 0) != 0;
        boolean z3 = getGlobalSetting("dynamic_power_savings_enabled", 0) != 0;
        setSettingsLocked(z, z2, getGlobalSetting("low_power_trigger_level", 0), getGlobalSetting("low_power_sticky_auto_disable_enabled", 1) != 0, getGlobalSetting("low_power_sticky_auto_disable_level", 90), getGlobalSetting("automatic_power_save_mode", 0), z3, getGlobalSetting("dynamic_power_savings_disable_threshold", this.mDynamicPowerSavingsDefaultDisableThreshold));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void setSettingsLocked(boolean z, boolean z2, int i, boolean z3, int i2, int i3, boolean z4, int i4) {
        this.mSettingsLoaded = true;
        int max = java.lang.Math.max(i2, i);
        boolean z5 = this.mSettingBatterySaverEnabled != z;
        boolean z6 = this.mSettingBatterySaverEnabledSticky != z2;
        boolean z7 = this.mSettingBatterySaverTriggerThreshold != i;
        boolean z8 = this.mSettingBatterySaverStickyAutoDisableEnabled != z3;
        boolean z9 = this.mSettingBatterySaverStickyAutoDisableThreshold != max;
        boolean z10 = this.mSettingAutomaticBatterySaver != i3;
        boolean z11 = this.mDynamicPowerSavingsDisableThreshold != i4;
        boolean z12 = this.mDynamicPowerSavingsEnableBatterySaver != z4;
        if (!z5 && !z6 && !z7 && !z10 && !z8 && !z9 && !z11 && !z12) {
            return;
        }
        this.mSettingBatterySaverEnabled = z;
        this.mSettingBatterySaverEnabledSticky = z2;
        this.mSettingBatterySaverTriggerThreshold = i;
        this.mSettingBatterySaverStickyAutoDisableEnabled = z3;
        this.mSettingBatterySaverStickyAutoDisableThreshold = max;
        this.mSettingAutomaticBatterySaver = i3;
        this.mDynamicPowerSavingsDisableThreshold = i4;
        this.mDynamicPowerSavingsEnableBatterySaver = z4;
        if (z7) {
            runOnBgThreadLazy(this.mThresholdChangeLogger, 2000);
        }
        if (!this.mSettingBatterySaverStickyAutoDisableEnabled) {
            hideStickyDisabledNotification();
        }
        if (z5) {
            enableBatterySaverLocked(z, true, 8, z ? "Global.low_power changed to 1" : "Global.low_power changed to 0");
        } else {
            doAutoBatterySaverLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        com.android.server.EventLogTags.writeBatterySaverSetting(this.mSettingBatterySaverTriggerThreshold);
    }

    public void setBatteryStatus(boolean z, int i, boolean z2) {
        synchronized (this.mLock) {
            boolean z3 = true;
            try {
                this.mBatteryStatusSet = true;
                boolean z4 = this.mIsPowered != z;
                boolean z5 = this.mBatteryLevel != i;
                if (this.mIsBatteryLevelLow == z2) {
                    z3 = false;
                }
                if (z4 || z5 || z3) {
                    this.mIsPowered = z;
                    this.mBatteryLevel = i;
                    this.mIsBatteryLevelLow = z2;
                    doAutoBatterySaverLocked();
                }
            } finally {
            }
        }
    }

    public android.os.BatterySaverPolicyConfig getFullBatterySaverPolicy() {
        android.os.BatterySaverPolicyConfig policyLocked;
        synchronized (this.mLock) {
            policyLocked = this.mBatterySaverController.getPolicyLocked(2);
        }
        return policyLocked;
    }

    public boolean setFullBatterySaverPolicy(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) {
        boolean fullPolicyLocked;
        synchronized (this.mLock) {
            fullPolicyLocked = this.mBatterySaverController.setFullPolicyLocked(batterySaverPolicyConfig, 13);
        }
        return fullPolicyLocked;
    }

    public boolean setAdaptiveBatterySaverEnabled(boolean z) {
        boolean adaptivePolicyEnabledLocked;
        synchronized (this.mLock) {
            this.mLastAdaptiveBatterySaverChangedExternallyElapsed = android.os.SystemClock.elapsedRealtime();
            adaptivePolicyEnabledLocked = this.mBatterySaverController.setAdaptivePolicyEnabledLocked(z, 11);
        }
        return adaptivePolicyEnabledLocked;
    }

    public boolean setAdaptiveBatterySaverPolicy(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) {
        boolean adaptivePolicyLocked;
        synchronized (this.mLock) {
            this.mLastAdaptiveBatterySaverChangedExternallyElapsed = android.os.SystemClock.elapsedRealtime();
            adaptivePolicyLocked = this.mBatterySaverController.setAdaptivePolicyLocked(batterySaverPolicyConfig, 11);
        }
        return adaptivePolicyLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void doAutoBatterySaverLocked() {
        if (!this.mBootCompleted || !this.mSettingsLoaded || !this.mBatteryStatusSet) {
            return;
        }
        updateStateLocked(false, false);
        if (android.os.SystemClock.elapsedRealtime() - this.mLastAdaptiveBatterySaverChangedExternallyElapsed > 86400000) {
            this.mBatterySaverController.setAdaptivePolicyEnabledLocked(false, 12);
            this.mBatterySaverController.resetAdaptivePolicyLocked(12);
        } else if (this.mIsPowered && this.mBatteryLevel >= 80) {
            this.mBatterySaverController.setAdaptivePolicyEnabledLocked(false, 7);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateStateLocked(boolean z, boolean z2) {
        if (!z && (!this.mBootCompleted || !this.mSettingsLoaded || !this.mBatteryStatusSet)) {
            return;
        }
        switch (this.mState) {
            case 1:
                if (!this.mIsPowered) {
                    if (z) {
                        if (!z2) {
                            android.util.Slog.e(TAG, "Tried to disable BS when it's already OFF");
                            break;
                        } else {
                            enableBatterySaverLocked(true, true, 2);
                            hideStickyDisabledNotification();
                            this.mState = 2;
                            break;
                        }
                    } else if (isAutomaticModeActiveLocked() && isInAutomaticLowZoneLocked()) {
                        enableBatterySaverLocked(true, false, 0);
                        hideStickyDisabledNotification();
                        this.mState = 3;
                        break;
                    } else if (isDynamicModeActiveLocked() && isInDynamicLowZoneLocked()) {
                        enableBatterySaverLocked(true, false, 9);
                        hideStickyDisabledNotification();
                        this.mState = 3;
                        break;
                    }
                }
                break;
            case 2:
                if (z) {
                    if (z2) {
                        android.util.Slog.e(TAG, "Tried to enable BS when it's already MANUAL_ON");
                        break;
                    } else {
                        enableBatterySaverLocked(false, true, 3);
                        this.mState = 1;
                        break;
                    }
                } else if (this.mIsPowered) {
                    enableBatterySaverLocked(false, false, 7);
                    if (this.mSettingBatterySaverEnabledSticky && !this.mBatterySaverStickyBehaviourDisabled) {
                        this.mState = 5;
                        break;
                    } else {
                        this.mState = 1;
                        break;
                    }
                }
                break;
            case 3:
                if (this.mIsPowered) {
                    enableBatterySaverLocked(false, false, 7);
                    this.mState = 1;
                    break;
                } else if (z) {
                    if (z2) {
                        android.util.Slog.e(TAG, "Tried to enable BS when it's already AUTO_ON");
                        break;
                    } else {
                        enableBatterySaverLocked(false, true, 3);
                        this.mState = 4;
                        break;
                    }
                } else if (isAutomaticModeActiveLocked() && !isInAutomaticLowZoneLocked()) {
                    enableBatterySaverLocked(false, false, 1);
                    this.mState = 1;
                    break;
                } else if (isDynamicModeActiveLocked() && !isInDynamicLowZoneLocked()) {
                    enableBatterySaverLocked(false, false, 10);
                    this.mState = 1;
                    break;
                } else if (!isAutomaticModeActiveLocked() && !isDynamicModeActiveLocked()) {
                    enableBatterySaverLocked(false, false, 8);
                    this.mState = 1;
                    break;
                }
                break;
            case 4:
                if (z) {
                    if (!z2) {
                        android.util.Slog.e(TAG, "Tried to disable BS when it's already AUTO_SNOOZED");
                        break;
                    } else {
                        enableBatterySaverLocked(true, true, 2);
                        this.mState = 2;
                        break;
                    }
                } else if (this.mIsPowered || ((isAutomaticModeActiveLocked() && !isInAutomaticLowZoneLocked()) || ((isDynamicModeActiveLocked() && !isInDynamicLowZoneLocked()) || (!isAutomaticModeActiveLocked() && !isDynamicModeActiveLocked())))) {
                    this.mState = 1;
                    break;
                }
                break;
            case 5:
                if (z) {
                    android.util.Slog.e(TAG, "Tried to manually change BS state from PENDING_STICKY_ON");
                    break;
                } else {
                    boolean z3 = this.mSettingBatterySaverStickyAutoDisableEnabled && this.mBatteryLevel >= this.mSettingBatterySaverStickyAutoDisableThreshold;
                    if ((this.mBatterySaverStickyBehaviourDisabled || !this.mSettingBatterySaverEnabledSticky) || z3) {
                        this.mState = 1;
                        setStickyActive(false);
                        triggerStickyDisabledNotification();
                        break;
                    } else if (!this.mIsPowered) {
                        enableBatterySaverLocked(true, true, 4);
                        this.mState = 2;
                        break;
                    }
                }
                break;
            default:
                android.util.Slog.wtf(TAG, "Unknown state: " + this.mState);
                break;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int getState() {
        int i;
        synchronized (this.mLock) {
            i = this.mState;
        }
        return i;
    }

    public void setBatterySaverEnabledManually(boolean z) {
        synchronized (this.mLock) {
            updateStateLocked(true, z);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void enableBatterySaverLocked(boolean z, boolean z2, int i) {
        enableBatterySaverLocked(z, z2, i, com.android.server.power.batterysaver.BatterySaverController.reasonToString(i));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void enableBatterySaverLocked(boolean z, boolean z2, int i, java.lang.String str) {
        if (this.mBatterySaverController.isFullEnabled() == z) {
            return;
        }
        if (z && this.mIsPowered) {
            return;
        }
        this.mLastChangedIntReason = i;
        this.mLastChangedStrReason = str;
        this.mSettingBatterySaverEnabled = z;
        putGlobalSetting("low_power", z ? 1 : 0);
        if (z2) {
            setStickyActive(!this.mBatterySaverStickyBehaviourDisabled && z);
        }
        this.mBatterySaverController.enableBatterySaver(z, i);
        if (i == 9 || i == 0) {
            triggerDynamicModeNotification();
        } else if (!z) {
            hideDynamicModeNotification();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void triggerDynamicModeNotification() {
        runOnBgThread(new java.lang.Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.batterysaver.BatterySaverStateMachine.this.lambda$triggerDynamicModeNotification$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$triggerDynamicModeNotification$2() {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        ensureNotificationChannelExists(notificationManager, DYNAMIC_MODE_NOTIF_CHANNEL_ID, android.R.string.duration_days_shortest);
        notificationManager.notifyAsUser(TAG, DYNAMIC_MODE_NOTIFICATION_ID, buildNotification(DYNAMIC_MODE_NOTIF_CHANNEL_ID, android.R.string.duration_hours_relative, android.R.string.duration_days_shortest_future, "android.settings.BATTERY_SAVER_SETTINGS", 0L), android.os.UserHandle.ALL);
    }

    @com.android.internal.annotations.VisibleForTesting
    void triggerStickyDisabledNotification() {
        if (!this.mBatterySaverTurnedOffNotificationEnabled) {
            return;
        }
        runOnBgThread(new java.lang.Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.batterysaver.BatterySaverStateMachine.this.lambda$triggerStickyDisabledNotification$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$triggerStickyDisabledNotification$3() {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        ensureNotificationChannelExists(notificationManager, BATTERY_SAVER_NOTIF_CHANNEL_ID, android.R.string.back_button_label);
        notificationManager.notifyAsUser(TAG, STICKY_AUTO_DISABLED_NOTIFICATION_ID, buildNotification(BATTERY_SAVER_NOTIF_CHANNEL_ID, android.R.string.badPin, android.R.string.autofill_update_title_with_type, "android.settings.BATTERY_SAVER_SETTINGS", STICKY_DISABLED_NOTIFY_TIMEOUT_MS), android.os.UserHandle.ALL);
    }

    private void ensureNotificationChannelExists(android.app.NotificationManager notificationManager, @android.annotation.NonNull java.lang.String str, int i) {
        android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel(str, this.mContext.getText(i), 3);
        notificationChannel.setSound(null, null);
        notificationChannel.setBlockable(true);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    private android.app.Notification buildNotification(@android.annotation.NonNull java.lang.String str, int i, int i2, @android.annotation.NonNull java.lang.String str2, long j) {
        android.content.res.Resources resources = this.mContext.getResources();
        android.content.Intent intent = new android.content.Intent(str2);
        intent.setFlags(268468224);
        android.app.PendingIntent activity = android.app.PendingIntent.getActivity(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
        java.lang.String string = resources.getString(i);
        java.lang.String string2 = resources.getString(i2);
        return new android.app.Notification.Builder(this.mContext, str).setSmallIcon(android.R.drawable.ic_audio_vol).setContentTitle(string).setContentText(string2).setContentIntent(activity).setStyle(new android.app.Notification.BigTextStyle().bigText(string2)).setOnlyAlertOnce(true).setAutoCancel(true).setTimeoutAfter(j).build();
    }

    private void hideDynamicModeNotification() {
        hideNotification(DYNAMIC_MODE_NOTIFICATION_ID);
    }

    private void hideStickyDisabledNotification() {
        hideNotification(STICKY_AUTO_DISABLED_NOTIFICATION_ID);
    }

    private void hideNotification(final int i) {
        runOnBgThread(new java.lang.Runnable() { // from class: com.android.server.power.batterysaver.BatterySaverStateMachine$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.batterysaver.BatterySaverStateMachine.this.lambda$hideNotification$4(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideNotification$4(int i) {
        ((android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class)).cancelAsUser(TAG, i, android.os.UserHandle.ALL);
    }

    private void setStickyActive(boolean z) {
        this.mSettingBatterySaverEnabledSticky = z;
        putGlobalSetting("low_power_sticky", this.mSettingBatterySaverEnabledSticky ? 1 : 0);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void putGlobalSetting(java.lang.String str, int i) {
        android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), str, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected int getGlobalSetting(java.lang.String str, int i) {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), str, i);
    }

    public void dump(java.io.PrintWriter printWriter) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println();
        indentingPrintWriter.println("Battery saver state machine:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.print("Enabled=");
                indentingPrintWriter.println(this.mBatterySaverController.isEnabled());
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("full=");
                indentingPrintWriter.println(this.mBatterySaverController.isFullEnabled());
                indentingPrintWriter.print("adaptive=");
                indentingPrintWriter.print(this.mBatterySaverController.isAdaptiveEnabled());
                if (this.mBatterySaverController.isAdaptiveEnabled()) {
                    indentingPrintWriter.print(" (advertise=");
                    indentingPrintWriter.print(getBatterySaverPolicy().shouldAdvertiseIsEnabled());
                    indentingPrintWriter.print(")");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.print("mState=");
                indentingPrintWriter.println(this.mState);
                indentingPrintWriter.print("mLastChangedIntReason=");
                indentingPrintWriter.println(this.mLastChangedIntReason);
                indentingPrintWriter.print("mLastChangedStrReason=");
                indentingPrintWriter.println(this.mLastChangedStrReason);
                indentingPrintWriter.print("mBootCompleted=");
                indentingPrintWriter.println(this.mBootCompleted);
                indentingPrintWriter.print("mSettingsLoaded=");
                indentingPrintWriter.println(this.mSettingsLoaded);
                indentingPrintWriter.print("mBatteryStatusSet=");
                indentingPrintWriter.println(this.mBatteryStatusSet);
                indentingPrintWriter.print("mIsPowered=");
                indentingPrintWriter.println(this.mIsPowered);
                indentingPrintWriter.print("mBatteryLevel=");
                indentingPrintWriter.println(this.mBatteryLevel);
                indentingPrintWriter.print("mIsBatteryLevelLow=");
                indentingPrintWriter.println(this.mIsBatteryLevelLow);
                indentingPrintWriter.print("mSettingAutomaticBatterySaver=");
                indentingPrintWriter.println(this.mSettingAutomaticBatterySaver);
                indentingPrintWriter.print("mSettingBatterySaverEnabled=");
                indentingPrintWriter.println(this.mSettingBatterySaverEnabled);
                indentingPrintWriter.print("mSettingBatterySaverEnabledSticky=");
                indentingPrintWriter.println(this.mSettingBatterySaverEnabledSticky);
                indentingPrintWriter.print("mSettingBatterySaverStickyAutoDisableEnabled=");
                indentingPrintWriter.println(this.mSettingBatterySaverStickyAutoDisableEnabled);
                indentingPrintWriter.print("mSettingBatterySaverStickyAutoDisableThreshold=");
                indentingPrintWriter.println(this.mSettingBatterySaverStickyAutoDisableThreshold);
                indentingPrintWriter.print("mSettingBatterySaverTriggerThreshold=");
                indentingPrintWriter.println(this.mSettingBatterySaverTriggerThreshold);
                indentingPrintWriter.print("mBatterySaverStickyBehaviourDisabled=");
                indentingPrintWriter.println(this.mBatterySaverStickyBehaviourDisabled);
                indentingPrintWriter.print("mBatterySaverTurnedOffNotificationEnabled=");
                indentingPrintWriter.println(this.mBatterySaverTurnedOffNotificationEnabled);
                indentingPrintWriter.print("mDynamicPowerSavingsDefaultDisableThreshold=");
                indentingPrintWriter.println(this.mDynamicPowerSavingsDefaultDisableThreshold);
                indentingPrintWriter.print("mDynamicPowerSavingsDisableThreshold=");
                indentingPrintWriter.println(this.mDynamicPowerSavingsDisableThreshold);
                indentingPrintWriter.print("mDynamicPowerSavingsEnableBatterySaver=");
                indentingPrintWriter.println(this.mDynamicPowerSavingsEnableBatterySaver);
                indentingPrintWriter.print("mLastAdaptiveBatterySaverChangedExternallyElapsed=");
                indentingPrintWriter.println(this.mLastAdaptiveBatterySaverChangedExternallyElapsed);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        synchronized (this.mLock) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1133871366145L, this.mBatterySaverController.isEnabled());
            protoOutputStream.write(1159641169938L, this.mState);
            protoOutputStream.write(1133871366158L, this.mBatterySaverController.isFullEnabled());
            protoOutputStream.write(1133871366159L, this.mBatterySaverController.isAdaptiveEnabled());
            protoOutputStream.write(1133871366160L, getBatterySaverPolicy().shouldAdvertiseIsEnabled());
            protoOutputStream.write(1133871366146L, this.mBootCompleted);
            protoOutputStream.write(1133871366147L, this.mSettingsLoaded);
            protoOutputStream.write(1133871366148L, this.mBatteryStatusSet);
            protoOutputStream.write(1133871366150L, this.mIsPowered);
            protoOutputStream.write(1120986464263L, this.mBatteryLevel);
            protoOutputStream.write(1133871366152L, this.mIsBatteryLevelLow);
            protoOutputStream.write(1159641169939L, this.mSettingAutomaticBatterySaver);
            protoOutputStream.write(1133871366153L, this.mSettingBatterySaverEnabled);
            protoOutputStream.write(1133871366154L, this.mSettingBatterySaverEnabledSticky);
            protoOutputStream.write(1120986464267L, this.mSettingBatterySaverTriggerThreshold);
            protoOutputStream.write(1133871366156L, this.mSettingBatterySaverStickyAutoDisableEnabled);
            protoOutputStream.write(1120986464269L, this.mSettingBatterySaverStickyAutoDisableThreshold);
            protoOutputStream.write(1120986464276L, this.mDynamicPowerSavingsDefaultDisableThreshold);
            protoOutputStream.write(1120986464277L, this.mDynamicPowerSavingsDisableThreshold);
            protoOutputStream.write(1133871366166L, this.mDynamicPowerSavingsEnableBatterySaver);
            protoOutputStream.write(1112396529681L, this.mLastAdaptiveBatterySaverChangedExternallyElapsed);
            protoOutputStream.end(start);
        }
    }
}
