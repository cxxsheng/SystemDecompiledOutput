package com.android.internal.os;

/* loaded from: classes4.dex */
public class LooperStats implements android.os.Looper.Observer {
    public static final java.lang.String DEBUG_ENTRY_PREFIX = "__DEBUG_";
    public static final boolean DEFAULT_IGNORE_BATTERY_STATUS = false;
    private static final boolean DISABLED_SCREEN_STATE_TRACKING_VALUE = false;
    private static final int SESSION_POOL_SIZE = 50;
    private com.android.internal.os.CachedDeviceState.TimeInStateStopwatch mBatteryStopwatch;
    private com.android.internal.os.CachedDeviceState.Readonly mDeviceState;
    private final int mEntriesSizeCap;
    private int mSamplingInterval;
    private final android.util.SparseArray<com.android.internal.os.LooperStats.Entry> mEntries = new android.util.SparseArray<>(512);
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.internal.os.LooperStats.Entry mOverflowEntry = new com.android.internal.os.LooperStats.Entry("OVERFLOW");
    private final com.android.internal.os.LooperStats.Entry mHashCollisionEntry = new com.android.internal.os.LooperStats.Entry("HASH_COLLISION");
    private final java.util.concurrent.ConcurrentLinkedQueue<com.android.internal.os.LooperStats.DispatchSession> mSessionPool = new java.util.concurrent.ConcurrentLinkedQueue<>();
    private long mStartCurrentTime = java.lang.System.currentTimeMillis();
    private long mStartElapsedTime = android.os.SystemClock.elapsedRealtime();
    private boolean mAddDebugEntries = false;
    private boolean mTrackScreenInteractive = false;
    private boolean mIgnoreBatteryStatus = false;

    public LooperStats(int i, int i2) {
        this.mSamplingInterval = i;
        this.mEntriesSizeCap = i2;
    }

    public void setDeviceState(com.android.internal.os.CachedDeviceState.Readonly readonly) {
        if (this.mBatteryStopwatch != null) {
            this.mBatteryStopwatch.close();
        }
        this.mDeviceState = readonly;
        this.mBatteryStopwatch = readonly.createTimeOnBatteryStopwatch();
    }

    public void setAddDebugEntries(boolean z) {
        this.mAddDebugEntries = z;
    }

    @Override // android.os.Looper.Observer
    public java.lang.Object messageDispatchStarting() {
        if (deviceStateAllowsCollection() && shouldCollectDetailedData()) {
            com.android.internal.os.LooperStats.DispatchSession poll = this.mSessionPool.poll();
            if (poll == null) {
                poll = new com.android.internal.os.LooperStats.DispatchSession();
            }
            poll.startTimeMicro = getElapsedRealtimeMicro();
            poll.cpuStartMicro = getThreadTimeMicro();
            poll.systemUptimeMillis = getSystemUptimeMillis();
            return poll;
        }
        return com.android.internal.os.LooperStats.DispatchSession.NOT_SAMPLED;
    }

    @Override // android.os.Looper.Observer
    public void messageDispatched(java.lang.Object obj, android.os.Message message) {
        if (!deviceStateAllowsCollection()) {
            return;
        }
        com.android.internal.os.LooperStats.DispatchSession dispatchSession = (com.android.internal.os.LooperStats.DispatchSession) obj;
        com.android.internal.os.LooperStats.Entry findEntry = findEntry(message, dispatchSession != com.android.internal.os.LooperStats.DispatchSession.NOT_SAMPLED);
        if (findEntry != null) {
            synchronized (findEntry) {
                findEntry.messageCount++;
                if (dispatchSession != com.android.internal.os.LooperStats.DispatchSession.NOT_SAMPLED) {
                    findEntry.recordedMessageCount++;
                    long elapsedRealtimeMicro = getElapsedRealtimeMicro() - dispatchSession.startTimeMicro;
                    long threadTimeMicro = getThreadTimeMicro() - dispatchSession.cpuStartMicro;
                    findEntry.totalLatencyMicro += elapsedRealtimeMicro;
                    findEntry.maxLatencyMicro = java.lang.Math.max(findEntry.maxLatencyMicro, elapsedRealtimeMicro);
                    findEntry.cpuUsageMicro += threadTimeMicro;
                    findEntry.maxCpuUsageMicro = java.lang.Math.max(findEntry.maxCpuUsageMicro, threadTimeMicro);
                    if (message.getWhen() > 0) {
                        long max = java.lang.Math.max(0L, dispatchSession.systemUptimeMillis - message.getWhen());
                        findEntry.delayMillis += max;
                        findEntry.maxDelayMillis = java.lang.Math.max(findEntry.maxDelayMillis, max);
                        findEntry.recordedDelayMessageCount++;
                    }
                }
            }
        }
        recycleSession(dispatchSession);
    }

    @Override // android.os.Looper.Observer
    public void dispatchingThrewException(java.lang.Object obj, android.os.Message message, java.lang.Exception exc) {
        if (!deviceStateAllowsCollection()) {
            return;
        }
        com.android.internal.os.LooperStats.DispatchSession dispatchSession = (com.android.internal.os.LooperStats.DispatchSession) obj;
        com.android.internal.os.LooperStats.Entry findEntry = findEntry(message, dispatchSession != com.android.internal.os.LooperStats.DispatchSession.NOT_SAMPLED);
        if (findEntry != null) {
            synchronized (findEntry) {
                findEntry.exceptionCount++;
            }
        }
        recycleSession(dispatchSession);
    }

    private boolean deviceStateAllowsCollection() {
        if (this.mIgnoreBatteryStatus) {
            return true;
        }
        return (this.mDeviceState == null || this.mDeviceState.isCharging()) ? false : true;
    }

    public java.util.List<com.android.internal.os.LooperStats.ExportedEntry> getEntries() {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            int size = this.mEntries.size();
            arrayList = new java.util.ArrayList(size);
            for (int i = 0; i < size; i++) {
                com.android.internal.os.LooperStats.Entry valueAt = this.mEntries.valueAt(i);
                synchronized (valueAt) {
                    arrayList.add(new com.android.internal.os.LooperStats.ExportedEntry(valueAt));
                }
            }
        }
        maybeAddSpecialEntry(arrayList, this.mOverflowEntry);
        maybeAddSpecialEntry(arrayList, this.mHashCollisionEntry);
        if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            arrayList.add(createDebugEntry("start_time_millis", this.mStartElapsedTime));
            arrayList.add(createDebugEntry("end_time_millis", android.os.SystemClock.elapsedRealtime()));
            arrayList.add(createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            arrayList.add(createDebugEntry(com.android.internal.os.BinderCallsStats.SettingsObserver.SETTINGS_SAMPLING_INTERVAL_KEY, this.mSamplingInterval));
        }
        return arrayList;
    }

    private com.android.internal.os.LooperStats.ExportedEntry createDebugEntry(java.lang.String str, long j) {
        com.android.internal.os.LooperStats.Entry entry = new com.android.internal.os.LooperStats.Entry(DEBUG_ENTRY_PREFIX + str);
        entry.messageCount = 1L;
        entry.recordedMessageCount = 1L;
        entry.totalLatencyMicro = j;
        return new com.android.internal.os.LooperStats.ExportedEntry(entry);
    }

    public long getStartTimeMillis() {
        return this.mStartCurrentTime;
    }

    public long getStartElapsedTimeMillis() {
        return this.mStartElapsedTime;
    }

    public long getBatteryTimeMillis() {
        if (this.mBatteryStopwatch != null) {
            return this.mBatteryStopwatch.getMillis();
        }
        return 0L;
    }

    private void maybeAddSpecialEntry(java.util.List<com.android.internal.os.LooperStats.ExportedEntry> list, com.android.internal.os.LooperStats.Entry entry) {
        synchronized (entry) {
            if (entry.messageCount > 0 || entry.exceptionCount > 0) {
                list.add(new com.android.internal.os.LooperStats.ExportedEntry(entry));
            }
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mEntries.clear();
        }
        synchronized (this.mHashCollisionEntry) {
            this.mHashCollisionEntry.reset();
        }
        synchronized (this.mOverflowEntry) {
            this.mOverflowEntry.reset();
        }
        this.mStartCurrentTime = java.lang.System.currentTimeMillis();
        this.mStartElapsedTime = android.os.SystemClock.elapsedRealtime();
        if (this.mBatteryStopwatch != null) {
            this.mBatteryStopwatch.reset();
        }
    }

    public void setSamplingInterval(int i) {
        this.mSamplingInterval = i;
    }

    public void setTrackScreenInteractive(boolean z) {
        this.mTrackScreenInteractive = z;
    }

    public void setIgnoreBatteryStatus(boolean z) {
        this.mIgnoreBatteryStatus = z;
    }

    private com.android.internal.os.LooperStats.Entry findEntry(android.os.Message message, boolean z) {
        boolean z2;
        if (this.mTrackScreenInteractive) {
            z2 = this.mDeviceState.isScreenInteractive();
        } else {
            z2 = false;
        }
        int idFor = com.android.internal.os.LooperStats.Entry.idFor(message, z2);
        synchronized (this.mLock) {
            com.android.internal.os.LooperStats.Entry entry = this.mEntries.get(idFor);
            if (entry == null) {
                if (!z) {
                    return null;
                }
                if (this.mEntries.size() >= this.mEntriesSizeCap) {
                    return this.mOverflowEntry;
                }
                entry = new com.android.internal.os.LooperStats.Entry(message, z2);
                this.mEntries.put(idFor, entry);
            }
            if (entry.workSourceUid != message.workSourceUid || entry.handler.getClass() != message.getTarget().getClass() || entry.handler.getLooper().getThread() != message.getTarget().getLooper().getThread() || entry.isInteractive != z2) {
                return this.mHashCollisionEntry;
            }
            return entry;
        }
    }

    private void recycleSession(com.android.internal.os.LooperStats.DispatchSession dispatchSession) {
        if (dispatchSession != com.android.internal.os.LooperStats.DispatchSession.NOT_SAMPLED && this.mSessionPool.size() < 50) {
            this.mSessionPool.add(dispatchSession);
        }
    }

    protected long getThreadTimeMicro() {
        return android.os.SystemClock.currentThreadTimeMicro();
    }

    protected long getElapsedRealtimeMicro() {
        return android.os.SystemClock.elapsedRealtimeNanos() / 1000;
    }

    protected long getSystemUptimeMillis() {
        return android.os.SystemClock.uptimeMillis();
    }

    protected boolean shouldCollectDetailedData() {
        return java.util.concurrent.ThreadLocalRandom.current().nextInt(this.mSamplingInterval) == 0;
    }

    private static class DispatchSession {
        static final com.android.internal.os.LooperStats.DispatchSession NOT_SAMPLED = new com.android.internal.os.LooperStats.DispatchSession();
        public long cpuStartMicro;
        public long startTimeMicro;
        public long systemUptimeMillis;

        private DispatchSession() {
        }
    }

    private static class Entry {
        public long cpuUsageMicro;
        public long delayMillis;
        public long exceptionCount;
        public final android.os.Handler handler;
        public final boolean isInteractive;
        public long maxCpuUsageMicro;
        public long maxDelayMillis;
        public long maxLatencyMicro;
        public long messageCount;
        public final java.lang.String messageName;
        public long recordedDelayMessageCount;
        public long recordedMessageCount;
        public long totalLatencyMicro;
        public final int workSourceUid;

        Entry(android.os.Message message, boolean z) {
            this.workSourceUid = message.workSourceUid;
            this.handler = message.getTarget();
            this.messageName = this.handler.getMessageName(message);
            this.isInteractive = z;
        }

        Entry(java.lang.String str) {
            this.workSourceUid = -1;
            this.messageName = str;
            this.handler = null;
            this.isInteractive = false;
        }

        void reset() {
            this.messageCount = 0L;
            this.recordedMessageCount = 0L;
            this.exceptionCount = 0L;
            this.totalLatencyMicro = 0L;
            this.maxLatencyMicro = 0L;
            this.cpuUsageMicro = 0L;
            this.maxCpuUsageMicro = 0L;
            this.delayMillis = 0L;
            this.maxDelayMillis = 0L;
            this.recordedDelayMessageCount = 0L;
        }

        static int idFor(android.os.Message message, boolean z) {
            int hashCode = ((((((217 + message.workSourceUid) * 31) + message.getTarget().getLooper().getThread().hashCode()) * 31) + message.getTarget().getClass().hashCode()) * 31) + (z ? com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : com.android.internal.logging.nano.MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
            if (message.getCallback() != null) {
                return (hashCode * 31) + message.getCallback().getClass().hashCode();
            }
            return (hashCode * 31) + message.what;
        }
    }

    public static class ExportedEntry {
        public final long cpuUsageMicros;
        public final long delayMillis;
        public final long exceptionCount;
        public final java.lang.String handlerClassName;
        public final boolean isInteractive;
        public final long maxCpuUsageMicros;
        public final long maxDelayMillis;
        public final long maxLatencyMicros;
        public final long messageCount;
        public final java.lang.String messageName;
        public final long recordedDelayMessageCount;
        public final long recordedMessageCount;
        public final java.lang.String threadName;
        public final long totalLatencyMicros;
        public final int workSourceUid;

        ExportedEntry(com.android.internal.os.LooperStats.Entry entry) {
            this.workSourceUid = entry.workSourceUid;
            if (entry.handler != null) {
                this.handlerClassName = entry.handler.getClass().getName();
                this.threadName = entry.handler.getLooper().getThread().getName();
            } else {
                this.handlerClassName = "";
                this.threadName = "";
            }
            this.isInteractive = entry.isInteractive;
            this.messageName = entry.messageName;
            this.messageCount = entry.messageCount;
            this.recordedMessageCount = entry.recordedMessageCount;
            this.exceptionCount = entry.exceptionCount;
            this.totalLatencyMicros = entry.totalLatencyMicro;
            this.maxLatencyMicros = entry.maxLatencyMicro;
            this.cpuUsageMicros = entry.cpuUsageMicro;
            this.maxCpuUsageMicros = entry.maxCpuUsageMicro;
            this.delayMillis = entry.delayMillis;
            this.maxDelayMillis = entry.maxDelayMillis;
            this.recordedDelayMessageCount = entry.recordedDelayMessageCount;
        }
    }
}
