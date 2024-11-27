package com.android.server.devicestate;

/* loaded from: classes.dex */
final class OverrideRequest {
    public static final int OVERRIDE_REQUEST_TYPE_BASE_STATE = 1;
    public static final int OVERRIDE_REQUEST_TYPE_EMULATED_STATE = 0;
    private final int mFlags;
    private final int mPid;
    private final int mRequestType;

    @android.annotation.NonNull
    private final android.hardware.devicestate.DeviceState mRequestedState;
    private final android.os.IBinder mToken;
    private final int mUid;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OverrideRequestType {
    }

    OverrideRequest(android.os.IBinder iBinder, int i, int i2, @android.annotation.NonNull android.hardware.devicestate.DeviceState deviceState, int i3, int i4) {
        this.mToken = iBinder;
        this.mPid = i;
        this.mUid = i2;
        this.mRequestedState = deviceState;
        this.mFlags = i3;
        this.mRequestType = i4;
    }

    android.os.IBinder getToken() {
        return this.mToken;
    }

    int getPid() {
        return this.mPid;
    }

    int getUid() {
        return this.mUid;
    }

    @android.annotation.NonNull
    android.hardware.devicestate.DeviceState getRequestedDeviceState() {
        return this.mRequestedState;
    }

    int getRequestedStateIdentifier() {
        return this.mRequestedState.getIdentifier();
    }

    int getFlags() {
        return this.mFlags;
    }

    int getRequestType() {
        return this.mRequestType;
    }
}
