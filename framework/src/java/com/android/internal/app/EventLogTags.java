package com.android.internal.app;

/* loaded from: classes4.dex */
public class EventLogTags {
    public static final int HARMFUL_APP_WARNING_LAUNCH_ANYWAY = 53001;
    public static final int HARMFUL_APP_WARNING_UNINSTALL = 53000;

    private EventLogTags() {
    }

    public static void writeHarmfulAppWarningUninstall(java.lang.String str) {
        android.util.EventLog.writeEvent(HARMFUL_APP_WARNING_UNINSTALL, str);
    }

    public static void writeHarmfulAppWarningLaunchAnyway(java.lang.String str) {
        android.util.EventLog.writeEvent(HARMFUL_APP_WARNING_LAUNCH_ANYWAY, str);
    }
}
