package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
interface DeviceActivityMonitor extends com.android.server.timezonedetector.Dumpable {

    public interface Listener {
        void onFlightComplete();
    }

    void addListener(@android.annotation.NonNull com.android.server.timezonedetector.DeviceActivityMonitor.Listener listener);
}
