package android.os.incremental;

/* loaded from: classes3.dex */
public final class IncrementalFileStorages {
    private static final java.lang.String SYSTEM_DATA_LOADER_PACKAGE = "android";
    private static final java.lang.String TAG = "IncrementalFileStorages";
    private android.os.incremental.IncrementalStorage mDefaultStorage;
    private final android.os.incremental.IncrementalManager mIncrementalManager;
    private android.os.incremental.IncrementalStorage mInheritedStorage;
    private final java.io.File mStageDir;

    public static android.os.incremental.IncrementalFileStorages initialize(android.content.Context context, java.io.File file, java.io.File file2, android.content.pm.DataLoaderParams dataLoaderParams, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener, android.os.incremental.StorageHealthCheckParams storageHealthCheckParams, android.os.incremental.IStorageHealthListener iStorageHealthListener, java.util.List<android.content.pm.InstallationFileParcel> list, android.os.incremental.PerUidReadTimeouts[] perUidReadTimeoutsArr, android.content.pm.IPackageLoadingProgressCallback iPackageLoadingProgressCallback) throws java.io.IOException {
        android.os.incremental.IncrementalManager incrementalManager = (android.os.incremental.IncrementalManager) context.getSystemService(android.content.Context.INCREMENTAL_SERVICE);
        if (incrementalManager == null) {
            throw new java.io.IOException("Failed to obtain incrementalManager.");
        }
        android.os.incremental.IncrementalFileStorages incrementalFileStorages = new android.os.incremental.IncrementalFileStorages(file, file2, incrementalManager, dataLoaderParams);
        for (android.content.pm.InstallationFileParcel installationFileParcel : list) {
            if (installationFileParcel.location == 0) {
                try {
                    incrementalFileStorages.addApkFile(installationFileParcel);
                } catch (java.io.IOException e) {
                    throw new java.io.IOException("Failed to add file to IncFS: " + installationFileParcel.name + ", reason: ", e);
                }
            } else {
                throw new java.io.IOException("Unknown file location: " + installationFileParcel.location);
            }
        }
        if (iPackageLoadingProgressCallback != null) {
            incrementalManager.registerLoadingProgressCallback(file.getAbsolutePath(), iPackageLoadingProgressCallback);
        }
        incrementalFileStorages.startLoading(dataLoaderParams, iDataLoaderStatusListener, storageHealthCheckParams, iStorageHealthListener, perUidReadTimeoutsArr);
        return incrementalFileStorages;
    }

    private IncrementalFileStorages(java.io.File file, java.io.File file2, android.os.incremental.IncrementalManager incrementalManager, android.content.pm.DataLoaderParams dataLoaderParams) throws java.io.IOException {
        try {
            this.mStageDir = file;
            this.mIncrementalManager = incrementalManager;
            if (file2 != null && android.os.incremental.IncrementalManager.isIncrementalPath(file2.getAbsolutePath())) {
                this.mInheritedStorage = this.mIncrementalManager.openStorage(file2.getAbsolutePath());
                if (this.mInheritedStorage != null) {
                    if ("android".equals(dataLoaderParams.getComponentName().getPackageName()) && !this.mInheritedStorage.isFullyLoaded()) {
                        throw new java.io.IOException("Inherited storage has missing pages.");
                    }
                    this.mDefaultStorage = this.mIncrementalManager.createStorage(file.getAbsolutePath(), this.mInheritedStorage, 5);
                    if (this.mDefaultStorage == null) {
                        throw new java.io.IOException("Couldn't create linked incremental storage at " + file);
                    }
                    return;
                }
            }
            this.mDefaultStorage = this.mIncrementalManager.createStorage(file.getAbsolutePath(), dataLoaderParams, 5);
            if (this.mDefaultStorage == null) {
                throw new java.io.IOException("Couldn't create incremental storage at " + file);
            }
        } catch (java.io.IOException e) {
            cleanUp();
            throw e;
        }
    }

    private void addApkFile(android.content.pm.InstallationFileParcel installationFileParcel) throws java.io.IOException {
        java.lang.String str = installationFileParcel.name;
        if (!new java.io.File(this.mStageDir, str).exists()) {
            this.mDefaultStorage.makeFile(str, installationFileParcel.size, 511, null, installationFileParcel.metadata, installationFileParcel.signature, null);
        }
    }

    public void startLoading(android.content.pm.DataLoaderParams dataLoaderParams, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener, android.os.incremental.StorageHealthCheckParams storageHealthCheckParams, android.os.incremental.IStorageHealthListener iStorageHealthListener, android.os.incremental.PerUidReadTimeouts[] perUidReadTimeoutsArr) throws java.io.IOException {
        if (!this.mDefaultStorage.startLoading(dataLoaderParams, iDataLoaderStatusListener, storageHealthCheckParams, iStorageHealthListener, perUidReadTimeoutsArr)) {
            throw new java.io.IOException("Failed to start or restart loading data for Incremental installation.");
        }
    }

    public void makeFile(java.lang.String str, byte[] bArr, int i) throws java.io.IOException {
        this.mDefaultStorage.makeFile(str, bArr.length, i, java.util.UUID.randomUUID(), null, null, bArr);
    }

    public boolean makeLink(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.IOException {
        if (this.mInheritedStorage == null) {
            return false;
        }
        this.mInheritedStorage.makeLink(new java.io.File(str2, str).getAbsolutePath(), this.mDefaultStorage, new java.io.File(str3, str).getAbsolutePath());
        return true;
    }

    public void disallowReadLogs() {
        this.mDefaultStorage.disallowReadLogs();
    }

    public void cleanUpAndMarkComplete() {
        android.os.incremental.IncrementalStorage cleanUp = cleanUp();
        if (cleanUp != null) {
            cleanUp.onInstallationComplete();
        }
    }

    private android.os.incremental.IncrementalStorage cleanUp() {
        android.os.incremental.IncrementalStorage incrementalStorage = this.mDefaultStorage;
        this.mInheritedStorage = null;
        this.mDefaultStorage = null;
        if (incrementalStorage == null) {
            return null;
        }
        try {
            this.mIncrementalManager.unregisterLoadingProgressCallbacks(this.mStageDir.getAbsolutePath());
            incrementalStorage.unBind(this.mStageDir.getAbsolutePath());
        } catch (java.io.IOException e) {
        }
        return incrementalStorage;
    }
}
