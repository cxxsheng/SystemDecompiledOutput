package android.app;

/* loaded from: classes.dex */
public class EventLogTags {
    public static final int WM_ON_ACTIVITY_RESULT_CALLED = 30062;
    public static final int WM_ON_CREATE_CALLED = 30057;
    public static final int WM_ON_DESTROY_CALLED = 30060;
    public static final int WM_ON_PAUSED_CALLED = 30021;
    public static final int WM_ON_RESTART_CALLED = 30058;
    public static final int WM_ON_RESUME_CALLED = 30022;
    public static final int WM_ON_START_CALLED = 30059;
    public static final int WM_ON_STOP_CALLED = 30049;
    public static final int WM_ON_TOP_RESUMED_GAINED_CALLED = 30064;
    public static final int WM_ON_TOP_RESUMED_LOST_CALLED = 30065;

    private EventLogTags() {
    }

    public static void writeWmOnPausedCalled(int i, java.lang.String str, java.lang.String str2, long j) {
        android.util.EventLog.writeEvent(WM_ON_PAUSED_CALLED, java.lang.Integer.valueOf(i), str, str2, java.lang.Long.valueOf(j));
    }

    public static void writeWmOnResumeCalled(int i, java.lang.String str, java.lang.String str2, long j) {
        android.util.EventLog.writeEvent(WM_ON_RESUME_CALLED, java.lang.Integer.valueOf(i), str, str2, java.lang.Long.valueOf(j));
    }

    public static void writeWmOnStopCalled(int i, java.lang.String str, java.lang.String str2, long j) {
        android.util.EventLog.writeEvent(WM_ON_STOP_CALLED, java.lang.Integer.valueOf(i), str, str2, java.lang.Long.valueOf(j));
    }

    public static void writeWmOnCreateCalled(int i, java.lang.String str, java.lang.String str2, long j) {
        android.util.EventLog.writeEvent(WM_ON_CREATE_CALLED, java.lang.Integer.valueOf(i), str, str2, java.lang.Long.valueOf(j));
    }

    public static void writeWmOnRestartCalled(int i, java.lang.String str, java.lang.String str2, long j) {
        android.util.EventLog.writeEvent(WM_ON_RESTART_CALLED, java.lang.Integer.valueOf(i), str, str2, java.lang.Long.valueOf(j));
    }

    public static void writeWmOnStartCalled(int i, java.lang.String str, java.lang.String str2, long j) {
        android.util.EventLog.writeEvent(WM_ON_START_CALLED, java.lang.Integer.valueOf(i), str, str2, java.lang.Long.valueOf(j));
    }

    public static void writeWmOnDestroyCalled(int i, java.lang.String str, java.lang.String str2, long j) {
        android.util.EventLog.writeEvent(WM_ON_DESTROY_CALLED, java.lang.Integer.valueOf(i), str, str2, java.lang.Long.valueOf(j));
    }

    public static void writeWmOnActivityResultCalled(int i, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_ON_ACTIVITY_RESULT_CALLED, java.lang.Integer.valueOf(i), str, str2);
    }

    public static void writeWmOnTopResumedGainedCalled(int i, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_ON_TOP_RESUMED_GAINED_CALLED, java.lang.Integer.valueOf(i), str, str2);
    }

    public static void writeWmOnTopResumedLostCalled(int i, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_ON_TOP_RESUMED_LOST_CALLED, java.lang.Integer.valueOf(i), str, str2);
    }
}
