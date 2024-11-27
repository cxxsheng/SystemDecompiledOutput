package android.os.incremental;

/* loaded from: classes3.dex */
public final class IncrementalStorage {
    private static final int INCFS_MAX_ADD_DATA_SIZE = 128;
    private static final int INCFS_MAX_HASH_SIZE = 32;
    private static final java.lang.String TAG = "IncrementalStorage";
    private static final int UUID_BYTE_SIZE = 16;
    private final int mId;
    private final android.os.incremental.IIncrementalService mService;

    public IncrementalStorage(android.os.incremental.IIncrementalService iIncrementalService, int i) {
        this.mService = iIncrementalService;
        this.mId = i;
    }

    public int getId() {
        return this.mId;
    }

    public void bind(java.lang.String str) throws java.io.IOException {
        bind("", str);
    }

    public void bind(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        try {
            int makeBindMount = this.mService.makeBindMount(this.mId, str, str2, 0);
            if (makeBindMount < 0) {
                throw new java.io.IOException("bind() failed with errno " + (-makeBindMount));
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void bindPermanent(java.lang.String str) throws java.io.IOException {
        bindPermanent("", str);
    }

    public void bindPermanent(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        try {
            int makeBindMount = this.mService.makeBindMount(this.mId, str, str2, 1);
            if (makeBindMount < 0) {
                throw new java.io.IOException("bind() permanent failed with errno " + (-makeBindMount));
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unBind(java.lang.String str) throws java.io.IOException {
        try {
            int deleteBindMount = this.mService.deleteBindMount(this.mId, str);
            if (deleteBindMount < 0) {
                throw new java.io.IOException("unbind() failed with errno " + (-deleteBindMount));
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeDirectory(java.lang.String str) throws java.io.IOException {
        try {
            int makeDirectory = this.mService.makeDirectory(this.mId, str);
            if (makeDirectory < 0) {
                throw new java.io.IOException("makeDirectory() failed with errno " + (-makeDirectory));
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeDirectories(java.lang.String str) throws java.io.IOException {
        try {
            int makeDirectories = this.mService.makeDirectories(this.mId, str);
            if (makeDirectories < 0) {
                throw new java.io.IOException("makeDirectory() failed with errno " + (-makeDirectories));
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeFile(java.lang.String str, long j, int i, java.util.UUID uuid, byte[] bArr, byte[] bArr2, byte[] bArr3) throws java.io.IOException {
        try {
            if (uuid == null && bArr == null) {
                throw new java.io.IOException("File ID and metadata cannot both be null");
            }
            validateV4Signature(bArr2);
            android.os.incremental.IncrementalNewFileParams incrementalNewFileParams = new android.os.incremental.IncrementalNewFileParams();
            incrementalNewFileParams.size = j;
            if (bArr == null) {
                bArr = new byte[0];
            }
            incrementalNewFileParams.metadata = bArr;
            incrementalNewFileParams.fileId = idToBytes(uuid);
            incrementalNewFileParams.signature = bArr2;
            int makeFile = this.mService.makeFile(this.mId, str, i, incrementalNewFileParams, bArr3);
            if (makeFile != 0) {
                throw new java.io.IOException("makeFile() failed with errno " + (-makeFile));
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeFileFromRange(java.lang.String str, java.lang.String str2, long j, long j2) throws java.io.IOException {
        try {
            int makeFileFromRange = this.mService.makeFileFromRange(this.mId, str, str2, j, j2);
            if (makeFileFromRange < 0) {
                throw new java.io.IOException("makeFileFromRange() failed, errno " + (-makeFileFromRange));
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void makeLink(java.lang.String str, android.os.incremental.IncrementalStorage incrementalStorage, java.lang.String str2) throws java.io.IOException {
        try {
            int makeLink = this.mService.makeLink(this.mId, str, incrementalStorage.getId(), str2);
            if (makeLink < 0) {
                throw new java.io.IOException("makeLink() failed with errno " + (-makeLink));
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unlink(java.lang.String str) throws java.io.IOException {
        try {
            int unlink = this.mService.unlink(this.mId, str);
            if (unlink < 0) {
                throw new java.io.IOException("unlink() failed with errno " + (-unlink));
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void moveFile(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        int makeLink;
        try {
            makeLink = this.mService.makeLink(this.mId, str, this.mId, str2);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (makeLink < 0) {
            throw new java.io.IOException("moveFile() failed at makeLink(), errno " + (-makeLink));
        }
        try {
            this.mService.unlink(this.mId, str);
        } catch (android.os.RemoteException e2) {
        }
    }

    public void moveDir(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        int makeBindMount;
        if (!new java.io.File(str2).exists()) {
            throw new java.io.IOException("moveDir() requires that destination dir already exists.");
        }
        try {
            makeBindMount = this.mService.makeBindMount(this.mId, str, str2, 1);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (makeBindMount < 0) {
            throw new java.io.IOException("moveDir() failed at making bind mount, errno " + (-makeBindMount));
        }
        try {
            this.mService.deleteBindMount(this.mId, str);
        } catch (android.os.RemoteException e2) {
        }
    }

    public boolean isFileFullyLoaded(java.lang.String str) throws java.io.IOException {
        try {
            int isFileFullyLoaded = this.mService.isFileFullyLoaded(this.mId, str);
            if (isFileFullyLoaded >= 0) {
                return isFileFullyLoaded == 0;
            }
            throw new java.io.IOException("isFileFullyLoaded() failed, errno " + (-isFileFullyLoaded));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isFullyLoaded() throws java.io.IOException {
        try {
            int isFullyLoaded = this.mService.isFullyLoaded(this.mId);
            if (isFullyLoaded >= 0) {
                return isFullyLoaded == 0;
            }
            throw new java.io.IOException("isFullyLoaded() failed at querying loading progress, errno " + (-isFullyLoaded));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public float getLoadingProgress() throws java.io.IOException {
        try {
            float loadingProgress = this.mService.getLoadingProgress(this.mId);
            if (loadingProgress < 0.0f) {
                throw new java.io.IOException("getLoadingProgress() failed at querying loading progress, errno " + (-loadingProgress));
            }
            return loadingProgress;
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return 0.0f;
        }
    }

    public byte[] getFileMetadata(java.lang.String str) {
        try {
            return this.mService.getMetadataByPath(this.mId, str);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public byte[] getFileMetadata(java.util.UUID uuid) {
        try {
            return this.mService.getMetadataById(this.mId, idToBytes(uuid));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public boolean startLoading(android.content.pm.DataLoaderParams dataLoaderParams, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener, android.os.incremental.StorageHealthCheckParams storageHealthCheckParams, android.os.incremental.IStorageHealthListener iStorageHealthListener, android.os.incremental.PerUidReadTimeouts[] perUidReadTimeoutsArr) {
        java.util.Objects.requireNonNull(perUidReadTimeoutsArr);
        try {
            return this.mService.startLoading(this.mId, dataLoaderParams.getData(), iDataLoaderStatusListener, storageHealthCheckParams, iStorageHealthListener, perUidReadTimeoutsArr);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void onInstallationComplete() {
        try {
            this.mService.onInstallationComplete(this.mId);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static byte[] idToBytes(java.util.UUID uuid) {
        if (uuid == null) {
            return new byte[0];
        }
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(new byte[16]);
        wrap.putLong(uuid.getMostSignificantBits());
        wrap.putLong(uuid.getLeastSignificantBits());
        return wrap.array();
    }

    public static java.util.UUID bytesToId(byte[] bArr) throws java.lang.IllegalArgumentException {
        if (bArr.length != 16) {
            throw new java.lang.IllegalArgumentException("Expected array of size 16, got " + bArr.length);
        }
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
        return new java.util.UUID(wrap.getLong(), wrap.getLong());
    }

    public void disallowReadLogs() {
        try {
            this.mService.disallowReadLogs(this.mId);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private static void validateV4Signature(byte[] bArr) throws java.io.IOException {
        if (bArr == null || bArr.length == 0) {
            return;
        }
        try {
            android.os.incremental.V4Signature readFrom = android.os.incremental.V4Signature.readFrom(bArr);
            if (!readFrom.isVersionSupported()) {
                throw new java.io.IOException("v4 signature version " + readFrom.version + " is not supported");
            }
            android.os.incremental.V4Signature.HashingInfo fromByteArray = android.os.incremental.V4Signature.HashingInfo.fromByteArray(readFrom.hashingInfo);
            android.os.incremental.V4Signature.SigningInfos fromByteArray2 = android.os.incremental.V4Signature.SigningInfos.fromByteArray(readFrom.signingInfos);
            if (fromByteArray.hashAlgorithm != 1) {
                throw new java.io.IOException("Unsupported hashAlgorithm: " + fromByteArray.hashAlgorithm);
            }
            if (fromByteArray.log2BlockSize != 12) {
                throw new java.io.IOException("Unsupported log2BlockSize: " + ((int) fromByteArray.log2BlockSize));
            }
            if (fromByteArray.salt != null && fromByteArray.salt.length > 0) {
                throw new java.io.IOException("Unsupported salt: " + java.util.Arrays.toString(fromByteArray.salt));
            }
            if (fromByteArray.rawRootHash.length != 32) {
                throw new java.io.IOException("rawRootHash has to be 32 bytes");
            }
            if (fromByteArray2.signingInfo.additionalData.length > 128) {
                throw new java.io.IOException("additionalData has to be at most 128 bytes");
            }
        } catch (java.io.IOException e) {
            throw new java.io.IOException("Failed to read v4 signature:", e);
        }
    }

    public boolean configureNativeBinaries(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) {
        try {
            return this.mService.configureNativeBinaries(this.mId, str, str2, str3, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean waitForNativeBinariesExtraction() {
        try {
            return this.mService.waitForNativeBinariesExtraction(this.mId);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean registerLoadingProgressListener(android.os.incremental.IStorageLoadingProgressListener iStorageLoadingProgressListener) {
        try {
            return this.mService.registerLoadingProgressListener(this.mId, iStorageLoadingProgressListener);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean unregisterLoadingProgressListener() {
        try {
            return this.mService.unregisterLoadingProgressListener(this.mId);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public android.os.PersistableBundle getMetrics() {
        try {
            return this.mService.getMetrics(this.mId);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }
}
