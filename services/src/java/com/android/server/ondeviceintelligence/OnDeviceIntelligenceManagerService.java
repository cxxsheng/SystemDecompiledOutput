package com.android.server.ondeviceintelligence;

/* loaded from: classes2.dex */
public class OnDeviceIntelligenceManagerService extends com.android.server.SystemService {
    private static final boolean DEFAULT_SERVICE_ENABLED = true;
    private static final java.lang.String KEY_SERVICE_ENABLED = "service_enabled";
    private static final java.lang.String NAMESPACE_ON_DEVICE_INTELLIGENCE = "ondeviceintelligence";
    private static final java.lang.String TAG = com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.class.getSimpleName();
    private final android.content.Context mContext;
    volatile boolean mIsServiceEnabled;
    protected final java.lang.Object mLock;
    private com.android.server.ondeviceintelligence.RemoteOnDeviceTrustedInferenceService mRemoteInferenceService;
    private com.android.server.ondeviceintelligence.RemoteOnDeviceIntelligenceService mRemoteOnDeviceIntelligenceService;

    public OnDeviceIntelligenceManagerService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mContext = context;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("on_device_intelligence", new com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.OnDeviceIntelligenceManagerInternal(), true);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener(NAMESPACE_ON_DEVICE_INTELLIGENCE, com.android.internal.os.BackgroundThread.getExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda0
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.lambda$onBootPhase$0(properties);
                }
            });
            this.mIsServiceEnabled = isServiceEnabled();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$0(android.provider.DeviceConfig.Properties properties) {
        onDeviceConfigChange(properties.getKeyset());
    }

    private void onDeviceConfigChange(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        if (set.contains(KEY_SERVICE_ENABLED)) {
            this.mIsServiceEnabled = isServiceEnabled();
        }
    }

    private boolean isServiceEnabled() {
        return android.provider.DeviceConfig.getBoolean(NAMESPACE_ON_DEVICE_INTELLIGENCE, KEY_SERVICE_ENABLED, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class OnDeviceIntelligenceManagerInternal extends android.app.ondeviceintelligence.IOnDeviceIntelligenceManager.Stub {
        private OnDeviceIntelligenceManagerInternal() {
        }

        public void getVersion(final android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            android.util.Slog.i(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "OnDeviceIntelligenceManagerInternal getVersion");
            java.util.Objects.requireNonNull(remoteCallback);
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG);
            if (!com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Service not available");
                remoteCallback.sendResult((android.os.Bundle) null);
            } else {
                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.ensureRemoteIntelligenceServiceInitialized();
                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$OnDeviceIntelligenceManagerInternal$$ExternalSyntheticLambda1
                    public final void runNoResult(java.lang.Object obj) {
                        ((android.service.ondeviceintelligence.IOnDeviceIntelligenceService) obj).getVersion(remoteCallback);
                    }
                });
            }
        }

        public void getFeature(final int i, final android.app.ondeviceintelligence.IFeatureCallback iFeatureCallback) throws android.os.RemoteException {
            android.util.Slog.i(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "OnDeviceIntelligenceManagerInternal getFeatures");
            java.util.Objects.requireNonNull(iFeatureCallback);
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG);
            if (!com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Service not available");
                iFeatureCallback.onFailure(1000, "OnDeviceIntelligenceManagerService is unavailable", new android.os.PersistableBundle());
            } else {
                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.ensureRemoteIntelligenceServiceInitialized();
                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$OnDeviceIntelligenceManagerInternal$$ExternalSyntheticLambda5
                    public final void runNoResult(java.lang.Object obj) {
                        ((android.service.ondeviceintelligence.IOnDeviceIntelligenceService) obj).getFeature(i, iFeatureCallback);
                    }
                });
            }
        }

        public void listFeatures(final android.app.ondeviceintelligence.IListFeaturesCallback iListFeaturesCallback) throws android.os.RemoteException {
            android.util.Slog.i(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "OnDeviceIntelligenceManagerInternal getFeatures");
            java.util.Objects.requireNonNull(iListFeaturesCallback);
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG);
            if (!com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Service not available");
                iListFeaturesCallback.onFailure(1000, "OnDeviceIntelligenceManagerService is unavailable", new android.os.PersistableBundle());
            } else {
                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.ensureRemoteIntelligenceServiceInitialized();
                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$OnDeviceIntelligenceManagerInternal$$ExternalSyntheticLambda2
                    public final void runNoResult(java.lang.Object obj) {
                        ((android.service.ondeviceintelligence.IOnDeviceIntelligenceService) obj).listFeatures(iListFeaturesCallback);
                    }
                });
            }
        }

        public void getFeatureDetails(final android.app.ondeviceintelligence.Feature feature, final android.app.ondeviceintelligence.IFeatureDetailsCallback iFeatureDetailsCallback) throws android.os.RemoteException {
            android.util.Slog.i(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "OnDeviceIntelligenceManagerInternal getFeatureStatus");
            java.util.Objects.requireNonNull(feature);
            java.util.Objects.requireNonNull(iFeatureDetailsCallback);
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG);
            if (!com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Service not available");
                iFeatureDetailsCallback.onFailure(1000, "OnDeviceIntelligenceManagerService is unavailable", new android.os.PersistableBundle());
            } else {
                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.ensureRemoteIntelligenceServiceInitialized();
                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$OnDeviceIntelligenceManagerInternal$$ExternalSyntheticLambda3
                    public final void runNoResult(java.lang.Object obj) {
                        ((android.service.ondeviceintelligence.IOnDeviceIntelligenceService) obj).getFeatureDetails(feature, iFeatureDetailsCallback);
                    }
                });
            }
        }

        public void requestFeatureDownload(final android.app.ondeviceintelligence.Feature feature, final android.os.ICancellationSignal iCancellationSignal, final android.app.ondeviceintelligence.IDownloadCallback iDownloadCallback) throws android.os.RemoteException {
            android.util.Slog.i(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "OnDeviceIntelligenceManagerInternal requestFeatureDownload");
            java.util.Objects.requireNonNull(feature);
            java.util.Objects.requireNonNull(iDownloadCallback);
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG);
            if (!com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Service not available");
                iDownloadCallback.onDownloadFailed(4, "OnDeviceIntelligenceManagerService is unavailable", new android.os.PersistableBundle());
            }
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.ensureRemoteIntelligenceServiceInitialized();
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$OnDeviceIntelligenceManagerInternal$$ExternalSyntheticLambda4
                public final void runNoResult(java.lang.Object obj) {
                    ((android.service.ondeviceintelligence.IOnDeviceIntelligenceService) obj).requestFeatureDownload(feature, iCancellationSignal, iDownloadCallback);
                }
            });
        }

        public void requestTokenCount(final android.app.ondeviceintelligence.Feature feature, final android.app.ondeviceintelligence.Content content, final android.os.ICancellationSignal iCancellationSignal, final android.app.ondeviceintelligence.ITokenCountCallback iTokenCountCallback) throws android.os.RemoteException {
            android.util.Slog.i(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "OnDeviceIntelligenceManagerInternal prepareFeatureProcessing");
            java.util.Objects.requireNonNull(feature);
            java.util.Objects.requireNonNull(content);
            java.util.Objects.requireNonNull(iTokenCountCallback);
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG);
            if (!com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Service not available");
                iTokenCountCallback.onFailure(1000, "OnDeviceIntelligenceManagerService is unavailable", new android.os.PersistableBundle());
            }
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.ensureRemoteTrustedInferenceServiceInitialized();
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteInferenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$OnDeviceIntelligenceManagerInternal$$ExternalSyntheticLambda0
                public final void runNoResult(java.lang.Object obj) {
                    ((android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService) obj).requestTokenCount(feature, content, iCancellationSignal, iTokenCountCallback);
                }
            });
        }

        public void processRequest(final android.app.ondeviceintelligence.Feature feature, final android.app.ondeviceintelligence.Content content, final int i, final android.os.ICancellationSignal iCancellationSignal, final android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, final android.app.ondeviceintelligence.IResponseCallback iResponseCallback) throws android.os.RemoteException {
            android.util.Slog.i(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "OnDeviceIntelligenceManagerInternal processRequest");
            java.util.Objects.requireNonNull(feature);
            java.util.Objects.requireNonNull(iResponseCallback);
            java.util.Objects.requireNonNull(content);
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG);
            if (!com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Service not available");
                iResponseCallback.onFailure(15, "OnDeviceIntelligenceManagerService is unavailable", new android.os.PersistableBundle());
            }
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.ensureRemoteTrustedInferenceServiceInitialized();
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteInferenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$OnDeviceIntelligenceManagerInternal$$ExternalSyntheticLambda6
                public final void runNoResult(java.lang.Object obj) {
                    ((android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService) obj).processRequest(feature, content, i, iCancellationSignal, iProcessingSignal, iResponseCallback);
                }
            });
        }

        public void processRequestStreaming(final android.app.ondeviceintelligence.Feature feature, final android.app.ondeviceintelligence.Content content, final int i, final android.os.ICancellationSignal iCancellationSignal, final android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, final android.app.ondeviceintelligence.IStreamingResponseCallback iStreamingResponseCallback) throws android.os.RemoteException {
            android.util.Slog.i(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "OnDeviceIntelligenceManagerInternal processRequestStreaming");
            java.util.Objects.requireNonNull(feature);
            java.util.Objects.requireNonNull(content);
            java.util.Objects.requireNonNull(iStreamingResponseCallback);
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG);
            if (!com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Service not available");
                iStreamingResponseCallback.onFailure(15, "OnDeviceIntelligenceManagerService is unavailable", new android.os.PersistableBundle());
            }
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.ensureRemoteTrustedInferenceServiceInitialized();
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteInferenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$OnDeviceIntelligenceManagerInternal$$ExternalSyntheticLambda7
                public final void runNoResult(java.lang.Object obj) {
                    ((android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService) obj).processRequestStreaming(feature, content, i, iCancellationSignal, iProcessingSignal, iStreamingResponseCallback);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureRemoteIntelligenceServiceInitialized() throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteOnDeviceIntelligenceService == null) {
                    java.lang.String string = this.mContext.getResources().getString(android.R.string.config_defaultListenerAccessPackages);
                    validateService(string, false);
                    this.mRemoteOnDeviceIntelligenceService = new com.android.server.ondeviceintelligence.RemoteOnDeviceIntelligenceService(this.mContext, android.content.ComponentName.unflattenFromString(string), android.os.UserHandle.SYSTEM.getIdentifier());
                    this.mRemoteOnDeviceIntelligenceService.setServiceLifecycleCallbacks(new com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<android.service.ondeviceintelligence.IOnDeviceIntelligenceService>() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.1
                        public void onConnected(@android.annotation.NonNull android.service.ondeviceintelligence.IOnDeviceIntelligenceService iOnDeviceIntelligenceService) {
                            try {
                                iOnDeviceIntelligenceService.registerRemoteServices(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.getRemoteProcessingService());
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Failed to send connected event", e);
                            }
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$2, reason: invalid class name */
    class AnonymousClass2 extends android.service.ondeviceintelligence.IRemoteProcessingService.Stub {
        AnonymousClass2() {
        }

        public void updateProcessingState(final android.os.Bundle bundle, final android.service.ondeviceintelligence.IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) {
            try {
                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.ensureRemoteTrustedInferenceServiceInitialized();
                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteInferenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$2$$ExternalSyntheticLambda0
                    public final void runNoResult(java.lang.Object obj) {
                        ((android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService) obj).updateProcessingState(bundle, iProcessingUpdateStatusCallback);
                    }
                });
            } catch (android.os.RemoteException e) {
                try {
                    iProcessingUpdateStatusCallback.onFailure(1, "Received failure invoking the remote processing service.");
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Failed to send failure status.", e2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public android.service.ondeviceintelligence.IRemoteProcessingService.Stub getRemoteProcessingService() {
        return new com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.AnonymousClass2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureRemoteTrustedInferenceServiceInitialized() throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteInferenceService == null) {
                    java.lang.String string = this.mContext.getResources().getString(android.R.string.config_defaultMusicRecognitionService);
                    validateService(string, true);
                    this.mRemoteInferenceService = new com.android.server.ondeviceintelligence.RemoteOnDeviceTrustedInferenceService(this.mContext, android.content.ComponentName.unflattenFromString(string), android.os.UserHandle.SYSTEM.getIdentifier());
                    this.mRemoteInferenceService.setServiceLifecycleCallbacks(new com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService>() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.3
                        public void onConnected(@android.annotation.NonNull android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService iOnDeviceTrustedInferenceService) {
                            try {
                                com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.ensureRemoteIntelligenceServiceInitialized();
                                iOnDeviceTrustedInferenceService.registerRemoteStorageService(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.getIRemoteStorageService());
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.w(com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.TAG, "Failed to send connected event", e);
                            }
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$4, reason: invalid class name */
    class AnonymousClass4 extends android.service.ondeviceintelligence.IRemoteStorageService.Stub {
        AnonymousClass4() {
        }

        public void getReadOnlyFileDescriptor(final java.lang.String str, final com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture) {
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$4$$ExternalSyntheticLambda1
                public final void runNoResult(java.lang.Object obj) {
                    ((android.service.ondeviceintelligence.IOnDeviceIntelligenceService) obj).getReadOnlyFileDescriptor(str, androidFuture);
                }
            });
        }

        public void getReadOnlyFeatureFileDescriptorMap(final android.app.ondeviceintelligence.Feature feature, final android.os.RemoteCallback remoteCallback) {
            com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$4$$ExternalSyntheticLambda0
                public final void runNoResult(java.lang.Object obj) {
                    ((android.service.ondeviceintelligence.IOnDeviceIntelligenceService) obj).getReadOnlyFeatureFileDescriptorMap(feature, remoteCallback);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public android.service.ondeviceintelligence.IRemoteStorageService.Stub getIRemoteStorageService() {
        return new com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.AnonymousClass4();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void validateService(java.lang.String str, boolean z) throws android.os.RemoteException {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.RuntimeException("");
        }
        android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(android.content.ComponentName.unflattenFromString(str), 786432L, 0);
        if (serviceInfo != null) {
            if (!z) {
                checkServiceRequiresPermission(serviceInfo, "android.permission.BIND_ON_DEVICE_INTELLIGENCE_SERVICE");
                return;
            }
            checkServiceRequiresPermission(serviceInfo, "android.permission.BIND_ON_DEVICE_TRUSTED_SERVICE");
            if (!isIsolatedService(serviceInfo)) {
                throw new java.lang.SecurityException("Call required an isolated service, but the configured service: " + str + ", is not isolated");
            }
            return;
        }
        throw new java.lang.RuntimeException("Could not find service info for serviceName: " + str);
    }

    private static void checkServiceRequiresPermission(android.content.pm.ServiceInfo serviceInfo, java.lang.String str) {
        if (!str.equals(serviceInfo.permission)) {
            throw new java.lang.SecurityException(java.lang.String.format("Service %s requires %s permission. Found %s permission", serviceInfo.getComponentName(), str, serviceInfo.permission));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isIsolatedService(@android.annotation.NonNull android.content.pm.ServiceInfo serviceInfo) {
        return (serviceInfo.flags & 2) != 0 && (serviceInfo.flags & 4) == 0;
    }
}
