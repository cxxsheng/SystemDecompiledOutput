package com.android.server.biometrics;

/* loaded from: classes.dex */
public abstract class BiometricSensor {
    static final int STATE_AUTHENTICATING = 3;
    static final int STATE_CANCELING = 4;
    static final int STATE_COOKIE_RETURNED = 2;
    static final int STATE_STOPPED = 5;
    static final int STATE_UNKNOWN = 0;
    static final int STATE_WAITING_FOR_COOKIE = 1;
    private static final java.lang.String TAG = "BiometricService/Sensor";
    public final int id;
    public final android.hardware.biometrics.IBiometricAuthenticator impl;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private int mCookie;
    private int mError;
    private int mSensorState;
    private int mUpdatedStrength;
    public final int modality;
    public final int oemStrength;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface SensorState {
    }

    abstract boolean confirmationAlwaysRequired(int i);

    abstract boolean confirmationSupported();

    BiometricSensor(@android.annotation.NonNull android.content.Context context, int i, int i2, int i3, android.hardware.biometrics.IBiometricAuthenticator iBiometricAuthenticator) {
        this.mContext = context;
        this.id = i;
        this.modality = i2;
        this.oemStrength = i3;
        this.impl = iBiometricAuthenticator;
        this.mUpdatedStrength = i3;
        goToStateUnknown();
    }

    void goToStateUnknown() {
        this.mSensorState = 0;
        this.mCookie = 0;
        this.mError = 0;
    }

    void goToStateWaitingForCookie(boolean z, android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, java.lang.String str, long j2, int i2, boolean z2, boolean z3) throws android.os.RemoteException {
        this.mCookie = i2;
        this.impl.prepareForAuthentication(z, iBinder, j, i, iBiometricSensorReceiver, str, j2, this.mCookie, z2, z3);
        this.mSensorState = 1;
    }

    void goToStateCookieReturnedIfCookieMatches(int i) {
        if (i == this.mCookie) {
            android.util.Slog.d(TAG, "Sensor(" + this.id + ") matched cookie: " + i);
            this.mSensorState = 2;
        }
    }

    void startSensor() throws android.os.RemoteException {
        this.impl.startPreparedClient(this.mCookie);
        this.mSensorState = 3;
    }

    void goToStateCancelling(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        if (this.mSensorState != 4) {
            this.impl.cancelAuthenticationFromService(iBinder, str, j);
            this.mSensorState = 4;
        }
    }

    void goToStoppedStateIfCookieMatches(int i, int i2) {
        if (i == this.mCookie) {
            android.util.Slog.d(TAG, "Sensor(" + this.id + ") now in STATE_STOPPED");
            this.mError = i2;
            this.mSensorState = 5;
        }
    }

    int getCurrentStrength() {
        return this.oemStrength | this.mUpdatedStrength;
    }

    int getSensorState() {
        return this.mSensorState;
    }

    int getCookie() {
        return this.mCookie;
    }

    void updateStrength(int i) {
        this.mUpdatedStrength = i;
        android.util.Slog.d(TAG, ("updateStrength: Before(" + this + ")") + " After(" + this + ")");
    }

    public java.lang.String toString() {
        return "ID(" + this.id + "), oemStrength: " + this.oemStrength + ", updatedStrength: " + this.mUpdatedStrength + ", modality " + this.modality + ", state: " + this.mSensorState + ", cookie: " + this.mCookie;
    }
}
