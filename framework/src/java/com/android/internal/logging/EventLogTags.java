package com.android.internal.logging;

/* loaded from: classes4.dex */
public class EventLogTags {
    public static final int COMMIT_SYS_CONFIG_FILE = 525000;
    public static final int SYSUI_ACTION = 524288;
    public static final int SYSUI_COUNT = 524290;
    public static final int SYSUI_HISTOGRAM = 524291;
    public static final int SYSUI_LATENCY = 36070;
    public static final int SYSUI_MULTI_ACTION = 524292;
    public static final int SYSUI_VIEW_VISIBILITY = 524287;

    private EventLogTags() {
    }

    public static void writeSysuiViewVisibility(int i, int i2) {
        android.util.EventLog.writeEvent(524287, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public static void writeSysuiAction(int i, java.lang.String str) {
        android.util.EventLog.writeEvent(524288, java.lang.Integer.valueOf(i), str);
    }

    public static void writeSysuiMultiAction(java.lang.Object[] objArr) {
        android.util.EventLog.writeEvent(524292, objArr);
    }

    public static void writeSysuiCount(java.lang.String str, int i) {
        android.util.EventLog.writeEvent(SYSUI_COUNT, str, java.lang.Integer.valueOf(i));
    }

    public static void writeSysuiHistogram(java.lang.String str, int i) {
        android.util.EventLog.writeEvent(SYSUI_HISTOGRAM, str, java.lang.Integer.valueOf(i));
    }

    public static void writeSysuiLatency(int i, int i2) {
        android.util.EventLog.writeEvent(36070, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    public static void writeCommitSysConfigFile(java.lang.String str, long j) {
        android.util.EventLog.writeEvent(COMMIT_SYS_CONFIG_FILE, str, java.lang.Long.valueOf(j));
    }
}
