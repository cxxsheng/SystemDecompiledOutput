package com.android.server.tare;

/* loaded from: classes2.dex */
public class Scribe {
    private static final boolean DEBUG;
    private static final int MAX_NUM_TRANSACTION_DUMP = 25;
    private static final long MAX_TRANSACTION_AGE_MS = 691200000;
    private static final int STATE_FILE_VERSION = 0;
    private static final java.lang.String TAG = "TARE-" + com.android.server.tare.Scribe.class.getSimpleName();
    private static final long WRITE_DELAY = 30000;
    private static final java.lang.String XML_ATTR_CONSUMPTION_LIMIT = "consumptionLimit";
    private static final java.lang.String XML_ATTR_CTP = "ctp";
    private static final java.lang.String XML_ATTR_CURRENT_BALANCE = "currentBalance";
    private static final java.lang.String XML_ATTR_DELTA = "delta";
    private static final java.lang.String XML_ATTR_END_TIME = "endTime";
    private static final java.lang.String XML_ATTR_EVENT_ID = "eventId";
    private static final java.lang.String XML_ATTR_LAST_RECLAMATION_TIME = "lastReclamationTime";
    private static final java.lang.String XML_ATTR_LAST_STOCK_RECALCULATION_TIME = "lastStockRecalculationTime";
    private static final java.lang.String XML_ATTR_PACKAGE_NAME = "pkgName";
    private static final java.lang.String XML_ATTR_PR_BATTERY_LEVEL = "batteryLevel";
    private static final java.lang.String XML_ATTR_PR_DISCHARGE = "discharge";
    private static final java.lang.String XML_ATTR_PR_LOSS = "loss";
    private static final java.lang.String XML_ATTR_PR_NEG_REGULATIONS = "negRegulations";
    private static final java.lang.String XML_ATTR_PR_NUM_LOSS = "numLoss";
    private static final java.lang.String XML_ATTR_PR_NUM_NEG_REGULATIONS = "numNegRegulations";
    private static final java.lang.String XML_ATTR_PR_NUM_POS_REGULATIONS = "numPosRegulations";
    private static final java.lang.String XML_ATTR_PR_NUM_PROFIT = "numProfits";
    private static final java.lang.String XML_ATTR_PR_NUM_REWARDS = "numRewards";
    private static final java.lang.String XML_ATTR_PR_POS_REGULATIONS = "posRegulations";
    private static final java.lang.String XML_ATTR_PR_PROFIT = "profit";
    private static final java.lang.String XML_ATTR_PR_REWARDS = "rewards";
    private static final java.lang.String XML_ATTR_PR_SCREEN_OFF_DISCHARGE_MAH = "screenOffDischargeMah";
    private static final java.lang.String XML_ATTR_PR_SCREEN_OFF_DURATION_MS = "screenOffDurationMs";
    private static final java.lang.String XML_ATTR_REMAINING_CONSUMABLE_CAKES = "remainingConsumableCakes";
    private static final java.lang.String XML_ATTR_START_TIME = "startTime";
    private static final java.lang.String XML_ATTR_TAG = "tag";
    private static final java.lang.String XML_ATTR_TIME_SINCE_FIRST_SETUP_MS = "timeSinceFirstSetup";
    private static final java.lang.String XML_ATTR_USER_ID = "userId";
    private static final java.lang.String XML_ATTR_VERSION = "version";
    private static final java.lang.String XML_TAG_HIGH_LEVEL_STATE = "irs-state";
    private static final java.lang.String XML_TAG_LEDGER = "ledger";
    private static final java.lang.String XML_TAG_PERIOD_REPORT = "report";
    private static final java.lang.String XML_TAG_REWARD_BUCKET = "rewardBucket";
    private static final java.lang.String XML_TAG_TARE = "tare";
    private static final java.lang.String XML_TAG_TRANSACTION = "transaction";
    private static final java.lang.String XML_TAG_USER = "user";
    private final com.android.server.tare.Analyst mAnalyst;
    private final java.lang.Runnable mCleanRunnable;
    private final com.android.server.tare.InternalResourceService mIrs;

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    private long mLastReclamationTime;

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    private long mLastStockRecalculationTime;

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    private final android.util.SparseArrayMap<java.lang.String, com.android.server.tare.Ledger> mLedgers;
    private long mLoadedTimeSinceFirstSetup;

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    private final android.util.SparseLongArray mRealtimeSinceUsersAddedOffsets;

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    private long mRemainingConsumableCakes;

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    private long mSatiatedConsumptionLimit;
    private final android.util.AtomicFile mStateFile;
    private final java.lang.Runnable mWriteRunnable;

    static {
        DEBUG = com.android.server.tare.InternalResourceService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    Scribe(com.android.server.tare.InternalResourceService internalResourceService, com.android.server.tare.Analyst analyst) {
        this(internalResourceService, analyst, android.os.Environment.getDataSystemDirectory());
    }

    @com.android.internal.annotations.VisibleForTesting
    Scribe(com.android.server.tare.InternalResourceService internalResourceService, com.android.server.tare.Analyst analyst, java.io.File file) {
        this.mLedgers = new android.util.SparseArrayMap<>();
        this.mRealtimeSinceUsersAddedOffsets = new android.util.SparseLongArray();
        this.mCleanRunnable = new java.lang.Runnable() { // from class: com.android.server.tare.Scribe$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.tare.Scribe.this.cleanupLedgers();
            }
        };
        this.mWriteRunnable = new java.lang.Runnable() { // from class: com.android.server.tare.Scribe$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.tare.Scribe.this.writeState();
            }
        };
        this.mIrs = internalResourceService;
        this.mAnalyst = analyst;
        java.io.File file2 = new java.io.File(file, XML_TAG_TARE);
        file2.mkdirs();
        this.mStateFile = new android.util.AtomicFile(new java.io.File(file2, "state.xml"), XML_TAG_TARE);
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    void adjustRemainingConsumableCakesLocked(long j) {
        long j2 = this.mRemainingConsumableCakes;
        this.mRemainingConsumableCakes += j;
        if (this.mRemainingConsumableCakes < 0) {
            android.util.Slog.w(TAG, "Overdrew consumable cakes by " + com.android.server.tare.TareUtils.cakeToString(-this.mRemainingConsumableCakes));
            this.mRemainingConsumableCakes = 0L;
        }
        if (this.mRemainingConsumableCakes != j2) {
            postWrite();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    void discardLedgerLocked(int i, @android.annotation.NonNull java.lang.String str) {
        this.mLedgers.delete(i, str);
        postWrite();
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    void onUserRemovedLocked(int i) {
        this.mLedgers.delete(i);
        this.mRealtimeSinceUsersAddedOffsets.delete(i);
        postWrite();
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    long getSatiatedConsumptionLimitLocked() {
        return this.mSatiatedConsumptionLimit;
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    long getLastReclamationTimeLocked() {
        return this.mLastReclamationTime;
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    long getLastStockRecalculationTimeLocked() {
        return this.mLastStockRecalculationTime;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    com.android.server.tare.Ledger getLedgerLocked(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.tare.Ledger ledger = (com.android.server.tare.Ledger) this.mLedgers.get(i, str);
        if (ledger == null) {
            com.android.server.tare.Ledger ledger2 = new com.android.server.tare.Ledger();
            this.mLedgers.add(i, str, ledger2);
            return ledger2;
        }
        return ledger;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    android.util.SparseArrayMap<java.lang.String, com.android.server.tare.Ledger> getLedgersLocked() {
        return this.mLedgers;
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    long getCakesInCirculationForLoggingLocked() {
        long j = 0;
        for (int numMaps = this.mLedgers.numMaps() - 1; numMaps >= 0; numMaps--) {
            for (int numElementsForKeyAt = this.mLedgers.numElementsForKeyAt(numMaps) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                j += ((com.android.server.tare.Ledger) this.mLedgers.valueAt(numMaps, numElementsForKeyAt)).getCurrentBalance();
            }
        }
        return j;
    }

    long getRealtimeSinceFirstSetupMs(long j) {
        return this.mLoadedTimeSinceFirstSetup + j;
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    long getRemainingConsumableCakesLocked() {
        return this.mRemainingConsumableCakes;
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    android.util.SparseLongArray getRealtimeSinceUsersAddedLocked(long j) {
        android.util.SparseLongArray sparseLongArray = new android.util.SparseLongArray();
        for (int size = this.mRealtimeSinceUsersAddedOffsets.size() - 1; size >= 0; size--) {
            sparseLongArray.put(this.mRealtimeSinceUsersAddedOffsets.keyAt(size), this.mRealtimeSinceUsersAddedOffsets.valueAt(size) + j);
        }
        return sparseLongArray;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    void loadFromDiskLocked() {
        int i;
        int i2;
        java.lang.String name;
        int i3;
        int attributeInt;
        this.mLedgers.clear();
        if (!recordExists()) {
            this.mSatiatedConsumptionLimit = this.mIrs.getInitialSatiatedConsumptionLimitLocked();
            this.mRemainingConsumableCakes = this.mIrs.getConsumptionLimitLocked();
            return;
        }
        this.mSatiatedConsumptionLimit = 0L;
        this.mRemainingConsumableCakes = 0L;
        android.util.SparseArray<android.util.ArraySet<java.lang.String>> sparseArray = new android.util.SparseArray<>();
        android.util.SparseArrayMap<java.lang.String, com.android.server.tare.InstalledPackageInfo> installedPackages = this.mIrs.getInstalledPackages();
        int i4 = 1;
        int numMaps = installedPackages.numMaps() - 1;
        while (true) {
            i = -1;
            if (numMaps < 0) {
                break;
            }
            int keyAt = installedPackages.keyAt(numMaps);
            for (int numElementsForKeyAt = installedPackages.numElementsForKeyAt(numMaps) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                com.android.server.tare.InstalledPackageInfo installedPackageInfo = (com.android.server.tare.InstalledPackageInfo) installedPackages.valueAt(numMaps, numElementsForKeyAt);
                if (installedPackageInfo.uid != -1) {
                    android.util.ArraySet<java.lang.String> arraySet = sparseArray.get(keyAt);
                    if (arraySet == null) {
                        arraySet = new android.util.ArraySet<>();
                        sparseArray.put(keyAt, arraySet);
                    }
                    arraySet.add(installedPackageInfo.packageName);
                }
            }
            numMaps--;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            java.io.FileInputStream openRead = this.mStateFile.openRead();
            try {
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                int eventType = resolvePullParser.getEventType();
                while (true) {
                    i2 = 2;
                    if (eventType == 2 || eventType == 1) {
                        break;
                    } else {
                        eventType = resolvePullParser.next();
                    }
                }
                if (eventType == 1) {
                    if (DEBUG) {
                        android.util.Slog.w(TAG, "No persisted state.");
                    }
                    if (openRead != null) {
                        openRead.close();
                        return;
                    }
                    return;
                }
                if (XML_TAG_TARE.equals(resolvePullParser.getName()) && ((attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, XML_ATTR_VERSION)) < 0 || attributeInt > 0)) {
                    android.util.Slog.e(TAG, "Invalid version number (" + attributeInt + "), aborting file read");
                    if (openRead != null) {
                        openRead.close();
                        return;
                    }
                    return;
                }
                long currentTimeMillis = java.lang.System.currentTimeMillis() - MAX_TRANSACTION_AGE_MS;
                int next = resolvePullParser.next();
                long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                while (next != i4) {
                    if (next == i2 && (name = resolvePullParser.getName()) != null) {
                        switch (name.hashCode()) {
                            case -934521548:
                                if (name.equals(XML_TAG_PERIOD_REPORT)) {
                                    i3 = i2;
                                    break;
                                }
                                i3 = i;
                                break;
                            case 3599307:
                                if (name.equals(XML_TAG_USER)) {
                                    i3 = 1;
                                    break;
                                }
                                i3 = i;
                                break;
                            case 689502574:
                                if (name.equals(XML_TAG_HIGH_LEVEL_STATE)) {
                                    i3 = 0;
                                    break;
                                }
                                i3 = i;
                                break;
                            default:
                                i3 = i;
                                break;
                        }
                        switch (i3) {
                            case 0:
                                this.mLastReclamationTime = resolvePullParser.getAttributeLong((java.lang.String) null, XML_ATTR_LAST_RECLAMATION_TIME);
                                this.mLastStockRecalculationTime = resolvePullParser.getAttributeLong((java.lang.String) null, XML_ATTR_LAST_STOCK_RECALCULATION_TIME, 0L);
                                this.mLoadedTimeSinceFirstSetup = resolvePullParser.getAttributeLong((java.lang.String) null, XML_ATTR_TIME_SINCE_FIRST_SETUP_MS, -android.os.SystemClock.elapsedRealtime());
                                this.mSatiatedConsumptionLimit = resolvePullParser.getAttributeLong((java.lang.String) null, XML_ATTR_CONSUMPTION_LIMIT, this.mIrs.getInitialSatiatedConsumptionLimitLocked());
                                long consumptionLimitLocked = this.mIrs.getConsumptionLimitLocked();
                                this.mRemainingConsumableCakes = java.lang.Math.min(consumptionLimitLocked, resolvePullParser.getAttributeLong((java.lang.String) null, XML_ATTR_REMAINING_CONSUMABLE_CAKES, consumptionLimitLocked));
                                break;
                            case 1:
                                j = java.lang.Math.min(j, readUserFromXmlLocked(resolvePullParser, sparseArray, currentTimeMillis));
                                break;
                            case 2:
                                arrayList.add(readReportFromXml(resolvePullParser));
                                break;
                            default:
                                android.util.Slog.e(TAG, "Unexpected tag: " + name);
                                break;
                        }
                    }
                    next = resolvePullParser.next();
                    i4 = 1;
                    i = -1;
                    i2 = 2;
                }
                this.mAnalyst.loadReports(arrayList);
                scheduleCleanup(j);
                if (openRead != null) {
                    openRead.close();
                }
            } finally {
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.wtf(TAG, "Error reading state from disk", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void postWrite() {
        com.android.server.tare.TareHandlerThread.getHandler().postDelayed(this.mWriteRunnable, 30000L);
    }

    boolean recordExists() {
        return this.mStateFile.exists();
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    void setConsumptionLimitLocked(long j) {
        if (this.mRemainingConsumableCakes > j) {
            this.mRemainingConsumableCakes = j;
        } else if (j > this.mSatiatedConsumptionLimit) {
            this.mRemainingConsumableCakes = j - (this.mSatiatedConsumptionLimit - this.mRemainingConsumableCakes);
        }
        this.mSatiatedConsumptionLimit = j;
        postWrite();
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    void setLastReclamationTimeLocked(long j) {
        this.mLastReclamationTime = j;
        postWrite();
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    void setLastStockRecalculationTimeLocked(long j) {
        this.mLastStockRecalculationTime = j;
        postWrite();
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    void setUserAddedTimeLocked(int i, long j) {
        this.mRealtimeSinceUsersAddedOffsets.put(i, -j);
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    void tearDownLocked() {
        com.android.server.tare.TareHandlerThread.getHandler().removeCallbacks(this.mCleanRunnable);
        com.android.server.tare.TareHandlerThread.getHandler().removeCallbacks(this.mWriteRunnable);
        this.mLedgers.clear();
        this.mRemainingConsumableCakes = 0L;
        this.mSatiatedConsumptionLimit = 0L;
        this.mLastReclamationTime = 0L;
    }

    @com.android.internal.annotations.VisibleForTesting
    void writeImmediatelyForTesting() {
        this.mWriteRunnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupLedgers() {
        synchronized (this.mIrs.getLock()) {
            try {
                com.android.server.tare.TareHandlerThread.getHandler().removeCallbacks(this.mCleanRunnable);
                long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                for (int numMaps = this.mLedgers.numMaps() - 1; numMaps >= 0; numMaps--) {
                    int keyAt = this.mLedgers.keyAt(numMaps);
                    for (int numElementsForKey = this.mLedgers.numElementsForKey(keyAt) - 1; numElementsForKey >= 0; numElementsForKey--) {
                        com.android.server.tare.Ledger.Transaction removeOldTransactions = ((com.android.server.tare.Ledger) this.mLedgers.get(keyAt, (java.lang.String) this.mLedgers.keyAt(numMaps, numElementsForKey))).removeOldTransactions(MAX_TRANSACTION_AGE_MS);
                        if (removeOldTransactions != null) {
                            j = java.lang.Math.min(j, removeOldTransactions.endTimeMs);
                        }
                    }
                }
                scheduleCleanup(j);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    private static java.lang.String intern(@android.annotation.Nullable java.lang.String str) {
        if (str == null) {
            return null;
        }
        return str.intern();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x012b, code lost:
    
        if (r7 != false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x012d, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0137, code lost:
    
        return android.util.Pair.create(r3, new com.android.server.tare.Ledger(r5, r1, r2));
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x009c, code lost:
    
        if (r10.equals(com.android.server.tare.Scribe.XML_TAG_REWARD_BUCKET) != false) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a3 A[SYNTHETIC] */
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.util.Pair<java.lang.String, com.android.server.tare.Ledger> readLedgerFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.util.ArraySet<java.lang.String> arraySet, long j) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String name;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.lang.String intern = intern(typedXmlPullParser.getAttributeValue((java.lang.String) null, XML_ATTR_PACKAGE_NAME));
        long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_CURRENT_BALANCE);
        boolean contains = arraySet.contains(intern);
        if (!contains) {
            android.util.Slog.w(TAG, "Invalid pkg " + intern + " is saved to disk");
        }
        int next = typedXmlPullParser.next();
        while (true) {
            char c = 1;
            if (next != 1) {
                name = typedXmlPullParser.getName();
                if (next == 3) {
                    if (XML_TAG_LEDGER.equals(name)) {
                    }
                } else if (next == 2 && name != null) {
                    if (contains) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "Starting ledger tag: " + name);
                        }
                        switch (name.hashCode()) {
                            case 1255941625:
                                break;
                            case 2141246174:
                                if (name.equals(XML_TAG_TRANSACTION)) {
                                    c = 0;
                                    switch (c) {
                                        case 0:
                                            long attributeLong2 = typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_END_TIME);
                                            if (attributeLong2 > j) {
                                                arrayList.add(new com.android.server.tare.Ledger.Transaction(typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_START_TIME), attributeLong2, typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_EVENT_ID), intern(typedXmlPullParser.getAttributeValue((java.lang.String) null, XML_ATTR_TAG)), typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_DELTA), typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_CTP)));
                                                break;
                                            } else if (!DEBUG) {
                                                break;
                                            } else {
                                                android.util.Slog.d(TAG, "Skipping event because it's too old.");
                                                break;
                                            }
                                        case 1:
                                            arrayList2.add(readRewardBucketFromXml(typedXmlPullParser));
                                            break;
                                        default:
                                            android.util.Slog.e(TAG, "Unexpected event: (" + next + ") " + name);
                                            return null;
                                    }
                                }
                                c = 65535;
                                switch (c) {
                                }
                            default:
                                c = 65535;
                                switch (c) {
                                }
                        }
                    } else {
                        continue;
                    }
                }
                next = typedXmlPullParser.next();
            }
        }
        android.util.Slog.e(TAG, "Unexpected event: (" + next + ") " + name);
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    private long readUserFromXmlLocked(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.util.SparseArray<android.util.ArraySet<java.lang.String>> sparseArray, long j) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.util.Pair<java.lang.String, com.android.server.tare.Ledger> readLedgerFromXml;
        com.android.server.tare.Ledger ledger;
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "userId");
        android.util.ArraySet<java.lang.String> arraySet = sparseArray.get(attributeInt);
        if (arraySet == null) {
            android.util.Slog.w(TAG, "Invalid user " + attributeInt + " is saved to disk");
            attributeInt = -10000;
        }
        if (attributeInt != -10000) {
            this.mRealtimeSinceUsersAddedOffsets.put(attributeInt, typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_TIME_SINCE_FIRST_SETUP_MS, -android.os.SystemClock.elapsedRealtime()));
        }
        int next = typedXmlPullParser.next();
        long j2 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        while (next != 1) {
            java.lang.String name = typedXmlPullParser.getName();
            if (next == 3) {
                if (XML_TAG_USER.equals(name)) {
                    break;
                }
            } else if (XML_TAG_LEDGER.equals(name)) {
                if (attributeInt != -10000 && (readLedgerFromXml = readLedgerFromXml(typedXmlPullParser, arraySet, j)) != null && (ledger = (com.android.server.tare.Ledger) readLedgerFromXml.second) != null) {
                    this.mLedgers.add(attributeInt, (java.lang.String) readLedgerFromXml.first, ledger);
                    com.android.server.tare.Ledger.Transaction earliestTransaction = ledger.getEarliestTransaction();
                    if (earliestTransaction != null) {
                        j2 = java.lang.Math.min(j2, earliestTransaction.endTimeMs);
                    }
                }
            } else {
                android.util.Slog.e(TAG, "Unknown tag: " + name);
            }
            next = typedXmlPullParser.next();
        }
        return j2;
    }

    @android.annotation.NonNull
    private static com.android.server.tare.Analyst.Report readReportFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.server.tare.Analyst.Report report = new com.android.server.tare.Analyst.Report();
        report.cumulativeBatteryDischarge = typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_PR_DISCHARGE);
        report.currentBatteryLevel = typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_PR_BATTERY_LEVEL);
        report.cumulativeProfit = typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_PR_PROFIT);
        report.numProfitableActions = typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_PR_NUM_PROFIT);
        report.cumulativeLoss = typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_PR_LOSS);
        report.numUnprofitableActions = typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_PR_NUM_LOSS);
        report.cumulativeRewards = typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_PR_REWARDS);
        report.numRewards = typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_PR_NUM_REWARDS);
        report.cumulativePositiveRegulations = typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_PR_POS_REGULATIONS);
        report.numPositiveRegulations = typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_PR_NUM_POS_REGULATIONS);
        report.cumulativeNegativeRegulations = typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_PR_NEG_REGULATIONS);
        report.numNegativeRegulations = typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_PR_NUM_NEG_REGULATIONS);
        report.screenOffDurationMs = typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_PR_SCREEN_OFF_DURATION_MS, 0L);
        report.screenOffDischargeMah = typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_PR_SCREEN_OFF_DISCHARGE_MAH, 0L);
        return report;
    }

    @android.annotation.Nullable
    private static com.android.server.tare.Ledger.RewardBucket readRewardBucketFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.server.tare.Ledger.RewardBucket rewardBucket = new com.android.server.tare.Ledger.RewardBucket();
        rewardBucket.startTimeMs = typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_START_TIME);
        int next = typedXmlPullParser.next();
        while (next != 1) {
            java.lang.String name = typedXmlPullParser.getName();
            if (next == 3) {
                if (XML_TAG_REWARD_BUCKET.equals(name)) {
                    break;
                }
            } else {
                if (next != 2 || !XML_ATTR_DELTA.equals(name)) {
                    android.util.Slog.e(TAG, "Unexpected event: (" + next + ") " + name);
                    return null;
                }
                rewardBucket.cumulativeDelta.put(typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_EVENT_ID), typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTR_DELTA));
            }
            next = typedXmlPullParser.next();
        }
        return rewardBucket;
    }

    private void scheduleCleanup(long j) {
        if (j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            return;
        }
        com.android.server.tare.TareHandlerThread.getHandler().postDelayed(this.mCleanRunnable, java.lang.Math.max(3600000L, (j + MAX_TRANSACTION_AGE_MS) - java.lang.System.currentTimeMillis()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeState() {
        synchronized (this.mIrs.getLock()) {
            com.android.server.tare.TareHandlerThread.getHandler().removeCallbacks(this.mWriteRunnable);
            com.android.server.tare.TareHandlerThread.getHandler().removeCallbacks(this.mCleanRunnable);
            if (this.mIrs.getEnabledMode() == 0) {
                return;
            }
            long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            try {
                java.io.FileOutputStream startWrite = this.mStateFile.startWrite();
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    resolveSerializer.startTag((java.lang.String) null, XML_TAG_TARE);
                    resolveSerializer.attributeInt((java.lang.String) null, XML_ATTR_VERSION, 0);
                    resolveSerializer.startTag((java.lang.String) null, XML_TAG_HIGH_LEVEL_STATE);
                    resolveSerializer.attributeLong((java.lang.String) null, XML_ATTR_LAST_RECLAMATION_TIME, this.mLastReclamationTime);
                    resolveSerializer.attributeLong((java.lang.String) null, XML_ATTR_LAST_STOCK_RECALCULATION_TIME, this.mLastStockRecalculationTime);
                    resolveSerializer.attributeLong((java.lang.String) null, XML_ATTR_TIME_SINCE_FIRST_SETUP_MS, this.mLoadedTimeSinceFirstSetup + android.os.SystemClock.elapsedRealtime());
                    resolveSerializer.attributeLong((java.lang.String) null, XML_ATTR_CONSUMPTION_LIMIT, this.mSatiatedConsumptionLimit);
                    resolveSerializer.attributeLong((java.lang.String) null, XML_ATTR_REMAINING_CONSUMABLE_CAKES, this.mRemainingConsumableCakes);
                    resolveSerializer.endTag((java.lang.String) null, XML_TAG_HIGH_LEVEL_STATE);
                    for (int numMaps = this.mLedgers.numMaps() - 1; numMaps >= 0; numMaps--) {
                        j = java.lang.Math.min(j, writeUserLocked(resolveSerializer, this.mLedgers.keyAt(numMaps)));
                    }
                    java.util.List<com.android.server.tare.Analyst.Report> reports = this.mAnalyst.getReports();
                    int size = reports.size();
                    for (int i = 0; i < size; i++) {
                        writeReport(resolveSerializer, reports.get(i));
                    }
                    resolveSerializer.endTag((java.lang.String) null, XML_TAG_TARE);
                    resolveSerializer.endDocument();
                    this.mStateFile.finishWrite(startWrite);
                    if (startWrite != null) {
                        startWrite.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (startWrite != null) {
                        try {
                            startWrite.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Error writing state to disk", e);
            }
            scheduleCleanup(j);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    private long writeUserLocked(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int i) throws java.io.IOException {
        java.lang.String str;
        int indexOfKey = this.mLedgers.indexOfKey(i);
        java.lang.String str2 = null;
        java.lang.String str3 = XML_TAG_USER;
        typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_USER);
        typedXmlSerializer.attributeInt((java.lang.String) null, "userId", i);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_TIME_SINCE_FIRST_SETUP_MS, this.mRealtimeSinceUsersAddedOffsets.get(i, this.mLoadedTimeSinceFirstSetup) + android.os.SystemClock.elapsedRealtime());
        int numElementsForKey = this.mLedgers.numElementsForKey(i) - 1;
        long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        while (numElementsForKey >= 0) {
            java.lang.String str4 = (java.lang.String) this.mLedgers.keyAt(indexOfKey, numElementsForKey);
            com.android.server.tare.Ledger ledger = (com.android.server.tare.Ledger) this.mLedgers.get(i, str4);
            ledger.removeOldTransactions(MAX_TRANSACTION_AGE_MS);
            typedXmlSerializer.startTag(str2, XML_TAG_LEDGER);
            typedXmlSerializer.attribute(str2, XML_ATTR_PACKAGE_NAME, str4);
            typedXmlSerializer.attributeLong(str2, XML_ATTR_CURRENT_BALANCE, ledger.getCurrentBalance());
            java.util.List<com.android.server.tare.Ledger.Transaction> transactions = ledger.getTransactions();
            int i2 = 0;
            while (i2 < transactions.size()) {
                com.android.server.tare.Ledger.Transaction transaction = transactions.get(i2);
                if (i2 != 0) {
                    str = str3;
                } else {
                    str = str3;
                    j = java.lang.Math.min(j, transaction.endTimeMs);
                }
                writeTransaction(typedXmlSerializer, transaction);
                i2++;
                str3 = str;
            }
            java.lang.String str5 = str3;
            java.util.List<com.android.server.tare.Ledger.RewardBucket> rewardBuckets = ledger.getRewardBuckets();
            for (int i3 = 0; i3 < rewardBuckets.size(); i3++) {
                writeRewardBucket(typedXmlSerializer, rewardBuckets.get(i3));
            }
            str2 = null;
            typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_LEDGER);
            numElementsForKey--;
            str3 = str5;
        }
        typedXmlSerializer.endTag(str2, str3);
        return j;
    }

    private static void writeTransaction(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.tare.Ledger.Transaction transaction) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_TRANSACTION);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_START_TIME, transaction.startTimeMs);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_END_TIME, transaction.endTimeMs);
        typedXmlSerializer.attributeInt((java.lang.String) null, XML_ATTR_EVENT_ID, transaction.eventId);
        if (transaction.tag != null) {
            typedXmlSerializer.attribute((java.lang.String) null, XML_ATTR_TAG, transaction.tag);
        }
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_DELTA, transaction.delta);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_CTP, transaction.ctp);
        typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_TRANSACTION);
    }

    private static void writeRewardBucket(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.tare.Ledger.RewardBucket rewardBucket) throws java.io.IOException {
        int size = rewardBucket.cumulativeDelta.size();
        if (size == 0) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_REWARD_BUCKET);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_START_TIME, rewardBucket.startTimeMs);
        for (int i = 0; i < size; i++) {
            typedXmlSerializer.startTag((java.lang.String) null, XML_ATTR_DELTA);
            typedXmlSerializer.attributeInt((java.lang.String) null, XML_ATTR_EVENT_ID, rewardBucket.cumulativeDelta.keyAt(i));
            typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_DELTA, rewardBucket.cumulativeDelta.valueAt(i));
            typedXmlSerializer.endTag((java.lang.String) null, XML_ATTR_DELTA);
        }
        typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_REWARD_BUCKET);
    }

    private static void writeReport(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.tare.Analyst.Report report) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_PERIOD_REPORT);
        typedXmlSerializer.attributeInt((java.lang.String) null, XML_ATTR_PR_DISCHARGE, report.cumulativeBatteryDischarge);
        typedXmlSerializer.attributeInt((java.lang.String) null, XML_ATTR_PR_BATTERY_LEVEL, report.currentBatteryLevel);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_PR_PROFIT, report.cumulativeProfit);
        typedXmlSerializer.attributeInt((java.lang.String) null, XML_ATTR_PR_NUM_PROFIT, report.numProfitableActions);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_PR_LOSS, report.cumulativeLoss);
        typedXmlSerializer.attributeInt((java.lang.String) null, XML_ATTR_PR_NUM_LOSS, report.numUnprofitableActions);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_PR_REWARDS, report.cumulativeRewards);
        typedXmlSerializer.attributeInt((java.lang.String) null, XML_ATTR_PR_NUM_REWARDS, report.numRewards);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_PR_POS_REGULATIONS, report.cumulativePositiveRegulations);
        typedXmlSerializer.attributeInt((java.lang.String) null, XML_ATTR_PR_NUM_POS_REGULATIONS, report.numPositiveRegulations);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_PR_NEG_REGULATIONS, report.cumulativeNegativeRegulations);
        typedXmlSerializer.attributeInt((java.lang.String) null, XML_ATTR_PR_NUM_NEG_REGULATIONS, report.numNegativeRegulations);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_PR_SCREEN_OFF_DURATION_MS, report.screenOffDurationMs);
        typedXmlSerializer.attributeLong((java.lang.String) null, XML_ATTR_PR_SCREEN_OFF_DISCHARGE_MAH, report.screenOffDischargeMah);
        typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_PERIOD_REPORT);
    }

    @com.android.internal.annotations.GuardedBy({"mIrs.getLock()"})
    void dumpLocked(final android.util.IndentingPrintWriter indentingPrintWriter, final boolean z) {
        indentingPrintWriter.println("Ledgers:");
        indentingPrintWriter.increaseIndent();
        this.mLedgers.forEach(new android.util.SparseArrayMap.TriConsumer() { // from class: com.android.server.tare.Scribe$$ExternalSyntheticLambda0
            public final void accept(int i, java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.tare.Scribe.this.lambda$dumpLocked$0(indentingPrintWriter, z, i, (java.lang.String) obj, (com.android.server.tare.Ledger) obj2);
            }
        });
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpLocked$0(android.util.IndentingPrintWriter indentingPrintWriter, boolean z, int i, java.lang.String str, com.android.server.tare.Ledger ledger) {
        indentingPrintWriter.print(com.android.server.tare.TareUtils.appToString(i, str));
        if (this.mIrs.isSystem(i, str)) {
            indentingPrintWriter.print(" (system)");
        }
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        ledger.dump(indentingPrintWriter, z ? Integer.MAX_VALUE : 25);
        indentingPrintWriter.decreaseIndent();
    }
}
