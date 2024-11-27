package com.android.server.ambientcontext;

/* loaded from: classes.dex */
abstract class AmbientContextManagerPerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.ambientcontext.AmbientContextManagerPerUserService, com.android.server.ambientcontext.AmbientContextManagerService> {
    private static final java.lang.String TAG = com.android.server.ambientcontext.AmbientContextManagerPerUserService.class.getSimpleName();

    enum ServiceType {
        DEFAULT,
        WEARABLE
    }

    abstract void clearRemoteService();

    abstract void ensureRemoteServiceInitiated();

    abstract int getAmbientContextEventArrayExtraKeyConfig();

    abstract int getAmbientContextPackageNameExtraKeyConfig();

    abstract android.content.ComponentName getComponentName();

    abstract int getConsentComponentConfig();

    abstract java.lang.String getProtectedBindPermission();

    abstract com.android.server.ambientcontext.RemoteAmbientDetectionService getRemoteService();

    abstract com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType getServiceType();

    abstract void setComponentName(android.content.ComponentName componentName);

    AmbientContextManagerPerUserService(@android.annotation.NonNull com.android.server.ambientcontext.AmbientContextManagerService ambientContextManagerService, java.lang.Object obj, int i) {
        super(ambientContextManagerService, obj, i);
    }

    public void onQueryServiceStatus(int[] iArr, java.lang.String str, final android.os.RemoteCallback remoteCallback) {
        android.util.Slog.d(TAG, "Query event status of " + java.util.Arrays.toString(iArr) + " for " + str);
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Detection service is not available at this moment.");
                    sendStatusCallback(remoteCallback, 3);
                } else {
                    ensureRemoteServiceInitiated();
                    getRemoteService().queryServiceStatus(iArr, str, getServerStatusCallback(new java.util.function.Consumer() { // from class: com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.ambientcontext.AmbientContextManagerPerUserService.this.lambda$onQueryServiceStatus$0(remoteCallback, (java.lang.Integer) obj);
                        }
                    }));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onQueryServiceStatus$0(android.os.RemoteCallback remoteCallback, java.lang.Integer num) {
        sendStatusCallback(remoteCallback, num.intValue());
    }

    public void onUnregisterObserver(java.lang.String str) {
        synchronized (this.mLock) {
            stopDetection(str);
            ((com.android.server.ambientcontext.AmbientContextManagerService) this.mMaster).clientRemoved(this.mUserId, str);
        }
    }

    public void onStartConsentActivity(int[] iArr, java.lang.String str) {
        android.util.Slog.d(TAG, "Opening consent activity of " + java.util.Arrays.toString(iArr) + " for " + str);
        try {
            android.content.pm.ParceledListSlice recentTasks = android.app.ActivityTaskManager.getService().getRecentTasks(1, 0, getUserId());
            if (recentTasks == null || recentTasks.getList().isEmpty()) {
                android.util.Slog.e(TAG, "Recent task list is empty!");
                return;
            }
            android.app.ActivityManager.RecentTaskInfo recentTaskInfo = (android.app.ActivityManager.RecentTaskInfo) recentTasks.getList().get(0);
            if (!str.equals(recentTaskInfo.topActivityInfo.packageName)) {
                android.util.Slog.e(TAG, "Recent task package name: " + recentTaskInfo.topActivityInfo.packageName + " doesn't match with client package name: " + str);
                return;
            }
            android.content.ComponentName consentComponent = getConsentComponent();
            if (consentComponent == null) {
                android.util.Slog.e(TAG, "Consent component not found!");
                return;
            }
            android.util.Slog.d(TAG, "Starting consent activity for " + str);
            android.content.Intent intent = new android.content.Intent();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    android.content.Context context = getContext();
                    java.lang.String string = context.getResources().getString(getAmbientContextPackageNameExtraKeyConfig());
                    java.lang.String string2 = context.getResources().getString(getAmbientContextEventArrayExtraKeyConfig());
                    intent.setComponent(consentComponent);
                    if (string != null) {
                        intent.putExtra(string, str);
                    } else {
                        android.util.Slog.d(TAG, "Missing packageNameExtraKey for consent activity");
                    }
                    if (string2 != null) {
                        intent.putExtra(string2, iArr);
                    } else {
                        android.util.Slog.d(TAG, "Missing eventArrayExtraKey for consent activity");
                    }
                    android.app.ActivityOptions makeBasic = android.app.ActivityOptions.makeBasic();
                    makeBasic.setLaunchTaskId(recentTaskInfo.taskId);
                    context.startActivityAsUser(intent, makeBasic.toBundle(), context.getUser());
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (android.content.ActivityNotFoundException e) {
                android.util.Slog.e(TAG, "unable to start consent activity");
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(TAG, "Failed to query recent tasks!");
        }
    }

    public void onRegisterObserver(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver) {
        synchronized (this.mLock) {
            try {
                if (!setUpServiceIfNeeded()) {
                    android.util.Slog.w(TAG, "Detection service is not available at this moment.");
                    completeRegistration(iAmbientContextObserver, 3);
                } else {
                    startDetection(ambientContextEventRequest, str, iAmbientContextObserver);
                    ((com.android.server.ambientcontext.AmbientContextManagerService) this.mMaster).newClientAdded(this.mUserId, ambientContextEventRequest, str, iAmbientContextObserver);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        android.util.Slog.d(TAG, "newServiceInfoLocked with component name: " + componentName.getClassName());
        if (getComponentName() == null || !componentName.getClassName().equals(getComponentName().getClassName())) {
            android.util.Slog.d(TAG, "service name does not match this per user, returning...");
            return null;
        }
        try {
            android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 0L, this.mUserId);
            if (serviceInfo != null) {
                if (!getProtectedBindPermission().equals(serviceInfo.permission)) {
                    throw new java.lang.SecurityException(java.lang.String.format("Service %s requires %s permission. Found %s permission", serviceInfo.getComponentName(), getProtectedBindPermission(), serviceInfo.permission));
                }
            }
            return serviceInfo;
        } catch (android.os.RemoteException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get service for " + componentName);
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    protected void dumpLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            super.dumpLocked(str, printWriter);
        }
        com.android.server.ambientcontext.RemoteAmbientDetectionService remoteService = getRemoteService();
        if (remoteService != null) {
            remoteService.dump("", new android.util.IndentingPrintWriter(printWriter, "  "));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void stopDetection(java.lang.String str) {
        android.util.Slog.d(TAG, "Stop detection for " + str);
        synchronized (this.mLock) {
            try {
                if (getComponentName() != null) {
                    ensureRemoteServiceInitiated();
                    getRemoteService().stopDetection(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void destroyLocked() {
        android.util.Slog.d(TAG, "Trying to cancel the remote request. Reason: Service destroyed.");
        com.android.server.ambientcontext.RemoteAmbientDetectionService remoteService = getRemoteService();
        if (remoteService != null) {
            synchronized (this.mLock) {
                remoteService.unbind();
                clearRemoteService();
            }
        }
    }

    protected void startDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, final android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver) {
        android.util.Slog.d(TAG, "Requested detection of " + ambientContextEventRequest.getEventTypes());
        synchronized (this.mLock) {
            try {
                if (setUpServiceIfNeeded()) {
                    ensureRemoteServiceInitiated();
                    getRemoteService().startDetection(ambientContextEventRequest, str, createDetectionResultRemoteCallback(), getServerStatusCallback(new java.util.function.Consumer() { // from class: com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.ambientcontext.AmbientContextManagerPerUserService.this.lambda$startDetection$1(iAmbientContextObserver, (java.lang.Integer) obj);
                        }
                    }));
                } else {
                    android.util.Slog.w(TAG, "No valid component found for AmbientContextDetectionService");
                    completeRegistration(iAmbientContextObserver, 2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDetection$1(android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver, java.lang.Integer num) {
        completeRegistration(iAmbientContextObserver, num.intValue());
    }

    protected void completeRegistration(android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver, int i) {
        try {
            iAmbientContextObserver.onRegistrationComplete(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to call IAmbientContextObserver.onRegistrationComplete: " + e.getMessage());
        }
    }

    protected void sendStatusCallback(android.os.RemoteCallback remoteCallback, int i) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("android.app.ambientcontext.AmbientContextStatusBundleKey", i);
        remoteCallback.sendResult(bundle);
    }

    protected void sendDetectionResultIntent(android.app.PendingIntent pendingIntent, java.util.List<android.app.ambientcontext.AmbientContextEvent> list) {
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.app.ambientcontext.extra.AMBIENT_CONTEXT_EVENTS", new java.util.ArrayList(list));
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
        try {
            pendingIntent.send(getContext(), 0, intent, null, null, null, makeBasic.toBundle());
            android.util.Slog.i(TAG, "Sending PendingIntent to " + pendingIntent.getCreatorPackage() + ": " + list);
        } catch (android.app.PendingIntent.CanceledException e) {
            android.util.Slog.w(TAG, "Couldn't deliver pendingIntent:" + pendingIntent);
        }
    }

    @android.annotation.NonNull
    protected android.os.RemoteCallback createDetectionResultRemoteCallback() {
        return new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticLambda0
            public final void onResult(android.os.Bundle bundle) {
                com.android.server.ambientcontext.AmbientContextManagerPerUserService.this.lambda$createDetectionResultRemoteCallback$2(bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDetectionResultRemoteCallback$2(android.os.Bundle bundle) {
        android.service.ambientcontext.AmbientContextDetectionResult ambientContextDetectionResult = (android.service.ambientcontext.AmbientContextDetectionResult) bundle.get("android.app.ambientcontext.AmbientContextDetectionResultBundleKey");
        java.lang.String packageName = ambientContextDetectionResult.getPackageName();
        android.app.ambientcontext.IAmbientContextObserver clientRequestObserver = ((com.android.server.ambientcontext.AmbientContextManagerService) this.mMaster).getClientRequestObserver(this.mUserId, packageName);
        if (clientRequestObserver == null) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                clientRequestObserver.onEvents(ambientContextDetectionResult.getEvents());
                android.util.Slog.i(TAG, "Got detection result of " + ambientContextDetectionResult.getEvents() + " for " + packageName);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to call IAmbientContextObserver.onEvents: " + e.getMessage());
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    private boolean setUpServiceIfNeeded() {
        if (getComponentName() == null) {
            android.content.ComponentName[] updateServiceInfoListLocked = updateServiceInfoListLocked();
            if (updateServiceInfoListLocked == null || updateServiceInfoListLocked.length != 2) {
                android.util.Slog.d(TAG, "updateServiceInfoListLocked returned incorrect componentNames");
                return false;
            }
            switch (com.android.server.ambientcontext.AmbientContextManagerPerUserService.AnonymousClass1.$SwitchMap$com$android$server$ambientcontext$AmbientContextManagerPerUserService$ServiceType[getServiceType().ordinal()]) {
                case 1:
                    setComponentName(updateServiceInfoListLocked[0]);
                    break;
                case 2:
                    setComponentName(updateServiceInfoListLocked[1]);
                    break;
                default:
                    android.util.Slog.d(TAG, "updateServiceInfoListLocked returned unknown service types.");
                    return false;
            }
        }
        if (getComponentName() == null) {
            return false;
        }
        try {
            return android.app.AppGlobals.getPackageManager().getServiceInfo(getComponentName(), 0L, this.mUserId) != null;
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "RemoteException while setting up service");
            return false;
        }
    }

    /* renamed from: com.android.server.ambientcontext.AmbientContextManagerPerUserService$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$server$ambientcontext$AmbientContextManagerPerUserService$ServiceType = new int[com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.values().length];

        static {
            try {
                $SwitchMap$com$android$server$ambientcontext$AmbientContextManagerPerUserService$ServiceType[com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.DEFAULT.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$server$ambientcontext$AmbientContextManagerPerUserService$ServiceType[com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.WEARABLE.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
        }
    }

    private android.os.RemoteCallback getServerStatusCallback(final java.util.function.Consumer<java.lang.Integer> consumer) {
        return new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticLambda2
            public final void onResult(android.os.Bundle bundle) {
                com.android.server.ambientcontext.AmbientContextManagerPerUserService.lambda$getServerStatusCallback$3(consumer, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getServerStatusCallback$3(java.util.function.Consumer consumer, android.os.Bundle bundle) {
        android.service.ambientcontext.AmbientContextDetectionServiceStatus ambientContextDetectionServiceStatus = (android.service.ambientcontext.AmbientContextDetectionServiceStatus) bundle.get("android.app.ambientcontext.AmbientContextServiceStatusBundleKey");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int statusCode = ambientContextDetectionServiceStatus.getStatusCode();
            consumer.accept(java.lang.Integer.valueOf(statusCode));
            android.util.Slog.i(TAG, "Got detection status of " + statusCode + " for " + ambientContextDetectionServiceStatus.getPackageName());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private android.content.ComponentName getConsentComponent() {
        java.lang.String string = getContext().getResources().getString(getConsentComponentConfig());
        if (android.text.TextUtils.isEmpty(string)) {
            return null;
        }
        android.util.Slog.i(TAG, "Consent component name: " + string);
        return android.content.ComponentName.unflattenFromString(string);
    }
}
