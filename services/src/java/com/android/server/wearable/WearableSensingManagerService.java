package com.android.server.wearable;

/* loaded from: classes.dex */
public class WearableSensingManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.wearable.WearableSensingManagerService, com.android.server.wearable.WearableSensingManagerPerUserService> {
    private static final boolean DEFAULT_SERVICE_ENABLED = true;
    private static final java.lang.String KEY_SERVICE_ENABLED = "service_enabled";
    public static final int MAX_TEMPORARY_SERVICE_DURATION_MS = 30000;
    private static final java.lang.String RATE_LIMITER_PACKAGE_NAME = "android";
    private final android.content.Context mContext;
    private final java.util.Set<com.android.server.wearable.WearableSensingManagerService.DataRequestObserverContext> mDataRequestObserverContexts;

    @android.annotation.NonNull
    private volatile com.android.server.utils.quota.MultiRateLimiter mDataRequestRateLimiter;
    volatile boolean mIsServiceEnabled;
    private final java.util.concurrent.atomic.AtomicInteger mNextDataRequestObserverId;
    private static final java.lang.String TAG = com.android.server.wearable.WearableSensingManagerService.class.getSimpleName();
    private static final java.lang.String RATE_LIMITER_TAG = com.android.server.wearable.WearableSensingManagerService.class.getSimpleName();

    private static final class DataRequestObserverContext {
        final int mDataRequestObserverId;

        @android.annotation.NonNull
        final android.app.PendingIntent mDataRequestPendingIntent;

        @android.annotation.NonNull
        final android.os.RemoteCallback mDataRequestRemoteCallback;
        final int mDataType;
        final int mUserId;

        DataRequestObserverContext(int i, int i2, int i3, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback) {
            this.mDataType = i;
            this.mUserId = i2;
            this.mDataRequestObserverId = i3;
            this.mDataRequestPendingIntent = pendingIntent;
            this.mDataRequestRemoteCallback = remoteCallback;
        }
    }

    public WearableSensingManagerService(android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultShutdownVibrationFile), null, 68);
        this.mNextDataRequestObserverId = new java.util.concurrent.atomic.AtomicInteger(1);
        this.mDataRequestObserverContexts = new java.util.HashSet();
        this.mContext = context;
        this.mDataRequestRateLimiter = new com.android.server.utils.quota.MultiRateLimiter.Builder(context).addRateLimit(android.app.wearable.WearableSensingDataRequest.getRateLimit(), android.app.wearable.WearableSensingDataRequest.getRateLimitWindowSize()).build();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("wearable_sensing", new com.android.server.wearable.WearableSensingManagerService.WearableSensingManagerInternal());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService, com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("wearable_sensing", getContext().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.wearable.WearableSensingManagerService$$ExternalSyntheticLambda1
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.wearable.WearableSensingManagerService.this.lambda$onBootPhase$0(properties);
                }
            });
            this.mIsServiceEnabled = android.provider.DeviceConfig.getBoolean("wearable_sensing", KEY_SERVICE_ENABLED, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$0(android.provider.DeviceConfig.Properties properties) {
        onDeviceConfigChange(properties.getKeyset());
    }

    private void onDeviceConfigChange(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        if (set.contains(KEY_SERVICE_ENABLED)) {
            this.mIsServiceEnabled = android.provider.DeviceConfig.getBoolean("wearable_sensing", KEY_SERVICE_ENABLED, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.wearable.WearableSensingManagerPerUserService newServiceLocked(int i, boolean z) {
        return new com.android.server.wearable.WearableSensingManagerPerUserService(this, this.mLock, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public void onServiceRemoved(com.android.server.wearable.WearableSensingManagerPerUserService wearableSensingManagerPerUserService, int i) {
        android.util.Slog.d(TAG, "onServiceRemoved");
        wearableSensingManagerPerUserService.destroyLocked();
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageRestartedLocked(int i) {
        android.util.Slog.d(TAG, "onServicePackageRestartedLocked.");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onServicePackageUpdatedLocked(int i) {
        android.util.Slog.d(TAG, "onServicePackageUpdatedLocked.");
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
        boolean z = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getKnownPackageNames(19, 0).length != 0;
        android.util.Slog.i(TAG, "Wearable sensing service configured: " + z);
        return z;
    }

    public android.content.ComponentName getComponentName(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.wearable.WearableSensingManagerPerUserService serviceForUserLocked = getServiceForUserLocked(i);
                if (serviceForUserLocked != null) {
                    return serviceForUserLocked.getComponentName();
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void provideDataStream(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) {
        synchronized (this.mLock) {
            try {
                com.android.server.wearable.WearableSensingManagerPerUserService serviceForUserLocked = getServiceForUserLocked(i);
                if (serviceForUserLocked != null) {
                    serviceForUserLocked.onProvideDataStream(parcelFileDescriptor, remoteCallback);
                } else {
                    android.util.Slog.w(TAG, "Service not available.");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void provideData(int i, android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.RemoteCallback remoteCallback) {
        synchronized (this.mLock) {
            try {
                com.android.server.wearable.WearableSensingManagerPerUserService serviceForUserLocked = getServiceForUserLocked(i);
                if (serviceForUserLocked != null) {
                    serviceForUserLocked.onProvidedData(persistableBundle, sharedMemory, remoteCallback);
                } else {
                    android.util.Slog.w(TAG, "Service not available.");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setDataRequestRateLimitWindowSize(@android.annotation.NonNull java.time.Duration duration) {
        android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Setting the data request rate limit window size to %s. This also resets the current limit and should only be callable from a test.", new java.lang.Object[]{duration}));
        this.mDataRequestRateLimiter = new com.android.server.utils.quota.MultiRateLimiter.Builder(this.mContext).addRateLimit(android.app.wearable.WearableSensingDataRequest.getRateLimit(), duration).build();
    }

    @com.android.internal.annotations.VisibleForTesting
    void resetDataRequestRateLimitWindowSize() {
        android.util.Slog.w(TAG, "Resetting the data request rate limit window size back to the default value. This also resets the current limit and should only be callable from a test.");
        this.mDataRequestRateLimiter = new com.android.server.utils.quota.MultiRateLimiter.Builder(this.mContext).addRateLimit(android.app.wearable.WearableSensingDataRequest.getRateLimit(), android.app.wearable.WearableSensingDataRequest.getRateLimitWindowSize()).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.wearable.WearableSensingManagerService.DataRequestObserverContext getDataRequestObserverContext(int i, int i2, android.app.PendingIntent pendingIntent) {
        synchronized (this.mDataRequestObserverContexts) {
            try {
                for (com.android.server.wearable.WearableSensingManagerService.DataRequestObserverContext dataRequestObserverContext : this.mDataRequestObserverContexts) {
                    if (dataRequestObserverContext.mDataType == i && dataRequestObserverContext.mUserId == i2 && dataRequestObserverContext.mDataRequestPendingIntent.equals(pendingIntent)) {
                        return dataRequestObserverContext;
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public android.os.RemoteCallback createDataRequestRemoteCallback(final android.app.PendingIntent pendingIntent, final int i) {
        return new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.wearable.WearableSensingManagerService$$ExternalSyntheticLambda0
            public final void onResult(android.os.Bundle bundle) {
                com.android.server.wearable.WearableSensingManagerService.this.lambda$createDataRequestRemoteCallback$1(i, pendingIntent, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDataRequestRemoteCallback$1(int i, android.app.PendingIntent pendingIntent, android.os.Bundle bundle) {
        android.app.wearable.WearableSensingDataRequest wearableSensingDataRequest = (android.app.wearable.WearableSensingDataRequest) bundle.getParcelable("android.app.wearable.WearableSensingDataRequestBundleKey", android.app.wearable.WearableSensingDataRequest.class);
        if (wearableSensingDataRequest == null) {
            android.util.Slog.e(TAG, "Received data request callback without a request.");
            return;
        }
        android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) bundle.getParcelable("android.app.wearable.WearableSensingDataRequestStatusCallbackBundleKey", android.os.RemoteCallback.class);
        if (remoteCallback == null) {
            android.util.Slog.e(TAG, "Received data request callback without a status callback.");
            return;
        }
        if (wearableSensingDataRequest.getDataSize() > android.app.wearable.WearableSensingDataRequest.getMaxRequestSize()) {
            android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("WearableSensingDataRequest size exceeds the maximum allowed size of %s bytes. Dropping the request.", new java.lang.Object[]{java.lang.Integer.valueOf(android.app.wearable.WearableSensingDataRequest.getMaxRequestSize())}));
            com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 3);
            return;
        }
        if (!this.mDataRequestRateLimiter.isWithinQuota(i, "android", RATE_LIMITER_TAG)) {
            android.util.Slog.w(TAG, "Data request exceeded rate limit. Dropping the request.");
            com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 4);
            return;
        }
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.app.wearable.extra.WEARABLE_SENSING_DATA_REQUEST", (android.os.Parcelable) wearableSensingDataRequest);
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setPendingIntentBackgroundActivityStartMode(2);
        this.mDataRequestRateLimiter.noteEvent(i, "android", RATE_LIMITER_TAG);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                pendingIntent.send(getContext(), 0, intent, null, null, null, makeBasic.toBundle());
                com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 1);
                android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("Sending data request to %s: %s", new java.lang.Object[]{pendingIntent.getCreatorPackage(), wearableSensingDataRequest.toExpandedString()}));
            } catch (android.app.PendingIntent.CanceledException e) {
                android.util.Slog.w(TAG, "Could not deliver pendingIntent: " + pendingIntent);
                com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 2);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callPerUserServiceIfExist(java.util.function.Consumer<com.android.server.wearable.WearableSensingManagerPerUserService> consumer, android.os.RemoteCallback remoteCallback) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        synchronized (this.mLock) {
            try {
                com.android.server.wearable.WearableSensingManagerPerUserService serviceForUserLocked = getServiceForUserLocked(callingUserId);
                if (serviceForUserLocked == null) {
                    android.util.Slog.w(TAG, "Service not available for userId " + callingUserId);
                    com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 3);
                    return;
                }
                consumer.accept(serviceForUserLocked);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class WearableSensingManagerInternal extends android.app.wearable.IWearableSensingManager.Stub {
        private WearableSensingManagerInternal() {
        }

        public void provideConnection(final android.os.ParcelFileDescriptor parcelFileDescriptor, final android.os.RemoteCallback remoteCallback) {
            android.util.Slog.i(com.android.server.wearable.WearableSensingManagerService.TAG, "WearableSensingManagerInternal provideConnection.");
            java.util.Objects.requireNonNull(parcelFileDescriptor);
            java.util.Objects.requireNonNull(remoteCallback);
            com.android.server.wearable.WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_WEARABLE_SENSING_SERVICE", com.android.server.wearable.WearableSensingManagerService.TAG);
            if (!com.android.server.wearable.WearableSensingManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.wearable.WearableSensingManagerService.TAG, "Service not available.");
                com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 3);
            } else {
                com.android.server.wearable.WearableSensingManagerService.this.callPerUserServiceIfExist(new java.util.function.Consumer() { // from class: com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.wearable.WearableSensingManagerPerUserService) obj).onProvideConnection(parcelFileDescriptor, remoteCallback);
                    }
                }, remoteCallback);
            }
        }

        public void provideDataStream(final android.os.ParcelFileDescriptor parcelFileDescriptor, final android.os.RemoteCallback remoteCallback) {
            android.util.Slog.i(com.android.server.wearable.WearableSensingManagerService.TAG, "WearableSensingManagerInternal provideDataStream.");
            java.util.Objects.requireNonNull(parcelFileDescriptor);
            java.util.Objects.requireNonNull(remoteCallback);
            com.android.server.wearable.WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_WEARABLE_SENSING_SERVICE", com.android.server.wearable.WearableSensingManagerService.TAG);
            if (!com.android.server.wearable.WearableSensingManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.wearable.WearableSensingManagerService.TAG, "Service not available.");
                com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 3);
            } else {
                com.android.server.wearable.WearableSensingManagerService.this.callPerUserServiceIfExist(new java.util.function.Consumer() { // from class: com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.wearable.WearableSensingManagerPerUserService) obj).onProvideDataStream(parcelFileDescriptor, remoteCallback);
                    }
                }, remoteCallback);
            }
        }

        public void provideData(final android.os.PersistableBundle persistableBundle, final android.os.SharedMemory sharedMemory, final android.os.RemoteCallback remoteCallback) {
            android.util.Slog.d(com.android.server.wearable.WearableSensingManagerService.TAG, "WearableSensingManagerInternal provideData.");
            java.util.Objects.requireNonNull(persistableBundle);
            java.util.Objects.requireNonNull(remoteCallback);
            com.android.server.wearable.WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_WEARABLE_SENSING_SERVICE", com.android.server.wearable.WearableSensingManagerService.TAG);
            if (!com.android.server.wearable.WearableSensingManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.wearable.WearableSensingManagerService.TAG, "Service not available.");
                com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 3);
            } else {
                com.android.server.wearable.WearableSensingManagerService.this.callPerUserServiceIfExist(new java.util.function.Consumer() { // from class: com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.wearable.WearableSensingManagerPerUserService) obj).onProvidedData(persistableBundle, sharedMemory, remoteCallback);
                    }
                }, remoteCallback);
            }
        }

        public void registerDataRequestObserver(final int i, final android.app.PendingIntent pendingIntent, final android.os.RemoteCallback remoteCallback) {
            final android.os.RemoteCallback remoteCallback2;
            final int i2;
            android.util.Slog.i(com.android.server.wearable.WearableSensingManagerService.TAG, "WearableSensingManagerInternal registerDataRequestObserver.");
            java.util.Objects.requireNonNull(pendingIntent);
            com.android.server.wearable.WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_WEARABLE_SENSING_SERVICE", com.android.server.wearable.WearableSensingManagerService.TAG);
            if (!com.android.server.wearable.WearableSensingManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.wearable.WearableSensingManagerService.TAG, "Service not available.");
                com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 3);
                return;
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (com.android.server.wearable.WearableSensingManagerService.this.mDataRequestObserverContexts) {
                try {
                    com.android.server.wearable.WearableSensingManagerService.DataRequestObserverContext dataRequestObserverContext = com.android.server.wearable.WearableSensingManagerService.this.getDataRequestObserverContext(i, callingUserId, pendingIntent);
                    if (dataRequestObserverContext != null) {
                        android.util.Slog.i(com.android.server.wearable.WearableSensingManagerService.TAG, "Received duplicate data request observer.");
                        remoteCallback2 = dataRequestObserverContext.mDataRequestRemoteCallback;
                        i2 = dataRequestObserverContext.mDataRequestObserverId;
                    } else {
                        android.os.RemoteCallback createDataRequestRemoteCallback = com.android.server.wearable.WearableSensingManagerService.this.createDataRequestRemoteCallback(pendingIntent, callingUserId);
                        int andIncrement = com.android.server.wearable.WearableSensingManagerService.this.mNextDataRequestObserverId.getAndIncrement();
                        com.android.server.wearable.WearableSensingManagerService.this.mDataRequestObserverContexts.add(new com.android.server.wearable.WearableSensingManagerService.DataRequestObserverContext(i, callingUserId, andIncrement, pendingIntent, createDataRequestRemoteCallback));
                        remoteCallback2 = createDataRequestRemoteCallback;
                        i2 = andIncrement;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.wearable.WearableSensingManagerService.this.callPerUserServiceIfExist(new java.util.function.Consumer() { // from class: com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wearable.WearableSensingManagerService.WearableSensingManagerInternal.lambda$registerDataRequestObserver$3(i, remoteCallback2, i2, pendingIntent, remoteCallback, (com.android.server.wearable.WearableSensingManagerPerUserService) obj);
                }
            }, remoteCallback);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$registerDataRequestObserver$3(int i, android.os.RemoteCallback remoteCallback, int i2, android.app.PendingIntent pendingIntent, android.os.RemoteCallback remoteCallback2, com.android.server.wearable.WearableSensingManagerPerUserService wearableSensingManagerPerUserService) {
            wearableSensingManagerPerUserService.onRegisterDataRequestObserver(i, remoteCallback, i2, pendingIntent.getCreatorPackage(), remoteCallback2);
        }

        public void unregisterDataRequestObserver(final int i, android.app.PendingIntent pendingIntent, final android.os.RemoteCallback remoteCallback) {
            android.util.Slog.i(com.android.server.wearable.WearableSensingManagerService.TAG, "WearableSensingManagerInternal unregisterDataRequestObserver.");
            java.util.Objects.requireNonNull(pendingIntent);
            java.util.Objects.requireNonNull(remoteCallback);
            com.android.server.wearable.WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_WEARABLE_SENSING_SERVICE", com.android.server.wearable.WearableSensingManagerService.TAG);
            if (!com.android.server.wearable.WearableSensingManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.wearable.WearableSensingManagerService.TAG, "Service not available.");
                com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 3);
                return;
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (com.android.server.wearable.WearableSensingManagerService.this.mDataRequestObserverContexts) {
                try {
                    com.android.server.wearable.WearableSensingManagerService.DataRequestObserverContext dataRequestObserverContext = com.android.server.wearable.WearableSensingManagerService.this.getDataRequestObserverContext(i, callingUserId, pendingIntent);
                    if (dataRequestObserverContext == null) {
                        android.util.Slog.w(com.android.server.wearable.WearableSensingManagerService.TAG, "Previous observer not found, cannot unregister.");
                        return;
                    }
                    com.android.server.wearable.WearableSensingManagerService.this.mDataRequestObserverContexts.remove(dataRequestObserverContext);
                    final int i2 = dataRequestObserverContext.mDataRequestObserverId;
                    final java.lang.String creatorPackage = dataRequestObserverContext.mDataRequestPendingIntent.getCreatorPackage();
                    com.android.server.wearable.WearableSensingManagerService.this.callPerUserServiceIfExist(new java.util.function.Consumer() { // from class: com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.wearable.WearableSensingManagerPerUserService) obj).onUnregisterDataRequestObserver(i, i2, creatorPackage, remoteCallback);
                        }
                    }, remoteCallback);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void startHotwordRecognition(final android.content.ComponentName componentName, final android.os.RemoteCallback remoteCallback) {
            android.util.Slog.i(com.android.server.wearable.WearableSensingManagerService.TAG, "WearableSensingManagerInternal startHotwordRecognition.");
            java.util.Objects.requireNonNull(remoteCallback);
            com.android.server.wearable.WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_WEARABLE_SENSING_SERVICE", com.android.server.wearable.WearableSensingManagerService.TAG);
            if (!com.android.server.wearable.WearableSensingManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.wearable.WearableSensingManagerService.TAG, "Service not available.");
                com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 3);
            } else {
                com.android.server.wearable.WearableSensingManagerService.this.callPerUserServiceIfExist(new java.util.function.Consumer() { // from class: com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.wearable.WearableSensingManagerPerUserService) obj).onStartHotwordRecognition(componentName, remoteCallback);
                    }
                }, remoteCallback);
            }
        }

        public void stopHotwordRecognition(final android.os.RemoteCallback remoteCallback) {
            android.util.Slog.i(com.android.server.wearable.WearableSensingManagerService.TAG, "WearableSensingManagerInternal stopHotwordRecognition.");
            java.util.Objects.requireNonNull(remoteCallback);
            com.android.server.wearable.WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_WEARABLE_SENSING_SERVICE", com.android.server.wearable.WearableSensingManagerService.TAG);
            if (!com.android.server.wearable.WearableSensingManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.wearable.WearableSensingManagerService.TAG, "Service not available.");
                com.android.server.wearable.WearableSensingManagerPerUserService.notifyStatusCallback(remoteCallback, 3);
            } else {
                com.android.server.wearable.WearableSensingManagerService.this.callPerUserServiceIfExist(new java.util.function.Consumer() { // from class: com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.wearable.WearableSensingManagerPerUserService) obj).onStopHotwordRecognition(remoteCallback);
                    }
                }, remoteCallback);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.wearable.WearableSensingShellCommand(com.android.server.wearable.WearableSensingManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }
}
