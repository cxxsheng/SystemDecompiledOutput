package com.android.server.blob;

/* loaded from: classes.dex */
public class BlobStoreManagerService extends com.android.server.SystemService {

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private final android.util.ArraySet<java.lang.Long> mActiveBlobIds;
    private final android.os.Handler mBackgroundHandler;
    private final java.lang.Object mBlobsLock;

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private final android.util.ArrayMap<android.app.blob.BlobHandle, com.android.server.blob.BlobMetadata> mBlobsMap;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private long mCurrentMaxSessionId;
    private final android.os.Handler mHandler;
    private final com.android.server.blob.BlobStoreManagerService.Injector mInjector;

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private final android.util.ArraySet<java.lang.Long> mKnownBlobIds;
    private android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final java.util.Random mRandom;
    private final java.lang.Runnable mSaveBlobsInfoRunnable;
    private final java.lang.Runnable mSaveSessionsRunnable;
    private final com.android.server.blob.BlobStoreManagerService.SessionStateChangeListener mSessionStateChangeListener;

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private final android.util.SparseArray<android.util.LongSparseArray<com.android.server.blob.BlobStoreSession>> mSessions;
    private com.android.server.blob.BlobStoreManagerService.StatsPullAtomCallbackImpl mStatsCallbackImpl;
    private android.app.StatsManager mStatsManager;

    public BlobStoreManagerService(android.content.Context context) {
        this(context, new com.android.server.blob.BlobStoreManagerService.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    BlobStoreManagerService(android.content.Context context, com.android.server.blob.BlobStoreManagerService.Injector injector) {
        super(context);
        this.mBlobsLock = new java.lang.Object();
        this.mSessions = new android.util.SparseArray<>();
        this.mBlobsMap = new android.util.ArrayMap<>();
        this.mActiveBlobIds = new android.util.ArraySet<>();
        this.mKnownBlobIds = new android.util.ArraySet<>();
        this.mRandom = new java.security.SecureRandom();
        this.mSessionStateChangeListener = new com.android.server.blob.BlobStoreManagerService.SessionStateChangeListener();
        this.mStatsCallbackImpl = new com.android.server.blob.BlobStoreManagerService.StatsPullAtomCallbackImpl();
        this.mSaveBlobsInfoRunnable = new java.lang.Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.blob.BlobStoreManagerService.this.writeBlobsInfo();
            }
        };
        this.mSaveSessionsRunnable = new java.lang.Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.blob.BlobStoreManagerService.this.writeBlobSessions();
            }
        };
        this.mContext = context;
        this.mInjector = injector;
        this.mHandler = this.mInjector.initializeMessageHandler();
        this.mBackgroundHandler = this.mInjector.getBackgroundHandler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.Handler initializeMessageHandler() {
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(com.android.server.blob.BlobStoreConfig.TAG, 0, true);
        serviceThread.start();
        android.os.Handler handler = new android.os.Handler(serviceThread.getLooper());
        com.android.server.Watchdog.getInstance().addThread(handler);
        return handler;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("blob_store", new com.android.server.blob.BlobStoreManagerService.Stub());
        com.android.server.LocalServices.addService(com.android.server.blob.BlobStoreManagerInternal.class, new com.android.server.blob.BlobStoreManagerService.LocalService());
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mStatsManager = (android.app.StatsManager) getContext().getSystemService(android.app.StatsManager.class);
        registerReceivers();
        ((com.android.server.usage.StorageStatsManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.server.usage.StorageStatsManagerLocal.class)).registerStorageStatsAugmenter(new com.android.server.blob.BlobStoreManagerService.BlobStorageStatsAugmenter(), com.android.server.blob.BlobStoreConfig.TAG);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 550) {
            com.android.server.blob.BlobStoreConfig.initialize(this.mContext);
            return;
        }
        if (i == 600) {
            synchronized (this.mBlobsLock) {
                android.util.SparseArray<android.util.SparseArray<java.lang.String>> allPackages = getAllPackages();
                readBlobSessionsLocked(allPackages);
                readBlobsInfoLocked(allPackages);
            }
            registerBlobStorePuller();
            return;
        }
        if (i == 1000) {
            com.android.server.blob.BlobStoreIdleJobService.schedule(this.mContext);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private long generateNextSessionIdLocked() {
        int i = 0;
        while (true) {
            long nextLong = this.mRandom.nextLong();
            long abs = nextLong == Long.MIN_VALUE ? 0L : java.lang.Math.abs(nextLong);
            if (this.mKnownBlobIds.indexOf(java.lang.Long.valueOf(abs)) < 0 && abs != 0) {
                return abs;
            }
            int i2 = i + 1;
            if (i >= 32) {
                throw new java.lang.IllegalStateException("Failed to allocate session ID");
            }
            i = i2;
        }
    }

    private void registerReceivers() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverAsUser(new com.android.server.blob.BlobStoreManagerService.PackageChangedReceiver(), android.os.UserHandle.ALL, intentFilter, null, this.mHandler);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiverAsUser(new com.android.server.blob.BlobStoreManagerService.UserActionReceiver(), android.os.UserHandle.ALL, intentFilter2, null, this.mHandler);
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private android.util.LongSparseArray<com.android.server.blob.BlobStoreSession> getUserSessionsLocked(int i) {
        android.util.LongSparseArray<com.android.server.blob.BlobStoreSession> longSparseArray = this.mSessions.get(i);
        if (longSparseArray == null) {
            android.util.LongSparseArray<com.android.server.blob.BlobStoreSession> longSparseArray2 = new android.util.LongSparseArray<>();
            this.mSessions.put(i, longSparseArray2);
            return longSparseArray2;
        }
        return longSparseArray;
    }

    @com.android.internal.annotations.VisibleForTesting
    void addUserSessionsForTest(android.util.LongSparseArray<com.android.server.blob.BlobStoreSession> longSparseArray, int i) {
        synchronized (this.mBlobsLock) {
            this.mSessions.put(i, longSparseArray);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.blob.BlobMetadata getBlobForTest(android.app.blob.BlobHandle blobHandle) {
        com.android.server.blob.BlobMetadata blobMetadata;
        synchronized (this.mBlobsLock) {
            blobMetadata = this.mBlobsMap.get(blobHandle);
        }
        return blobMetadata;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getBlobsCountForTest() {
        int size;
        synchronized (this.mBlobsLock) {
            size = this.mBlobsMap.size();
        }
        return size;
    }

    @com.android.internal.annotations.VisibleForTesting
    void addActiveIdsForTest(long... jArr) {
        synchronized (this.mBlobsLock) {
            try {
                for (long j : jArr) {
                    addActiveBlobIdLocked(j);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.Set<java.lang.Long> getActiveIdsForTest() {
        android.util.ArraySet<java.lang.Long> arraySet;
        synchronized (this.mBlobsLock) {
            arraySet = this.mActiveBlobIds;
        }
        return arraySet;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.Set<java.lang.Long> getKnownIdsForTest() {
        android.util.ArraySet<java.lang.Long> arraySet;
        synchronized (this.mBlobsLock) {
            arraySet = this.mKnownBlobIds;
        }
        return arraySet;
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private void addSessionForUserLocked(com.android.server.blob.BlobStoreSession blobStoreSession, int i) {
        getUserSessionsLocked(i).put(blobStoreSession.getSessionId(), blobStoreSession);
        addActiveBlobIdLocked(blobStoreSession.getSessionId());
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    @com.android.internal.annotations.VisibleForTesting
    void addBlobLocked(com.android.server.blob.BlobMetadata blobMetadata) {
        this.mBlobsMap.put(blobMetadata.getBlobHandle(), blobMetadata);
        addActiveBlobIdLocked(blobMetadata.getBlobId());
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private void addActiveBlobIdLocked(long j) {
        this.mActiveBlobIds.add(java.lang.Long.valueOf(j));
        this.mKnownBlobIds.add(java.lang.Long.valueOf(j));
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private int getSessionsCountLocked(final int i, final java.lang.String str) {
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(0);
        forEachSessionInUser(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.blob.BlobStoreManagerService.lambda$getSessionsCountLocked$0(i, str, atomicInteger, (com.android.server.blob.BlobStoreSession) obj);
            }
        }, android.os.UserHandle.getUserId(i));
        return atomicInteger.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getSessionsCountLocked$0(int i, java.lang.String str, java.util.concurrent.atomic.AtomicInteger atomicInteger, com.android.server.blob.BlobStoreSession blobStoreSession) {
        if (blobStoreSession.getOwnerUid() == i && blobStoreSession.getOwnerPackageName().equals(str)) {
            atomicInteger.getAndIncrement();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long createSessionInternal(android.app.blob.BlobHandle blobHandle, int i, java.lang.String str) {
        long generateNextSessionIdLocked;
        synchronized (this.mBlobsLock) {
            try {
                int sessionsCountLocked = getSessionsCountLocked(i, str);
                if (sessionsCountLocked >= com.android.server.blob.BlobStoreConfig.getMaxActiveSessions()) {
                    throw new android.os.LimitExceededException("Too many active sessions for the caller: " + sessionsCountLocked);
                }
                generateNextSessionIdLocked = generateNextSessionIdLocked();
                addSessionForUserLocked(new com.android.server.blob.BlobStoreSession(this.mContext, generateNextSessionIdLocked, blobHandle, i, str, this.mSessionStateChangeListener), android.os.UserHandle.getUserId(i));
                if (com.android.server.blob.BlobStoreConfig.LOGV) {
                    android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Created session for " + blobHandle + "; callingUid=" + i + ", callingPackage=" + str);
                }
                writeBlobSessionsAsync();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return generateNextSessionIdLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.blob.BlobStoreSession openSessionInternal(long j, int i, java.lang.String str) {
        com.android.server.blob.BlobStoreSession blobStoreSession;
        synchronized (this.mBlobsLock) {
            blobStoreSession = getUserSessionsLocked(android.os.UserHandle.getUserId(i)).get(j);
            if (blobStoreSession == null || !blobStoreSession.hasAccess(i, str) || blobStoreSession.isFinalized()) {
                throw new java.lang.SecurityException("Session not found: " + j);
            }
        }
        blobStoreSession.open();
        return blobStoreSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abandonSessionInternal(long j, int i, java.lang.String str) {
        synchronized (this.mBlobsLock) {
            try {
                com.android.server.blob.BlobStoreSession openSessionInternal = openSessionInternal(j, i, str);
                openSessionInternal.open();
                openSessionInternal.abandon();
                if (com.android.server.blob.BlobStoreConfig.LOGV) {
                    android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Abandoned session with id " + j + "; callingUid=" + i + ", callingPackage=" + str);
                }
                writeBlobSessionsAsync();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.ParcelFileDescriptor openBlobInternal(android.app.blob.BlobHandle blobHandle, int i, java.lang.String str) throws java.io.IOException {
        android.os.ParcelFileDescriptor openForRead;
        synchronized (this.mBlobsLock) {
            try {
                com.android.server.blob.BlobMetadata blobMetadata = this.mBlobsMap.get(blobHandle);
                if (blobMetadata == null || !blobMetadata.isAccessAllowedForCaller(str, i)) {
                    if (blobMetadata == null) {
                        com.android.internal.util.FrameworkStatsLog.write(300, i, 0L, 0L, 2);
                    } else {
                        com.android.internal.util.FrameworkStatsLog.write(300, i, blobMetadata.getBlobId(), blobMetadata.getSize(), 3);
                    }
                    throw new java.lang.SecurityException("Caller not allowed to access " + blobHandle + "; callingUid=" + i + ", callingPackage=" + str);
                }
                com.android.internal.util.FrameworkStatsLog.write(300, i, blobMetadata.getBlobId(), blobMetadata.getSize(), 1);
                openForRead = blobMetadata.openForRead(str, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return openForRead;
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private int getCommittedBlobsCountLocked(final int i, final java.lang.String str) {
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(0);
        forEachBlobLocked(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.blob.BlobStoreManagerService.lambda$getCommittedBlobsCountLocked$1(str, i, atomicInteger, (com.android.server.blob.BlobMetadata) obj);
            }
        });
        return atomicInteger.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getCommittedBlobsCountLocked$1(java.lang.String str, int i, java.util.concurrent.atomic.AtomicInteger atomicInteger, com.android.server.blob.BlobMetadata blobMetadata) {
        if (blobMetadata.isACommitter(str, i)) {
            atomicInteger.getAndIncrement();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private int getLeasedBlobsCountLocked(final int i, final java.lang.String str) {
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(0);
        forEachBlobLocked(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.blob.BlobStoreManagerService.lambda$getLeasedBlobsCountLocked$2(str, i, atomicInteger, (com.android.server.blob.BlobMetadata) obj);
            }
        });
        return atomicInteger.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getLeasedBlobsCountLocked$2(java.lang.String str, int i, java.util.concurrent.atomic.AtomicInteger atomicInteger, com.android.server.blob.BlobMetadata blobMetadata) {
        if (blobMetadata.isALeasee(str, i)) {
            atomicInteger.getAndIncrement();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acquireLeaseInternal(android.app.blob.BlobHandle blobHandle, int i, java.lang.CharSequence charSequence, long j, int i2, java.lang.String str) {
        synchronized (this.mBlobsLock) {
            try {
                int leasedBlobsCountLocked = getLeasedBlobsCountLocked(i2, str);
                if (leasedBlobsCountLocked >= com.android.server.blob.BlobStoreConfig.getMaxLeasedBlobs()) {
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_LEASED, i2, 0L, 0L, 6);
                    throw new android.os.LimitExceededException("Too many leased blobs for the caller: " + leasedBlobsCountLocked);
                }
                if (j != 0 && blobHandle.expiryTimeMillis != 0 && j > blobHandle.expiryTimeMillis) {
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_LEASED, i2, 0L, 0L, 4);
                    throw new java.lang.IllegalArgumentException("Lease expiry cannot be later than blobs expiry time");
                }
                com.android.server.blob.BlobMetadata blobMetadata = this.mBlobsMap.get(blobHandle);
                if (blobMetadata == null || !blobMetadata.isAccessAllowedForCaller(str, i2)) {
                    if (blobMetadata == null) {
                        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_LEASED, i2, 0L, 0L, 2);
                    } else {
                        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_LEASED, i2, blobMetadata.getBlobId(), blobMetadata.getSize(), 3);
                    }
                    throw new java.lang.SecurityException("Caller not allowed to access " + blobHandle + "; callingUid=" + i2 + ", callingPackage=" + str);
                }
                if (blobMetadata.getSize() > getRemainingLeaseQuotaBytesInternal(i2, str)) {
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_LEASED, i2, blobMetadata.getBlobId(), blobMetadata.getSize(), 5);
                    throw new android.os.LimitExceededException("Total amount of data with an active lease is exceeding the max limit");
                }
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_LEASED, i2, blobMetadata.getBlobId(), blobMetadata.getSize(), 1);
                blobMetadata.addOrReplaceLeasee(str, i2, i, charSequence, j);
                if (com.android.server.blob.BlobStoreConfig.LOGV) {
                    android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Acquired lease on " + blobHandle + "; callingUid=" + i2 + ", callingPackage=" + str);
                }
                writeBlobsInfoAsync();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    @com.android.internal.annotations.VisibleForTesting
    long getTotalUsageBytesLocked(final int i, final java.lang.String str) {
        final java.util.concurrent.atomic.AtomicLong atomicLong = new java.util.concurrent.atomic.AtomicLong(0L);
        forEachBlobLocked(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.blob.BlobStoreManagerService.lambda$getTotalUsageBytesLocked$3(str, i, atomicLong, (com.android.server.blob.BlobMetadata) obj);
            }
        });
        return atomicLong.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getTotalUsageBytesLocked$3(java.lang.String str, int i, java.util.concurrent.atomic.AtomicLong atomicLong, com.android.server.blob.BlobMetadata blobMetadata) {
        if (blobMetadata.isALeasee(str, i)) {
            atomicLong.getAndAdd(blobMetadata.getSize());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseLeaseInternal(final android.app.blob.BlobHandle blobHandle, int i, java.lang.String str) {
        synchronized (this.mBlobsLock) {
            try {
                final com.android.server.blob.BlobMetadata blobMetadata = this.mBlobsMap.get(blobHandle);
                if (blobMetadata == null || !blobMetadata.isAccessAllowedForCaller(str, i)) {
                    throw new java.lang.SecurityException("Caller not allowed to access " + blobHandle + "; callingUid=" + i + ", callingPackage=" + str);
                }
                blobMetadata.removeLeasee(str, i);
                if (com.android.server.blob.BlobStoreConfig.LOGV) {
                    android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Released lease on " + blobHandle + "; callingUid=" + i + ", callingPackage=" + str);
                }
                if (!blobMetadata.hasValidLeases()) {
                    this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda14
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.blob.BlobStoreManagerService.this.lambda$releaseLeaseInternal$4(blobHandle, blobMetadata);
                        }
                    }, com.android.server.blob.BlobStoreConfig.getDeletionOnLastLeaseDelayMs());
                }
                writeBlobsInfoAsync();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseLeaseInternal$4(android.app.blob.BlobHandle blobHandle, com.android.server.blob.BlobMetadata blobMetadata) {
        synchronized (this.mBlobsLock) {
            try {
                if (java.util.Objects.equals(this.mBlobsMap.get(blobHandle), blobMetadata)) {
                    if (blobMetadata.shouldBeDeleted(true)) {
                        deleteBlobLocked(blobMetadata);
                        this.mBlobsMap.remove(blobHandle);
                    }
                    writeBlobsInfoAsync();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseAllLeasesInternal(final int i, final java.lang.String str) {
        synchronized (this.mBlobsLock) {
            try {
                this.mBlobsMap.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.blob.BlobMetadata) obj2).removeLeasee(str, i);
                    }
                });
                writeBlobsInfoAsync();
                if (com.android.server.blob.BlobStoreConfig.LOGV) {
                    android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Release all leases associated with pkg=" + str + ", uid=" + i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getRemainingLeaseQuotaBytesInternal(int i, java.lang.String str) {
        long appDataBytesLimit;
        synchronized (this.mBlobsLock) {
            appDataBytesLimit = com.android.server.blob.BlobStoreConfig.getAppDataBytesLimit() - getTotalUsageBytesLocked(i, str);
            if (appDataBytesLimit <= 0) {
                appDataBytesLimit = 0;
            }
        }
        return appDataBytesLimit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.app.blob.BlobInfo> queryBlobsForUserInternal(final int i) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mBlobsLock) {
            final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            final java.util.function.Function function = new java.util.function.Function() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda12
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.content.res.Resources lambda$queryBlobsForUserInternal$6;
                    lambda$queryBlobsForUserInternal$6 = com.android.server.blob.BlobStoreManagerService.this.lambda$queryBlobsForUserInternal$6(arrayMap, i, (java.lang.String) obj);
                    return lambda$queryBlobsForUserInternal$6;
                }
            };
            forEachBlobLocked(new java.util.function.BiConsumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda13
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.blob.BlobStoreManagerService.lambda$queryBlobsForUserInternal$8(i, function, arrayList, (android.app.blob.BlobHandle) obj, (com.android.server.blob.BlobMetadata) obj2);
                }
            });
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.res.Resources lambda$queryBlobsForUserInternal$6(android.util.ArrayMap arrayMap, int i, java.lang.String str) {
        java.lang.ref.WeakReference weakReference = (java.lang.ref.WeakReference) arrayMap.get(str);
        android.content.res.Resources resources = weakReference == null ? null : (android.content.res.Resources) weakReference.get();
        if (resources == null) {
            android.content.res.Resources packageResources = com.android.server.blob.BlobStoreUtils.getPackageResources(this.mContext, str, i);
            arrayMap.put(str, new java.lang.ref.WeakReference(packageResources));
            return packageResources;
        }
        return resources;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$queryBlobsForUserInternal$8(final int i, final java.util.function.Function function, java.util.ArrayList arrayList, final android.app.blob.BlobHandle blobHandle, com.android.server.blob.BlobMetadata blobMetadata) {
        if (!blobMetadata.hasACommitterOrLeaseeInUser(i)) {
            return;
        }
        final java.util.ArrayList arrayList2 = new java.util.ArrayList();
        blobMetadata.forEachLeasee(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.blob.BlobStoreManagerService.lambda$queryBlobsForUserInternal$7(i, function, blobHandle, arrayList2, (com.android.server.blob.BlobMetadata.Leasee) obj);
            }
        });
        arrayList.add(new android.app.blob.BlobInfo(blobMetadata.getBlobId(), blobHandle.getExpiryTimeMillis(), blobHandle.getLabel(), blobMetadata.getSize(), arrayList2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$queryBlobsForUserInternal$7(int i, java.util.function.Function function, android.app.blob.BlobHandle blobHandle, java.util.ArrayList arrayList, com.android.server.blob.BlobMetadata.Leasee leasee) {
        int descriptionResourceId;
        if (!leasee.isStillValid() || i != android.os.UserHandle.getUserId(leasee.uid)) {
            return;
        }
        if (leasee.descriptionResEntryName == null) {
            descriptionResourceId = 0;
        } else {
            descriptionResourceId = com.android.server.blob.BlobStoreUtils.getDescriptionResourceId((android.content.res.Resources) function.apply(leasee.packageName), leasee.descriptionResEntryName, leasee.packageName);
        }
        arrayList.add(new android.app.blob.LeaseInfo(leasee.packageName, leasee.expiryTimeMillis == 0 ? blobHandle.getExpiryTimeMillis() : leasee.expiryTimeMillis, descriptionResourceId, leasee.description));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteBlobInternal(final long j) {
        synchronized (this.mBlobsLock) {
            this.mBlobsMap.entrySet().removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$deleteBlobInternal$9;
                    lambda$deleteBlobInternal$9 = com.android.server.blob.BlobStoreManagerService.this.lambda$deleteBlobInternal$9(j, (java.util.Map.Entry) obj);
                    return lambda$deleteBlobInternal$9;
                }
            });
            writeBlobsInfoAsync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$deleteBlobInternal$9(long j, java.util.Map.Entry entry) {
        com.android.server.blob.BlobMetadata blobMetadata = (com.android.server.blob.BlobMetadata) entry.getValue();
        if (blobMetadata.getBlobId() == j) {
            deleteBlobLocked(blobMetadata);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.app.blob.BlobHandle> getLeasedBlobsInternal(final int i, @android.annotation.NonNull final java.lang.String str) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mBlobsLock) {
            forEachBlobLocked(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.blob.BlobStoreManagerService.lambda$getLeasedBlobsInternal$10(str, i, arrayList, (com.android.server.blob.BlobMetadata) obj);
                }
            });
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getLeasedBlobsInternal$10(java.lang.String str, int i, java.util.ArrayList arrayList, com.android.server.blob.BlobMetadata blobMetadata) {
        if (blobMetadata.isALeasee(str, i)) {
            arrayList.add(blobMetadata.getBlobHandle());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.blob.LeaseInfo getLeaseInfoInternal(android.app.blob.BlobHandle blobHandle, int i, @android.annotation.NonNull java.lang.String str) {
        android.app.blob.LeaseInfo leaseInfo;
        synchronized (this.mBlobsLock) {
            try {
                com.android.server.blob.BlobMetadata blobMetadata = this.mBlobsMap.get(blobHandle);
                if (blobMetadata == null || !blobMetadata.isAccessAllowedForCaller(str, i)) {
                    throw new java.lang.SecurityException("Caller not allowed to access " + blobHandle + "; callingUid=" + i + ", callingPackage=" + str);
                }
                leaseInfo = blobMetadata.getLeaseInfo(str, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return leaseInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyCallingPackage(int i, java.lang.String str) {
        if (this.mPackageManagerInternal.getPackageUid(str, 0L, android.os.UserHandle.getUserId(i)) != i) {
            throw new java.lang.SecurityException("Specified calling package [" + str + "] does not match the calling uid " + i);
        }
    }

    class SessionStateChangeListener {
        SessionStateChangeListener() {
        }

        public void onStateChanged(@android.annotation.NonNull com.android.server.blob.BlobStoreSession blobStoreSession) {
            com.android.server.blob.BlobStoreManagerService.this.mHandler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.BiConsumer() { // from class: com.android.server.blob.BlobStoreManagerService$SessionStateChangeListener$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.blob.BlobStoreManagerService) obj).onStateChangedInternal((com.android.server.blob.BlobStoreSession) obj2);
                }
            }, com.android.server.blob.BlobStoreManagerService.this, blobStoreSession).recycleOnUse());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStateChangedInternal(@android.annotation.NonNull final com.android.server.blob.BlobStoreSession blobStoreSession) {
        int committedBlobsCountLocked;
        com.android.server.blob.BlobMetadata blobMetadata;
        com.android.server.blob.BlobMetadata.Committer existingCommitter;
        com.android.server.blob.BlobMetadata.Committer committer;
        switch (blobStoreSession.getState()) {
            case 2:
            case 5:
                synchronized (this.mBlobsLock) {
                    try {
                        deleteSessionLocked(blobStoreSession);
                        getUserSessionsLocked(android.os.UserHandle.getUserId(blobStoreSession.getOwnerUid())).remove(blobStoreSession.getSessionId());
                        if (com.android.server.blob.BlobStoreConfig.LOGV) {
                            android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Session is invalid; deleted " + blobStoreSession);
                        }
                        break;
                    } finally {
                    }
                }
            case 3:
                this.mBackgroundHandler.post(new java.lang.Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.blob.BlobStoreManagerService.this.lambda$onStateChangedInternal$11(blobStoreSession);
                    }
                });
                break;
            case 4:
                synchronized (this.mBlobsLock) {
                    try {
                        committedBlobsCountLocked = getCommittedBlobsCountLocked(blobStoreSession.getOwnerUid(), blobStoreSession.getOwnerPackageName());
                    } catch (java.lang.Exception e) {
                        if (existingCommitter == null) {
                            blobMetadata.removeCommitter(committer);
                        } else {
                            blobMetadata.addOrReplaceCommitter(existingCommitter);
                        }
                        android.util.Slog.d(com.android.server.blob.BlobStoreConfig.TAG, "Error committing the blob: " + blobStoreSession, e);
                        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_COMMITTED, blobStoreSession.getOwnerUid(), blobStoreSession.getSessionId(), blobMetadata.getSize(), 2);
                        blobStoreSession.sendCommitCallbackResult(1);
                        if (blobStoreSession.getSessionId() == blobMetadata.getBlobId()) {
                            deleteBlobLocked(blobMetadata);
                            this.mBlobsMap.remove(blobMetadata.getBlobHandle());
                        }
                    } finally {
                    }
                    if (committedBlobsCountLocked >= com.android.server.blob.BlobStoreConfig.getMaxCommittedBlobs()) {
                        android.util.Slog.d(com.android.server.blob.BlobStoreConfig.TAG, "Failed to commit: too many committed blobs. count: " + committedBlobsCountLocked + "; blob: " + blobStoreSession);
                        blobStoreSession.sendCommitCallbackResult(1);
                        deleteSessionLocked(blobStoreSession);
                        getUserSessionsLocked(android.os.UserHandle.getUserId(blobStoreSession.getOwnerUid())).remove(blobStoreSession.getSessionId());
                        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_COMMITTED, blobStoreSession.getOwnerUid(), blobStoreSession.getSessionId(), blobStoreSession.getSize(), 4);
                        break;
                    } else {
                        int indexOfKey = this.mBlobsMap.indexOfKey(blobStoreSession.getBlobHandle());
                        if (indexOfKey >= 0) {
                            blobMetadata = this.mBlobsMap.valueAt(indexOfKey);
                        } else {
                            com.android.server.blob.BlobMetadata blobMetadata2 = new com.android.server.blob.BlobMetadata(this.mContext, blobStoreSession.getSessionId(), blobStoreSession.getBlobHandle());
                            addBlobLocked(blobMetadata2);
                            blobMetadata = blobMetadata2;
                        }
                        existingCommitter = blobMetadata.getExistingCommitter(blobStoreSession.getOwnerPackageName(), blobStoreSession.getOwnerUid());
                        committer = new com.android.server.blob.BlobMetadata.Committer(blobStoreSession.getOwnerPackageName(), blobStoreSession.getOwnerUid(), blobStoreSession.getBlobAccessMode(), com.android.server.blob.BlobStoreConfig.getAdjustedCommitTimeMs(existingCommitter == null ? 0L : existingCommitter.getCommitTimeMs(), java.lang.System.currentTimeMillis()));
                        blobMetadata.addOrReplaceCommitter(committer);
                        writeBlobsInfoLocked();
                        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_COMMITTED, blobStoreSession.getOwnerUid(), blobMetadata.getBlobId(), blobMetadata.getSize(), 1);
                        blobStoreSession.sendCommitCallbackResult(0);
                        if (blobStoreSession.getSessionId() != blobMetadata.getBlobId()) {
                            deleteSessionLocked(blobStoreSession);
                        }
                        getUserSessionsLocked(android.os.UserHandle.getUserId(blobStoreSession.getOwnerUid())).remove(blobStoreSession.getSessionId());
                        if (com.android.server.blob.BlobStoreConfig.LOGV) {
                            android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Successfully committed session " + blobStoreSession);
                        }
                        break;
                    }
                }
            default:
                android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Invalid session state: " + com.android.server.blob.BlobStoreSession.stateToString(blobStoreSession.getState()));
                break;
        }
        synchronized (this.mBlobsLock) {
            try {
                writeBlobSessionsLocked();
            } catch (java.lang.Exception e2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStateChangedInternal$11(com.android.server.blob.BlobStoreSession blobStoreSession) {
        blobStoreSession.computeDigest();
        this.mHandler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.blob.BlobStoreSession) obj).verifyBlobData();
            }
        }, blobStoreSession).recycleOnUse());
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private void writeBlobSessionsLocked() throws java.lang.Exception {
        java.io.FileOutputStream startWrite;
        android.util.AtomicFile prepareSessionsIndexFile = prepareSessionsIndexFile();
        if (prepareSessionsIndexFile == null) {
            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Error creating sessions index file");
            return;
        }
        java.io.FileOutputStream fileOutputStream = null;
        try {
            startWrite = prepareSessionsIndexFile.startWrite(android.os.SystemClock.uptimeMillis());
        } catch (java.lang.Exception e) {
            e = e;
        }
        try {
            org.xmlpull.v1.XmlSerializer fastXmlSerializer = new com.android.internal.util.FastXmlSerializer();
            fastXmlSerializer.setOutput(startWrite, java.nio.charset.StandardCharsets.UTF_8.name());
            fastXmlSerializer.startDocument(null, true);
            fastXmlSerializer.startTag(null, "ss");
            com.android.internal.util.XmlUtils.writeIntAttribute(fastXmlSerializer, "v", 6);
            int size = this.mSessions.size();
            for (int i = 0; i < size; i++) {
                android.util.LongSparseArray<com.android.server.blob.BlobStoreSession> valueAt = this.mSessions.valueAt(i);
                int size2 = valueAt.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    fastXmlSerializer.startTag(null, "s");
                    valueAt.valueAt(i2).writeToXml(fastXmlSerializer);
                    fastXmlSerializer.endTag(null, "s");
                }
            }
            fastXmlSerializer.endTag(null, "ss");
            fastXmlSerializer.endDocument();
            prepareSessionsIndexFile.finishWrite(startWrite);
            if (com.android.server.blob.BlobStoreConfig.LOGV) {
                android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Finished persisting sessions data");
            }
        } catch (java.lang.Exception e2) {
            e = e2;
            fileOutputStream = startWrite;
            prepareSessionsIndexFile.failWrite(fileOutputStream);
            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Error writing sessions data", e);
            throw e;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private void readBlobSessionsLocked(android.util.SparseArray<android.util.SparseArray<java.lang.String>> sparseArray) {
        com.android.server.blob.BlobStoreSession createFromXml;
        if (!com.android.server.blob.BlobStoreConfig.getBlobStoreRootDir().exists()) {
            return;
        }
        android.util.AtomicFile prepareSessionsIndexFile = prepareSessionsIndexFile();
        if (prepareSessionsIndexFile == null) {
            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Error creating sessions index file");
            return;
        }
        if (!prepareSessionsIndexFile.exists()) {
            android.util.Slog.w(com.android.server.blob.BlobStoreConfig.TAG, "Sessions index file not available: " + prepareSessionsIndexFile.getBaseFile());
            return;
        }
        this.mSessions.clear();
        try {
            java.io.FileInputStream openRead = prepareSessionsIndexFile.openRead();
            try {
                org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
                newPullParser.setInput(openRead, java.nio.charset.StandardCharsets.UTF_8.name());
                com.android.internal.util.XmlUtils.beginDocument(newPullParser, "ss");
                int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(newPullParser, "v");
                while (true) {
                    com.android.internal.util.XmlUtils.nextElement(newPullParser);
                    if (newPullParser.getEventType() == 1) {
                        break;
                    }
                    if ("s".equals(newPullParser.getName()) && (createFromXml = com.android.server.blob.BlobStoreSession.createFromXml(newPullParser, readIntAttribute, this.mContext, this.mSessionStateChangeListener)) != null) {
                        android.util.SparseArray<java.lang.String> sparseArray2 = sparseArray.get(android.os.UserHandle.getUserId(createFromXml.getOwnerUid()));
                        if (sparseArray2 != null && createFromXml.getOwnerPackageName().equals(sparseArray2.get(createFromXml.getOwnerUid()))) {
                            addSessionForUserLocked(createFromXml, android.os.UserHandle.getUserId(createFromXml.getOwnerUid()));
                        } else {
                            createFromXml.getSessionFile().delete();
                        }
                        this.mCurrentMaxSessionId = java.lang.Math.max(this.mCurrentMaxSessionId, createFromXml.getSessionId());
                    }
                }
                if (com.android.server.blob.BlobStoreConfig.LOGV) {
                    android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Finished reading sessions data");
                }
                if (openRead != null) {
                    openRead.close();
                }
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Error reading sessions data", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private void writeBlobsInfoLocked() throws java.lang.Exception {
        android.util.AtomicFile prepareBlobsIndexFile = prepareBlobsIndexFile();
        if (prepareBlobsIndexFile == null) {
            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Error creating blobs index file");
            return;
        }
        java.io.FileOutputStream fileOutputStream = null;
        try {
            java.io.FileOutputStream startWrite = prepareBlobsIndexFile.startWrite(android.os.SystemClock.uptimeMillis());
            try {
                org.xmlpull.v1.XmlSerializer fastXmlSerializer = new com.android.internal.util.FastXmlSerializer();
                fastXmlSerializer.setOutput(startWrite, java.nio.charset.StandardCharsets.UTF_8.name());
                fastXmlSerializer.startDocument(null, true);
                fastXmlSerializer.startTag(null, "bs");
                com.android.internal.util.XmlUtils.writeIntAttribute(fastXmlSerializer, "v", 6);
                int size = this.mBlobsMap.size();
                for (int i = 0; i < size; i++) {
                    fastXmlSerializer.startTag(null, "b");
                    this.mBlobsMap.valueAt(i).writeToXml(fastXmlSerializer);
                    fastXmlSerializer.endTag(null, "b");
                }
                fastXmlSerializer.endTag(null, "bs");
                fastXmlSerializer.endDocument();
                prepareBlobsIndexFile.finishWrite(startWrite);
                if (com.android.server.blob.BlobStoreConfig.LOGV) {
                    android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Finished persisting blobs data");
                }
            } catch (java.lang.Exception e) {
                e = e;
                fileOutputStream = startWrite;
                prepareBlobsIndexFile.failWrite(fileOutputStream);
                android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Error writing blobs data", e);
                throw e;
            }
        } catch (java.lang.Exception e2) {
            e = e2;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private void readBlobsInfoLocked(android.util.SparseArray<android.util.SparseArray<java.lang.String>> sparseArray) {
        if (!com.android.server.blob.BlobStoreConfig.getBlobStoreRootDir().exists()) {
            return;
        }
        android.util.AtomicFile prepareBlobsIndexFile = prepareBlobsIndexFile();
        if (prepareBlobsIndexFile == null) {
            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Error creating blobs index file");
            return;
        }
        if (!prepareBlobsIndexFile.exists()) {
            android.util.Slog.w(com.android.server.blob.BlobStoreConfig.TAG, "Blobs index file not available: " + prepareBlobsIndexFile.getBaseFile());
            return;
        }
        this.mBlobsMap.clear();
        try {
            java.io.FileInputStream openRead = prepareBlobsIndexFile.openRead();
            try {
                org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
                newPullParser.setInput(openRead, java.nio.charset.StandardCharsets.UTF_8.name());
                com.android.internal.util.XmlUtils.beginDocument(newPullParser, "bs");
                int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(newPullParser, "v");
                while (true) {
                    com.android.internal.util.XmlUtils.nextElement(newPullParser);
                    if (newPullParser.getEventType() == 1) {
                        break;
                    }
                    if ("b".equals(newPullParser.getName())) {
                        com.android.server.blob.BlobMetadata createFromXml = com.android.server.blob.BlobMetadata.createFromXml(newPullParser, readIntAttribute, this.mContext);
                        createFromXml.removeCommittersFromUnknownPkgs(sparseArray);
                        createFromXml.removeLeaseesFromUnknownPkgs(sparseArray);
                        this.mCurrentMaxSessionId = java.lang.Math.max(this.mCurrentMaxSessionId, createFromXml.getBlobId());
                        if (readIntAttribute >= 6) {
                            addBlobLocked(createFromXml);
                        } else {
                            com.android.server.blob.BlobMetadata blobMetadata = this.mBlobsMap.get(createFromXml.getBlobHandle());
                            if (blobMetadata == null) {
                                addBlobLocked(createFromXml);
                            } else {
                                blobMetadata.addCommittersAndLeasees(createFromXml);
                                createFromXml.getBlobFile().delete();
                            }
                        }
                    }
                }
                if (com.android.server.blob.BlobStoreConfig.LOGV) {
                    android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Finished reading blobs data");
                }
                if (openRead != null) {
                    openRead.close();
                }
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Error reading blobs data", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeBlobsInfo() {
        synchronized (this.mBlobsLock) {
            try {
                writeBlobsInfoLocked();
            } catch (java.lang.Exception e) {
            }
        }
    }

    private void writeBlobsInfoAsync() {
        if (!this.mHandler.hasCallbacks(this.mSaveBlobsInfoRunnable)) {
            this.mHandler.post(this.mSaveBlobsInfoRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeBlobSessions() {
        synchronized (this.mBlobsLock) {
            try {
                writeBlobSessionsLocked();
            } catch (java.lang.Exception e) {
            }
        }
    }

    private void writeBlobSessionsAsync() {
        if (!this.mHandler.hasCallbacks(this.mSaveSessionsRunnable)) {
            this.mHandler.post(this.mSaveSessionsRunnable);
        }
    }

    private android.util.SparseArray<android.util.SparseArray<java.lang.String>> getAllPackages() {
        android.util.SparseArray<android.util.SparseArray<java.lang.String>> sparseArray = new android.util.SparseArray<>();
        for (int i : ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds()) {
            android.util.SparseArray<java.lang.String> sparseArray2 = new android.util.SparseArray<>();
            sparseArray.put(i, sparseArray2);
            java.util.List<android.content.pm.ApplicationInfo> installedApplications = this.mPackageManagerInternal.getInstalledApplications(794624L, i, android.os.Process.myUid());
            int size = installedApplications.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.content.pm.ApplicationInfo applicationInfo = installedApplications.get(i2);
                sparseArray2.put(applicationInfo.uid, applicationInfo.packageName);
            }
        }
        return sparseArray;
    }

    private android.util.AtomicFile prepareSessionsIndexFile() {
        java.io.File prepareSessionIndexFile = com.android.server.blob.BlobStoreConfig.prepareSessionIndexFile();
        if (prepareSessionIndexFile == null) {
            return null;
        }
        return new android.util.AtomicFile(prepareSessionIndexFile, "session_index");
    }

    private android.util.AtomicFile prepareBlobsIndexFile() {
        java.io.File prepareBlobsIndexFile = com.android.server.blob.BlobStoreConfig.prepareBlobsIndexFile();
        if (prepareBlobsIndexFile == null) {
            return null;
        }
        return new android.util.AtomicFile(prepareBlobsIndexFile, "blobs_index");
    }

    @com.android.internal.annotations.VisibleForTesting
    void handlePackageRemoved(final java.lang.String str, final int i) {
        synchronized (this.mBlobsLock) {
            try {
                getUserSessionsLocked(android.os.UserHandle.getUserId(i)).removeIf(new com.android.internal.util.function.LongObjPredicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda15
                    public final boolean test(long j, java.lang.Object obj) {
                        boolean lambda$handlePackageRemoved$12;
                        lambda$handlePackageRemoved$12 = com.android.server.blob.BlobStoreManagerService.this.lambda$handlePackageRemoved$12(i, str, j, (com.android.server.blob.BlobStoreSession) obj);
                        return lambda$handlePackageRemoved$12;
                    }
                });
                writeBlobSessionsAsync();
                this.mBlobsMap.entrySet().removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$handlePackageRemoved$13;
                        lambda$handlePackageRemoved$13 = com.android.server.blob.BlobStoreManagerService.this.lambda$handlePackageRemoved$13(str, i, (java.util.Map.Entry) obj);
                        return lambda$handlePackageRemoved$13;
                    }
                });
                writeBlobsInfoAsync();
                if (com.android.server.blob.BlobStoreConfig.LOGV) {
                    android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Removed blobs data associated with pkg=" + str + ", uid=" + i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handlePackageRemoved$12(int i, java.lang.String str, long j, com.android.server.blob.BlobStoreSession blobStoreSession) {
        if (blobStoreSession.getOwnerUid() == i && blobStoreSession.getOwnerPackageName().equals(str)) {
            deleteSessionLocked(blobStoreSession);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handlePackageRemoved$13(java.lang.String str, int i, java.util.Map.Entry entry) {
        com.android.server.blob.BlobMetadata blobMetadata = (com.android.server.blob.BlobMetadata) entry.getValue();
        boolean isACommitter = blobMetadata.isACommitter(str, i);
        if (isACommitter) {
            blobMetadata.removeCommitter(str, i);
        }
        blobMetadata.removeLeasee(str, i);
        if (blobMetadata.shouldBeDeleted(isACommitter)) {
            deleteBlobLocked(blobMetadata);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUserRemoved(final int i) {
        synchronized (this.mBlobsLock) {
            try {
                android.util.LongSparseArray longSparseArray = (android.util.LongSparseArray) this.mSessions.removeReturnOld(i);
                if (longSparseArray != null) {
                    int size = longSparseArray.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        deleteSessionLocked((com.android.server.blob.BlobStoreSession) longSparseArray.valueAt(i2));
                    }
                }
                this.mBlobsMap.entrySet().removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$handleUserRemoved$14;
                        lambda$handleUserRemoved$14 = com.android.server.blob.BlobStoreManagerService.this.lambda$handleUserRemoved$14(i, (java.util.Map.Entry) obj);
                        return lambda$handleUserRemoved$14;
                    }
                });
                if (com.android.server.blob.BlobStoreConfig.LOGV) {
                    android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Removed blobs data in user " + i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handleUserRemoved$14(int i, java.util.Map.Entry entry) {
        com.android.server.blob.BlobMetadata blobMetadata = (com.android.server.blob.BlobMetadata) entry.getValue();
        blobMetadata.removeDataForUser(i);
        if (blobMetadata.shouldBeDeleted(true)) {
            deleteBlobLocked(blobMetadata);
            return true;
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    @com.android.internal.annotations.VisibleForTesting
    void handleIdleMaintenanceLocked() {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.io.File blobsDir = com.android.server.blob.BlobStoreConfig.getBlobsDir();
        if (blobsDir.exists()) {
            for (java.io.File file : blobsDir.listFiles()) {
                try {
                    long parseLong = java.lang.Long.parseLong(file.getName());
                    if (this.mActiveBlobIds.indexOf(java.lang.Long.valueOf(parseLong)) < 0) {
                        arrayList2.add(file);
                        arrayList.add(java.lang.Long.valueOf(parseLong));
                    }
                } catch (java.lang.NumberFormatException e) {
                    android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Error parsing the file name: " + file, e);
                    arrayList2.add(file);
                }
            }
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((java.io.File) arrayList2.get(i)).delete();
            }
        }
        this.mBlobsMap.entrySet().removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$handleIdleMaintenanceLocked$15;
                lambda$handleIdleMaintenanceLocked$15 = com.android.server.blob.BlobStoreManagerService.this.lambda$handleIdleMaintenanceLocked$15(arrayList, (java.util.Map.Entry) obj);
                return lambda$handleIdleMaintenanceLocked$15;
            }
        });
        writeBlobsInfoAsync();
        int size2 = this.mSessions.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mSessions.valueAt(i2).removeIf(new com.android.internal.util.function.LongObjPredicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda18
                public final boolean test(long j, java.lang.Object obj) {
                    boolean lambda$handleIdleMaintenanceLocked$16;
                    lambda$handleIdleMaintenanceLocked$16 = com.android.server.blob.BlobStoreManagerService.this.lambda$handleIdleMaintenanceLocked$16(arrayList, j, (com.android.server.blob.BlobStoreSession) obj);
                    return lambda$handleIdleMaintenanceLocked$16;
                }
            });
        }
        android.util.Slog.d(com.android.server.blob.BlobStoreConfig.TAG, "Completed idle maintenance; deleted " + java.util.Arrays.toString(arrayList.toArray()));
        writeBlobSessionsAsync();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handleIdleMaintenanceLocked$15(java.util.ArrayList arrayList, java.util.Map.Entry entry) {
        com.android.server.blob.BlobMetadata blobMetadata = (com.android.server.blob.BlobMetadata) entry.getValue();
        blobMetadata.removeExpiredLeases();
        if (blobMetadata.shouldBeDeleted(true)) {
            deleteBlobLocked(blobMetadata);
            arrayList.add(java.lang.Long.valueOf(blobMetadata.getBlobId()));
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handleIdleMaintenanceLocked$16(java.util.ArrayList arrayList, long j, com.android.server.blob.BlobStoreSession blobStoreSession) {
        boolean z;
        if (!blobStoreSession.isExpired()) {
            z = false;
        } else {
            z = true;
        }
        boolean z2 = blobStoreSession.getBlobHandle().isExpired() ? true : z;
        if (z2) {
            deleteSessionLocked(blobStoreSession);
            arrayList.add(java.lang.Long.valueOf(blobStoreSession.getSessionId()));
        }
        return z2;
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private void deleteSessionLocked(com.android.server.blob.BlobStoreSession blobStoreSession) {
        blobStoreSession.destroy();
        this.mActiveBlobIds.remove(java.lang.Long.valueOf(blobStoreSession.getSessionId()));
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    private void deleteBlobLocked(com.android.server.blob.BlobMetadata blobMetadata) {
        blobMetadata.destroy();
        this.mActiveBlobIds.remove(java.lang.Long.valueOf(blobMetadata.getBlobId()));
    }

    void runClearAllSessions(int i) {
        synchronized (this.mBlobsLock) {
            try {
                int size = this.mSessions.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int keyAt = this.mSessions.keyAt(i2);
                    if (i == -1 || i == keyAt) {
                        android.util.LongSparseArray<com.android.server.blob.BlobStoreSession> valueAt = this.mSessions.valueAt(i2);
                        int size2 = valueAt.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            this.mActiveBlobIds.remove(java.lang.Long.valueOf(valueAt.valueAt(i3).getSessionId()));
                        }
                    }
                }
                if (i == -1) {
                    this.mSessions.clear();
                } else {
                    this.mSessions.remove(i);
                }
                writeBlobSessionsAsync();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void runClearAllBlobs(final int i) {
        synchronized (this.mBlobsLock) {
            this.mBlobsMap.entrySet().removeIf(new java.util.function.Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$runClearAllBlobs$17;
                    lambda$runClearAllBlobs$17 = com.android.server.blob.BlobStoreManagerService.this.lambda$runClearAllBlobs$17(i, (java.util.Map.Entry) obj);
                    return lambda$runClearAllBlobs$17;
                }
            });
            writeBlobsInfoAsync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$runClearAllBlobs$17(int i, java.util.Map.Entry entry) {
        com.android.server.blob.BlobMetadata blobMetadata = (com.android.server.blob.BlobMetadata) entry.getValue();
        if (i == -1) {
            this.mActiveBlobIds.remove(java.lang.Long.valueOf(blobMetadata.getBlobId()));
            return true;
        }
        blobMetadata.removeDataForUser(i);
        if (!blobMetadata.shouldBeDeleted(false)) {
            return false;
        }
        this.mActiveBlobIds.remove(java.lang.Long.valueOf(blobMetadata.getBlobId()));
        return true;
    }

    void deleteBlob(@android.annotation.NonNull android.app.blob.BlobHandle blobHandle, int i) {
        synchronized (this.mBlobsLock) {
            try {
                com.android.server.blob.BlobMetadata blobMetadata = this.mBlobsMap.get(blobHandle);
                if (blobMetadata == null) {
                    return;
                }
                blobMetadata.removeDataForUser(i);
                if (blobMetadata.shouldBeDeleted(false)) {
                    deleteBlobLocked(blobMetadata);
                    this.mBlobsMap.remove(blobHandle);
                }
                writeBlobsInfoAsync();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void runIdleMaintenance() {
        synchronized (this.mBlobsLock) {
            handleIdleMaintenanceLocked();
        }
    }

    boolean isBlobAvailable(long j, int i) {
        synchronized (this.mBlobsLock) {
            try {
                int size = this.mBlobsMap.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.blob.BlobMetadata valueAt = this.mBlobsMap.valueAt(i2);
                    if (valueAt.getBlobId() == j) {
                        return valueAt.hasACommitterInUser(i);
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    public void dumpSessionsLocked(android.util.IndentingPrintWriter indentingPrintWriter, com.android.server.blob.BlobStoreManagerService.DumpArgs dumpArgs) {
        int size = this.mSessions.size();
        for (int i = 0; i < size; i++) {
            int keyAt = this.mSessions.keyAt(i);
            if (dumpArgs.shouldDumpUser(keyAt)) {
                android.util.LongSparseArray<com.android.server.blob.BlobStoreSession> valueAt = this.mSessions.valueAt(i);
                indentingPrintWriter.println("List of sessions in user #" + keyAt + " (" + valueAt.size() + "):");
                indentingPrintWriter.increaseIndent();
                int size2 = valueAt.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    long keyAt2 = valueAt.keyAt(i2);
                    com.android.server.blob.BlobStoreSession valueAt2 = valueAt.valueAt(i2);
                    if (dumpArgs.shouldDumpSession(valueAt2.getOwnerPackageName(), valueAt2.getOwnerUid(), valueAt2.getSessionId())) {
                        indentingPrintWriter.println("Session #" + keyAt2);
                        indentingPrintWriter.increaseIndent();
                        valueAt2.dump(indentingPrintWriter, dumpArgs);
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mBlobsLock"})
    public void dumpBlobsLocked(android.util.IndentingPrintWriter indentingPrintWriter, com.android.server.blob.BlobStoreManagerService.DumpArgs dumpArgs) {
        indentingPrintWriter.println("List of blobs (" + this.mBlobsMap.size() + "):");
        indentingPrintWriter.increaseIndent();
        int size = this.mBlobsMap.size();
        for (int i = 0; i < size; i++) {
            com.android.server.blob.BlobMetadata valueAt = this.mBlobsMap.valueAt(i);
            if (dumpArgs.shouldDumpBlob(valueAt.getBlobId())) {
                indentingPrintWriter.println("Blob #" + valueAt.getBlobId());
                indentingPrintWriter.increaseIndent();
                valueAt.dump(indentingPrintWriter, dumpArgs);
                indentingPrintWriter.decreaseIndent();
            }
        }
        if (this.mBlobsMap.isEmpty()) {
            indentingPrintWriter.println("<empty>");
        }
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class BlobStorageStatsAugmenter implements com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter {
        private BlobStorageStatsAugmenter() {
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter
        public void augmentStatsForPackageForUser(@android.annotation.NonNull android.content.pm.PackageStats packageStats, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final android.os.UserHandle userHandle, final boolean z) {
            final java.util.concurrent.atomic.AtomicLong atomicLong = new java.util.concurrent.atomic.AtomicLong(0L);
            com.android.server.blob.BlobStoreManagerService.this.forEachSessionInUser(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.blob.BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForPackageForUser$0(str, atomicLong, (com.android.server.blob.BlobStoreSession) obj);
                }
            }, userHandle.getIdentifier());
            com.android.server.blob.BlobStoreManagerService.this.forEachBlob(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.blob.BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForPackageForUser$1(str, userHandle, z, atomicLong, (com.android.server.blob.BlobMetadata) obj);
                }
            });
            packageStats.dataSize += atomicLong.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$augmentStatsForPackageForUser$0(java.lang.String str, java.util.concurrent.atomic.AtomicLong atomicLong, com.android.server.blob.BlobStoreSession blobStoreSession) {
            if (blobStoreSession.getOwnerPackageName().equals(str)) {
                atomicLong.getAndAdd(blobStoreSession.getSize());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$augmentStatsForPackageForUser$1(java.lang.String str, android.os.UserHandle userHandle, boolean z, java.util.concurrent.atomic.AtomicLong atomicLong, com.android.server.blob.BlobMetadata blobMetadata) {
            if (blobMetadata.shouldAttributeToLeasee(str, userHandle.getIdentifier(), z)) {
                atomicLong.getAndAdd(blobMetadata.getSize());
            }
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter
        public void augmentStatsForUid(@android.annotation.NonNull android.content.pm.PackageStats packageStats, final int i, final boolean z) {
            int userId = android.os.UserHandle.getUserId(i);
            final java.util.concurrent.atomic.AtomicLong atomicLong = new java.util.concurrent.atomic.AtomicLong(0L);
            com.android.server.blob.BlobStoreManagerService.this.forEachSessionInUser(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.blob.BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForUid$2(i, atomicLong, (com.android.server.blob.BlobStoreSession) obj);
                }
            }, userId);
            com.android.server.blob.BlobStoreManagerService.this.forEachBlob(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.blob.BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForUid$3(i, z, atomicLong, (com.android.server.blob.BlobMetadata) obj);
                }
            });
            packageStats.dataSize += atomicLong.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$augmentStatsForUid$2(int i, java.util.concurrent.atomic.AtomicLong atomicLong, com.android.server.blob.BlobStoreSession blobStoreSession) {
            if (blobStoreSession.getOwnerUid() == i) {
                atomicLong.getAndAdd(blobStoreSession.getSize());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$augmentStatsForUid$3(int i, boolean z, java.util.concurrent.atomic.AtomicLong atomicLong, com.android.server.blob.BlobMetadata blobMetadata) {
            if (blobMetadata.shouldAttributeToLeasee(i, z)) {
                atomicLong.getAndAdd(blobMetadata.getSize());
            }
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter
        public void augmentStatsForUser(@android.annotation.NonNull android.content.pm.PackageStats packageStats, @android.annotation.NonNull final android.os.UserHandle userHandle) {
            final java.util.concurrent.atomic.AtomicLong atomicLong = new java.util.concurrent.atomic.AtomicLong(0L);
            com.android.server.blob.BlobStoreManagerService.this.forEachSessionInUser(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.blob.BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForUser$4(atomicLong, (com.android.server.blob.BlobStoreSession) obj);
                }
            }, userHandle.getIdentifier());
            com.android.server.blob.BlobStoreManagerService.this.forEachBlob(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.blob.BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForUser$5(userHandle, atomicLong, (com.android.server.blob.BlobMetadata) obj);
                }
            });
            packageStats.dataSize += atomicLong.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$augmentStatsForUser$4(java.util.concurrent.atomic.AtomicLong atomicLong, com.android.server.blob.BlobStoreSession blobStoreSession) {
            atomicLong.getAndAdd(blobStoreSession.getSize());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$augmentStatsForUser$5(android.os.UserHandle userHandle, java.util.concurrent.atomic.AtomicLong atomicLong, com.android.server.blob.BlobMetadata blobMetadata) {
            if (blobMetadata.shouldAttributeToUser(userHandle.getIdentifier())) {
                atomicLong.getAndAdd(blobMetadata.getSize());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forEachSessionInUser(java.util.function.Consumer<com.android.server.blob.BlobStoreSession> consumer, int i) {
        synchronized (this.mBlobsLock) {
            try {
                android.util.LongSparseArray<com.android.server.blob.BlobStoreSession> userSessionsLocked = getUserSessionsLocked(i);
                int size = userSessionsLocked.size();
                for (int i2 = 0; i2 < size; i2++) {
                    consumer.accept(userSessionsLocked.valueAt(i2));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forEachBlob(java.util.function.Consumer<com.android.server.blob.BlobMetadata> consumer) {
        synchronized (this.mBlobsMap) {
            forEachBlobLocked(consumer);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsMap"})
    private void forEachBlobLocked(java.util.function.Consumer<com.android.server.blob.BlobMetadata> consumer) {
        int size = this.mBlobsMap.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(this.mBlobsMap.valueAt(i));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBlobsMap"})
    private void forEachBlobLocked(java.util.function.BiConsumer<android.app.blob.BlobHandle, com.android.server.blob.BlobMetadata> biConsumer) {
        int size = this.mBlobsMap.size();
        for (int i = 0; i < size; i++) {
            biConsumer.accept(this.mBlobsMap.keyAt(i), this.mBlobsMap.valueAt(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAllowedBlobStoreAccess(int i, java.lang.String str) {
        return (android.os.Process.isSdkSandboxUid(i) || android.os.Process.isIsolated(i) || this.mPackageManagerInternal.isInstantApp(str, android.os.UserHandle.getUserId(i))) ? false : true;
    }

    private class PackageChangedReceiver extends android.content.BroadcastReceiver {
        private PackageChangedReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            boolean z;
            if (com.android.server.blob.BlobStoreConfig.LOGV) {
                android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Received " + intent);
            }
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case 267468725:
                    if (action.equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 1580442797:
                    if (action.equals("android.intent.action.PACKAGE_FULLY_REMOVED")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                case true:
                    java.lang.String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    if (schemeSpecificPart == null) {
                        android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Package name is missing in the intent: " + intent);
                        break;
                    } else {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        if (intExtra == -1) {
                            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "uid is missing in the intent: " + intent);
                            break;
                        } else {
                            com.android.server.blob.BlobStoreManagerService.this.handlePackageRemoved(schemeSpecificPart, intExtra);
                            break;
                        }
                    }
                default:
                    android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Received unknown intent: " + intent);
                    break;
            }
        }
    }

    private class UserActionReceiver extends android.content.BroadcastReceiver {
        private UserActionReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            if (com.android.server.blob.BlobStoreConfig.LOGV) {
                android.util.Slog.v(com.android.server.blob.BlobStoreConfig.TAG, "Received: " + intent);
            }
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case -2061058799:
                    if (action.equals("android.intent.action.USER_REMOVED")) {
                        c = 0;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                    if (intExtra == -10000) {
                        android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "userId is missing in the intent: " + intent);
                        break;
                    } else {
                        com.android.server.blob.BlobStoreManagerService.this.handleUserRemoved(intExtra);
                        break;
                    }
                default:
                    android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Received unknown intent: " + intent);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Stub extends android.app.blob.IBlobStoreManager.Stub {
        private Stub() {
        }

        public long createSession(@android.annotation.NonNull android.app.blob.BlobHandle blobHandle, @android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            java.util.Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.blob.BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!com.android.server.blob.BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new java.lang.SecurityException("Caller not allowed to create session; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            try {
                return com.android.server.blob.BlobStoreManagerService.this.createSessionInternal(blobHandle, callingUid, str);
            } catch (android.os.LimitExceededException e) {
                throw new android.os.ParcelableException(e);
            }
        }

        @android.annotation.NonNull
        public android.app.blob.IBlobStoreSession openSession(long j, @android.annotation.NonNull java.lang.String str) {
            com.android.internal.util.Preconditions.checkArgumentPositive(j, "sessionId must be positive: " + j);
            java.util.Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.blob.BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            return com.android.server.blob.BlobStoreManagerService.this.openSessionInternal(j, callingUid, str);
        }

        public void abandonSession(long j, @android.annotation.NonNull java.lang.String str) {
            com.android.internal.util.Preconditions.checkArgumentPositive(j, "sessionId must be positive: " + j);
            java.util.Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.blob.BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            com.android.server.blob.BlobStoreManagerService.this.abandonSessionInternal(j, callingUid, str);
        }

        public android.os.ParcelFileDescriptor openBlob(@android.annotation.NonNull android.app.blob.BlobHandle blobHandle, @android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            java.util.Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.blob.BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!com.android.server.blob.BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new java.lang.SecurityException("Caller not allowed to open blob; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            try {
                return com.android.server.blob.BlobStoreManagerService.this.openBlobInternal(blobHandle, callingUid, str);
            } catch (java.io.IOException e) {
                throw android.util.ExceptionUtils.wrap(e);
            }
        }

        public void acquireLease(@android.annotation.NonNull android.app.blob.BlobHandle blobHandle, int i, @android.annotation.Nullable java.lang.CharSequence charSequence, long j, @android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            com.android.internal.util.Preconditions.checkArgument(android.content.res.ResourceId.isValid(i) || charSequence != null, "Description must be valid; descriptionId=" + i + ", description=" + ((java.lang.Object) charSequence));
            com.android.internal.util.Preconditions.checkArgumentNonnegative(j, "leaseExpiryTimeMillis must not be negative");
            java.util.Objects.requireNonNull(str, "packageName must not be null");
            java.lang.CharSequence truncatedLeaseDescription = com.android.server.blob.BlobStoreConfig.getTruncatedLeaseDescription(charSequence);
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.blob.BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!com.android.server.blob.BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new java.lang.SecurityException("Caller not allowed to open blob; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            try {
                com.android.server.blob.BlobStoreManagerService.this.acquireLeaseInternal(blobHandle, i, truncatedLeaseDescription, j, callingUid, str);
            } catch (android.content.res.Resources.NotFoundException e) {
                throw new java.lang.IllegalArgumentException(e);
            } catch (android.os.LimitExceededException e2) {
                throw new android.os.ParcelableException(e2);
            }
        }

        public void releaseLease(@android.annotation.NonNull android.app.blob.BlobHandle blobHandle, @android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            java.util.Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.blob.BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!com.android.server.blob.BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new java.lang.SecurityException("Caller not allowed to open blob; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            com.android.server.blob.BlobStoreManagerService.this.releaseLeaseInternal(blobHandle, callingUid, str);
        }

        public void releaseAllLeases(@android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.blob.BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!com.android.server.blob.BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new java.lang.SecurityException("Caller not allowed to open blob; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            com.android.server.blob.BlobStoreManagerService.this.releaseAllLeasesInternal(callingUid, str);
        }

        public long getRemainingLeaseQuotaBytes(@android.annotation.NonNull java.lang.String str) {
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.blob.BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            return com.android.server.blob.BlobStoreManagerService.this.getRemainingLeaseQuotaBytesInternal(callingUid, str);
        }

        public void waitForIdle(@android.annotation.NonNull final android.os.RemoteCallback remoteCallback) {
            java.util.Objects.requireNonNull(remoteCallback, "remoteCallback must not be null");
            com.android.server.blob.BlobStoreManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "Caller is not allowed to call this; caller=" + android.os.Binder.getCallingUid());
            com.android.server.blob.BlobStoreManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$Stub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.blob.BlobStoreManagerService.Stub.this.lambda$waitForIdle$1(remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$waitForIdle$1(final android.os.RemoteCallback remoteCallback) {
            com.android.server.blob.BlobStoreManagerService.this.mBackgroundHandler.post(new java.lang.Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$Stub$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.blob.BlobStoreManagerService.Stub.this.lambda$waitForIdle$0(remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$waitForIdle$0(final android.os.RemoteCallback remoteCallback) {
            android.os.Handler handler = com.android.server.blob.BlobStoreManagerService.this.mHandler;
            java.util.Objects.requireNonNull(remoteCallback);
            handler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$Stub$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    remoteCallback.sendResult((android.os.Bundle) obj);
                }
            }, (java.lang.Object) null).recycleOnUse());
        }

        @android.annotation.NonNull
        public java.util.List<android.app.blob.BlobInfo> queryBlobsForUser(int i) {
            verifyCallerIsSystemUid("queryBlobsForUser");
            if (i == -2) {
                i = android.app.ActivityManager.getCurrentUser();
            }
            ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).ensureNotSpecialUser(i);
            return com.android.server.blob.BlobStoreManagerService.this.queryBlobsForUserInternal(i);
        }

        public void deleteBlob(long j) {
            verifyCallerIsSystemUid("deleteBlob");
            com.android.server.blob.BlobStoreManagerService.this.deleteBlobInternal(j);
        }

        @android.annotation.NonNull
        public java.util.List<android.app.blob.BlobHandle> getLeasedBlobs(@android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.blob.BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            return com.android.server.blob.BlobStoreManagerService.this.getLeasedBlobsInternal(callingUid, str);
        }

        @android.annotation.Nullable
        public android.app.blob.LeaseInfo getLeaseInfo(@android.annotation.NonNull android.app.blob.BlobHandle blobHandle, @android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            java.util.Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.blob.BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!com.android.server.blob.BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new java.lang.SecurityException("Caller not allowed to open blob; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            return com.android.server.blob.BlobStoreManagerService.this.getLeaseInfoInternal(blobHandle, callingUid, str);
        }

        public void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.blob.BlobStoreManagerService.this.mContext, com.android.server.blob.BlobStoreConfig.TAG, printWriter)) {
                com.android.server.blob.BlobStoreManagerService.DumpArgs parse = com.android.server.blob.BlobStoreManagerService.DumpArgs.parse(strArr);
                android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "    ");
                if (parse.shouldDumpHelp()) {
                    printWriter.println("dumpsys blob_store [options]:");
                    indentingPrintWriter.increaseIndent();
                    parse.dumpArgsUsage(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                    return;
                }
                synchronized (com.android.server.blob.BlobStoreManagerService.this.mBlobsLock) {
                    try {
                        if (parse.shouldDumpAllSections()) {
                            indentingPrintWriter.println("mCurrentMaxSessionId: " + com.android.server.blob.BlobStoreManagerService.this.mCurrentMaxSessionId);
                            indentingPrintWriter.println();
                        }
                        if (parse.shouldDumpSessions()) {
                            com.android.server.blob.BlobStoreManagerService.this.dumpSessionsLocked(indentingPrintWriter, parse);
                            indentingPrintWriter.println();
                        }
                        if (parse.shouldDumpBlobs()) {
                            com.android.server.blob.BlobStoreManagerService.this.dumpBlobsLocked(indentingPrintWriter, parse);
                            indentingPrintWriter.println();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (parse.shouldDumpConfig()) {
                    indentingPrintWriter.println("BlobStore config:");
                    indentingPrintWriter.increaseIndent();
                    com.android.server.blob.BlobStoreConfig.dump(indentingPrintWriter, com.android.server.blob.BlobStoreManagerService.this.mContext);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(@android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor2, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr) {
            return new com.android.server.blob.BlobStoreManagerShellCommand(com.android.server.blob.BlobStoreManagerService.this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }

        private void verifyCallerIsSystemUid(java.lang.String str) {
            if (android.os.UserHandle.getCallingAppId() != 1000 || !((android.os.UserManager) com.android.server.blob.BlobStoreManagerService.this.mContext.getSystemService(android.os.UserManager.class)).isUserAdmin(android.os.UserHandle.getCallingUserId())) {
                throw new java.lang.SecurityException("Only admin user's app with system uidare allowed to call #" + str);
            }
        }
    }

    static final class DumpArgs {
        private static final int FLAG_DUMP_BLOBS = 2;
        private static final int FLAG_DUMP_CONFIG = 4;
        private static final int FLAG_DUMP_SESSIONS = 1;
        private boolean mDumpAll;
        private boolean mDumpHelp;
        private boolean mDumpUnredacted;
        private int mSelectedSectionFlags;
        private final java.util.ArrayList<java.lang.String> mDumpPackages = new java.util.ArrayList<>();
        private final java.util.ArrayList<java.lang.Integer> mDumpUids = new java.util.ArrayList<>();
        private final java.util.ArrayList<java.lang.Integer> mDumpUserIds = new java.util.ArrayList<>();
        private final java.util.ArrayList<java.lang.Long> mDumpBlobIds = new java.util.ArrayList<>();

        public boolean shouldDumpSession(java.lang.String str, int i, long j) {
            if (!com.android.internal.util.CollectionUtils.isEmpty(this.mDumpPackages) && this.mDumpPackages.indexOf(str) < 0) {
                return false;
            }
            if (com.android.internal.util.CollectionUtils.isEmpty(this.mDumpUids) || this.mDumpUids.indexOf(java.lang.Integer.valueOf(i)) >= 0) {
                return com.android.internal.util.CollectionUtils.isEmpty(this.mDumpBlobIds) || this.mDumpBlobIds.indexOf(java.lang.Long.valueOf(j)) >= 0;
            }
            return false;
        }

        public boolean shouldDumpAllSections() {
            return this.mDumpAll || this.mSelectedSectionFlags == 0;
        }

        public void allowDumpSessions() {
            this.mSelectedSectionFlags |= 1;
        }

        public boolean shouldDumpSessions() {
            return shouldDumpAllSections() || (this.mSelectedSectionFlags & 1) != 0;
        }

        public void allowDumpBlobs() {
            this.mSelectedSectionFlags |= 2;
        }

        public boolean shouldDumpBlobs() {
            return shouldDumpAllSections() || (this.mSelectedSectionFlags & 2) != 0;
        }

        public void allowDumpConfig() {
            this.mSelectedSectionFlags |= 4;
        }

        public boolean shouldDumpConfig() {
            return shouldDumpAllSections() || (this.mSelectedSectionFlags & 4) != 0;
        }

        public boolean shouldDumpBlob(long j) {
            return com.android.internal.util.CollectionUtils.isEmpty(this.mDumpBlobIds) || this.mDumpBlobIds.indexOf(java.lang.Long.valueOf(j)) >= 0;
        }

        public boolean shouldDumpFull() {
            return this.mDumpUnredacted;
        }

        public boolean shouldDumpUser(int i) {
            return com.android.internal.util.CollectionUtils.isEmpty(this.mDumpUserIds) || this.mDumpUserIds.indexOf(java.lang.Integer.valueOf(i)) >= 0;
        }

        public boolean shouldDumpHelp() {
            return this.mDumpHelp;
        }

        private DumpArgs() {
        }

        public static com.android.server.blob.BlobStoreManagerService.DumpArgs parse(java.lang.String[] strArr) {
            com.android.server.blob.BlobStoreManagerService.DumpArgs dumpArgs = new com.android.server.blob.BlobStoreManagerService.DumpArgs();
            if (strArr == null) {
                return dumpArgs;
            }
            int i = 0;
            while (i < strArr.length) {
                java.lang.String str = strArr[i];
                if ("--all".equals(str) || "-a".equals(str)) {
                    dumpArgs.mDumpAll = true;
                } else if ("--unredacted".equals(str) || "-u".equals(str)) {
                    int callingUid = android.os.Binder.getCallingUid();
                    if (callingUid == 2000 || callingUid == 0) {
                        dumpArgs.mDumpUnredacted = true;
                    }
                } else if ("--sessions".equals(str)) {
                    dumpArgs.allowDumpSessions();
                } else if ("--blobs".equals(str)) {
                    dumpArgs.allowDumpBlobs();
                } else if ("--config".equals(str)) {
                    dumpArgs.allowDumpConfig();
                } else if ("--package".equals(str) || "-p".equals(str)) {
                    i++;
                    dumpArgs.mDumpPackages.add(getStringArgRequired(strArr, i, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME));
                } else if ("--uid".equals(str)) {
                    i++;
                    dumpArgs.mDumpUids.add(java.lang.Integer.valueOf(getIntArgRequired(strArr, i, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID)));
                } else if ("--user".equals(str)) {
                    i++;
                    dumpArgs.mDumpUserIds.add(java.lang.Integer.valueOf(getIntArgRequired(strArr, i, "userId")));
                } else if ("--blob".equals(str) || "-b".equals(str)) {
                    i++;
                    dumpArgs.mDumpBlobIds.add(java.lang.Long.valueOf(getLongArgRequired(strArr, i, "blobId")));
                } else if ("--help".equals(str) || "-h".equals(str)) {
                    dumpArgs.mDumpHelp = true;
                } else {
                    dumpArgs.mDumpBlobIds.add(java.lang.Long.valueOf(getLongArgRequired(strArr, i, "blobId")));
                }
                i++;
            }
            return dumpArgs;
        }

        private static java.lang.String getStringArgRequired(java.lang.String[] strArr, int i, java.lang.String str) {
            if (i >= strArr.length) {
                throw new java.lang.IllegalArgumentException("Missing " + str);
            }
            return strArr[i];
        }

        private static int getIntArgRequired(java.lang.String[] strArr, int i, java.lang.String str) {
            if (i >= strArr.length) {
                throw new java.lang.IllegalArgumentException("Missing " + str);
            }
            try {
                return java.lang.Integer.parseInt(strArr[i]);
            } catch (java.lang.NumberFormatException e) {
                throw new java.lang.IllegalArgumentException("Invalid " + str + ": " + strArr[i]);
            }
        }

        private static long getLongArgRequired(java.lang.String[] strArr, int i, java.lang.String str) {
            if (i >= strArr.length) {
                throw new java.lang.IllegalArgumentException("Missing " + str);
            }
            try {
                return java.lang.Long.parseLong(strArr[i]);
            } catch (java.lang.NumberFormatException e) {
                throw new java.lang.IllegalArgumentException("Invalid " + str + ": " + strArr[i]);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpArgsUsage(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("--help | -h");
            printWithIndent(indentingPrintWriter, "Dump this help text");
            indentingPrintWriter.println("--sessions");
            printWithIndent(indentingPrintWriter, "Dump only the sessions info");
            indentingPrintWriter.println("--blobs");
            printWithIndent(indentingPrintWriter, "Dump only the committed blobs info");
            indentingPrintWriter.println("--config");
            printWithIndent(indentingPrintWriter, "Dump only the config values");
            indentingPrintWriter.println("--package | -p [package-name]");
            printWithIndent(indentingPrintWriter, "Dump blobs info associated with the given package");
            indentingPrintWriter.println("--uid | -u [uid]");
            printWithIndent(indentingPrintWriter, "Dump blobs info associated with the given uid");
            indentingPrintWriter.println("--user [user-id]");
            printWithIndent(indentingPrintWriter, "Dump blobs info in the given user");
            indentingPrintWriter.println("--blob | -b [session-id | blob-id]");
            printWithIndent(indentingPrintWriter, "Dump blob info corresponding to the given ID");
            indentingPrintWriter.println("--full | -f");
            printWithIndent(indentingPrintWriter, "Dump full unredacted blobs data");
        }

        private void printWithIndent(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(str);
            indentingPrintWriter.decreaseIndent();
        }
    }

    private void registerBlobStorePuller() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BLOB_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private class StatsPullAtomCallbackImpl implements android.app.StatsManager.StatsPullAtomCallback {
        private StatsPullAtomCallbackImpl() {
        }

        public int onPullAtom(int i, java.util.List<android.util.StatsEvent> list) {
            switch (i) {
                case com.android.internal.util.FrameworkStatsLog.BLOB_INFO /* 10081 */:
                    return com.android.server.blob.BlobStoreManagerService.this.pullBlobData(i, list);
                default:
                    throw new java.lang.UnsupportedOperationException("Unknown tagId=" + i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullBlobData$18(java.util.List list, int i, com.android.server.blob.BlobMetadata blobMetadata) {
        list.add(blobMetadata.dumpAsStatsEvent(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int pullBlobData(final int i, final java.util.List<android.util.StatsEvent> list) {
        forEachBlob(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.blob.BlobStoreManagerService.lambda$pullBlobData$18(list, i, (com.android.server.blob.BlobMetadata) obj);
            }
        });
        return 0;
    }

    private class LocalService extends com.android.server.blob.BlobStoreManagerInternal {
        private LocalService() {
        }

        @Override // com.android.server.blob.BlobStoreManagerInternal
        public void onIdleMaintenance() {
            com.android.server.blob.BlobStoreManagerService.this.runIdleMaintenance();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        public android.os.Handler initializeMessageHandler() {
            return com.android.server.blob.BlobStoreManagerService.initializeMessageHandler();
        }

        public android.os.Handler getBackgroundHandler() {
            return com.android.internal.os.BackgroundThread.getHandler();
        }
    }
}
