package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
class MultiBiometricLockoutState {
    private static final java.lang.String TAG = "MultiBiometricLockoutState";
    private final java.util.Map<java.lang.Integer, java.util.Map<java.lang.Integer, com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState>> mCanUserAuthenticate = new java.util.HashMap();
    private final java.time.Clock mClock;

    MultiBiometricLockoutState(java.time.Clock clock) {
        this.mClock = clock;
    }

    private java.util.Map<java.lang.Integer, com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState> createUnlockedMap() {
        java.util.HashMap hashMap = new java.util.HashMap();
        hashMap.put(15, new com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState(15, false, false));
        hashMap.put(255, new com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState(255, false, false));
        hashMap.put(4095, new com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState(4095, false, false));
        return hashMap;
    }

    private java.util.Map<java.lang.Integer, com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState> getAuthMapForUser(int i) {
        if (!this.mCanUserAuthenticate.containsKey(java.lang.Integer.valueOf(i))) {
            this.mCanUserAuthenticate.put(java.lang.Integer.valueOf(i), createUnlockedMap());
        }
        return this.mCanUserAuthenticate.get(java.lang.Integer.valueOf(i));
    }

    void setPermanentLockOut(int i, int i2) {
        java.util.Map<java.lang.Integer, com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState> authMapForUser = getAuthMapForUser(i);
        switch (i2) {
            case 15:
                authMapForUser.get(15).mPermanentlyLockedOut = true;
            case 255:
                authMapForUser.get(255).mPermanentlyLockedOut = true;
            case 4095:
                authMapForUser.get(4095).mPermanentlyLockedOut = true;
                break;
            default:
                android.util.Slog.e(TAG, "increaseLockoutTime called for invalid strength : " + i2);
                break;
        }
    }

    void clearPermanentLockOut(int i, int i2) {
        java.util.Map<java.lang.Integer, com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState> authMapForUser = getAuthMapForUser(i);
        switch (i2) {
            case 15:
                authMapForUser.get(15).mPermanentlyLockedOut = false;
            case 255:
                authMapForUser.get(255).mPermanentlyLockedOut = false;
            case 4095:
                authMapForUser.get(4095).mPermanentlyLockedOut = false;
                break;
            default:
                android.util.Slog.e(TAG, "increaseLockoutTime called for invalid strength : " + i2);
                break;
        }
    }

    void setTimedLockout(int i, int i2) {
        java.util.Map<java.lang.Integer, com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState> authMapForUser = getAuthMapForUser(i);
        switch (i2) {
            case 15:
                authMapForUser.get(15).mTimedLockout = true;
            case 255:
                authMapForUser.get(255).mTimedLockout = true;
            case 4095:
                authMapForUser.get(4095).mTimedLockout = true;
                break;
            default:
                android.util.Slog.e(TAG, "increaseLockoutTime called for invalid strength : " + i2);
                break;
        }
    }

    void clearTimedLockout(int i, int i2) {
        java.util.Map<java.lang.Integer, com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState> authMapForUser = getAuthMapForUser(i);
        switch (i2) {
            case 15:
                authMapForUser.get(15).mTimedLockout = false;
            case 255:
                authMapForUser.get(255).mTimedLockout = false;
            case 4095:
                authMapForUser.get(4095).mTimedLockout = false;
                break;
            default:
                android.util.Slog.e(TAG, "increaseLockoutTime called for invalid strength : " + i2);
                break;
        }
    }

    int getLockoutState(int i, int i2) {
        java.util.Map<java.lang.Integer, com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState> authMapForUser = getAuthMapForUser(i);
        if (!authMapForUser.containsKey(java.lang.Integer.valueOf(i2))) {
            android.util.Slog.e(TAG, "Error, getLockoutState for unknown strength: " + i2 + " returning LOCKOUT_NONE");
            return 0;
        }
        com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState authenticatorState = authMapForUser.get(java.lang.Integer.valueOf(i2));
        if (authenticatorState.mPermanentlyLockedOut) {
            return 2;
        }
        return authenticatorState.mTimedLockout ? 1 : 0;
    }

    public java.lang.String toString() {
        final long millis = this.mClock.millis();
        java.lang.String str = "Permanent Lockouts\n";
        for (java.util.Map.Entry<java.lang.Integer, java.util.Map<java.lang.Integer, com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState>> entry : this.mCanUserAuthenticate.entrySet()) {
            str = str + "UserId=" + entry.getKey().intValue() + ", {" + ((java.lang.String) entry.getValue().entrySet().stream().map(new java.util.function.Function() { // from class: com.android.server.biometrics.sensors.MultiBiometricLockoutState$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String lambda$toString$0;
                    lambda$toString$0 = com.android.server.biometrics.sensors.MultiBiometricLockoutState.lambda$toString$0(millis, (java.util.Map.Entry) obj);
                    return lambda$toString$0;
                }
            }).collect(java.util.stream.Collectors.joining(", "))) + "}\n";
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$toString$0(long j, java.util.Map.Entry entry) {
        return ((com.android.server.biometrics.sensors.MultiBiometricLockoutState.AuthenticatorState) entry.getValue()).toString(j);
    }

    private static class AuthenticatorState {
        private java.lang.Integer mAuthenticatorType;
        private boolean mPermanentlyLockedOut;
        private boolean mTimedLockout;

        AuthenticatorState(java.lang.Integer num, boolean z, boolean z2) {
            this.mAuthenticatorType = num;
            this.mPermanentlyLockedOut = z;
            this.mTimedLockout = z2;
        }

        java.lang.String toString(long j) {
            return java.lang.String.format("(%s, permanentLockout=%s, timedLockout=%s)", android.hardware.biometrics.BiometricManager.authenticatorToStr(this.mAuthenticatorType.intValue()), this.mPermanentlyLockedOut ? "true" : "false", this.mTimedLockout ? "true" : "false");
        }
    }
}
