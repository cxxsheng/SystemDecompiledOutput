package android.app.blob;

/* loaded from: classes.dex */
public class BlobStoreManager {
    public static final int COMMIT_RESULT_ERROR = 1;
    public static final int COMMIT_RESULT_SUCCESS = 0;
    public static final int INVALID_RES_ID = -1;
    private final android.content.Context mContext;
    private final android.app.blob.IBlobStoreManager mService;

    public BlobStoreManager(android.content.Context context, android.app.blob.IBlobStoreManager iBlobStoreManager) {
        this.mContext = context;
        this.mService = iBlobStoreManager;
    }

    public long createSession(android.app.blob.BlobHandle blobHandle) throws java.io.IOException {
        try {
            return this.mService.createSession(blobHandle, this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            e.maybeRethrow(android.os.LimitExceededException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public android.app.blob.BlobStoreManager.Session openSession(long j) throws java.io.IOException {
        try {
            return new android.app.blob.BlobStoreManager.Session(this.mService.openSession(j, this.mContext.getOpPackageName()));
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void abandonSession(long j) throws java.io.IOException {
        try {
            this.mService.abandonSession(j, this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public android.os.ParcelFileDescriptor openBlob(android.app.blob.BlobHandle blobHandle) throws java.io.IOException {
        try {
            return this.mService.openBlob(blobHandle, this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void acquireLease(android.app.blob.BlobHandle blobHandle, int i, long j) throws java.io.IOException {
        try {
            this.mService.acquireLease(blobHandle, i, null, j, this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            e.maybeRethrow(android.os.LimitExceededException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void acquireLease(android.app.blob.BlobHandle blobHandle, java.lang.CharSequence charSequence, long j) throws java.io.IOException {
        try {
            this.mService.acquireLease(blobHandle, -1, charSequence, j, this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            e.maybeRethrow(android.os.LimitExceededException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void acquireLease(android.app.blob.BlobHandle blobHandle, int i) throws java.io.IOException {
        acquireLease(blobHandle, i, 0L);
    }

    public void acquireLease(android.app.blob.BlobHandle blobHandle, java.lang.CharSequence charSequence) throws java.io.IOException {
        acquireLease(blobHandle, charSequence, 0L);
    }

    public void releaseLease(android.app.blob.BlobHandle blobHandle) throws java.io.IOException {
        try {
            this.mService.releaseLease(blobHandle, this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void releaseAllLeases() throws java.lang.Exception {
        try {
            this.mService.releaseAllLeases(this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public long getRemainingLeaseQuotaBytes() {
        try {
            return this.mService.getRemainingLeaseQuotaBytes(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void waitForIdle(long j) throws java.lang.InterruptedException, java.util.concurrent.TimeoutException {
        try {
            final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            this.mService.waitForIdle(new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.app.blob.BlobStoreManager$$ExternalSyntheticLambda0
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    countDownLatch.countDown();
                }
            }));
            if (!countDownLatch.await(j, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                throw new java.util.concurrent.TimeoutException("Timed out waiting for service to become idle");
            }
        } catch (android.os.ParcelableException e) {
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.blob.BlobInfo> queryBlobsForUser(android.os.UserHandle userHandle) throws java.io.IOException {
        try {
            return this.mService.queryBlobsForUser(userHandle.getIdentifier());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void deleteBlob(android.app.blob.BlobInfo blobInfo) throws java.io.IOException {
        try {
            this.mService.deleteBlob(blobInfo.getId());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.blob.BlobHandle> getLeasedBlobs() throws java.io.IOException {
        try {
            return this.mService.getLeasedBlobs(this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public android.app.blob.LeaseInfo getLeaseInfo(android.app.blob.BlobHandle blobHandle) throws java.io.IOException {
        try {
            return this.mService.getLeaseInfo(blobHandle, this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public static class Session implements java.io.Closeable {
        private final android.app.blob.IBlobStoreSession mSession;

        private Session(android.app.blob.IBlobStoreSession iBlobStoreSession) {
            this.mSession = iBlobStoreSession;
        }

        public android.os.ParcelFileDescriptor openWrite(long j, long j2) throws java.io.IOException {
            try {
                android.os.ParcelFileDescriptor openWrite = this.mSession.openWrite(j, j2);
                openWrite.seekTo(j);
                return openWrite;
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public android.os.ParcelFileDescriptor openRead() throws java.io.IOException {
            try {
                return this.mSession.openRead();
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public long getSize() throws java.io.IOException {
            try {
                return this.mSession.getSize();
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            try {
                this.mSession.close();
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public void abandon() throws java.io.IOException {
            try {
                this.mSession.abandon();
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public void allowPackageAccess(java.lang.String str, byte[] bArr) throws java.io.IOException {
            try {
                this.mSession.allowPackageAccess(str, bArr);
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                e.maybeRethrow(android.os.LimitExceededException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public boolean isPackageAccessAllowed(java.lang.String str, byte[] bArr) throws java.io.IOException {
            try {
                return this.mSession.isPackageAccessAllowed(str, bArr);
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public void allowSameSignatureAccess() throws java.io.IOException {
            try {
                this.mSession.allowSameSignatureAccess();
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public boolean isSameSignatureAccessAllowed() throws java.io.IOException {
            try {
                return this.mSession.isSameSignatureAccessAllowed();
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public void allowPublicAccess() throws java.io.IOException {
            try {
                this.mSession.allowPublicAccess();
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public boolean isPublicAccessAllowed() throws java.io.IOException {
            try {
                return this.mSession.isPublicAccessAllowed();
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        public void commit(final java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) throws java.io.IOException {
            try {
                this.mSession.commit(new android.app.blob.IBlobCommitCallback.Stub() { // from class: android.app.blob.BlobStoreManager.Session.1
                    @Override // android.app.blob.IBlobCommitCallback
                    public void onResult(int i) {
                        executor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.BiConsumer() { // from class: android.app.blob.BlobStoreManager$Session$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                ((java.util.function.Consumer) obj).accept((java.lang.Integer) obj2);
                            }
                        }, consumer, java.lang.Integer.valueOf(i)));
                    }
                });
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.IOException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }
}
