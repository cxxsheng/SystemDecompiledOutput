package com.android.server.utils;

/* loaded from: classes2.dex */
public class EventLogger {
    private static final java.lang.String DUMP_TITLE_PREFIX = "Events log: ";
    private final java.util.ArrayDeque<com.android.server.utils.EventLogger.Event> mEvents;
    private final int mMemSize;

    @android.annotation.Nullable
    private final java.lang.String mTag;

    public interface DumpSink {
        void sink(java.lang.String str, java.util.List<com.android.server.utils.EventLogger.Event> list);
    }

    public EventLogger(int i, @android.annotation.Nullable java.lang.String str) {
        this.mEvents = new java.util.ArrayDeque<>(i);
        this.mMemSize = i;
        this.mTag = str;
    }

    public synchronized void enqueue(com.android.server.utils.EventLogger.Event event) {
        try {
            if (this.mEvents.size() >= this.mMemSize) {
                this.mEvents.removeFirst();
            }
            this.mEvents.addLast(event);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void enqueueAndLog(java.lang.String str, int i, java.lang.String str2) {
        enqueue(new com.android.server.utils.EventLogger.StringEvent(str).printLog(i, str2));
    }

    public synchronized void enqueueAndSlog(java.lang.String str, int i, java.lang.String str2) {
        enqueue(new com.android.server.utils.EventLogger.StringEvent(str).printSlog(i, str2));
    }

    public synchronized void dump(com.android.server.utils.EventLogger.DumpSink dumpSink) {
        dumpSink.sink(this.mTag, new java.util.ArrayList(this.mEvents));
    }

    public synchronized void dump(java.io.PrintWriter printWriter) {
        dump(printWriter, "");
    }

    protected java.lang.String getDumpTitle() {
        if (this.mTag == null) {
            return DUMP_TITLE_PREFIX;
        }
        return DUMP_TITLE_PREFIX + this.mTag;
    }

    public synchronized void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(getDumpTitle());
        java.util.Iterator<com.android.server.utils.EventLogger.Event> it = this.mEvents.iterator();
        while (it.hasNext()) {
            printWriter.println(str + it.next().toString());
        }
    }

    public static abstract class Event {
        public static final int ALOGE = 1;
        public static final int ALOGI = 0;
        public static final int ALOGV = 3;
        public static final int ALOGW = 2;
        private static final java.text.SimpleDateFormat sFormat = new java.text.SimpleDateFormat("MM-dd HH:mm:ss:SSS", java.util.Locale.US);
        private final long mTimestamp = java.lang.System.currentTimeMillis();

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface LogType {
        }

        public abstract java.lang.String eventToString();

        public java.lang.String toString() {
            return sFormat.format(new java.util.Date(this.mTimestamp)) + " " + eventToString();
        }

        public com.android.server.utils.EventLogger.Event printLog(java.lang.String str) {
            return printLog(0, str);
        }

        public com.android.server.utils.EventLogger.Event printLog(int i, java.lang.String str) {
            switch (i) {
                case 0:
                    android.util.Log.i(str, eventToString());
                    return this;
                case 1:
                    android.util.Log.e(str, eventToString());
                    return this;
                case 2:
                    android.util.Log.w(str, eventToString());
                    return this;
                default:
                    android.util.Log.v(str, eventToString());
                    return this;
            }
        }

        public com.android.server.utils.EventLogger.Event printSlog(int i, java.lang.String str) {
            switch (i) {
                case 0:
                    android.util.Slog.i(str, eventToString());
                    return this;
                case 1:
                    android.util.Slog.e(str, eventToString());
                    return this;
                case 2:
                    android.util.Slog.w(str, eventToString());
                    return this;
                default:
                    android.util.Slog.v(str, eventToString());
                    return this;
            }
        }
    }

    public static class StringEvent extends com.android.server.utils.EventLogger.Event {
        private final java.lang.String mDescription;

        @android.annotation.Nullable
        private final java.lang.String mSource;

        public static com.android.server.utils.EventLogger.StringEvent from(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, java.lang.Object... objArr) {
            return new com.android.server.utils.EventLogger.StringEvent(str, java.lang.String.format(java.util.Locale.US, str2, objArr));
        }

        public StringEvent(java.lang.String str) {
            this(null, str);
        }

        public StringEvent(java.lang.String str, java.lang.String str2) {
            this.mSource = str;
            this.mDescription = str2;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            if (this.mSource == null) {
                return this.mDescription;
            }
            return java.lang.String.format("[%-40s] %s", this.mSource, this.mDescription == null ? "" : this.mDescription);
        }
    }
}
