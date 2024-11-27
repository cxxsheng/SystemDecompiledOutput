package com.android.server.os;

/* loaded from: classes2.dex */
public final class NativeTombstoneManager {
    private static final java.lang.String TAG = com.android.server.os.NativeTombstoneManager.class.getSimpleName();
    private static final java.io.File TOMBSTONE_DIR = new java.io.File("/data/tombstones");
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final com.android.server.os.NativeTombstoneManager.TombstoneWatcher mWatcher;
    private final java.util.concurrent.locks.ReentrantLock mTmpFileLock = new java.util.concurrent.locks.ReentrantLock();
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.os.NativeTombstoneManager.TombstoneFile> mTombstones = new android.util.SparseArray<>();

    NativeTombstoneManager(android.content.Context context) {
        this.mContext = context;
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(TAG + ":tombstoneWatcher", 10, true);
        serviceThread.start();
        this.mHandler = serviceThread.getThreadHandler();
        this.mWatcher = new com.android.server.os.NativeTombstoneManager.TombstoneWatcher();
        this.mWatcher.startWatching();
    }

    void onSystemReady() {
        registerForUserRemoval();
        registerForPackageRemoval();
        com.android.server.BootReceiver.initDropboxRateLimiter();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.os.NativeTombstoneManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.os.NativeTombstoneManager.this.lambda$onSystemReady$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$0() {
        java.io.File[] listFiles = TOMBSTONE_DIR.listFiles();
        for (int i = 0; listFiles != null && i < listFiles.length; i++) {
            if (listFiles[i].isFile()) {
                handleTombstone(listFiles[i]);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTombstone(java.io.File file) {
        java.io.File file2;
        java.lang.String str;
        java.lang.String name = file.getName();
        if (name.endsWith(".tmp")) {
            this.mTmpFileLock.lock();
            try {
                file.delete();
                return;
            } finally {
                this.mTmpFileLock.unlock();
            }
        }
        if (!name.startsWith("tombstone_")) {
            return;
        }
        boolean endsWith = name.endsWith(".pb");
        if (endsWith) {
            file2 = file;
        } else {
            file2 = new java.io.File(file.getAbsolutePath() + ".pb");
        }
        java.util.Optional<com.android.server.os.NativeTombstoneManager.TombstoneFile> handleProtoTombstone = handleProtoTombstone(file2, endsWith);
        if (!handleProtoTombstone.isPresent()) {
            str = "UNKNOWN";
        } else {
            str = handleProtoTombstone.get().getProcessName();
        }
        com.android.server.BootReceiver.addTombstoneToDropBox(this.mContext, file, endsWith, str, this.mTmpFileLock);
    }

    private java.util.Optional<com.android.server.os.NativeTombstoneManager.TombstoneFile> handleProtoTombstone(java.io.File file, boolean z) {
        java.lang.String name = file.getName();
        if (!name.endsWith(".pb")) {
            android.util.Slog.w(TAG, "unexpected tombstone name: " + file);
            return java.util.Optional.empty();
        }
        try {
            int parseInt = java.lang.Integer.parseInt(name.substring("tombstone_".length()).substring(0, r0.length() - 3));
            if (parseInt < 0 || parseInt > 99) {
                android.util.Slog.w(TAG, "unexpected tombstone name: " + file);
                return java.util.Optional.empty();
            }
            try {
                android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, 805306368);
                java.util.Optional<com.android.server.os.NativeTombstoneManager.TombstoneFile> parse = com.android.server.os.NativeTombstoneManager.TombstoneFile.parse(open);
                if (!parse.isPresent()) {
                    libcore.io.IoUtils.closeQuietly(open);
                    return java.util.Optional.empty();
                }
                if (z) {
                    synchronized (this.mLock) {
                        try {
                            com.android.server.os.NativeTombstoneManager.TombstoneFile tombstoneFile = this.mTombstones.get(parseInt);
                            if (tombstoneFile != null) {
                                tombstoneFile.dispose();
                            }
                            this.mTombstones.put(parseInt, parse.get());
                        } finally {
                        }
                    }
                }
                return parse;
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.w(TAG, "failed to open " + file, e);
                return java.util.Optional.empty();
            }
        } catch (java.lang.NumberFormatException e2) {
            android.util.Slog.w(TAG, "unexpected tombstone name: " + file);
            return java.util.Optional.empty();
        }
    }

    public void purge(final java.util.Optional<java.lang.Integer> optional, final java.util.Optional<java.lang.Integer> optional2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.os.NativeTombstoneManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.os.NativeTombstoneManager.this.lambda$purge$1(optional, optional2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$purge$1(java.util.Optional optional, java.util.Optional optional2) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mTombstones.size() - 1; size >= 0; size--) {
                    com.android.server.os.NativeTombstoneManager.TombstoneFile valueAt = this.mTombstones.valueAt(size);
                    if (valueAt.matches(optional, optional2)) {
                        valueAt.purge();
                        this.mTombstones.removeAt(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void purgePackage(int i, boolean z) {
        java.util.Optional<java.lang.Integer> of;
        int appId = android.os.UserHandle.getAppId(i);
        if (z) {
            of = java.util.Optional.empty();
        } else {
            of = java.util.Optional.of(java.lang.Integer.valueOf(android.os.UserHandle.getUserId(i)));
        }
        purge(of, java.util.Optional.of(java.lang.Integer.valueOf(appId)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void purgeUser(int i) {
        purge(java.util.Optional.of(java.lang.Integer.valueOf(i)), java.util.Optional.empty());
    }

    private void registerForPackageRemoval() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverForAllUsers(new android.content.BroadcastReceiver() { // from class: com.android.server.os.NativeTombstoneManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.UID", com.android.server.am.ProcessList.INVALID_ADJ);
                if (intExtra == -10000) {
                    return;
                }
                com.android.server.os.NativeTombstoneManager.this.purgePackage(intExtra, intent.getBooleanExtra("android.intent.extra.REMOVED_FOR_ALL_USERS", false));
            }
        }, intentFilter, null, this.mHandler);
    }

    private void registerForUserRemoval() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiverForAllUsers(new android.content.BroadcastReceiver() { // from class: com.android.server.os.NativeTombstoneManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra < 1) {
                    return;
                }
                com.android.server.os.NativeTombstoneManager.this.purgeUser(intExtra);
            }
        }, intentFilter, null, this.mHandler);
    }

    public void collectTombstones(final java.util.ArrayList<android.app.ApplicationExitInfo> arrayList, int i, final int i2, final int i3) {
        final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        if (!android.os.UserHandle.isApp(i)) {
            return;
        }
        final int userId = android.os.UserHandle.getUserId(i);
        final int appId = android.os.UserHandle.getAppId(i);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.os.NativeTombstoneManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.os.NativeTombstoneManager.this.lambda$collectTombstones$3(userId, appId, i2, arrayList, i3, completableFuture);
            }
        });
        try {
            completableFuture.get();
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collectTombstones$3(int i, int i2, int i3, java.util.ArrayList arrayList, int i4, java.util.concurrent.CompletableFuture completableFuture) {
        boolean z;
        synchronized (this.mLock) {
            try {
                int size = this.mTombstones.size();
                z = false;
                for (int i5 = 0; i5 < size; i5++) {
                    com.android.server.os.NativeTombstoneManager.TombstoneFile valueAt = this.mTombstones.valueAt(i5);
                    if (valueAt.matches(java.util.Optional.of(java.lang.Integer.valueOf(i)), java.util.Optional.of(java.lang.Integer.valueOf(i2))) && (i3 == 0 || valueAt.mPid == i3)) {
                        int size2 = arrayList.size();
                        int i6 = 0;
                        while (true) {
                            if (i6 < size2) {
                                android.app.ApplicationExitInfo applicationExitInfo = (android.app.ApplicationExitInfo) arrayList.get(i6);
                                if (!valueAt.matches(applicationExitInfo)) {
                                    i6++;
                                } else {
                                    applicationExitInfo.setNativeTombstoneRetriever(valueAt.getPfdRetriever());
                                    break;
                                }
                            } else if (arrayList.size() < i4) {
                                arrayList.add(valueAt.toAppExitInfo());
                                z = true;
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            java.util.Collections.sort(arrayList, new java.util.Comparator() { // from class: com.android.server.os.NativeTombstoneManager$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$collectTombstones$2;
                    lambda$collectTombstones$2 = com.android.server.os.NativeTombstoneManager.lambda$collectTombstones$2((android.app.ApplicationExitInfo) obj, (android.app.ApplicationExitInfo) obj2);
                    return lambda$collectTombstones$2;
                }
            });
        }
        completableFuture.complete(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$collectTombstones$2(android.app.ApplicationExitInfo applicationExitInfo, android.app.ApplicationExitInfo applicationExitInfo2) {
        long timestamp = applicationExitInfo2.getTimestamp() - applicationExitInfo.getTimestamp();
        if (timestamp < 0) {
            return -1;
        }
        if (timestamp == 0) {
            return 0;
        }
        return 1;
    }

    static class TombstoneFile {
        int mAppId;
        java.lang.String mCrashReason;
        final android.os.ParcelFileDescriptor mPfd;
        int mPid;
        java.lang.String mProcessName;
        boolean mPurged = false;
        final android.app.IParcelFileDescriptorRetriever mRetriever = new com.android.server.os.NativeTombstoneManager.TombstoneFile.ParcelFileDescriptorRetriever();
        long mTimestampMs;
        int mUid;
        int mUserId;

        TombstoneFile(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            this.mPfd = parcelFileDescriptor;
        }

        public boolean matches(java.util.Optional<java.lang.Integer> optional, java.util.Optional<java.lang.Integer> optional2) {
            if (this.mPurged) {
                return false;
            }
            if (!optional.isPresent() || optional.get().intValue() == this.mUserId) {
                return !optional2.isPresent() || optional2.get().intValue() == this.mAppId;
            }
            return false;
        }

        public boolean matches(android.app.ApplicationExitInfo applicationExitInfo) {
            return applicationExitInfo.getReason() == 5 && applicationExitInfo.getPid() == this.mPid && applicationExitInfo.getRealUid() == this.mUid && java.lang.Math.abs(applicationExitInfo.getTimestamp() - this.mTimestampMs) <= com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        }

        public java.lang.String getProcessName() {
            return this.mProcessName;
        }

        public void dispose() {
            libcore.io.IoUtils.closeQuietly(this.mPfd);
        }

        public void purge() {
            if (!this.mPurged) {
                try {
                    android.system.Os.ftruncate(this.mPfd.getFileDescriptor(), 0L);
                } catch (android.system.ErrnoException e) {
                    android.util.Slog.e(com.android.server.os.NativeTombstoneManager.TAG, "Failed to truncate tombstone", e);
                }
                this.mPurged = true;
            }
        }

        static java.util.Optional<com.android.server.os.NativeTombstoneManager.TombstoneFile> parse(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            long j;
            android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor()));
            int i = 0;
            java.lang.String str = null;
            java.lang.String str2 = "";
            java.lang.String str3 = str2;
            int i2 = 0;
            while (protoInputStream.nextField() != -1) {
                try {
                    switch (protoInputStream.getFieldNumber()) {
                        case 5:
                            i2 = protoInputStream.readInt(1155346202629L);
                            break;
                        case 7:
                            i = protoInputStream.readInt(1155346202631L);
                            break;
                        case 8:
                            str3 = protoInputStream.readString(1138166333448L);
                            break;
                        case 9:
                            if (str != null) {
                                break;
                            } else {
                                str = protoInputStream.readString(2237677961225L);
                                break;
                            }
                        case 15:
                            if (!str2.equals("")) {
                                break;
                            } else {
                                long start = protoInputStream.start(2246267895823L);
                                while (true) {
                                    if (protoInputStream.nextField() != -1) {
                                        switch (protoInputStream.getFieldNumber()) {
                                            case 1:
                                                str2 = protoInputStream.readString(1138166333441L);
                                                break;
                                        }
                                    }
                                }
                                protoInputStream.end(start);
                                break;
                            }
                    }
                } catch (java.io.IOException | android.util.proto.ProtoParseException e) {
                    android.util.Slog.e(com.android.server.os.NativeTombstoneManager.TAG, "Failed to parse tombstone", e);
                    return java.util.Optional.empty();
                }
            }
            if (!android.os.UserHandle.isApp(i)) {
                android.util.Slog.e(com.android.server.os.NativeTombstoneManager.TAG, "Tombstone's UID (" + i + ") not an app, ignoring");
                return java.util.Optional.empty();
            }
            try {
                android.system.StructStat fstat = android.system.Os.fstat(parcelFileDescriptor.getFileDescriptor());
                j = (fstat.st_atim.tv_sec * 1000) + (fstat.st_atim.tv_nsec / 1000000);
            } catch (android.system.ErrnoException e2) {
                android.util.Slog.e(com.android.server.os.NativeTombstoneManager.TAG, "Failed to get timestamp of tombstone", e2);
                j = 0;
            }
            int userId = android.os.UserHandle.getUserId(i);
            int appId = android.os.UserHandle.getAppId(i);
            if (!str3.startsWith("u:r:untrusted_app")) {
                android.util.Slog.e(com.android.server.os.NativeTombstoneManager.TAG, "Tombstone has invalid selinux label (" + str3 + "), ignoring");
                return java.util.Optional.empty();
            }
            com.android.server.os.NativeTombstoneManager.TombstoneFile tombstoneFile = new com.android.server.os.NativeTombstoneManager.TombstoneFile(parcelFileDescriptor);
            tombstoneFile.mUserId = userId;
            tombstoneFile.mAppId = appId;
            tombstoneFile.mPid = i2;
            tombstoneFile.mUid = i;
            tombstoneFile.mProcessName = str != null ? str : "";
            tombstoneFile.mTimestampMs = j;
            tombstoneFile.mCrashReason = str2;
            return java.util.Optional.of(tombstoneFile);
        }

        public android.app.IParcelFileDescriptorRetriever getPfdRetriever() {
            return this.mRetriever;
        }

        public android.app.ApplicationExitInfo toAppExitInfo() {
            android.app.ApplicationExitInfo applicationExitInfo = new android.app.ApplicationExitInfo();
            applicationExitInfo.setPid(this.mPid);
            applicationExitInfo.setRealUid(this.mUid);
            applicationExitInfo.setPackageUid(this.mUid);
            applicationExitInfo.setDefiningUid(this.mUid);
            applicationExitInfo.setProcessName(this.mProcessName);
            applicationExitInfo.setReason(5);
            applicationExitInfo.setStatus(0);
            applicationExitInfo.setImportance(1000);
            applicationExitInfo.setPackageName("");
            applicationExitInfo.setProcessStateSummary(null);
            applicationExitInfo.setPss(0L);
            applicationExitInfo.setRss(0L);
            applicationExitInfo.setTimestamp(this.mTimestampMs);
            applicationExitInfo.setDescription(this.mCrashReason);
            applicationExitInfo.setSubReason(0);
            applicationExitInfo.setNativeTombstoneRetriever(this.mRetriever);
            return applicationExitInfo;
        }

        class ParcelFileDescriptorRetriever extends android.app.IParcelFileDescriptorRetriever.Stub {
            ParcelFileDescriptorRetriever() {
            }

            @android.annotation.Nullable
            public android.os.ParcelFileDescriptor getPfd() {
                if (com.android.server.os.NativeTombstoneManager.TombstoneFile.this.mPurged) {
                    return null;
                }
                try {
                    return android.os.ParcelFileDescriptor.open(new java.io.File("/proc/self/fd/" + com.android.server.os.NativeTombstoneManager.TombstoneFile.this.mPfd.getFd()), 268435456);
                } catch (java.io.FileNotFoundException e) {
                    android.util.Slog.e(com.android.server.os.NativeTombstoneManager.TAG, "failed to reopen file descriptor as read-only", e);
                    return null;
                }
            }
        }
    }

    class TombstoneWatcher extends android.os.FileObserver {
        TombstoneWatcher() {
            super(com.android.server.os.NativeTombstoneManager.TOMBSTONE_DIR, com.android.internal.util.FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
        }

        @Override // android.os.FileObserver
        public void onEvent(int i, @android.annotation.Nullable final java.lang.String str) {
            if (str == null) {
                android.util.Slog.w(com.android.server.os.NativeTombstoneManager.TAG, "path is null at TombstoneWatcher.onEvent()");
            } else {
                com.android.server.os.NativeTombstoneManager.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.os.NativeTombstoneManager$TombstoneWatcher$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.os.NativeTombstoneManager.TombstoneWatcher.this.lambda$onEvent$0(str);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEvent$0(java.lang.String str) {
            if (str.endsWith(".tmp")) {
                return;
            }
            com.android.server.os.NativeTombstoneManager.this.handleTombstone(new java.io.File(com.android.server.os.NativeTombstoneManager.TOMBSTONE_DIR, str));
        }
    }
}
