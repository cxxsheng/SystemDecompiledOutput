package com.android.server.backup.transport;

/* loaded from: classes.dex */
public class BackupTransportClient {
    private static final java.lang.String TAG = "BackupTransportClient";
    private final com.android.internal.backup.IBackupTransport mTransportBinder;
    private final com.android.server.backup.transport.BackupTransportClient.TransportStatusCallbackPool mCallbackPool = new com.android.server.backup.transport.BackupTransportClient.TransportStatusCallbackPool();
    private final com.android.server.backup.transport.BackupTransportClient.TransportFutures mTransportFutures = new com.android.server.backup.transport.BackupTransportClient.TransportFutures();

    BackupTransportClient(com.android.internal.backup.IBackupTransport iBackupTransport) {
        this.mTransportBinder = iBackupTransport;
    }

    public java.lang.String name() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.name(newFuture);
        return (java.lang.String) getFutureResult(newFuture);
    }

    public android.content.Intent configurationIntent() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.configurationIntent(newFuture);
        return (android.content.Intent) getFutureResult(newFuture);
    }

    public java.lang.String currentDestinationString() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.currentDestinationString(newFuture);
        return (java.lang.String) getFutureResult(newFuture);
    }

    public android.content.Intent dataManagementIntent() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.dataManagementIntent(newFuture);
        return (android.content.Intent) getFutureResult(newFuture);
    }

    @android.annotation.Nullable
    public java.lang.CharSequence dataManagementIntentLabel() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.dataManagementIntentLabel(newFuture);
        return (java.lang.CharSequence) getFutureResult(newFuture);
    }

    public java.lang.String transportDirName() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.transportDirName(newFuture);
        return (java.lang.String) getFutureResult(newFuture);
    }

    public int initializeDevice() throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.initializeDevice(acquire);
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public int clearBackupData(android.content.pm.PackageInfo packageInfo) throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.clearBackupData(packageInfo, acquire);
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public int finishBackup() throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.finishBackup(acquire);
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public long requestBackupTime() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.requestBackupTime(newFuture);
        java.lang.Long l = (java.lang.Long) getFutureResult(newFuture);
        if (l == null) {
            return -1000L;
        }
        return l.longValue();
    }

    public int performBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.performBackup(packageInfo, parcelFileDescriptor, i, acquire);
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public java.util.List<android.app.backup.RestoreSet> getAvailableRestoreSets() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.getAvailableRestoreSets(newFuture);
        return (java.util.List) getFutureResult(newFuture);
    }

    public long getCurrentRestoreSet() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.getCurrentRestoreSet(newFuture);
        java.lang.Long l = (java.lang.Long) getFutureResult(newFuture);
        if (l == null) {
            return -1000L;
        }
        return l.longValue();
    }

    public int startRestore(long j, android.content.pm.PackageInfo[] packageInfoArr) throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.startRestore(j, packageInfoArr, acquire);
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public android.app.backup.RestoreDescription nextRestorePackage() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.nextRestorePackage(newFuture);
        return (android.app.backup.RestoreDescription) getFutureResult(newFuture);
    }

    public int getRestoreData(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.getRestoreData(parcelFileDescriptor, acquire);
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public void finishRestore() throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.finishRestore(acquire);
            acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public long requestFullBackupTime() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.requestFullBackupTime(newFuture);
        java.lang.Long l = (java.lang.Long) getFutureResult(newFuture);
        if (l == null) {
            return -1000L;
        }
        return l.longValue();
    }

    public int performFullBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.performFullBackup(packageInfo, parcelFileDescriptor, i, acquire);
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public int checkFullBackupSize(long j) throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.checkFullBackupSize(j, acquire);
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public int sendBackupData(int i) throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        this.mTransportBinder.sendBackupData(i, acquire);
        try {
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public void cancelFullBackup() throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.cancelFullBackup(acquire);
            acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public boolean isAppEligibleForBackup(android.content.pm.PackageInfo packageInfo, boolean z) throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.isAppEligibleForBackup(packageInfo, z, newFuture);
        java.lang.Boolean bool = (java.lang.Boolean) getFutureResult(newFuture);
        return bool != null && bool.booleanValue();
    }

    public long getBackupQuota(java.lang.String str, boolean z) throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.getBackupQuota(str, z, newFuture);
        java.lang.Long l = (java.lang.Long) getFutureResult(newFuture);
        if (l == null) {
            return -1000L;
        }
        return l.longValue();
    }

    public int getNextFullRestoreDataChunk(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.getNextFullRestoreDataChunk(parcelFileDescriptor, acquire);
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public int abortFullRestore() throws android.os.RemoteException {
        com.android.server.backup.transport.TransportStatusCallback acquire = this.mCallbackPool.acquire();
        try {
            this.mTransportBinder.abortFullRestore(acquire);
            return acquire.getOperationStatus();
        } finally {
            this.mCallbackPool.recycle(acquire);
        }
    }

    public int getTransportFlags() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.getTransportFlags(newFuture);
        java.lang.Integer num = (java.lang.Integer) getFutureResult(newFuture);
        if (num == null) {
            return -1000;
        }
        return num.intValue();
    }

    public android.app.backup.IBackupManagerMonitor getBackupManagerMonitor() throws android.os.RemoteException {
        com.android.internal.infra.AndroidFuture newFuture = this.mTransportFutures.newFuture();
        this.mTransportBinder.getBackupManagerMonitor(newFuture);
        return android.app.backup.IBackupManagerMonitor.Stub.asInterface((android.os.IBinder) getFutureResult(newFuture));
    }

    void onBecomingUnusable() {
        this.mCallbackPool.cancelActiveCallbacks();
        this.mTransportFutures.cancelActiveFutures();
    }

    private <T> T getFutureResult(com.android.internal.infra.AndroidFuture<T> androidFuture) {
        try {
            try {
                return (T) androidFuture.get(com.android.server.backup.BackupAndRestoreFeatureFlags.getBackupTransportFutureTimeoutMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (java.lang.InterruptedException | java.util.concurrent.CancellationException | java.util.concurrent.ExecutionException | java.util.concurrent.TimeoutException e) {
                android.util.Slog.w(TAG, "Failed to get result from transport:", e);
                this.mTransportFutures.remove(androidFuture);
                return null;
            }
        } finally {
            this.mTransportFutures.remove(androidFuture);
        }
    }

    private static class TransportFutures {
        private final java.util.Set<com.android.internal.infra.AndroidFuture<?>> mActiveFutures;
        private final java.lang.Object mActiveFuturesLock;

        private TransportFutures() {
            this.mActiveFuturesLock = new java.lang.Object();
            this.mActiveFutures = new java.util.HashSet();
        }

        <T> com.android.internal.infra.AndroidFuture<T> newFuture() {
            com.android.internal.infra.AndroidFuture<T> androidFuture = new com.android.internal.infra.AndroidFuture<>();
            synchronized (this.mActiveFuturesLock) {
                this.mActiveFutures.add(androidFuture);
            }
            return androidFuture;
        }

        <T> void remove(com.android.internal.infra.AndroidFuture<T> androidFuture) {
            synchronized (this.mActiveFuturesLock) {
                this.mActiveFutures.remove(androidFuture);
            }
        }

        void cancelActiveFutures() {
            synchronized (this.mActiveFuturesLock) {
                java.util.Iterator<com.android.internal.infra.AndroidFuture<?>> it = this.mActiveFutures.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().cancel(true);
                    } catch (java.util.concurrent.CancellationException e) {
                    }
                }
                this.mActiveFutures.clear();
            }
        }
    }

    private static class TransportStatusCallbackPool {
        private static final int MAX_POOL_SIZE = 100;
        private final java.util.Set<com.android.server.backup.transport.TransportStatusCallback> mActiveCallbacks;
        private final java.util.Queue<com.android.server.backup.transport.TransportStatusCallback> mCallbackPool;
        private final java.lang.Object mPoolLock;

        private TransportStatusCallbackPool() {
            this.mPoolLock = new java.lang.Object();
            this.mCallbackPool = new java.util.ArrayDeque();
            this.mActiveCallbacks = new java.util.HashSet();
        }

        com.android.server.backup.transport.TransportStatusCallback acquire() {
            com.android.server.backup.transport.TransportStatusCallback poll;
            synchronized (this.mPoolLock) {
                try {
                    poll = this.mCallbackPool.poll();
                    if (poll == null) {
                        poll = new com.android.server.backup.transport.TransportStatusCallback();
                    }
                    poll.reset();
                    this.mActiveCallbacks.add(poll);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return poll;
        }

        void recycle(com.android.server.backup.transport.TransportStatusCallback transportStatusCallback) {
            synchronized (this.mPoolLock) {
                try {
                    this.mActiveCallbacks.remove(transportStatusCallback);
                    if (this.mCallbackPool.size() > 100) {
                        android.util.Slog.d(com.android.server.backup.transport.BackupTransportClient.TAG, "TransportStatusCallback pool size exceeded");
                    } else {
                        this.mCallbackPool.add(transportStatusCallback);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void cancelActiveCallbacks() {
            synchronized (this.mPoolLock) {
                for (com.android.server.backup.transport.TransportStatusCallback transportStatusCallback : this.mActiveCallbacks) {
                    try {
                        transportStatusCallback.onOperationCompleteWithStatus(-1000);
                        transportStatusCallback.getOperationStatus();
                    } catch (android.os.RemoteException e) {
                    }
                    if (this.mCallbackPool.size() < 100) {
                        this.mCallbackPool.add(transportStatusCallback);
                    }
                }
                this.mActiveCallbacks.clear();
            }
        }
    }
}
