package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class AuthSessionCoordinator implements com.android.server.biometrics.sensors.AuthSessionListener {
    private static final java.lang.String TAG = "AuthSessionCoordinator";
    private final java.util.Set<java.lang.Integer> mAuthOperations;
    private com.android.server.biometrics.sensors.AuthResultCoordinator mAuthResultCoordinator;
    private boolean mIsAuthenticating;
    private final com.android.server.biometrics.sensors.MultiBiometricLockoutState mMultiBiometricLockoutState;
    private final com.android.server.biometrics.sensors.AuthSessionCoordinator.RingBuffer mRingBuffer;
    private int mUserId;

    public AuthSessionCoordinator() {
        this(android.os.SystemClock.elapsedRealtimeClock());
    }

    @com.android.internal.annotations.VisibleForTesting
    AuthSessionCoordinator(java.time.Clock clock) {
        this.mAuthOperations = new java.util.HashSet();
        this.mAuthResultCoordinator = new com.android.server.biometrics.sensors.AuthResultCoordinator();
        this.mMultiBiometricLockoutState = new com.android.server.biometrics.sensors.MultiBiometricLockoutState(clock);
        this.mRingBuffer = new com.android.server.biometrics.sensors.AuthSessionCoordinator.RingBuffer(100);
    }

    void onAuthSessionStarted(int i) {
        this.mAuthOperations.clear();
        this.mUserId = i;
        this.mIsAuthenticating = true;
        this.mAuthResultCoordinator = new com.android.server.biometrics.sensors.AuthResultCoordinator();
        this.mRingBuffer.addApiCall("internal : onAuthSessionStarted(" + i + ")");
    }

    void endAuthSession() {
        java.util.Map<java.lang.Integer, java.lang.Integer> result = this.mAuthResultCoordinator.getResult();
        java.util.Iterator it = java.util.Arrays.asList(4095, 255, 15).iterator();
        while (it.hasNext()) {
            int intValue = ((java.lang.Integer) it.next()).intValue();
            java.lang.Integer num = result.get(java.lang.Integer.valueOf(intValue));
            if ((num.intValue() & 4) == 4) {
                this.mMultiBiometricLockoutState.clearPermanentLockOut(this.mUserId, intValue);
                this.mMultiBiometricLockoutState.clearTimedLockout(this.mUserId, intValue);
            } else if ((num.intValue() & 1) == 1) {
                this.mMultiBiometricLockoutState.setPermanentLockOut(this.mUserId, intValue);
            } else if ((num.intValue() & 2) == 2) {
                this.mMultiBiometricLockoutState.setTimedLockout(this.mUserId, intValue);
            }
        }
        if (this.mAuthOperations.isEmpty()) {
            this.mRingBuffer.addApiCall("internal : onAuthSessionEnded(" + this.mUserId + ")");
            clearSession();
        }
    }

    private void clearSession() {
        this.mIsAuthenticating = false;
        this.mAuthOperations.clear();
    }

    public int getLockoutStateFor(int i, int i2) {
        return this.mMultiBiometricLockoutState.getLockoutState(i, i2);
    }

    @Override // com.android.server.biometrics.sensors.AuthSessionListener
    public void authStartedFor(int i, int i2, long j) {
        this.mRingBuffer.addApiCall("authStartedFor(userId=" + i + ", sensorId=" + i2 + ", requestId=" + j + ")");
        if (!this.mIsAuthenticating) {
            onAuthSessionStarted(i);
        }
        if (this.mAuthOperations.contains(java.lang.Integer.valueOf(i2))) {
            android.util.Slog.e(TAG, "Error, authStartedFor(" + i2 + ") without being finished");
            return;
        }
        if (this.mUserId != i) {
            android.util.Slog.e(TAG, "Error authStartedFor(" + i + ") Incorrect userId, expected" + this.mUserId + ", ignoring...");
            return;
        }
        this.mAuthOperations.add(java.lang.Integer.valueOf(i2));
    }

    @Override // com.android.server.biometrics.sensors.AuthSessionListener
    public void lockedOutFor(int i, int i2, int i3, long j) {
        java.lang.String str = "lockOutFor(userId=" + i + ", biometricStrength=" + i2 + ", sensorId=" + i3 + ", requestId=" + j + ")";
        this.mRingBuffer.addApiCall(str);
        this.mAuthResultCoordinator.lockedOutFor(i2);
        attemptToFinish(i, i3, str);
    }

    @Override // com.android.server.biometrics.sensors.AuthSessionListener
    public void lockOutTimed(int i, int i2, int i3, long j, long j2) {
        java.lang.String str = "lockOutTimedFor(userId=" + i + ", biometricStrength=" + i2 + ", sensorId=" + i3 + "time=" + j + ", requestId=" + j2 + ")";
        this.mRingBuffer.addApiCall(str);
        this.mAuthResultCoordinator.lockOutTimed(i2);
        attemptToFinish(i, i3, str);
    }

    @Override // com.android.server.biometrics.sensors.AuthSessionListener
    public void authEndedFor(int i, int i2, int i3, long j, boolean z) {
        java.lang.String str = "authEndedFor(userId=" + i + " ,biometricStrength=" + i2 + ", sensorId=" + i3 + ", requestId=" + j + ", wasSuccessful=" + z + ")";
        this.mRingBuffer.addApiCall(str);
        if (z) {
            this.mAuthResultCoordinator.authenticatedFor(i2);
        }
        attemptToFinish(i, i3, str);
    }

    @Override // com.android.server.biometrics.sensors.AuthSessionListener
    public void resetLockoutFor(int i, int i2, long j) {
        this.mRingBuffer.addApiCall("resetLockoutFor(userId=" + i + " ,biometricStrength=" + i2 + ", requestId=" + j + ")");
        if (i2 == 15) {
            clearSession();
            this.mMultiBiometricLockoutState.clearPermanentLockOut(i, i2);
            this.mMultiBiometricLockoutState.clearTimedLockout(i, i2);
        }
    }

    private void attemptToFinish(int i, int i2, java.lang.String str) {
        boolean z;
        boolean z2 = true;
        if (this.mAuthOperations.contains(java.lang.Integer.valueOf(i2))) {
            z = false;
        } else {
            android.util.Slog.e(TAG, "Error unable to find auth operation : " + str);
            z = true;
        }
        if (i == this.mUserId) {
            z2 = z;
        } else {
            android.util.Slog.e(TAG, "Error mismatched userId, expected=" + this.mUserId + " for " + str);
        }
        if (z2) {
            return;
        }
        this.mAuthOperations.remove(java.lang.Integer.valueOf(i2));
        if (this.mIsAuthenticating) {
            endAuthSession();
        }
    }

    public java.lang.String toString() {
        return this.mRingBuffer + "\n" + this.mMultiBiometricLockoutState;
    }

    private static class RingBuffer {
        private int mApiCallNumber;
        private final java.lang.String[] mApiCalls;
        private int mCurr;
        private final int mSize;

        RingBuffer(int i) {
            if (i <= 0) {
                android.util.Slog.wtf(com.android.server.biometrics.sensors.AuthSessionCoordinator.TAG, "Cannot initialize ring buffer of size: " + i);
            }
            this.mApiCalls = new java.lang.String[i];
            this.mCurr = 0;
            this.mSize = i;
            this.mApiCallNumber = 0;
        }

        void addApiCall(java.lang.String str) {
            this.mApiCalls[this.mCurr] = str;
            this.mCurr++;
            this.mCurr %= this.mSize;
            this.mApiCallNumber++;
        }

        public java.lang.String toString() {
            int i = this.mApiCallNumber > this.mSize ? this.mApiCallNumber - this.mSize : 0;
            java.lang.String str = "";
            for (int i2 = 0; i2 < this.mSize; i2++) {
                int i3 = (this.mCurr + i2) % this.mSize;
                if (this.mApiCalls[i3] != null) {
                    str = str + java.lang.String.format("#%-5d %s\n", java.lang.Integer.valueOf(i), this.mApiCalls[i3]);
                    i++;
                }
            }
            return str;
        }
    }
}
