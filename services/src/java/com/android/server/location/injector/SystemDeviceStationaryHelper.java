package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemDeviceStationaryHelper extends com.android.server.location.injector.DeviceStationaryHelper {
    private com.android.server.DeviceIdleInternal mDeviceIdle;

    public void onSystemReady() {
        com.android.server.DeviceIdleInternal deviceIdleInternal = (com.android.server.DeviceIdleInternal) com.android.server.LocalServices.getService(com.android.server.DeviceIdleInternal.class);
        java.util.Objects.requireNonNull(deviceIdleInternal);
        this.mDeviceIdle = deviceIdleInternal;
    }

    @Override // com.android.server.location.injector.DeviceStationaryHelper
    public void addListener(com.android.server.DeviceIdleInternal.StationaryListener stationaryListener) {
        com.android.internal.util.Preconditions.checkState(this.mDeviceIdle != null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeviceIdle.registerStationaryListener(stationaryListener);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.DeviceStationaryHelper
    public void removeListener(com.android.server.DeviceIdleInternal.StationaryListener stationaryListener) {
        com.android.internal.util.Preconditions.checkState(this.mDeviceIdle != null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeviceIdle.unregisterStationaryListener(stationaryListener);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
