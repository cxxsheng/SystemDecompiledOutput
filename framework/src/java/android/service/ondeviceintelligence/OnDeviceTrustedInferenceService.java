package android.service.ondeviceintelligence;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class OnDeviceTrustedInferenceService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.ondeviceintelligence.OnDeviceTrustedInferenceService";
    private static final java.lang.String TAG = android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.class.getSimpleName();
    private android.service.ondeviceintelligence.IRemoteStorageService mRemoteStorageService;

    public abstract void onCountTokens(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, android.os.CancellationSignal cancellationSignal, android.os.OutcomeReceiver<java.lang.Long, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException> outcomeReceiver);

    public abstract void onProcessRequest(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.CancellationSignal cancellationSignal, android.app.ondeviceintelligence.ProcessingSignal processingSignal, android.os.OutcomeReceiver<android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException> outcomeReceiver);

    public abstract void onProcessRequestStreaming(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.CancellationSignal cancellationSignal, android.app.ondeviceintelligence.ProcessingSignal processingSignal, android.app.ondeviceintelligence.StreamingResponseReceiver<android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException> streamingResponseReceiver);

    public abstract void onUpdateProcessingState(android.os.Bundle bundle, android.os.OutcomeReceiver<android.os.PersistableBundle, android.service.ondeviceintelligence.OnDeviceIntelligenceService.OnDeviceUpdateProcessingException> outcomeReceiver);

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.Stub() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.1
                @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
                public void registerRemoteStorageService(android.service.ondeviceintelligence.IRemoteStorageService iRemoteStorageService) {
                    java.util.Objects.requireNonNull(iRemoteStorageService);
                    android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.this.mRemoteStorageService = iRemoteStorageService;
                }

                @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
                public void requestTokenCount(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.ITokenCountCallback iTokenCountCallback) {
                    java.util.Objects.requireNonNull(feature);
                    java.util.Objects.requireNonNull(iTokenCountCallback);
                    android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.this.onCountTokens(feature, content, android.os.CancellationSignal.fromTransport(iCancellationSignal), android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.this.wrapTokenCountCallback(iTokenCountCallback));
                }

                @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
                public void processRequestStreaming(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IStreamingResponseCallback iStreamingResponseCallback) {
                    java.util.Objects.requireNonNull(feature);
                    java.util.Objects.requireNonNull(content);
                    java.util.Objects.requireNonNull(iStreamingResponseCallback);
                    android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.this.onProcessRequestStreaming(feature, content, i, android.os.CancellationSignal.fromTransport(iCancellationSignal), android.app.ondeviceintelligence.ProcessingSignal.fromTransport(iProcessingSignal), android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.this.wrapStreamingResponseCallback(iStreamingResponseCallback));
                }

                @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
                public void processRequest(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.ICancellationSignal iCancellationSignal, android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal, android.app.ondeviceintelligence.IResponseCallback iResponseCallback) {
                    java.util.Objects.requireNonNull(feature);
                    java.util.Objects.requireNonNull(content);
                    java.util.Objects.requireNonNull(iResponseCallback);
                    android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.this.onProcessRequest(feature, content, i, android.os.CancellationSignal.fromTransport(iCancellationSignal), android.app.ondeviceintelligence.ProcessingSignal.fromTransport(iProcessingSignal), android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.this.wrapResponseCallback(iResponseCallback));
                }

                @Override // android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService
                public void updateProcessingState(android.os.Bundle bundle, android.service.ondeviceintelligence.IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) {
                    java.util.Objects.requireNonNull(bundle);
                    java.util.Objects.requireNonNull(iProcessingUpdateStatusCallback);
                    android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.this.onUpdateProcessingState(bundle, android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.wrapOutcomeReceiver(iProcessingUpdateStatusCallback));
                }
            };
        }
        android.util.Slog.w(TAG, "Incorrect service interface, returning null.");
        return null;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final java.io.FileInputStream openFileInput(java.lang.String str) throws java.io.FileNotFoundException {
        try {
            com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture = new com.android.internal.infra.AndroidFuture<>();
            this.mRemoteStorageService.getReadOnlyFileDescriptor(str, androidFuture);
            return new java.io.FileInputStream(androidFuture.get().getFileDescriptor());
        } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
            android.util.Log.w(TAG, "Cannot open file due to remote service failure");
            throw new java.io.FileNotFoundException(e.getMessage());
        }
    }

    public final void openFileInputAsync(final java.lang.String str, final java.util.concurrent.Executor executor, final java.util.function.Consumer<java.io.FileInputStream> consumer) throws java.io.FileNotFoundException {
        com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mRemoteStorageService.getReadOnlyFileDescriptor(str, androidFuture);
            androidFuture.whenCompleteAsync(new java.util.function.BiConsumer() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.lambda$openFileInputAsync$2(str, executor, consumer, (android.os.ParcelFileDescriptor) obj, (java.lang.Throwable) obj2);
                }
            }, executor);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Cannot open file due to remote service failure");
            throw new java.io.FileNotFoundException(e.getMessage());
        }
    }

    static /* synthetic */ void lambda$openFileInputAsync$2(java.lang.String str, java.util.concurrent.Executor executor, final java.util.function.Consumer consumer, final android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.e(TAG, "Failure when reading file: " + str + th);
            executor.execute(new java.lang.Runnable() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(null);
                }
            });
        } else {
            executor.execute(new java.lang.Runnable() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor()));
                }
            });
        }
    }

    public final void fetchFeatureFileInputStreamMap(android.app.ondeviceintelligence.Feature feature, java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.Map<java.lang.String, java.io.FileInputStream>> consumer) {
        try {
            this.mRemoteStorageService.getReadOnlyFeatureFileDescriptorMap(feature, wrapResultReceiverAsReadOnly(consumer, executor));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private android.os.RemoteCallback wrapResultReceiverAsReadOnly(final java.util.function.Consumer<java.util.Map<java.lang.String, java.io.FileInputStream>> consumer, final java.util.concurrent.Executor executor) {
        return new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService$$ExternalSyntheticLambda3
            @Override // android.os.RemoteCallback.OnResultListener
            public final void onResult(android.os.Bundle bundle) {
                android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.lambda$wrapResultReceiverAsReadOnly$6(executor, consumer, bundle);
            }
        });
    }

    static /* synthetic */ void lambda$wrapResultReceiverAsReadOnly$6(java.util.concurrent.Executor executor, final java.util.function.Consumer consumer, final android.os.Bundle bundle) {
        if (bundle == null) {
            executor.execute(new java.lang.Runnable() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(new java.util.HashMap());
                }
            });
            return;
        }
        final java.util.HashMap hashMap = new java.util.HashMap();
        bundle.keySet().forEach(new java.util.function.Consumer() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.lambda$wrapResultReceiverAsReadOnly$4(android.os.Bundle.this, hashMap, (java.lang.String) obj);
            }
        });
        executor.execute(new java.lang.Runnable() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(hashMap);
            }
        });
    }

    static /* synthetic */ void lambda$wrapResultReceiverAsReadOnly$4(android.os.Bundle bundle, java.util.Map map, java.lang.String str) {
        android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) bundle.getParcelable(str, android.os.ParcelFileDescriptor.class);
        if (parcelFileDescriptor != null) {
            map.put(str, new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.OutcomeReceiver<android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException> wrapResponseCallback(final android.app.ondeviceintelligence.IResponseCallback iResponseCallback) {
        return new android.os.OutcomeReceiver<android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException>() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.2
            @Override // android.os.OutcomeReceiver
            public void onResult(android.app.ondeviceintelligence.Content content) {
                try {
                    iResponseCallback.onSuccess(content);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException onDeviceIntelligenceManagerProcessingException) {
                try {
                    iResponseCallback.onFailure(onDeviceIntelligenceManagerProcessingException.getErrorCode(), onDeviceIntelligenceManagerProcessingException.getMessage(), onDeviceIntelligenceManagerProcessingException.getErrorParams());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.TAG, "Error sending result: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.ondeviceintelligence.StreamingResponseReceiver<android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException> wrapStreamingResponseCallback(final android.app.ondeviceintelligence.IStreamingResponseCallback iStreamingResponseCallback) {
        return new android.app.ondeviceintelligence.StreamingResponseReceiver<android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException>() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.3
            @Override // android.app.ondeviceintelligence.StreamingResponseReceiver
            public void onNewContent(android.app.ondeviceintelligence.Content content) {
                try {
                    iStreamingResponseCallback.onNewContent(content);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onResult(android.app.ondeviceintelligence.Content content) {
                try {
                    iStreamingResponseCallback.onSuccess(content);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException onDeviceIntelligenceManagerProcessingException) {
                try {
                    iStreamingResponseCallback.onFailure(onDeviceIntelligenceManagerProcessingException.getErrorCode(), onDeviceIntelligenceManagerProcessingException.getMessage(), onDeviceIntelligenceManagerProcessingException.getErrorParams());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.TAG, "Error sending result: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.OutcomeReceiver<java.lang.Long, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException> wrapTokenCountCallback(final android.app.ondeviceintelligence.ITokenCountCallback iTokenCountCallback) {
        return new android.os.OutcomeReceiver<java.lang.Long, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException>() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.4
            @Override // android.os.OutcomeReceiver
            public void onResult(java.lang.Long l) {
                try {
                    iTokenCountCallback.onSuccess(l.longValue());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException onDeviceIntelligenceManagerProcessingException) {
                try {
                    iTokenCountCallback.onFailure(onDeviceIntelligenceManagerProcessingException.getErrorCode(), onDeviceIntelligenceManagerProcessingException.getMessage(), onDeviceIntelligenceManagerProcessingException.getErrorParams());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.TAG, "Error sending failure: " + e);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.OutcomeReceiver<android.os.PersistableBundle, android.service.ondeviceintelligence.OnDeviceIntelligenceService.OnDeviceUpdateProcessingException> wrapOutcomeReceiver(final android.service.ondeviceintelligence.IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) {
        return new android.os.OutcomeReceiver<android.os.PersistableBundle, android.service.ondeviceintelligence.OnDeviceIntelligenceService.OnDeviceUpdateProcessingException>() { // from class: android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.5
            @Override // android.os.OutcomeReceiver
            public void onResult(android.os.PersistableBundle persistableBundle) {
                try {
                    android.service.ondeviceintelligence.IProcessingUpdateStatusCallback.this.onSuccess(persistableBundle);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.TAG, "Error sending result: " + e);
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(android.service.ondeviceintelligence.OnDeviceIntelligenceService.OnDeviceUpdateProcessingException onDeviceUpdateProcessingException) {
                try {
                    android.service.ondeviceintelligence.IProcessingUpdateStatusCallback.this.onFailure(onDeviceUpdateProcessingException.getErrorCode(), onDeviceUpdateProcessingException.getMessage());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(android.service.ondeviceintelligence.OnDeviceTrustedInferenceService.TAG, "Error sending exception details: " + e);
                }
            }
        };
    }
}
