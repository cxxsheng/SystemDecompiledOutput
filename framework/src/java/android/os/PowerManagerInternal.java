package android.os;

/* loaded from: classes3.dex */
public abstract class PowerManagerInternal {
    public static final int BOOST_DISPLAY_UPDATE_IMMINENT = 1;
    public static final int BOOST_INTERACTION = 0;
    public static final int MODE_DEVICE_IDLE = 8;
    public static final int MODE_DISPLAY_CHANGE = 17;
    public static final int MODE_DISPLAY_INACTIVE = 9;
    public static final int MODE_DOUBLE_TAP_TO_WAKE = 0;
    public static final int MODE_EXPENSIVE_RENDERING = 6;
    public static final int MODE_FIXED_PERFORMANCE = 3;
    public static final int MODE_INTERACTIVE = 7;
    public static final int MODE_LAUNCH = 5;
    public static final int MODE_LOW_POWER = 1;
    public static final int MODE_SUSTAINED_PERFORMANCE = 2;
    public static final int MODE_VR = 4;
    public static final int WAKEFULNESS_ASLEEP = 0;
    public static final int WAKEFULNESS_AWAKE = 1;
    public static final int WAKEFULNESS_DOZING = 3;
    public static final int WAKEFULNESS_DREAMING = 2;

    public interface LowPowerModeListener {
        int getServiceType();

        void onLowPowerModeChanged(android.os.PowerSaveState powerSaveState);
    }

    public abstract void finishUidChanges();

    public abstract android.os.PowerManager.SleepData getLastGoToSleep();

    public abstract android.os.PowerManager.WakeData getLastWakeup();

    public abstract android.os.PowerSaveState getLowPowerState(int i);

    public abstract boolean interceptPowerKeyDown(android.view.KeyEvent keyEvent);

    public abstract boolean isAmbientDisplaySuppressed();

    public abstract void nap(long j, boolean z);

    public abstract void registerLowPowerModeObserver(android.os.PowerManagerInternal.LowPowerModeListener lowPowerModeListener);

    public abstract void setButtonBrightnessOverrideFromWindowManager(float f);

    public abstract boolean setDeviceIdleMode(boolean z);

    public abstract void setDeviceIdleTempWhitelist(int[] iArr);

    public abstract void setDeviceIdleWhitelist(int[] iArr);

    public abstract void setDozeOverrideFromDreamManager(int i, int i2);

    public abstract void setDrawWakeLockOverrideFromSidekick(boolean z);

    public abstract boolean setLightDeviceIdleMode(boolean z);

    public abstract void setLowPowerStandbyActive(boolean z);

    public abstract void setLowPowerStandbyAllowlist(int[] iArr);

    public abstract void setMaximumScreenOffTimeoutFromDeviceAdmin(int i, long j);

    public abstract void setPowerBoost(int i, int i2);

    public abstract void setPowerMode(int i, boolean z);

    public abstract void setScreenBrightnessOverrideFromWindowManager(float f);

    public abstract void setUserActivityTimeoutOverrideFromWindowManager(long j);

    public abstract void setUserInactiveOverrideFromWindowManager();

    public abstract void startUidChanges();

    public abstract void uidActive(int i);

    public abstract void uidGone(int i);

    public abstract void uidIdle(int i);

    public abstract void updateUidProcState(int i, int i2);

    public abstract boolean wasDeviceIdleFor(long j);

    public static java.lang.String wakefulnessToString(int i) {
        switch (i) {
            case 0:
                return "Asleep";
            case 1:
                return "Awake";
            case 2:
                return "Dreaming";
            case 3:
                return "Dozing";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static int wakefulnessToProtoEnum(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return i;
        }
    }

    public static boolean isInteractive(int i) {
        return i == 1 || i == 2;
    }

    public void registerLowPowerModeObserver(final int i, final java.util.function.Consumer<android.os.PowerSaveState> consumer) {
        registerLowPowerModeObserver(new android.os.PowerManagerInternal.LowPowerModeListener() { // from class: android.os.PowerManagerInternal.1
            @Override // android.os.PowerManagerInternal.LowPowerModeListener
            public int getServiceType() {
                return i;
            }

            @Override // android.os.PowerManagerInternal.LowPowerModeListener
            public void onLowPowerModeChanged(android.os.PowerSaveState powerSaveState) {
                consumer.accept(powerSaveState);
            }
        });
    }
}
