package com.android.server.deviceidle;

/* loaded from: classes5.dex */
public interface IDeviceIdleConstraint {
    public static final int ACTIVE = 0;
    public static final int SENSING_OR_ABOVE = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MinimumState {
    }

    void startMonitoring();

    void stopMonitoring();
}
