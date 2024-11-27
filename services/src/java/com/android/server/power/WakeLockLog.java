package com.android.server.power;

/* loaded from: classes2.dex */
final class WakeLockLog {
    private static final boolean DEBUG = false;
    private static final int FLAG_ACQUIRE_CAUSES_WAKEUP = 16;
    private static final int FLAG_ON_AFTER_RELEASE = 8;
    private static final int FLAG_SYSTEM_WAKELOCK = 32;
    private static final int LEVEL_DOZE_WAKE_LOCK = 6;
    private static final int LEVEL_DRAW_WAKE_LOCK = 7;
    private static final int LEVEL_FULL_WAKE_LOCK = 2;
    private static final int LEVEL_PARTIAL_WAKE_LOCK = 1;
    private static final int LEVEL_PROXIMITY_SCREEN_OFF_WAKE_LOCK = 5;
    private static final int LEVEL_SCREEN_BRIGHT_WAKE_LOCK = 4;
    private static final int LEVEL_SCREEN_DIM_WAKE_LOCK = 3;
    private static final int LEVEL_UNKNOWN = 0;
    private static final int LOG_SIZE = 10240;
    private static final int LOG_SIZE_MIN = 10;
    private static final int MASK_LOWER_6_BITS = 63;
    private static final int MASK_LOWER_7_BITS = 127;
    private static final int MAX_LOG_ENTRY_BYTE_SIZE = 9;
    private static final java.lang.String TAG = "PowerManagerService.WLLog";
    private static final int TAG_DATABASE_SIZE = 128;
    private static final int TAG_DATABASE_SIZE_MAX = 128;
    private static final int TYPE_ACQUIRE = 1;
    private static final int TYPE_RELEASE = 2;
    private static final int TYPE_TIME_RESET = 0;
    private final android.content.Context mContext;
    private final java.text.SimpleDateFormat mDumpsysDateFormat;
    private final com.android.server.power.WakeLockLog.Injector mInjector;
    private final java.lang.Object mLock;
    private final com.android.server.power.WakeLockLog.TheLog mLog;
    private final com.android.server.power.WakeLockLog.TagDatabase mTagDatabase;
    private static final java.lang.String[] LEVEL_TO_STRING = {"unknown", "partial", "full", "screen-dim", "screen-bright", "prox", "doze", "draw"};
    private static final java.lang.String[] REDUCED_TAG_PREFIXES = {"*job*/", "*gms_scheduler*/", "IntentOp:"};
    private static final java.text.SimpleDateFormat DATE_FORMAT = new java.text.SimpleDateFormat("MM-dd HH:mm:ss.SSS");

    WakeLockLog(android.content.Context context) {
        this(new com.android.server.power.WakeLockLog.Injector(), context);
    }

    @com.android.internal.annotations.VisibleForTesting
    WakeLockLog(com.android.server.power.WakeLockLog.Injector injector, android.content.Context context) {
        this.mLock = new java.lang.Object();
        this.mInjector = injector;
        this.mTagDatabase = new com.android.server.power.WakeLockLog.TagDatabase(injector);
        this.mLog = new com.android.server.power.WakeLockLog.TheLog(injector, new com.android.server.power.WakeLockLog.EntryByteTranslator(this.mTagDatabase), this.mTagDatabase);
        this.mDumpsysDateFormat = injector.getDateFormat();
        this.mContext = context;
    }

    public void onWakeLockAcquired(java.lang.String str, int i, int i2) {
        onWakeLockEvent(1, str, i, i2);
    }

    public void onWakeLockReleased(java.lang.String str, int i) {
        onWakeLockEvent(2, str, i, 0);
    }

    public void dump(java.io.PrintWriter printWriter) {
        dump(printWriter, false);
    }

    @com.android.internal.annotations.VisibleForTesting
    void dump(java.io.PrintWriter printWriter, boolean z) {
        try {
            synchronized (this.mLock) {
                try {
                    printWriter.println("Wake Lock Log");
                    android.util.SparseArray<java.lang.String[]> sparseArray = new android.util.SparseArray<>();
                    int i = 0;
                    int i2 = 0;
                    for (int i3 = 0; i3 < this.mLog.mSavedAcquisitions.size(); i3++) {
                        i2++;
                        com.android.server.power.WakeLockLog.LogEntry logEntry = (com.android.server.power.WakeLockLog.LogEntry) this.mLog.mSavedAcquisitions.get(i3);
                        logEntry.updatePackageName(sparseArray, this.mContext.getPackageManager());
                        logEntry.dump(printWriter, this.mDumpsysDateFormat);
                    }
                    java.util.Iterator<com.android.server.power.WakeLockLog.LogEntry> allItems = this.mLog.getAllItems(new com.android.server.power.WakeLockLog.LogEntry());
                    while (allItems.hasNext()) {
                        com.android.server.power.WakeLockLog.LogEntry next = allItems.next();
                        if (next != null) {
                            if (next.type == 0) {
                                i++;
                            } else {
                                i2++;
                                next.updatePackageName(sparseArray, this.mContext.getPackageManager());
                                next.dump(printWriter, this.mDumpsysDateFormat);
                            }
                        }
                    }
                    printWriter.println("  -");
                    printWriter.println("  Events: " + i2 + ", Time-Resets: " + i);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("  Buffer, Bytes used: ");
                    sb.append(this.mLog.getUsedBufferSize());
                    printWriter.println(sb.toString());
                    if (z) {
                        printWriter.println("  " + this.mTagDatabase);
                    }
                } finally {
                }
            }
        } catch (java.lang.Exception e) {
            printWriter.println("Exception dumping wake-lock log: " + e.toString());
        }
    }

    private void onWakeLockEvent(int i, java.lang.String str, int i2, int i3) {
        int i4;
        if (str == null) {
            android.util.Slog.w(TAG, "Insufficient data to log wakelock [tag: " + str + ", ownerUid: " + i2 + ", flags: 0x" + java.lang.Integer.toHexString(i3));
            return;
        }
        long currentTimeMillis = this.mInjector.currentTimeMillis();
        if (i == 1) {
            i4 = translateFlagsFromPowerManager(i3);
        } else {
            i4 = 0;
        }
        handleWakeLockEventInternal(i, tagNameReducer(str), i2, i4, currentTimeMillis);
    }

    private void handleWakeLockEventInternal(int i, java.lang.String str, int i2, int i3, long j) {
        synchronized (this.mLock) {
            this.mLog.addEntry(new com.android.server.power.WakeLockLog.LogEntry(j, i, this.mTagDatabase.findOrCreateTag(str, i2, true), i3));
        }
    }

    int translateFlagsFromPowerManager(int i) {
        int i2;
        switch (65535 & i) {
            case 1:
                i2 = 1;
                break;
            case 6:
                i2 = 3;
                break;
            case 10:
                i2 = 4;
                break;
            case 26:
                i2 = 2;
                break;
            case 32:
                i2 = 5;
                break;
            case 64:
                i2 = 6;
                break;
            case 128:
                i2 = 7;
                break;
            default:
                android.util.Slog.w(TAG, "Unsupported lock level for logging, flags: " + i);
                i2 = 0;
                break;
        }
        if ((268435456 & i) != 0) {
            i2 |= 16;
        }
        if ((536870912 & i) != 0) {
            i2 |= 8;
        }
        if ((i & Integer.MIN_VALUE) != 0) {
            return i2 | 32;
        }
        return i2;
    }

    private java.lang.String tagNameReducer(java.lang.String str) {
        java.lang.String str2 = null;
        if (str == null) {
            return null;
        }
        java.lang.String[] strArr = REDUCED_TAG_PREFIXES;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            java.lang.String str3 = strArr[i];
            if (!str.startsWith(str3)) {
                i++;
            } else {
                str2 = str3;
                break;
            }
        }
        if (str2 != null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append((java.lang.CharSequence) str, 0, str2.length());
            int max = java.lang.Math.max(str.lastIndexOf(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER), str.lastIndexOf("."));
            int length2 = sb.length();
            boolean z = true;
            while (length2 < max) {
                char charAt = str.charAt(length2);
                boolean z2 = charAt == '.' || charAt == '/';
                if (z2 || z) {
                    sb.append(charAt);
                }
                length2++;
                z = z2;
            }
            sb.append(str.substring(length2));
            return sb.toString();
        }
        return str;
    }

    static class LogEntry {
        public int flags;
        public java.lang.String packageName;
        public com.android.server.power.WakeLockLog.TagData tag;
        public long time;
        public int type;

        LogEntry() {
        }

        LogEntry(long j, int i, com.android.server.power.WakeLockLog.TagData tagData, int i2) {
            set(j, i, tagData, i2);
        }

        public void set(long j, int i, com.android.server.power.WakeLockLog.TagData tagData, int i2) {
            this.time = j;
            this.type = i;
            this.tag = tagData;
            this.flags = i2;
        }

        public void dump(java.io.PrintWriter printWriter, java.text.SimpleDateFormat simpleDateFormat) {
            printWriter.println("  " + toStringInternal(simpleDateFormat));
        }

        public java.lang.String toString() {
            return toStringInternal(com.android.server.power.WakeLockLog.DATE_FORMAT);
        }

        private java.lang.String toStringInternal(java.text.SimpleDateFormat simpleDateFormat) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if (this.type == 0) {
                return simpleDateFormat.format(new java.util.Date(this.time)) + " - RESET";
            }
            sb.append(simpleDateFormat.format(new java.util.Date(this.time)));
            sb.append(" - ");
            sb.append(this.tag == null ? "---" : java.lang.Integer.valueOf(this.tag.ownerUid));
            if (this.packageName != null) {
                sb.append(" (");
                sb.append(this.packageName);
                sb.append(")");
            }
            sb.append(" - ");
            sb.append(this.type == 1 ? "ACQ" : "REL");
            sb.append(" ");
            sb.append(this.tag == null ? "UNKNOWN" : this.tag.tag);
            if (this.type == 1) {
                sb.append(" (");
                flagsToString(sb);
                sb.append(")");
            }
            return sb.toString();
        }

        private void flagsToString(java.lang.StringBuilder sb) {
            sb.append(com.android.server.power.WakeLockLog.LEVEL_TO_STRING[this.flags & 7]);
            if ((this.flags & 8) == 8) {
                sb.append(",on-after-release");
            }
            if ((this.flags & 16) == 16) {
                sb.append(",acq-causes-wake");
            }
            if ((this.flags & 32) == 32) {
                sb.append(",system-wakelock");
            }
        }

        public void updatePackageName(android.util.SparseArray<java.lang.String[]> sparseArray, android.content.pm.PackageManager packageManager) {
            java.lang.String[] strArr;
            if (this.tag == null) {
                return;
            }
            if (sparseArray.contains(this.tag.ownerUid)) {
                strArr = sparseArray.get(this.tag.ownerUid);
            } else {
                java.lang.String[] packagesForUid = packageManager.getPackagesForUid(this.tag.ownerUid);
                sparseArray.put(this.tag.ownerUid, packagesForUid);
                strArr = packagesForUid;
            }
            if (strArr != null && strArr.length > 0) {
                this.packageName = strArr[0];
                if (strArr.length > 1) {
                    this.packageName += ",...";
                }
            }
        }
    }

    static class EntryByteTranslator {
        static final int ERROR_TIME_IS_NEGATIVE = -1;
        static final int ERROR_TIME_TOO_LARGE = -2;
        private final com.android.server.power.WakeLockLog.TagDatabase mTagDatabase;

        EntryByteTranslator(com.android.server.power.WakeLockLog.TagDatabase tagDatabase) {
            this.mTagDatabase = tagDatabase;
        }

        com.android.server.power.WakeLockLog.LogEntry fromBytes(byte[] bArr, long j, com.android.server.power.WakeLockLog.LogEntry logEntry) {
            if (bArr == null || bArr.length == 0) {
                return null;
            }
            if (logEntry == null) {
                logEntry = new com.android.server.power.WakeLockLog.LogEntry();
            }
            int i = (bArr[0] >> 6) & 3;
            if ((i & 2) == 2) {
                i = 2;
            }
            switch (i) {
                case 0:
                    if (bArr.length >= 9) {
                        logEntry.set(((bArr[1] & 255) << 56) | ((bArr[2] & 255) << 48) | ((bArr[3] & 255) << 40) | ((bArr[4] & 255) << 32) | ((bArr[5] & 255) << 24) | ((bArr[6] & 255) << 16) | ((bArr[7] & 255) << 8) | (255 & bArr[8]), 0, null, 0);
                        return logEntry;
                    }
                    return null;
                case 1:
                    if (bArr.length >= 3) {
                        int i2 = bArr[0] & 63;
                        logEntry.set((bArr[2] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) + j, 1, this.mTagDatabase.getTag(bArr[1] & Byte.MAX_VALUE), i2);
                        return logEntry;
                    }
                    return null;
                case 2:
                    if (bArr.length >= 2) {
                        logEntry.set((bArr[1] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) + j, 2, this.mTagDatabase.getTag(bArr[0] & Byte.MAX_VALUE), 0);
                        return logEntry;
                    }
                    return null;
                default:
                    android.util.Slog.w(com.android.server.power.WakeLockLog.TAG, "Type not recognized [" + i + "]", new java.lang.Exception());
                    return null;
            }
        }

        int toBytes(com.android.server.power.WakeLockLog.LogEntry logEntry, byte[] bArr, long j) {
            switch (logEntry.type) {
                case 0:
                    long j2 = logEntry.time;
                    if (bArr != null && bArr.length >= 9) {
                        bArr[0] = 0;
                        bArr[1] = (byte) ((j2 >> 56) & 255);
                        bArr[2] = (byte) ((j2 >> 48) & 255);
                        bArr[3] = (byte) ((j2 >> 40) & 255);
                        bArr[4] = (byte) ((j2 >> 32) & 255);
                        bArr[5] = (byte) ((j2 >> 24) & 255);
                        bArr[6] = (byte) ((j2 >> 16) & 255);
                        bArr[7] = (byte) ((j2 >> 8) & 255);
                        bArr[8] = (byte) (j2 & 255);
                    }
                    return 9;
                case 1:
                    if (bArr == null || bArr.length < 3) {
                        return 3;
                    }
                    int relativeTime = getRelativeTime(j, logEntry.time);
                    if (relativeTime < 0) {
                        return relativeTime;
                    }
                    bArr[0] = (byte) ((logEntry.flags & 63) | 64);
                    bArr[1] = (byte) this.mTagDatabase.getTagIndex(logEntry.tag);
                    bArr[2] = (byte) (relativeTime & 255);
                    return 3;
                case 2:
                    if (bArr != null && bArr.length >= 2) {
                        int relativeTime2 = getRelativeTime(j, logEntry.time);
                        if (relativeTime2 < 0) {
                            return relativeTime2;
                        }
                        bArr[0] = (byte) (this.mTagDatabase.getTagIndex(logEntry.tag) | 128);
                        bArr[1] = (byte) (relativeTime2 & 255);
                    }
                    return 2;
                default:
                    throw new java.lang.RuntimeException("Unknown type " + logEntry);
            }
        }

        private int getRelativeTime(long j, long j2) {
            if (j2 < j) {
                return -1;
            }
            long j3 = j2 - j;
            if (j3 > 255) {
                return -2;
            }
            return (int) j3;
        }
    }

    static class TheLog {
        private final byte[] mBuffer;
        private final java.util.List<com.android.server.power.WakeLockLog.LogEntry> mSavedAcquisitions;
        private final com.android.server.power.WakeLockLog.TagDatabase mTagDatabase;
        private final com.android.server.power.WakeLockLog.EntryByteTranslator mTranslator;
        private final byte[] mTempBuffer = new byte[9];
        private final byte[] mReadWriteTempBuffer = new byte[9];
        private int mStart = 0;
        private int mEnd = 0;
        private long mStartTime = 0;
        private long mLatestTime = 0;
        private long mChangeCount = 0;

        TheLog(com.android.server.power.WakeLockLog.Injector injector, com.android.server.power.WakeLockLog.EntryByteTranslator entryByteTranslator, com.android.server.power.WakeLockLog.TagDatabase tagDatabase) {
            this.mBuffer = new byte[java.lang.Math.max(injector.getLogSize(), 10)];
            this.mTranslator = entryByteTranslator;
            this.mTagDatabase = tagDatabase;
            this.mTagDatabase.setCallback(new com.android.server.power.WakeLockLog.TagDatabase.Callback() { // from class: com.android.server.power.WakeLockLog.TheLog.1
                @Override // com.android.server.power.WakeLockLog.TagDatabase.Callback
                public void onIndexRemoved(int i) {
                    com.android.server.power.WakeLockLog.TheLog.this.removeTagIndex(i);
                }
            });
            this.mSavedAcquisitions = new java.util.ArrayList();
        }

        int getUsedBufferSize() {
            return this.mBuffer.length - getAvailableSpace();
        }

        void addEntry(com.android.server.power.WakeLockLog.LogEntry logEntry) {
            if (isBufferEmpty()) {
                long j = logEntry.time;
                this.mLatestTime = j;
                this.mStartTime = j;
            }
            int bytes = this.mTranslator.toBytes(logEntry, this.mTempBuffer, this.mLatestTime);
            if (bytes == -1) {
                return;
            }
            if (bytes == -2) {
                addEntry(new com.android.server.power.WakeLockLog.LogEntry(logEntry.time, 0, null, 0));
                bytes = this.mTranslator.toBytes(logEntry, this.mTempBuffer, this.mLatestTime);
            }
            if (bytes > 9 || bytes <= 0) {
                android.util.Slog.w(com.android.server.power.WakeLockLog.TAG, "Log entry size is out of expected range: " + bytes);
                return;
            }
            if (!makeSpace(bytes)) {
                return;
            }
            writeBytesAt(this.mEnd, this.mTempBuffer, bytes);
            this.mEnd = (this.mEnd + bytes) % this.mBuffer.length;
            this.mLatestTime = logEntry.time;
            com.android.server.power.WakeLockLog.TagDatabase.updateTagTime(logEntry.tag, logEntry.time);
            this.mChangeCount++;
        }

        java.util.Iterator<com.android.server.power.WakeLockLog.LogEntry> getAllItems(final com.android.server.power.WakeLockLog.LogEntry logEntry) {
            return new java.util.Iterator<com.android.server.power.WakeLockLog.LogEntry>() { // from class: com.android.server.power.WakeLockLog.TheLog.2
                private final long mChangeValue;
                private int mCurrent;
                private long mCurrentTimeReference;

                {
                    this.mCurrent = com.android.server.power.WakeLockLog.TheLog.this.mStart;
                    this.mCurrentTimeReference = com.android.server.power.WakeLockLog.TheLog.this.mStartTime;
                    this.mChangeValue = com.android.server.power.WakeLockLog.TheLog.this.mChangeCount;
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    checkState();
                    return this.mCurrent != com.android.server.power.WakeLockLog.TheLog.this.mEnd;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.Iterator
                public com.android.server.power.WakeLockLog.LogEntry next() {
                    checkState();
                    if (!hasNext()) {
                        throw new java.util.NoSuchElementException("No more entries left.");
                    }
                    com.android.server.power.WakeLockLog.LogEntry readEntryAt = com.android.server.power.WakeLockLog.TheLog.this.readEntryAt(this.mCurrent, this.mCurrentTimeReference, logEntry);
                    this.mCurrent = (this.mCurrent + com.android.server.power.WakeLockLog.TheLog.this.mTranslator.toBytes(readEntryAt, null, com.android.server.power.WakeLockLog.TheLog.this.mStartTime)) % com.android.server.power.WakeLockLog.TheLog.this.mBuffer.length;
                    this.mCurrentTimeReference = readEntryAt.time;
                    return readEntryAt;
                }

                public java.lang.String toString() {
                    return "@" + this.mCurrent;
                }

                private void checkState() {
                    if (this.mChangeValue != com.android.server.power.WakeLockLog.TheLog.this.mChangeCount) {
                        throw new java.util.ConcurrentModificationException("Buffer modified, old change: " + this.mChangeValue + ", new change: " + com.android.server.power.WakeLockLog.TheLog.this.mChangeCount);
                    }
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeTagIndex(int i) {
            if (isBufferEmpty()) {
                return;
            }
            int i2 = this.mStart;
            long j = this.mStartTime;
            com.android.server.power.WakeLockLog.LogEntry logEntry = new com.android.server.power.WakeLockLog.LogEntry();
            while (i2 != this.mEnd) {
                com.android.server.power.WakeLockLog.LogEntry readEntryAt = readEntryAt(i2, j, logEntry);
                if (readEntryAt == null) {
                    android.util.Slog.w(com.android.server.power.WakeLockLog.TAG, "Entry is unreadable - Unexpected @ " + i2);
                    return;
                }
                if (readEntryAt.tag != null && readEntryAt.tag.index == i) {
                    readEntryAt.tag = null;
                    writeEntryAt(i2, readEntryAt, j);
                }
                j = readEntryAt.time;
                i2 = (i2 + this.mTranslator.toBytes(readEntryAt, null, 0L)) % this.mBuffer.length;
            }
        }

        private boolean makeSpace(int i) {
            int i2 = i + 1;
            if (this.mBuffer.length < i2) {
                return false;
            }
            while (getAvailableSpace() < i2) {
                removeOldestItem();
            }
            return true;
        }

        private int getAvailableSpace() {
            return this.mEnd > this.mStart ? this.mBuffer.length - (this.mEnd - this.mStart) : this.mEnd < this.mStart ? this.mStart - this.mEnd : this.mBuffer.length;
        }

        private void removeOldestItem() {
            if (isBufferEmpty()) {
                return;
            }
            com.android.server.power.WakeLockLog.LogEntry readEntryAt = readEntryAt(this.mStart, this.mStartTime, null);
            if (readEntryAt.type == 1) {
                this.mSavedAcquisitions.add(readEntryAt);
            } else if (readEntryAt.type == 2) {
                int i = 0;
                while (true) {
                    if (i >= this.mSavedAcquisitions.size()) {
                        break;
                    }
                    if (!java.util.Objects.equals(this.mSavedAcquisitions.get(i).tag, readEntryAt.tag)) {
                        i++;
                    } else {
                        this.mSavedAcquisitions.remove(i);
                        break;
                    }
                }
            }
            this.mStart = (this.mStart + this.mTranslator.toBytes(readEntryAt, null, this.mStartTime)) % this.mBuffer.length;
            this.mStartTime = readEntryAt.time;
            this.mChangeCount++;
        }

        private boolean isBufferEmpty() {
            return this.mStart == this.mEnd;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.power.WakeLockLog.LogEntry readEntryAt(int i, long j, com.android.server.power.WakeLockLog.LogEntry logEntry) {
            int length;
            for (int i2 = 0; i2 < 9 && (length = (i + i2) % this.mBuffer.length) != this.mEnd; i2++) {
                this.mReadWriteTempBuffer[i2] = this.mBuffer[length];
            }
            return this.mTranslator.fromBytes(this.mReadWriteTempBuffer, j, logEntry);
        }

        private void writeEntryAt(int i, com.android.server.power.WakeLockLog.LogEntry logEntry, long j) {
            int bytes = this.mTranslator.toBytes(logEntry, this.mReadWriteTempBuffer, j);
            if (bytes > 0) {
                writeBytesAt(i, this.mReadWriteTempBuffer, bytes);
            }
        }

        private void writeBytesAt(int i, byte[] bArr, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                this.mBuffer[(i + i3) % this.mBuffer.length] = bArr[i3];
            }
        }
    }

    static class TagDatabase {
        private final com.android.server.power.WakeLockLog.TagData[] mArray;
        private com.android.server.power.WakeLockLog.TagDatabase.Callback mCallback;
        private final int mInvalidIndex;

        interface Callback {
            void onIndexRemoved(int i);
        }

        TagDatabase(com.android.server.power.WakeLockLog.Injector injector) {
            int min = java.lang.Math.min(injector.getTagDatabaseSize(), 128) - 1;
            this.mArray = new com.android.server.power.WakeLockLog.TagData[min];
            this.mInvalidIndex = min;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Tag Database: size(");
            sb.append(this.mArray.length);
            sb.append(")");
            int i = 0;
            int i2 = 0;
            for (com.android.server.power.WakeLockLog.TagData tagData : this.mArray) {
                i2 += 8;
                if (tagData != null) {
                    i++;
                    i2 += tagData.getByteSize();
                    if (tagData.tag != null) {
                        tagData.tag.length();
                    }
                }
            }
            sb.append(", entries: ");
            sb.append(i);
            sb.append(", Bytes used: ");
            sb.append(i2);
            return sb.toString();
        }

        public void setCallback(com.android.server.power.WakeLockLog.TagDatabase.Callback callback) {
            this.mCallback = callback;
        }

        public com.android.server.power.WakeLockLog.TagData getTag(int i) {
            if (i < 0 || i >= this.mArray.length || i == this.mInvalidIndex) {
                return null;
            }
            return this.mArray[i];
        }

        public com.android.server.power.WakeLockLog.TagData getTag(java.lang.String str, int i) {
            return findOrCreateTag(str, i, false);
        }

        public int getTagIndex(com.android.server.power.WakeLockLog.TagData tagData) {
            return tagData == null ? this.mInvalidIndex : tagData.index;
        }

        public com.android.server.power.WakeLockLog.TagData findOrCreateTag(java.lang.String str, int i, boolean z) {
            com.android.server.power.WakeLockLog.TagData tagData = new com.android.server.power.WakeLockLog.TagData(str, i);
            int i2 = -1;
            int i3 = -1;
            com.android.server.power.WakeLockLog.TagData tagData2 = null;
            for (int i4 = 0; i4 < this.mArray.length; i4++) {
                com.android.server.power.WakeLockLog.TagData tagData3 = this.mArray[i4];
                if (tagData.equals(tagData3)) {
                    return tagData3;
                }
                if (z) {
                    if (tagData3 != null) {
                        if (tagData2 == null || tagData3.lastUsedTime < tagData2.lastUsedTime) {
                            i3 = i4;
                            tagData2 = tagData3;
                        }
                    } else if (i2 == -1) {
                        i2 = i4;
                    }
                }
            }
            if (!z) {
                return null;
            }
            if ((i2 == -1) && this.mCallback != null) {
                this.mCallback.onIndexRemoved(i3);
            }
            if (i2 == -1) {
                i2 = i3;
            }
            setToIndex(tagData, i2);
            return tagData;
        }

        public static void updateTagTime(com.android.server.power.WakeLockLog.TagData tagData, long j) {
            if (tagData != null) {
                tagData.lastUsedTime = j;
            }
        }

        private void setToIndex(com.android.server.power.WakeLockLog.TagData tagData, int i) {
            if (i < 0 || i >= this.mArray.length) {
                return;
            }
            com.android.server.power.WakeLockLog.TagData tagData2 = this.mArray[i];
            if (tagData2 != null) {
                tagData2.index = this.mInvalidIndex;
            }
            this.mArray[i] = tagData;
            tagData.index = i;
        }
    }

    static class TagData {
        public int index;
        public long lastUsedTime;
        public int ownerUid;
        public java.lang.String tag;

        TagData(java.lang.String str, int i) {
            this.tag = str;
            this.ownerUid = i;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.power.WakeLockLog.TagData)) {
                return false;
            }
            com.android.server.power.WakeLockLog.TagData tagData = (com.android.server.power.WakeLockLog.TagData) obj;
            return android.text.TextUtils.equals(this.tag, tagData.tag) && this.ownerUid == tagData.ownerUid;
        }

        public java.lang.String toString() {
            return "[" + this.ownerUid + " ; " + this.tag + "]";
        }

        int getByteSize() {
            return (this.tag == null ? 0 : this.tag.length() * 2) + 8 + 4 + 4 + 8;
        }
    }

    public static class Injector {
        public int getTagDatabaseSize() {
            return 128;
        }

        public int getLogSize() {
            return com.android.server.power.WakeLockLog.LOG_SIZE;
        }

        public long currentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }

        public java.text.SimpleDateFormat getDateFormat() {
            return com.android.server.power.WakeLockLog.DATE_FORMAT;
        }
    }
}
