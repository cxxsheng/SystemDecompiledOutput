package com.android.server.ambientcontext;

/* loaded from: classes.dex */
public class AmbientContextManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.ambientcontext.AmbientContextManagerService, com.android.server.ambientcontext.AmbientContextManagerPerUserService> {
    private static final boolean DEFAULT_SERVICE_ENABLED = true;
    private static final java.lang.String KEY_SERVICE_ENABLED = "service_enabled";
    public static final int MAX_TEMPORARY_SERVICE_DURATION_MS = 30000;
    private final android.content.Context mContext;
    private java.util.Set<com.android.server.ambientcontext.AmbientContextManagerService.ClientRequest> mExistingClientRequests;
    boolean mIsServiceEnabled;
    boolean mIsWearableServiceEnabled;
    private static final java.lang.String TAG = com.android.server.ambientcontext.AmbientContextManagerService.class.getSimpleName();
    private static final java.util.Set<java.lang.Integer> DEFAULT_EVENT_SET = com.google.android.collect.Sets.newHashSet(new java.lang.Integer[]{1, 2, 3});

    static class ClientRequest {
        private final android.app.ambientcontext.IAmbientContextObserver mObserver;
        private final java.lang.String mPackageName;
        private final android.app.ambientcontext.AmbientContextEventRequest mRequest;
        private final int mUserId;

        ClientRequest(int i, android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver) {
            this.mUserId = i;
            this.mRequest = ambientContextEventRequest;
            this.mPackageName = str;
            this.mObserver = iAmbientContextObserver;
        }

        java.lang.String getPackageName() {
            return this.mPackageName;
        }

        android.app.ambientcontext.AmbientContextEventRequest getRequest() {
            return this.mRequest;
        }

        android.app.ambientcontext.IAmbientContextObserver getObserver() {
            return this.mObserver;
        }

        boolean hasUserId(int i) {
            return this.mUserId == i;
        }

        boolean hasUserIdAndPackageName(int i, java.lang.String str) {
            return i == this.mUserId && str.equals(getPackageName());
        }
    }

    public AmbientContextManagerService(android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.array.config_concurrentDisplayDeviceStates, true), null, 68);
        this.mContext = context;
        this.mExistingClientRequests = java.util.concurrent.ConcurrentHashMap.newKeySet();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("ambient_context", new com.android.server.ambientcontext.AmbientContextManagerService.AmbientContextManagerInternal());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService, com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("ambient_context_manager_service", getContext().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.ambientcontext.AmbientContextManagerService$$ExternalSyntheticLambda0
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.ambientcontext.AmbientContextManagerService.this.lambda$onBootPhase$0(properties);
                }
            });
            this.mIsServiceEnabled = android.provider.DeviceConfig.getBoolean("ambient_context_manager_service", KEY_SERVICE_ENABLED, true);
            this.mIsWearableServiceEnabled = android.provider.DeviceConfig.getBoolean("wearable_sensing", KEY_SERVICE_ENABLED, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$0(android.provider.DeviceConfig.Properties properties) {
        onDeviceConfigChange(properties.getKeyset());
    }

    void newClientAdded(int i, android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver) {
        android.util.Slog.d(TAG, "New client added: " + str);
        synchronized (this.mExistingClientRequests) {
            this.mExistingClientRequests.removeAll(findExistingRequests(i, str));
            this.mExistingClientRequests.add(new com.android.server.ambientcontext.AmbientContextManagerService.ClientRequest(i, ambientContextEventRequest, str, iAmbientContextObserver));
        }
    }

    void clientRemoved(int i, java.lang.String str) {
        android.util.Slog.d(TAG, "Remove client: " + str);
        synchronized (this.mExistingClientRequests) {
            this.mExistingClientRequests.removeAll(findExistingRequests(i, str));
        }
    }

    private java.util.Set<com.android.server.ambientcontext.AmbientContextManagerService.ClientRequest> findExistingRequests(int i, java.lang.String str) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (com.android.server.ambientcontext.AmbientContextManagerService.ClientRequest clientRequest : this.mExistingClientRequests) {
            if (clientRequest.hasUserIdAndPackageName(i, str)) {
                arraySet.add(clientRequest);
            }
        }
        return arraySet;
    }

    @android.annotation.Nullable
    android.app.ambientcontext.IAmbientContextObserver getClientRequestObserver(int i, java.lang.String str) {
        synchronized (this.mExistingClientRequests) {
            try {
                for (com.android.server.ambientcontext.AmbientContextManagerService.ClientRequest clientRequest : this.mExistingClientRequests) {
                    if (clientRequest.hasUserIdAndPackageName(i, str)) {
                        return clientRequest.getObserver();
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void onDeviceConfigChange(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        if (set.contains(KEY_SERVICE_ENABLED)) {
            this.mIsServiceEnabled = android.provider.DeviceConfig.getBoolean("ambient_context_manager_service", KEY_SERVICE_ENABLED, true);
            this.mIsWearableServiceEnabled = android.provider.DeviceConfig.getBoolean("wearable_sensing", KEY_SERVICE_ENABLED, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.ambientcontext.AmbientContextManagerPerUserService newServiceLocked(int i, boolean z) {
        return null;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected java.util.List<com.android.server.ambientcontext.AmbientContextManagerPerUserService> newServiceListLocked(int i, boolean z, java.lang.String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            android.util.Slog.i(TAG, "serviceNames sent in newServiceListLocked is null, or empty");
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(strArr.length);
        if (strArr.length == 2 && !isDefaultService(strArr[0]) && !isDefaultWearableService(strArr[1])) {
            android.util.Slog.i(TAG, "Not using default services, services provided for testing should be exactly two services.");
            arrayList.add(new com.android.server.ambientcontext.DefaultAmbientContextManagerPerUserService(this, this.mLock, i, com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.DEFAULT, strArr[0]));
            arrayList.add(new com.android.server.ambientcontext.WearableAmbientContextManagerPerUserService(this, this.mLock, i, com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.WEARABLE, strArr[1]));
            return arrayList;
        }
        if (strArr.length > 2) {
            android.util.Slog.i(TAG, "Incorrect number of services provided for testing.");
        }
        for (java.lang.String str : strArr) {
            android.util.Slog.d(TAG, "newServicesListLocked with service name: " + str);
            if (getServiceType(str) == com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.WEARABLE) {
                arrayList.add(new com.android.server.ambientcontext.WearableAmbientContextManagerPerUserService(this, this.mLock, i, com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.WEARABLE, str));
            } else {
                arrayList.add(new com.android.server.ambientcontext.DefaultAmbientContextManagerPerUserService(this, this.mLock, i, com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.DEFAULT, str));
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public void onServiceRemoved(com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserService, int i) {
        android.util.Slog.d(TAG, "onServiceRemoved");
        ambientContextManagerPerUserService.destroyLocked();
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageRestartedLocked(int i) {
        android.util.Slog.d(TAG, "Restoring remote request. Reason: Service package restarted.");
        restorePreviouslyEnabledClients(i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageUpdatedLocked(int i) {
        android.util.Slog.d(TAG, "Restoring remote request. Reason: Service package updated.");
        restorePreviouslyEnabledClients(i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.ACCESS_AMBIENT_CONTEXT_EVENT", TAG);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected int getMaximumTemporaryServiceDurationMs() {
        return 30000;
    }

    public static boolean isDetectionServiceConfigured() {
        boolean z = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getKnownPackageNames(18, 0).length != 0;
        android.util.Slog.i(TAG, "Detection service configured: " + z);
        return z;
    }

    void startDetection(int i, android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_AMBIENT_CONTEXT_EVENT", TAG);
        synchronized (this.mLock) {
            try {
                com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes = getAmbientContextManagerPerUserServiceForEventTypes(i, ambientContextEventRequest.getEventTypes());
                if (ambientContextManagerPerUserServiceForEventTypes != null) {
                    ambientContextManagerPerUserServiceForEventTypes.startDetection(ambientContextEventRequest, str, iAmbientContextObserver);
                } else {
                    android.util.Slog.i(TAG, "service not available for user_id: " + i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void stopAmbientContextEvent(int i, java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_AMBIENT_CONTEXT_EVENT", TAG);
        synchronized (this.mLock) {
            try {
                for (com.android.server.ambientcontext.AmbientContextManagerService.ClientRequest clientRequest : this.mExistingClientRequests) {
                    android.util.Slog.i(TAG, "Looping through clients");
                    if (clientRequest.hasUserIdAndPackageName(i, str)) {
                        android.util.Slog.i(TAG, "we have an existing client");
                        com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes = getAmbientContextManagerPerUserServiceForEventTypes(i, clientRequest.getRequest().getEventTypes());
                        if (ambientContextManagerPerUserServiceForEventTypes != null) {
                            ambientContextManagerPerUserServiceForEventTypes.stopDetection(str);
                        } else {
                            android.util.Slog.i(TAG, "service not available for user_id: " + i);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void queryServiceStatus(int i, java.lang.String str, int[] iArr, android.os.RemoteCallback remoteCallback) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_AMBIENT_CONTEXT_EVENT", TAG);
        synchronized (this.mLock) {
            try {
                com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes = getAmbientContextManagerPerUserServiceForEventTypes(i, intArrayToIntegerSet(iArr));
                if (ambientContextManagerPerUserServiceForEventTypes != null) {
                    ambientContextManagerPerUserServiceForEventTypes.onQueryServiceStatus(iArr, str, remoteCallback);
                } else {
                    android.util.Slog.i(TAG, "query service not available for user_id: " + i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void restorePreviouslyEnabledClients(int i) {
        synchronized (this.mLock) {
            try {
                for (com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserService : getServiceListForUserLocked(i)) {
                    for (com.android.server.ambientcontext.AmbientContextManagerService.ClientRequest clientRequest : this.mExistingClientRequests) {
                        if (clientRequest.hasUserId(i)) {
                            android.util.Slog.d(TAG, "Restoring detection for " + clientRequest.getPackageName());
                            ambientContextManagerPerUserService.startDetection(clientRequest.getRequest(), clientRequest.getPackageName(), clientRequest.getObserver());
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.content.ComponentName getComponentName(int i, com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType serviceType) {
        synchronized (this.mLock) {
            try {
                com.android.server.ambientcontext.AmbientContextManagerPerUserService serviceForType = getServiceForType(i, serviceType);
                if (serviceForType != null) {
                    return serviceForType.getComponentName();
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.ambientcontext.AmbientContextManagerPerUserService getAmbientContextManagerPerUserServiceForEventTypes(int i, java.util.Set<java.lang.Integer> set) {
        if (isWearableEventTypesOnly(set)) {
            return getServiceForType(i, com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.WEARABLE);
        }
        return getServiceForType(i, com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.DEFAULT);
    }

    private com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType getServiceType(java.lang.String str) {
        java.lang.String string = this.mContext.getResources().getString(android.R.string.config_defaultShutdownVibrationFile);
        if (string != null && string.equals(str)) {
            return com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.WEARABLE;
        }
        return com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.DEFAULT;
    }

    private boolean isDefaultService(java.lang.String str) {
        java.lang.String string = this.mContext.getResources().getString(android.R.string.config_customVpnAlwaysOnDisconnectedDialogComponent);
        if (string != null && string.equals(str)) {
            return true;
        }
        return false;
    }

    private boolean isDefaultWearableService(java.lang.String str) {
        java.lang.String string = this.mContext.getResources().getString(android.R.string.config_defaultShutdownVibrationFile);
        if (string != null && string.equals(str)) {
            return true;
        }
        return false;
    }

    private com.android.server.ambientcontext.AmbientContextManagerPerUserService getServiceForType(int i, com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType serviceType) {
        android.util.Slog.d(TAG, "getServiceForType with userid: " + i + " service type: " + serviceType.name());
        synchronized (this.mLock) {
            try {
                java.util.List<com.android.server.ambientcontext.AmbientContextManagerPerUserService> serviceListForUserLocked = getServiceListForUserLocked(i);
                java.lang.String str = TAG;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Services that are available: ");
                sb.append(serviceListForUserLocked == null ? "null services" : serviceListForUserLocked.size() + " number of services");
                android.util.Slog.d(str, sb.toString());
                if (serviceListForUserLocked == null) {
                    return null;
                }
                for (com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserService : serviceListForUserLocked) {
                    if (ambientContextManagerPerUserService.getServiceType() == serviceType) {
                        return ambientContextManagerPerUserService;
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isWearableEventTypesOnly(java.util.Set<java.lang.Integer> set) {
        if (set.isEmpty()) {
            android.util.Slog.d(TAG, "empty event types.");
            return false;
        }
        java.util.Iterator<java.lang.Integer> it = set.iterator();
        while (it.hasNext()) {
            if (it.next().intValue() < 100000) {
                android.util.Slog.d(TAG, "Not all events types are wearable events.");
                return false;
            }
        }
        android.util.Slog.d(TAG, "only wearable events.");
        return true;
    }

    private boolean isWearableEventTypesOnly(int[] iArr) {
        return isWearableEventTypesOnly(new java.util.HashSet(java.util.Arrays.asList(intArrayToIntegerArray(iArr))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean containsMixedEvents(int[] iArr) {
        if (isWearableEventTypesOnly(iArr)) {
            return false;
        }
        for (int i : iArr) {
            if (!DEFAULT_EVENT_SET.contains(java.lang.Integer.valueOf(i))) {
                android.util.Slog.w(TAG, "Received mixed event types, this is not supported.");
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int[] integerSetToIntArray(@android.annotation.NonNull java.util.Set<java.lang.Integer> set) {
        int[] iArr = new int[set.size()];
        java.util.Iterator<java.lang.Integer> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.Set<java.lang.Integer> intArrayToIntegerSet(int[] iArr) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i : iArr) {
            hashSet.add(java.lang.Integer.valueOf(i));
        }
        return hashSet;
    }

    @android.annotation.NonNull
    private static java.lang.Integer[] intArrayToIntegerArray(@android.annotation.NonNull int[] iArr) {
        java.lang.Integer[] numArr = new java.lang.Integer[iArr.length];
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            numArr[i2] = java.lang.Integer.valueOf(iArr[i]);
            i++;
            i2++;
        }
        return numArr;
    }

    private final class AmbientContextManagerInternal extends android.app.ambientcontext.IAmbientContextManager.Stub {
        private AmbientContextManagerInternal() {
        }

        public void registerObserver(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, final android.app.PendingIntent pendingIntent, final android.os.RemoteCallback remoteCallback) {
            java.util.Objects.requireNonNull(ambientContextEventRequest);
            java.util.Objects.requireNonNull(pendingIntent);
            java.util.Objects.requireNonNull(remoteCallback);
            final com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes = com.android.server.ambientcontext.AmbientContextManagerService.this.getAmbientContextManagerPerUserServiceForEventTypes(android.os.UserHandle.getCallingUserId(), ambientContextEventRequest.getEventTypes());
            registerObserverWithCallback(ambientContextEventRequest, pendingIntent.getCreatorPackage(), new android.app.ambientcontext.IAmbientContextObserver.Stub() { // from class: com.android.server.ambientcontext.AmbientContextManagerService.AmbientContextManagerInternal.1
                public void onEvents(java.util.List<android.app.ambientcontext.AmbientContextEvent> list) throws android.os.RemoteException {
                    ambientContextManagerPerUserServiceForEventTypes.sendDetectionResultIntent(pendingIntent, list);
                }

                public void onRegistrationComplete(int i) throws android.os.RemoteException {
                    ambientContextManagerPerUserServiceForEventTypes.sendStatusCallback(remoteCallback, i);
                }
            });
        }

        public void registerObserverWithCallback(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver) {
            android.util.Slog.i(com.android.server.ambientcontext.AmbientContextManagerService.TAG, "AmbientContextManagerService registerObserverWithCallback.");
            java.util.Objects.requireNonNull(ambientContextEventRequest);
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(iAmbientContextObserver);
            com.android.server.ambientcontext.AmbientContextManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_AMBIENT_CONTEXT_EVENT", com.android.server.ambientcontext.AmbientContextManagerService.TAG);
            com.android.server.ambientcontext.AmbientContextManagerService.this.assertCalledByPackageOwner(str);
            com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes = com.android.server.ambientcontext.AmbientContextManagerService.this.getAmbientContextManagerPerUserServiceForEventTypes(android.os.UserHandle.getCallingUserId(), ambientContextEventRequest.getEventTypes());
            if (ambientContextManagerPerUserServiceForEventTypes == null) {
                android.util.Slog.w(com.android.server.ambientcontext.AmbientContextManagerService.TAG, "onRegisterObserver unavailable user_id: " + android.os.UserHandle.getCallingUserId());
                return;
            }
            int checkStatusCode = checkStatusCode(ambientContextManagerPerUserServiceForEventTypes, com.android.server.ambientcontext.AmbientContextManagerService.integerSetToIntArray(ambientContextEventRequest.getEventTypes()));
            if (checkStatusCode == 1) {
                ambientContextManagerPerUserServiceForEventTypes.onRegisterObserver(ambientContextEventRequest, str, iAmbientContextObserver);
            } else {
                ambientContextManagerPerUserServiceForEventTypes.completeRegistration(iAmbientContextObserver, checkStatusCode);
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_AMBIENT_CONTEXT_EVENT")
        public void unregisterObserver(java.lang.String str) {
            unregisterObserver_enforcePermission();
            com.android.server.ambientcontext.AmbientContextManagerService.this.assertCalledByPackageOwner(str);
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.ambientcontext.AmbientContextManagerService.this).mLock) {
                try {
                    for (com.android.server.ambientcontext.AmbientContextManagerService.ClientRequest clientRequest : com.android.server.ambientcontext.AmbientContextManagerService.this.mExistingClientRequests) {
                        if (clientRequest != null && clientRequest.getPackageName().equals(str)) {
                            com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes = com.android.server.ambientcontext.AmbientContextManagerService.this.getAmbientContextManagerPerUserServiceForEventTypes(android.os.UserHandle.getCallingUserId(), clientRequest.getRequest().getEventTypes());
                            if (ambientContextManagerPerUserServiceForEventTypes != null) {
                                ambientContextManagerPerUserServiceForEventTypes.onUnregisterObserver(str);
                            } else {
                                android.util.Slog.w(com.android.server.ambientcontext.AmbientContextManagerService.TAG, "onUnregisterObserver unavailable user_id: " + android.os.UserHandle.getCallingUserId());
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void queryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) {
            java.util.Objects.requireNonNull(iArr);
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(remoteCallback);
            com.android.server.ambientcontext.AmbientContextManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_AMBIENT_CONTEXT_EVENT", com.android.server.ambientcontext.AmbientContextManagerService.TAG);
            com.android.server.ambientcontext.AmbientContextManagerService.this.assertCalledByPackageOwner(str);
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.ambientcontext.AmbientContextManagerService.this).mLock) {
                try {
                    com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes = com.android.server.ambientcontext.AmbientContextManagerService.this.getAmbientContextManagerPerUserServiceForEventTypes(android.os.UserHandle.getCallingUserId(), com.android.server.ambientcontext.AmbientContextManagerService.this.intArrayToIntegerSet(iArr));
                    if (ambientContextManagerPerUserServiceForEventTypes == null) {
                        android.util.Slog.w(com.android.server.ambientcontext.AmbientContextManagerService.TAG, "queryServiceStatus unavailable user_id: " + android.os.UserHandle.getCallingUserId());
                        return;
                    }
                    int checkStatusCode = checkStatusCode(ambientContextManagerPerUserServiceForEventTypes, iArr);
                    if (checkStatusCode == 1) {
                        ambientContextManagerPerUserServiceForEventTypes.onQueryServiceStatus(iArr, str, remoteCallback);
                    } else {
                        ambientContextManagerPerUserServiceForEventTypes.sendStatusCallback(remoteCallback, checkStatusCode);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void startConsentActivity(int[] iArr, java.lang.String str) {
            java.util.Objects.requireNonNull(iArr);
            java.util.Objects.requireNonNull(str);
            com.android.server.ambientcontext.AmbientContextManagerService.this.assertCalledByPackageOwner(str);
            com.android.server.ambientcontext.AmbientContextManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_AMBIENT_CONTEXT_EVENT", com.android.server.ambientcontext.AmbientContextManagerService.TAG);
            if (com.android.server.ambientcontext.AmbientContextManagerService.this.containsMixedEvents(iArr)) {
                android.util.Slog.d(com.android.server.ambientcontext.AmbientContextManagerService.TAG, "AmbientContextEventRequest contains mixed events, this is not supported.");
                return;
            }
            com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes = com.android.server.ambientcontext.AmbientContextManagerService.this.getAmbientContextManagerPerUserServiceForEventTypes(android.os.UserHandle.getCallingUserId(), com.android.server.ambientcontext.AmbientContextManagerService.this.intArrayToIntegerSet(iArr));
            if (ambientContextManagerPerUserServiceForEventTypes != null) {
                ambientContextManagerPerUserServiceForEventTypes.onStartConsentActivity(iArr, str);
                return;
            }
            android.util.Slog.w(com.android.server.ambientcontext.AmbientContextManagerService.TAG, "startConsentActivity unavailable user_id: " + android.os.UserHandle.getCallingUserId());
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.ambientcontext.AmbientContextManagerService.this.mContext, com.android.server.ambientcontext.AmbientContextManagerService.TAG, printWriter)) {
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.ambientcontext.AmbientContextManagerService.this).mLock) {
                    com.android.server.ambientcontext.AmbientContextManagerService.this.dumpLocked("", printWriter);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.ambientcontext.AmbientContextShellCommand(com.android.server.ambientcontext.AmbientContextManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        private int checkStatusCode(com.android.server.ambientcontext.AmbientContextManagerPerUserService ambientContextManagerPerUserService, int[] iArr) {
            if (ambientContextManagerPerUserService.getServiceType() == com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.DEFAULT && !com.android.server.ambientcontext.AmbientContextManagerService.this.mIsServiceEnabled) {
                android.util.Slog.d(com.android.server.ambientcontext.AmbientContextManagerService.TAG, "Service not enabled.");
                return 3;
            }
            if (ambientContextManagerPerUserService.getServiceType() == com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.WEARABLE && !com.android.server.ambientcontext.AmbientContextManagerService.this.mIsWearableServiceEnabled) {
                android.util.Slog.d(com.android.server.ambientcontext.AmbientContextManagerService.TAG, "Wearable Service not available.");
                return 3;
            }
            if (com.android.server.ambientcontext.AmbientContextManagerService.this.containsMixedEvents(iArr)) {
                android.util.Slog.d(com.android.server.ambientcontext.AmbientContextManagerService.TAG, "AmbientContextEventRequest contains mixed events, this is not supported.");
                return 2;
            }
            return 1;
        }
    }
}
