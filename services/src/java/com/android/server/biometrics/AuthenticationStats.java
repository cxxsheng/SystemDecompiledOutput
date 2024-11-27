package com.android.server.biometrics;

/* loaded from: classes.dex */
public class AuthenticationStats {
    private static final float FRR_NOT_ENOUGH_ATTEMPTS = -1.0f;
    private static final java.lang.String TAG = "AuthenticationStats";
    private int mEnrollmentNotifications;
    private final int mModality;
    private int mRejectedAttempts;
    private int mTotalAttempts;
    private final int mUserId;

    public AuthenticationStats(int i, int i2, int i3, int i4, int i5) {
        this.mUserId = i;
        this.mTotalAttempts = i2;
        this.mRejectedAttempts = i3;
        this.mEnrollmentNotifications = i4;
        this.mModality = i5;
    }

    public AuthenticationStats(int i, int i2) {
        this.mUserId = i;
        this.mTotalAttempts = 0;
        this.mRejectedAttempts = 0;
        this.mEnrollmentNotifications = 0;
        this.mModality = i2;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public int getTotalAttempts() {
        return this.mTotalAttempts;
    }

    public int getRejectedAttempts() {
        return this.mRejectedAttempts;
    }

    public int getEnrollmentNotifications() {
        return this.mEnrollmentNotifications;
    }

    public int getModality() {
        return this.mModality;
    }

    public float getFrr() {
        if (this.mTotalAttempts > 0) {
            return this.mRejectedAttempts / this.mTotalAttempts;
        }
        return -1.0f;
    }

    public void authenticate(boolean z) {
        if (!z) {
            this.mRejectedAttempts++;
        }
        this.mTotalAttempts++;
    }

    public void resetData() {
        this.mTotalAttempts = 0;
        this.mRejectedAttempts = 0;
        android.util.Slog.d(TAG, "Reset Counters.");
    }

    public void updateNotificationCounter() {
        this.mEnrollmentNotifications++;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.biometrics.AuthenticationStats)) {
            return false;
        }
        com.android.server.biometrics.AuthenticationStats authenticationStats = (com.android.server.biometrics.AuthenticationStats) obj;
        return getUserId() == authenticationStats.getUserId() && getTotalAttempts() == authenticationStats.getTotalAttempts() && getRejectedAttempts() == authenticationStats.getRejectedAttempts() && getEnrollmentNotifications() == authenticationStats.getEnrollmentNotifications() && getModality() == authenticationStats.getModality();
    }

    public int hashCode() {
        return java.lang.String.format("userId: %d, totalAttempts: %d, rejectedAttempts: %d, enrollmentNotifications: %d, modality: %d", java.lang.Integer.valueOf(this.mUserId), java.lang.Integer.valueOf(this.mTotalAttempts), java.lang.Integer.valueOf(this.mRejectedAttempts), java.lang.Integer.valueOf(this.mEnrollmentNotifications), java.lang.Integer.valueOf(this.mModality)).hashCode();
    }
}
