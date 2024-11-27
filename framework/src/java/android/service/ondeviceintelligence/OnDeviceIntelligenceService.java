package android.service.ondeviceintelligence;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class OnDeviceIntelligenceService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.ondeviceintelligence.OnDeviceIntelligenceService";
    private static final java.lang.String TAG = android.service.ondeviceintelligence.OnDeviceIntelligenceService.class.getSimpleName();
    private volatile android.service.ondeviceintelligence.IRemoteProcessingService mRemoteProcessingService;

    public abstract void onDownloadFeature(android.app.ondeviceintelligence.Feature feature, android.os.CancellationSignal cancellationSignal, android.app.ondeviceintelligence.DownloadCallback downloadCallback);

    public abstract void onGetFeature(int i, android.os.OutcomeReceiver<android.app.ondeviceintelligence.Feature, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException> outcomeReceiver);

    public abstract void onGetFeatureDetails(android.app.ondeviceintelligence.Feature feature, android.os.OutcomeReceiver<android.app.ondeviceintelligence.FeatureDetails, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException> outcomeReceiver);

    public abstract void onGetReadOnlyFeatureFileDescriptorMap(android.app.ondeviceintelligence.Feature feature, java.util.function.Consumer<java.util.Map<java.lang.String, android.os.ParcelFileDescriptor>> consumer);

    public abstract void onGetVersion(java.util.function.LongConsumer longConsumer);

    public abstract void onListFeatures(android.os.OutcomeReceiver<java.util.List<android.app.ondeviceintelligence.Feature>, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException> outcomeReceiver);

    /* renamed from: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.ondeviceintelligence.IOnDeviceIntelligenceService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getVersion(final android.os.RemoteCallback remoteCallback) {
            java.util.Objects.requireNonNull(remoteCallback);
            android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.onGetVersion(new java.util.function.LongConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.LongConsumer
                public final void accept(long j) {
                    android.service.ondeviceintelligence.OnDeviceIntelligenceService.AnonymousClass1.lambda$getVersion$0(android.os.RemoteCallback.this, j);
                }
            });
        }

        static /* synthetic */ void lambda$getVersion$0(android.os.RemoteCallback remoteCallback, long j) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putLong(android.app.ondeviceintelligence.OnDeviceIntelligenceManager.API_VERSION_BUNDLE_KEY, j);
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void listFeatures(android.app.ondeviceintelligence.IListFeaturesCallback iListFeaturesCallback) {
            java.util.Objects.requireNonNull(iListFeaturesCallback);
            android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.onListFeatures(android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.wrapListFeaturesCallback(iListFeaturesCallback));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getFeature(int i, android.app.ondeviceintelligence.IFeatureCallback iFeatureCallback) {
            java.util.Objects.requireNonNull(iFeatureCallback);
            android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.onGetFeature(i, android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.wrapFeatureCallback(iFeatureCallback));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getFeatureDetails(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.IFeatureDetailsCallback iFeatureDetailsCallback) {
            java.util.Objects.requireNonNull(feature);
            java.util.Objects.requireNonNull(iFeatureDetailsCallback);
            android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.onGetFeatureDetails(feature, android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.wrapFeatureDetailsCallback(iFeatureDetailsCallback));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void requestFeatureDownload(android.app.ondeviceintelligence.Feature feature, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IDownloadCallback iDownloadCallback) {
            java.util.Objects.requireNonNull(feature);
            java.util.Objects.requireNonNull(iDownloadCallback);
            android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.onDownloadFeature(feature, android.os.CancellationSignal.fromTransport(iCancellationSignal), android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.wrapDownloadCallback(iDownloadCallback));
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getReadOnlyFileDescriptor(java.lang.String str, com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture) {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(androidFuture);
            android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.onGetReadOnlyFileDescriptor(str, androidFuture);
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void getReadOnlyFeatureFileDescriptorMap(android.app.ondeviceintelligence.Feature feature, final android.os.RemoteCallback remoteCallback) {
            java.util.Objects.requireNonNull(feature);
            java.util.Objects.requireNonNull(remoteCallback);
            android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.onGetReadOnlyFeatureFileDescriptorMap(feature, new java.util.function.Consumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.ondeviceintelligence.OnDeviceIntelligenceService.AnonymousClass1.lambda$getReadOnlyFeatureFileDescriptorMap$1(android.os.RemoteCallback.this, (java.util.Map) obj);
                }
            });
        }

        static /* synthetic */ void lambda$getReadOnlyFeatureFileDescriptorMap$1(android.os.RemoteCallback remoteCallback, java.util.Map map) {
            final android.os.Bundle bundle = new android.os.Bundle();
            java.util.Objects.requireNonNull(bundle);
            map.forEach(new java.util.function.BiConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    android.os.Bundle.this.putParcelable((java.lang.String) obj, (android.os.ParcelFileDescriptor) obj2);
                }
            });
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.ondeviceintelligence.IOnDeviceIntelligenceService
        public void registerRemoteServices(android.service.ondeviceintelligence.IRemoteProcessingService iRemoteProcessingService) {
            android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.mRemoteProcessingService = iRemoteProcessingService;
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new android.service.ondeviceintelligence.OnDeviceIntelligenceService.AnonymousClass1();
        }
        android.util.Slog.w(TAG, "Incorrect service interface, returning null.");
        return null;
    }

    public final void updateProcessingState(android.os.Bundle bundle, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.os.PersistableBundle, android.service.ondeviceintelligence.OnDeviceIntelligenceService.OnDeviceUpdateProcessingException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        if (this.mRemoteProcessingService == null) {
            throw new java.lang.IllegalStateException("Remote processing service is unavailable.");
        }
        try {
            this.mRemoteProcessingService.updateProcessingState(bundle, new android.service.ondeviceintelligence.OnDeviceIntelligenceService.AnonymousClass2(executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error in updateProcessingState: " + e);
            throw new java.lang.RuntimeException(e);
        }
    }

    /* renamed from: android.service.ondeviceintelligence.OnDeviceIntelligenceService$2, reason: invalid class name */
    class AnonymousClass2 extends android.service.ondeviceintelligence.IProcessingUpdateStatusCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$callbackExecutor;
        final /* synthetic */ android.os.OutcomeReceiver val$statusReceiver;

        AnonymousClass2(java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$statusReceiver = outcomeReceiver;
        }

        @Override // android.service.ondeviceintelligence.IProcessingUpdateStatusCallback
        public void onSuccess(final android.os.PersistableBundle persistableBundle) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$statusReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$2$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onResult(r2);
                        }
                    });
                }
            });
        }

        @Override // android.service.ondeviceintelligence.IProcessingUpdateStatusCallback
        public void onFailure(final int i, final java.lang.String str) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$statusReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$2$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$2$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onError(new android.service.ondeviceintelligence.OnDeviceIntelligenceService.OnDeviceUpdateProcessingException(r2, r3));
                        }
                    });
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.OutcomeReceiver<android.app.ondeviceintelligence.Feature, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException> wrapFeatureCallback(final android.app.ondeviceintelligence.IFeatureCallback iFeatureCallback) {
        return new android.os.OutcomeReceiver<android.app.ondeviceintelligence.Feature, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException>() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService.3
            @Override // android.os.OutcomeReceiver
            public void onResult(android.app.ondeviceintelligence.Feature feature) {
                try {
                    iFeatureCallback.onSuccess(feature);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceIntelligenceService.TAG, "Error sending feature: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException onDeviceIntelligenceManagerException) {
                try {
                    iFeatureCallback.onFailure(onDeviceIntelligenceManagerException.getErrorCode(), onDeviceIntelligenceManagerException.getMessage(), onDeviceIntelligenceManagerException.getErrorParams());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceIntelligenceService.TAG, "Error sending download feature: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.OutcomeReceiver<java.util.List<android.app.ondeviceintelligence.Feature>, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException> wrapListFeaturesCallback(final android.app.ondeviceintelligence.IListFeaturesCallback iListFeaturesCallback) {
        return new android.os.OutcomeReceiver<java.util.List<android.app.ondeviceintelligence.Feature>, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException>() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService.4
            @Override // android.os.OutcomeReceiver
            public void onResult(java.util.List<android.app.ondeviceintelligence.Feature> list) {
                try {
                    iListFeaturesCallback.onSuccess(list);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceIntelligenceService.TAG, "Error sending feature: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException onDeviceIntelligenceManagerException) {
                try {
                    iListFeaturesCallback.onFailure(onDeviceIntelligenceManagerException.getErrorCode(), onDeviceIntelligenceManagerException.getMessage(), onDeviceIntelligenceManagerException.getErrorParams());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceIntelligenceService.TAG, "Error sending download feature: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.OutcomeReceiver<android.app.ondeviceintelligence.FeatureDetails, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException> wrapFeatureDetailsCallback(final android.app.ondeviceintelligence.IFeatureDetailsCallback iFeatureDetailsCallback) {
        return new android.os.OutcomeReceiver<android.app.ondeviceintelligence.FeatureDetails, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException>() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService.5
            @Override // android.os.OutcomeReceiver
            public void onResult(android.app.ondeviceintelligence.FeatureDetails featureDetails) {
                try {
                    iFeatureDetailsCallback.onSuccess(featureDetails);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceIntelligenceService.TAG, "Error sending feature status: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException onDeviceIntelligenceManagerException) {
                try {
                    iFeatureDetailsCallback.onFailure(onDeviceIntelligenceManagerException.getErrorCode(), onDeviceIntelligenceManagerException.getMessage(), onDeviceIntelligenceManagerException.getErrorParams());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceIntelligenceService.TAG, "Error sending feature status: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.ondeviceintelligence.DownloadCallback wrapDownloadCallback(final android.app.ondeviceintelligence.IDownloadCallback iDownloadCallback) {
        return new android.app.ondeviceintelligence.DownloadCallback() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService.6
            @Override // android.app.ondeviceintelligence.DownloadCallback
            public void onDownloadStarted(long j) {
                try {
                    iDownloadCallback.onDownloadStarted(j);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceIntelligenceService.TAG, "Error sending download status: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.DownloadCallback
            public void onDownloadFailed(int i, java.lang.String str, android.os.PersistableBundle persistableBundle) {
                try {
                    iDownloadCallback.onDownloadFailed(i, str, persistableBundle);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceIntelligenceService.TAG, "Error sending download status: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.DownloadCallback
            public void onDownloadProgress(long j) {
                try {
                    iDownloadCallback.onDownloadProgress(j);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceIntelligenceService.TAG, "Error sending download status: " + e);
                }
            }

            @Override // android.app.ondeviceintelligence.DownloadCallback
            public void onDownloadCompleted(android.os.PersistableBundle persistableBundle) {
                try {
                    iDownloadCallback.lambda$onDownloadCompleted$6(persistableBundle);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceIntelligenceService.TAG, "Error sending download status: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetReadOnlyFileDescriptor(final java.lang.String str, final com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture) {
        android.util.Slog.v(TAG, "onGetReadOnlyFileDescriptor " + str);
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.ondeviceintelligence.OnDeviceIntelligenceService$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
                android.service.ondeviceintelligence.OnDeviceIntelligenceService.this.lambda$onGetReadOnlyFileDescriptor$0(str, androidFuture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGetReadOnlyFileDescriptor$0(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws java.lang.Exception {
        android.util.Slog.v(TAG, "onGetReadOnlyFileDescriptor: " + str + " under internal app storage.");
        android.os.ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            try {
                parcelFileDescriptor = android.os.ParcelFileDescriptor.open(new java.io.File(getBaseContext().getFilesDir(), str), 268435456);
                android.util.Slog.d(TAG, "Successfully opened a file with ParcelFileDescriptor.");
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.e(TAG, "Cannot open file. No ParcelFileDescriptor returned.");
            }
        } finally {
            androidFuture.complete(parcelFileDescriptor);
        }
    }

    public static class OnDeviceUpdateProcessingException extends android.service.ondeviceintelligence.OnDeviceIntelligenceService.OnDeviceIntelligenceServiceException {
        public static final int PROCESSING_UPDATE_STATUS_CONNECTION_FAILED = 1;

        @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.FIELD})
        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ErrorCode {
        }

        public OnDeviceUpdateProcessingException(int i) {
            super(i);
        }

        public OnDeviceUpdateProcessingException(int i, java.lang.String str) {
            super(i, str);
        }
    }

    public static abstract class OnDeviceIntelligenceServiceException extends java.lang.Exception {
        private final int mErrorCode;

        public OnDeviceIntelligenceServiceException(int i) {
            this.mErrorCode = i;
        }

        public OnDeviceIntelligenceServiceException(int i, java.lang.String str) {
            super(str);
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }
}
