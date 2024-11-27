package com.android.internal.os;

/* loaded from: classes4.dex */
public final class RpmStats {
    public java.util.Map<java.lang.String, com.android.internal.os.RpmStats.PowerStatePlatformSleepState> mPlatformLowPowerStats = new android.util.ArrayMap();
    public java.util.Map<java.lang.String, com.android.internal.os.RpmStats.PowerStateSubsystem> mSubsystemLowPowerStats = new android.util.ArrayMap();

    public com.android.internal.os.RpmStats.PowerStatePlatformSleepState getAndUpdatePlatformState(java.lang.String str, long j, int i) {
        com.android.internal.os.RpmStats.PowerStatePlatformSleepState powerStatePlatformSleepState = this.mPlatformLowPowerStats.get(str);
        if (powerStatePlatformSleepState == null) {
            powerStatePlatformSleepState = new com.android.internal.os.RpmStats.PowerStatePlatformSleepState();
            this.mPlatformLowPowerStats.put(str, powerStatePlatformSleepState);
        }
        powerStatePlatformSleepState.mTimeMs = j;
        powerStatePlatformSleepState.mCount = i;
        return powerStatePlatformSleepState;
    }

    public com.android.internal.os.RpmStats.PowerStateSubsystem getSubsystem(java.lang.String str) {
        com.android.internal.os.RpmStats.PowerStateSubsystem powerStateSubsystem = this.mSubsystemLowPowerStats.get(str);
        if (powerStateSubsystem == null) {
            com.android.internal.os.RpmStats.PowerStateSubsystem powerStateSubsystem2 = new com.android.internal.os.RpmStats.PowerStateSubsystem();
            this.mSubsystemLowPowerStats.put(str, powerStateSubsystem2);
            return powerStateSubsystem2;
        }
        return powerStateSubsystem;
    }

    public static class PowerStateElement {
        public int mCount;
        public long mTimeMs;

        private PowerStateElement(long j, int i) {
            this.mTimeMs = j;
            this.mCount = i;
        }
    }

    public static class PowerStatePlatformSleepState {
        public int mCount;
        public long mTimeMs;
        public java.util.Map<java.lang.String, com.android.internal.os.RpmStats.PowerStateElement> mVoters = new android.util.ArrayMap();

        public void putVoter(java.lang.String str, long j, int i) {
            com.android.internal.os.RpmStats.PowerStateElement powerStateElement = this.mVoters.get(str);
            if (powerStateElement == null) {
                this.mVoters.put(str, new com.android.internal.os.RpmStats.PowerStateElement(j, i));
            } else {
                powerStateElement.mTimeMs = j;
                powerStateElement.mCount = i;
            }
        }
    }

    public static class PowerStateSubsystem {
        public java.util.Map<java.lang.String, com.android.internal.os.RpmStats.PowerStateElement> mStates = new android.util.ArrayMap();

        public void putState(java.lang.String str, long j, int i) {
            com.android.internal.os.RpmStats.PowerStateElement powerStateElement = this.mStates.get(str);
            if (powerStateElement == null) {
                this.mStates.put(str, new com.android.internal.os.RpmStats.PowerStateElement(j, i));
            } else {
                powerStateElement.mTimeMs = j;
                powerStateElement.mCount = i;
            }
        }
    }
}
