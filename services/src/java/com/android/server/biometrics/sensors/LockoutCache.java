package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class LockoutCache implements com.android.server.biometrics.sensors.LockoutTracker {
    private static final java.lang.String TAG = "LockoutCache";
    private final android.util.SparseIntArray mUserLockoutStates = new android.util.SparseIntArray();

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public void setLockoutModeForUser(int i, int i2) {
        android.util.Slog.d(TAG, "Lockout for user: " + i + " is " + i2);
        synchronized (this) {
            this.mUserLockoutStates.put(i, i2);
        }
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public int getLockoutModeForUser(int i) {
        int i2;
        synchronized (this) {
            i2 = this.mUserLockoutStates.get(i, 0);
        }
        return i2;
    }
}
