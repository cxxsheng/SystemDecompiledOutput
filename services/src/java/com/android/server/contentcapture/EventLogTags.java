package com.android.server.contentcapture;

/* loaded from: classes.dex */
public class EventLogTags {
    public static final int CC_CONNECT_STATE_CHANGED = 53200;
    public static final int CC_CURRENT_ALLOWLIST = 53202;
    public static final int CC_SET_ALLOWLIST = 53201;
    public static final int CC_UPDATE_OPTIONS = 53203;

    private EventLogTags() {
    }

    public static void writeCcConnectStateChanged(int i, int i2, int i3) {
        android.util.EventLog.writeEvent(CC_CONNECT_STATE_CHANGED, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3));
    }

    public static void writeCcSetAllowlist(int i, int i2, int i3) {
        android.util.EventLog.writeEvent(CC_SET_ALLOWLIST, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3));
    }

    public static void writeCcCurrentAllowlist(int i, int i2) {
        android.util.EventLog.writeEvent(CC_CURRENT_ALLOWLIST, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public static void writeCcUpdateOptions(int i, int i2) {
        android.util.EventLog.writeEvent(CC_UPDATE_OPTIONS, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }
}
