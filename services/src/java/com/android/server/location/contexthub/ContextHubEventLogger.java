package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
public class ContextHubEventLogger {
    public static final int NUM_EVENTS_TO_STORE = 20;
    private static final java.lang.String TAG = "ContextHubEventLogger";
    private static com.android.server.location.contexthub.ContextHubEventLogger sInstance = null;
    private final com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<com.android.server.location.contexthub.ContextHubEventLogger.NanoappLoadEvent> mNanoappLoadEventQueue = new com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<>(20);
    private final com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<com.android.server.location.contexthub.ContextHubEventLogger.NanoappUnloadEvent> mNanoappUnloadEventQueue = new com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<>(20);
    private final com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<com.android.server.location.contexthub.ContextHubEventLogger.NanoappMessageEvent> mMessageFromNanoappQueue = new com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<>(20);
    private final com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<com.android.server.location.contexthub.ContextHubEventLogger.NanoappMessageEvent> mMessageToNanoappQueue = new com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<>(20);
    private final com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<com.android.server.location.contexthub.ContextHubEventLogger.ContextHubRestartEvent> mContextHubRestartEventQueue = new com.android.server.location.contexthub.ConcurrentLinkedEvictingDeque<>(20);

    public static class ContextHubEventBase {
        public final int contextHubId;
        public final long timeStampInMs;

        public ContextHubEventBase(long j, int i) {
            this.timeStampInMs = j;
            this.contextHubId = i;
        }
    }

    public static class NanoappEventBase extends com.android.server.location.contexthub.ContextHubEventLogger.ContextHubEventBase {
        public final long nanoappId;
        public final boolean success;

        public NanoappEventBase(long j, int i, long j2, boolean z) {
            super(j, i);
            this.nanoappId = j2;
            this.success = z;
        }
    }

    public static class NanoappLoadEvent extends com.android.server.location.contexthub.ContextHubEventLogger.NanoappEventBase {
        public final long nanoappSize;
        public final int nanoappVersion;

        public NanoappLoadEvent(long j, int i, long j2, int i2, long j3, boolean z) {
            super(j, i, j2, z);
            this.nanoappVersion = i2;
            this.nanoappSize = j3;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(com.android.server.location.contexthub.ContextHubServiceUtil.formatDateFromTimestamp(this.timeStampInMs));
            sb.append(": NanoappLoadEvent[hubId = ");
            sb.append(this.contextHubId);
            sb.append(", appId = 0x");
            sb.append(java.lang.Long.toHexString(this.nanoappId));
            sb.append(", appVersion = ");
            sb.append(this.nanoappVersion);
            sb.append(", appSize = ");
            sb.append(this.nanoappSize);
            sb.append(" bytes, success = ");
            sb.append(this.success ? "true" : "false");
            sb.append(']');
            return sb.toString();
        }
    }

    public static class NanoappUnloadEvent extends com.android.server.location.contexthub.ContextHubEventLogger.NanoappEventBase {
        public NanoappUnloadEvent(long j, int i, long j2, boolean z) {
            super(j, i, j2, z);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(com.android.server.location.contexthub.ContextHubServiceUtil.formatDateFromTimestamp(this.timeStampInMs));
            sb.append(": NanoappUnloadEvent[hubId = ");
            sb.append(this.contextHubId);
            sb.append(", appId = 0x");
            sb.append(java.lang.Long.toHexString(this.nanoappId));
            sb.append(", success = ");
            sb.append(this.success ? "true" : "false");
            sb.append(']');
            return sb.toString();
        }
    }

    public static class NanoappMessageEvent extends com.android.server.location.contexthub.ContextHubEventLogger.NanoappEventBase {
        public final android.hardware.location.NanoAppMessage message;

        public NanoappMessageEvent(long j, int i, android.hardware.location.NanoAppMessage nanoAppMessage, boolean z) {
            super(j, i, 0L, z);
            this.message = nanoAppMessage;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(com.android.server.location.contexthub.ContextHubServiceUtil.formatDateFromTimestamp(this.timeStampInMs));
            sb.append(": NanoappMessageEvent[hubId = ");
            sb.append(this.contextHubId);
            sb.append(", ");
            sb.append(this.message.toString());
            sb.append(", success = ");
            sb.append(this.success ? "true" : "false");
            sb.append(']');
            return sb.toString();
        }
    }

    public static class ContextHubRestartEvent extends com.android.server.location.contexthub.ContextHubEventLogger.ContextHubEventBase {
        public ContextHubRestartEvent(long j, int i) {
            super(j, i);
        }

        public java.lang.String toString() {
            return com.android.server.location.contexthub.ContextHubServiceUtil.formatDateFromTimestamp(this.timeStampInMs) + ": ContextHubRestartEvent[hubId = " + this.contextHubId + ']';
        }
    }

    private ContextHubEventLogger() {
    }

    public static synchronized com.android.server.location.contexthub.ContextHubEventLogger getInstance() {
        com.android.server.location.contexthub.ContextHubEventLogger contextHubEventLogger;
        synchronized (com.android.server.location.contexthub.ContextHubEventLogger.class) {
            try {
                if (sInstance == null) {
                    sInstance = new com.android.server.location.contexthub.ContextHubEventLogger();
                }
                contextHubEventLogger = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return contextHubEventLogger;
    }

    public synchronized void clear() {
        java.util.Collection[] collectionArr = {this.mNanoappLoadEventQueue, this.mNanoappUnloadEventQueue, this.mMessageFromNanoappQueue, this.mMessageToNanoappQueue, this.mContextHubRestartEventQueue};
        for (int i = 0; i < 5; i++) {
            collectionArr[i].clear();
        }
    }

    public synchronized void logNanoappLoad(int i, long j, int i2, long j2, boolean z) {
        com.android.server.location.contexthub.ContextHubEventLogger.NanoappLoadEvent nanoappLoadEvent = new com.android.server.location.contexthub.ContextHubEventLogger.NanoappLoadEvent(java.lang.System.currentTimeMillis(), i, j, i2, j2, z);
        if (!this.mNanoappLoadEventQueue.add(nanoappLoadEvent)) {
            android.util.Log.e(TAG, "Unable to add nanoapp load event to queue: " + nanoappLoadEvent);
        }
    }

    public synchronized void logNanoappUnload(int i, long j, boolean z) {
        com.android.server.location.contexthub.ContextHubEventLogger.NanoappUnloadEvent nanoappUnloadEvent = new com.android.server.location.contexthub.ContextHubEventLogger.NanoappUnloadEvent(java.lang.System.currentTimeMillis(), i, j, z);
        if (!this.mNanoappUnloadEventQueue.add(nanoappUnloadEvent)) {
            android.util.Log.e(TAG, "Unable to add nanoapp unload event to queue: " + nanoappUnloadEvent);
        }
    }

    public synchronized void logMessageFromNanoapp(int i, android.hardware.location.NanoAppMessage nanoAppMessage, boolean z) {
        if (nanoAppMessage == null) {
            return;
        }
        com.android.server.location.contexthub.ContextHubEventLogger.NanoappMessageEvent nanoappMessageEvent = new com.android.server.location.contexthub.ContextHubEventLogger.NanoappMessageEvent(java.lang.System.currentTimeMillis(), i, nanoAppMessage, z);
        if (!this.mMessageFromNanoappQueue.add(nanoappMessageEvent)) {
            android.util.Log.e(TAG, "Unable to add message from nanoapp event to queue: " + nanoappMessageEvent);
        }
    }

    public synchronized void logMessageToNanoapp(int i, android.hardware.location.NanoAppMessage nanoAppMessage, boolean z) {
        if (nanoAppMessage == null) {
            return;
        }
        com.android.server.location.contexthub.ContextHubEventLogger.NanoappMessageEvent nanoappMessageEvent = new com.android.server.location.contexthub.ContextHubEventLogger.NanoappMessageEvent(java.lang.System.currentTimeMillis(), i, nanoAppMessage, z);
        if (!this.mMessageToNanoappQueue.add(nanoappMessageEvent)) {
            android.util.Log.e(TAG, "Unable to add message to nanoapp event to queue: " + nanoappMessageEvent);
        }
    }

    public synchronized void logContextHubRestart(int i) {
        com.android.server.location.contexthub.ContextHubEventLogger.ContextHubRestartEvent contextHubRestartEvent = new com.android.server.location.contexthub.ContextHubEventLogger.ContextHubRestartEvent(java.lang.System.currentTimeMillis(), i);
        if (!this.mContextHubRestartEventQueue.add(contextHubRestartEvent)) {
            android.util.Log.e(TAG, "Unable to add Context Hub restart event to queue: " + contextHubRestartEvent);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Nanoapp Loads:");
        sb.append(java.lang.System.lineSeparator());
        java.util.Iterator<com.android.server.location.contexthub.ContextHubEventLogger.NanoappLoadEvent> it = this.mNanoappLoadEventQueue.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(java.lang.System.lineSeparator());
        }
        sb.append(java.lang.System.lineSeparator());
        sb.append("Nanoapp Unloads:");
        sb.append(java.lang.System.lineSeparator());
        java.util.Iterator<com.android.server.location.contexthub.ContextHubEventLogger.NanoappUnloadEvent> it2 = this.mNanoappUnloadEventQueue.iterator();
        while (it2.hasNext()) {
            sb.append(it2.next());
            sb.append(java.lang.System.lineSeparator());
        }
        sb.append(java.lang.System.lineSeparator());
        sb.append("Messages from Nanoapps:");
        sb.append(java.lang.System.lineSeparator());
        java.util.Iterator<com.android.server.location.contexthub.ContextHubEventLogger.NanoappMessageEvent> it3 = this.mMessageFromNanoappQueue.iterator();
        while (it3.hasNext()) {
            sb.append(it3.next());
            sb.append(java.lang.System.lineSeparator());
        }
        sb.append(java.lang.System.lineSeparator());
        sb.append("Messages to Nanoapps:");
        sb.append(java.lang.System.lineSeparator());
        java.util.Iterator<com.android.server.location.contexthub.ContextHubEventLogger.NanoappMessageEvent> it4 = this.mMessageToNanoappQueue.iterator();
        while (it4.hasNext()) {
            sb.append(it4.next());
            sb.append(java.lang.System.lineSeparator());
        }
        sb.append(java.lang.System.lineSeparator());
        sb.append("Context Hub Restarts:");
        sb.append(java.lang.System.lineSeparator());
        java.util.Iterator<com.android.server.location.contexthub.ContextHubEventLogger.ContextHubRestartEvent> it5 = this.mContextHubRestartEventQueue.iterator();
        while (it5.hasNext()) {
            sb.append(it5.next());
            sb.append(java.lang.System.lineSeparator());
        }
        return sb.toString();
    }
}
