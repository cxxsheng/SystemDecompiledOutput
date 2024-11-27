package com.android.server.sensorprivacy;

/* loaded from: classes2.dex */
class SensorState {
    private long mLastChange;
    private int mStateType;

    SensorState(int i) {
        this.mStateType = i;
        this.mLastChange = com.android.server.sensorprivacy.SensorPrivacyService.getCurrentTimeMillis();
    }

    SensorState(int i, long j) {
        this.mStateType = i;
        this.mLastChange = java.lang.Math.min(com.android.server.sensorprivacy.SensorPrivacyService.getCurrentTimeMillis(), j);
    }

    SensorState(com.android.server.sensorprivacy.SensorState sensorState) {
        this.mStateType = sensorState.getState();
        this.mLastChange = sensorState.getLastChange();
    }

    boolean setState(int i) {
        if (this.mStateType != i) {
            this.mStateType = i;
            this.mLastChange = com.android.server.sensorprivacy.SensorPrivacyService.getCurrentTimeMillis();
            return true;
        }
        return false;
    }

    int getState() {
        return this.mStateType;
    }

    long getLastChange() {
        return this.mLastChange;
    }

    private static int enabledToState(boolean z) {
        return z ? 1 : 2;
    }

    SensorState(boolean z) {
        this(enabledToState(z));
    }

    boolean setEnabled(boolean z) {
        return setState(enabledToState(z));
    }

    boolean isEnabled() {
        return getState() == 1;
    }
}
