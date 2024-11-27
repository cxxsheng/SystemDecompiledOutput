package com.android.server.deviceidle;

/* loaded from: classes.dex */
public class DeviceIdleConstraintTracker {
    public final int minState;
    public final java.lang.String name;
    public boolean active = false;
    public boolean monitoring = false;

    public DeviceIdleConstraintTracker(java.lang.String str, int i) {
        this.name = str;
        this.minState = i;
    }
}
