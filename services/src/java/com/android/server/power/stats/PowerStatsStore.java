package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class PowerStatsStore {
    private static final java.lang.String DIR_LOCK_FILENAME = ".lock";
    private static final long MAX_POWER_STATS_SPAN_STORAGE_BYTES = 102400;
    private static final java.lang.String POWER_STATS_DIR = "power-stats";
    private static final java.lang.String POWER_STATS_SPAN_FILE_EXTENSION = ".pss";
    private static final java.lang.String TAG = "PowerStatsStore";
    private final java.util.concurrent.locks.ReentrantLock mFileLock;
    private final android.os.Handler mHandler;
    private java.nio.channels.FileLock mJvmLock;
    private final java.io.File mLockFile;
    private final long mMaxStorageBytes;
    private final com.android.server.power.stats.PowerStatsSpan.SectionReader mSectionReader;
    private final java.io.File mStoreDir;
    private final java.io.File mSystemDir;
    private volatile java.util.List<com.android.server.power.stats.PowerStatsSpan.Metadata> mTableOfContents;

    public PowerStatsStore(@android.annotation.NonNull java.io.File file, android.os.Handler handler, com.android.server.power.stats.AggregatedPowerStatsConfig aggregatedPowerStatsConfig) {
        this(file, MAX_POWER_STATS_SPAN_STORAGE_BYTES, handler, new com.android.server.power.stats.PowerStatsStore.DefaultSectionReader(aggregatedPowerStatsConfig));
    }

    @com.android.internal.annotations.VisibleForTesting
    public PowerStatsStore(@android.annotation.NonNull java.io.File file, long j, android.os.Handler handler, @android.annotation.NonNull com.android.server.power.stats.PowerStatsSpan.SectionReader sectionReader) {
        this.mFileLock = new java.util.concurrent.locks.ReentrantLock();
        this.mSystemDir = file;
        this.mStoreDir = new java.io.File(file, POWER_STATS_DIR);
        this.mLockFile = new java.io.File(this.mStoreDir, DIR_LOCK_FILENAME);
        this.mHandler = handler;
        this.mMaxStorageBytes = j;
        this.mSectionReader = sectionReader;
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.stats.PowerStatsStore$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.PowerStatsStore.this.maybeClearLegacyStore();
            }
        });
    }

    @android.annotation.NonNull
    public java.util.List<com.android.server.power.stats.PowerStatsSpan.Metadata> getTableOfContents() {
        java.util.List<com.android.server.power.stats.PowerStatsSpan.Metadata> list = this.mTableOfContents;
        if (list != null) {
            return list;
        }
        com.android.modules.utils.TypedXmlPullParser newBinaryPullParser = android.util.Xml.newBinaryPullParser();
        lockStoreDirectory();
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.io.File file : this.mStoreDir.listFiles()) {
                java.lang.String name = file.getName();
                if (name.endsWith(POWER_STATS_SPAN_FILE_EXTENSION)) {
                    try {
                        java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
                        try {
                            newBinaryPullParser.setInput(bufferedInputStream, java.nio.charset.StandardCharsets.UTF_8.name());
                            com.android.server.power.stats.PowerStatsSpan.Metadata read = com.android.server.power.stats.PowerStatsSpan.Metadata.read(newBinaryPullParser);
                            if (read == null) {
                                android.util.Slog.e(TAG, "Removing incompatible PowerStatsSpan file: " + name);
                                file.delete();
                            } else {
                                arrayList.add(read);
                            }
                            bufferedInputStream.close();
                        } catch (java.lang.Throwable th) {
                            try {
                                bufferedInputStream.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                        android.util.Slog.wtf(TAG, "Cannot read PowerStatsSpan file: " + name);
                    }
                }
            }
            arrayList.sort(com.android.server.power.stats.PowerStatsSpan.Metadata.COMPARATOR);
            this.mTableOfContents = java.util.Collections.unmodifiableList(arrayList);
            return arrayList;
        } finally {
            unlockStoreDirectory();
        }
    }

    public void storePowerStatsSpan(final com.android.server.power.stats.PowerStatsSpan powerStatsSpan) {
        maybeClearLegacyStore();
        lockStoreDirectory();
        try {
            if (!this.mStoreDir.exists() && !this.mStoreDir.mkdirs()) {
                android.util.Slog.e(TAG, "Could not create a directory for power stats store");
                return;
            }
            new android.util.AtomicFile(makePowerStatsSpanFilename(powerStatsSpan.getId())).write(new java.util.function.Consumer() { // from class: com.android.server.power.stats.PowerStatsStore$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.power.stats.PowerStatsStore.lambda$storePowerStatsSpan$0(com.android.server.power.stats.PowerStatsSpan.this, (java.io.FileOutputStream) obj);
                }
            });
            this.mTableOfContents = null;
            removeOldSpansLocked();
        } finally {
            unlockStoreDirectory();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$storePowerStatsSpan$0(com.android.server.power.stats.PowerStatsSpan powerStatsSpan, java.io.FileOutputStream fileOutputStream) {
        try {
            powerStatsSpan.writeXml(fileOutputStream, android.util.Xml.newBinarySerializer());
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.Nullable
    public com.android.server.power.stats.PowerStatsSpan loadPowerStatsSpan(long j, java.lang.String... strArr) {
        com.android.modules.utils.TypedXmlPullParser newBinaryPullParser = android.util.Xml.newBinaryPullParser();
        lockStoreDirectory();
        try {
            java.io.File makePowerStatsSpanFilename = makePowerStatsSpanFilename(j);
            try {
                java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(makePowerStatsSpanFilename));
                try {
                    com.android.server.power.stats.PowerStatsSpan read = com.android.server.power.stats.PowerStatsSpan.read(bufferedInputStream, newBinaryPullParser, this.mSectionReader, strArr);
                    bufferedInputStream.close();
                    return read;
                } catch (java.lang.Throwable th) {
                    try {
                        bufferedInputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.wtf(TAG, "Cannot read PowerStatsSpan file: " + makePowerStatsSpanFilename, e);
                unlockStoreDirectory();
                return null;
            }
        } finally {
            unlockStoreDirectory();
        }
    }

    void storeAggregatedPowerStats(com.android.server.power.stats.AggregatedPowerStats aggregatedPowerStats) {
        com.android.server.power.stats.PowerStatsSpan createPowerStatsSpan = createPowerStatsSpan(aggregatedPowerStats);
        if (createPowerStatsSpan == null) {
            return;
        }
        storePowerStatsSpan(createPowerStatsSpan);
    }

    static com.android.server.power.stats.PowerStatsSpan createPowerStatsSpan(com.android.server.power.stats.AggregatedPowerStats aggregatedPowerStats) {
        long j;
        java.util.List<com.android.server.power.stats.AggregatedPowerStats.ClockUpdate> clockUpdates = aggregatedPowerStats.getClockUpdates();
        if (clockUpdates.isEmpty()) {
            android.util.Slog.w(TAG, "No clock updates in aggregated power stats " + aggregatedPowerStats);
            return null;
        }
        int i = 0;
        long j2 = clockUpdates.get(0).monotonicTime;
        com.android.server.power.stats.PowerStatsSpan powerStatsSpan = new com.android.server.power.stats.PowerStatsSpan(j2);
        long j3 = 0;
        while (i < clockUpdates.size()) {
            com.android.server.power.stats.AggregatedPowerStats.ClockUpdate clockUpdate = clockUpdates.get(i);
            if (i == clockUpdates.size() - 1) {
                j = aggregatedPowerStats.getDuration() - j3;
            } else {
                j = clockUpdate.monotonicTime - j2;
            }
            powerStatsSpan.addTimeFrame(clockUpdate.monotonicTime, clockUpdate.currentTime, j);
            j3 += j;
            i++;
            j2 = clockUpdate.monotonicTime;
        }
        powerStatsSpan.addSection(new com.android.server.power.stats.AggregatedPowerStatsSection(aggregatedPowerStats));
        return powerStatsSpan;
    }

    public void storeBatteryUsageStats(long j, android.os.BatteryUsageStats batteryUsageStats) {
        com.android.server.power.stats.PowerStatsSpan powerStatsSpan = new com.android.server.power.stats.PowerStatsSpan(j);
        powerStatsSpan.addTimeFrame(j, batteryUsageStats.getStatsStartTimestamp(), batteryUsageStats.getStatsDuration());
        powerStatsSpan.addSection(new com.android.server.power.stats.BatteryUsageStatsSection(batteryUsageStats));
        storePowerStatsSpan(powerStatsSpan);
    }

    private java.io.File makePowerStatsSpanFilename(long j) {
        return new java.io.File(this.mStoreDir, java.lang.String.format(java.util.Locale.ENGLISH, "%019d", java.lang.Long.valueOf(j)) + POWER_STATS_SPAN_FILE_EXTENSION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeClearLegacyStore() {
        java.io.File file = new java.io.File(this.mSystemDir, com.android.server.power.stats.BatteryUsageStatsSection.TYPE);
        if (file.exists()) {
            android.os.FileUtils.deleteContentsAndDir(file);
        }
    }

    private void lockStoreDirectory() {
        this.mFileLock.lock();
        try {
            this.mLockFile.getParentFile().mkdirs();
            this.mLockFile.createNewFile();
            this.mJvmLock = java.nio.channels.FileChannel.open(this.mLockFile.toPath(), java.nio.file.StandardOpenOption.WRITE).lock();
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Cannot lock snapshot directory", e);
        }
    }

    private void unlockStoreDirectory() {
        try {
            try {
                this.mJvmLock.close();
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Cannot unlock snapshot directory", e);
            }
        } finally {
            this.mFileLock.unlock();
        }
    }

    private void removeOldSpansLocked() {
        java.util.Map.Entry firstEntry;
        java.util.TreeMap treeMap = new java.util.TreeMap();
        long j = 0;
        for (java.io.File file : this.mStoreDir.listFiles()) {
            long length = file.length();
            j += length;
            if (file.getName().endsWith(POWER_STATS_SPAN_FILE_EXTENSION)) {
                treeMap.put(file, java.lang.Long.valueOf(length));
            }
        }
        while (j > this.mMaxStorageBytes && (firstEntry = treeMap.firstEntry()) != null) {
            java.io.File file2 = (java.io.File) firstEntry.getKey();
            if (!file2.delete()) {
                android.util.Slog.e(TAG, "Cannot delete power stats span " + file2);
            }
            j -= ((java.lang.Long) firstEntry.getValue()).longValue();
            treeMap.remove(file2);
            this.mTableOfContents = null;
        }
    }

    public void reset() {
        lockStoreDirectory();
        try {
            for (java.io.File file : this.mStoreDir.listFiles()) {
                if (file.getName().endsWith(POWER_STATS_SPAN_FILE_EXTENSION) && !file.delete()) {
                    android.util.Slog.e(TAG, "Cannot delete power stats span " + file);
                }
            }
            this.mTableOfContents = java.util.List.of();
            unlockStoreDirectory();
        } catch (java.lang.Throwable th) {
            unlockStoreDirectory();
            throw th;
        }
    }

    public void dumpTableOfContents(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Power stats store TOC");
        indentingPrintWriter.increaseIndent();
        java.util.Iterator<com.android.server.power.stats.PowerStatsSpan.Metadata> it = getTableOfContents().iterator();
        while (it.hasNext()) {
            it.next().dump(indentingPrintWriter);
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Power stats store");
        indentingPrintWriter.increaseIndent();
        java.util.Iterator<com.android.server.power.stats.PowerStatsSpan.Metadata> it = getTableOfContents().iterator();
        while (it.hasNext()) {
            com.android.server.power.stats.PowerStatsSpan loadPowerStatsSpan = loadPowerStatsSpan(it.next().getId(), new java.lang.String[0]);
            if (loadPowerStatsSpan != null) {
                loadPowerStatsSpan.dump(indentingPrintWriter);
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    private static class DefaultSectionReader implements com.android.server.power.stats.PowerStatsSpan.SectionReader {
        private final com.android.server.power.stats.AggregatedPowerStatsConfig mAggregatedPowerStatsConfig;

        DefaultSectionReader(com.android.server.power.stats.AggregatedPowerStatsConfig aggregatedPowerStatsConfig) {
            this.mAggregatedPowerStatsConfig = aggregatedPowerStatsConfig;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // com.android.server.power.stats.PowerStatsSpan.SectionReader
        public com.android.server.power.stats.PowerStatsSpan.Section read(java.lang.String str, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            char c;
            switch (str.hashCode()) {
                case 222490035:
                    if (str.equals(com.android.server.power.stats.BatteryUsageStatsSection.TYPE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 2132539023:
                    if (str.equals(com.android.server.power.stats.AggregatedPowerStatsSection.TYPE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return new com.android.server.power.stats.AggregatedPowerStatsSection(com.android.server.power.stats.AggregatedPowerStats.createFromXml(typedXmlPullParser, this.mAggregatedPowerStatsConfig));
                case 1:
                    return new com.android.server.power.stats.BatteryUsageStatsSection(android.os.BatteryUsageStats.createFromXml(typedXmlPullParser));
                default:
                    return null;
            }
        }
    }
}
