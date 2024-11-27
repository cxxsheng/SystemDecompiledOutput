package android.os;

/* loaded from: classes3.dex */
public class EventLogTags {
    public static final int SERVICE_MANAGER_SLOW = 230001;
    public static final int SERVICE_MANAGER_STATS = 230000;

    private EventLogTags() {
    }

    public static void writeServiceManagerStats(int i, int i2, int i3) {
        android.util.EventLog.writeEvent(SERVICE_MANAGER_STATS, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3));
    }

    public static void writeServiceManagerSlow(int i, java.lang.String str) {
        android.util.EventLog.writeEvent(SERVICE_MANAGER_SLOW, java.lang.Integer.valueOf(i), str);
    }
}
