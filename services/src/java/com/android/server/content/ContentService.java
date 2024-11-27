package com.android.server.content;

/* loaded from: classes.dex */
public final class ContentService extends android.content.IContentService.Stub {
    public static final long ACCOUNT_ACCESS_CHECK_CHANGE_ID = 201794303;
    public static final long AUTHORITY_ACCESS_CHECK_CHANGE_ID = 207133734;
    private static final long BACKGROUND_OBSERVER_DELAY = 10000;
    static final boolean DEBUG = false;
    static final java.lang.String TAG = "ContentService";
    private static final int TOO_MANY_OBSERVERS_THRESHOLD = 1000;
    private static final com.android.internal.os.BinderDeathDispatcher<android.database.IContentObserver> sObserverDeathDispatcher = new com.android.internal.os.BinderDeathDispatcher<>();

    @com.android.internal.annotations.GuardedBy({"sObserverLeakDetectedUid"})
    private static final android.util.ArraySet<java.lang.Integer> sObserverLeakDetectedUid = new android.util.ArraySet<>(0);
    private final android.accounts.AccountManagerInternal mAccountManagerInternal;
    private android.content.Context mContext;
    private boolean mFactoryTest;
    private final com.android.server.content.ContentService.ObserverNode mRootNode = new com.android.server.content.ContentService.ObserverNode("");
    private com.android.server.content.SyncManager mSyncManager = null;
    private final java.lang.Object mSyncManagerLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mCache"})
    private final android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.ArrayMap<android.util.Pair<java.lang.String, android.net.Uri>, android.os.Bundle>>> mCache = new android.util.SparseArray<>();
    private android.content.BroadcastReceiver mCacheReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.content.ContentService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            synchronized (com.android.server.content.ContentService.this.mCache) {
                try {
                    if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                        com.android.server.content.ContentService.this.mCache.clear();
                    } else {
                        android.net.Uri data = intent.getData();
                        if (data != null) {
                            com.android.server.content.ContentService.this.invalidateCacheLocked(intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ), data.getSchemeSpecificPart(), null);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.content.ContentService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mService = new com.android.server.content.ContentService(getContext(), android.os.FactoryTest.getMode() == 1);
            publishBinderService(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT, this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            this.mService.onBootPhase(i);
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onStartUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onUnlockUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.onStopUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            synchronized (this.mService.mCache) {
                this.mService.mCache.remove(targetUser.getUserIdentifier());
            }
        }
    }

    private com.android.server.content.SyncManager getSyncManager() {
        com.android.server.content.SyncManager syncManager;
        synchronized (this.mSyncManagerLock) {
            try {
                if (this.mSyncManager == null) {
                    this.mSyncManager = new com.android.server.content.SyncManager(this.mContext, this.mFactoryTest);
                }
                syncManager = this.mSyncManager;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return syncManager;
    }

    void onStartUser(int i) {
        if (this.mSyncManager != null) {
            this.mSyncManager.onStartUser(i);
        }
    }

    void onUnlockUser(int i) {
        if (this.mSyncManager != null) {
            this.mSyncManager.onUnlockUser(i);
        }
    }

    void onStopUser(int i) {
        if (this.mSyncManager != null) {
            this.mSyncManager.onStopUser(i);
        }
    }

    protected synchronized void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        int i;
        if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, printWriter)) {
            java.io.PrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            boolean contains = com.android.internal.util.ArrayUtils.contains(strArr, "-a");
            long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
            try {
                if (this.mSyncManager == null) {
                    indentingPrintWriter.println("SyncManager not available yet");
                } else {
                    this.mSyncManager.dump(fileDescriptor, indentingPrintWriter, contains);
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Observer tree:");
                synchronized (this.mRootNode) {
                    try {
                        int[] iArr = new int[2];
                        final android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
                        this.mRootNode.dumpLocked(fileDescriptor, indentingPrintWriter, strArr, "", "  ", iArr, sparseIntArray);
                        indentingPrintWriter.println();
                        java.util.ArrayList arrayList = new java.util.ArrayList();
                        for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                            arrayList.add(java.lang.Integer.valueOf(sparseIntArray.keyAt(i2)));
                        }
                        java.util.Collections.sort(arrayList, new java.util.Comparator<java.lang.Integer>() { // from class: com.android.server.content.ContentService.2
                            @Override // java.util.Comparator
                            public int compare(java.lang.Integer num, java.lang.Integer num2) {
                                int i3 = sparseIntArray.get(num.intValue());
                                int i4 = sparseIntArray.get(num2.intValue());
                                if (i3 < i4) {
                                    return 1;
                                }
                                if (i3 > i4) {
                                    return -1;
                                }
                                return 0;
                            }
                        });
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            int intValue = ((java.lang.Integer) arrayList.get(i3)).intValue();
                            indentingPrintWriter.print("  pid ");
                            indentingPrintWriter.print(intValue);
                            indentingPrintWriter.print(": ");
                            indentingPrintWriter.print(sparseIntArray.get(intValue));
                            indentingPrintWriter.println(" observers");
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.print("Total number of nodes: ");
                        indentingPrintWriter.println(iArr[0]);
                        indentingPrintWriter.print("Total number of observers: ");
                        indentingPrintWriter.println(iArr[1]);
                        sObserverDeathDispatcher.dump(indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                    } finally {
                    }
                }
                synchronized (sObserverLeakDetectedUid) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("Observer leaking UIDs: ");
                    indentingPrintWriter.println(sObserverLeakDetectedUid.toString());
                }
                synchronized (this.mCache) {
                    try {
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Cached content:");
                        indentingPrintWriter.increaseIndent();
                        for (i = 0; i < this.mCache.size(); i++) {
                            indentingPrintWriter.println("User " + this.mCache.keyAt(i) + ":");
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println(this.mCache.valueAt(i));
                            indentingPrintWriter.decreaseIndent();
                        }
                        indentingPrintWriter.decreaseIndent();
                    } finally {
                    }
                }
                android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    ContentService(android.content.Context context, boolean z) {
        this.mContext = context;
        this.mFactoryTest = z;
        ((com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class)).setSyncAdapterPackagesProvider(new com.android.server.pm.permission.LegacyPermissionManagerInternal.SyncAdapterPackagesProvider() { // from class: com.android.server.content.ContentService$$ExternalSyntheticLambda0
            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.SyncAdapterPackagesProvider
            public final java.lang.String[] getPackages(java.lang.String str, int i) {
                java.lang.String[] lambda$new$0;
                lambda$new$0 = com.android.server.content.ContentService.this.lambda$new$0(str, i);
                return lambda$new$0;
            }
        });
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverAsUser(this.mCacheReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.LOCALE_CHANGED");
        this.mContext.registerReceiverAsUser(this.mCacheReceiver, android.os.UserHandle.ALL, intentFilter2, null, null);
        this.mAccountManagerInternal = (android.accounts.AccountManagerInternal) com.android.server.LocalServices.getService(android.accounts.AccountManagerInternal.class);
    }

    void onBootPhase(int i) {
        switch (i) {
            case 550:
                getSyncManager();
                break;
        }
        if (this.mSyncManager != null) {
            this.mSyncManager.onBootPhase(i);
        }
    }

    public void registerContentObserver(android.net.Uri uri, boolean z, android.database.IContentObserver iContentObserver, int i, int i2) {
        if (iContentObserver == null || uri == null) {
            throw new java.lang.IllegalArgumentException("You must pass a valid uri and observer");
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int handleIncomingUser = handleIncomingUser(uri, callingPid, callingUid, 1, true, i);
        java.lang.String checkContentProviderAccess = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).checkContentProviderAccess(uri.getAuthority(), handleIncomingUser);
        if (checkContentProviderAccess != null) {
            if (i2 >= 26) {
                throw new java.lang.SecurityException(checkContentProviderAccess);
            }
            if (!checkContentProviderAccess.startsWith("Failed to find provider")) {
                android.util.Log.w(TAG, "Ignoring content changes for " + uri + " from " + callingUid + ": " + checkContentProviderAccess);
                return;
            }
        }
        synchronized (this.mRootNode) {
            this.mRootNode.addObserverLocked(uri, iContentObserver, z, this.mRootNode, callingUid, callingPid, handleIncomingUser);
        }
    }

    public void registerContentObserver(android.net.Uri uri, boolean z, android.database.IContentObserver iContentObserver) {
        registerContentObserver(uri, z, iContentObserver, android.os.UserHandle.getCallingUserId(), 10000);
    }

    public void unregisterContentObserver(android.database.IContentObserver iContentObserver) {
        if (iContentObserver == null) {
            throw new java.lang.IllegalArgumentException("You must pass a valid observer");
        }
        synchronized (this.mRootNode) {
            this.mRootNode.removeObserverLocked(iContentObserver);
        }
    }

    public void notifyChange(android.net.Uri[] uriArr, android.database.IContentObserver iContentObserver, boolean z, int i, int i2, int i3, java.lang.String str) {
        android.util.ArrayMap arrayMap;
        int i4;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        com.android.server.content.ContentService.ObserverCollector observerCollector = new com.android.server.content.ContentService.ObserverCollector();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        for (android.net.Uri uri : uriArr) {
            int handleIncomingUser = handleIncomingUser(uri, callingPid, callingUid, 2, true, i2);
            android.util.Pair create = android.util.Pair.create(uri.getAuthority(), java.lang.Integer.valueOf(handleIncomingUser));
            if (!arrayMap2.containsKey(create)) {
                java.lang.String checkContentProviderAccess = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).checkContentProviderAccess(uri.getAuthority(), handleIncomingUser);
                if (checkContentProviderAccess != null) {
                    if (i3 >= 26) {
                        throw new java.lang.SecurityException(checkContentProviderAccess);
                    }
                    if (!checkContentProviderAccess.startsWith("Failed to find provider")) {
                        android.util.Log.w(TAG, "Ignoring notify for " + uri + " from " + callingUid + ": " + checkContentProviderAccess);
                    }
                }
                arrayMap2.put(create, getProviderPackageName(uri, handleIncomingUser));
            }
            synchronized (this.mRootNode) {
                this.mRootNode.collectObserversLocked(uri, com.android.server.content.ContentService.ObserverNode.countUriSegments(uri), 0, iContentObserver, z, i, handleIncomingUser, observerCollector);
            }
        }
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            observerCollector.dispatch();
            com.android.server.content.SyncManager syncManager = getSyncManager();
            int i5 = 0;
            while (i5 < arrayMap2.size()) {
                java.lang.String str2 = (java.lang.String) ((android.util.Pair) arrayMap2.keyAt(i5)).first;
                int intValue = ((java.lang.Integer) ((android.util.Pair) arrayMap2.keyAt(i5)).second).intValue();
                java.lang.String str3 = (java.lang.String) arrayMap2.valueAt(i5);
                if ((i & 1) == 0) {
                    arrayMap = arrayMap2;
                    i4 = callingUid;
                } else {
                    arrayMap = arrayMap2;
                    i4 = callingUid;
                    syncManager.scheduleLocalSync(null, callingUserId, callingUid, str2, getSyncExemptionForCaller(callingUid), callingUid, callingPid, str);
                }
                synchronized (this.mCache) {
                    try {
                        for (android.net.Uri uri2 : uriArr) {
                            if (java.util.Objects.equals(uri2.getAuthority(), str2)) {
                                invalidateCacheLocked(intValue, str3, uri2);
                            }
                        }
                    } finally {
                    }
                }
                i5++;
                arrayMap2 = arrayMap;
                callingUid = i4;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private int checkUriPermission(android.net.Uri uri, int i, int i2, int i3, int i4) {
        try {
            return android.app.ActivityManager.getService().checkUriPermission(uri, i, i2, i3, i4, (android.os.IBinder) null);
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class ObserverCollector {
        private final android.util.ArrayMap<com.android.server.content.ContentService.ObserverCollector.Key, java.util.List<android.net.Uri>> collected = new android.util.ArrayMap<>();

        /* JADX INFO: Access modifiers changed from: private */
        static class Key {
            final int flags;
            final android.database.IContentObserver observer;
            final boolean selfChange;
            final int uid;
            final int userId;

            Key(android.database.IContentObserver iContentObserver, int i, boolean z, int i2, int i3) {
                this.observer = iContentObserver;
                this.uid = i;
                this.selfChange = z;
                this.flags = i2;
                this.userId = i3;
            }

            public boolean equals(java.lang.Object obj) {
                if (!(obj instanceof com.android.server.content.ContentService.ObserverCollector.Key)) {
                    return false;
                }
                com.android.server.content.ContentService.ObserverCollector.Key key = (com.android.server.content.ContentService.ObserverCollector.Key) obj;
                return java.util.Objects.equals(this.observer, key.observer) && this.uid == key.uid && this.selfChange == key.selfChange && this.flags == key.flags && this.userId == key.userId;
            }

            public int hashCode() {
                return java.util.Objects.hash(this.observer, java.lang.Integer.valueOf(this.uid), java.lang.Boolean.valueOf(this.selfChange), java.lang.Integer.valueOf(this.flags), java.lang.Integer.valueOf(this.userId));
            }
        }

        public void collect(android.database.IContentObserver iContentObserver, int i, boolean z, android.net.Uri uri, int i2, int i3) {
            com.android.server.content.ContentService.ObserverCollector.Key key = new com.android.server.content.ContentService.ObserverCollector.Key(iContentObserver, i, z, i2, i3);
            java.util.List<android.net.Uri> list = this.collected.get(key);
            if (list == null) {
                list = new java.util.ArrayList<>();
                this.collected.put(key, list);
            }
            list.add(uri);
        }

        public void dispatch() {
            for (int i = 0; i < this.collected.size(); i++) {
                final com.android.server.content.ContentService.ObserverCollector.Key keyAt = this.collected.keyAt(i);
                final java.util.List<android.net.Uri> valueAt = this.collected.valueAt(i);
                java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.content.ContentService$ObserverCollector$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.content.ContentService.ObserverCollector.lambda$dispatch$0(com.android.server.content.ContentService.ObserverCollector.Key.this, valueAt);
                    }
                };
                boolean z = (keyAt.flags & 32768) != 0;
                if (((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getUidProcessState(keyAt.uid) <= 6 || z) {
                    runnable.run();
                } else {
                    com.android.internal.os.BackgroundThread.getHandler().postDelayed(runnable, 10000L);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$dispatch$0(com.android.server.content.ContentService.ObserverCollector.Key key, java.util.List list) {
            try {
                key.observer.onChangeEtc(key.selfChange, (android.net.Uri[]) list.toArray(new android.net.Uri[list.size()]), key.flags, key.userId);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void requestSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.lang.String str2) {
        android.os.Bundle.setDefusable(bundle, true);
        android.content.ContentResolver.validateSyncExtrasBundle(bundle);
        int callingUserId = android.os.UserHandle.getCallingUserId();
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        if (!hasAccountAccess(true, account, callingUid) || !hasAuthorityAccess(str, callingUid, callingUserId)) {
            return;
        }
        validateExtras(callingUid, bundle);
        int syncExemptionAndCleanUpExtrasForCaller = getSyncExemptionAndCleanUpExtrasForCaller(callingUid, bundle);
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            getSyncManager().scheduleSync(account, callingUserId, callingUid, str, bundle, -2, syncExemptionAndCleanUpExtrasForCaller, callingUid, callingPid, str2);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void sync(android.content.SyncRequest syncRequest, java.lang.String str) {
        syncAsUser(syncRequest, android.os.UserHandle.getCallingUserId(), str);
    }

    private long clampPeriod(long j) {
        long minPeriodMillis = android.app.job.JobInfo.getMinPeriodMillis() / 1000;
        if (j < minPeriodMillis) {
            android.util.Slog.w(TAG, "Requested poll frequency of " + j + " seconds being rounded up to " + minPeriodMillis + "s.");
            return minPeriodMillis;
        }
        return j;
    }

    public void syncAsUser(android.content.SyncRequest syncRequest, int i, java.lang.String str) {
        enforceCrossUserPermission(i, "no permission to request sync as user: " + i);
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        if (!hasAccountAccess(true, syncRequest.getAccount(), callingUid) || !hasAuthorityAccess(syncRequest.getProvider(), callingUid, i)) {
            return;
        }
        android.os.Bundle bundle = syncRequest.getBundle();
        validateExtras(callingUid, bundle);
        int syncExemptionAndCleanUpExtrasForCaller = getSyncExemptionAndCleanUpExtrasForCaller(callingUid, bundle);
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            long syncFlexTime = syncRequest.getSyncFlexTime();
            long syncRunTime = syncRequest.getSyncRunTime();
            if (syncRequest.isPeriodic()) {
                this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
                getSyncManager().updateOrAddPeriodicSync(new com.android.server.content.SyncStorageEngine.EndPoint(syncRequest.getAccount(), syncRequest.getProvider(), i), clampPeriod(syncRunTime), syncFlexTime, bundle);
            } else {
                getSyncManager().scheduleSync(syncRequest.getAccount(), i, callingUid, syncRequest.getProvider(), bundle, -2, syncExemptionAndCleanUpExtrasForCaller, callingUid, callingPid, str);
            }
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void cancelSync(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) {
        cancelSyncAsUser(account, str, componentName, android.os.UserHandle.getCallingUserId());
    }

    public void cancelSyncAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) {
        if (str != null && str.length() == 0) {
            throw new java.lang.IllegalArgumentException("Authority must be non-empty");
        }
        enforceCrossUserPermission(i, "no permission to modify the sync settings for user " + i);
        if (componentName != null) {
            android.util.Slog.e(TAG, "cname not null.");
            return;
        }
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            com.android.server.content.SyncStorageEngine.EndPoint endPoint = new com.android.server.content.SyncStorageEngine.EndPoint(account, str, i);
            getSyncManager().clearScheduledSyncOperations(endPoint);
            getSyncManager().cancelActiveSync(endPoint, null, "API");
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void cancelRequest(android.content.SyncRequest syncRequest) {
        if (syncRequest.isPeriodic()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        }
        int callingUid = android.os.Binder.getCallingUid();
        android.os.Bundle bundle = new android.os.Bundle(syncRequest.getBundle());
        validateExtras(callingUid, bundle);
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            com.android.server.content.SyncStorageEngine.EndPoint endPoint = new com.android.server.content.SyncStorageEngine.EndPoint(syncRequest.getAccount(), syncRequest.getProvider(), callingUserId);
            if (syncRequest.isPeriodic()) {
                getSyncManager().removePeriodicSync(endPoint, bundle, "cancelRequest() by uid=" + callingUid);
            }
            getSyncManager().cancelScheduledSyncOperation(endPoint, bundle);
            getSyncManager().cancelActiveSync(endPoint, bundle, "API");
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public android.content.SyncAdapterType[] getSyncAdapterTypes() {
        return getSyncAdapterTypesAsUser(android.os.UserHandle.getCallingUserId());
    }

    public android.content.SyncAdapterType[] getSyncAdapterTypesAsUser(int i) {
        enforceCrossUserPermission(i, "no permission to read sync settings for user " + i);
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().getSyncAdapterTypes(callingUid, i);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* renamed from: getSyncAdapterPackagesForAuthorityAsUser, reason: merged with bridge method [inline-methods] */
    public java.lang.String[] lambda$new$0(java.lang.String str, int i) {
        enforceCrossUserPermission(i, "no permission to read sync settings for user " + i);
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().getSyncAdapterPackagesForAuthorityAsUser(str, callingUid, i);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    public java.lang.String getSyncAdapterPackageAsUser(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        enforceCrossUserPermission(i, "no permission to read sync settings for user " + i);
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().getSyncAdapterPackageAsUser(str, str2, callingUid, i);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean getSyncAutomatically(android.accounts.Account account, java.lang.String str) {
        return getSyncAutomaticallyAsUser(account, str, android.os.UserHandle.getCallingUserId());
    }

    public boolean getSyncAutomaticallyAsUser(android.accounts.Account account, java.lang.String str, int i) {
        enforceCrossUserPermission(i, "no permission to read the sync settings for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_SETTINGS", "no permission to read the sync settings");
        int callingUid = android.os.Binder.getCallingUid();
        if (!hasAccountAccess(true, account, callingUid) || !hasAuthorityAccess(str, callingUid, i)) {
            return false;
        }
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().getSyncStorageEngine().getSyncAutomatically(account, i, str);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setSyncAutomatically(android.accounts.Account account, java.lang.String str, boolean z) {
        setSyncAutomaticallyAsUser(account, str, z, android.os.UserHandle.getCallingUserId());
    }

    public void setSyncAutomaticallyAsUser(android.accounts.Account account, java.lang.String str, boolean z, int i) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Authority must be non-empty");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        enforceCrossUserPermission(i, "no permission to modify the sync settings for user " + i);
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        if (!hasAccountAccess(true, account, callingUid) || !hasAuthorityAccess(str, callingUid, i)) {
            return;
        }
        int syncExemptionForCaller = getSyncExemptionForCaller(callingUid);
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            getSyncManager().getSyncStorageEngine().setSyncAutomatically(account, i, str, z, syncExemptionForCaller, callingUid, callingPid);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addPeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, long j) {
        android.os.Bundle.setDefusable(bundle, true);
        if (account == null) {
            throw new java.lang.IllegalArgumentException("Account must not be null");
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Authority must not be empty.");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!hasAccountAccess(true, account, callingUid) || !hasAuthorityAccess(str, callingUid, callingUserId)) {
            return;
        }
        validateExtras(callingUid, bundle);
        long clampPeriod = clampPeriod(j);
        long calculateDefaultFlexTime = com.android.server.content.SyncStorageEngine.calculateDefaultFlexTime(clampPeriod);
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            getSyncManager().updateOrAddPeriodicSync(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, callingUserId), clampPeriod, calculateDefaultFlexTime, bundle);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removePeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) {
        android.os.Bundle.setDefusable(bundle, true);
        if (account == null) {
            throw new java.lang.IllegalArgumentException("Account must not be null");
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Authority must not be empty");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!hasAccountAccess(true, account, callingUid) || !hasAuthorityAccess(str, callingUid, callingUserId)) {
            return;
        }
        validateExtras(callingUid, bundle);
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            getSyncManager().removePeriodicSync(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, callingUserId), bundle, "removePeriodicSync() by uid=" + callingUid);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.util.List<android.content.PeriodicSync> getPeriodicSyncs(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) {
        if (account == null) {
            throw new java.lang.IllegalArgumentException("Account must not be null");
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Authority must not be empty");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_SETTINGS", "no permission to read the sync settings");
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!hasAccountAccess(true, account, callingUid)) {
            return new java.util.ArrayList();
        }
        if (!hasAuthorityAccess(str, callingUid, callingUserId)) {
            return new java.util.ArrayList();
        }
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().getPeriodicSyncs(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, callingUserId));
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getIsSyncable(android.accounts.Account account, java.lang.String str) {
        return getIsSyncableAsUser(account, str, android.os.UserHandle.getCallingUserId());
    }

    public int getIsSyncableAsUser(android.accounts.Account account, java.lang.String str, int i) {
        enforceCrossUserPermission(i, "no permission to read the sync settings for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_SETTINGS", "no permission to read the sync settings");
        int callingUid = android.os.Binder.getCallingUid();
        if (!hasAccountAccess(true, account, callingUid) || !hasAuthorityAccess(str, callingUid, i)) {
            return 0;
        }
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().computeSyncable(account, i, str, false, false);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setIsSyncable(android.accounts.Account account, java.lang.String str, int i) {
        setIsSyncableAsUser(account, str, i, android.os.UserHandle.getCallingUserId());
    }

    public void setIsSyncableAsUser(android.accounts.Account account, java.lang.String str, int i, int i2) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Authority must not be empty");
        }
        enforceCrossUserPermission(i2, "no permission to set the sync settings for user " + i2);
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        int normalizeSyncable = normalizeSyncable(i);
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        if (!hasAccountAccess(true, account, callingUid) || !hasAuthorityAccess(str, callingUid, i2)) {
            return;
        }
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            getSyncManager().getSyncStorageEngine().setIsSyncable(account, i2, str, normalizeSyncable, callingUid, callingPid);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean getMasterSyncAutomatically() {
        return getMasterSyncAutomaticallyAsUser(android.os.UserHandle.getCallingUserId());
    }

    public boolean getMasterSyncAutomaticallyAsUser(int i) {
        enforceCrossUserPermission(i, "no permission to read the sync settings for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_SETTINGS", "no permission to read the sync settings");
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().getSyncStorageEngine().getMasterSyncAutomatically(i);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setMasterSyncAutomatically(boolean z) {
        setMasterSyncAutomaticallyAsUser(z, android.os.UserHandle.getCallingUserId());
    }

    public void setMasterSyncAutomaticallyAsUser(boolean z, int i) {
        enforceCrossUserPermission(i, "no permission to set the sync status for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SYNC_SETTINGS", "no permission to write the sync settings");
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            getSyncManager().getSyncStorageEngine().setMasterSyncAutomatically(z, i, getSyncExemptionForCaller(callingUid), callingUid, callingPid);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.READ_SYNC_STATS")
    public boolean isSyncActive(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) {
        isSyncActive_enforcePermission();
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (!hasAccountAccess(true, account, callingUid) || !hasAuthorityAccess(str, callingUid, callingUserId)) {
            return false;
        }
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            return getSyncManager().getSyncStorageEngine().isSyncActive(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, callingUserId));
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.util.List<android.content.SyncInfo> getCurrentSyncs() {
        return getCurrentSyncsAsUser(android.os.UserHandle.getCallingUserId());
    }

    public java.util.List<android.content.SyncInfo> getCurrentSyncsAsUser(final int i) {
        enforceCrossUserPermission(i, "no permission to read the sync settings for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_STATS", "no permission to read the sync stats");
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.GET_ACCOUNTS") == 0;
        final int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            java.util.List<android.content.SyncInfo> currentSyncsCopy = getSyncManager().getSyncStorageEngine().getCurrentSyncsCopy(i, z);
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            currentSyncsCopy.removeIf(new java.util.function.Predicate() { // from class: com.android.server.content.ContentService$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getCurrentSyncsAsUser$1;
                    lambda$getCurrentSyncsAsUser$1 = com.android.server.content.ContentService.this.lambda$getCurrentSyncsAsUser$1(callingUid, i, (android.content.SyncInfo) obj);
                    return lambda$getCurrentSyncsAsUser$1;
                }
            });
            return currentSyncsCopy;
        } catch (java.lang.Throwable th) {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getCurrentSyncsAsUser$1(int i, int i2, android.content.SyncInfo syncInfo) {
        return !hasAuthorityAccess(syncInfo.authority, i, i2);
    }

    public android.content.SyncStatusInfo getSyncStatus(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) {
        return getSyncStatusAsUser(account, str, componentName, android.os.UserHandle.getCallingUserId());
    }

    @android.annotation.Nullable
    public android.content.SyncStatusInfo getSyncStatusAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Authority must not be empty");
        }
        enforceCrossUserPermission(i, "no permission to read the sync stats for user " + i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_SYNC_STATS", "no permission to read the sync stats");
        int callingUid = android.os.Binder.getCallingUid();
        if (!hasAccountAccess(true, account, callingUid) || !hasAuthorityAccess(str, callingUid, i)) {
            return null;
        }
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            if (account != null && str != null) {
                return getSyncManager().getSyncStorageEngine().getStatusByAuthority(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, i));
            }
            throw new java.lang.IllegalArgumentException("Must call sync status with valid authority");
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isSyncPending(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) {
        return isSyncPendingAsUser(account, str, componentName, android.os.UserHandle.getCallingUserId());
    }

    @android.annotation.EnforcePermission("android.permission.READ_SYNC_STATS")
    public boolean isSyncPendingAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) {
        isSyncPendingAsUser_enforcePermission();
        enforceCrossUserPermission(i, "no permission to retrieve the sync settings for user " + i);
        int callingUid = android.os.Binder.getCallingUid();
        if (!hasAccountAccess(true, account, callingUid) || !hasAuthorityAccess(str, callingUid, i)) {
            return false;
        }
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            if (account != null && str != null) {
                return getSyncManager().getSyncStorageEngine().isSyncPending(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, i));
            }
            throw new java.lang.IllegalArgumentException("Invalid authority specified");
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addStatusChangeListener(int i, android.content.ISyncStatusObserver iSyncStatusObserver) {
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        if (iSyncStatusObserver != null) {
            try {
                getSyncManager().getSyncStorageEngine().addStatusChangeListener(i, callingUid, iSyncStatusObserver);
            } finally {
                android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeStatusChangeListener(android.content.ISyncStatusObserver iSyncStatusObserver) {
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        if (iSyncStatusObserver != null) {
            try {
                getSyncManager().getSyncStorageEngine().removeStatusChangeListener(iSyncStatusObserver);
            } finally {
                android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @android.annotation.Nullable
    private java.lang.String getProviderPackageName(android.net.Uri uri, int i) {
        android.content.pm.ProviderInfo resolveContentProviderAsUser = this.mContext.getPackageManager().resolveContentProviderAsUser(uri.getAuthority(), 0, i);
        if (resolveContentProviderAsUser != null) {
            return resolveContentProviderAsUser.packageName;
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mCache"})
    private android.util.ArrayMap<android.util.Pair<java.lang.String, android.net.Uri>, android.os.Bundle> findOrCreateCacheLocked(int i, java.lang.String str) {
        android.util.ArrayMap<java.lang.String, android.util.ArrayMap<android.util.Pair<java.lang.String, android.net.Uri>, android.os.Bundle>> arrayMap = this.mCache.get(i);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>();
            this.mCache.put(i, arrayMap);
        }
        android.util.ArrayMap<android.util.Pair<java.lang.String, android.net.Uri>, android.os.Bundle> arrayMap2 = arrayMap.get(str);
        if (arrayMap2 == null) {
            android.util.ArrayMap<android.util.Pair<java.lang.String, android.net.Uri>, android.os.Bundle> arrayMap3 = new android.util.ArrayMap<>();
            arrayMap.put(str, arrayMap3);
            return arrayMap3;
        }
        return arrayMap2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mCache"})
    public void invalidateCacheLocked(int i, java.lang.String str, android.net.Uri uri) {
        android.util.ArrayMap<android.util.Pair<java.lang.String, android.net.Uri>, android.os.Bundle> arrayMap;
        android.util.ArrayMap<java.lang.String, android.util.ArrayMap<android.util.Pair<java.lang.String, android.net.Uri>, android.os.Bundle>> arrayMap2 = this.mCache.get(i);
        if (arrayMap2 == null || (arrayMap = arrayMap2.get(str)) == null) {
            return;
        }
        if (uri != null) {
            int i2 = 0;
            while (i2 < arrayMap.size()) {
                android.util.Pair<java.lang.String, android.net.Uri> keyAt = arrayMap.keyAt(i2);
                if (keyAt.second != null && ((android.net.Uri) keyAt.second).toString().startsWith(uri.toString())) {
                    arrayMap.removeAt(i2);
                } else {
                    i2++;
                }
            }
            return;
        }
        arrayMap.clear();
    }

    @android.annotation.RequiresPermission("android.permission.CACHE_CONTENT")
    public void putCache(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, int i) {
        android.os.Bundle.setDefusable(bundle, true);
        enforceNonFullCrossUserPermission(i, TAG);
        this.mContext.enforceCallingOrSelfPermission("android.permission.CACHE_CONTENT", TAG);
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).checkPackage(android.os.Binder.getCallingUid(), str);
        java.lang.String providerPackageName = getProviderPackageName(uri, i);
        android.util.Pair<java.lang.String, android.net.Uri> create = android.util.Pair.create(str, uri);
        synchronized (this.mCache) {
            try {
                android.util.ArrayMap<android.util.Pair<java.lang.String, android.net.Uri>, android.os.Bundle> findOrCreateCacheLocked = findOrCreateCacheLocked(i, providerPackageName);
                if (bundle != null) {
                    findOrCreateCacheLocked.put(create, bundle);
                } else {
                    findOrCreateCacheLocked.remove(create);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.RequiresPermission("android.permission.CACHE_CONTENT")
    public android.os.Bundle getCache(java.lang.String str, android.net.Uri uri, int i) {
        android.os.Bundle bundle;
        enforceNonFullCrossUserPermission(i, TAG);
        this.mContext.enforceCallingOrSelfPermission("android.permission.CACHE_CONTENT", TAG);
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).checkPackage(android.os.Binder.getCallingUid(), str);
        java.lang.String providerPackageName = getProviderPackageName(uri, i);
        android.util.Pair create = android.util.Pair.create(str, uri);
        synchronized (this.mCache) {
            bundle = findOrCreateCacheLocked(i, providerPackageName).get(create);
        }
        return bundle;
    }

    private int handleIncomingUser(android.net.Uri uri, int i, int i2, int i3, boolean z, int i4) {
        if (i4 == -2) {
            i4 = android.app.ActivityManager.getCurrentUser();
        }
        if (i4 == -1) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "No access to " + uri);
        } else {
            if (i4 < 0) {
                throw new java.lang.IllegalArgumentException("Invalid user: " + i4);
            }
            if (i4 != android.os.UserHandle.getCallingUserId() && checkUriPermission(uri, i, i2, i3, i4) != 0) {
                boolean z2 = true;
                if (this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0 && (!z || this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") != 0)) {
                    z2 = false;
                }
                if (!z2) {
                    throw new java.lang.SecurityException("No access to " + uri + ": neither user " + i2 + " nor current process has " + (z ? "android.permission.INTERACT_ACROSS_USERS_FULL or android.permission.INTERACT_ACROSS_USERS" : "android.permission.INTERACT_ACROSS_USERS_FULL"));
                }
            }
        }
        return i4;
    }

    private void enforceCrossUserPermission(int i, java.lang.String str) {
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", str);
        }
    }

    private void enforceNonFullCrossUserPermission(int i, java.lang.String str) {
        if (android.os.UserHandle.getCallingUserId() == i || this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") == 0) {
            return;
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", str);
    }

    private boolean hasAccountAccess(boolean z, android.accounts.Account account, int i) {
        if (account == null) {
            return true;
        }
        if (z && !android.app.compat.CompatChanges.isChangeEnabled(ACCOUNT_ACCESS_CHECK_CHANGE_ID, i)) {
            return true;
        }
        long clearCallingIdentity = android.content.IContentService.Stub.clearCallingIdentity();
        try {
            return this.mAccountManagerInternal.hasAccountAccess(account, i);
        } finally {
            android.content.IContentService.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean hasAuthorityAccess(@android.annotation.Nullable java.lang.String str, int i, int i2) {
        if (!android.text.TextUtils.isEmpty(str) && android.app.compat.CompatChanges.isChangeEnabled(AUTHORITY_ACCESS_CHECK_CHANGE_ID, i)) {
            return !com.android.internal.util.ArrayUtils.isEmpty(lambda$new$0(str, i2));
        }
        return true;
    }

    private static int normalizeSyncable(int i) {
        if (i > 0) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        return -2;
    }

    private void validateExtras(int i, android.os.Bundle bundle) {
        if (bundle.containsKey("v_exemption")) {
            switch (i) {
                case 0:
                case 1000:
                case 2000:
                    return;
                default:
                    android.util.Log.w(TAG, "Invalid extras specified. requestsync -f/-F needs to run on 'adb shell'");
                    throw new java.lang.SecurityException("Invalid extras specified.");
            }
        }
    }

    private int getSyncExemptionForCaller(int i) {
        return getSyncExemptionAndCleanUpExtrasForCaller(i, null);
    }

    private int getSyncExemptionAndCleanUpExtrasForCaller(int i, android.os.Bundle bundle) {
        if (bundle != null) {
            int i2 = bundle.getInt("v_exemption", -1);
            bundle.remove("v_exemption");
            if (i2 != -1) {
                return i2;
            }
        }
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        if (activityManagerInternal == null) {
            return 0;
        }
        int uidProcessState = activityManagerInternal.getUidProcessState(i);
        boolean isUidActive = activityManagerInternal.isUidActive(i);
        if (uidProcessState <= 2 || uidProcessState == 3) {
            return 2;
        }
        if (uidProcessState > 6 && !isUidActive) {
            return 0;
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.SYNC_EXEMPTION_OCCURRED, i, getProcStateForStatsd(uidProcessState), isUidActive, getRestrictionLevelForStatsd(activityManagerInternal.getRestrictionLevel(i)));
        return 1;
    }

    private int getProcStateForStatsd(int i) {
        switch (i) {
        }
        return 0;
    }

    private int getRestrictionLevelForStatsd(int i) {
        switch (i) {
            case 0:
                break;
            case 10:
                break;
            case 20:
                break;
            case 30:
                break;
            case 40:
                break;
            case 50:
                break;
            case 60:
                break;
        }
        return 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static final class ObserverNode {
        private java.lang.String mName;
        private java.util.ArrayList<com.android.server.content.ContentService.ObserverNode> mChildren = new java.util.ArrayList<>();
        private java.util.ArrayList<com.android.server.content.ContentService.ObserverNode.ObserverEntry> mObservers = new java.util.ArrayList<>();

        private class ObserverEntry implements android.os.IBinder.DeathRecipient {
            public final boolean notifyForDescendants;
            public final android.database.IContentObserver observer;
            private final java.lang.Object observersLock;
            public final int pid;
            public final int uid;
            private final int userHandle;

            public ObserverEntry(android.database.IContentObserver iContentObserver, boolean z, java.lang.Object obj, int i, int i2, int i3, android.net.Uri uri) {
                boolean contains;
                java.lang.String str;
                this.observersLock = obj;
                this.observer = iContentObserver;
                this.uid = i;
                this.pid = i2;
                this.userHandle = i3;
                this.notifyForDescendants = z;
                int linkToDeath = com.android.server.content.ContentService.sObserverDeathDispatcher.linkToDeath(this.observer, this);
                if (linkToDeath == -1) {
                    binderDied();
                    return;
                }
                if (linkToDeath == 1000) {
                    synchronized (com.android.server.content.ContentService.sObserverLeakDetectedUid) {
                        try {
                            contains = com.android.server.content.ContentService.sObserverLeakDetectedUid.contains(java.lang.Integer.valueOf(this.uid));
                            if (!contains) {
                                com.android.server.content.ContentService.sObserverLeakDetectedUid.add(java.lang.Integer.valueOf(this.uid));
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    if (!contains) {
                        try {
                            str = (java.lang.String) com.android.internal.util.ArrayUtils.firstOrNull(android.app.AppGlobals.getPackageManager().getPackagesForUid(this.uid));
                        } catch (android.os.RemoteException e) {
                            str = null;
                        }
                        android.util.Slog.wtf(com.android.server.content.ContentService.TAG, "Observer registered too many times. Leak? cpid=" + this.pid + " cuid=" + this.uid + " cpkg=" + str + " url=" + uri);
                    }
                }
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                synchronized (this.observersLock) {
                    com.android.server.content.ContentService.ObserverNode.this.removeObserverLocked(this.observer);
                }
            }

            public void dumpLocked(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, java.lang.String str, java.lang.String str2, android.util.SparseIntArray sparseIntArray) {
                sparseIntArray.put(this.pid, sparseIntArray.get(this.pid) + 1);
                printWriter.print(str2);
                printWriter.print(str);
                printWriter.print(": pid=");
                printWriter.print(this.pid);
                printWriter.print(" uid=");
                printWriter.print(this.uid);
                printWriter.print(" user=");
                printWriter.print(this.userHandle);
                printWriter.print(" target=");
                printWriter.println(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.observer != null ? this.observer.asBinder() : null)));
            }
        }

        public ObserverNode(java.lang.String str) {
            this.mName = str;
        }

        public void dumpLocked(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, java.lang.String str, java.lang.String str2, int[] iArr, android.util.SparseIntArray sparseIntArray) {
            java.lang.String str3;
            java.lang.String str4;
            if (this.mObservers.size() <= 0) {
                str3 = null;
            } else {
                str3 = "".equals(str) ? this.mName : str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mName;
                for (int i = 0; i < this.mObservers.size(); i++) {
                    iArr[1] = iArr[1] + 1;
                    this.mObservers.get(i).dumpLocked(fileDescriptor, printWriter, strArr, str3, str2, sparseIntArray);
                }
            }
            if (this.mChildren.size() > 0) {
                if (str3 != null) {
                    str4 = str3;
                } else if ("".equals(str)) {
                    str4 = this.mName;
                } else {
                    str4 = str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mName;
                }
                for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
                    iArr[0] = iArr[0] + 1;
                    this.mChildren.get(i2).dumpLocked(fileDescriptor, printWriter, strArr, str4, str2, iArr, sparseIntArray);
                }
            }
        }

        public static java.lang.String getUriSegment(android.net.Uri uri, int i) {
            if (uri != null) {
                if (i == 0) {
                    return uri.getAuthority();
                }
                return uri.getPathSegments().get(i - 1);
            }
            return null;
        }

        public static int countUriSegments(android.net.Uri uri) {
            if (uri == null) {
                return 0;
            }
            return uri.getPathSegments().size() + 1;
        }

        public void addObserverLocked(android.net.Uri uri, android.database.IContentObserver iContentObserver, boolean z, java.lang.Object obj, int i, int i2, int i3) {
            addObserverLocked(uri, 0, iContentObserver, z, obj, i, i2, i3);
        }

        private void addObserverLocked(android.net.Uri uri, int i, android.database.IContentObserver iContentObserver, boolean z, java.lang.Object obj, int i2, int i3, int i4) {
            if (i == countUriSegments(uri)) {
                this.mObservers.add(new com.android.server.content.ContentService.ObserverNode.ObserverEntry(iContentObserver, z, obj, i2, i3, i4, uri));
                return;
            }
            java.lang.String uriSegment = getUriSegment(uri, i);
            if (uriSegment == null) {
                throw new java.lang.IllegalArgumentException("Invalid Uri (" + uri + ") used for observer");
            }
            int size = this.mChildren.size();
            for (int i5 = 0; i5 < size; i5++) {
                com.android.server.content.ContentService.ObserverNode observerNode = this.mChildren.get(i5);
                if (observerNode.mName.equals(uriSegment)) {
                    observerNode.addObserverLocked(uri, i + 1, iContentObserver, z, obj, i2, i3, i4);
                    return;
                }
            }
            com.android.server.content.ContentService.ObserverNode observerNode2 = new com.android.server.content.ContentService.ObserverNode(uriSegment);
            this.mChildren.add(observerNode2);
            observerNode2.addObserverLocked(uri, i + 1, iContentObserver, z, obj, i2, i3, i4);
        }

        public boolean removeObserverLocked(android.database.IContentObserver iContentObserver) {
            int size = this.mChildren.size();
            int i = 0;
            while (i < size) {
                if (this.mChildren.get(i).removeObserverLocked(iContentObserver)) {
                    this.mChildren.remove(i);
                    i--;
                    size--;
                }
                i++;
            }
            android.os.IBinder asBinder = iContentObserver.asBinder();
            int size2 = this.mObservers.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size2) {
                    break;
                }
                com.android.server.content.ContentService.ObserverNode.ObserverEntry observerEntry = this.mObservers.get(i2);
                if (observerEntry.observer.asBinder() != asBinder) {
                    i2++;
                } else {
                    this.mObservers.remove(i2);
                    com.android.server.content.ContentService.sObserverDeathDispatcher.unlinkToDeath(iContentObserver, observerEntry);
                    break;
                }
            }
            return this.mChildren.size() == 0 && this.mObservers.size() == 0;
        }

        private void collectMyObserversLocked(android.net.Uri uri, boolean z, android.database.IContentObserver iContentObserver, boolean z2, int i, int i2, com.android.server.content.ContentService.ObserverCollector observerCollector) {
            int size = this.mObservers.size();
            android.os.IBinder asBinder = iContentObserver == null ? null : iContentObserver.asBinder();
            for (int i3 = 0; i3 < size; i3++) {
                com.android.server.content.ContentService.ObserverNode.ObserverEntry observerEntry = this.mObservers.get(i3);
                boolean z3 = observerEntry.observer.asBinder() == asBinder;
                if ((!z3 || z2) && (i2 == -1 || observerEntry.userHandle == -1 || i2 == observerEntry.userHandle)) {
                    if (z) {
                        if ((i & 2) != 0 && observerEntry.notifyForDescendants) {
                        }
                        observerCollector.collect(observerEntry.observer, observerEntry.uid, z3, uri, i, i2);
                    } else {
                        if (!observerEntry.notifyForDescendants) {
                        }
                        observerCollector.collect(observerEntry.observer, observerEntry.uid, z3, uri, i, i2);
                    }
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        public void collectObserversLocked(android.net.Uri uri, int i, android.database.IContentObserver iContentObserver, boolean z, int i2, int i3, com.android.server.content.ContentService.ObserverCollector observerCollector) {
            collectObserversLocked(uri, countUriSegments(uri), i, iContentObserver, z, i2, i3, observerCollector);
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0046  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void collectObserversLocked(android.net.Uri uri, int i, int i2, android.database.IContentObserver iContentObserver, boolean z, int i3, int i4, com.android.server.content.ContentService.ObserverCollector observerCollector) {
            java.lang.String str;
            int size;
            int i5;
            int i6 = i2;
            if (i6 >= i) {
                collectMyObserversLocked(uri, true, iContentObserver, z, i3, i4, observerCollector);
            } else if (i6 < i) {
                java.lang.String uriSegment = getUriSegment(uri, i6);
                collectMyObserversLocked(uri, false, iContentObserver, z, i3, i4, observerCollector);
                str = uriSegment;
                size = this.mChildren.size();
                i5 = 0;
                while (i5 < size) {
                    com.android.server.content.ContentService.ObserverNode observerNode = this.mChildren.get(i5);
                    if (str == null || observerNode.mName.equals(str)) {
                        observerNode.collectObserversLocked(uri, i, i6 + 1, iContentObserver, z, i3, i4, observerCollector);
                        if (str != null) {
                            return;
                        }
                    }
                    i5++;
                    i6 = i2;
                }
            }
            str = null;
            size = this.mChildren.size();
            i5 = 0;
            while (i5 < size) {
            }
        }
    }

    private void enforceShell(java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 2000 && callingUid != 0) {
            throw new java.lang.SecurityException("Non-shell user attempted to call " + str);
        }
    }

    public void resetTodayStats() {
        enforceShell("resetTodayStats");
        if (this.mSyncManager != null) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mSyncManager.resetTodayStats();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void onDbCorruption(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.util.Slog.e(str, str2);
        android.util.Slog.e(str, "at " + str3);
        android.util.Slog.wtf(str, str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.content.ContentShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }
}
