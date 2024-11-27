package com.android.server.blob;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes.dex */
class BlobStoreSession extends android.app.blob.IBlobStoreSession.Stub {
    static final int STATE_ABANDONED = 2;
    static final int STATE_CLOSED = 0;
    static final int STATE_COMMITTED = 3;
    static final int STATE_OPENED = 1;
    static final int STATE_VERIFIED_INVALID = 5;
    static final int STATE_VERIFIED_VALID = 4;

    @com.android.internal.annotations.GuardedBy({"mSessionLock"})
    private final com.android.server.blob.BlobAccessMode mBlobAccessMode;

    @com.android.internal.annotations.GuardedBy({"mSessionLock"})
    private android.app.blob.IBlobCommitCallback mBlobCommitCallback;
    private final android.app.blob.BlobHandle mBlobHandle;
    private final android.content.Context mContext;
    private final long mCreationTimeMs;
    private byte[] mDataDigest;
    private final com.android.server.blob.BlobStoreManagerService.SessionStateChangeListener mListener;
    private final java.lang.String mOwnerPackageName;
    private final int mOwnerUid;

    @com.android.internal.annotations.GuardedBy({"mRevocableFds"})
    private final java.util.ArrayList<android.os.RevocableFileDescriptor> mRevocableFds;
    private java.io.File mSessionFile;
    private final long mSessionId;
    private final java.lang.Object mSessionLock;

    @com.android.internal.annotations.GuardedBy({"mSessionLock"})
    private int mState;

    private BlobStoreSession(android.content.Context context, long j, android.app.blob.BlobHandle blobHandle, int i, java.lang.String str, long j2, com.android.server.blob.BlobStoreManagerService.SessionStateChangeListener sessionStateChangeListener) {
        this.mSessionLock = new java.lang.Object();
        this.mRevocableFds = new java.util.ArrayList<>();
        this.mState = 0;
        this.mBlobAccessMode = new com.android.server.blob.BlobAccessMode();
        this.mContext = context;
        this.mBlobHandle = blobHandle;
        this.mSessionId = j;
        this.mOwnerUid = i;
        this.mOwnerPackageName = str;
        this.mCreationTimeMs = j2;
        this.mListener = sessionStateChangeListener;
    }

    BlobStoreSession(android.content.Context context, long j, android.app.blob.BlobHandle blobHandle, int i, java.lang.String str, com.android.server.blob.BlobStoreManagerService.SessionStateChangeListener sessionStateChangeListener) {
        this(context, j, blobHandle, i, str, java.lang.System.currentTimeMillis(), sessionStateChangeListener);
    }

    public android.app.blob.BlobHandle getBlobHandle() {
        return this.mBlobHandle;
    }

    public long getSessionId() {
        return this.mSessionId;
    }

    public int getOwnerUid() {
        return this.mOwnerUid;
    }

    public java.lang.String getOwnerPackageName() {
        return this.mOwnerPackageName;
    }

    boolean hasAccess(int i, java.lang.String str) {
        return this.mOwnerUid == i && this.mOwnerPackageName.equals(str);
    }

    void open() {
        synchronized (this.mSessionLock) {
            try {
                if (isFinalized()) {
                    throw new java.lang.IllegalStateException("Not allowed to open session with state: " + stateToString(this.mState));
                }
                this.mState = 1;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    int getState() {
        int i;
        synchronized (this.mSessionLock) {
            i = this.mState;
        }
        return i;
    }

    void sendCommitCallbackResult(int i) {
        synchronized (this.mSessionLock) {
            try {
                this.mBlobCommitCallback.onResult(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.d(com.android.server.blob.BlobStoreConfig.TAG, "Error sending the callback result", e);
            }
            this.mBlobCommitCallback = null;
        }
    }

    com.android.server.blob.BlobAccessMode getBlobAccessMode() {
        com.android.server.blob.BlobAccessMode blobAccessMode;
        synchronized (this.mSessionLock) {
            blobAccessMode = this.mBlobAccessMode;
        }
        return blobAccessMode;
    }

    boolean isFinalized() {
        boolean z;
        synchronized (this.mSessionLock) {
            try {
                z = this.mState == 3 || this.mState == 2;
            } finally {
            }
        }
        return z;
    }

    boolean isExpired() {
        long lastModified = getSessionFile().lastModified();
        if (lastModified == 0) {
            lastModified = this.mCreationTimeMs;
        }
        return com.android.server.blob.BlobStoreConfig.hasSessionExpired(lastModified);
    }

    @android.annotation.NonNull
    public android.os.ParcelFileDescriptor openWrite(long j, long j2) {
        java.io.FileDescriptor fileDescriptor;
        android.os.ParcelFileDescriptor revocableFileDescriptor;
        com.android.internal.util.Preconditions.checkArgumentNonnegative(j, "offsetBytes must not be negative");
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            if (this.mState != 1) {
                throw new java.lang.IllegalStateException("Not allowed to write in state: " + stateToString(this.mState));
            }
        }
        try {
            fileDescriptor = openWriteInternal(j, j2);
            try {
                android.os.RevocableFileDescriptor revocableFileDescriptor2 = new android.os.RevocableFileDescriptor(this.mContext, fileDescriptor, com.android.server.blob.BlobStoreUtils.getRevocableFdHandler());
                synchronized (this.mSessionLock) {
                    try {
                        if (this.mState != 1) {
                            libcore.io.IoUtils.closeQuietly(fileDescriptor);
                            throw new java.lang.IllegalStateException("Not allowed to write in state: " + stateToString(this.mState));
                        }
                        trackRevocableFdLocked(revocableFileDescriptor2);
                        revocableFileDescriptor = revocableFileDescriptor2.getRevocableFileDescriptor();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return revocableFileDescriptor;
            } catch (java.io.IOException e) {
                e = e;
                libcore.io.IoUtils.closeQuietly(fileDescriptor);
                throw android.util.ExceptionUtils.wrap(e);
            }
        } catch (java.io.IOException e2) {
            e = e2;
            fileDescriptor = null;
        }
    }

    @android.annotation.NonNull
    private java.io.FileDescriptor openWriteInternal(long j, long j2) throws java.io.IOException {
        try {
            java.io.File sessionFile = getSessionFile();
            if (sessionFile == null) {
                throw new java.lang.IllegalStateException("Couldn't get the file for this session");
            }
            java.io.FileDescriptor open = android.system.Os.open(sessionFile.getPath(), android.system.OsConstants.O_CREAT | android.system.OsConstants.O_RDWR, com.android.internal.util.FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
            if (j > 0 && android.system.Os.lseek(open, j, android.system.OsConstants.SEEK_SET) != j) {
                throw new java.lang.IllegalStateException("Failed to seek " + j + "; curOffset=" + j);
            }
            if (j2 > 0) {
                ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).allocateBytes(open, j2);
            }
            return open;
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    @android.annotation.NonNull
    public android.os.ParcelFileDescriptor openRead() {
        java.io.FileDescriptor fileDescriptor;
        android.os.ParcelFileDescriptor revocableFileDescriptor;
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new java.lang.IllegalStateException("Not allowed to read in state: " + stateToString(this.mState));
                }
                if (!com.android.server.blob.BlobStoreConfig.shouldUseRevocableFdForReads()) {
                    try {
                        return new android.os.ParcelFileDescriptor(openReadInternal());
                    } catch (java.io.IOException e) {
                        throw android.util.ExceptionUtils.wrap(e);
                    }
                }
                try {
                    fileDescriptor = openReadInternal();
                    try {
                        android.os.RevocableFileDescriptor revocableFileDescriptor2 = new android.os.RevocableFileDescriptor(this.mContext, fileDescriptor);
                        synchronized (this.mSessionLock) {
                            try {
                                if (this.mState != 1) {
                                    libcore.io.IoUtils.closeQuietly(fileDescriptor);
                                    throw new java.lang.IllegalStateException("Not allowed to read in state: " + stateToString(this.mState));
                                }
                                trackRevocableFdLocked(revocableFileDescriptor2);
                                revocableFileDescriptor = revocableFileDescriptor2.getRevocableFileDescriptor();
                            } finally {
                            }
                        }
                        return revocableFileDescriptor;
                    } catch (java.io.IOException e2) {
                        e = e2;
                        libcore.io.IoUtils.closeQuietly(fileDescriptor);
                        throw android.util.ExceptionUtils.wrap(e);
                    }
                } catch (java.io.IOException e3) {
                    e = e3;
                    fileDescriptor = null;
                }
            } finally {
            }
        }
    }

    @android.annotation.NonNull
    private java.io.FileDescriptor openReadInternal() throws java.io.IOException {
        try {
            java.io.File sessionFile = getSessionFile();
            if (sessionFile == null) {
                throw new java.lang.IllegalStateException("Couldn't get the file for this session");
            }
            return android.system.Os.open(sessionFile.getPath(), android.system.OsConstants.O_RDONLY, 0);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public long getSize() {
        return getSessionFile().length();
    }

    public void allowPackageAccess(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr) {
        assertCallerIsOwner();
        java.util.Objects.requireNonNull(str, "packageName must not be null");
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new java.lang.IllegalStateException("Not allowed to change access type in state: " + stateToString(this.mState));
                }
                if (this.mBlobAccessMode.getAllowedPackagesCount() >= com.android.server.blob.BlobStoreConfig.getMaxPermittedPackages()) {
                    throw new android.os.ParcelableException(new android.os.LimitExceededException("Too many packages permitted to access the blob: " + this.mBlobAccessMode.getAllowedPackagesCount()));
                }
                this.mBlobAccessMode.allowPackageAccess(str, bArr);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void allowSameSignatureAccess() {
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new java.lang.IllegalStateException("Not allowed to change access type in state: " + stateToString(this.mState));
                }
                this.mBlobAccessMode.allowSameSignatureAccess();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void allowPublicAccess() {
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new java.lang.IllegalStateException("Not allowed to change access type in state: " + stateToString(this.mState));
                }
                this.mBlobAccessMode.allowPublicAccess();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isPackageAccessAllowed(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr) {
        boolean isPackageAccessAllowed;
        assertCallerIsOwner();
        java.util.Objects.requireNonNull(str, "packageName must not be null");
        com.android.internal.util.Preconditions.checkByteArrayNotEmpty(bArr, "certificate");
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new java.lang.IllegalStateException("Not allowed to get access type in state: " + stateToString(this.mState));
                }
                isPackageAccessAllowed = this.mBlobAccessMode.isPackageAccessAllowed(str, bArr);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return isPackageAccessAllowed;
    }

    public boolean isSameSignatureAccessAllowed() {
        boolean isSameSignatureAccessAllowed;
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new java.lang.IllegalStateException("Not allowed to get access type in state: " + stateToString(this.mState));
                }
                isSameSignatureAccessAllowed = this.mBlobAccessMode.isSameSignatureAccessAllowed();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return isSameSignatureAccessAllowed;
    }

    public boolean isPublicAccessAllowed() {
        boolean isPublicAccessAllowed;
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    throw new java.lang.IllegalStateException("Not allowed to get access type in state: " + stateToString(this.mState));
                }
                isPublicAccessAllowed = this.mBlobAccessMode.isPublicAccessAllowed();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return isPublicAccessAllowed;
    }

    public void close() {
        closeSession(0, false);
    }

    public void abandon() {
        closeSession(2, true);
    }

    public void commit(android.app.blob.IBlobCommitCallback iBlobCommitCallback) {
        synchronized (this.mSessionLock) {
            this.mBlobCommitCallback = iBlobCommitCallback;
            closeSession(3, true);
        }
    }

    private void closeSession(int i, boolean z) {
        assertCallerIsOwner();
        synchronized (this.mSessionLock) {
            try {
                if (this.mState != 1) {
                    if (i == 0) {
                        return;
                    }
                    throw new java.lang.IllegalStateException("Not allowed to delete or abandon a session with state: " + stateToString(this.mState));
                }
                this.mState = i;
                revokeAllFds();
                if (z) {
                    this.mListener.onStateChanged(this);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void computeDigest() {
        try {
            try {
                android.os.Trace.traceBegin(524288L, "computeBlobDigest-i" + this.mSessionId + "-l" + getSessionFile().length());
                this.mDataDigest = android.os.FileUtils.digest(getSessionFile(), this.mBlobHandle.algorithm);
            } catch (java.io.IOException | java.security.NoSuchAlgorithmException e) {
                android.util.Slog.e(com.android.server.blob.BlobStoreConfig.TAG, "Error computing the digest", e);
            }
        } finally {
            android.os.Trace.traceEnd(524288L);
        }
    }

    void verifyBlobData() {
        synchronized (this.mSessionLock) {
            try {
                if (this.mDataDigest != null && java.util.Arrays.equals(this.mDataDigest, this.mBlobHandle.digest)) {
                    this.mState = 4;
                } else {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Digest of the data (");
                    sb.append(this.mDataDigest == null ? "null" : android.app.blob.BlobHandle.safeDigest(this.mDataDigest));
                    sb.append(") didn't match the given BlobHandle.digest (");
                    sb.append(android.app.blob.BlobHandle.safeDigest(this.mBlobHandle.digest));
                    sb.append(")");
                    android.util.Slog.d(com.android.server.blob.BlobStoreConfig.TAG, sb.toString());
                    this.mState = 5;
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_COMMITTED, getOwnerUid(), this.mSessionId, getSize(), 3);
                    sendCommitCallbackResult(1);
                }
                this.mListener.onStateChanged(this);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void destroy() {
        revokeAllFds();
        getSessionFile().delete();
    }

    private void revokeAllFds() {
        synchronized (this.mRevocableFds) {
            try {
                for (int size = this.mRevocableFds.size() - 1; size >= 0; size--) {
                    this.mRevocableFds.get(size).revoke();
                }
                this.mRevocableFds.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSessionLock"})
    private void trackRevocableFdLocked(final android.os.RevocableFileDescriptor revocableFileDescriptor) {
        synchronized (this.mRevocableFds) {
            this.mRevocableFds.add(revocableFileDescriptor);
        }
        revocableFileDescriptor.addOnCloseListener(new android.os.ParcelFileDescriptor.OnCloseListener() { // from class: com.android.server.blob.BlobStoreSession$$ExternalSyntheticLambda0
            @Override // android.os.ParcelFileDescriptor.OnCloseListener
            public final void onClose(java.io.IOException iOException) {
                com.android.server.blob.BlobStoreSession.this.lambda$trackRevocableFdLocked$0(revocableFileDescriptor, iOException);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$trackRevocableFdLocked$0(android.os.RevocableFileDescriptor revocableFileDescriptor, java.io.IOException iOException) {
        synchronized (this.mRevocableFds) {
            this.mRevocableFds.remove(revocableFileDescriptor);
        }
    }

    @android.annotation.Nullable
    java.io.File getSessionFile() {
        if (this.mSessionFile == null) {
            this.mSessionFile = com.android.server.blob.BlobStoreConfig.prepareBlobFile(this.mSessionId);
        }
        return this.mSessionFile;
    }

    @android.annotation.NonNull
    static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "<closed>";
            case 1:
                return "<opened>";
            case 2:
                return "<abandoned>";
            case 3:
                return "<committed>";
            case 4:
                return "<verified_valid>";
            case 5:
                return "<verified_invalid>";
            default:
                android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Unknown state: " + i);
                return "<unknown>";
        }
    }

    public java.lang.String toString() {
        return "BlobStoreSession {id:" + this.mSessionId + ",handle:" + this.mBlobHandle + ",uid:" + this.mOwnerUid + ",pkg:" + this.mOwnerPackageName + "}";
    }

    private void assertCallerIsOwner() {
        if (android.os.Binder.getCallingUid() != this.mOwnerUid) {
            throw new java.lang.SecurityException(this.mOwnerUid + " is not the session owner");
        }
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter, com.android.server.blob.BlobStoreManagerService.DumpArgs dumpArgs) {
        synchronized (this.mSessionLock) {
            indentingPrintWriter.println("state: " + stateToString(this.mState));
            indentingPrintWriter.println("ownerUid: " + this.mOwnerUid);
            indentingPrintWriter.println("ownerPkg: " + this.mOwnerPackageName);
            indentingPrintWriter.println("creation time: " + com.android.server.blob.BlobStoreUtils.formatTime(this.mCreationTimeMs));
            indentingPrintWriter.println("size: " + android.text.format.Formatter.formatFileSize(this.mContext, getSize(), 8));
            indentingPrintWriter.println("blobHandle:");
            indentingPrintWriter.increaseIndent();
            this.mBlobHandle.dump(indentingPrintWriter, dumpArgs.shouldDumpFull());
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("accessMode:");
            indentingPrintWriter.increaseIndent();
            this.mBlobAccessMode.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Open fds: #" + this.mRevocableFds.size());
        }
    }

    void writeToXml(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        synchronized (this.mSessionLock) {
            com.android.internal.util.XmlUtils.writeLongAttribute(xmlSerializer, "id", this.mSessionId);
            com.android.internal.util.XmlUtils.writeStringAttribute(xmlSerializer, "p", this.mOwnerPackageName);
            com.android.internal.util.XmlUtils.writeIntAttribute(xmlSerializer, "u", this.mOwnerUid);
            com.android.internal.util.XmlUtils.writeLongAttribute(xmlSerializer, "crt", this.mCreationTimeMs);
            xmlSerializer.startTag(null, "bh");
            this.mBlobHandle.writeToXml(xmlSerializer);
            xmlSerializer.endTag(null, "bh");
            xmlSerializer.startTag(null, "am");
            this.mBlobAccessMode.writeToXml(xmlSerializer);
            xmlSerializer.endTag(null, "am");
        }
    }

    @android.annotation.Nullable
    static com.android.server.blob.BlobStoreSession createFromXml(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser, int i, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.blob.BlobStoreManagerService.SessionStateChangeListener sessionStateChangeListener) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        long currentTimeMillis;
        long readLongAttribute = com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, "id");
        java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, "p");
        int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "u");
        if (i >= 5) {
            currentTimeMillis = com.android.internal.util.XmlUtils.readLongAttribute(xmlPullParser, "crt");
        } else {
            currentTimeMillis = java.lang.System.currentTimeMillis();
        }
        int depth = xmlPullParser.getDepth();
        android.app.blob.BlobHandle blobHandle = null;
        com.android.server.blob.BlobAccessMode blobAccessMode = null;
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("bh".equals(xmlPullParser.getName())) {
                blobHandle = android.app.blob.BlobHandle.createFromXml(xmlPullParser);
            } else if ("am".equals(xmlPullParser.getName())) {
                blobAccessMode = com.android.server.blob.BlobAccessMode.createFromXml(xmlPullParser);
            }
        }
        if (blobHandle == null) {
            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "blobHandle should be available");
            return null;
        }
        if (blobAccessMode == null) {
            android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "blobAccessMode should be available");
            return null;
        }
        com.android.server.blob.BlobStoreSession blobStoreSession = new com.android.server.blob.BlobStoreSession(context, readLongAttribute, blobHandle, readIntAttribute, readStringAttribute, currentTimeMillis, sessionStateChangeListener);
        blobStoreSession.mBlobAccessMode.allow(blobAccessMode);
        return blobStoreSession;
    }
}
