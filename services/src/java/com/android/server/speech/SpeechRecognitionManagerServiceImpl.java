package com.android.server.speech;

/* loaded from: classes2.dex */
final class SpeechRecognitionManagerServiceImpl extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.speech.SpeechRecognitionManagerServiceImpl, com.android.server.speech.SpeechRecognitionManagerService> {
    private static final int MAX_CONCURRENT_CONNECTIONS_BY_CLIENT = 10;
    private static final java.lang.String TAG = com.android.server.speech.SpeechRecognitionManagerServiceImpl.class.getSimpleName();
    private final java.lang.Object mLock;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.Integer, java.util.Set<com.android.server.speech.RemoteSpeechRecognitionService>> mRemoteServicesByUid;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mSessionCountByUid;

    SpeechRecognitionManagerServiceImpl(@android.annotation.NonNull com.android.server.speech.SpeechRecognitionManagerService speechRecognitionManagerService, @android.annotation.NonNull java.lang.Object obj, int i) {
        super(speechRecognitionManagerService, obj, i);
        this.mLock = new java.lang.Object();
        this.mRemoteServicesByUid = new java.util.HashMap();
        this.mSessionCountByUid = new android.util.SparseIntArray();
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            return android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get service for " + componentName);
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean updateLocked(boolean z) {
        return super.updateLocked(z);
    }

    void createSessionLocked(android.content.ComponentName componentName, final android.os.IBinder iBinder, boolean z, final android.speech.IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) {
        if (((com.android.server.speech.SpeechRecognitionManagerService) this.mMaster).debug) {
            android.util.Slog.i(TAG, java.lang.String.format("#createSessionLocked, component=%s, onDevice=%s", componentName, java.lang.Boolean.valueOf(z)));
        }
        if (z) {
            componentName = getOnDeviceComponentNameLocked();
        }
        if (!z && android.os.Process.isIsolated(android.os.Binder.getCallingUid())) {
            android.util.Slog.w(TAG, "Isolated process can only start on device speech recognizer.");
            tryRespondWithError(iRecognitionServiceManagerCallback, 5);
            return;
        }
        if (componentName == null) {
            if (((com.android.server.speech.SpeechRecognitionManagerService) this.mMaster).debug) {
                android.util.Slog.i(TAG, "Service component is undefined, responding with error.");
            }
            tryRespondWithError(iRecognitionServiceManagerCallback, 5);
            return;
        }
        final int callingUid = android.os.Binder.getCallingUid();
        final com.android.server.speech.RemoteSpeechRecognitionService createService = createService(callingUid, componentName);
        if (createService == null) {
            tryRespondWithError(iRecognitionServiceManagerCallback, 10);
            return;
        }
        final android.os.IBinder.DeathRecipient deathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.speech.SpeechRecognitionManagerServiceImpl$$ExternalSyntheticLambda2
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                com.android.server.speech.SpeechRecognitionManagerServiceImpl.this.lambda$createSessionLocked$0(iBinder, callingUid, createService);
            }
        };
        try {
            iBinder.linkToDeath(deathRecipient, 0);
            createService.connect().thenAccept(new java.util.function.Consumer() { // from class: com.android.server.speech.SpeechRecognitionManagerServiceImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.speech.SpeechRecognitionManagerServiceImpl.this.lambda$createSessionLocked$1(iRecognitionServiceManagerCallback, createService, iBinder, callingUid, deathRecipient, (android.speech.IRecognitionService) obj);
                }
            });
        } catch (android.os.RemoteException e) {
            handleClientDeath(iBinder, callingUid, createService, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSessionLocked$0(android.os.IBinder iBinder, int i, com.android.server.speech.RemoteSpeechRecognitionService remoteSpeechRecognitionService) {
        handleClientDeath(iBinder, i, remoteSpeechRecognitionService, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSessionLocked$1(android.speech.IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback, final com.android.server.speech.RemoteSpeechRecognitionService remoteSpeechRecognitionService, final android.os.IBinder iBinder, final int i, final android.os.IBinder.DeathRecipient deathRecipient, android.speech.IRecognitionService iRecognitionService) {
        if (iRecognitionService != null) {
            try {
                iRecognitionServiceManagerCallback.onSuccess(new android.speech.IRecognitionService.Stub() { // from class: com.android.server.speech.SpeechRecognitionManagerServiceImpl.1
                    public void startListening(android.content.Intent intent, android.speech.IRecognitionListener iRecognitionListener, @android.annotation.NonNull android.content.AttributionSource attributionSource) throws android.os.RemoteException {
                        attributionSource.enforceCallingUid();
                        if (!attributionSource.isTrusted(((com.android.server.speech.SpeechRecognitionManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.speech.SpeechRecognitionManagerServiceImpl.this).mMaster).getContext())) {
                            attributionSource = ((android.permission.PermissionManager) ((com.android.server.speech.SpeechRecognitionManagerService) ((com.android.server.infra.AbstractPerUserSystemService) com.android.server.speech.SpeechRecognitionManagerServiceImpl.this).mMaster).getContext().getSystemService(android.permission.PermissionManager.class)).registerAttributionSource(attributionSource);
                        }
                        remoteSpeechRecognitionService.startListening(intent, iRecognitionListener, attributionSource);
                        remoteSpeechRecognitionService.associateClientWithActiveListener(iBinder, iRecognitionListener);
                    }

                    public void stopListening(android.speech.IRecognitionListener iRecognitionListener) throws android.os.RemoteException {
                        remoteSpeechRecognitionService.stopListening(iRecognitionListener);
                    }

                    public void cancel(android.speech.IRecognitionListener iRecognitionListener, boolean z) throws android.os.RemoteException {
                        remoteSpeechRecognitionService.cancel(iRecognitionListener, z);
                        if (z) {
                            com.android.server.speech.SpeechRecognitionManagerServiceImpl.this.handleClientDeath(iBinder, i, remoteSpeechRecognitionService, false);
                            iBinder.unlinkToDeath(deathRecipient, 0);
                        }
                    }

                    public void checkRecognitionSupport(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IRecognitionSupportCallback iRecognitionSupportCallback) {
                        remoteSpeechRecognitionService.checkRecognitionSupport(intent, attributionSource, iRecognitionSupportCallback);
                    }

                    public void triggerModelDownload(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IModelDownloadListener iModelDownloadListener) {
                        remoteSpeechRecognitionService.triggerModelDownload(intent, attributionSource, iModelDownloadListener);
                    }
                });
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error creating a speech recognition session", e);
                tryRespondWithError(iRecognitionServiceManagerCallback, 5);
                return;
            }
        }
        tryRespondWithError(iRecognitionServiceManagerCallback, 5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleClientDeath(android.os.IBinder iBinder, int i, com.android.server.speech.RemoteSpeechRecognitionService remoteSpeechRecognitionService, boolean z) {
        if (z) {
            remoteSpeechRecognitionService.shutdown(iBinder);
        }
        synchronized (this.mLock) {
            try {
                decrementSessionCountForUidLocked(i);
                if (!remoteSpeechRecognitionService.hasActiveSessions()) {
                    removeService(i, remoteSpeechRecognitionService);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.content.ComponentName getOnDeviceComponentNameLocked() {
        java.lang.String componentNameLocked = getComponentNameLocked();
        if (((com.android.server.speech.SpeechRecognitionManagerService) this.mMaster).debug) {
            android.util.Slog.i(TAG, "Resolved component name: " + componentNameLocked);
        }
        if (componentNameLocked == null) {
            if (((com.android.server.speech.SpeechRecognitionManagerService) this.mMaster).verbose) {
                android.util.Slog.v(TAG, "ensureRemoteServiceLocked(): no service component name.");
                return null;
            }
            return null;
        }
        return android.content.ComponentName.unflattenFromString(componentNameLocked);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int getSessionCountByUidLocked(int i) {
        return this.mSessionCountByUid.get(i, 0);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void incrementSessionCountForUidLocked(int i) {
        this.mSessionCountByUid.put(i, this.mSessionCountByUid.get(i, 0) + 1);
        android.util.Log.i(TAG, "Client " + i + " has opened " + this.mSessionCountByUid.get(i, 0) + " sessions");
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void decrementSessionCountForUidLocked(int i) {
        int i2 = this.mSessionCountByUid.get(i, 1) - 1;
        if (i2 > 0) {
            this.mSessionCountByUid.put(i, i2);
        } else {
            this.mSessionCountByUid.delete(i);
        }
    }

    private com.android.server.speech.RemoteSpeechRecognitionService createService(int i, final android.content.ComponentName componentName) {
        boolean checkPrivilege;
        synchronized (this.mLock) {
            try {
                java.util.Set<com.android.server.speech.RemoteSpeechRecognitionService> set = this.mRemoteServicesByUid.get(java.lang.Integer.valueOf(i));
                if (set != null && set.size() >= 10) {
                    android.util.Slog.w(TAG, "Number of remote services exceeded for uid: " + i);
                    com.android.modules.expresslog.Counter.logIncrementWithUid("speech_recognition.value_exceed_service_connections_count", i);
                    return null;
                }
                if (getSessionCountByUidLocked(i) == 10) {
                    android.util.Slog.w(TAG, "Number of sessions exceeded for uid: " + i);
                    com.android.modules.expresslog.Counter.logIncrementWithUid("speech_recognition.value_exceed_session_count", i);
                }
                if (set != null) {
                    java.util.Optional<com.android.server.speech.RemoteSpeechRecognitionService> findFirst = set.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.speech.SpeechRecognitionManagerServiceImpl$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$createService$2;
                            lambda$createService$2 = com.android.server.speech.SpeechRecognitionManagerServiceImpl.lambda$createService$2(componentName, (com.android.server.speech.RemoteSpeechRecognitionService) obj);
                            return lambda$createService$2;
                        }
                    }).findFirst();
                    if (findFirst.isPresent()) {
                        if (((com.android.server.speech.SpeechRecognitionManagerService) this.mMaster).debug) {
                            android.util.Slog.i(TAG, "Reused existing connection to " + componentName);
                        }
                        incrementSessionCountForUidLocked(i);
                        return findFirst.get();
                    }
                }
                if (componentName != null && !componentMapsToRecognitionService(componentName)) {
                    return null;
                }
                if (componentName == null) {
                    checkPrivilege = false;
                } else {
                    checkPrivilege = checkPrivilege(componentName);
                }
                com.android.server.speech.RemoteSpeechRecognitionService remoteSpeechRecognitionService = new com.android.server.speech.RemoteSpeechRecognitionService(getContext(), componentName, getUserId(), i, checkPrivilege);
                this.mRemoteServicesByUid.computeIfAbsent(java.lang.Integer.valueOf(i), new java.util.function.Function() { // from class: com.android.server.speech.SpeechRecognitionManagerServiceImpl$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        java.util.Set lambda$createService$3;
                        lambda$createService$3 = com.android.server.speech.SpeechRecognitionManagerServiceImpl.lambda$createService$3((java.lang.Integer) obj);
                        return lambda$createService$3;
                    }
                }).add(remoteSpeechRecognitionService);
                if (((com.android.server.speech.SpeechRecognitionManagerService) this.mMaster).debug) {
                    android.util.Slog.i(TAG, "Creating a new connection to " + componentName);
                }
                incrementSessionCountForUidLocked(i);
                return remoteSpeechRecognitionService;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$createService$2(android.content.ComponentName componentName, com.android.server.speech.RemoteSpeechRecognitionService remoteSpeechRecognitionService) {
        return remoteSpeechRecognitionService.getServiceComponentName().equals(componentName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.Set lambda$createService$3(java.lang.Integer num) {
        return new java.util.HashSet();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean checkPrivilege(@android.annotation.NonNull android.content.ComponentName componentName) {
        return componentName.equals(getDefaultRecognitionServiceComponent()) || componentName.equals(getOnDeviceComponentNameLocked()) || isPreinstalledApp(componentName);
    }

    private boolean isPreinstalledApp(@android.annotation.NonNull android.content.ComponentName componentName) {
        android.content.pm.PackageManager packageManager = getContext().getPackageManager();
        if (packageManager == null) {
            return false;
        }
        try {
            return (packageManager.getApplicationInfoAsUser(componentName.getPackageName(), 1048576, getUserId()).flags & 1) != 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @android.annotation.Nullable
    private android.content.ComponentName getDefaultRecognitionServiceComponent() {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(getContext().getContentResolver(), "voice_recognition_service", getUserId());
        if (stringForUser == null) {
            return null;
        }
        return android.content.ComponentName.unflattenFromString(stringForUser);
    }

    private boolean componentMapsToRecognitionService(@android.annotation.NonNull android.content.ComponentName componentName) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser = getContext().getPackageManager().queryIntentServicesAsUser(new android.content.Intent("android.speech.RecognitionService"), 0, getUserId());
            if (queryIntentServicesAsUser == null) {
                return false;
            }
            for (android.content.pm.ResolveInfo resolveInfo : queryIntentServicesAsUser) {
                if (resolveInfo.serviceInfo != null && componentName.equals(resolveInfo.serviceInfo.getComponentName())) {
                    return true;
                }
            }
            android.util.Slog.w(TAG, "serviceComponent is not RecognitionService: " + componentName);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void removeService(int i, com.android.server.speech.RemoteSpeechRecognitionService remoteSpeechRecognitionService) {
        synchronized (this.mLock) {
            try {
                java.util.Set<com.android.server.speech.RemoteSpeechRecognitionService> set = this.mRemoteServicesByUid.get(java.lang.Integer.valueOf(i));
                if (set != null) {
                    set.remove(remoteSpeechRecognitionService);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void tryRespondWithError(android.speech.IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback, int i) {
        try {
            iRecognitionServiceManagerCallback.onError(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to respond with error");
        }
    }
}
