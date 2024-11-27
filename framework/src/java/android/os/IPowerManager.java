package android.os;

/* loaded from: classes3.dex */
public interface IPowerManager extends android.os.IInterface {
    public static final int GO_TO_SLEEP_FLAG_NO_DOZE = 1;
    public static final int GO_TO_SLEEP_REASON_ACCESSIBILITY = 7;
    public static final int GO_TO_SLEEP_REASON_APPLICATION = 0;
    public static final int GO_TO_SLEEP_REASON_FORCE_SUSPEND = 8;
    public static final int GO_TO_SLEEP_REASON_HDMI = 5;
    public static final int GO_TO_SLEEP_REASON_INATTENTIVE = 9;
    public static final int GO_TO_SLEEP_REASON_LID_SWITCH = 3;
    public static final int GO_TO_SLEEP_REASON_MAX = 10;
    public static final int GO_TO_SLEEP_REASON_MIN = 0;
    public static final int GO_TO_SLEEP_REASON_POWER_BUTTON = 4;
    public static final int GO_TO_SLEEP_REASON_QUIESCENT = 10;
    public static final int GO_TO_SLEEP_REASON_SLEEP_BUTTON = 6;
    public static final int GO_TO_SLEEP_REASON_TIMEOUT = 2;
    public static final int LOCATION_MODE_ALL_DISABLED_WHEN_SCREEN_OFF = 2;
    public static final int LOCATION_MODE_FOREGROUND_ONLY = 3;
    public static final int LOCATION_MODE_GPS_DISABLED_WHEN_SCREEN_OFF = 1;
    public static final int LOCATION_MODE_NO_CHANGE = 0;
    public static final int LOCATION_MODE_THROTTLE_REQUESTS_WHEN_SCREEN_OFF = 4;
    public static final int MAX_LOCATION_MODE = 4;
    public static final int MIN_LOCATION_MODE = 0;

    void acquireLowPowerStandbyPorts(android.os.IBinder iBinder, java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> list) throws android.os.RemoteException;

    void acquireWakeLock(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3, int i2, android.os.IWakeLockCallback iWakeLockCallback) throws android.os.RemoteException;

    void acquireWakeLockAsync(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3) throws android.os.RemoteException;

    void acquireWakeLockWithUid(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, int i2, int i3, android.os.IWakeLockCallback iWakeLockCallback) throws android.os.RemoteException;

    boolean areAutoPowerSaveModesEnabled() throws android.os.RemoteException;

    void boostScreenBrightness(long j) throws android.os.RemoteException;

    void crash(java.lang.String str) throws android.os.RemoteException;

    void forceLowPowerStandbyActive(boolean z) throws android.os.RemoteException;

    boolean forceSuspend() throws android.os.RemoteException;

    java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() throws android.os.RemoteException;

    android.os.ParcelDuration getBatteryDischargePrediction() throws android.os.RemoteException;

    float getBrightnessConstraint(int i) throws android.os.RemoteException;

    android.os.BatterySaverPolicyConfig getFullPowerSavePolicy() throws android.os.RemoteException;

    int getLastShutdownReason() throws android.os.RemoteException;

    int getLastSleepReason() throws android.os.RemoteException;

    android.os.IPowerManager.LowPowerStandbyPolicy getLowPowerStandbyPolicy() throws android.os.RemoteException;

    int getPowerSaveModeTrigger() throws android.os.RemoteException;

    android.os.PowerSaveState getPowerSaveState(int i) throws android.os.RemoteException;

    void goToSleep(long j, int i, int i2) throws android.os.RemoteException;

    void goToSleepWithDisplayId(int i, long j, int i2, int i3) throws android.os.RemoteException;

    boolean isAmbientDisplayAvailable() throws android.os.RemoteException;

    boolean isAmbientDisplaySuppressed() throws android.os.RemoteException;

    boolean isAmbientDisplaySuppressedForToken(java.lang.String str) throws android.os.RemoteException;

    boolean isAmbientDisplaySuppressedForTokenByApp(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isBatteryDischargePredictionPersonalized() throws android.os.RemoteException;

    boolean isBatterySaverSupported() throws android.os.RemoteException;

    boolean isDeviceIdleMode() throws android.os.RemoteException;

    boolean isDisplayInteractive(int i) throws android.os.RemoteException;

    boolean isExemptFromLowPowerStandby() throws android.os.RemoteException;

    boolean isFeatureAllowedInLowPowerStandby(java.lang.String str) throws android.os.RemoteException;

    boolean isInteractive() throws android.os.RemoteException;

    boolean isLightDeviceIdleMode() throws android.os.RemoteException;

    boolean isLowPowerStandbyEnabled() throws android.os.RemoteException;

    boolean isLowPowerStandbySupported() throws android.os.RemoteException;

    boolean isPowerSaveMode() throws android.os.RemoteException;

    boolean isReasonAllowedInLowPowerStandby(int i) throws android.os.RemoteException;

    boolean isScreenBrightnessBoosted() throws android.os.RemoteException;

    boolean isWakeLockLevelSupported(int i) throws android.os.RemoteException;

    void nap(long j) throws android.os.RemoteException;

    void reboot(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException;

    void rebootCustom(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException;

    void rebootSafeMode(boolean z, boolean z2) throws android.os.RemoteException;

    void releaseLowPowerStandbyPorts(android.os.IBinder iBinder) throws android.os.RemoteException;

    void releaseWakeLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void releaseWakeLockAsync(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    boolean setAdaptivePowerSaveEnabled(boolean z) throws android.os.RemoteException;

    boolean setAdaptivePowerSavePolicy(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) throws android.os.RemoteException;

    void setAttentionLight(boolean z, int i) throws android.os.RemoteException;

    void setBatteryDischargePrediction(android.os.ParcelDuration parcelDuration, boolean z) throws android.os.RemoteException;

    void setDozeAfterScreenOff(boolean z) throws android.os.RemoteException;

    boolean setDynamicPowerSaveHint(boolean z, int i) throws android.os.RemoteException;

    boolean setFullPowerSavePolicy(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) throws android.os.RemoteException;

    void setKeyboardVisibility(boolean z) throws android.os.RemoteException;

    void setLowPowerStandbyActiveDuringMaintenance(boolean z) throws android.os.RemoteException;

    void setLowPowerStandbyEnabled(boolean z) throws android.os.RemoteException;

    void setLowPowerStandbyPolicy(android.os.IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) throws android.os.RemoteException;

    void setPowerBoost(int i, int i2) throws android.os.RemoteException;

    void setPowerMode(int i, boolean z) throws android.os.RemoteException;

    boolean setPowerModeChecked(int i, boolean z) throws android.os.RemoteException;

    boolean setPowerSaveModeEnabled(boolean z) throws android.os.RemoteException;

    void setStayOnSetting(int i) throws android.os.RemoteException;

    void shutdown(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException;

    void suppressAmbientDisplay(java.lang.String str, boolean z) throws android.os.RemoteException;

    void updateWakeLockCallback(android.os.IBinder iBinder, android.os.IWakeLockCallback iWakeLockCallback) throws android.os.RemoteException;

    void updateWakeLockUids(android.os.IBinder iBinder, int[] iArr) throws android.os.RemoteException;

    void updateWakeLockUidsAsync(android.os.IBinder iBinder, int[] iArr) throws android.os.RemoteException;

    void updateWakeLockWorkSource(android.os.IBinder iBinder, android.os.WorkSource workSource, java.lang.String str) throws android.os.RemoteException;

    void userActivity(int i, long j, int i2, int i3) throws android.os.RemoteException;

    void wakeUp(long j, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void wakeUpWithProximityCheck(long j, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements android.os.IPowerManager {
        @Override // android.os.IPowerManager
        public void acquireWakeLock(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3, int i2, android.os.IWakeLockCallback iWakeLockCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void acquireWakeLockWithUid(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, int i2, int i3, android.os.IWakeLockCallback iWakeLockCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void releaseWakeLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void updateWakeLockUids(android.os.IBinder iBinder, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setPowerBoost(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setPowerMode(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean setPowerModeChecked(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void updateWakeLockWorkSource(android.os.IBinder iBinder, android.os.WorkSource workSource, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void updateWakeLockCallback(android.os.IBinder iBinder, android.os.IWakeLockCallback iWakeLockCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean isWakeLockLevelSupported(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void userActivity(int i, long j, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void wakeUp(long j, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void goToSleep(long j, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void goToSleepWithDisplayId(int i, long j, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void nap(long j) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public float getBrightnessConstraint(int i) throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.os.IPowerManager
        public boolean isInteractive() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isDisplayInteractive(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean areAutoPowerSaveModesEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isPowerSaveMode() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public android.os.PowerSaveState getPowerSaveState(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public boolean setPowerSaveModeEnabled(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isBatterySaverSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public android.os.BatterySaverPolicyConfig getFullPowerSavePolicy() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public boolean setFullPowerSavePolicy(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean setDynamicPowerSaveHint(boolean z, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean setAdaptivePowerSavePolicy(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean setAdaptivePowerSaveEnabled(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public int getPowerSaveModeTrigger() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IPowerManager
        public void setBatteryDischargePrediction(android.os.ParcelDuration parcelDuration, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public android.os.ParcelDuration getBatteryDischargePrediction() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public boolean isBatteryDischargePredictionPersonalized() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isDeviceIdleMode() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isLightDeviceIdleMode() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isLowPowerStandbySupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isLowPowerStandbyEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void setLowPowerStandbyEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setLowPowerStandbyActiveDuringMaintenance(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void forceLowPowerStandbyActive(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setLowPowerStandbyPolicy(android.os.IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public android.os.IPowerManager.LowPowerStandbyPolicy getLowPowerStandbyPolicy() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public boolean isExemptFromLowPowerStandby() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isReasonAllowedInLowPowerStandby(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isFeatureAllowedInLowPowerStandby(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void acquireLowPowerStandbyPorts(android.os.IBinder iBinder, java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> list) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void releaseLowPowerStandbyPorts(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public void reboot(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void rebootSafeMode(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void shutdown(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void crash(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public int getLastShutdownReason() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IPowerManager
        public int getLastSleepReason() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IPowerManager
        public void setStayOnSetting(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void boostScreenBrightness(long j) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void acquireWakeLockAsync(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void releaseWakeLockAsync(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void updateWakeLockUidsAsync(android.os.IBinder iBinder, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean isScreenBrightnessBoosted() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void setAttentionLight(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setDozeAfterScreenOff(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplayAvailable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void suppressAmbientDisplay(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplaySuppressedForToken(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplaySuppressed() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplaySuppressedForTokenByApp(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean forceSuspend() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void rebootCustom(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setKeyboardVisibility(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IPowerManager
        public void wakeUpWithProximityCheck(long j, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IPowerManager {
        public static final java.lang.String DESCRIPTOR = "android.os.IPowerManager";
        static final int TRANSACTION_acquireLowPowerStandbyPorts = 45;
        static final int TRANSACTION_acquireWakeLock = 1;
        static final int TRANSACTION_acquireWakeLockAsync = 56;
        static final int TRANSACTION_acquireWakeLockWithUid = 2;
        static final int TRANSACTION_areAutoPowerSaveModesEnabled = 19;
        static final int TRANSACTION_boostScreenBrightness = 55;
        static final int TRANSACTION_crash = 51;
        static final int TRANSACTION_forceLowPowerStandbyActive = 39;
        static final int TRANSACTION_forceSuspend = 67;
        static final int TRANSACTION_getActiveLowPowerStandbyPorts = 47;
        static final int TRANSACTION_getBatteryDischargePrediction = 31;
        static final int TRANSACTION_getBrightnessConstraint = 16;
        static final int TRANSACTION_getFullPowerSavePolicy = 24;
        static final int TRANSACTION_getLastShutdownReason = 52;
        static final int TRANSACTION_getLastSleepReason = 53;
        static final int TRANSACTION_getLowPowerStandbyPolicy = 41;
        static final int TRANSACTION_getPowerSaveModeTrigger = 29;
        static final int TRANSACTION_getPowerSaveState = 21;
        static final int TRANSACTION_goToSleep = 13;
        static final int TRANSACTION_goToSleepWithDisplayId = 14;
        static final int TRANSACTION_isAmbientDisplayAvailable = 62;
        static final int TRANSACTION_isAmbientDisplaySuppressed = 65;
        static final int TRANSACTION_isAmbientDisplaySuppressedForToken = 64;
        static final int TRANSACTION_isAmbientDisplaySuppressedForTokenByApp = 66;
        static final int TRANSACTION_isBatteryDischargePredictionPersonalized = 32;
        static final int TRANSACTION_isBatterySaverSupported = 23;
        static final int TRANSACTION_isDeviceIdleMode = 33;
        static final int TRANSACTION_isDisplayInteractive = 18;
        static final int TRANSACTION_isExemptFromLowPowerStandby = 42;
        static final int TRANSACTION_isFeatureAllowedInLowPowerStandby = 44;
        static final int TRANSACTION_isInteractive = 17;
        static final int TRANSACTION_isLightDeviceIdleMode = 34;
        static final int TRANSACTION_isLowPowerStandbyEnabled = 36;
        static final int TRANSACTION_isLowPowerStandbySupported = 35;
        static final int TRANSACTION_isPowerSaveMode = 20;
        static final int TRANSACTION_isReasonAllowedInLowPowerStandby = 43;
        static final int TRANSACTION_isScreenBrightnessBoosted = 59;
        static final int TRANSACTION_isWakeLockLevelSupported = 10;
        static final int TRANSACTION_nap = 15;
        static final int TRANSACTION_reboot = 48;
        static final int TRANSACTION_rebootCustom = 68;
        static final int TRANSACTION_rebootSafeMode = 49;
        static final int TRANSACTION_releaseLowPowerStandbyPorts = 46;
        static final int TRANSACTION_releaseWakeLock = 3;
        static final int TRANSACTION_releaseWakeLockAsync = 57;
        static final int TRANSACTION_setAdaptivePowerSaveEnabled = 28;
        static final int TRANSACTION_setAdaptivePowerSavePolicy = 27;
        static final int TRANSACTION_setAttentionLight = 60;
        static final int TRANSACTION_setBatteryDischargePrediction = 30;
        static final int TRANSACTION_setDozeAfterScreenOff = 61;
        static final int TRANSACTION_setDynamicPowerSaveHint = 26;
        static final int TRANSACTION_setFullPowerSavePolicy = 25;
        static final int TRANSACTION_setKeyboardVisibility = 69;
        static final int TRANSACTION_setLowPowerStandbyActiveDuringMaintenance = 38;
        static final int TRANSACTION_setLowPowerStandbyEnabled = 37;
        static final int TRANSACTION_setLowPowerStandbyPolicy = 40;
        static final int TRANSACTION_setPowerBoost = 5;
        static final int TRANSACTION_setPowerMode = 6;
        static final int TRANSACTION_setPowerModeChecked = 7;
        static final int TRANSACTION_setPowerSaveModeEnabled = 22;
        static final int TRANSACTION_setStayOnSetting = 54;
        static final int TRANSACTION_shutdown = 50;
        static final int TRANSACTION_suppressAmbientDisplay = 63;
        static final int TRANSACTION_updateWakeLockCallback = 9;
        static final int TRANSACTION_updateWakeLockUids = 4;
        static final int TRANSACTION_updateWakeLockUidsAsync = 58;
        static final int TRANSACTION_updateWakeLockWorkSource = 8;
        static final int TRANSACTION_userActivity = 11;
        static final int TRANSACTION_wakeUp = 12;
        static final int TRANSACTION_wakeUpWithProximityCheck = 70;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IPowerManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IPowerManager)) {
                return (android.os.IPowerManager) queryLocalInterface;
            }
            return new android.os.IPowerManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "acquireWakeLock";
                case 2:
                    return "acquireWakeLockWithUid";
                case 3:
                    return "releaseWakeLock";
                case 4:
                    return "updateWakeLockUids";
                case 5:
                    return "setPowerBoost";
                case 6:
                    return "setPowerMode";
                case 7:
                    return "setPowerModeChecked";
                case 8:
                    return "updateWakeLockWorkSource";
                case 9:
                    return "updateWakeLockCallback";
                case 10:
                    return "isWakeLockLevelSupported";
                case 11:
                    return "userActivity";
                case 12:
                    return "wakeUp";
                case 13:
                    return "goToSleep";
                case 14:
                    return "goToSleepWithDisplayId";
                case 15:
                    return "nap";
                case 16:
                    return "getBrightnessConstraint";
                case 17:
                    return "isInteractive";
                case 18:
                    return "isDisplayInteractive";
                case 19:
                    return "areAutoPowerSaveModesEnabled";
                case 20:
                    return "isPowerSaveMode";
                case 21:
                    return "getPowerSaveState";
                case 22:
                    return "setPowerSaveModeEnabled";
                case 23:
                    return "isBatterySaverSupported";
                case 24:
                    return "getFullPowerSavePolicy";
                case 25:
                    return "setFullPowerSavePolicy";
                case 26:
                    return "setDynamicPowerSaveHint";
                case 27:
                    return "setAdaptivePowerSavePolicy";
                case 28:
                    return "setAdaptivePowerSaveEnabled";
                case 29:
                    return "getPowerSaveModeTrigger";
                case 30:
                    return "setBatteryDischargePrediction";
                case 31:
                    return "getBatteryDischargePrediction";
                case 32:
                    return "isBatteryDischargePredictionPersonalized";
                case 33:
                    return "isDeviceIdleMode";
                case 34:
                    return "isLightDeviceIdleMode";
                case 35:
                    return "isLowPowerStandbySupported";
                case 36:
                    return "isLowPowerStandbyEnabled";
                case 37:
                    return "setLowPowerStandbyEnabled";
                case 38:
                    return "setLowPowerStandbyActiveDuringMaintenance";
                case 39:
                    return "forceLowPowerStandbyActive";
                case 40:
                    return "setLowPowerStandbyPolicy";
                case 41:
                    return "getLowPowerStandbyPolicy";
                case 42:
                    return "isExemptFromLowPowerStandby";
                case 43:
                    return "isReasonAllowedInLowPowerStandby";
                case 44:
                    return "isFeatureAllowedInLowPowerStandby";
                case 45:
                    return "acquireLowPowerStandbyPorts";
                case 46:
                    return "releaseLowPowerStandbyPorts";
                case 47:
                    return "getActiveLowPowerStandbyPorts";
                case 48:
                    return "reboot";
                case 49:
                    return "rebootSafeMode";
                case 50:
                    return "shutdown";
                case 51:
                    return "crash";
                case 52:
                    return "getLastShutdownReason";
                case 53:
                    return "getLastSleepReason";
                case 54:
                    return "setStayOnSetting";
                case 55:
                    return "boostScreenBrightness";
                case 56:
                    return "acquireWakeLockAsync";
                case 57:
                    return "releaseWakeLockAsync";
                case 58:
                    return "updateWakeLockUidsAsync";
                case 59:
                    return "isScreenBrightnessBoosted";
                case 60:
                    return "setAttentionLight";
                case 61:
                    return "setDozeAfterScreenOff";
                case 62:
                    return "isAmbientDisplayAvailable";
                case 63:
                    return "suppressAmbientDisplay";
                case 64:
                    return "isAmbientDisplaySuppressedForToken";
                case 65:
                    return "isAmbientDisplaySuppressed";
                case 66:
                    return "isAmbientDisplaySuppressedForTokenByApp";
                case 67:
                    return "forceSuspend";
                case 68:
                    return "rebootCustom";
                case 69:
                    return "setKeyboardVisibility";
                case 70:
                    return "wakeUpWithProximityCheck";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    android.os.WorkSource workSource = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    android.os.IWakeLockCallback asInterface = android.os.IWakeLockCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acquireWakeLock(readStrongBinder, readInt, readString, readString2, workSource, readString3, readInt2, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    android.os.IWakeLockCallback asInterface2 = android.os.IWakeLockCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acquireWakeLockWithUid(readStrongBinder2, readInt3, readString4, readString5, readInt4, readInt5, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseWakeLock(readStrongBinder3, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    updateWakeLockUids(readStrongBinder4, createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPowerBoost(readInt7, readInt8);
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPowerMode(readInt9, readBoolean);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean powerModeChecked = setPowerModeChecked(readInt10, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(powerModeChecked);
                    return true;
                case 8:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    android.os.WorkSource workSource2 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateWakeLockWorkSource(readStrongBinder5, workSource2, readString6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    android.os.IWakeLockCallback asInterface3 = android.os.IWakeLockCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateWakeLockCallback(readStrongBinder6, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWakeLockLevelSupported = isWakeLockLevelSupported(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWakeLockLevelSupported);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    userActivity(readInt12, readLong, readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    long readLong2 = parcel.readLong();
                    int readInt15 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    wakeUp(readLong2, readInt15, readString7, readString8);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    long readLong3 = parcel.readLong();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    goToSleep(readLong3, readInt16, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    long readLong4 = parcel.readLong();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    goToSleepWithDisplayId(readInt18, readLong4, readInt19, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    nap(readLong5);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float brightnessConstraint = getBrightnessConstraint(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeFloat(brightnessConstraint);
                    return true;
                case 17:
                    boolean isInteractive = isInteractive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInteractive);
                    return true;
                case 18:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDisplayInteractive = isDisplayInteractive(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDisplayInteractive);
                    return true;
                case 19:
                    boolean areAutoPowerSaveModesEnabled = areAutoPowerSaveModesEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areAutoPowerSaveModesEnabled);
                    return true;
                case 20:
                    boolean isPowerSaveMode = isPowerSaveMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPowerSaveMode);
                    return true;
                case 21:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.PowerSaveState powerSaveState = getPowerSaveState(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(powerSaveState, 1);
                    return true;
                case 22:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean powerSaveModeEnabled = setPowerSaveModeEnabled(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(powerSaveModeEnabled);
                    return true;
                case 23:
                    boolean isBatterySaverSupported = isBatterySaverSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBatterySaverSupported);
                    return true;
                case 24:
                    android.os.BatterySaverPolicyConfig fullPowerSavePolicy = getFullPowerSavePolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fullPowerSavePolicy, 1);
                    return true;
                case 25:
                    android.os.BatterySaverPolicyConfig batterySaverPolicyConfig = (android.os.BatterySaverPolicyConfig) parcel.readTypedObject(android.os.BatterySaverPolicyConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean fullPowerSavePolicy2 = setFullPowerSavePolicy(batterySaverPolicyConfig);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(fullPowerSavePolicy2);
                    return true;
                case 26:
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dynamicPowerSaveHint = setDynamicPowerSaveHint(readBoolean4, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dynamicPowerSaveHint);
                    return true;
                case 27:
                    android.os.BatterySaverPolicyConfig batterySaverPolicyConfig2 = (android.os.BatterySaverPolicyConfig) parcel.readTypedObject(android.os.BatterySaverPolicyConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean adaptivePowerSavePolicy = setAdaptivePowerSavePolicy(batterySaverPolicyConfig2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(adaptivePowerSavePolicy);
                    return true;
                case 28:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean adaptivePowerSaveEnabled = setAdaptivePowerSaveEnabled(readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(adaptivePowerSaveEnabled);
                    return true;
                case 29:
                    int powerSaveModeTrigger = getPowerSaveModeTrigger();
                    parcel2.writeNoException();
                    parcel2.writeInt(powerSaveModeTrigger);
                    return true;
                case 30:
                    android.os.ParcelDuration parcelDuration = (android.os.ParcelDuration) parcel.readTypedObject(android.os.ParcelDuration.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBatteryDischargePrediction(parcelDuration, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    android.os.ParcelDuration batteryDischargePrediction = getBatteryDischargePrediction();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(batteryDischargePrediction, 1);
                    return true;
                case 32:
                    boolean isBatteryDischargePredictionPersonalized = isBatteryDischargePredictionPersonalized();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBatteryDischargePredictionPersonalized);
                    return true;
                case 33:
                    boolean isDeviceIdleMode = isDeviceIdleMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceIdleMode);
                    return true;
                case 34:
                    boolean isLightDeviceIdleMode = isLightDeviceIdleMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLightDeviceIdleMode);
                    return true;
                case 35:
                    boolean isLowPowerStandbySupported = isLowPowerStandbySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLowPowerStandbySupported);
                    return true;
                case 36:
                    boolean isLowPowerStandbyEnabled = isLowPowerStandbyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLowPowerStandbyEnabled);
                    return true;
                case 37:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLowPowerStandbyEnabled(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLowPowerStandbyActiveDuringMaintenance(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceLowPowerStandbyActive(readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    android.os.IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy = (android.os.IPowerManager.LowPowerStandbyPolicy) parcel.readTypedObject(android.os.IPowerManager.LowPowerStandbyPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLowPowerStandbyPolicy(lowPowerStandbyPolicy);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    android.os.IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy2 = getLowPowerStandbyPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lowPowerStandbyPolicy2, 1);
                    return true;
                case 42:
                    boolean isExemptFromLowPowerStandby = isExemptFromLowPowerStandby();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExemptFromLowPowerStandby);
                    return true;
                case 43:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isReasonAllowedInLowPowerStandby = isReasonAllowedInLowPowerStandby(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isReasonAllowedInLowPowerStandby);
                    return true;
                case 44:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isFeatureAllowedInLowPowerStandby = isFeatureAllowedInLowPowerStandby(readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFeatureAllowedInLowPowerStandby);
                    return true;
                case 45:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.os.IPowerManager.LowPowerStandbyPortDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    acquireLowPowerStandbyPorts(readStrongBinder7, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    releaseLowPowerStandbyPorts(readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> activeLowPowerStandbyPorts = getActiveLowPowerStandbyPorts();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeLowPowerStandbyPorts, 1);
                    return true;
                case 48:
                    boolean readBoolean10 = parcel.readBoolean();
                    java.lang.String readString10 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reboot(readBoolean10, readString10, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    boolean readBoolean12 = parcel.readBoolean();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    rebootSafeMode(readBoolean12, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    boolean readBoolean14 = parcel.readBoolean();
                    java.lang.String readString11 = parcel.readString();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    shutdown(readBoolean14, readString11, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    crash(readString12);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int lastShutdownReason = getLastShutdownReason();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastShutdownReason);
                    return true;
                case 53:
                    int lastSleepReason = getLastSleepReason();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastSleepReason);
                    return true;
                case 54:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStayOnSetting(readInt26);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boostScreenBrightness(readLong6);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt27 = parcel.readInt();
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    android.os.WorkSource workSource3 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acquireWakeLockAsync(readStrongBinder9, readInt27, readString13, readString14, workSource3, readString15);
                    return true;
                case 57:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseWakeLockAsync(readStrongBinder10, readInt28);
                    return true;
                case 58:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    updateWakeLockUidsAsync(readStrongBinder11, createIntArray2);
                    return true;
                case 59:
                    boolean isScreenBrightnessBoosted = isScreenBrightnessBoosted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenBrightnessBoosted);
                    return true;
                case 60:
                    boolean readBoolean16 = parcel.readBoolean();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAttentionLight(readBoolean16, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDozeAfterScreenOff(readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    boolean isAmbientDisplayAvailable = isAmbientDisplayAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAmbientDisplayAvailable);
                    return true;
                case 63:
                    java.lang.String readString16 = parcel.readString();
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressAmbientDisplay(readString16, readBoolean18);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAmbientDisplaySuppressedForToken = isAmbientDisplaySuppressedForToken(readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAmbientDisplaySuppressedForToken);
                    return true;
                case 65:
                    boolean isAmbientDisplaySuppressed = isAmbientDisplaySuppressed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAmbientDisplaySuppressed);
                    return true;
                case 66:
                    java.lang.String readString18 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAmbientDisplaySuppressedForTokenByApp = isAmbientDisplaySuppressedForTokenByApp(readString18, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAmbientDisplaySuppressedForTokenByApp);
                    return true;
                case 67:
                    boolean forceSuspend = forceSuspend();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSuspend);
                    return true;
                case 68:
                    boolean readBoolean19 = parcel.readBoolean();
                    java.lang.String readString19 = parcel.readString();
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    rebootCustom(readBoolean19, readString19, readBoolean20);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKeyboardVisibility(readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    long readLong7 = parcel.readLong();
                    int readInt31 = parcel.readInt();
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    wakeUpWithProximityCheck(readLong7, readInt31, readString20, readString21);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IPowerManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IPowerManager.Stub.DESCRIPTOR;
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLock(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3, int i2, android.os.IWakeLockCallback iWakeLockCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iWakeLockCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLockWithUid(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, int i2, int i3, android.os.IWakeLockCallback iWakeLockCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iWakeLockCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseWakeLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockUids(android.os.IBinder iBinder, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setPowerBoost(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setPowerMode(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setPowerModeChecked(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockWorkSource(android.os.IBinder iBinder, android.os.WorkSource workSource, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockCallback(android.os.IBinder iBinder, android.os.IWakeLockCallback iWakeLockCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iWakeLockCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isWakeLockLevelSupported(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void userActivity(int i, long j, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void wakeUp(long j, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void goToSleep(long j, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void goToSleepWithDisplayId(int i, long j, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void nap(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public float getBrightnessConstraint(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isInteractive() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDisplayInteractive(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean areAutoPowerSaveModesEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isPowerSaveMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public android.os.PowerSaveState getPowerSaveState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.PowerSaveState) obtain2.readTypedObject(android.os.PowerSaveState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setPowerSaveModeEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isBatterySaverSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public android.os.BatterySaverPolicyConfig getFullPowerSavePolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.BatterySaverPolicyConfig) obtain2.readTypedObject(android.os.BatterySaverPolicyConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setFullPowerSavePolicy(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(batterySaverPolicyConfig, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setDynamicPowerSaveHint(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setAdaptivePowerSavePolicy(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(batterySaverPolicyConfig, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setAdaptivePowerSaveEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getPowerSaveModeTrigger() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setBatteryDischargePrediction(android.os.ParcelDuration parcelDuration, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelDuration, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public android.os.ParcelDuration getBatteryDischargePrediction() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelDuration) obtain2.readTypedObject(android.os.ParcelDuration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isBatteryDischargePredictionPersonalized() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDeviceIdleMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLightDeviceIdleMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLowPowerStandbySupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLowPowerStandbyEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyActiveDuringMaintenance(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void forceLowPowerStandbyActive(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyPolicy(android.os.IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lowPowerStandbyPolicy, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public android.os.IPowerManager.LowPowerStandbyPolicy getLowPowerStandbyPolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.IPowerManager.LowPowerStandbyPolicy) obtain2.readTypedObject(android.os.IPowerManager.LowPowerStandbyPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isExemptFromLowPowerStandby() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isReasonAllowedInLowPowerStandby(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isFeatureAllowedInLowPowerStandby(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireLowPowerStandbyPorts(android.os.IBinder iBinder, java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseLowPowerStandbyPorts(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public java.util.List<android.os.IPowerManager.LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.IPowerManager.LowPowerStandbyPortDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void reboot(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void rebootSafeMode(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void shutdown(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void crash(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getLastShutdownReason() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getLastSleepReason() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setStayOnSetting(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void boostScreenBrightness(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLockAsync(android.os.IBinder iBinder, int i, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeString(str3);
                    this.mRemote.transact(56, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseWakeLockAsync(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockUidsAsync(android.os.IBinder iBinder, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(58, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isScreenBrightnessBoosted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setAttentionLight(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setDozeAfterScreenOff(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplayAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void suppressAmbientDisplay(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressedForToken(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressedForTokenByApp(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean forceSuspend() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void rebootCustom(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setKeyboardVisibility(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void wakeUpWithProximityCheck(long j, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IPowerManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 69;
        }
    }

    public static class LowPowerStandbyPolicy implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.os.IPowerManager.LowPowerStandbyPolicy> CREATOR = new android.os.Parcelable.Creator<android.os.IPowerManager.LowPowerStandbyPolicy>() { // from class: android.os.IPowerManager.LowPowerStandbyPolicy.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.IPowerManager.LowPowerStandbyPolicy createFromParcel(android.os.Parcel parcel) {
                android.os.IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy = new android.os.IPowerManager.LowPowerStandbyPolicy();
                lowPowerStandbyPolicy.readFromParcel(parcel);
                return lowPowerStandbyPolicy;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.IPowerManager.LowPowerStandbyPolicy[] newArray(int i) {
                return new android.os.IPowerManager.LowPowerStandbyPolicy[i];
            }
        };
        public java.util.List<java.lang.String> allowedFeatures;
        public int allowedReasons = 0;
        public java.util.List<java.lang.String> exemptPackages;
        public java.lang.String identifier;

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.identifier);
            parcel.writeStringList(this.exemptPackages);
            parcel.writeInt(this.allowedReasons);
            parcel.writeStringList(this.allowedFeatures);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.identifier = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.exemptPackages = parcel.createStringArrayList();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.allowedReasons = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.allowedFeatures = parcel.createStringArrayList();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class LowPowerStandbyPortDescription implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.os.IPowerManager.LowPowerStandbyPortDescription> CREATOR = new android.os.Parcelable.Creator<android.os.IPowerManager.LowPowerStandbyPortDescription>() { // from class: android.os.IPowerManager.LowPowerStandbyPortDescription.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.IPowerManager.LowPowerStandbyPortDescription createFromParcel(android.os.Parcel parcel) {
                android.os.IPowerManager.LowPowerStandbyPortDescription lowPowerStandbyPortDescription = new android.os.IPowerManager.LowPowerStandbyPortDescription();
                lowPowerStandbyPortDescription.readFromParcel(parcel);
                return lowPowerStandbyPortDescription;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.IPowerManager.LowPowerStandbyPortDescription[] newArray(int i) {
                return new android.os.IPowerManager.LowPowerStandbyPortDescription[i];
            }
        };
        public byte[] localAddress;
        public int protocol = 0;
        public int portMatcher = 0;
        public int portNumber = 0;

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.protocol);
            parcel.writeInt(this.portMatcher);
            parcel.writeInt(this.portNumber);
            parcel.writeByteArray(this.localAddress);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.protocol = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.portMatcher = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.portNumber = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.localAddress = parcel.createByteArray();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
