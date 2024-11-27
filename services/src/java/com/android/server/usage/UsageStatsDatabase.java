package com.android.server.usage;

/* loaded from: classes2.dex */
public class UsageStatsDatabase {

    @com.android.internal.annotations.VisibleForTesting
    public static final int BACKUP_VERSION = 4;
    private static final java.lang.String BAK_SUFFIX = ".bak";
    private static final java.lang.String CHECKED_IN_SUFFIX = "-c";
    private static final boolean DEBUG = false;
    private static final int DEFAULT_CURRENT_VERSION = 5;
    static final boolean KEEP_BACKUP_DIR = false;
    static final java.lang.String KEY_USAGE_STATS = "usage_stats";

    @com.android.internal.annotations.VisibleForTesting
    static final int[] MAX_FILES_PER_INTERVAL_TYPE = {100, 50, 12, 10};
    private static final java.lang.String RETENTION_LEN_KEY = "ro.usagestats.chooser.retention";
    private static final int SELECTION_LOG_RETENTION_LEN = android.os.SystemProperties.getInt(RETENTION_LEN_KEY, 14);
    private static final java.lang.String TAG = "UsageStatsDatabase";
    private final java.io.File mBackupsDir;
    private final com.android.server.usage.UnixCalendar mCal;
    private int mCurrentVersion;
    private boolean mFirstUpdate;
    private final java.io.File[] mIntervalDirs;
    private final java.lang.Object mLock;
    private boolean mNewUpdate;
    private final java.io.File mPackageMappingsFile;
    final com.android.server.usage.PackagesTokenData mPackagesTokenData;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    final android.util.LongSparseArray<android.util.AtomicFile>[] mSortedStatFiles;
    private final java.io.File mUpdateBreadcrumb;
    private boolean mUpgradePerformed;
    private final java.io.File mVersionFile;

    public interface CheckinAction {
        boolean checkin(com.android.server.usage.IntervalStats intervalStats);
    }

    public interface StatCombiner<T> {
        boolean combine(com.android.server.usage.IntervalStats intervalStats, boolean z, java.util.List<T> list);
    }

    @com.android.internal.annotations.VisibleForTesting
    public UsageStatsDatabase(java.io.File file, int i) {
        this.mLock = new java.lang.Object();
        this.mPackagesTokenData = new com.android.server.usage.PackagesTokenData();
        this.mIntervalDirs = new java.io.File[]{new java.io.File(file, "daily"), new java.io.File(file, "weekly"), new java.io.File(file, "monthly"), new java.io.File(file, "yearly")};
        this.mCurrentVersion = i;
        this.mVersionFile = new java.io.File(file, "version");
        this.mBackupsDir = new java.io.File(file, "backups");
        this.mUpdateBreadcrumb = new java.io.File(file, "breadcrumb");
        this.mSortedStatFiles = new android.util.LongSparseArray[this.mIntervalDirs.length];
        this.mPackageMappingsFile = new java.io.File(file, "mappings");
        this.mCal = new com.android.server.usage.UnixCalendar(0L);
    }

    public UsageStatsDatabase(java.io.File file) {
        this(file, 5);
    }

    public void init(long j) {
        synchronized (this.mLock) {
            try {
                for (java.io.File file : this.mIntervalDirs) {
                    file.mkdirs();
                    if (!file.exists()) {
                        throw new java.lang.IllegalStateException("Failed to create directory " + file.getAbsolutePath());
                    }
                }
                checkVersionAndBuildLocked();
                if (this.mUpgradePerformed) {
                    prune(j);
                } else {
                    indexFilesLocked();
                }
                for (android.util.LongSparseArray<android.util.AtomicFile> longSparseArray : this.mSortedStatFiles) {
                    int firstIndexOnOrAfter = longSparseArray.firstIndexOnOrAfter(j);
                    if (firstIndexOnOrAfter >= 0) {
                        int size = longSparseArray.size();
                        for (int i = firstIndexOnOrAfter; i < size; i++) {
                            longSparseArray.valueAt(i).delete();
                        }
                        while (firstIndexOnOrAfter < size) {
                            longSparseArray.removeAt(firstIndexOnOrAfter);
                            firstIndexOnOrAfter++;
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean checkinDailyFiles(com.android.server.usage.UsageStatsDatabase.CheckinAction checkinAction) {
        int i;
        synchronized (this.mLock) {
            try {
                android.util.LongSparseArray<android.util.AtomicFile> longSparseArray = this.mSortedStatFiles[0];
                int size = longSparseArray.size();
                int i2 = -1;
                int i3 = 0;
                while (true) {
                    i = size - 1;
                    if (i3 >= i) {
                        break;
                    }
                    if (longSparseArray.valueAt(i3).getBaseFile().getPath().endsWith(CHECKED_IN_SUFFIX)) {
                        i2 = i3;
                    }
                    i3++;
                }
                int i4 = i2 + 1;
                if (i4 == i) {
                    return true;
                }
                for (int i5 = i4; i5 < i; i5++) {
                    try {
                        com.android.server.usage.IntervalStats intervalStats = new com.android.server.usage.IntervalStats();
                        readLocked(longSparseArray.valueAt(i5), intervalStats, false);
                        if (!checkinAction.checkin(intervalStats)) {
                            return false;
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(TAG, "Failed to check-in", e);
                        return false;
                    }
                }
                while (i4 < i) {
                    android.util.AtomicFile valueAt = longSparseArray.valueAt(i4);
                    java.io.File file = new java.io.File(valueAt.getBaseFile().getPath() + CHECKED_IN_SUFFIX);
                    if (!valueAt.getBaseFile().renameTo(file)) {
                        android.util.Slog.e(TAG, "Failed to mark file " + valueAt.getBaseFile().getPath() + " as checked-in");
                        return true;
                    }
                    longSparseArray.setValueAt(i4, new android.util.AtomicFile(file));
                    i4++;
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void forceIndexFiles() {
        synchronized (this.mLock) {
            indexFilesLocked();
        }
    }

    private void indexFilesLocked() {
        java.io.FilenameFilter filenameFilter = new java.io.FilenameFilter() { // from class: com.android.server.usage.UsageStatsDatabase.1
            @Override // java.io.FilenameFilter
            public boolean accept(java.io.File file, java.lang.String str) {
                return !str.endsWith(com.android.server.usage.UsageStatsDatabase.BAK_SUFFIX);
            }
        };
        for (int i = 0; i < this.mSortedStatFiles.length; i++) {
            if (this.mSortedStatFiles[i] == null) {
                this.mSortedStatFiles[i] = new android.util.LongSparseArray<>();
            } else {
                this.mSortedStatFiles[i].clear();
            }
            java.io.File[] listFiles = this.mIntervalDirs[i].listFiles(filenameFilter);
            if (listFiles != null) {
                for (java.io.File file : listFiles) {
                    android.util.AtomicFile atomicFile = new android.util.AtomicFile(file);
                    try {
                        this.mSortedStatFiles[i].put(parseBeginTime(atomicFile), atomicFile);
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "failed to index file: " + file, e);
                    }
                }
                int size = this.mSortedStatFiles[i].size() - MAX_FILES_PER_INTERVAL_TYPE[i];
                if (size > 0) {
                    for (int i2 = 0; i2 < size; i2++) {
                        this.mSortedStatFiles[i].valueAt(0).delete();
                        this.mSortedStatFiles[i].removeAt(0);
                    }
                    android.util.Slog.d(TAG, "Deleted " + size + " stat files for interval " + i);
                }
            }
        }
    }

    boolean isFirstUpdate() {
        return this.mFirstUpdate;
    }

    boolean isNewUpdate() {
        return this.mNewUpdate;
    }

    boolean wasUpgradePerformed() {
        return this.mUpgradePerformed;
    }

    private void checkVersionAndBuildLocked() {
        java.lang.String buildFingerprint = getBuildFingerprint();
        this.mFirstUpdate = true;
        this.mNewUpdate = true;
        int i = 0;
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(this.mVersionFile));
            try {
                int parseInt = java.lang.Integer.parseInt(bufferedReader.readLine());
                java.lang.String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    this.mFirstUpdate = false;
                }
                if (buildFingerprint.equals(readLine)) {
                    this.mNewUpdate = false;
                }
                bufferedReader.close();
                i = parseInt;
            } catch (java.lang.Throwable th) {
                try {
                    bufferedReader.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | java.lang.NumberFormatException e) {
        }
        if (i != this.mCurrentVersion) {
            android.util.Slog.i(TAG, "Upgrading from version " + i + " to " + this.mCurrentVersion);
            if (!this.mUpdateBreadcrumb.exists()) {
                try {
                    doUpgradeLocked(i);
                } catch (java.lang.Exception e2) {
                    android.util.Slog.e(TAG, "Failed to upgrade from version " + i + " to " + this.mCurrentVersion, e2);
                    this.mCurrentVersion = i;
                    return;
                }
            } else {
                android.util.Slog.i(TAG, "Version upgrade breadcrumb found on disk! Continuing version upgrade");
            }
        }
        if (this.mUpdateBreadcrumb.exists()) {
            try {
                java.io.BufferedReader bufferedReader2 = new java.io.BufferedReader(new java.io.FileReader(this.mUpdateBreadcrumb));
                try {
                    long parseLong = java.lang.Long.parseLong(bufferedReader2.readLine());
                    int parseInt2 = java.lang.Integer.parseInt(bufferedReader2.readLine());
                    bufferedReader2.close();
                    if (this.mCurrentVersion >= 4) {
                        continueUpgradeLocked(parseInt2, parseLong);
                    } else {
                        android.util.Slog.wtf(TAG, "Attempting to upgrade to an unsupported version: " + this.mCurrentVersion);
                    }
                } catch (java.lang.Throwable th3) {
                    try {
                        bufferedReader2.close();
                    } catch (java.lang.Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                    throw th3;
                }
            } catch (java.io.IOException | java.lang.NumberFormatException e3) {
                android.util.Slog.e(TAG, "Failed read version upgrade breadcrumb");
                throw new java.lang.RuntimeException(e3);
            }
        }
        if (i != this.mCurrentVersion || this.mNewUpdate) {
            try {
                java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(this.mVersionFile));
                try {
                    bufferedWriter.write(java.lang.Integer.toString(this.mCurrentVersion));
                    bufferedWriter.write("\n");
                    bufferedWriter.write(buildFingerprint);
                    bufferedWriter.write("\n");
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } finally {
                }
            } catch (java.io.IOException e4) {
                android.util.Slog.e(TAG, "Failed to write new version");
                throw new java.lang.RuntimeException(e4);
            }
        }
        if (this.mUpdateBreadcrumb.exists()) {
            this.mUpdateBreadcrumb.delete();
            this.mUpgradePerformed = true;
        }
        if (this.mBackupsDir.exists()) {
            this.mUpgradePerformed = true;
            deleteDirectory(this.mBackupsDir);
        }
    }

    private java.lang.String getBuildFingerprint() {
        return android.os.Build.VERSION.RELEASE + ";" + android.os.Build.VERSION.CODENAME + ";" + android.os.Build.VERSION.INCREMENTAL;
    }

    private void doUpgradeLocked(int i) {
        if (i < 2) {
            android.util.Slog.i(TAG, "Deleting all usage stats files");
            for (int i2 = 0; i2 < this.mIntervalDirs.length; i2++) {
                java.io.File[] listFiles = this.mIntervalDirs[i2].listFiles();
                if (listFiles != null) {
                    for (java.io.File file : listFiles) {
                        file.delete();
                    }
                }
            }
            return;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.io.File file2 = new java.io.File(this.mBackupsDir, java.lang.Long.toString(currentTimeMillis));
        file2.mkdirs();
        if (!file2.exists()) {
            throw new java.lang.IllegalStateException("Failed to create backup directory " + file2.getAbsolutePath());
        }
        try {
            java.nio.file.Files.copy(this.mVersionFile.toPath(), new java.io.File(file2, this.mVersionFile.getName()).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            for (int i3 = 0; i3 < this.mIntervalDirs.length; i3++) {
                java.io.File file3 = new java.io.File(file2, this.mIntervalDirs[i3].getName());
                file3.mkdir();
                if (!file3.exists()) {
                    throw new java.lang.IllegalStateException("Failed to create interval backup directory " + file3.getAbsolutePath());
                }
                java.io.File[] listFiles2 = this.mIntervalDirs[i3].listFiles();
                if (listFiles2 != null) {
                    for (int i4 = 0; i4 < listFiles2.length; i4++) {
                        try {
                            java.nio.file.Files.move(listFiles2[i4].toPath(), new java.io.File(file3, listFiles2[i4].getName()).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        } catch (java.io.IOException e) {
                            android.util.Slog.e(TAG, "Failed to back up file : " + listFiles2[i4].toString());
                            throw new java.lang.RuntimeException(e);
                        }
                    }
                }
            }
            java.io.BufferedWriter bufferedWriter = null;
            try {
                try {
                    java.io.BufferedWriter bufferedWriter2 = new java.io.BufferedWriter(new java.io.FileWriter(this.mUpdateBreadcrumb));
                    try {
                        bufferedWriter2.write(java.lang.Long.toString(currentTimeMillis));
                        bufferedWriter2.write("\n");
                        bufferedWriter2.write(java.lang.Integer.toString(i));
                        bufferedWriter2.write("\n");
                        bufferedWriter2.flush();
                        libcore.io.IoUtils.closeQuietly(bufferedWriter2);
                    } catch (java.io.IOException e2) {
                        e = e2;
                        android.util.Slog.e(TAG, "Failed to write new version upgrade breadcrumb");
                        throw new java.lang.RuntimeException(e);
                    } catch (java.lang.Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        libcore.io.IoUtils.closeQuietly(bufferedWriter);
                        throw th;
                    }
                } catch (java.io.IOException e3) {
                    e = e3;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.io.IOException e4) {
            android.util.Slog.e(TAG, "Failed to back up version file : " + this.mVersionFile.toString());
            throw new java.lang.RuntimeException(e4);
        }
    }

    private void continueUpgradeLocked(int i, long j) {
        if (i <= 3) {
            android.util.Slog.w(TAG, "Reading UsageStats as XML; current database version: " + this.mCurrentVersion);
        }
        java.io.File file = new java.io.File(this.mBackupsDir, java.lang.Long.toString(j));
        if (i >= 5) {
            readMappingsLocked();
        }
        for (int i2 = 0; i2 < this.mIntervalDirs.length; i2++) {
            java.io.File[] listFiles = new java.io.File(file, this.mIntervalDirs[i2].getName()).listFiles();
            if (listFiles != null) {
                for (int i3 = 0; i3 < listFiles.length; i3++) {
                    try {
                        com.android.server.usage.IntervalStats intervalStats = new com.android.server.usage.IntervalStats();
                        readLocked(new android.util.AtomicFile(listFiles[i3]), intervalStats, i, this.mPackagesTokenData, false);
                        if (this.mCurrentVersion >= 5) {
                            intervalStats.obfuscateData(this.mPackagesTokenData);
                        }
                        writeLocked(new android.util.AtomicFile(new java.io.File(this.mIntervalDirs[i2], java.lang.Long.toString(intervalStats.beginTime))), intervalStats, this.mCurrentVersion, this.mPackagesTokenData);
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(TAG, "Failed to upgrade backup file : " + listFiles[i3].toString());
                    }
                }
            }
        }
        if (this.mCurrentVersion >= 5) {
            try {
                writeMappingsLocked();
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "Failed to write the tokens mappings file.");
            }
        }
    }

    int onPackageRemoved(java.lang.String str, long j) {
        int removePackage;
        synchronized (this.mLock) {
            removePackage = this.mPackagesTokenData.removePackage(str, j);
            try {
                writeMappingsLocked();
            } catch (java.lang.Exception e) {
                android.util.Slog.w(TAG, "Unable to update package mappings on disk after removing token " + removePackage);
            }
        }
        return removePackage;
    }

    boolean pruneUninstalledPackagesData() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mIntervalDirs.length; i++) {
                java.io.File[] listFiles = this.mIntervalDirs[i].listFiles();
                if (listFiles != null) {
                    for (int i2 = 0; i2 < listFiles.length; i2++) {
                        try {
                            com.android.server.usage.IntervalStats intervalStats = new com.android.server.usage.IntervalStats();
                            android.util.AtomicFile atomicFile = new android.util.AtomicFile(listFiles[i2]);
                            if (readLocked(atomicFile, intervalStats, this.mCurrentVersion, this.mPackagesTokenData, false)) {
                                writeLocked(atomicFile, intervalStats, this.mCurrentVersion, this.mPackagesTokenData);
                            }
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(TAG, "Failed to prune data from: " + listFiles[i2].toString());
                            return false;
                        }
                    }
                }
            }
            try {
                writeMappingsLocked();
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "Failed to write package mappings after pruning data.");
                return false;
            }
        }
        return true;
    }

    void prunePackagesDataOnUpgrade(java.util.HashMap<java.lang.String, java.lang.Long> hashMap) {
        if (com.android.internal.util.ArrayUtils.isEmpty(hashMap)) {
            return;
        }
        synchronized (this.mLock) {
            for (int i = 0; i < this.mIntervalDirs.length; i++) {
                java.io.File[] listFiles = this.mIntervalDirs[i].listFiles();
                if (listFiles != null) {
                    for (int i2 = 0; i2 < listFiles.length; i2++) {
                        try {
                            com.android.server.usage.IntervalStats intervalStats = new com.android.server.usage.IntervalStats();
                            android.util.AtomicFile atomicFile = new android.util.AtomicFile(listFiles[i2]);
                            readLocked(atomicFile, intervalStats, this.mCurrentVersion, this.mPackagesTokenData, false);
                            if (pruneStats(hashMap, intervalStats)) {
                                writeLocked(atomicFile, intervalStats, this.mCurrentVersion, this.mPackagesTokenData);
                            }
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(TAG, "Failed to prune data from: " + listFiles[i2].toString());
                        }
                    }
                }
            }
        }
    }

    private boolean pruneStats(java.util.HashMap<java.lang.String, java.lang.Long> hashMap, com.android.server.usage.IntervalStats intervalStats) {
        boolean z = false;
        for (int size = intervalStats.packageStats.size() - 1; size >= 0; size--) {
            android.app.usage.UsageStats valueAt = intervalStats.packageStats.valueAt(size);
            java.lang.Long l = hashMap.get(valueAt.mPackageName);
            if (l == null || l.longValue() > valueAt.mEndTimeStamp) {
                intervalStats.packageStats.removeAt(size);
                z = true;
            }
        }
        if (z) {
            intervalStats.packageStatsObfuscated.clear();
        }
        for (int size2 = intervalStats.events.size() - 1; size2 >= 0; size2--) {
            android.app.usage.UsageEvents.Event event = intervalStats.events.get(size2);
            java.lang.Long l2 = hashMap.get(event.mPackage);
            if (l2 == null || l2.longValue() > event.mTimeStamp) {
                intervalStats.events.remove(size2);
                z = true;
            }
        }
        return z;
    }

    public void onTimeChanged(long j) {
        synchronized (this.mLock) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Time changed by ");
            android.util.TimeUtils.formatDuration(j, sb);
            sb.append(".");
            android.util.LongSparseArray<android.util.AtomicFile>[] longSparseArrayArr = this.mSortedStatFiles;
            int length = longSparseArrayArr.length;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                android.util.LongSparseArray<android.util.AtomicFile> longSparseArray = longSparseArrayArr[i2];
                int size = longSparseArray.size();
                int i4 = i3;
                int i5 = i;
                for (int i6 = 0; i6 < size; i6++) {
                    android.util.AtomicFile valueAt = longSparseArray.valueAt(i6);
                    long keyAt = longSparseArray.keyAt(i6) + j;
                    if (keyAt < 0) {
                        i5++;
                        valueAt.delete();
                    } else {
                        try {
                            valueAt.openRead().close();
                        } catch (java.io.IOException e) {
                        }
                        java.lang.String l = java.lang.Long.toString(keyAt);
                        if (valueAt.getBaseFile().getName().endsWith(CHECKED_IN_SUFFIX)) {
                            l = l + CHECKED_IN_SUFFIX;
                        }
                        i4++;
                        valueAt.getBaseFile().renameTo(new java.io.File(valueAt.getBaseFile().getParentFile(), l));
                    }
                }
                longSparseArray.clear();
                i2++;
                i = i5;
                i3 = i4;
            }
            sb.append(" files deleted: ");
            sb.append(i);
            sb.append(" files moved: ");
            sb.append(i3);
            android.util.Slog.i(TAG, sb.toString());
            indexFilesLocked();
        }
    }

    public com.android.server.usage.IntervalStats getLatestUsageStats(int i) {
        synchronized (this.mLock) {
            if (i >= 0) {
                if (i < this.mIntervalDirs.length) {
                    int size = this.mSortedStatFiles[i].size();
                    if (size == 0) {
                        return null;
                    }
                    try {
                        android.util.AtomicFile valueAt = this.mSortedStatFiles[i].valueAt(size - 1);
                        com.android.server.usage.IntervalStats intervalStats = new com.android.server.usage.IntervalStats();
                        readLocked(valueAt, intervalStats, false);
                        return intervalStats;
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(TAG, "Failed to read usage stats file", e);
                        return null;
                    }
                }
            }
            throw new java.lang.IllegalArgumentException("Bad interval type " + i);
        }
    }

    void filterStats(com.android.server.usage.IntervalStats intervalStats) {
        if (this.mPackagesTokenData.removedPackagesMap.isEmpty()) {
            return;
        }
        android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap = this.mPackagesTokenData.removedPackagesMap;
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            java.lang.String keyAt = arrayMap.keyAt(i);
            android.app.usage.UsageStats usageStats = intervalStats.packageStats.get(keyAt);
            if (usageStats != null && usageStats.mEndTimeStamp < arrayMap.valueAt(i).longValue()) {
                intervalStats.packageStats.remove(keyAt);
            }
        }
        for (int size2 = intervalStats.events.size() - 1; size2 >= 0; size2--) {
            android.app.usage.UsageEvents.Event event = intervalStats.events.get(size2);
            java.lang.Long l = arrayMap.get(event.mPackage);
            if (l != null && l.longValue() > event.mTimeStamp) {
                intervalStats.events.remove(size2);
            }
        }
    }

    @android.annotation.Nullable
    public <T> java.util.List<T> queryUsageStats(int i, long j, long j2, com.android.server.usage.UsageStatsDatabase.StatCombiner<T> statCombiner, boolean z) {
        if (i < 0 || i >= this.mIntervalDirs.length) {
            throw new java.lang.IllegalArgumentException("Bad interval type " + i);
        }
        if (j2 <= j) {
            return null;
        }
        synchronized (this.mLock) {
            try {
                android.util.LongSparseArray<android.util.AtomicFile> longSparseArray = this.mSortedStatFiles[i];
                int lastIndexOnOrBefore = longSparseArray.lastIndexOnOrBefore(j2);
                if (lastIndexOnOrBefore < 0) {
                    return null;
                }
                if (longSparseArray.keyAt(lastIndexOnOrBefore) == j2 && lastIndexOnOrBefore - 1 < 0) {
                    return null;
                }
                int lastIndexOnOrBefore2 = longSparseArray.lastIndexOnOrBefore(j);
                if (lastIndexOnOrBefore2 < 0) {
                    lastIndexOnOrBefore2 = 0;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                while (lastIndexOnOrBefore2 <= lastIndexOnOrBefore) {
                    android.util.AtomicFile valueAt = longSparseArray.valueAt(lastIndexOnOrBefore2);
                    com.android.server.usage.IntervalStats intervalStats = new com.android.server.usage.IntervalStats();
                    try {
                        readLocked(valueAt, intervalStats, z);
                        if (j < intervalStats.endTime && !statCombiner.combine(intervalStats, false, arrayList)) {
                            break;
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(TAG, "Failed to read usage stats file", e);
                    }
                    lastIndexOnOrBefore2++;
                }
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int findBestFitBucket(long j, long j2) {
        int i;
        synchronized (this.mLock) {
            try {
                i = -1;
                long j3 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                for (int length = this.mSortedStatFiles.length - 1; length >= 0; length--) {
                    int lastIndexOnOrBefore = this.mSortedStatFiles[length].lastIndexOnOrBefore(j);
                    int size = this.mSortedStatFiles[length].size();
                    if (lastIndexOnOrBefore >= 0 && lastIndexOnOrBefore < size) {
                        long abs = java.lang.Math.abs(this.mSortedStatFiles[length].keyAt(lastIndexOnOrBefore) - j);
                        if (abs < j3) {
                            i = length;
                            j3 = abs;
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public void prune(long j) {
        synchronized (this.mLock) {
            try {
                this.mCal.setTimeInMillis(j);
                this.mCal.addYears(-2);
                pruneFilesOlderThan(this.mIntervalDirs[3], this.mCal.getTimeInMillis());
                this.mCal.setTimeInMillis(j);
                this.mCal.addMonths(-6);
                pruneFilesOlderThan(this.mIntervalDirs[2], this.mCal.getTimeInMillis());
                this.mCal.setTimeInMillis(j);
                this.mCal.addWeeks(-4);
                pruneFilesOlderThan(this.mIntervalDirs[1], this.mCal.getTimeInMillis());
                this.mCal.setTimeInMillis(j);
                this.mCal.addDays(-10);
                pruneFilesOlderThan(this.mIntervalDirs[0], this.mCal.getTimeInMillis());
                this.mCal.setTimeInMillis(j);
                this.mCal.addDays(-SELECTION_LOG_RETENTION_LEN);
                for (int i = 0; i < this.mIntervalDirs.length; i++) {
                    pruneChooserCountsOlderThan(this.mIntervalDirs[i], this.mCal.getTimeInMillis());
                }
                indexFilesLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void pruneFilesOlderThan(java.io.File file, long j) {
        long j2;
        java.io.File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (java.io.File file2 : listFiles) {
                try {
                    j2 = parseBeginTime(file2);
                } catch (java.io.IOException e) {
                    j2 = 0;
                }
                if (j2 < j) {
                    new android.util.AtomicFile(file2).delete();
                }
            }
        }
    }

    private void pruneChooserCountsOlderThan(java.io.File file, long j) {
        long j2;
        java.io.File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (java.io.File file2 : listFiles) {
                try {
                    j2 = parseBeginTime(file2);
                } catch (java.io.IOException e) {
                    j2 = 0;
                }
                if (j2 < j) {
                    try {
                        android.util.AtomicFile atomicFile = new android.util.AtomicFile(file2);
                        com.android.server.usage.IntervalStats intervalStats = new com.android.server.usage.IntervalStats();
                        readLocked(atomicFile, intervalStats, false);
                        int size = intervalStats.packageStats.size();
                        for (int i = 0; i < size; i++) {
                            android.app.usage.UsageStats valueAt = intervalStats.packageStats.valueAt(i);
                            if (valueAt.mChooserCounts != null) {
                                valueAt.mChooserCounts.clear();
                            }
                        }
                        writeLocked(atomicFile, intervalStats);
                    } catch (java.lang.Exception e2) {
                        android.util.Slog.e(TAG, "Failed to delete chooser counts from usage stats file", e2);
                    }
                }
            }
        }
    }

    private static long parseBeginTime(android.util.AtomicFile atomicFile) throws java.io.IOException {
        return parseBeginTime(atomicFile.getBaseFile());
    }

    private static long parseBeginTime(java.io.File file) throws java.io.IOException {
        java.lang.String name = file.getName();
        for (int i = 0; i < name.length(); i++) {
            char charAt = name.charAt(i);
            if (charAt < '0' || charAt > '9') {
                name = name.substring(0, i);
                break;
            }
        }
        try {
            return java.lang.Long.parseLong(name);
        } catch (java.lang.NumberFormatException e) {
            throw new java.io.IOException(e);
        }
    }

    private void writeLocked(android.util.AtomicFile atomicFile, com.android.server.usage.IntervalStats intervalStats) throws java.io.IOException, java.lang.RuntimeException {
        if (this.mCurrentVersion <= 3) {
            android.util.Slog.wtf(TAG, "Attempting to write UsageStats as XML with version " + this.mCurrentVersion);
            return;
        }
        writeLocked(atomicFile, intervalStats, this.mCurrentVersion, this.mPackagesTokenData);
    }

    private static void writeLocked(android.util.AtomicFile atomicFile, com.android.server.usage.IntervalStats intervalStats, int i, com.android.server.usage.PackagesTokenData packagesTokenData) throws java.io.IOException, java.lang.RuntimeException {
        java.io.FileOutputStream startWrite = atomicFile.startWrite();
        try {
            writeLocked(startWrite, intervalStats, i, packagesTokenData);
            atomicFile.finishWrite(startWrite);
            atomicFile.failWrite(null);
        } catch (java.lang.Exception e) {
            atomicFile.failWrite(startWrite);
        } catch (java.lang.Throwable th) {
            atomicFile.failWrite(startWrite);
            throw th;
        }
    }

    private static void writeLocked(java.io.OutputStream outputStream, com.android.server.usage.IntervalStats intervalStats, int i, com.android.server.usage.PackagesTokenData packagesTokenData) throws java.lang.Exception {
        switch (i) {
            case 1:
            case 2:
            case 3:
                android.util.Slog.wtf(TAG, "Attempting to write UsageStats as XML with version " + i);
                return;
            case 4:
                try {
                    com.android.server.usage.UsageStatsProto.write(outputStream, intervalStats);
                    return;
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(TAG, "Unable to write interval stats to proto.", e);
                    throw e;
                }
            case 5:
                intervalStats.obfuscateData(packagesTokenData);
                try {
                    com.android.server.usage.UsageStatsProtoV2.write(outputStream, intervalStats);
                    return;
                } catch (java.lang.Exception e2) {
                    android.util.Slog.e(TAG, "Unable to write interval stats to proto.", e2);
                    throw e2;
                }
            default:
                throw new java.lang.RuntimeException("Unhandled UsageStatsDatabase version: " + java.lang.Integer.toString(i) + " on write.");
        }
    }

    private void readLocked(android.util.AtomicFile atomicFile, com.android.server.usage.IntervalStats intervalStats, boolean z) throws java.io.IOException, java.lang.RuntimeException {
        if (this.mCurrentVersion <= 3) {
            android.util.Slog.wtf(TAG, "Reading UsageStats as XML; current database version: " + this.mCurrentVersion);
        }
        readLocked(atomicFile, intervalStats, this.mCurrentVersion, this.mPackagesTokenData, z);
    }

    private static boolean readLocked(android.util.AtomicFile atomicFile, com.android.server.usage.IntervalStats intervalStats, int i, com.android.server.usage.PackagesTokenData packagesTokenData, boolean z) throws java.io.IOException, java.lang.RuntimeException {
        try {
            java.io.FileInputStream openRead = atomicFile.openRead();
            try {
                intervalStats.beginTime = parseBeginTime(atomicFile);
                boolean readLocked = readLocked(openRead, intervalStats, i, packagesTokenData, z);
                intervalStats.lastTimeSaved = atomicFile.getLastModifiedTime();
                return readLocked;
            } finally {
                try {
                    openRead.close();
                } catch (java.io.IOException e) {
                }
            }
        } catch (java.io.FileNotFoundException e2) {
            android.util.Slog.e(TAG, TAG, e2);
            throw e2;
        }
    }

    private static boolean readLocked(java.io.InputStream inputStream, com.android.server.usage.IntervalStats intervalStats, int i, com.android.server.usage.PackagesTokenData packagesTokenData, boolean z) throws java.lang.RuntimeException {
        switch (i) {
            case 1:
            case 2:
            case 3:
                android.util.Slog.w(TAG, "Reading UsageStats as XML; database version: " + i);
                try {
                    com.android.server.usage.UsageStatsXml.read(inputStream, intervalStats);
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(TAG, "Unable to read interval stats from XML", e);
                }
                return false;
            case 4:
                try {
                    com.android.server.usage.UsageStatsProto.read(inputStream, intervalStats);
                } catch (java.lang.Exception e2) {
                    android.util.Slog.e(TAG, "Unable to read interval stats from proto.", e2);
                }
                return false;
            case 5:
                try {
                    com.android.server.usage.UsageStatsProtoV2.read(inputStream, intervalStats, z);
                } catch (java.lang.Exception e3) {
                    android.util.Slog.e(TAG, "Unable to read interval stats from proto.", e3);
                }
                return intervalStats.deobfuscateData(packagesTokenData);
            default:
                throw new java.lang.RuntimeException("Unhandled UsageStatsDatabase version: " + java.lang.Integer.toString(i) + " on read.");
        }
    }

    public void readMappingsLocked() {
        if (!this.mPackageMappingsFile.exists()) {
            return;
        }
        try {
            java.io.FileInputStream openRead = new android.util.AtomicFile(this.mPackageMappingsFile).openRead();
            try {
                com.android.server.usage.UsageStatsProtoV2.readObfuscatedData(openRead, this.mPackagesTokenData);
                if (openRead != null) {
                    openRead.close();
                }
                android.util.SparseArray<java.util.ArrayList<java.lang.String>> sparseArray = this.mPackagesTokenData.tokensToPackagesMap;
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = sparseArray.keyAt(i);
                    java.util.ArrayList<java.lang.String> valueAt = sparseArray.valueAt(i);
                    android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap = new android.util.ArrayMap<>();
                    int size2 = valueAt.size();
                    arrayMap.put(valueAt.get(0), java.lang.Integer.valueOf(keyAt));
                    for (int i2 = 1; i2 < size2; i2++) {
                        arrayMap.put(valueAt.get(i2), java.lang.Integer.valueOf(i2));
                    }
                    this.mPackagesTokenData.packagesToTokensMap.put(valueAt.get(0), arrayMap);
                }
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to read the obfuscated packages mapping file.", e);
        }
    }

    void writeMappingsLocked() throws java.io.IOException {
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mPackageMappingsFile);
        java.io.FileOutputStream startWrite = atomicFile.startWrite();
        try {
            try {
                com.android.server.usage.UsageStatsProtoV2.writeObfuscatedData(startWrite, this.mPackagesTokenData);
                atomicFile.finishWrite(startWrite);
                startWrite = null;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Unable to write obfuscated data to proto.", e);
            }
        } finally {
            atomicFile.failWrite(startWrite);
        }
    }

    void obfuscateCurrentStats(com.android.server.usage.IntervalStats[] intervalStatsArr) {
        if (this.mCurrentVersion < 5) {
            return;
        }
        for (com.android.server.usage.IntervalStats intervalStats : intervalStatsArr) {
            intervalStats.obfuscateData(this.mPackagesTokenData);
        }
    }

    public void putUsageStats(int i, com.android.server.usage.IntervalStats intervalStats) throws java.io.IOException {
        if (intervalStats == null) {
            return;
        }
        synchronized (this.mLock) {
            if (i >= 0) {
                try {
                    if (i < this.mIntervalDirs.length) {
                        android.util.AtomicFile atomicFile = this.mSortedStatFiles[i].get(intervalStats.beginTime);
                        if (atomicFile == null) {
                            atomicFile = new android.util.AtomicFile(new java.io.File(this.mIntervalDirs[i], java.lang.Long.toString(intervalStats.beginTime)));
                            this.mSortedStatFiles[i].put(intervalStats.beginTime, atomicFile);
                        }
                        writeLocked(atomicFile, intervalStats);
                        intervalStats.lastTimeSaved = atomicFile.getLastModifiedTime();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            throw new java.lang.IllegalArgumentException("Bad interval type " + i);
        }
    }

    byte[] getBackupPayload(java.lang.String str) {
        return getBackupPayload(str, 4);
    }

    @com.android.internal.annotations.VisibleForTesting
    public byte[] getBackupPayload(java.lang.String str, int i) {
        byte[] byteArray;
        if (i >= 1 && i <= 3) {
            android.util.Slog.wtf(TAG, "Attempting to backup UsageStats as XML with version " + i);
            return null;
        }
        if (i < 1 || i > 4) {
            android.util.Slog.wtf(TAG, "Attempting to backup UsageStats with an unknown version: " + i);
            return null;
        }
        synchronized (this.mLock) {
            try {
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                if (KEY_USAGE_STATS.equals(str)) {
                    prune(java.lang.System.currentTimeMillis());
                    java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
                    try {
                        dataOutputStream.writeInt(i);
                        dataOutputStream.writeInt(this.mSortedStatFiles[0].size());
                        for (int i2 = 0; i2 < this.mSortedStatFiles[0].size(); i2++) {
                            writeIntervalStatsToStream(dataOutputStream, this.mSortedStatFiles[0].valueAt(i2), i);
                        }
                        dataOutputStream.writeInt(this.mSortedStatFiles[1].size());
                        for (int i3 = 0; i3 < this.mSortedStatFiles[1].size(); i3++) {
                            writeIntervalStatsToStream(dataOutputStream, this.mSortedStatFiles[1].valueAt(i3), i);
                        }
                        dataOutputStream.writeInt(this.mSortedStatFiles[2].size());
                        for (int i4 = 0; i4 < this.mSortedStatFiles[2].size(); i4++) {
                            writeIntervalStatsToStream(dataOutputStream, this.mSortedStatFiles[2].valueAt(i4), i);
                        }
                        dataOutputStream.writeInt(this.mSortedStatFiles[3].size());
                        for (int i5 = 0; i5 < this.mSortedStatFiles[3].size(); i5++) {
                            writeIntervalStatsToStream(dataOutputStream, this.mSortedStatFiles[3].valueAt(i5), i);
                        }
                    } catch (java.io.IOException e) {
                        android.util.Slog.d(TAG, "Failed to write data to output stream", e);
                        byteArrayOutputStream.reset();
                    }
                }
                byteArray = byteArrayOutputStream.toByteArray();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return byteArray;
    }

    private void calculatePackagesUsedWithinTimeframe(com.android.server.usage.IntervalStats intervalStats, java.util.Set<java.lang.String> set, long j) {
        for (android.app.usage.UsageStats usageStats : intervalStats.packageStats.values()) {
            if (usageStats.getLastTimePackageUsed() > j) {
                set.add(usageStats.mPackageName);
            }
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    public java.util.Set<java.lang.String> applyRestoredPayload(java.lang.String str, byte[] bArr) {
        java.io.DataInputStream dataInputStream;
        int readInt;
        synchronized (this.mLock) {
            try {
                if (!KEY_USAGE_STATS.equals(str)) {
                    return java.util.Collections.EMPTY_SET;
                }
                int i = 0;
                com.android.server.usage.IntervalStats latestUsageStats = getLatestUsageStats(0);
                com.android.server.usage.IntervalStats latestUsageStats2 = getLatestUsageStats(1);
                com.android.server.usage.IntervalStats latestUsageStats3 = getLatestUsageStats(2);
                com.android.server.usage.IntervalStats latestUsageStats4 = getLatestUsageStats(3);
                android.util.ArraySet arraySet = new android.util.ArraySet();
                try {
                    try {
                        dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
                        readInt = dataInputStream.readInt();
                    } catch (java.io.IOException e) {
                        android.util.Slog.d(TAG, "Failed to read data from input stream", e);
                    }
                    if (readInt < 1 || readInt > 4) {
                        return arraySet;
                    }
                    for (int i2 = 0; i2 < this.mIntervalDirs.length; i2++) {
                        deleteDirectoryContents(this.mIntervalDirs[i2]);
                    }
                    com.android.server.usage.IntervalStats intervalStats = latestUsageStats4;
                    long currentTimeMillis = java.lang.System.currentTimeMillis() - java.util.concurrent.TimeUnit.DAYS.toMillis(90L);
                    int readInt2 = dataInputStream.readInt();
                    for (int i3 = 0; i3 < readInt2; i3++) {
                        com.android.server.usage.IntervalStats deserializeIntervalStats = deserializeIntervalStats(getIntervalStatsBytes(dataInputStream), readInt);
                        calculatePackagesUsedWithinTimeframe(deserializeIntervalStats, arraySet, currentTimeMillis);
                        arraySet.addAll(deserializeIntervalStats.packageStats.keySet());
                        putUsageStats(0, mergeStats(deserializeIntervalStats, latestUsageStats));
                    }
                    int readInt3 = dataInputStream.readInt();
                    for (int i4 = 0; i4 < readInt3; i4++) {
                        com.android.server.usage.IntervalStats deserializeIntervalStats2 = deserializeIntervalStats(getIntervalStatsBytes(dataInputStream), readInt);
                        calculatePackagesUsedWithinTimeframe(deserializeIntervalStats2, arraySet, currentTimeMillis);
                        putUsageStats(1, mergeStats(deserializeIntervalStats2, latestUsageStats2));
                    }
                    int readInt4 = dataInputStream.readInt();
                    for (int i5 = 0; i5 < readInt4; i5++) {
                        com.android.server.usage.IntervalStats deserializeIntervalStats3 = deserializeIntervalStats(getIntervalStatsBytes(dataInputStream), readInt);
                        calculatePackagesUsedWithinTimeframe(deserializeIntervalStats3, arraySet, currentTimeMillis);
                        putUsageStats(2, mergeStats(deserializeIntervalStats3, latestUsageStats3));
                    }
                    int readInt5 = dataInputStream.readInt();
                    while (i < readInt5) {
                        com.android.server.usage.IntervalStats deserializeIntervalStats4 = deserializeIntervalStats(getIntervalStatsBytes(dataInputStream), readInt);
                        calculatePackagesUsedWithinTimeframe(deserializeIntervalStats4, arraySet, currentTimeMillis);
                        com.android.server.usage.IntervalStats intervalStats2 = intervalStats;
                        putUsageStats(3, mergeStats(deserializeIntervalStats4, intervalStats2));
                        i++;
                        intervalStats = intervalStats2;
                    }
                    indexFilesLocked();
                    return arraySet;
                } finally {
                    indexFilesLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private com.android.server.usage.IntervalStats mergeStats(com.android.server.usage.IntervalStats intervalStats, com.android.server.usage.IntervalStats intervalStats2) {
        if (intervalStats2 == null) {
            return intervalStats;
        }
        if (intervalStats == null) {
            return null;
        }
        intervalStats.activeConfiguration = intervalStats2.activeConfiguration;
        intervalStats.configurations.putAll((android.util.ArrayMap<? extends android.content.res.Configuration, ? extends android.app.usage.ConfigurationStats>) intervalStats2.configurations);
        intervalStats.events.clear();
        intervalStats.events.merge(intervalStats2.events);
        return intervalStats;
    }

    private void writeIntervalStatsToStream(java.io.DataOutputStream dataOutputStream, android.util.AtomicFile atomicFile, int i) throws java.io.IOException {
        com.android.server.usage.IntervalStats intervalStats = new com.android.server.usage.IntervalStats();
        try {
            readLocked(atomicFile, intervalStats, false);
            sanitizeIntervalStatsForBackup(intervalStats);
            byte[] serializeIntervalStats = serializeIntervalStats(intervalStats, i);
            dataOutputStream.writeInt(serializeIntervalStats.length);
            dataOutputStream.write(serializeIntervalStats);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to read usage stats file", e);
            dataOutputStream.writeInt(0);
        }
    }

    private static byte[] getIntervalStatsBytes(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        int readInt = dataInputStream.readInt();
        byte[] bArr = new byte[readInt];
        dataInputStream.read(bArr, 0, readInt);
        return bArr;
    }

    private static void sanitizeIntervalStatsForBackup(com.android.server.usage.IntervalStats intervalStats) {
        if (intervalStats == null) {
            return;
        }
        intervalStats.activeConfiguration = null;
        intervalStats.configurations.clear();
        intervalStats.events.clear();
    }

    private byte[] serializeIntervalStats(com.android.server.usage.IntervalStats intervalStats, int i) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeLong(intervalStats.beginTime);
            writeLocked(dataOutputStream, intervalStats, i, this.mPackagesTokenData);
        } catch (java.lang.Exception e) {
            android.util.Slog.d(TAG, "Serializing IntervalStats Failed", e);
            byteArrayOutputStream.reset();
        }
        return byteArrayOutputStream.toByteArray();
    }

    private com.android.server.usage.IntervalStats deserializeIntervalStats(byte[] bArr, int i) {
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
        com.android.server.usage.IntervalStats intervalStats = new com.android.server.usage.IntervalStats();
        try {
            intervalStats.beginTime = dataInputStream.readLong();
            readLocked((java.io.InputStream) dataInputStream, intervalStats, i, this.mPackagesTokenData, false);
            return intervalStats;
        } catch (java.lang.Exception e) {
            android.util.Slog.d(TAG, "DeSerializing IntervalStats Failed", e);
            return null;
        }
    }

    private static void deleteDirectoryContents(java.io.File file) {
        for (java.io.File file2 : file.listFiles()) {
            deleteDirectory(file2);
        }
    }

    private static void deleteDirectory(java.io.File file) {
        java.io.File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (java.io.File file2 : listFiles) {
                if (!file2.isDirectory()) {
                    file2.delete();
                } else {
                    deleteDirectory(file2);
                }
            }
        }
        file.delete();
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, boolean z) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println();
                indentingPrintWriter.println("UsageStatsDatabase:");
                indentingPrintWriter.increaseIndent();
                dumpMappings(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Database Summary:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mSortedStatFiles.length; i++) {
                    android.util.LongSparseArray<android.util.AtomicFile> longSparseArray = this.mSortedStatFiles[i];
                    int size = longSparseArray.size();
                    indentingPrintWriter.print(com.android.server.usage.UserUsageStatsService.intervalToString(i));
                    indentingPrintWriter.print(" stats files: ");
                    indentingPrintWriter.print(size);
                    indentingPrintWriter.println(", sorted list of files:");
                    indentingPrintWriter.increaseIndent();
                    for (int i2 = 0; i2 < size; i2++) {
                        long keyAt = longSparseArray.keyAt(i2);
                        if (z) {
                            indentingPrintWriter.print(com.android.server.usage.UserUsageStatsService.formatDateTime(keyAt, false));
                        } else {
                            indentingPrintWriter.printPair(java.lang.Long.toString(keyAt), com.android.server.usage.UserUsageStatsService.formatDateTime(keyAt, true));
                        }
                        indentingPrintWriter.println();
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dumpMappings(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Obfuscated Packages Mappings:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("Counter: " + this.mPackagesTokenData.counter);
                indentingPrintWriter.println("Tokens Map Size: " + this.mPackagesTokenData.tokensToPackagesMap.size());
                if (!this.mPackagesTokenData.removedPackageTokens.isEmpty()) {
                    indentingPrintWriter.println("Removed Package Tokens: " + java.util.Arrays.toString(this.mPackagesTokenData.removedPackageTokens.toArray()));
                }
                for (int i = 0; i < this.mPackagesTokenData.tokensToPackagesMap.size(); i++) {
                    indentingPrintWriter.println("Token " + this.mPackagesTokenData.tokensToPackagesMap.keyAt(i) + ": [" + java.lang.String.join(", ", this.mPackagesTokenData.tokensToPackagesMap.valueAt(i)) + "]");
                }
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void deleteDataFor(java.lang.String str) {
        prunePackagesDataOnUpgrade(new java.util.HashMap<>(java.util.Collections.singletonMap(str, java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime()))));
    }

    com.android.server.usage.IntervalStats readIntervalStatsForFile(int i, long j) {
        com.android.server.usage.IntervalStats intervalStats;
        synchronized (this.mLock) {
            try {
                intervalStats = new com.android.server.usage.IntervalStats();
                try {
                    readLocked(this.mSortedStatFiles[i].get(j, null), intervalStats, false);
                } catch (java.lang.Exception e) {
                    return null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return intervalStats;
    }
}
