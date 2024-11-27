package android.webkit;

/* loaded from: classes4.dex */
public class EventLogTags {
    public static final int BROWSER_DOUBLE_TAP_DURATION = 70102;
    public static final int BROWSER_SNAP_CENTER = 70150;
    public static final int BROWSER_ZOOM_LEVEL_CHANGE = 70101;
    public static final int EXP_DET_ATTEMPT_TO_CALL_OBJECT_GETCLASS = 70151;

    private EventLogTags() {
    }

    public static void writeBrowserZoomLevelChange(int i, int i2, long j) {
        android.util.EventLog.writeEvent(BROWSER_ZOOM_LEVEL_CHANGE, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Long.valueOf(j));
    }

    public static void writeBrowserDoubleTapDuration(int i, long j) {
        android.util.EventLog.writeEvent(BROWSER_DOUBLE_TAP_DURATION, java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j));
    }

    public static void writeBrowserSnapCenter() {
        android.util.EventLog.writeEvent(BROWSER_SNAP_CENTER, new java.lang.Object[0]);
    }

    public static void writeExpDetAttemptToCallObjectGetclass(java.lang.String str) {
        android.util.EventLog.writeEvent(EXP_DET_ATTEMPT_TO_CALL_OBJECT_GETCLASS, str);
    }
}
