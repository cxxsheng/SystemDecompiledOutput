package com.android.server.appop;

/* loaded from: classes.dex */
final class DiscreteRegistry {
    private static final java.lang.String ATTR_ATTRIBUTION_FLAGS = "af";
    private static final java.lang.String ATTR_CHAIN_ID = "ci";
    private static final java.lang.String ATTR_FLAGS = "f";
    private static final java.lang.String ATTR_LARGEST_CHAIN_ID = "lc";
    private static final java.lang.String ATTR_NOTE_DURATION = "nd";
    private static final java.lang.String ATTR_NOTE_TIME = "nt";
    private static final java.lang.String ATTR_OP_ID = "op";
    private static final java.lang.String ATTR_PACKAGE_NAME = "pn";
    private static final java.lang.String ATTR_TAG = "at";
    private static final java.lang.String ATTR_UID = "ui";
    private static final java.lang.String ATTR_UID_STATE = "us";
    private static final java.lang.String ATTR_VERSION = "v";
    private static final int CURRENT_VERSION = 1;
    private static final java.lang.String DEFAULT_DISCRETE_OPS = "1,0,26,27,100,101,120,136,141";
    static final java.lang.String DISCRETE_HISTORY_FILE_SUFFIX = "tl";
    private static final int OP_FLAGS_DISCRETE = 11;
    private static final java.lang.String PROPERTY_DISCRETE_FLAGS = "discrete_history_op_flags";
    private static final java.lang.String PROPERTY_DISCRETE_HISTORY_CUTOFF = "discrete_history_cutoff_millis";
    private static final java.lang.String PROPERTY_DISCRETE_HISTORY_QUANTIZATION = "discrete_history_quantization_millis";
    private static final java.lang.String PROPERTY_DISCRETE_OPS_LIST = "discrete_history_ops_cslist";
    private static final java.lang.String TAG_ENTRY = "e";
    private static final java.lang.String TAG_HISTORY = "h";
    private static final java.lang.String TAG_OP = "o";
    private static final java.lang.String TAG_PACKAGE = "p";
    private static final java.lang.String TAG_TAG = "a";
    private static final java.lang.String TAG_UID = "u";
    private static int sDiscreteFlags;
    private static long sDiscreteHistoryCutoff;
    private static long sDiscreteHistoryQuantization;
    private static int[] sDiscreteOps;

    @com.android.internal.annotations.GuardedBy({"mOnDiskLock"})
    private java.io.File mDiscreteAccessDir;

    @com.android.internal.annotations.GuardedBy({"mInMemoryLock"})
    private com.android.server.appop.DiscreteRegistry.DiscreteOps mDiscreteOps;

    @android.annotation.NonNull
    private final java.lang.Object mInMemoryLock;
    private static final java.lang.String TAG = com.android.server.appop.DiscreteRegistry.class.getSimpleName();
    private static final long DEFAULT_DISCRETE_HISTORY_CUTOFF = java.time.Duration.ofDays(7).toMillis();
    private static final long MAXIMUM_DISCRETE_HISTORY_CUTOFF = java.time.Duration.ofDays(30).toMillis();
    private static final long DEFAULT_DISCRETE_HISTORY_QUANTIZATION = java.time.Duration.ofMinutes(1).toMillis();
    private final java.lang.Object mOnDiskLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mOnDiskLock"})
    private com.android.server.appop.DiscreteRegistry.DiscreteOps mCachedOps = null;
    private boolean mDebugMode = false;

    DiscreteRegistry(java.lang.Object obj) {
        this.mInMemoryLock = obj;
        synchronized (this.mOnDiskLock) {
            this.mDiscreteAccessDir = new java.io.File(new java.io.File(android.os.Environment.getDataSystemDirectory(), "appops"), "discrete");
            createDiscreteAccessDirLocked();
            int readLargestChainIdFromDiskLocked = readLargestChainIdFromDiskLocked();
            synchronized (this.mInMemoryLock) {
                this.mDiscreteOps = new com.android.server.appop.DiscreteRegistry.DiscreteOps(readLargestChainIdFromDiskLocked);
            }
        }
    }

    void systemReady() {
        android.provider.DeviceConfig.addOnPropertiesChangedListener("privacy", android.os.AsyncTask.THREAD_POOL_EXECUTOR, new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.appop.DiscreteRegistry$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.appop.DiscreteRegistry.this.lambda$systemReady$0(properties);
            }
        });
        lambda$systemReady$0(android.provider.DeviceConfig.getProperties("privacy", new java.lang.String[0]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setDiscreteHistoryParameters, reason: merged with bridge method [inline-methods] */
    public void lambda$systemReady$0(android.provider.DeviceConfig.Properties properties) {
        int[] parseOpsList;
        if (properties.getKeyset().contains(PROPERTY_DISCRETE_HISTORY_CUTOFF)) {
            sDiscreteHistoryCutoff = properties.getLong(PROPERTY_DISCRETE_HISTORY_CUTOFF, DEFAULT_DISCRETE_HISTORY_CUTOFF);
            if (!android.os.Build.IS_DEBUGGABLE && !this.mDebugMode) {
                sDiscreteHistoryCutoff = java.lang.Long.min(MAXIMUM_DISCRETE_HISTORY_CUTOFF, sDiscreteHistoryCutoff);
            }
        } else {
            sDiscreteHistoryCutoff = DEFAULT_DISCRETE_HISTORY_CUTOFF;
        }
        if (properties.getKeyset().contains(PROPERTY_DISCRETE_HISTORY_QUANTIZATION)) {
            sDiscreteHistoryQuantization = properties.getLong(PROPERTY_DISCRETE_HISTORY_QUANTIZATION, DEFAULT_DISCRETE_HISTORY_QUANTIZATION);
            if (!android.os.Build.IS_DEBUGGABLE && !this.mDebugMode) {
                sDiscreteHistoryQuantization = java.lang.Math.max(DEFAULT_DISCRETE_HISTORY_QUANTIZATION, sDiscreteHistoryQuantization);
            }
        } else {
            sDiscreteHistoryQuantization = DEFAULT_DISCRETE_HISTORY_QUANTIZATION;
        }
        int i = 11;
        if (properties.getKeyset().contains(PROPERTY_DISCRETE_FLAGS)) {
            i = properties.getInt(PROPERTY_DISCRETE_FLAGS, 11);
            sDiscreteFlags = i;
        }
        sDiscreteFlags = i;
        if (properties.getKeyset().contains(PROPERTY_DISCRETE_OPS_LIST)) {
            parseOpsList = parseOpsList(properties.getString(PROPERTY_DISCRETE_OPS_LIST, DEFAULT_DISCRETE_OPS));
        } else {
            parseOpsList = parseOpsList(DEFAULT_DISCRETE_OPS);
        }
        sDiscreteOps = parseOpsList;
    }

    void recordDiscreteAccess(int i, java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, int i3, int i4, long j, long j2, int i5, int i6) {
        if (!isDiscreteOp(i2, i3)) {
            return;
        }
        synchronized (this.mInMemoryLock) {
            this.mDiscreteOps.addDiscreteAccess(i2, i, str, str2, i3, i4, j, j2, i5, i6);
        }
    }

    void writeAndClearAccessHistory() {
        com.android.server.appop.DiscreteRegistry.DiscreteOps discreteOps;
        synchronized (this.mOnDiskLock) {
            try {
                if (this.mDiscreteAccessDir == null) {
                    android.util.Slog.d(TAG, "State not saved - persistence not initialized.");
                    return;
                }
                synchronized (this.mInMemoryLock) {
                    discreteOps = this.mDiscreteOps;
                    this.mDiscreteOps = new com.android.server.appop.DiscreteRegistry.DiscreteOps(discreteOps.mChainIdOffset);
                    this.mCachedOps = null;
                }
                deleteOldDiscreteHistoryFilesLocked();
                if (!discreteOps.isEmpty()) {
                    persistDiscreteOpsLocked(discreteOps);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void addFilteredDiscreteOpsToHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps, long j, long j2, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String[] strArr, @android.annotation.Nullable java.lang.String str2, int i3, java.util.Set<java.lang.String> set) {
        android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap;
        boolean z = set != null;
        com.android.server.appop.DiscreteRegistry.DiscreteOps allDiscreteOps = getAllDiscreteOps();
        android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap2 = new android.util.ArrayMap<>();
        if (z) {
            arrayMap = createAttributionChains(allDiscreteOps, set);
        } else {
            arrayMap = arrayMap2;
        }
        allDiscreteOps.filter(java.lang.Math.max(j, java.time.Instant.now().minus(sDiscreteHistoryCutoff, (java.time.temporal.TemporalUnit) java.time.temporal.ChronoUnit.MILLIS).toEpochMilli()), j2, i, i2, str, strArr, str2, i3, arrayMap);
        allDiscreteOps.applyToHistoricalOps(historicalOps, arrayMap);
    }

    private int readLargestChainIdFromDiskLocked() {
        java.io.File[] listFiles = this.mDiscreteAccessDir.listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return 0;
        }
        long j = 0;
        java.io.File file = null;
        for (java.io.File file2 : listFiles) {
            java.lang.String name = file2.getName();
            if (name.endsWith(DISCRETE_HISTORY_FILE_SUFFIX)) {
                long longValue = java.lang.Long.valueOf(name.substring(0, name.length() - DISCRETE_HISTORY_FILE_SUFFIX.length())).longValue();
                if (j < longValue) {
                    file = file2;
                    j = longValue;
                }
            }
        }
        if (file == null) {
            return 0;
        }
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
            try {
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, TAG_HISTORY);
                int attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_LARGEST_CHAIN_ID, 0);
                try {
                    fileInputStream.close();
                } catch (java.io.IOException e) {
                }
                return attributeInt;
            } catch (java.lang.Throwable th) {
                try {
                    fileInputStream.close();
                } catch (java.io.IOException e2) {
                }
                return 0;
            }
        } catch (java.io.FileNotFoundException e3) {
            return 0;
        }
    }

    private android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> createAttributionChains(com.android.server.appop.DiscreteRegistry.DiscreteOps discreteOps, java.util.Set<java.lang.String> set) {
        int i;
        android.util.ArrayMap<java.lang.String, com.android.server.appop.DiscreteRegistry.DiscretePackageOps> arrayMap;
        java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> list;
        int i2;
        int i3;
        android.util.ArrayMap<java.lang.String, java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent>> arrayMap2;
        int i4;
        int i5;
        int i6;
        com.android.server.appop.DiscreteRegistry.DiscreteOps discreteOps2 = discreteOps;
        android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap3 = new android.util.ArrayMap<>();
        int size = discreteOps2.mUids.size();
        int i7 = 0;
        while (i7 < size) {
            android.util.ArrayMap<java.lang.String, com.android.server.appop.DiscreteRegistry.DiscretePackageOps> arrayMap4 = discreteOps2.mUids.valueAt(i7).mPackages;
            int intValue = discreteOps2.mUids.keyAt(i7).intValue();
            int size2 = arrayMap4.size();
            int i8 = 0;
            while (i8 < size2) {
                android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.DiscreteOp> arrayMap5 = arrayMap4.valueAt(i8).mPackageOps;
                java.lang.String keyAt = arrayMap4.keyAt(i8);
                int size3 = arrayMap5.size();
                int i9 = 0;
                while (i9 < size3) {
                    android.util.ArrayMap<java.lang.String, java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent>> arrayMap6 = arrayMap5.valueAt(i9).mAttributedOps;
                    int intValue2 = arrayMap5.keyAt(i9).intValue();
                    int size4 = arrayMap6.size();
                    int i10 = 0;
                    while (i10 < size4) {
                        java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> valueAt = arrayMap6.valueAt(i10);
                        java.lang.String keyAt2 = arrayMap6.keyAt(i10);
                        int size5 = valueAt.size();
                        int i11 = 0;
                        while (i11 < size5) {
                            int i12 = size;
                            com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent = valueAt.get(i11);
                            if (discreteOpEvent != null) {
                                i = size5;
                                arrayMap = arrayMap4;
                                if (discreteOpEvent.mAttributionChainId != -1) {
                                    if ((discreteOpEvent.mAttributionFlags & 8) == 0) {
                                        list = valueAt;
                                        i2 = i10;
                                        i3 = size4;
                                        arrayMap2 = arrayMap6;
                                        i4 = i9;
                                        i5 = size3;
                                        i6 = size2;
                                    } else {
                                        if (arrayMap3.containsKey(java.lang.Integer.valueOf(discreteOpEvent.mAttributionChainId))) {
                                            i6 = size2;
                                        } else {
                                            i6 = size2;
                                            arrayMap3.put(java.lang.Integer.valueOf(discreteOpEvent.mAttributionChainId), new com.android.server.appop.DiscreteRegistry.AttributionChain(set));
                                        }
                                        list = valueAt;
                                        i2 = i10;
                                        i3 = size4;
                                        arrayMap2 = arrayMap6;
                                        i4 = i9;
                                        i5 = size3;
                                        arrayMap3.get(java.lang.Integer.valueOf(discreteOpEvent.mAttributionChainId)).addEvent(keyAt, intValue, keyAt2, intValue2, discreteOpEvent);
                                    }
                                    i11++;
                                    i10 = i2;
                                    valueAt = list;
                                    size5 = i;
                                    size = i12;
                                    arrayMap4 = arrayMap;
                                    size2 = i6;
                                    size4 = i3;
                                    arrayMap6 = arrayMap2;
                                    i9 = i4;
                                    size3 = i5;
                                }
                            } else {
                                i = size5;
                                arrayMap = arrayMap4;
                            }
                            list = valueAt;
                            i2 = i10;
                            i3 = size4;
                            arrayMap2 = arrayMap6;
                            i4 = i9;
                            i5 = size3;
                            i6 = size2;
                            i11++;
                            i10 = i2;
                            valueAt = list;
                            size5 = i;
                            size = i12;
                            arrayMap4 = arrayMap;
                            size2 = i6;
                            size4 = i3;
                            arrayMap6 = arrayMap2;
                            i9 = i4;
                            size3 = i5;
                        }
                        i10++;
                        size2 = size2;
                    }
                    i9++;
                    size2 = size2;
                }
                i8++;
                size2 = size2;
            }
            i7++;
            discreteOps2 = discreteOps;
        }
        return arrayMap3;
    }

    private void readDiscreteOpsFromDisk(com.android.server.appop.DiscreteRegistry.DiscreteOps discreteOps) {
        synchronized (this.mOnDiskLock) {
            try {
                long epochMilli = java.time.Instant.now().minus(sDiscreteHistoryCutoff, (java.time.temporal.TemporalUnit) java.time.temporal.ChronoUnit.MILLIS).toEpochMilli();
                java.io.File[] listFiles = this.mDiscreteAccessDir.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    for (java.io.File file : listFiles) {
                        java.lang.String name = file.getName();
                        if (name.endsWith(DISCRETE_HISTORY_FILE_SUFFIX) && java.lang.Long.valueOf(name.substring(0, name.length() - DISCRETE_HISTORY_FILE_SUFFIX.length())).longValue() >= epochMilli) {
                            discreteOps.readFromFile(file, epochMilli);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearHistory() {
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                this.mDiscreteOps = new com.android.server.appop.DiscreteRegistry.DiscreteOps(0);
            }
            clearOnDiskHistoryLocked();
        }
    }

    void clearHistory(int i, java.lang.String str) {
        com.android.server.appop.DiscreteRegistry.DiscreteOps allDiscreteOps;
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                allDiscreteOps = getAllDiscreteOps();
                clearHistory();
            }
            allDiscreteOps.clearHistory(i, str);
            persistDiscreteOpsLocked(allDiscreteOps);
        }
    }

    void offsetHistory(long j) {
        com.android.server.appop.DiscreteRegistry.DiscreteOps allDiscreteOps;
        synchronized (this.mOnDiskLock) {
            synchronized (this.mInMemoryLock) {
                allDiscreteOps = getAllDiscreteOps();
                clearHistory();
            }
            allDiscreteOps.offsetHistory(j);
            persistDiscreteOpsLocked(allDiscreteOps);
        }
    }

    void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, int i3, @android.annotation.NonNull java.text.SimpleDateFormat simpleDateFormat, @android.annotation.NonNull java.util.Date date, @android.annotation.NonNull java.lang.String str3, int i4) {
        com.android.server.appop.DiscreteRegistry.DiscreteOps allDiscreteOps = getAllDiscreteOps();
        allDiscreteOps.filter(0L, java.time.Instant.now().toEpochMilli(), i2, i, str, i3 == -1 ? null : new java.lang.String[]{android.app.AppOpsManager.opToPublicName(i3)}, str2, 31, new android.util.ArrayMap());
        printWriter.print(str3);
        printWriter.print("Largest chain id: ");
        printWriter.print(this.mDiscreteOps.mLargestChainId);
        printWriter.println();
        allDiscreteOps.dump(printWriter, simpleDateFormat, date, str3, i4);
    }

    private void clearOnDiskHistoryLocked() {
        this.mCachedOps = null;
        android.os.FileUtils.deleteContentsAndDir(this.mDiscreteAccessDir);
        createDiscreteAccessDir();
    }

    private com.android.server.appop.DiscreteRegistry.DiscreteOps getAllDiscreteOps() {
        com.android.server.appop.DiscreteRegistry.DiscreteOps discreteOps = new com.android.server.appop.DiscreteRegistry.DiscreteOps(0);
        synchronized (this.mOnDiskLock) {
            try {
                synchronized (this.mInMemoryLock) {
                    discreteOps.merge(this.mDiscreteOps);
                }
                if (this.mCachedOps == null) {
                    this.mCachedOps = new com.android.server.appop.DiscreteRegistry.DiscreteOps(0);
                    readDiscreteOpsFromDisk(this.mCachedOps);
                }
                discreteOps.merge(this.mCachedOps);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return discreteOps;
    }

    private static final class AttributionChain {
        java.util.Set<java.lang.String> mExemptPkgs;
        java.util.ArrayList<com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent> mChain = new java.util.ArrayList<>();
        com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent mStartEvent = null;
        com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent mLastVisibleEvent = null;

        private static final class OpEvent {
            java.lang.String mAttributionTag;
            int mOpCode;
            com.android.server.appop.DiscreteRegistry.DiscreteOpEvent mOpEvent;
            java.lang.String mPkgName;
            int mUid;

            OpEvent(java.lang.String str, int i, java.lang.String str2, int i2, com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent) {
                this.mPkgName = str;
                this.mUid = i;
                this.mAttributionTag = str2;
                this.mOpCode = i2;
                this.mOpEvent = discreteOpEvent;
            }

            public boolean matches(java.lang.String str, int i, java.lang.String str2, int i2, com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent) {
                return java.util.Objects.equals(str, this.mPkgName) && this.mUid == i && java.util.Objects.equals(str2, this.mAttributionTag) && this.mOpCode == i2 && this.mOpEvent.mAttributionChainId == discreteOpEvent.mAttributionChainId && this.mOpEvent.mAttributionFlags == discreteOpEvent.mAttributionFlags && this.mOpEvent.mNoteTime == discreteOpEvent.mNoteTime;
            }

            public boolean packageOpEquals(com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent opEvent) {
                return java.util.Objects.equals(opEvent.mPkgName, this.mPkgName) && opEvent.mUid == this.mUid && java.util.Objects.equals(opEvent.mAttributionTag, this.mAttributionTag) && this.mOpCode == opEvent.mOpCode;
            }

            public boolean equalsExceptDuration(com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent opEvent) {
                return opEvent.mOpEvent.mNoteDuration != this.mOpEvent.mNoteDuration && packageOpEquals(opEvent) && this.mOpEvent.equalsExceptDuration(opEvent.mOpEvent);
            }
        }

        AttributionChain(java.util.Set<java.lang.String> set) {
            this.mExemptPkgs = set;
        }

        boolean isComplete() {
            return (this.mChain.isEmpty() || getStart() == null || !isEnd(this.mChain.get(this.mChain.size() - 1))) ? false : true;
        }

        boolean isStart(java.lang.String str, int i, java.lang.String str2, int i2, com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent) {
            if (this.mStartEvent == null || discreteOpEvent == null) {
                return false;
            }
            return this.mStartEvent.matches(str, i, str2, i2, discreteOpEvent);
        }

        private com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent getStart() {
            if (this.mChain.isEmpty() || !isStart(this.mChain.get(0))) {
                return null;
            }
            return this.mChain.get(0);
        }

        private com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent getLastVisible() {
            for (int size = this.mChain.size() - 1; size > 0; size--) {
                com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent opEvent = this.mChain.get(size);
                if (!this.mExemptPkgs.contains(opEvent.mPkgName)) {
                    return opEvent;
                }
            }
            return null;
        }

        /* JADX WARN: Code restructure failed: missing block: B:38:0x0083, code lost:
        
            r7.mChain.add(r8, r6);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void addEvent(java.lang.String str, int i, java.lang.String str2, int i2, com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent) {
            com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent opEvent = new com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent(str, i, str2, i2, discreteOpEvent);
            int i3 = 0;
            for (int i4 = 0; i4 < this.mChain.size(); i4++) {
                com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent opEvent2 = this.mChain.get(i4);
                if (opEvent2.equalsExceptDuration(opEvent)) {
                    if (opEvent.mOpEvent.mNoteDuration != -1) {
                        opEvent2.mOpEvent = opEvent.mOpEvent;
                        return;
                    }
                    return;
                }
            }
            if (this.mChain.isEmpty() || isEnd(opEvent)) {
                this.mChain.add(opEvent);
            } else if (!isStart(opEvent)) {
                while (true) {
                    if (i3 >= this.mChain.size()) {
                        break;
                    }
                    com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent opEvent3 = this.mChain.get(i3);
                    if ((isStart(opEvent3) || opEvent3.mOpEvent.mNoteTime <= opEvent.mOpEvent.mNoteTime) && (i3 != this.mChain.size() - 1 || !isEnd(opEvent3))) {
                        if (i3 != this.mChain.size() - 1) {
                            i3++;
                        } else {
                            this.mChain.add(opEvent);
                            break;
                        }
                    }
                }
            } else {
                this.mChain.add(0, opEvent);
            }
            this.mStartEvent = isComplete() ? getStart() : null;
            this.mLastVisibleEvent = isComplete() ? getLastVisible() : null;
        }

        private boolean isEnd(com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent opEvent) {
            return (opEvent == null || (opEvent.mOpEvent.mAttributionFlags & 1) == 0) ? false : true;
        }

        private boolean isStart(com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent opEvent) {
            return (opEvent == null || (opEvent.mOpEvent.mAttributionFlags & 4) == 0) ? false : true;
        }
    }

    private final class DiscreteOps {
        int mChainIdOffset;
        int mLargestChainId;
        android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.DiscreteUidOps> mUids = new android.util.ArrayMap<>();

        DiscreteOps(int i) {
            this.mChainIdOffset = i;
            this.mLargestChainId = i;
        }

        boolean isEmpty() {
            return this.mUids.isEmpty();
        }

        void merge(com.android.server.appop.DiscreteRegistry.DiscreteOps discreteOps) {
            this.mLargestChainId = java.lang.Math.max(this.mLargestChainId, discreteOps.mLargestChainId);
            int size = discreteOps.mUids.size();
            for (int i = 0; i < size; i++) {
                int intValue = discreteOps.mUids.keyAt(i).intValue();
                getOrCreateDiscreteUidOps(intValue).merge(discreteOps.mUids.valueAt(i));
            }
        }

        void addDiscreteAccess(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, int i4, long j, long j2, int i5, int i6) {
            int i7;
            if (i6 == -1) {
                i7 = i6;
            } else {
                int i8 = this.mChainIdOffset + i6;
                if (i8 > this.mLargestChainId) {
                    this.mLargestChainId = i8;
                } else if (i8 < 0) {
                    this.mLargestChainId = 0;
                    this.mChainIdOffset = i6 * (-1);
                    i7 = 0;
                }
                i7 = i8;
            }
            getOrCreateDiscreteUidOps(i2).addDiscreteAccess(i, str, str2, i3, i4, j, j2, i5, i7);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(long j, long j2, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String[] strArr, @android.annotation.Nullable java.lang.String str2, int i3, android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap) {
            if ((i & 1) != 0) {
                android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.DiscreteUidOps> arrayMap2 = new android.util.ArrayMap<>();
                arrayMap2.put(java.lang.Integer.valueOf(i2), getOrCreateDiscreteUidOps(i2));
                this.mUids = arrayMap2;
            }
            for (int size = this.mUids.size() - 1; size >= 0; size--) {
                this.mUids.valueAt(size).filter(j, j2, i, str, strArr, str2, i3, this.mUids.keyAt(size).intValue(), arrayMap);
                if (this.mUids.valueAt(size).isEmpty()) {
                    this.mUids.removeAt(size);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void offsetHistory(long j) {
            int size = this.mUids.size();
            for (int i = 0; i < size; i++) {
                this.mUids.valueAt(i).offsetHistory(j);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearHistory(int i, java.lang.String str) {
            if (this.mUids.containsKey(java.lang.Integer.valueOf(i))) {
                this.mUids.get(java.lang.Integer.valueOf(i)).clearPackage(str);
                if (this.mUids.get(java.lang.Integer.valueOf(i)).isEmpty()) {
                    this.mUids.remove(java.lang.Integer.valueOf(i));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void applyToHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps, android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap) {
            int size = this.mUids.size();
            for (int i = 0; i < size; i++) {
                this.mUids.valueAt(i).applyToHistory(historicalOps, this.mUids.keyAt(i).intValue(), arrayMap);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToStream(java.io.FileOutputStream fileOutputStream) throws java.lang.Exception {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.startTag((java.lang.String) null, com.android.server.appop.DiscreteRegistry.TAG_HISTORY);
            resolveSerializer.attributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_VERSION, 1);
            resolveSerializer.attributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_LARGEST_CHAIN_ID, this.mLargestChainId);
            int size = this.mUids.size();
            for (int i = 0; i < size; i++) {
                resolveSerializer.startTag((java.lang.String) null, com.android.server.appop.DiscreteRegistry.TAG_UID);
                resolveSerializer.attributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_UID, this.mUids.keyAt(i).intValue());
                this.mUids.valueAt(i).serialize(resolveSerializer);
                resolveSerializer.endTag((java.lang.String) null, com.android.server.appop.DiscreteRegistry.TAG_UID);
            }
            resolveSerializer.endTag((java.lang.String) null, com.android.server.appop.DiscreteRegistry.TAG_HISTORY);
            resolveSerializer.endDocument();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.text.SimpleDateFormat simpleDateFormat, @android.annotation.NonNull java.util.Date date, @android.annotation.NonNull java.lang.String str, int i) {
            int size = this.mUids.size();
            for (int i2 = 0; i2 < size; i2++) {
                printWriter.print(str);
                printWriter.print("Uid: ");
                printWriter.print(this.mUids.keyAt(i2));
                printWriter.println();
                this.mUids.valueAt(i2).dump(printWriter, simpleDateFormat, date, str + "  ", i);
            }
        }

        private com.android.server.appop.DiscreteRegistry.DiscreteUidOps getOrCreateDiscreteUidOps(int i) {
            com.android.server.appop.DiscreteRegistry.DiscreteUidOps discreteUidOps = this.mUids.get(java.lang.Integer.valueOf(i));
            if (discreteUidOps == null) {
                com.android.server.appop.DiscreteRegistry.DiscreteUidOps discreteUidOps2 = com.android.server.appop.DiscreteRegistry.this.new DiscreteUidOps();
                this.mUids.put(java.lang.Integer.valueOf(i), discreteUidOps2);
                return discreteUidOps2;
            }
            return discreteUidOps;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readFromFile(java.io.File file, long j) {
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                try {
                    try {
                        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                        com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, com.android.server.appop.DiscreteRegistry.TAG_HISTORY);
                        if (resolvePullParser.getAttributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_VERSION) != 1) {
                            throw new java.lang.IllegalStateException("Dropping unsupported discrete history " + file);
                        }
                        int depth = resolvePullParser.getDepth();
                        while (com.android.internal.util.XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                            if (com.android.server.appop.DiscreteRegistry.TAG_UID.equals(resolvePullParser.getName())) {
                                getOrCreateDiscreteUidOps(resolvePullParser.getAttributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_UID, -1)).deserialize(resolvePullParser, j);
                            }
                        }
                        fileInputStream.close();
                    } catch (java.lang.Throwable th) {
                        try {
                            android.util.Slog.e(com.android.server.appop.DiscreteRegistry.TAG, "Failed to read file " + file.getName() + " " + th.getMessage() + " " + java.util.Arrays.toString(th.getStackTrace()));
                            fileInputStream.close();
                        } catch (java.lang.Throwable th2) {
                            try {
                                fileInputStream.close();
                            } catch (java.io.IOException e) {
                            }
                            throw th2;
                        }
                    }
                } catch (java.io.IOException e2) {
                }
            } catch (java.io.FileNotFoundException e3) {
            }
        }
    }

    private void createDiscreteAccessDir() {
        if (!this.mDiscreteAccessDir.exists()) {
            if (!this.mDiscreteAccessDir.mkdirs()) {
                android.util.Slog.e(TAG, "Failed to create DiscreteRegistry directory");
            }
            android.os.FileUtils.setPermissions(this.mDiscreteAccessDir.getPath(), 505, -1, -1);
        }
    }

    private void persistDiscreteOpsLocked(com.android.server.appop.DiscreteRegistry.DiscreteOps discreteOps) {
        java.io.FileOutputStream fileOutputStream;
        long epochMilli = java.time.Instant.now().toEpochMilli();
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(this.mDiscreteAccessDir, epochMilli + DISCRETE_HISTORY_FILE_SUFFIX));
        try {
            fileOutputStream = atomicFile.startWrite();
        } catch (java.lang.Throwable th) {
            th = th;
            fileOutputStream = null;
        }
        try {
            discreteOps.writeToStream(fileOutputStream);
            atomicFile.finishWrite(fileOutputStream);
        } catch (java.lang.Throwable th2) {
            th = th2;
            android.util.Slog.e(TAG, "Error writing timeline state: " + th.getMessage() + " " + java.util.Arrays.toString(th.getStackTrace()));
            if (fileOutputStream != null) {
                atomicFile.failWrite(fileOutputStream);
            }
        }
    }

    private void deleteOldDiscreteHistoryFilesLocked() {
        java.io.File[] listFiles = this.mDiscreteAccessDir.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (java.io.File file : listFiles) {
                java.lang.String name = file.getName();
                if (name.endsWith(DISCRETE_HISTORY_FILE_SUFFIX)) {
                    try {
                        if (java.time.Instant.now().minus(sDiscreteHistoryCutoff, (java.time.temporal.TemporalUnit) java.time.temporal.ChronoUnit.MILLIS).toEpochMilli() > java.lang.Long.valueOf(name.substring(0, name.length() - DISCRETE_HISTORY_FILE_SUFFIX.length())).longValue()) {
                            file.delete();
                            android.util.Slog.e(TAG, "Deleting file " + name);
                        }
                    } catch (java.lang.Throwable th) {
                        android.util.Slog.e(TAG, "Error while cleaning timeline files: ", th);
                    }
                }
            }
        }
    }

    private void createDiscreteAccessDirLocked() {
        if (!this.mDiscreteAccessDir.exists()) {
            if (!this.mDiscreteAccessDir.mkdirs()) {
                android.util.Slog.e(TAG, "Failed to create DiscreteRegistry directory");
            }
            android.os.FileUtils.setPermissions(this.mDiscreteAccessDir.getPath(), 505, -1, -1);
        }
    }

    private final class DiscreteUidOps {
        android.util.ArrayMap<java.lang.String, com.android.server.appop.DiscreteRegistry.DiscretePackageOps> mPackages = new android.util.ArrayMap<>();

        DiscreteUidOps() {
        }

        boolean isEmpty() {
            return this.mPackages.isEmpty();
        }

        void merge(com.android.server.appop.DiscreteRegistry.DiscreteUidOps discreteUidOps) {
            int size = discreteUidOps.mPackages.size();
            for (int i = 0; i < size; i++) {
                java.lang.String keyAt = discreteUidOps.mPackages.keyAt(i);
                getOrCreateDiscretePackageOps(keyAt).merge(discreteUidOps.mPackages.valueAt(i));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(long j, long j2, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String[] strArr, @android.annotation.Nullable java.lang.String str2, int i2, int i3, android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap) {
            if ((i & 2) != 0) {
                android.util.ArrayMap<java.lang.String, com.android.server.appop.DiscreteRegistry.DiscretePackageOps> arrayMap2 = new android.util.ArrayMap<>();
                arrayMap2.put(str, getOrCreateDiscretePackageOps(str));
                this.mPackages = arrayMap2;
            }
            for (int size = this.mPackages.size() - 1; size >= 0; size--) {
                this.mPackages.valueAt(size).filter(j, j2, i, strArr, str2, i2, i3, this.mPackages.keyAt(size), arrayMap);
                if (this.mPackages.valueAt(size).isEmpty()) {
                    this.mPackages.removeAt(size);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void offsetHistory(long j) {
            int size = this.mPackages.size();
            for (int i = 0; i < size; i++) {
                this.mPackages.valueAt(i).offsetHistory(j);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPackage(java.lang.String str) {
            this.mPackages.remove(str);
        }

        void addDiscreteAccess(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, int i3, long j, long j2, int i4, int i5) {
            getOrCreateDiscretePackageOps(str).addDiscreteAccess(i, str2, i2, i3, j, j2, i4, i5);
        }

        private com.android.server.appop.DiscreteRegistry.DiscretePackageOps getOrCreateDiscretePackageOps(java.lang.String str) {
            com.android.server.appop.DiscreteRegistry.DiscretePackageOps discretePackageOps = this.mPackages.get(str);
            if (discretePackageOps == null) {
                com.android.server.appop.DiscreteRegistry.DiscretePackageOps discretePackageOps2 = com.android.server.appop.DiscreteRegistry.this.new DiscretePackageOps();
                this.mPackages.put(str, discretePackageOps2);
                return discretePackageOps2;
            }
            return discretePackageOps;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void applyToHistory(android.app.AppOpsManager.HistoricalOps historicalOps, int i, @android.annotation.NonNull android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap) {
            int size = this.mPackages.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mPackages.valueAt(i2).applyToHistory(historicalOps, i, this.mPackages.keyAt(i2), arrayMap);
            }
        }

        void serialize(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.lang.Exception {
            int size = this.mPackages.size();
            for (int i = 0; i < size; i++) {
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.appop.DiscreteRegistry.TAG_PACKAGE);
                typedXmlSerializer.attribute((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_PACKAGE_NAME, this.mPackages.keyAt(i));
                this.mPackages.valueAt(i).serialize(typedXmlSerializer);
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.appop.DiscreteRegistry.TAG_PACKAGE);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.text.SimpleDateFormat simpleDateFormat, @android.annotation.NonNull java.util.Date date, @android.annotation.NonNull java.lang.String str, int i) {
            int size = this.mPackages.size();
            for (int i2 = 0; i2 < size; i2++) {
                printWriter.print(str);
                printWriter.print("Package: ");
                printWriter.print(this.mPackages.keyAt(i2));
                printWriter.println();
                this.mPackages.valueAt(i2).dump(printWriter, simpleDateFormat, date, str + "  ", i);
            }
        }

        void deserialize(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, long j) throws java.lang.Exception {
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if (com.android.server.appop.DiscreteRegistry.TAG_PACKAGE.equals(typedXmlPullParser.getName())) {
                    getOrCreateDiscretePackageOps(typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_PACKAGE_NAME)).deserialize(typedXmlPullParser, j);
                }
            }
        }
    }

    private final class DiscretePackageOps {
        android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.DiscreteOp> mPackageOps = new android.util.ArrayMap<>();

        DiscretePackageOps() {
        }

        boolean isEmpty() {
            return this.mPackageOps.isEmpty();
        }

        void addDiscreteAccess(int i, @android.annotation.Nullable java.lang.String str, int i2, int i3, long j, long j2, int i4, int i5) {
            getOrCreateDiscreteOp(i).addDiscreteAccess(str, i2, i3, j, j2, i4, i5);
        }

        void merge(com.android.server.appop.DiscreteRegistry.DiscretePackageOps discretePackageOps) {
            int size = discretePackageOps.mPackageOps.size();
            for (int i = 0; i < size; i++) {
                int intValue = discretePackageOps.mPackageOps.keyAt(i).intValue();
                getOrCreateDiscreteOp(intValue).merge(discretePackageOps.mPackageOps.valueAt(i));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(long j, long j2, int i, @android.annotation.Nullable java.lang.String[] strArr, @android.annotation.Nullable java.lang.String str, int i2, int i3, java.lang.String str2, android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap) {
            for (int size = this.mPackageOps.size() - 1; size >= 0; size--) {
                int intValue = this.mPackageOps.keyAt(size).intValue();
                if ((i & 8) != 0 && !com.android.internal.util.ArrayUtils.contains(strArr, android.app.AppOpsManager.opToPublicName(intValue))) {
                    this.mPackageOps.removeAt(size);
                }
                this.mPackageOps.valueAt(size).filter(j, j2, i, str, i2, i3, str2, this.mPackageOps.keyAt(size).intValue(), arrayMap);
                if (this.mPackageOps.valueAt(size).isEmpty()) {
                    this.mPackageOps.removeAt(size);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void offsetHistory(long j) {
            int size = this.mPackageOps.size();
            for (int i = 0; i < size; i++) {
                this.mPackageOps.valueAt(i).offsetHistory(j);
            }
        }

        private com.android.server.appop.DiscreteRegistry.DiscreteOp getOrCreateDiscreteOp(int i) {
            com.android.server.appop.DiscreteRegistry.DiscreteOp discreteOp = this.mPackageOps.get(java.lang.Integer.valueOf(i));
            if (discreteOp == null) {
                com.android.server.appop.DiscreteRegistry.DiscreteOp discreteOp2 = com.android.server.appop.DiscreteRegistry.this.new DiscreteOp();
                this.mPackageOps.put(java.lang.Integer.valueOf(i), discreteOp2);
                return discreteOp2;
            }
            return discreteOp;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void applyToHistory(android.app.AppOpsManager.HistoricalOps historicalOps, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap) {
            int size = this.mPackageOps.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mPackageOps.valueAt(i2).applyToHistory(historicalOps, i, str, this.mPackageOps.keyAt(i2).intValue(), arrayMap);
            }
        }

        void serialize(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.lang.Exception {
            int size = this.mPackageOps.size();
            for (int i = 0; i < size; i++) {
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.appop.DiscreteRegistry.TAG_OP);
                typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_OP_ID, this.mPackageOps.keyAt(i).intValue());
                this.mPackageOps.valueAt(i).serialize(typedXmlSerializer);
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.appop.DiscreteRegistry.TAG_OP);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.text.SimpleDateFormat simpleDateFormat, @android.annotation.NonNull java.util.Date date, @android.annotation.NonNull java.lang.String str, int i) {
            int size = this.mPackageOps.size();
            for (int i2 = 0; i2 < size; i2++) {
                printWriter.print(str);
                printWriter.print(android.app.AppOpsManager.opToName(this.mPackageOps.keyAt(i2).intValue()));
                printWriter.println();
                this.mPackageOps.valueAt(i2).dump(printWriter, simpleDateFormat, date, str + "  ", i);
            }
        }

        void deserialize(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, long j) throws java.lang.Exception {
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if (com.android.server.appop.DiscreteRegistry.TAG_OP.equals(typedXmlPullParser.getName())) {
                    getOrCreateDiscreteOp(typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_OP_ID)).deserialize(typedXmlPullParser, j);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DiscreteOp {
        android.util.ArrayMap<java.lang.String, java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent>> mAttributedOps = new android.util.ArrayMap<>();

        DiscreteOp() {
        }

        boolean isEmpty() {
            return this.mAttributedOps.isEmpty();
        }

        void merge(com.android.server.appop.DiscreteRegistry.DiscreteOp discreteOp) {
            int size = discreteOp.mAttributedOps.size();
            for (int i = 0; i < size; i++) {
                java.lang.String keyAt = discreteOp.mAttributedOps.keyAt(i);
                java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> valueAt = discreteOp.mAttributedOps.valueAt(i);
                this.mAttributedOps.put(keyAt, com.android.server.appop.DiscreteRegistry.stableListMerge(getOrCreateDiscreteOpEventsList(keyAt), valueAt));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(long j, long j2, int i, @android.annotation.Nullable java.lang.String str, int i2, int i3, java.lang.String str2, int i4, android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap) {
            if ((i & 4) != 0) {
                android.util.ArrayMap<java.lang.String, java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent>> arrayMap2 = new android.util.ArrayMap<>();
                arrayMap2.put(str, getOrCreateDiscreteOpEventsList(str));
                this.mAttributedOps = arrayMap2;
            }
            for (int size = this.mAttributedOps.size() - 1; size >= 0; size--) {
                java.lang.String keyAt = this.mAttributedOps.keyAt(size);
                java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> filterEventsList = com.android.server.appop.DiscreteRegistry.filterEventsList(this.mAttributedOps.valueAt(size), j, j2, i2, i3, str2, i4, this.mAttributedOps.keyAt(size), arrayMap);
                this.mAttributedOps.put(keyAt, filterEventsList);
                if (filterEventsList.size() == 0) {
                    this.mAttributedOps.removeAt(size);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void offsetHistory(long j) {
            com.android.server.appop.DiscreteRegistry.DiscreteOp discreteOp = this;
            int size = discreteOp.mAttributedOps.size();
            int i = 0;
            while (i < size) {
                java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> valueAt = discreteOp.mAttributedOps.valueAt(i);
                int size2 = valueAt.size();
                int i2 = 0;
                while (i2 < size2) {
                    com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent = valueAt.get(i2);
                    valueAt.set(i2, com.android.server.appop.DiscreteRegistry.this.new DiscreteOpEvent(discreteOpEvent.mNoteTime - j, discreteOpEvent.mNoteDuration, discreteOpEvent.mUidState, discreteOpEvent.mOpFlag, discreteOpEvent.mAttributionFlags, discreteOpEvent.mAttributionChainId));
                    i2++;
                    discreteOp = this;
                }
                i++;
                discreteOp = this;
            }
        }

        void addDiscreteAccess(@android.annotation.Nullable java.lang.String str, int i, int i2, long j, long j2, int i3, int i4) {
            java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> orCreateDiscreteOpEventsList = getOrCreateDiscreteOpEventsList(str);
            int size = orCreateDiscreteOpEventsList.size();
            while (true) {
                if (size <= 0) {
                    break;
                }
                com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent = orCreateDiscreteOpEventsList.get(size - 1);
                if (com.android.server.appop.DiscreteRegistry.discretizeTimeStamp(discreteOpEvent.mNoteTime) < com.android.server.appop.DiscreteRegistry.discretizeTimeStamp(j)) {
                    break;
                }
                if (discreteOpEvent.mOpFlag == i && discreteOpEvent.mUidState == i2) {
                    if (discreteOpEvent.mAttributionFlags == i3) {
                        if (discreteOpEvent.mAttributionChainId != i4) {
                            size--;
                        } else if (com.android.server.appop.DiscreteRegistry.discretizeDuration(j2) == com.android.server.appop.DiscreteRegistry.discretizeDuration(discreteOpEvent.mNoteDuration)) {
                            return;
                        }
                    }
                    size--;
                }
                size--;
            }
            orCreateDiscreteOpEventsList.add(size, com.android.server.appop.DiscreteRegistry.this.new DiscreteOpEvent(j, j2, i2, i, i3, i4));
        }

        private java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> getOrCreateDiscreteOpEventsList(java.lang.String str) {
            java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> list = this.mAttributedOps.get(str);
            if (list == null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                this.mAttributedOps.put(str, arrayList);
                return arrayList;
            }
            return list;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void applyToHistory(android.app.AppOpsManager.HistoricalOps historicalOps, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap) {
            android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo;
            com.android.server.appop.DiscreteRegistry.AttributionChain attributionChain;
            int size = this.mAttributedOps.size();
            for (int i3 = 0; i3 < size; i3++) {
                java.lang.String keyAt = this.mAttributedOps.keyAt(i3);
                java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> valueAt = this.mAttributedOps.valueAt(i3);
                int size2 = valueAt.size();
                int i4 = 0;
                while (i4 < size2) {
                    com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent = valueAt.get(i4);
                    if (discreteOpEvent.mAttributionChainId != -1 && arrayMap != null && (attributionChain = arrayMap.get(java.lang.Integer.valueOf(discreteOpEvent.mAttributionChainId))) != null && attributionChain.isComplete() && attributionChain.isStart(str, i, keyAt, i2, discreteOpEvent) && attributionChain.mLastVisibleEvent != null) {
                        com.android.server.appop.DiscreteRegistry.AttributionChain.OpEvent opEvent = attributionChain.mLastVisibleEvent;
                        opEventProxyInfo = new android.app.AppOpsManager.OpEventProxyInfo(opEvent.mUid, opEvent.mPkgName, opEvent.mAttributionTag);
                    } else {
                        opEventProxyInfo = null;
                    }
                    historicalOps.addDiscreteAccess(i2, i, str, keyAt, discreteOpEvent.mUidState, discreteOpEvent.mOpFlag, com.android.server.appop.DiscreteRegistry.discretizeTimeStamp(discreteOpEvent.mNoteTime), com.android.server.appop.DiscreteRegistry.discretizeDuration(discreteOpEvent.mNoteDuration), opEventProxyInfo);
                    i4++;
                    size2 = size2;
                    valueAt = valueAt;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.text.SimpleDateFormat simpleDateFormat, @android.annotation.NonNull java.util.Date date, @android.annotation.NonNull java.lang.String str, int i) {
            int size = this.mAttributedOps.size();
            for (int i2 = 0; i2 < size; i2++) {
                printWriter.print(str);
                printWriter.print("Attribution: ");
                printWriter.print(this.mAttributedOps.keyAt(i2));
                printWriter.println();
                java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> valueAt = this.mAttributedOps.valueAt(i2);
                int size2 = valueAt.size();
                for (int max = i < 1 ? 0 : java.lang.Math.max(0, size2 - i); max < size2; max++) {
                    valueAt.get(max).dump(printWriter, simpleDateFormat, date, str + "  ");
                }
            }
        }

        void serialize(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.lang.Exception {
            int size = this.mAttributedOps.size();
            for (int i = 0; i < size; i++) {
                typedXmlSerializer.startTag((java.lang.String) null, "a");
                if (this.mAttributedOps.keyAt(i) != null) {
                    typedXmlSerializer.attribute((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_TAG, this.mAttributedOps.keyAt(i));
                }
                java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> valueAt = this.mAttributedOps.valueAt(i);
                int size2 = valueAt.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    typedXmlSerializer.startTag((java.lang.String) null, com.android.server.appop.DiscreteRegistry.TAG_ENTRY);
                    valueAt.get(i2).serialize(typedXmlSerializer);
                    typedXmlSerializer.endTag((java.lang.String) null, com.android.server.appop.DiscreteRegistry.TAG_ENTRY);
                }
                typedXmlSerializer.endTag((java.lang.String) null, "a");
            }
        }

        void deserialize(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, long j) throws java.lang.Exception {
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                if ("a".equals(typedXmlPullParser.getName())) {
                    java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> orCreateDiscreteOpEventsList = getOrCreateDiscreteOpEventsList(typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_TAG));
                    int depth2 = typedXmlPullParser.getDepth();
                    while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                        if (com.android.server.appop.DiscreteRegistry.TAG_ENTRY.equals(typedXmlPullParser.getName())) {
                            long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_NOTE_TIME);
                            long attributeLong2 = typedXmlPullParser.getAttributeLong((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_NOTE_DURATION, -1L);
                            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_UID_STATE);
                            int attributeInt2 = typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_FLAGS);
                            int attributeInt3 = typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_ATTRIBUTION_FLAGS, 0);
                            int attributeInt4 = typedXmlPullParser.getAttributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_CHAIN_ID, -1);
                            if (attributeLong + attributeLong2 >= j) {
                                orCreateDiscreteOpEventsList.add(com.android.server.appop.DiscreteRegistry.this.new DiscreteOpEvent(attributeLong, attributeLong2, attributeInt, attributeInt2, attributeInt3, attributeInt4));
                            }
                        }
                    }
                    java.util.Collections.sort(orCreateDiscreteOpEventsList, new java.util.Comparator() { // from class: com.android.server.appop.DiscreteRegistry$DiscreteOp$$ExternalSyntheticLambda0
                        @Override // java.util.Comparator
                        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                            int lambda$deserialize$0;
                            lambda$deserialize$0 = com.android.server.appop.DiscreteRegistry.DiscreteOp.lambda$deserialize$0((com.android.server.appop.DiscreteRegistry.DiscreteOpEvent) obj, (com.android.server.appop.DiscreteRegistry.DiscreteOpEvent) obj2);
                            return lambda$deserialize$0;
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$deserialize$0(com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent, com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent2) {
            if (discreteOpEvent.mNoteTime < discreteOpEvent2.mNoteTime) {
                return -1;
            }
            return discreteOpEvent.mNoteTime == discreteOpEvent2.mNoteTime ? 0 : 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DiscreteOpEvent {
        final int mAttributionChainId;
        final int mAttributionFlags;
        final long mNoteDuration;
        final long mNoteTime;
        final int mOpFlag;
        final int mUidState;

        DiscreteOpEvent(long j, long j2, int i, int i2, int i3, int i4) {
            this.mNoteTime = j;
            this.mNoteDuration = j2;
            this.mUidState = i;
            this.mOpFlag = i2;
            this.mAttributionFlags = i3;
            this.mAttributionChainId = i4;
        }

        public boolean equalsExceptDuration(com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent) {
            return this.mNoteTime == discreteOpEvent.mNoteTime && this.mUidState == discreteOpEvent.mUidState && this.mOpFlag == discreteOpEvent.mOpFlag && this.mAttributionFlags == discreteOpEvent.mAttributionFlags && this.mAttributionChainId == discreteOpEvent.mAttributionChainId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.text.SimpleDateFormat simpleDateFormat, @android.annotation.NonNull java.util.Date date, @android.annotation.NonNull java.lang.String str) {
            printWriter.print(str);
            printWriter.print("Access [");
            printWriter.print(android.app.AppOpsManager.getUidStateName(this.mUidState));
            printWriter.print("-");
            printWriter.print(android.app.AppOpsManager.flagsToString(this.mOpFlag));
            printWriter.print("] at ");
            date.setTime(com.android.server.appop.DiscreteRegistry.discretizeTimeStamp(this.mNoteTime));
            printWriter.print(simpleDateFormat.format(date));
            if (this.mNoteDuration != -1) {
                printWriter.print(" for ");
                printWriter.print(com.android.server.appop.DiscreteRegistry.discretizeDuration(this.mNoteDuration));
                printWriter.print(" milliseconds ");
            }
            if (this.mAttributionFlags != 0) {
                printWriter.print(" attribution flags=");
                printWriter.print(this.mAttributionFlags);
                printWriter.print(" with chainId=");
                printWriter.print(this.mAttributionChainId);
            }
            printWriter.println();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void serialize(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.lang.Exception {
            typedXmlSerializer.attributeLong((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_NOTE_TIME, this.mNoteTime);
            if (this.mNoteDuration != -1) {
                typedXmlSerializer.attributeLong((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_NOTE_DURATION, this.mNoteDuration);
            }
            if (this.mAttributionFlags != 0) {
                typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_ATTRIBUTION_FLAGS, this.mAttributionFlags);
            }
            if (this.mAttributionChainId != -1) {
                typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_CHAIN_ID, this.mAttributionChainId);
            }
            typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_UID_STATE, this.mUidState);
            typedXmlSerializer.attributeInt((java.lang.String) null, com.android.server.appop.DiscreteRegistry.ATTR_FLAGS, this.mOpFlag);
        }
    }

    private static int[] parseOpsList(java.lang.String str) {
        java.lang.String[] split;
        if (str.isEmpty()) {
            split = new java.lang.String[0];
        } else {
            split = str.split(",");
        }
        int length = split.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            try {
                iArr[i] = java.lang.Integer.parseInt(split[i]);
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.e(TAG, "Failed to parse Discrete ops list: " + e.getMessage());
                return parseOpsList(DEFAULT_DISCRETE_OPS);
            }
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> stableListMerge(java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> list, java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> list2) {
        int size = list.size();
        int size2 = list2.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size + size2);
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i < size || i2 < size2) {
                if (i == size) {
                    arrayList.add(list2.get(i2));
                    i2++;
                } else if (i2 == size2) {
                    arrayList.add(list.get(i));
                    i++;
                } else if (list.get(i).mNoteTime < list2.get(i2).mNoteTime) {
                    arrayList.add(list.get(i));
                    i++;
                } else {
                    arrayList.add(list2.get(i2));
                    i2++;
                }
            } else {
                return arrayList;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> filterEventsList(java.util.List<com.android.server.appop.DiscreteRegistry.DiscreteOpEvent> list, long j, long j2, int i, int i2, java.lang.String str, int i3, java.lang.String str2, android.util.ArrayMap<java.lang.Integer, com.android.server.appop.DiscreteRegistry.AttributionChain> arrayMap) {
        int size = list.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i4 = 0; i4 < size; i4++) {
            com.android.server.appop.DiscreteRegistry.DiscreteOpEvent discreteOpEvent = list.get(i4);
            com.android.server.appop.DiscreteRegistry.AttributionChain attributionChain = arrayMap.get(java.lang.Integer.valueOf(discreteOpEvent.mAttributionChainId));
            if ((attributionChain == null || attributionChain.isStart(str, i2, str2, i3, discreteOpEvent) || !attributionChain.isComplete() || discreteOpEvent.mAttributionChainId == -1) && (discreteOpEvent.mOpFlag & i) != 0 && discreteOpEvent.mNoteTime + discreteOpEvent.mNoteDuration > j && discreteOpEvent.mNoteTime < j2) {
                arrayList.add(discreteOpEvent);
            }
        }
        return arrayList;
    }

    private static boolean isDiscreteOp(int i, int i2) {
        return com.android.internal.util.ArrayUtils.contains(sDiscreteOps, i) && (sDiscreteFlags & i2) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long discretizeTimeStamp(long j) {
        return (j / sDiscreteHistoryQuantization) * sDiscreteHistoryQuantization;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long discretizeDuration(long j) {
        if (j == -1) {
            return -1L;
        }
        return sDiscreteHistoryQuantization * (((j + sDiscreteHistoryQuantization) - 1) / sDiscreteHistoryQuantization);
    }

    void setDebugMode(boolean z) {
        this.mDebugMode = z;
    }
}
