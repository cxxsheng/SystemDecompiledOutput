package android.net;

/* loaded from: classes2.dex */
public class EventLogTags {
    public static final int NTP_FAILURE = 50081;
    public static final int NTP_SUCCESS = 50080;

    private EventLogTags() {
    }

    public static void writeNtpSuccess(java.lang.String str, long j, long j2) {
        android.util.EventLog.writeEvent(NTP_SUCCESS, str, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2));
    }

    public static void writeNtpFailure(java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(NTP_FAILURE, str, str2);
    }
}
