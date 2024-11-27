package org.chromium.arc;

/* loaded from: classes5.dex */
public class EventLogTags {
    public static final int ARC_SYSTEM_EVENT = 300000;

    private EventLogTags() {
    }

    public static void writeArcSystemEvent(java.lang.String str) {
        android.util.EventLog.writeEvent(ARC_SYSTEM_EVENT, str);
    }
}
