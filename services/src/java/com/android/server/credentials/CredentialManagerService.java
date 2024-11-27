package com.android.server.credentials;

/* loaded from: classes.dex */
public final class CredentialManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.credentials.CredentialManagerService, com.android.server.credentials.CredentialManagerServiceImpl> {
    public static final java.lang.String AUTOFILL_PLACEHOLDER_VALUE = "credential-provider";
    private static final java.lang.String DEVICE_CONFIG_ENABLE_CREDENTIAL_DESC_API = "enable_credential_description_api";
    private static final java.lang.String DEVICE_CONFIG_ENABLE_CREDENTIAL_MANAGER = "enable_credential_manager";
    private static final java.lang.String PERMISSION_DENIED_ERROR = "permission_denied";
    private static final java.lang.String PERMISSION_DENIED_WRITE_SECURE_SETTINGS_ERROR = "Caller is missing WRITE_SECURE_SETTINGS permission";
    private static final java.lang.String TAG = "CredManSysService";
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.Map<android.os.IBinder, com.android.server.credentials.RequestSession>> mRequestSessions;
    private final com.android.server.credentials.CredentialManagerService.SessionManager mSessionManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.List<com.android.server.credentials.CredentialManagerServiceImpl>> mSystemServicesCacheList;

    public CredentialManagerService(@android.annotation.NonNull android.content.Context context) {
        super(context, new com.android.server.infra.SecureSettingsServiceNameResolver(context, "credential_service", true), null, 4);
        this.mSystemServicesCacheList = new android.util.SparseArray<>();
        this.mRequestSessions = new android.util.SparseArray<>();
        this.mSessionManager = new com.android.server.credentials.CredentialManagerService.SessionManager();
        this.mContext = context;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<com.android.server.credentials.CredentialManagerServiceImpl> constructSystemServiceListLocked(final int i) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        android.service.credentials.CredentialProviderInfoFactory.getAvailableSystemServices(this.mContext, i, false, new java.util.HashSet()).forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.CredentialManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.credentials.CredentialManagerService.this.lambda$constructSystemServiceListLocked$0(arrayList, i, (android.credentials.CredentialProviderInfo) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$constructSystemServiceListLocked$0(java.util.List list, int i, android.credentials.CredentialProviderInfo credentialProviderInfo) {
        list.add(new com.android.server.credentials.CredentialManagerServiceImpl(this, this.mLock, i, credentialProviderInfo));
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected java.lang.String getServiceSettingsProperty() {
        return "credential_service";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.credentials.CredentialManagerServiceImpl newServiceLocked(int i, boolean z) {
        android.util.Slog.w(TAG, "Should not be here - CredentialManagerService is configured to use multiple services");
        return null;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("credential", new com.android.server.credentials.CredentialManagerService.CredentialManagerServiceStub());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected java.util.List<com.android.server.credentials.CredentialManagerServiceImpl> newServiceListLocked(int i, boolean z, java.lang.String[] strArr) {
        getOrConstructSystemServiceListLock(i);
        if (strArr == null || strArr.length == 0) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(strArr.length);
        for (java.lang.String str : strArr) {
            if (!android.text.TextUtils.isEmpty(str)) {
                try {
                    arrayList.add(new com.android.server.credentials.CredentialManagerServiceImpl(this, this.mLock, i, str));
                } catch (android.content.pm.PackageManager.NameNotFoundException | java.lang.SecurityException e) {
                    android.util.Slog.e(TAG, "Unable to add serviceInfo : ", e);
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void handlePackageRemovedMultiModeLocked(java.lang.String str, int i) {
        updateProvidersWhenPackageRemoved(new com.android.server.credentials.CredentialManagerService.SettingsWrapper(this.mContext), str);
        java.util.List<com.android.server.credentials.CredentialManagerServiceImpl> peekServiceListForUserLocked = peekServiceListForUserLocked(i);
        if (peekServiceListForUserLocked == null) {
            return;
        }
        java.util.ArrayList<com.android.server.credentials.CredentialManagerServiceImpl> arrayList = new java.util.ArrayList();
        for (com.android.server.credentials.CredentialManagerServiceImpl credentialManagerServiceImpl : peekServiceListForUserLocked) {
            if (credentialManagerServiceImpl != null && str.equals(credentialManagerServiceImpl.getCredentialProviderInfo().getServiceInfo().getComponentName().getPackageName())) {
                arrayList.add(credentialManagerServiceImpl);
            }
        }
        for (com.android.server.credentials.CredentialManagerServiceImpl credentialManagerServiceImpl2 : arrayList) {
            removeServiceFromCache(credentialManagerServiceImpl2, i);
            removeServiceFromSystemServicesCache(credentialManagerServiceImpl2, i);
            com.android.server.credentials.CredentialDescriptionRegistry.forUser(i).evictProviderWithPackageName(credentialManagerServiceImpl2.getServicePackageName());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removeServiceFromSystemServicesCache(com.android.server.credentials.CredentialManagerServiceImpl credentialManagerServiceImpl, int i) {
        if (this.mSystemServicesCacheList.get(i) != null) {
            this.mSystemServicesCacheList.get(i).remove(credentialManagerServiceImpl);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<com.android.server.credentials.CredentialManagerServiceImpl> getOrConstructSystemServiceListLock(int i) {
        java.util.List<com.android.server.credentials.CredentialManagerServiceImpl> list = this.mSystemServicesCacheList.get(i);
        if (list == null || list.size() == 0) {
            java.util.List<com.android.server.credentials.CredentialManagerServiceImpl> constructSystemServiceListLocked = constructSystemServiceListLocked(i);
            this.mSystemServicesCacheList.put(i, constructSystemServiceListLocked);
            return constructSystemServiceListLocked;
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasWriteSecureSettingsPermission() {
        return hasPermission("android.permission.WRITE_SECURE_SETTINGS");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyGetProvidersPermission() throws java.lang.SecurityException {
        if (hasPermission("android.permission.QUERY_ALL_PACKAGES") || hasPermission("android.permission.LIST_ENABLED_CREDENTIAL_PROVIDERS")) {
        } else {
            throw new java.lang.SecurityException("Caller is missing permission: QUERY_ALL_PACKAGES or LIST_ENABLED_CREDENTIAL_PROVIDERS");
        }
    }

    private boolean hasPermission(java.lang.String str) {
        boolean z = this.mContext.checkCallingOrSelfPermission(str) == 0;
        if (!z) {
            android.util.Slog.e(TAG, "Caller does not have permission: " + str);
        }
        return z;
    }

    private void runForUser(@android.annotation.NonNull java.util.function.Consumer<com.android.server.credentials.CredentialManagerServiceImpl> consumer) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                try {
                    java.util.Iterator<com.android.server.credentials.CredentialManagerServiceImpl> it = getCredentialProviderServicesLocked(callingUserId).iterator();
                    while (it.hasNext()) {
                        consumer.accept(it.next());
                    }
                } finally {
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.Set<android.content.ComponentName> getPrimaryProvidersForUserId(android.content.Context context, int i) {
        java.lang.String[] readServiceNameList = new com.android.server.infra.SecureSettingsServiceNameResolver(context, "credential_service_primary", true).readServiceNameList(android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, "getPrimaryProvidersForUserId", null));
        if (readServiceNameList == null) {
            return new java.util.HashSet();
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.lang.String str : readServiceNameList) {
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
            if (unflattenFromString == null) {
                android.util.Slog.w(TAG, "Primary provider component name unflatten from string error: " + str);
            } else {
                hashSet.add(unflattenFromString);
            }
        }
        return hashSet;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<com.android.server.credentials.CredentialManagerServiceImpl> getCredentialProviderServicesLocked(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.List<com.android.server.credentials.CredentialManagerServiceImpl> serviceListForUserLocked = getServiceListForUserLocked(i);
        if (serviceListForUserLocked != null && !serviceListForUserLocked.isEmpty()) {
            arrayList.addAll(serviceListForUserLocked);
        }
        arrayList.addAll(getOrConstructSystemServiceListLock(i));
        return arrayList;
    }

    public static boolean isCredentialDescriptionApiEnabled() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.DeviceConfig.getBoolean("credential_manager", DEVICE_CONFIG_ENABLE_CREDENTIAL_DESC_API, false);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<com.android.server.credentials.ProviderSession> initiateProviderSessionsWithActiveContainers(com.android.server.credentials.GetRequestSession getRequestSession, java.util.Set<android.util.Pair<android.credentials.CredentialOption, com.android.server.credentials.CredentialDescriptionRegistry.FilterResult>> set) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.util.Pair<android.credentials.CredentialOption, com.android.server.credentials.CredentialDescriptionRegistry.FilterResult> pair : set) {
            com.android.server.credentials.ProviderRegistryGetSession createNewSession = com.android.server.credentials.ProviderRegistryGetSession.createNewSession(this.mContext, android.os.UserHandle.getCallingUserId(), getRequestSession, getRequestSession.mClientAppInfo, ((com.android.server.credentials.CredentialDescriptionRegistry.FilterResult) pair.second).mPackageName, (android.credentials.CredentialOption) pair.first);
            arrayList.add(createNewSession);
            getRequestSession.addProviderSession(createNewSession.getComponentName(), createNewSession);
        }
        return arrayList;
    }

    private java.util.List<com.android.server.credentials.ProviderSession> initiateProviderSessionsWithActiveContainers(com.android.server.credentials.PrepareGetRequestSession prepareGetRequestSession, java.util.Set<android.util.Pair<android.credentials.CredentialOption, com.android.server.credentials.CredentialDescriptionRegistry.FilterResult>> set) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.util.Pair<android.credentials.CredentialOption, com.android.server.credentials.CredentialDescriptionRegistry.FilterResult> pair : set) {
            com.android.server.credentials.ProviderRegistryGetSession createNewSession = com.android.server.credentials.ProviderRegistryGetSession.createNewSession(this.mContext, android.os.UserHandle.getCallingUserId(), prepareGetRequestSession, prepareGetRequestSession.mClientAppInfo, ((com.android.server.credentials.CredentialDescriptionRegistry.FilterResult) pair.second).mPackageName, (android.credentials.CredentialOption) pair.first);
            arrayList.add(createNewSession);
            prepareGetRequestSession.addProviderSession(createNewSession.getComponentName(), createNewSession);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.util.Set<android.util.Pair<android.credentials.CredentialOption, com.android.server.credentials.CredentialDescriptionRegistry.FilterResult>> getFilteredResultFromRegistry(java.util.List<android.credentials.CredentialOption> list) {
        java.util.Set<com.android.server.credentials.CredentialDescriptionRegistry.FilterResult> matchingProviders = com.android.server.credentials.CredentialDescriptionRegistry.forUser(android.os.UserHandle.getCallingUserId()).getMatchingProviders((java.util.Set) list.stream().map(new java.util.function.Function() { // from class: com.android.server.credentials.CredentialManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.HashSet lambda$getFilteredResultFromRegistry$1;
                lambda$getFilteredResultFromRegistry$1 = com.android.server.credentials.CredentialManagerService.lambda$getFilteredResultFromRegistry$1((android.credentials.CredentialOption) obj);
                return lambda$getFilteredResultFromRegistry$1;
            }
        }).collect(java.util.stream.Collectors.toSet()));
        java.util.HashSet hashSet = new java.util.HashSet();
        for (com.android.server.credentials.CredentialDescriptionRegistry.FilterResult filterResult : matchingProviders) {
            for (android.credentials.CredentialOption credentialOption : list) {
                if (com.android.server.credentials.CredentialDescriptionRegistry.checkForMatch(filterResult.mElementKeys, new java.util.HashSet(credentialOption.getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS")))) {
                    hashSet.add(new android.util.Pair(credentialOption, filterResult));
                }
            }
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.HashSet lambda$getFilteredResultFromRegistry$1(android.credentials.CredentialOption credentialOption) {
        return new java.util.HashSet(credentialOption.getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<com.android.server.credentials.ProviderSession> initiateProviderSessions(final com.android.server.credentials.RequestSession requestSession, final java.util.List<java.lang.String> list) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        runForUser(new java.util.function.Consumer() { // from class: com.android.server.credentials.CredentialManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.credentials.CredentialManagerService.this.lambda$initiateProviderSessions$2(requestSession, list, arrayList, (com.android.server.credentials.CredentialManagerServiceImpl) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initiateProviderSessions$2(com.android.server.credentials.RequestSession requestSession, java.util.List list, java.util.List list2, com.android.server.credentials.CredentialManagerServiceImpl credentialManagerServiceImpl) {
        synchronized (this.mLock) {
            try {
                com.android.server.credentials.ProviderSession initiateProviderSessionForRequestLocked = credentialManagerServiceImpl.initiateProviderSessionForRequestLocked(requestSession, list);
                if (initiateProviderSessionForRequestLocked != null) {
                    list2.add(initiateProviderSessionForRequestLocked);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService, com.android.server.SystemService
    @com.android.internal.annotations.GuardedBy({"CredentialDescriptionRegistry.sLock"})
    public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        super.onUserStopped(targetUser);
        com.android.server.credentials.CredentialDescriptionRegistry.clearUserSession(targetUser.getUserIdentifier());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.service.credentials.CallingAppInfo constructCallingAppInfo(java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2) {
        try {
            return new android.service.credentials.CallingAppInfo(str, getContext().getPackageManager().getPackageInfoAsUser(str, android.content.pm.PackageManager.PackageInfoFlags.of(134217728L), i).signingInfo, str2);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Issue while retrieving signatureInfo : ", e);
            return new android.service.credentials.CallingAppInfo(str, null, str2);
        }
    }

    final class CredentialManagerServiceStub extends android.credentials.ICredentialManager.Stub {
        CredentialManagerServiceStub() {
        }

        public android.os.ICancellationSignal getCandidateCredentials(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback, android.os.IBinder iBinder, java.lang.String str) {
            android.util.Slog.i(com.android.server.credentials.CredentialManagerService.TAG, "starting getCandidateCredentials with callingPackage: " + str);
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            int callingUserId = android.os.UserHandle.getCallingUserId();
            com.android.server.credentials.GetCandidateRequestSession getCandidateRequestSession = new com.android.server.credentials.GetCandidateRequestSession(com.android.server.credentials.CredentialManagerService.this.getContext(), com.android.server.credentials.CredentialManagerService.this.mSessionManager, ((com.android.server.infra.AbstractMasterSystemService) com.android.server.credentials.CredentialManagerService.this).mLock, callingUserId, android.os.Binder.getCallingUid(), iGetCandidateCredentialsCallback, getCredentialRequest, com.android.server.credentials.CredentialManagerService.this.constructCallingAppInfo(str, callingUserId, getCredentialRequest.getOrigin()), getEnabledProvidersForUser(callingUserId), android.os.CancellationSignal.fromTransport(createTransport), iBinder);
            com.android.server.credentials.CredentialManagerService.this.addSessionLocked(callingUserId, getCandidateRequestSession);
            java.util.List<com.android.server.credentials.ProviderSession> initiateProviderSessions = com.android.server.credentials.CredentialManagerService.this.initiateProviderSessions(getCandidateRequestSession, (java.util.List) getCredentialRequest.getCredentialOptions().stream().map(new com.android.server.credentials.CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda3()).collect(java.util.stream.Collectors.toList()));
            finalizeAndEmitInitialPhaseMetric(getCandidateRequestSession);
            if (initiateProviderSessions.isEmpty()) {
                try {
                    iGetCandidateCredentialsCallback.onError("android.credentials.GetCandidateCredentialsException.TYPE_NO_CREDENTIAL", "No credentials available on this device.");
                } catch (android.os.RemoteException e) {
                    android.util.Slog.i(com.android.server.credentials.CredentialManagerService.TAG, "Issue invoking onError on IGetCredentialCallback callback: " + e.getMessage());
                }
            }
            invokeProviderSessions(initiateProviderSessions);
            return createTransport;
        }

        public android.os.ICancellationSignal executeGetCredential(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IGetCredentialCallback iGetCredentialCallback, java.lang.String str) {
            long nanoTime = java.lang.System.nanoTime();
            android.util.Slog.i(com.android.server.credentials.CredentialManagerService.TAG, "starting executeGetCredential with callingPackage: " + str);
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            int callingUserId = android.os.UserHandle.getCallingUserId();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.credentials.CredentialManagerService.this.enforceCallingPackage(str, callingUid);
            com.android.server.credentials.CredentialManagerService.this.validateGetCredentialRequest(getCredentialRequest);
            com.android.server.credentials.GetRequestSession getRequestSession = new com.android.server.credentials.GetRequestSession(com.android.server.credentials.CredentialManagerService.this.getContext(), com.android.server.credentials.CredentialManagerService.this.mSessionManager, ((com.android.server.infra.AbstractMasterSystemService) com.android.server.credentials.CredentialManagerService.this).mLock, callingUserId, callingUid, iGetCredentialCallback, getCredentialRequest, com.android.server.credentials.CredentialManagerService.this.constructCallingAppInfo(str, callingUserId, getCredentialRequest.getOrigin()), getEnabledProvidersForUser(callingUserId), android.os.CancellationSignal.fromTransport(createTransport), nanoTime);
            com.android.server.credentials.CredentialManagerService.this.addSessionLocked(callingUserId, getRequestSession);
            java.util.List<com.android.server.credentials.ProviderSession> prepareProviderSessions = prepareProviderSessions(getCredentialRequest, getRequestSession);
            if (prepareProviderSessions.isEmpty()) {
                try {
                    iGetCredentialCallback.onError("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", "No credentials available on this device.");
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.credentials.CredentialManagerService.TAG, "Issue invoking onError on IGetCredentialCallback callback: " + e.getMessage());
                }
            }
            invokeProviderSessions(prepareProviderSessions);
            return createTransport;
        }

        public android.os.ICancellationSignal executePrepareGetCredential(android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.IPrepareGetCredentialCallback iPrepareGetCredentialCallback, android.credentials.IGetCredentialCallback iGetCredentialCallback, java.lang.String str) {
            long nanoTime = java.lang.System.nanoTime();
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            if (getCredentialRequest.getOrigin() != null) {
                com.android.server.credentials.CredentialManagerService.this.mContext.enforceCallingPermission("android.permission.CREDENTIAL_MANAGER_SET_ORIGIN", null);
            }
            com.android.server.credentials.CredentialManagerService.this.enforcePermissionForAllowedProviders(getCredentialRequest);
            int callingUserId = android.os.UserHandle.getCallingUserId();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.credentials.CredentialManagerService.this.enforceCallingPackage(str, callingUid);
            java.util.List<com.android.server.credentials.ProviderSession> prepareProviderSessions = prepareProviderSessions(getCredentialRequest, new com.android.server.credentials.PrepareGetRequestSession(com.android.server.credentials.CredentialManagerService.this.getContext(), com.android.server.credentials.CredentialManagerService.this.mSessionManager, ((com.android.server.infra.AbstractMasterSystemService) com.android.server.credentials.CredentialManagerService.this).mLock, callingUserId, callingUid, iGetCredentialCallback, getCredentialRequest, com.android.server.credentials.CredentialManagerService.this.constructCallingAppInfo(str, callingUserId, getCredentialRequest.getOrigin()), getEnabledProvidersForUser(callingUserId), android.os.CancellationSignal.fromTransport(createTransport), nanoTime, iPrepareGetCredentialCallback));
            if (prepareProviderSessions.isEmpty()) {
                try {
                    iPrepareGetCredentialCallback.onResponse(new android.credentials.PrepareGetCredentialResponseInternal(android.service.credentials.PermissionUtils.hasPermission(com.android.server.credentials.CredentialManagerService.this.mContext, str, "android.permission.CREDENTIAL_MANAGER_QUERY_CANDIDATE_CREDENTIALS"), (java.util.Set) null, false, false, (android.app.PendingIntent) null));
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.credentials.CredentialManagerService.TAG, "Issue invoking onError on IGetCredentialCallback callback: " + e.getMessage());
                }
            }
            invokeProviderSessions(prepareProviderSessions);
            return createTransport;
        }

        private java.util.List<com.android.server.credentials.ProviderSession> prepareProviderSessions(android.credentials.GetCredentialRequest getCredentialRequest, com.android.server.credentials.GetRequestSession getRequestSession) {
            java.util.List<com.android.server.credentials.ProviderSession> initiateProviderSessions;
            if (com.android.server.credentials.CredentialManagerService.isCredentialDescriptionApiEnabled()) {
                java.util.List<android.credentials.CredentialOption> list = getCredentialRequest.getCredentialOptions().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.credentials.CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$prepareProviderSessions$0;
                        lambda$prepareProviderSessions$0 = com.android.server.credentials.CredentialManagerService.CredentialManagerServiceStub.lambda$prepareProviderSessions$0((android.credentials.CredentialOption) obj);
                        return lambda$prepareProviderSessions$0;
                    }
                }).toList();
                java.util.List<android.credentials.CredentialOption> list2 = getCredentialRequest.getCredentialOptions().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.credentials.CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$prepareProviderSessions$1;
                        lambda$prepareProviderSessions$1 = com.android.server.credentials.CredentialManagerService.CredentialManagerServiceStub.lambda$prepareProviderSessions$1((android.credentials.CredentialOption) obj);
                        return lambda$prepareProviderSessions$1;
                    }
                }).toList();
                java.util.List initiateProviderSessionsWithActiveContainers = com.android.server.credentials.CredentialManagerService.this.initiateProviderSessionsWithActiveContainers(getRequestSession, (java.util.Set<android.util.Pair<android.credentials.CredentialOption, com.android.server.credentials.CredentialDescriptionRegistry.FilterResult>>) com.android.server.credentials.CredentialManagerService.this.getFilteredResultFromRegistry(list));
                java.util.List initiateProviderSessions2 = com.android.server.credentials.CredentialManagerService.this.initiateProviderSessions(getRequestSession, (java.util.List) list2.stream().map(new com.android.server.credentials.CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda3()).collect(java.util.stream.Collectors.toList()));
                java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
                linkedHashSet.addAll(initiateProviderSessions2);
                linkedHashSet.addAll(initiateProviderSessionsWithActiveContainers);
                initiateProviderSessions = new java.util.ArrayList<>(linkedHashSet);
            } else {
                initiateProviderSessions = com.android.server.credentials.CredentialManagerService.this.initiateProviderSessions(getRequestSession, (java.util.List) getCredentialRequest.getCredentialOptions().stream().map(new com.android.server.credentials.CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda3()).collect(java.util.stream.Collectors.toList()));
            }
            finalizeAndEmitInitialPhaseMetric(getRequestSession);
            return initiateProviderSessions;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$prepareProviderSessions$0(android.credentials.CredentialOption credentialOption) {
            return credentialOption.getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS") != null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$prepareProviderSessions$1(android.credentials.CredentialOption credentialOption) {
            return credentialOption.getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS") == null;
        }

        private void invokeProviderSessions(java.util.List<com.android.server.credentials.ProviderSession> list) {
            list.forEach(new com.android.server.credentials.CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda0());
        }

        public android.os.ICancellationSignal executeCreateCredential(android.credentials.CreateCredentialRequest createCredentialRequest, android.credentials.ICreateCredentialCallback iCreateCredentialCallback, java.lang.String str) {
            long nanoTime = java.lang.System.nanoTime();
            android.util.Slog.i(com.android.server.credentials.CredentialManagerService.TAG, "starting executeCreateCredential with callingPackage: " + str);
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            if (createCredentialRequest.getOrigin() != null) {
                com.android.server.credentials.CredentialManagerService.this.mContext.enforceCallingPermission("android.permission.CREDENTIAL_MANAGER_SET_ORIGIN", null);
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.credentials.CredentialManagerService.this.enforceCallingPackage(str, callingUid);
            com.android.server.credentials.CreateRequestSession createRequestSession = new com.android.server.credentials.CreateRequestSession(com.android.server.credentials.CredentialManagerService.this.getContext(), com.android.server.credentials.CredentialManagerService.this.mSessionManager, ((com.android.server.infra.AbstractMasterSystemService) com.android.server.credentials.CredentialManagerService.this).mLock, callingUserId, callingUid, createCredentialRequest, iCreateCredentialCallback, com.android.server.credentials.CredentialManagerService.this.constructCallingAppInfo(str, callingUserId, createCredentialRequest.getOrigin()), getEnabledProvidersForUser(callingUserId), com.android.server.credentials.CredentialManagerService.getPrimaryProvidersForUserId(com.android.server.credentials.CredentialManagerService.this.getContext(), callingUserId), android.os.CancellationSignal.fromTransport(createTransport), nanoTime);
            com.android.server.credentials.CredentialManagerService.this.addSessionLocked(callingUserId, createRequestSession);
            processCreateCredential(createCredentialRequest, iCreateCredentialCallback, createRequestSession);
            return createTransport;
        }

        private void processCreateCredential(android.credentials.CreateCredentialRequest createCredentialRequest, android.credentials.ICreateCredentialCallback iCreateCredentialCallback, com.android.server.credentials.CreateRequestSession createRequestSession) {
            java.util.List initiateProviderSessions = com.android.server.credentials.CredentialManagerService.this.initiateProviderSessions(createRequestSession, java.util.List.of(createCredentialRequest.getType()));
            if (initiateProviderSessions.isEmpty()) {
                try {
                    iCreateCredentialCallback.onError("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS", "No create options available.");
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.credentials.CredentialManagerService.TAG, "Issue invoking onError on ICreateCredentialCallback callback: ", e);
                }
            }
            finalizeAndEmitInitialPhaseMetric(createRequestSession);
            initiateProviderSessions.forEach(new com.android.server.credentials.CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda0());
        }

        private void finalizeAndEmitInitialPhaseMetric(com.android.server.credentials.GetCandidateRequestSession getCandidateRequestSession) {
            com.android.server.credentials.metrics.InitialPhaseMetric initialPhaseMetric = getCandidateRequestSession.mRequestSessionMetric.getInitialPhaseMetric();
            initialPhaseMetric.setAutofillSessionId(getCandidateRequestSession.getAutofillSessionId());
            initialPhaseMetric.setAutofillRequestId(getCandidateRequestSession.getAutofillRequestId());
            finalizeAndEmitInitialPhaseMetric((com.android.server.credentials.RequestSession) getCandidateRequestSession);
        }

        private void finalizeAndEmitInitialPhaseMetric(com.android.server.credentials.RequestSession requestSession) {
            try {
                com.android.server.credentials.metrics.InitialPhaseMetric initialPhaseMetric = requestSession.mRequestSessionMetric.getInitialPhaseMetric();
                initialPhaseMetric.setCredentialServiceBeginQueryTimeNanoseconds(java.lang.System.nanoTime());
                com.android.server.credentials.MetricUtilities.logApiCalledInitialPhase(initialPhaseMetric, requestSession.mRequestSessionMetric.returnIncrementSequence());
            } catch (java.lang.Exception e) {
                android.util.Slog.i(com.android.server.credentials.CredentialManagerService.TAG, "Unexpected error during metric logging: ", e);
            }
        }

        public void setEnabledProviders(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, int i, android.credentials.ISetEnabledProvidersCallback iSetEnabledProvidersCallback) {
            int callingUid = android.os.Binder.getCallingUid();
            if (!com.android.server.credentials.CredentialManagerService.this.hasWriteSecureSettingsPermission()) {
                try {
                    com.android.server.credentials.MetricUtilities.logApiCalledSimpleV2(com.android.server.credentials.metrics.ApiName.SET_ENABLED_PROVIDERS, com.android.server.credentials.metrics.ApiStatus.FAILURE, callingUid);
                    iSetEnabledProvidersCallback.onError(com.android.server.credentials.CredentialManagerService.PERMISSION_DENIED_ERROR, com.android.server.credentials.CredentialManagerService.PERMISSION_DENIED_WRITE_SECURE_SETTINGS_ERROR);
                    return;
                } catch (android.os.RemoteException e) {
                    com.android.server.credentials.MetricUtilities.logApiCalledSimpleV2(com.android.server.credentials.metrics.ApiName.SET_ENABLED_PROVIDERS, com.android.server.credentials.metrics.ApiStatus.FAILURE, callingUid);
                    android.util.Slog.e(com.android.server.credentials.CredentialManagerService.TAG, "Issue with invoking response: ", e);
                    return;
                }
            }
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, "setEnabledProviders", null);
            java.util.HashSet hashSet = new java.util.HashSet(list2);
            hashSet.addAll(list);
            boolean putStringForUser = android.provider.Settings.Secure.putStringForUser(com.android.server.credentials.CredentialManagerService.this.getContext().getContentResolver(), "credential_service", java.lang.String.join(":", hashSet), handleIncomingUser);
            boolean putStringForUser2 = android.provider.Settings.Secure.putStringForUser(com.android.server.credentials.CredentialManagerService.this.getContext().getContentResolver(), "credential_service_primary", java.lang.String.join(":", list), handleIncomingUser);
            if (!putStringForUser || !putStringForUser2) {
                android.util.Slog.e(com.android.server.credentials.CredentialManagerService.TAG, "Failed to store setting containing enabled or primary providers");
                try {
                    com.android.server.credentials.MetricUtilities.logApiCalledSimpleV2(com.android.server.credentials.metrics.ApiName.SET_ENABLED_PROVIDERS, com.android.server.credentials.metrics.ApiStatus.FAILURE, callingUid);
                    iSetEnabledProvidersCallback.onError("failed_setting_store", "Failed to store setting containing enabled or primary providers");
                } catch (android.os.RemoteException e2) {
                    com.android.server.credentials.MetricUtilities.logApiCalledSimpleV2(com.android.server.credentials.metrics.ApiName.SET_ENABLED_PROVIDERS, com.android.server.credentials.metrics.ApiStatus.FAILURE, callingUid);
                    android.util.Slog.e(com.android.server.credentials.CredentialManagerService.TAG, "Issue with invoking error response: ", e2);
                    return;
                }
            }
            try {
                com.android.server.credentials.MetricUtilities.logApiCalledSimpleV2(com.android.server.credentials.metrics.ApiName.SET_ENABLED_PROVIDERS, com.android.server.credentials.metrics.ApiStatus.SUCCESS, callingUid);
                iSetEnabledProvidersCallback.onResponse();
            } catch (android.os.RemoteException e3) {
                com.android.server.credentials.MetricUtilities.logApiCalledSimpleV2(com.android.server.credentials.metrics.ApiName.SET_ENABLED_PROVIDERS, com.android.server.credentials.metrics.ApiStatus.FAILURE, callingUid);
                android.util.Slog.e(com.android.server.credentials.CredentialManagerService.TAG, "Issue with invoking response: ", e3);
            }
        }

        public boolean isEnabledCredentialProviderService(android.content.ComponentName componentName, java.lang.String str) {
            android.util.Slog.i(com.android.server.credentials.CredentialManagerService.TAG, "isEnabledCredentialProviderService with componentName: " + componentName.flattenToString());
            int callingUserId = android.os.UserHandle.getCallingUserId();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.credentials.CredentialManagerService.this.enforceCallingPackage(str, callingUid);
            if (!componentName.getPackageName().equals(str)) {
                android.util.Slog.w(com.android.server.credentials.CredentialManagerService.TAG, "isEnabledCredentialProviderService component name does not match requested component");
                com.android.server.credentials.MetricUtilities.logApiCalledSimpleV2(com.android.server.credentials.metrics.ApiName.IS_ENABLED_CREDENTIAL_PROVIDER_SERVICE, com.android.server.credentials.metrics.ApiStatus.FAILURE, callingUid);
                throw new java.lang.IllegalArgumentException("provided component name does not match does not match requesting component");
            }
            java.util.Set<android.content.ComponentName> enabledProvidersForUser = getEnabledProvidersForUser(callingUserId);
            com.android.server.credentials.MetricUtilities.logApiCalledSimpleV2(com.android.server.credentials.metrics.ApiName.IS_ENABLED_CREDENTIAL_PROVIDER_SERVICE, com.android.server.credentials.metrics.ApiStatus.SUCCESS, callingUid);
            if (enabledProvidersForUser == null) {
                return false;
            }
            return enabledProvidersForUser.contains(componentName);
        }

        public java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServices(int i, int i2) {
            com.android.server.credentials.CredentialManagerService.this.verifyGetProvidersPermission();
            com.android.server.credentials.MetricUtilities.logApiCalledSimpleV2(com.android.server.credentials.metrics.ApiName.GET_CREDENTIAL_PROVIDER_SERVICES, com.android.server.credentials.metrics.ApiStatus.SUCCESS, android.os.Binder.getCallingUid());
            return android.service.credentials.CredentialProviderInfoFactory.getCredentialProviderServices(com.android.server.credentials.CredentialManagerService.this.mContext, i, i2, getEnabledProvidersForUser(i), com.android.server.credentials.CredentialManagerService.getPrimaryProvidersForUserId(com.android.server.credentials.CredentialManagerService.this.mContext, i));
        }

        public java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServicesForTesting(int i) {
            com.android.server.credentials.CredentialManagerService.this.verifyGetProvidersPermission();
            int callingUserId = android.os.UserHandle.getCallingUserId();
            return android.service.credentials.CredentialProviderInfoFactory.getCredentialProviderServicesForTesting(com.android.server.credentials.CredentialManagerService.this.mContext, callingUserId, i, getEnabledProvidersForUser(callingUserId), com.android.server.credentials.CredentialManagerService.getPrimaryProvidersForUserId(com.android.server.credentials.CredentialManagerService.this.mContext, callingUserId));
        }

        public boolean isServiceEnabled() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.provider.DeviceConfig.getBoolean("credential_manager", com.android.server.credentials.CredentialManagerService.DEVICE_CONFIG_ENABLE_CREDENTIAL_MANAGER, true);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private java.util.Set<android.content.ComponentName> getEnabledProvidersForUser(int i) {
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, "getEnabledProvidersForUser", null);
            java.util.HashSet hashSet = new java.util.HashSet();
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(com.android.server.credentials.CredentialManagerService.this.mContext.getContentResolver(), "credential_service", handleIncomingUser);
            if (!android.text.TextUtils.isEmpty(stringForUser)) {
                for (java.lang.String str : stringForUser.split(":")) {
                    android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
                    if (unflattenFromString != null) {
                        hashSet.add(unflattenFromString);
                    }
                }
            }
            return hashSet;
        }

        public android.os.ICancellationSignal clearCredentialState(android.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.credentials.IClearCredentialStateCallback iClearCredentialStateCallback, java.lang.String str) {
            long nanoTime = java.lang.System.nanoTime();
            android.util.Slog.i(com.android.server.credentials.CredentialManagerService.TAG, "starting clearCredentialState with callingPackage: " + str);
            int callingUserId = android.os.UserHandle.getCallingUserId();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.credentials.CredentialManagerService.this.enforceCallingPackage(str, callingUid);
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            com.android.server.credentials.ClearRequestSession clearRequestSession = new com.android.server.credentials.ClearRequestSession(com.android.server.credentials.CredentialManagerService.this.getContext(), com.android.server.credentials.CredentialManagerService.this.mSessionManager, ((com.android.server.infra.AbstractMasterSystemService) com.android.server.credentials.CredentialManagerService.this).mLock, callingUserId, callingUid, iClearCredentialStateCallback, clearCredentialStateRequest, com.android.server.credentials.CredentialManagerService.this.constructCallingAppInfo(str, callingUserId, null), getEnabledProvidersForUser(callingUserId), android.os.CancellationSignal.fromTransport(createTransport), nanoTime);
            com.android.server.credentials.CredentialManagerService.this.addSessionLocked(callingUserId, clearRequestSession);
            java.util.List initiateProviderSessions = com.android.server.credentials.CredentialManagerService.this.initiateProviderSessions(clearRequestSession, java.util.List.of());
            if (initiateProviderSessions.isEmpty()) {
                try {
                    iClearCredentialStateCallback.onError("UNKNOWN", "No credentials available on this device");
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.credentials.CredentialManagerService.TAG, "Issue invoking onError on IClearCredentialStateCallback callback: ", e);
                }
            }
            finalizeAndEmitInitialPhaseMetric(clearRequestSession);
            initiateProviderSessions.forEach(new com.android.server.credentials.CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda0());
            return createTransport;
        }

        public void registerCredentialDescription(android.credentials.RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, java.lang.String str) throws java.lang.IllegalArgumentException, com.android.server.credentials.NonCredentialProviderCallerException {
            android.util.Slog.i(com.android.server.credentials.CredentialManagerService.TAG, "registerCredentialDescription with callingPackage: " + str);
            if (!com.android.server.credentials.CredentialManagerService.isCredentialDescriptionApiEnabled()) {
                throw new java.lang.UnsupportedOperationException("Feature not supported");
            }
            com.android.server.credentials.CredentialManagerService.this.enforceCallingPackage(str, android.os.Binder.getCallingUid());
            com.android.server.credentials.CredentialDescriptionRegistry.forUser(android.os.UserHandle.getCallingUserId()).executeRegisterRequest(registerCredentialDescriptionRequest, str);
        }

        public void unregisterCredentialDescription(android.credentials.UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest, java.lang.String str) throws java.lang.IllegalArgumentException {
            android.util.Slog.i(com.android.server.credentials.CredentialManagerService.TAG, "unregisterCredentialDescription with callingPackage: " + str);
            if (!com.android.server.credentials.CredentialManagerService.isCredentialDescriptionApiEnabled()) {
                throw new java.lang.UnsupportedOperationException("Feature not supported");
            }
            com.android.server.credentials.CredentialManagerService.this.enforceCallingPackage(str, android.os.Binder.getCallingUid());
            com.android.server.credentials.CredentialDescriptionRegistry.forUser(android.os.UserHandle.getCallingUserId()).executeUnregisterRequest(unregisterCredentialDescriptionRequest, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateGetCredentialRequest(android.credentials.GetCredentialRequest getCredentialRequest) {
        if (getCredentialRequest.getOrigin() != null) {
            this.mContext.enforceCallingPermission("android.permission.CREDENTIAL_MANAGER_SET_ORIGIN", null);
        }
        enforcePermissionForAllowedProviders(getCredentialRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforcePermissionForAllowedProviders(android.credentials.GetCredentialRequest getCredentialRequest) {
        if (getCredentialRequest.getCredentialOptions().stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.credentials.CredentialManagerService$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$enforcePermissionForAllowedProviders$3;
                lambda$enforcePermissionForAllowedProviders$3 = com.android.server.credentials.CredentialManagerService.lambda$enforcePermissionForAllowedProviders$3((android.credentials.CredentialOption) obj);
                return lambda$enforcePermissionForAllowedProviders$3;
            }
        })) {
            this.mContext.enforceCallingPermission("android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS", null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$enforcePermissionForAllowedProviders$3(android.credentials.CredentialOption credentialOption) {
        return (credentialOption.getAllowedProviders() == null || credentialOption.getAllowedProviders().isEmpty()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSessionLocked(int i, com.android.server.credentials.RequestSession requestSession) {
        synchronized (this.mLock) {
            this.mSessionManager.addSession(i, requestSession.mRequestId, requestSession);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceCallingPackage(java.lang.String str, int i) {
        try {
            if (this.mContext.createContextAsUser(android.os.UserHandle.getUserHandleForUid(i), 0).getPackageManager().getPackageUid(str, android.content.pm.PackageManager.PackageInfoFlags.of(0L)) != i) {
                throw new java.lang.SecurityException(str + " does not belong to uid " + i);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.SecurityException(str + " not found");
        }
    }

    private class SessionManager implements com.android.server.credentials.RequestSession.SessionLifetime {
        private SessionManager() {
        }

        @Override // com.android.server.credentials.RequestSession.SessionLifetime
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void onFinishRequestSession(int i, android.os.IBinder iBinder) {
            if (com.android.server.credentials.CredentialManagerService.this.mRequestSessions.get(i) != null) {
                ((java.util.Map) com.android.server.credentials.CredentialManagerService.this.mRequestSessions.get(i)).remove(iBinder);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void addSession(int i, android.os.IBinder iBinder, com.android.server.credentials.RequestSession requestSession) {
            if (com.android.server.credentials.CredentialManagerService.this.mRequestSessions.get(i) == null) {
                com.android.server.credentials.CredentialManagerService.this.mRequestSessions.put(i, new java.util.HashMap());
            }
            ((java.util.Map) com.android.server.credentials.CredentialManagerService.this.mRequestSessions.get(i)).put(iBinder, requestSession);
        }
    }

    public static void updateProvidersWhenPackageRemoved(com.android.server.credentials.CredentialManagerService.SettingsWrapper settingsWrapper, java.lang.String str) {
        android.util.Slog.i(TAG, "updateProvidersWhenPackageRemoved");
        java.lang.String stringForUser = settingsWrapper.getStringForUser("credential_service_primary", android.os.UserHandle.myUserId());
        if (stringForUser == null) {
            android.util.Slog.w(TAG, "settings key is null");
            return;
        }
        java.util.Set<java.lang.String> storedProviders = getStoredProviders(stringForUser, str);
        if (!settingsWrapper.putStringForUser("credential_service_primary", java.lang.String.join(":", storedProviders), android.os.UserHandle.myUserId(), true)) {
            android.util.Slog.e(TAG, "Failed to remove primary package: " + str);
            return;
        }
        java.lang.String stringForUser2 = settingsWrapper.getStringForUser("autofill_service", android.os.UserHandle.myUserId());
        if (stringForUser2 != null && storedProviders.isEmpty()) {
            if (stringForUser2.equals(AUTOFILL_PLACEHOLDER_VALUE)) {
                if (!settingsWrapper.putStringForUser("autofill_service", "", android.os.UserHandle.myUserId(), true)) {
                    android.util.Slog.e(TAG, "Failed to remove autofill package: " + str);
                }
            } else {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(stringForUser2);
                if (unflattenFromString != null && unflattenFromString.getPackageName().equals(str) && !settingsWrapper.putStringForUser("autofill_service", "", android.os.UserHandle.myUserId(), true)) {
                    android.util.Slog.e(TAG, "Failed to remove autofill package: " + str);
                }
            }
        }
        if (!settingsWrapper.putStringForUser("credential_service", java.lang.String.join(":", getStoredProviders(settingsWrapper.getStringForUser("credential_service", android.os.UserHandle.myUserId()), str)), android.os.UserHandle.myUserId(), true)) {
            android.util.Slog.e(TAG, "Failed to remove secondary package: " + str);
        }
    }

    public static java.util.Set<java.lang.String> getStoredProviders(java.lang.String str, java.lang.String str2) {
        java.util.HashSet hashSet = new java.util.HashSet();
        if (str == null || str2 == null) {
            return hashSet;
        }
        for (java.lang.String str3 : str.split(":")) {
            if (android.text.TextUtils.isEmpty(str3) || str3.equals("null")) {
                android.util.Slog.d(TAG, "provider component name is empty or null");
            } else {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str3);
                if (unflattenFromString != null && !unflattenFromString.getPackageName().equals(str2)) {
                    hashSet.add(unflattenFromString.flattenToString());
                }
            }
        }
        return hashSet;
    }

    public static class SettingsWrapper {
        private final android.content.Context mContext;

        public SettingsWrapper(@android.annotation.NonNull android.content.Context context) {
            this.mContext = context;
        }

        android.content.ContentResolver getContentResolver() {
            return this.mContext.getContentResolver();
        }

        public java.lang.String getStringForUser(java.lang.String str, int i) {
            return android.provider.Settings.Secure.getStringForUser(getContentResolver(), str, i);
        }

        public boolean putStringForUser(java.lang.String str, java.lang.String str2, int i, boolean z) {
            return android.provider.Settings.Secure.putStringForUser(getContentResolver(), str, str2, null, false, i, z);
        }
    }
}
