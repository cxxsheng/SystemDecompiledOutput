package android.content;

/* loaded from: classes.dex */
public class EventLogTags {
    public static final int BINDER_SAMPLE = 52004;
    public static final int CONTENT_QUERY_SAMPLE = 52002;
    public static final int CONTENT_UPDATE_SAMPLE = 52003;

    private EventLogTags() {
    }

    public static void writeContentQuerySample(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i, java.lang.String str5, int i2) {
        android.util.EventLog.writeEvent(CONTENT_QUERY_SAMPLE, str, str2, str3, str4, java.lang.Integer.valueOf(i), str5, java.lang.Integer.valueOf(i2));
    }

    public static void writeContentUpdateSample(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4, int i2) {
        android.util.EventLog.writeEvent(CONTENT_UPDATE_SAMPLE, str, str2, str3, java.lang.Integer.valueOf(i), str4, java.lang.Integer.valueOf(i2));
    }

    public static void writeBinderSample(java.lang.String str, int i, int i2, java.lang.String str2, int i3) {
        android.util.EventLog.writeEvent(BINDER_SAMPLE, str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str2, java.lang.Integer.valueOf(i3));
    }
}
