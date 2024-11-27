package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
class AuthResult {
    static final int AUTHENTICATED = 2;
    static final int FAILED = 0;
    static final int LOCKED_OUT = 1;
    private final int mBiometricStrength;
    private final int mStatus;

    AuthResult(int i, int i2) {
        this.mStatus = i;
        this.mBiometricStrength = i2;
    }

    int getStatus() {
        return this.mStatus;
    }

    int getBiometricStrength() {
        return this.mBiometricStrength;
    }
}
