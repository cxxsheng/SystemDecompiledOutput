package com.android.internal.os;

/* loaded from: classes4.dex */
public class BatteryStatsHistory {
    static final int BATTERY_LEVEL_DETAILS_FLAG = 1;
    private static final boolean DEBUG = false;
    static final int DELTA_BATTERY_CHARGE_FLAG = 16777216;
    static final int DELTA_BATTERY_LEVEL_FLAG = 524288;
    static final int DELTA_EVENT_FLAG = 8388608;
    static final int DELTA_STATE2_FLAG = 2097152;
    static final int DELTA_STATE_FLAG = 1048576;
    static final int DELTA_STATE_MASK = -33554432;
    static final int DELTA_TIME_ABS = 524285;
    static final int DELTA_TIME_INT = 524286;
    static final int DELTA_TIME_LONG = 524287;
    static final int DELTA_TIME_MASK = 524287;
    static final int DELTA_WAKELOCK_FLAG = 4194304;
    static final int EXTENSION_POWER_STATS_DESCRIPTOR_FLAG = 1;
    static final int EXTENSION_POWER_STATS_FLAG = 2;
    static final int EXTENSION_PROCESS_STATE_CHANGE_FLAG = 4;
    private static final java.lang.String FILE_SUFFIX = ".bh";
    private static final java.lang.String HISTORY_DIR = "battery-history";
    static final int HISTORY_TAG_INDEX_LIMIT = 32766;
    private static final int MAX_HISTORY_TAG_STRING_LENGTH = 1024;
    private static final int MIN_FREE_SPACE = 104857600;
    static final int STATE1_TRACE_MASK = 1073741823;
    static final int STATE2_TRACE_MASK = -1;
    static final int STATE_BATTERY_HEALTH_MASK = 7;
    static final int STATE_BATTERY_HEALTH_SHIFT = 26;
    static final int STATE_BATTERY_MASK = -16777216;
    static final int STATE_BATTERY_PLUG_MASK = 3;
    static final int STATE_BATTERY_PLUG_SHIFT = 24;
    static final int STATE_BATTERY_STATUS_MASK = 7;
    static final int STATE_BATTERY_STATUS_SHIFT = 29;
    private static final java.lang.String TAG = "BatteryStatsHistory";
    static final int TAG_FIRST_OCCURRENCE_FLAG = 32768;
    private static final int VERSION = 210;
    private android.util.AtomicFile mActiveFile;
    private final com.android.internal.os.Clock mClock;
    private com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile mCurrentFile;
    private android.os.Parcel mCurrentParcel;
    private int mCurrentParcelEnd;
    private final com.android.internal.os.BatteryStatsHistory.EventLogger mEventLogger;
    private boolean mHaveBatteryLevel;
    private final android.os.BatteryStats.HistoryItem mHistoryAddTmp;
    private final android.os.Parcel mHistoryBuffer;
    private int mHistoryBufferLastPos;
    private long mHistoryBufferStartTime;
    private final android.os.BatteryStats.HistoryItem mHistoryCur;
    private final com.android.internal.os.BatteryStatsHistory.BatteryHistoryDirectory mHistoryDir;
    private final android.os.BatteryStats.HistoryItem mHistoryLastLastWritten;
    private final android.os.BatteryStats.HistoryItem mHistoryLastWritten;
    private java.util.List<android.os.Parcel> mHistoryParcels;
    private final java.util.HashMap<android.os.BatteryStats.HistoryTag, java.lang.Integer> mHistoryTagPool;
    private android.util.SparseArray<android.os.BatteryStats.HistoryTag> mHistoryTags;
    private byte mLastHistoryStepLevel;
    private int mMaxHistoryBufferSize;
    private final com.android.internal.os.MonotonicClock mMonotonicClock;
    private boolean mMutable;
    private int mNextHistoryTagIdx;
    private int mNumHistoryTagChars;
    private int mParcelIndex;
    private boolean mRecordingHistory;
    private final com.android.internal.os.BatteryStatsHistory.HistoryStepDetailsCalculator mStepDetailsCalculator;
    private final java.io.File mSystemDir;
    private int mTraceLastState;
    private int mTraceLastState2;
    private com.android.internal.os.BatteryStatsHistory.TraceDelegate mTracer;
    private long mTrackRunningHistoryElapsedRealtimeMs;
    private long mTrackRunningHistoryUptimeMs;
    private final com.android.internal.os.BatteryStatsHistory mWritableHistory;
    private final java.util.concurrent.locks.ReentrantLock mWriteLock;
    private final android.util.ArraySet<com.android.internal.os.PowerStats.Descriptor> mWrittenPowerStatsDescriptors;

    public interface HistoryStepDetailsCalculator {
        void clear();

        android.os.BatteryStats.HistoryStepDetails getHistoryStepDetails();
    }

    private static class BatteryHistoryFile implements java.lang.Comparable<com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile> {
        public final android.util.AtomicFile atomicFile;
        public final long monotonicTimeMs;

        private BatteryHistoryFile(java.io.File file, long j) {
            this.monotonicTimeMs = j;
            this.atomicFile = new android.util.AtomicFile(new java.io.File(file, j + com.android.internal.os.BatteryStatsHistory.FILE_SUFFIX));
        }

        @Override // java.lang.Comparable
        public int compareTo(com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile batteryHistoryFile) {
            return java.lang.Long.compare(this.monotonicTimeMs, batteryHistoryFile.monotonicTimeMs);
        }

        public boolean equals(java.lang.Object obj) {
            return this.monotonicTimeMs == ((com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile) obj).monotonicTimeMs;
        }

        public int hashCode() {
            return java.lang.Long.hashCode(this.monotonicTimeMs);
        }

        public java.lang.String toString() {
            return this.atomicFile.getBaseFile().toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BatteryHistoryDirectory {
        private boolean mCleanupNeeded;
        private final java.io.File mDirectory;
        private final java.util.List<com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile> mHistoryFiles = new java.util.ArrayList();
        private final java.util.concurrent.locks.ReentrantLock mLock = new java.util.concurrent.locks.ReentrantLock();
        private int mMaxHistoryFiles;
        private final com.android.internal.os.MonotonicClock mMonotonicClock;

        BatteryHistoryDirectory(java.io.File file, com.android.internal.os.MonotonicClock monotonicClock, int i) {
            this.mDirectory = file;
            this.mMonotonicClock = monotonicClock;
            this.mMaxHistoryFiles = i;
            if (this.mMaxHistoryFiles == 0) {
                android.util.Slog.wtf(com.android.internal.os.BatteryStatsHistory.TAG, "mMaxHistoryFiles should not be zero when writing history");
            }
        }

        void setMaxHistoryFiles(int i) {
            this.mMaxHistoryFiles = i;
            cleanup();
        }

        void lock() {
            this.mLock.lock();
        }

        void unlock() {
            this.mLock.unlock();
            if (this.mCleanupNeeded) {
                cleanup();
            }
        }

        boolean isLocked() {
            return this.mLock.isLocked();
        }

        void load() {
            this.mDirectory.mkdirs();
            if (!this.mDirectory.exists()) {
                android.util.Slog.wtf(com.android.internal.os.BatteryStatsHistory.TAG, "HistoryDir does not exist:" + this.mDirectory.getPath());
            }
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            final android.util.ArraySet arraySet = new android.util.ArraySet();
            this.mDirectory.listFiles(new java.io.FilenameFilter() { // from class: com.android.internal.os.BatteryStatsHistory$BatteryHistoryDirectory$$ExternalSyntheticLambda0
                @Override // java.io.FilenameFilter
                public final boolean accept(java.io.File file, java.lang.String str) {
                    boolean lambda$load$0;
                    lambda$load$0 = com.android.internal.os.BatteryStatsHistory.BatteryHistoryDirectory.this.lambda$load$0(arrayList, arraySet, file, str);
                    return lambda$load$0;
                }
            });
            if (!arraySet.isEmpty()) {
                this.mHistoryFiles.addAll(arraySet);
                java.util.Collections.sort(this.mHistoryFiles);
            }
            if (!arrayList.isEmpty()) {
                com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.internal.os.BatteryStatsHistory$BatteryHistoryDirectory$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.os.BatteryStatsHistory.BatteryHistoryDirectory.this.lambda$load$1(arrayList);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$load$0(java.util.List list, java.util.Set set, java.io.File file, java.lang.String str) {
            int lastIndexOf = str.lastIndexOf(com.android.internal.os.BatteryStatsHistory.FILE_SUFFIX);
            if (lastIndexOf <= 0) {
                list.add(new java.io.File(file, str));
                return false;
            }
            try {
                set.add(new com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile(this.mDirectory, java.lang.Long.parseLong(str.substring(0, lastIndexOf))));
                return true;
            } catch (java.lang.NumberFormatException e) {
                list.add(new java.io.File(file, str));
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$load$1(java.util.List list) {
            lock();
            try {
                java.util.Iterator it = list.iterator();
                while (it.hasNext()) {
                    ((java.io.File) it.next()).delete();
                }
            } finally {
                unlock();
            }
        }

        java.util.List<java.lang.String> getFileNames() {
            lock();
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.Iterator<com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile> it = this.mHistoryFiles.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().atomicFile.getBaseFile().getName());
                }
                return arrayList;
            } finally {
                unlock();
            }
        }

        com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile getFirstFile() {
            lock();
            try {
                if (!this.mHistoryFiles.isEmpty()) {
                    return this.mHistoryFiles.get(0);
                }
                unlock();
                return null;
            } finally {
                unlock();
            }
        }

        com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile getLastFile() {
            lock();
            try {
                if (!this.mHistoryFiles.isEmpty()) {
                    return this.mHistoryFiles.get(this.mHistoryFiles.size() - 1);
                }
                unlock();
                return null;
            } finally {
                unlock();
            }
        }

        com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile getNextFile(com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile batteryHistoryFile, long j, long j2) {
            if (!this.mLock.isHeldByCurrentThread()) {
                throw new java.lang.IllegalStateException("Iterating battery history without a lock");
            }
            int size = this.mHistoryFiles.size() - 2;
            int i = 0;
            int i2 = size;
            int i3 = 0;
            while (true) {
                if (size < 0) {
                    break;
                }
                com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile batteryHistoryFile2 = this.mHistoryFiles.get(size);
                if (batteryHistoryFile != null && batteryHistoryFile2.monotonicTimeMs == batteryHistoryFile.monotonicTimeMs) {
                    i3 = size + 1;
                }
                if (batteryHistoryFile2.monotonicTimeMs > j2) {
                    i2 = size - 1;
                }
                if (batteryHistoryFile2.monotonicTimeMs > j) {
                    size--;
                } else {
                    i = size;
                    break;
                }
            }
            if (i3 >= i) {
                i = i3;
            }
            if (i <= i2) {
                return this.mHistoryFiles.get(i);
            }
            return null;
        }

        com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile makeBatteryHistoryFile() {
            com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile batteryHistoryFile = new com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile(this.mDirectory, this.mMonotonicClock.monotonicTime());
            lock();
            try {
                this.mHistoryFiles.add(batteryHistoryFile);
                return batteryHistoryFile;
            } finally {
                unlock();
            }
        }

        void writeToParcel(android.os.Parcel parcel, boolean z) {
            lock();
            try {
                android.os.SystemClock.uptimeMillis();
                parcel.writeInt(this.mHistoryFiles.size() - 1);
                for (int i = 0; i < this.mHistoryFiles.size() - 1; i++) {
                    android.util.AtomicFile atomicFile = this.mHistoryFiles.get(i).atomicFile;
                    byte[] bArr = new byte[0];
                    try {
                        bArr = atomicFile.readFully();
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(com.android.internal.os.BatteryStatsHistory.TAG, "Error reading file " + atomicFile.getBaseFile().getPath(), e);
                    }
                    if (z) {
                        parcel.writeBlob(bArr);
                    } else {
                        parcel.writeByteArray(bArr);
                    }
                }
            } finally {
                unlock();
            }
        }

        int getFileCount() {
            lock();
            try {
                return this.mHistoryFiles.size();
            } finally {
                unlock();
            }
        }

        int getSize() {
            lock();
            int i = 0;
            for (int i2 = 0; i2 < this.mHistoryFiles.size() - 1; i2++) {
                try {
                    i += (int) this.mHistoryFiles.get(i2).atomicFile.getBaseFile().length();
                } finally {
                    unlock();
                }
            }
            return i;
        }

        void reset() {
            lock();
            try {
                java.util.Iterator<com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile> it = this.mHistoryFiles.iterator();
                while (it.hasNext()) {
                    it.next().atomicFile.delete();
                }
                this.mHistoryFiles.clear();
            } finally {
                unlock();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cleanup() {
            if (this.mDirectory == null) {
                return;
            }
            if (isLocked()) {
                this.mCleanupNeeded = true;
                return;
            }
            this.mCleanupNeeded = false;
            lock();
            try {
                if (!com.android.internal.os.BatteryStatsHistory.hasFreeDiskSpace(this.mDirectory)) {
                    this.mHistoryFiles.remove(0).atomicFile.delete();
                }
                while (this.mHistoryFiles.size() > this.mMaxHistoryFiles) {
                    this.mHistoryFiles.get(0).atomicFile.delete();
                    this.mHistoryFiles.remove(0);
                }
            } finally {
                unlock();
            }
        }
    }

    public static class TraceDelegate {
        private final boolean mShouldSetProperty;

        public TraceDelegate() {
            this.mShouldSetProperty = android.os.Build.IS_USERDEBUG && android.os.Process.myUid() == 1000;
        }

        public boolean tracingEnabled() {
            return android.os.Trace.isTagEnabled(131072L) || this.mShouldSetProperty;
        }

        public void traceCounter(java.lang.String str, int i) {
            android.os.Trace.traceCounter(131072L, str, i);
            if (this.mShouldSetProperty) {
                try {
                    android.os.SystemProperties.set("debug.tracing." + str, java.lang.Integer.toString(i));
                } catch (java.lang.RuntimeException e) {
                    android.util.Slog.e(com.android.internal.os.BatteryStatsHistory.TAG, "Failed to set debug.tracing." + str, e);
                }
            }
        }

        public void traceInstantEvent(java.lang.String str, java.lang.String str2) {
            android.os.Trace.instantForTrack(131072L, str, str2);
        }
    }

    public static class EventLogger {
        public void writeCommitSysConfigFile(long j) {
            com.android.internal.logging.EventLogTags.writeCommitSysConfigFile("batterystats", android.os.SystemClock.uptimeMillis() - j);
        }
    }

    public BatteryStatsHistory(java.io.File file, int i, int i2, com.android.internal.os.BatteryStatsHistory.HistoryStepDetailsCalculator historyStepDetailsCalculator, com.android.internal.os.Clock clock, com.android.internal.os.MonotonicClock monotonicClock) {
        this(file, i, i2, historyStepDetailsCalculator, clock, monotonicClock, new com.android.internal.os.BatteryStatsHistory.TraceDelegate(), new com.android.internal.os.BatteryStatsHistory.EventLogger());
    }

    public BatteryStatsHistory(java.io.File file, int i, int i2, com.android.internal.os.BatteryStatsHistory.HistoryStepDetailsCalculator historyStepDetailsCalculator, com.android.internal.os.Clock clock, com.android.internal.os.MonotonicClock monotonicClock, com.android.internal.os.BatteryStatsHistory.TraceDelegate traceDelegate, com.android.internal.os.BatteryStatsHistory.EventLogger eventLogger) {
        this(android.os.Parcel.obtain(), file, i, i2, historyStepDetailsCalculator, clock, monotonicClock, traceDelegate, eventLogger);
        initHistoryBuffer();
    }

    public BatteryStatsHistory(android.os.Parcel parcel, java.io.File file, int i, int i2, com.android.internal.os.BatteryStatsHistory.HistoryStepDetailsCalculator historyStepDetailsCalculator, com.android.internal.os.Clock clock, com.android.internal.os.MonotonicClock monotonicClock, com.android.internal.os.BatteryStatsHistory.TraceDelegate traceDelegate, com.android.internal.os.BatteryStatsHistory.EventLogger eventLogger) {
        this(parcel, file, i, i2, historyStepDetailsCalculator, clock, monotonicClock, traceDelegate, eventLogger, null);
    }

    private BatteryStatsHistory(android.os.Parcel parcel, java.io.File file, int i, int i2, com.android.internal.os.BatteryStatsHistory.HistoryStepDetailsCalculator historyStepDetailsCalculator, com.android.internal.os.Clock clock, com.android.internal.os.MonotonicClock monotonicClock, com.android.internal.os.BatteryStatsHistory.TraceDelegate traceDelegate, com.android.internal.os.BatteryStatsHistory.EventLogger eventLogger, com.android.internal.os.BatteryStatsHistory batteryStatsHistory) {
        this.mHistoryParcels = null;
        this.mParcelIndex = 0;
        this.mWriteLock = new java.util.concurrent.locks.ReentrantLock();
        this.mHistoryCur = new android.os.BatteryStats.HistoryItem();
        this.mHistoryTagPool = new java.util.HashMap<>();
        this.mHistoryLastWritten = new android.os.BatteryStats.HistoryItem();
        this.mHistoryLastLastWritten = new android.os.BatteryStats.HistoryItem();
        this.mHistoryAddTmp = new android.os.BatteryStats.HistoryItem();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        this.mHistoryBufferLastPos = -1;
        this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
        this.mTrackRunningHistoryUptimeMs = 0L;
        this.mWrittenPowerStatsDescriptors = new android.util.ArraySet<>();
        this.mLastHistoryStepLevel = (byte) 0;
        this.mMutable = true;
        this.mTraceLastState = 0;
        this.mTraceLastState2 = 0;
        this.mHistoryBuffer = parcel;
        this.mSystemDir = file;
        this.mMaxHistoryBufferSize = i2;
        this.mStepDetailsCalculator = historyStepDetailsCalculator;
        this.mTracer = traceDelegate;
        this.mClock = clock;
        this.mMonotonicClock = monotonicClock;
        this.mEventLogger = eventLogger;
        this.mWritableHistory = batteryStatsHistory;
        if (this.mWritableHistory != null) {
            this.mMutable = false;
        }
        if (batteryStatsHistory != null) {
            this.mHistoryDir = batteryStatsHistory.mHistoryDir;
            return;
        }
        this.mHistoryDir = new com.android.internal.os.BatteryStatsHistory.BatteryHistoryDirectory(new java.io.File(file, HISTORY_DIR), monotonicClock, i);
        this.mHistoryDir.load();
        com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile lastFile = this.mHistoryDir.getLastFile();
        setActiveFile(lastFile == null ? this.mHistoryDir.makeBatteryHistoryFile() : lastFile);
    }

    public BatteryStatsHistory(int i, com.android.internal.os.BatteryStatsHistory.HistoryStepDetailsCalculator historyStepDetailsCalculator, com.android.internal.os.Clock clock, com.android.internal.os.MonotonicClock monotonicClock) {
        this(i, historyStepDetailsCalculator, clock, monotonicClock, new com.android.internal.os.BatteryStatsHistory.TraceDelegate(), new com.android.internal.os.BatteryStatsHistory.EventLogger());
    }

    public BatteryStatsHistory(int i, com.android.internal.os.BatteryStatsHistory.HistoryStepDetailsCalculator historyStepDetailsCalculator, com.android.internal.os.Clock clock, com.android.internal.os.MonotonicClock monotonicClock, com.android.internal.os.BatteryStatsHistory.TraceDelegate traceDelegate, com.android.internal.os.BatteryStatsHistory.EventLogger eventLogger) {
        this.mHistoryParcels = null;
        this.mParcelIndex = 0;
        this.mWriteLock = new java.util.concurrent.locks.ReentrantLock();
        this.mHistoryCur = new android.os.BatteryStats.HistoryItem();
        this.mHistoryTagPool = new java.util.HashMap<>();
        this.mHistoryLastWritten = new android.os.BatteryStats.HistoryItem();
        this.mHistoryLastLastWritten = new android.os.BatteryStats.HistoryItem();
        this.mHistoryAddTmp = new android.os.BatteryStats.HistoryItem();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        this.mHistoryBufferLastPos = -1;
        this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
        this.mTrackRunningHistoryUptimeMs = 0L;
        this.mWrittenPowerStatsDescriptors = new android.util.ArraySet<>();
        this.mLastHistoryStepLevel = (byte) 0;
        this.mMutable = true;
        this.mTraceLastState = 0;
        this.mTraceLastState2 = 0;
        this.mMaxHistoryBufferSize = i;
        this.mStepDetailsCalculator = historyStepDetailsCalculator;
        this.mTracer = traceDelegate;
        this.mClock = clock;
        this.mMonotonicClock = monotonicClock;
        this.mEventLogger = eventLogger;
        this.mHistoryBuffer = android.os.Parcel.obtain();
        this.mSystemDir = null;
        this.mHistoryDir = null;
        this.mWritableHistory = null;
        initHistoryBuffer();
    }

    private BatteryStatsHistory(android.os.Parcel parcel) {
        this.mHistoryParcels = null;
        this.mParcelIndex = 0;
        this.mWriteLock = new java.util.concurrent.locks.ReentrantLock();
        this.mHistoryCur = new android.os.BatteryStats.HistoryItem();
        this.mHistoryTagPool = new java.util.HashMap<>();
        this.mHistoryLastWritten = new android.os.BatteryStats.HistoryItem();
        this.mHistoryLastLastWritten = new android.os.BatteryStats.HistoryItem();
        this.mHistoryAddTmp = new android.os.BatteryStats.HistoryItem();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        this.mHistoryBufferLastPos = -1;
        this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
        this.mTrackRunningHistoryUptimeMs = 0L;
        this.mWrittenPowerStatsDescriptors = new android.util.ArraySet<>();
        this.mLastHistoryStepLevel = (byte) 0;
        this.mMutable = true;
        this.mTraceLastState = 0;
        this.mTraceLastState2 = 0;
        this.mClock = com.android.internal.os.Clock.SYSTEM_CLOCK;
        this.mTracer = null;
        this.mSystemDir = null;
        this.mHistoryDir = null;
        this.mStepDetailsCalculator = null;
        this.mEventLogger = new com.android.internal.os.BatteryStatsHistory.EventLogger();
        this.mWritableHistory = null;
        this.mMutable = false;
        byte[] readBlob = parcel.readBlob();
        this.mHistoryBuffer = android.os.Parcel.obtain();
        this.mHistoryBuffer.unmarshall(readBlob, 0, readBlob.length);
        this.mMonotonicClock = null;
        readFromParcel(parcel, true);
    }

    private void initHistoryBuffer() {
        this.mTrackRunningHistoryElapsedRealtimeMs = 0L;
        this.mTrackRunningHistoryUptimeMs = 0L;
        this.mWrittenPowerStatsDescriptors.clear();
        this.mHistoryBufferStartTime = this.mMonotonicClock.monotonicTime();
        this.mHistoryBuffer.setDataSize(0);
        this.mHistoryBuffer.setDataPosition(0);
        this.mHistoryBuffer.setDataCapacity(this.mMaxHistoryBufferSize / 2);
        this.mHistoryLastLastWritten.clear();
        this.mHistoryLastWritten.clear();
        this.mHistoryTagPool.clear();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        this.mHistoryBufferLastPos = -1;
        if (this.mStepDetailsCalculator != null) {
            this.mStepDetailsCalculator.clear();
        }
    }

    public void setMaxHistoryFiles(int i) {
        if (this.mHistoryDir != null) {
            this.mHistoryDir.setMaxHistoryFiles(i);
        }
    }

    public void setMaxHistoryBufferSize(int i) {
        this.mMaxHistoryBufferSize = i;
    }

    public com.android.internal.os.BatteryStatsHistory copy() {
        com.android.internal.os.BatteryStatsHistory batteryStatsHistory;
        synchronized (this) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            obtain.appendFrom(this.mHistoryBuffer, 0, this.mHistoryBuffer.dataSize());
            batteryStatsHistory = new com.android.internal.os.BatteryStatsHistory(obtain, this.mSystemDir, 0, 0, null, null, null, null, this.mEventLogger, this);
        }
        return batteryStatsHistory;
    }

    public boolean isReadOnly() {
        return !this.mMutable || this.mActiveFile == null;
    }

    private void setActiveFile(com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile batteryHistoryFile) {
        this.mActiveFile = batteryHistoryFile.atomicFile;
    }

    public void startNextFile(long j) {
        synchronized (this) {
            startNextFileLocked(j);
        }
    }

    private void startNextFileLocked(long j) {
        android.os.SystemClock.uptimeMillis();
        writeHistory();
        setActiveFile(this.mHistoryDir.makeBatteryHistoryFile());
        try {
            this.mActiveFile.getBaseFile().createNewFile();
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Could not create history file: " + this.mActiveFile.getBaseFile());
        }
        this.mHistoryBufferStartTime = this.mMonotonicClock.monotonicTime(j);
        this.mHistoryBuffer.setDataSize(0);
        this.mHistoryBuffer.setDataPosition(0);
        this.mHistoryBuffer.setDataCapacity(this.mMaxHistoryBufferSize / 2);
        this.mHistoryBufferLastPos = -1;
        this.mHistoryLastWritten.clear();
        this.mHistoryLastLastWritten.clear();
        for (java.util.Map.Entry<android.os.BatteryStats.HistoryTag, java.lang.Integer> entry : this.mHistoryTagPool.entrySet()) {
            entry.setValue(java.lang.Integer.valueOf(entry.getValue().intValue() | 32768));
        }
        this.mWrittenPowerStatsDescriptors.clear();
        this.mHistoryDir.cleanup();
    }

    public boolean isResetEnabled() {
        return this.mHistoryDir == null || !this.mHistoryDir.isLocked();
    }

    public void reset() {
        synchronized (this) {
            if (this.mHistoryDir != null) {
                this.mHistoryDir.reset();
                setActiveFile(this.mHistoryDir.makeBatteryHistoryFile());
            }
            initHistoryBuffer();
        }
    }

    public long getStartTime() {
        synchronized (this) {
            com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile firstFile = this.mHistoryDir.getFirstFile();
            if (firstFile != null) {
                return firstFile.monotonicTimeMs;
            }
            return this.mHistoryBufferStartTime;
        }
    }

    public com.android.internal.os.BatteryStatsHistoryIterator iterate(long j, long j2) {
        if (this.mMutable) {
            return copy().iterate(j, j2);
        }
        if (this.mHistoryDir != null) {
            this.mHistoryDir.lock();
        }
        this.mCurrentFile = null;
        this.mCurrentParcel = null;
        this.mCurrentParcelEnd = 0;
        this.mParcelIndex = 0;
        return new com.android.internal.os.BatteryStatsHistoryIterator(this, j, j2);
    }

    void iteratorFinished() {
        this.mHistoryBuffer.setDataPosition(this.mHistoryBuffer.dataSize());
        if (this.mHistoryDir != null) {
            this.mHistoryDir.unlock();
        }
    }

    public android.os.Parcel getNextParcel(long j, long j2) {
        checkImmutable();
        if (this.mCurrentParcel != null) {
            if (this.mCurrentParcel.dataPosition() < this.mCurrentParcelEnd) {
                return this.mCurrentParcel;
            }
            if (this.mHistoryBuffer == this.mCurrentParcel) {
                return null;
            }
            if (this.mHistoryParcels == null || !this.mHistoryParcels.contains(this.mCurrentParcel)) {
                this.mCurrentParcel.recycle();
            }
        }
        if (this.mHistoryDir != null) {
            com.android.internal.os.BatteryStatsHistory.BatteryHistoryFile nextFile = this.mHistoryDir.getNextFile(this.mCurrentFile, j, j2);
            while (nextFile != null) {
                this.mCurrentParcel = null;
                this.mCurrentParcelEnd = 0;
                android.os.Parcel obtain = android.os.Parcel.obtain();
                if (readFileToParcel(obtain, nextFile.atomicFile)) {
                    int readInt = obtain.readInt();
                    int dataPosition = obtain.dataPosition();
                    this.mCurrentParcelEnd = readInt + dataPosition;
                    this.mCurrentParcel = obtain;
                    if (dataPosition < this.mCurrentParcelEnd) {
                        this.mCurrentFile = nextFile;
                        return this.mCurrentParcel;
                    }
                } else {
                    obtain.recycle();
                }
                nextFile = this.mHistoryDir.getNextFile(nextFile, j, j2);
            }
        }
        if (this.mHistoryParcels != null) {
            while (this.mParcelIndex < this.mHistoryParcels.size()) {
                java.util.List<android.os.Parcel> list = this.mHistoryParcels;
                int i = this.mParcelIndex;
                this.mParcelIndex = i + 1;
                android.os.Parcel parcel = list.get(i);
                if (verifyVersion(parcel)) {
                    parcel.readLong();
                    int readInt2 = parcel.readInt();
                    int dataPosition2 = parcel.dataPosition();
                    this.mCurrentParcelEnd = readInt2 + dataPosition2;
                    this.mCurrentParcel = parcel;
                    if (dataPosition2 < this.mCurrentParcelEnd) {
                        return this.mCurrentParcel;
                    }
                }
            }
        }
        if (this.mHistoryBuffer.dataSize() <= 0) {
            return null;
        }
        this.mHistoryBuffer.setDataPosition(0);
        this.mCurrentParcel = this.mHistoryBuffer;
        this.mCurrentParcelEnd = this.mCurrentParcel.dataSize();
        return this.mCurrentParcel;
    }

    private void checkImmutable() {
        if (this.mMutable) {
            throw new java.lang.IllegalStateException("Iterating over a mutable battery history");
        }
    }

    public boolean readFileToParcel(android.os.Parcel parcel, android.util.AtomicFile atomicFile) {
        try {
            android.os.SystemClock.uptimeMillis();
            byte[] readFully = atomicFile.readFully();
            parcel.unmarshall(readFully, 0, readFully.length);
            parcel.setDataPosition(0);
            if (!verifyVersion(parcel)) {
                return false;
            }
            parcel.readLong();
            return true;
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error reading file " + atomicFile.getBaseFile().getPath(), e);
            return false;
        }
    }

    private boolean verifyVersion(android.os.Parcel parcel) {
        parcel.setDataPosition(0);
        return parcel.readInt() == 210;
    }

    public long getHistoryBufferStartTime(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        parcel.setDataPosition(0);
        parcel.readInt();
        long readLong = parcel.readLong();
        parcel.setDataPosition(dataPosition);
        return readLong;
    }

    public void writeSummaryToParcel(android.os.Parcel parcel, boolean z) {
        parcel.writeBoolean(z);
        if (z) {
            writeToParcel(parcel);
        }
        parcel.writeInt(this.mHistoryTagPool.size());
        for (java.util.Map.Entry<android.os.BatteryStats.HistoryTag, java.lang.Integer> entry : this.mHistoryTagPool.entrySet()) {
            android.os.BatteryStats.HistoryTag key = entry.getKey();
            parcel.writeInt(entry.getValue().intValue());
            parcel.writeString(key.string);
            parcel.writeInt(key.uid);
        }
    }

    public void readSummaryFromParcel(android.os.Parcel parcel) {
        if (parcel.readBoolean()) {
            readFromParcel(parcel);
        }
        this.mHistoryTagPool.clear();
        this.mNextHistoryTagIdx = 0;
        this.mNumHistoryTagChars = 0;
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            int readInt2 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            int readInt3 = parcel.readInt();
            android.os.BatteryStats.HistoryTag historyTag = new android.os.BatteryStats.HistoryTag();
            historyTag.string = readString;
            historyTag.uid = readInt3;
            historyTag.poolIdx = readInt2;
            this.mHistoryTagPool.put(historyTag, java.lang.Integer.valueOf(readInt2));
            if (readInt2 >= this.mNextHistoryTagIdx) {
                this.mNextHistoryTagIdx = readInt2 + 1;
            }
            this.mNumHistoryTagChars += historyTag.string.length() + 1;
        }
    }

    public void writeToParcel(android.os.Parcel parcel) {
        synchronized (this) {
            writeHistoryBuffer(parcel);
            writeToParcel(parcel, false);
        }
    }

    public void writeToBatteryUsageStatsParcel(android.os.Parcel parcel) {
        synchronized (this) {
            parcel.writeBlob(this.mHistoryBuffer.marshall());
            writeToParcel(parcel, true);
        }
    }

    private void writeToParcel(android.os.Parcel parcel, boolean z) {
        if (this.mHistoryDir != null) {
            this.mHistoryDir.writeToParcel(parcel, z);
        }
    }

    public static com.android.internal.os.BatteryStatsHistory createFromBatteryUsageStatsParcel(android.os.Parcel parcel) {
        return new com.android.internal.os.BatteryStatsHistory(parcel);
    }

    public boolean readSummary() {
        if (this.mActiveFile == null) {
            android.util.Slog.w(TAG, "readSummary: no history file associated with this instance");
            return false;
        }
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            try {
                android.os.SystemClock.uptimeMillis();
                if (this.mActiveFile.exists()) {
                    byte[] readFully = this.mActiveFile.readFully();
                    if (readFully.length > 0) {
                        obtain.unmarshall(readFully, 0, readFully.length);
                        obtain.setDataPosition(0);
                        readHistoryBuffer(obtain);
                    }
                }
                obtain.recycle();
                return true;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Error reading battery history", e);
                reset();
                obtain.recycle();
                return false;
            }
        } catch (java.lang.Throwable th) {
            obtain.recycle();
            throw th;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        readHistoryBuffer(parcel);
        readFromParcel(parcel, false);
    }

    private void readFromParcel(android.os.Parcel parcel, boolean z) {
        android.os.SystemClock.uptimeMillis();
        this.mHistoryParcels = new java.util.ArrayList();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            byte[] readBlob = z ? parcel.readBlob() : parcel.createByteArray();
            if (readBlob != null && readBlob.length != 0) {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                obtain.unmarshall(readBlob, 0, readBlob.length);
                obtain.setDataPosition(0);
                this.mHistoryParcels.add(obtain);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasFreeDiskSpace(java.io.File file) {
        return new android.os.StatFs(file.getAbsolutePath()).getAvailableBytes() > 104857600;
    }

    private static boolean hasFreeDiskSpace$ravenwood(java.io.File file) {
        return true;
    }

    public java.util.List<java.lang.String> getFilesNames() {
        return this.mHistoryDir.getFileNames();
    }

    public android.util.AtomicFile getActiveFile() {
        return this.mActiveFile;
    }

    public int getHistoryUsedSize() {
        int size = this.mHistoryDir.getSize() + this.mHistoryBuffer.dataSize();
        if (this.mHistoryParcels != null) {
            for (int i = 0; i < this.mHistoryParcels.size(); i++) {
                size += this.mHistoryParcels.get(i).dataSize();
            }
        }
        return size;
    }

    public void setHistoryRecordingEnabled(boolean z) {
        synchronized (this) {
            this.mRecordingHistory = z;
        }
    }

    public boolean isRecordingHistory() {
        boolean z;
        synchronized (this) {
            z = this.mRecordingHistory;
        }
        return z;
    }

    public void forceRecordAllHistory() {
        synchronized (this) {
            this.mHaveBatteryLevel = true;
            this.mRecordingHistory = true;
        }
    }

    public void startRecordingHistory(long j, long j2, boolean z) {
        synchronized (this) {
            this.mRecordingHistory = true;
            this.mHistoryCur.currentTime = this.mClock.currentTimeMillis();
            writeHistoryItem(j, j2, this.mHistoryCur, z ? (byte) 7 : (byte) 5);
            this.mHistoryCur.currentTime = 0L;
        }
    }

    public void continueRecordingHistory() {
        synchronized (this) {
            if (this.mHistoryBuffer.dataPosition() > 0 || this.mHistoryDir.getFileCount() > 1) {
                this.mRecordingHistory = true;
                long elapsedRealtime = this.mClock.elapsedRealtime();
                long uptimeMillis = this.mClock.uptimeMillis();
                writeHistoryItem(elapsedRealtime, uptimeMillis, this.mHistoryCur, (byte) 4);
                startRecordingHistory(elapsedRealtime, uptimeMillis, false);
            }
        }
    }

    public void setBatteryState(boolean z, int i, int i2, int i3) {
        synchronized (this) {
            this.mHaveBatteryLevel = true;
            setChargingState(z);
            this.mHistoryCur.batteryStatus = (byte) i;
            this.mHistoryCur.batteryLevel = (byte) i2;
            this.mHistoryCur.batteryChargeUah = i3;
        }
    }

    public void setBatteryState(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        synchronized (this) {
            this.mHaveBatteryLevel = true;
            this.mHistoryCur.batteryStatus = (byte) i;
            this.mHistoryCur.batteryLevel = (byte) i2;
            this.mHistoryCur.batteryHealth = (byte) i3;
            this.mHistoryCur.batteryPlugType = (byte) i4;
            this.mHistoryCur.batteryTemperature = (short) i5;
            this.mHistoryCur.batteryVoltage = (short) i6;
            this.mHistoryCur.batteryChargeUah = i7;
        }
    }

    public void setPluggedInState(boolean z) {
        synchronized (this) {
            if (z) {
                this.mHistoryCur.states |= 524288;
            } else {
                this.mHistoryCur.states &= -524289;
            }
        }
    }

    public void setChargingState(boolean z) {
        synchronized (this) {
            if (z) {
                this.mHistoryCur.states2 |= 16777216;
            } else {
                this.mHistoryCur.states2 &= -16777217;
            }
        }
    }

    public void recordEvent(long j, long j2, int i, java.lang.String str, int i2) {
        synchronized (this) {
            this.mHistoryCur.eventCode = i;
            this.mHistoryCur.eventTag = this.mHistoryCur.localEventTag;
            this.mHistoryCur.eventTag.string = str;
            this.mHistoryCur.eventTag.uid = i2;
            writeHistoryItem(j, j2);
        }
    }

    public void recordCurrentTimeChange(long j, long j2, long j3) {
        synchronized (this) {
            if (this.mRecordingHistory) {
                this.mHistoryCur.currentTime = j3;
                writeHistoryItem(j, j2, this.mHistoryCur, (byte) 5);
                this.mHistoryCur.currentTime = 0L;
            }
        }
    }

    public void recordShutdownEvent(long j, long j2, long j3) {
        synchronized (this) {
            if (this.mRecordingHistory) {
                this.mHistoryCur.currentTime = j3;
                writeHistoryItem(j, j2, this.mHistoryCur, (byte) 8);
                this.mHistoryCur.currentTime = 0L;
            }
        }
    }

    public void recordBatteryState(long j, long j2, int i, boolean z) {
        synchronized (this) {
            this.mHistoryCur.batteryLevel = (byte) i;
            setPluggedInState(z);
            writeHistoryItem(j, j2);
        }
    }

    public void recordPowerStats(long j, long j2, com.android.internal.os.PowerStats powerStats) {
        synchronized (this) {
            this.mHistoryCur.powerStats = powerStats;
            this.mHistoryCur.states2 |= 131072;
            writeHistoryItem(j, j2);
        }
    }

    public void recordProcessStateChange(long j, long j2, int i, int i2) {
        synchronized (this) {
            this.mHistoryCur.processStateChange = this.mHistoryCur.localProcessStateChange;
            this.mHistoryCur.processStateChange.uid = i;
            this.mHistoryCur.processStateChange.processState = i2;
            this.mHistoryCur.states2 |= 131072;
            writeHistoryItem(j, j2);
        }
    }

    public void recordWifiConsumedCharge(long j, long j2, double d) {
        synchronized (this) {
            this.mHistoryCur.wifiRailChargeMah += d;
            writeHistoryItem(j, j2);
        }
    }

    public void recordWakelockStartEvent(long j, long j2, java.lang.String str, int i) {
        synchronized (this) {
            this.mHistoryCur.wakelockTag = this.mHistoryCur.localWakelockTag;
            this.mHistoryCur.wakelockTag.string = str;
            this.mHistoryCur.wakelockTag.uid = i;
            recordStateStartEvent(j, j2, 1073741824);
        }
    }

    public boolean maybeUpdateWakelockTag(long j, long j2, java.lang.String str, int i) {
        synchronized (this) {
            if (this.mHistoryLastWritten.cmd != 0) {
                return false;
            }
            if (this.mHistoryLastWritten.wakelockTag != null) {
                this.mHistoryLastWritten.wakelockTag = null;
                this.mHistoryCur.wakelockTag = this.mHistoryCur.localWakelockTag;
                this.mHistoryCur.wakelockTag.string = str;
                this.mHistoryCur.wakelockTag.uid = i;
                writeHistoryItem(j, j2);
            }
            return true;
        }
    }

    public void recordWakelockStopEvent(long j, long j2, java.lang.String str, int i) {
        synchronized (this) {
            this.mHistoryCur.wakelockTag = this.mHistoryCur.localWakelockTag;
            android.os.BatteryStats.HistoryTag historyTag = this.mHistoryCur.wakelockTag;
            if (str == null) {
                str = "";
            }
            historyTag.string = str;
            this.mHistoryCur.wakelockTag.uid = i;
            recordStateStopEvent(j, j2, 1073741824);
        }
    }

    public void recordStateStartEvent(long j, long j2, int i) {
        synchronized (this) {
            android.os.BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = i | historyItem.states;
            writeHistoryItem(j, j2);
        }
    }

    public void recordStateStopEvent(long j, long j2, int i) {
        synchronized (this) {
            android.os.BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states = (~i) & historyItem.states;
            writeHistoryItem(j, j2);
        }
    }

    public void recordStateChangeEvent(long j, long j2, int i, int i2) {
        synchronized (this) {
            this.mHistoryCur.states = (i | this.mHistoryCur.states) & (~i2);
            writeHistoryItem(j, j2);
        }
    }

    public void recordState2StartEvent(long j, long j2, int i) {
        synchronized (this) {
            android.os.BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = i | historyItem.states2;
            writeHistoryItem(j, j2);
        }
    }

    public void recordState2StopEvent(long j, long j2, int i) {
        synchronized (this) {
            android.os.BatteryStats.HistoryItem historyItem = this.mHistoryCur;
            historyItem.states2 = (~i) & historyItem.states2;
            writeHistoryItem(j, j2);
        }
    }

    public void recordWakeupEvent(long j, long j2, java.lang.String str) {
        synchronized (this) {
            this.mHistoryCur.wakeReasonTag = this.mHistoryCur.localWakeReasonTag;
            this.mHistoryCur.wakeReasonTag.string = str;
            this.mHistoryCur.wakeReasonTag.uid = 0;
            writeHistoryItem(j, j2);
        }
    }

    public void recordScreenBrightnessEvent(long j, long j2, int i) {
        synchronized (this) {
            this.mHistoryCur.states = setBitField(this.mHistoryCur.states, i, 0, 7);
            writeHistoryItem(j, j2);
        }
    }

    public void recordGpsSignalQualityEvent(long j, long j2, int i) {
        synchronized (this) {
            this.mHistoryCur.states2 = setBitField(this.mHistoryCur.states2, i, 7, 384);
            writeHistoryItem(j, j2);
        }
    }

    public void recordDeviceIdleEvent(long j, long j2, int i) {
        synchronized (this) {
            this.mHistoryCur.states2 = setBitField(this.mHistoryCur.states2, i, 25, 100663296);
            writeHistoryItem(j, j2);
        }
    }

    public void recordPhoneStateChangeEvent(long j, long j2, int i, int i2, int i3, int i4) {
        synchronized (this) {
            this.mHistoryCur.states = (i | this.mHistoryCur.states) & (~i2);
            if (i3 != -1) {
                this.mHistoryCur.states = setBitField(this.mHistoryCur.states, i3, 6, 448);
            }
            if (i4 != -1) {
                this.mHistoryCur.states = setBitField(this.mHistoryCur.states, i4, 3, 56);
            }
            writeHistoryItem(j, j2);
        }
    }

    public void recordDataConnectionTypeChangeEvent(long j, long j2, int i) {
        synchronized (this) {
            this.mHistoryCur.states = setBitField(this.mHistoryCur.states, i, 9, android.os.BatteryStats.HistoryItem.STATE_DATA_CONNECTION_MASK);
            writeHistoryItem(j, j2);
        }
    }

    public void recordNrStateChangeEvent(long j, long j2, int i) {
        synchronized (this) {
            this.mHistoryCur.states2 = setBitField(this.mHistoryCur.states2, i, 9, 1536);
            writeHistoryItem(j, j2);
        }
    }

    public void recordWifiSupplicantStateChangeEvent(long j, long j2, int i) {
        synchronized (this) {
            this.mHistoryCur.states2 = setBitField(this.mHistoryCur.states2, i, 0, 15);
            writeHistoryItem(j, j2);
        }
    }

    public void recordWifiSignalStrengthChangeEvent(long j, long j2, int i) {
        synchronized (this) {
            this.mHistoryCur.states2 = setBitField(this.mHistoryCur.states2, i, 4, 112);
            writeHistoryItem(j, j2);
        }
    }

    private void recordTraceEvents(int i, android.os.BatteryStats.HistoryTag historyTag) {
        java.lang.String str;
        if (i == 0) {
            return;
        }
        int i2 = (-49153) & i;
        if ((32768 & i) != 0) {
            str = "+";
        } else {
            str = (i & 16384) != 0 ? com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE : "";
        }
        java.lang.String[] strArr = android.os.BatteryStats.HISTORY_EVENT_NAMES;
        if (i2 < 0 || i2 >= strArr.length) {
            return;
        }
        this.mTracer.traceInstantEvent("battery_stats." + strArr[i2], str + strArr[i2] + "=" + historyTag.uid + ":\"" + historyTag.string + "\"");
    }

    private void recordTraceCounters(int i, int i2, int i3, android.os.BatteryStats.BitDescription[] bitDescriptionArr) {
        int i4;
        int i5 = (i ^ i2) & i3;
        if (i5 == 0) {
            return;
        }
        for (android.os.BatteryStats.BitDescription bitDescription : bitDescriptionArr) {
            if ((bitDescription.mask & i5) != 0) {
                if (bitDescription.shift < 0) {
                    i4 = (bitDescription.mask & i2) != 0 ? 1 : 0;
                } else {
                    i4 = (bitDescription.mask & i2) >> bitDescription.shift;
                }
                this.mTracer.traceCounter("battery_stats." + bitDescription.name, i4);
            }
        }
    }

    private int setBitField(int i, int i2, int i3, int i4) {
        int i5 = i2 << i3;
        int i6 = ~i4;
        if ((i5 & i6) != 0) {
            android.util.Slog.wtfStack(TAG, "Value " + java.lang.Integer.toHexString(i2) + " does not fit in the bit field: " + java.lang.Integer.toHexString(i4));
            i5 &= i4;
        }
        return (i & i6) | i5;
    }

    public void writeHistoryItem(long j, long j2) {
        synchronized (this) {
            if (this.mTrackRunningHistoryElapsedRealtimeMs != 0) {
                long j3 = j - this.mTrackRunningHistoryElapsedRealtimeMs;
                long j4 = j2 - this.mTrackRunningHistoryUptimeMs;
                if (j4 < j3 - 20) {
                    long j5 = j - (j3 - j4);
                    this.mHistoryAddTmp.setTo(this.mHistoryLastWritten);
                    this.mHistoryAddTmp.wakelockTag = null;
                    this.mHistoryAddTmp.wakeReasonTag = null;
                    this.mHistoryAddTmp.eventCode = 0;
                    this.mHistoryAddTmp.states &= Integer.MAX_VALUE;
                    writeHistoryItem(j5, j2, this.mHistoryAddTmp);
                }
            }
            this.mHistoryCur.states |= Integer.MIN_VALUE;
            this.mTrackRunningHistoryElapsedRealtimeMs = j;
            this.mTrackRunningHistoryUptimeMs = j2;
            writeHistoryItem(j, j2, this.mHistoryCur);
        }
    }

    private void writeHistoryItem(long j, long j2, android.os.BatteryStats.HistoryItem historyItem) {
        if (this.mTracer != null && this.mTracer.tracingEnabled()) {
            recordTraceEvents(historyItem.eventCode, historyItem.eventTag);
            recordTraceCounters(this.mTraceLastState, historyItem.states, 1073741823, android.os.BatteryStats.HISTORY_STATE_DESCRIPTIONS);
            recordTraceCounters(this.mTraceLastState2, historyItem.states2, -1, android.os.BatteryStats.HISTORY_STATE2_DESCRIPTIONS);
            this.mTraceLastState = historyItem.states;
            this.mTraceLastState2 = historyItem.states2;
        }
        if ((!this.mHaveBatteryLevel || !this.mRecordingHistory) && historyItem.powerStats == null && historyItem.processStateChange == null) {
            return;
        }
        if (!this.mMutable) {
            throw new java.util.ConcurrentModificationException("Battery history is not writable");
        }
        long monotonicTime = this.mMonotonicClock.monotonicTime(j) - this.mHistoryLastWritten.time;
        int i = this.mHistoryLastWritten.states ^ historyItem.states;
        int i2 = this.mHistoryLastWritten.states2 ^ historyItem.states2;
        int i3 = this.mHistoryLastWritten.states ^ this.mHistoryLastLastWritten.states;
        int i4 = this.mHistoryLastWritten.states2 ^ this.mHistoryLastLastWritten.states2;
        if (this.mHistoryBufferLastPos >= 0 && this.mHistoryLastWritten.cmd == 0 && monotonicTime < 1000 && (i & i3) == 0 && (i2 & i4) == 0 && !this.mHistoryLastWritten.tagsFirstOccurrence && !historyItem.tagsFirstOccurrence && ((this.mHistoryLastWritten.wakelockTag == null || historyItem.wakelockTag == null) && ((this.mHistoryLastWritten.wakeReasonTag == null || historyItem.wakeReasonTag == null) && this.mHistoryLastWritten.stepDetails == null && ((this.mHistoryLastWritten.eventCode == 0 || historyItem.eventCode == 0) && this.mHistoryLastWritten.batteryLevel == historyItem.batteryLevel && this.mHistoryLastWritten.batteryStatus == historyItem.batteryStatus && this.mHistoryLastWritten.batteryHealth == historyItem.batteryHealth && this.mHistoryLastWritten.batteryPlugType == historyItem.batteryPlugType && this.mHistoryLastWritten.batteryTemperature == historyItem.batteryTemperature && this.mHistoryLastWritten.batteryVoltage == historyItem.batteryVoltage && this.mHistoryLastWritten.powerStats == null && this.mHistoryLastWritten.processStateChange == null)))) {
            this.mHistoryBuffer.setDataSize(this.mHistoryBufferLastPos);
            this.mHistoryBuffer.setDataPosition(this.mHistoryBufferLastPos);
            this.mHistoryBufferLastPos = -1;
            j -= monotonicTime;
            if (this.mHistoryLastWritten.wakelockTag != null) {
                historyItem.wakelockTag = historyItem.localWakelockTag;
                historyItem.wakelockTag.setTo(this.mHistoryLastWritten.wakelockTag);
            }
            if (this.mHistoryLastWritten.wakeReasonTag != null) {
                historyItem.wakeReasonTag = historyItem.localWakeReasonTag;
                historyItem.wakeReasonTag.setTo(this.mHistoryLastWritten.wakeReasonTag);
            }
            if (this.mHistoryLastWritten.eventCode != 0) {
                historyItem.eventCode = this.mHistoryLastWritten.eventCode;
                historyItem.eventTag = historyItem.localEventTag;
                historyItem.eventTag.setTo(this.mHistoryLastWritten.eventTag);
            }
            this.mHistoryLastWritten.setTo(this.mHistoryLastLastWritten);
        }
        int dataSize = this.mHistoryBuffer.dataSize();
        if (dataSize >= this.mMaxHistoryBufferSize) {
            if (this.mMaxHistoryBufferSize == 0) {
                android.util.Slog.wtf(TAG, "mMaxHistoryBufferSize should not be zero when writing history");
                this.mMaxHistoryBufferSize = 1024;
            }
            android.os.BatteryStats.HistoryItem historyItem2 = new android.os.BatteryStats.HistoryItem();
            historyItem2.setTo(historyItem);
            startNextFile(j);
            long j3 = j;
            startRecordingHistory(j3, j2, false);
            writeHistoryItem(j3, j2, historyItem2, (byte) 0);
            return;
        }
        if (dataSize == 0) {
            android.os.BatteryStats.HistoryItem historyItem3 = new android.os.BatteryStats.HistoryItem();
            historyItem3.setTo(historyItem);
            historyItem3.currentTime = this.mClock.currentTimeMillis();
            historyItem3.wakelockTag = null;
            historyItem3.wakeReasonTag = null;
            historyItem3.eventCode = 0;
            historyItem3.eventTag = null;
            historyItem3.tagsFirstOccurrence = false;
            historyItem3.powerStats = null;
            historyItem3.processStateChange = null;
            writeHistoryItem(j, j2, historyItem3, (byte) 7);
        }
        writeHistoryItem(j, j2, historyItem, (byte) 0);
    }

    private void writeHistoryItem(long j, long j2, android.os.BatteryStats.HistoryItem historyItem, byte b) {
        if (!this.mMutable) {
            throw new java.util.ConcurrentModificationException("Battery history is not writable");
        }
        this.mHistoryBufferLastPos = this.mHistoryBuffer.dataPosition();
        this.mHistoryLastLastWritten.setTo(this.mHistoryLastWritten);
        boolean z = this.mHistoryLastWritten.tagsFirstOccurrence || historyItem.tagsFirstOccurrence;
        this.mHistoryLastWritten.setTo(this.mMonotonicClock.monotonicTime(j), b, historyItem);
        if (this.mHistoryLastWritten.time < this.mHistoryLastLastWritten.time - 60000) {
            android.util.Slog.wtf(TAG, "Significantly earlier event written to battery history: time=" + this.mHistoryLastWritten.time + " previous=" + this.mHistoryLastLastWritten.time);
        }
        this.mHistoryLastWritten.tagsFirstOccurrence = z;
        writeHistoryDelta(this.mHistoryBuffer, this.mHistoryLastWritten, this.mHistoryLastLastWritten);
        historyItem.wakelockTag = null;
        historyItem.wakeReasonTag = null;
        historyItem.eventCode = 0;
        historyItem.eventTag = null;
        historyItem.tagsFirstOccurrence = false;
        historyItem.powerStats = null;
        historyItem.processStateChange = null;
    }

    private void writeHistoryDelta(android.os.Parcel parcel, android.os.BatteryStats.HistoryItem historyItem, android.os.BatteryStats.HistoryItem historyItem2) {
        int i;
        int i2;
        int i3;
        int i4;
        if (historyItem2 == null || historyItem.cmd != 0) {
            parcel.writeInt(DELTA_TIME_ABS);
            historyItem.writeToParcel(parcel, 0);
            return;
        }
        long j = historyItem.time - historyItem2.time;
        int buildBatteryLevelInt = buildBatteryLevelInt(historyItem2);
        int buildStateInt = buildStateInt(historyItem2);
        if (j < 0 || j > 2147483647L) {
            i = 524287;
        } else if (j >= 524285) {
            i = DELTA_TIME_INT;
        } else {
            i = (int) j;
        }
        int i5 = (historyItem.states & DELTA_STATE_MASK) | i;
        int buildBatteryLevelInt2 = buildBatteryLevelInt(historyItem);
        if (historyItem.batteryLevel < this.mLastHistoryStepLevel || this.mLastHistoryStepLevel == 0) {
            historyItem.stepDetails = this.mStepDetailsCalculator.getHistoryStepDetails();
            if (historyItem.stepDetails != null) {
                buildBatteryLevelInt2 |= 1;
                this.mLastHistoryStepLevel = historyItem.batteryLevel;
            }
        } else {
            historyItem.stepDetails = null;
            this.mLastHistoryStepLevel = historyItem.batteryLevel;
        }
        boolean z = buildBatteryLevelInt2 != buildBatteryLevelInt;
        if (z) {
            i5 |= 524288;
        }
        int buildStateInt2 = buildStateInt(historyItem);
        boolean z2 = buildStateInt2 != buildStateInt;
        if (z2) {
            i5 |= 1048576;
        }
        if (historyItem.powerStats == null) {
            i2 = 0;
        } else if (this.mWrittenPowerStatsDescriptors.contains(historyItem.powerStats.descriptor)) {
            i2 = 2;
        } else {
            i2 = 3;
        }
        if (historyItem.processStateChange != null) {
            i2 |= 4;
        }
        if (i2 != 0) {
            historyItem.states2 |= 131072;
        } else {
            historyItem.states2 &= -131073;
        }
        boolean z3 = (historyItem.states2 == historyItem2.states2 && i2 == 0) ? false : true;
        if (z3) {
            i5 |= 2097152;
        }
        if (historyItem.wakelockTag != null || historyItem.wakeReasonTag != null) {
            i5 |= 4194304;
        }
        if (historyItem.eventCode != 0) {
            i5 |= 8388608;
        }
        boolean z4 = historyItem.batteryChargeUah != historyItem2.batteryChargeUah;
        if (z4) {
            i5 |= 16777216;
        }
        parcel.writeInt(i5);
        if (i >= DELTA_TIME_INT) {
            if (i == DELTA_TIME_INT) {
                parcel.writeInt((int) j);
            } else {
                parcel.writeLong(j);
            }
        }
        if (z) {
            parcel.writeInt(buildBatteryLevelInt2);
        }
        if (z2) {
            parcel.writeInt(buildStateInt2);
        }
        if (z3) {
            parcel.writeInt(historyItem.states2);
        }
        if (historyItem.wakelockTag != null || historyItem.wakeReasonTag != null) {
            if (historyItem.wakelockTag != null) {
                i3 = writeHistoryTag(historyItem.wakelockTag);
            } else {
                i3 = 65535;
            }
            if (historyItem.wakeReasonTag != null) {
                i4 = writeHistoryTag(historyItem.wakeReasonTag);
            } else {
                i4 = 65535;
            }
            parcel.writeInt((i4 << 16) | i3);
            if (historyItem.wakelockTag != null && (i3 & 32768) != 0) {
                historyItem.wakelockTag.writeToParcel(parcel, 0);
                historyItem.tagsFirstOccurrence = true;
            }
            if (historyItem.wakeReasonTag != null && (i4 & 32768) != 0) {
                historyItem.wakeReasonTag.writeToParcel(parcel, 0);
                historyItem.tagsFirstOccurrence = true;
            }
        }
        if (historyItem.eventCode != 0) {
            int writeHistoryTag = writeHistoryTag(historyItem.eventTag);
            parcel.writeInt(setBitField(65535 & historyItem.eventCode, writeHistoryTag, 16, -65536));
            if ((writeHistoryTag & 32768) != 0) {
                historyItem.eventTag.writeToParcel(parcel, 0);
                historyItem.tagsFirstOccurrence = true;
            }
        }
        if (historyItem.stepDetails != null) {
            historyItem.stepDetails.writeToParcel(parcel);
        }
        if (z4) {
            parcel.writeInt(historyItem.batteryChargeUah);
        }
        parcel.writeDouble(historyItem.modemRailChargeMah);
        parcel.writeDouble(historyItem.wifiRailChargeMah);
        if (i2 != 0) {
            parcel.writeInt(i2);
            if (historyItem.powerStats != null) {
                if ((1 & i2) != 0) {
                    historyItem.powerStats.descriptor.writeSummaryToParcel(parcel);
                    this.mWrittenPowerStatsDescriptors.add(historyItem.powerStats.descriptor);
                }
                historyItem.powerStats.writeToParcel(parcel);
            }
            if (historyItem.processStateChange != null) {
                historyItem.processStateChange.writeToParcel(parcel);
            }
        }
    }

    private int buildBatteryLevelInt(android.os.BatteryStats.HistoryItem historyItem) {
        int bitField = setBitField(setBitField(0, historyItem.batteryLevel, 25, DELTA_STATE_MASK), historyItem.batteryTemperature, 15, 33521664);
        short s = historyItem.batteryVoltage;
        if (s == -1) {
            s = 16383;
        }
        return setBitField(bitField, s, 1, HISTORY_TAG_INDEX_LIMIT);
    }

    private int buildStateInt(android.os.BatteryStats.HistoryItem historyItem) {
        int i = 1;
        if ((historyItem.batteryPlugType & 1) == 0) {
            i = 2;
            if ((historyItem.batteryPlugType & 2) == 0) {
                if ((historyItem.batteryPlugType & 4) == 0) {
                    i = 0;
                } else {
                    i = 3;
                }
            }
        }
        return (historyItem.states & 16777215) | ((historyItem.batteryStatus & 7) << 29) | ((historyItem.batteryHealth & 7) << 26) | ((i & 3) << 24);
    }

    private int writeHistoryTag(android.os.BatteryStats.HistoryTag historyTag) {
        if (historyTag.string == null) {
            android.util.Slog.wtfStack(TAG, "writeHistoryTag called with null name");
        }
        int length = historyTag.string.length();
        if (length > 1024) {
            android.util.Slog.e(TAG, "Long battery history tag: " + historyTag.string);
            historyTag.string = historyTag.string.substring(0, 1024);
        }
        java.lang.Integer num = this.mHistoryTagPool.get(historyTag);
        if (num != null) {
            int intValue = num.intValue();
            if ((intValue & 32768) != 0) {
                this.mHistoryTagPool.put(historyTag, java.lang.Integer.valueOf((-32769) & intValue));
            }
            return intValue;
        }
        if (this.mNextHistoryTagIdx < HISTORY_TAG_INDEX_LIMIT) {
            int i = this.mNextHistoryTagIdx;
            android.os.BatteryStats.HistoryTag historyTag2 = new android.os.BatteryStats.HistoryTag();
            historyTag2.setTo(historyTag);
            historyTag.poolIdx = i;
            this.mHistoryTagPool.put(historyTag2, java.lang.Integer.valueOf(i));
            this.mNextHistoryTagIdx++;
            this.mNumHistoryTagChars += length + 1;
            if (this.mHistoryTags != null) {
                this.mHistoryTags.put(i, historyTag2);
            }
            return i | 32768;
        }
        historyTag.poolIdx = -1;
        return android.content.res.Configuration.DENSITY_DPI_ANY;
    }

    public void commitCurrentHistoryBatchLocked() {
        synchronized (this) {
            this.mHistoryLastWritten.cmd = (byte) -1;
        }
    }

    public void writeHistory() {
        synchronized (this) {
            if (isReadOnly()) {
                android.util.Slog.w(TAG, "writeHistory: this instance instance is read-only");
                return;
            }
            this.mMonotonicClock.write();
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                android.os.SystemClock.uptimeMillis();
                writeHistoryBuffer(obtain);
                writeParcelToFileLocked(obtain, this.mActiveFile);
            } finally {
                obtain.recycle();
            }
        }
    }

    public void readHistoryBuffer(android.os.Parcel parcel) throws android.os.ParcelFormatException {
        synchronized (this) {
            int readInt = parcel.readInt();
            if (readInt != 210) {
                android.util.Slog.w("BatteryStats", "readHistoryBuffer: version got " + readInt + ", expected 210; erasing old stats");
                return;
            }
            this.mHistoryBufferStartTime = parcel.readLong();
            this.mHistoryBuffer.setDataSize(0);
            this.mHistoryBuffer.setDataPosition(0);
            int readInt2 = parcel.readInt();
            int dataPosition = parcel.dataPosition();
            if (readInt2 >= this.mMaxHistoryBufferSize * 100) {
                throw new android.os.ParcelFormatException("File corrupt: history data buffer too large " + readInt2);
            }
            if ((readInt2 & (-4)) != readInt2) {
                throw new android.os.ParcelFormatException("File corrupt: history data buffer not aligned " + readInt2);
            }
            this.mHistoryBuffer.appendFrom(parcel, dataPosition, readInt2);
            parcel.setDataPosition(dataPosition + readInt2);
        }
    }

    private void writeHistoryBuffer(android.os.Parcel parcel) {
        parcel.writeInt(210);
        parcel.writeLong(this.mHistoryBufferStartTime);
        parcel.writeInt(this.mHistoryBuffer.dataSize());
        parcel.appendFrom(this.mHistoryBuffer, 0, this.mHistoryBuffer.dataSize());
    }

    private void writeParcelToFileLocked(android.os.Parcel parcel, android.util.AtomicFile atomicFile) {
        this.mWriteLock.lock();
        java.io.FileOutputStream fileOutputStream = null;
        try {
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                fileOutputStream = atomicFile.startWrite();
                fileOutputStream.write(parcel.marshall());
                fileOutputStream.flush();
                atomicFile.finishWrite(fileOutputStream);
                this.mEventLogger.writeCommitSysConfigFile(uptimeMillis);
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Error writing battery statistics", e);
                atomicFile.failWrite(fileOutputStream);
            }
        } finally {
            this.mWriteLock.unlock();
        }
    }

    public int getHistoryStringPoolSize() {
        int size;
        synchronized (this) {
            size = this.mHistoryTagPool.size();
        }
        return size;
    }

    public int getHistoryStringPoolBytes() {
        int i;
        synchronized (this) {
            i = this.mNumHistoryTagChars;
        }
        return i;
    }

    public java.lang.String getHistoryTagPoolString(int i) {
        java.lang.String str;
        synchronized (this) {
            ensureHistoryTagArray();
            android.os.BatteryStats.HistoryTag historyTag = this.mHistoryTags.get(i);
            str = historyTag != null ? historyTag.string : null;
        }
        return str;
    }

    public int getHistoryTagPoolUid(int i) {
        int i2;
        synchronized (this) {
            ensureHistoryTagArray();
            android.os.BatteryStats.HistoryTag historyTag = this.mHistoryTags.get(i);
            i2 = historyTag != null ? historyTag.uid : -1;
        }
        return i2;
    }

    private void ensureHistoryTagArray() {
        if (this.mHistoryTags != null) {
            return;
        }
        this.mHistoryTags = new android.util.SparseArray<>(this.mHistoryTagPool.size());
        for (java.util.Map.Entry<android.os.BatteryStats.HistoryTag, java.lang.Integer> entry : this.mHistoryTagPool.entrySet()) {
            this.mHistoryTags.put(entry.getValue().intValue() & (-32769), entry.getKey());
        }
    }

    public static final class VarintParceler {
        public void writeLongArray(android.os.Parcel parcel, long[] jArr) {
            byte b;
            if (jArr.length == 0) {
                return;
            }
            int i = 0;
            int i2 = 0;
            for (long j : jArr) {
                boolean z = false;
                while (!z) {
                    if (((-128) & j) == 0) {
                        b = (byte) j;
                        z = true;
                    } else {
                        b = (byte) ((((int) j) & 127) | 128);
                        j >>>= 7;
                    }
                    if (i == 32) {
                        parcel.writeInt(i2);
                        i = 0;
                        i2 = 0;
                    }
                    i2 |= (b & 255) << i;
                    i += 8;
                }
            }
            if (i != 0) {
                parcel.writeInt(i2);
            }
        }

        public void readLongArray(android.os.Parcel parcel, long[] jArr) {
            if (jArr.length == 0) {
                return;
            }
            int readInt = parcel.readInt();
            int i = 4;
            for (int i2 = 0; i2 < jArr.length; i2++) {
                long j = 0;
                int i3 = 0;
                while (true) {
                    if (i3 >= 64) {
                        break;
                    }
                    if (i == 0) {
                        readInt = parcel.readInt();
                        i = 4;
                    }
                    byte b = (byte) readInt;
                    readInt >>= 8;
                    i--;
                    j |= (b & Byte.MAX_VALUE) << i3;
                    if ((b & 128) != 0) {
                        i3 += 7;
                    } else {
                        jArr[i2] = j;
                        break;
                    }
                }
                if (i3 >= 64) {
                    throw new android.os.ParcelFormatException("Invalid varint format");
                }
            }
        }
    }
}
