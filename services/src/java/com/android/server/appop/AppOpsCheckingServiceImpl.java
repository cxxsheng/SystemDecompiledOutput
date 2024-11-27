package com.android.server.appop;

/* loaded from: classes.dex */
public class AppOpsCheckingServiceImpl implements com.android.server.appop.AppOpsCheckingServiceInterface {

    @com.android.internal.annotations.VisibleForTesting
    static final int CURRENT_VERSION = 4;
    private static final boolean DEBUG = false;
    private static final int NO_FILE_VERSION = -2;
    private static final int NO_VERSION = -1;
    static final java.lang.String TAG = "LegacyAppOpsServiceInterfaceImpl";
    private static final long WRITE_DELAY = 1800000;
    final android.content.Context mContext;
    boolean mFastWriteScheduled;
    final android.util.AtomicFile mFile;
    final android.os.Handler mHandler;
    final java.lang.Object mLock;
    final android.util.SparseArray<int[]> mSwitchedOps;
    boolean mWriteScheduled;
    private int mVersionAtBoot = -2;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseArray<android.util.SparseIntArray> mUidModes = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.SparseIntArray>> mUserPackageModes = new android.util.SparseArray<>();
    private final com.android.server.appop.LegacyAppOpStateParser mAppOpsStateParser = new com.android.server.appop.LegacyAppOpStateParser();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener> mModeChangedListeners = new java.util.ArrayList();
    final java.lang.Runnable mWriteRunner = new java.lang.Runnable() { // from class: com.android.server.appop.AppOpsCheckingServiceImpl.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.appop.AppOpsCheckingServiceImpl.this.mLock) {
                com.android.server.appop.AppOpsCheckingServiceImpl.this.mWriteScheduled = false;
                com.android.server.appop.AppOpsCheckingServiceImpl.this.mFastWriteScheduled = false;
                new android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void>() { // from class: com.android.server.appop.AppOpsCheckingServiceImpl.1.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // android.os.AsyncTask
                    public java.lang.Void doInBackground(java.lang.Void... voidArr) {
                        com.android.server.appop.AppOpsCheckingServiceImpl.this.writeState();
                        return null;
                    }
                }.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, null);
            }
        }
    };

    AppOpsCheckingServiceImpl(java.io.File file, @android.annotation.NonNull java.lang.Object obj, android.os.Handler handler, android.content.Context context, android.util.SparseArray<int[]> sparseArray) {
        this.mFile = new android.util.AtomicFile(file);
        this.mLock = obj;
        this.mHandler = handler;
        this.mContext = context;
        this.mSwitchedOps = sparseArray;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void systemReady() {
        synchronized (this.mLock) {
            upgradeLocked(this.mVersionAtBoot);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseIntArray getNonDefaultUidModes(int i, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                android.util.SparseIntArray sparseIntArray = this.mUidModes.get(i, null);
                if (sparseIntArray == null) {
                    return new android.util.SparseIntArray();
                }
                return sparseIntArray.clone();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseIntArray getNonDefaultPackageModes(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> arrayMap = this.mUserPackageModes.get(i);
                if (arrayMap == null) {
                    return new android.util.SparseIntArray();
                }
                android.util.SparseIntArray sparseIntArray = arrayMap.get(str);
                if (sparseIntArray == null) {
                    return new android.util.SparseIntArray();
                }
                return sparseIntArray.clone();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getUidMode(int i, java.lang.String str, int i2) {
        synchronized (this.mLock) {
            try {
                android.util.SparseIntArray sparseIntArray = this.mUidModes.get(i, null);
                if (sparseIntArray == null) {
                    return android.app.AppOpsManager.opToDefaultMode(i2);
                }
                return sparseIntArray.get(i2, android.app.AppOpsManager.opToDefaultMode(i2));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean setUidMode(int i, java.lang.String str, int i2, int i3) {
        int i4;
        int opToDefaultMode = android.app.AppOpsManager.opToDefaultMode(i2);
        synchronized (this.mLock) {
            try {
                android.util.SparseIntArray sparseIntArray = this.mUidModes.get(i, null);
                if (sparseIntArray == null) {
                    i4 = opToDefaultMode;
                } else {
                    i4 = sparseIntArray.get(i2, opToDefaultMode);
                }
                if (i3 == i4) {
                    return false;
                }
                if (i3 == opToDefaultMode) {
                    sparseIntArray.delete(i2);
                    if (sparseIntArray.size() == 0) {
                        this.mUidModes.remove(i);
                    }
                } else {
                    if (sparseIntArray == null) {
                        sparseIntArray = new android.util.SparseIntArray();
                        this.mUidModes.put(i, sparseIntArray);
                    }
                    sparseIntArray.put(i2, i3);
                }
                scheduleWriteLocked();
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mModeChangedListeners);
                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                    ((com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener) arrayList.get(i5)).onUidModeChanged(i, i2, i3, str);
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getPackageMode(java.lang.String str, int i, int i2) {
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> arrayMap = this.mUserPackageModes.get(i2, null);
                if (arrayMap == null) {
                    return android.app.AppOpsManager.opToDefaultMode(i);
                }
                android.util.SparseIntArray orDefault = arrayMap.getOrDefault(str, null);
                if (orDefault == null) {
                    return android.app.AppOpsManager.opToDefaultMode(i);
                }
                return orDefault.get(i, android.app.AppOpsManager.opToDefaultMode(i));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void setPackageMode(java.lang.String str, int i, int i2, int i3) {
        int i4;
        int opToDefaultMode = android.app.AppOpsManager.opToDefaultMode(i);
        synchronized (this.mLock) {
            try {
                android.util.SparseIntArray sparseIntArray = null;
                android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> arrayMap = this.mUserPackageModes.get(i3, null);
                if (arrayMap == null && i2 != opToDefaultMode) {
                    arrayMap = new android.util.ArrayMap<>();
                    this.mUserPackageModes.put(i3, arrayMap);
                }
                if (arrayMap == null) {
                    i4 = opToDefaultMode;
                } else {
                    sparseIntArray = arrayMap.get(str);
                    if (sparseIntArray == null) {
                        i4 = opToDefaultMode;
                    } else {
                        i4 = sparseIntArray.get(i, opToDefaultMode);
                    }
                }
                if (i2 == i4) {
                    return;
                }
                if (i2 == opToDefaultMode) {
                    sparseIntArray.delete(i);
                    if (sparseIntArray.size() == 0) {
                        arrayMap.remove(str);
                        if (arrayMap.size() == 0) {
                            this.mUserPackageModes.remove(i3);
                        }
                    }
                } else {
                    if (arrayMap == null) {
                        arrayMap = new android.util.ArrayMap<>();
                        this.mUserPackageModes.put(i3, arrayMap);
                    }
                    if (sparseIntArray == null) {
                        sparseIntArray = new android.util.SparseIntArray();
                        arrayMap.put(str, sparseIntArray);
                    }
                    sparseIntArray.put(i, i2);
                }
                scheduleFastWriteLocked();
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mModeChangedListeners);
                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                    ((com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener) arrayList.get(i5)).onPackageModeChanged(str, i3, i, i2);
                }
            } finally {
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void removeUid(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mUidModes.get(i) == null) {
                    return;
                }
                this.mUidModes.remove(i);
                scheduleFastWriteLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removePackage(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> arrayMap = this.mUserPackageModes.get(i, null);
                if (arrayMap == null) {
                    return false;
                }
                if (arrayMap.remove(str) == null) {
                    return false;
                }
                scheduleFastWriteLocked();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void clearAllModes() {
        synchronized (this.mLock) {
            this.mUidModes.clear();
            this.mUserPackageModes.clear();
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseBooleanArray getForegroundOps(int i, java.lang.String str) {
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        synchronized (this.mLock) {
            try {
                android.util.SparseIntArray sparseIntArray = this.mUidModes.get(i);
                if (sparseIntArray == null) {
                    return sparseBooleanArray;
                }
                for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                    if (sparseIntArray.valueAt(i2) == 4) {
                        sparseBooleanArray.put(sparseIntArray.keyAt(i2), true);
                    }
                }
                return sparseBooleanArray;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseBooleanArray getForegroundOps(java.lang.String str, int i) {
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> arrayMap = this.mUserPackageModes.get(i);
                if (arrayMap == null) {
                    return sparseBooleanArray;
                }
                android.util.SparseIntArray sparseIntArray = arrayMap.get(str);
                if (sparseIntArray == null) {
                    return sparseBooleanArray;
                }
                for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                    if (sparseIntArray.valueAt(i2) == 4) {
                        sparseBooleanArray.put(sparseIntArray.keyAt(i2), true);
                    }
                }
                return sparseBooleanArray;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void scheduleWriteLocked() {
        if (!this.mWriteScheduled) {
            this.mWriteScheduled = true;
            this.mHandler.postDelayed(this.mWriteRunner, 1800000L);
        }
    }

    private void scheduleFastWriteLocked() {
        if (!this.mFastWriteScheduled) {
            this.mWriteScheduled = true;
            this.mFastWriteScheduled = true;
            this.mHandler.removeCallbacks(this.mWriteRunner);
            this.mHandler.postDelayed(this.mWriteRunner, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void writeState() {
        int size;
        int size2;
        synchronized (this.mFile) {
            try {
                try {
                    java.io.FileOutputStream startWrite = this.mFile.startWrite();
                    try {
                        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((java.lang.String) null, true);
                        resolveSerializer.startTag((java.lang.String) null, "app-ops");
                        resolveSerializer.attributeInt((java.lang.String) null, "v", 4);
                        android.util.SparseArray sparseArray = new android.util.SparseArray();
                        android.util.SparseArray sparseArray2 = new android.util.SparseArray();
                        synchronized (this.mLock) {
                            try {
                                size = this.mUidModes.size();
                                for (int i = 0; i < size; i++) {
                                    sparseArray.put(this.mUidModes.keyAt(i), this.mUidModes.valueAt(i).clone());
                                }
                                size2 = this.mUserPackageModes.size();
                                for (int i2 = 0; i2 < size2; i2++) {
                                    int keyAt = this.mUserPackageModes.keyAt(i2);
                                    android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> valueAt = this.mUserPackageModes.valueAt(i2);
                                    android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                                    sparseArray2.put(keyAt, arrayMap);
                                    int size3 = valueAt.size();
                                    for (int i3 = 0; i3 < size3; i3++) {
                                        arrayMap.put(valueAt.keyAt(i3), valueAt.valueAt(i3).clone());
                                    }
                                }
                            } finally {
                            }
                        }
                        for (int i4 = 0; i4 < size; i4++) {
                            int keyAt2 = sparseArray.keyAt(i4);
                            android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) sparseArray.valueAt(i4);
                            resolveSerializer.startTag((java.lang.String) null, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID);
                            resolveSerializer.attributeInt((java.lang.String) null, "n", keyAt2);
                            int size4 = sparseIntArray.size();
                            for (int i5 = 0; i5 < size4; i5++) {
                                int keyAt3 = sparseIntArray.keyAt(i5);
                                int valueAt2 = sparseIntArray.valueAt(i5);
                                resolveSerializer.startTag((java.lang.String) null, "op");
                                resolveSerializer.attributeInt((java.lang.String) null, "n", keyAt3);
                                resolveSerializer.attributeInt((java.lang.String) null, "m", valueAt2);
                                resolveSerializer.endTag((java.lang.String) null, "op");
                            }
                            resolveSerializer.endTag((java.lang.String) null, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID);
                        }
                        for (int i6 = 0; i6 < size2; i6++) {
                            int keyAt4 = sparseArray2.keyAt(i6);
                            android.util.ArrayMap arrayMap2 = (android.util.ArrayMap) sparseArray2.valueAt(i6);
                            resolveSerializer.startTag((java.lang.String) null, "user");
                            resolveSerializer.attributeInt((java.lang.String) null, "n", keyAt4);
                            int size5 = arrayMap2.size();
                            int i7 = 0;
                            while (i7 < size5) {
                                java.lang.String str = (java.lang.String) arrayMap2.keyAt(i7);
                                android.util.SparseIntArray sparseIntArray2 = (android.util.SparseIntArray) arrayMap2.valueAt(i7);
                                resolveSerializer.startTag((java.lang.String) null, "pkg");
                                resolveSerializer.attribute((java.lang.String) null, "n", str);
                                int size6 = sparseIntArray2.size();
                                int i8 = 0;
                                while (i8 < size6) {
                                    int keyAt5 = sparseIntArray2.keyAt(i8);
                                    int valueAt3 = sparseIntArray2.valueAt(i8);
                                    resolveSerializer.startTag((java.lang.String) null, "op");
                                    resolveSerializer.attributeInt((java.lang.String) null, "n", keyAt5);
                                    resolveSerializer.attributeInt((java.lang.String) null, "m", valueAt3);
                                    resolveSerializer.endTag((java.lang.String) null, "op");
                                    i8++;
                                    size5 = size5;
                                }
                                resolveSerializer.endTag((java.lang.String) null, "pkg");
                                i7++;
                                size5 = size5;
                            }
                            resolveSerializer.endTag((java.lang.String) null, "user");
                        }
                        resolveSerializer.endTag((java.lang.String) null, "app-ops");
                        resolveSerializer.endDocument();
                        this.mFile.finishWrite(startWrite);
                    } catch (java.io.IOException e) {
                        android.util.Slog.w(TAG, "Failed to write state, restoring backup.", e);
                        this.mFile.failWrite(startWrite);
                    }
                } catch (java.io.IOException e2) {
                    android.util.Slog.w(TAG, "Failed to write state: " + e2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void readState() {
        synchronized (this.mFile) {
            synchronized (this.mLock) {
                this.mVersionAtBoot = this.mAppOpsStateParser.readState(this.mFile, this.mUidModes, this.mUserPackageModes);
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void shutdown() {
        boolean z;
        synchronized (this) {
            try {
                z = false;
                if (this.mWriteScheduled) {
                    this.mWriteScheduled = false;
                    this.mFastWriteScheduled = false;
                    this.mHandler.removeCallbacks(this.mWriteRunner);
                    z = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            writeState();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void upgradeLocked(int i) {
        if (i == -2 || i >= 4) {
            return;
        }
        android.util.Slog.d(TAG, "Upgrading app-ops xml from version " + i + " to 4");
        switch (i) {
            case -1:
                upgradeRunAnyInBackgroundLocked();
                upgradeScheduleExactAlarmLocked();
                resetUseFullScreenIntentLocked();
                break;
            case 1:
                upgradeScheduleExactAlarmLocked();
                resetUseFullScreenIntentLocked();
                break;
            case 2:
            case 3:
                resetUseFullScreenIntentLocked();
                break;
        }
        scheduleFastWriteLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void upgradeRunAnyInBackgroundLocked() {
        int size = this.mUidModes.size();
        for (int i = 0; i < size; i++) {
            android.util.SparseIntArray valueAt = this.mUidModes.valueAt(i);
            int indexOfKey = valueAt.indexOfKey(63);
            if (indexOfKey >= 0) {
                valueAt.put(70, valueAt.valueAt(indexOfKey));
            }
        }
        int size2 = this.mUserPackageModes.size();
        for (int i2 = 0; i2 < size2; i2++) {
            android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> valueAt2 = this.mUserPackageModes.valueAt(i2);
            int size3 = valueAt2.size();
            for (int i3 = 0; i3 < size3; i3++) {
                android.util.SparseIntArray valueAt3 = valueAt2.valueAt(i3);
                int indexOfKey2 = valueAt3.indexOfKey(63);
                if (indexOfKey2 >= 0) {
                    valueAt3.put(70, valueAt3.valueAt(indexOfKey2));
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void upgradeScheduleExactAlarmLocked() {
        com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal = (com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class);
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        java.lang.String[] appOpPermissionPackages = permissionManagerServiceInternal.getAppOpPermissionPackages(android.app.AppOpsManager.opToPermission(107));
        int[] userIds = userManagerInternal.getUserIds();
        for (java.lang.String str : appOpPermissionPackages) {
            for (int i : userIds) {
                int packageUid = packageManagerInternal.getPackageUid(str, 0L, i);
                if (getUidMode(packageUid, "default:0", 107) == android.app.AppOpsManager.opToDefaultMode(107)) {
                    setUidMode(packageUid, "default:0", 107, 0);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void resetUseFullScreenIntentLocked() {
        com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal = (com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class);
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        android.permission.PermissionManager permissionManager = (android.permission.PermissionManager) this.mContext.getSystemService(android.permission.PermissionManager.class);
        java.lang.String opToPermission = android.app.AppOpsManager.opToPermission(133);
        java.lang.String[] appOpPermissionPackages = permissionManagerServiceInternal.getAppOpPermissionPackages(opToPermission);
        int[] userIds = userManagerInternal.getUserIds();
        for (java.lang.String str : appOpPermissionPackages) {
            for (int i : userIds) {
                int packageUid = packageManagerInternal.getPackageUid(str, 0L, i);
                if ((permissionManager.getPermissionFlags(str, opToPermission, android.os.UserHandle.of(i)) & 1) == 0) {
                    setUidMode(packageUid, "default:0", 133, android.app.AppOpsManager.opToDefaultMode(133));
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<java.lang.Integer> getUidsWithNonDefaultModes() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mUidModes.size(); i++) {
                try {
                    if (this.mUidModes.valueAt(i).size() > 0) {
                        arrayList.add(java.lang.Integer.valueOf(this.mUidModes.keyAt(i)));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<android.content.pm.UserPackage> getPackagesWithNonDefaultModes() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mUserPackageModes.size(); i++) {
                try {
                    android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> valueAt = this.mUserPackageModes.valueAt(i);
                    for (int i2 = 0; i2 < valueAt.size(); i2++) {
                        if (valueAt.valueAt(i2).size() > 0) {
                            arrayList.add(android.content.pm.UserPackage.of(this.mUserPackageModes.keyAt(i), valueAt.keyAt(i2)));
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean addAppOpsModeChangedListener(com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener appOpsModeChangedListener) {
        boolean add;
        synchronized (this.mLock) {
            add = this.mModeChangedListeners.add(appOpsModeChangedListener);
        }
        return add;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removeAppOpsModeChangedListener(com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener appOpsModeChangedListener) {
        boolean remove;
        synchronized (this.mLock) {
            remove = this.mModeChangedListeners.remove(appOpsModeChangedListener);
        }
        return remove;
    }
}
