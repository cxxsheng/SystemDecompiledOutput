package com.android.server.policy;

/* loaded from: classes2.dex */
public class EventLogTags {
    public static final int INTERCEPT_POWER = 70001;
    public static final int SCREEN_TOGGLED = 70000;

    private EventLogTags() {
    }

    public static void writeScreenToggled(int i) {
        android.util.EventLog.writeEvent(SCREEN_TOGGLED, i);
    }

    public static void writeInterceptPower(java.lang.String str, int i, int i2) {
        android.util.EventLog.writeEvent(INTERCEPT_POWER, str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }
}
