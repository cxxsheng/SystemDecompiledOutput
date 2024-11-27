package com.android.server;

/* loaded from: classes5.dex */
public interface DeviceIdleInternal {

    public interface StationaryListener {
        void onDeviceStationaryChanged(boolean z);
    }

    void addPowerSaveTempWhitelistApp(int i, java.lang.String str, long j, int i2, int i3, boolean z, int i4, java.lang.String str2);

    void addPowerSaveTempWhitelistApp(int i, java.lang.String str, long j, int i2, boolean z, int i3, java.lang.String str2);

    void addPowerSaveTempWhitelistAppDirect(int i, long j, int i2, boolean z, int i3, java.lang.String str, int i4);

    void exitIdle(java.lang.String str);

    java.lang.String[] getFullPowerWhitelistExceptIdle();

    long getNotificationAllowlistDuration();

    int[] getPowerSaveTempWhitelistAppIds();

    int[] getPowerSaveWhitelistSystemAppIds();

    int[] getPowerSaveWhitelistUserAppIds();

    int getTempAllowListType(int i, int i2);

    boolean isAppOnWhitelist(int i);

    void onConstraintStateChanged(com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint, boolean z);

    void registerDeviceIdleConstraint(com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint, java.lang.String str, int i);

    void registerStationaryListener(com.android.server.DeviceIdleInternal.StationaryListener stationaryListener);

    void setAlarmsActive(boolean z);

    void setJobsActive(boolean z);

    void unregisterDeviceIdleConstraint(com.android.server.deviceidle.IDeviceIdleConstraint iDeviceIdleConstraint);

    void unregisterStationaryListener(com.android.server.DeviceIdleInternal.StationaryListener stationaryListener);
}
