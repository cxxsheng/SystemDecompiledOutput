package com.android.server.biometrics.sensors.face;

/* loaded from: classes.dex */
public class LockoutHalImpl implements com.android.server.biometrics.sensors.LockoutTracker {
    private int mCurrentUserLockoutMode;

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public int getLockoutModeForUser(int i) {
        return this.mCurrentUserLockoutMode;
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public void setLockoutModeForUser(int i, int i2) {
        setCurrentUserLockoutMode(i2);
    }

    public void setCurrentUserLockoutMode(int i) {
        this.mCurrentUserLockoutMode = i;
    }
}
