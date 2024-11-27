package com.android.internal.jank;

/* loaded from: classes4.dex */
public class EventLogTags {
    public static final int JANK_CUJ_EVENTS_BEGIN_REQUEST = 37001;
    public static final int JANK_CUJ_EVENTS_CANCEL_REQUEST = 37003;
    public static final int JANK_CUJ_EVENTS_END_REQUEST = 37002;

    private EventLogTags() {
    }

    public static void writeJankCujEventsBeginRequest(int i, long j, long j2, long j3, java.lang.String str) {
        android.util.EventLog.writeEvent(JANK_CUJ_EVENTS_BEGIN_REQUEST, java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3), str);
    }

    public static void writeJankCujEventsEndRequest(int i, long j, long j2, long j3) {
        android.util.EventLog.writeEvent(JANK_CUJ_EVENTS_END_REQUEST, java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3));
    }

    public static void writeJankCujEventsCancelRequest(int i, long j, long j2, long j3) {
        android.util.EventLog.writeEvent(JANK_CUJ_EVENTS_CANCEL_REQUEST, java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3));
    }
}
