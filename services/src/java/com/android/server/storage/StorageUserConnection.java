package com.android.server.storage;

/* loaded from: classes2.dex */
public final class StorageUserConnection {
    private static final int DEFAULT_REMOTE_TIMEOUT_SECONDS = 20;
    private static final java.lang.String TAG = "StorageUserConnection";
    private final android.content.Context mContext;
    private final android.os.HandlerThread mHandlerThread;
    private final com.android.server.storage.StorageSessionController mSessionController;
    private final android.os.storage.StorageManagerInternal mSmInternal;
    private final int mUserId;
    private final java.lang.Object mSessionsLock = new java.lang.Object();
    private final com.android.server.storage.StorageUserConnection.ActiveConnection mActiveConnection = new com.android.server.storage.StorageUserConnection.ActiveConnection();

    @com.android.internal.annotations.GuardedBy({"mSessionsLock"})
    private final java.util.Map<java.lang.String, com.android.server.storage.StorageUserConnection.Session> mSessions = new java.util.HashMap();

    @com.android.internal.annotations.GuardedBy({"mSessionsLock"})
    private final android.util.SparseArray<java.lang.Integer> mUidsBlockedOnIo = new android.util.SparseArray<>();

    @java.lang.FunctionalInterface
    interface AsyncStorageServiceCall {
        void run(@android.annotation.NonNull android.service.storage.IExternalStorageService iExternalStorageService, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;
    }

    public StorageUserConnection(android.content.Context context, int i, com.android.server.storage.StorageSessionController storageSessionController) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        this.mUserId = com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        this.mSessionController = storageSessionController;
        this.mSmInternal = (android.os.storage.StorageManagerInternal) com.android.server.LocalServices.getService(android.os.storage.StorageManagerInternal.class);
        this.mHandlerThread = new android.os.HandlerThread("StorageUserConnectionThread-" + this.mUserId);
        this.mHandlerThread.start();
    }

    public void startSession(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str2, java.lang.String str3) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(parcelFileDescriptor);
        java.util.Objects.requireNonNull(str2);
        java.util.Objects.requireNonNull(str3);
        com.android.server.storage.StorageUserConnection.Session session = new com.android.server.storage.StorageUserConnection.Session(str, str2, str3);
        synchronized (this.mSessionsLock) {
            com.android.internal.util.Preconditions.checkArgument(!this.mSessions.containsKey(str));
            this.mSessions.put(str, session);
        }
        this.mActiveConnection.startSession(session, parcelFileDescriptor);
    }

    public void notifyVolumeStateChanged(java.lang.String str, android.os.storage.StorageVolume storageVolume) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(storageVolume);
        synchronized (this.mSessionsLock) {
            try {
                if (!this.mSessions.containsKey(str)) {
                    android.util.Slog.i(TAG, "No session found for sessionId: " + str);
                    return;
                }
                this.mActiveConnection.notifyVolumeStateChanged(str, storageVolume);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void freeCache(java.lang.String str, long j) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        synchronized (this.mSessionsLock) {
            try {
                java.util.Iterator<java.lang.String> it = this.mSessions.keySet().iterator();
                while (it.hasNext()) {
                    this.mActiveConnection.freeCache(it.next(), str, j);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyAnrDelayStarted(java.lang.String str, int i, int i2, int i3) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        java.util.List primaryVolumeIds = this.mSmInternal.getPrimaryVolumeIds();
        synchronized (this.mSessionsLock) {
            try {
                java.util.Iterator<java.lang.String> it = this.mSessions.keySet().iterator();
                while (it.hasNext()) {
                    if (primaryVolumeIds.contains(it.next())) {
                        this.mActiveConnection.notifyAnrDelayStarted(str, i, i2, i3);
                        return;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public com.android.server.storage.StorageUserConnection.Session removeSession(java.lang.String str) {
        com.android.server.storage.StorageUserConnection.Session remove;
        synchronized (this.mSessionsLock) {
            this.mUidsBlockedOnIo.clear();
            remove = this.mSessions.remove(str);
        }
        return remove;
    }

    public void removeSessionAndWait(java.lang.String str) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        com.android.server.storage.StorageUserConnection.Session removeSession = removeSession(str);
        if (removeSession == null) {
            android.util.Slog.i(TAG, "No session found for id: " + str);
            return;
        }
        android.util.Slog.i(TAG, "Waiting for session end " + removeSession + " ...");
        this.mActiveConnection.endSession(removeSession);
    }

    public void resetUserSessions() {
        synchronized (this.mSessionsLock) {
            try {
                if (this.mSessions.isEmpty()) {
                    return;
                }
                this.mSmInternal.resetUser(this.mUserId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeAllSessions() {
        synchronized (this.mSessionsLock) {
            android.util.Slog.i(TAG, "Removing  " + this.mSessions.size() + " sessions for user: " + this.mUserId + "...");
            this.mSessions.clear();
        }
    }

    public void close() {
        this.mActiveConnection.close();
        this.mHandlerThread.quit();
    }

    public java.util.Set<java.lang.String> getAllSessionIds() {
        java.util.HashSet hashSet;
        synchronized (this.mSessionsLock) {
            hashSet = new java.util.HashSet(this.mSessions.keySet());
        }
        return hashSet;
    }

    public void notifyAppIoBlocked(java.lang.String str, int i, int i2, int i3) {
        synchronized (this.mSessionsLock) {
            this.mUidsBlockedOnIo.put(i, java.lang.Integer.valueOf(this.mUidsBlockedOnIo.get(i, 0).intValue() + 1));
        }
    }

    public void notifyAppIoResumed(java.lang.String str, int i, int i2, int i3) {
        synchronized (this.mSessionsLock) {
            try {
                int intValue = this.mUidsBlockedOnIo.get(i, 0).intValue();
                if (intValue == 0) {
                    android.util.Slog.w(TAG, "Unexpected app IO resumption for uid: " + i);
                }
                if (intValue <= 1) {
                    this.mUidsBlockedOnIo.remove(i);
                } else {
                    this.mUidsBlockedOnIo.put(i, java.lang.Integer.valueOf(intValue - 1));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAppIoBlocked(int i) {
        boolean contains;
        synchronized (this.mSessionsLock) {
            contains = this.mUidsBlockedOnIo.contains(i);
        }
        return contains;
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ActiveConnection implements java.lang.AutoCloseable {
        private final java.lang.Object mLock;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final java.util.ArrayList<java.util.concurrent.CompletableFuture<java.lang.Void>> mOutstandingOps;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        private java.util.concurrent.CompletableFuture<android.service.storage.IExternalStorageService> mRemoteFuture;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        private android.content.ServiceConnection mServiceConnection;

        private ActiveConnection() {
            this.mLock = new java.lang.Object();
            this.mOutstandingOps = new java.util.ArrayList<>();
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            android.content.ServiceConnection serviceConnection;
            synchronized (this.mLock) {
                try {
                    android.util.Slog.i(com.android.server.storage.StorageUserConnection.TAG, "Closing connection for user " + com.android.server.storage.StorageUserConnection.this.mUserId);
                    serviceConnection = this.mServiceConnection;
                    this.mServiceConnection = null;
                    if (this.mRemoteFuture != null) {
                        this.mRemoteFuture.cancel(true);
                        this.mRemoteFuture = null;
                    }
                    java.util.Iterator<java.util.concurrent.CompletableFuture<java.lang.Void>> it = this.mOutstandingOps.iterator();
                    while (it.hasNext()) {
                        it.next().cancel(true);
                    }
                    this.mOutstandingOps.clear();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (serviceConnection != null) {
                try {
                    com.android.server.storage.StorageUserConnection.this.mContext.unbindService(serviceConnection);
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(com.android.server.storage.StorageUserConnection.TAG, "Failed to unbind service", e);
                }
            }
        }

        private void asyncBestEffort(java.util.function.Consumer<android.service.storage.IExternalStorageService> consumer) {
            synchronized (this.mLock) {
                try {
                    if (this.mRemoteFuture == null) {
                        android.util.Slog.w(com.android.server.storage.StorageUserConnection.TAG, "Dropping async request service is not bound");
                        return;
                    }
                    android.service.storage.IExternalStorageService now = this.mRemoteFuture.getNow(null);
                    if (now == null) {
                        android.util.Slog.w(com.android.server.storage.StorageUserConnection.TAG, "Dropping async request service is not connected");
                    } else {
                        consumer.accept(now);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void waitForAsyncVoid(com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall asyncStorageServiceCall) throws java.lang.Exception {
            final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
            waitForAsync(asyncStorageServiceCall, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda2
                public final void onResult(android.os.Bundle bundle) {
                    com.android.server.storage.StorageUserConnection.ActiveConnection.this.lambda$waitForAsyncVoid$0(completableFuture, bundle);
                }
            }), completableFuture, this.mOutstandingOps, 20L);
        }

        private <T> T waitForAsync(final com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall asyncStorageServiceCall, final android.os.RemoteCallback remoteCallback, final java.util.concurrent.CompletableFuture<T> completableFuture, java.util.ArrayList<java.util.concurrent.CompletableFuture<T>> arrayList, long j) throws java.lang.Exception {
            java.util.concurrent.CompletableFuture<android.service.storage.IExternalStorageService> connectIfNeeded = connectIfNeeded();
            try {
                synchronized (this.mLock) {
                    arrayList.add(completableFuture);
                }
                T t = (T) connectIfNeeded.thenCompose(new java.util.function.Function() { // from class: com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        java.util.concurrent.CompletionStage lambda$waitForAsync$1;
                        lambda$waitForAsync$1 = com.android.server.storage.StorageUserConnection.ActiveConnection.lambda$waitForAsync$1(com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall.this, remoteCallback, completableFuture, (android.service.storage.IExternalStorageService) obj);
                        return lambda$waitForAsync$1;
                    }
                }).get(j, java.util.concurrent.TimeUnit.SECONDS);
                synchronized (this.mLock) {
                    arrayList.remove(completableFuture);
                }
                return t;
            } catch (java.lang.Throwable th) {
                synchronized (this.mLock) {
                    arrayList.remove(completableFuture);
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.util.concurrent.CompletionStage lambda$waitForAsync$1(com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall asyncStorageServiceCall, android.os.RemoteCallback remoteCallback, java.util.concurrent.CompletableFuture completableFuture, android.service.storage.IExternalStorageService iExternalStorageService) {
            try {
                asyncStorageServiceCall.run(iExternalStorageService, remoteCallback);
            } catch (android.os.RemoteException e) {
                completableFuture.completeExceptionally(e);
            }
            return completableFuture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$startSession$2(com.android.server.storage.StorageUserConnection.Session session, android.os.ParcelFileDescriptor parcelFileDescriptor, android.service.storage.IExternalStorageService iExternalStorageService, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            iExternalStorageService.startSession(session.sessionId, 3, parcelFileDescriptor, session.upperPath, session.lowerPath, remoteCallback);
        }

        public void startSession(final com.android.server.storage.StorageUserConnection.Session session, final android.os.ParcelFileDescriptor parcelFileDescriptor) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
            try {
                try {
                    waitForAsyncVoid(new com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall() { // from class: com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda5
                        @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
                        public final void run(android.service.storage.IExternalStorageService iExternalStorageService, android.os.RemoteCallback remoteCallback) {
                            com.android.server.storage.StorageUserConnection.ActiveConnection.lambda$startSession$2(com.android.server.storage.StorageUserConnection.Session.this, parcelFileDescriptor, iExternalStorageService, remoteCallback);
                        }
                    });
                    try {
                        parcelFileDescriptor.close();
                    } catch (java.io.IOException e) {
                    }
                } catch (java.lang.Exception e2) {
                    throw new com.android.server.storage.StorageSessionController.ExternalStorageServiceException("Failed to start session: " + session, e2);
                }
            } catch (java.lang.Throwable th) {
                try {
                    parcelFileDescriptor.close();
                } catch (java.io.IOException e3) {
                }
                throw th;
            }
        }

        public void endSession(final com.android.server.storage.StorageUserConnection.Session session) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
            try {
                waitForAsyncVoid(new com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall() { // from class: com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda6
                    @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
                    public final void run(android.service.storage.IExternalStorageService iExternalStorageService, android.os.RemoteCallback remoteCallback) {
                        com.android.server.storage.StorageUserConnection.ActiveConnection.lambda$endSession$3(com.android.server.storage.StorageUserConnection.Session.this, iExternalStorageService, remoteCallback);
                    }
                });
            } catch (java.lang.Exception e) {
                throw new com.android.server.storage.StorageSessionController.ExternalStorageServiceException("Failed to end session: " + session, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$endSession$3(com.android.server.storage.StorageUserConnection.Session session, android.service.storage.IExternalStorageService iExternalStorageService, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            iExternalStorageService.endSession(session.sessionId, remoteCallback);
        }

        public void notifyVolumeStateChanged(final java.lang.String str, final android.os.storage.StorageVolume storageVolume) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
            try {
                waitForAsyncVoid(new com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall() { // from class: com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda4
                    @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
                    public final void run(android.service.storage.IExternalStorageService iExternalStorageService, android.os.RemoteCallback remoteCallback) {
                        iExternalStorageService.notifyVolumeStateChanged(str, storageVolume, remoteCallback);
                    }
                });
            } catch (java.lang.Exception e) {
                throw new com.android.server.storage.StorageSessionController.ExternalStorageServiceException("Failed to notify volume state changed for vol : " + storageVolume, e);
            }
        }

        public void freeCache(final java.lang.String str, final java.lang.String str2, final long j) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
            try {
                waitForAsyncVoid(new com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall() { // from class: com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda1
                    @Override // com.android.server.storage.StorageUserConnection.AsyncStorageServiceCall
                    public final void run(android.service.storage.IExternalStorageService iExternalStorageService, android.os.RemoteCallback remoteCallback) {
                        iExternalStorageService.freeCache(str, str2, j, remoteCallback);
                    }
                });
            } catch (java.lang.Exception e) {
                throw new com.android.server.storage.StorageSessionController.ExternalStorageServiceException("Failed to free " + j + " bytes for volumeUuid : " + str2, e);
            }
        }

        public void notifyAnrDelayStarted(final java.lang.String str, final int i, final int i2, final int i3) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
            asyncBestEffort(new java.util.function.Consumer() { // from class: com.android.server.storage.StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.storage.StorageUserConnection.ActiveConnection.lambda$notifyAnrDelayStarted$6(str, i, i2, i3, (android.service.storage.IExternalStorageService) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$notifyAnrDelayStarted$6(java.lang.String str, int i, int i2, int i3, android.service.storage.IExternalStorageService iExternalStorageService) {
            try {
                iExternalStorageService.notifyAnrDelayStarted(str, i, i2, i3);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.storage.StorageUserConnection.TAG, "Failed to notify ANR delay started", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: setResult, reason: merged with bridge method [inline-methods] */
        public void lambda$waitForAsyncVoid$0(android.os.Bundle bundle, java.util.concurrent.CompletableFuture<java.lang.Void> completableFuture) {
            android.os.ParcelableException parcelableException = (android.os.ParcelableException) bundle.getParcelable("android.service.storage.extra.error", android.os.ParcelableException.class);
            if (parcelableException != null) {
                completableFuture.completeExceptionally(parcelableException);
            } else {
                completableFuture.complete(null);
            }
        }

        private java.util.concurrent.CompletableFuture<android.service.storage.IExternalStorageService> connectIfNeeded() throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
            android.content.ComponentName externalStorageServiceComponentName = com.android.server.storage.StorageUserConnection.this.mSessionController.getExternalStorageServiceComponentName();
            if (externalStorageServiceComponentName == null) {
                throw new com.android.server.storage.StorageSessionController.ExternalStorageServiceException("Not ready to bind to the ExternalStorageService for user " + com.android.server.storage.StorageUserConnection.this.mUserId);
            }
            synchronized (this.mLock) {
                try {
                    if (this.mRemoteFuture != null) {
                        return this.mRemoteFuture;
                    }
                    final java.util.concurrent.CompletableFuture<android.service.storage.IExternalStorageService> completableFuture = new java.util.concurrent.CompletableFuture<>();
                    this.mServiceConnection = new android.content.ServiceConnection() { // from class: com.android.server.storage.StorageUserConnection.ActiveConnection.1
                        @Override // android.content.ServiceConnection
                        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                            android.util.Slog.i(com.android.server.storage.StorageUserConnection.TAG, "Service: [" + componentName + "] connected. User [" + com.android.server.storage.StorageUserConnection.this.mUserId + "]");
                            handleConnection(iBinder);
                        }

                        @Override // android.content.ServiceConnection
                        public void onServiceDisconnected(android.content.ComponentName componentName) {
                            android.util.Slog.i(com.android.server.storage.StorageUserConnection.TAG, "Service: [" + componentName + "] disconnected. User [" + com.android.server.storage.StorageUserConnection.this.mUserId + "]");
                            handleDisconnection();
                        }

                        @Override // android.content.ServiceConnection
                        public void onBindingDied(android.content.ComponentName componentName) {
                            android.util.Slog.i(com.android.server.storage.StorageUserConnection.TAG, "Service: [" + componentName + "] died. User [" + com.android.server.storage.StorageUserConnection.this.mUserId + "]");
                            handleDisconnection();
                        }

                        @Override // android.content.ServiceConnection
                        public void onNullBinding(android.content.ComponentName componentName) {
                            android.util.Slog.wtf(com.android.server.storage.StorageUserConnection.TAG, "Service: [" + componentName + "] is null. User [" + com.android.server.storage.StorageUserConnection.this.mUserId + "]");
                        }

                        private void handleConnection(android.os.IBinder iBinder) {
                            synchronized (com.android.server.storage.StorageUserConnection.ActiveConnection.this.mLock) {
                                completableFuture.complete(android.service.storage.IExternalStorageService.Stub.asInterface(iBinder));
                            }
                        }

                        private void handleDisconnection() {
                            com.android.server.storage.StorageUserConnection.ActiveConnection.this.close();
                            com.android.server.storage.StorageUserConnection.this.resetUserSessions();
                        }
                    };
                    android.util.Slog.i(com.android.server.storage.StorageUserConnection.TAG, "Binding to the ExternalStorageService for user " + com.android.server.storage.StorageUserConnection.this.mUserId);
                    if (com.android.server.storage.StorageUserConnection.this.mContext.bindServiceAsUser(new android.content.Intent().setComponent(externalStorageServiceComponentName), this.mServiceConnection, 65, com.android.server.storage.StorageUserConnection.this.mHandlerThread.getThreadHandler(), android.os.UserHandle.of(com.android.server.storage.StorageUserConnection.this.mUserId))) {
                        android.util.Slog.i(com.android.server.storage.StorageUserConnection.TAG, "Bound to the ExternalStorageService for user " + com.android.server.storage.StorageUserConnection.this.mUserId);
                        this.mRemoteFuture = completableFuture;
                        return completableFuture;
                    }
                    throw new com.android.server.storage.StorageSessionController.ExternalStorageServiceException("Failed to bind to the ExternalStorageService for user " + com.android.server.storage.StorageUserConnection.this.mUserId);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Session {
        public final java.lang.String lowerPath;
        public final java.lang.String sessionId;
        public final java.lang.String upperPath;

        Session(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.sessionId = str;
            this.upperPath = str2;
            this.lowerPath = str3;
        }

        public java.lang.String toString() {
            return "[SessionId: " + this.sessionId + ". UpperPath: " + this.upperPath + ". LowerPath: " + this.lowerPath + "]";
        }
    }
}
